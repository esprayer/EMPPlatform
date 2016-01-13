package com.framework.sys.exception;

/**
 * <strong>Title : EcointelRuntimeException<br></strong>
 * <strong>Description : </strong>@类注释说明写在此处@<br> 
 * <strong>Create on : 2011-10-31<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointellects.com<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 * <br>
 * <strong>修改历史:</strong><br>
 * 修改人		修改日期		修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public class HLSRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3979301942179432763L;

	public HLSRuntimeException() {
		super();
	}
	
	public HLSRuntimeException(String message) {
		super(message);
	}

	public HLSRuntimeException(Throwable cause) {
		super(cause);
	}

	public HLSRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
