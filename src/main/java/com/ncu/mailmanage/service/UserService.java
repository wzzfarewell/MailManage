package com.ncu.mailmanage.service;

import com.github.pagehelper.PageInfo;
import com.ncu.mailmanage.global.ServerResponse;
import com.ncu.mailmanage.pojo.User;

import java.util.List;

/**
 * UserService
 *
 * @author wzzfarewell
 * @date 2019/8/17
 **/
public interface UserService {

    User findByUsername(String username);

    List<String> findPermissionsByUserId(Long userId);

    String findIntroductionByUserId(Long userId);

    int updatePassword(User user);

    ServerResponse register(User user);

    PageInfo<User> listNotLocked(int pageNum, int pageSize);

    int lockUserById(Long userId);

    PageInfo<User> searchByCondition(int pageNum, int pageSize, String name, String mailAddress);

    ServerResponse updateByUserId(User user);

    List<User> listContacts(Long userId);
}
