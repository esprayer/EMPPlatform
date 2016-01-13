package dwz.framework.exception;

import dwz.framework.sys.exception.ServiceException;

/**
 * <strong>ObjectHasSubObjectException</strong><br>
 * <br> 
 * <strong>Create on : 2012-2-8<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-epp v1.0.0</strong><br>
 */
public class ObjectHasSubObjectException extends ServiceException {
	
	private static final long serialVersionUID = 2087539659791955385L;
	
	public ObjectHasSubObjectException() {
		super();
	}
	
	public ObjectHasSubObjectException(String message) {
		super(message);
	}

	public ObjectHasSubObjectException(Throwable cause) {
		super(cause);
	}

	public ObjectHasSubObjectException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
