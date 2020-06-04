package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.model.SocialNetwork;
import ml.socshared.frontend.domain.response.SocialAccountResponse;
import ml.socshared.frontend.service.SocAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocAccountServiceImpl implements SocAccountService {

    private final GatewayServiceClient client;

    @Override
    public List<SocialAccountResponse> getAccounts(String token) {
        return client.getAccounts("Bearer " + token);
    }

    @Override
    public SocialAccountResponse getFacebookSocialAccount(String token) {
        return client.getFacebookSocialAccount("Bearer " + token);
    }

    @Override
    public boolean checkSocialAccount(List<SocialAccountResponse> accounts, SocialNetwork network) {
        for (SocialAccountResponse account : accounts) {
            if (account.getSocialNetwork() == network)
                return true;
        }
        return false;
    }
}
