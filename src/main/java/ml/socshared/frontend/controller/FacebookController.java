package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.service.FacebookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FacebookController {

    private final FacebookService service;

    @GetMapping("/facebook/access")
    public String getAccessUrlFacebook(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        String url = service.getUrlAccessAddress(accessToken).getUrlForAccess();
        log.info(url);
        return "redirect:" + url;
    }

}
