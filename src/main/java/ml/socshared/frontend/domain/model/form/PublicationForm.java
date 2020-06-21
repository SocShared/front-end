package ml.socshared.frontend.domain.model.form;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class PublicationForm {
    @Size(min = 5)
    private String text;
    private String dateTime;
    private Boolean isDeferred;
    @Pattern(regexp = "([\\d[A-z][А-я]\\s@]*(,?[\\d[A-z][А-я]\\s@])*)*")
    private String keywords;

}
