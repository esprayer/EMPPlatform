package com.mrp.biz.server.plugins.resolver;

import com.mrp.biz.server.plugins.util.FormCodeUtil;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.bizmodel.SYS_MDL_VAL;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;

public class FormCommonCodeCreater extends FormDataResolverAdapter {
	
	public void finishSaveForm(JStatement stmt, EFFormDataModel model) throws Exception {
		
		if (model.getFormEditStatus() != EFFormDataModel._FORM_EDIT_CREATE_) return;
		
		EFDataSet     eds = model.getBillDataSet();
	    String    headcnt = eds.getTableName();
	    String       code = "";
	    String      sguid = "";
	    String     F_DJLX = model.getString(SYS_MDL_VAL.F_DJLX, "");
	    int     billcount = eds.getRowCount();
	    java.util.List<String> dataSetKeyList;
	    
	    for(int i=0;i<billcount;i++){
	        EFRowSet  ers = eds.getRowSet(i);
	        sguid = ers.getString("F_GUID", null);
	        code = FormCodeUtil.GetNewBillCode(stmt, model, ers, headcnt, F_DJLX);
	        ers.putString("F_DJBH", code);
	        String sql = " update " + eds.getTableName()
	        		   + " set F_DJBH = '" + code.trim() + "' where F_GUID = '" + sguid + "'";
	        stmt.execute(sql);
	        dataSetKeyList = ers.getDataSetKeyList();
	        for(int j = 0; j < dataSetKeyList.size(); j++) {
	        	sql = " update " + dataSetKeyList.get(j)
     		  		+ " set F_DJBH = '" + code.trim() + "' where F_GUID = '" + sguid + "'";
	        	stmt.execute(sql);
	        }
	    }
	} 
}
