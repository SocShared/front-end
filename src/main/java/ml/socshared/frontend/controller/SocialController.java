package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.SocialNetwork;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import ml.socshared.frontend.domain.response.SocialAccountResponse;
import ml.socshared.frontend.exception.impl.HttpUnauthorizedException;
import ml.socshared.frontend.security.response.OAuth2TokenResponse;
import ml.socshared.frontend.security.service.AuthService;
import ml.socshared.frontend.service.SocAccountService;
import ml.socshared.frontend.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Pageable;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SocialController {

    private final AuthService authService;
    private final SocAccountService accountService;

    @GetMapping("/social")
    public String socConnectedPage(Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        if (accessToken.isEmpty())
            return "redirect:/";

        List<SocialAccountResponse> responses = accountService.getAccounts(accessToken);
        model.addAttribute("facebook_connect", accountService.checkSocialAccount(responses, SocialNetwork.FACEBOOK));
        model.addAttribute("vk_connect", accountService.checkSocialAccount(responses, SocialNetwork.VK));
        model.addAttribute("accounts_list", responses);
        AppUrlAccess appAccess = new AppUrlAccess();
        model.addAttribute("appUrlAccess", appAccess);
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Социальные аккаунты"));
        return "soc_accounts";
    }

    @GetMapping("/")
    public String lendingPage(Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("isAuthorized", !accessToken.isEmpty());
        return "landing_page";
    }

    @GetMapping("/refresh")
    public String refresh(Model model, HttpServletResponse response,
                          @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken,
                          @CookieValue(value = "JWT_RT", defaultValue = "") String refreshToken) {
        if (!accessToken.isEmpty() && !refreshToken.isEmpty()) {
            try {
                OAuth2TokenResponse res = authService.getToken(refreshToken);
                Cookie accessTokenCookie = new Cookie("JWT_AT", res.getAccessToken());
                accessTokenCookie.setMaxAge(24 * 60 * 60);
                accessTokenCookie.setSecure(true);
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setPath("/");
                accessTokenCookie.setDomain("socshared.ml");
                response.addCookie(accessTokenCookie);

                Cookie refreshTokenCookie = new Cookie("JWT_RT", res.getRefreshToken());
                refreshTokenCookie.setMaxAge(24 * 60 * 60 * 30);
                refreshTokenCookie.setSecure(true);
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setPath("/");
                refreshTokenCookie.setDomain("socshared.ml");
                response.addCookie(refreshTokenCookie);

                model.addAttribute("isAuthorized", true);
                return "redirect:/social";
            } catch (Exception exc) {
                model.addAttribute("isAuthorized", false);
                return "redirect:/exit";
            }
        } else {
            model.addAttribute("isAuthorized", false);
            return "redirect:/exit";
        }
    }

    @GetMapping("/exit")
    public String exit(Model model, HttpServletResponse response) {
        Cookie accessToken = new Cookie("JWT_AT", null);
        accessToken.setMaxAge(0);
        accessToken.setSecure(true);
        accessToken.setHttpOnly(true);
        accessToken.setPath("/");
        accessToken.setDomain("socshared.ml");
        response.addCookie(accessToken);

        Cookie refreshToken = new Cookie("JWT_RT", null);
        refreshToken.setMaxAge(0);
        refreshToken.setSecure(true);
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/");
        refreshToken.setDomain("socshared.ml");
        response.addCookie(refreshToken);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        model.addAttribute("isAuthorized", false);
        return "redirect:/";
    }

}
