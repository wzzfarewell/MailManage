package com.ncu.mailmanage.dao;

import com.ncu.mailmanage.pojo.Mail;
import com.ncu.mailmanage.vo.MailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MailMapper {
    int deleteByPrimaryKey(Long mailId);

    int insert(Mail record);

    int insertSelective(Mail record);

    int insertSendMail(@Param(value = "userId")Long userId,@Param(value = "maildId")Long maildId);

    int insertReceiveMail(@Param(value = "userId")Long userId,@Param(value = "maildId")Long maildId);

    Mail selectByPrimaryKey(Long mailId);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKeyWithBLOBs(Mail record);

    int updateByPrimaryKey(Mail record);

    List<MailVo> listAll();

    List<MailVo> listByCondition(@Param("title") String title, @Param("sender") String sender, @Param("receiver") String receiver);
}