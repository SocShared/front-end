package ml.socshared.frontend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
    AccountType type;
    String accountId;
    String username;
}
