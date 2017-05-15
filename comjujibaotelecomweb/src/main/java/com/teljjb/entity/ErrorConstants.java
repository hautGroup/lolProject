package com.teljjb.entity;

/**
 * @Filename ErrorConstants.java
 * @Description
 * @Version 1.0
 * @Author lingmao
 * @Email 162283610@qq.com
 * @History <li>Author: lingmao</li>
 * <li>Date: 2016年4月7日</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class ErrorConstants {

    public static String SUCCESS = "0000";
    //	public static String[]	SERVER_CONNENT_FAILURE					= { "9999", "服务器连接异常！" };
    public static String[] SERVER_FAILURE = {"9998", "Sorry,有点故障,请在重试下！"};
    public static String[] SERVER_SMS_ERROR = {"9996", "短信发送失败"};
    public static String[] SERVER_SIGN_TIME_OUT = {"9995", "签名有效时间超时"};
    public static String[] USER_LOGIN_AGAIN = {"9994", "请重新登录！"};
    public static String[] PLATFORM_ERROR = {"9990", "手机平台不能为空"};
    public static String[] UNKONE_ERROR = {"1001", "未知异常"};
    public static String[] MOBILE_ISNULL = {"2001", "手机号码不能为空"};
    public static String[] MOBILE_FORMAT_ERROR = {"2002", "手机号码格式不正确"};
    public static String[] MOBILE_CARRIER_ERROR = {"2003", "未能识别运营商"};
}
