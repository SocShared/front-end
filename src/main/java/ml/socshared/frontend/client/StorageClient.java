package ml.socshared.frontend.client;


import ml.socshared.frontend.domain.storage.request.PublicationRequest;
import ml.socshared.frontend.domain.storage.response.PublicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@FeignClient(name="StorageApi", url = "${feign.url.api}")
public interface StorageClient {

    @PostMapping("/protected/posts")
    PublicationResponse savePublication(@RequestBody PublicationRequest postRequest,
                                        @RequestHeader("Authorization") String token);

    @GetMapping("/protected/groups/{systemGroupId}/posts")
    public Page<PublicationResponse> getPostList(@PathVariable UUID systemGroupId,
                                                 @Min(0) @NotNull @RequestParam(name = "page", required = false) Integer page,
                                                 @Min(0) @Max(100) @NotNull @RequestParam(name = "size", required = false) Integer size,
                                                 @RequestHeader("Authorization") String token);
}
