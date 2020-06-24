package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.SocialNetwork;
import ml.socshared.frontend.domain.model.form.DurationStat;
import ml.socshared.frontend.service.BStatService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
@Slf4j
@Controller
@RequiredArgsConstructor
public class BstatController  {

    private final BStatService bStatService;

    @GetMapping("/social/vk/groups/{systemGroupId}")
    public String pageVkGroupStat(@ModelAttribute DurationStat duration,
            @PathVariable UUID systemGroupId, Pageable pageable, Model model,
            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("request get vk group statistic");
        model.addAttribute("systemGroupId", systemGroupId);
        model.addAttribute("socialNetwork", "vk");
        bStatService.getGroupStatPage(systemGroupId, SocialNetwork.VK, model, duration, pageable, accessToken);
        return "bstat_group_stat :: content";
    }

    @GetMapping("/social/facebook/groups/{systemGroupId}")
    public String pageFbGroupStat(@ModelAttribute DurationStat duration,
            @PathVariable UUID systemGroupId, Pageable pageable, Model model,
            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("request get fb group statistic");
        model.addAttribute("systemGroupId", systemGroupId);
        model.addAttribute("socialNetwork", "facebook");
        bStatService.getGroupStatPage(systemGroupId, SocialNetwork.FACEBOOK, model, duration, pageable, accessToken);
        return "bstat_group_stat :: content";
    }


    @GetMapping("/social/vk/groups/{systemGroupId}/posts/{systemPostId}")
    public String pageVkPostStat(@ModelAttribute DurationStat duration,
            @PathVariable UUID systemGroupId,  @PathVariable UUID systemPostId, Model model,
            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("systemGroupId", systemGroupId);
        model.addAttribute("systemPostId", systemPostId);
        model.addAttribute("socialNetwork", "vk");
        bStatService.getPostStatPage(systemGroupId, systemPostId, SocialNetwork.VK, model, duration, accessToken);
        return "bstat_publication_stat :: content";
    }

    @GetMapping("/social/facebook/groups/{systemGroupId}/posts/{systemPostId}")
    public String pageFbPostStat(@ModelAttribute DurationStat duration,
            @PathVariable UUID systemGroupId,  @PathVariable UUID systemPostId, Model model,
            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("systemGroupId", systemGroupId);
        model.addAttribute("systemPostId", systemPostId);
        model.addAttribute("socialNetwork", "facebook");
        bStatService.getPostStatPage(systemGroupId, systemPostId, SocialNetwork.FACEBOOK, model,duration,  accessToken);
        return "bstat_publication_stat :: content";
    }

}
