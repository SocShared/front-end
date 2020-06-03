package ml.socshared.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BstatController  {

    @GetMapping("/bstat/groups")
    public String pageGroupStat(Model model) {
        return "bstat_group_stat";
    }

}
