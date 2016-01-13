/**
 * 
 */
package dwz.framework.log;

import dwz.framework.sys.business.BusinessObject;

/**
 * @author peng.shi
 *
 */
public interface LogType extends BusinessObject
{
	String getId();
	
	String getDefine();
	
	void setDefine();
	
	String getName();
	
	void setName();
	
}
