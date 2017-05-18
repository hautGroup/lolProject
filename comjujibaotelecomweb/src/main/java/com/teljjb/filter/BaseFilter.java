package com.teljjb.filter;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *                       
 * @Filename BaseFilter.java
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
public class BaseFilter {
	
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
		//		map.put("reqUrl", httpRequest.getRequestURI());
		return map;
	}
	
}
