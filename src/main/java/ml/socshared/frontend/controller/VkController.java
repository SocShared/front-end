package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.model.form.AppId;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import ml.socshared.frontend.service.VkService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class VkController {

    private final VkService service;


    @PostMapping(value = "/social/connection/vk/redirect")
    public void connectionVkAccount(@ModelAttribute AppId appId, HttpServletResponse httpServletResponse,
                                    @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        String url = "https://oauth.vk.com/authorize?client_id=" +String.valueOf(appId.getId()) + "&response_type=token&scope=offline,groups,wall&v=103.5";
        httpServletResponse.setHeader("Location", url);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/social/connection/vk")
    public String connectionVkAccount(Model model,
                                      @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        model.addAttribute("appUrl", new AppUrlAccess());
        model.addAttribute("appId", new AppId());
        return "connection_vk :: content";
    }

    @PostMapping("/social/connection/vk")
    public String applicationUrl(@ModelAttribute AppUrlAccess appUrl,
                                 @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken, Model model) {
        log.info("appId: " + String.valueOf(appUrl.getUrl()));
        try {
            //TODO передается токен а не URL
            service.vkConnection(model, appUrl.getUrl(), accessToken);
        } catch (Exception exp) {
            log.error("Exception", exp);
        }


        return "redirect:/lk";
    }

    @GetMapping("/social/vk/groups")
    public String getPageOfConnectedVkGroups(Pageable pageable, Model model,
                                             @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {

        log.info("Request get page of connected vk groups");
        service.getConnectedPageUserGroups(pageable, model, accessToken);
        return "soc_vk_groups :: content";
    }



    @GetMapping("/social/vk/groups/connection/{vkGroupId}")
    public String connectionGroup(@PathVariable String vkGroupId, Model model,
                                   @CookieValue(value = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("Request to connection group");
        service.connectByGroupId(vkGroupId, accessToken);
        service.getConnectedPageUserGroups(PageRequest.of(0, 20 ), model, accessToken);
        return "soc_vk_groups :: content";
    }
    @GetMapping("/social/vk/groups/disconnection/{vkGroupId}")
    public String disconnectionGroup(@PathVariable String vkGroupId, Model model,
                                     @CookieValue(value = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("Request disconnection group");
        service.disconnectionGroupById(vkGroupId, accessToken);
        service.getConnectedPageUserGroups(PageRequest.of(0, 20 ), model, accessToken);
        return "soc_vk_groups :: content";
    }
}
