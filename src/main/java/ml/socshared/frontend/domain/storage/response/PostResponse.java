package ml.socshared.frontend.domain.storage.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class PostResponse {
    UUID publicationId;
    UUID userId;
    String text;
    Date publicationDateTime;
    LocalDateTime createdAt;
    Set<GroupPostStatus> postStatus;
}
