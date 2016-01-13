package com.print.service;

import com.report.table.jxml.*;
import java.util.*;
import com.print.*;
import com.print.JF1PageSets;
import com.report.table.jxml.TableManager;
import com.f1j.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PrintFormatObject {
  static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.print.service.Language");
  public static final short PAGESTATUS_FORM_FEED = 0xFF; // 有一项填满了

  // 形成所有的格式
  // 形成列信息列表
  protected Hashtable colList   = null;
  // 形成组信息列表
  protected Hashtable groupList = null;
  // 形成总的页面信息
  protected ViewDefine viewDefine       = null;
  // 形成分面的页面信息
  protected ViewDefine[] viewDefines    = null;
  // 标题信息
  protected Vector titleList = null;
  //
  protected TableManager tableManager  = null;
  //
  protected CustomPrint printObject = null;
  //
  JF1PageSets F1PageSets   = null;
  protected int fixCols = 0;
  protected int valCols = 0;
  protected int startPageno = 1;
  protected int curPageNo = 1;
  protected int style = 0;
  public int desColIndex = 3;//摘要列

  private int     TitleRows = 0;    // 标题行数
  private int     HeadRows  = 0;    // 表头行数
  private int     BodyRows  = 0;    // 表体行数
  private int     TailRows  = 1;    // 表尾行数
  private int     LastBodyRows  = 0;//最未一页的表体行数
  private boolean fillpage = true;
  private String COUPLING_FRONT = res.getString("String_375");
  private String COUPLING_AFTER = res.getString("String_376");

  public String getSplitPageText() {
    return res.getString("String_377");
  }

  public String getFrontPageText() {
    return COUPLING_FRONT;
  }
  public String getAfterPageText() {
    return COUPLING_AFTER;
  }

  // 最大表头级数，也就是表头的行数
  protected int maxLevle = 1;

  public Hashtable getColList() {
    return colList;
  }

  public ViewDefine[] getViewDefines() {
    return viewDefines;
  }

  public ViewDefine getViewDefine() {
    return viewDefine;
  }

  public Hashtable getGroupList() {
    return groupList;
  }

  public Vector getTitleList() {
    return titleList;
  }

  public int getMaxLevle() {
    return maxLevle;
  }

  public TableManager getTableManager() {
    return tableManager;
  }

  public CustomPrint getPrintObject() {
    return printObject;
  }

  public int getStyle() {
    return style;
  }

  public int getFixCols() {
    return fixCols;
  }

  public int getStartPageno() {
    return startPageno;
  }

  public int getCurPageNo() {
	  return curPageNo;
  }
  
  public int getValCols() {
    return valCols;
  }

  public int getDesColIndex() {
    return desColIndex;
  }

  public JF1PageSets getF1PageSets() {
    return F1PageSets;
  }

  public int getBodyRows() {
    return BodyRows;
  }
  public void setLastBodyRows(int rows){
    this.LastBodyRows = rows;
  }
  public int getLastBodyRows(){
    return this.LastBodyRows;
  }
  public int getTailRows() {
    return TailRows;
  }
  public int getFormFeed() {
    return style;
  }
  public int getTitleRows() {
    return TitleRows;
  }
  public boolean getFillPage(){
    return this.fillpage;
  }
  public int getColIndexByID(ColumnDefine CD) {
    int index = -1;String ID = CD.getColID();ColumnDefine BCD = null;
    Vector colList = viewDefine.getViewColList();
    for(int i=0;i<colList.size();i++) {
      BCD = (ColumnDefine)colList.get(i);
      if ( BCD.getColID().equals(ID) ) {
        index = i;break;
      }
    }
    return index;
  }
  public int getHeadRows() {
    return HeadRows;
  }

  public int getPageRows() {
    return this.TitleRows+this.HeadRows+this.BodyRows+this.TailRows;
  }

  public int getCurrentPageRows(){
    if (LastBodyRows>0)
      return this.TitleRows+this.HeadRows+this.LastBodyRows+this.TailRows;
    else
      return this.TitleRows+this.HeadRows+this.BodyRows+this.TailRows;
  }
  protected PrintFormatObject() {
  }

  public void setCurPageNo(int curPageNo) {
	  this.curPageNo = curPageNo;
  }
  
  public static PrintFormatObject getPrintFormatObject(JBook Book,
	      TableManager tableManager, CustomPrint printObject,
	      int FixCols, int ValCols, int StartPageNo, int Style, int desColIndex,
	      int Rows,boolean Fillpage) {
	  return getPrintFormatObject(Book, tableManager, printObject, FixCols, ValCols, StartPageNo, 0,  Style, desColIndex, Rows, Fillpage);
  }
  
  public static PrintFormatObject getPrintFormatObject(JBook Book, 
	      TableManager tableManager, CustomPrint printObject,
	      int FixCols, int ValCols, int StartPageNo, int CurPageNo, int Style, int desColIndex,
	      int Rows,boolean Fillpage) {
	  return getPrintFormatObject(Book, null, tableManager, printObject, FixCols, ValCols, StartPageNo, CurPageNo, Style, desColIndex, Rows, Fillpage);
  }
  
  public static PrintFormatObject getPrintFormatObject(JBook Book, JBook printBook,
      TableManager tableManager, CustomPrint printObject,
      int FixCols, int ValCols, int StartPageNo, int CurPageNo, int Style, int desColIndex,
      int Rows,boolean Fillpage) {
    PrintFormatObject pf = new PrintFormatObject();
    pf.desColIndex = desColIndex; // 摘要的列索引
    pf. printObject  = printObject;
    pf.getPrintObject().setDefaultPageFormat(pf);
    // 取出F1的打印参数
    pf.F1PageSets = new JF1PageSets(Book);
    pf.fixCols = FixCols;pf.valCols = ValCols;pf.startPageno = StartPageNo;pf.style = Style;
    pf.curPageNo = CurPageNo;
    pf.fillpage = Fillpage;
    pf. tableManager = tableManager;

    // 形成所有的格式
    // 形成列信息列表
    pf. colList        = DrawObject2.getColumnDefines(tableManager,printObject);
    // 形成组信息列表
    pf. groupList      = DrawObject2.getGroupDefines(tableManager,pf.colList,printObject);
    // 形成总的页面信息
    pf. viewDefine     = DrawObject2.getViewLayout(tableManager,pf.colList,pf.groupList);
    // 形成分面的页面信息
    pf. viewDefines    = DrawObject2.getViewLayouts(pf.viewDefine,pf.colList,pf.groupList,FixCols,ValCols,Style);
    // 形成标题信息
    pf. titleList      = DrawObject2.getViewTitles(tableManager,printObject);
    // 获取表头最大级数
    pf. maxLevle       = GroupDefine.getMaxLevel(pf.groupList);
    // 标题行数
    pf.TitleRows = pf.titleList.size();
    // 表头行数
    pf.HeadRows  = pf.maxLevle;
    //　表尾行数=固定一行+表尾行数
    pf.TailRows  = 1+tableManager.getTails().size(); //
    // 表体行数
    if (Rows==0){
      pf.BodyRows = pf.buildDataRowsPerPage();
    }else{
      pf.BodyRows = Rows;
    }
    return pf;
  }
  // 确定每一页有多少数据行
  private int buildDataRowsPerPage() {
    double DataHeight = getDataHeight();
    // 必须进行舍位操作，如果有余数，不管是4还是5都不能进行新的一行了
    double dRow = Math.floor(DataHeight / printObject.getRowHeigth());
    //    double dRow = Math.round(DataHeight / RowHeight);
//    return (int)dRow;
    int row=(int)dRow;
    //按指定比例压缩 modi by fengbo 2008-3-10 支持正式帐页打印按比例缩放
    if(!F1PageSets.PrintScaleFitToPage){
      row=(int)(dRow/(F1PageSets.PrintScale/100.00));
    }
    return row;
  }
  private double getDataHeight() {
    double DataHeight = 0.00; double FmtHeight = 0.00;
    // 确定标题高度
    if ( TitleRows > 0 )
      FmtHeight  = 1*this.printObject.getFirstRowHeigth() + (TitleRows-1)*printObject.getRowHeigth();
    // 确定表头高度
    FmtHeight += (HeadRows * printObject.getRowHeigth());
    // 确定表尾行数
    FmtHeight += (TailRows*printObject.getRowHeigth());
    DataHeight = F1PageSets.PrintPaperHeight -
                                               (F1PageSets.PrintTopMargin +
//                                                F1PageSets.PrintHeaderMargin +
                                                FmtHeight +
//                                                F1PageSets.PrintFooterMargin +
                                                F1PageSets.PrintBottomMargin
                                                );
    return DataHeight;
  }
  public double getDataWidth() {
    double DataWidth = 0.00;
    DataWidth = F1PageSets.PrintPaperWidth - (F1PageSets.PrintLeftMargin+F1PageSets.PrintRightMargin);
    return DataWidth;
  }
}
