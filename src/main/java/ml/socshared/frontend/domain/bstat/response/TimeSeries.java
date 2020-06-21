package ml.socshared.frontend.domain.bstat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSeries<T> extends DataList<T> {
    LocalDate begin;
    LocalDate end;
}
