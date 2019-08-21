package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.pojo.User;
import com.ncu.mailmanage.service.MailService;
import com.ncu.mailmanage.service.UserService;
import com.ncu.mailmanage.vo.MailVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @GetMapping("/write-mail")
    @RequiresPermissions("sendMail")
    public String WriteMail(HttpSession session,Model model){
        User user=(User) session.getAttribute("user");
        List<User> contactsList = userService.listContacts(user.getUserId());
        model.addAttribute("contactsList",contactsList);
        return "write-mail";
    }

    @RequestMapping("/sentMail")
    @RequiresPermissions("sendMail")
    public String sentMail(MailVo mailVo) {
        mailService.setMail(mailVo);
        return "send-mail-success";
    }






}
