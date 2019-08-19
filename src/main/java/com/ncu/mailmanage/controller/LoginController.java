package com.ncu.mailmanage.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * LoginController
 *
 * @author wzzfarewell
 * @date 2019/8/17
 **/
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        if(SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:index";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(String name, String password, Model model){
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try{
            user.login(token);
            return "redirect:index";
        }catch (UnknownAccountException | IncorrectCredentialsException e){
            model.addAttribute("message", "账户或密码错误！");
        }catch (DisabledAccountException e){
            model.addAttribute("message", "账户已被锁定！");
        }catch (Throwable e){
            model.addAttribute("message", "未知错误！");
        }
        return "login";
    }

}
