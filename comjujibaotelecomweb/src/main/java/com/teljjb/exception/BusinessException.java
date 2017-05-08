package com.teljjb.exception;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7617925338905953846L;

	/**   
	 * errCode:错误码
	 * @since Ver 1.1   
	 */   
	private Integer code;
	/**   
	 * errParams:错误参数   
	 * @since Ver 1.1   
	 */   
	private Object[] errParams;
	
	/**     
	 * getCode(取错误码)     
	 * @return  
	 */
	public Integer getCode() {
		return code;
	}
	
	public Integer getErrorCode() {
		return code;
	}

	/**   
	  
	 * getErrParams(取错误参数)   
	 * @param  @return    设定文件
	 * @return Object[]    错误参数数组   
	 * @Exception 异常对象   
	 * @since  CodingExample　Ver(编码范例查看) 1.1   
	 * 	  
	*/
	public Object[] getErrParams() {
		return errParams;
	}

	public BusinessException(Integer code) {
		super();
		this.code = code;
	}

	public BusinessException(Integer code, Object[] errParams) {
		super();
		this.code = code;
		this.errParams = errParams;
	}

	public BusinessException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(Integer code, Object[] errParams, String message) {
		super(message);
		this.code = code;
		this.errParams = errParams;
	}
	
	public BusinessException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BusinessException(Integer code, Object[] errParams, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.errParams = errParams;
	}
	
	public BusinessException(Integer code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public BusinessException(Integer code, Object[] errParams, Throwable cause) {
		super(cause);
		this.code = code;
		this.errParams = errParams;
	}

}
