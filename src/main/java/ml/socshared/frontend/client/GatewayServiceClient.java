package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;
import ml.socshared.frontend.domain.model.SocialAccount;
import ml.socshared.frontend.domain.tech_support.Comment;
import ml.socshared.frontend.domain.tech_support.FullQuestion;
import ml.socshared.frontend.domain.tech_support.ShortQuestion;
import ml.socshared.frontend.domain.tech_support.request.QuestionCreateRequest;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GateWayClient", url = "${feign.url.api:}")
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

    @PostMapping("api/v1/protected/support/questions/{questionId}/comments")
    Integer addCommentToQuestion(@PathVariable Integer questionId,
                                 @RequestBody Comment comment,
                                 @RequestHeader("Authorization") String token);

    @PostMapping("api/v1/protected/support/questions/")
    Integer addQuestion(@RequestBody QuestionCreateRequest question,
                        @RequestHeader("Authorization") String token);

    @GetMapping("api/v1/protected/facebook/access")
    AccessUrlResponse getAccessUrl(@RequestHeader("Authorization") String token);

}
