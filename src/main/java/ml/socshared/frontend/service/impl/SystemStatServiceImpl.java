package ml.socshared.frontend.service.impl;

import ml.socshared.frontend.domain.bstat.model.ChartPoint;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.PieChartData;
import ml.socshared.frontend.service.SystemStatService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SystemStatServiceImpl implements SystemStatService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

    @Override
    public void pageCommonSystemStat(Model model) {
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Системная статистика"));

        PieChartData<Integer, String> pieChartData = new PieChartData<>(
                Arrays.asList(120, 199), Arrays.asList("Вконтакте", "Facebook"), Arrays.asList("#4d7198", "#3b5998")
        );
        model.addAttribute("soc_pie_chart", pieChartData);

        LocalDate d = LocalDate.now();
        Pair<List<Integer>, List<String>> chartAccounts = Pair.of(
                Arrays.asList(15, 19, 18, 12, 10, 11, 15, 14, 13),
                Arrays.asList(d.minusDays(8).format(formatter),
                        d.minusDays(7).format(formatter),
                        d.minusDays(6).format(formatter),
                        d.minusDays(5).format(formatter),
                        d.minusDays(4).format(formatter),
                        d.minusDays(3).format(formatter),
                        d.minusDays(2).format(formatter),
                        d.minusDays(1).format(formatter),
                        d.format(formatter)));

        model.addAttribute("vk_number_chart", chartAccounts);
        model.addAttribute("fb_number_chart", chartAccounts);

    }
}
