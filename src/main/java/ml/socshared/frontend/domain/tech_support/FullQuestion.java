package ml.socshared.frontend.domain.tech_support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.socshared.frontend.domain.response.RestResponsePage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FullQuestion extends QuestionResponse {
    RestResponsePage<Comment> comments;
    Boolean canCreateComment;
    Boolean canDeleteQuestion;
}
