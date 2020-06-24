package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.client.StatClient;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.stat.SocCountResponse;
import ml.socshared.frontend.domain.stat.errorstat.ErrorsStatResponse;
import ml.socshared.frontend.domain.stat.userstat.UsersStatResponse;
import ml.socshared.frontend.domain.stat.usingsocial.UsingSocialNetworkResponse;
import ml.socshared.frontend.domain.user.UserResponse;
import ml.socshared.frontend.service.StatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatClient statClient;

    @Override
    public UsingSocialNetworkResponse getUsingSocialNetworkStat(String token) {
        return statClient.getUsingSocialNetworkStat("Bearer " + token);
    }

    @Override
    public UsersStatResponse getOnlineUsersCount(String token) {
        return statClient.getOnlineUsersCount("Bearer " + token);
    }

    @Override
    public List<UsersStatResponse> getOnlineUsersStatTimeline(String token) {
        return statClient.getOnlineUsersStatTimeline("Bearer " + token);
    }

    @Override
    public RestResponsePage<UserResponse> getOnlineUsers(Integer page, Integer size, String token) {
        return statClient.getOnlineUsers(page, size, "Bearer " + token);
    }

    @Override
    public UsersStatResponse getActiveUsersCount(String token) {
        return statClient.getActiveUsersCount("Bearer " + token);
    }

    @Override
    public List<UsersStatResponse> getActiveUsersStatTimeline(String token) {
        return statClient.getActiveUsersStatTimeline("Bearer " + token);
    }

    @Override
    public RestResponsePage<UserResponse> getActiveUsersStat(Integer page, Integer size, String token) {
        return statClient.getActiveUsersStat(page, size, "Bearer " + token);
    }

    @Override
    public UsersStatResponse getNewUsersCount(String token) {
        return statClient.getNewUsersCount("Bearer " + token);
    }

    @Override
    public List<UsersStatResponse> getNewUsersStatTimeline(String token) {
        return statClient.getNewUsersStatTimeline("Bearer " + token);
    }

    @Override
    public RestResponsePage<UserResponse> getNewUsers(Integer page, Integer size, String token) {
        return statClient.getNewUsers(page, size, "Bearer " + token);
    }

    @Override
    public UsersStatResponse getAllUsersCount(String token) {
        return statClient.getAllUsersCount("Bearer " + token);
    }

    @Override
    public List<UsersStatResponse> getAllUsersStatTimeline(String token) {
        return statClient.getAllUsersStatTimeline("Bearer " + token);
    }

    @Override
    public RestResponsePage<UserResponse> getAllUsers(Integer page, Integer size, String token) {
        return statClient.getAllUsers(page, size, "Bearer " + token);
    }

    @Override
    public ErrorsStatResponse getErrorsStat(String token) {
        return statClient.getErrorsStat("Bearer " + token);
    }

    @Override
    public SocCountResponse getSocCount(String token) {
        return statClient.getSocCount("Bearer " + token);
    }
}
