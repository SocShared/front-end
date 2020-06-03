package ml.socshared.frontend.domain.adapter.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {
    UUID systemUserId;
    String groupId;
    String name;
    String adapterID;
    Integer membersCount;
    String type;
    LocalDate createData;
}
