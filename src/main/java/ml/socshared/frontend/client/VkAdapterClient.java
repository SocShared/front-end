package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.storage.response.GroupResponseStorage;
import ml.socshared.frontend.domain.vk.response.VkGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="GateWayVkAPI", url="${feign.url.api}")
public interface VkAdapterClient {

    @GetMapping("api/v1/protected/social/vk/groups")
    RestResponsePage<VkGroupResponse> getVkGroups(@RequestParam("page") Integer page,
                                                  @RequestParam("size")Integer size,
                                                  @RequestHeader("Authorization") String token);

    @PostMapping("api/v1/protected/vk/groups/{socGroupId}")
    void connectVkGroupById(@PathVariable String socGroupId,
                            @RequestHeader("Authorization") String token);

    @DeleteMapping("api/v1/protected/vk/groups/{vkGroupId}")
    void deleteGroupById(@PathVariable String vkGroupId,
                         @RequestHeader("Authorization") String token);

    //RestResponsePage<PostResponse> getPostsOfGroup(Integer page, Integer size, String token);

    @GetMapping("api/v1/protected/social/vk/groups")
    RestResponsePage<VkGroupResponse> getSelectedGroups(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                             @RequestHeader("Authorization") String token );
}
