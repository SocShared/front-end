package ml.socshared.frontend.domain.tech_support.request;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionCreateRequest {
    UUID authorId;
    String title;
    String text;
}
