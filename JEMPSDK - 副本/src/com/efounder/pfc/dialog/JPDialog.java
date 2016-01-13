package com.efounder.pfc.dialog;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import com.core.xml.JBOFClass;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPDialog extends JDialog {
	protected Vector     WindowCustomEventList = null;
	public static int RESULT_OK=1;
	public static int RESULT_CANCEL=0;
	public int Result=0;
	public Object PO = null;
	protected Object CustomObject;
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public JPDialog(Frame frame, String title, boolean modal) {
		super(frame,title,modal);
	}
	public JPDialog(JDialog frame, String title, boolean modal) {
		super(frame,title,modal);
	}

	public JComponent getCustom() {
		return null;
	}
	public void addWindowCustomEvent() {
//		if ( WindowCustomEventList == null )
//			WindowCustomEventList = new Vector();
//		WindowCustomEventList.add(wc);
	}
	
	public void removeWindowCustomEvent() {
//		if ( WindowCustomEventList != null ) {
//			WindowCustomEventList.remove(wc);
//		}
	}
	
	protected void CallWindowCustomEvent(String EventName,Object o) {
//		if ( WindowCustomEventList == null ) return;
//		IWindowCustom iwc = null;
//		for(int i=0;i<WindowCustomEventList.size();i++) {
//			iwc = (IWindowCustom)WindowCustomEventList.get(i);
//			JBOFClass.CallObjectMethod(iwc,EventName,this,o);
//		}
	}
	
	public Object getResultObject() {
		return null;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public JPDialog() {
		super();
		try {
			jbPInit();
		} catch(Exception e) {
          e.printStackTrace();
		}
	}
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	void jbPInit() throws Exception {
	}
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public void CenterWindow() {
		Dimension dlgSize = this.getSize();
		Dimension ScnSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((ScnSize.width - dlgSize.width)/2, (ScnSize.height - dlgSize.height)/2);
	}
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public int OnOk() {
		Result = RESULT_OK;
		this.hide();
		this.dispose();
//      removeNotify();
		return Result;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public int OnCancel() {
		Result = RESULT_CANCEL;
		this.hide();
		this.dispose();
//      removeNotify();
		return Result;
	}
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public void setCustomObject(Object O) {
		CustomObject = O;
	}
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public void InitDialog() {

	}
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public void DestroyDialog() {

	}
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public void Show() {
		if ( this.isVisible() ) return;
		InitDialog();
		super.show();
	}
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//--------------------------------------------------------------------------------------------------
	public void Hide() {
		if ( !this.isVisible() ) return;
		DestroyDialog();
		super.hide();
	}
}
