package com.teljjb.filter;

import com.teljjb.entity.SiteContext;
import com.teljjb.entity.SiteContextThreadLocal;
import com.teljjb.util.PropertiesHelp;
import com.teljjb.util.StringUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SiteContentFilter implements Filter {
	
	private static final Logger	LOG	= Logger.getLogger(SiteContentFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
																								throws IOException,
            ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getParameter("sendUrl");
		if(!StringUtil.isEmpty(url)&&url.contains(".")&&!url.contains(PropertiesHelp
				.getProperty("cookie.domain"))){
			url = "/index.html";
		}
		req.setAttribute("sendUrl", url);
		
		//初始化
		SiteContext context = new SiteContext();
		context.init(req, res);
		
		SiteContextThreadLocal.set(context);
		res.setHeader("Set-Cookie", "name="+new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date())+"; Secure; HttpOnly");  
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		
	}
	
}
