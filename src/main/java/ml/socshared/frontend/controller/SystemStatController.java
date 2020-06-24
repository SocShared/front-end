package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.response.SocialAccountResponse;
import ml.socshared.frontend.domain.stat.SocCountResponse;
import ml.socshared.frontend.domain.stat.userstat.UsersStatResponse;
import ml.socshared.frontend.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.frontend.domain.user.UserResponse;
import ml.socshared.frontend.service.StatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SystemStatController {

    private final StatService statService;

    @GetMapping("/sys_stat")
    public String getUserAccountInfo(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Системная статистика"));

        UsingSocialNetworkResponse usingSocialNetworkResponse = statService.getUsingSocialNetworkStat(accessToken);
        SocCountResponse socCountResponse = statService.getSocCount(accessToken);

        long allEventUsingSocial = usingSocialNetworkResponse.getFacebook().getAllEventsCount() +
                usingSocialNetworkResponse.getVk().getAllEventsCount();

        long usingFacebookPerc = (long) (1.0 * usingSocialNetworkResponse.getFacebook().getAllEventsCount() / allEventUsingSocial * 100);
        long usingVkPerc = (long) (1.0 * usingSocialNetworkResponse.getVk().getAllEventsCount() / allEventUsingSocial * 100);

        model.addAttribute("using_social_network", usingSocialNetworkResponse);
        model.addAttribute("using_facebook_perc", usingFacebookPerc);
        model.addAttribute("using_vk_perc", usingVkPerc);
        model.addAttribute("soc_count", socCountResponse);
        model.addAttribute("active_users_count", statService.getActiveUsersCount(accessToken));
        model.addAttribute("online_users_count", statService.getOnlineUsersCount(accessToken));
        model.addAttribute("new_users_count", statService.getNewUsersCount(accessToken));
        model.addAttribute("all_users_count", statService.getAllUsersCount(accessToken));
        model.addAttribute("errors_count", statService.getErrorsStat(accessToken).getAllErrorsCount());

        return "sys_stat";
    }

}
