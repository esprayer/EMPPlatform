package jservice.jbof.classes.GenerQueryObject;

import com.report.table.jxml.*;
import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import jdatareport.dof.classes.report.*;
import jdatareport.dof.classes.*;
import jdatareport.dof.classes.report.util.*;
import java.net.URL;
import jdatareport.dof.classes.report.paint.JDRQueryPainterUtils;
import com.f1j.ss.CellFormat;
import jdatareport.dof.classes.report.paint.JDRPainter;

/**
 * 此处插入类型描述。
 * 创建日期：(2002-1-21 13:01:46)
 * @author：lubo
 */
public class MultiLevelHeaderTreeTable
    extends JPanel {
  //属性
  private JDataReport report = null;
  private TableManager tableManager = null;
  private QueryDataSet queryDataSet;

  public TableManager getTableManager() {
    return tableManager;
  }

  public QueryDataSet getQueryDataSet(){
    return this.queryDataSet;
  }
  public JDataReport getDataReport() {
    return report;
  }

  //构造函数
  public MultiLevelHeaderTreeTable() {
    super(new BorderLayout());
  }
  protected JDRPainter mPainter = null;
  /**
   *
   * @param painter JDRPainter
   */
  public void setJDRPainter(JDRPainter painter) {
    mPainter = painter;
  }
  /**
   *
   * @return JDRPainter
   */
  public JDRPainter getJDRPainter() {
    return mPainter;
  }
//  /**
//   * 初始化数据
//   * @param sTable String 格式xml字符串
//   * @param txt String       文本数据
//   * @throws Exception
//   */
//  public void openTreeBookTXTData(String sTable, String txt) throws Exception {
//    if (report != null) {
//      this.remove(report);
//    }
//    tableManager = new TableManager(sTable);
//    //构造数据集
//    this.queryDataSet =
//        QueryDataTransformer.getQueryNodeFromTXTString(getViewIDs(tableManager),txt);
//    JDRModel model = new JDRModel(tableManager, queryDataSet);
//    model.setQueryFormat(new JDRQueryFormatInfo());
//    report = new JDataReport(model);
//    report.load();
//    this.add(report, BorderLayout.CENTER);
//  }
//
//  /**
//   * 初始化数据
//   * @param sTable String 格式xml字符串
//   * @param txt String       文本数据
//   * @throws Exception
//   */
//  public void openTreeBookXMLData(String sTable, String xmlStr) throws Exception {
//    if (report != null) {
//      this.remove(report);
//    }
//    tableManager = new TableManager(sTable);
//    //构造数据集
//    this.queryDataSet =
//        QueryDataTransformer.getQueryNodeFromXMLString(getViewIDs(tableManager),xmlStr);
//    JDRModel model = new JDRModel(tableManager, queryDataSet);
//    model.setQueryFormat(new JDRQueryFormatInfo());
//    report = new JDataReport(model);
//    report.load();
//    this.add(report, BorderLayout.CENTER);
//  }
//
//  /**
//   * 初始化数据
//   * @param sTable String 格式xml字符串
//   * @param url URL       文本数据URL
//   * @throws Exception
//   */
//  public void openTreeBookData(String sTable, URL url) throws Exception {
//    if (report != null) {
//      this.remove(report);
//    }
//    tableManager = new TableManager(sTable);
//    //构造数据集
//    this.queryDataSet =
//        QueryDataTransformer.getQueryNodeFromZIPURL(getViewIDs(tableManager),url);
//    JDRModel model = new JDRModel(tableManager, queryDataSet);
//    model.setQueryFormat(new JDRQueryFormatInfo());
//    report = new JDataReport(model);
//    report.load();
//    this.add(report, BorderLayout.CENTER);
//  }
//
//  /**
//   * 初始化数据
//   * @param sTable String   格式xml字符串
//   * @param queryDataSet QueryDataSet 已经构造好的数据集
//   * @throws Exception
//   */
//  public void openTreeBookData(String sTable,
//                               QueryDataSet queryDataSet) throws Exception {
//    if (report != null) {
//      this.remove(report);
//    }
//    tableManager = new TableManager(sTable);
//    this.queryDataSet=queryDataSet;
//    JDRModel model = new JDRModel(tableManager, queryDataSet);
//    model.setQueryFormat(new JDRQueryFormatInfo());
//    report = new JDataReport(model);
//    report.load();
//    this.add(report, BorderLayout.CENTER);
//  }

  /**
   * 初始化数据
   * @param tableManager TableManager 格式对象
   * @param queryDataSet QueryDataSet 已经构造好的数据集
   * @throws Exception
   */
  public void openTreeBookData(TableManager tableManager,
                               QueryDataSet queryDataSet) throws Exception {
    if (report != null) {
      this.remove(report);
    }

//    //用本地格式文件修正
//    QueryFormat.getQueryFormat(tableManager,"");
    this.tableManager =tableManager;
    this.queryDataSet=queryDataSet;
    JDRModel model = new JDRModel(tableManager, queryDataSet);
    model.setQueryFormat(new JDRQueryFormatInfo());
    report = new JDataReport(model);
    report.load();
    this.add(report, BorderLayout.CENTER);
  }

  public void openTreeBookData(TableManager tableManager,
                               QueryDataSet queryDataSet,String queryFormatID) throws Exception {
    if (report != null) {
      this.remove(report);
    }

    //用本地格式文件修正
    this.tableManager=QueryFormat.getQueryFormat(tableManager,queryFormatID);
    this.queryDataSet=queryDataSet;
    JDRModel model = new JDRModel(tableManager, queryDataSet);
    model.setQueryFormat(new JDRQueryFormatInfo());
    report = new JDataReport(model);
    if ( this.mPainter != null ) report.setJDRPainter(mPainter);
    report.load();
    this.add(report, BorderLayout.CENTER);
  }
  /**
   * 解析格式XML,获取显示列
   * @param mTitleMgr TableManager
   * @return String[]
   * @throws Exception
   */
  public static String[] getViewIDs (TableManager mTitleMgr) throws Exception {
    Vector mColIds = new Vector();
    Vector mViewCols = mTitleMgr.getTableView();
    Vector mValueCols = mTitleMgr.getColumns();
    Vector mValueGroups = mTitleMgr.getGroups();
    XmlViewCol xmlViewCol;
    XmlColumn xmlColumn;
    XmlGroup xmlGroup;
    XmlItem leafItem;
    Vector leafItems;
    for (int i = 0; i < mViewCols.size(); i++) {
      xmlViewCol = (XmlViewCol) mViewCols.elementAt(i);
      if (xmlViewCol.getType().equals("col")) {
        xmlColumn = (XmlColumn) TableManager.getElementById(
            mValueCols, xmlViewCol.getId());
        mColIds.addElement(xmlColumn.getId());
      }
      else if (xmlViewCol.getType().equals("group")) {
        xmlGroup = (XmlGroup) TableManager.getElementById(mValueGroups,
            xmlViewCol.getId());
        leafItems = JDRQueryPainterUtils.getLeafItems(mTitleMgr,
            xmlGroup);
        for (int k = 0; k < leafItems.size(); k++) {
          leafItem = (XmlItem) leafItems.elementAt(k);
          xmlColumn = (XmlColumn) TableManager.getElementById(
              mValueCols, leafItem.getId());
          mColIds.addElement(xmlColumn.getId());
        }
      }
    }
    String []ids=new String[mColIds.size()];
    mColIds.toArray(ids);
    return ids;
  }
}

