package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;
import ml.socshared.frontend.domain.response.SuccessResponse;

public interface FacebookService {

    AccessUrlResponse getUrlAccessAddress(String token);
    SuccessResponse saveAccountFacebook(String authorizationCode, String token);
    void deleteFacebookAccount(String token);

}
