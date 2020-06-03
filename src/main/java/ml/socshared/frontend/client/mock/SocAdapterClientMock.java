package ml.socshared.frontend.client.mock;

import ml.socshared.frontend.client.SocAdapterClient;
import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.response.RestResponsePage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import  ml.socshared.frontend.client.mock.MockConstants;

@Component
public class SocAdapterClientMock implements SocAdapterClient {
    @Override
    public RestResponsePage<GroupResponse> getVkGroups(UUID systemUserId, Integer page, Integer size,
                                                       String token) {
        List<GroupResponse> groups = new ArrayList<>();
        groups.add(new GroupResponse(MockConstants.user1,MockConstants.socGroupId1, "GroupName!", "vk",
                500, "group", LocalDate.now()));
        groups.add(new GroupResponse(MockConstants.user1, MockConstants.socGroupId2, "SuperGroup!", "vk",
                500, "group", LocalDate.now()));
        return new RestResponsePage<>(groups);
    }

    @Override
    public RestResponsePage<GroupResponse> getFbGroups(UUID systemUserId, Integer page, Integer size,
                                                       String token) {
        List<GroupResponse> groups = new ArrayList<>();
        groups.add(new GroupResponse(MockConstants.user1,MockConstants.socGroupId1, "GroupName!", "fb",
                500, "group", LocalDate.now()));
        groups.add(new GroupResponse(MockConstants.user1, MockConstants.socGroupId2, "SuperGroup!", "fb",
                500, "group", LocalDate.now()));
        return new RestResponsePage<>(groups);
    }
}
