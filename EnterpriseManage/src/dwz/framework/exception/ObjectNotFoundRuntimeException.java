package dwz.framework.exception;

import dwz.framework.sys.exception.ServiceRuntimeException;

/**
 * <strong>ObjectNotFoundRuntimeException</strong><br>
 * <br>
 * <strong>Create on : 2012-2-22<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-epp v1.0.0</strong><br>
 */
public class ObjectNotFoundRuntimeException extends ServiceRuntimeException
{
	/**
	 * <code>serialVersionUID</code>-注释
	 */
	private static final long serialVersionUID = 5903654929136228760L;

	public ObjectNotFoundRuntimeException() {
		super();
	}

	public ObjectNotFoundRuntimeException(String message) {
		super(message);
	}

	public ObjectNotFoundRuntimeException(Throwable cause) {
		super(cause);
	}

	public ObjectNotFoundRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
