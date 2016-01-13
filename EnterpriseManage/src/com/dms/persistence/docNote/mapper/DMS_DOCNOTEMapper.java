package com.dms.persistence.docNote.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dms.persistence.docLogger.bean.DMS_DOCLOG;
import com.dms.persistence.docNote.bean.DMS_DOCNOTE;

@Repository
public interface DMS_DOCNOTEMapper extends BaseMapper<DMS_DOCNOTE,String>{
	// 列举目录下所有文档
	List<DMS_DOCNOTE>  DocNoteList(@Param("F_DOCID") String F_DOCID);
	// 删除为文档的评论
	void  deleteDocNote(@Param("F_DOCID") String F_DOCID);
}
