package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.client.SocAdapterClient;
import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.form.AppId;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import ml.socshared.frontend.service.VkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VkServiceImpl implements VkService {

    final private SocAdapterClient vkClient;
    final private GatewayServiceClient gateClient;

    final private String vkToken = "ThisIsVkToken";

    @Override
    public void getPagePostsOfGroup(UUID systemUserId, UUID systemGroupId, Pageable pageable, Model model) {

    }

    @Override
    public void vkConnection( Model model, String token) {
        model.addAttribute("appUrl", new AppUrlAccess());
        model.addAttribute("appId", new AppId());
        model.addAttribute("success_added_app", true);
        String tokenVk = "4646";
        gateClient.sendTokenForVk(tokenVk, token);
    }


    @Override
    public void getSelectionPageUserGroups(UUID systemUserId, Pageable pageable, Model model) {

    }

    @Override
    public void getConnectedPageUserGroups(UUID systemUserId, Pageable pageable, Model model) {
        Page<GroupResponse> groupsPage = vkClient.getVkGroups(systemUserId, pageable.getPageSize(),
                                                            pageable.getPageNumber(), vkToken);
        model.addAttribute("groups_page", groupsPage);
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                new BreadcrumbElement("social", "Социальные Аккаунты")),
                "подключенные группы вконтакте"));
    }
}
