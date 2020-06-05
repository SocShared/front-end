package ml.socshared.frontend.domain.vk.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VkGroupResponse {
    private String systemUserId;
    private String systemGroupId;
    private String groupId;
    private String name;
    private String adapterId;
    private Integer membersCount;
    private VkGroupType type;
    private Boolean selected;
}