package com.efounder.form.server.provider.plugins;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.bizmodel.SYS_MDL_CTN;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.*;
import com.efounder.form.io.*;
import com.efounder.form.server.provider.plugins.util.*;
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
public class FormDataProvider extends FormDataProviderAdapter {
	/**
	 * 表单数据加载插件：此插件在Prepare阶段执行
	 * 主要装入以下数据：
	 * 1.表单表头数据
	 * 2.表单分录数据
	 * 3.表单明细数据
	 */
	  /**
	   *
	   * @param formContext FormContext
	   * @param formModel EFFormDataModel
	   * @return Object
	   * @throws Exception
	   */
	public Object prepareLoadForm(JStatement stmt, EFFormDataModel formModel, JParamObject PO) throws Exception {
		EFDataSet ctnHead = FormDataUtil.getSYS_MDL_CTN(stmt, PO.GetValueByParamName(SYS_MODEL.MODEL_ID, ""), SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_);
		EFDataSet ctnItem = FormDataUtil.getSYS_MDL_CTN(stmt, PO.GetValueByParamName(SYS_MODEL.MODEL_ID, ""), SYS_MDL_CTN._BIZ_CTN_TYPE_JIDS_);
		
		EFDataSet billDataSet = FormDataUtil.getFormHeadDataSet(stmt, PO, formModel, ctnHead, SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_);
	    // 获取表单分录数据集
	    EFDataSet[] itemDataSets = FormDataUtil.getFormItemDataSets(stmt, PO, formModel, ctnItem, SYS_MDL_CTN._BIZ_CTN_TYPE_JIDS_);
	    
	    for(int i = 0; i < itemDataSets.length; i++) {
	    	billDataSet.getRowSet(0).setDataSet(itemDataSets[i].getTableName(), itemDataSets[i]);
	    }
	    formModel.setBillDataSet(billDataSet);
	    return formModel;
	}
}
