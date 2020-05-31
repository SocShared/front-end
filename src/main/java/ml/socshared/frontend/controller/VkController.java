package ml.socshared.frontend.controller;

import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.model.form.AppId;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class VkController {

    GatewayServiceClient client;

    @Autowired
    VkController(GatewayServiceClient c) {
        client = c;
    }

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
                                @CookieValue("token") String token, Model model) {
        model.addAttribute("appUrl", new AppUrlAccess());
        model.addAttribute("appId", new AppId());
        model.addAttribute("success_added_app", true);
        Integer tokenVk = 4646;
        client.sendTokenForVk(tokenVk, token);
        return "connection_vk";
    }
}
