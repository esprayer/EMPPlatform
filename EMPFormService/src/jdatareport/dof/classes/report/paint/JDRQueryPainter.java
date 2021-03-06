package jdatareport.dof.classes.report.paint;

import java.util.*;

import java.awt.*;
import java.awt.event.*;

import com.efounder.eai.data.JParamObject;
import com.f1j.ss.*;
import com.f1j.swing.*;
import com.pub.util.*;
import com.report.table.jxml.*;
import jdatareport.dof.classes.report.*;
import jdatareport.dof.classes.report.figure.*;
import jdatareport.dof.classes.report.filter.*;
import jdatareport.dof.classes.report.util.*;
import jservice.jbof.classes.GenerQueryObject.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JDRQueryPainter {
  /**
   *
   */
  private double FONT_SCALE = JDRConstants.DEFAULT_FONT_SCALE;
  private int miJedecn = 2, miSldecn = 3, miDjdecn = 2, miWbdecn = 4,
      miHldecn = 4; //数值精度

  private JBook mBook = null;
  private JDRModel mModel = null;
  private JDRQueryFormatInfo mFormatInfo = null;
  private JDRCellFigure mCellFigure = null;

  private TableManager mTitleMgr = null;
  private QueryDataSet queryDataSet;
  private Vector mColIds = new Vector();

  private Vector mViewTitles = null;
  private Vector mViewTails = null;
  private Vector mValueCols = null;
  private Vector mValueGroups = null;
  private Vector mViewCols = null;

  private int mColCount = 0;
  private int mTitleLevels = 0;

  private int mStartRow = 0;
  private int mStartCol = 0;

  private int mRowIndex = 0;
  private int mColIndex = 0;
  private int mBodyLevel = 1;

  private byte mFilter = JDRView.FILTER_ALL;
  /**
   * 过滤器
   */
  public JDRRowFilter mRowFilter = null;
  /**
   * 是否有未显示数据
   */
  public boolean hasMoreData = true;

  private String parentRowNumStr = "";

  /**
   * 记录某行的文本行数，譬如表头的第一行有3行文本。
   */
  private Map rowCountMap = new HashMap();
  /**
   * 标题行数
   */
  private int vTitleRows = 1;
  /**
   * 多查询区域的最大列数
   */
  private int maxColOfAllQueryArea = 0;
  /**
   * 查询是否多区域
   */
  private boolean isMultiQueryArea = false;
  /**
   * 是否打印表头
   */
  private boolean printHeader = true;

  /**
   * 构造器
   * @param cellFigure
   */
  public JDRQueryPainter(JBook book, JDRModel model, JDRCellFigure cellFigure) {
    try {
      if (book == null || model == null || cellFigure == null) {
        throw new IllegalArgumentException("null arguments");
      }
      this.mBook = book;
      this.mModel = model;
      this.mCellFigure = cellFigure;
      init();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setTableManager(TableManager tm) {
    mTitleMgr = tm;
    mValueCols = mTitleMgr.getColumns();
    mViewTitles = mTitleMgr.getTitles();
    mViewTails = mTitleMgr.getTails();
    mViewCols = mTitleMgr.getTableView();
    mValueGroups = mTitleMgr.getGroups();
    mColCount = JDRQueryPainterUtils.getTotalCols(mViewCols, mValueGroups);
    mTitleLevels = JDRQueryPainterUtils.getHeaderLevels(mTitleMgr);
    if (mColCount > maxColOfAllQueryArea) {
      maxColOfAllQueryArea = mColCount;
    }
  }

  /**
   * 初始化
   */
  private void init() throws Exception {
    Object[] queryResult = (Object[]) getQuery();
    if (queryResult != null && queryResult.length == 2 &&
        queryResult[0] != null && queryResult[1] != null) {
      //精度
      JParamObject PO = JParamObject.Create();
      this.miJedecn = PO.GetIntByEnvName("ZW_JEDECN", 2);
      this.miSldecn = PO.GetIntByEnvName("ZW_SLDECN", 3);
      this.miDjdecn = PO.GetIntByEnvName("ZW_DJDECN", 2);
      this.miWbdecn = PO.GetIntByEnvName("ZW_WBDECN", 4);
      this.miHldecn = PO.GetIntByEnvName("ZW_HLDECN", 4);

      mFormatInfo = this.mModel.getQueryFormat();
      mTitleMgr = (TableManager) queryResult[0];
      queryDataSet = (QueryDataSet) queryResult[1];
      mValueCols = mTitleMgr.getColumns();
      mViewTitles = mTitleMgr.getTitles();
      mViewTails = mTitleMgr.getTails();
      mViewCols = mTitleMgr.getTableView();
      mValueGroups = mTitleMgr.getGroups();

      mColCount = JDRQueryPainterUtils.getTotalCols(mViewCols, mValueGroups);
      mTitleLevels = JDRQueryPainterUtils.getHeaderLevels(mTitleMgr);

      maxColOfAllQueryArea = mColCount;

      mModel.setMaxCol(mColCount);
      mModel.setTitleRowCount(mViewTitles.size());
      mModel.setHeadRowCount(mTitleLevels);

      //坐标
      int cellStartRow = mCellFigure.getStartRow();
      int cellStartCol = mCellFigure.getStartCol();
      int cellEndRow = mCellFigure.getEndRow();
      int cellEndCol = mCellFigure.getEndCol();

      int refRow = 0;
      int refCol = 0;
      //refRow = JDRPaintUtils.calcRefRow(mModel, mCellFigure);
      refCol = JDRPaintUtils.calcRefCol(mModel, mCellFigure);
      mStartRow = cellStartRow + refRow;
      mStartCol = cellStartCol + refCol;

      //是否打印表头 2008-8-5 fengbo
      Vector attrVector = mTitleMgr.getAttrExt();
      XMLAttr attr = null;
      String id = null;
      String value = null;
      for (int i = 0; i < attrVector.size(); i++) {
        attr = (XMLAttr) attrVector.get(i);
        id = attr.getId();
        //是否打印表头
        if (TableManager.ATTR_PRINT_HEAD_ID.equals(id)) {
          value = attr.getValue();
          this.printHeader = TableManager.ATTR_VALUE_YES.equals(value);
        }
      }
    }
  }

  /**
   * 绘制非过滤查询结果
   * @param cellFiguref
   * @throws java.lang.Exception
   */
  public void drawQuery(byte filter, boolean flag) throws Exception {
    this.mRowFilter = null;
    this.mFilter = filter;
    short ShowScroll = 1;
    try {
      this.mBook.setRowSummaryBeforeDetail(true);
      //this.mBook.setRowMode(false);
      mBook.setShowGridLines(false);
      mBook.setAllowInCellEditing(false);
      mBook.setAllowDelete(false);
      //2007-03-16 YRH 不允许 ＋ 这个东西拖动
      mBook.setAllowFillRange(false);
      //mBook.setEnableProtection(true);
      mBook.setShowHScrollBar(ShowScroll);
      mBook.setShowVScrollBar(ShowScroll);
      mBook.setRowMode(false);
      mBook.setColWidthUnits(mBook.eColWidthUnitsTwips);
      drawQueryTitle();
      drawQueryHeader();
      drawQueryBody(flag);
      drawQueryFooter();
//      this.mBook.setRowMode(true);
      //setMouseEvent();
      setBookBackColor();
      //选中首行
      int startRow = mModel.getTitleRowCount() + mModel.getHeadRowCount();
      mBook.setSelection(startRow, mStartCol, startRow, mStartCol + mColCount);
      //取消过滤查询结果
      queryDataSet.setRowFilter(null);
      queryDataSet.filter(mColIds.size());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置表格颜色
   */
  private void setBookBackColor() throws Exception {
    CellFormat cf;
    //1 设置表头的底色
    int titleRowCount = mViewTitles.size();
    int headColount = mStartCol + mColCount - 1;
    int headRowCount = mTitleLevels - 1;
    mBook.setSelection(mStartRow + titleRowCount, mStartCol,
                       mStartRow + titleRowCount + headRowCount,
                       mStartCol + headColount);
    cf = mBook.getCellFormat();
    cf.setPatternFG(0XCEDDF0);
    mBook.setCellFormat(cf);
    //2 表格数据部分的背景色
    //每组行数
//    int COLOR_LINE = 3;
//    int crtRow;
//    int startRow = mModel.getTitleRowCount() + mModel.getHeadRowCount();
//    //调整开始行
//    int k = (startRow % COLOR_LINE);
//    for (int i = 0; i < mBook.getLastRow() + 1 - startRow; i++) {
//      //对比色
//      crtRow = startRow + i;
//      mBook.setSelection(crtRow, 0, crtRow, mColIds.size());
//      cf = mBook.getCellFormat();
//      cf.setPattern(CellFormat.ePatternCanvas);
//      //偶数组颜色
//      if ( ( ( (crtRow - k) / COLOR_LINE) % 2) == 0) {
//        cf.setPatternFG(0XF5F6F8);
//      }
//      //奇数组颜色，（奇怪，JBook对颜色不敏感，淡颜色总是显示为白色！？）
//      else {
//        //注释此行,该组背景色会继承Windows窗口的背景色;
//        cf.setPatternFG(0XE5F1FF);//cf.setPatternFG(0XE5F1FF);
//      }
//      mBook.setCellFormat(cf);
//    }
    //3 设置外围颜色
    mBook.setExtraColor(new Color(0XF5, 0XF6, 0XF8));

    //背景色
    /**整体背景色，此方法设置的颜色仅对没有单独设置颜色的位置起作用;
     * 譬如前面已经设置了表头、标体的颜色,则这些区域的背景色不受此方法的影响.
     */
    mBook.setBackColor(new Color(0XF5, 0XF6, 0XF8));

  }

  /**
   * 鼠标事件
   */
  private void setMouseEvent() {
    Point p = new Point(200, 200);
    //SwingUtilities.convertPointToScreen(p,mBook);
    MouseEvent ME = new MouseEvent(mBook, MouseEvent.MOUSE_CLICKED,
                                   System.currentTimeMillis(),
                                   MouseEvent.BUTTON1_MASK, p.x, p.y, 1, false,
                                   MouseEvent.BUTTON1);
    mBook.dispatchEvent(ME);
  }

  /**
   * 过滤绘制查询结果
   * @param cellFiguref
   * @throws java.lang.Exception
   */
  public void drawQuery(JDRRowFilter colFilter) throws Exception {
    this.mRowFilter = colFilter;
    try {
      mBook.setRowMode(false);
      this.mBook.setRowSummaryBeforeDetail(true);
      mBook.setShowGridLines(false);
      mBook.setAllowInCellEditing(false);
      mBook.setAllowDelete(false);
      drawQueryTitle();
      drawQueryHeader();
      drawQueryBody(true);
      drawQueryFooter();
//      this.mBook.setRowMode(true);
      //setMouseEvent();
      setBookBackColor();
      //选中首行
      int startRow = mModel.getTitleRowCount() + mModel.getHeadRowCount();
      mBook.setSelection(startRow, mStartCol, startRow, mStartCol + mColCount);
      //过滤查询结果
      queryDataSet.setRowFilter(mRowFilter);
      queryDataSet.filter(mColIds.size());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 绘制标题
   * @throws java.lang.Exception
   */
  public void drawQueryTitle() throws Exception {
    for (int i = 0; i < mViewTitles.size(); i++) {
      int row = mStartRow + mRowIndex;
//      int col = mStartCol + mColIndex;
      int col = mStartCol;
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
      colInsets = checkColumn(colInsets);

      if (labelCount == 1) {
        labelIndex = 0;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, replaceNextLineTag(labelText));
        }
        mBook.setSelection(row, col, row, mColCount - 1);
        cf = mBook.getCellFormat();
        cf.setMergeCells(true);
        mBook.setCellFormat(cf);

        this.setQueryTitleFormat(row, col, title, labelIndex);
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
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, replaceNextLineTag(labelText));
        }
        this.setQueryTitleFormat(row, col, title, labelIndex);

        labelIndex = 1;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + colInsets, replaceNextLineTag(labelText));
        }
        this.setQueryTitleFormat(row, col + colInsets, title, labelIndex);

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

        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, labelText);
        }
        this.setQueryTitleFormat(row, col, title, labelIndex);

        labelIndex = 1;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + col + colInsets,
                        replaceNextLineTag(labelText));
        }
        this.setQueryTitleFormat(row, col + colInsets, title, labelIndex);

        labelIndex = 2;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + 2 * colInsets, replaceNextLineTag(labelText));
        }
        this.setQueryTitleFormat(row, col + 2 * colInsets, title, labelIndex);

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
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, replaceNextLineTag(labelText));
        }
        this.setQueryTitleFormat(row, col, title, labelIndex);

        labelIndex = 1;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + colInsets, replaceNextLineTag(labelText));
        }
        this.setQueryTitleFormat(row, col + colInsets, title, labelIndex);

        labelIndex = 2;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          //追加处理
          for (int j = 3; j < labelCount; j++) {
            label = (XmlLabel) labels.elementAt(j);
            labelText += " ";
            labelText += label.getCaption();
          }
          mBook.setText(row, col + 2 * colInsets, replaceNextLineTag(labelText));
        }
        this.setQueryTitleFormat(row, col + 2 * colInsets, title, labelIndex);

      }
      mRowIndex++;
      this.vTitleRows = mRowIndex;
    }

  }

  /**
   * 设置标题格式
   * @param title
   * @param labelIndex
   * @throws java.lang.Exception
   */
  private void setQueryTitleFormat(int row, int col, XmlTitle title,
                                   int labelIndex) throws Exception {
    if (this.mFilter == JDRView.FILTER_ALL ||
        this.mFilter == JDRView.FILTER_FORMAT_ONLY) {
      mBook.setSelection(row, col, row, col);
      CellFormat cf = mBook.getCellFormat();
      //格式
      String align = title.getAlign();
      int fontSize = (int) (Integer.parseInt(title.getFontsize().trim()) *
                            FONT_SCALE);
      String fontName = title.getFontname();
      String fontStyle = title.getFontstyle();
      if (fontStyle==null || fontStyle.equals("")) fontStyle = "0";
      int ifontStyle = Integer.parseInt(fontStyle);
      //主标题用特殊字体
//      if (row == 0) {
//        fontName = "华文中宋"; //JDRConstants.DEFAULT_FONT_NAME;
//      }
//      else {
//        fontName = JDRConstants.DEFAULT_FONT_NAME;
//      }
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
      cf.setTopBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setTopBorder(mFormatInfo.mTitleInnerBorder);
      cf.setBottomBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setBottomBorder(mFormatInfo.mTitleInnerBorder);
      cf.setLeftBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setLeftBorder(mFormatInfo.mTitleInnerBorder);
      cf.setRightBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setRightBorder(mFormatInfo.mTitleInnerBorder);

      cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
      cf.setFontSize(fontSize);
      cf.setFontName(fontName);
      switch (ifontStyle) {
        case 1:
          cf.setFontBold(true);
          break;
        case 2:
          cf.setFontItalic(true);
          break;
        case 3:
          cf.setFontUnderline(true);
      }
      mBook.setCellFormat(cf);
    }
  }

  /**
   * 绘制表头
   * @throws java.lang.Exception
   */
  public void drawQueryHeader() throws Exception {
    mColIds.clear();
    mColIndex = 0;
    boolean isLast = false;
    for (int i = 0; i < mViewCols.size(); i++) {
      isLast = (i == mViewCols.size() - 1);
      int row = mStartRow + mRowIndex;
      int col = mStartCol + mColIndex;
      XmlViewCol xmlViewCol = (XmlViewCol) mViewCols.elementAt(i);
      if (xmlViewCol.getType().equals("col")) {
        XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(
            mValueCols, xmlViewCol.getId());
        //合并单元格
        //最后一列与上一个查询格式右对齐
        //2008-8-4 fengbo
        if (printHeader) {
          if (isLast && this.isIsMultiQueryArea()
              && this.maxColOfAllQueryArea > this.mColCount) {
            int beginCol = col;
            int endCol = this.maxColOfAllQueryArea - 1;
            mBook.setSelection(row, beginCol,
                               mStartRow + (mRowIndex + mTitleLevels - 1),
                               endCol);
          }
          else {
            mBook.setSelection(row, col,
                               mStartRow + (mRowIndex + mTitleLevels - 1),
                               col);
          }

          CellFormat cf = mBook.getCellFormat();
          cf.setMergeCells(true);
          mBook.setCellFormat(cf);
          if (this.mFilter == JDRView.FILTER_ALL ||
              this.mFilter == JDRView.FILTER_DATA_ONLY) {
            String headerText = xmlColumn.getCaption();
            mBook.setText(row, col, replaceNextLineTag(headerText));
          }

          //格式
          mBook.setSelection(row, col, row, col);
          setQueryHeaderFormat(xmlColumn);
        }
        mColIds.addElement(xmlColumn.getId());
        mColIndex++;
      }
      else if (xmlViewCol.getType().equals("group")) {
        XmlGroup xmlGroup = (XmlGroup) TableManager.getElementById(mValueGroups,
            xmlViewCol.getId());
        Vector leafItems = JDRQueryPainterUtils.getLeafItems(mTitleMgr,
            xmlGroup);
        if (leafItems.size() > 0) {
          drawQueryGroup(mRowIndex, mColIndex,
                         (mRowIndex + mTitleLevels - 1),
                         xmlGroup, isLast);
          mColIndex += leafItems.size();
        }
        for (int k = 0; k < leafItems.size(); k++) {
          XmlItem leafItem = (XmlItem) leafItems.elementAt(k);
          XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(
              mValueCols, leafItem.getId());
          mColIds.addElement(xmlColumn.getId());
        }
      }
    }
    if (!isMultiQueryArea) {
      setQueryTHOutterFormat();
    }
    if (this.printHeader) {
      mRowIndex += mTitleLevels;
    }
  }

  /**
   * 设置表头格式
   * @param xmlColumn
   * @throws java.lang.Exception
   */
  private void setQueryHeaderFormat(XmlColumn xmlColumn) throws Exception {
    if (this.mFilter == JDRView.FILTER_ALL ||
        this.mFilter == JDRView.FILTER_FORMAT_ONLY) {
      int fontSize = (int) (Integer.parseInt(xmlColumn.getFontsize().trim()) *
                            FONT_SCALE);
      String fontName = xmlColumn.getFontname();
      String fontStyle = xmlColumn.getFontstyle();
      if (fontStyle==null || fontStyle.equals("")) fontStyle = "0";
      int ifontStyle = Integer.parseInt(fontStyle);
//      fontName = JDRConstants.DEFAULT_FONT_NAME;

      CellFormat cf = mBook.getCellFormat();
      cf.setTopBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setTopBorder(mFormatInfo.mHeadInnerBorder);
      cf.setBottomBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setBottomBorder(mFormatInfo.mHeadInnerBorder);
      cf.setLeftBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setLeftBorder(mFormatInfo.mHeadInnerBorder);
      cf.setRightBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setRightBorder(mFormatInfo.mHeadInnerBorder);

      cf.setFontSize(fontSize);
      cf.setFontName(fontName);
      switch (ifontStyle) {
        case 1:
          cf.setFontBold(true);
          break;
        case 2:
          cf.setFontItalic(true);
          break;
        case 3:
          cf.setFontUnderline(true);
      }

      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
      cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
      String strTemp = xmlColumn.getWidth();
      int width = 0;
      if (strTemp == null || strTemp.equals("")) {
        width = 25;
      }
      else {
        width = Integer.parseInt(strTemp) / 6;
      }
      mBook.setCellFormat(cf);
    }
  }

  /**
   * 格式设置
   * @throws java.lang.Exception
   */
  private void setQueryTHOutterFormat() throws Exception {
    if (this.mFilter == JDRView.FILTER_ALL ||
        this.mFilter == JDRView.FILTER_FORMAT_ONLY) {
      //表头格式
      int titleRowCount = mViewTitles.size();
      int headColount = mStartCol + mColCount - 1;
      this.mBook.setSelection(mStartRow, mStartCol, mStartRow + titleRowCount,
                              mStartCol + headColount);
      CellFormat cf = mBook.getCellFormat();
      cf.setTopBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setTopBorder(mFormatInfo.mTitleOuterBorder);
      cf.setBottomBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setBottomBorder(mFormatInfo.mTitleOuterBorder);
      cf.setLeftBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setLeftBorder(mFormatInfo.mTitleOuterBorder);
      cf.setRightBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setRightBorder(mFormatInfo.mTitleOuterBorder);
      mBook.setCellFormat(cf);

      int headRowCount = mTitleLevels - 1;
      this.mBook.setSelection(mStartRow + titleRowCount, mStartCol,
                              mStartRow + titleRowCount + headRowCount,
                              mStartCol + headColount);
      cf = mBook.getCellFormat();
      cf.setTopBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setTopBorder(mFormatInfo.mHeadOuterBorder);
      cf.setBottomBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setBottomBorder(mFormatInfo.mHeadOuterBorder);
      cf.setLeftBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setLeftBorder(mFormatInfo.mHeadOuterBorder);
      cf.setRightBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setRightBorder(mFormatInfo.mHeadOuterBorder);
      cf.setPattern(CellFormat.ePatternSolid);
      //设置表头的底色
      cf.setPatternFG(new java.awt.Color(0XCE, 0XDD, 0XF0).getRGB());
      mBook.setCellFormat(cf);
    }
  }

  /**
   * 绘制查询体数据
   * @param flag boolean  数据是否复位
   * @return boolean      是否还有未显示的数据
   * @throws Exception
   */
  public void drawQueryBody(boolean flag) throws Exception {
    Vector pageVector;
    mBook.setRowMode(false);
    /**
     * 数据页复位，数据层级关系复位条件：
     * 1.设置过滤条件后，第一次取数据时；
     * 2.取消过滤条件时，第一次取数据时；
     */
    if (flag) {
      //1 清除 Book数据
      int k = true ? 1 : 0;
      int startRow = mModel.getTitleRowCount() + mModel.getHeadRowCount();
      int startCol = 0;
      int endRow = mModel.getMaxRow() + 1;
      int endCol = mModel.getMaxCol();
      if (endCol > 0) {
        endCol--;
      }
      mBook.setSelection(startRow, startCol, endRow, endCol);
      mBook.editDelete(JBook.eClearAll);
      //2 同步Model
      mModel.setMaxRow(mBook.getLastRow());
      //3 数据页复位
      queryDataSet.setCurrentPageNum(0);
      //过滤时,要清除级次信息
      mBook.setRowOutlineLevel(startRow, endRow, 0, false);
      hasMoreData = true;
    }
    //过滤状态时,不论数据是否分级,均按非分级数据处理
    if (this.mRowFilter != null) {
      pageVector = queryDataSet.getNextPageData(false);
    }
    else {
      pageVector = queryDataSet.getNextPageData(true);
      //如果数据分级，则一次全部取出数据，因此不再有未装载数据
      if (queryDataSet.isClassed()) {
        hasMoreData = false;
      }
    }
    if (pageVector == null || pageVector.size() == 0) {
      hasMoreData = false;
      return;
    }
    int bodyDataBeginRowIndex = mRowIndex;
    //2008-8-5 fengbo
    if (this.printHeader) {
      drawQueryBodyData(mBook.getLastRow() + 1, pageVector);
    }
    else {
      drawQueryBodyData(mBook.getLastRow(), pageVector);
    }

    int bodyDataEndRowIndex = mBook.getLastRow();
    setQueryBodyFormat(bodyDataBeginRowIndex, bodyDataEndRowIndex);
//    //计算下一次的起始位置
//    mRowIndex = mBook.getLastRow() + 1;
//    mBook.setRowMode(true);

//    //数据不分级时，编辑保护，不允许任何编辑操作
//    if(!queryDataSet.isClassed()){
//      this.mBook.getBook().getSheet(0).setEnableProtection(true); //.setProtection(true,"",1);
//    }

    //数据分级且存在父结点行，则关闭父结点行
    if (queryDataSet.isClassed() && parentRowNumStr.length() > 0) {
      String[] rowNumStr = parentRowNumStr.split(",");
      int rowNum;
      for (int i = 0; i < rowNumStr.length; i++) {
        rowNum = Integer.parseInt(rowNumStr[i]);
        this.mBook.getBook().getSheet(0).setRowOutlineCollapsed(rowNum, true);
      }
      parentRowNumStr = "";
    }
  }

  /**
   * 设置数据格式
   * @throws java.lang.Exception
   */
  public void setQueryBodyFormat(int begin, int end) throws Exception {
    if (this.mFilter == JDRView.FILTER_ALL ||
        this.mFilter == JDRView.FILTER_FORMAT_ONLY) {
      int bodyStartRow = mModel.getTitleRowCount();
      if (this.printHeader) {
        bodyStartRow += mModel.getHeadRowCount();
      }
      int bodyEndRow = mModel.getMaxRow();
      int bodyRowCount = mModel.getBodyRowCount();

      if (begin == -1) {
        begin = mStartRow + bodyStartRow;
      }
      if (end == -1) {
        end = mStartRow + bodyEndRow;
      }

      if (bodyRowCount <= 0) {
        return;
      }
      /**
       * 负数是否显示为红字标志，可以在数据中心中设置
       * 默认为显示红字
       * add by hufeng 2005.11.17
       */
      JParamObject PO = JParamObject.Create();
      String showRedOfDefault= PO.GetValueByEnvName("CX_SHOWRED", "");
      if (showRedOfDefault == null || showRedOfDefault.trim().equals("")) {
        showRedOfDefault = "1";
      }
      //设置最大列数
      this.mBook.setMaxCol(this.maxColOfAllQueryArea - 1);
      for (int i = 0; i < mColIds.size(); i++) {
        XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(
            mValueCols, (String) mColIds.get(i));
        //取负数显示格式,借用getEditctrl()方法 2008-9-7 fengbo
        //优先使用本查询格式规定的负数显示模式
        String showRed = xmlColumn.getEditctrl();
        if (showRed == null || showRed.trim().equals("")) {
          showRed = showRedOfDefault;
        }
        int fontSize = (int) (Integer.parseInt(xmlColumn.getFontsize().trim()) *
                              FONT_SCALE);
        String fontName = xmlColumn.getFontname();

        String type = xmlColumn.getDatatype();
        String format = xmlColumn.getFormat();
        String colWidth = null;
        if (this.isMultiQueryArea) {
          colWidth = String.valueOf(this.mBook.getColWidth(i) /
                                    JDRConstants.DEFAULT_SCALE);
        }
        else {
          colWidth = xmlColumn.getWidth();
        }
        String align = xmlColumn.getAlign();

        //C,N,J,S,D,W,H
        if (format == null) {
          format = "";
        }
        //取XML文件中的datatype，如果是N则保持原来的
        if (type.equals("N")) {
          format = xmlColumn.getFormat();

          //若未指定格式，则提供默认格式；否则数据不能显示 2007-4-9 fengbo
          if (format == null || format.trim().equals("")) {
            format = "#,##0.00";
          }

          if (format.startsWith(",")) {
            format = "#" + format;
          }
          if (showRed.equals("1")) {
            format += ";[Red]" + format + ";";
          }
          else {
            format += ";-" + format + ";";
          }
        }
        else if (type.equals("J")) { //金额
          format = "#,##0." + StringFunction.FillZeroFromBegin(0, miJedecn);
          if (showRed.equals("1")) {
            format += ";[Red]" + format + ";";
          }
          else {
            format += ";-" + format + ";";
          }
        }
        else if (type.equals("S")) { //数量
          format = "#,##0." + StringFunction.FillZeroFromBegin(0, miSldecn);
          if (showRed.equals("1")) {
            format += ";[Red]" + format + ";";
          }
          else {
            format += ";-" + format + ";";
          }
        }
        else if (type.equals("D")) { //单价
          format = "#,##0." + StringFunction.FillZeroFromBegin(0, miDjdecn);
          if (showRed.equals("1")) {
            format += ";[Red]" + format + ";";
          }
          else {
            format += ";-" + format + ";";
          }
        }
        else if (type.equals("W")) { //外币
          format = "#,##0." + StringFunction.FillZeroFromBegin(0, miWbdecn);
          if (showRed.equals("1")) {
            format += ";[Red]" + format + ";";
          }
          else {
            format += ";-" + format + ";";
          }
        }
        else if (type.equals("H")) { //汇率
          format = "#,##0." + StringFunction.FillZeroFromBegin(0, miHldecn);
          if (showRed.equals("1")) {
            format += ";[Red]" + format + ";";
          }
          else {
            format += ";-" + format + ";";
          }
        }

        if (colWidth != null && colWidth.length() > 0) {
          this.mBook.setColWidth(mStartCol + i,
                                 Integer.parseInt(colWidth) *
                                 JDRConstants.DEFAULT_SCALE);
        }
//        mBook.setSelection(mStartRow + bodyStartRow, mStartCol + i,
//                           mStartRow + bodyEndRow, mStartCol + i);
        mBook.setSelection(begin, mStartCol + i,
                           end, mStartCol + i);
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

      //设置第一行（标题行）行高为2倍标准行高
      this.mBook.setRowHeight(mStartRow, mStartRow,
                              JDRConstants.DEFAULT_ROW_HEIGHT * 2, false, false);

      //若有次标题,则设置次标题行高为标准行高
      if (vTitleRows > 1) {
        this.mBook.setRowHeight(mStartRow + 1, mStartRow + vTitleRows - 1,
                                JDRConstants.DEFAULT_ROW_HEIGHT, false, false);
      }
      //设置表头行高为实际行高,暂不使用此方案
      //setHeadRowHight();

      //设置表体行高为标准行高,标准行高支持14号字体
      int maxFonrSize = getMaxFontSize(this.mTitleMgr);
      double k = (double) maxFonrSize / 14;
      this.mBook.setRowHeight(mStartRow + bodyStartRow, mStartRow + bodyEndRow,
                              (int) (JDRConstants.DEFAULT_ROW_HEIGHT * k) + 2, false, false);

      //整体格式
      this.mBook.setSelection(mStartRow + bodyStartRow, mStartCol,
                              mStartRow + bodyEndRow,
                              mStartCol + this.mColCount - 1);
      CellFormat cf = mBook.getCellFormat();
      cf.setBottomBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setBottomBorder(mFormatInfo.mBodyOuterBorder);
      cf.setLeftBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setLeftBorder(mFormatInfo.mBodyOuterBorder);
      cf.setRightBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setRightBorder(mFormatInfo.mBodyOuterBorder);
      cf.setHorizontalInsideBorder(mFormatInfo.mBodyInnerBorder);
      cf.setHorizontalInsideBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setVerticalInsideBorder(mFormatInfo.mBodyInnerBorder);
      cf.setVerticalInsideBorderColor(mFormatInfo.BORDERCOLOR);
      mBook.setCellFormat(cf);
      mBook.setActiveCell(bodyStartRow, 0);
      mBook.setSelection(bodyStartRow, 0, bodyStartRow, 0);
    }
  }

  /**
   * 绘制组
   * @param curRow
   * @param curCol
   * @param endRow
   * @param xmlGroup
   * @throws java.lang.Exception
   */
  private void drawQueryGroup(int rowIndex, int colIndex, int endRow,
                              XmlGroup xmlGroup, boolean isLast) throws
      Exception {
    Vector leafItems = JDRQueryPainterUtils.getLeafItems(mTitleMgr, xmlGroup);
    int leafCount = leafItems.size();
    try {
      //最后一列与上一个查询格式右对齐
      if (isLast && this.isIsMultiQueryArea()
          && this.maxColOfAllQueryArea > this.mColCount) {
        int endCol = this.maxColOfAllQueryArea - 1;
        mBook.setSelection(mStartRow + rowIndex,
                           mStartCol + colIndex,
                           mStartRow + rowIndex,
                           endCol);
      }
      else {
        mBook.setSelection(mStartRow + rowIndex,
                           mStartCol + colIndex,
                           mStartRow + rowIndex,
                           mStartCol + (colIndex + leafCount - 1));
      }
      CellFormat cf = mBook.getCellFormat();
      cf.setMergeCells(true);
      mBook.setCellFormat(cf);
      if (this.mFilter == JDRView.FILTER_ALL ||
          this.mFilter == JDRView.FILTER_DATA_ONLY) {
        String groupText = xmlGroup.getCaption();
        mBook.setText(mStartRow + rowIndex, mStartCol + colIndex,
                      replaceNextLineTag(groupText));
      }
      setQueryGroupFormat(rowIndex, colIndex, endRow, xmlGroup);

      rowIndex++;
      drawQueryGroupItems(rowIndex, colIndex, endRow, xmlGroup, isLast);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置组格式
   * @param rowIndex
   * @param colIndex
   * @param endRow
   * @param xmlGroup
   * @throws java.lang.Exception
   */
  private void setQueryGroupFormat(int rowIndex, int colIndex, int endRow,
                                   XmlGroup xmlGroup) throws Exception {
    if (this.mFilter == JDRView.FILTER_ALL ||
        this.mFilter == JDRView.FILTER_FORMAT_ONLY) {
      int fontSize = JDRConstants.DEFAULT_FONT_SIZE;
      if (xmlGroup.getFontsize() != null &&
          xmlGroup.getFontsize().trim().length() > 0) {
        fontSize = (int) (Integer.parseInt(xmlGroup.getFontsize().trim()) *
                          FONT_SCALE);
      }
      String fontName = JDRConstants.DEFAULT_FONT_NAME;
      if (xmlGroup.getFontname() != null && xmlGroup.getFontname().length() > 0) {
        fontName = xmlGroup.getFontname();
      }
      String fontStyle = xmlGroup.getFontstyle();
      if (fontStyle==null || fontStyle.equals("")) fontStyle = "0";
      int ifontStyle = Integer.parseInt(fontStyle);

//      fontName = JDRConstants.DEFAULT_FONT_NAME;

      mBook.setSelection(mStartRow + rowIndex, mStartCol + colIndex,
                         mStartRow + rowIndex, mStartCol + colIndex);
      CellFormat cf = mBook.getCellFormat();
      cf.setTopBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setTopBorder(mFormatInfo.mHeadInnerBorder);
      cf.setBottomBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setBottomBorder(mFormatInfo.mHeadInnerBorder);
      cf.setLeftBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setLeftBorder(mFormatInfo.mHeadInnerBorder);
      cf.setRightBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setRightBorder(mFormatInfo.mHeadInnerBorder);

      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
      cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
      cf.setFontSize(fontSize);
      switch (ifontStyle) {
        case 1:
          cf.setFontBold(true);
          break;
        case 2:
          cf.setFontItalic(true);
          break;
        case 3:
          cf.setFontUnderline(true);
      }

      cf.setFontName(fontName);
      mBook.setCellFormat(cf);
    }
  }

  /**
   * 绘制组的细目
   * @param rowIndex
   * @param colIndex
   * @param endRow
   * @param xmlGroup
   * @throws java.lang.Exception
   */
  private void drawQueryGroupItems(int rowIndex, int colIndex, int endRow,
                                   XmlGroup xmlGroup, boolean isLast) throws
      Exception {
    boolean tag = false;
    Vector xmlItems = xmlGroup.getItems();
    for (int i = 0; i < xmlItems.size(); i++) {
      tag = (i == xmlItems.size() - 1);
      XmlItem xmlItem = (XmlItem) xmlItems.elementAt(i);
      if (xmlItem.getType().equals("col")) {
        XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(
            mValueCols, xmlItem.getId());
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          String itemText = xmlColumn.getCaption();
          mBook.setText(mStartRow + rowIndex, mStartCol + colIndex,
                        replaceNextLineTag(itemText));
        }
        if (tag && isLast && this.isIsMultiQueryArea()
            && this.maxColOfAllQueryArea > this.mColCount) {
          int endCol = this.maxColOfAllQueryArea - 1;
          mBook.setSelection(mStartRow + rowIndex,
                             mStartCol + colIndex,
                             mStartRow + endRow,
                             endCol);
        }
        else {
          mBook.setSelection(mStartRow + rowIndex,
                             mStartCol + colIndex,
                             mStartRow + endRow,
                             mStartCol + colIndex);
        }
        CellFormat cf = mBook.getCellFormat();
        cf.setMergeCells(true);
        mBook.setCellFormat(cf);

        setQueryGroupItemsFormat(rowIndex, colIndex, endRow, xmlColumn);
        colIndex++;
      }
      else if (xmlItem.getType().equals("group")) {
        XmlGroup xmlTemp = (XmlGroup) mTitleMgr.getGroupById(xmlItem.getId());
        drawQueryGroup(rowIndex, colIndex, endRow, xmlTemp, tag && isLast);
        colIndex += JDRQueryPainterUtils.getGroupCols(mValueGroups, xmlTemp);
      }
    }
  }

  /**
   * 设置组细目格式
   * @param rowIndex
   * @param colIndex
   * @param endRow
   * @param xmlColumn
   * @throws java.lang.Exception
   */
  private void setQueryGroupItemsFormat(int rowIndex, int colIndex, int endRow,
                                        XmlColumn xmlColumn) throws Exception {
    if (this.mFilter == JDRView.FILTER_ALL ||
        this.mFilter == JDRView.FILTER_FORMAT_ONLY) {
      //格式
      int fontSize = JDRConstants.DEFAULT_FONT_SIZE;
      if (xmlColumn.getFontsize() != null &&
          xmlColumn.getFontsize().trim().length() > 0) {
        fontSize = (int) (Integer.parseInt(xmlColumn.getFontsize().trim()) *
                          FONT_SCALE);
      }
      String fontName = JDRConstants.DEFAULT_FONT_NAME;
      if (xmlColumn.getFontname() != null &&
          xmlColumn.getFontname().length() > 0) {
        fontName = xmlColumn.getFontname();
      }
      String fontStyle = xmlColumn.getFontstyle();
      if (fontStyle==null || fontStyle.equals("")) fontStyle = "0";
      int ifontStyle = Integer.parseInt(fontStyle);
//      fontName = JDRConstants.DEFAULT_FONT_NAME;
      int row = mStartRow + rowIndex;
      int col = mStartCol + colIndex;
      mBook.setSelection(row, col, row, col);
      CellFormat cf = mBook.getCellFormat();
      cf.setTopBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setTopBorder(mFormatInfo.mHeadInnerBorder);
      cf.setBottomBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setBottomBorder(mFormatInfo.mHeadInnerBorder);
      cf.setLeftBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setLeftBorder(mFormatInfo.mHeadInnerBorder);
      cf.setRightBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setRightBorder(mFormatInfo.mHeadInnerBorder);
      cf.setFontSize(fontSize);
      cf.setFontName(fontName);
      switch (ifontStyle) {
        case 1:
          cf.setFontBold(true);
          break;
        case 2:
          cf.setFontItalic(true);
          break;
        case 3:
          cf.setFontUnderline(true);
      }

      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
      cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
      mBook.setCellFormat(cf);
    }
  }

  /**
   * 绘制数据
   * @param rowIndex int       绘制数据起始行
   * @param dataVector Vector  数据集合
   * @throws Exception
   */
  public void drawQueryBodyData(int rowIndex, Vector dataVector) throws
      Exception {
    if (this.mColIds == null || queryDataSet == null) {
      return;
    }
    int visiableRowCount = 0;
    XmlColumn xmlColumn;
    String type;
    String showmask;
    String sValue;
    int col;
    Vector childVector;
    try {
      for (int j = 0; j < dataVector.size(); j++) {
        //行过滤
        if (this.mRowFilter != null &&
            !this.mRowFilter.isAcceptable( (String[]) dataVector.get(j),
                                          this.mColIds.size())) {
          continue;
        }
        int row = mStartRow + visiableRowCount + rowIndex;
        for (int i = 0; i < this.mColIds.size(); i++) {
          col = mStartCol + i;
          //最后一列与上一个查询右对齐
          if (i == this.mColIds.size() - 1
              && this.isIsMultiQueryArea()
              && this.maxColOfAllQueryArea > this.mColCount) {
            //合并
            mBook.setSelection(row, col, row, this.maxColOfAllQueryArea - 1);
            CellFormat cf = mBook.getCellFormat();
            cf.setMergeCells(true);
            mBook.setCellFormat(cf);
          }
          mBook.setRowHidden(row, true);
          xmlColumn = (XmlColumn) TableManager.getElementById(
              mValueCols, (String)this.mColIds.get(i));
          type = xmlColumn.getDatatype();
          showmask = xmlColumn.getShowMask();
          sValue = ( (String[]) dataVector.get(j))[i];
          if (sValue != null) {
            sValue = sValue.trim();
          }
          // 如果是分级编号后面的queryDataSet.getStruCount()列，需要缩进,前面加空格
          for (int m = 0; m < queryDataSet.getStruCount(); m++) {
            if (i == queryDataSet.getStruCount() + m) {
              String temp = "";
              for (int k = 0; k < (mBodyLevel - 1); k++) {
                temp += "  ";
              }
              sValue = temp + sValue;
            }
          }
          if (this.mFilter == JDRView.FILTER_ALL ||
              this.mFilter == JDRView.FILTER_DATA_ONLY) {
            //数字类型；N 数值 J 金额 S 数量 W 外币 D 单价 H 汇率
            if (type.equals("N") || type.equals("J") || type.equals("S") ||
                type.equals("D") || type.equals("W") || type.equals("H") || type.equals("P")) {
              //计算数值
              double dValue = 0;
              if (sValue != null && !sValue.equals("null") &&
                  sValue.trim().length() > 0) {
                dValue = Double.parseDouble(sValue);
              }
              //绘制数值
              mBook.setNumber(row, col, dValue);
            }
            else {
              //2006-11-07 Yrh 如果设置了显示掩码 1:A;2:B;
              //Debug.PrintlnMessageToSystem(showmask+"/"+sValue);
              if (!"".equals(showmask) && sValue != null && !"".equals(sValue)) {
                sValue = getMaskedValue(showmask, sValue);
              }
              //2006-11-07 END
              mBook.setText(row, col, replaceNextLineTag(sValue));
            }
          }
          //setQueryBodyCellFormat(row, col);
        }

        //数据分级且非过滤状态时,递规绘制下级数据
        if (queryDataSet.isClassed() && this.mRowFilter == null) {
          //结点序号
          int index = Integer.parseInt( ( (String[]) dataVector.get(j))[this.
                                       mColIds.size()]);
          //获取该结点的直接下级结点集合
          childVector = queryDataSet.getChildOfData(index);
          if (childVector != null && childVector.size() > 0) {
            //记住父行号
            parentRowNumStr += row + ",";
            int crtRow = row + 1;
            int ChildLength = queryDataSet.getAllChildCount(index);
            int r1 = crtRow;
            int r2 = crtRow + ChildLength - 1;
            this.mBook.getBook().getSheet(0).setRowOutlineLevel(r1, r2,
                mBodyLevel, false);
            mBodyLevel++;
            drawQueryBodyData(crtRow, childVector);
            mBodyLevel--;
            rowIndex += ChildLength;
          }
        }
        visiableRowCount++;
      }
      mModel.setMaxRow(mStartRow + rowIndex + visiableRowCount - 1);
      //计算下一次的起始位置
      mRowIndex = mBook.getLastRow() + 1;
//      drawQueryHeader();
//      drawQueryHeader();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String getMaskedValue(String showmask, String sValue) {
    String maskValue = "";
    //枚举类型 1:A;2:B;3:C
    int start = showmask.indexOf(sValue + ":");
    if (start >= 0) {
      showmask = showmask.substring(start);
      int end = showmask.indexOf(";");
      if (end > -1) {
        return showmask.substring(sValue.length() + 1, end);
      }
      else {
        return showmask.substring(sValue.length() + 1);
      }

    }
    //非枚举类型 ####年##月##日 ##时##分##秒,譬如 20060816210912 转换为 “2006年08月16日 21时09分12秒”
    if (showmask.startsWith("#")) {
      String s;
      start = 0;
      int count = 0;
      for (int i = 0; i < showmask.length(); i++) {
        //逐个取出字符
        s = showmask.substring(i, i + 1);
        if (s.equals("#")) {
          count++;
        }
        else {
          if (start < sValue.length()) {
            if (start + count <= sValue.length()) {
              maskValue += sValue.substring(start, start + count) + s;
            }
            else {
              maskValue += sValue.substring(start);
            }
          }
          start += count;
          count = 0;
        }
      }
      //最后一段
      if (start < sValue.length()) {
        //fengbo 2009-1-13 ,解决数据长度不足问题
		//maskValue += sValue.substring(start, start + count);
        maskValue += sValue.substring(start);
      }
    }
    if (maskValue.equals("")) {
      maskValue = sValue;
    }
    return maskValue;
  }

  /**
   * 设置单元格格式
   * @param row
   * @param col
   * @throws java.lang.Exception
   */
  private void setQueryBodyCellFormat(int row, int col) throws Exception {
    if (this.mFilter == JDRView.FILTER_ALL ||
        this.mFilter == JDRView.FILTER_FORMAT_ONLY) {
      mBook.setSelection(row, col, row, col);
      CellFormat cf = mBook.getCellFormat();
      cf.setBottomBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setBottomBorder(mFormatInfo.mBodyInnerBorder);
      cf.setLeftBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setLeftBorder(mFormatInfo.mBodyInnerBorder);
      cf.setRightBorderColor(mFormatInfo.BORDERCOLOR);
      cf.setRightBorder(mFormatInfo.mBodyInnerBorder);
      cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
      mBook.setCellFormat(cf);
    }
  }

  /**
   * 绘制表尾。
   * @throws Exception
   */
  public void drawQueryFooter() throws Exception {
    for (int i = 0; i < this.mViewTails.size(); i++) {
      int row = mStartRow + mRowIndex;
      int col = 0; //mStartCol + mColIndex;

      XmlTail tail = (XmlTail) mViewTails.elementAt(i);
      Vector labels = tail.getLabels();
      XmlLabel label = null;
      String labelText = "";
      CellFormat cf = null;
      int labelCount = labels.size();
      int labelIndex = 0;
      int colInsets = mColCount / labelCount;
      if (colInsets == 0) {
        colInsets = 1;
      }
      colInsets = checkColumn(colInsets);

      if (labelCount == 1) {
        labelIndex = 0;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, replaceNextLineTag(labelText));
        }
//        mBook.setSelection(row, col, row, mColCount - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);

        this.setQueryFooterFormat(row, col, tail);
      }
      else if (labelCount == 2) {

//        mBook.setSelection(row, col, row, colInsets - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);
//
//        mBook.setSelection(row, colInsets, row, mColCount - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);

        labelIndex = 0;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, replaceNextLineTag(labelText));
        }
        //this.setQueryTitleFormat(row, col, tail, labelIndex);
        this.setQueryFooterFormat(row, col, tail);
        labelIndex = 1;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + colInsets, replaceNextLineTag(labelText));
        }
        //this.setQueryTitleFormat(row, col + colInsets, tail, labelIndex);
        this.setQueryFooterFormat(row, col + colInsets, tail);
      }
      else if (labelCount == 3) {
//        mBook.setSelection(row, col, row, colInsets - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);
//
//        mBook.setSelection(row, colInsets, row, 2 * colInsets - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);
//
//        mBook.setSelection(row, 2 * colInsets, row, mColCount - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);

        labelIndex = 0;

        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, labelText);
        }
        //this.setQueryTitleFormat(row, col, tail, labelIndex);
        this.setQueryFooterFormat(row, col, tail);
        labelIndex = 1;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + col + colInsets,
                        replaceNextLineTag(labelText));
        }
        //this.setQueryTitleFormat(row, col + colInsets, tail, labelIndex);
        this.setQueryFooterFormat(row, col + colInsets, tail);
        labelIndex = 2;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + 2 * colInsets, replaceNextLineTag(labelText));
        }
        //this.setQueryTitleFormat(row, col + 2 * colInsets, tail, labelIndex);
        this.setQueryFooterFormat(row, col + 2 * colInsets, tail);
      }
      else if (labelCount > 3) {
//        mBook.setSelection(row, col, row, colInsets - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);
//
//        mBook.setSelection(row, colInsets, row, 2 * colInsets - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);
//
//        mBook.setSelection(row, 2 * colInsets, row, mColCount - 1);
//        cf = mBook.getCellFormat();
//        cf.setMergeCells(true);
//        mBook.setCellFormat(cf);

        labelIndex = 0;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col, replaceNextLineTag(labelText));
          mBook.setText(row, col, replaceNextLineTag(labelText));
        }
        //this.setQueryTitleFormat(row, col, tail, labelIndex);
        this.setQueryFooterFormat(row, col, tail);
        labelIndex = 1;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + colInsets, replaceNextLineTag(labelText));
        }
        //this.setQueryTitleFormat(row, col + colInsets, tail, labelIndex);
        this.setQueryFooterFormat(row, col + colInsets, tail);
        labelIndex = 2;
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          mBook.setText(row, col + 2 * colInsets, replaceNextLineTag(labelText));
          this.setQueryFooterFormat(row, col + 2 * colInsets, tail);
        }
        labelIndex = 3;
        labelText = "";
        if (this.mFilter == JDRView.FILTER_ALL ||
            this.mFilter == JDRView.FILTER_DATA_ONLY) {
          label = (XmlLabel) labels.elementAt(labelIndex);
          //追加处理
          for (int j = 3; j < labelCount; j++) {
            label = (XmlLabel) labels.elementAt(j);
            labelText += "  " + label.getCaption();
          }
          mBook.setText(row, col + 3 * colInsets, replaceNextLineTag(labelText));
          this.setQueryFooterFormat(row, col + 3 * colInsets, tail);
        }
        //this.setQueryTitleFormat(row, col + 2 * colInsets, tail, labelIndex);
      }
      mRowIndex++;
    }
  }

  /**
   * 设置页脚格式：字体，字号。
   * @param title
   * @param labelIndex
   * @throws java.lang.Exception
   */
  private void setQueryFooterFormat(int row, int col, XmlTail tail) throws
      Exception {
    if (this.mFilter == JDRView.FILTER_ALL ||
        this.mFilter == JDRView.FILTER_FORMAT_ONLY) {
      mBook.setSelection(row, col, row, col);
      CellFormat cf = mBook.getCellFormat();
      //格式
      int fontSize = (int) (Integer.parseInt(tail.getFontsize().trim()) *
                            FONT_SCALE);
      String fontName = tail.getFontname();
      String fontStyle = tail.getFontstyle();
      if (fontStyle==null || fontStyle.equals("")) fontStyle = "0";
      int ifontStyle = Integer.parseInt(fontStyle);

      cf.setFontSize(fontSize);
      cf.setFontName(fontName);
      switch (ifontStyle) {
        case 1:
          cf.setFontBold(true);
          break;
        case 2:
          cf.setFontItalic(true);
          break;
        case 3:
          cf.setFontUnderline(true);
      }
      mBook.setCellFormat(cf);
    }
  }

  /**
   * 打开查询
   * @return
   */
  private Object getQuery() {
    String queryName = mCellFigure.getName();
    Object queryResult = mModel.getQuery(queryName);
    return queryResult;
  }

  /**
   * 检查是否有隐藏列
   * 2007-11-12 fengbo 增加该注释:
   * 若第一个隐藏列在第一个间隔内部,则间隔长度=间隔长度+1;
   * 这是一种模糊处理方法,某些时候(总共6列,三个副标题,第2列是隐藏列)会有问题;
   * @param column int
   * @return int
   */
  private int checkColumn(int column) {
    String vsWidth;
    int viWidth = 0;
    for (int c = 0; c < mViewCols.size(); c++) {
      XmlViewCol xmlViewCol = (XmlViewCol) mViewCols.elementAt(c);
      XmlColumn xmlColumn = null;
      if (xmlViewCol.getType().equals("col")) {
        xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols,
            xmlViewCol.getId());
      }
      else if (xmlViewCol.getType().equals("group")) {
        XmlGroup xmlGroup = (XmlGroup) TableManager.getElementById(mValueGroups,
            xmlViewCol.getId());
        Vector leafItems = JDRQueryPainterUtils.getLeafItems(mTitleMgr,
            xmlGroup);
        for (int k = 0; k < leafItems.size(); k++) {
          XmlItem leafItem = (XmlItem) leafItems.elementAt(k);
          xmlColumn = (XmlColumn) TableManager.getElementById(
              mValueCols, leafItem.getId());
        }
      }
      vsWidth = xmlColumn.getWidth();
      if (vsWidth.equals("")) {
        vsWidth = "0";
      }
      viWidth = Integer.parseInt(vsWidth);
      //2007-11-12 fengbo
//      if (viWidth <= 0 && column >= c) {
      if (viWidth <= 0 && column > c) {
        column++;
        break;
      }
    }
    return column;
  }

  public JBook getJBook() {
    return this.mBook;
  }

  /**
   * 替换回车换行标志符, Add By 2007-5-29 fengbo.
   * 下面的语句支持标题换行。
   * 注意：回车换行符"\n\r"在后台设置后，到前台会转换为" "，
   * 因此可以在后台加换行标志，然后在前台统一替换为"\n\r"。
   * @param text String
   * @return String
   */
  private String replaceNextLineTag(String text) {
    if (text != null) {
      return text = text.replaceAll(TableManager.NEW_LINE, "\n\r");
    }
    return "";
  }

  /**
   * 以下代码暂时注释，不要删除。 2007-5-29 fengbo
   */
  //  private void setRowLines(int rowIndex,String text){
//    int count=getLineCountOfText(text);
//    Integer oInteger=(Integer)rowCountMap.get(new Integer(rowIndex));
//    if(oInteger!=null){
//      if(oInteger.intValue()<count){
//        rowCountMap.put(new Integer(rowIndex),new Integer(count)) ;
//      }
//    }
//    else{
//      rowCountMap.put(new Integer(rowIndex),new Integer(count)) ;
//    }
//  }
//  private int getLineCountOfText(String text){
//    String [] array=text.split(TableManager.NEW_LINE);
//    return array.length;
//  }


//  private void setHeadRowHight(){
//    try{
//      Iterator iterator=this.rowCountMap.keySet().iterator();
//      Integer rowNum,rowHight;
//      while(iterator.hasNext()){
//        rowNum=(Integer)iterator.next();
//        rowHight=(Integer)rowCountMap.get(rowNum);
//        if(rowHight!=null){
//          mBook.setRowHeight(rowNum.intValue(),rowHight.intValue()*JDRConstants.DEFAULT_ROW_HEIGHT);
//        }
//      }
//    }catch(Exception ex){
//      ex.printStackTrace();
//    }
//  }

  /**
   * 取各列的最大字号
   * @param tableManager TableManager
   * @return int
   */
  private int getMaxFontSize(TableManager tableManager) {
    int maxFontSize = 0;
    try {
      Map idMap = new HashMap();
      String[] viewIDs = MultiLevelHeaderTreeTable.getViewIDs(tableManager);
      for (int j = 0; j < viewIDs.length; j++) {
        idMap.put(viewIDs[j], viewIDs[j]);
      }

      int tempFontSize = 0;
      String tempID = null;
      XmlColumn col = null;

      Vector columns = tableManager.getColumns();
      for (int i = 0; i < columns.size(); i++) {
        col = (XmlColumn) columns.get(i);
        tempID = col.getId();
        if (idMap.get(tempID) != null) {
          tempFontSize = Integer.parseInt(col.getFontsize().trim());
          if (tempFontSize > maxFontSize) {
            maxFontSize = tempFontSize;
          }
        }
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    //异常处理
    if (maxFontSize == 0) {
      maxFontSize = 8;
    }
    return maxFontSize;
  }

  public void setIsMultiQueryArea(boolean isMultiQueryArea) {
    this.isMultiQueryArea = isMultiQueryArea;
  }

  public void setPrintHeader(boolean printHeader) {
    this.printHeader = printHeader;
  }

  public boolean isIsMultiQueryArea() {
    return isMultiQueryArea;
  }

  public boolean isPrintHeader() {
    return printHeader;
  }

  public int getMaxColCount() {
    return this.maxColOfAllQueryArea;
  }
}
