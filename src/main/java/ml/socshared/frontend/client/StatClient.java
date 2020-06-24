package ml.socshared.frontend.client;

import ml.socshared.frontend.config.feign.DefaultClientConfiguration;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.stat.SocCountResponse;
import ml.socshared.frontend.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.frontend.domain.stat.userstat.UsersStatResponse;
import ml.socshared.frontend.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.frontend.domain.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "stat-client", url = "${feign.url.api}", configuration = DefaultClientConfiguration.class)
public interface StatClient {

    @GetMapping(value = "/api/v1/protected/stat/social")
    UsingSocialNetworkResponse getUsingSocialNetworkStat(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/online/count")
    UsersStatResponse getOnlineUsersCount(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/online/count/timeline")
    List<UsersStatResponse> getOnlineUsersStatTimeline(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/online")
    RestResponsePage<UserResponse> getOnlineUsers(@RequestParam(name = "page") Integer page,
                                                  @RequestParam(name = "size") Integer size,
                                                  @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/active/count")
    UsersStatResponse getActiveUsersCount(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/active/count/timeline")
    List<UsersStatResponse> getActiveUsersStatTimeline(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/active")
    RestResponsePage<UserResponse> getActiveUsersStat(@RequestParam(name = "page") Integer page,
                                                      @RequestParam(name = "size") Integer size,
                                                      @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/new/count")
    UsersStatResponse getNewUsersCount(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/new/count/timeline")
    List<UsersStatResponse> getNewUsersStatTimeline(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/new")
    RestResponsePage<UserResponse> getNewUsers(@RequestParam(name = "page") Integer page,
                                               @RequestParam(name = "size") Integer size,
                                               @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/all/count")
    UsersStatResponse getAllUsersCount(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/all/count/timeline")
    List<UsersStatResponse> getAllUsersStatTimeline(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/users/all")
    RestResponsePage<UserResponse> getAllUsers(@RequestParam(name = "page") Integer page,
                                               @RequestParam(name = "size") Integer size,
                                               @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/errors")
    ErrorsStatResponse getErrorsStat(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/protected/stat/soc/count")
    SocCountResponse getSocCount(@RequestHeader("Authorization") String token);

}
