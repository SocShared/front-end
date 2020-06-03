package ml.socshared.frontend.domain.request.tech_support;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionCreateRequest {
    UUID authorId;
    String title;
    String text;
}
