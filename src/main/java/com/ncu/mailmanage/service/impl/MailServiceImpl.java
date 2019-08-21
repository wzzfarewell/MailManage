package com.ncu.mailmanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncu.mailmanage.dao.MailMapper;
import com.ncu.mailmanage.dao.UserMapper;
import com.ncu.mailmanage.pojo.Mail;
import com.ncu.mailmanage.service.MailService;
import com.ncu.mailmanage.vo.MailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MailServiceImpl
 *
 * @author wzzfarewell
 * @date 2019/8/20
 **/
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private MailMapper mailMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteMailById(Long id) {
        return mailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<MailVo> listAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MailVo> mailVos = mailMapper.listAll();
        return new PageInfo<>(mailVos);
    }

    @Override
    public PageInfo<MailVo> listByCondition(int pageNum, int pageSize, String title, String sender, String receiver) {
        PageHelper.startPage(pageNum, pageSize);
        List<MailVo> mailVos;
        if(StringUtils.isNotBlank(title) && StringUtils.isNotBlank(sender) && StringUtils.isNotBlank(receiver)){
            mailVos = mailMapper.listByCondition( "%" + title + "%", sender, receiver);
        }else if(StringUtils.isNotBlank(title) && StringUtils.isBlank(sender) && StringUtils.isNotBlank(receiver)){
            mailVos = mailMapper.listByCondition( "%" + title + "%", null, receiver);
        }else if(StringUtils.isNotBlank(title) && StringUtils.isNotBlank(sender) && StringUtils.isBlank(receiver)){
            mailVos = mailMapper.listByCondition( "%" + title + "%", sender, null);
        }else if(StringUtils.isBlank(title) && StringUtils.isNotBlank(sender) && StringUtils.isNotBlank(receiver)){
            mailVos = mailMapper.listByCondition( null, sender, receiver);
        }else if(StringUtils.isNotBlank(title) && StringUtils.isBlank(sender) && StringUtils.isBlank(receiver)){
            mailVos = mailMapper.listByCondition( "%" + title + "%", null, null);
        }else if(StringUtils.isBlank(title) && StringUtils.isBlank(sender) && StringUtils.isNotBlank(receiver)){
            mailVos = mailMapper.listByCondition( null, null, receiver);
        }else if(StringUtils.isBlank(title) && StringUtils.isNotBlank(sender) && StringUtils.isBlank(receiver)){
            mailVos = mailMapper.listByCondition( null, sender, null);
        }else{
            mailVos = mailMapper.listByCondition( null, null, null);
        }
        return new PageInfo<>(mailVos);
    }

    @Override
    public int setMail(MailVo mailVo) {
        int result=0;
        Mail mail=new Mail();
        mail.setTitle(mailVo.getTitle());
        mail.setBody(mailVo.getBody());
        mail.setSendTime(mailVo.getSendTime());
        result=mailMapper.insertSelective(mail);

        System.out.println(mailVo.getSender());
        Long mailId=mail.getMailId();
        Long senderId=userMapper.findByUsername(mailVo.getSender()).getUserId();
        result+=mailMapper.insertSendMail(senderId,mailId);

        Long receiverId=userMapper.findByUsername(mailVo.getReceiver()).getUserId();
        result+=mailMapper.insertSendMail(receiverId,mailId);
        return result;
    }
}
