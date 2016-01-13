package com.dms.biz.document;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.dms.persistence.document.bean.DMS_DOC;
import com.dms.persistence.folder.bean.DMS_FOLDER;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSUser;

public interface DocServiceMgr extends BusinessObjectServiceMgr {
	int createDocumennt(DMS_DOC docObject);
	List<DMS_DOC> loadFOLDERDOCS(String F_FOLDERID);
	DMS_DOC loadDoc(String F_DOCID);	
	DMS_DOC lockedDoc(String F_DOCID,SYSUser user);	
	DMS_DOC unLockedDoc(String F_DOCID,SYSUser user);
	//列举该用户删除的文件
	List<DMS_DOC> loadDeleteDoc(@Param("F_DEL_USERID") String F_DEL_USERID);
	void upateDoc(DMS_DOC doc);	
	void deleteDoc(List<String> docList,SYSUser user, HttpServletRequest request);
	void reduceDoc(List<String> docList,SYSUser user, HttpServletRequest request);
	void completelyDelete(List<String> folderList, List<String> docList,SYSUser user, HttpServletRequest request);
}
