package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.global.Constant;
import com.ncu.mailmanage.pojo.User;
import com.ncu.mailmanage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * IndexController
 *
 * @author sean
 * @date 2019/8/19
 **/
@Controller
@RequestMapping("/self")
public class SelfController {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String self(Model model,HttpSession session){
        User user=(User)session.getAttribute(Constant.CURRENT_USER);
        model.addAttribute("introduction",userService.findIntroductionByUserId(user.getUserId()));
        return "self";
    }

    @PostMapping("/updatePwd")
    public String updatePwd(@RequestParam("oldpwd") String oldpwd, @RequestParam("newpwd1") String newpwd1,HttpSession session){
        User user=(User) session.getAttribute(Constant.CURRENT_USER);
        String check=userService.findByUsername(user.getName()).getPassword();
        if (oldpwd.equals(check)){

        }
        return "self";     //要注销当前账号重新登录
    }
}
