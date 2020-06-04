package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.service.FacebookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class FacebookController {

    private final FacebookService service;

    @GetMapping("/facebook/access")
    public String getAccessUrlFacebook(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        return "redirect:" + service.getUrlAccessAddress(accessToken).getUrlForAccess();
    }

}
