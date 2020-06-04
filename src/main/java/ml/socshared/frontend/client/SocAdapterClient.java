package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.response.RestResponsePage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name="GateWayVkAPI", url="${feign.url.api}")
public interface SocAdapterClient {

        @GetMapping("api/v1/protected/social/vk/groups")
        RestResponsePage<GroupResponse> getVkGroups(@RequestParam("page") Integer page,
                                                    @RequestParam("size")Integer size,
                                                    @RequestHeader("Authorization") String token);


//        RestResponsePage<GroupResponse> getFbGroups(Integer page, Integer size,
//                                                    String token);

}
