package ml.socshared.frontend.client;

import ml.socshared.frontend.config.feign.DefaultClientConfiguration;
import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;
import ml.socshared.frontend.domain.response.SocialAccountResponse;
import ml.socshared.frontend.domain.tech_support.Comment;
import ml.socshared.frontend.domain.tech_support.FullQuestion;
import ml.socshared.frontend.domain.tech_support.QuestionsPage;
import ml.socshared.frontend.domain.tech_support.ShortQuestion;
import ml.socshared.frontend.domain.tech_support.request.QuestionCreateRequest;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.domain.text.response.KeyWordResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@FeignClient(name = "GateWayClient", url = "${feign.url.api:}", configuration = DefaultClientConfiguration.class)
public interface GatewayServiceClient {

    @GetMapping("/api/v1/protected/social/accounts")
    List<SocialAccountResponse> getAccounts(@RequestHeader("Authorization") String token);

    @PostMapping("/api/v1/protected/social/vk/app")
    SuccessResponse sendTokenForVk(@RequestBody String vkToken, @RequestHeader("Authorization") String token);

    @DeleteMapping("/api/v1/protected/social/vk/app")
    SuccessResponse removeVkToken(@RequestHeader("Authorization") String token);

    @GetMapping("api/v1/protected/support?page={page}&size={size}")
    QuestionsPage getQuestionsPage(@PathVariable Integer page, @PathVariable Integer size,
                                   @RequestHeader("Authorization") String token);

    @GetMapping("api/v1/protected/support/questions/{questionId}?page={page}&size={size}")
    FullQuestion getFullQuestionPage(@PathVariable Integer questionId,
                                                       @PathVariable Integer page, @PathVariable Integer size,
                                                       @RequestHeader("Authorization") String token);

    @PostMapping("api/v1/protected/support/questions/{questionId}/comments")
    Integer addCommentToQuestion(@PathVariable Integer questionId,
                                 @RequestBody Comment comment,
                                 @RequestHeader("Authorization") String token);

    @PostMapping("api/v1/protected/support/questions")
    Integer addQuestion(@RequestBody QuestionCreateRequest question,
                        @RequestHeader("Authorization") String token);

    @DeleteMapping("/api/v1/protected/support/questions/{questionId}")
    void deleteQuestionById(@PathVariable Integer questionId,
                            @RequestHeader("Authorization") String token);

    @GetMapping(value = "/protected/text/keywords", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    List<KeyWordResponse> getKeyWords(@NotNull @RequestBody String text,
                                      @RequestParam(value = "min_len", defaultValue = "2") Integer minLength,
                                      @RequestParam(value = "max_len", defaultValue  = "4") Integer maxLength,
                                      @RequestHeader("Authorization") String token);

}
