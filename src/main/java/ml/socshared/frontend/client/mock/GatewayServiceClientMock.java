package ml.socshared.frontend.client.mock;

import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.response.SocialAccountResponse;
import ml.socshared.frontend.domain.tech_support.Comment;
import ml.socshared.frontend.domain.tech_support.FullQuestion;
import ml.socshared.frontend.domain.tech_support.ShortQuestion;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.domain.tech_support.request.QuestionCreateRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GatewayServiceClientMock implements GatewayServiceClient {
    @Override
    public List<SocialAccountResponse> getAccounts(String token) {
        return null;
    }

    @Override
    public SuccessResponse sendTokenForVk(String vkToken, String token) {
        return null;
    }

    @Override
    public RestResponsePage<ShortQuestion> getQuestionsPage(Integer page, Integer size, String token) {
        return null;
    }

    @Override
    public FullQuestion getFullQuestionPage(Integer questionId, Integer page, Integer size, String token) {
        return null;
    }

    @Override
    public Integer addCommentToQuestion(Integer questionId, Comment comment, String token) {
        return null;
    }

    @Override
    public Integer addQuestion(QuestionCreateRequest question, String token) {
        return null;
    }

    @Override
    public AccessUrlResponse getAccessUrl(String token) {
        return null;
    }

    @Override
    public SuccessResponse saveAccountFacebook(String authorizationCode, String token) {
        return null;
    }


}
