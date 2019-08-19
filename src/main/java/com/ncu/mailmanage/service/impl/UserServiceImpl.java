package com.ncu.mailmanage.service.impl;

import com.ncu.mailmanage.dao.PermissionMapper;
import com.ncu.mailmanage.dao.UserMapper;
import com.ncu.mailmanage.pojo.User;
import com.ncu.mailmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author wzzfarewell
 * @date 2019/8/17
 **/
@Service()
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PermissionMapper permissionMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PermissionMapper permissionMapper) {
        this.userMapper = userMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<String> findPermissionsByUserId(Long userId) {
        return permissionMapper.findByUserId(userId);
    }
}
