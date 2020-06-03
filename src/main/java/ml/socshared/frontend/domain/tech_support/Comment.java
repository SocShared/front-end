package ml.socshared.frontend.domain.tech_support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class Comment {
    Integer id;
    @JsonProperty(required = true)
    UUID authorId;
    @JsonProperty(required = true)
    String text;
    ZonedDateTime time;
}
