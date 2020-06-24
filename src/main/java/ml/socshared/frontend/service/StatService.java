package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.stat.SocCountResponse;
import ml.socshared.frontend.domain.stat.TotalStatsResponse;
import ml.socshared.frontend.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.frontend.domain.stat.userstat.UsersStatResponse;
import ml.socshared.frontend.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.frontend.domain.user.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface StatService {

    UsingSocialNetworkResponse getUsingSocialNetworkStat(String token);
    UsersStatResponse getOnlineUsersCount(String token);
    List<UsersStatResponse> getOnlineUsersStatTimeline(String token);
    RestResponsePage<UserResponse> getOnlineUsers(Integer page, Integer size, String token);
    UsersStatResponse getActiveUsersCount(String token);
    List<UsersStatResponse> getActiveUsersStatTimeline(String token);
    RestResponsePage<UserResponse> getActiveUsersStat(Integer page, Integer size, String token);
    UsersStatResponse getNewUsersCount(String token);
    List<UsersStatResponse> getNewUsersStatTimeline(String token);
    RestResponsePage<UserResponse> getNewUsers(Integer page, Integer size, String token);
    UsersStatResponse getAllUsersCount(String token);
    List<UsersStatResponse> getAllUsersStatTimeline(String token);
    RestResponsePage<UserResponse> getAllUsers(Integer page, Integer size, String token);
    ErrorsStatResponse getErrorsStat(String token);
    SocCountResponse getSocCount(String token);
    TotalStatsResponse getTotalCount(String token);

}
