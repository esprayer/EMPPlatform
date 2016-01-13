package jreport.jbof.classes.BOFReportObject;

import jframework.foundation.classes.*;
import jfoundation.bridge.classes.*;
import jreport.swing.classes.util.*;
import jreport.jbof.classes.BOFReportObject.ReportExplorer.JReportObjectStub;
import jreportwh.jdof.classes.common.util.BoolUtil;
import jframework.log.JLogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.client.fwkview.FMISContentWindow;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;

import jbof.gui.window.classes.JBOFChildWindow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBOFReportPropertyObject {
  static ResourceBundle res = ResourceBundle.getBundle("jreport.jbof.classes.BOFReportObject.Language");
  public JBOFReportPropertyObject() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  //������
  public static boolean setBbxf(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
    /**
     * ��¼��־��Ϣ
     * add by hufeng 2006.8.17
     */
    writeBbLog("BBXF", PO);
    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "setBbxf", PO, Data,CustomObject, AdditiveObject);
    return BoolUtil.getBoolean( (String) RO.ResponseObject);
  }
  public static boolean setBbfc(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
    /**
     * ��¼��־��Ϣ
     * add by hufeng 2006.8.17
     */
    writeBbLog("BBFC", PO);
    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "setBbfc", PO, Data,CustomObject, AdditiveObject);
    return BoolUtil.getBoolean( (String) RO.ResponseObject);
  }

  //��ʽ���\uFFFD
  public static boolean setGsfc(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
    /**
     * ��¼��־��Ϣ
     * add by hufeng 2006.8.17
     */
    writeBbLog("GSFC", PO);
    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "setGsfc", PO, Data,CustomObject, AdditiveObject);
    return BoolUtil.getBoolean( (String) RO.ResponseObject);
  }

  //����
  public static boolean setJs(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "setJs", PO, Data, CustomObject, AdditiveObject);
    return BoolUtil.getBoolean( (String) RO.ResponseObject);
  }

  //���\uFFFD
  public static boolean setWc(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "setWc", PO, Data,CustomObject, AdditiveObject);
    return BoolUtil.getBoolean( (String) RO.ResponseObject);
  }
  
  //���\uFFFD
  public static boolean createRPTLSHRSJFromRPTDWSJ(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "createRPTLSHRSJFromRPTDWSJ", PO, Data,CustomObject, AdditiveObject);
    return BoolUtil.getBoolean( (String) RO.ResponseObject);
  }	

//  public static String getDwWckz(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
//	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getDwWckz", PO, Data,CustomObject, AdditiveObject);
//	    return (String) RO.ResponseObject;
//	  }
  public static int getDwZrzxMx(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getDwZrzxMx", PO, Data,CustomObject, AdditiveObject);
	    return (Integer) RO.ResponseObject;
	  }
  public static HashMap getDwWckz(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getDwWckz", PO, Data,CustomObject, AdditiveObject);
	    return (HashMap) RO.ResponseObject;
	  }
  // add by lqk
  public static List getImportBeforeCal(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getImportBeforeCal", PO, Data,CustomObject, AdditiveObject);
	    return (List) RO.ResponseObject;
	  }
  //add by lqk 2013-11-13
  public static List getQrLx(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("TradeServer.ReportObject", "getQrLx", PO, Data,CustomObject, AdditiveObject);
	    return (List) RO.ResponseObject;
	  }
  public static String getDwZrzx(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getDwZrzx", PO, Data,CustomObject, AdditiveObject);
	    return (String) RO.ResponseObject;
	  }
  public static boolean getPzWc(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("FIAESERVER.ReportObject", "getPzWc", PO, Data,CustomObject, AdditiveObject);
	    return BoolUtil.getBoolean( (String) RO.ResponseObject);
	  }
  public static boolean getQjgb(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("FIAESERVER.ReportObject", "getQjgb", PO, Data,CustomObject, AdditiveObject);
	    return BoolUtil.getBoolean( (String) RO.ResponseObject);
	  }
  public static boolean getPzFc(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("FIAESERVER.ReportObject", "getPzFc", PO, Data,CustomObject, AdditiveObject);
	    return BoolUtil.getBoolean( (String) RO.ResponseObject);
	  }
  public static boolean getSjdwsh(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getSjdwsh", PO, Data,CustomObject, AdditiveObject);
	    return BoolUtil.getBoolean( (String) RO.ResponseObject);
	  }
  public static Object getDwDeal(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getDwDeal", PO, Data,CustomObject, AdditiveObject);
	    return RO.ResponseObject;
	  }
  public static Object getDwJy(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getDwJy", PO, Data,CustomObject, AdditiveObject);
	    return RO.ResponseObject;
	  }
	  public static Object getSqspJyPass(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
		    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getSqspJyPass", PO, Data,CustomObject, AdditiveObject);
		    return RO.ResponseObject;
		  }
  public static Object getDwJyPass(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getDwJyPass", PO, Data,CustomObject, AdditiveObject);
	    return RO.ResponseObject;
	  }
  public static Object getUnderDwDeal(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getUnderDwDeal", PO, Data,CustomObject, AdditiveObject);
	    return RO.ResponseObject;
	  }
  
  public static Object getCedwBb(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getCedwBb", PO, Data,CustomObject, AdditiveObject);
	    return RO.ResponseObject;
	  }
  
  public static Object getPzLastDate(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("FIAEServer.ReportObject", "getPzLastDate", PO, Data,CustomObject, AdditiveObject);
	    return RO.ResponseObject;
	  }
  public static Object getZbLastDate(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("FIAESERVER.ReportObject", "getZbLastDate", PO, Data,CustomObject, AdditiveObject);
	    return RO.ResponseObject;
	  }
  public static Object getTradeYMQR(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("TradeServer.ReportObject", "MonthlyCheckYMQR", PO, Data,CustomObject, AdditiveObject);
	    if ( RO.ResponseObject == null ) {
	    	return new ArrayList();
	    }
	    return RO.ResponseObject;
	  }
  public static Object getTradeYsYfYMQR(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("TradeServer.ReportObject", "MonthlyYsYfYMQR", PO, Data,CustomObject, AdditiveObject);
	    if ( RO.ResponseObject == null ) {
	    	return new ArrayList();
	    }
	    return RO.ResponseObject;
	  }  
  public static Object getFiaeYMQR(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("FIAEServer.ReportObject", "MonthlyCheckYMQR", PO, Data,CustomObject, AdditiveObject);
	    if ( RO.ResponseObject == null ) {
	    	return new ArrayList();
	    }
	    return RO.ResponseObject;
	  }

  public static boolean getDwWcByZrzx(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	  	String sSvr = "";
	  	if ( Data != null ) {
	  		sSvr = (String) Data;
	  		if ( !sSvr.trim().equals("")) {
	  			sSvr += ".";
	  		}
	  	} else {
	  		sSvr = GetRealRptSvr();
	  	}
	  	
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(sSvr+"ReportObject", "getDwWcByZrzx", PO, Data,CustomObject, AdditiveObject);
	    return BoolUtil.getBoolean( (String) RO.ResponseObject);
	  }  
  public static boolean getDwWc(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	  	String sSvr = "";
	  	if ( Data != null ) {
	  		sSvr = (String) Data;
	  		if ( !sSvr.trim().equals("")) {
	  			sSvr += ".";
	  		}
	  	} else {
	  		sSvr = GetRealRptSvr();
	  	}
	  	
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(sSvr+"ReportObject", "getDwWc", PO, Data,CustomObject, AdditiveObject);
	    return BoolUtil.getBoolean( (String) RO.ResponseObject);
	  }
  
  public static boolean getBbWc(JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
	    JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getBbWc", PO, Data,CustomObject, AdditiveObject);
	    return BoolUtil.getBoolean( (String) RO.ResponseObject);
	  }

  private void jbInit() throws Exception {
  }

  /**
   * ��¼��棬�����־
   * @param gnbh String
   * @param PO JParamObject
   */
  private static void writeBbLog(String gnbh, JParamObject PO) {
    String mess = "";
    String sjbh = "";
    int type = PO.GetIntByParamName("ADD_TYPE");
    String addBh = PO.GetValueByParamName("ADD_BH");
    String bbbh = PO.GetValueByParamName("BBZD_BH");
    String date = PO.GetValueByParamName("BBZD_DATE");
    String fcbz = "";
    if (gnbh.equals("BBFC")) {
      fcbz = PO.GetValueByParamName("BBFC", "");
    }
    else {
      fcbz = PO.GetValueByParamName("GSFC", "");
    }
    if (fcbz.equals("")) {
      return;
    }
    else if (fcbz.equals("1")) {
      mess = res.getString("String_46");
    }
    else if (fcbz.equals("0")) {
      mess = res.getString("String_48");
    }
    switch (type) {
      case JReportObjectStub.STUB_TYPE_REPORT:
        mess += res.getString("String_49");
        sjbh = ";;;";
        break;
      case JReportObjectStub.STUB_TYPE_DWZD:
        mess += res.getString("String_51");
        sjbh = addBh + ";;;";
        break;
      case JReportObjectStub.STUB_TYPE_LBZD:
        mess += res.getString("String_53");
        sjbh = ";" + addBh + ";;";
        break;
      case JReportObjectStub.STUB_TYPE_BMZD:
        mess += res.getString("String_56");
        sjbh = ";;" + addBh + ";";
        break;
      case JReportObjectStub.STUB_TYPE_CBZD:
        mess += res.getString("String_59");
        sjbh = ";;;" + addBh + ";";
        break;
      default:
        mess += "������\uFFFD";
        sjbh = ";;;;";
    }
    sjbh += bbbh + ";" + date;
    if (gnbh.equals("GSFC")) { //������ʽ
      mess += res.getString("String_66");
    }
    //
    JLogManager.writeDataLog(mess, sjbh);
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

}
