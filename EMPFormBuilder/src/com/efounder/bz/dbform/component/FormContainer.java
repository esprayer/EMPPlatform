package com.efounder.bz.dbform.component;

public abstract interface FormContainer extends FormComponent {
	 abstract FormComponent[] getFormComponents();

	 public abstract void addChildFormComp(FormComponent paramFormComponent, Object paramObject, int paramInt);

	 public abstract void removeChildFormComp(FormComponent paramFormComponent);
}

