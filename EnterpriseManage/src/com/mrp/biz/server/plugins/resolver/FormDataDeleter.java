package com.mrp.biz.server.plugins.resolver;

import com.mrp.biz.server.plugins.util.BizFormUtil;

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

public class FormDataDeleter extends FormDataResolverAdapter {
	/**
	 *
	 */
	public FormDataDeleter() {
	}

	/**
	 * 
	 * @param formContext  FormContext
	 * @param formModel    EFFormDataModel
	 * @throws Exception
	 */
	public void prepareSaveForm(JStatement stmt, EFFormDataModel model) throws Exception {
		int formEditStatus = model.getFormEditStatus();
		
		//如果之前FormEditStatus已经设置了，就不需要再重新设置了
		if(formEditStatus == EFFormDataModel._FORM_EDIT_DELETE_) {
			BizFormUtil.formDataDeleter(stmt, model);
			return;
		}
		if (model.getFormEditStatus() == EFFormDataModel._FORM_EDIT_CREATE_) return;
		BizFormUtil.formDataDeleter(stmt, model);
	}
}
