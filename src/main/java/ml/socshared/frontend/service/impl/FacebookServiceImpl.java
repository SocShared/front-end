package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;
import ml.socshared.frontend.service.FacebookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacebookServiceImpl implements FacebookService {

    private final GatewayServiceClient client;

    @Override
    public AccessUrlResponse getUrlAccessAddress(String token) {
        log.info("getting facebook access url");
        return client.getAccessUrl(token);
    }
}
