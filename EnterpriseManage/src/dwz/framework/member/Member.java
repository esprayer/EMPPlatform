/**
 * 
 */
package dwz.framework.member;

import java.util.Date;

import dwz.framework.enums.YesNo;
import dwz.framework.sys.business.BusinessObject;

/**
 * @author peng.shi
 */
public interface Member extends BusinessObject
{
	/* (non-Javadoc)
	 * @see dwz.framework.sys.business.BusinessObject#getId()
	 */
	public Long getId();
	
	/**
	 * 获取用户名
	 * @return
	 */
	public String getUsername();

	/**
	 * 获取密码
	 * @return
	 */
	public String getPassword();

	/**
	 * 设定密码
	 * @param pwd
	 */
	public void setPassword(String pwd);

	/**
	 * 获取邮件
	 * @return
	 */
	public String getEmail();

	/**
	 * 设定邮件
	 * @param email
	 */
	public void setEmail(String email);

	/**
	 * 获取firstname
	 * @return
	 */
	public String getFirstName();

	/**
	 * 设定first name
	 * @param firstName
	 */
	public void setFirstName(String firstName);

	/**
	 * 获取 surname
	 * @return
	 */
	public String getSurName();

	/**
	 * 设定surname
	 * @param surName
	 */
	public void setSurName(String surName);

	/**
	 * 获取 inserttime
	 * @return
	 */
	public Date getInsertTime();
	
	/**
	 * 获取Updatetime
	 * @return
	 */
	public Date getUpdateTime();
	
	/**
	 * 获取Confired状态
	 * @return
	 */
	public YesNo getConfirmed();

}
