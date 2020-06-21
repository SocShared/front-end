package ml.socshared.frontend.config.feign;

import feign.codec.ErrorDecoder;
import ml.socshared.frontend.client.decoder.BStatErrorDecoder;
import ml.socshared.frontend.client.decoder.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;

public class DefaultClientConfiguration {
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
