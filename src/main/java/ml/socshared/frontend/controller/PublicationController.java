package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PublicationController {

    @GetMapping("/publication")
    public String getPublication(Model model) {
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("support", "Назад")), "Публикации"));

        return "publication";
    }
}
