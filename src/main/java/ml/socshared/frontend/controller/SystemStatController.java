package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.user.UserResponse;
import ml.socshared.frontend.service.SystemStatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SystemStatController {

    private final SystemStatService sysStatService;

    @GetMapping("/sys_stat")
    public String getUserAccountInfo(@CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        log.info("Request get of system statistic page");
        sysStatService.pageCommonSystemStat(model);
        return "sys_stat";
    }

}
