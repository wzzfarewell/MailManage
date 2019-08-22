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
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Dates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(date);
        mailVo.setSendTime(date);
        mailService.setMail(mailVo);
        return "send-mail-success";
    }

    @GetMapping("/inbox")
    @RequiresPermissions("receiveMail")
    public String inbox(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        HttpSession session,
                        Model model){
        User user=(User) session.getAttribute("user");
        model.addAttribute("page", mailService.listByReceiver(user.getUserId(),pageNum, pageSize));
        return "inbox";
    }

    @GetMapping("/checkMail/{mailId}")
    public String checkMail(@PathVariable Long mailId,
                           Model model){
        MailVo mailVo = mailService.checkMail(mailId);
        model.addAttribute("mailVo",mailVo);
        return "check-mail";
    }

    @RequestMapping("/deleteReceiveMail/{mailId}")
    public String deleteReceiveMail(@PathVariable Long mailId) {
        mailService.deleteReceiveMail(mailId);
        return "redirect:/inbox";
    }
}
