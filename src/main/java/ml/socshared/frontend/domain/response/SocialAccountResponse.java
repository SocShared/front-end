package ml.socshared.frontend.domain.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ml.socshared.frontend.domain.model.SocialNetwork;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SocialAccountResponse {
    private UUID systemUserId;
    private String accountId;
    private String firstName;
    private String lastName;
    private String email;
    private SocialNetwork socialNetwork;
}
