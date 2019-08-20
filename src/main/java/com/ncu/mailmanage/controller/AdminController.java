package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.service.MailService;
import com.ncu.mailmanage.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * AdminController
 * 管理员控制器
 * @author wzzfarewell
 * @date 2019/8/20
 **/
@Controller
public class AdminController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @GetMapping("/lockUser")
    @RequiresPermissions("lockUser")
    public String lockUser(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                           Model model){
        model.addAttribute("page", userService.listNotLocked(pageNum, pageSize));
        return "lock-user";
    }

    @PostMapping("/lockUser/search")
    @RequiresPermissions("lockUser")
    public String searchUser(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                             Model model, String name, String mailAddress){
//        LOG.info("pageNum：\n" + pageNum);
        model.addAttribute("page", userService.searchByCondition(pageNum, pageSize, name, mailAddress));
        return "lock-user :: userList";
    }

    @GetMapping("/lockUser/{id}")
    @RequiresPermissions("lockUser")
    public String lockUser(@PathVariable Long id, RedirectAttributes attributes, HttpServletRequest request){
        int resultCount = userService.lockUserById(id);
        if(resultCount > 0){
            attributes.addFlashAttribute("message", "操作成功！");
        }else{
            attributes.addFlashAttribute("message", "操作失败！");
        }
        return "redirect:" + request.getContextPath() + "/lockUser";
    }


    @GetMapping("/deleteMail")
    @RequiresPermissions("deleteMail")
    public String deleteMail(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                           Model model){
        model.addAttribute("page", mailService.listAll(pageNum, pageSize));
        return "delete-mail";
    }

    @PostMapping("/deleteMail/search")
    @RequiresPermissions("deleteMail")
    public String searchMail(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                             Model model, String title, String sender, String receiver){
        LOG.info("pageNum：\n" + pageNum);
        model.addAttribute("page", mailService.listByCondition(pageNum, pageSize, title, sender, receiver));
        return "delete-mail :: mailList";
    }

    @GetMapping("/deleteMail/{id}")
    @RequiresPermissions("deleteMail")
    public String deleteMail(@PathVariable Long id, RedirectAttributes attributes, HttpServletRequest request){
        int resultCount = mailService.deleteMailById(id);
        if(resultCount > 0){
            attributes.addFlashAttribute("message", "操作成功！");
        }else{
            attributes.addFlashAttribute("message", "操作失败！");
        }
        return "redirect:" + request.getContextPath() + "/deleteMail";
    }

}
