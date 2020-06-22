package ml.socshared.frontend.domain.tech_support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class Comment {
    Integer id;
    String authorLogin;
    String text;
    ZonedDateTime time;
}