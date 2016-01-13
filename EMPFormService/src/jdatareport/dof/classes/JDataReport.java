package jdatareport.dof.classes;

import java.io.*;
import java.util.*;

import javax.swing.*;

import com.f1j.ss.*;
import com.f1j.swing.*;
import com.report.table.jxml.TableManager;

import jframework.foundation.classes.*;
import jdatareport.dof.classes.report.*;
import jdatareport.dof.classes.report.style.*;
import jdatareport.dof.classes.report.util.*;
import jdatareport.dof.classes.report.ext.*;
import jdatareport.dof.classes.report.ext.sort.ui.*;
import jdatareport.dof.classes.report.ext.condition.ui.*;
import jdatareport.dof.classes.report.filter.*;
import jdatareport.dof.classes.report.paint.JDRQueryPainter;

/**
 *
 * <p>Title: JDataReport</p>
 * <p>Description: 客户端使用的报表类</p>
 * <p>
 * ========<br>
 * <font size= 30><b>概述</b></font><br>
 * ========<br>
 * </p>
 * <p>
 * 实际上整个报表的视图都依赖于他的数据模型<br>
 * &nbsp&nbsp(1) 最简单的方式是静态的设计好报表的配置文件,而后加载,是只需要提供名称,报表会自动加载配置
 * 文件,根据他生成的报表的数据模型,而后报表根据数据模型生成报表的视图.这种依赖相对来说是比较
 * 隐式的.<br>
 * 另外为了方便还提供了另外两种方法解除对数据模型的依赖<br>
 * &nbsp&nbsp(2) 一种称为Consructor Injection,就是在视图的构造方法中提供数据模型.<br>
 * &nbsp&nbsp(3) 另外一种是Settter Injection,通过Settter方法提供数据模型,当然还可以有第三中方法
 * Interface Injection,通过在具体的实现的方法提供数据模型的接口来解除依赖关系(没有实现)<br>
 * </p>
 * <p>
 * 在报表的使用当中,一般分为2种:静态报表,动态报表<br>
 * &nbsp&nbsp(1) 对于静态的可以采用第一种方式静态设计好报表配置文件,放在指定的目录下(
 * "src\Resource\Resource\zh\XML\JDataReport"),构造报表时只需要提供报表的名称.<br>
 * &nbsp&nbsp(2) 对于动态报表可以采用后面的两种方式构造<br>
 * </p>
 * <p>
 * ========<br>
 * <font size= 30><b>使用</b></font><br>
 * ========<br>
 * </p>
    <br>
    <font size=4><b>(1) 静态:</b></font><br>
    report = new JDataReport("TestReport");<br>
    JDRModel model = report.getModel();<br>
    JDataWindow  form = getForm();
    if(form != null){
    &nbsp&nbsp model.addForm("form0", form);
    }
    String[] queryResult = getQueryResult();<br>
    if(queryResult!= null){<br>
    &nbsp&nbsp model.addQuery("query0", queryResult);<br>
    }<br>
    report.load();//加载<br>
    <br>
    <font size=4><b>(2) 动态:</b></font><br>
    JDRModel model = new JDRModel(name,params);<br>
    JDataWindow  form = getForm();
    if(form != null){
    &nbsp&nbsp model.addForm("form0", form);
    }
    String[] queryResult = getQueryResult();<br>
    if(queryResult!= null){<br>
    &nbsp&nbsp model.addQuery("query0", queryResult);<br>
    }<br>
    JDRRptFigure rptFigure = model.getRptFigure();<br>
    JDRCellFigure cellFigure = new JDRCellFigure();<br>
    cellFigure.setName("Text0");<br>
    cellFigure.setAttribute(name,value);<br>
    ...<br>
    rptFigure.addCellFigure(cellFigure);<br>
    ...<br>
    report = new JDataReport(model);<br>
    report.load();//加载<br>
    或者<br>
    report = new JDataReport();<br>
    report.setModel(model);<br>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 *
 */

public class JDataReport
    extends JDRView implements ViewChangedListener {
  private static final int xs = 14; //.21
  private int fixRowsHeight = 0;
  private int rowHeight = 0;

  /**
   * 构造方法,用于构造静态报表
   * @param rptName 报表名称
   */
  public JDataReport(String rptName) {
    super(rptName, null);
  }

  /**
   * 构造方法,用于构造静态报表
   * @param rptName 报表名称
   * @param params  参数列表
   */
  public JDataReport(String rptName, Hashtable params) {
    super(rptName, params);
  }

  /**
   * 构造方法,用于构造动态报表
   * @param model 数据模型
   */
  public JDataReport(JDRModel model) {
    super(model);
  }

  public void load() throws Exception {
    super.load();
    mBook.addViewChangedListener(this);
    int row = 0;
    for (row = mBook.getFixedRow(); row < mBook.getFixedRows(); row++) {
      fixRowsHeight += mBook.getRowHeight(row) / xs;
      //System.out.println(row + ":" + mBook.getRowHeight(row) / xs + ":" +fixRowsHeight);
    }
    rowHeight = mBook.getRowHeight(row) / xs;
  }

  /**
   * 将未显示数据逐页加入组件
   * @param e ViewChangedEvent
   */
  public synchronized void viewChanged(ViewChangedEvent e) {
    try {
      if (mBook.getSheet() != 0) {
        return;
      }
      //Debug.PrintlnMessageToSystem("view : "+mBook.getTopRow()+":"+mBook.getLastRow()+":"+mBook.getHeight()+":"+fixRowsHeight+":"+rowHeight+":"+(mBook.getHeight()-fixRowsHeight)/rowHeight);
      // 2007-6-5 fengbo,避免发生 by /zero 异常;
      if(rowHeight==0){
        rowHeight=JDRConstants.DEFAULT_ROW_HEIGHT/xs;
      }
      if ( mBook.getTopRow()>=(mBook.getLastRow() - ( (mBook.getHeight() - fixRowsHeight) / rowHeight) * 2)) {
        JDRQueryPainter queryPanter = mPainter.queryPanter;
        queryPanter.mRowFilter = mPainter.mColFilter;
        if (mModel != null && mPainter != null && queryPanter.hasMoreData) {
          queryPanter.drawQueryBody(false);
          if (queryPanter.hasMoreData) {
            setReportFormat();
          }
        }
      }
    }
    catch (Exception ee) {
      ee.printStackTrace();
    }
  }

  /**
   * 转出Excel97
   * @param fileName 转出文件名称
   */
  public void export2Excel(String fileName) {
    if (fileName == null) {
      throw new IllegalArgumentException("转出文件名为空");
    }
    String path = null;
    //选择路径
    JFileChooser mFileChooser = new JFileChooser();
    mFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    mFileChooser.setMultiSelectionEnabled(false);
    mFileChooser.setRequestFocusEnabled(true);

    int option = mFileChooser.showOpenDialog(this);
    if (option == JFileChooser.OPEN_DIALOG) {
      if (mFileChooser.getSelectedFile() != null) {
        File file = mFileChooser.getSelectedFile();
        path = file.getAbsolutePath();
      }
    }
    else {
      return;
    }

    //校验路径的有效性
    if (path == null || path.length() == 0) {
      JOptionPane.showMessageDialog(this, "转出路径不能为空，请选择导出路径", "系统提示",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }
    else if (!new File(path).exists()) {
      JOptionPane.showMessageDialog(this, "转出路径不存在，请选择导出路径", "系统提示",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }
    else {

      //报表转出
      String outputDir = path;
      String singleFileName = fileName;
      String outputPath = null;
      try {
        outputPath = outputDir + File.separator + singleFileName + ".xls";
        mBook.write(outputPath, new WriteParams(JBook.eFileExcel97));
      }
      catch (IOException ioe) {
        JOptionPane.showMessageDialog(this, "转出错误" + ioe.getLocalizedMessage(),
                                      "系统提示", JOptionPane.ERROR_MESSAGE);
        ioe.printStackTrace();
        return;
      }
      catch (Exception e) {
        JOptionPane.showMessageDialog(this, "转出错误" + e.getLocalizedMessage(),
                                      "系统提示", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        return;
      }
      JOptionPane.showMessageDialog(this, fileName + "转出成功", "系统提示",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /**
   * 打印页面设置
   * @param rptId 报表的唯一标识,页面设置的配置文件将以此区别不同报表的页面设置
   */
  public void printPageSetup() {
//        ;
//        try {
//            JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.PageSetupDlg.class, mBook, true);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
  }

  /**
   * 打印预览
   */
  public void printPreview() {
//        try {
//            JBookPrintPreviewWindow previewWindow = new JBookPrintPreviewWindow(mBook);
//            previewWindow.LoadWindowIcon("printview.gif");
//            String Text = this.mRptName + " ( 打印预览 )";
//            JActiveDComDM.MainApplication.OpenObjectWindow(Text, previewWindow.WindowIcon, Text, previewWindow);
//            previewWindow.InitChildWindow(JActiveDComDM.MainApplication, JActiveDComDM.MainApplication.MainWindow, mBook, null);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
  }

  /**
   * 打印
   * @param rptId 报表的唯一标识,页面设置的配置文件将以此区别不同报表的页面设置
   */
  public void print() {
//        try {
    //复制一份,设置格式为正常
//            JBook printBook = JPrintUtils.copyPrintBook(mBook);
//            JReportBookPrintSetDlg Print = new JReportBookPrintSetDlg(printBook);
//            Print.addWindowListener(new JMulLangF1Object());
//            Print.CenterWindow();
//            Print.show();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
  }

  /**
   * 排序
   */
  public void sort() {
    //取格式、数据
    JDRModel model = getModel();
    Object[] queryResult = (Object[]) model.getQuerys().get("SingleQuery");
    if (queryResult != null) {
      Vector sortCols = JDRXManager.getSortColumns( (TableManager) queryResult[
          0]);
      JQuerySortDialog dlg = new JQuerySortDialog(JActiveDComDM.MainApplication.
                                                  MainWindow, "查询排序", true);
      dlg.setSortColumn(sortCols);
      dlg.CenterWindow();
      dlg.show();

      //实现排序
      int option = dlg.Result;
      if (option == dlg.RESULT_OK) {
        Vector cols = dlg.getSortColumns();
        int bodyStartRow = model.getTitleRowCount() + model.getHeadRowCount();
        int bodyEndRow = model.getMaxRow();
        int bodyRowCount = model.getBodyRowCount();
        int bodyEndCol = model.getMaxCol();

        int startRow = bodyStartRow;
        int startCol = 0;
        int endRow = bodyEndRow;
        int endCol = bodyEndCol;
        if (endCol > 0) {
          endCol--;
        }
        JDRXManager.sort(this.getBook(), startRow, startCol, endRow, endCol,
                         cols);
        try {
          //this.load(JDataReport.FILTER_FORMAT_ONLY);
          this.mBook.getBook().getSheet(0).setRowOutlineLevel(bodyStartRow,
              bodyEndRow, 0, false);
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }

      }
    }
  }

  /**
   * 过滤
   */
  public void filter() {
    //取格式、数据
    JDRModel model = getModel();
    Object[] queryResult = (Object[]) model.getQuerys().get("SingleQuery");
    if (queryResult != null) {
      JCDialog dlg = new JCDialog(JActiveDComDM.MainApplication.MainWindow,
                                  "过滤查询", true);
      Vector filterCols = JDRXManager.getQueryColumns( (TableManager)
          queryResult[0]);
      dlg.setCompareItems(filterCols);
      dlg.CenterWindow();
      dlg.show();

      //实现过滤
      int option = dlg.Result;
      if (option == dlg.RESULT_OK) {
        JBook book = this.getBook();
        try {
          JDRRowFilter rowFilter = new JDRRowFilter();
          rowFilter.setFilterExpressions(dlg.getExpression());
          this.load(rowFilter);
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }

      }
    }
  }

  /**
   * 样式
   */
  public void style() {
    JDRStyleDialog dlg = new JDRStyleDialog(JActiveDComDM.MainApplication.
                                            MainWindow, "表格样式", true);
    dlg.CenterWindow();
    dlg.setVisible(true);
    int option = dlg.Result;
    if (option == dlg.RESULT_OK) {
      JDRQueryFormatInfo fmtInfo = dlg.getSelectedFormatInfo();
      if (fmtInfo != null) {
        this.getModel().setQueryFormat(fmtInfo);
        try {
          this.load(JDataReport.FILTER_FORMAT_ONLY);
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}
