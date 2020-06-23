package ml.socshared.frontend.domain.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccessType {
    @JsonProperty("confidential")
    CONFIDENTIAL,
    @JsonProperty("public")
    PUBLIC
}
