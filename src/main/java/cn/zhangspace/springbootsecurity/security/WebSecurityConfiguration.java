package cn.zhangspace.springbootsecurity.security;

import cn.zhangspace.springbootsecurity.config.LoginFailHandler;
import cn.zhangspace.springbootsecurity.config.LoginSuccessHandle;
import cn.zhangspace.springbootsecurity.config.MD5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private DataSource dataSource;

    @Autowired
    public WebSecurityConfiguration(DataSource dataSource){
        this.dataSource = dataSource;
    }

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
                .successHandler(new LoginSuccessHandle())
                .failureHandler(new LoginFailHandler())
                .successForwardUrl("/index")
                .failureForwardUrl("/error") // 登录失败后的页面URI
                .permitAll()
                .and().logout().permitAll();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //配置Spring Security使用内存用户存储
//        auth.inMemoryAuthentication()
//                .withUser("zhangspace")
//                .password("123456")
//                .authorities("ROLE_ADMIN") //.authorities("ROLE_ADMIN") == roles("ADMIN")
//                .and()
//                .withUser("zhang")
//                .password("123456")
//                .authorities("ROLE_USER");


//        auth.inMemoryAuthentication().withUser("zhangspace").password("123456").roles("ADMIN")
//                .and().withUser("zhang").password("123456").roles("USER");

        //配置Spring Security使用JDBC存储
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from edu_user where username=?")
                .authoritiesByUsernameQuery("select username,role_user from edu_role_user where username=?")
                .passwordEncoder(new MD5PasswordEncoder());
    }
}
