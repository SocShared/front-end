package ml.socshared.frontend.exception.impl;

import ml.socshared.frontend.exception.AbstractRestHandleableException;
import org.springframework.http.HttpStatus;

public class HttpForbiddenException extends AbstractRestHandleableException {
    public HttpForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }

    public HttpForbiddenException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public HttpForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
