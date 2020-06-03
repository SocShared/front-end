package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.response.RestResponsePage;

import java.util.UUID;

public interface SocAdapterClient {

        RestResponsePage<GroupResponse> getVkGroups(UUID systemUserId, Integer page, Integer size,
                                                    String token);
        RestResponsePage<GroupResponse> getFbGroups(UUID systemUserId, Integer page, Integer size,
                                                    String token);

}
