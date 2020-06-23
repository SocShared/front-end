package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.form.PublicationForm;
import ml.socshared.frontend.domain.text.request.TextRequest;
import ml.socshared.frontend.domain.text.response.KeyWordResponse;
import ml.socshared.frontend.service.PublicationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService service;

    @GetMapping("/publication")
    public String getPublication(Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Публикации"));

        service.writePublicationPage(model, accessToken);
        return "publication";
    }

    @PostMapping("/publication")
    public String sendPublication(@Valid @ModelAttribute("publication") PublicationForm post,
                                  @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken,
                                  BindingResult postBinding,
                                  Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Публикации"));
        if (post.getIsDeferred() && post.getDateTime().isBlank()) {
            postBinding.addError(new FieldError("publication", "dateTime", "У отложенной публикации должна быть дата и время"));
        }

        if (postBinding.hasErrors()) {
            model.addAttribute("publication", post);
            return "publication";
        }
        service.sendPublication(post, model, accessToken);
        return "publication";
    }

    @GetMapping("/social/vk/groups/{systemGroupId}/publications")
    public String getPublicationsByGroupId(@PathVariable UUID systemGroupId, Pageable pageable, Model model,
                                           @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        service.getPublicationsByGroupId(systemGroupId, pageable, model, accessToken);
        return "group_publications";
    }

    @PostMapping("/publication/keywords")
    public String getKeywords(@RequestBody TextRequest request, Model model,
                              @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        List<KeyWordResponse> keyWordResponseList = service.getKeyWords(request.getText(), accessToken);

        model.addAttribute("keywords", keyWordResponseList);
        return "publication";
    }


}
