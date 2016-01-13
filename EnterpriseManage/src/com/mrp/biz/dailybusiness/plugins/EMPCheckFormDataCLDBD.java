package com.mrp.biz.dailybusiness.plugins;

import java.sql.ResultSet;

import com.mrp.biz.server.EMPCheckFormBusinessUtil;

import esyt.framework.com.builder.base.data.DataSetUtils;
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
public class EMPCheckFormDataCLDBD extends FormDataResolverAdapter {
	
	public void prepareSaveForm(JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		EFDataSet                    billDataSet = formDataModel.getBillDataSet();
		EFRowSet                      itemRowSet;
		EFDataSet                    itemDataSet;
		EFDataSet                      HYDBDMXDS = null;
		java.util.List<String>    dataSetKeyList = new java.util.ArrayList<String>();
		String                          errorMsg = "";
		String                            F_KJQJ = "";
		String                            F_DJBH = "";
		
		if (formDataModel.getFormEditStatus() == EFFormDataModel._FORM_EDIT_DELETE_) return;

		if(billDataSet.getRowCount() > 0) {
			itemRowSet = billDataSet.getRowSet(0);
			dataSetKeyList = itemRowSet.getDataSetKeyList();
			if(dataSetKeyList.size() > 0) {
				if(formDataModel.getFormEditStatus() != EFFormDataModel._FORM_EDIT_SUBMIT_) {
					itemDataSet = itemRowSet.getDataSet(dataSetKeyList.get(0));
					for(int i = 0; i < itemDataSet.getRowCount(); i++){
				    	itemRowSet = itemDataSet.getRowSet(i);
				    	errorMsg = EMPCheckFormBusinessUtil.checkTransfersNumber(stmt, itemRowSet);	
			    		if(errorMsg != null && !errorMsg.equals("")) {
				    		throw new Exception(errorMsg);
				    	}
				    }
				} else {
					F_KJQJ = itemRowSet.getString("F_KJQJ", "");
					F_DJBH = itemRowSet.getString("F_DJBH", "");
					HYDBDMXDS = collectTransfersFormItem(stmt, F_KJQJ, F_DJBH);	
					
					for(int i = 0; i < HYDBDMXDS.getRowCount(); i++) {
						itemRowSet = HYDBDMXDS.getRowSet(i);

						errorMsg = EMPCheckFormBusinessUtil.checkXMWG(stmt, itemRowSet.getString("F_XMBH", ""), itemRowSet.getString("F_XMMC", ""));
						if(!errorMsg.trim().equals("")) throw new Exception(errorMsg);
						
						errorMsg = EMPCheckFormBusinessUtil.checkTransfersStockNumber(stmt, itemRowSet);
						if(!errorMsg.trim().equals("")) throw new Exception(errorMsg);
						
						errorMsg = EMPCheckFormBusinessUtil.checkTransfersApplyForNumber(stmt, itemRowSet);
						if(!errorMsg.trim().equals("")) throw new Exception(errorMsg);
					}
				}			
			}
		}	    
	}
	
	//汇总调拨单明细
	public EFDataSet collectTransfersFormItem(JStatement stmt, String F_KJQJ, String F_DJBH) {
		String       strSql = "";
		ResultSet        rs = null;
		EFDataSet    itemDS = EFDataSet.getInstance();
		
		try {
			strSql = " select HYDBDMX.F_XMBH, HYDBDMX.F_CPBH, HYDBDMX.F_DWBH, HYDBDMX.F_CSBH, HYDBDMX.F_CKBH, HYDBDMX.F_CLBH, HYDBDMX.F_CLDJ, "
				   + " MAX(XM.F_XMMC) as F_XMMC, MAX(CP.F_CPMC) as F_CPMC, MAX(CK.F_CKMC) as F_CKMC,"
				   + " MAX(HYDWZD.F_DWMC) as F_DWMC, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCLZD.F_CLMC) as F_CLMC,"
				   + " round(sum(HYDBDMX.F_CLSL),2) as F_DBSL"
				   + " from HYDBDMX "
			       + " LEFT JOIN HYCLZD ON HYDBDMX.F_CLBH = HYCLZD.F_CLBH"
			       + " LEFT JOIN HYDWZD ON HYDBDMX.F_DWBH = HYDWZD.F_DWBH"
			       + " LEFT JOIN HYCSZD ON HYDBDMX.F_CSBH = HYCSZD.F_CSBH"
			       + " LEFT JOIN HYCPZD CP ON HYDBDMX.F_CPBH = CP.F_CPBH"
			       + " LEFT JOIN HYXMZD XM ON HYDBDMX.F_XMBH = XM.F_XMBH"
			       + " LEFT JOIN HYCKZD CK ON HYDBDMX.F_CKBH = CK.F_CKBH"
			       + " where F_KJQJ = '" + F_KJQJ + "' and F_DJBH = '" + F_DJBH + "'"
			       + " group by HYDBDMX.F_XMBH, HYDBDMX.F_CPBH,HYDBDMX.F_DWBH, HYDBDMX.F_CSBH, HYDBDMX.F_CKBH, HYDBDMX.F_CLBH, HYDBDMX.F_CLDJ";
			try {
				rs = stmt.executeQuery(strSql);
				itemDS = DataSetUtils.resultSet2DataSet(rs, itemDS);
			} catch (Exception e) {
				e.printStackTrace();
				itemDS = EFDataSet.getInstance();
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return itemDS;
	}
}
