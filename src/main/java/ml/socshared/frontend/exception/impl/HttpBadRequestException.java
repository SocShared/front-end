package ml.socshared.frontend.exception.impl;

import ml.socshared.frontend.exception.AbstractRestHandleableException;
import org.springframework.http.HttpStatus;

public class HttpBadRequestException extends AbstractRestHandleableException {
    public HttpBadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public HttpBadRequestException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public HttpBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}