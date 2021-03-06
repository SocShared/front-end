package ml.socshared.frontend.client;

import ml.socshared.frontend.config.feign.DefaultClientConfiguration;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.domain.user.UpdateUserRequest;
import ml.socshared.frontend.domain.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "account-client", url = "${feign.url.api:}", configuration = DefaultClientConfiguration.class)
public interface AccountClient {

    @GetMapping(value = "/api/v1/protected/users/info")
    UserResponse getUserResponseInfo(@RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/protected/users/mail/confirmed")
    SuccessResponse sendMailConfirmed(@RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/protected/users")
    void updateUser(UpdateUserRequest request, @RequestHeader("Authorization") String token);

}
