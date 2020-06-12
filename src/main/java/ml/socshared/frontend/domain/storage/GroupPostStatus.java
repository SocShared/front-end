package ml.socshared.frontend.domain.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ml.socshared.frontend.domain.model.SocialNetwork;

import java.util.UUID;

@Getter
@Setter
public class GroupPostStatus {

    private UUID groupId;
    private PostStatus postStatus;
    private String statusText;
    private SocialNetwork socialNetwork;
    private String postFacebookId;
    private String postVkId;

    public enum PostStatus {
        @JsonProperty("published")
        PUBLISHED,
        @JsonProperty("awaiting")
        AWAITING,
        @JsonProperty("not_successful")
        NOT_SUCCESSFUL,
        @JsonProperty("processing")
        PROCESSING
    }

}
