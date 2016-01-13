package jservice.jbof.classes.GenerQueryObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQueryReportModel
    extends AbstractReportDataModel {
  public JQueryReportModel() {
  }

  public int OpenReportObject(Object roe, JParamObject PO) {
    /**@todo Override this jreport.foundation.classes.JReportDataModel method*/
//      ReportView.setPublicAttrib();
//      mXMLObject = new JXMLObject( (String) roe);
    init();
    drawReport();
    return 0;
  }

  //参数初始化
  public void init() {

  }

  //绘制标题
  public void drawReportTitle() {
  }

  //绘制表头
  public void drawReportHead() {
  }

  //绘制表体
  public void drawReportBody() {
  }

  //绘制表尾
  public void drawReportTail() {
  }

  //绘制表格式
  public void setReportFormat() {
  }

  //获得报表总的行数
  public int getRowCount() {
    return mRowCount;
  }

  //获得报表的列数
  public int getColCount() {
    return mColCount;
  }

  //获得标题
  public String getTitle() {
    return mTitle;
  }

  //设置标题
  public void setTitle(String title) {
    mTitle = title;
  }

  public String GetCaption() {
    /**@todo Override this jreport.foundation.classes.JReportDataModel method*/
    return mCaption;
  }

  public void SetCaption(String caption) {
    /**@todo Override this jreport.foundation.classes.JReportDataModel method*/
    mCaption = caption;
  }

}
