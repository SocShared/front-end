package ml.socshared.frontend.client.mock;

import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.model.AccountType;
import ml.socshared.frontend.domain.model.SocialAccount;
import ml.socshared.frontend.domain.response.SuccessResponse;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class GatewayServiceClientMock implements GatewayServiceClient {
    @Override
    public List<SocialAccount> getAccounts(String token) {
        List<SocialAccount> accs= new LinkedList<>();
        accs.add(new SocialAccount(AccountType.FACEBOOK, "465464", "Test User Facebook"));
        accs.add(new SocialAccount(AccountType.VKONTAKTE, "98946484", "Test User Vkontakte"));
        return accs;
    }

    @Override
    public SuccessResponse sendTokenForVk(Integer vkToken, String token) {
        return new SuccessResponse(true);
    }
}
