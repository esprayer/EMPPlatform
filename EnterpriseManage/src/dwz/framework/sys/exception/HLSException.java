package dwz.framework.sys.exception;

/**
 * <strong>Title : EcointelException<br></strong>
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
public class HLSException extends Exception {

	private static final long serialVersionUID = 6063007064094713117L;
	
	public HLSException() {
		super();
	}
	public HLSException(String message) {
		super(message);
	}

	public HLSException(Throwable cause) {
		super(cause);
	}

	public HLSException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
