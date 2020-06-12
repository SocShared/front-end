package ml.socshared.frontend.client;


import ml.socshared.frontend.domain.adapter.response.GroupResponse;
import ml.socshared.frontend.domain.response.RestResponsePage;
import ml.socshared.frontend.domain.storage.request.PublicationRequest;
import ml.socshared.frontend.domain.storage.response.PublicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@FeignClient(name="StorageApi", url = "${feign.url.api}")
public interface StorageClient {

    @PostMapping("api/v1/protected/posts")
    PublicationResponse savePublication(@RequestBody PublicationRequest postRequest,
                                        @RequestHeader("Authorization") String token);

    @GetMapping("api/v1/protected/groups/{systemGroupId}/posts")
    RestResponsePage<PublicationResponse> getPostList(@PathVariable UUID systemGroupId,
                                                      @RequestParam Integer page,
                                                      @RequestParam Integer size,
                                                      @RequestHeader("Authorization") String token);
    @GetMapping("api/v1/protected/groups")
    RestResponsePage<GroupResponse> getGroupsList(@RequestParam Integer page,
                                                  @RequestParam Integer size,
                                                  @RequestHeader("Authorization") String token);

}
