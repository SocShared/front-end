package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.form.PublicationForm;
import ml.socshared.frontend.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService service;

    @GetMapping("/publication")
    public String getPublication(Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("support", "Назад")), "Публикации"));
        service.writePublicationPage(model, accessToken);
        return "publication";
    }

    @PostMapping("/publication")
    public String sendPublication(@ModelAttribute PublicationForm post,
                                  Model model,
                                  @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken ) {
        service.sendPublication(post, model, accessToken);
        return "publication";
        }
}
