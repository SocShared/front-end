package ml.socshared.frontend.domain.model.tech_support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.socshared.frontend.domain.response.RestResponsePage;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullQuestion extends QuestionResponse {
    RestResponsePage<Comment> comments;
}
