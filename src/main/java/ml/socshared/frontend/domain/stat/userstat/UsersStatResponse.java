package ml.socshared.frontend.domain.stat.userstat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import ml.socshared.frontend.config.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersStatResponse {

    private Long onlineUsers;
    private Long newUsers;
    private Long activeUsers;
    private Long allUsers;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime dateTime;

}
