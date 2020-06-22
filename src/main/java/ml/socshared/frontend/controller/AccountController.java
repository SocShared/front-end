package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.domain.user.UserResponse;
import ml.socshared.frontend.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Arrays;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account")
    public String getUserAccountInfo(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Аккаунт"));

        UserResponse userResponse = accountService.getUserResponseInfo(accessToken);

        model.addAttribute("user", userResponse);
        model.addAttribute("access_confirmed", false);
        return "account";
    }

    @GetMapping("/account/confirmed")
    public String sendMailConfirmed(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        SuccessResponse successResponse = accountService.sendMailConfirmed(accessToken);
        model.addAttribute("access_confirmed", true);
        return "redirect:/account";
    }

}
