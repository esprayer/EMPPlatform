package com.print.service;

import jservice.jbof.classes.GenerQueryObject.*;
import java.util.*;

import com.core.xml.JBOFClass;
import com.core.xml.StubObject;
import com.efounder.eai.data.JParamObject;
import com.f1j.swing.*;
import com.report.table.jxml.TableManager;

/**
 * <p>Title: 帐册和余额打印处理类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AccountPrint {
  static ResourceBundle res = ResourceBundle.getBundle("com.print.service.Language");
  private static final String    YE_PRINT_CLASS  = "com.print.account.JAccountBookPrinter";
  private static final String    SLZ_PRINT_CLASS = "";
  private static final String    DLZ_PRINT_CLASS = "";
  private static final String    PZ_PRINT_CLASS  = "";
  private static final String    RPT_PRINT_CLASS = "";
  private static final Hashtable PrintClassList  =  new Hashtable();
  static {
    PrintClassList.put("YE",YE_PRINT_CLASS);
    PrintClassList.put("SLZ",SLZ_PRINT_CLASS);
    PrintClassList.put("DLZ",DLZ_PRINT_CLASS);
    PrintClassList.put("PZ",PZ_PRINT_CLASS);
    PrintClassList.put("RPT",RPT_PRINT_CLASS);
  }
  public AccountPrint() {
  }
  /**
   *
   * @param Book JBook
   * @param tableManager TableManager
   * @param datas StubObject[]
   * @param printObject CustomPrint
   * @param dataLevel int
   * @param FixCols int  固定列
   * @param ValCols int  变动列
   * @param StartPageNo int 首页页号
   * @param Style int   是否换页
   * @param colIndex int
   * @param Rows int 每页行数
   * @param context 是否需要“承前页”“过次页”
   * @param filpage 是否补空行
   * @throws Exception
   * @return Object
   */
  public static Object printAccountBook3(JBook Book, TableManager tableManager,
                                         StubObject[] datas,
                                         CustomPrint printObject, int dataLevel,
                                         int FixCols, int ValCols,
                                         int StartPageNo, int Style,
                                         int colIndex, int Rows,
                                         boolean context,boolean fillpage) throws Exception {
    // 获取打印格式对象
    PrintFormatObject printFormatObject = PrintFormatObject.getPrintFormatObject(Book,tableManager,printObject,FixCols,ValCols,StartPageNo,Style,colIndex,Rows,fillpage);
    // 获取打印数据对象
    PrintDataObject   printDataObject   = PrintDataObject.getPrintDataObject(printFormatObject,datas,printObject,dataLevel);
    // 打印
    Object printResult = DrawObject2.printObject(Book,printFormatObject,printDataObject,printObject,context);
    Object[] printArray = {printFormatObject,printDataObject,printResult,datas};
    return printArray;
  }
  
  
  /**
  * 帐页打印专用方法，为防止修改以前的方法而影响其他功能，特侧加次方法
  * @param Book printBook
  * @param Book queryBook
  * @param tableManager TableManager
  * @param datas StubObject[]
  * @param printObject CustomPrint
  * @param dataLevel int
  * @param FixCols int  固定列
  * @param ValCols int  变动列
  * @param StartPageNo int 首页页号
  * @param Style int   是否换页
  * @param colIndex int
  * @param Rows int 每页行数
  * @param context 是否需要“承前页”“过次页”
  * @param filpage 是否补空行
  * @param startZy 是否起始帐页 -1：不处理；0：是；1：否
  * @throws Exception
  * @return Object
  */
 public static Object printAccountBook4(JBook printBook, JBook queryBook, 
		                                 TableManager tableManager,
                                        StubObject[] datas, CustomPrint printObject, 
                                        int dataLevel, int FixCols, 
                                        int ValCols, int StartPageNo, 
                                        int CurPageNo, int Style,
                                        int colIndex, int Rows,
                                        boolean context,boolean fillpage,
                                        boolean startZy) throws Exception {
	  // 获取打印格式对象
	  PrintFormatObject printFormatObject = PrintFormatObject.getPrintFormatObject(queryBook,printBook,tableManager,printObject,FixCols,ValCols,StartPageNo,CurPageNo,Style,colIndex,Rows,fillpage);
	  // 获取打印数据对象
	  PrintDataObject   printDataObject   = PrintDataObject.getPrintDataObject(printFormatObject,datas,printObject,dataLevel);
	  // 打印
	  Object printResult = DrawObject2.printObject(printBook,printFormatObject,printDataObject,printObject,context, startZy);
	  Object[] printArray = {printFormatObject,printDataObject,printResult,datas};
	  return printArray;
 }
 
  public static Object printAccountBook2(JBook Book,TableManager tableManager,Object Context,int Index,JParamObject PO) throws Exception {
    Hashtable ColList   = DrawObject2.getColumnDefines(tableManager,null);
    Hashtable GroupList = DrawObject2.getGroupDefines(tableManager,ColList,null);
    ViewDefine VD       = DrawObject2.getViewLayout(tableManager,ColList,GroupList);
    ViewDefine[] VDS    = DrawObject2.getViewLayouts(VD,ColList,GroupList,4,3,0);
    return VDS;
  }
  /**
   * 账册打印的方法：
   * 1、第一步，首先确定出，对于当前用户设置的纸张类型，一张纸上能够打印多少行的数据
   *   确定的方法：(页高-(页眉＋上边距＋标题＋页脚＋下边距))/行高 (在打印中，行高是固定的)
   *   标题高度的确定 标题=(标题行数*行高)
   * 2、
   */
  public static Object printAccountBook(JBook Book,TableManager tableManager,Vector queryData,Object Context,int Index,JParamObject PO) throws Exception {
    JQueryStubObject QO = (JQueryStubObject)Context;
    if ( QO == null ) throw new Exception(res.getString("String_6"));
    // 根据JQueryStubObject 获取相应的打印对象
    Object PrintObject  = getBookPrintObject(QO);
    // 构造AccountBookStub
    JAccountBookStub AccountBookStub = new JAccountBookStub(Book,tableManager,queryData,QO,PrintObject,PO);
    Object[] OArray = {AccountBookStub,new Integer(Index),QO};
    // 用户打印对象的初始化
    JBOFClass.CallObjectMethod(PrintObject,"initPrintOjbect",OArray);
    // 初始化
    AccountBookStub.initAccountBookStub();
    // 对JAccountBookStub 进行打印 将AccountBook打印到指定的Sheet
    JBOFClass.CallObjectMethod(PrintObject,"printOjbect",OArray);
    return null;
  }
  /**
   * 根据Context对象获取打印对象
   * @param context JQueryStubObject
   * @return Object
   */
  private static Object getBookPrintObject(JQueryStubObject context) {
    Object PrintObject = null;
    try {
      String sPrintClass = null;
      String TYPE = (String)context.EntityQuery.get("viewtype");
      if ( TYPE != null && !"".equals(TYPE) )
        sPrintClass = (String)PrintClassList.get(TYPE);
      if (sPrintClass == null || "".equals(sPrintClass) )
        sPrintClass = YE_PRINT_CLASS;
//      if (sPrintClass != null && !"".equals(sPrintClass) ) {
        Class  PrintClass  = Class.forName(sPrintClass);
        PrintObject = PrintClass.newInstance();
//      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return PrintObject;
  }
  /**
   *
   * @param abs JAccountBookStub
   * @return Object
   */
  public static Object printAccountBookFormat(JAccountBookStub abs) {
    DrawObject.drawAccountBookFormat(abs);
    return null;
  }
  public static Object printAccouontBookData(JAccountBookStub abs) {
    DrawObject.drawAccountBookData(abs);
    return null;
  }
}
