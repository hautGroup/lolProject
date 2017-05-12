package com.teljjb.util;

/**
 * Created by dezhonger on 2017/5/12.
 */
public final class PasswordUtil {

    /**
     * 密码强度校验
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        if(StringUtil.isEmpty(password) || password.length() <= 6) {
            return false;
        }
        return true;
    }
}
