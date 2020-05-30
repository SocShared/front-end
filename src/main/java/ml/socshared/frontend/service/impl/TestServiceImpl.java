package ml.socshared.frontend.service.impl;

import ml.socshared.frontend.client.TestFeignClient;
import ml.socshared.frontend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private TestFeignClient client;

    @Autowired
    public TestServiceImpl(TestFeignClient feignClient) {
        this.client = feignClient;
    }

    @Override
    public String test() {
        return client.test();
    }
}
