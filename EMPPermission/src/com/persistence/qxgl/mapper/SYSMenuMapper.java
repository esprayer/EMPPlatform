package com.persistence.qxgl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.dal.BaseMapper;
import com.persistence.qxgl.beans.SYSMenu;

@Repository
public interface SYSMenuMapper extends BaseMapper<SYSMenu,String>{
	List<SYSMenu> findAllMenu(String appId);
	List<SYSMenu> findSubMenuByBh(@Param("MENU_BH") String menuBh,@Param("APP_ID") String appId,@Param("js") int js);
	SYSMenu findMenuByBh(@Param("MENU_BH")String menuBh,@Param("APP_ID") String appId);
	void deleteMenu(@Param("MENU_BH")String menuBh,@Param("APP_ID") String appId);
	void updateParent(@Param("MENU_BH")String menuBh,@Param("APP_ID") String appId,@Param("F_MX") String f_mx);
	void deleteSubMenu(@Param("MENU_BH")String menuBh,@Param("APP_ID") String appId);
	int getSubMenuCount(@Param("MENU_BH")String parent,@Param("APP_ID") String appId);
}
