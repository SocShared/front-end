package ml.socshared.frontend.domain.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class NewClientRequest {

    private String name;

    @NotNull
    private AccessType accessType;

    private String validRedirectUri;

}
