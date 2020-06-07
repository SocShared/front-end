package ml.socshared.frontend.domain.storage.request;

import lombok.Getter;
import lombok.Setter;
import ml.socshared.frontend.domain.storage.PostType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class PostRequest {

    private Date publicationDateTime;
    private GroupRequest groups;
    private PostType type;
    @NotNull
    private String text;
    private String[] hashTags;

}
