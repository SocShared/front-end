package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.form.PublicationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    @GetMapping("/settings")
    public String getPublication(Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Найстройки"));

        model.addAttribute("client", new NewClientRequest());
        return "settings";
    }

    @PostMapping("/settings")
    public String sendPublication(@Valid @ModelAttribute("publication") PublicationForm post,
                                  BindingResult postBinding,
                                  Model model,
                                  @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Найстройки"));

        model.addAttribute("client", new NewClientRequest());
        return "settings";
    }

}
