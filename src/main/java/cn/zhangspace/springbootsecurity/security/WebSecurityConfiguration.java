package cn.zhangspace.springbootsecurity.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        //CSER
        http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository()).requireCsrfProtectionMatcher(
                httpServletRequest -> httpServletRequest.getMethod().equals("POST")
        );

        //CSP
        http.headers().contentSecurityPolicy("script-src https://code.jquery.com/");

        //X-Frames-Options
         http.headers().frameOptions().deny();
         http.headers().frameOptions().sameOrigin();
         http.headers().frameOptions().disable();



    }
}
