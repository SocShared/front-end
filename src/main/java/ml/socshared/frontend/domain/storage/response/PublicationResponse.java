package ml.socshared.frontend.domain.storage.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class PublicationResponse {
    private UUID publicationId;
    private UUID userId;
    private String text;
    private Date publicationDateTime;
    private LocalDateTime createdAt;
    private Set<GroupPostStatus> postStatus;
    private Publication.PostType postType;
}
