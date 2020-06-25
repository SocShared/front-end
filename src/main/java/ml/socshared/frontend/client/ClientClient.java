package ml.socshared.frontend.client;

import ml.socshared.frontend.config.feign.DefaultClientConfiguration;
import ml.socshared.frontend.domain.client.ClientResponse;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.response.RestResponsePage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "client-client", url = "${feign.url.api:}", configuration = DefaultClientConfiguration.class)
public interface ClientClient {

    @GetMapping(value = "/api/v1/protected/clients")
    RestResponsePage<ClientResponse> findAllClients(@RequestParam(name = "page") Integer page,
                                                    @RequestParam(name = "size") Integer size,
                                                    @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/clients/{clientId}")
    ClientResponse findByClientId(@PathVariable UUID clientId, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/users/clients/{clientId}")
    ClientResponse findByUserIdAndClientId(@PathVariable UUID clientId, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/users/clients")
    RestResponsePage<ClientResponse> findByUserId(@RequestParam(name = "page") Integer page,
                                                  @RequestParam(name = "size") Integer size,
                                                  @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/protected/users/clients")
    ClientResponse addClient(@RequestBody NewClientRequest newClientRequest,
                             @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/protected/users/clients/{clientId}")
    void updateClient(@PathVariable UUID clientId, @RequestBody NewClientRequest newClientRequest,
                      @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/protected/users/clients/{clientId}")
    void deleteClientById(@PathVariable UUID clientId, @RequestHeader("Authorization") String token);
}
