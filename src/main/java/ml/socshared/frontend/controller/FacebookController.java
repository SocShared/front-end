package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.adapter.response.GroupResponse;
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
import java.util.Collections;

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
        return "redirect:/lk";
    }

    @GetMapping("/social/facebook/turn_off")
    public String deleteFacebookAccount(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Социальные аккаунты"));
        service.deleteFacebookAccount(accessToken);
        return "soc_accounts :: content";
    }

    @GetMapping("/social/facebook/groups")
    public String groupsFacebook(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                 @RequestParam(name = "size", defaultValue = "100") Integer size,
                                 @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        FacebookPage<FacebookGroupResponse> groupsPage = service.getGroupsFacebookAccount(page, size, accessToken);

        model.addAttribute("groups_page", groupsPage);
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("social", "Социальные Аккаунты")),
                "Подключенные группы"));

        return "soc_fb_groups :: content";
    }

    @GetMapping("/social/facebook/groups/connection/{groupId}")
    public String connectionGroup(@PathVariable String groupId, Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        GroupResponse groupResponse = service.addGroup(groupId, accessToken);

        return groupsFacebook(model, 0, 100, accessToken);
    }

    @GetMapping("/social/facebook/groups/disconnection/{groupId}")
    public String disconnectionGroup(@PathVariable String groupId,Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        service.deleteGroup(groupId, accessToken);
        return groupsFacebook(model, 0, 100, accessToken);
    }

}
