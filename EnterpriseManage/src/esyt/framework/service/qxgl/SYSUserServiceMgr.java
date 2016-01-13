package esyt.framework.service.qxgl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.EditPassCmd;
import esyt.framework.persistence.qxgl.beans.SYSUser;

public interface SYSUserServiceMgr extends BusinessObjectServiceMgr {
	// 用户
	List<SYSUser> searchUser(String USER_ORGID, int startIndex, int count);

	List<SYSUser> searchUser(String USER_ORGID, String USER_ID, int startIndex,int count);
	
	List<SYSUser> checkUserByBm(String USER_ORGID, String USER_DISABLE);
	
	SYSUser getUser(String USER_ORGID, String USER_ID);

	SYSUser loadUserById(String USER_ID);

	List<SYSUser> checkUserById(String USER_ID);
	void addUser(SYSUser hybmObject)throws NoSuchAlgorithmException, UnsupportedEncodingException;

	void updateUser(SYSUser userObject);
	
	void updatePass(EditPassCmd editPassCmd)throws NoSuchAlgorithmException, UnsupportedEncodingException ;

	void delUser(String USER_ORGID, String USER_ID);
	
	int hasMatchUser(String userId,String pwd)throws Exception;
}
