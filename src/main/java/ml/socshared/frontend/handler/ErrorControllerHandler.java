package ml.socshared.frontend.handler;

import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class ErrorControllerHandler {

    private final AuthService authService;

    @ExceptionHandler({HttpForbiddenException.class, HttpNotFoundException.class})
    public String forbidden() {
        return "404";
    }

    @ExceptionHandler(HttpUnauthorizedException.class)
    public String unauthorized(Model model, HttpServletResponse response,
                               @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken,
                               @CookieValue(name = "JWT_RT", defaultValue = "") String refreshToken) {
        if (!accessToken.isEmpty() && !refreshToken.isEmpty()) {
            response.addCookie(new Cookie("JWT_AT", ""));
            response.addCookie(new Cookie("JWT_RT", ""));
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

            model.addAttribute("isAuthorized", true);
            return "soc_accounts";
        } else {
            model.addAttribute("isAuthorized", false);
            //return "landing_page";
            return "redirect:https://facebook.com";
        }
    }

    @ExceptionHandler(Exception.class)
    public String otherException(Exception exc) {
        exc.printStackTrace();
        return "500";
    }
}
