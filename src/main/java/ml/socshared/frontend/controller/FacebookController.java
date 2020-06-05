package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.facebook.FacebookPage;
import ml.socshared.frontend.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.service.FacebookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FacebookController {

    private final FacebookService service;

    @GetMapping("/social/facebook/access")
    public String getAccessUrlFacebook(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        String url = service.getUrlAccessAddress(accessToken).getUrlForAccess();
        return "redirect:" + url;
    }

    @GetMapping("/facebook/callback")
    public String saveFacebookAccount(@RequestParam("code") String authorizationCode, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        SuccessResponse successResponse = service.saveAccountFacebook(authorizationCode, accessToken);
        return "redirect:/social";
    }

    @GetMapping("/social/facebook/turn_off")
    public String deleteFacebookAccount(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        service.deleteFacebookAccount(accessToken);
        return "redirect:/social";
    }

    @GetMapping("/social/facebook/groups")
    public String groupsFacebook(Model model,
                                 @RequestParam(name = "page", required = false) Integer page,
                                 @RequestParam(name = "size", required = false) Integer size,
                                 @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        FacebookPage<FacebookGroupResponse> groupsPage = service.getGroupsFacebookAccount(page, size, accessToken);

        model.addAttribute("groups_page", groupsPage);
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("social", "Социальные Аккаунты")),
                "Подключенные группы"));

        return "soc_fb_groups";
    }

    @GetMapping("/social/facebook/groups/connection/{groupId}")
    public String connectionGroup(@PathVariable String groupId, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        // TODO: подключение группы из Facebook
        return "redirect:/social/facebook/groups";
    }

    @GetMapping("/social/facebook/groups/disconnection/{groupId}")
    public String disconnectionGroup(@PathVariable String groupId, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        // TODO: отключение группы из Facebook
        return "redirect:/social/facebook/groups";
    }

}
