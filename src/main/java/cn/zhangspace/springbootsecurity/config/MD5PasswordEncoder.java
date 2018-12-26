package cn.zhangspace.springbootsecurity.config;

import cn.zhangspace.springbootsecurity.util.MD5Util;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encodePassword(String rawPass, Object salt) {
        return MD5Util.MD5Encode(rawPass,"UTF-8");
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        if(encPass.equals(MD5Util.MD5Encode(rawPass,"UTF-8"))){
            return true;
        }
        return false;
    }
}
