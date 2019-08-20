package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.global.Constant;
import com.ncu.mailmanage.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
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
    public String index(HttpSession session){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return "login";
        }
        user.setPassword(StringUtils.EMPTY);
        session.setAttribute(Constant.CURRENT_USER, user);
        return "index";
    }

}
