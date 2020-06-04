package ml.socshared.frontend.domain.storage.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import ml.socshared.frontend.domain.storage.response.base.BaseEntity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class Publication extends BaseEntity {

    private UUID publicationId;
    private UUID userId;
    private String text;

    @JsonManagedReference
    private Set<Group> groups;

    private Date publicationDateTime;

    private PostType postType;

    @JsonManagedReference
    private Set<GroupPostStatus> postStatus;

    public Publication() {
    }

    public enum PostType {
        @JsonProperty("in_real_time")
        IN_REAL_TIME,
        @JsonProperty("deferred")
        DEFERRED
    }
}
