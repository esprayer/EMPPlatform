/**
 * 
 */
package dwz.framework.log.enums;

import dwz.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum LogLevel implements BaseEnum<Integer>
{
	INFO("信息",0),ERROR("错误",1);
	
	private String desc;
	private int code;
	
	private LogLevel(String desc,int code)
	{
		this.desc = desc;
		this.code = code;
	}

	@Override
	public Integer getCode()
	{
		return this.code;
	}

	@Override
	public String getDesc()
	{
		return this.desc;
	}
	
}
