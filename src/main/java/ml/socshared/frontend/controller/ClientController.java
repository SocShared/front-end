package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.client.ClientResponse;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/app")
    public String getClients(Pageable pageable, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Приложения OAuth2"));

        // TODO Сделать адекватную пагинацию
        RestResponsePage<ClientResponse> clients = clientService.findByUserId(pageable.getPageNumber(), pageable.getPageSize(), accessToken);

        model.addAttribute("clients", clients);

        return "app :: content";
    }

    @GetMapping("/app/clients")
    public String getClient(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("app", "Приложения OAuth2")),
                "Подключение приложения"));

        model.addAttribute("client", new NewClientRequest());
        return "clients :: content";
    }

    @GetMapping("/app/clients/{clientId}")
    public String getClient(@PathVariable UUID clientId,
                            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("app", "Приложения OAuth2")),
                "Подключение приложения"));

        ClientResponse clientResponse = clientService.findByUserIdAndClientId(clientId, accessToken);

        model.addAttribute("client", clientResponse);
        return "clients :: content";
    }

    @PostMapping("/app/clients")
    public String sendClient(@Valid @ModelAttribute("client") NewClientRequest newClientRequest,
                             BindingResult postBinding,
                             Model model,
                             @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("app", "Приложения OAuth2")),
                "Подключение приложения"));

        if (postBinding.hasErrors()) {
            model.addAttribute("client", newClientRequest);
            return "clients :: content";
        }

        ClientResponse clientResponse;
        if (newClientRequest.getClientId() == null)
            clientResponse = clientService.addClient(newClientRequest, accessToken);
        else {
            clientService.updateClient(newClientRequest.getClientId(), newClientRequest, accessToken);
            clientResponse = clientService.findByUserIdAndClientId(newClientRequest.getClientId(), accessToken);
        }

        model.addAttribute("client", clientResponse);

        return "clients :: content";

    }

    @GetMapping("/app/clients/{clientId}/remove")
    public String removeClient(Pageable pageable, @PathVariable UUID clientId,
                               @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Приложения OAuth2"));

        clientService.deleteClientById(clientId, accessToken);

        return getClients(pageable, accessToken, model);
    }
}
