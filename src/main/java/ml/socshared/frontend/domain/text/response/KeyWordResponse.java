package ml.socshared.frontend.domain.text.response;

import lombok.Data;

@Data
public class KeyWordResponse {
    private String keyWord;
    private double score;
}