
package com.teljjb.filter;

import com.alibaba.fastjson.JSON;
import com.teljjb.entity.ErrorConstants;
import com.teljjb.entity.ServiceContextThreadLocal;
import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.util.Assert;
import com.teljjb.util.MD5Util;
import com.teljjb.util.PropertiesHelp;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 *                       
 * @Filename SignFilter.java
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
public class SignFilter implements Filter {
	
	private static final Logger			LOG	= Logger.getLogger(SignFilter.class);
	
	private static ApplicationContext applicationContext;
	

	static {
		applicationContext = new FileSystemXmlApplicationContext("classpath*:/spring/spring-*.xml");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//		filterConfig commService = applicationContext.getBean("commService", CommService.class);
	}
	
	@Override
	public void doFilter(	ServletRequest request, ServletResponse response,
							FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (req.getRequestURI().startsWith("/notify/")||req.getRequestURI().startsWith("/life/huafei/")||req.getRequestURI().startsWith("/testCase/")) {
			chain.doFilter(request, response);
			return ;
		}
		@SuppressWarnings("rawtypes")
		BaseResponse result = new BaseResponse();
		try {
			if (PropertiesHelp.getProperty("IS_MOCK").equals("Y")
				&& "1".equals(req.getParameter("debug"))) {
				chain.doFilter(request, response);
				return;
			}
			String appKey = ServiceContextThreadLocal.get().getAppkey();
			String platform = ServiceContextThreadLocal.get().getPlatform();
			Assert.hasText(platform, "手机平台不能为空");
			Assert.hasText(appKey, "appKey不能为空");
			
			//签名时间校验
			//			String time = ServiceContextThreadLocal.get().getTime();
			//			Date requestTime = DateUtil.parse(time, "yyyyMMddHHmmss");
			//			Date currentTime = new Date();
			//			if (requestTime.getTime() > (currentTime.getTime() + 30 * 60 * 1000)
			//				|| requestTime.getTime() < (currentTime.getTime() - 30 * 60 * 1000)) {
			//				throw new BusinessException("签名过期");
			//			}
			
			//签名验证
			String requestSign = requestSign(req);
			//			System.out.println("right sign:========" + MD5Util.getMD5Str(requestSign.toString()));
			if (MD5Util.getMD5Str(requestSign.toString()).equals(req.getParameter("sign"))) {
				chain.doFilter(request, response);
				return;
			}
			
			LOG.info("sign different " + requestSign + "!=" + req.getParameter("sign"));
			
			throw new BusinessException(9999, "签名校验出错");
		} catch (BusinessException e) {
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			LOG.error("系统出错[MapiSign.doFilter],context=" + ServiceContextThreadLocal.get(), e);
//			result.setCode(ErrorConstants.SERVER_FAILURE[0]);
//			result.setMessage(ErrorConstants.SERVER_FAILURE[1]);
			result.setCode(-9999);
			result.setMessage(ErrorConstants.SERVER_FAILURE[1]);
		}
		
		LOG.error("mapi sign,result=" + result + ",context=" + ServiceContextThreadLocal.get());
		//		出错返回信息
		if (res.isCommitted()) {
			LOG.error("mapi sigin is committed....,reqUrl=" + req.getRequestURI());
			return;
		}
		
		res.setContentType("text/json");
		res.setCharacterEncoding("UTF-8");
		PrintWriter pw = res.getWriter();
		pw.write(JSON.toJSONString(result));
	}
	
	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String requestSign(ServletRequest request) {
		// 遍历请求过来的所有参数存储在map中
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				paramMap.put(paramName, paramValue);
			} else {
				paramMap.put(paramName, "");
			}
		}
		
		//排序
		Object[] keys = paramMap.keySet().toArray();
		Arrays.sort(keys);
		
		//组装签名字符串
		StringBuilder sb = new StringBuilder();
		for (Object key : keys) {
			String value = paramMap.get(key);
			if (StringUtils.isEmpty(value) || "sign".equals(key)) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key).append("=").append(paramMap.get(key));
		}
		
		String appKey = ServiceContextThreadLocal.get().getAppkey();
		String platform = ServiceContextThreadLocal.get().getPlatform();
		//		String platform = ServiceContextThreadLocal.get().getRequestHeader().get("platform");//这里的数据最原始,ServiceContent会把iphone转换成IOS。会导致签名失败
//		String secretkey = getSecretkey(platform, appKey);
		
		StringBuilder signStr = new StringBuilder();
//		signStr.append(platform).append("&").append(secretkey);
		if (sb.length() > 0) {
			signStr.append("&").append(sb);
		}
		
		//		System.out.println("signStr======" + signStr.toString());
		//		System.out.println("sign=======" + paramMap.get("sign").toString());
		return signStr.toString();
	}
	
//	private String getSecretkey(String platform, String appkey) {
//		String secretkey = appkey;
//		try {
//			JjbApiKeyResult appKey = commService.getApiKey(platform, appkey);
//			if (appKey != null && appKey.getEnable().equals("Y")) {
//				secretkey = appKey.getSecretkey();
//				return secretkey;
//			}
//			return secretkey;
//		} catch (BusinessException e) {
//			LOG.error("获取 secretkey 异常");
//		}
//		return null;
//
//	}
	
	@Override
	public void destroy() {
	}
	
}
