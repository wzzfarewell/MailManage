package com.ncu.mailmanage.service;

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
}
