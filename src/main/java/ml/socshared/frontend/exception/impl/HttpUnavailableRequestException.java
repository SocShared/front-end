package ml.socshared.frontend.exception.impl;

import ml.socshared.frontend.exception.AbstractRestHandleableException;
import ml.socshared.frontend.exception.SocsharedErrors;
import org.springframework.http.HttpStatus;

public class HttpUnavailableRequestException extends AbstractRestHandleableException {
    public HttpUnavailableRequestException() {
        super(SocsharedErrors.UNAVAILABLE_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpUnavailableRequestException(SocsharedErrors errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
