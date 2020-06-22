package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.client.AccountClient;
import ml.socshared.frontend.domain.response.SuccessResponse;
import ml.socshared.frontend.domain.user.UserResponse;
import ml.socshared.frontend.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountClient accountClient;

    @Override
    public UserResponse getUserResponseInfo(String token) {
        return accountClient.getUserResponseInfo("Bearer " + token);
    }

    @Override
    public SuccessResponse sendMailConfirmed(String token) {
        return accountClient.sendMailConfirmed("Bearer " + token);
    }
}
