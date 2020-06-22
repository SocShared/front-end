package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.client.ClientResponse;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/app")
    public String getClients(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Клиенты"));

        // TODO Сделать адекватную пагинацию
        RestResponsePage<ClientResponse> clients = clientService.findByUserId(0, 100, accessToken);

        model.addAttribute("clients", clients);

        return "app";
    }

    @GetMapping("/app/clients")
    public String getClient(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Клиенты"));

        model.addAttribute("client", new NewClientRequest());
        return "clients";
    }

    @GetMapping("/app/clients/{clientId}")
    public String getClient(@PathVariable UUID clientId,
                            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Клиенты"));

        ClientResponse clientResponse = clientService.findByUserIdAndClientId(clientId, accessToken);

        model.addAttribute("client", clientResponse);
        return "clients";
    }

    @PostMapping("/app/clients")
    public String sendClient(@Valid @ModelAttribute("client") NewClientRequest newClientRequest,
                                  BindingResult postBinding,
                                  Model model,
                                  @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Клиенты"));
        if (postBinding.hasErrors()) {
            model.addAttribute("client", newClientRequest);
            return "clients";
        }

        clientService.addClient(newClientRequest, accessToken);

        return "redirect:/app";
    }

    @PostMapping("/app/clients/{clientId}")
    public String sendClient(@PathVariable UUID clientId,
                             @Valid @ModelAttribute("client") NewClientRequest newClientRequest,
                             BindingResult postBinding,
                             Model model,
                             @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Клиенты"));
        if (postBinding.hasErrors()) {
            model.addAttribute("client", newClientRequest);
            return "clients";
        }

        clientService.updateClient(clientId, newClientRequest, accessToken);

        return "redirect:/app";
    }

}
