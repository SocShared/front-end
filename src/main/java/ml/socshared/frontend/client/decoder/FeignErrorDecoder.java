package ml.socshared.frontend.client.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.exception.impl.HttpBadRequestException;
import ml.socshared.frontend.exception.impl.HttpForbiddenException;
import ml.socshared.frontend.exception.impl.HttpNotFoundException;
import ml.socshared.frontend.exception.impl.HttpUnauthorizedException;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            String msg = methodKey + " return error: " + response.body().toString();
            log.warn(msg);
            return new HttpNotFoundException(msg);
        } else if (response.status() == 401) {
            return new HttpUnauthorizedException("unauthorized");
        } else if (response.status() == 403) {
            return new HttpForbiddenException("forbidden");
        } else if (response.status() == 400) {
            String msg = methodKey + " return error: " + response.body().toString();
            log.warn(msg);
            return new HttpNotFoundException(msg);
        }

        String msg = "Unexpected error: " + response.status();
        return new Exception(msg);
    }


}


