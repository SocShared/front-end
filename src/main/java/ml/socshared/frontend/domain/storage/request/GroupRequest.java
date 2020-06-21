package ml.socshared.frontend.domain.storage.request;


import ml.socshared.frontend.domain.model.SocialNetwork;

import java.util.UUID;

//TODO
public class GroupRequest {
    UUID groupId;
    UUID userId;
    String name;
    SocialNetwork socialNetwork;
    String groupFacebookId;
    String groupVkId;
}
