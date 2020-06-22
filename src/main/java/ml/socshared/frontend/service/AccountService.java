package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.user.UserResponse;

public interface AccountService {

    UserResponse getUserResponseInfo(String token);

}
