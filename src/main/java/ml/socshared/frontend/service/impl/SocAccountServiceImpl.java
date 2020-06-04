package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.client.GatewayServiceClient;
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

}
