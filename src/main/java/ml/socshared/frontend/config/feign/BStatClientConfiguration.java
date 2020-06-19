package ml.socshared.frontend.config.feign;


import feign.Contract;
import feign.codec.ErrorDecoder;
import ml.socshared.frontend.client.decoder.BStatErrorDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Version;

public class BStatClientConfiguration {

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new BStatErrorDecoder();
    }
}
