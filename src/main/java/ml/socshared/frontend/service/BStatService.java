package ml.socshared.frontend.service;

import org.springframework.ui.Model;

import java.util.UUID;

public interface BStatService {

    void getGroupStatPage(UUID systemGroupId, Model model,String accessToken);
}
