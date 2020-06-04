package ml.socshared.frontend.client.mock;

import ml.socshared.frontend.client.StorageClient;
import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.storage.response.Group;
import ml.socshared.frontend.domain.storage.response.GroupResponseStorage;
import ml.socshared.frontend.domain.storage.response.PostResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class StorageClientMock implements StorageClient {
    @Override
    public RestResponsePage<PostResponse> getPostsOfGroup(Integer page, Integer size, String token) {
        return null;
    }

    @Override
    public RestResponsePage<GroupResponseStorage> getSelectedGroups(Integer page, Integer size, String token) {
        List<GroupResponseStorage> groups = new ArrayList<>();
        groups.add(new GroupResponseStorage(MockConstants.socGroupId1, MockConstants.user1, "NotSelectedGroup1", Group.SocialNetwork.VK, "", "2"));
        groups.add(new GroupResponseStorage(MockConstants.socGroupId2, MockConstants.user1, "g1", Group.SocialNetwork.VK, "", "3"));
        return new RestResponsePage<>(groups);
    }

    @Override
    public void connectGroupById(String groupId, Group.SocialNetwork socialNetwork, String token) {

    }
}
