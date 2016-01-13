package jreport.swing.classes;

import jreport.swing.classes.ReportBook.*;
import com.f1j.swing.*;
import java.awt.*;
import com.f1j.ss.*;
import jreport.foundation.classes.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JReportViewEditor {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.Language");
  public JReportViewEditor() {
  }
  public static void hideCSGRObject(JReportView ReportView) {
    GRObject GR = ReportView.getObject("RPTCOMBO");
    if ( GR != null ) {
      GR.setVisible(false);
    }
  }
  /**
   *  从指定位置获取CellEditor
   * @param ReportView JReportView
   * @param row int
   * @param col int
   * @return ICellEditor
   */
  public static ICellEditor getCellEditor(JReportView ReportView,int row,int col,int RowStatus) {
    //
    JReportDataModel RDM = ReportView.getModel();
    ICellEditor CellEditor = null;
    if ( RDM instanceof JReportModel ) {
      JReportModel RM = (JReportModel)RDM;
      // 0:正常行 1:旧版变动行 2:新版变动主行 3:新版变动行
      // 4:固定行但是要使用下拉列表
//      int RowStatus = JBdhUtils.checkPhyRowStatus(RM,row);
      /**
       * 如果是变动区主行显示下拉列表
       * 只有在系统报表下才显示列表
       * 在单位报表中显示用户使用不方便
       * modified by hufeng 2008.3.26
       */
      if (  RowStatus == 2 && RM.ADD_TYPE == JReportModel.STUB_TYPE_REPORT) {
        int PriPhyRow = JBdhUtils.getPhyNewBdhPrioRow(RM,row);
        int PriLogRow = JBdhUtils.getLogRow(RM,PriPhyRow);
        // 获取新版变动区主的所在相应列的单元UI
        /**
         * 将枚举字典信息存放在变动主行上面
         * BS中是存放在第一行变动行上的，为了与CS兼容，现在改为存放在变动主行上
         */
//        JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(PriLogRow+1,col+1,1,RM);
        JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(PriLogRow,col+1,1,RM);
        CellEditor = new JBDCellComboBox();
        // 设置相应列的UI对象
        CellEditor.setCustomObject(UI);
      }
      if ( RowStatus == 3 ) {// 如果是变版本变动区，则有可能需要进行Combox的设置
        // 获取变动区的主行
        int PriPhyRow = JBdhUtils.getPhyNewBdhPrioRow(RM,row);
        int PriLogRow = JBdhUtils.getLogRow(RM,PriPhyRow);
        // 获取新版变动区主的所在相应列的单元UI
        JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(PriLogRow,col+1,RM);
        // 如果变动区主行的相应列的UI不为空，并且DYZD_COMBO不为空，则需要进行设置
        if ( UI != null && UI.DYZD_COMBO != null && !"".equals(UI.DYZD_COMBO.trim()) ) {
          /**
           * 应集团公司要求，在枚举字典中加入日期选择框
           * add by hufeng 2005.12.27
           */
          if(UI.DYZD_COMBO.indexOf("EM_DATE_") >= 0){
            CellEditor = new JCellDateComboBox();
          }else{
            CellEditor = new JCellComboBox();
          }
          // 设置相应列的UI对象
          CellEditor.setCustomObject(UI);
        }
      }
      if ( RowStatus == 4 ) {// 如果是固定行使用下拉列表，并且是在单位报表中，则有可能需要进行Combox的设置
       JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(row+1,col+1,RM);
        if ( RM.ADD_TYPE == JReportModel.STUB_TYPE_REPORT ) {
          if (UI.DYZD_MJZD) { //设置或使用了枚举字典
              CellEditor = new JBDCellComboBox();
              CellEditor.setCustomObject(UI);
          }
        }
        if ( RM.ADD_TYPE == JReportModel.STUB_TYPE_DWZD ) {
          JUnitInfo AddUI = JAddUnitInfo.GetUnitInfoByHL(row+1,col+1,1,RM);
          AddUI.DYZD_COMBO = UI.DYZD_COMBO;
          AddUI.DYZD_MJZD  = UI.DYZD_MJZD;
          if (AddUI.DYZD_MJZD) { //设置或使用了枚举字典
              CellEditor = new JCellComboBox();
              CellEditor.setCustomObject(AddUI);
          }
        }
      }
    }
    return CellEditor;
  }
  /**
   * 根据获取的CellEditor和指定位置，准备CellEditor的组件
   * @param ReportView JReportView
   * @param CellEditor ICellEditor
   * @param Row int
   * @param Col int
   * @return Component
   */
  public static java.awt.Component prepareEditor(JReportView ReportView,ICellEditor CellEditor,int Row,int Col,int RowStatus) {
    JUnitInfo BDUI = null;java.awt.Component comp = null;
    JReportDataModel RDM = ReportView.getModel();
    JReportModel RM = (JReportModel)RDM;
    if ( RowStatus == 3 ) {
      int logSRow = JBdhUtils.getLogRow(RM,Row);
      int logSCol = Col;
      BDUI = JBdhInfo.getBDUnitInfo(RM, logSRow - 1, logSCol, Row, Col);
      comp = CellEditor.getEditorComponent(ReportView, BDUI, true, Row, Col);
    }
    if ( RowStatus == 2 ) {
      comp = CellEditor.getEditorComponent(ReportView, null, true, Row, Col);
    }
    if (  RowStatus == 4 ) {
        if ( RM.ADD_TYPE == JReportModel.STUB_TYPE_REPORT ) {
            comp = CellEditor.getEditorComponent(ReportView, null, true, Row, Col);
        }
        if ( RM.ADD_TYPE == JReportModel.STUB_TYPE_DWZD ) {
            BDUI = JAddUnitInfo.GetUnitInfoByHL(Row+1,Col+1,1,RM);
            comp = CellEditor.getEditorComponent(ReportView, BDUI, true, Row, Col);
        }
    }
    return comp;
  }
  /**
   *
   * @param ReportView JReportView
   * @param selectionChangedEvent SelectionChangedEvent
   */
  public static void selectionChanged(JReportView ReportView,SelectionChangedEvent selectionChangedEvent) {
    try {
      JReportDataModel RDM = ReportView.getModel();
      if ( !(RDM instanceof JReportModel) ) return;
      JReportModel RM = (JReportModel)RDM;
      /**
       * 如果不是数据状态，直接返回，不处理
       * --
       * 如果数据不允许编辑，则不需要创建新的编辑器
       * 以免在数据封存后，还可以修改具体数据
       * modified by hufeng 2008.7.30
       */
      if ( RM.getDataStatus() != RM.MODEL_STATUS_DATA ||
          RM.isAllowEditData() == false) {
          return;
      }
      int Row = ReportView.getPhyRow();
      int Col = ReportView.getCol();
      int RowStatus = JBdhUtils.checkPhyRowStatus(RM,Row);
      if ( RowStatus == 0 ) {
          JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(Row+1,Col+1,RM);
          if ( UI != null ) {
              if (UI.DYZD_MJZD) {
                  RowStatus = 4;
              }
          }
      }
      // 第一步，检查当前单元格数据是否合法
      checkCellData(ReportView, Row, Col);
      // 第二步，删除掉上一次产生的组件
      removeCellEditor(ReportView, Row, Col);
      // 第三步，检查在当前单元格是否需要进行CellEditor的设置
      ICellEditor CellEditor = getCellEditor(ReportView,Row,Col,RowStatus);
      // 如果CellEditor不为空，说明需要进行CellEditor的处理
      if ( CellEditor != null ) {
        // 将当前获取的CellEditor设置F1的当前选择的单元格上
        addCellEditor(ReportView,CellEditor,Row,Col,RowStatus);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  private static void addCellEditor(JReportView ReportView,ICellEditor CellEditor,int Row,int Col,int RowStatus) {
    java.awt.Component EditComp = prepareEditor(ReportView,CellEditor,Row,Col,RowStatus);
    java.awt.Rectangle rect = new java.awt.Rectangle();
    try {
      // 增加到ReportView中
      ReportView.add(EditComp);
      ReportView.setCellEditor(CellEditor);
      // 设置相应的位置
      ReportView.rangeToPixels(Row, Col, Row, Col,rect);
      EditComp.setSize(rect.width,rect.height);
      EditComp.setLocation(rect.x,rect.y);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  private static void removeCellEditor(JReportView ReportView,int Row,int Col) {
    ICellEditor CE = ReportView.getCellEditor();
    if ( CE != null ) {
//      CE.show(false);
      java.awt.Rectangle rect = CE.getComponent().getBounds();
      ReportView.remove(CE.getComponent());
      ReportView.repaint(rect.x,rect.y,rect.width,rect.height);
    }
  }

  /**
   * 检查当前单元格的数据是否合法
   * @param ReportView JReportView
   */
  private static void checkCellData(JReportView ReportView,int Row,int Col){
    ICellEditor CE = ReportView.getCellEditor();
    if ( CE != null ) {
      CE.endEditing(Row,Col,null,null);
    }
  }
}
