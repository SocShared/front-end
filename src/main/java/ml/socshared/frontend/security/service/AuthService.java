package ml.socshared.frontend.security.service;

import ml.socshared.frontend.security.response.OAuth2TokenResponse;

public interface AuthService {

    OAuth2TokenResponse getToken(String refreshToken);

}
