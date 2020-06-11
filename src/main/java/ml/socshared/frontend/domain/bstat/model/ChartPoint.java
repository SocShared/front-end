package ml.socshared.frontend.domain.bstat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartPoint <Tx, Ty>{
    Tx x;
    Ty y;
}
