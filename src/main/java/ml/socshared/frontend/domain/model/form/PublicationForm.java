package ml.socshared.frontend.domain.model.form;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class PublicationForm {
    @Size(min = 5)
    String text;
    String dateTime;
    Boolean isDeferred;

}
