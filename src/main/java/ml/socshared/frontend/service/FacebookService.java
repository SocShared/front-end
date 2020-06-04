package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;

public interface FacebookService {

    AccessUrlResponse getUrlAccessAddress(String token);

}
