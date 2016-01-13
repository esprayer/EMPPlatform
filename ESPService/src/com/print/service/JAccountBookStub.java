package com.print.service;

import java.util.*;

import com.core.xml.JBOFClass;
import com.efounder.eai.data.JParamObject;
import com.f1j.swing.*;
import com.print.*;
import com.report.table.jxml.*;
import jdatareport.dof.classes.report.paint.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JAccountBookStub {
  static ResourceBundle res = ResourceBundle.getBundle("com.print.service.Language");
  public static String COUPLING_FRONT = res.getString("String_249");
  public static String COUPLING_AFTER = res.getString("String_250");
  private String FontName = "宋体";
  private int    FontSize = 11;
  private JF1PageSets F1PageSets = null;
  private JBook       ViewBook   = null;
  private int     XmCol     = 2;    // 项目列
  private int     TitleRows = 0;    // 标题行数
  private int     HeadRows  = 0;    // 表头行数
  private int     TailRows  = 1;    // 表尾行数
  private int     Columns   = 0;    // 查询的列数
  private int     Rows      = 0;    // 查询的行数
  private int     RowPages  = 0;    // 打印的行页数
  private int     ColPages  = 1;    // 打印的列页数
  private double  FirstTitleRowHeight = 0.50;// 主标题的行高
  private double  RowHeight = 0.25; // 固定行高 默认是0.25英寸
  private boolean Coupling  = true; // 承前继后标志
  private boolean TailNullRow = true;// 尾部是否加空行标记
  private boolean MuliColumns = false;// 是否行理多栏账或是
  private int     DataRowsPerPage = 0;// 每页数据行数 需要进行计算才能得到
  private int     ColsPerPage = 0;// 每页列数 需要进行计算才能得到
  private TableManager          FormatManger = null;  // 格式定义
  //private TreeTableDataManager  DataManager  = null; // 数据管理
  private Vector queryData;
  private Object  DrawObject    = null;
  private Vector  BibDataList      = null;
  private Vector  ParDataList      = null;
  private int     DataRows      = 0;
  private Object  PrintObject = null;
  private Object  Context     = null;

  private String  CompanyName = null;
  private String  SoftName    = null;
  private String  PrintDate   = null;

  private JParamObject PO = null;

  private Vector mColIds = null;

  private Vector mViewTitles = null;
  private Vector mValueCols = null;
  private Vector mValueGroups = null;
  private Vector mViewCols = null;
  //2007-4-3增加
  private String left_Tail;
  private String right_Tail;
  private String other_Tail;

  public void setTailRows(int TailRows) {
    this.TailRows = TailRows;
  }

  public void setDrawObject(Object DrawObject) {
    this.DrawObject = DrawObject;
  }

  public void setPrintObject(Object PrintObject) {
    this.PrintObject = PrintObject;
  }

  public void setFontSize(int FontSize) {
    this.FontSize = FontSize;
  }

  public void setFontName(String FontName) {
    this.FontName = FontName;
  }

  public Vector getMViewTitles() {
    return mViewTitles;
  }

  public Vector getMValueCols() {
    return mValueCols;
  }

  public Vector getMValueGroups() {
    return mValueGroups;
  }

  public void setRowPages(int RowPages) {
    this.RowPages = RowPages;
  }

  public int getRowPages() {
    return RowPages;
  }
  public void setColPages(int ColPages) {
    this.ColPages = ColPages;
  }

  public int getColPages() {
    return ColPages;
  }

  public Vector getMViewCols() {
    return mViewCols;
  }

  public void setFirstTitleRowHeight(double FirstTitleRowHeight) {
    this.FirstTitleRowHeight = FirstTitleRowHeight;
  }

  public double getFirstTitleRowHeight() {
    return FirstTitleRowHeight;
  }

  public Vector getMColIds() {
    return mColIds;
  }

  public String getLeft_Tail() {
    return left_Tail;
  }

  public String getRight_Tail() {
    return right_Tail;
  }

  public String getOther_Tail() {
    return other_Tail;
  }

  public Object getContext() {
    return Context;
  }

  public String getSoftName() {
    return SoftName;
  }

  public String getCompanyName() {
    return CompanyName;
  }

  public String getPrintDate() {
    return PrintDate;
  }

  public int getTailRows() {
    return TailRows;
  }

  public Object getDrawObject() {
    return DrawObject;
  }

  public Vector getBibDataList() {
    return BibDataList;
  }

  public int getDataRows() {
    return DataRows;
  }

  public Object getPrintObject() {
    return PrintObject;
  }

  public int getFontSize() {
    return FontSize;
  }

  public String getFontName() {
    return FontName;
  }

  public void setHeadRows(int HeadRows) {
    this.HeadRows = HeadRows;
  }

  public int getHeadRows() {
    return HeadRows;
  }

  public void setDataManager(Vector queryDataSet) {
    this.queryData = queryDataSet;
  }

  public Vector getDataManager() {
    return queryData;
  }

  public void setFormatManger(TableManager FormatManger) {
    this.FormatManger = FormatManger;
  }

  public TableManager getFormatManger() {
    return FormatManger;
  }

  public void setTitleRowHeight(double TitleRowHeight) {
    this.FirstTitleRowHeight = TitleRowHeight;
  }

  public double getTitleRowHeight() {
    return FirstTitleRowHeight;
  }

  public void setViewBook(JBook ViewBook) {
    this.ViewBook = ViewBook;
  }

  public JBook getViewBook() {
    return ViewBook;
  }

  public void setF1PageSets(JF1PageSets F1PageSets) {
    this.F1PageSets = F1PageSets;
  }

  public JF1PageSets getF1PageSets() {
    return F1PageSets;
  }

  public void setRows(int Rows) {
    this.Rows = Rows;
  }

  public int getRows() {
    return Rows;
  }

  public static void setCOUPLING_FRONT(String COUPLING_FRONT) {
    JAccountBookStub.COUPLING_FRONT = COUPLING_FRONT;
  }

  public static String getCOUPLING_FRONT() {
    return JAccountBookStub.COUPLING_FRONT;
  }

  public static void setCOUPLING_AFTER(String COUPLING_AFTER) {
    JAccountBookStub.COUPLING_AFTER = COUPLING_AFTER;
  }

  public static String getCOUPLING_AFTER() {
    return JAccountBookStub.COUPLING_AFTER;
  }

  public void setColumns(int Columns) {
    this.Columns = Columns;
  }

  public int getColumns() {
    return Columns;
  }
  // 获取每页的数据行数
  public int getDataRowsPerPage() {
    return DataRowsPerPage;
  }
  // 获取每页的总行数
  public int getRowsPerPage() {
    return this.TitleRows+this.HeadRows+this.DataRowsPerPage+this.TailRows;
  }

  public void setXmCol(int XmCol) {
    this.XmCol = XmCol;
  }

  public int getXmCol() {
    return XmCol;
  }

  public void setMuliColumns(boolean MuliColumns) {
    this.MuliColumns = MuliColumns;
  }

  public boolean isMuliColumns() {
    return MuliColumns;
  }

  public void setCoupling(boolean Coupling) {
    this.Coupling = Coupling;
  }

  public boolean isCoupling() {
    return Coupling;
  }

  public void setTitleRows(int TitleRows) {
    this.TitleRows = TitleRows;
  }

  public int getTitleRows() {
    return TitleRows;
  }

  public void setTailNullRow(boolean TailNullRow) {
    this.TailNullRow = TailNullRow;
  }

  public boolean isTailNullRow() {
    return TailNullRow;
  }

  public void setRowHeight(double RowHeight) {
    this.RowHeight = RowHeight;
  }

  public double getRowHeight() {
    return RowHeight;
  }

  public void setColsPerPage(int ColsPerPage) {
    this.ColsPerPage = ColsPerPage;
  }

  public int getColsPerPage() {
    return ColsPerPage;
  }
// 构造一个账薄对象的Stub
  /**
   *
   * @param fps JF1PageSets
   */
  public void initAccountBookStub() {
    // 初始化格式管理器
    buildFormatManger();
  }
  /**
   *
   * @param book JBook
   * @param tm TableManager
   * @param tdm TreeTableDataManager
   * @param Context Object
   * @param PrintObject Object
   */
  public JAccountBookStub(JBook book,TableManager tm,Vector queryData,Object Context,Object PrintObject,JParamObject po) {
    PO = po;
    ViewBook     = book;
    F1PageSets   = new JF1PageSets(book);
    FormatManger = tm;
    this.queryData  = queryData;
    this.Context     = Context;
    this.PrintObject = PrintObject;
    // 初始化单位名称，日期等
    initContext();
    // 初始化Manager
    initManager();
    //initAccountBookStub(book,tm,tdm,Context,PrintObject);
    //初始化页尾参数 2007-4-3
    initTail();
  }

  //2007-4-3
  private void initTail(){
//    PO.SetValueByParamName("LEFT_TAIL","");
//    PO.SetValueByParamName("RIGHT_TAIL","11111");
//    PO.SetValueByParamName("OTHER_TAIL","123\n456");
    this.left_Tail=PO.GetValueByParamName("LEFT_TAIL",null);
    this.right_Tail=PO.GetValueByParamName("RIGHT_TAIL",null);
    this.other_Tail=PO.GetValueByParamName("OTHER_TAIL",null);
    //设置页尾行数
    if(this.other_Tail!=null){
      this.TailRows += this.other_Tail.split("\n").length;
    }
  }

  private void initContext() {
    this.CompanyName = PO.GetValueByEnvName("CW_SYDWMC", "");
    this.SoftName    = PO.GetValueByEnvName("CW_SOFTMC", "");
    Calendar Cale = Calendar.getInstance();
    int Year,Month,Day;
    Year  = Cale.get(Calendar.YEAR);
    Month = Cale.get(Calendar.MONTH)+1;// 月份是从0开始的需要加1
    Day   = Cale.get(Calendar.DAY_OF_MONTH);
    PrintDate   = Year+res.getString("String_260")+Month+res.getString("String_261")+Day+res.getString("String_262");
  }
  private void initManager() {
    // 0 初始化TableManager
    initFormatManager();
    // 0.5 初始化TreeTableDataManager
    initTreeTableDataManager();
    // 1 确定标题行数
    TitleRows = buildTitleRows();
    // 2 确定表头行数
    HeadRows  = buildHeadRows();
    // 3 确定表的所有列数
    Columns   = buildViewCols();
  }
  // 创建格式参数
  private void buildFormatManger() {

    // 4 确定每页最多的数据行数
    DataRowsPerPage = buildDataRowsPerPage();
    // 5 确定每页最多的数据列
    ColsPerPage = buildColsPerPage();
    // 6 确定有多少数据行数
    Rows = buildViewRows();
    // 7 确定打印的行页数
    RowPages = buildPrintPage();
  }
  private void initTreeTableDataManager() {
    // 用来存放查询出的数据
    BibDataList = new Vector();
    // 获取所有列标识
    Object[] ColsID = this.FormatManger.getColId();// mValueCols.toArray();
    // 在这里形成所有的数据，与查询模式共用一组数据，节省空间和提高速度
    //DataManager.getData(ColsID,BibDataList);
    BibDataList=queryData;
    // 形成些次查询中的所有的数据行数
    //DataRows = DataManager.getDataSize(BibDataList);
    DataRows=queryData.size();
  }
  /**
   * 确定数据区的高度 页高-(页上边距+页眉+标题+表头+页脚+页下边距)
   * @return double
   */
  public double getDataHeight() {
    double DataHeight = 0.00; double FmtHeight = 0.00;
    // 确定标题高度
    if ( TitleRows > 0 )
      FmtHeight  = 1*this.FirstTitleRowHeight + (TitleRows-1)*RowHeight;
    // 确定表头高度
    FmtHeight += (HeadRows * RowHeight);
    // 确定表尾行数
    FmtHeight += (TailRows*RowHeight);
    DataHeight = F1PageSets.PrintPaperHeight -
                                               (F1PageSets.PrintTopMargin +
//                                                F1PageSets.PrintHeaderMargin +
                                                FmtHeight +
//                                                F1PageSets.PrintFooterMargin +
                                                F1PageSets.PrintBottomMargin
                                                );
    return DataHeight;
  }
  /**
   * 确定数据区的宽度
   * @return double
   */
  public double getDataWidth() {
    double DataWidth = 0.00;
    DataWidth = F1PageSets.PrintPaperWidth - (F1PageSets.PrintLeftMargin+F1PageSets.PrintRightMargin);
    return DataWidth;
  }
  /**  // 确定每页最多的数据行数
   * 计算公式:页高-(页上边距+页眉+标题+表头+页脚+页下边距)
   * @return int
   */
  private int buildDataRowsPerPage() {
    double DataHeight = getDataHeight();
    // 必须进行舍位操作，如果有余数，不管是4还是5都不能进行新的一行了
    double dRow = Math.floor(DataHeight / RowHeight);
//    double dRow = Math.round(DataHeight / RowHeight);
    return (int)dRow;
  }
  // 确定每页最多的数据列
  // 在多栏账，二维余额表里需要进行每页多少列数的计算，一般在三栏账，余额表里无需进行列的计算，所有的列都只放在一页上
  private int buildColsPerPage() {
    return mViewCols.size();
  }
  // 初始化TableManager
  private void initFormatManager() {
    mValueCols   = FormatManger.getColumns();
    mViewTitles  = FormatManger.getTitles();
    mViewCols    = FormatManger.getTableView();
    mValueGroups = FormatManger.getGroups();
  }
  // 获取标题行数
  private int buildTitleRows() {
    return FormatManger.getTitles().size();
  }
  // 获取表头行数
  private int buildHeadRows() {
    return JDRQueryPainterUtils.getHeaderLevels(FormatManger);
  }
  // 获取表的所有列数
  private int buildViewCols() {
    return JDRQueryPainterUtils.getTotalCols(mViewCols,mValueGroups);
  }
  // 获取表的所有行数
  private int buildViewRows() {
    Integer iCount = new Integer(0);
    if ( PrintObject != null ) {
      iCount = (Integer)JBOFClass.CallObjectMethod(PrintObject,"getCustomRows",this);
    }
    // 在此需要加上用户增加的行数，如在账页查询中需要增加的“承前页，承后页等”
    return DataRows+iCount.intValue();
  }
  // 打印的页数
  /**
   * 确定方法:
   * @return int
   */
  private int buildPrintPage() {
    double dDataRowsPerPage = (double)DataRowsPerPage;
    double dRows        = (double)Rows;
    // 在这里必须要加0.5处理，如果有余数，则必须要进行新的一页数据
    double dPages = Math.round((dRows/dDataRowsPerPage)+0.5);
    return (int)dPages;
  }

  public void setLeft_Tail(String left_Tail) {
    this.left_Tail = left_Tail;
  }

  public void setRight_Tail(String right_Tail) {
    this.right_Tail = right_Tail;
  }

  public void setOther_Tail(String other_Tail) {
    this.other_Tail = other_Tail;
  }
}
