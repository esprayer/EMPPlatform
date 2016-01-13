package com.business.metadata.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.business.MetadataColumnObject;
import com.business.MetadataObject;
import com.business.MetadataServiceMgr;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.persistence.beans.SYS_OBJCOL;
import dwz.persistence.beans.SYS_OBJECT;
import dwz.persistence.mapper.SYS_OBJCOLMapper;
import dwz.persistence.mapper.SYS_OBJECTMapper;

@Transactional(rollbackFor = Exception.class)
@Service("metadataServiceMgr")
public class MetadataServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements MetadataServiceMgr {
	@Autowired
	private SYS_OBJECTMapper sys_objectMapper;
	
	@Autowired
	private SYS_OBJCOLMapper sys_objcolMapper;
	
	@Autowired
	private tableManagerDao tableManagerDao;
	
	public MetadataObject getSYS_OBJECT(String OBJ_ID) {
		SYS_OBJECT po = sys_objectMapper.load(OBJ_ID);
		return new MetadataObject(po);
	}

	public void addSYS_OBJECT(MetadataObject metaObject) {
		Date now = new Date();
		SYS_OBJECT po = metaObject.getSYS_OBJECT();
		po.setF_CHDATE(now);
		po.setF_CRDATE(now);
		po.setF_STAU(0);
		sys_objectMapper.insert(po);
	}

	public void addSYS_OBJCOL(MetadataColumnObject metaObjectCol) {
		Date now = new Date();		
		SYS_OBJCOL objcol = metaObjectCol.getSYS_OBJCOL();
		objcol.setF_CHDATE(now);
		objcol.setF_CRDATE(now);				
		sys_objcolMapper.insert(objcol);
		
		
		List<SYS_OBJCOL> objcols = sys_objcolMapper.findAllObjectColumn(objcol.getOBJ_ID());
		SYS_OBJECT object = sys_objectMapper.load(objcol.getOBJ_ID());
		if(object.getF_STAU() == 1)tableManagerDao.addColumnPro(object, objcol, objcols);
	}
	
	public void updSYS_OBJECT(MetadataObject metaObject) {
		Date now = new Date();
		SYS_OBJECT po = sys_objectMapper.load(metaObject.getOBJ_ID());
		po.setOBJ_MC(metaObject.getOBJ_MC());
		po.setOBJ_DES(metaObject.getOBJ_DES());
		po.setOBJ_TYPE(metaObject.getSYS_OBJECT().getOBJ_TYPE());
		po.setOBJ_APPTYPE(metaObject.getSYS_OBJECT().getOBJ_APPTYPE());
		po.setSYS_ID(metaObject.getSYS_OBJECT().getSYS_ID());
		po.setF_CHDATE(now);
		sys_objectMapper.update(po);
	}

	public void delSYS_OBJECT(String OBJ_ID) {
		sys_objectMapper.delete(OBJ_ID);
	}

	public List<MetadataObject> searchSYS_OBJECT(String orderField, int startIndex, int count) {
		List<MetadataObject> bos = new ArrayList<MetadataObject>();
		List<SYS_OBJECT> pos = sys_objectMapper.findAll();
		for (SYS_OBJECT po : pos) {
			bos.add(new MetadataObject(po));
		}
		return bos;
	}

	public List<MetadataObject> findByObjId(String keywords) {
		List<MetadataObject> bos = new ArrayList<MetadataObject>();
		List<SYS_OBJECT> pos = sys_objectMapper.findByObjId(keywords);
		for (SYS_OBJECT po : pos) {
			bos.add(new MetadataObject(po));
		}
		return bos;
	}

	public MetadataColumnObject getSYS_OBJCOL(String OBJ_ID, String COL_ID) {
		SYS_OBJCOL po = sys_objcolMapper.loadSYS_OBJCOL(OBJ_ID, COL_ID);
		return new MetadataColumnObject(po);
	}

	public void updateSYS_OBJCOL_STAU(String OBJ_ID, String COL_ID, int F_STAU) {
		sys_objcolMapper.updateSYS_OBJCOL_STAU(OBJ_ID, COL_ID, F_STAU);
	}

	public void updSYS_OBJCOL(MetadataColumnObject metaObject) {
		Date now = new Date();
		SYS_OBJCOL objcol = sys_objcolMapper.loadSYS_OBJCOL(metaObject.getOBJ_ID(), metaObject.getCOL_ID());		
		SYS_OBJECT object = sys_objectMapper.load(metaObject.getOBJ_ID());
		objcol.setCOL_MC(metaObject.getCOL_MC());
		objcol.setCOL_DES(metaObject.getCOL_DES());
		objcol.setCOL_TYPE(metaObject.getCOL_TYPE());
		objcol.setCOL_LEN(metaObject.getCOL_LEN());
		objcol.setCOL_ISKEY(metaObject.getCOL_ISKEY());
		objcol.setCOL_ISNULL(metaObject.getCOL_ISNULL());
		objcol.setCOL_VISIBLE(metaObject.getCOL_VISIBLE());
		objcol.setCOL_EDIT(metaObject.getCOL_EDIT());
		objcol.setCOL_EDITABLE(metaObject.getCOL_EDITABLE());
		objcol.setCOL_VIEW(metaObject.getCOL_VIEW());
		objcol.setCOL_DEFAULT(metaObject.getCOL_DEFAULT());
		objcol.setCOL_ISFKEY(metaObject.getCOL_ISFKEY());
		objcol.setCOL_SCALE(metaObject.getCOL_SCALE());
		objcol.setCOL_FOBJ(metaObject.getCOL_FOBJ());
		objcol.setF_CHDATE(now);
		sys_objcolMapper.update(objcol);
		List<SYS_OBJCOL> objcols = sys_objcolMapper.findAllObjectColumn(metaObject.getOBJ_ID());
		if(object.getF_STAU() == 1) tableManagerDao.updateColumnPro(object, objcol, objcols);		
	}

	public List<MetadataColumnObject> findObjectColByObjId(String OBJ_ID, String keywords, String typeKeywords) {
		if(typeKeywords.equals("-1")) typeKeywords = null;
		List<MetadataColumnObject> bos = new ArrayList<MetadataColumnObject>();
		List<SYS_OBJCOL> pos = sys_objcolMapper.findObjectColByObjId(OBJ_ID, keywords, typeKeywords);
		for (SYS_OBJCOL po : pos) {
			bos.add(new MetadataColumnObject(po));
		}
		return bos;
	}

	@Override
	public List<MetadataColumnObject> searchSYS_OBJCOLS(String orderField, String OBJ_ID, int startIndex, int count) {
		List<MetadataColumnObject> bos = new ArrayList<MetadataColumnObject>();
		List<SYS_OBJCOL> pos = sys_objcolMapper.findAllObjectColumn(OBJ_ID);
		for (SYS_OBJCOL po : pos) {
			bos.add(new MetadataColumnObject(po));
		}
		return bos;
	}

	public Object[] createObject(String OBJ_ID) {
		int stau = 0;
		Object[] message = new Object[2];
		List<SYS_OBJCOL> objcols = sys_objcolMapper.findAllObjectColumn(OBJ_ID);
		SYS_OBJECT object = sys_objectMapper.load(OBJ_ID);
		try {
			tableManagerDao.createObject(object, objcols);
			stau = 0;
			message[0] = stau;			
		} catch(Exception ce) {
			ce.printStackTrace();
			stau = -1;
			message[0] = stau;	
			message[1] = ce.toString();
		}
		if(stau == 0) {
			sys_objectMapper.updateF_STAU(OBJ_ID, 1);
		}
		return message;
	}
	
	public Object[] deleteObject(String OBJ_ID) {
		int stau = 0;
		Object[] message = new Object[2];
		try {
			sys_objectMapper.delete(OBJ_ID);
			sys_objcolMapper.deleteObject(OBJ_ID);
			stau = 0;
		} catch(Exception ce) {
			ce.printStackTrace();
			stau = -1;
			message[0] = stau;	
			message[1] = ce.toString();
		}
		return message;
	}
}
