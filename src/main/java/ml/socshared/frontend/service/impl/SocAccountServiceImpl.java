package ml.socshared.frontend.service.impl;

import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.model.SocialAccount;
import ml.socshared.frontend.service.SocAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SocAccountServiceImpl implements SocAccountService {

    GatewayServiceClient client;

    @Autowired
    SocAccountServiceImpl(GatewayServiceClient gc) {
        client = gc;
    }

    @Override
    public List<SocialAccount> getAccounts(String token) {
        return client.getAccounts(token);
    }
}
