package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.client.ClientResponse;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.service.ClientService;
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
    public String getClients(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Приложения OAuth2"));

        // TODO Сделать адекватную пагинацию
        RestResponsePage<ClientResponse> clients = clientService.findByUserId(0, 100, accessToken);

        model.addAttribute("clients", clients);

        return "app :: content";
    }

    @GetMapping("/app/clients")
    public String getClient(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("app :: content", "Приложения OAuth2")),
                "Подключение приложения"));

        model.addAttribute("client", new NewClientRequest());
        return "clients :: content";
    }

    @GetMapping("/app/clients/{clientId}")
    public String getClient(@PathVariable UUID clientId,
                            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("app :: content", "Приложения OAuth2")),
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
        if (postBinding.hasErrors()) {
            model.addAttribute("client", newClientRequest);
            return "clients :: content";
        }

        if (newClientRequest.getClientId() == null)
            clientService.addClient(newClientRequest, accessToken);
        else
            clientService.updateClient(newClientRequest.getClientId(), newClientRequest, accessToken);

        return "redirect:/app";
    }

}
