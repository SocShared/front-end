package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.PieChartData;
import ml.socshared.frontend.domain.stat.TotalStatsResponse;
import ml.socshared.frontend.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.frontend.domain.stat.userstat.UsersStatResponse;
import ml.socshared.frontend.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.frontend.service.StatService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SystemStatController {

    private final StatService statService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

    @GetMapping("/sys_stat")
    public String getTotalStat(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Системная статистика"));

        TotalStatsResponse totalStatsResponse = statService.getTotalCount(accessToken);

        model.addAttribute("total_count", totalStatsResponse);

        return "sys_stat :: content";
    }

    @GetMapping("/sys_stat/soc_network")
    public String getSocStat(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {

        UsingSocialNetworkResponse usingSocialNetworkResponse = statService.getUsingSocialNetworkStat(accessToken);
        TotalStatsResponse totalStatsResponse = statService.getTotalCount(accessToken);

        model.addAttribute("using_social_network", usingSocialNetworkResponse);
        model.addAttribute("total_count", totalStatsResponse);

        model.addAttribute("bread", new Breadcrumbs(Collections.singletonList(
                new BreadcrumbElement("sys_stat", "Системная статистика")),
                "Статистика по социальным сетям"));

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

        return "sys_soc_stat :: content";
    }

    @GetMapping("/sys_stat/users")
    public String getUsersStat(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.singletonList(
                new BreadcrumbElement("sys_stat", "Системная статистика")),
                "Статистика по пользователям"));

        TotalStatsResponse totalStatsResponse = statService.getTotalCount(accessToken);
        model.addAttribute("total_count", totalStatsResponse);

        List<UsersStatResponse> onlineUsers = statService.getOnlineUsersStatTimeline(accessToken);

        List<Long> valuesOnline = new ArrayList<>();
        List<String> datesOnline = new ArrayList<>();
        for (int i = onlineUsers.size() - 1; i >= 0; i--) {
            valuesOnline.add(onlineUsers.get(i).getOnlineUsers());
            datesOnline.add(onlineUsers.get(i).getDateTime().format(formatter));
        }

        Pair<List<Long>, List<String>> onlineNumberChart = Pair.of(valuesOnline, datesOnline);
        model.addAttribute("online_number_chart", onlineNumberChart);

        List<UsersStatResponse> activeUsers = statService.getActiveUsersStatTimeline(accessToken);

        List<Long> valuesActive = new ArrayList<>();
        List<String> datesActive = new ArrayList<>();
        for (int i = activeUsers.size() - 1; i >= 0; i--) {
            valuesActive.add(activeUsers.get(i).getActiveUsers());
            datesActive.add(activeUsers.get(i).getDateTime().format(formatter));
        }

        Pair<List<Long>, List<String>> activeNumberChart = Pair.of(valuesActive, datesActive);
        model.addAttribute("active_number_chart", activeNumberChart);

        List<UsersStatResponse> newUsers = statService.getNewUsersStatTimeline(accessToken);

        List<Long> valuesNew = new ArrayList<>();
        List<String> datesNew = new ArrayList<>();
        for (int i = newUsers.size() - 1; i >= 0; i--) {
            valuesNew.add(newUsers.get(i).getNewUsers());
            datesNew.add(newUsers.get(i).getDateTime().format(formatter));
        }

        Pair<List<Long>, List<String>> newNumberChart = Pair.of(valuesNew, datesNew);
        model.addAttribute("new_number_chart", newNumberChart);

        return "sys_users_stat :: content";
    }

    @GetMapping("/sys_stat/info")
    public String getInfoStat(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.singletonList(
                new BreadcrumbElement("sys_stat", "Системная статистика")),
                "Информация о системе"));

        ErrorsStatResponse errorsStatResponse = statService.getErrorsStat(accessToken);

        PieChartData<Long, String> errorsChartData = new PieChartData<>(
                Arrays.asList(errorsStatResponse.getAuthErrorsCount(),
                errorsStatResponse.getBstatErrorsCount(),
                errorsStatResponse.getFbAdapterErrorsCount(),
                errorsStatResponse.getGatewayErrorsCount(),
                errorsStatResponse.getMailSenderErrorsCount(),
                errorsStatResponse.getStorageErrorsCount(),
                errorsStatResponse.getTechSupportErrorsCount(),
                errorsStatResponse.getTextAnalyzerErrorsCount(),
                errorsStatResponse.getVkAdapterErrorsCount(),
                errorsStatResponse.getWorkerErrorsCount()),
                Arrays.asList("Сервис авторизации", "Сервис бизнес-статистики", "Сервис Facebook Adapter",
                        "Сервис Gateway", "Сервис отправки почты", "Сервис хранения данных", "Сервис технической поддержки",
                        "Сервис анализа текста", "Сервис VK Adapter", "Сервис выполнения задач"),
                Arrays.asList("#B22222", "#FFFF00", "#FFFFE0", "#EE82EE", "#808080", "#000080", "#008000", "#2F4F4F", "#F8F8FF", "#4B0082")
        );

        model.addAttribute("errors_chart_data", errorsChartData);

        model.addAttribute("errors_stat", errorsStatResponse);

        return "sys_info_stat :: content";
    }
}
