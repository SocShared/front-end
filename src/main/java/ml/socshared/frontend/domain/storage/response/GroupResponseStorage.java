package ml.socshared.frontend.domain.storage.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseStorage {
    String groupId;
    UUID userId;
    String name;
    String socialNetwork;
    String facbookId;
    String vkId;
}
