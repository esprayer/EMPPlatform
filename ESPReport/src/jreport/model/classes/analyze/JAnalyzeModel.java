package jreport.model.classes.analyze;

import com.efounder.eai.data.JParamObject;
import com.report.table.jxml.TableManager;

import jreport.swing.classes.*;
import jframework.foundation.classes.*;
import jservice.jbof.classes.GenerQueryObject.*;
import jdatareport.dof.classes.report.QueryDataSet;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author hufeng
 * @version 1.0
 */

public class JAnalyzeModel extends JReportModel {
  public JAnalyzeModel() {
  }

  public int OpenReportObject(Object roe, JParamObject PO) {
    try {
      Object[] ob = (Object[]) roe;
      String[] data = (String[]) ob[0];
      JGenerQueryWindow QueryWindow = new JGenerQueryWindow();
      //初始化参数
      init(PO);
      // 设置应用
      QueryWindow.setApplication(JActiveDComDM.MainApplication);
      Object []obj = QueryWindow.showData(data[0],data[1]);
      QueryWindow.vwQueryView.openTreeBookData((TableManager)obj[0], (QueryDataSet)obj[1]);
//      QueryWindow.vwQueryView.openTreeBookData(data[0], data[1]);
      /**
       * 改为可修改
       * modified by hufeng 2005.8.29
       */
//      QueryWindow.vwQueryView.getDataReport().getBook().setEnableProtection(true);
	  QueryWindow.vwQueryView.getDataReport().getBook().setAllowInCellEditing(false);
      ReportView.copyAll(QueryWindow.vwQueryView.getDataReport().getBook());
      ReportView.repaint();

      /**
       * 设置最大行最大列
       */
//      if (m_rowCount > 0 && m_rows.size() > 0) {
//        ReportView.setMaxRow(m_rowCount + 3);
//        ReportView.setMaxCol(m_rows.size() + 1);
//      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * 初始化参数
   * @param PO JParamObject
   * @throws Exception
   */
  private void init(JParamObject PO) throws Exception {
  }

}
