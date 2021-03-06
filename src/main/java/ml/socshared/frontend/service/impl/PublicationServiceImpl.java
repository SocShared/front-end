package ml.socshared.frontend.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.client.StorageClient;
import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.model.form.PublicationForm;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.storage.GroupPostStatus;
import ml.socshared.frontend.domain.storage.PostType;
import ml.socshared.frontend.domain.storage.request.PublicationRequest;
import ml.socshared.frontend.domain.storage.response.PostStatus;
import ml.socshared.frontend.domain.storage.response.PublicationResponse;
import ml.socshared.frontend.domain.text.response.KeyWordResponse;
import ml.socshared.frontend.exception.impl.HttpBadRequestException;
import ml.socshared.frontend.service.PublicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final StorageClient storageClient;
    private final GatewayServiceClient gatewayClient;


    @Override
    public void writePublicationPage(Model model, String accessToken) {
        Page<GroupResponse> groups = storageClient.getGroupsList(0, 100, authToken(accessToken));
        if (groups.isEmpty()) {
            throw new HttpBadRequestException("Для того, чтобы отправлять публикации. " +
                    "Подключите группы к системе");
        }
        model.addAttribute("publication", new PublicationForm());
    }

    @Override
    public void sendPublication(@Valid PublicationForm pub, Model model, String accessToken) {
        writePublicationPage(model, accessToken);
        PublicationRequest p = convertPublication(pub);
        if (p.getPublicationDateTime() != null) {
            p.setPublicationDateTime(new Date(p.getPublicationDateTime().getTime() - 3 * 60 * 60 * 1000));
        }
        Page<GroupResponse> groups = storageClient.getGroupsList(0, 100, authToken(accessToken));
        String[] groupsIds = new String[(int) groups.getTotalElements()];
        int totalPages = groups.getTotalPages();
        int elementIndex = 0;
        for (GroupResponse g : groups) {
            groupsIds[elementIndex] = g.getGroupId();
            elementIndex++;
        }
        for (int i = 1; i < totalPages; i++) {
            for (GroupResponse g : groups) {
                groupsIds[elementIndex] = g.getGroupId();
                elementIndex++;
            }
            groups = storageClient.getGroupsList(i, 100, authToken(accessToken));
        }
        String[] keywords = pub.getKeywords().split(",");
        List<String> result = new ArrayList<>();
        for (String keyword : keywords) {
            if (!keyword.isBlank())
                result.add(keyword);
        }

        p.setHashTags(result.toArray(String[]::new));
        p.setGroupIds(groupsIds);
        storageClient.savePublication(p, authToken(accessToken));
    }

    @Override
    public List<KeyWordResponse> getKeyWords(String text, String token) {
        return gatewayClient.getKeyWords(text, null, null, "Bearer" + token);
    }

    @Override
    public void getPublicationsByGroupId(UUID systemGroupId, Pageable pageable, Model model, String accessToken) {
        RestResponsePage<PublicationResponse> postPage = storageClient.getPostList(systemGroupId, pageable.getPageNumber(), pageable.getPageSize(), authToken(accessToken));

        List<PostStatus> postStatusList = new ArrayList<>();
        for (PublicationResponse pub : postPage) {
            for (GroupPostStatus status : pub.getPostStatus()) {
                if (status.getGroupId().equals(systemGroupId)) {
                    postStatusList.add(status.getPostStatus());
                    break;
                }
            }
        }
        model.addAttribute("post_page", postPage);
        model.addAttribute("status_list", postStatusList);
    }


    private PublicationRequest convertPublication(PublicationForm p) {
        final int ms = 1000;
        PublicationRequest pr = new PublicationRequest();
        pr.setText(p.getText());
        LocalDateTime time;
        try {
            if (!p.getDateTime().isEmpty()) {
                time = LocalDateTime.parse(p.getDateTime(), formatter);
                Date d = new Date(time.toEpochSecond(ZoneOffset.UTC) * ms);
                pr.setPublicationDateTime(d);
            }
        } catch (DateTimeParseException exp) {
            String msg = String.format("invalid date time string format -> %s", p.getDateTime());
            log.warn(msg);
            throw new HttpBadRequestException(msg);
        }

        if (p.getIsDeferred()) {
            pr.setType(PostType.DEFERRED);
        } else {
            pr.setType(PostType.IN_REAL_TIME);
        }
        return pr;
    }


    String authToken(String token) {
        return "Bearer " + token;
    }
}