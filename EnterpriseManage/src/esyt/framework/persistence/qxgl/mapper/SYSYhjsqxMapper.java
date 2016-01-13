package esyt.framework.persistence.qxgl.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import esyt.framework.persistence.qxgl.beans.BaseUSRole;
import esyt.framework.persistence.qxgl.beans.SYSRole;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Repository
public interface SYSYhjsqxMapper extends BaseMapper{
	List<SYSRole> queryUserRole(String user_id);
	List<SYSRole> queryWfpRole(String user_id);
	
	int isExistsYwxtQx(String qxid);
	
	List<SYSUser> queryRoleUser(@Param("USER_ORGID") String org_id,@Param("ROLE_ID") String role_id);
	List<SYSUser> queryWfpUser(@Param("USER_ORGID") String org_id,@Param("ROLE_ID") String role_id);
	
	List<Map> queryMenuTree(String app_id);
	
	void saveUserRoles(BaseUSRole obj);
	void deleteUserRoles(String user_id);
	
	void deleteUserVsRole(BaseUSRole obj);
	
	void saveGnqx(Map map);
	void delGnqxById(@Param("F_ID") String f_id);
	
	void saveRoleGnqx(Map map);
	void delRoleGnqx(@Param("F_ID") String f_id);
	
	List<Map> querySubMenu(@Param("YWXT_ID") String ywxtId,@Param("USER_ID") String user_id,@Param("JS") int js, @Param("MENU_BH") String menu_bh);
	List<Map> querySubMenuByRole(@Param("YWXT_ID") String ywxtId,@Param("ROLE_ID") String role_id,@Param("JS") int js, @Param("MENU_BH") String menu_bh);

}
