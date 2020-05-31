package ml.socshared.frontend.controller;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.SocialAccount;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import ml.socshared.frontend.service.SocAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class SocPage {

    SocAccountService accountService;

    @Autowired
    SocPage(SocAccountService account) {
        accountService = account;
    }



    @GetMapping("/social")
    public String socConnectedPage(Model model, @CookieValue(name = "token", required = false) String token) {
        List<SocialAccount> accounts = accountService.getAccounts(token);
        model.addAttribute("accounts_list", accounts);
        AppUrlAccess appAccess = new AppUrlAccess();
        model.addAttribute("appUrlAccess", appAccess);
        return "soc_accounts";
    }

}
