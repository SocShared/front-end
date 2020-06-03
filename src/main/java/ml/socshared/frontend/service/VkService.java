package ml.socshared.frontend.service;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.UUID;

public interface VkService {
    void getSelectionPageUserGroups(UUID systemUserId, Pageable pageable, Model model);
    void getConnectedPageUserGroups(UUID systemUserId, Pageable pageable, Model model);
    void getPagePostsOfGroup(UUID systemUserId, UUID systemGroupId, Pageable pageable, Model model);

    void vkConnection(Model model, String token);
}
