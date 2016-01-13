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
  public final static String BBWC = "RT2040"; //报表完成
  public final static String BBFC = "RT2050"; //报表封存
  public final static String BBFS = "RT2055"; //报表公式封存
  public final static String BBJS = "RT2060"; //报表解锁
  public final static String BBXF = "RT6666"; //报表下发,有这个权限的人，可以设置报表为下发状态，并且不受下发报表不能修改报表的限制
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
   * 报表封存应该只封存公式计算，公式校验应该放开
   * modified by hufeng 2005.11.25 at SCSY
   */
  public static final String[] CALC_MENUITEM_NAMES = new String[] {
      "mnCalcReport", "mnCalcSelect","mnDealReport" 
  };
  public static final String[] ADJUST_MENUITEM_NAMES = new String[] {
      "mnAdjustReport" 
  };

  /**
   * 报表汇总菜单
   * 需要加入对汇总权限的判断
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
   * 系统报表专用菜单
   */
  public static final String[] SYSTEM_REPORT_MENUS = new String[] {
      "mnPrintArea", "mnPrintArea1", "mnPrintArea2", "mnPrintTitle",
      "mnSplitDefine","mnSplitClear","mnBDQSetup1","mnBDQSetup4",
      "mnInsertCol","mnDeleteCol"
  };


  /**
   * 检测选择报表
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
   * 检测报表功能权限
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
