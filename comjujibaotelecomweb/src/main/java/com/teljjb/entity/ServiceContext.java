package com.teljjb.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Map;

/**
 * @Filename ServiceContext.java
 * @Description
 * @Version 1.0
 * @Author lingmao
 * @Email 162283610@qq.com
 * @History <li>Author: lingmao</li>
 * <li>Date: 2016年4月7日</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class ServiceContext {

    private String password; // 密码

    private String platform;        //平台

    private String time;            //每次客户端请求的时间,yyyyMMddHHmmss

    private String userId;            //用户会话session标示

    private String appkey;            //app的key，用于签名加密

    private String clientIp;        //客户端IP

    private String userSessionId;    //用户会话session标示

    private Map<String, String> requestHeader;

    private Map<String, String> requestBody;

    private String reqUrl;

    private String referer;

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Map<String, String> requestBody) {
        this.requestBody = requestBody;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
