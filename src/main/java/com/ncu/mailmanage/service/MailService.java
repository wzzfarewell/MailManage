package com.ncu.mailmanage.service;

import com.github.pagehelper.PageInfo;
import com.ncu.mailmanage.vo.MailVo;

/**
 * MailService
 *
 * @author wzzfarewell
 * @date 2019/8/20
 **/
public interface MailService {

    int deleteMailById(Long id);

    PageInfo<MailVo> listAll(int pageNum, int pageSize);

    PageInfo<MailVo> listByCondition(int pageNum, int pageSize, String title, String sender, String receiver);
}
