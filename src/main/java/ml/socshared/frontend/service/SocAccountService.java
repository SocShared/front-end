package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.response.SocialAccountResponse;

import java.util.List;

public interface SocAccountService {

    List<SocialAccountResponse> getAccounts(String token);
}
