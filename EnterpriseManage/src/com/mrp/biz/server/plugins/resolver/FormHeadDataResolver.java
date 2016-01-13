package com.mrp.biz.server.plugins.resolver;

import com.mrp.biz.server.plugins.util.BizFormUtil;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;
import esyt.framework.com.dbform.meta.domodel.SYS_OBJCOLS;

public class FormHeadDataResolver extends FormDataResolverAdapter {

	public void saveBillData(JStatement stmt, EFFormDataModel formModel) throws Exception {
		EFRowSet    headRowSet = null;
		EFRowSet   bizMetaData = null;
		EFDataSet  billDataSet = null;
		
		billDataSet = formModel.getBillDataSet();
		bizMetaData = BizFormUtil.getBIZMetaData(stmt, billDataSet.getTableName());
		
		for(int i = 0; i < billDataSet.getRowCount(); i++) {
			headRowSet = billDataSet.getRowSet(i);
			BizFormUtil.saveRowSetData(stmt, billDataSet.getTableName(), headRowSet, bizMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_));
		}
	}
}
