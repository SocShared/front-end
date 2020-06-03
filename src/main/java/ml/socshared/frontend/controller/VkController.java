package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.client.mock.MockConstants;
import ml.socshared.frontend.domain.model.form.AppId;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import ml.socshared.frontend.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequiredArgsConstructor
public class VkController {

    private final GatewayServiceClient client;

    private final VkService service;


    @PostMapping(value = "/social/connection/vk/redirect")
    public void connectionVkAccount(@ModelAttribute AppId appId, HttpServletResponse httpServletResponse) {
        String url = "https://oauth.vk.com/authorize?client_id=" +String.valueOf(appId.getId()) + "&response_type=token&scope=offline,groups,wall&v=103.5";
        httpServletResponse.setHeader("Location", url);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/social/connection/vk")
    public String connectionVkAccount(Model model, @CookieValue(name = "token", required = false) String token) {
        model.addAttribute("appUrl", new AppUrlAccess());
        model.addAttribute("appId", new AppId());
        return "connection_vk";
    }

    @PostMapping("/social/connection/vk")
    public String applicationUrl(@ModelAttribute AppUrlAccess appUrl,
                                @CookieValue(value = "token", required = false) String token, Model model) {
        log.info("appId: " + String.valueOf(appUrl.getUrl()));

        service.vkConnection(model, token);

        return "connection_vk";
    }



    @GetMapping("/social/vk/groups")
    public String getPageOfConnectedVkGroups(Pageable pageable, Model model,
                                             @CookieValue(value = "JWT_AT", defaultValue = "") String jwtToken) {

        log.info("Request get page of connected vk groups");
        service.getConnectedPageUserGroups(MockConstants.user1, pageable, model);
        return "soc_vk_page_groups_connected";
    }

    @GetMapping("/social/vk/groups/{groupId}")
    public String getPageOfConnectedVkGroups(@PathVariable String groupId,
                                            Pageable pageable, Model model,
                                             @CookieValue(value = "JWT_AT", defaultValue = "") String jwtToken) {

        log.info("Request get page of connected vk groups");
        service.getStatGroupPageAndPostList(groupId, pageable, model, "");
        return "soc_vk_page_groups_connected";
    }
}
