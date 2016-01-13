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
   *  ��ָ��λ�û�ȡCellEditor
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
      // 0:������ 1:�ɰ�䶯�� 2:�°�䶯���� 3:�°�䶯��
      // 4:�̶��е���Ҫʹ�������б�
//      int RowStatus = JBdhUtils.checkPhyRowStatus(RM,row);
      /**
       * ����Ǳ䶯��������ʾ�����б�
       * ֻ����ϵͳ�����²���ʾ�б�
       * �ڵ�λ��������ʾ�û�ʹ�ò�����
       * modified by hufeng 2008.3.26
       */
      if (  RowStatus == 2 && RM.ADD_TYPE == JReportModel.STUB_TYPE_REPORT) {
        int PriPhyRow = JBdhUtils.getPhyNewBdhPrioRow(RM,row);
        int PriLogRow = JBdhUtils.getLogRow(RM,PriPhyRow);
        // ��ȡ�°�䶯������������Ӧ�еĵ�ԪUI
        /**
         * ��ö���ֵ���Ϣ����ڱ䶯��������
         * BS���Ǵ���ڵ�һ�б䶯���ϵģ�Ϊ����CS���ݣ����ڸ�Ϊ����ڱ䶯������
         */
//        JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(PriLogRow+1,col+1,1,RM);
        JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(PriLogRow,col+1,1,RM);
        CellEditor = new JBDCellComboBox();
        // ������Ӧ�е�UI����
        CellEditor.setCustomObject(UI);
      }
      if ( RowStatus == 3 ) {// ����Ǳ�汾�䶯�������п�����Ҫ����Combox������
        // ��ȡ�䶯��������
        int PriPhyRow = JBdhUtils.getPhyNewBdhPrioRow(RM,row);
        int PriLogRow = JBdhUtils.getLogRow(RM,PriPhyRow);
        // ��ȡ�°�䶯������������Ӧ�еĵ�ԪUI
        JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(PriLogRow,col+1,RM);
        // ����䶯�����е���Ӧ�е�UI��Ϊ�գ�����DYZD_COMBO��Ϊ�գ�����Ҫ��������
        if ( UI != null && UI.DYZD_COMBO != null && !"".equals(UI.DYZD_COMBO.trim()) ) {
          /**
           * Ӧ���Ź�˾Ҫ����ö���ֵ��м�������ѡ���
           * add by hufeng 2005.12.27
           */
          if(UI.DYZD_COMBO.indexOf("EM_DATE_") >= 0){
            CellEditor = new JCellDateComboBox();
          }else{
            CellEditor = new JCellComboBox();
          }
          // ������Ӧ�е�UI����
          CellEditor.setCustomObject(UI);
        }
      }
      if ( RowStatus == 4 ) {// ����ǹ̶���ʹ�������б��������ڵ�λ�����У����п�����Ҫ����Combox������
       JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(row+1,col+1,RM);
        if ( RM.ADD_TYPE == JReportModel.STUB_TYPE_REPORT ) {
          if (UI.DYZD_MJZD) { //���û�ʹ����ö���ֵ�
              CellEditor = new JBDCellComboBox();
              CellEditor.setCustomObject(UI);
          }
        }
        if ( RM.ADD_TYPE == JReportModel.STUB_TYPE_DWZD ) {
          JUnitInfo AddUI = JAddUnitInfo.GetUnitInfoByHL(row+1,col+1,1,RM);
          AddUI.DYZD_COMBO = UI.DYZD_COMBO;
          AddUI.DYZD_MJZD  = UI.DYZD_MJZD;
          if (AddUI.DYZD_MJZD) { //���û�ʹ����ö���ֵ�
              CellEditor = new JCellComboBox();
              CellEditor.setCustomObject(AddUI);
          }
        }
      }
    }
    return CellEditor;
  }
  /**
   * ���ݻ�ȡ��CellEditor��ָ��λ�ã�׼��CellEditor�����
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
       * �����������״̬��ֱ�ӷ��أ�������
       * --
       * ������ݲ�����༭������Ҫ�����µı༭��
       * ���������ݷ��󣬻������޸ľ�������
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
      // ��һ������鵱ǰ��Ԫ�������Ƿ�Ϸ�
      checkCellData(ReportView, Row, Col);
      // �ڶ�����ɾ������һ�β��������
      removeCellEditor(ReportView, Row, Col);
      // ������������ڵ�ǰ��Ԫ���Ƿ���Ҫ����CellEditor������
      ICellEditor CellEditor = getCellEditor(ReportView,Row,Col,RowStatus);
      // ���CellEditor��Ϊ�գ�˵����Ҫ����CellEditor�Ĵ���
      if ( CellEditor != null ) {
        // ����ǰ��ȡ��CellEditor����F1�ĵ�ǰѡ��ĵ�Ԫ����
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
      // ���ӵ�ReportView��
      ReportView.add(EditComp);
      ReportView.setCellEditor(CellEditor);
      // ������Ӧ��λ��
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
   * ��鵱ǰ��Ԫ��������Ƿ�Ϸ�
   * @param ReportView JReportView
   */
  private static void checkCellData(JReportView ReportView,int Row,int Col){
    ICellEditor CE = ReportView.getCellEditor();
    if ( CE != null ) {
      CE.endEditing(Row,Col,null,null);
    }
  }
}
