package cn.zhangspace.springbootsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class LoginSuccessHandle   extends SimpleUrlAuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Collection collection =  authentication.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(collection);
        for(GrantedAuthority grantedAuthority:grantedAuthorities){
          if(grantedAuthority.getAuthority().equals("admin")){
              getRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse, "/admin");
          }
        }


    }
}
