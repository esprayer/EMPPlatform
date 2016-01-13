package com.efounder.component;

import com.dal.object.AbstractDO;

public class EMPComposeFormInfo{
	protected EMPComponentStub dataContainerStub = null;
	private             String            FormID;
	private             String          FormName;
	private             String          FormType;
	private             String         FormClass;
	
	public EMPComponentStub getDataContainerStub() {
		return dataContainerStub;
	}
	public void setDataContainerStub(EMPComponentStub dataContainerStub) {
		this.dataContainerStub = dataContainerStub;
	}
	public String getFormID() {
		return FormID;
	}
	public void setFormID(String FormID) {
		this.FormID = FormID;
	}
	public String getFormName() {
		return FormName;
	}
	public void setFormName(String FormName) {
		this.FormName = FormName;
	}
	public String getFormType() {
		return FormType;
	}
	public void setFormType(String FormType) {
		this.FormType = FormType;
	}
	public String getFormClass() {
		return FormClass;
	}
	public void setFormClass(String formClass) {
		FormClass = formClass;
	}
}
