package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.client.NewClientRequest;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.domain.user.UpdateUserRequest;
import ml.socshared.frontend.domain.user.UserResponse;
import ml.socshared.frontend.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        log.info("email_verified: " + userResponse.isEmailVerified());

        model.addAttribute("user", userResponse);
        model.addAttribute("access_confirmed", false);
        return "account";
    }

    @PostMapping("/account")
    public String updateAccount(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken,
                              @ModelAttribute("user") UpdateUserRequest userRequest) {
        accountService.updateUser(userRequest, accessToken);
        return "redirect:/account";
    }

    @GetMapping("/account/confirmed")
    public String sendMailConfirmed(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        SuccessResponse successResponse = accountService.sendMailConfirmed(accessToken);
        model.addAttribute("access_confirmed", successResponse.getSuccess());
        return "account";
    }

}
