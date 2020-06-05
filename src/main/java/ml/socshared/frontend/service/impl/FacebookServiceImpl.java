package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.FacebookClient;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.client.StorageClient;
import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.facebook.FacebookPage;
import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;
import ml.socshared.frontend.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.domain.storage.response.GroupResponseStorage;
import ml.socshared.frontend.service.FacebookService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacebookServiceImpl implements FacebookService {

    private final FacebookClient facebookClient;

    @Override
    public AccessUrlResponse getUrlAccessAddress(String token) {
        log.info("getting facebook access url");
        return facebookClient.getAccessUrl("Bearer" + token);
    }

    @Override
    public SuccessResponse saveAccountFacebook(String authorizationCode, String token) {
        return facebookClient.saveAccountFacebook(authorizationCode, "Bearer " + token);
    }

    @Override
    public FacebookPage<FacebookGroupResponse> getGroupsFacebookAccount(Integer page, Integer size, String token) {
        return facebookClient.getGroups(page, size, "Bearer " + token);
    }

    @Override
    public GroupResponse addGroup(String fbGroupId, String token) {
        return facebookClient.addGroup(fbGroupId, "Bearer " + token);
    }

    @Override
    public void deleteGroup(String fbGroupId, String token) {
        facebookClient.deleteGroup(fbGroupId, "Bearer " + token);
    }

    @Override
    public void deleteFacebookAccount(String token) {
        facebookClient.deleteFacebookAccount("Bearer " + token);
    }
}
