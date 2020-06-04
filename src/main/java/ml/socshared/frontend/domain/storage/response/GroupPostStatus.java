package ml.socshared.frontend.domain.storage.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.UUID;

@Data
public class GroupPostStatus {

        private UUID groupId;
        private PostStatus postStatus;

        @JsonBackReference
        private Publication publication;
        public enum PostStatus {
            PUBLISHED,
            AWAITING,
            NOT_SUCCESSFUL,
            PROCESSING
        }

        public GroupPostStatus() {
            postStatus = PostStatus.AWAITING;
        }

    }

