package dwz.framework.exception;

import dwz.framework.sys.exception.ServiceException;

/**
 * <strong>ObjectNotFoundException</strong><br>
 * <br> 
 * <strong>Create on : 2012-2-14<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-epp v1.0.0</strong><br>
 */
public class ObjectNotFoundException extends ServiceException
{
	private static final long serialVersionUID = 2446921528880894125L;

	public ObjectNotFoundException() {
		super();
	}

	public ObjectNotFoundException(String message) {
		super(message);
	}

	public ObjectNotFoundException(Throwable cause) {
		super(cause);
	}

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
