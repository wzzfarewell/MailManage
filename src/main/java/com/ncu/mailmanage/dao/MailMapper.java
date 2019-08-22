package com.ncu.mailmanage.dao;

import com.ncu.mailmanage.pojo.Attachment;
import com.ncu.mailmanage.pojo.Mail;
import com.ncu.mailmanage.vo.MailVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailMapper {
    int deleteByPrimaryKey(Long mailId);

    int insert(Mail record);

    int insertSelective(Mail record);

    int insertSendMail(@Param(value = "userId")Long userId,@Param(value = "mailId")Long mailId);

    int insertReceiveMail(@Param(value = "userId")Long userId,@Param(value = "mailId")Long mailId);

    Mail selectByPrimaryKey(Long mailId);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKeyWithBLOBs(Mail record);

    int updateByPrimaryKey(Mail record);

    List<MailVo> listAll();

    List<MailVo> listByCondition(@Param("title") String title, @Param("sender") String sender, @Param("receiver") String receiver);

    List<MailVo> listByReceiver(Long userId);

    String findSenderByMailId(Long mailId);

    int updateReceiveMailState(@Param("mailId")Long mailId,@Param("stateId")Long stateId);

    int insertAttachmentSelective(Attachment attachment);

    int insertAttachmentMail(@Param("mailId")Long mailId,@Param("attId")Long attId);
}