package ml.socshared.frontend.domain.tech_support;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class ShortQuestion {
    Integer id;
    String title;
    UUID authorId;
    String authorLogin;
    Boolean haveResponse;
    ZonedDateTime time;
}
