package com.print.service;

import com.efounder.eai.data.JParamObject;
import com.f1j.swing.*;
import com.report.table.jxml.*;
import java.util.*;

import jdatareport.dof.classes.report.paint.JDRQueryPainterUtils;
import jdatareport.dof.classes.report.util.JDRConstants;
import jdatareport.dof.classes.report.util.JDRQueryFormatInfo;

import com.f1j.ss.*;
import com.print.*;
import com.report.table.*;
import jservice.jbof.classes.GenerQueryObject.*;
import jservice.jbof.classes.*;
import com.pub.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DrawObject {
  static ResourceBundle res = ResourceBundle.getBundle("com.print.service.Language");
  public static double F1_EXCEL_CE = 0.12;// 假设每列相差0.10英寸
  public static int FONT_SCALE = 18;//JDRConstants.DEFAULT_FONT_SCALE;
  public static int DEFAULT_SCALE = 15;
  private JBook mBook = null;
  private JQueryStubObject QO = null;
  private TableManager mTitleMgr = null;

  private Vector mColIds = new Vector();
  private Vector mViewTitles = null;
  private Vector mValueCols = null;
  private Vector mValueGroups = null;
  private Vector mViewCols = null;

  private int mColCount = 0;
  private int mHeadLevels = 0;

  private int mStartRow = 0;
  private int mStartCol = 0;

//  private int mRowIndex = 0;
//  private int mColIndex = 0;
  private int mBodyLevel = 1;

  private int DataIndex  = 0;
  private int miJedecn = 2,miSldecn = 3,miDjdecn = 2,miWbdecn = 4,miHldecn = 4; //数值精度

  JParamObject PO = null;

  JAccountBookStub AccountBookStub = null;
  public DrawObject(JAccountBookStub abs) {
    PO = JParamObject.Create();
    miJedecn = Integer.parseInt(PO.GetValueByEnvName("ZW_JEDECN","2"));
    miSldecn = Integer.parseInt(PO.GetValueByEnvName("ZW_SLDECN","3"));
    miDjdecn = Integer.parseInt(PO.GetValueByEnvName("ZW_DJDECN","2"));
    miWbdecn = Integer.parseInt(PO.GetValueByEnvName("ZW_WBDECN","4"));
    miHldecn = Integer.parseInt(PO.GetValueByEnvName("ZW_HLDECN","4"));

    AccountBookStub = abs;
    QO = (JQueryStubObject)abs.getContext();
    if ( AccountBookStub != null ) AccountBookStub.setDrawObject(this);
  }
  // 绘制数据
  public static void drawAccountBookData(JAccountBookStub abs) {
    DrawObject drawObject = (DrawObject)abs.getDrawObject();
    try {
      drawObject.drawAllPagesData();
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      JGenerQueryObject.hideWaiting(drawObject.QO);
    }
  }
  private  void drawAllPagesData() throws Exception {
    // 0.初始化参数
    initDraw();
    // 0.5 获取数据列表
    Vector dataList = AccountBookStub.getBibDataList();
    // 获取数据长度
    int Rows = dataList.size();
    // 获取数据自定义的数据长度
    int UserRows = 0;//调用用户自定义的方法
    // 从数据区开始
    int RowIndex = AccountBookStub.getTitleRows()+AccountBookStub.getHeadRows();
    // 获取每页打印的数据行数
    int DataRowsPerPage = AccountBookStub.getDataRowsPerPage();
    int RowsPerPage     = AccountBookStub.getRowsPerPage();
    // 数据区索引，肯定是从0开始
    DataIndex = 0;
    for(int page=0;page<AccountBookStub.getRowPages();page++) {
//      JGenerQueryObject.getQueryWait(QO).setTitle1("正在生成"+QO.CaptionText+"的第"+String.valueOf(page+1)+"页打印数据");
      RowIndex = AccountBookStub.getTitleRows()+AccountBookStub.getHeadRows() + (page*RowsPerPage);
      drawBookData(dataList,page,Rows,RowsPerPage,DataRowsPerPage,RowIndex);
    }
    mBook.setActiveCell(0,0);
  }
  private void drawBookData(Vector dataList,int page,int Rows,int RowsPerPage,int DataRowsPerPage,int RowIndex) throws Exception {
    // 按照数据行数进行设置
    // 对账页打印方式中的每一页中的第一行进行自定义设置
    int Row = 0;
    for(int Index=0;Index<DataRowsPerPage;Index++) {
      if ( DataIndex >= dataList.size() ) break;
      // 对账页打印方式中的每一页中的第一行进行自定义设置
      // 获取数据
      String []node = (String[])dataList.get(DataIndex++);
      // 计算行坐标
      Row = RowIndex + Index;
      JGenerQueryObject.getQueryWait(QO).setTitle2(res.getString("String_42")+String.valueOf(page+1)+res.getString("String_43")+String.valueOf(Row+1)+res.getString("String_44"));
      drawOneRowData(Row,node);
    }
    // 对账页打印方式中的每一页中的最后一行进行自定义设置
  }
  // 绘制一行数据
  private void drawOneRowData(int row,String[] data) throws Exception {
    for(int i=0;i<mColIds.size();i++) {
      int col = i;
      XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols, (String)this.mColIds.get(i));
      String type = xmlColumn.getDatatype();
      String sValue = data[i];
      //数字类型
      if ("N".equals(type) || "J".equals(type) || "S".equals(type) ||
          "D".equals(type) || "W".equals(type) || "H".equals(type)) {
          //计算数值
          double dValue = 0;
          if (sValue != null && sValue.trim().length() > 0) {
              dValue = Double.parseDouble(sValue);
          }
          //绘制数值
          if(dValue==0){
              mBook.setText(row, col, "");
          }else{
              mBook.setNumber(row, col, dValue);
          }
      }
      else {
          mBook.setText(row, col, sValue);
      }
    }
  }
  // 绘制格式
  public static void drawAccountBookFormat(JAccountBookStub abs) {
    DrawObject drawObject = (DrawObject)abs.getDrawObject();
    if ( drawObject == null ) {
      drawObject = new DrawObject(abs);
    }
    try {
      // 生成等待对话框
//      JGenerQueryObject.processWait(drawObject.QO);
//      JGenerQueryObject.showWaiting(drawObject.QO);
//      JGenerQueryObject.getQueryWait(drawObject.QO).setTitle1("正在生成"+drawObject.QO.CaptionText+"的打印格式，请稍候...");
      drawObject.drawAllPagesFormat();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  private void drawAllPagesFormat() throws Exception {
    // 0.初始化参数
    initDraw();
    // 绘制每一页格式
    for(int page=0;page<AccountBookStub.getRowPages();page++) {
//      JGenerQueryObject.getQueryWait(QO).setTitle1("正在生成"+QO.CaptionText+"的第"+String.valueOf(page+1)+"页打印格式");
      drawBookFormat(page);
    }
    // 设置每一列的列宽，需要根据纸张类型进行自动设置
    setAutoColWidth();
    // 设置打印区域
    setPrintArea();
  }
  // 设置打印区域，必须需要设置，不然页数会打不全
  private void setPrintArea() throws Exception {
    int SRow = 0,SCol = 0;
    int ERow = AccountBookStub.getRowPages()*AccountBookStub.getRowsPerPage();
    int ECol = mColIds.size()-1;
    mBook.setSelection(SRow,SCol,ERow-1,ECol);
    mBook.setPrintArea();
    mBook.setSelection(0,0,0,0);
  }
  // 真正的格式绘制
  /**
   * 1.绘制 Title
   * 2.绘制 Head
   * 3.绘制 Body
   */
  private void drawBookFormat(int page) throws Exception {
    // 1.绘制 Title
    JGenerQueryObject.getQueryWait(QO).setTitle2(res.getString("String_52")+String.valueOf(page+1)+res.getString("String_53"));
    drawBookTitle(page);
    JGenerQueryObject.getQueryWait(QO).setTitle2(res.getString("String_54")+String.valueOf(page+1)+res.getString("String_55"));
    // 2.绘制 Head
    drawBookHead(page);
    JGenerQueryObject.getQueryWait(QO).setTitle2(res.getString("String_56")+String.valueOf(page+1)+res.getString("String_57"));
    // 3.绘制 Body
    drawBookBody(page);
    JGenerQueryObject.getQueryWait(QO).setTitle2(res.getString("String_58")+String.valueOf(page+1)+res.getString("String_59"));
    // 4.绘制 Tail
    drawBookTail(page);
    JGenerQueryObject.getQueryWait(QO).setTitle2(res.getString("String_60")+String.valueOf(page+1)+res.getString("String_61"));
    // 5.设置行高
    setRowHeightForPage(page);
    JGenerQueryObject.getQueryWait(QO).setTitle2(res.getString("String_62")+String.valueOf(page+1)+res.getString("String_63"));
    // 6.设置分页符
    setPageBreak(page);
  }
  // 4 绘制Tail 2007-4-3 修改
  void drawBookTail(int page) throws Exception {
    CellFormat cf;
    //页尾第一行左边的文本
    String left_Tail;
    //获取第一个非隐藏列
    int beginCol=0;
//    for(int i=0;i<mBook.getMaxCol();i++){
//      if(mBook.getColWidth(i)>0){
//        beginCol=i;
//        break;
//      }
//    }
    //页尾行数
    int tailRows=AccountBookStub.getTailRows();
    int PageStartRow = (page+1) * AccountBookStub.getRowsPerPage();
    if(AccountBookStub.getLeft_Tail()!=null){
      left_Tail=AccountBookStub.getLeft_Tail();
    }
    else{
      left_Tail=AccountBookStub.getSoftName();
    }
    mBook.setActiveCell(PageStartRow-tailRows,beginCol);
    mBook.setText(PageStartRow-tailRows,beginCol,left_Tail);
    cf = mBook.getCellFormat();
    cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
    mBook.setCellFormat(cf);
    //页尾第一行右边的文本
    String right_Tail;
    if(AccountBookStub.getRight_Tail()!=null){
      right_Tail=AccountBookStub.getRight_Tail();
    }
    else{
      right_Tail=res.getString("String_64")+AccountBookStub.getCompanyName() + res.getString("String_65")+AccountBookStub.getPrintDate()+res.getString("String_66")+String.valueOf(page+1)+res.getString("String_67");
    }
    mBook.setText(PageStartRow-tailRows,mColIds.size()-1,right_Tail);//AccountBookStub.getColsPerPage(),Text);
    mBook.setActiveCell(PageStartRow-tailRows,mColIds.size()-1);//.getColsPerPage());
    cf = mBook.getCellFormat();
    cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
    mBook.setCellFormat(cf);
    //页尾其他的文本，紧跟在第一行之后
    if(AccountBookStub.getOther_Tail()!=null ){
      String []tailArray=AccountBookStub.getOther_Tail().split("\n");
      for(int i=1;i<=tailArray.length;i++){
        mBook.setActiveCell(PageStartRow-i,beginCol);
        mBook.setText(PageStartRow-i,beginCol,tailArray[i-1]);
        cf = mBook.getCellFormat();
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
        mBook.setCellFormat(cf);
      }
    }

//以下代码是修改之前的
//    String Text ;CellFormat cf;
//    int PageStartRow = (page+1) * AccountBookStub.getRowsPerPage();
//    Text = AccountBookStub.getSoftName();
//    mBook.setActiveCell(PageStartRow-1,0);
//    mBook.setText(PageStartRow-1,0,Text);
//    cf = mBook.getCellFormat();
//    cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
//    mBook.setCellFormat(cf);
//    Text = "单位："+AccountBookStub.getCompanyName() + "  打印日期："+AccountBookStub.getPrintDate()+"  第"+String.valueOf(page+1)+"页";
//    mBook.setText(PageStartRow-1,mColIds.size()-1,Text);//AccountBookStub.getColsPerPage(),Text);
//    mBook.setActiveCell(PageStartRow-1,mColIds.size()-1);//.getColsPerPage());
//    cf = mBook.getCellFormat();
//    cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
//    mBook.setCellFormat(cf);
  }
  // 6.设置分页符
  void setPageBreak(int page) throws Exception {
    int PageStartRow = (page+1) * AccountBookStub.getRowsPerPage();
    mBook.addRowPageBreak(PageStartRow);
  }
  // 5.设置行高
  void setRowHeightForPage(int page) throws Exception {
    int PageStartRow = page * AccountBookStub.getRowsPerPage();
    int SRow = mStartRow + PageStartRow;
    int ERow = SRow + AccountBookStub.getRowsPerPage();
    int twips = 0;
    twips = (int) CanvasUtils.convertInchtoTwip(AccountBookStub.getFirstTitleRowHeight());
    mBook.setRowHeight(SRow,SRow,twips,false,false);
    twips = (int) CanvasUtils.convertInchtoTwip(AccountBookStub.getRowHeight());
    mBook.setRowHeight(SRow+1,ERow,twips,false,false);
  }
  /**
   *
   */
  private void initDraw() {
    mTitleMgr = AccountBookStub.getFormatManger();
    mBook = AccountBookStub.getViewBook();
    mViewTitles = AccountBookStub.getMViewTitles();
    mValueCols = AccountBookStub.getMValueCols();
    mValueGroups = AccountBookStub.getMValueGroups();
    mViewCols = AccountBookStub.getMViewCols();
    mColCount = AccountBookStub.getColumns();
    mHeadLevels = AccountBookStub.getHeadRows();
  }
  /**
   *
   * @param page int
   * @throws Exception
   */
  private void drawBookTitle(int page) throws Exception {
    int PageStartRow = page * AccountBookStub.getRowsPerPage();
    int mRowIndex = 0;int mColIndex = 0;
    try {
      for (int i = 0; i < mViewTitles.size(); i++) {
        int row = mStartRow + mRowIndex + PageStartRow;
        int col = mStartCol + mColIndex;
        // 设置每页的每一行的行高
//      int twips = 0;
//      if ( row == mStartRow + PageStartRow ) {
//        twips = (int) CanvasUtils.convertInchtoTwip(AccountBookStub.getFirstTitleRowHeight());
//      } else {
//        twips = (int) CanvasUtils.convertInchtoTwip(AccountBookStub.getRowHeight());
//      }
//      mBook.setRowHeight(row,row,twips,false,false);
        XmlTitle title = (XmlTitle) mViewTitles.elementAt(i);
        Vector labels = title.getLabels();
        XmlLabel label = null;
        String labelText = "";
        CellFormat cf = null;
        int labelCount = labels.size();
        int labelIndex = 0;
        int colInsets = mColCount / labelCount;
        if (colInsets == 0) {
          colInsets = 1;
        }

        if (labelCount == 1) {
          labelIndex = 0;
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, labelText);
          mBook.setSelection(row, col, row, mColCount - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);
          this.setQueryTitleFormat(mBook,row, col, title, labelIndex);
        }
        else if (labelCount == 2) {

          mBook.setSelection(row, col, row, colInsets - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);

          mBook.setSelection(row, colInsets, row, mColCount - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);

          labelIndex = 0;
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, labelText);

          this.setQueryTitleFormat(mBook,row, col, title, labelIndex);

          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + colInsets, labelText);

          this.setQueryTitleFormat(mBook,row, col + colInsets, title, labelIndex);

        }
        else if (labelCount == 3) {
          mBook.setSelection(row, col, row, colInsets - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);

          mBook.setSelection(row, colInsets, row, 2 * colInsets - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);

          mBook.setSelection(row, 2 * colInsets, row, mColCount - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);

          labelIndex = 0;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, labelText);

          this.setQueryTitleFormat(mBook,row, col, title, labelIndex);

          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + col + colInsets, labelText);

          this.setQueryTitleFormat(mBook,row, col + colInsets, title, labelIndex);

          labelIndex = 2;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + 2 * colInsets, labelText);

          this.setQueryTitleFormat(mBook,row, col + 2 * colInsets, title, labelIndex);

        }
        else if (labelCount > 3) {
          mBook.setSelection(row, col, row, colInsets - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);

          mBook.setSelection(row, colInsets, row, 2 * colInsets - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);

          mBook.setSelection(row, 2 * colInsets, row, mColCount - 1);
          cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);

          labelIndex = 0;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, labelText);

          this.setQueryTitleFormat(mBook,row, col, title, labelIndex);

          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + colInsets, labelText);

          this.setQueryTitleFormat(mBook,row, col + colInsets, title, labelIndex);

          labelIndex = 2;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          //追加处理
          for (int j = 3; j < labelCount; j++) {
            label = (XmlLabel) labels.elementAt(j);
            labelText += " ";
            labelText += label.getCaption();
          }
          mBook.setText(row, col + 2 * colInsets, labelText);

          this.setQueryTitleFormat(mBook,row, col + 2 * colInsets, title, labelIndex);

        }
        mRowIndex++;
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @param page int
   * @throws Exception
   */
  private void drawBookHead(int page) throws Exception {
    int fontsize=11;
    String fontname="宋体";
    int mColIndex = 0;int mRowIndex = AccountBookStub.getTitleRows();
    int PageStartRow = page * AccountBookStub.getRowsPerPage();
    mColIds.removeAllElements();
    for (int i = 0; i < mViewCols.size(); i++) {
        int row = mStartRow + mRowIndex + PageStartRow;
        int col = mStartCol + mColIndex;

        XmlViewCol xmlViewCol = (XmlViewCol) mViewCols.elementAt(i);
        if (xmlViewCol.getType().equals("col")) {
            XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols, xmlViewCol.getId());
            //合并单元格
            mBook.setSelection(row, col, mStartRow + (mRowIndex + mHeadLevels - 1)+PageStartRow, col);
            CellFormat cf = mBook.getCellFormat();
            cf.setMergeCells(true);
            mBook.setCellFormat(cf);
            String headerText = xmlColumn.getCaption();
            //获取任意列标题的字体，字号
            fontname=xmlColumn.getFontname();
            fontsize=Integer.parseInt(xmlColumn.getFontsize());
            mBook.setText(row, col, headerText);

            //格式
//            mBook.setSelection(row, col, row, col);
//            setQueryHeaderFormat(xmlColumn);

            mColIds.addElement(xmlColumn.getId());
            mColIndex++;
        }
        else if (xmlViewCol.getType().equals("group")) {
            XmlGroup xmlGroup = (XmlGroup) TableManager.getElementById(mValueGroups, xmlViewCol.getId());
            Vector leafItems = JDRQueryPainterUtils.getLeafItems(mTitleMgr, xmlGroup);
            if (leafItems.size() > 0) {
                drawQueryGroup(mRowIndex, mColIndex, (mRowIndex + mHeadLevels - 1), xmlGroup,page);
                mColIndex += leafItems.size();
            }
            for (int k = 0; k < leafItems.size(); k++) {
                XmlItem leafItem = (XmlItem) leafItems.elementAt(k);
                XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols, leafItem.getId());
                mColIds.addElement(xmlColumn.getId());
            }
        }
    }
    setQueryTHOutterFormat(page,fontname,fontsize);

    mRowIndex += mHeadLevels;

  }
  private void drawQueryGroup(int rowIndex, int colIndex, int endRow, XmlGroup xmlGroup,int page) throws Exception {
      Vector leafItems = JDRQueryPainterUtils.getLeafItems(mTitleMgr, xmlGroup);
      int leafCount = leafItems.size();
      int PageStartRow = page * AccountBookStub.getRowsPerPage();
      try {
          //合并
          mBook.setSelection(mStartRow + rowIndex+PageStartRow, mStartCol + colIndex, mStartRow + rowIndex+PageStartRow,
                             mStartCol + (colIndex + leafCount - 1));
          CellFormat cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);
          String groupText = xmlGroup.getCaption();
          mBook.setText(mStartRow + rowIndex+PageStartRow, mStartCol + colIndex, groupText);

//          setQueryGroupFormat(rowIndex, colIndex, endRow, xmlGroup);

          rowIndex++;
          drawQueryGroupItems(rowIndex, colIndex, endRow, xmlGroup,page);
      }
      catch (Exception e) {
          e.printStackTrace();
      }
  }
  private void drawQueryGroupItems(int rowIndex, int colIndex, int endRow, XmlGroup xmlGroup,int page) throws Exception {
      Vector xmlItems = xmlGroup.getItems();
      int PageStartRow = page * AccountBookStub.getRowsPerPage();
      for (int i = 0; i < xmlItems.size(); i++) {
          XmlItem xmlItem = (XmlItem) xmlItems.elementAt(i);
          if (xmlItem.getType().equals("col")) {
              XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols, xmlItem.getId());
              String itemText = xmlColumn.getCaption();
              mBook.setText(mStartRow + rowIndex+PageStartRow, mStartCol + colIndex, itemText);
              //合并单元格
              mBook.setSelection(mStartRow + rowIndex+PageStartRow, mStartCol + colIndex,
                                 mStartRow + rowIndex+PageStartRow, mStartCol + colIndex);

              CellFormat cf = mBook.getCellFormat();
              cf.setMergeCells(true);
              mBook.setCellFormat(cf);

//              setQueryGroupItemsFormat(rowIndex, colIndex, endRow, xmlColumn);
              colIndex++;
          }
          else if (xmlItem.getType().equals("group")) {
              XmlGroup xmlTemp = (XmlGroup) mTitleMgr.getGroupById(xmlItem.getId());
              drawQueryGroup(rowIndex, colIndex, endRow, xmlTemp,page);
              colIndex += JDRQueryPainterUtils.getGroupCols(mValueGroups, xmlTemp);
          }
      }
  }
  /**
   *
   * @throws java.lang.Exception
   */
  private void setQueryTHOutterFormat(int page,String fontname,int fontsize) throws Exception {
    int PageStartRow = page * AccountBookStub.getRowsPerPage();
    int TRows = AccountBookStub.getTitleRows();
    int HRows = AccountBookStub.getHeadRows();
          //表头格式
          // 设置行高
//          int twips = (int) CanvasUtils.convertInchtoTwip(AccountBookStub.getRowHeight());
//          mBook.setRowHeight(mStartRow+PageStartRow+TRows,mStartRow + PageStartRow+TRows+HRows,twips,false,false);

          int headColount = mStartCol + mColCount - 1;
          this.mBook.setSelection(mStartRow+PageStartRow+TRows, mStartCol,
                                  mStartRow + PageStartRow+TRows+HRows,mStartCol + headColount);
          CellFormat cf = mBook.getCellFormat();
          cf.setTopBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
          cf.setTopBorder(JDRQueryFormatInfo.mHeadOuterBorder);
          cf.setBottomBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
          cf.setBottomBorder(JDRQueryFormatInfo.mBodyOuterBorder);
          cf.setLeftBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
          cf.setLeftBorder(JDRQueryFormatInfo.mBodyOuterBorder);
          cf.setRightBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
          cf.setRightBorder(JDRQueryFormatInfo.mBodyOuterBorder);
          cf.setHorizontalInsideBorder(JDRQueryFormatInfo.mBodyInnerBorder);
          cf.setHorizontalInsideBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
          cf.setVerticalInsideBorder(JDRQueryFormatInfo.mBodyInnerBorder);
          cf.setVerticalInsideBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
          //fontname=AccountBookStub.getFontName();
          //fontsize = AccountBookStub.getFontSize() * FONT_SCALE;
          cf.setFontSize(fontsize* FONT_SCALE);
          cf.setFontName(fontname);
          cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
          cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
          mBook.setCellFormat(cf);
  }

  private void drawBookBody(int page) throws Exception {
    setQueryBodyFormat(page);
  }
  private void setAutoColWidth() throws Exception {
    int OldColWidthTwips=0;int NumberCols = 0;int i=0;
    //
    mBook.setColWidthUnits(mBook.eColWidthUnitsTwips);
    // 1.统计出现有的所有列的宽度,统计出其中的数值列列数
    for (i = 0; i < mColIds.size(); i++) {
        XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols, (String) mColIds.get(i));
        String colWidth = xmlColumn.getWidth();
        String type = xmlColumn.getDatatype();
        if ("N".equals(type) || "J".equals(type) || "S".equals(type) ||
            "D".equals(type) || "W".equals(type) || "H".equals(type)){
          NumberCols++;
        }
        int ColWidth = Integer.parseInt(colWidth)* DEFAULT_SCALE;
        mBook.setColWidth(i, ColWidth);
        ColWidth = mBook.getColWidth(i);
        OldColWidthTwips += ColWidth;
    }
    // 如果数值列等0，则分配到所有的列上
    if ( NumberCols == 0 ) NumberCols = mColIds.size();
    // 所有列的宽度，以英寸为单位 这里要 无论0.00x中的x为
    double OldColWidthInch = CanvasUtils.convertTwiptoInch((double)(OldColWidthTwips));
    OldColWidthInch = Math.round(OldColWidthInch*100.00)/100.00;
    // 取出当前纸张的每一页的宽度
    double PageWidth = AccountBookStub.getDataWidth();
    // 计算出之间的差额
    double WidthCE   = Math.floor((PageWidth - OldColWidthInch)*100.00)/100.00;
    // 计算出分配差额
    double FPWidthCE  = WidthCE/(NumberCols*1.00) - F1_EXCEL_CE;// 有可能为负数，
    FPWidthCE = Math.floor(FPWidthCE*100.00)/100.00;
    // 将英寸转换成TWips
    int    iFPWidthCE = (int)CanvasUtils.convertInchtoTwip(FPWidthCE);
    // 将差额分配到数值列上
    for (i = 0; i < mColIds.size(); i++) {
        XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols, (String) mColIds.get(i));
        String colWidth = xmlColumn.getWidth();
        String type = xmlColumn.getDatatype();
        OldColWidthTwips = Integer.parseInt(colWidth) * DEFAULT_SCALE;
        // 如果是调整所有列，或是为数值列，需要加上调整差额
        if (NumberCols == mColIds.size() ||
            ("N".equals(type) || "J".equals(type) || "S".equals(type) ||
             "D".equals(type) || "W".equals(type) || "H".equals(type))) {
          OldColWidthTwips += iFPWidthCE;
        }
        mBook.setColWidth(mStartCol + i, OldColWidthTwips);
    }
    OldColWidthTwips = 0;
    for (i = 0; i < mColIds.size(); i++) {
        int ColWidth = mBook.getColWidth(i);
        OldColWidthTwips += ColWidth;
    }
    double NewPageWidth = CanvasUtils.convertTwiptoInch(OldColWidthTwips);
  }
  private void setQueryBodyFormat(int page) throws Exception {
    /**
     * 负数是否显示为红字标志，可以在数据中心中设置
     * 默认为显示红字
     * add by hufeng 2005.11.17
     */
    PO = JParamObject.Create();
    String showRed = PO.GetValueByEnvName("CX_SHOWRED","");
    if(showRed == null || showRed.trim().equals("")){
      showRed = "1";
    }
    int PageStartRow = page * AccountBookStub.getRowsPerPage();
    int bodyStartRow = AccountBookStub.getTitleRows() + AccountBookStub.getHeadRows();
    // 减去尾行
    int bodyEndRow   = AccountBookStub.getRowsPerPage() - AccountBookStub.getTailRows();

    for (int i = 0; i < mColIds.size(); i++) {
      XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols, (String) mColIds.get(i));
      //2007-8-3 fengbo 打印字体大小
      int fontSize = 144;//(int) (Integer.parseInt(xmlColumn.getFontsize()) * FONT_SCALE);
      String fontName = xmlColumn.getFontname();
      fontName = JDRConstants.DEFAULT_FONT_NAME;

      String type = xmlColumn.getDatatype();
      String format = xmlColumn.getFormat();
      String colWidth = xmlColumn.getWidth();
      String align = xmlColumn.getAlign();

      //N,C,D
      if (format == null) {
        format = "";
      }
      //              if (type.equals("N")) {
      //                  format = "#,##0.";
      //                  for (int k = 0; k < 2; k++) { // 这里需要处理小数位数
      //                      format += "0";
      //                  }
      //                  // 如果是彩色打印
      //                  if ( !mBook.isPrintNoColor() )
      //                    format += ";[Red]" + format;
      //              }
      //取XML文件中的datatype，如果是N则保持原来的
      if (type.equals("N")) {
        format = xmlColumn.getFormat();
        if (format.startsWith(",")){
          format = "#" + format;
        }
        //加入查询选项
        if (!mBook.isPrintNoColor() && showRed.equals("1")){
          format += ";[Red]" + format;
        }
      }else if(type.equals("J")){//金额
        format = "#,##0."+StringFunction.FillZeroFromBegin(0,miJedecn);
        if (!mBook.isPrintNoColor() && showRed.equals("1")){
          format += ";[Red]" + format;
        }
      }else if(type.equals("S")){//数量
        format = "#,##0."+StringFunction.FillZeroFromBegin(0,miSldecn);
        if (!mBook.isPrintNoColor() && showRed.equals("1")){
          format += ";[Red]" + format;
        }
      }else if(type.equals("D")){//单价
        format = "#,##0."+StringFunction.FillZeroFromBegin(0,miDjdecn);
        if (!mBook.isPrintNoColor() && showRed.equals("1")){
          format += ";[Red]" + format;
        }
      }else if(type.equals("W")){//外币
        format = "#,##0."+StringFunction.FillZeroFromBegin(0,miWbdecn);
        if (!mBook.isPrintNoColor() && showRed.equals("1")){
          format += ";[Red]" + format;
        }
      }else if(type.equals("H")){//汇率
        format = "#,##0."+StringFunction.FillZeroFromBegin(0,miHldecn);
        if (!mBook.isPrintNoColor() && showRed.equals("1")){
          format += ";[Red]" + format;
        }
      }

      //              if (colWidth != null && colWidth.length() > 0) {
      //                  this.mBook.setColWidth(mStartCol + i, Integer.parseInt(colWidth) * JDRConstants.DEFAULT_SCALE);
      //              }
      //              int twips = (int) CanvasUtils.convertInchtoTwip(AccountBookStub.getRowHeight());
      //              mBook.setRowHeight(mStartRow + bodyStartRow+PageStartRow,mStartRow + bodyEndRow+PageStartRow,twips,false,false);
      mBook.setSelection(mStartRow + bodyStartRow+PageStartRow, mStartCol + i, mStartRow + bodyEndRow+PageStartRow, mStartCol + i);
      CellFormat cf = mBook.getCellFormat();
      cf.setFontSize(fontSize);
      cf.setFontName(fontName);

      if (format != null && format.trim().length() > 0) {
        cf.setValueFormat(format);
      }

      if (align.equals("left")) {
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
      }
      else if (align.equals("right")) {
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
      }
      else if (align.equals("center")) {
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
      }
      else {
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
      }
      cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
      mBook.setCellFormat(cf);

    } //End of 格式

    //          this.mBook.setRowHeight(mStartRow, mStartRow, JDRConstants.DEFAULT_ROW_HEIGHT * 2, false, false);
    //          this.mBook.setRowHeight(mStartRow + 1, mStartRow + bodyEndRow, JDRConstants.DEFAULT_ROW_HEIGHT, false, false);

    //整体格式
    this.mBook.setSelection(mStartRow + bodyStartRow+PageStartRow, mStartCol,
                            mStartRow + bodyEndRow+PageStartRow-1,
                            mStartCol + this.mColCount - 1);
    CellFormat cf = mBook.getCellFormat();
    cf.setBottomBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    cf.setBottomBorder(JDRQueryFormatInfo.mBodyOuterBorder);
    cf.setLeftBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    cf.setLeftBorder(JDRQueryFormatInfo.mBodyOuterBorder);
    cf.setRightBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    cf.setRightBorder(JDRQueryFormatInfo.mBodyOuterBorder);
    cf.setHorizontalInsideBorder(JDRQueryFormatInfo.mBodyInnerBorder);
    cf.setHorizontalInsideBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    cf.setVerticalInsideBorder(JDRQueryFormatInfo.mBodyInnerBorder);
    cf.setVerticalInsideBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    mBook.setCellFormat(cf);
    //          mBook.setActiveCell(bodyStartRow, 0);
    //          mBook.setSelection(bodyStartRow, 0, bodyStartRow, 0);

  }

  public void setQueryTitleFormat(JBook mBook,int row, int col, XmlTitle title, int labelIndex) throws Exception {

    mBook.setSelection(row, col, row, col);
    CellFormat cf = mBook.getCellFormat();
    //格式
    String align = title.getAlign();
    int fontSize = (int) (Integer.parseInt(title.getFontsize()) * FONT_SCALE);
    String fontName = title.getFontname();
    fontName = title.getFontname();//JDRConstants.DEFAULT_FONT_NAME;

    if (align.equals("left")) {
      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
    }
    else if (align.equals("right")) {
      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
    }
    else if (align.equals("center")) {
      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
    }
    else if (align.equals("y_axis")) {

    }
    else if (align.equals("x_axis")) {
      if (labelIndex == 0) {
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
      }
      else if (labelIndex == title.getLabels().size() - 1) {
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
      }
      else {
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
      }
    }
    //          cf.setTopBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    //          cf.setTopBorder(JDRQueryFormatInfo .mTitleInnerBorder);
    //          cf.setBottomBorderColor(JDRQueryFormatInfo .BORDERCOLOR);
    //          cf.setBottomBorder(JDRQueryFormatInfo .mTitleInnerBorder);
    //          cf.setLeftBorderColor(JDRQueryFormatInfo .BORDERCOLOR);
    //          cf.setLeftBorder(JDRQueryFormatInfo .mTitleInnerBorder);
    //          cf.setRightBorderColor(JDRQueryFormatInfo .BORDERCOLOR);
    //          cf.setRightBorder(JDRQueryFormatInfo.mTitleInnerBorder);

    cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
    cf.setFontSize(fontSize);
    cf.setFontName(fontName);
    mBook.setCellFormat(cf);

  }
}
