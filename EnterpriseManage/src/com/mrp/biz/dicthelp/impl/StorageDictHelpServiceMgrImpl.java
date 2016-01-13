package com.mrp.biz.dicthelp.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrp.biz.dicthelp.StorageDictHelpServiceMgr;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.com.builder.base.data.EFDataSet;

import com.mrp.repository.dailyBusiness.storage.EMPStorageQueryHelp;


@Transactional(rollbackFor = Exception.class)
@Service("storageDictHelpServiceMgr")
public class StorageDictHelpServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements StorageDictHelpServiceMgr {

	@Autowired
	private EMPStorageQueryHelp storageQueryHelp;

	public EFDataSet searchHYXMZD(String beginDate, String endDate, String F_XMBH, String F_CLBH, int startIndex, int count) {
		EFDataSet datasetList = storageQueryHelp.projectHelp(beginDate, endDate, F_XMBH, F_CLBH, startIndex, count);
		return datasetList;
	}

}
