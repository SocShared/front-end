package ml.socshared.frontend.domain.storage.response;

import com.fasterxml.jackson.annotation.JsonProperty;

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