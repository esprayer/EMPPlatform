package esyt.framework.pub;

public final class SYSConstant {
	//--------------------------------------------------------
	//   登陆常量
	//--------------------------------------------------------
	public static final int LOGIN_USER_NOTEXISTS	= 0X00001;   //用户不存在
	public static final int LOGIN_PWD_WRONG		= 0X00002;	 //密码错误
	public static final int LOGIN_VALIDATE_TRUE	= 0X00003;   //用户验证通过
	
	//---------------------------------------------------
    //        用户管理常量
	//---------------------------------------------------
	public static final String USER_TYPE_SUPERADMIN = "9";//超级管理员
	public static final String USER_TYPE_ADMIN = "1";//普通管理员
	
	//---------------------------------------------------
    //        系统标识
	//---------------------------------------------------
	public static final String SYS_TYPE_DMS = "DMS";//文档管理系统
	public static final String SYS_TYPE_MRP = "MRP";//物料管理系统
	public static final String SYS_TYPE_UPM = "UPM";//用户管理系统
	public static final String SYS_TYPE_CMS = "CMS";//合同管理系统
}
