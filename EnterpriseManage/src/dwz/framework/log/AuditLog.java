/**
 * 
 */
package dwz.framework.log;

import dwz.framework.sys.business.BusinessObject;

/**
 * @author peng.shi
 *
 */
public interface AuditLog extends BusinessObject
{
	String getId();
	
	LogType getLogType();
	
}
