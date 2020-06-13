package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.model.SocialNetwork;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.UUID;

public interface BStatService {

    void getGroupStatPage(UUID systemGroupId, Model model, Pageable pageable, String accessToken);
    void getPostStatPage(UUID systemGroupId, UUID systemPostId, SocialNetwork soc, Model model, String accessToken);
}
