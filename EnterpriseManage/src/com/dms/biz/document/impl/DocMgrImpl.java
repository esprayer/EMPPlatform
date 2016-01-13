package com.dms.biz.document.impl;

import java.io.File;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etsoft.pub.enums.JDocLogOperateTypeEnum;
import com.etsoft.pub.util.StringFunction;
import com.dms.biz.document.DocServiceMgr;
import com.dms.persistence.docLogger.bean.DMS_DOCLOG;
import com.dms.persistence.docLogger.mapper.DMS_DOCLOGMapper;
import com.dms.persistence.docNote.mapper.DMS_DOCNOTEMapper;
import com.dms.persistence.document.bean.DMS_DOC;
import com.dms.persistence.document.mapper.DMS_DOCMapper;
import com.dms.persistence.version.mapper.DMS_DOCVERSIONMapper;
import com.etsoft.pub.util.FileUtil;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Transactional(rollbackFor = Exception.class)
@Service("DocServiceMgr")
public class DocMgrImpl extends AbstractBusinessObjectServiceMgr implements DocServiceMgr {

	@Autowired
	private DMS_DOCMapper docMapper ;
	
	@Autowired
	private DMS_DOCNOTEMapper docNoteMapper ;
	
	@Autowired
	private DMS_DOCVERSIONMapper docVersionMapper ;
	
	@Autowired
	private DMS_DOCLOGMapper docLogMapper ;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//--------------------------------------------------------------------------------------------------
	//描述:上传文件，并将文件信息插入到数据库中
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public int createDocumennt(DMS_DOC doc) {
		Date now = new Date();
		doc.setF_CHDATE(now);
		doc.setF_CRDATE(now);
		List<String> dataList = loadMaxDocName(doc);
		String data = "";
		String suffix = doc.getF_NAME().substring(doc.getF_NAME().lastIndexOf("."));
		String fileName = doc.getF_NAME().substring(0, doc.getF_NAME().lastIndexOf("."));
		int iIndex = 0;
		
		if(dataList.size() == 1) {
			data = dataList.get(dataList.size() - 1).toString();
			data = fileName + "(1)" + suffix;
			doc.setF_NAME(data);
		} else if(dataList.size() > 1) {
			iIndex = getMaxDocIndex(dataList, fileName);
			iIndex++;
			data = fileName + "(" + iIndex + ")" + suffix;
			doc.setF_NAME(data);
		}		
		doc.setF_TYPE(doc.getF_TYPE().toLowerCase());
		docMapper.insert(doc);
		return 0;
	}

	//--------------------------------------------------------------------------------------------------
	//描述:获取上传了相同的文件的最大序号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public int getMaxDocIndex(List<String> docList, String fileName) {
		String data = "";
		String strIndex = "";
		String temp = "";
		int iIndex = 0;
		int index = 0;
		
		for(int i = 0; i < docList.size(); i++) {
			data = docList.get(i).toString();
			temp = data.substring(fileName.length() + 1);
			if(temp.indexOf(")") > -1) {
				strIndex = temp.substring(0, temp.indexOf(")"));
				try {
					iIndex = Integer.parseInt(strIndex);
					if(iIndex > index) index = iIndex;
				} catch(Exception ce) {
					ce.printStackTrace();
				}
			}			
		}
		return index;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:加载文件，并将文件大小转换
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public DMS_DOC loadDoc(String F_DOCID) {
		long s = 0;
		String size = "";
		BigInteger bigInt;
		DMS_DOC doc = docMapper.loadDoc(F_DOCID);
		
		if(doc == null) return null;
		
		bigInt = doc.getF_SIZE();
		s = bigInt.longValue();
		size = FileUtil.FormetFileSize(s);
		doc.setF_CONSIZE(size);
		return doc;
	}

	//--------------------------------------------------------------------------------------------------
	//描述:加载目录下的文件
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<DMS_DOC> loadFOLDERDOCS(String F_FOLDERID) {
		List<DMS_DOC> docList = docMapper.loadFOLDERDOCS(F_FOLDERID, "0");
		long s = 0;
		String size = "";
		BigInteger bigInt;
		for(int i = 0; i < docList.size(); i++) {
			bigInt = ((DMS_DOC)docList.get(i)).getF_SIZE();
			s = bigInt.longValue();
			size = FileUtil.FormetFileSize(s);
			((DMS_DOC)docList.get(i)).setF_CONSIZE(size);
		}
		return docList;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取是否上传了相同的文件,如果有相同的名字，则加上序号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<String> loadMaxDocName(DMS_DOC doc) {				
		String F_NAME = doc.getF_NAME();
		String F_PATH = doc.getF_FOLDERID();
		final List<String> dataList = new ArrayList<String>();
		String suffix = F_NAME.substring(F_NAME.lastIndexOf("."));
		String fileName = F_NAME.substring(0, F_NAME.lastIndexOf("."));

		String strSql = "select F_NAME from dms_doc where f_name = '" + doc.getF_NAME() 
		              + "' and F_DELETE = '0' and F_FOLDERID = '" + doc.getF_FOLDERID() + "'";

		jdbcTemplate.query(strSql, new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				dataList.add(rs.getString(1));
			}
		});
		
		if(dataList.size() > 0) {
			strSql = "select F_NAME from dms_doc where f_name REGEXP BINARY '" + fileName + "[(0-9)]{0,}" + suffix 
                   + "' and F_FOLDERID = '" + F_PATH + "' and F_DELETE = '0' order by F_NAME asc";
			jdbcTemplate.query(strSql, new Object[]{}, new RowCallbackHandler(){
				public void processRow(ResultSet rs) throws SQLException{
					dataList.add(rs.getString(1));
				}
			});
		}
		
		return dataList;
	}


	//--------------------------------------------------------------------------------------------------
	//描述:文件加锁
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public DMS_DOC lockedDoc(String F_DOCID,SYSUser user) {
		DMS_DOC doc = docMapper.loadDoc(F_DOCID);
		doc.setF_LOCKED("1");
		doc.setF_LOCKED_USERID(user.getUSER_ID());
		doc.setF_LOCKEDDATE(new Date());
		docMapper.lockedDoc(doc);
		return loadDoc(F_DOCID);
	}


	//--------------------------------------------------------------------------------------------------
	//描述:文件解锁
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public DMS_DOC unLockedDoc(String F_DOCID,SYSUser user) {
		long s = 0;
		String size = "";
		BigInteger bigInt;
		DMS_DOC doc = docMapper.loadDoc(F_DOCID);
		bigInt = doc.getF_SIZE();
		s = bigInt.longValue();
		size = FileUtil.FormetFileSize(s);
		doc.setF_CONSIZE(size);
		doc.setF_LOCKED("0");
		doc.setF_LOCKED_USERID("");
		doc.setF_LOCKEDDATE(new Date());
		docMapper.lockedDoc(doc);
		return doc;
	}

	//修改后保存文档信息
	public void upateDoc(DMS_DOC doc) {
		doc.setF_CHDATE(new Date());
		docMapper.update(doc);
	}

	/**
	 * 删除文件
	 */
	public void deleteDoc(List<String> docList, SYSUser user, HttpServletRequest request) {
		DMS_DOC doc;
		for(int i = 0; i < docList.size(); i++) {
			doc = docMapper.loadDoc(docList.get(i));
			if(doc.getF_LOCKED().equals("1")) continue;
			docMapper.deleteDoc(doc.getF_DOCID(), "1", user.getUSER_ID(), new Date());
			deleteLog(doc, user, request);
		}
	}
	
	/**
	 * 彻底删除文件
	 */
	public void completelyDelete(List<String> folderList, List<String> docList, SYSUser user, HttpServletRequest request) {
		File file = null;
		DMS_DOC doc;
		for(int i = 0; i < docList.size(); i++) {
			try {
				doc = docMapper.loadDoc(docList.get(i));
				//删除文件
				docMapper.completelyDelete(docList.get(i));
				//删除日志
//				deleteDocLog(docList.get(i));
				//删除评论
				deleteDocNote(docList.get(i));
				//删除版本
				deleteDocVersion(docList.get(i));
				//删除物理文件
				String servletPath = request.getRealPath("") + "\\upload\\dms";
			    String sourceFileName = servletPath + "\\"  + docList.get(i);
			    file = new File(sourceFileName);
				FileUtil.deleteDir(file);
				
				//添加文件日志信息
				DMS_DOCLOG docLog = new DMS_DOCLOG();
				docLog.setF_DOCID(docList.get(i));
				docLog.setF_OPERATOR(user.getUSER_ID());
				docLog.setF_OPTYPE(JDocLogOperateTypeEnum.COMPLETELYDELETE.getValue());
				docLog.setF_IP(StringFunction.getIpAddr(request));
				docLog.setF_OPID(StringFunction.generateKey());
				docLog.setF_MSG("删除文件：" + doc.getF_NAME());
				docLog.setF_CRDATE(new Date());
				docLogMapper.insert(docLog);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除文档日志
	 * @param F_DOCID
	 */
	private void deleteDocLog(String F_DOCID) {
		docLogMapper.deleteDocLog(F_DOCID);
	}
	
	/**
	 * 删除文档备注
	 * @param F_DOCID
	 */
	private void deleteDocNote(String F_DOCID) {
		docNoteMapper.deleteDocNote(F_DOCID);
	}

	/**
	 * 删除文档版本
	 * @param F_DOCID
	 */
	private void deleteDocVersion(String F_DOCID) {
		docVersionMapper.deleteDocVersion(F_DOCID);
	}
	
	/**
	 * 添加删除日志
	 * @param doc
	 * @param user
	 * @param request
	 */
	private void deleteLog(DMS_DOC doc, SYSUser user, HttpServletRequest request) {
		DMS_DOCLOG docLog = new DMS_DOCLOG();
        docLog.setF_DOCID(doc.getF_DOCID());
        docLog.setF_OPERATOR(user.getUSER_ID());
        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.DELETE.getValue());
        docLog.setF_IP(StringFunction.getIpAddr(request));
        docLog.setF_OPID(StringFunction.generateKey());
        docLog.setF_CRDATE(new Date());
        docLog.setF_MSG("移动文件至回收站：" + doc.getF_NAME());
        docLogMapper.insert(docLog);
	}
	
	/**
	 * 添加还原日志
	 * @param doc
	 * @param user
	 * @param request
	 */
	private void reduceLog(DMS_DOC doc, SYSUser user, HttpServletRequest request) {
		DMS_DOCLOG docLog = new DMS_DOCLOG();
        docLog.setF_DOCID(doc.getF_DOCID());
        docLog.setF_OPERATOR(user.getUSER_ID());
        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.REDUCE.getValue());
        docLog.setF_IP(StringFunction.getIpAddr(request));
        docLog.setF_OPID(StringFunction.generateKey());
        docLog.setF_CRDATE(new Date());
        docLog.setF_MSG("还原文件：" + doc.getF_NAME());
        docLogMapper.insert(docLog);
	}

	//--------------------------------------------------------------------------------------------------
	//描述:加载登陆用户删除目录
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<DMS_DOC> loadDeleteDoc(String F_DEL_USERID) {		
		return docMapper.loadDeleteDoc(F_DEL_USERID);
	}

	/**
	 * 还原文件
	 */
	public void reduceDoc(List<String> docList, SYSUser user, HttpServletRequest request) {
		DMS_DOC doc;
		for(int i = 0; i < docList.size(); i++) {
			doc = docMapper.loadDoc(docList.get(i));
			if(doc.getF_LOCKED().equals("1")) continue;
			docMapper.deleteDoc(doc.getF_DOCID(), "0", null, null);
			reduceLog(doc, user, request);
		}
	}
}
