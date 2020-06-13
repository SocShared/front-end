package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.model.form.CheckBoxGroupForm;
import ml.socshared.frontend.domain.model.form.PublicationForm;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import ml.socshared.frontend.domain.text.response.KeyWordResponse;

import java.util.List;
import java.util.UUID;

public interface PublicationService {

    void writePublicationPage(Model model,String accessToken);
    void sendPublication(PublicationForm pub, Model model, String accessToken);
    List<KeyWordResponse> getKeyWords(String text, String token);
    void getPublicationsByGroupId(UUID systemGroupId, Pageable pageable, Model model, String accessToken);

}

