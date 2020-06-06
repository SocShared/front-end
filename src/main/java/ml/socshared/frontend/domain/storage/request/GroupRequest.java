package ml.socshared.frontend.domain.storage.request;


import ml.socshared.frontend.domain.storage.response.Group;

import java.util.UUID;

//TODO
public class GroupRequest {
    UUID groupId;
    UUID userId;
    String name;
    Group.SocialNetwork socialNetwork;
    String facebookId;
    String vkId;
}
