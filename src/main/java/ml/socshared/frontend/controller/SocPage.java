package ml.socshared.frontend.controller;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.AccountType;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.SocialAccount;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import ml.socshared.frontend.service.SocAccountService;
import ml.socshared.frontend.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;
import java.util.*;

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
       // List<SocialAccount> accounts = accountService.getAccounts(token);
        List<SocialAccount> accs = new LinkedList<>();
        accs.add(new SocialAccount(AccountType.FACEBOOK, "465464", "Test User Facebook"));
        accs.add(new SocialAccount(AccountType.VKONTAKTE, "98946484", "Test User Vkontakte"));
        model.addAttribute("accounts_list", accs);
        AppUrlAccess appAccess = new AppUrlAccess();
        model.addAttribute("appUrlAccess", appAccess);
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(new BreadcrumbElement("support", "назад")), "Социальные аккаунты"));
        return "soc_accounts";
    }

    @GetMapping("/")
    String lendingPage(@CookieValue(name = "token", required = false) String token) {
        return "landing_page";
    }



}
