package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.facebook.FacebookPage;
import ml.socshared.frontend.domain.facebook.response.AccessUrlResponse;
import ml.socshared.frontend.domain.facebook.response.FacebookGroupResponse;
import ml.socshared.frontend.domain.response.SocialAccountResponse;
import ml.socshared.frontend.domain.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@FeignClient(name = "facebook-api", url = "${feign.url.api:}")
public interface FacebookClient {

    @GetMapping("api/v1/protected/facebook/access")
    AccessUrlResponse getAccessUrl(@RequestHeader("Authorization") String token);

    @GetMapping("api/v1/protected/facebook/account")
    SocialAccountResponse getFacebookSocialAccount(@RequestHeader("Authorization") String token);

    @GetMapping("api/v1/protected/facebook/connect/{authorizationCode}")
    SuccessResponse saveAccountFacebook(@PathVariable String authorizationCode,
                                        @RequestHeader("Authorization") String token);

    FacebookPage<FacebookGroupResponse> getGroups(@RequestParam(name = "page", required = false) Integer page,
                                                  @RequestParam(name = "size", required = false) Integer size,
                                                  @RequestHeader("Authorization") String token);

    @PostMapping("/protected/fb/groups/{fbGroupId}")
    GroupResponse addGroup(@PathVariable String fbGroupId, @RequestHeader("Authorization") String token);

    @DeleteMapping("api/v1/protected/facebook/account")
    void deleteFacebookAccount(@RequestHeader("Authorization") String token);

}
