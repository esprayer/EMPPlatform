package com.mrp.biz.server.plugins.resolver;

import java.util.HashMap;
import java.util.Map;

import com.mrp.biz.server.plugins.util.BizFormUtil;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;


/**
 *
 * <p>Title: </p>
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

public class FormDataProvider extends FormDataResolverAdapter {

	/**
	 *
	 */
	public FormDataProvider() {
	}

	public void prepareSaveForm(JStatement stmt, EFFormDataModel formModel) throws Exception {
		EFDataSet                    billDataSet = formModel.getBillDataSet();
		EFRowSet                      headRowSet;
		java.util.List<String>    dataSetKeyList = new java.util.ArrayList<String>();
		String                     billContentID = "";
		
		headRowSet = billDataSet.getRowSet(0);
		dataSetKeyList = headRowSet.getDataSetKeyList();
		billContentID = dataSetKeyList.get(0);
		
		loadBillData(stmt, formModel);
		loadBillItemData(stmt, formModel, billContentID);
	}
	
	public Object loadBillData(JStatement stmt, EFFormDataModel formModel) throws Exception {
		EFDataSet                            eds = null;
		EFRowSet                             ers = null;
		EFRowSet                          rowset = formModel.getBillDataSet().getRowSet(0);
		java.util.List<String>    dataSetKeyList = new java.util.ArrayList<String>();
		EFDataSet                    itemDataSet = null;;
		String                          sqlWhere = " F_KJQJ = '" + rowset.getString("F_KJQJ", "") 
		                                         + "' and F_DJBH = '" + rowset.getString("F_DJBH", "") + "'";
		String                        sqlOrderBy = " F_KJQJ";
		
		dataSetKeyList = rowset.getDataSetKeyList();
		
		eds = BizFormUtil.getFormDataSet(stmt, formModel.getBillDataSet().getTableName(), sqlWhere, sqlOrderBy);
		ers = eds.getRowSet(0);
		BizFormUtil.convertRowSet(ers, rowset);
		formModel.getBillDataSet().getRowSetList().remove(0);
		if(dataSetKeyList.size() > 0) {
			itemDataSet = rowset.getDataSet(dataSetKeyList.get(0));
			ers.setDataSet(dataSetKeyList.get(0), itemDataSet);
		}		
		formModel.getBillDataSet().addRowSet(ers);
		return null;
	}

	public Object loadBillItemData(JStatement stmt, EFFormDataModel formModel, String billContentID) throws Exception {
		EFDataSet          billDataSet = formModel.getBillDataSet();
		EFRowSet                rowset = formModel.getBillDataSet().getRowSet(0);
		String                     key = "";
		String                sqlWhere = " F_KJQJ = '" + rowset.getString("F_KJQJ", "") 
		                               + "' and F_DJBH = '" + rowset.getString("F_DJBH", "") + "'";
		String              sqlOrderBy = " F_FLGUID";
		EFRowSet            headRowSet;
		EFRowSet            itemRowSet;
		EFRowSet             newRowSet;
		EFDataSet          itemDataSet;
		EFDataSet           oldDataSet = rowset.getDataSet(billContentID);
		Map<String, EFRowSet>    flMap = new HashMap<String, EFRowSet>();
			
		if(oldDataSet == null || oldDataSet.getRowCount() == 0) {
			if(billDataSet.getRowCount() > 0) {
				headRowSet = billDataSet.getRowSet(0);
				itemDataSet = BizFormUtil.getFormDataSet(stmt, billContentID, sqlWhere, sqlOrderBy);
				headRowSet.removeDataSet(billContentID);
				headRowSet.setDataSet(billContentID, itemDataSet);
			}
		} else {
			for(int i = 0; i < oldDataSet.getRowCount(); i++) {
				itemRowSet = oldDataSet.getRowSet(i);
				key = itemRowSet.getString("F_FLGUID", "") + "@" + itemRowSet.getString("F_FLBH", "");
				flMap.put(key, itemRowSet);
			}
			if(billDataSet.getRowCount() > 0) {
				headRowSet = billDataSet.getRowSet(0);
				itemDataSet = BizFormUtil.getFormDataSet(stmt, billContentID, sqlWhere, sqlOrderBy);
				for(int i = 0; i < itemDataSet.getRowCount(); i++) {
					newRowSet = itemDataSet.getRowSet(i);
					key = newRowSet.getString("F_FLGUID", "") + "@" + newRowSet.getString("F_FLBH", "");
					itemRowSet = flMap.get(key);
					if(itemRowSet == null) continue;
					BizFormUtil.convertRowSet(newRowSet, itemRowSet);
				}
				headRowSet.removeDataSet(billContentID);
				headRowSet.setDataSet(billContentID, itemDataSet);
			}
		}
		
		return null;
	}
}
