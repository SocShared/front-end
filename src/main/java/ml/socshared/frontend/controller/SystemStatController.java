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
    public String getSocStat(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {

        UsingSocialNetworkResponse usingSocialNetworkResponse = statService.getUsingSocialNetworkStat(accessToken);
        TotalStatsResponse totalStatsResponse = statService.getTotalCount(accessToken);

        model.addAttribute("using_social_network", usingSocialNetworkResponse);
        model.addAttribute("total_count", totalStatsResponse);

        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Системная статистика"));

        PieChartData<Long, String> usingFacebookVkChartData = new PieChartData<>(
                Arrays.asList(usingSocialNetworkResponse.getVk().getAllEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getAllEventsCount()),
                Arrays.asList("Вконтакте", "Facebook"), Arrays.asList("#4d7198", "#3b5998")
        );
        model.addAttribute("using_facebook_vk_chart_data", usingFacebookVkChartData);

        PieChartData<Long, String> usingFacebookChartData = new PieChartData<>(
                Arrays.asList(usingSocialNetworkResponse.getFacebook().getAddPostEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getPostByIdEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getPostsEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getRemoveAccountEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getSaveAccountEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getUserGroupEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getUserGroupsEventsCount(),
                        usingSocialNetworkResponse.getFacebook().getUserInfoEventsCount()),
                Arrays.asList("Публикация поста", "Получение поста по ID", "Получение списка постов", "Отключение аккаунта",
                        "Подключение аккаунта", "Получение группы", "Получение групп", "Получение информации об аккаунте"),
                Arrays.asList("#B22222", "#FFFF00", "#FFFFE0", "#EE82EE", "#808080", "#000080", "#008000", "#2F4F4F")
        );
        model.addAttribute("using_facebook_chart_data", usingFacebookChartData);

        PieChartData<Long, String> usingVkChartData = new PieChartData<>(
                Arrays.asList(usingSocialNetworkResponse.getVk().getAddPostEventsCount(),
                        usingSocialNetworkResponse.getVk().getPostByIdEventsCount(),
                        usingSocialNetworkResponse.getVk().getPostsEventsCount(),
                        usingSocialNetworkResponse.getVk().getUserGroupEventsCount(),
                        usingSocialNetworkResponse.getVk().getUserGroupsEventsCount(),
                        usingSocialNetworkResponse.getVk().getUserInfoEventsCount()),
                Arrays.asList("Публикация поста", "Получение поста по ID", "Получение списка постов", "Получение группы",
                        "Получение групп", "Получение информации об аккаунте"),
                Arrays.asList("#B22222", "#FFFF00", "#FFFFE0", "#EE82EE", "#808080", "#000080")
        );
        model.addAttribute("using_vk_chart_data", usingVkChartData);

        return "sys_soc_stat";
    }


}
