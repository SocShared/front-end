package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.facebook.FacebookPage;
import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;
import ml.socshared.frontend.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.frontend.domain.response.SuccessResponse;

public interface FacebookService {

    AccessUrlResponse getUrlAccessAddress(String token);
    SuccessResponse saveAccountFacebook(String authorizationCode, String token);
    FacebookPage<FacebookGroupResponse> getGroupsFacebookAccount(Integer page, Integer size, String token);
    GroupResponse addGroup(String fbGroupId, String token);
    void deleteFacebookAccount(String token);

}
