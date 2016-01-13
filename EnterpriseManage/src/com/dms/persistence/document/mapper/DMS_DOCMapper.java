package com.dms.persistence.document.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dms.persistence.document.bean.DMS_DOC;
import com.dms.persistence.folder.bean.DMS_FOLDER;

@Repository
public interface DMS_DOCMapper extends BaseMapper<DMS_DOC,String>{
	// 新建目录
	void  createDirectory(@Param("docObject") DMS_DOC docObject);
	
	// 查找目录下文件
	List<DMS_DOC>  loadFOLDERDOCS(@Param("F_FOLDERID") String F_FOLDERID, @Param("F_DELETE") String F_DELETE);
	
	// 查找文件
	DMS_DOC  loadDoc(@Param("F_DOCID") String F_DOCID);
	
	// 文件加锁、解锁
	void lockedDoc(@Param("doc") DMS_DOC doc);
	
	//列举该用户删除的文件
	List<DMS_DOC> loadDeleteDoc(@Param("F_DEL_USERID") String F_DEL_USERID);
	
	void deleteDoc(@Param("F_DOCID") String F_DOCID, @Param("F_DELETE") String F_DELETE, @Param("F_DEL_USERID") String F_DEL_USERID, @Param("F_DELDATE") java.util.Date F_DELDATE);
	
	void completelyDelete(@Param("F_DOCID") String F_DOCID);
}
