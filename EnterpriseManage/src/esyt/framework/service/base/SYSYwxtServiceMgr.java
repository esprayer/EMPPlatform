package esyt.framework.service.base;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSRole;
import esyt.framework.persistence.qxgl.beans.SYSYwxt;

public interface SYSYwxtServiceMgr extends BusinessObjectServiceMgr {
	//应用系统管理
	List<SYSYwxt> searchYwxt(int startIndex, int count);
	List<SYSYwxt> searchYwxt(String ywxtBhOrMc, int startIndex, int count);
	SYSYwxt getYwxt(String ywxtBh);
	void addYwxt(SYSYwxt ywxtObj);
	void updateYwxt(SYSYwxt ywxtObj);
	void delYwxt(String ywxtBh);
}
