package jservice.jbof.classes.GenerQueryObject;

import java.awt.*;
import java.awt.event.*;

import com.efounder.eai.data.JResponseObject;
import com.f1j.swing.*;
import jbof.application.classes.*;
import jbof.gui.window.classes.style.mdi.imp.*;
import jservice.jbof.classes.GenerQueryObject.action.*;
import jframework.foundation.classes.JActiveDComDM;
import jservice.jbof.classes.GenerQueryObject.JGenerQueryWindow;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JGenerQueryActions {
  /**
   *
   */
  private JBookActionImpl mActionImpl = new JGenerQueryActionImpl();
  private JBookActionManager mActionMgr = new JBookActionManager(mActionImpl);

  /**
   *
   */
  public JGenerQueryActions() {
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertRow(JContextObject CO, JMenuItemStub MIS,
                                ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_ROW;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertCol(JContextObject CO, JMenuItemStub MIS,
                                ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_COL;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertSheet(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_WORKSHEET;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertPagebreak(JContextObject CO, JMenuItemStub MIS,
                                      ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_PAGE_BREAK;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertHyperlink(JContextObject CO, JMenuItemStub MIS,
                                      ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_HYPER_LINK;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertDraw0(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_GRAPHICS_NORMAL;
    execute(CO, actionName);
  }

  /**
   * 2008-7-16 fengbo
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertDraw1(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow= (JGenerQueryWindow) CO.CustomObject;
    if(queryWindow==null){
      return;
    }
    try {
      Object[] result = new Object[3];
      result[0] = queryWindow.vwQueryView.getTableManager();
      result[1] = queryWindow.vwQueryView.getQueryDataSet();
      result[2] = queryWindow.getChartFormatObject();
      JResponseObject RO = new JResponseObject(result, 0, null);
      queryWindow.PO.SetValueByParamName("WindowCaption", queryWindow.QO.CaptionText + "图表");
      queryWindow.PO.SetValueByParamName("ShowWindow",
                             "com.pansoft.pub.chart.window.QueryChartWindow");
      //用于生成查询图表格式文件名
      queryWindow.PO.SetValueByParamName("QO.PARAM", (String) queryWindow.QO.Param);
      JActiveDComDM.BusinessActiveFramework.MInvokeObjectMethod(
          "GenerQueryObject", "directProcessQuery", "", queryWindow.PO, this, RO);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
//    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_GRAPHICS_CHART;
//    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertDraw2(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.
        ACTION_INSERT_GRAPHICS_PICTURE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertDraw3(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_GRAPHICS_CURVE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertDraw4(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_GRAPHICS_LINE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertDraw5(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_GRAPHICS_ROUND;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertDraw6(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.
        ACTION_INSERT_GRAPHICS_POLYGON;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertDraw7(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.
        ACTION_INSERT_GRAPHICS_RECTANGLE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnFind(JContextObject CO, JMenuItemStub MIS, ActionEvent AE,
                           Object CustomObject) {
    String actionName = JBookActionConstants.ACTION_FIND;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnFreezePanes(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE,
                                  Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_FREEZE_PANES;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDefreezePanes(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE,
                                    Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_DEFREEZE_PANES;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnReplace(JContextObject CO, JMenuItemStub MIS,
                              ActionEvent AE,
                              Object CustomObject) {
    String actionName = JBookActionConstants.ACTION_REPLACE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertObject1(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_FORM_BUTTON;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertObject2(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_FORM_CHECKBOX;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertObject3(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_FORM_COMBOBOX;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertObject4(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_FORM_LIST;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertObject5(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.
        ACTION_INSERT_FORM_RADIOBUTTON;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnInsertObject6(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_INSERT_FORM_TEXTAREA;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnCellFormat(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_CELL;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDelete(JContextObject CO, JMenuItemStub MIS,
                             ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_DELETE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDeleteRow(JContextObject CO, JMenuItemStub MIS,
                                ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_DELETE_ROW;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDeleteCol(JContextObject CO, JMenuItemStub MIS,
                                ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_DELETE_ROW;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDeleteSheet(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_DELETE_WORKSHEET;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDeletePagebreak(JContextObject CO, JMenuItemStub MIS,
                                      ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_DELETE_PAGE_BREAK;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnRowFormat1(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_ROW_HEIGHT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnRowFormat2(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_ROW_PREFER_HEIGHT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnRowFormat3(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_ROW_HIDE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnRowFormat4(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_ROW_SHOW;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnColFormat1(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_COL_WIDTH;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnColFormat2(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_COL_PREFER_WIDTH;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnColFormat3(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_COL_HIDE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnColFormat4(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_COL_SHOW;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnSheetFormat1(JContextObject CO, JMenuItemStub MIS,
                                   ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_WORKSHEET_RENAME;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnSheetFormat2(JContextObject CO, JMenuItemStub MIS,
                                   ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_WORKSHEET_HIDE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnSheetFormat3(JContextObject CO, JMenuItemStub MIS,
                                   ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_WORKSHEET_SHOW;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnSheetFormat5(JContextObject CO, JMenuItemStub MIS,
                                   ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_WORKSHEET_ATTRIBUTE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnObjectFormat1(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_OBJECT_OPTION;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnObjectFormat2(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_OBJECT_BRING2FRONT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnObjectFormat3(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_OBJECT_SEND2BACK;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnObjectFormat4(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_OBJECT_UNGROUP;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnObjectFormat5(JContextObject CO, JMenuItemStub MIS,
                                    ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_OBJECT_GROUP;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnPrintArea1(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.PRINT.ACTION_AREA;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnPrintArea2(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.PRINT.ACTION_CANCEL_AREA;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnPrintTitle(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.PRINT.ACTION_TITLE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnPageSetup(JContextObject CO, JMenuItemStub MIS,
                                ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.PRINT.ACTION_PAGESETUP;
    executePrint(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnPrintPreview(JContextObject CO, JMenuItemStub MIS,
                                   ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.PRINT.ACTION_PREWVIEW;
    executePrint(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnPrint(JContextObject CO, JMenuItemStub MIS,
                            ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.PRINT.ACTION_PRINT;
    executePrint(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnGoto(JContextObject CO, JMenuItemStub MIS, ActionEvent AE,
                           Object CustomObject) {
    String actionName = JBookActionConstants.ACTION_GOTO;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnBold(JContextObject CO, JMenuItemStub MIS, ActionEvent AE,
                           Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_BOLD;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnItalic(JContextObject CO, JMenuItemStub MIS,
                             ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_ITALIC;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnUnderline(JContextObject CO, JMenuItemStub MIS,
                                ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_UNDERLINE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnLeft(JContextObject CO, JMenuItemStub MIS, ActionEvent AE,
                           Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_LEFT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnCenter(JContextObject CO, JMenuItemStub MIS,
                             ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_CENTER;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnRight(JContextObject CO, JMenuItemStub MIS,
                            ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_RIGHT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnUnite(JContextObject CO, JMenuItemStub MIS,
                            ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_MERGE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnLeftToRight(JContextObject CO, JMenuItemStub MIS,
                                  ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_LEFT2RIGHT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnUpToDown(JContextObject CO, JMenuItemStub MIS,
                               ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_TOP2BOTTOM;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnMoney(JContextObject CO, JMenuItemStub MIS,
                            ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_MONEY;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnPercent(JContextObject CO, JMenuItemStub MIS,
                              ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_PERCENT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDot(JContextObject CO, JMenuItemStub MIS, ActionEvent AE,
                          Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_DOT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnAddZero(JContextObject CO, JMenuItemStub MIS,
                              ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_ADD_ZERO;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDeleteZero(JContextObject CO, JMenuItemStub MIS,
                                 ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_DELETE_ZERO;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnSubSpace(JContextObject CO, JMenuItemStub MIS,
                               ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_SUB_SPACE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnAddSpace(JContextObject CO, JMenuItemStub MIS,
                               ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_ADD_SPACE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnBorder(JContextObject CO, JMenuItemStub MIS,
                             ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_BORDER;
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        int X = MIS.Button.getLocationOnScreen().x;
        int Y = MIS.Button.getLocationOnScreen().y + MIS.Button.getHeight();
        Point location = new Point(X, Y);
        if (book != null) {
          JBookAction action = new JBookAction(book, actionName, location);
          mActionMgr.execute(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnFontColor(JContextObject CO, JMenuItemStub MIS,
                                ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_FONT_COLOR;
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        int X = MIS.Button.getLocationOnScreen().x;
        int Y = MIS.Button.getLocationOnScreen().y + MIS.Button.getHeight();
        Point location = new Point(X, Y);
        if (book != null) {
          JBookAction action = new JBookAction(book, actionName, location);
          mActionMgr.execute(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnBackColor(JContextObject CO, JMenuItemStub MIS,
                                ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_BACK_COLOR;
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        int X = MIS.Button.getLocationOnScreen().x;
        int Y = MIS.Button.getLocationOnScreen().y + MIS.Button.getHeight();
        Point location = new Point(X, Y);
        if (book != null) {
          JBookAction action = new JBookAction(book, actionName, location);
          mActionMgr.execute(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnTop(JContextObject CO, JMenuItemStub MIS, ActionEvent AE,
                          Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_TOP;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnVCenter(JContextObject CO, JMenuItemStub MIS,
                              ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_VERTICAL_CENTER;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDown(JContextObject CO, JMenuItemStub MIS, ActionEvent AE,
                           Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_BOTTOM;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnDraw(JContextObject CO, JMenuItemStub MIS, ActionEvent AE,
                           Object CustomObject) {
    String actionName = JBookActionConstants.FORMAT.ACTION_GRAPHICS;
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, actionName, CO);
          mActionMgr.execute(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnExport(JContextObject CO, JMenuItemStub MIS,
                             ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        String actionName = JBookActionConstants.ACTION_EXPORT;
        String defaultName = queryWindow.getTitle();
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, actionName, defaultName);
          mActionMgr.execute(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnCopy(JContextObject CO, JMenuItemStub MIS,
                           ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_COPY;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnCut(JContextObject CO, JMenuItemStub MIS,
                          ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_CUT;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnPaste(JContextObject CO, JMenuItemStub MIS,
                            ActionEvent AE, Object CustomObject) {
    String actionName = JBookActionConstants.EDIT.ACTION_PASTE;
    execute(CO, actionName);
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */

  public void ActionmnQueryXJ(JContextObject CO, JMenuItemStub MIS,
                              ActionEvent AE, Object CustomObject) {

  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */

  public void ActionmnSort(JContextObject CO, JMenuItemStub MIS,
                           ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, "", queryWindow);
          ( (JGenerQueryActionImpl) mActionImpl).onSort(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */

  public void ActionmnFilter(JContextObject CO, JMenuItemStub MIS,
                             ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, "", queryWindow);
          ( (JGenerQueryActionImpl) mActionImpl).onFilter(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * 取消过滤
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnCancelFilter(JContextObject CO, JMenuItemStub MIS,
                                   ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, "", queryWindow);
          ( (JGenerQueryActionImpl) mActionImpl).onCancelFilter(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */

  public void ActionmnReQuery(JContextObject CO, JMenuItemStub MIS,
                              ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, "", queryWindow);
          ( (JGenerQueryActionImpl) mActionImpl).onRequery(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */

  public void ActionmnStyle(JContextObject CO, JMenuItemStub MIS,
                            ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, "", queryWindow);
          ( (JGenerQueryActionImpl) mActionImpl).onStyle(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   *
   * @param CO
   * @param actionName
   */
  private void execute(JContextObject CO, String actionName) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, actionName, queryWindow);
          mActionMgr.execute(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param CO
   * @param actionName
   */
  private void executePrint(JContextObject CO, String actionName) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          JBookAction action = new JBookAction(book, actionName, queryWindow);
          mActionMgr.execute(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnEditReadOnly(JContextObject CO, JMenuItemStub MIS,
                                   ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          book.setAllowInCellEditing(false);
//                    book.setEnableProtection(true);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param CO
   * @param MIS
   * @param AE
   * @param CustomObject
   */
  public void ActionmnEditWritable(JContextObject CO, JMenuItemStub MIS,
                                   ActionEvent AE, Object CustomObject) {
    JGenerQueryWindow queryWindow = (JGenerQueryWindow) CO.CustomObject;
    try {
      if (queryWindow != null && queryWindow.vwQueryView != null) {
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();
        if (book != null) {
          book.setAllowInCellEditing(true);
//                    book.setEnableProtection(false);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
