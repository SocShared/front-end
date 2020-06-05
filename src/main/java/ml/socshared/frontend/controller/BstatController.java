package ml.socshared.frontend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class BstatController  {

    @GetMapping("/bstat/groups/{systemGroupId}")
    public String pageGroupStat(
            @PathVariable UUID systemGroupId, Model model,
            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {

        return "bstat_group_stat";
    }

}
