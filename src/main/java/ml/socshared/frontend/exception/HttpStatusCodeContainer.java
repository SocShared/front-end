package ml.socshared.frontend.exception;

import org.springframework.http.HttpStatus;

public interface HttpStatusCodeContainer {
    HttpStatus getHttpStatus();
}

