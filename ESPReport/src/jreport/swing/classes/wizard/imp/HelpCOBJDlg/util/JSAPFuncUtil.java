package jreport.swing.classes.wizard.imp.HelpCOBJDlg.util;

import java.util.*;

import javax.swing.*;

import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;
import jreport.jdal.classes.sap.*;
import java.util.ResourceBundle;
import com.pansoft.esp.fmis.client.fwkview.FMISContentWindow;
import jbof.gui.window.classes.JBOFChildWindow;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Pansoft</p>
 * @author not attributable
 * @version 1.0
 */
public class JSAPFuncUtil {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.Language");

    public static HashMap SAPBBCObjMap = new HashMap();

    /**
     *
     * @param func  String
     * @return      Object
     */
    public static Object getSAPFuncObjInfo(String func) {
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("F_FUNC", func);
        JResponseObject RO =
            (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getSAPFunctionInfo", PO, null);
        if (RO.ErrorCode == -1) {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, res.getString("String_22") + RO.ErrorString, res.getString("String_23"),
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return RO.ResponseObject;
    }
	public static String GetRealRptSvr() {
		String sSvrName = "";
		Object childwindow=com.efounder.eai.ide.EnterpriseExplorer.ContentView.getActiveWindow();
		if(childwindow instanceof FMISContentWindow) {
			childwindow = ( (FMISContentWindow) childwindow).getFMISComp();
			if ( childwindow instanceof JBOFChildWindow ) {
				sSvrName = ((JBOFChildWindow)childwindow).getPageBaseUrl();
			}
		}
		if ( !sSvrName.equals("") && sSvrName != null ) {
			return sSvrName + ".";
		} else {
			sSvrName = "";
		}
		return sSvrName;
	}

    /**
     * 将所有的SAP报表的条件对象增加到一个静态的HashMap中,在帮助的时候用.
     * @param funcName  String           函数名称
     * @param obj       JSAPFuncObjStub  条件对象的对象
     */
    public static void addSAPFuncCObj(String funcName, JSAPFuncObjStub obj) {
        if (funcName == null || "".equals(funcName) || obj == null)
            return;
        SAPBBCObjMap.put(obj, funcName);
    }

}
