package ml.socshared.frontend.domain.storage.request;

import lombok.Getter;
import lombok.Setter;
import ml.socshared.frontend.domain.storage.PostType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class PublicationRequest {

    private Date publicationDateTime;
    @NotEmpty
    private String[] groupIds;
    @NotNull
    private PostType type;
    @NotNull
    private String text;
    private String[] hashTags;

}