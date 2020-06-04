package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.storage.response.GroupResponseStorage;
import ml.socshared.frontend.domain.storage.response.PostResponse;

import java.util.UUID;

public interface StorageClient {
    RestResponsePage<PostResponse> getPostsOfGroup(UUID systemGroupId, Integer page, Integer size, String token);
    RestResponsePage<GroupResponseStorage> getSelectedGroups(UUID systemUserId, Integer page, Integer size, String token);
}
