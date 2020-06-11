package ml.socshared.frontend.domain.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.socshared.frontend.domain.storage.response.PostStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupPostStatus {

    private UUID groupId;
    private PostStatus postStatus;


}
