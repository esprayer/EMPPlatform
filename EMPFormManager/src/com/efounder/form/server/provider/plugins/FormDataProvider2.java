package com.efounder.form.server.provider.plugins;

import java.util.HashMap;
import java.util.Map;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.efounder.form.io.FormDataResolverAdapter;
import com.efounder.form.server.resolver.util.BizFormUtil;
import com.efounder.form.server.resolver.util.SYS_MDL_VAL;
import com.efounder.sql.JStatement;
import com.metadata.bizmodel.SYS_MODEL;

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

public class FormDataProvider2 extends FormDataResolverAdapter {

	/**
	 *
	 */
	public FormDataProvider2() {
	}

	public void prepareSaveForm(JStatement stmt, EFFormDataModel formModel, JParamObject PO) throws Exception {
		EFDataSet                    billDataSet = formModel.getBillDataSet();
		EFRowSet                      headRowSet;
		java.util.List<String>    dataSetKeyList = new java.util.ArrayList<String>();
		String                     billContentID = "";
		
		headRowSet = billDataSet.getRowSet(0);
		
		loadBillData(stmt, formModel, PO);
		
		
		dataSetKeyList = headRowSet.getDataSetKeyList();
		if(dataSetKeyList.size() > 0) {
			billContentID = dataSetKeyList.get(0);
			loadBillItemData(stmt, formModel, PO, billContentID);
		}
	}
	
	public Object loadBillData(JStatement stmt, EFFormDataModel formModel, JParamObject PO) throws Exception {
		EFDataSet                            eds = null;
		EFRowSet                             ers = null;
		EFRowSet                          rowset = formModel.getBillDataSet().getRowSet(0);
		java.util.List<String>    dataSetKeyList = new java.util.ArrayList<String>();
		EFDataSet                    itemDataSet = null;;
		String                          sqlWhere = PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ) 
		                                         + " = '" + rowset.getString(PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ), "") 
		                                         + "' and " + PO.GetValueByParamName(SYS_MODEL._BILL_BH_COL_, SYS_MDL_VAL.BILL_BH) 
		                                         + " = '" + rowset.getString(PO.GetValueByParamName(SYS_MODEL._BILL_BH_COL_, SYS_MDL_VAL.BILL_BH), "") + "'";
		String                        sqlOrderBy = " " + PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ) ;
		
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

	public Object loadBillItemData(JStatement stmt, EFFormDataModel formModel, JParamObject PO, String billContentID) throws Exception {
		EFDataSet          billDataSet = formModel.getBillDataSet();
		EFRowSet                rowset = formModel.getBillDataSet().getRowSet(0);
		String                sqlWhere = PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ) 
								       + " = '" + rowset.getString(PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ), "") 
								       + "' and " + PO.GetValueByParamName(SYS_MODEL._BILL_BH_COL_, SYS_MDL_VAL.BILL_BH) 
								       + " = '" + rowset.getString(PO.GetValueByParamName(SYS_MODEL._BILL_BH_COL_, SYS_MDL_VAL.BILL_BH), "") + "'";
		String              sqlOrderBy = PO.GetValueByParamName(SYS_MODEL._BILL_BH_COL_, SYS_MDL_VAL.BLFL_BH);
		EFRowSet            headRowSet;
		EFDataSet          itemDataSet;

		if(billDataSet.getRowCount() > 0) {
			headRowSet = billDataSet.getRowSet(0);
			itemDataSet = BizFormUtil.getFormDataSet(stmt, billContentID, sqlWhere, sqlOrderBy);
			headRowSet.removeDataSet(billContentID);
			headRowSet.setDataSet(billContentID, itemDataSet);
		}
		
		return null;
	}
}
