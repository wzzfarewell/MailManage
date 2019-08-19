package com.ncu.mailmanage.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ShiroConfig
 * 对Shiro的配置
 * @author wzzap
 * @date 2019/8/16
 **/
@Configuration
public class ShiroConfig {

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // md5加密4次
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(4);
        return matcher;
    }

//    public static void main(String[] args) {
//        Object salt = ByteSource.Util.bytes("farewell");
//        Object result = new SimpleHash("md5", "123456", salt, 4);
//        System.out.println(result);
//    }

    /**
     * 自定义的Realm
     * @return
     */
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * 安全管理器
     * 注：使用shiro-spring-boot-starter 1.4时，返回类型是SecurityManager会报错，
     * 直接引用shiro-spring则不报错
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager());
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/");
        filterFactoryBean.setUnauthorizedUrl("/403");

        //注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，匹配了就不再继续验证
        //所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了。
        Map<String, String> map = new LinkedHashMap<>();
        // 匿名访问
        map.put("/img/**", "anon");
        map.put("/css/**", "anon");
        map.put("/js/**", "anon");
        map.put("/error/**", "anon");
        map.put("/login", "anon");
        map.put("/register", "anon");
        // 配置退出过滤器,其中的具体的退出代码 Shiro 已经替我们实现了
        map.put("/logout", "logout");
        map.put("/favicon.ico", "anon");
        // 需验证后访问
        map.put("/**", "authc");

        filterFactoryBean.setFilterChainDefinitionMap(map);
        return filterFactoryBean;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
