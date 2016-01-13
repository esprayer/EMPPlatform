package dwz.framework.enums;

import dwz.framework.sys.lang.enums.BaseEnum;

/**
 * <strong>Position</strong><br>
 * <strong>Create on : 2012-3-12<br></strong>
 * <p><strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong></p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-roomyi v1.0.0</strong><br>
 */
public enum Position implements BaseEnum<Integer>
{
	Top("顶置",0),Pre("前置",1),Back("后置",2),Bottom("尾部",3);
	
	private String desc;
	private Integer code;
	
	private Position(String desc,Integer code)
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
