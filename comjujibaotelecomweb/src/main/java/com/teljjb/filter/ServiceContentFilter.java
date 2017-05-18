package com.teljjb.filter;

import com.teljjb.entity.ErrorConstants;
import com.teljjb.entity.ServiceContext;
import com.teljjb.entity.ServiceContextThreadLocal;
import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.util.PropertiesHelp;
import com.teljjb.util.StringUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 *                       
 * @Filename ServiceContentFilter.java
 * @Description 
 * @Version 1.0
 * @Author lingmao
 * @Email 162283610@qq.com
 *       
 * @History
 *<li>Author: lingmao</li>
 *<li>Date: 2016年4月7日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class ServiceContentFilter extends BaseFilter implements Filter {
	
	private static final Logger LOG = Logger.getLogger(ServiceContentFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(	ServletRequest request, ServletResponse response,
							FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String appkey = getHttpHeadOrParam(httpRequest, "appkey");
		String time = getHttpHeadOrParam(httpRequest, "time");
		String platform = getHttpHeadOrParam(httpRequest, "platform");
		String userId = getHttpHeadOrParam(httpRequest, "userId");
		String userSessionId = getHttpHeadOrParam(httpRequest, "userSessionId");
		String password = getHttpHeadOrParam(httpRequest, "password");

		LOG.info("appkey:"+appkey);
		LOG.info("time:"+time);
		LOG.info("platform:"+platform);
		LOG.info("userId:"+userId);
		LOG.info("userSessionId:"+userSessionId);
		LOG.info("password:"+password);




		String clientIp = getClientIp(httpRequest);//获取A10提供的真实客户数据
        System.out.println("userId = " + userId);
        System.out.println("password = " + password);
        System.out.println("platform = " + platform);
        System.out.println("clientIp = " + clientIp);
		if ("iphone".equals(platform)) {
			platform = "ios";
		}
		if (StringUtil.isEmpty(platform)) {
			platform = "wap";
		}
//		String debug = getHttpHeadOrParam(httpRequest, "debug");
//		if (PropertiesHelp.getProperty("IS_MOCK").equals("Y") && "1".equals(debug)) {
//			appkey = "lJNkcCGdhW";//html5真实的
//			userSessionId = "55c47daae0a84cc39b1a7c79cbe8ce57";//70797
//		}
		
		@SuppressWarnings("rawtypes")
		BaseResponse result = new BaseResponse();
		try {
			if (StringUtil.isEmpty(platform)) {
				throw new BusinessException(-9999,
					ErrorConstants.PLATFORM_ERROR[1]);
			}
			
			ServiceContext context = new ServiceContext();
			context.setClientIp(clientIp);
			context.setPlatform(platform);
			context.setUserId(userId);
            context.setPassword(password);
			context.setUserSessionId(userSessionId);
			context.setAppkey(appkey != null ? appkey : "");
			context.setRequestBody(buildHttpBodytMap(httpRequest));
			context.setRequestHeader(buildHttpHeaderMap(httpRequest));
			context.setTime(time);
			context.setReqUrl(httpRequest.getRequestURI());
			
			ServiceContextThreadLocal.set(context);
			
			chain.doFilter(request, response);
			return;
		} catch (BusinessException e) {
			LOG.error("系统出错[ServiceContentFilter.doFilter],message=" + e.getMessage());
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			LOG.error("系统出错[ServiceContentFilter.doFilter]", e);
			result.setCode(-9999);
			result.setMessage(ErrorConstants.SERVER_FAILURE[1]);
		}
		LOG.error(result.toString());
	}
	
	private String getHttpHeadOrParam(HttpServletRequest req, String key) {
		return !StringUtil.isEmpty(req.getHeader(key)) ? req.getHeader(key) : req.getParameter(key);
	}
	
	private String getClientIp(HttpServletRequest request) {
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
		return ip;
	}
	
	@Override
	public void destroy() {
		LOG.info("destroy");
	}
	
}
