package com.teljjb.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class HttPClientUtil {
	private static Logger logger = Logger.getLogger(HttPClientUtil.class);


	/**
	 * 模拟post表单请求
	 *
	 * @param http_url
	 *            请求地址
	 * @param parameters
	 *            请求参数
	 * @return 结果
	 */
	public static String doPost(String http_url, Map<String, String> parameters) {
		PostMethod method = null;
		try {
			URI uri = new URI(http_url, true);
			HttpClient client = new HttpClient();
			HostConfiguration cf = new HostConfiguration();
			cf.setHost(uri);
			client.setHostConfiguration(cf);
			client.getParams().setAuthenticationPreemptive(true);
			method = new PostMethod(http_url);
			// 添加参数
			if (parameters != null) {
				for (Map.Entry<String, String> e : parameters.entrySet()) {
					method.addParameter(e.getKey(), e.getValue());
				}
			}
			client.executeMethod(method);
			// 打印服务器返回的状态
			logger.debug("【" + http_url + "请求结果状态】" + method.getStatusLine());
			// 输出返回的信息
			return method.getResponseBodyAsString();
		} catch (IOException e) {
			logger.error("dopost fail:param"+parameters,e);
			e.printStackTrace();
		} finally {
			// 释放连接
			if (method != null)
				method.releaseConnection();
		}
		return null;
	}

	/**
	 * 模拟post xml 请求
	 *
	 * @param http_url
	 *            请求地址
	 * @param xml
	 *            请求xml内容
	 * @return 结果
	 */
	public static String doXmlPost(String http_url, String xml) {
		PostMethod method = null;
		try {
			URI uri = new URI(http_url, true);
			HttpClient client = new HttpClient();
			HostConfiguration cf = new HostConfiguration();
			cf.setHost(uri);
			client.setHostConfiguration(cf);
			client.getParams().setAuthenticationPreemptive(true);
			method = new PostMethod(http_url);
			// 添加参数
			method.setRequestEntity(new StringRequestEntity(xml));
			method.setRequestHeader("Content-Type", "text/xml;charset=UTF-8");
			client.executeMethod(method);
			// 打印服务器返回的状态
			logger.debug("【" + http_url + "请求结果状态】" + method.getStatusLine());
			// 输出返回的信息
			return method.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			if (method != null)
				method.releaseConnection();
		}
		return null;
	}

	/**
	 * @param http_url
	 *            请求地址
	 * @return get请求结果
	 * @throws IOException 
	 */
	public static String doGet(String http_url) throws IOException {
		HttpMethod method;
		try {
			URI uri = new URI(http_url, true);
			HttpClient client = new HttpClient();
			HostConfiguration hcfg = new HostConfiguration();
			hcfg.setHost(uri);
			client.setHostConfiguration(hcfg);
			// 参数验证
			client.getParams().setAuthenticationPreemptive(true);
			// GET请求方式
			method = new GetMethod(http_url);
			client.executeMethod(method);
			return method.getResponseBodyAsString();
		} catch (IOException e) {
			logger.error("HttPClientUtil.doGet fail ",e);
			throw e;
		}
	}

	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		StringBuilder str = new StringBuilder();
		str.append(url);
		HttpGet get = new HttpGet(str.toString());
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}
	private static String paseResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();

		String charset = EntityUtils.getContentCharSet(entity);

		String body = null;
		try {
			body = EntityUtils.toString(entity,"UTF-8");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}
	

	private static String invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}
	
	private static HttpResponse sendRequest(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {
		HttpResponse response = null;

		try {
			httpclient.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param jsonParam
	 *            JSON字符串
	 * @return
	 */
	public static String doPostJson(String http_url, String json) {
		PostMethod method = null;
		String result = "";
		try {
			URI uri = new URI(http_url, true);
			HttpClient client = new HttpClient();
			HostConfiguration cf = new HostConfiguration();
			cf.setHost(uri);
			client.setHostConfiguration(cf);
			client.getParams().setAuthenticationPreemptive(true);
			method = new PostMethod(http_url);
			// 添加参数
			method.setRequestEntity(new StringRequestEntity(json));
			method.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			client.executeMethod(method);
			// 打印服务器返回的状态
			logger.debug("【" + http_url + "请求结果状态】" + method.getStatusLine());
			// 输出返回的信息
			result = method.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			if (method != null)
				method.releaseConnection();
		}
		return result;
	}
	
	
	
	/**
	 * @param http_url
	 *            请求地址
	 * @return get请求结果
	 * @throws IOException 
	 */
	public static InputStream doGetInputStream(String http_url) throws IOException {
		HttpMethod method;
		try {
			URI uri = new URI(http_url, true);
			HttpClient client = new HttpClient();
			HostConfiguration hcfg = new HostConfiguration();
			hcfg.setHost(uri);
			client.setHostConfiguration(hcfg);
			// 参数验证
			client.getParams().setAuthenticationPreemptive(true);
			// GET请求方式
			method = new GetMethod(http_url);
			client.executeMethod(method);
			return method.getResponseBodyAsStream();
		} catch (IOException e) {
			logger.error("HttPClientUtil.doGet fail ",e);
			throw e;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		String url = "http://xyzxpt.cnha10086.com:8088/API/Interface91/SubmitResult/";
		Map<String,String> param = new HashMap<>();
		Map<String,String> test = new HashMap<>();
		test.put("ActivityId", "123");
		test.put("PeriodNumber", "1");
		test.put("ActivityName", "测试");
		test.put("OrderNO", "12324444");
		test.put("PhoneNumber", "13989474396");
		test.put("BuyCount", "5");
		test.put("isWin", "0");
		test.put("Prize", "测试");
		System.out.println(JSONObject.toJSONString(test));
		System.out.println(DESUtils.encrypt2(JSONObject.toJSONString(test), "*(DE$)gj"));
		param.put("ResultValue", DESUtils.encrypt2(JSONObject.toJSONString(test), "*(DE$)gj"));
		System.out.println(doPost(url, param));
	}
}
