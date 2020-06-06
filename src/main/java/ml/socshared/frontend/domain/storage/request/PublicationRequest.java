package ml.socshared.frontend.domain.storage.request;

import lombok.Getter;
import lombok.Setter;
import ml.socshared.frontend.domain.storage.response.GroupPostStatus;
import ml.socshared.frontend.domain.storage.response.Publication;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class PublicationRequest {

    private GroupPostStatus.PostStatus postStatus;
    private Date publicationDateTime;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String[] groupIds;
    @NotNull
    private Publication.PostType type;
    @NotNull
    private String text;

}