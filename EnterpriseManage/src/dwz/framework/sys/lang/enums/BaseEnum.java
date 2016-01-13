package dwz.framework.sys.lang.enums;

/**
 * <strong>Title : BaseEnum<br></strong>
 * <strong>Description : </strong>
 * 基础业务枚举类,K为泛型,为Code字段的类型定义<br> 
 * <strong>Create on : 2011-11-2<br></strong>
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
public interface BaseEnum<K> {
	
	/**
	 * 得到存入Db/或者代表的值
	 * @return
	 */
	K getCode();
	
	/**
	 * 描述信息
	 * @return
	 */
	String getDesc();
	
	/**
	 * 显示文本名称
	 * @return
	 */
	String name();
    
}
