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

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = "";
        if(response.body() != null) {
            body = response.body().toString();
        }

        if (response.status() == 404) {
            String msg = methodKey + " return error (404): " + body;
            log.warn(msg);
            return new HttpNotFoundException(msg);
        } else if (response.status() == 401) {
            return new HttpUnauthorizedException("unauthorized");
        } else if (response.status() == 403) {
            return new HttpForbiddenException("forbidden");
        } else if (response.status() == 400) {
            String msg = methodKey + " return error (400): " + body ;
            log.warn(msg);
            return new HttpNotFoundException(msg);
        }

        String msg = "Unexpected error: " + response.status() + "; " + body;
        return new RuntimeException(msg);
    }


}


