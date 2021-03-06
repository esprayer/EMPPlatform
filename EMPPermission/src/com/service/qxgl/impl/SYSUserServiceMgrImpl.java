package com.service.qxgl.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.util.MD5Util;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;
import com.persistence.qxgl.beans.EditPassCmd;
import com.persistence.qxgl.beans.SYSUser;
import com.persistence.qxgl.mapper.SYSUserMapper;
import com.pub.SYSConstant;
import com.pub.util.JSecurityKit;
import com.service.qxgl.SYSUserServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("SYSUserServiceMgr")
public class SYSUserServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements SYSUserServiceMgr {
	@Autowired
	private SYSUserMapper userMapper;
	
	/**
	 * 用户管理
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Override
	public void addUser(SYSUser userObject) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Date now = new Date();
		userObject.setF_CHDATE(now);
		userObject.setF_CRDATE(now);
		userObject.setUSER_PASS(JSecurityKit.getEncryptedPwd(userObject.getUSER_PASS()));
		userObject.setUSER_DISABLE("1");
		userMapper.insert(userObject);
	}

	@Override
	public void delUser(String USER_ORGID, String USER_ID) {
		userMapper.deleteUser(USER_ORGID, USER_ID);
	}

	@Override
	public SYSUser getUser(String USER_ORGID, String USER_ID) {
		SYSUser po = userMapper.loadUser(USER_ORGID, USER_ID);
		return po;
	}
	
	@Override
	public SYSUser loadUserById(String USER_ID) {
		SYSUser user = userMapper.loadUserById(USER_ID);
		
		return user;
	}
	
	@Override
	public List<SYSUser> checkUserByBm(String USER_ORGID, String USER_DISABLE) {
		List<SYSUser> userList = userMapper.checkUserByBm(USER_ORGID, USER_DISABLE);
		return userList;
	}
	
	public List<SYSUser> checkUserById(String USER_ID) {
		List<SYSUser> userList = userMapper.checkUserById(USER_ID);
		
		return userList;
	}

	@Override
	public List<SYSUser> searchUser(String USER_ORGID, int startIndex, int count) {
		List<SYSUser> bos = new ArrayList<SYSUser>();
		List<SYSUser> pos = userMapper.findUserByOrg(USER_ORGID);
		for (SYSUser po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public List<SYSUser> searchUser(String USER_ORGID, String USER_ID,
			int startIndex, int count) {
		List<SYSUser> bos = new ArrayList<SYSUser>();
		List<SYSUser> pos = userMapper.findUserByOrgAndId(USER_ORGID, USER_ID);
		for (SYSUser po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public void updateUser(SYSUser userObject) {
		Date now = new Date();
		SYSUser user = userMapper.loadUser(userObject.getUSER_ORGID(), userObject.getUSER_ID());
		user.setUSER_NAME(userObject.getUSER_NAME());
		user.setUSER_PHONE1(userObject.getUSER_PHONE1());
		user.setUSER_PHONE2(userObject.getUSER_PHONE2());
		user.setUSER_ADDRESSS1(userObject.getUSER_ADDRESSS1());
		user.setUSER_MAIL1(userObject.getUSER_MAIL1());
		user.setUSER_DISABLE(userObject.getUSER_DISABLE());
		user.setF_CHDATE(now);
		userMapper.update(user);
	}

	@Override
	public int hasMatchUser(String userId, String pwd)throws Exception {
		SYSUser user = userMapper.loadUserById(userId);
		
		if(null==user){
			return SYSConstant.LOGIN_USER_NOTEXISTS;
		}
		
		pwd = null==pwd?"":pwd.trim();
		if(JSecurityKit.validMD5Passwd(userId, pwd, user.getUSER_PASS())){
			return SYSConstant.LOGIN_VALIDATE_TRUE;
		}else{
			return SYSConstant.LOGIN_PWD_WRONG;
		}
		
	}

	@Override
	public void updatePass(EditPassCmd editPassCmd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		userMapper.updatePass(JSecurityKit.getEncryptedPwd(editPassCmd.getNewPassword()),editPassCmd.getUserId());
	}
	
	
}
