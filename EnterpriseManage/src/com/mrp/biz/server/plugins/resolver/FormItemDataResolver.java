package com.mrp.biz.server.plugins.resolver;

import com.mrp.biz.server.plugins.util.BizFormUtil;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;
import esyt.framework.com.dbform.meta.domodel.SYS_OBJCOLS;

public class FormItemDataResolver extends FormDataResolverAdapter {

	public void saveBillData(JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		int                             rowCount=0;
		EFDataSet                    billDataSet = formDataModel.getBillDataSet();
		EFRowSet                      itemRowSet;
		EFDataSet                    itemDataSet;
		EFRowSet                     bizMetaData = null;
		java.util.List<String>    dataSetKeyList = new java.util.ArrayList<String>();
		
		int                       formEditStatus = formDataModel.getFormEditStatus();
		
		//如果之前FormEditStatus已经设置了，就不需要再重新设置了
		if(formEditStatus == EFFormDataModel._FORM_EDIT_DELETE_) return;
		
		if(billDataSet.getRowCount() > 0) {
			itemRowSet = billDataSet.getRowSet(0);
			dataSetKeyList = itemRowSet.getDataSetKeyList();
			if(dataSetKeyList.size() > 0) {
				itemDataSet = itemRowSet.getDataSet(dataSetKeyList.get(0));
				rowCount = itemDataSet.getRowCount();
				
				bizMetaData = BizFormUtil.getBIZMetaData(stmt, itemDataSet.getTableName());
				for(int i = 0; i < rowCount; i++){
			    	itemRowSet = itemDataSet.getRowSet(i);
			    	BizFormUtil.saveRowSetData(stmt, itemDataSet.getTableName(), itemRowSet, bizMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_));
			    }
			}
		}	    
	}
}
