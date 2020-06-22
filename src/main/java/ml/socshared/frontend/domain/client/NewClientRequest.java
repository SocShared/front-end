package ml.socshared.frontend.domain.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@ToString
public class NewClientRequest {

    private UUID clientId;
    private UUID clientSecret;

    private String name;

    @NotNull
    private AccessType accessType;

    @URL(message = "Ожидалось URL")
    private String validRedirectUri;

}
