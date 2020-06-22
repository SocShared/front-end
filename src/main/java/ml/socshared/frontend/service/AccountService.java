package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.domain.user.UserResponse;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AccountService {

    UserResponse getUserResponseInfo(String token);
    SuccessResponse sendMailConfirmed(String token);

}
