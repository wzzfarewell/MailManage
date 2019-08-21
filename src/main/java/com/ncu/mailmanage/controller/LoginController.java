package com.ncu.mailmanage.controller;

import com.ncu.mailmanage.global.Constant;
import com.ncu.mailmanage.global.ResponseCode;
import com.ncu.mailmanage.global.ServerResponse;
import com.ncu.mailmanage.pojo.User;
import com.ncu.mailmanage.service.UserService;
import com.ncu.mailmanage.utils.ValidateCodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LoginController
 * 登录和注册的控制器
 * @author wzzfarewell
 * @date 2019/8/17
 **/
@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        if(SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:index";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(String name, String password, String valCode, Model model, HttpSession session){
        LOG.info("登录验证码：\n" + valCode);
        if(!valCode.toUpperCase().equals(session.getAttribute(Constant.VALIDATE_CODE))) {
            model.addAttribute("message", "验证码输入错误！");
            return "login";
        }
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try{
            user.login(token);
            return "redirect:index";
        }catch (UnknownAccountException | IncorrectCredentialsException e){
            model.addAttribute("message", "账户或密码错误！");
        }catch (DisabledAccountException e){
            model.addAttribute("message", "账户已被锁定！");
        }catch (Throwable e){
            model.addAttribute("message", "未知错误！");
        }
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/registerSuccess")
    public String registerSuccess(){
        return "register-success";
    }

    @PostMapping("/register")
    @ResponseBody
    public ServerResponse doRegister(@Valid User user, BindingResult bindingResult, String valCode, HttpSession session){
        LOG.info("注册验证码：\n" + valCode);
        StringBuilder res = new StringBuilder();
        // 参数校验
        if(bindingResult.hasErrors()){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                res.append(fieldError.getDefaultMessage()).append(";");
            }
            res.delete(res.lastIndexOf(";"), res.length());
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), res.toString());
        }
        // 验证码输入正确
        if(valCode.toUpperCase().equals(session.getAttribute(Constant.VALIDATE_CODE))){
            return userService.register(user);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "验证码输入错误!");
    }

    @RequestMapping(value = "/val/valCode")
    public void getAuthCode(HttpServletResponse response, HttpSession session)
            throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ValidateCodeUtil validateCodeUtil = new ValidateCodeUtil(100, 30, 4, 60);
        if(session.getAttribute(Constant.VALIDATE_CODE) != null){
            session.removeAttribute(Constant.VALIDATE_CODE);
        }
        validateCodeUtil.write(response.getOutputStream());
        LOG.info("验证码：" + validateCodeUtil.getCode());
        session.setAttribute(Constant.VALIDATE_CODE, validateCodeUtil.getCode());
        response.getOutputStream().flush();
    }


    /**
     * 将前端字符串格式的日期转换为date类型
     * @param binder
     */
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

}
