package com.efounder.bz.dbform.component;

import javax.swing.Action;

import com.efounder.bz.dbform.datamodel.ActionComponent;

public abstract interface ActionProvider extends ActionComponent {
//	public abstract FormCanvas getFormCanvas();
//
//	public abstract void setFormCanvas(FormCanvas paramFormCanvas);

	public abstract FormComponent getContextComponent();

	public abstract void setContextComponent(FormComponent paramFormComponent);

	public abstract Action getAction(Object paramObject, Object[] paramArrayOfObject);
}