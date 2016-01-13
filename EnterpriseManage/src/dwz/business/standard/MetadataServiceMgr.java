package dwz.business.standard;

import java.util.List;
import java.util.Map;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface MetadataServiceMgr extends BusinessObjectServiceMgr {
	MetadataObject getSYS_OBJECT(String OBJ_ID);
	void addSYS_OBJECT(MetadataObject metaObject);
	void updSYS_OBJECT(MetadataObject metaObject);
	void delSYS_OBJECT(String OBJ_ID);
	
	List<MetadataObject> searchSYS_OBJECT(String orderField, int startIndex, int count);
	List<MetadataColumnObject> searchSYS_OBJCOLS(String orderField, String OBJ_ID, int startIndex, int count);
	List<MetadataObject> findByObjId(String keywords);

	MetadataColumnObject getSYS_OBJCOL(String OBJ_ID, String COL_ID);
	void addSYS_OBJCOL(MetadataColumnObject metaObject);
	void updSYS_OBJCOL(MetadataColumnObject metaObject);
	void updateSYS_OBJCOL_STAU(String OBJ_ID, String COL_ID, int F_STAU);
	
	List<MetadataColumnObject> findObjectColByObjId(String OBJ_ID, String keywords, String typeKeywords);
	Object[] createObject(String OBJ_ID);
	Object[] deleteObject(String OBJ_ID);
}
