package ml.socshared.frontend.domain.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBoxGroupForm {
    Boolean isSelected;
    Boolean is = false;
    String name;
    UUID systemGroupId;
}
