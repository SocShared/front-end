package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.storage.response.Group;
import ml.socshared.frontend.domain.storage.response.GroupResponseStorage;
import ml.socshared.frontend.domain.storage.response.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name="StorageApi", url="${feign.url.api}")
public interface StorageClient {


    //RestResponsePage<PostResponse> getPostsOfGroup(Integer page, Integer size, String token);

    @GetMapping("api/v1/protected/social/vk/groups")
    RestResponsePage<GroupResponseStorage> getSelectedGroups(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                             @RequestHeader("Authorization") String token );

    @PostMapping("api/v1/protected/groups/vk/{socGroupId}")
    void connectVkGroupById(@PathVariable String socGroupId,
                            @RequestHeader("Authorization") String token);
}
