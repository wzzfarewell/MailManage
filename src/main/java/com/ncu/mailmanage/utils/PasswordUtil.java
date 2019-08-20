package com.ncu.mailmanage.utils;

import com.ncu.mailmanage.global.Constant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * PasswordUtil
 *
 * @author wzzfarewell
 * @date 2019/8/20
 **/
public class PasswordUtil {

    public static String getMd5Password(String password){
        Object salt = ByteSource.Util.bytes(Constant.SALT);
        Object result = new SimpleHash(Constant.ALGORITHM_NAME, password, salt, Constant.HASH_ITERATIONS);
        return result.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(getMd5Password("123456"));
//    }

}
