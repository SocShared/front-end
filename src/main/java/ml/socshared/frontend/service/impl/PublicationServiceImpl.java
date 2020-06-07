package ml.socshared.frontend.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.StorageClient;
import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.model.form.CheckBoxGroupForm;
import ml.socshared.frontend.domain.model.form.PublicationForm;
import ml.socshared.frontend.domain.storage.request.GroupRequest;
import ml.socshared.frontend.domain.storage.request.PublicationRequest;
import ml.socshared.frontend.domain.storage.response.Publication;
import ml.socshared.frontend.exception.impl.HttpBadRequestException;
import ml.socshared.frontend.service.PublicationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a", Locale.ENGLISH);
    private final StorageClient storageClient;

    @Override
    public void writePublicationPage(Model model, String accessToken) {
        model.addAttribute("publication", new PublicationForm());
    }

    @Override
    public void sendPublication(@Valid PublicationForm pub, Model model, String accessToken) {
        writePublicationPage(model, accessToken);
        PublicationRequest p = convertPublication(pub);
        Page<GroupResponse> groups = storageClient.getGroupsList(0, 100, storageToken(accessToken));
        String[] groupsIds = new String[(int) groups.getTotalElements()];
        int totalPages = groups.getTotalPages();
        int elementIndex = 0;
        for(GroupResponse g : groups) {
            groupsIds[elementIndex] = g.getGroupId();
            elementIndex++;
        }
        for(int i = 1; i < totalPages; i++) {
            for(GroupResponse g : groups) {
                groupsIds[elementIndex] = g.getGroupId();
                elementIndex++;
            }
            groups = storageClient.getGroupsList(i, 100, storageToken(accessToken));
        }
        p.setGroupIds(groupsIds);
        p.setHashTags(new String[0]);
        storageClient.savePublication(p, storageToken(accessToken));
    }

    private PublicationRequest convertPublication(PublicationForm p) {
        final int ms = 1000;
        PublicationRequest pr = new PublicationRequest();
        pr.setText(p.getText());
        LocalDateTime time = null;
        try {
            if(p.getDateTime() != "") {
                time = LocalDateTime.parse(p.getDateTime(), formatter);
                Date d = new Date(time.toEpochSecond(ZoneOffset.UTC)*ms);
                pr.setPublicationDateTime(d);
            }
        } catch(DateTimeParseException exp) {
            String msg = String.format("invalid date time string format -> %s", p.getDateTime());
            log.warn(msg);
            throw new HttpBadRequestException(msg);
        }

        if(p.getIsDeferred()) {
            pr.setType(Publication.PostType.DEFERRED);
        } else {
            pr.setType(Publication.PostType.IN_REAL_TIME);
        }
        return pr;
    }

    String storageToken(String token) {
        return "Bearer " + token;
    }


}
