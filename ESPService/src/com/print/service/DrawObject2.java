package com.print.service;

import com.report.table.jxml.*;
import java.util.*;
import org.jdom.*;

import com.efounder.eai.data.JParamObject;
import com.efounder.pub.util.StringFunction;
import com.f1j.swing.*;
import com.print.*;
import com.print.service.PrintDataObject.ResultSetStatus;
import com.f1j.ss.*;

import jdatareport.dof.classes.report.paint.JDRQueryPainter;
import jdatareport.dof.classes.report.paint.JDRQueryPainterUtils;
import jdatareport.dof.classes.report.util.JDRQueryFormatInfo;
import jframework.foundation.classes.JActiveDComDM;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DrawObject2 {
  static ResourceBundle res = ResourceBundle.getBundle(
      "com.print.service.Language");
  protected int HPageCount = 0;
  protected int VPageCount = 0;
  protected JBook book = null;
  protected PrintFormatObject printFormatObject = null;
  protected PrintDataObject printDataObject = null;
  protected ResultSetStatus resultSetStatus = null;
  protected Object printObject = null;
  protected boolean isSetVPageBreak = false;
  protected boolean startZy = false;
  private int miJedecn = 2, miSldecn = 3, miDjdecn = 2, miWbdecn = 4,
      miHldecn = 4; //数值精度
  protected boolean Context = true;
  String[] lastnode = null;
  JParamObject PO = null;

  public int getVPageCount() {
    return VPageCount;
  }

  public int getHPageCount() {
    return HPageCount;
  }

  public DrawObject2() {
    PO = JParamObject.Create();
    miJedecn = Integer.parseInt(PO.GetValueByEnvName("ZW_JEDECN", "2"));
    miSldecn = Integer.parseInt(PO.GetValueByEnvName("ZW_SLDECN", "3"));
    miDjdecn = Integer.parseInt(PO.GetValueByEnvName("ZW_DJDECN", "2"));
    miWbdecn = Integer.parseInt(PO.GetValueByEnvName("ZW_WBDECN", "4"));
    miHldecn = Integer.parseInt(PO.GetValueByEnvName("ZW_HLDECN", "4"));
  }

  public static Object printObject(JBook book,
                                   PrintFormatObject printFormatObject,
                                   PrintDataObject printDataObject,
                                   Object printObject, boolean context) {
    DrawObject2 drawObject = new DrawObject2();
    drawObject.book = book;
    drawObject.printFormatObject = printFormatObject;
    drawObject.printDataObject = printDataObject;
    drawObject.printObject = printObject;
    drawObject.Context = context;
    try {
      printFormatObject.getPrintObject().beginPrintProgress();
      drawObject.printBook();
    }
    finally {
      printFormatObject.getPrintObject().endPrintProgress();
    }
    return drawObject;
  }

  /**
   * 帐页打印方法
   * ZhangES 2011-9-1
   */
  public static Object printObject(JBook printBook,
          						   PrintFormatObject printFormatObject,
          						   PrintDataObject printDataObject,
          						   Object printObject, boolean context,
          						   boolean startZy) {
	  DrawObject2 drawObject = new DrawObject2();
	  drawObject.book = printBook;
	  drawObject.printFormatObject = printFormatObject;
	  drawObject.printDataObject = printDataObject;
	  drawObject.printObject = printObject;
	  drawObject.Context = context;
	  drawObject.startZy = startZy;
	  try {
		  printFormatObject.getPrintObject().beginPrintProgress();
		  drawObject.printZyBook();
	  }
	  finally {
		  printFormatObject.getPrintObject().endPrintProgress();
	  }
	  return drawObject;
  }
  
  protected void printBook() {

	// 取出所有页面设置信息
	ViewDefine[] VDS = printFormatObject.getViewDefines();
	HPageCount = VDS.length;
	// 设置列宽
	setAutoColWidth(0, VDS);
	// 取出开始页号
	int PageNo = printFormatObject.getStartPageno();
	int startRow = 0;
	int startCol = 0;
	int PageCount = 0;

	resultSetStatus = printDataObject.next();
	//计算总页数,用于页脚的总页数打印
	VPageCount = (int) Math.ceil( (double) resultSetStatus.getSize() /
	                               printFormatObject.getBodyRows());
	if (VPageCount == 0) {
		VPageCount = 1;
	}
	//2009-02-25 YRH 由于打印时会加入承前页、过次页，因此造成总页数不准确，解决之
	if (VPageCount > 1 && Context){
//		VPageCount = (int) Math.ceil( (double) (resultSetStatus.getSize() +
//	                                            getContextPage(VPageCount)) /
//	                                   printFormatObject.getBodyRows());
		VPageCount = (int) Math.ceil( (double) resultSetStatus.getSize() /
	                                   (printFormatObject.getBodyRows()-2));
	     //计数最后一页的余行
	     //第一页只加“过次页”一行
	     int rowCount = resultSetStatus.getSize() - printFormatObject.getBodyRows() + 1;
	     int num = rowCount % (printFormatObject.getBodyRows() -2);
	     if (printFormatObject.getBodyRows() - num <= 2) {
	       VPageCount--;
	     }
	}
	int setStatus = resultSetStatus.getStatus();
	int pageStatus = 0;
	while (setStatus != ResultSetStatus.RESULTSETS_EOF) {
		// 绘制一个横向页的数据，返回页状态
		pageStatus = drawOneHPagesData(startRow, startCol, VDS, PageNo);
		if (setStatus == ResultSetStatus.RESULTSETS_BOF || // 如果第一次
	        pageStatus == PrintFormatObject.PAGESTATUS_FORM_FEED) { // 如果某一页需要换页了
			// 绘制一个横向页
	        drawLandscapePagesFormat(startRow, startCol, PageNo, VDS);
	        try {
	          book.addRowPageBreak(startRow + printFormatObject.getCurrentPageRows());
	        }
	        catch (Exception e) {
	          e.printStackTrace();
	        }
		}
		setStatus = resultSetStatus.getStatus();
		startRow += printFormatObject.getCurrentPageRows();
		PageNo++;
		PageCount++;
	}
	VPageCount = PageCount;
	setPrintArea(PageCount); //设置打印区域
  }
  
  /**
   * 帐页打印方法
   * ZhangES 2011-9-1
   */
  protected void printZyBook() {

    // 取出所有页面设置信息
    ViewDefine[] VDS = printFormatObject.getViewDefines();
    HPageCount = VDS.length;
    // 设置列宽
    setAutoColWidth(0, VDS);
    // 取出开始页号
    int PageNo = printFormatObject.getStartPageno();
    int curPageNo = printFormatObject.getCurPageNo();
    int startPageNo = ((CustomPrintObject)printObject).getSaterPageNO();
    int startRow = 0;
    int drawRow = 0;
    int startCol = 0;
    int PageCount = 0;
    int curPageCount = 0;
    
    //是否指定打印页,0：不指定；1：指定
    String designatedPage = "0";

    try{
    	designatedPage = ((CustomPrintObject)printFormatObject.printObject).DESIGNATEDPAGE;
    }catch(Exception ce){
    	ce.printStackTrace();
    	designatedPage = "0";
    }
    resultSetStatus = printDataObject.next();
    
    //计算总页数,用于页脚的总页数打印
    VPageCount = (int) Math.ceil( (double) resultSetStatus.getSize() /
                                 printFormatObject.getBodyRows());
    if (VPageCount == 0) {
    	VPageCount = 1;
	}
	//2009-02-25 YRH 由于打印时会加入承前页、过次页，因此造成总页数不准确，解决之
	if (VPageCount > 1 && Context){
	   VPageCount = (int) Math.ceil( (double) resultSetStatus.getSize() /
                                     (printFormatObject.getBodyRows()-2));
       //计数最后一页的余行
       //第一页只加“过次页”一行
       int rowCount = resultSetStatus.getSize() - printFormatObject.getBodyRows() + 1;
       int num = rowCount % (printFormatObject.getBodyRows() -2);
       if (printFormatObject.getBodyRows() - num <= 2) {
         VPageCount--;
       }
	}
    //2011-08-09 ZhangES 不指定页打印，处理方式和以前一样
    if(designatedPage.equals("0")){
    	drawRow = (curPageNo - 1)  * printFormatObject.getCurrentPageRows();  
        
        int setStatus = resultSetStatus.getStatus();
        int pageStatus = 0;
    	while (setStatus != ResultSetStatus.RESULTSETS_EOF) {
    		// 绘制一个横向页的数据，返回页状态
    		pageStatus = drawOneHPagesData(drawRow, startCol, VDS, PageNo);
    		if (setStatus == ResultSetStatus.RESULTSETS_BOF || // 如果第一次
    			pageStatus == PrintFormatObject.PAGESTATUS_FORM_FEED) { // 如果某一页需要换页了
    			// 绘制一个横向页
    	        drawLandscapePagesFormat(drawRow, startCol, PageNo, VDS);
    	        try {
    	          book.addRowPageBreak(startRow + printFormatObject.getCurrentPageRows());
    	        }
    	        catch (Exception e) {
    	          e.printStackTrace();
    	        }
    		}
    		setStatus = resultSetStatus.getStatus();
    		startRow += printFormatObject.getCurrentPageRows();
    		drawRow += printFormatObject.getCurrentPageRows();
    		PageNo++;
    		PageCount++;
    	}
    	VPageCount = PageCount;
        setPrintArea(PageCount); //设置打印区域
        printFormatObject.setCurPageNo(curPageNo);
    }
    //2011-08-09 ZhangES 指定页打印，处理方式有所区别
    else{
    	//计算当前查询总页数    	
    	curPageCount = VPageCount + curPageNo - 1;
    	if(startPageNo <= curPageCount && startPageNo >= curPageNo){
    		//减去“承前页”和“过次页”两行
    		startRow = (startPageNo - curPageNo) * (printFormatObject.getBodyRows() - 2);
    		
    		if(startRow != 0){
    			if(startPageNo - curPageNo > 0) {
    				startRow += 1;
    				resultSetStatus.setStatus(ResultSetStatus.RESULTSET_GEN);
    			}
    			
        		for(int i = 1; i <= resultSetStatus.getSize(); i++){
            		if(i < startRow + 1) {    			
            			lastnode = (String[]) printDataObject.getObject();
            			resultSetStatus = printDataObject.next();
            		}
            	}
    		}    		
        	
            //计算总页数,用于页脚的总页数打印
            if (VPageCount == 0) {
              VPageCount = 1;
            }

            int setStatus = resultSetStatus.getStatus();
            int pageStatus = 0;
        	// 绘制一个横向页的数据，返回页状态
        	
        	pageStatus = drawOneHPagesData(0, startCol, VDS, startPageNo);
        	
        	if (setStatus == ResultSetStatus.RESULTSETS_BOF || // 如果第一次
        		pageStatus == PrintFormatObject.PAGESTATUS_FORM_FEED) { // 如果某一页需要换页了
    			// 绘制一个横向页
    	        drawLandscapePagesFormat(0, startCol, startPageNo, VDS);
    	        try {
    	          book.addRowPageBreak(startRow + printFormatObject.getCurrentPageRows());
    	        }
    	        catch (Exception e) {
    	          e.printStackTrace();
    	        }
    		}
    		PageCount++;
    	    setPrintArea(VPageCount); //设置打印区域
    	    printFormatObject.setCurPageNo(curPageNo);
    	}
    }
  }

  // 设置打印区域，必须需要设置，不然页数会打不全
  private void setPrintArea(int HPageCount) {
    //2008-4-25 fengbo
    try {
      String printArea =null;
      //1 取本地的打印区域设置
      try{
        CustomPrintObject customPrintObject = (CustomPrintObject)this.
            printObject;
        printArea = JActiveDComDM.LocalRegistry.Get(customPrintObject.QO.Param  + "PrintArea");
      }
      catch(Exception ex){
        ex.printStackTrace();
      }

      //2 当选择列区域时譬如A:D，修正打印区域为A1:Dn,其中n为实际行数；如果不修正，行数是10亿
      if (printArea!=null && !"".equals(printArea) ) {
          int index=printArea.indexOf("!") ;
          //截掉Sheet!
          if(index>-1){
            printArea=printArea.substring(index+1) ;
          }
          index=printArea.indexOf(":") ;
          String startCell=null;
          String endCell=null;
          if(index>-1){
            startCell=printArea.substring(0,index);
            endCell=printArea.substring(index+1);
            //仅指定了开始列
            if(isAlphs(startCell)){
              startCell=startCell+"1";
            }
            //仅指定了结束列
            if(isAlphs(endCell)){
              endCell=endCell+(book.getLastRow()+1);
            }
            printArea=   startCell+ ":"  + endCell;
            book.setPrintArea(printArea);
          }
      }
      else {
        int SRow = 0, SCol = 0, ECol = 0;
        int ERow = HPageCount * printFormatObject.getPageRows();
        //总行数中要减去由于最后一页所造成的误差
        if (printFormatObject.getLastBodyRows() > 0) {
          ERow = ERow - printFormatObject.getBodyRows() +
              printFormatObject.getLastBodyRows();
        }
        ViewDefine[] VDS = printFormatObject.getViewDefines();
        for (int i = 0; i < VDS.length; i++) {
          ECol += VDS[i].getViewColList().size();
        }
        book.setSelection(SRow, SCol, ERow - 1, ECol - 1);
        book.setPrintArea();
        book.setSelection(0, 0, 0, 0);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   * 计算增加承前、过次页后所增加的行数，考虑页数变化
   * @param Page int 正常的页数
   * @return int
   */
  private int getContextPage(int Page){
    int ContextRows=0,pages=0,i=0;
     ContextRows = Page * 2 ;
     pages = (int)Math.ceil(ContextRows/printFormatObject.getBodyRows());
    if (pages > 1){
      i = getContextPage(pages);
    }
    ContextRows = ContextRows + i;
    return ContextRows;
  }

  private  boolean isAlphs(String s){
    boolean tag=true;
    char [] array=s.toCharArray();
    for(int i=0;i<array.length ;i++){
      if(array[i]>='0' && array[i]<='9'){
        tag=false;
        break;
      }
    }
    return tag;
  }

  protected int drawOneHPagesData(int startRow, int startCol,
                                  ViewDefine[] viewLayouts, int HPageno) {
    int pageStatus = PrintFormatObject.PAGESTATUS_FORM_FEED;
    ViewDefine VD = null;
    Vector colList = null;
    String[] node = null;
    int datarows = 0;
    int status = resultSetStatus.getStatus();
    if (status == ResultSetStatus.RESULTSET_CHN ||
        status == ResultSetStatus.RESULTSETS_BOF) {
      // 设置此数据集的开始页号
      printDataObject.getStubObject(resultSetStatus.getResultSetIndex()).
          setObject("startPageNo", String.valueOf(HPageno));
    }
    boolean isNeedNext = true;
    // 获取表体行数
    int BodyRows = printFormatObject.getBodyRows();
    startRow = startRow + printFormatObject.getTitleRows() +
        printFormatObject.getHeadRows();
    int desCol = printFormatObject.getDesColIndex();
    // 填制所有的表体行
    for (int Row = 0; Row < BodyRows; Row++) {
      int Col = startCol;
      // 这行数据在横向的多个页上绘制
      for (int page = 0; page < viewLayouts.length; page++) {
        // 取出此页的定义
        VD = viewLayouts[page];
        colList = VD.getViewColList();
        isNeedNext = true;
        if ( (Row == 0) && (desCol > -1)) { // 这里可能需要处理 "承前页"
          if (resultSetStatus.getStatus() != ResultSetStatus.RESULTSETS_BOF &&
              resultSetStatus.getStatus() != ResultSetStatus.RESULTSET_CHN &&
              Context) {
            //需要设置承前页
            drawOnePageString(startRow, Row, Col, desCol,
                              printFormatObject.getFrontPageText(), colList,
                              lastnode);
            isNeedNext = false;
            Col += colList.size();
            continue;
          }
        }
        else if ( ( (Row + 1) == BodyRows) && (desCol > -1)) { // 这里可能需要处理“过次页”
          if (resultSetStatus.getStatus() != ResultSetStatus.RESULTSET_EOF &&
              resultSetStatus.getStatus() != ResultSetStatus.RESULTSET_CHN &&
              Context) {
            //需要设置过次页
            drawOnePageString(startRow, Row, Col, desCol,
                              printFormatObject.getAfterPageText(), colList,
                              lastnode);
            isNeedNext = false;
            Col += colList.size();
            continue;
          }
        }
        if (isNeedNext && page == 0) {
          // 获取一行数据
          node = (String[]) printDataObject.getObject();
        }
        // 填一页的一行数据
        printFormatObject.getPrintObject().watchPrintProgress(1, page + 1,
            HPageno);
        drawOnePageData(startRow, Row, Col, VD, colList, node);
        lastnode = node;
        Col += colList.size();
        datarows++;
      }
      //
      if (isNeedNext) {
        resultSetStatus = printDataObject.next();
      }
      // 获取数据集的状心
      status = resultSetStatus.getStatus();
      // 如果是需要换数据集了
      if (status == ResultSetStatus.RESULTSET_CHN) {
        break; // 换,
      }
      if (status == ResultSetStatus.RESULTSETS_EOF) {
        break;
      }
    }
    //取当前状态
    int setStatus = resultSetStatus.getStatus();
    //最后一页并且不补空行时，设置一下当前的实际行数
    if (!printFormatObject.getFillPage() &&
        setStatus == ResultSetStatus.RESULTSETS_EOF) {
      //如果不是第一页，要加上“过次页”所占用的那一行
      if (HPageno > 0) {
        datarows++;
      }
      printFormatObject.setLastBodyRows(datarows);
    }
    return pageStatus;
  }

  /** 承前页、过次页
   * modify on 2006-04-05 By YRH add
   * @param startRow int
   * @param row int
   * @param startCol int
   * @param desCol int
   * @param text String
   * @param colList Vector
   * @param node QueryNode
   */
  protected void drawOnePageString(int startRow, int row, int startCol,
                                   int desCol, String text, Vector colList,
                                   String[] node) {
    ColumnDefine CD = null;
    int ColIndex = -1;
    try {
      book.setText(startRow + row, startCol + desCol, text);
      String sValue = "";
      //从最后一列向前，如果遇到第一个文本列，放完内容后就反回
      for (int index = colList.size() - 1; index >= 0; index--) {
        CD = (ColumnDefine) colList.get(index);
        String type = printFormatObject.getPrintObject().getColType(CD); //CD.getColType();
        ColIndex = printFormatObject.getColIndexByID(CD);
        if (ColIndex == -1) {
          continue;
        }

//        if (node==null){//承前页
//          sValue = book.getText(startRow + row - 1, ColIndex);
//        }else{//过次页
        sValue = node[index];
//        }

        if ("N".equals(type) || "J".equals(type) || "S".equals(type) ||
            "D".equals(type) || "W".equals(type) || "H".equals(type)) {
          //计算数值
          double dValue = 0;
          if (sValue != null && sValue.trim().length() > 0) {
            dValue = Double.parseDouble(sValue);
          }
          //绘制数值
          if (dValue == 0) {
            book.setText(startRow + row, startCol + index, "");
          }
          else {
            book.setNumber(startRow + row, startCol + index, dValue);
          }
        }
        else { //找到第一个文本列,到此为止
          //如果循环超过三次,说明这不是账页,把“承前页”,“过次页”放在这里吧，特殊情况殊殊处理
          if (colList.size() > index + 4) {
            book.setText(startRow + row, startCol + index, text);
          }
          else { //这一列是余额方向
            book.setText(startRow + row, startCol + index, sValue);
          }
          break;
        }
      }
//      if (node==null){//承前页
//        sDqye = book.getText(startRow + row - 1, book.getLastCol());
//      }else{//过次页
//        sDqye = node.getDatas()[node.getDatas().length].toString();
//      }
//      dDqye = Double.parseDouble(sDqye);
//      if (sDqye != null && sDqye.trim().length() > 0) {
//        dDqye = Double.parseDouble(sDqye);
//      }
//      //绘制数值
//      if (dDqye == 0) {
//        book.setText(startRow+row,  book.getLastCol(), "");
//      }
//      else {
//        book.setNumber(startRow+row,  book.getLastCol(), dDqye);
//      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void drawOnePageData(int startRow, int row, int startCol,
                                 ViewDefine VD, Vector colList, String[] node) {
    if (node == null) {
      return;
    }
    ColumnDefine CD = null;
    int ColIndex = -1;
    int Row = startRow + row;
    int _PageNo = 0;
    if (printFormatObject.fixCols + printFormatObject.valCols != 0) {
      _PageNo = startCol /
          (printFormatObject.fixCols + printFormatObject.valCols);
    }
    for (int index = 0; index < colList.size(); index++) {
      CD = (ColumnDefine) colList.get(index);
      String type = printFormatObject.getPrintObject().getColType(CD); //CD.getColType();
      //固定列数据取前fix列
      if (index < printFormatObject.fixCols) {
        ColIndex = index;
      }
      else {
        ColIndex = _PageNo * printFormatObject.valCols + index;
      }
      String sValue = node[ColIndex];
      //modi by fengbo 2007-10-30 处理显示掩码，和查询结果保持一致
      if (CD.getShowmask() != null) {
        sValue = JDRQueryPainter.getMaskedValue(CD.getShowmask(), sValue);
      }
      try {
        if ("N".equals(type) || "J".equals(type) || "S".equals(type) ||
            "D".equals(type) || "W".equals(type) || "H".equals(type)) {
          //计算数值
          double dValue = 0;
          if (sValue != null && sValue.trim().length() > 0) {
            dValue = Double.parseDouble(sValue);
          }
          //绘制数值
          if (dValue == 0) {
            book.setText(Row, startCol + index, "");
          }
          else {
            book.setNumber(Row, startCol + index, dValue);
          }
        }
        else {
          book.setText(Row, startCol + index, sValue);
        }
      }
      catch (Exception e) {}

    }
  }

//  private void drawOneRowData(int row,QueryNode node) throws Exception {
//    for(int i=0;i<mColIds.size();i++) {
//      int col = i;
//      XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(mValueCols, (String)this.mColIds.get(i));
//      String type = xmlColumn.getDatatype();
//      String sValue = (String) node.getDatas()[i].toString();
//      //数字类型
//      if (type.equals("N")) {
//          //计算数值
//          double dValue = 0;
//          if (sValue != null && sValue.trim().length() > 0) {
//              dValue = Double.parseDouble(sValue);
//          }
//          //绘制数值
//          if(dValue==0){
//              mBook.setText(row, col, "");
//          }else{
//              mBook.setNumber(row, col, dValue);
//          }
//      }
//      else {
//          mBook.setText(row, col, sValue);
//      }
//    }
//  }

  /**
   * 绘制横向上的所有页
   * @param startRow int
   * @param VPageno int
   * @param viewLayouts ViewDefine[]
   */
  protected void drawLandscapePagesFormat(int startRow, int startCol,
                                          int VPageno,
                                          ViewDefine[] viewLayouts) {
    // 获取横向上有几页
    int HPages = viewLayouts.length;
    int Col = startCol;
    int PageCols;
    int HPageNo = 0;
    for (int HPageIndex = 0; HPageIndex < HPages; HPageIndex++) {
      if (HPageIndex == 0) {
        // 0.设置行高
        setRowHeightForPage(startRow);
      }
      try {
        setAutoColWidth(Col, viewLayouts[HPageIndex]);
      }
      catch (Exception e) {}
      if (HPages > 1) {
        HPageNo = HPageIndex + 1;
      }
      printFormatObject.getPrintObject().watchPrintProgress(0, VPageno,
          HPageNo);
      drawOnePageFormat(startRow, Col, VPageno, HPageNo,
                        viewLayouts[HPageIndex]);
      PageCols = viewLayouts[HPageIndex].getColCount();
      Col = startCol + Col + PageCols;
      if (!isSetVPageBreak) {
        // 设置纵向分页符
        try {
          book.addColPageBreak(Col);
        }
        catch (Exception e) {}
      }
    }
    isSetVPageBreak = true;
  }

  protected void setAutoColWidth(int startCol, ViewDefine[] viewLayouts) {
    int HPages = viewLayouts.length;
    int Col = startCol;
    int PageCols;
    for (int HPageIndex = 0; HPageIndex < HPages; HPageIndex++) {
      try {
        setAutoColWidth(Col, viewLayouts[HPageIndex]);
      }
      catch (Exception e) {}
      PageCols = viewLayouts[HPageIndex].getColCount();
      Col = startCol + Col + PageCols;
    }
  }

  /**
   * 绘制一页
   * @param startRow int
   * @param startCol int
   * @param VPageno int
   * @param HPageno int
   * @param viewLayout ViewDefine
   */
  protected void drawOnePageFormat(int startRow, int startCol, int VPageno,
                                   int HPageno, ViewDefine viewLayout) {
    // 1.绘制标题
    drawBookTitle(startRow, startCol, viewLayout);
    // 2.绘制 Head
    drawBookHead(startRow, startCol, viewLayout);
    // 3.绘制 Body
    drawBookBody(startRow, startCol, viewLayout);
    // 4.绘制 Tail
    drawQueryFooter(startRow, startCol, viewLayout);
    drawBookTail(startRow, startCol, VPageno, HPageno, viewLayout);
    // 5.设置分页符
//    setPageBreak();
  }

  protected void drawBookHead(int startRow, int startCol,
                              ViewDefine viewLayout) {
    int HeadRows = printFormatObject.getHeadRows();
    int TitleRows = printFormatObject.getTitleRows();
    int Row = startRow + TitleRows;
    int Col = startCol;
    int groupColCount = 0;
    ColumnDefine CD = null;
    int ERow = startRow + TitleRows + HeadRows;
    Vector mViewCols = viewLayout.getViewLayout();
    for (int i = 0; i < mViewCols.size(); i++) {
      CD = (ColumnDefine) mViewCols.get(i);
      groupColCount = drawHead(CD, Row, Col, HeadRows, ERow);
      Col += groupColCount;
    }
    int ColCount = viewLayout.buildViewColList().size();
    setQueryTHOutterFormat(startRow + TitleRows, startCol,
                           startRow + TitleRows + HeadRows - 1,
                           startCol + ColCount - 1);
  }

  private void setQueryTHOutterFormat(int srow, int scol, int erow, int ecol) {
    try {
      book.setSelection(srow, scol, erow, ecol);
      CellFormat cf = book.getCellFormat();
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
      int fontSize = printFormatObject.getPrintObject().getHeadFontSize() *
          15;
      cf.setFontSize(fontSize);
      cf.setFontName(printFormatObject.getPrintObject().getHeadFontName());
      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
      cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
      book.setCellFormat(cf);
    }
    catch (Exception e) {

    }
  }

  protected int drawHead(ColumnDefine CD, int Row, int Col, int HeadRows,
                         int ERow) {
    int groupColCount = 0;
    Vector leafCol = new Vector();
    int srow = Row;
    int scol = Col;
    int erow = 0;
    int ecol = 0;
    ColumnDefine BCD = null;
    // 如果是组
    if (CD instanceof GroupDefine) {
      GroupDefine GD = (GroupDefine) CD;
      ViewDefine.buildViewCol(leafCol, CD);
      groupColCount = leafCol.size();
      erow = srow;
      ecol = scol + groupColCount - 1;
      setHead(srow, scol, erow, ecol, CD);
      Row++;
      for (int i = 0; i < GD.getSize(); i++) {
        BCD = GD.get(i);
        int LCol = drawHead(BCD, Row, Col, HeadRows, ERow);
        Col += LCol;
      }
    }
    else if (CD instanceof ColumnDefine) {
      ecol = scol;
      erow = ERow - 1;
      setHead(srow, scol, erow, ecol, CD);
      groupColCount = 1;
    }
    return groupColCount;
  }

  protected void setHead(int srow, int scol, int erow, int ecol,
                         ColumnDefine cd) {
    try {
      book.setSelection(srow, scol, erow, ecol);
      CellFormat cf = book.getCellFormat();
      cf.setMergeCells(true);
      book.setCellFormat(cf);
      String Text = cd.getColCaption();
      Text = Text.replaceAll(TableManager.NEW_LINE, "\n\r");
      book.setText(srow, scol, Text);
    }
    catch (Exception e) {

    }
  }

  protected void drawBookBody(int startRow, int startCol,
                              ViewDefine viewLayout) {
    try {
      setQueryBodyFormat(startRow, startCol, viewLayout);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setQueryBodyFormat(int startRow, int mStartCol,
                                  ViewDefine viewLayout) throws Exception {
    /**
     * 负数是否显示为红字标志，可以在数据中心中设置
     * 默认为显示红字
     * add by hufeng 2005.11.17
     */
    PO = JParamObject.Create();
    String showRed = PO.GetValueByEnvName("CX_SHOWRED", "");
    if (showRed == null || showRed.trim().equals("")) {
      showRed = "1";
    }
    int PageStartRow = startRow;
    int bodyStartRow = printFormatObject.getTitleRows() +
        printFormatObject.getHeadRows();
    // 减去尾行
    int bodyEndRow = printFormatObject.getCurrentPageRows() -
        printFormatObject.getTailRows();
    Vector mColIds = viewLayout.buildViewColList();
    int mColCount = mColIds.size();
    for (int i = 0; i < mColIds.size(); i++) {
      ColumnDefine xmlColumn = (ColumnDefine) mColIds.get(i);

//              int fontSize = (int) (Integer.parseInt(xmlColumn.getFontSize()) * DrawObject.FONT_SCALE);
      int fontSize = printFormatObject.getPrintObject().getColFontSize(
          xmlColumn) * 15;
      String fontName = printFormatObject.getPrintObject().getColFontName(
          xmlColumn);
//              fontName = JDRConstants.DEFAULT_FONT_NAME;

      String type = printFormatObject.getPrintObject().getColType(xmlColumn); //xmlColumn.getColType();
      String format = printFormatObject.getPrintObject().getColFormat(
          xmlColumn); //xmlColumn.getColFormat();
//              String colWidth = xmlColumn.getColWidth();
      String align = printFormatObject.getPrintObject().getColAlign(xmlColumn);

      //N,C,D
      if (format == null) {
        format = "";
      }

      if (type.equals("N")) {
        //若未指定格式，则提供默认格式；
        if (format == null || format.trim().equals("")) {
          format = "#,##0.00";
        }

        if (format.startsWith(",")) {
          format = "#" + format;
        }
        //modi by fengbo 2007-10-30
//        if (showRed.equals("1")) {
        if (!book.isPrintNoColor() && showRed.equals("1")) {
          format += ";[Red]" + format + ";";
        }
        else {
          format += ";-" + format + ";";
        }
      }
      else if (type.equals("J")) { //金额
        format = "#,##0." + StringFunction.FillZeroFromBegin(0, miJedecn);
        if (!book.isPrintNoColor() && showRed.equals("1")) {
          format += ";[Red]" + format;
        }
      }
      else if (type.equals("S")) { //数量
        format = "#,##0." + StringFunction.FillZeroFromBegin(0, miSldecn);
        if (!book.isPrintNoColor() && showRed.equals("1")) {
          format += ";[Red]" + format;
        }
      }
      else if (type.equals("D")) { //单价
        format = "#,##0." + StringFunction.FillZeroFromBegin(0, miDjdecn);
        if (!book.isPrintNoColor() && showRed.equals("1")) {
          format += ";[Red]" + format;
        }
      }
      else if (type.equals("W")) { //外币
        format = "#,##0." + StringFunction.FillZeroFromBegin(0, miWbdecn);
        if (!book.isPrintNoColor() && showRed.equals("1")) {
          format += ";[Red]" + format;
        }
      }
      else if (type.equals("H")) { //汇率
        format = "#,##0." + StringFunction.FillZeroFromBegin(0, miHldecn);
        if (!book.isPrintNoColor() && showRed.equals("1")) {
          format += ";[Red]" + format;
        }
      }

//              if (colWidth != null && colWidth.length() > 0) {
//                  this.mBook.setColWidth(mStartCol + i, Integer.parseInt(colWidth) * JDRConstants.DEFAULT_SCALE);
//              }
//              int twips = (int) CanvasUtils.convertInchtoTwip(AccountBookStub.getRowHeight());
//              mBook.setRowHeight(mStartRow + bodyStartRow+PageStartRow,mStartRow + bodyEndRow+PageStartRow,twips,false,false);
      book.setSelection(bodyStartRow + PageStartRow, mStartCol + i,
                        bodyEndRow + PageStartRow, mStartCol + i);
      CellFormat cf = book.getCellFormat();
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
      book.setCellFormat(cf);

    } //End of 格式

//          this.mBook.setRowHeight(mStartRow, mStartRow, JDRConstants.DEFAULT_ROW_HEIGHT * 2, false, false);
//          this.mBook.setRowHeight(mStartRow + 1, mStartRow + bodyEndRow, JDRConstants.DEFAULT_ROW_HEIGHT, false, false);

    //整体格式
    this.book.setSelection(bodyStartRow + PageStartRow, mStartCol,
                           bodyEndRow + PageStartRow - 1,
                           mStartCol + mColCount - 1);
    CellFormat cf = book.getCellFormat();
    cf.setBottomBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    cf.setBottomBorder(JDRQueryFormatInfo.mBodyOuterBorder);
    cf.setLeftBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    cf.setLeftBorder(JDRQueryFormatInfo.mBodyOuterBorder);
    cf.setRightBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    cf.setRightBorder(JDRQueryFormatInfo.mBodyOuterBorder);
//    cf.setHorizontalInsideBorder(JDRQueryFormatInfo.mBodyInnerBorder);
    cf.setHorizontalInsideBorder(JDRQueryFormatInfo.mBodyOuterBorder);
    cf.setHorizontalInsideBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
//    cf.setVerticalInsideBorder(JDRQueryFormatInfo.mBodyInnerBorder);
    cf.setVerticalInsideBorder(JDRQueryFormatInfo.mBodyInnerBorder);
    cf.setVerticalInsideBorderColor(JDRQueryFormatInfo.BORDERCOLOR);
    book.setCellFormat(cf);
//          mBook.setActiveCell(bodyStartRow, 0);
//          mBook.setSelection(bodyStartRow, 0, bodyStartRow, 0);

  }

  protected void drawBookTail(int startRow, int startCol, int VPageno,
                              int HPageno, ViewDefine viewLayout) {
    try {
      String Text = "";
      CellFormat cf;
      Vector mColIds = viewLayout.getViewColList();
      int PageEndRow = startRow + printFormatObject.getCurrentPageRows();
//      Text = printFormatObject.getPrintObject().getSoftName() +
//          res.getString("String_191") +
//          printFormatObject.getPrintObject().getUnitName();
      book.setActiveCell(PageEndRow - 1, startCol);
      book.setText(PageEndRow - 1, startCol, Text);
      cf = book.getCellFormat();
      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
      cf.setFontName("宋体");
      book.setCellFormat(cf);
//      Text = "打印人：于仁海  ";
      Text = res.getString("String_193") +
          printFormatObject.getPrintObject().getPrintDate();
      String SPage = res.getString("String_194") + String.valueOf(VPageno);
      if (HPageno != 0) {
        SPage += "-" + String.valueOf(HPageno);
      }
      SPage += res.getString("String_196");
      //共几页
      //mod：zhtb 修改 去掉打印总页数
      /*
      SPage += res.getString("String_197");
      SPage += this.VPageCount;
      SPage += res.getString("String_196");
      */
      Text += SPage;
      //2008-3-25 fengbo
      int col = startCol + getLastNotZeroWidthCol(mColIds);
      book.setText(PageEndRow - 1, col, Text); //AccountBookStub.getColsPerPage(),Text);
//      book.setText(PageEndRow - 1, startCol + mColIds.size() - 1, Text); //AccountBookStub.getColsPerPage(),Text);
      book.setActiveCell(PageEndRow - 1, col);
//      book.setActiveCell(PageEndRow - 1, startCol + mColIds.size() - 1); //.getColsPerPage());
      cf = book.getCellFormat();
      cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
      cf.setFontName("宋体");
      book.setCellFormat(cf);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * add by fengbo 2008-3-35
   * 获取最后一列宽度不为零的列,防止页脚打印到列宽为零的列上看不到。
   * @param mColIds Vector
   * @return int
   */
  private int getLastNotZeroWidthCol(Vector mColIds) {
    ColumnDefine columnDefine = null;
    for (int i = mColIds.size() - 1; i >= 0; i--) {
      columnDefine = (ColumnDefine) mColIds.get(i);
      if (columnDefine != null && !"0".equals(columnDefine.ColWidth.trim())) {
        return i;
      }
    }
    return mColIds.size() - 1;
  }

  /**
   * 增加页脚，2007-9-6 fengbo。
   * @param startRow int
   * @param startCol int
   * @param viewLayout ViewDefine
   */
  private void drawQueryFooter(int startRow, int startCol,
                               ViewDefine viewLayout) {
//
    int PageStartRow = startRow + printFormatObject.getCurrentPageRows() - 2;
    int mColCount = viewLayout.getColCount() + startCol;
    int mStartRow = 0;
    int mStartCol = startCol;
    int mRowIndex = 0;
    int mColIndex = 0;
    try {
      Vector mViewTails = printFormatObject.getTableManager().getTails();
      for (int i = 0; i < mViewTails.size(); i++) {
        int row = mStartRow + mRowIndex + PageStartRow;
        int col = mStartCol + mColIndex;
        XmlTail tail = (XmlTail) mViewTails.elementAt(i);
        Vector labels = tail.getLabels();
        XmlLabel label = null;
        String labelText = "";
        CellFormat cf = null;
        int labelCount = labels.size();
        int labelIndex = 0;
        int colInsets = viewLayout.getColCount() / labelCount + startCol; //+ 1;
        if (colInsets == 0) {
          colInsets = 1;
        }

        if (labelCount == 1) {
          labelIndex = 0;
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col, labelText);
          book.setSelection(row, col, row, mColCount - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);
          setQueryFooterFormat(row, col, tail);
        }
        else if (labelCount == 2) {

          book.setSelection(row, col, row, colInsets - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          book.setSelection(row, colInsets, row, mColCount - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          labelIndex = 0;
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col, labelText);

          setQueryFooterFormat(row, col, tail);
          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, mColCount - 1, labelText);

          setQueryFooterFormat(row, mColCount - 1, tail);
        }
        else if (labelCount == 3) {
          book.setSelection(row, col, row, colInsets - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          book.setSelection(row, colInsets, row, 2 * colInsets - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          book.setSelection(row, 2 * colInsets, row, mColCount - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          labelIndex = 0;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col, labelText);

          setQueryFooterFormat(row, col, tail);
          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col + col + colInsets, labelText);

          setQueryFooterFormat(row, col + col + colInsets, tail);
          labelIndex = 2;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col + 2 * colInsets, labelText);

          setQueryFooterFormat(row, col + 2 * colInsets, tail);
        }
        else if (labelCount > 3) {

          labelIndex = 0;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col, labelText);

          setQueryFooterFormat(row, col, tail);
          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col + colInsets, labelText);

          setQueryFooterFormat(row, col + colInsets, tail);
          labelIndex = 2;
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col + 2 * colInsets, labelText);

          labelIndex = 3;
          labelText = "";
          label = (XmlLabel) labels.elementAt(labelIndex);
          //追加处理
          for (int j = 3; j < labelCount; j++) {
            label = (XmlLabel) labels.elementAt(j);
            labelText += "  " + label.getCaption();
          }
          book.setText(row, col + 3 * colInsets, labelText);

          setQueryFooterFormat(row, col + 3 * colInsets, tail);
        }
        mRowIndex++;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
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
    book.setSelection(row, col, row, col);
    CellFormat cf = book.getCellFormat();
    //格式
    int fontSize = (int) (Integer.parseInt(tail.getFontsize()) * 15);
    String fontName = tail.getFontname();

    cf.setFontSize(fontSize);
    cf.setFontName(fontName);
    book.setCellFormat(cf);
  }

  private void setAutoColWidth(int startCol, ViewDefine VD) throws Exception {
    int OldColWidthTwips = 0;
    int NumberCols = 0, HideCols = 0;
    int i = 0;
    //
    book.setColWidthUnits(book.eColWidthUnitsTwips);
    // 1.统计出现有的所有列的宽度,统计出其中的数值列列数
    Vector mColIds = VD.getViewColList();
    for (i = 0; i < mColIds.size(); i++) {
      ColumnDefine CD = (ColumnDefine) mColIds.get(i);
//        String colWidth = CD.getColWidth();
      String type = printFormatObject.getPrintObject().getColType(CD); //CD.getColType();
      if ("N".equals(type) || "J".equals(type) || "S".equals(type) ||
          "D".equals(type) || "W".equals(type) || "H".equals(type)) {
        NumberCols++;
      }
//        int ColWidth = printFormatObject.getPrintObject().getColWidth(CD)*DrawObject.DEFAULT_SCALE; //Integer.parseInt(colWidth)* DrawObject.DEFAULT_SCALE;
      int ColWidth = printFormatObject.getPrintObject().getColWidth(CD) * 15; //Integer.parseInt(colWidth)* DrawObject.DEFAULT_SCALE;
      if (ColWidth == 0) {
        HideCols++;
      }
      book.setColWidth(i + startCol, ColWidth);
      ColWidth = book.getColWidth(i + startCol);
      OldColWidthTwips += ColWidth;
    }
    // 如果数值列等0，则分配到所有的列上，除去隐藏列
    if (NumberCols == 0) {
      NumberCols = mColIds.size() - HideCols;
    }
    // 所有列的宽度，以英寸为单位 这里要 无论0.00x中的x为
    double OldColWidthInch = CanvasUtils.convertTwiptoInch( (double) (
        OldColWidthTwips));
    OldColWidthInch = Math.round(OldColWidthInch * 100.00) / 100.00;
    // 取出当前纸张的每一页的宽度
    double PageWidth = printFormatObject.getDataWidth();
    // 计算出之间的差额
    double WidthCE = Math.floor( (PageWidth - OldColWidthInch) * 100.00) /
        100.00;
    // 计算出分配差额
    double FPWidthCE = WidthCE / (NumberCols * 1.00); // - DrawObject.F1_EXCEL_CE;// 有可能为负数，
    FPWidthCE = Math.floor(FPWidthCE * 100.00) / 100.00;
    // 将英寸转换成TWips
    int iFPWidthCE = (int) CanvasUtils.convertInchtoTwip(FPWidthCE);
    // 将差额分配到数值列上
    for (i = 0; i < mColIds.size(); i++) {
      ColumnDefine CD = (ColumnDefine) mColIds.get(i);
      String colWidth = String.valueOf(book.getColWidth(startCol + i)); //CD.getColWidth();
      String type = printFormatObject.getPrintObject().getColType(CD); //CD.getColType();
      OldColWidthTwips = Integer.parseInt(colWidth); // * DrawObject.DEFAULT_SCALE;
      // 如果是调整所有列，或是为数值列，需要加上调整差额
      if (OldColWidthTwips != 0 &&
          (NumberCols == (mColIds.size() - HideCols) ||
           ("N".equals(type) || "J".equals(type) ||
            "S".equals(type) ||
            "D".equals(type) || "W".equals(type) ||
            "H".equals(type)))) {
        OldColWidthTwips += iFPWidthCE;
      }
      book.setColWidth(startCol + i, OldColWidthTwips);
    }
    OldColWidthTwips = 0;
    for (i = 0; i < mColIds.size(); i++) {
      int ColWidth = book.getColWidth(i);
      OldColWidthTwips += ColWidth;
    }
    double NewPageWidth = CanvasUtils.convertTwiptoInch(OldColWidthTwips);
  }

  protected void drawBookTitle(int startRow, int startCol,
                               ViewDefine viewLayout) {
    int PageStartRow = startRow;
    int mColCount = viewLayout.getColCount() + startCol;
    int mStartRow = 0;
    int mStartCol = startCol;
    int mRowIndex = 0;
    int mColIndex = 0;
    try {
      Vector mViewTitles = printFormatObject.getTitleList();
      for (int i = 0; i < mViewTitles.size(); i++) {
        int row = mStartRow + mRowIndex + PageStartRow;
        int col = mStartCol + mColIndex;
        XmlTitle title = (XmlTitle) mViewTitles.elementAt(i);
        Vector labels = title.getLabels();
        XmlLabel label = null;
        String labelText = "";
        CellFormat cf = null;
        int labelCount = labels.size();
        int labelIndex = 0;
        int colInsets = viewLayout.getColCount() / labelCount + startCol; //+ 1;
        if (colInsets == 0) {
          colInsets = 1;
        }
        //2008-6-5 fengbo
        colInsets = checkColumn(printFormatObject.getTableManager(),colInsets);

        if (labelCount == 1) {
          labelIndex = 0;
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col, labelText);
          book.setSelection(row, col, row, mColCount - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);
          setQueryTitleFormat(i, book, row, col, title, labelIndex);
        }
        else if (labelCount == 2) {

          book.setSelection(row, col, row, colInsets - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          book.setSelection(row, colInsets, row, mColCount - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          labelIndex = 0;
          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col, labelText);

          setQueryTitleFormat(i, book, row, col, title, labelIndex);

          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          //book.setText(row, mColCount - 1, labelText);
          book.setText(row, col + colInsets, labelText);
          setQueryTitleFormat(i, book, row, col + colInsets, title,
                              labelIndex);
          //setQueryTitleFormat(row, col + colInsets, title, labelIndex);
        }
        else if (labelCount == 3) {
          book.setSelection(row, col, row, colInsets - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          book.setSelection(row, colInsets, row, 2 * colInsets - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          book.setSelection(row, 2 * colInsets, row, mColCount - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          labelIndex = 0;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col, labelText);

          setQueryTitleFormat(i, book, row, col, title, labelIndex);

          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col + col + colInsets, labelText);

          setQueryTitleFormat(i, book, row, col + colInsets, title,
                              labelIndex);

          labelIndex = 2;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col + 2 * colInsets, labelText);

          setQueryTitleFormat(i, book, row, col + 2 * colInsets, title,
                              labelIndex);

        }
        else if (labelCount > 3) {
          book.setSelection(row, col, row, colInsets - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          book.setSelection(row, colInsets, row, 2 * colInsets - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          book.setSelection(row, 2 * colInsets, row, mColCount - 1);
          cf = book.getCellFormat();
          cf.setMergeCells(true);
          book.setCellFormat(cf);

          labelIndex = 0;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col, labelText);

          setQueryTitleFormat(i, book, row, col, title, labelIndex);

          labelIndex = 1;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          book.setText(row, col + colInsets, labelText);

          setQueryTitleFormat(i, book, row, col + colInsets, title,
                              labelIndex);

          labelIndex = 2;

          label = (XmlLabel) labels.elementAt(labelIndex);
          labelText = label.getCaption();
          //追加处理
          for (int j = 3; j < labelCount; j++) {
            label = (XmlLabel) labels.elementAt(j);
            labelText += " ";
            labelText += label.getCaption();
          }
          book.setText(row, col + 2 * colInsets, labelText);

          setQueryTitleFormat(i, book, row, col + 2 * colInsets, title,
                              labelIndex);

        }
        mRowIndex++;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setQueryTitleFormat(int index, JBook mBook, int row, int col,
                                  XmlTitle title, int labelIndex) throws
      Exception {

    mBook.setSelection(row, col, row, col);
    CellFormat cf = mBook.getCellFormat();
    //格式
    String align = title.getAlign();
    int fontSize = 0;
    String fontName = null;
    boolean fontBold = false, fontItalic = false;
    if (index == 0) {
//      fontName = printFormatObject.getPrintObject().getFirstTitleFontName(title);
//      fontSize = printFormatObject.getPrintObject().getFirstTitleFontSize(title) * 15;
      fontName = title.getFontname();
      try {
        fontSize = Integer.parseInt(title.getFontsize()) * 15;
      }
      catch (Exception ex) {
        fontSize = printFormatObject.getPrintObject().getFirstTitleFontSize(
            title) * 15;
        ex.printStackTrace();
      }
      fontBold = printFormatObject.getPrintObject().getTitleFontBold();
      fontItalic = printFormatObject.getPrintObject().getTitleFontItalic();
    }
    else {
      fontName = printFormatObject.getPrintObject().getTitleFontName(title);
      fontSize = printFormatObject.getPrintObject().getTitleFontSize(title) *
          15;
      fontBold = printFormatObject.getPrintObject().getDefaultFontBold();
      fontItalic = printFormatObject.getPrintObject().getDefaultFontItalic();
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
    cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
    cf.setFontSize(fontSize);
    cf.setFontName(fontName);
    cf.setFontBold(fontBold);
    cf.setFontItalic(fontItalic);
    mBook.setCellFormat(cf);
  }

  /**
   * 设置行高
   * @param startRow int
   */
  protected void setRowHeightForPage(int startRow) {
    int PageStartRow = printFormatObject.getPageRows();
    int SRow = startRow;
    int ERow = SRow + PageStartRow - 1;
    int twips = 0;
    try {
      twips = (int) CanvasUtils.convertInchtoTwip(printFormatObject.
                                                  getPrintObject().
                                                  getFirstRowHeigth());
      book.setRowHeight(SRow, SRow, twips, false, false);
      twips = (int) CanvasUtils.convertInchtoTwip(printFormatObject.
                                                  getPrintObject().getRowHeigth());
      book.setRowHeight(SRow + 1, ERow, twips, false, false);
    }
    catch (Exception e) {}
  }

  /**
   * 获取显示示顺序列表（分面，对于多栏账适用）
   * @param tableManager TableManager
   * @param ColList Hashtable
   * @param GroupList Hashtable
   * @param FixCols int
   * @param ValCols int
   * @param Style int
   * @return ViewDefine[]
   */
  public static ViewDefine[] getViewLayouts(ViewDefine viewLayout,
                                            Hashtable ColList,
                                            Hashtable GroupList,
                                            int FixCols, int ValCols,
                                            int Style) {
    ViewDefine[] VDArray = null;
    Hashtable[] HTArray = null;
    ColumnDefine CD = null, BCD = null;
    Vector viewColList = viewLayout.buildViewColList(); // 所有的可视列，按顺序排列
    int ColCount = viewColList.size(); // 获取所有的列数
    //如果固定列大于总列数，则让其等于固定列，否则会出错
    if (FixCols > ColCount) {
      FixCols = ColCount;
    }
    //如果变动列大于总列数，则让其等于总列数 - 固定列
    if (FixCols + ValCols > ColCount) {
      ValCols = ColCount - FixCols;
    }

    if (FixCols == 0 || ValCols == 0) {
      VDArray = new ViewDefine[1];
      VDArray[0] = viewLayout;
      return VDArray;
    }
    // 每页的列数 (固定数+变动数)
    int ColsPerPage = FixCols + ValCols;
    // 获取纵向分页数
    int PortraitPages = (ColCount - FixCols) / (ValCols); // 纵向分页数,根据固定列和变动列计算而来
    int mod = (ColCount - FixCols) % (ValCols);
    if (mod > 0) {
      PortraitPages++;
    }
    // 形成ViewDefine的数组
    VDArray = new ViewDefine[PortraitPages];
    // 形成Hashtable的数组
    HTArray = new Hashtable[PortraitPages];
    for (int p = 0; p < PortraitPages; p++) {
      VDArray[p] = new ViewDefine();
      HTArray[p] = new Hashtable();
    }
    // 形成所有的固定列
    for (int i = 0; i < FixCols; i++) {
      // 取出列信息
      CD = (ColumnDefine) viewColList.get(i);
      // 所有页的所有固定列
      for (int p = 0; p < PortraitPages; p++) {
        // 创建新组,也有可能是新列
        BCD = rebuildGroup(CD, VDArray[p], HTArray[p]);
        // 增加到此页的可视列里
        VDArray[p].addCol(BCD);
      }
    }
    // 处理所有页的变动列
    for (int p = 0; p < PortraitPages; p++) {
      int beginCol = p * ValCols + FixCols;
      int endCol = (beginCol + ValCols) > ColCount ? ColCount :
          (beginCol + ValCols);
      for (int i = beginCol; i < endCol; i++) {
        CD = (ColumnDefine) viewColList.get(i);
        // 创建新组,也有可能是新列
        BCD = rebuildGroup(CD, VDArray[p], HTArray[p]);
        // 增加到此页的可视列里
        VDArray[p].addCol(BCD);
      }
    }
    return VDArray;
  }

  /**
   * 有可能是返回的组，也有可能是返回的列
   * @param CD ColumnDefine
   * @param VD ViewDefine
   * @param HT Hashtable
   * @return ColumnDefine
   */
  private static ColumnDefine rebuildGroup(ColumnDefine CD, ViewDefine VD,
                                           Hashtable HT) {
    // 如果没有在组中，则直接clone，返回
    ColumnDefine BCD = (ColumnDefine) CD.clone();
    if (CD.getParentCol() == null) {
      return BCD;
    }
    else { // 如果是在一个组中，则还需要处理
      GroupDefine BGD = null;
      GroupDefine GD = (GroupDefine) CD.getParentCol();
      while (GD != null) {
        BGD = (GroupDefine) HT.get(GD.getColID());
        if (BGD == null) {
          BGD = (GroupDefine) GD.clone();
          HT.put(GD.getColID(), BGD);
        }
        if (!BGD.hasChild(BCD)) {
          BGD.addChildColumn(BCD);
        }
        BCD = BGD;
        // 再上一级的
        GD = (GroupDefine) GD.getParentCol();
      }
      return BGD;
    }
  }

  /**
   * 获取显示顺序列表(不分页,对于三栏账适用)
   * @param tableManager TableManager
   * @param ColList Hashtable
   * @param GroupList Hashtable
   * @return ViewDefine
   */
  public static ViewDefine getViewLayout(TableManager tableManager,
                                         Hashtable ColList,
                                         Hashtable GroupList) {
    ViewDefine viewLayout = new ViewDefine();
//    viewLayout.setColList(ColList);
//    viewLayout.setGroupList(GroupList);
    List Nodes = tableManager.getChildrenNodes(tableManager.TableView,
                                               "ViewCol");
    if (Nodes == null || Nodes.size() == 0) {
      return viewLayout;
    }
    for (int i = 0; i < Nodes.size(); i++) {
      Element viewNode = (Element) Nodes.get(i);
      String ID = tableManager.getAttribute(viewNode, "id");
      String TY = tableManager.getAttribute(viewNode, "type");
      if ("col".equals(TY.toLowerCase())) {
        viewLayout.addCol( (ColumnDefine) ColList.get(ID));
      }
      if ("group".equals(TY.toLowerCase())) {
        viewLayout.addCol( (ColumnDefine) GroupList.get(ID));
      }
    }
    return viewLayout;
  }

  /**
   * 获取所有组定义信息
   * @param tableManager TableManager
   * @return Vector
   */
  public static Hashtable getGroupDefines(TableManager tableManager,
                                          Hashtable ColList,
                                          Object printObject) {
    Hashtable GroupList = new Hashtable();
    List Nodes = tableManager.getChildrenNodes(tableManager.Groups, "Group");
    if (Nodes == null || Nodes.size() == 0) {
      return GroupList;
    }

    for (int i = 0; i < Nodes.size(); i++) {
      GroupDefine element = new GroupDefine();
      Element node = (Element) Nodes.get(i);

      element.setColID(tableManager.getAttribute(node, "id"));
      element.setColName(tableManager.getAttribute(node, "name"));
      element.setColCaption(tableManager.getAttribute(node, "caption"));
      element.setFontName(tableManager.getAttribute(node, "fontname"));
      element.setFontStyle(tableManager.getAttribute(node, "fontstyle"));
      element.setFontSize(tableManager.getAttribute(node, "fontsize"));
      //获得Group下的Ｉtems
      List itemLists = tableManager.getChildrenNodes(node, "Item");
      for (int j = 0; j < itemLists.size(); j++) {
        Element itemNode = (Element) itemLists.get(j);
        String ID = tableManager.getAttribute(itemNode, "id");
        String TY = tableManager.getAttribute(itemNode, "type");
        if ("col".equals(TY.toLowerCase())) {
          element.addChildColumn( (ColumnDefine) ColList.get(ID));
        }
        if ("group".equals(TY.toLowerCase())) {
          element.addChildColumn( (ColumnDefine) GroupList.get(ID));
        }
      }
      GroupList.put(element.getColID(), element);
    }
    return GroupList;
  }

  /**
   * 获取所有列定义信息
   * @param tableManager TableManager
   * @return Vector
   */
  public static Hashtable getColumnDefines(TableManager tableManager,
                                           Object printObject) {
    Hashtable ColList = new Hashtable();
    List Nodes = tableManager.getChildrenNodes(tableManager.Cols, "Col");
    if (Nodes == null || Nodes.size() == 0) {
      return ColList;
    }
    for (int i = 0; i < Nodes.size(); i++) {
      ColumnDefine col = new ColumnDefine();
      Element colNode = (Element) Nodes.get(i);
      col.setColID(tableManager.getAttribute(colNode, "id"));
      col.setColName(tableManager.getAttribute(colNode, "name"));
      col.setColCaption(tableManager.getAttribute(colNode, "caption"));
      col.setFontName(tableManager.getAttribute(colNode, "fontname"));
      col.setFontStyle(tableManager.getAttribute(colNode, "fontstyle"));
      col.setFontSize(tableManager.getAttribute(colNode, "fontsize"));
      col.setColWidth(tableManager.getAttribute(colNode, "width"));
      col.setColFormat(tableManager.getAttribute(colNode, "format"));
      col.setColAlign(tableManager.getAttribute(colNode, "align"));
      col.setColType(tableManager.getAttribute(colNode, "datatype"));
      col.setShowmask(tableManager.getAttribute(colNode, "showmask"));
      ColList.put(col.getColID(), col);
    }
    return ColList;
  }

  /**
   * 返回打印标题
   * @param tableManager TableManager
   * @return Vector
   */
  public static Vector getViewTitles(TableManager tableManager,
                                     Object printObject) {
    return tableManager.getTitles();
  }

  /**
   * 检查是否有隐藏列
   * 2007-11-12 fengbo 增加该注释:
   * 若第一个隐藏列在第一个间隔内部,则间隔长度=间隔长度+1;
   * 这是一种模糊处理方法,某些时候(总共6列,三个副标题,第2列是隐藏列)会有问题;
   * @param column int
   * @return int
   */
  private int checkColumn(TableManager tm,int column) {
    String vsWidth;
    Vector mViewCols = tm.getTableView();
    Vector mValueGroups = tm.getGroups();
    Vector mValueCols = tm.getColumns();
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
        Vector leafItems = JDRQueryPainterUtils.getLeafItems(tm,
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
      if (viWidth <= 0 && column > c) {
        column++;
        break;
      }
    }
    return column;
  }
}
