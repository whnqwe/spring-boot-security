package cn.zhangspace.springbootsecurity.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
         http.headers().frameOptions().sameOrigin();

        // 授权
        http.authorizeRequests().anyRequest().fullyAuthenticated()
                .and().
                formLogin().usernameParameter("name") // 用户名参数
                .passwordParameter("pwd") // 密码参数
                .loginProcessingUrl("/loginAction") // 登录 Action 的 URI
                .loginPage("/login") // 登录页面 URI
                .failureForwardUrl("/error") // 登录失败后的页面URI
                .permitAll()
                .and().logout().permitAll();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("zhangspace").password("123456").roles("ADMIN")
                .and().withUser("zhang").password("123456").roles("USER");

    }
}
