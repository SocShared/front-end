package ml.socshared.frontend.security.client;

import ml.socshared.frontend.security.request.OAuthFlowRequest;
import ml.socshared.frontend.security.response.OAuth2TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-client", url = "${feign.url.auth:}")
public interface AuthClient {

    @PostMapping(value = "/oauth/token")
    OAuth2TokenResponse getToken(OAuthFlowRequest oAuthFlowRequest);

}
