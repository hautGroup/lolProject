package com.teljjb.entity;

/**
 * 支付平台
 * @author baisu
 *
 */
public enum PayPlatformEnums {
	WAP("wap","wap"),WECHAT("wechat","微信"),APP("app","APP");
	private String code;
	private String name;
	
	private PayPlatformEnums(String code, String name){
		this.code=code;
		this.name=name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
