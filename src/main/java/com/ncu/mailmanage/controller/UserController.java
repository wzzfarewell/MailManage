package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.pojo.User;
import com.ncu.mailmanage.service.MailService;
import com.ncu.mailmanage.service.UserService;
import com.ncu.mailmanage.utils.UploadUtils;
import com.ncu.mailmanage.vo.MailVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.expression.Dates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/reply-mail/{mailId}")
    @RequiresPermissions("sendMail")
    public String ReplyMail(@PathVariable Long mailId,HttpSession session,Model model){
        User user=(User) session.getAttribute("user");
        List<User> contactsList = userService.listContacts(user.getUserId());
        String sender=mailService.findSenderByMailId(mailId);
        model.addAttribute("contactsList",contactsList);
        model.addAttribute("sender",sender);
        return "write-mail";
    }

    @RequestMapping("/sentMail")
    @RequiresPermissions("sendMail")
    public String sentMail(MailVo mailVo,@RequestParam("file")MultipartFile file,RedirectAttributes attributes){

        // 文件上传
        // 设置文件名称，不能重复，可以使用uuid
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取文件名
        System.out.println(file);
        String oriName = file.getOriginalFilename();
        // 获取图片后缀
        String extName = oriName.substring(oriName.lastIndexOf("."));
        //构建上传路径
        // 存放上传图片的文件夹
        File fileDir = UploadUtils.getImgDirFile();
        try {
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + fileName + extName);
            // 上传图片到 -》 “绝对路径”
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filePath="upload/"+ fileName + extName;
        mailVo.setAttName(oriName);
        mailVo.setDownloadUrl(filePath);
        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(date);
        mailVo.setSendTime(date);
        int result=mailService.sendMail(mailVo);
        if(result > 0){
            attributes.addFlashAttribute("message", "发送邮件成功！");
        }else{
            attributes.addFlashAttribute("message", "发送邮件失败！");
        }
        return "redirect:/index";
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
    public String deleteReceiveMail(@PathVariable Long mailId,RedirectAttributes attributes) {
        int result=mailService.deleteReceiveMail(mailId);
        if(result > 0){
            attributes.addFlashAttribute("message", "删除邮件成功！");
        }else{
            attributes.addFlashAttribute("message", "删除邮件失败！");
        }
        return "redirect:/inbox";
    }
}
