package com.mrp.biz.dailybusiness.plugins;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.web.servlet.ModelAndView;

import com.mrp.biz.server.EMPCheckFormBusinessUtil;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JConnection;
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
public class EMPCheckFormDataCLCKD extends FormDataResolverAdapter {
	
	public void prepareSaveForm(JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		EFDataSet                    billDataSet = formDataModel.getBillDataSet();
		EFRowSet                      itemRowSet;
		EFDataSet                    itemDataSet;
		java.util.List<String>    dataSetKeyList = new java.util.ArrayList<String>();
		String                          errorMsg = "";
		
		if (formDataModel.getFormEditStatus() == EFFormDataModel._FORM_EDIT_DELETE_) return;

		if(billDataSet.getRowCount() > 0) {
			itemRowSet = billDataSet.getRowSet(0);
			dataSetKeyList = itemRowSet.getDataSetKeyList();
			if(dataSetKeyList.size() > 0) {
				itemDataSet = itemRowSet.getDataSet(dataSetKeyList.get(0));
				for(int i = 0; i < itemDataSet.getRowCount(); i++){
			    	itemRowSet = itemDataSet.getRowSet(i);
			    	if(formDataModel.getFormEditStatus() == EFFormDataModel._FORM_EDIT_SUBMIT_) {
			    		errorMsg = EMPCheckFormBusinessUtil.checkDVItemMaterialSave(stmt, itemRowSet.getString("F_KJQJ", ""), itemRowSet.getString("F_DJBH", ""));	
			    		if(errorMsg != null && !errorMsg.equals("")) {
				    		throw new Exception(errorMsg);
				    	}
			    	} else {
			    		errorMsg = onCheck(stmt, itemRowSet);
			    		if(errorMsg != null && !errorMsg.equals("")) {
				    		throw new Exception(errorMsg);
				    	}
				    	errorMsg = EMPCheckFormBusinessUtil.checkStockNumber(stmt, itemRowSet);
				    	if(!errorMsg.equals("")) {
				    		throw new Exception(errorMsg);
				    	}
				    	errorMsg = EMPCheckFormBusinessUtil.checkApplyForNumber(stmt, itemRowSet);
				    	if(!errorMsg.equals("")) {
				    		throw new Exception(errorMsg);
				    	}
			    	}
			    }
			}
		}	    
	}
	
	private String onCheck(JStatement stmt, EFRowSet rowset) {
		String     strSql = "";
		int         count = 0;

		strSql = "select count(*) from HYXMCP where F_XMBH = '" + rowset.getString("F_SSXMBH", "") + "' and F_CPBH = '" + rowset.getString("F_SSCPBH", "") + "'";
		count = onCheck(stmt, strSql);
		if(count == 0) return "所属项目【" + rowset.getString("F_SSXMMC", "") + "】不包括【" + rowset.getString("F_SSCPMC", "") + "】产品，请检查项目产品管理中，【" + rowset.getString("F_SSXMMC", "") + "】项目是否存在【" + rowset.getString("F_SSCPMC", "") + "】产品！";
		
		strSql = "select count(*) from HYXMMX where F_XMBH = '" + rowset.getString("F_SSXMBH", "") + "' and F_CLBH = '" + rowset.getString("F_CLBH", "") + "'";
		count = onCheck(stmt, strSql);
		if(count == 0) return "所属项目所在材料申请单中不包括【" + rowset.getString("F_CLMC", "") + "】材料，不能进行出库，请检查【" + rowset.getString("F_SSXMMC", "") + "】材料申请单！";
		
		strSql = "select count(*) from HYXMMX where F_XMBH = '" + rowset.getString("F_YYXMBH", "") + "' and F_CLBH = '" + rowset.getString("F_CLBH", "") + "'";
		count = onCheck(stmt, strSql);
		if(count == 0) return "应用项目所在材料申请单中不包括【" + rowset.getString("F_CLMC", "") + "】材料，不能进行出库，请检查【" + rowset.getString("F_YYCPMC", "") + "】材料申请单！";
		
		strSql = "select count(*) from HYXMCP where F_XMBH = '" + rowset.getString("F_YYXMBH", "") + "' and F_CPBH = '" + rowset.getString("F_YYCPBH", "") + "'";
		count = onCheck(stmt, strSql);
		if(count == 0) return "应用项目【" + rowset.getString("F_YYXMMC", "") + "】不包括【" + rowset.getString("F_YYCPMC", "") + "】产品，请检查项目产品管理中，【" + rowset.getString("F_YYXMMC", "") + "】项目是否存在【" + rowset.getString("F_YYCPMC", "") + "】产品！";
		
		return null;
	}
	
	public int onCheck(JStatement stmt, String strSql) {
		ResultSet        rs = null;
		int           count = 0;
		try {
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) {
				count = rs.getInt(1);
			}		
		} catch (Exception e) {
			e.printStackTrace();
			count = 0;
		}
		return count;
	}
}
