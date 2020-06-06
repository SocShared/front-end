package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.text.response.KeyWordResponse;

import java.util.List;

public interface PublicationService {

    List<KeyWordResponse> getKeyWords(String text, String token);

}
