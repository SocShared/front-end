package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.text.response.KeyWordResponse;
import ml.socshared.frontend.service.PublicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final GatewayServiceClient client;

    @Override
    public List<KeyWordResponse> getKeyWords(String text, String token) {
        return client.getKeyWords(text, null, null, "Bearer" + token);
    }
}
