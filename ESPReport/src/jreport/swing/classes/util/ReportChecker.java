package jreport.swing.classes.util;

import javax.swing.*;

import com.client.fwkview.FMISContentWindow;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;

import jbof.application.classes.*;
import jfoundation.bridge.classes.*;
import jframework.foundation.classes.*;
import jreportwh.jdof.classes.common.util.*;
import java.util.ResourceBundle;
import jbof.gui.window.classes.JBOFChildWindow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class ReportChecker {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.util.Language");
  public final static String BBWC = "RT2040"; //�������
  public final static String BBFC = "RT2050"; //������
  public final static String BBFS = "RT2055"; //����ʽ���
  public final static String BBJS = "RT2060"; //�������
  public final static String BBXF = "RT6666"; //�����·�,�����Ȩ�޵��ˣ��������ñ���Ϊ�·�״̬�����Ҳ����·��������޸ı��������
  public static final String[] PRINT_MENUITEM_NAMES = new String[] {
      "mnPrintArea", "mnPrintArea1", "mnPrintArea2", "mnPrintTitle"
  };
  public static final String[] MODIFY_MENUITEM_NAMES = new String[] {
	  "mnInsertRow", "mnInsertCol", "mnInsertPagebreak", "mnInsertHyperlink",
	  "mnInsertDraw1",
	  "mnInsertDraw2", "mnInsertDraw0", "mnInsertDraw1", "mnInsertDraw2",
	  "mnInsertDraw3",
	  "mnInsertDraw4", "mnInsertDraw5", "mnInsertDraw6", "mnInsertDraw7",
	  "mnInsertObject1",
	  "mnInsertObject2", "mnInsertObject3", "mnInsertObject4",
	  "mnInsertObject5", "mnInsertObject6",
	  "mnCellFormat", "mnDeleteRow", "mnDeleteCol", "mnDeleteSheet",
	  "mnDeletePagebreak",
	  "mnRowFormat1", "mnRowFormat2", "mnRowFormat3", "mnRowFormat4",
	  "mnColFormat1",
	  "mnColFormat2", "mnColFormat3", "mnColFormat4", "mnSheetFormat1",
	  "mnSheetFormat2",
	  "mnSheetFormat3", "mnSheetFormat5", "mnObjectFormat1", "mnObjectFormat2",
	  "mnObjectFormat3",
	  "mnObjectFormat4", "mnObjectFormat5",
  };

  public static final String[] MODIFY_MENUITEM_NAMES_WITHOUT_ROWCOL = new String[] {
      "mnInsertRow", "mnInsertCol", "mnInsertPagebreak", "mnInsertHyperlink",
      "mnInsertDraw1",
      "mnInsertDraw2", "mnInsertDraw0", "mnInsertDraw1", "mnInsertDraw2",
      "mnInsertDraw3",
      "mnInsertDraw4", "mnInsertDraw5", "mnInsertDraw6", "mnInsertDraw7",
      "mnInsertObject1",
      "mnInsertObject2", "mnInsertObject3", "mnInsertObject4",
      "mnInsertObject5", "mnInsertObject6",
      "mnCellFormat", "mnDeleteRow", "mnDeleteCol", "mnDeleteSheet",
      "mnDeletePagebreak",
/*
      "mnRowFormat1", "mnRowFormat2", "mnRowFormat3", "mnRowFormat4",
      "mnColFormat1",
      "mnColFormat2", "mnColFormat3", "mnColFormat4", "mnSheetFormat1",
 */
      "mnSheetFormat2",
      "mnSheetFormat3", "mnSheetFormat5", "mnObjectFormat1", "mnObjectFormat2",
      "mnObjectFormat3",
      "mnObjectFormat4", "mnObjectFormat5",
  };
  public static final String[] MODIFY_TOOLBARBTN_NAMES = new String[] {
      "mnBold", "mnItalic", "mnUnderline", "mnLeft", "mnCenter",
      "mnRight", "mnUnite", "mnLeftToRight", "mnUpToDown", "mnMoney",
      "mnPercent", "mnDot", "mnAddZero", "mnDelZero", "mnSubSpace",
      "mnAddSpace", "mnBorder", "mnFontColor", "mnBackColor", "mnTop",
      "mnVCenter", "mnDown", "mnDraw",
  };
  public static final String[] MODIFY_POPUPMENUITEM_NAMES = new String[] {
      "mnInsertRow", "mnInsertCol", "mnDeleteRow", "mnDeleteCol",
      "mnCellFormat", "mnUnKnow"
  };
  /**
   * ������Ӧ��ֻ��湫ʽ���㣬��ʽУ��Ӧ�÷ſ�
   * modified by hufeng 2005.11.25 at SCSY
   */
  public static final String[] CALC_MENUITEM_NAMES = new String[] {
      "mnCalcReport", "mnCalcSelect","mnDealReport" 
  };
  public static final String[] ADJUST_MENUITEM_NAMES = new String[] {
      "mnAdjustReport" 
  };

  /**
   * ������ܲ˵�
   * ��Ҫ����Ի���Ȩ�޵��ж�
   * modified by hufeng 2008.6.5
   */
  public static final String[] COUNT_MENUITEM_NAMES = new String[] {
      "mnCountReport"
  };

  public static final String[] FUNC_MENUITEM_NAMESmenuItemNames = new String[] {
      "mnInsertFunction", "mnInsertExtData"
  };
  public static final String[] BBFC_ENABLEOTHER = new String[] {
      "mnInsertSheet", "mnBDQSetup1", "mnBDQSetup3", "mnBDQSetup4", "mnSort1",
      "mnSort2",
	  "mnReplace", "mnDelete", "mnClearsj", "mnSaveAsJygs",
      "mnSaveAsJsgs","mnSaveAsZsgs", "mnPaste", "mnPasteSelect", "mnCut"/*, "mnCalcSelect","mnCountReport"*/
  };

  /**
   * ϵͳ����ר�ò˵�
   */
  public static final String[] SYSTEM_REPORT_MENUS = new String[] {
      "mnPrintArea", "mnPrintArea1", "mnPrintArea2", "mnPrintTitle",
      "mnSplitDefine","mnSplitClear","mnBDQSetup1","mnBDQSetup4",
      "mnInsertCol","mnDeleteCol"
  };


  /**
   * ���ѡ�񱨱�
   * @param CO
   * @return
   */
  public static boolean hasReportSelection(JContextObject CO) {
    JTable reportTable = (JTable) CO.ParamObject;
    if (UIUtil.hasTableRowSelection(reportTable)) {
      return true;
    }
    else {
      JOptionPane.showMessageDialog(CO.Application.MainWindow, res.getString("String_101"), res.getString("String_102"),
                                    JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
  }

  public static boolean hasAddSelection(JContextObject CO) {
    JTable addTable = (JTable) CO.DataObject;
    if (UIUtil.hasTreeTableRowSelection(addTable)) {
      return true;
    }
    else {
      JOptionPane.showMessageDialog(CO.Application.MainWindow, res.getString("String_103"), res.getString("String_104"),
                                    JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
  }

  /**
   * ��ⱨ����Ȩ��
   * @param PO
   * @return
   */
  public static boolean isAllowsChangeReportProperty(JParamObject PO) {
	  //JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("SecurityObject","CheckFunctionLimit", PO);
	  JResponseObject RO = (JResponseObject) JActiveDComDM.
		  AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject",
		  "CheckFunctionLimit", PO);
	  if (RO != null && RO.GetErrorCode() == 0) {
		  return true;
	  }
	  else {
		  return false;
	  }
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
