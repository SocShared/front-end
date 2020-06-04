package ml.socshared.frontend.service;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.UUID;

public interface VkService {
    void getSelectionPageUserGroups( Pageable pageable, Model model, String token);
    void getConnectedPageUserGroups(Pageable pageable, Model model, String token);
    void connectByGroupId(String groupId,String jwtToken);
    void getPagePostsOfGroup(UUID systemUserId, UUID systemGroupId, Pageable pageable, Model model);

    void vkConnection(Model model,String appAccessToken, String token);
   // void getStatGroupPageAndPostList(String groupid,Pageable pageable,Model model,String token);
}
