package ml.socshared.frontend.security.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.security.client.AuthClient;
import ml.socshared.frontend.security.request.OAuthFlowRequest;
import ml.socshared.frontend.security.request.TypeFlow;
import ml.socshared.frontend.security.response.OAuth2TokenResponse;
import ml.socshared.frontend.security.service.AuthService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient client;

    @Override
    public OAuth2TokenResponse getToken(String refreshToken) {

        OAuthFlowRequest req = new OAuthFlowRequest();
        req.setClientId("360dad92-ecb1-44e7-990a-3152d2642919");
        req.setClientSecret("cb456410-85ca-43b5-9a12-87171ad84516");
        req.setGrantType(TypeFlow.REFRESH_TOKEN);
        req.setRefreshToken(refreshToken);

        return client.getToken(req);
    }
}
