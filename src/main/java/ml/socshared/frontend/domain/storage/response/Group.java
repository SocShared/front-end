package ml.socshared.frontend.domain.storage.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import ml.socshared.frontend.domain.storage.response.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
public class Group extends BaseEntity {

    private UUID groupId;

    private UUID userId;

    private String name;

    private SocialNetwork socialNetwork;


    private String facebookId;


    private String vkId;

    @JsonBackReference
    private Set<Publication> publications;

    public enum SocialNetwork {
        @JsonProperty("VK")
        VK,
        @JsonProperty("FB")
        FACEBOOK
    }
}
