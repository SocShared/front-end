package ml.socshared.frontend.client;

import ml.socshared.frontend.config.feign.BStatClientConfiguration;
import ml.socshared.frontend.domain.bstat.response.GroupInfoResponse;
import ml.socshared.frontend.domain.bstat.response.PostInfoByTimeResponse;
import ml.socshared.frontend.domain.bstat.response.TimeSeries;
import ml.socshared.frontend.domain.model.SocialNetwork;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.UUID;

@FeignClient(name="BStatApi", url="${feign.url.api}", configuration = BStatClientConfiguration.class)
public interface BStatClient {
    @GetMapping("/api/v1/protected/social/{soc}/groups/{systemGroupId}/stat")
    GroupInfoResponse getStatisticOfGroup(@PathVariable UUID systemGroupId, @PathVariable SocialNetwork soc,
                                          @RequestParam("begin") Long begin, @RequestParam("end") Long end,
                                          @RequestHeader("Authorization") String token);

    @GetMapping("/api/v1/protected/social/{soc}/groups/{systemGroupId}/posts/{systemPostId}/stat")
    PostInfoByTimeResponse getStatisticOfPost(@PathVariable UUID systemGroupId, @PathVariable UUID systemPostId,
                                              @PathVariable SocialNetwork soc,
                                              @RequestParam("begin") Long begin, @RequestParam("end") Long end,
                                              @RequestHeader("Authorization") String token);

}
