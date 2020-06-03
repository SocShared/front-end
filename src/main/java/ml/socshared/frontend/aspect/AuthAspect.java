package ml.socshared.frontend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Slf4j
public class AuthAspect {

    @Before("execution(* ml.socshared.frontend.controller.*.*(..))")
    public void checkToken(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

}
