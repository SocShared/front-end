package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.model.SocialNetwork;
import ml.socshared.frontend.domain.response.SocialAccountResponse;

import java.util.List;

public interface SocAccountService {

    List<SocialAccountResponse> getAccounts(String token);
    SocialAccountResponse getFacebookSocialAccount(String token);
    boolean checkSocialAccount(List<SocialAccountResponse> response, SocialNetwork network);

}
