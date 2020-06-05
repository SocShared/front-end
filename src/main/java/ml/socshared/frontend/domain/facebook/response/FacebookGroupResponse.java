package ml.socshared.frontend.domain.facebook.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ml.socshared.frontend.domain.facebook.FacebookTypeGroup;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FacebookGroupResponse {

    private String systemUserId;
    private String systemGroupId;
    private String groupId;
    private String name;
    private String adapterId;
    private Integer membersCount;
    private FacebookTypeGroup type;
    private Boolean selected;

}
