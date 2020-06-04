package ml.socshared.frontend.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.exception.AbstractRestHandleableException;
import ml.socshared.frontend.exception.impl.HttpForbiddenException;
import ml.socshared.frontend.exception.impl.HttpNotFoundException;
import ml.socshared.frontend.exception.impl.HttpUnauthorizedException;
import ml.socshared.frontend.security.response.OAuth2TokenResponse;
import ml.socshared.frontend.security.service.AuthService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class ErrorControllerHandler {

    @ExceptionHandler({HttpForbiddenException.class, HttpNotFoundException.class})
    public String forbidden(AbstractRestHandleableException exception) {
        log.error("{}: {}", exception.getHttpStatus(), exception.getMessage());
        return "404";
    }

    @ExceptionHandler(HttpUnauthorizedException.class)
    public String unauthorized(HttpUnauthorizedException exception) {
        log.error("{}: {}", exception.getHttpStatus(), exception.getMessage());
        return "landing_page";
    }

    @ExceptionHandler(Exception.class)
    public String otherException(Exception exc) {
        exc.printStackTrace();
        log.error("{}: {}", HttpStatus.INTERNAL_SERVER_ERROR, exc);
        return "500";
    }
}
