package ml.socshared.frontend.client;

import ml.socshared.frontend.domain.bstat.response.TimeSeries;

public interface BStatClient {
    TimeSeries<Integer> getGroupOnline(String groupId, Long begin, Long end, String token);
    TimeSeries<Integer> getVariabilitySubscribers(String groupId, Long begin, Long end, String token);
}
