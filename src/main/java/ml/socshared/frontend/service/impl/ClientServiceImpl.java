package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.client.ClientClient;
import ml.socshared.frontend.domain.client.ClientResponse;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientClient clientClient;

    @Override
    public RestResponsePage<ClientResponse> findAllClients(Integer page, Integer size, String token) {
        return clientClient.findAllClients(page, size, "Bearer " + token);
    }

    @Override
    public ClientResponse findByClientId(UUID clientId, String token) {
        return clientClient.findByClientId(clientId, "Bearer " + token);
    }

    @Override
    public ClientResponse findByUserIdAndClientId(UUID clientId, String token) {
        return clientClient.findByUserIdAndClientId(clientId, "Bearer " + token);
    }

    @Override
    public RestResponsePage<ClientResponse> findByUserId(Integer page, Integer size, String token) {
        return clientClient.findByUserId(page, size, "Bearer " + token);
    }

    @Override
    public ClientResponse addClient(NewClientRequest newClientRequest, String token) {
        return clientClient.addClient(newClientRequest, "Bearer " + token);
    }

    @Override
    public void updateClient(UUID clientId, NewClientRequest newClientRequest, String token) {
        clientClient.updateClient(clientId, newClientRequest, "Bearer " + token);
    }
}
