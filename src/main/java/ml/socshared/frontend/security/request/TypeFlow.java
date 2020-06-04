package ml.socshared.frontend.security.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TypeFlow {
    @JsonProperty("password")
    PASSWORD,
    @JsonProperty("refreshToken")
    REFRESH_TOKEN,
    @JsonProperty("authorizationCode")
    AUTHORIZATION_CODE,
    @JsonProperty("clientCredentials")
    CLIENT_CREDENTIALS
}
