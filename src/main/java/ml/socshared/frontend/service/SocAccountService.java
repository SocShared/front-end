package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.model.SocialAccount;

import java.util.List;
import java.util.UUID;

public interface SocAccountService {

    List<SocialAccount> getAccounts(String token);
}
