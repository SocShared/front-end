package ml.socshared.frontend.domain.storage.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.socshared.frontend.domain.model.SocialNetwork;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupStorageResponse {

    private String groupId;
    private String userId;
    private String name;
    private SocialNetwork socialNetwork;
    private String groupFacebookId;
    private String groupVkId;

}
