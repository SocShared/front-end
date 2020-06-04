package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.service.FacebookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String groupsFacebook(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        // TODO: вывод групп из Facebook, просеянных через StorageService
        return "redirect:/social";
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
