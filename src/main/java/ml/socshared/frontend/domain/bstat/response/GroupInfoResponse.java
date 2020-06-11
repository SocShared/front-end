package ml.socshared.frontend.domain.bstat.response;

import lombok.Data;
import ml.socshared.frontend.domain.model.SocialNetwork;

@Data
public class GroupInfoResponse {
    String groupId;
    SocialNetwork socialNetwork;
    TimeSeries<TimePoint<Integer>> online;
    TimeSeries<TimePoint<Integer>> subscribers;
 }