package com.teljjb.response;


import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String msg = "";

	private Integer code = 1;

	
	private T data;
	

	
	
	public BaseResponse() {
		super();
	}
	
	public BaseResponse(T result) {
		super();
		this.data = result;
	}

	public BaseResponse(String message, Integer code, boolean isSuccess, T result) {
		super();
		this.msg = message;
		this.code = code;
		this.data = result;
	}
	
	public String getMessage() {
		return msg;
	}

	public void setMessage(String message) {
		this.msg = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getResult() {
		return data;
	}

	public void setResult(T result) {
		this.data = result;
	}


	@Override
	public String toString() {
		return "ServiceResult [result=" + data + ", message=" + msg
				 + ", code=" + code + "]";
	}

}
