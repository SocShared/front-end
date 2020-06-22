package ml.socshared.frontend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class UpdateUserRequest {

    @JsonIgnore
    private String username;
    @JsonIgnore
    private boolean emailVerified;
    @NotEmpty
    @Email
    private String email;
    private String firstname;
    private String lastname;

}
