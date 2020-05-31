package ml.socshared.frontend.client.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.exception.impl.HttpBadRequestException;
import ml.socshared.frontend.exception.impl.HttpNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class GatewayDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        //HashMap<Integer, >
        if (response.status() == 404) {
            String msg = methodKey + " return error: " + response.body().toString();
            log.warn(msg);
            return new HttpNotFoundException(msg);
        } else if(response.status() == 401) {

            return new HttpBadRequestException("forbidden");
        }
        String msg = "Unexpected error: " + response.body().toString();
        return new Exception(msg);
    }


}


