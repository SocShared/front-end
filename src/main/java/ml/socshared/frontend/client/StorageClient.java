package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.storage.response.Group;
import ml.socshared.frontend.domain.storage.response.GroupResponseStorage;
import ml.socshared.frontend.domain.storage.response.PostResponse;

import java.util.UUID;

public interface StorageClient {
    RestResponsePage<PostResponse> getPostsOfGroup(Integer page, Integer size, String token);
    RestResponsePage<GroupResponseStorage> getSelectedGroups(Integer page, Integer size, String token);
    void connectGroupById(String gtoupId, Group.SocialNetwork socialNetwork, String token);
}
