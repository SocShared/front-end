package ml.socshared.frontend.client;

import ml.socshared.frontend.config.feign.DefaultClientConfiguration;
import ml.socshared.frontend.domain.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-client", url = "${feign.url.api:}", configuration = DefaultClientConfiguration.class)
public interface AccountClient {

    @GetMapping(value = "/api/v1/protected/users/info")
    UserResponse getUserResponseInfo(@RequestHeader("Authorization") String token);

}
