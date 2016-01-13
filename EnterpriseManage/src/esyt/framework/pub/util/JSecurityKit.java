package esyt.framework.pub.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
/**
 * 权限验证工具类
 * @author Black
 *
 */
public class JSecurityKit {	
	
	/**
	 * 密码验证
	 * @param passwd 用户输入密码
	 * @param dbPasswd 数据库保存的密码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean validMD5Passwd(String passwd, String dbPasswd)
			throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String tempPass =  MD5Util.MD5(passwd);
		return tempPass.equals(dbPasswd);
	}
	
	/**
	 * 获得md5之后的16进制字符
	 * @param passwd 用户输入密码字符
	 * @return String md5加密后密码字符
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getEncryptedPwd(String passwd){
		return MD5Util.MD5(passwd);
	}
}