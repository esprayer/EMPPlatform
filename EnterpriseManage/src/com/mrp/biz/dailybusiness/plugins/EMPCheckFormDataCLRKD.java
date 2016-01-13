package com.mrp.biz.dailybusiness.plugins;

import java.util.ArrayList;
import java.util.List;

import com.mrp.biz.server.EMPCheckFormBusinessUtil;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;

/**
*
* <p>Title: 调拨单提交后，将调拨材料写进项目仓库中</p>
*
* <p>Description: </p>
*
* <p>Copyright: Copyright (c) 2009</p>
*
* <p>Company: </p>
*
* @author not attributable
* @version 1.0
*/
public class EMPCheckFormDataCLRKD extends FormDataResolverAdapter {
	
	public void prepareSaveForm(JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		EFDataSet                    billDataSet = formDataModel.getBillDataSet();
		EFRowSet                      itemRowSet;
		EFDataSet                    itemDataSet;
		java.util.List<String>    dataSetKeyList = new java.util.ArrayList<String>();
		String                          errorMsg = "";
		
		if(billDataSet.getRowCount() > 0) {
			itemRowSet = billDataSet.getRowSet(0);
			dataSetKeyList = itemRowSet.getDataSetKeyList();
			if(dataSetKeyList.size() > 0) {
				itemDataSet = itemRowSet.getDataSet(dataSetKeyList.get(0));
				for(int i = 0; i < itemDataSet.getRowCount(); i++){
			    	itemRowSet = itemDataSet.getRowSet(i);
			    	errorMsg = EMPCheckFormBusinessUtil.checkMaterialStockApplyNumber(stmt, itemRowSet);
			    	if(!errorMsg.equals("")) {
			    		throw new Exception(errorMsg);
			    	}
			    }
			}
		}	    
	}
}
