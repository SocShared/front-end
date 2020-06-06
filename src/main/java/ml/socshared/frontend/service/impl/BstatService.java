package ml.socshared.frontend.service.impl;

import java.util.UUID;

public interface BstatService {
    void getGroupVariabilityInfo(UUID systemGroupId, String token);
}
