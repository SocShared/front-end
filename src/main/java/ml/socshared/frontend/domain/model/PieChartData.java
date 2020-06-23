package ml.socshared.frontend.domain.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class PieChartData <DataT, LabelT> {

    public PieChartData(List<DataT> d, List<LabelT> l, List<String> c) {
        if(d == null || l == null || c == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if(!(d.size() == l.size() && d.size() == c.size())) {
            throw new IllegalArgumentException(String.format("All list must have equals size; But, size: d -> {}; l-> {}, c -> {}",
                                                d.size(), l.size(), c.size()));
        }
        data = d;
        labels = l;
        colors = c;
    }


    private List<DataT> data;
    private List<LabelT> labels;
    private List<String> colors;
}
