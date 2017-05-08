package com.teljjb.entity;
/**
 * 支付操作系统
 * @author baisu
 *
 */
public enum PayOsEnums {
	ANDROID("android","安卓"),IOS("ios","IOS");
	
	private String code;
	private String name;
	
	private PayOsEnums(String code, String name){
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
