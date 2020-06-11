package ml.socshared.frontend.domain.bstat.response;

import java.time.Duration;
import java.time.LocalDate;

public class TimeSeries<T> extends DataList<T> {
    LocalDate begin;
    LocalDate end;
}
