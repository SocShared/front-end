package ml.socshared.frontend.domain.bstat.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostInfoByTimeResponse {
    String groupId;
    String postId;
    LocalDateTime begin;
    LocalDateTime end;
    DataList<TimePoint<Integer>> variabilityNumberViews;
    DataList<TimePoint<Integer>> variabilityNumberLikes;
    DataList<TimePoint<Integer>> variabilityNumberShares;
    DataList<TimePoint<Integer>> variabilityNumberComments;
}
