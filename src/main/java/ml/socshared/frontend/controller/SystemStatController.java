package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.PieChartData;
import ml.socshared.frontend.domain.stat.TotalStatsResponse;
import ml.socshared.frontend.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.frontend.service.StatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SystemStatController {

    private final StatService statService;

    @GetMapping("/sys_stat")
    public String getTotalStat(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Системная статистика"));

        TotalStatsResponse totalStatsResponse = statService.getTotalCount(accessToken);

        model.addAttribute("total_count", totalStatsResponse);

        return "sys_stat";
    }

    @GetMapping("/sys_stat/soc_network")
    public String getSocStat(@ModelAttribute("total_count") TotalStatsResponse totalCount,
                             @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {

        UsingSocialNetworkResponse usingSocialNetworkResponse = statService.getUsingSocialNetworkStat(accessToken);

        model.addAttribute("using_social_network", usingSocialNetworkResponse);
        model.addAttribute("total_count", totalCount);

        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Системная статистика"));

        PieChartData<Long, String> pieChartData = new PieChartData<>(
                Arrays.asList(usingSocialNetworkResponse.getVk().getAllEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getAllEventsCount()),
                Arrays.asList("Вконтакте", "Facebook"), Arrays.asList("#4d7198", "#3b5998")
        );
        model.addAttribute("soc_pie_chart", pieChartData);

        return "sys_soc_stat";
    }


}
