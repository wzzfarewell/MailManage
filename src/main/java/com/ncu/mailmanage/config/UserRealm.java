package com.ncu.mailmanage.config;

import com.ncu.mailmanage.global.Constant;
import com.ncu.mailmanage.pojo.User;
import com.ncu.mailmanage.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * UserRealm
 * 用户认证+授权
 * @author wzzfarewell
 * @date 2019/8/16
 **/
public class UserRealm extends AuthorizingRealm {
    private static final Logger LOG = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection 实体信息
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOG.info("=================用户授权================");
        User user = (User) principalCollection.getPrimaryPrincipal();
        LOG.info(user.toString());
        List<String> permissions = userService.findPermissionsByUserId(user.getUserId());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 认证处理
     * @param authenticationToken 终端认证用户信息
     * @return 封装了用户信息的AuthenticationInfo实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOG.info("=================登录认证中================");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByUsername(token.getUsername());
        if(user == null){
            return null;
        }
        if(user.getLocked()){
            throw new DisabledAccountException();
        }
        return new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(),
                ByteSource.Util.bytes(Constant.SALT), getName());
    }
}
