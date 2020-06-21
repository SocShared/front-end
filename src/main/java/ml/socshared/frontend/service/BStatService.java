package ml.socshared.frontend.service;

import ml.socshared.frontend.domain.model.SocialNetwork;
import ml.socshared.frontend.domain.model.form.DurationStat;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.time.Duration;
import java.util.UUID;

public interface BStatService {

    void getGroupStatPage(UUID systemGroupId, SocialNetwork soc, Model model, DurationStat duration, Pageable pageable, String accessToken);
    void getPostStatPage(UUID systemGroupId, UUID systemPostId, SocialNetwork soc, Model model, DurationStat duration, String accessToken);
}
