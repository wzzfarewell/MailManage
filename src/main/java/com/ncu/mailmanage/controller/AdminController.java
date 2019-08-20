package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AdminController
 * 管理员控制器
 * @author wzzfarewell
 * @date 2019/8/20
 **/
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/lockUser")
    public String lockUser(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
                           Model model){
        model.addAttribute("page", userService.listNotLocked(pageNum, pageSize));
        return "lock-user";
    }


}
