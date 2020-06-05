package ml.socshared.frontend.domain.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FacebookTypeGroup {
    @JsonProperty("PAGE")
    FB_PAGE,
    @JsonProperty("GROUP")
    FB_GROUP
}