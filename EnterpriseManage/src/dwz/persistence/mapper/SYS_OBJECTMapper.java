package dwz.persistence.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import dwz.persistence.beans.SYS_OBJCOL;
import dwz.persistence.beans.SYS_OBJECT;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SYS_OBJECTMapper extends BaseMapper<SYS_OBJECT,String>{
	void createObject(@Param("OBJ_ID") String OBJ_ID, 
	        		  @Param("SYS_OBJCOLS") List<SYS_OBJCOL> SYS_OBJCOLS);
	void updateF_STAU(@Param("OBJ_ID") String OBJ_ID, 
	                  @Param("F_STAU") int F_STAU); 

}
