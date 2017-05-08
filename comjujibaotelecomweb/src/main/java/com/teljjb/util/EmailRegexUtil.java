package com.teljjb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dezhonger on 2017/5/8.
 */
public final class EmailRegexUtil {
    public static void main(String[] args) {
        String maile = "111@163.com";
        System.out.println(checkEmaile(maile));
    }

    /**
     * 正则表达式校验邮箱
     * @param emaile 待匹配的邮箱
     * @return 匹配成功返回true 否则返回false;
     */
    public static boolean checkEmaile(String emaile){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emaile);
        //进行正则匹配
        return m.matches();
    }
}
