package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IndexController
 *
 * @author wzzfarewell
 * @date 2019/8/17
 **/
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/deleteMail")
    @RequiresPermissions("deleteMail")
    public String deleteMail(){
        return "delete-mail";
    }

    @GetMapping("/sendMail")
    @RequiresPermissions("sendMail")
    public String sendMail(){
        return "send-mail";
    }
}
