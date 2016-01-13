package com.efounder.form.client.component.frame.action;

import java.awt.event.*;
import javax.swing.*;

import com.core.xml.JBOFClass;
import com.efounder.eai.ide.ExplorerIcons;

public class EMPAffixAction extends AbstractAction {

	private Object actionObject = null;
	private Object dataObject   = null;
	private String Command = null;
	
	public EMPAffixAction(Object actionObject,Object dataObject,String Command,String s, char c, String s1, Icon icon) {
		super("", icon);
		this.Command = Command;
		this.actionObject = actionObject;
		this.dataObject = dataObject;
		putValue(Action.SHORT_DESCRIPTION, s);
	}
	
	public void actionPerformed(ActionEvent actionEvent) {
		Object[] OArray = {actionObject,dataObject,this,actionEvent};
	    JBOFClass.VoidCallObjectMethod(actionObject,Command,OArray);
	}
}