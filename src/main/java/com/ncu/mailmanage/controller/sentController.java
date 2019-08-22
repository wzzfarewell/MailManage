package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.global.Constant;
import com.ncu.mailmanage.pojo.User;
import com.ncu.mailmanage.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/sent")
public class sentController {

    @Autowired
    private MailService mailService;

    private static final Logger LOG = LoggerFactory.getLogger(sentController.class);

    @GetMapping("")
    public String sent(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                       HttpSession session,
                       Model model){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        model.addAttribute("sentMailPage", mailService.listSentMail(user.getName(),pageNum, pageSize));
        return "sent";
    }

    @PostMapping("/deleteSentMail")
    @ResponseBody
    public void deleteSentMail(String[] list){
        for (int i=0;i<list.length;i++) {
            System.out.println(list[i]);
        }

        return ;
    }

}
