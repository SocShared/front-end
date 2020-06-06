package ml.socshared.frontend.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.form.CheckBoxGroupForm;
import ml.socshared.frontend.domain.model.form.PublicationForm;
import ml.socshared.frontend.domain.storage.request.PublicationRequest;
import ml.socshared.frontend.domain.storage.response.Publication;
import ml.socshared.frontend.exception.impl.HttpBadRequestException;
import ml.socshared.frontend.service.PublicationService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@Slf4j
public class PublicationServiceImpl implements PublicationService {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a", Locale.ENGLISH);

    @Override
    public void writePublicationPage(Model model, String accessToken) {
        model.addAttribute("publication", new PublicationForm());
    }

    @Override
    public void sendPublication(PublicationForm pub, Model model, String accessToken) {
        writePublicationPage(model, accessToken);
        PublicationRequest p = convertPub(pub);
    }

    private PublicationRequest convertPub(PublicationForm p) {
        final int ms = 1000;
        PublicationRequest pr = new PublicationRequest();
        String nowTime = LocalDateTime.now().format(formatter);
        pr.setText(p.getText());
        LocalDateTime time = null;
        try {
            time = LocalDateTime.parse(p.getDateTime(), formatter);
        } catch(DateTimeParseException exp) {
            String msg = String.format("invalid date time string format -> %s", p.getDateTime());
            log.warn(msg);
            throw new HttpBadRequestException(msg);
        }
        Date d = new Date(time.toEpochSecond(ZoneOffset.UTC)*ms);
        pr.setPublicationDateTime(d);
        if(p.getIsDeferred()) {
            pr.setType(Publication.PostType.DEFERRED);
        } else {
            pr.setType(Publication.PostType.IN_REAL_TIME);
        }
        return pr;
    }

}
