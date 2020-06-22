package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.client.ClientResponse;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.response.RestResponsePage;

import java.util.UUID;

public interface ClientService {

    RestResponsePage<ClientResponse> findAllClients(Integer page, Integer size, String token);
    ClientResponse findByClientId(UUID clientId, String token);
    ClientResponse findByUserIdAndClientId(UUID clientId, String token);
    RestResponsePage<ClientResponse> findByUserId(Integer page, Integer size, String token);
    ClientResponse addClient(NewClientRequest newClientRequest, String token);
    ClientResponse updateClient(UUID clientId, NewClientRequest newClientRequest, String token);

}
