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
public class FormHeadEnumProvider extends FormDataProviderAdapter {

  public FormHeadEnumProvider() {
  }
  /**
   *
   * @param formContext FormContext
   * @param formModel EFFormDataModel
   * @return Object
   * @throws Exception
   */
  public Object prepareLoadForm(JStatement stmt, EFFormDataModel formModel, JParamObject PO) throws Exception {
	  int          enumtype = PO.GetIntByParamName("ENUM_TYPE",0);
	  EFDataSet     ctnHead = FormDataUtil.getSYS_MDL_CTN(stmt, PO.GetValueByParamName(SYS_MODEL.MODEL_ID, ""), SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_);
	  EFDataSet     ctnItem = FormDataUtil.getSYS_MDL_CTN(stmt, PO.GetValueByParamName(SYS_MODEL.MODEL_ID, ""), SYS_MDL_CTN._BIZ_CTN_TYPE_JIDS_);
	  EFDataSet billDataSet;
	  switch(enumtype){
	  	case 1:{
	  		billDataSet = FormDataUtil.getFormHeadDataSet(stmt, PO, formModel, ctnHead, SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_);
	  		break;
	  	}
	  	case 2:{
	  		billDataSet = FormDataUtil.getFormHeadDataSet(stmt, PO, formModel, ctnItem, SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_);
	  		break;
	  	}
	  	default:
	  		billDataSet = FormDataUtil.getFormHeadDataSet(stmt, PO, formModel, ctnHead, SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_);
	  }

	  formModel.setBillDataSet(billDataSet);
	  return null;
  }

}
