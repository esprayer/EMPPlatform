package dwz.persistence.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import dwz.persistence.beans.SYS_OBJCOL;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SYS_OBJCOLMapper extends BaseMapper<SYS_OBJCOL,String>{
	// 查询
	SYS_OBJCOL loadSYS_OBJCOL(@Param("OBJ_ID") String OBJ_ID, 
			                  @Param("COL_ID") String COL_ID);
	
	// 查询
	List<SYS_OBJCOL> findObjectColByObjId(@Param("OBJ_ID") String OBJ_ID, 
									      @Param("COL_ID") String COL_ID,
									      @Param("typeKeywords") String typeKeywords);
	
	void updateSYS_OBJCOL_STAU(@Param("OBJ_ID") String OBJ_ID, 
							   @Param("COL_ID") String COL_ID,
							   @Param("F_STAU") int F_STAU);  
	
	void createTable(@Param("OBJ_ID") String OBJ_ID, 
	        		 @Param("SYS_OBJCOLS") List<SYS_OBJCOL> SYS_OBJCOLS); 
	
	void deleteObject(@Param("OBJ_ID") String OBJ_ID); 
}
