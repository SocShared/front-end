package ml.socshared.frontend.domain.storage.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.socshared.frontend.domain.storage.GroupPostStatus;
import ml.socshared.frontend.domain.storage.PostType;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationResponse {


    private UUID publicationId;
    private UUID userId;
    private String text;
    private Date publicationDateTime;
    private LocalDateTime createdAt;
    private Set<GroupPostStatus> postStatus;
    private PostType postType;

}
