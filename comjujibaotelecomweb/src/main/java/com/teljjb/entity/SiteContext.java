package com.teljjb.entity;

import com.teljjb.util.ControllerUtil;
import com.teljjb.util.PassportContext;
import com.teljjb.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Filename SiteContext.java
 * @Description m站上下文. m站很多地方使用cookie作为临时数据控制网站的一些功能，但是随着系统Cookie数据的增多，越来越难以管理。
 * SiteContext的主要目的即对关系到“网站功能”方面的cookie进行统一的管理
 * @History
 */
public class SiteContext {

    public static final String COOKIE_PAY_METHODS = "pay_methods";        //cookie记录限定的支付方式

    private HttpServletRequest request;
    private HttpServletResponse response;

    private PassportContext passport;                                    //登录用户信息


    private String payMethods;                                //指定支付方式

    private String platform;                                //平台


    private String remoteIp;                                    //远程用户ID

    private String browser;                                    //浏览器类型

    private String referer;
    private Map<String, String> requestHeader;
    private Map<String, String> requestBody;
    private String payPlatform;
    private String payOs;

    public void init(HttpServletRequest req, HttpServletResponse res) {
        this.request = req;
        this.response = res;

        //remoteIp
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip)) {
            ip = request.getHeader("Client_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        if (!StringUtil.isEmpty(ip)) {
            List<String> list = StringUtil.stringToList(ip, ",");
            if (list.size() > 1) {
                ip = list.get(0);
            }
        }
        this.setRemoteIp(ip);


//		//指定支付方式
//		String payMethods = ControllerUtil.getCookieValue(req, COOKIE_PAY_METHODS);
//		if (!StringUtil.isEmpty(payMethods)) {
//			setPayMethods(payMethods, false);
//		}

        //登录状态
        PassportContext passport = ControllerUtil.getPasspotContext(req, res);
        if (passport != null) {
            setPassport(passport, false);
        }


        //wrap信息
        String appPlatform = request.getHeader(ControllerUtil.APP_PLATFORM);
        if (StringUtil.isEmpty(appPlatform)) {
            appPlatform = ControllerUtil.getCookieValue(request, "platform");//从cookie里面去一次platform
        }
        if (!StringUtil.isEmpty(appPlatform)) {
            setPlatform(appPlatform);
        } else if (!StringUtil.isEmpty(ControllerUtil.getCookieValue(request, "passpotPlatform"))) {
            setPlatform(ControllerUtil.getCookieValue(request, "passpotPlatform"));
        } else {
            setPlatform("wap");
        }

//		String refererDomain =  PropertiesHelp.getProperty("referer_domain");
//		List<String> referers = StringUtil.stringToList(refererDomain, ",");
//		String referer = request.getHeader("Referer");
//		if(!StringUtil.isEmpty(referer)){
//			for(String s : referers){
//				if(!referer.contains(s)){
//					setReferer(s);
//				}
//			}
//		}
        String userAgent = request.getHeader("User-Agent");
        String tmpPay = ControllerUtil.getCookieValue(req, "payPlatform");
        //优先从cookie里去取平台
        this.payPlatform = PayPlatformEnums.WAP.getCode();
        this.payOs = PayOsEnums.ANDROID.getCode();
        if (StringUtils.isNotEmpty(tmpPay)) {
            this.payPlatform = tmpPay;
            //默认系统
            if (StringUtils.isNotEmpty(userAgent)) {
                if (userAgent.contains("iphone")) {
                    this.payOs = PayOsEnums.IOS.getCode();
                } else {
                    this.payOs = PayOsEnums.ANDROID.getCode();
                }
            }
        } else {
            if (StringUtils.isNotEmpty(userAgent)) {
                userAgent = userAgent.toLowerCase();
                //platform
                if (userAgent.contains("jujibao")) {
                    this.payPlatform = PayPlatformEnums.APP.getCode();
                } else if (userAgent.contains("micromessenger")) {
                    this.payPlatform = PayPlatformEnums.WECHAT.getCode();
                } else {
                    this.payPlatform = PayPlatformEnums.WAP.getCode();
                }
                //系统
                if (userAgent.contains("iphone")) {
                    this.payOs = PayOsEnums.IOS.getCode();
                } else {
                    this.payOs = PayOsEnums.ANDROID.getCode();
                }
            } else {
                this.payPlatform = PayPlatformEnums.WAP.getCode();
                this.payOs = PayOsEnums.ANDROID.getCode();
            }
        }


        setRequestBody(buildHttpBodytMap(req));


        setRequestHeader(buildHttpHeaderMap(req));
    }


    public String getPlatform() {
        return platform;
    }


    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public PassportContext getPassport() {
        return passport;
    }

    public void setPassport(PassportContext passport, boolean updateCookie) {
        this.passport = passport;
        if (updateCookie) {
            ControllerUtil.addUserCookie(response, passport);//保存cookie
        }
    }


    public String getPayMethods() {
        return payMethods;
    }

    public void setPayMethods(String payMethods, boolean updateCookie) {
        this.payMethods = payMethods;
        if (updateCookie) {
            ControllerUtil.addCookie(response, COOKIE_PAY_METHODS, payMethods, 24 * 60 * 60);
        }
    }

    @SuppressWarnings("rawtypes")
    public Map<String, String> buildHttpHeaderMap(HttpServletRequest httpRequest) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration names = httpRequest.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = httpRequest.getHeader(name);
            map.put(name, value);
        }
        return map;
    }

    @SuppressWarnings("rawtypes")
    public Map<String, String> buildHttpBodytMap(HttpServletRequest httpRequest) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration names = httpRequest.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = httpRequest.getParameter(name);
            map.put(name, value);
        }
        return map;
    }


    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
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


    public String getReferer() {
        return referer;
    }


    public void setReferer(String referer) {
        this.referer = referer;
    }


    public String getPayPlatform() {
        return payPlatform;
    }


    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }


    public String getPayOs() {
        return payOs;
    }


    public void setPayOs(String payOs) {
        this.payOs = payOs;
    }


    @Override
    public String toString() {
        return String
                .format(
                        "SiteContext [request=%s, response=%s, passport=%s,  payMethods=%s, remoteIp=%s, browser=%s]",
                        request, response, passport, payMethods,
                        remoteIp, browser);
    }

}
