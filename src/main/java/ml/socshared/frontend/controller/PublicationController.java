package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.storage.request.GroupRequest;
import ml.socshared.frontend.domain.storage.request.PostRequest;
import ml.socshared.frontend.domain.text.request.TextRequest;
import ml.socshared.frontend.domain.text.response.KeyWordResponse;
import ml.socshared.frontend.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService service;

    @ModelAttribute
    public PostRequest postRequest() {
        return new PostRequest();
    }

    @GetMapping("/publication")
    public String getPublication(Model model) {
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("support", "Назад")), "Публикации"));
        model.addAttribute("publication ", postRequest());
        return "publication";
    }

    @PostMapping("/publication/keywords")
    public String getKeywords(@RequestBody TextRequest request, Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        List<KeyWordResponse> keyWordResponseList = service.getKeyWords(request.getText(), accessToken);

        model.addAttribute("keywords", keyWordResponseList);
        return "publication";
    }

}
