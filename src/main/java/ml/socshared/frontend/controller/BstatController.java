package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.domain.model.SocialNetwork;
import ml.socshared.frontend.service.BStatService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BstatController  {

    private final BStatService bStatService;

    @GetMapping("/social/vk/groups/{systemGroupId}")
    public String pageGroupStat(
            @PathVariable UUID systemGroupId, Pageable pageable, Model model,
            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {

        bStatService.getGroupStatPage(systemGroupId, model, pageable, accessToken);
        return "bstat_group_stat";
    }

    @GetMapping("/social/vk/groups/{systemGroupId}/posts/{systemPostId}")
    public String pageGroupStat(
            @PathVariable UUID systemGroupId,  @PathVariable UUID systemPostId, Model model,
            @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        bStatService.getPostStatPage(systemGroupId, systemPostId, SocialNetwork.VK, model, accessToken);
        return "bstat_publication_stat";
    }

}
