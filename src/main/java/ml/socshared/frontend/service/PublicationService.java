package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.model.form.CheckBoxGroupForm;
import ml.socshared.frontend.domain.model.form.PublicationForm;
import org.springframework.ui.Model;

import ml.socshared.frontend.domain.text.response.KeyWordResponse;


import java.util.List;

public interface PublicationService {

    void writePublicationPage(Model model,String accessToken);
    void sendPublication(PublicationForm pub, Model model, String accessToken);
    List<KeyWordResponse> getKeyWords(String text, String token);

}

