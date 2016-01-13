package esyt.framework.service.qxgl;

import java.util.List;

import esyt.framework.persistence.qxgl.beans.SYSMenu;
import esyt.framework.persistence.qxgl.beans.SYSUser;
import esyt.framework.persistence.qxgl.beans.SYSYwxt;

public interface SYSSecurityMgr {
	public List<SYSYwxt> queryUserYwxt(SYSUser user);
	public List<SYSYwxt> queryUserYwxt(String userId);

	public List<SYSMenu> queryUserMenu(String user,String ywxt);
	public List queryUsersGnqx(String user,String ywxt);
	public boolean isHaveYwxtQx();
	public boolean isHaveMenuQx();
	public boolean isHaveGnqx();
}
