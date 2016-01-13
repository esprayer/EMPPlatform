package esyt.framework.persistence.qxgl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import esyt.framework.persistence.qxgl.beans.SYSMenu;
import esyt.framework.persistence.qxgl.beans.SYSUser;
import esyt.framework.persistence.qxgl.beans.SYSYwxt;

@Repository
public interface SYSSecurityMapper extends BaseMapper{
	public List<SYSYwxt> queryUserYwxt(SYSUser user);
	public List<SYSYwxt> queryUserYwxt(String userId);

	public List<SYSMenu> queryUserMenuOfApp(@Param("USER_ID") String userId,@Param("APP_ID") String APP_ID);
	public List queryUsersGnqx(String user,String ywxt);
	public boolean isHaveYwxtQx();
	public boolean isHaveMenuQx();
	public boolean isHaveGnqx();
}
