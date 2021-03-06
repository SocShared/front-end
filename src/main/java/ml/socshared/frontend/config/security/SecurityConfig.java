package ml.socshared.frontend.config.security;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.config.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static ml.socshared.frontend.config.Constants.HOME_PROFILE;

@Configuration
@EnableWebSecurity
@Profile({Constants.DEV_PROFILE, Constants.PROD_PROFILE, Constants.LOCAL_PROFILE, HOME_PROFILE})
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Run LOCAL/TEST Security Configuration");
        http.
                csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
    }
}
