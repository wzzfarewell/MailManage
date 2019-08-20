package com.ncu.mailmanage.dao;

import com.ncu.mailmanage.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);

    User findByUsername(String username);

    String findIntroductionByUserId(Long userId);
}