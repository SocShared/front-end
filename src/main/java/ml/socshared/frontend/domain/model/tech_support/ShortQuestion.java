package ml.socshared.frontend.domain.model.tech_support;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class ShortQuestion {
    Integer id;
    String title;
    UUID authorId;
    Boolean haveResponse;
    ZonedDateTime time;
}
