package ml.socshared.frontend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinearChart <DataType, LabelType> {
    List<DataType> data;
    List<LabelType> labels;
    String name;
}
