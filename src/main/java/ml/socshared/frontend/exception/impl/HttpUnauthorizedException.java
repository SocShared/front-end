package ml.socshared.frontend.exception.impl;

import ml.socshared.frontend.exception.AbstractRestHandleableException;
import org.springframework.http.HttpStatus;

public class HttpUnauthorizedException extends AbstractRestHandleableException {
    public HttpUnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public HttpUnauthorizedException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public HttpUnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
