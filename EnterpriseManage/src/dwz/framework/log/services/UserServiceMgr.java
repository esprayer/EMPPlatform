/**
 * 
 */
package dwz.framework.log.services;

import java.util.List;

import dwz.framework.exception.ObjectDuplicateException;
import dwz.framework.log.User;
import dwz.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface UserServiceMgr extends BusinessObjectServiceMgr
{
	
	public static final String SERVICE_NAME = "userServiceMgr";
	
	/**
	 * 创建User
	 * @param usename
	 * @param password
	 * @return
	 */
	User createUser(String usename,String password);
	
	/**
	 * 添加User
	 * @param user
	 * @return
	 * @throws ObjectDuplicateException
	 */
	User addUser(User user) throws ObjectDuplicateException;
	
	/**
	 * 更新User
	 * @param user
	 * @return
	 */
	User updateUser(User user);
	
	/**
	 * 获取Users，条件Username like
	 * @param username
	 * @return
	 */
	List<User> getUsers();
	
}
