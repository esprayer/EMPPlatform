package dwz.persistence.formservice.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import dwz.persistence.formservice.bean.SYS_DBFORM;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SYS_DBFORMMapper extends BaseMapper<SYS_DBFORM,String>{
	// 查询
	List<SYS_DBFORM> loadSYS_DBFORM(@Param("BBZD_BH") String BBZD_BH);
}
