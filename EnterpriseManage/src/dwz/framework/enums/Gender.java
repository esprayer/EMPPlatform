package dwz.framework.enums;

import dwz.framework.sys.lang.enums.BaseEnum;

/**
 * <strong>Gender</strong><br>
 * <strong>Create on : 2012-3-11<br></strong>
 * <p><strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong></p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-roomyi v1.0.0</strong><br>
 */
public enum Gender implements BaseEnum<Integer>
{
	Male("男",1),FMale("女",0);
	
	private String desc;
	private Integer code;
	
	private Gender(String desc,Integer code)
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
