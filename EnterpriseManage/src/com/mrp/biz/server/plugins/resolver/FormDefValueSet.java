package com.mrp.biz.server.plugins.resolver;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.mrp.biz.server.plugins.util.FormBillFieldUtil;

import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;

public class FormDefValueSet extends FormDataResolverAdapter {

	public void prepareSaveForm(JStatement stmt, EFFormDataModel model) throws Exception {
		EFDataSet eds = model.getBillDataSet();
	    formHeadDefValueSet(stmt, model, eds);
	    
	    EFRowSet ers = model.getBillData(0);
	    formItemDefValueSet(stmt, model, ers);		
	}
	
	protected void formHeadDefValueSet(JStatement stmt, EFFormDataModel model, EFDataSet eds) throws Exception {
		java.util.Date currTime = new java.util.Date();
		EFRowSet ers = eds.getRowSet(0);
		EFRowSet rowset = null;
	    String F_GUID = ers.getString("F_GUID", null);
	    String F_KJQJ = ers.getString("F_KJQJ", "");
	    String F_DJBH = ers.getString("F_DJBH", null);
	    
	    ers.putNumber("F_CHDATE", currTime.getTime());
	    
	    if (model.getFormEditStatus() == EFFormDataModel._FORM_EDIT_CREATE_) {
	    	ers.putString("F_DJBH", ers.getString("F_GUID", ""));
			ers.putNumber("F_CRDATE", currTime.getTime());		
			ers.putNumber("F_CHDATE", currTime.getTime());
	        return;
	    }
	    
	    //提交之前，已经是最新的信息，所以此处可以不用转换
	    if (model.getFormEditStatus() == EFFormDataModel._FORM_EDIT_SUBMIT_) return;
	    
	    Iterator it;
	    EFDataSet headDataSet = EFDataSet.getInstance(eds.getTableName());
	    ResultSet rs;
		String strSql = "select * from " + eds.getTableName() + " where F_KJQJ = '" + F_KJQJ + "'";
		if(F_GUID != null && !F_GUID.equals("")) {
			strSql += " and F_GUID = '" + F_GUID + "'";
		}
		if(F_DJBH != null && !F_DJBH.equals("")) {
			strSql += " and F_DJBH = '" + F_DJBH + "'";
		}
		rs = stmt.executeQuery(strSql);
		headDataSet = DataSetUtils.resultSet2DataSet(rs, headDataSet);
		
		if(headDataSet.getRowCount() > 0) {
			rowset = headDataSet.getRowSet(0);
			it = rowset.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry m = (Entry) it.next();
				if(ers.getDataMap().get(m.getKey().toString()) == null || 
				   ers.getDataMap().get(m.getKey().toString()).toString().equals("") 
				   || m.getKey().toString().equals("F_CRDATE")) {
					ers.getDataMap().put(m.getKey().toString(), m.getValue());
				}
			}
		}		
	}
	
	protected void formItemDefValueSet(JStatement stmt, EFFormDataModel model, EFRowSet ers) throws Exception {
		java.util.List<String>   dataSetKeyList = new java.util.ArrayList<String>();
		EFDataSet                           eds;
		DecimalFormat                      dffl = FormBillFieldUtil.getBILL_FL_Format();
		String                             flbh;
		java.util.Date                 currTime = new java.util.Date();
		dataSetKeyList = ers.getDataSetKeyList();
		
		//如果删除分录的话，分录不做任何修改，防止做了修改后，删除的时候找不到分录
		if(model.getFormEditStatus() == EFFormDataModel._FORM_EDIT_DELETE_) return;

		for(int i = 0 ; i < dataSetKeyList.size(); i++) {
			eds = ers.getDataSet(dataSetKeyList.get(i).toString());
			
			for(int j = 0; j < eds.getRowCount(); j++) {
				//重新组织分录编号
				if (model.getFormEditStatus() != EFFormDataModel._FORM_EDIT_SUBMIT_) {
					eds.getRowSet(j).putString("F_DJBH", ers.getString("F_DJBH", ""));
					eds.getRowSet(j).putNumber("F_CRDATE", ers.getNumber("F_CRDATE", currTime.getTime()));
				} 
				//如果是提交，则重新组织分录编号
				else {
					flbh = dffl.format(j+ 1);
					eds.getRowSet(j).putString("F_FLBH", flbh);
				}
				eds.getRowSet(j).putNumber("F_CHDATE", ers.getNumber("F_CHDATE", currTime.getTime()));
				flbh = eds.getRowSet(j).getString("F_FLBH", "");
				//修改单据分录的时候，分录已经存在，重新赋值导致不能更新
				if(flbh.trim().equals("")) {
					flbh = dffl.format(j+ 1);
					eds.getRowSet(j).putString("F_FLBH", flbh);
				}
			}
		}
	}
}
