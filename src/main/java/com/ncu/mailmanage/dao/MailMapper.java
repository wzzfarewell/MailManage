package com.ncu.mailmanage.dao;

import com.ncu.mailmanage.pojo.Mail;

public interface MailMapper {
    int deleteByPrimaryKey(Long mailId);

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(Long mailId);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKeyWithBLOBs(Mail record);

    int updateByPrimaryKey(Mail record);
}