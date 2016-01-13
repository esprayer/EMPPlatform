package com.efounder.form.server.resolver.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.metadata.bizmodel.SYS_MODEL;

@Transactional(rollbackFor = Exception.class)
@Service("formModelDataResolverUtil")
public class FormModelDataResolverUtil {
	
	
	@Autowired
	private   FormSaveUtil               saveUtil;
	
	@Autowired
	private  FormSubmitUtil            submitUtil;

	public EFFormDataModel saveForm(String headTableName, EFRowSet headRowSet, String F_DJLX, String serviceKey) throws Exception {
		EFFormDataModel  formDataModel = EFFormDataModel.getInstance();
		EFDataSet          billDataSet = EFDataSet.getInstance(headTableName);
		
		billDataSet.addRowSet(headRowSet);
		formDataModel.setBillDataSet(billDataSet);

		formDataModel.putString(SYS_MDL_VAL.F_DJLX, F_DJLX);
		
		//如果批量添加分录，则F_FLGUID一样，F_FLBH不同，如果一次添加一条分录，则每条分录的F_FLGUID都不一样，但是编辑的时候，
		//如果批量添加的时候，需要先删除F_FLGUID相同的分录，再重新添加
		formDataModel.setFormSaveType(headRowSet.getString(EFFormDataModel._FORMEDITSAVETYPE_, "1"));
		
		//如果是单据删除分录，就提前设置上,因为删除的话，只能删除单据分录，不能删除单据
		if(headRowSet.getNumber("formEditStatus", null) != null) formDataModel.setFormEditStatus(headRowSet.getNumber("formEditStatus", null).intValue());
		
		saveUtil.saveForm(formDataModel, serviceKey);
		return formDataModel;
	}
	
	public EFFormDataModel saveForm(String headTableName, EFRowSet headRowSet, JParamObject PO) throws Exception {
		EFFormDataModel  formDataModel = EFFormDataModel.getInstance();
		EFDataSet          billDataSet = EFDataSet.getInstance(headTableName);
		
		billDataSet.addRowSet(headRowSet);
		formDataModel.setBillDataSet(billDataSet);
		
		//如果批量添加分录，则F_FLGUID一样，F_FLBH不同，如果一次添加一条分录，则每条分录的F_FLGUID都不一样，但是编辑的时候，
		//如果批量添加的时候，需要先删除F_FLGUID相同的分录，再重新添加
		formDataModel.setFormSaveType(headRowSet.getString(EFFormDataModel._FORMEDITSAVETYPE_, "1"));
		
		//如果是单据删除分录，就提前设置上,因为删除的话，只能删除单据分录，不能删除单据
		if(headRowSet.getNumber("formEditStatus", null) != null) formDataModel.setFormEditStatus(headRowSet.getNumber("formEditStatus", null).intValue());
		
		saveUtil.saveForm(formDataModel, PO);
		return formDataModel;
	}
	

	public EFFormDataModel submitForm(String headTableName, EFRowSet headRowSet, String serviceKey) {
		EFFormDataModel   formDataModel = EFFormDataModel.getInstance();
		EFDataSet           billDataSet = EFDataSet.getInstance(headTableName);
		
		billDataSet.addRowSet(headRowSet);
		formDataModel.setBillDataSet(billDataSet);
		formDataModel.setFormSaveType(EFFormDataModel._FORM_SAVE_ITEMS_ALL_);
		formDataModel.setFormEditStatus(EFFormDataModel._FORM_EDIT_SUBMIT_);	
		submitUtil.submitForm(formDataModel, headRowSet, serviceKey);
		return formDataModel;
	}
	
	public EFFormDataModel submitForm(String headTableName, EFRowSet headRowSet, JParamObject PO) {
		EFFormDataModel   formDataModel = EFFormDataModel.getInstance();
		EFDataSet           billDataSet = EFDataSet.getInstance(headTableName);
		
		billDataSet.addRowSet(headRowSet);
		formDataModel.setBillDataSet(billDataSet);
		formDataModel.setFormSaveType(EFFormDataModel._FORM_SAVE_ITEMS_ALL_);
		formDataModel.setFormEditStatus(EFFormDataModel._FORM_EDIT_SUBMIT_);	
		submitUtil.submitForm(formDataModel, headRowSet, PO);
		return formDataModel;
	}
}
