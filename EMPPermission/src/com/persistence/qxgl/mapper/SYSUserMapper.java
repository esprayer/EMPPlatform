package com.persistence.qxgl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dal.BaseMapper;
import com.persistence.qxgl.beans.SYSUser;

@Repository
public interface SYSUserMapper extends BaseMapper<SYSUser,String>{
	// 查询
	List<SYSUser> findUserByOrg(@Param("USER_ORGID") String USER_ORGID);
	
	// 查询
	List<SYSUser> findUserByOrgAndId(@Param("USER_ORGID") String USER_ORGID, @Param("USER_ID") String USER_ID);
	
	// 查询
	SYSUser loadUser(@Param("USER_ORGID") String USER_ORGID, @Param("USER_ID") String USER_ID);
	
	// 查询
	SYSUser loadUserById(@Param("USER_ID") String USER_ID);
	
	List<SYSUser> checkUserById(@Param("USER_ID") String USER_ID);
	
	List<SYSUser> checkUserByBm(@Param("USER_ORGID") String USER_ORGID, @Param("USER_DISABLE") String USER_DISABLE);
	
	void updatePass(@Param("USER_PASS") String newPass, @Param("USER_ID") String USER_ID);

	
	// 删除
	void deleteUser(@Param("USER_ORGID") String USER_ORGID, @Param("USER_ID") String USER_ID);
}
