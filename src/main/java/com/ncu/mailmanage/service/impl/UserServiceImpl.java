package com.ncu.mailmanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncu.mailmanage.dao.PermissionMapper;
import com.ncu.mailmanage.dao.UserMapper;
import com.ncu.mailmanage.global.ResponseCode;
import com.ncu.mailmanage.global.ServerResponse;
import com.ncu.mailmanage.pojo.User;
import com.ncu.mailmanage.service.UserService;
import com.ncu.mailmanage.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author wzzfarewell
 * @date 2019/8/17
 **/
@Service
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

    @Override
    public ServerResponse register(User user) {
        User user1;
        user1 = userMapper.findByUsername(user.getName());
        if(user1 != null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "此用户名已存在");
        }
        user1  = userMapper.findByMailAddress(user.getMailAddress());
        if(user1 != null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "此邮箱地址已存在");
        }

        user.setPassword(PasswordUtil.getMd5Password(user.getPassword()));
        user.setLocked(false);
        int resultCount = userMapper.insert(user);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("注册成功");
        }
        return ServerResponse.createByErrorMessage("注册失败");
    }

    @Override
    public PageInfo<User> listNotLocked(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.listByNotLocked();
        return new PageInfo<>(users);
    }
}
