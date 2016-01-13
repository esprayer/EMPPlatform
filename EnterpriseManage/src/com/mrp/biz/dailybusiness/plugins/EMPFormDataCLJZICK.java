package com.mrp.biz.dailybusiness.plugins;

import java.util.ArrayList;
import java.util.List;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;

/**
*
* <p>Title: 入库单提交后，将入库材料写进项目仓库中</p>
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
public class EMPFormDataCLJZICK extends FormDataResolverAdapter {
	
	public void finishSaveForm(JStatement stmt, EFFormDataModel model) throws Exception {
		EFDataSet                    headDataSet = model.getBillDataSet();
		EFRowSet                      headRowSet = headDataSet.getRowSet(0);
	    java.util.List<String>    dataSetKeyList = headRowSet.getDataSetKeyList();
	    List<String>                        list = new ArrayList<String>();
	    EFDataSet                    itemDataSet = headRowSet.getDataSet(dataSetKeyList.get(0));
	    EFRowSet                      itemRowSet = null;
	    String                            strSql = "";
	    Long                            F_CHDATE = (Long) headRowSet.getNumber("F_CHDATE", (new java.util.Date()).getTime());
	    
	    for(int i = 0; i < itemDataSet.getRowCount(); i++){
	        itemRowSet = itemDataSet.getRowSet(i);
	        strSql = "insert into HYXMCK (F_DJBH, F_CLBH, F_XMBH, F_DWBH, F_CSBH, F_CLDJ, F_CRFX, F_CLSL, F_FLBH,"
	        	   + "F_CPBH, F_CRDATE, F_CHDATE, F_KJQJ, F_CKBH, F_YYCKBH, F_YYXMBH, F_YYCPBH) values('"
	        	   + itemRowSet.getString("F_DJBH", "") + "','" + itemRowSet.getString("F_CLBH", "") + "','"
	        	   + itemRowSet.getString("F_XMBH", "") + "','" + itemRowSet.getString("F_DWBH", "") + "','"
	        	   + itemRowSet.getString("F_CSBH", "") + "',round(" + itemRowSet.getObject("F_CLDJ", 0) + ",2),'R'," 
	        	   + "round(" + itemRowSet.getObject("F_RKSL", "") + ",2),'"
	        	   + itemRowSet.getString("F_FLBH", "") + "','" + itemRowSet.getString("F_CPBH", "") + "',"
	        	   + F_CHDATE + "," + F_CHDATE + ",'" + itemRowSet.getString("F_KJQJ", "") + "','"
	        	   + headRowSet.getString("F_CKBH", "") + "', '" + itemRowSet.getString("F_CKBH", "") + "','"
	        	   + itemRowSet.getString("F_XMBH", "") + "','" + itemRowSet.getString("F_CPBH", "") + "')";
	        System.out.println("JZIXMCK sql|||" + strSql);
			list.add(strSql);
	    }
	    stmt.clearBatch();
		for (int i = 0; i < list.size(); i++) {
			stmt.addBatch((String) list.get(i));
		}
		stmt.executeBatch();
	} 
}
