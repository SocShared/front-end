package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.model.SocialAccount;
import ml.socshared.frontend.domain.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

//@FeignClient(name = "Storage service client", url = "${front-end.services.gateway.url}")
public interface GatewayServiceClient {

    @GetMapping("/api/v1/protected/soc-accounts")
    List<SocialAccount> getAccounts(@RequestHeader("Authorization") String token);


    @PostMapping("/api/v1/protected/soc-accounts/vk/token")
    SuccessResponse sendTokenForVk(Integer vkToken, @RequestHeader("Authorization") String token);

}
