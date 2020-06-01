package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.model.SocialAccount;
import ml.socshared.frontend.domain.model.tech_support.FullQuestion;
import ml.socshared.frontend.domain.model.tech_support.ShortQuestion;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "GateWayClient", url = "http://localhost:8083/")
public interface GatewayServiceClient {

    @GetMapping("/api/v1/protected/soc-accounts")
    List<SocialAccount> getAccounts(@RequestHeader("Authorization") String token);


    @PostMapping("/api/v1/protected/social/{vkToken}")
    SuccessResponse sendTokenForVk(@PathVariable String vkToken, @RequestHeader("Authorization") String token);

    @GetMapping("api/v1/protected/support?page={page}&size={size}")
    RestResponsePage<ShortQuestion> getQuestionsPage(@PathVariable Integer page, @PathVariable Integer size,
                                                     @RequestHeader("Authorization") String token);

    @GetMapping("api/v1/protected/support/questions/{questionId}?page={page}&size={size}")
    FullQuestion getFullQuestionPage(@PathVariable Integer questionId,
                                                       @PathVariable Integer page, @PathVariable Integer size,
                                                       @RequestHeader("Authorization") String token);

}
