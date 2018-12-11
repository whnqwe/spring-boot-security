package cn.zhangspace.springbootsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
