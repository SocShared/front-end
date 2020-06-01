    //package ml.socshared.frontend.client.mock;
    //
    //import ml.socshared.frontend.client.GatewayServiceClient;
    //import ml.socshared.frontend.domain.model.AccountType;
    //import ml.socshared.frontend.domain.model.SocialAccount;
    //import ml.socshared.frontend.domain.model.tech_support.ShortQuestion;
    //import ml.socshared.frontend.domain.response.SuccessResponse;
    //import org.springframework.data.domain.Page;
    //import org.springframework.stereotype.Component;
    //
    //import java.util.LinkedList;
    //import java.util.List;
    //
    //public class GatewayServiceClientMock implements GatewayServiceClient {
    //    @Override
    //    public List<SocialAccount> getAccounts(String token) {
    //        List<SocialAccount> accs = new LinkedList<>();
    //        accs.add(new SocialAccount(AccountType.FACEBOOK, "465464", "Test User Facebook"));
    //        accs.add(new SocialAccount(AccountType.VKONTAKTE, "98946484", "Test User Vkontakte"));
    //        return accs;
    //    }
    //
    //    @Override
    //    public SuccessResponse sendTokenForVk(String vkToken, String token) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Page<ShortQuestion> getQuestionsPage(Integer page, Integer size, String token) {
    //        return null;
    //    }
    //}
