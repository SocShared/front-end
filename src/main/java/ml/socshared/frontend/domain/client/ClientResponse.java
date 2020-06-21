package ml.socshared.frontend.domain.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ml.socshared.frontend.domain.user.RoleResponse;
import ml.socshared.frontend.domain.user.UserResponse;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientResponse {

    private UUID clientId;
    private UUID clientSecret;
    private String name;
    private AccessType accessType;
    private String validRedirectUri;
    private Set<RoleResponse> roles;
    private UserResponse user;



}
