package ml.socshared.frontend.domain.model.form;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicationForm {
    String text;
    String dateTime;
    Boolean isDeferred;

}
