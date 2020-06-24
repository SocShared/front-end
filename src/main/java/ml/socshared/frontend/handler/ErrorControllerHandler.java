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
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ErrorControllerHandler {

    private final AuthService authService;

    @ExceptionHandler({HttpForbiddenException.class, HttpNotFoundException.class})
    public String forbidden(AbstractRestHandleableException exception) {
        log.error("{}: {}", exception.getHttpStatus(), exception.getMessage());
        return "404";
    }

    @ExceptionHandler(HttpUnauthorizedException.class)
    public String unauthorized(HttpUnauthorizedException exception, ServletWebRequest webRequest, HttpServletResponse response,
                               @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken,
                               @CookieValue(value = "JWT_RT", defaultValue = "") String refreshToken) {
        log.error("{}: {}", exception.getHttpStatus(), exception.getMessage());
        if (!accessToken.isEmpty() && !refreshToken.isEmpty()) {
            try {
                OAuth2TokenResponse res = authService.getToken(refreshToken);
                Cookie accessTokenCookie = new Cookie("JWT_AT", res.getAccessToken());
                accessTokenCookie.setMaxAge(24 * 60 * 60);
                accessTokenCookie.setSecure(true);
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setPath("/");
                accessTokenCookie.setDomain("socshared.ml");
                response.addCookie(accessTokenCookie);

                Cookie refreshTokenCookie = new Cookie("JWT_RT", res.getRefreshToken());
                refreshTokenCookie.setMaxAge(24 * 60 * 60 * 30);
                refreshTokenCookie.setSecure(true);
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setPath("/");
                refreshTokenCookie.setDomain("socshared.ml");
                response.addCookie(refreshTokenCookie);

                return "redirect:" + webRequest.getContextPath();
            } catch (Exception exc) {
                return "redirect:/exit";
            }
        } else {
            return "redirect:/exit";
        }
    }

    @ExceptionHandler(Exception.class)
    public String otherException(Exception exc) {
        exc.printStackTrace();
        log.error("{}: {}", HttpStatus.INTERNAL_SERVER_ERROR, exc);
        return "500";
    }
}
