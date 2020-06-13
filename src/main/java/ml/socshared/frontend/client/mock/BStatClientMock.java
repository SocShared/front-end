package ml.socshared.frontend.client.mock;

import ml.socshared.frontend.client.BStatClient;
import ml.socshared.frontend.domain.bstat.response.TimeSeries;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


public class BStatClientMock{





    public TimeSeries<Integer> getGroupOnline(String groupId, Long begin, Long end, String token) {
        List<Integer> data = Arrays.asList(
                50, 100, 150, 60, 30, 0, 15, 40, 45, 40, 48, 100, 50, 50, 100, 150, 60, 30, 0, 15, 40, 45, 40, 48, 100, 50,
                50, 100, 150, 60, 30, 0, 15, 40, 45, 40, 48, 100, 50, 50, 100, 150, 60, 30, 0, 15, 40, 45, 40, 48, 100, 50
        );
        TimeSeries<Integer> res = new TimeSeries<>();
        res.setData(data);
        res.setSize(data.size());
        return res;
    }


    public TimeSeries<Integer> getVariabilitySubscribers(String groupId, Long begin, Long end, String token) {
        List<Integer> data = Arrays.asList(
                50, 100, 150, 60, 30, 0, 15, 40, 45, 40, 48, 100, 50, 50, 100, 150, 60, 30, 0, 15, 40, 45, 40, 48, 100, 50,
                50, 100, 150, 60, 30, 0, 15, 40, 45, 40, 48, 100, 50, 50, 100, 150, 60, 30, 0, 15, 40, 45, 40, 48, 100, 50
        );
        TimeSeries<Integer> res = new TimeSeries<>();
        res.setData(data);
        res.setSize(data.size());
        return res;
    }
}
