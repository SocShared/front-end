package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.SocialNetwork;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import ml.socshared.frontend.domain.response.SocialAccountResponse;
import ml.socshared.frontend.domain.user.RoleResponse;
import ml.socshared.frontend.domain.user.UserResponse;
import ml.socshared.frontend.exception.impl.HttpUnauthorizedException;
import ml.socshared.frontend.security.response.OAuth2TokenResponse;
import ml.socshared.frontend.security.service.AuthService;
import ml.socshared.frontend.service.AccountService;
import ml.socshared.frontend.service.FacebookService;
import ml.socshared.frontend.service.SocAccountService;
import ml.socshared.frontend.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes("roles")
public class SocialController {

    private final AuthService authService;
    private final AccountService accountService;
    private final SocAccountService socAccountService;
    private final FacebookService fbService;
    private final VkService vkService;

    @ModelAttribute("roles")
    public Set<RoleResponse> getRole() {
        return new HashSet<>();
    }

    @GetMapping("/lk")
    public String basePage(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken,
                           HttpServletRequest request, Model model) {
        if (accessToken.isEmpty())
            return "redirect:/";

        UserResponse userResponse = accountService.getUserResponseInfo(accessToken);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("roles", userResponse.getRoles());

        setModelRole(model, userResponse.getRoles());

        return "index";
    }


    @GetMapping("/social")
    public String socConnectedPage(Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        List<SocialAccountResponse> responses = socAccountService.getAccounts(accessToken);
        model.addAttribute("facebook_connect", socAccountService.checkSocialAccount(responses, SocialNetwork.FACEBOOK));
        model.addAttribute("vk_connect", socAccountService.checkSocialAccount(responses, SocialNetwork.VK));
        model.addAttribute("accounts_list", responses);
        AppUrlAccess appAccess = new AppUrlAccess();
        model.addAttribute("appUrlAccess", appAccess);
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Социальные аккаунты"));
        return "soc_accounts :: content";
    }

    @GetMapping("/social/facebook/turn_off")
    public String deleteFacebookAccount(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        fbService.deleteFacebookAccount(accessToken);
        List<SocialAccountResponse> responses = socAccountService.getAccounts(accessToken);
        model.addAttribute("facebook_connect", socAccountService.checkSocialAccount(responses, SocialNetwork.FACEBOOK));
        model.addAttribute("vk_connect", socAccountService.checkSocialAccount(responses, SocialNetwork.VK));
        model.addAttribute("accounts_list", responses);
        AppUrlAccess appAccess = new AppUrlAccess();
        model.addAttribute("appUrlAccess", appAccess);
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Социальные аккаунты"));
        return "soc_accounts :: content";
    }


    @GetMapping("social/disconnection/vk")
    public String disconnectionVkAccount(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        log.info("Request disconnection vk account");
        vkService.vkDisconnection("Bearer " +  accessToken);
        List<SocialAccountResponse> responses = socAccountService.getAccounts(accessToken);
        model.addAttribute("facebook_connect", socAccountService.checkSocialAccount(responses, SocialNetwork.FACEBOOK));
        model.addAttribute("vk_connect", socAccountService.checkSocialAccount(responses, SocialNetwork.VK));
        model.addAttribute("accounts_list", responses);
        AppUrlAccess appAccess = new AppUrlAccess();
        model.addAttribute("appUrlAccess", appAccess);
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Социальные аккаунты"));
        return "soc_accounts :: content";
    }

    @GetMapping("/")
    public String lendingPage(Model model, @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("isAuthorized", !accessToken.isEmpty());
        return "landing_page";
    }

    @GetMapping("/exit")
    public String exit(Model model, HttpServletResponse response) {
        Cookie accessToken = new Cookie("JWT_AT", "");
        accessToken.setMaxAge(0);
        accessToken.setSecure(true);
        accessToken.setHttpOnly(true);
        accessToken.setPath("/");
        accessToken.setDomain("socshared.ml");
        response.addCookie(accessToken);

        Cookie refreshToken = new Cookie("JWT_RT", "");
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

    private void setModelRole(Model model, Set<RoleResponse> roleResponses) {
        for (RoleResponse role : roleResponses) {
            if (role.getName().equals("CONTENT_MANAGER"))
                model.addAttribute("content_manager", role.getName());
            else if (role.getName().equals("ADMIN"))
                model.addAttribute("admin", role.getName());
        }
        if (model.getAttribute("admin") == null)
            model.addAttribute("admin", "");
        if (model.getAttribute("content_manager") == null)
            model.addAttribute("content_manager", "");
    }

}
