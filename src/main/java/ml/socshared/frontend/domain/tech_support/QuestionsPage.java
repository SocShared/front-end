package ml.socshared.frontend.domain.tech_support;

import lombok.Data;
import ml.socshared.frontend.domain.response.RestResponsePage;
import org.springframework.data.domain.Page;

@Data
public class QuestionsPage {
    private RestResponsePage<ShortQuestion> shortQuestions;
    private Boolean canDelete;
}
