package com.teljjb.util;

/**
 * Created by dezhonger on 2017/5/12.
 */
public final class NicknameUtil {
    public static boolean checkNickname(String nickname) {
        if(StringUtil.isEmpty(nickname) || nickname.length() <= 3) {
            return false;
        }
        return true;
    }
}
