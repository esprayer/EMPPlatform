package com.dal;

import java.util.List;

import com.dal.object.AbstractDO;


/**
 * <strong>BaseMapper</strong><br>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * @version <strong>ecointel-epp v1.0.0</strong><br>
 */
public interface BaseMapper<T extends AbstractDO, PK extends java.io.Serializable> {

	PK insert(T model);

	void delete(PK modelPK);
	
	T load(PK modelPK);

	void update(T model);

	void updateSelective(T model);
	
	int countAll();
	
	List<T> findAll();
	
	List<T> findAllObjectColumn(PK modelPK);
	
	List<T> findByObjId(PK modelPK);
	
	List<PK> findAllIds();
	
}
