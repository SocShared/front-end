package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.*;
import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.bstat.response.TimeSeries;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.form.AppId;
import ml.socshared.frontend.domain.model.form.AppUrlAccess;
import ml.socshared.frontend.domain.storage.response.Group;
import ml.socshared.frontend.domain.storage.response.GroupResponseStorage;
import ml.socshared.frontend.service.VkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VkServiceImpl implements VkService {

    final private VkAdapterClient vkClient;
    final private GatewayServiceClient gateClient;
    final private BStatClient bstatClient;
    final private StorageClient storageClient;

    final private String vkToken = "ThisIsVkToken";
    final private String bstatToken = "ThisIsVkToken";
    final private String sorageToken = "StorageToken";

    @Override
    public void getPagePostsOfGroup(UUID systemUserId, UUID systemGroupId, Pageable pageable, Model model) {

    }

    @Override
    public void vkConnection( Model model, String appAccessToken, String token) {
        model.addAttribute("appUrl", new AppUrlAccess());
        model.addAttribute("appId", new AppId());
        model.addAttribute("success_added_app", true);
        gateClient.sendTokenForVk(appAccessToken, token);
    }

    @Override
    public void getStatGroupPageAndPostList(String groupId, Pageable pageable, Model model, String token) {
        TimeSeries<Integer> online = bstatClient.getGroupOnline(groupId, LocalDate.now().minusDays(1).toEpochSecond(LocalTime.MIN, ZoneOffset.UTC),
                LocalDate.now().toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC), bstatToken );
        TimeSeries<Integer> subscribers = bstatClient.getVariabilitySubscribers(groupId, LocalDate.now().minusDays(1).toEpochSecond(LocalTime.MIN, ZoneOffset.UTC),
                LocalDate.now().toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC), bstatToken);

    }

    /**
     * Страница для выбора подключения групп
     */
    @Override
    public void getSelectionPageUserGroups(Pageable pageable, Model model, String token) {
        try {
            Page<GroupResponse> groupsPage = vkClient.getVkGroups(pageable.getPageSize(),
                    pageable.getPageNumber(), token);
            model.addAttribute("groups_page", groupsPage);
            model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                    new BreadcrumbElement("social", "Социальные Аккаунты")),
                    "Подключение групп"));
        } catch (Exception exp) {
            log.trace("Page of selection group vk error", exp);
            throw exp;
        }

    }

    /**
     * Страница со списокм подключенных груп к системе
     */
    @Override
    public void getConnectedPageUserGroups(Pageable pageable, Model model, String token) {
        Page<GroupResponseStorage> groupsPage = storageClient.getSelectedGroups(pageable.getPageNumber(),
                                                            pageable.getPageSize(), token);
        model.addAttribute("groups_page", groupsPage);
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                new BreadcrumbElement("social", "Социальные Аккаунты")),
                "Подключенные группы"));
    }

    @Override
    public void connectByGroupId(String groupId, String jwtToken) {
        log.info("ПОДКЛЮЧЕНИЕ ГРУППЫ -> {}", groupId);
        storageClient.connectVkGroupById(groupId, jwtToken);
    }
}
