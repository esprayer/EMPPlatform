package jreport.swing.classes.util;

import java.util.*;

import javax.swing.*;

import com.client.fwkview.FMISContentWindow;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;

import jbof.application.classes.*;
import jfoundation.bridge.classes.*;
import jframework.foundation.classes.*;
import jreport.jbof.classes.BOFReportObject.ReportExplorer.*;
import jreport.swing.classes.*;
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

public final class ReportUtil {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.util.Language");

  public static final String[] SYMBOL_NAMES = new String[] {
      "=", "<>", ">", ">=", "<", "<="}; //符号名称

  /**获得校验公式的符号*/
  public static String getSymbolFromJYGS(String value) {
    if (StringUtil.isStartWith(value, SYMBOL_NAMES)) {
      if (value.startsWith("=")) {
        return "=";
      }
      else if (value.startsWith("<>")) {
        return "<>";
      }
      else if (value.startsWith(">")) {
        if (value.startsWith(">=")) {
          return ">=";
        }
        else {
          return ">";
        }
      }
      else if (value.startsWith(">=")) {
        return ">=";
      }
      else if (value.startsWith("<")) {
        if (value.startsWith("<=")) {
          return "<=";
        }
        else {
          return "<";
        }
      }
      else if (value.startsWith("<=")) {
        return "<=";
      }
    }
    return value;

  }

  /**获得校验公式，不包括符号*/
  public static String getJYGSWithoutSymbol(String value) {
    if (StringUtil.isStartWith(value, SYMBOL_NAMES)) {
      if (value.startsWith("=")) {
        return value.substring(1, value.length());
      }
      else if (value.startsWith("<>")) {
        return value.substring(2, value.length());
      }
      else if (value.startsWith(">")) {
        if (value.startsWith(">=")) {
          return value.substring(2, value.length());
        }
        else {
          return value.substring(1, value.length());
        }
      }
      else if (value.startsWith(">=")) {
        return value.substring(2, value.length());
      }
      else if (value.startsWith("<")) {
        if (value.startsWith("<=")) {
          return value.substring(2, value.length());
        }
        else {
          return value.substring(1, value.length());
        }
      }
      else if (value.startsWith("<=")) {
        return value.substring(2, value.length());
      }
    }
    return value;
  }

  /**获得调整公式，不包括符号*/
   public static String getTZGSWithoutSymbol(String value) {
     if (StringUtil.isStartWith(value, SYMBOL_NAMES)) {
       if (value.startsWith("=")) {
         return value.substring(1, value.length());
       }
       else if (value.startsWith("<>")) {
         return value.substring(2, value.length());
       }
       else if (value.startsWith(">")) {
         if (value.startsWith(">=")) {
           return value.substring(2, value.length());
         }
         else {
           return value.substring(1, value.length());
         }
       }
       else if (value.startsWith(">=")) {
         return value.substring(2, value.length());
       }
       else if (value.startsWith("<")) {
         if (value.startsWith("<=")) {
           return value.substring(2, value.length());
         }
         else {
           return value.substring(1, value.length());
         }
       }
       else if (value.startsWith("<=")) {
         return value.substring(2, value.length());
       }
     }
     return value;
   }


  /**获得报表选择的区域*/
  public static int[] getReportViewSelection(JReportView ReportView) {
    try {
      int startRow = ReportView.getSelStartRow();
      int startCol = ReportView.getSelStartCol();
      int endRow = ReportView.getSelEndRow();
      int endCol = ReportView.getSelEndCol();
      int temp = 0;
      if (startRow > endRow) {
        temp = startRow;
        startRow = endRow;
        endRow = temp;
      }
      if (startCol > endCol) {
        temp = startCol;
        startCol = endCol;
        endCol = temp;
      }
      int[] selection = new int[] {
          startRow + 1,
          startCol + 1,
          endRow + 1,
          endCol + 1};
      return selection;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**获得坐标的字符表示*/
  public static String getTextFromCoordinate(int SRow, int SCol, int ERow,
                                             int ECol, JReportView ReportView) {
    try {
      String T1, T2, Text;
      T1 = ReportView.formatRCNr(SRow - 1, SCol - 1, false);
      if (SRow == ERow && SCol == ECol) {
        Text = T1;
      }
      else {
        T2 = ReportView.formatRCNr(ERow - 1, ECol - 1, false);
        Text = T1 + ":" + T2;
      }
      return Text;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**获得实际坐标*/
  public static int[] getCoordinateFromText(String text) {
    if (text != null) {
      text = text.toUpperCase();
      int digitalIndex = StringUtil.getFirstNumberIndex(text);
      if (digitalIndex == -1) {
        int coordl = intID(text);
        return new int[] {
            coordl};
      }
      else {
        int coordl = intID(text.substring(0, digitalIndex));
        int coordh = Integer.parseInt(text.substring(digitalIndex, text.length()));
        return new int[] {
            coordh, coordl};
      }
    }
    return null;
  }

  /**获得坐标的字符表示的实际坐标数字*/
  public static int intID(String sid) {
    int result = 0;
    int carry = 1;
    for (int i = sid.length() - 1; i >= 0; i--) {
      result += ( (sid.charAt(i) - 65) + 1) * carry;
      carry *= 26;
    }
    return result;
  }

  /**获得实际坐标数字的字符表示*/
  public static String strID(int index) {
    String str = "";
    index--;
    str += (char) (65 + index % 26);
    while ( (index = index / 26 - 1) >= 0) {
      str = (char) (65 + index % 26) + str;
    }
    return str;
  }

  /**获得附加列表*/
  public static Vector GetAddList(JContextObject CO) {
    JTable trDateList;
    String CurrentYear, CurrentMonth;
    int[] Paths;
    int Path;
    JReportObjectStub ROS;
    Vector AddList = new Vector();
    trDateList = (JTable) CO.DataObject;
    try {
      Paths = trDateList.getSelectedRows();
      for (int i = 0; i < Paths.length; i++) {
        Path = Paths[i];
        ROS = (JReportObjectStub) trDateList.getModel().getValueAt(Path, 0);
        AddList.add(ROS);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return AddList;
  }

  /**根据年月信息获得月份的天数*/
  public static String getDays(String date) {
    int intY, intM, intD;
    int[] standardDays = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int[] leapyearDays = {
        31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    if (date == null || date.trim().equals("") || date.trim().length() != 6) {
      return null;
    }
    date = date.trim();
    try {
      intY = Integer.parseInt(date.substring(0, 4));
      intM = Integer.parseInt(date.substring(4, 6));
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    if (intM > 12 || intM < 1 || intY < 1) {
      return null;
    }
    if ( (intY % 4 == 0 && intY % 100 != 0) || intY % 400 == 0) {
      return Integer.toString(leapyearDays[intM - 1]);
    }
    return Integer.toString(standardDays[intM - 1]);
  }

  /**获得年度后缀,客户端使用*/
  public String getReportSuffixYear() {
    String suffixYear = "";
    JParamObject PO = JParamObject.Create();
    JResponseObject RO = (JResponseObject) JActiveDComDM.
        AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject",
        "getReportSuffixYear", PO);
    if (RO != null && RO.ErrorCode == 0) {
      suffixYear = (String) RO.ResponseObject;
    }
    return suffixYear;
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

  /**获得登陆日期，客户端使用*/
  public static String getLoginDate() {
    JParamObject PO = JParamObject.Create();
    String loginDate = PO.GetValueByEnvName("LoginDate");
    return loginDate;
  }

  public static int getColCount(String BBZD_BH, String BBZD_DATE) {
    int colCount = -1;
    JParamObject PO = JParamObject.Create();
    PO.SetValueByParamName("BBZD_BH", BBZD_BH);
    PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
    JResponseObject RO = (JResponseObject) JActiveDComDM.
        AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject",
        "getColCount", PO);
    if (RO != null && RO.ErrorCode == 0) {
      colCount = Integer.parseInt( (String) RO.ResponseObject);
    }
    return colCount;
  }

    /**
     * 取当前账的会计年度
     * @return String
     */
    public static String getCurrentKJND() {
	JParamObject PO = JParamObject.Create();
        return PO.GetValueByEnvName("RPT_KJND","");
    }
}
