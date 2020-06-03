package ml.socshared.frontend.domain.tech_support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.socshared.frontend.domain.response.RestResponsePage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullQuestion extends QuestionResponse {
    RestResponsePage<Comment> comments;
}
