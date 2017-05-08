package com.teljjb.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


public class IpUtil {
	public static String getIp(HttpServletRequest request) {
		// remoteIp
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
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
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

		return ip;
	}
}
