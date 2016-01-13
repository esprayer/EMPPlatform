package com.pub.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.efounder.pub.util.MD5Tool;
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
	public static boolean validMD5Passwd(String passwd, String dbPasswd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String tempPass =  MD5Util.MD5(passwd);
    	String encryptpass = MD5Tool.getDefault().getMD5ofStr(passwd);
		return tempPass.equals(dbPasswd) || tempPass.equals(encryptpass);
	}
	
	/**
	 * 密码验证
	 * @param passwd 用户输入密码
	 * @param dbPasswd 数据库保存的密码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean validMD5Passwd(String userId, String passwd, String dbPasswd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String tempPass =  MD5Util.MD5(passwd);
    	String encryptpass = MD5Tool.getDefault().getMD5ofStr(passwd);
    	String encryptpass2 = MD5Tool.getDefault().getMD5ofStr(userId + passwd);
    	
		return dbPasswd.equals(tempPass) || dbPasswd.equals(encryptpass) || dbPasswd.equals(encryptpass2);
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