package dwz.framework.enums;

import dwz.framework.sys.lang.enums.BaseEnum;

/**
 * <strong>UserType</strong><br>
 * <strong>Create on : 2012-3-12<br>
 * </strong>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-roomyi v1.0.0</strong><br>
 */
public enum UserType implements BaseEnum<Integer>
{
	Common_Member("会员", 0),Broker("经纪人",1),ConsoleManager("控制台管理员", 2);
	
	private String desc;
	private Integer code;
	
	private UserType(String desc, Integer code)
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
