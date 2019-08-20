package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.global.Constant;
import com.ncu.mailmanage.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * IndexController
 *
 * @author wzzfarewell
 * @date 2019/8/17
 **/
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model, HttpSession session){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return "login";
        }
        user.setPassword(StringUtils.EMPTY);
        model.addAttribute(Constant.CURRENT_USER, user);
        session.setAttribute(Constant.CURRENT_USER, user);
        return "index";
    }

    @GetMapping("/deleteMail")
    @RequiresPermissions("deleteMail")
    public String deleteMail(){
        return "delete-mail-success";
    }

    @GetMapping("/sendMail")
    @RequiresPermissions("sendMail")
    public String sendMail(){
        return "send-mail-success";
    }
}
