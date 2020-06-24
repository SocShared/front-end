package ml.socshared.frontend.domain.stat.usingsocial;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsingSocialNetworkResponse {

    private VkEventsResponse vk;
    private FacebookEventsResponse facebook;

}
