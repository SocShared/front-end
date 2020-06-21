package ml.socshared.frontend.domain.storage.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.socshared.frontend.domain.model.SocialNetwork;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseStorage {
    String groupId;
    UUID userId;
    String name;
    SocialNetwork socialNetwork;
    String groupFacebookId;
    String groupVkId;
}
