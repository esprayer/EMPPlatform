package jservice.jbof.classes;

import java.lang.reflect.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import org.jdom.*;
import jbof.gui.window.classes.*;
import jfoundation.bridge.classes.*;
import jfoundation.gui.window.classes.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;
//import jreport.swing.classes.util.ReportUtil;
import jservice.jbof.classes.GenerQueryObject.GlobalDataObject;
import jservice.jbof.classes.GenerQueryObject.JGenerQueryActionImpl;
import jservice.jbof.classes.GenerQueryObject.JQueryStubObject;
import jservice.jbof.classes.GenerQueryObject.JGenerQueryWindow;
import jservice.jbof.classes.GenerQueryObject.JQueryWaitPanel;
import jservice.jbof.classes.GenerQueryObject.action.JBookAction;
import jservice.jbof.classes.GenerQueryObject.action.JBookActionConstants;
import jservice.jbof.classes.GenerQueryObject.action.JBookActionImpl;
import jservice.jbof.classes.GenerQueryObject.action.JBookActionManager;
import jservice.jbof.classes.query.QueryParamManager;
import jservice.jdal.classes.query.pub.parse.ExteriorQueryStru;
import jtoolkit.xml.classes.*;
import javax.print.PrintService;
import java.awt.print.PrinterJob;
import jbof.gui.window.classes.style.mdi.JBOFMDIChildWindow;
import org.openide.*;

import com.common.JWaitDialog;
import com.core.xml.StubObject;
import com.efounder.eai.*;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.pub.util.Debug;
import com.f1j.swing.JBook;
import com.print.service.AccountPrint;
import com.print.service.AccountPrintFormatParam;
import com.print.service.CustomPrint;
import com.print.service.CustomPrintObject;
import com.print.service.DrawObject2;
import com.report.table.jxml.TableManager;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JGenerQueryObject
    extends JActiveObject {
  static ResourceBundle res = ResourceBundle.getBundle(
      "jservice.jbof.classes.Language");
  private static final String GUID = "00000000-1111-0001-0000000000000017";
  public static final Hashtable QueryObjectList = new Hashtable();
  JWaitDialog WaitWindow = null;
//  protected JParamObject PO = JParamObject.Create();
  public JGenerQueryObject() {
    setObjectGUID(GUID);
  }

  public Object InitObject(Object Param, Object Data, Object CustomObject,
                           Object AdditiveObject) {
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //描述: 返回但是不显示查询结果。
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object LinkQueryObjectNoShow(Object Param, Object Data,
                                      Object CustomObject,
                                      Object AdditiveObject) {
    JBOFMDIChildWindow childQueryWindow = null;
    String oldPath = (String) Param;
    String QueryStub = (String) Param;
    JGenerQueryWindow QueryWindow = null;
    QueryWindow = (JGenerQueryWindow) CustomObject;
    if (Param == null) {
      return null;
    }
    Object EO = null;
    String Path = QueryStub;
    JQueryStubObject QO = null;
    try {
      Path = Path.substring(0, Path.lastIndexOf("/") + 1);
      if (!Path.endsWith("/")) {
        Path += "/";
        // 准备阶段
      }

      //2007-11-13 YRH 加上语言路径
      if (QueryStub.indexOf("/" + JActiveDComDM.International + "/") < 0) {
        QueryStub = QueryStub.replaceFirst(QueryStub.substring(QueryStub.
            lastIndexOf("/")), "/" + JActiveDComDM.International + "/") +
            QueryStub.substring(QueryStub.
                                lastIndexOf("/") + 1);
      }

      JXMLBaseObject XMLObject = getQueryObject(QueryStub);
      //如果找不到，则反回原值再找
      if (XMLObject == null) {
        QueryStub = (String) Param;
        XMLObject = getQueryObject(QueryStub);
        if (XMLObject == null) {
          return null;
        }
      }
      else {
        Param = QueryStub;
      }
      //END 2007-11-13 YRH
      QO = getQueryStubObject(XMLObject, Path);
      QO.Param = Param;

      // 处理前缀阶段
      JParamObject PO = (JParamObject) Data;

      if (PO != null) {
        long s = System.currentTimeMillis();
        JResponseObject RO = processOrganizeQuery(QO, PO);
        if (RO.ErrorCode == 0) {
          // 处理实体阶段
          Object[] objs=processEntityQueryNoShow(QO, PO, RO);
          PO = (JParamObject) objs[0];
          childQueryWindow = (JBOFMDIChildWindow)objs[1];
          //分页查询且有后继数据页时,继续下一次查询 2007-7-18 fengbo
          if (PO.GetValueByParamName(GlobalDataObject.__MULTI_PAGE_QUERY).
              equals(GlobalDataObject.__MULTI_PAGE_QUERY)
              && !PO.GetValueByParamName("EX_DATE", "").equals("")) {
            queryDataObject(oldPath,PO,QueryWindow,null);
          }
        }
        else {
          JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.
                                        MainWindow, RO.ErrorString,
                                        res.getString("String_42"),
                                        JOptionPane.ERROR_MESSAGE);
        }
        long e = System.currentTimeMillis();
        Debug.PrintlnMessageToSystem(res.getString("String_43") + (e - s) +
                                     res.getString("String_44"));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,
                                    e.getMessage(), res.getString("String_45"),
                                    JOptionPane.ERROR_MESSAGE);
    }
    finally {
      JActiveDComDM.MainApplication.EndWaitCursor();
      this.hideWaiting(QO);
    }
    return childQueryWindow;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object LinkQueryObject(Object Param, Object Data, Object CustomObject,
                                Object AdditiveObject) {
    //2007-12-20 add by fengbo
    String oldPath = (String) Param;
    String QueryStub = (String) Param;
    JGenerQueryWindow QueryWindow = null;
    QueryWindow = (JGenerQueryWindow) CustomObject;
    if (Param == null) {
      return null;
    }
    Object EO = null;
    String xmlFile = QueryStub;
    String Path = QueryStub; // /Resource/JCost/Query/fsxhzcx.xml ->/Resource/JCost/Query/
    JQueryStubObject QO = null;
    try {
      Path = Path.substring(0, Path.lastIndexOf("/") + 1);
      if (!Path.endsWith("/")) {
        Path += "/";
        // 准备阶段
      }

      //2007-11-13 YRH 加上语言路径
      if (QueryStub.indexOf("/" + JActiveDComDM.International + "/") < 0) {
        QueryStub = QueryStub.replaceFirst(QueryStub.substring(QueryStub.
            lastIndexOf("/")), "/" + JActiveDComDM.International + "/") +
            QueryStub.substring(QueryStub.
                                lastIndexOf("/") + 1);
      }

      JXMLBaseObject XMLObject = getQueryObject(QueryStub);
      //如果找不到，则反回原值再找
      if (XMLObject == null) {
        QueryStub = (String) Param;
        XMLObject = getQueryObject(QueryStub);
        if (XMLObject == null) {
          return null;
        }
      }
      else {
        xmlFile = QueryStub;
        Param = QueryStub;
      }
      //END 2007-11-13 YRH

      QO = getQueryStubObject(XMLObject, Path);
      // 如果有父窗口需设置
      if (QueryWindow != null) {
        QO.EntityQuery.put("PARENTWINDOW", QueryWindow);
      }
      // 创建Wait
      processWait(QO);
      // 显示Wait
      showWaiting(QO);

      QO.Param = Param; //QO.Data = Data;
      if (QO == null) {
        return null;
      }
      // 处理前缀阶段
      JParamObject PO = (JParamObject) Data; //processPrefixQuery(QO);

      if (PO != null) {
        // 显示信息
        getQueryWait(QO).setTitle1(res.getString("String_30") + QO.CaptionText +
                                   (
                                       QO.CaptionText.endsWith(res.getString(
                                           "String_31")) ?
                                       "" : res.getString("String_33")) +
                                   res.getString("String_34"));
        JActiveDComDM.MainApplication.BeginWaitCursor();
        // 处理组织阶段
        getQueryWait(QO).setTitle2(res.getString("String_35"));
        Debug.PrintlnMessageToSystem(res.getString("String_36"));
        long s = System.currentTimeMillis();
        JResponseObject RO = processOrganizeQuery(QO, PO);
        if (RO.ErrorCode == 0) {
          //显示自定义提示信息
          if (RO.ErrorString!=null && !"".equals(RO.ErrorString)){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.
                                          MainWindow, RO.ErrorString,
                                          res.getString("String_113"),
                                          JOptionPane.ERROR_MESSAGE);
          }
          // 处理实体阶段
          EO = processEntityQuery(QO, PO, RO);
          //分页查询且有后继数据页时,继续下一次查询 2007-7-18 fengbo
          PO = (JParamObject) EO;
          if (PO.GetValueByParamName(GlobalDataObject.__MULTI_PAGE_QUERY).
              equals(GlobalDataObject.__MULTI_PAGE_QUERY)
              && !PO.GetValueByParamName("EX_DATE", "").equals("")) {
            JActiveDComDM.BusinessActiveFramework.
                MInvokeObjectMethod("GenerQueryObject", "queryDataObject",
                                    oldPath, PO, QueryWindow, null);
          }
        }
        else {
          JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.
                                        MainWindow, RO.ErrorString,
                                        res.getString("String_42"),
                                        JOptionPane.ERROR_MESSAGE);
        }
        long e = System.currentTimeMillis();
        Debug.PrintlnMessageToSystem(res.getString("String_43") + (e - s) +
                                     res.getString("String_44"));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,
                                    e.getMessage(), res.getString("String_45"),
                                    JOptionPane.ERROR_MESSAGE);
    }
    finally {
      JActiveDComDM.MainApplication.EndWaitCursor();
      this.hideWaiting(QO);
    }
    return EO;
  }

  //------------------------------------------------------------------------------------------------
  //描述:新增的数据查询方法,只是返回查询结果,放入全局数据存储区;不展示结果;
  //设计:
  //实现: 2007.7.16 fengbo
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object queryDataObject(Object Param,
                                Object Data,
                                Object CustomObject,
                                Object AdditiveObject) {
    String QueryStub = (String) Param;
    if (Param == null) {
      return null;
    }
    JResponseObject RO = null;
    String Path = QueryStub;
    JQueryStubObject QO = null;
    JParamObject PO = (JParamObject) Data;
    try {
      Path = Path.substring(0, Path.lastIndexOf("/") + 1);
      if (!Path.endsWith("/")) {
        Path += "/";
      }
      //2007-11-13 YRH 加上语言路径
      if (QueryStub.indexOf("/" + JActiveDComDM.International + "/") < 0) {
        QueryStub = QueryStub.replaceFirst(QueryStub.substring(QueryStub.
            lastIndexOf("/")), "/" + JActiveDComDM.International + "/") +
            QueryStub.substring(QueryStub.
                                lastIndexOf("/") + 1);
      }

      JXMLBaseObject XMLObject = getQueryObject(QueryStub);
      //如果找不到，则反回原值再找
      if (XMLObject == null) {
        QueryStub = (String) Param;
        XMLObject = getQueryObject(QueryStub);
        if (XMLObject == null) {
          return null;
        }
      }
      else {
        Param = QueryStub;
      }
      //END 2007-11-13 YRH
      QO = getQueryStubObject(XMLObject, Path);

      QO.Param = Param;
      if (QO == null) {
        return null;
      }
      if (PO != null) {
        // 处理组织阶段
        RO = processOrganizeQuery(QO, PO);
        //在全局静态存储区，存储查询结果
        GlobalDataObject.putValue(PO.GetValueByParamName(GlobalDataObject.
            __PARAM_KEY), RO);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,
                                    e.getMessage(), res.getString("String_55"),
                                    JOptionPane.ERROR_MESSAGE);
      //在全局静态存储区，存储异常信息
      GlobalDataObject.putValue(PO.GetValueByParamName(GlobalDataObject.
          __PARAM_KEY),
                                GlobalDataObject.__ERROR_INFO);
    }
    finally {
    }
    return RO;
  }

  // 创建一个等待窗口
  public static void processWait(JQueryStubObject QO) {
    JQueryWaitPanel QWP = new JQueryWaitPanel();
    JDialog WaitDialog = new JWaitDialog(QWP);
    QO.PostfixQuery.put("WaitDialog", WaitDialog);
  }

  // 获取等待Panel
  public static JQueryWaitPanel getQueryWait(JQueryStubObject QO) {
    JWaitDialog WaitDialog = (JWaitDialog) QO.PostfixQuery.get("WaitDialog");
    JQueryWaitPanel QWP = null;
    if ( WaitDialog != null)
      QWP = (JQueryWaitPanel) WaitDialog.getUserObject();
    return QWP;
  }

  // 显示等待窗口
  public static void showWaiting(JQueryStubObject QO) {
    JWaitDialog WaitDialog = (JWaitDialog) QO.PostfixQuery.get("WaitDialog");
    Runnable WC = new WaitClass(WaitDialog);
    SwingUtilities.invokeLater(WC);
  }

  // 关闭等待窗口
  public static void hideWaiting(JQueryStubObject QO) {
    JWaitDialog WaitDialog = (JWaitDialog) QO.PostfixQuery.get("WaitDialog");
    if (WaitDialog != null) {
      WaitDialog.hide();
      WaitDialog.dispose();
    }
  }

//------------------------------------------------------------------------------------------------
  //描述: 正式账页打印所用
  //设计: Skyline
  //实现: YRH(2005.02.24)
  //修改: ZhangES(2011.08.19)
  /**
   * @param Param Object            XML文件存放路径
   * @param Data Object             为空，不用
   * @param CustomObject Object     PO
   * @param AdditiveObject Object   String[] //0:有余额打印;1:无余额也打;2:固定列;3:变动列;4:开始页号;5:是否换页;
   * @return Object
   */
  //------------------------------------------------------------------------------------------------
  public Object QueryObjectPrint(Object Param, Object Data, Object CustomObject,
                                 Object AdditiveObject) {
    String QueryStub = (String) Param;
    JParamObject PO = (JParamObject) CustomObject;

    AccountPrintFormatParam AccountFormat = (AccountPrintFormatParam)
        AdditiveObject;
    int FixCols = AccountFormat.Fixcol; // 固定列 如果是三栏账设为0
    //因为只有账页打印才调用此方法；账页都有会计期间隐藏列，所以 特殊处理账页隐藏的‘会计期间’列
    if (FixCols >= 2) {
      FixCols++;
    }
    int ValCols = AccountFormat.Valcol; // 变动列 如果是三栏账设为0
    int StartPageNo = AccountFormat.SaterPageNO; // 开始页号
    int CurPageNo = 1; // 当前打印页数
    int Style = AccountFormat.ChangePage; // 0:新科目换页 1:不换
    int PageRows = AccountFormat.PageRows; //每页行数
    String Type = AccountFormat.Type; //账页类型

    if (Param == null) {
      return null;
    }
    Object EO = "";
    String ES = "";
    String Path = QueryStub;
    JQueryStubObject QO = null;
    try {
      Path = Path.substring(0, Path.lastIndexOf("/") + 1);
      if (!Path.endsWith("/")) {
        Path += "/";
        // 准备阶段
      }
      //2007-11-13 YRH 加上语言路径
      if (QueryStub.indexOf("/" + JActiveDComDM.International + "/") < 0) {
        QueryStub = QueryStub.replaceFirst(QueryStub.substring(QueryStub.
            lastIndexOf("/")), "/" + JActiveDComDM.International + "/") +
            QueryStub.substring(QueryStub.
                                lastIndexOf("/") + 1);
      }

      JXMLBaseObject XMLObject = getQueryObject(QueryStub);
      //如果找不到，则反回原值再找
      if (XMLObject == null) {
        QueryStub = (String) Param;
        XMLObject = getQueryObject(QueryStub);
        if (XMLObject == null) {
          return null;
        }
      }
      else {
        Param = QueryStub;
      }
      //END 2007-11-13 YRH

      QO = getQueryStubObject(XMLObject, Path);

      QO.Param = Param;
      QO.Data = Data;
      if (QO == null) {
        return null;
      }
      // 处理前缀阶段
      if (PO != null) {
        processWait(QO);
        // 显示Wait
        //showWaiting(QO);
        // 显示信息
        getQueryWait(QO).setTitle1(res.getString("String_69") + QO.CaptionText +
                                   (
                                       QO.CaptionText.endsWith(res.getString(
                                           "String_70")) ?
                                       "" : res.getString("String_72")) +
                                   res.getString("String_73"));

        JActiveDComDM.MainApplication.BeginWaitCursor();
        // 处理组织阶段
        getQueryWait(QO).setTitle2(res.getString("String_74"));

        int count = AccountFormat.ZyCount;
        JParamObject convertPO = null;
        JResponseObject RO = null;
        //打印
        JBook queryBook = null; //查询后的JBook
        JBook printBook = new JBook(); //将要打印的JBook
        TableManager tableManager = null;

        //处理：无发生、无余额不打印（多栏账除外）
        //帐页查询至少3行：上期结转、本期合计、本年累计
        int COUNT = 3;
        //查询结果的总行数（标题+表头+数据）
        int Rows = 0;
        //冻结行数=标题+表头
        int FixedRows = 0;
        //是否起始帐页   0：是；1：否
        boolean startZy = true;
        //2011-08-18 ZhangES
        //处理原因：有多个账册，并同时放到一个JBook中
        for(int i = 0; i < count; i++){
        	convertPO = QueryParamManager.getDefault("QueryPrint").beginConvert("", PO, null);
        	RO = processOrganizeQuery(QO, convertPO);
        	// 处理实体阶段
            getQueryWait(QO).setTitle2(res.getString("String_75"));
            JGenerQueryWindow QueryWindow = (JGenerQueryWindow) processEntityQueryPrint(QO, PO, RO);
        	if (RO.ErrorCode == 0) {
        		queryBook = QueryWindow.vwQueryView.getDataReport().getBook();

                tableManager = QueryWindow.vwQueryView.getTableManager();
                Rows = queryBook.getLastRow() + 1;
                FixedRows = queryBook.getBookModel().getFixedRows();
                if (AccountFormat.YePrint &&
                    !AccountFormat.WePrint &&
                    !Type.startsWith("4")) {

                	if (Rows == FixedRows) { //只有表头
                		if(EO.toString().trim().equals("")) EO = (Object) (String.valueOf(0));
                		else EO = (Object) (EO.toString() + ";1");
                		continue;
                	}
                	//当无发生额时，判断余额是否为零
                	else if (Rows == FixedRows + COUNT) {
                		String vsDqye = "";
                		//2009-03-22现在各种账式的金额列都在最后
//                    	if (AccountFormat.Format.equals("100")) {
                    		vsDqye = queryBook.getText(queryBook.getLastRow(), queryBook.getLastCol());
//                    	}
//                    	else {
//                    		vsDqye = Book.getText(Book.getLastRow(), Book.getLastCol() - 1);
//                    	}
                    	if ("".equals(vsDqye) || vsDqye == null) {
                    		vsDqye = "0";
                    	}
                    	double vdYe = Double.parseDouble(vsDqye);
                    	if (vdYe == 0) {
                    		if(EO.toString().trim().equals("")) EO = (Object) (String.valueOf(0));
                    		else EO = (Object) (EO.toString() + ";1");
                    		continue;
                    	}//end if
                	}//end else if
                }//end if

                StubObject[] datas = new StubObject[1];
                datas[0] = new StubObject();
                datas[0].setObject("dataManager",
                                   QueryWindow.vwQueryView.getQueryDataSet());
                CustomPrint printObject = new CustomPrintObject(PO, QO, queryBook, printBook, AccountFormat);
                Object printArray = AccountPrint.printAccountBook4(printBook, queryBook,
                					tableManager, datas, printObject, 0, FixCols, ValCols,
                					StartPageNo + CurPageNo - 1, CurPageNo, Style, 3, PageRows, true, AccountFormat.FillPage, startZy);
                Object[] PrintArray = (Object[]) printArray;
                DrawObject2 printResult = (DrawObject2) PrintArray[2];
                //起始页号
                Object Key = "startPageNo", Default = "0";
                Object startPageNo = datas[0].getObject(Key, Default);
                //页数
                int PageCount = printResult.getVPageCount();
                CurPageNo += PageCount;
                if(EO.toString().trim().equals("")) EO = (Object) String.valueOf(PageCount);
                else EO = (Object) (EO.toString() + ";" + String.valueOf(PageCount));
                startZy = false;
        	}//end if
        	else {
                JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.
                                              MainWindow, RO.ErrorString,
                                              res.getString("String_89"),
                                              JOptionPane.ERROR_MESSAGE);
        	}
        }//end for

        //打印
        if (PO.GetValueByParamName("EXCEL", "").trim().equals("0")) {
          ActionmnPrintArea2(printBook);
          this.Print(printBook, QO,
                     PO.GetValueByParamName("ZDYH").trim().equals("1"));
        }
        else {
          this.saveToExcel(printBook, QO);
        }

      }
    }
    catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,
                                    e.getMessage(), res.getString("String_90"),
                                    JOptionPane.ERROR_MESSAGE);
    }
    finally {
      JActiveDComDM.MainApplication.EndWaitCursor();
    }
    return EO;
  }


  //------------------------------------------------------------------------------------------------
  //描述: 清除打印区域：就是以默认大小打印，可以设置其打印区域是最大行列数
  //修改:
  //------------------------------------------------------------------------------------------------
  public void ActionmnPrintArea2(JBook printBook) {
      try {
          int rowCount = printBook.getLastRow();
          int colCount = printBook.getLastCol();
          String selection = "A1:";
          String colString = strID(colCount);
          String rowString = String.valueOf(rowCount);

          printBook.setSelection(0, 0, rowCount, colCount);
          printBook.setPrintArea();

//          printBook.setPrintArea(selection + colString + rowString);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
  public String strID(int index) {
	String str = "";
	index--;
	str += (char) (65 + index % 26);
	while ( (index = index / 26 - 1) >= 0) {
		str = (char) (65 + index % 26) + str;
	}
	return str;
  }

  //2008-3-7 add by fengbo
  //将后台返回的查询数据拆分为多个数据集分别打印；
  //譬如：往来对帐单打印。
  /**
   * @param Param Object            XML文件存放路径
   * @param Data Object             为空，不用
   * @param CustomObject Object     PO
   * @param AdditiveObject Object   String[] //0:有余额打印;1:无余额也打;2:固定列;3:变动列;4:开始页号;5:是否换页;
   * @return Object
   */
  //------------------------------------------------------------------------------------------------
  public Object QueryObjectDisposePrint(Object Param, Object Data,
                                        Object CustomObject,
                                        Object AdditiveObject) {
    String QueryStub = (String) Param;
    JParamObject PO = (JParamObject) CustomObject;

    AccountPrintFormatParam AccountFormat = (AccountPrintFormatParam)
        AdditiveObject;
    if (Param == null) {
      return null;
    }
    Object EO = null;
    String Path = QueryStub;
    JQueryStubObject QO = null;
    try {
      Path = Path.substring(0, Path.lastIndexOf("/") + 1);
      if (!Path.endsWith("/")) {
        Path += "/";
        // 准备阶段
      }
      //2007-11-13 YRH 加上语言路径
      if (QueryStub.indexOf("/" + JActiveDComDM.International + "/") < 0) {
        QueryStub = QueryStub.replaceFirst(QueryStub.substring(QueryStub.
            lastIndexOf("/")), "/" + JActiveDComDM.International + "/") +
            QueryStub.substring(QueryStub.
                                lastIndexOf("/") + 1);
      }

      JXMLBaseObject XMLObject = getQueryObject(QueryStub);
      //如果找不到，则反回原值再找
      if (XMLObject == null) {
        QueryStub = (String) Param;
        XMLObject = getQueryObject(QueryStub);
        if (XMLObject == null) {
          return null;
        }
      }
      else {
        Param = QueryStub;
      }
      //END 2007-11-13 YRH

      QO = getQueryStubObject(XMLObject, Path);

      QO.Param = Param;
      QO.Data = Data;
      if (QO == null) {
        return null;
      }
      // 处理前缀阶段
      if (PO != null) {
        processWait(QO);
        // 显示Wait
        //showWaiting(QO);
        // 显示信息
        getQueryWait(QO).setTitle1(res.getString("String_69") + QO.CaptionText +
                                   (
                                       QO.CaptionText.endsWith(res.getString(
                                           "String_70")) ?
                                       "" : res.getString("String_72")) +
                                   res.getString("String_73"));

        JActiveDComDM.MainApplication.BeginWaitCursor();
        // 处理组织阶段
        getQueryWait(QO).setTitle2(res.getString("String_74"));

        JResponseObject RO = processOrganizeQuery(QO, PO);
        if (RO.ErrorCode == 0) {
          // 处理实体阶段
          getQueryWait(QO).setTitle2(res.getString("String_75"));
          JGenerQueryWindow QueryWindow = (JGenerQueryWindow)
              processEntityQueryPrint(QO, PO, RO);
          String className = PO.GetValueByParamName("PRINT_BILL_IMPL");
          IQueryDecomposePrint printObject =
              (IQueryDecomposePrint) Class.forName(className).newInstance();
          printObject.printBill(this, PO, QO, QueryWindow, AccountFormat);
        }
        else {
          JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.
                                        MainWindow, RO.ErrorString,
                                        res.getString("String_89"),
                                        JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,
                                    e.getMessage(), res.getString("String_90"),
                                    JOptionPane.ERROR_MESSAGE);
    }
    finally {
      JActiveDComDM.MainApplication.EndWaitCursor();
    }
    return EO;
  }

  /**
   * add by fengbo 2007-12-11
   * 借用正式帐页打印程序，打印不需要显示查询结果，直接打印的情况；
   * 譬如：内部存款余额调节表；
   * @param Param Object
   * @param Data Object
   * @param CustomObject Object
   * @param AdditiveObject Object
   * @return Object
   */
  public Object QueryObjectDerictPrint(Object Param, Object Data,
                                       Object CustomObject,
                                       Object AdditiveObject) {

    JParamObject PO = null;

    AccountPrintFormatParam AccountFormat = (AccountPrintFormatParam)
        CustomObject;
    int FixCols = AccountFormat.Fixcol; // 固定列 如果是三栏账设为0
    //因为只有账页打印才调用此方法；账页都有会计期间隐藏列，所以 特殊处理账页隐藏的‘会计期间’列
    if (FixCols >= 2) {
      FixCols++;
    }
    int ValCols = AccountFormat.Valcol; // 变动列 如果是三栏账设为0
    int StartPageNo = AccountFormat.SaterPageNO; // 开始页号
    int Style = AccountFormat.ChangePage; // 0:新科目换页 1:不换
    int PageRows = AccountFormat.PageRows; //每页行数
    String Type = AccountFormat.Type; //账页类型

    Object EO = null;
    try {
      PO = (JParamObject) Data;
      JResponseObject RO = (JResponseObject) AdditiveObject;
      JQueryStubObject QO = new JQueryStubObject();
      QO.EntityQueryClass =
          "jservice.jbof.classes.GenerQueryObject.JGenerQueryWindow";
      QO.CaptionText = "";
      JGenerQueryWindow QueryWindow = (JGenerQueryWindow)
          directProcessQueryEntity(QO, PO, RO);
      //打印
      JBook Book = QueryWindow.vwQueryView.getDataReport().getBook();
      TableManager tableManager = QueryWindow.vwQueryView.getTableManager();
      int Rows = Book.getLastRow() + 1;
      int FixedRows = Book.getBookModel().getFixedRows();
      //无发生、无余额不打印,并且不是多栏账
      if (AccountFormat.YePrint && !AccountFormat.WePrint &&
          !Type.startsWith("4")) {
        if (Rows == FixedRows) { //只有表头
          return EO;
        }
        else if (Rows == FixedRows + 1) { //只有一行上期结转
          String vsDqye = "";
          //2009-03-22现在各种账式的金额列都在最后
//          if (AccountFormat.Format.equals("100")) {
            vsDqye = Book.getText(Book.getLastRow(), Book.getLastCol());
//          }
//          else {
//            vsDqye = Book.getText(Book.getLastRow(), Book.getLastCol() - 1);
//          }
          if ("".equals(vsDqye) || vsDqye == null) {
            vsDqye = "0";
          }
          double vdYe = Double.parseDouble(vsDqye);
          if (vdYe == 0) {
            return EO;
          }
        }
      }
      // 打印
      if (Book.getSheet() == 0) {
        insertPrintSheet(Book, 1);
        StubObject[] datas = new StubObject[1];
        datas[0] = new StubObject();
        datas[0].setObject("dataManager",
                           QueryWindow.vwQueryView.getQueryDataSet());
        CustomPrint printObject = new CustomPrintObject(PO, QO, Book,
            AccountFormat);
        Object printArray = AccountPrint.printAccountBook3(Book,
            tableManager, datas, printObject, 0, FixCols, ValCols,
            StartPageNo, Style, 3, PageRows, true, AccountFormat.FillPage);
        Object[] PrintArray = (Object[]) printArray;
        DrawObject2 printResult = (DrawObject2) PrintArray[2];
        //起始页号
        Object Key = "startPageNo", Default = "0";
        Object startPageNo = datas[0].getObject(Key, Default);
        //页数
        int PageCount = printResult.getVPageCount();
        EO = (Object) String.valueOf(PageCount);
      }
      //打印方向
      if (PO.GetValueByParamName("PrintLandscape", "0").trim().equals("1")) {
        Book.setPrintLandscape(true);
      }
      //打印
      if (PO.GetValueByParamName("EXCEL", "").trim().equals("0")) {
        this.Print(Book, QO,
                   PO.GetValueByParamName("ZDYH").trim().equals("1"));
      }
      else {
        this.saveToExcel(Book, QO);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,
                                    e.getMessage(), res.getString("String_90"),
                                    JOptionPane.ERROR_MESSAGE);
    }
    finally {
      JActiveDComDM.MainApplication.EndWaitCursor();
    }
    return EO;
  }

  /**
   * 插入一个Sheet
   * @param Book JBook
   * @param SheetIndex int
   */
  public void insertPrintSheet(JBook Book, int SheetIndex) {
    try {
      int SheetCount = Book.getBook().getSheetCount();
      if (SheetIndex >= SheetCount) { // 如果不存在这个Sheet
        Book.insertSheets(SheetCount, 1);
      }
      Book.setSheet(SheetIndex);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * 打印
   * @param Book JBook
   * @param QueryStubObject JQueryStubObject
   */
  public void Print(JBook Book, JQueryStubObject QueryStubObject, boolean Show) {
    try {
      //取出默认的打印机
      PrintService Printers;
      Printers = PrinterJob.getPrinterJob().getPrintService();

      PrintService PS = Printers;
      PrinterJob Job = PrinterJob.getPrinterJob();
      Job.setJobName(QueryStubObject.CaptionText);
      Job.setPrintService(PS);
      Book.filePrint(Show, Job);
//    	PrinterJob  printerJob=PrinterJob.getPrinterJob();
//    	boolean boo=printerJob.printDialog() ;
//    	if(boo==true){
//    		Book.filePrint(Show, printerJob);
//    	}

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 输出到Excel
   */
  public void saveToExcel(JBook Book, JQueryStubObject QueryStubObject) {
    try {
      if (Book != null) {
        JBookActionImpl actionImpl = new JGenerQueryActionImpl();
        JBookActionManager actionMgr = new JBookActionManager(actionImpl);
        String actionName = JBookActionConstants.ACTION_EXPORT;
        String defaultName = QueryStubObject.CaptionText;
        JBookAction action = new JBookAction(Book, actionName, defaultName);
        actionMgr.execute(action);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object QueryObject(Object Param, Object Data, Object CustomObject, Object AdditiveObject) {
    //2007-12-20 add by fengbo
    final String oldPath = (String) Param;
    String QueryStub = (String) Param;

    if (Param == null) {
      return null;
    }

    final Object EO = null;
    String xmlFile = QueryStub;
    String Path = QueryStub; // /Resource/JCost/Query/fsxhzcx.xml ->/Resource/JCost/Query/
//    final JQueryStubObject QO = null;
    try {
      Path = Path.substring(0, Path.lastIndexOf("/") + 1);
      if (!Path.endsWith("/")) {
        Path += "/";
        // 准备阶段
      }
      //2007-11-13 YRH 加上语言路径
      if (QueryStub.indexOf("/" + JActiveDComDM.International + "/") < 0) {
        QueryStub = QueryStub.replaceFirst(QueryStub.substring(QueryStub.
            lastIndexOf("/")), "/" + JActiveDComDM.International + "/") +
            QueryStub.substring(QueryStub.
                                lastIndexOf("/") + 1);
      }

      JXMLBaseObject XMLObject = getQueryObject(QueryStub);
      //如果找不到，则反回原值再找
      if (XMLObject == null) {
        QueryStub = (String) Param;
        XMLObject = getQueryObject(QueryStub);
        if (XMLObject == null) {
          return null;
        }
      }
      else {
        xmlFile = QueryStub;
        Param = QueryStub;
      }
      //END 2007-11-13 YRH

      final JQueryStubObject QO = getQueryStubObject(XMLObject, Path);

      QO.Param = Param;
      QO.Data = Data;
      QO.CustomObject = CustomObject;
      //处理服务名
      com.core.xml.StubObject stub = (com.core.xml.StubObject) CustomObject;
      String serverName = stub.getString("ServerName", "");
      String datafromecc = stub.getString("DataFromEcc","0");
      if (!serverName.equals(""))
        QO.ServerName = serverName;
      //是否从ECC6取数
      if (datafromecc.equals("1"))
        QO.isECC6 = true;

      if (QO == null) {
        return null;
      }
      // 处理前缀阶段
      final JParamObject PO = processPrefixQuery(QO);
      if ( PO == null ) return null;

      if (!serverName.equals(""))
        PO.SetValueByParamName("Server", serverName);

//      if (QO.ExteriorQueryClass!=null){
//        int Option = JOptionPane.showConfirmDialog(null, "是否从ECC6取数？", "系统提示",
//                                      JOptionPane.YES_NO_CANCEL_OPTION);
//        if (Option==JOptionPane.YES_OPTION){
//          QO.isECC6=true;
//        }else if(Option==JOptionPane.NO_OPTION){
//          QO.isECC6 = false;
//        }else{
//          return null;
//        }
//      }
      Runnable run = new Runnable() {
        public void run() {
          try {
            queryEntityData(oldPath, EO, QO, PO);
          } catch ( Exception ex ) {
            ex.printStackTrace();
          }
        }
      };
      WaitingManager.getDefault().waitInvoke(QO.CaptionText,"正在打开节点" + QO.CaptionText,QO.EntityIconURL,run);
    }
    catch (Exception e) {
//      hideWaiting(QO);
      e.printStackTrace();
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,
                                    e.getMessage(), res.getString("String_116"),
                                    JOptionPane.ERROR_MESSAGE);
    }
    finally {
      JActiveDComDM.MainApplication.EndWaitCursor();
//      hideWaiting(QO);
    }
    return EO;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ZhangES(2014.11.19)
  //实现: ZhangES
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object QueryObjectNoDialog(Object Param, Object Data, Object CustomObject, Object AdditiveObject) {
    final String oldPath = (String) Param;
    String QueryStub = (String) Param;

    if (Param == null) {
      return null;
    }

    final Object EO = null;
    String xmlFile = QueryStub;
    String Path = QueryStub; // /Resource/JCost/Query/fsxhzcx.xml ->/Resource/JCost/Query/
    try {
      Path = Path.substring(0, Path.lastIndexOf("/") + 1);
      if (!Path.endsWith("/")) {
        Path += "/";
        // 准备阶段
      }
      //2007-11-13 YRH 加上语言路径
      if (QueryStub.indexOf("/" + JActiveDComDM.International + "/") < 0) {
        QueryStub = QueryStub.replaceFirst(QueryStub.substring(QueryStub.
            lastIndexOf("/")), "/" + JActiveDComDM.International + "/") +
            QueryStub.substring(QueryStub.
                                lastIndexOf("/") + 1);
      }

      JXMLBaseObject XMLObject = getQueryObject(QueryStub);
      //如果找不到，则反回原值再找
      if (XMLObject == null) {
        QueryStub = (String) Param;
        XMLObject = getQueryObject(QueryStub);
        if (XMLObject == null) {
          return null;
        }
      }
      else {
        xmlFile = QueryStub;
        Param = QueryStub;
      }
      //END 2007-11-13 YRH

      final JQueryStubObject QO = getQueryStubObject(XMLObject, Path);

      QO.Param = Param;
      QO.Data = Data;
      QO.CustomObject = CustomObject;
      //处理服务名
      com.core.xml.StubObject stub = (com.core.xml.StubObject) CustomObject;
      String serverName = stub.getString("ServerName", "");
      String datafromecc = stub.getString("DataFromEcc","0");
      if (!serverName.equals(""))
        QO.ServerName = serverName;
      	//是否从ECC6取数
      if (datafromecc.equals("1"))
        QO.isECC6 = true;

      if (QO == null) {
        return null;
      }
      // 处理前缀阶段
      final JParamObject PO = (JParamObject) Data;
      if ( PO == null ) return null;

      if (!serverName.equals(""))
        PO.SetValueByParamName("Server", serverName);
      Runnable run = new Runnable() {
        public void run() {
          try {
            queryEntityData(oldPath, EO, QO, PO);
          } catch ( Exception ex ) {
            ex.printStackTrace();
          }
        }
      };
      WaitingManager.getDefault().waitInvoke(QO.CaptionText,"正在打开节点" + QO.CaptionText,QO.EntityIconURL,run);
    }
    catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, e.getMessage(), res.getString("String_116"), JOptionPane.ERROR_MESSAGE);
    }
    finally {
      JActiveDComDM.MainApplication.EndWaitCursor();
    }
    return EO;
  }
  
  private Object queryEntityData(String oldPath, Object EO, JQueryStubObject QO,
                                 JParamObject PO) throws Exception {
    if (PO != null) {
      // 生成Wait
//        processWait(QO);
      // 显示Wait
//        showWaiting(QO);
      // 显示信息
      WaitingManager.getDefault().setWaitMessage(res.getString("String_100") + QO.CaptionText +
                                   (
                                       QO.CaptionText.endsWith(res.getString(
                                           "String_101")) ?
                                       "" : res.getString("String_103")) +
                                   res.getString("String_104"),"",null);
      JActiveDComDM.MainApplication.BeginWaitCursor();
      // 处理组织阶段
      WaitingManager.getDefault().setWaitMessage("",res.getString("String_105"),null);
      System.out.println(res.getString("String_106"));
      long s = System.currentTimeMillis();
      JResponseObject RO = processOrganizeQuery(QO, PO);
      if (RO.ErrorCode == 0) {
        //显示自定义提示信息
        if (RO.ErrorString!=null && !"".equals(RO.ErrorString)){
          JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.
                                        MainWindow, RO.ErrorString,
                                        res.getString("String_113"),
                                        JOptionPane.ERROR_MESSAGE);
        }
        // 处理实体阶段
        WaitingManager.getDefault().setWaitMessage("",res.getString("String_107"),null);
        EO = processEntityQuery(QO, PO, RO);

        //分页查询且有后继数据页时,继续下一次查询 2007-7-18 fengbo
        PO = (JParamObject) EO;
        if (PO.GetValueByParamName(GlobalDataObject.__MULTI_PAGE_QUERY).
            equals(GlobalDataObject.__MULTI_PAGE_QUERY)
            && !PO.GetValueByParamName("EX_DATE", "").equals("")) {
          JActiveDComDM.BusinessActiveFramework.
              MInvokeObjectMethod("GenerQueryObject", "queryDataObject",
                                  oldPath, PO, "",null);
        }

      }
      else {
        hideWaiting(QO);
        JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.
                                      MainWindow, RO.ErrorString,
                                      res.getString("String_113"),
                                      JOptionPane.ERROR_MESSAGE);
      }
      long e = System.currentTimeMillis();
      Debug.PrintlnMessageToSystem(res.getString("String_114") + (e - s) +
                                   res.getString("String_115"));
    }
    return EO;
  }

  public JParamObject processOLDPrefixQuery(JQueryStubObject QO) throws
      Exception {
    JParamObject PO = null;
    Class PrefixClass = Class.forName(QO.PrefixQueryClass);
    Constructor ContArray[] = PrefixClass.getConstructors();
    if (ContArray == null) {
      return PO;
    }
    Constructor Cont = null;
    for (int i = 0; i < ContArray.length; i++) {
      Cont = ContArray[i];
      Class cls[] = Cont.getParameterTypes();
      if (cls.length == 3) {
        PO = processOLDDialog(QO, PrefixClass, Cont);
        break;
      }
    }
    return PO;
  }

  JParamObject processOLDDialog(JQueryStubObject QO, Class cls,
                                Constructor Cont) throws Exception {
    Object Params[] = {
        JActiveDComDM.MainApplication.MainWindow, QO.CaptionText, new Boolean(true)};
    Object PrefixObject = Cont.newInstance(Params);

    JFrameDialog Dlg = (JFrameDialog) PrefixObject;
//    Dlg.setCustomObject(QO);
    Dlg.CenterWindow();
    Dlg.Show();

    if (Dlg.Result==Dlg.RESULT_OK){
      QO.CustomObject = Dlg.getCustomObject();
      return Dlg.PO;
    }
    return null;
  }

  //处理前缀阶段
  public JParamObject processPrefixQuery(JQueryStubObject QO) throws Exception {
    // 处理老的查询方式
    if ("1.0".equals(QO.QueryVersion)) {
      return processOLDPrefixQuery(QO);
    }
    JFrameDialog Dlg = null;
    //2006-12-08
    try {
      Constructor constructor = Class.forName(QO.PrefixQueryClass).
          getConstructor(new Class[] {JQueryStubObject.class});
      Dlg = (JFrameDialog) constructor.newInstance(new Object[] {QO});
    }
    catch (Exception ex) {
      //ex.printStackTrace();
      try {
        Object PrefixObject = Class.forName(QO.PrefixQueryClass).newInstance();
        Dlg = (JFrameDialog) PrefixObject;
        Dlg.setCustomObject(QO);
        Dlg.PO = getServerPO();
      }
      catch (Exception EEEE) {
        EEEE.printStackTrace();
      }
    }
    finally {
      Dlg.CenterWindow();
      Dlg.Show();
      if (Dlg.Result == Dlg.RESULT_OK) {
        QO.CustomObject = Dlg.getCustomObject();
        return Dlg.PO;
      }
      return null;
    }
  }

  // 处理后台数据组织阶段
  public JResponseObject processOrganizeQuery(JQueryStubObject QO,
                                              JParamObject PO) throws Exception {
    JResponseObject RO = null;
    if (QO.QueryBmStruct != null) {
      PO.SetValueByEnvName("QUERY_BM_STRUCT", QO.QueryBmStruct);
    }
    RO = (JResponseObject) EAI.DAL.IOM("GenerQueryObject", "QueryObject", (Object) PO, (Object) QO);
    return RO;
  }

  // 处理前台数据展示阶段
  public Object processEntityQuery(JQueryStubObject QO, JParamObject PO,
                                   JResponseObject RO) throws Exception {
    JBOFChildWindow QueryWindow = null;
    // 设置返回对象
    QO.RO = RO;
    // 创建查询窗口
    WaitingManager.getDefault().setWaitMessage("",res.getString("String_122"),null);
    boolean showTree = PO.GetValueByParamName("showTree", "").equals("1")? true:false;
    if(!showTree) {
    	QueryWindow = (JBOFChildWindow) Class.forName(QO.EntityQueryClass).
    			newInstance();
    } else {
    	Constructor constructor = Class.forName(QO.EntityQueryClass).
    	          getConstructor(new Class[] {JQueryStubObject.class, JParamObject.class});
    	QueryWindow = (JBOFChildWindow)constructor.newInstance(new Object[] {QO, PO});
    }
    // 设置应用
    QueryWindow.setApplication(JActiveDComDM.MainApplication);
    // 设置ActiveObject
    QueryWindow.setActiveObject(this);
    // 初始化
    WaitingManager.getDefault().setWaitMessage("",res.getString("String_123"),null);
    QueryWindow.InitChildWindow(JActiveDComDM.MainApplication,
                                JActiveDComDM.MainApplication.MainWindow, QO,
                                PO);
    String hideName = PO.GetValueByParamName("hideMenuName", "");
    if(hideName != null && hideName.length() != 0) {
    	String[] hideArray = hideName.split(",");
    	JGenerQueryWindow qw = (JGenerQueryWindow)QueryWindow;
    	if(hideArray != null) {
    		for(int i = 0; i < hideArray.length; i++) {
    			qw.setMenuVisible(hideArray[i], false);
    		}
    	}
    }
    // 打开
    WaitingManager.getDefault().setWaitMessage("",res.getString("String_124"),null);
    JActiveDComDM.MainApplication.OpenObjectWindow(QO.CaptionText,
        QO.EntityIconURL, QO.CaptionText, QueryWindow);

    //获取新PO 2007-7-18 fengbo
    if (QueryWindow instanceof JGenerQueryWindow) {
      PO = ( (JGenerQueryWindow) QueryWindow).PO;
    }
    return PO;
  }

  /**
   * add by fengbo 2007-11-21
   * 用于不经过后台处理，在前台直接以查询方式展示数据。
   */
  public void directProcessQuery(Object Param, Object Data, Object CustomObject,
                                 Object AdditiveObject) {
    try {
      JParamObject PO = (JParamObject) Data;
      JResponseObject RO = (JResponseObject) AdditiveObject;
      JQueryStubObject QO = new JQueryStubObject();
      //默认用jservice.jbof.classes.GenerQueryObject.JGenerQueryWindow 展示查询结果
      QO.EntityQueryClass = PO.GetValueByParamName("ShowWindow",
          "jservice.jbof.classes.GenerQueryObject.JGenerQueryWindow");
      QO.CaptionText = PO.GetValueByParamName("WindowCaption", "");
      QO.Param = PO.GetValueByParamName("QO.PARAM");
      JBOFChildWindow QueryWindow = directProcessQueryEntity(QO, PO, RO);
      JActiveDComDM.MainApplication.OpenObjectWindow(QO.CaptionText,
          QO.EntityIconURL, QO.CaptionText, QueryWindow);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * add by fengbo 2007-11-21
   * 用于不经过后台处理，在前台直接展示数据。
   * @param QO JQueryStubObject
   * @param PO JParamObject
   * @param RO JResponseObject
   * @return Object
   * @throws Exception
   */
  private JBOFChildWindow directProcessQueryEntity(JQueryStubObject QO,
      JParamObject PO,
      JResponseObject RO) throws Exception {

    // 设置返回对象
    QO.RO = RO;
    // 创建查询窗口
    JBOFChildWindow QueryWindow = (JBOFChildWindow) Class.forName(QO.
        EntityQueryClass).
        newInstance();
    // 设置应用
    QueryWindow.setApplication(JActiveDComDM.MainApplication);
    // 设置ActiveObject
    QueryWindow.setActiveObject(this);
    // 初始化
    QueryWindow.InitChildWindow(JActiveDComDM.MainApplication,
                                JActiveDComDM.MainApplication.MainWindow, QO,
                                PO);
    return QueryWindow;
  }

  // 处理实体阶段为(正式账打印而改造)
  public Object processEntityQueryPrint(JQueryStubObject QO, JParamObject PO,
                                        JResponseObject RO) throws Exception {
    JBOFChildWindow QueryWindow = null;
    try {
      //设置
      PO.SetValueByParamName("IS_LEDGER_PRINT", "1");
      // 设置返回对象
      QO.RO = RO;
      // 创建查询窗口
//      getQueryWait(QO).setTitle2(res.getString("String_127"));
      QueryWindow = (JBOFChildWindow) Class.forName(QO.EntityQueryClass).
          newInstance();
      // 设置应用
      QueryWindow.setApplication(JActiveDComDM.MainApplication);
      // 设置ActiveObject
      QueryWindow.setActiveObject(this);
      // 初始化
//      getQueryWait(QO).setTitle2(res.getString("String_128"));
      QueryWindow.InitChildWindow(JActiveDComDM.MainApplication,
                                  JActiveDComDM.MainApplication.MainWindow, QO,
                                  PO);
      //把显示部分去掉
//    getQueryWait(QO).setTitle2("显示查询数据...");
//    JActiveDComDM.MainApplication.OpenObjectWindow(QO.CaptionText, QO.EntityIconURL, QO.CaptionText, QueryWindow);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return QueryWindow;
  }

  // 处理前台数据展示阶段
  public Object[] processEntityQueryNoShow(JQueryStubObject QO, JParamObject PO,
                                   JResponseObject RO) throws Exception {
    JBOFChildWindow QueryWindow = null;
    // 设置返回对象
    QO.RO = RO;
    QueryWindow = (JBOFChildWindow) Class.forName(QO.EntityQueryClass).
        newInstance();
    // 设置应用
    QueryWindow.setApplication(JActiveDComDM.MainApplication);
    // 设置ActiveObject
    QueryWindow.setActiveObject(this);
    QueryWindow.InitChildWindow(JActiveDComDM.MainApplication,
                                JActiveDComDM.MainApplication.MainWindow, QO,
                                PO);
//    JActiveDComDM.MainApplication.OpenObjectWindow(QO.CaptionText,
//        QO.EntityIconURL, QO.CaptionText, QueryWindow);

    //获取新PO 2007-7-18 fengbo
    if (QueryWindow instanceof JGenerQueryWindow) {
      PO = ( (JGenerQueryWindow) QueryWindow).PO;
    }
    Object [] objs=new Object[2];
    objs[0]=PO;
    objs[1]=QueryWindow;
    return objs;
  }


  private void processEQO(Hashtable ha, JXMLBaseObject XMLObject, Element e) {
    java.util.List alist = e.getAttributes();
    if (alist != null) {
      for (int i = 0; i < alist.size(); i++) {
        Attribute at = (Attribute) alist.get(i);
        ha.put(at.getName(), at.getValue());
      }
    }
  }

  JQueryStubObject getQueryStubObject(JXMLBaseObject XMLObject, String Path) {
    JQueryStubObject QueryStubObject = new JQueryStubObject();
    try {

      QueryStubObject.Path = Path;
      Element e = XMLObject.Root;
      QueryStubObject.QueryVersion = XMLObject.GetElementValue(e, "ver", null);
      e = XMLObject.GetElementByName("QueryCaption");
      QueryStubObject.CaptionID = XMLObject.GetElementValue(e, "id", null);
      QueryStubObject.CaptionText = XMLObject.GetElementValue(e, "text", null);
      QueryStubObject.CaptionType = XMLObject.GetElementValue(e,"type",null);
      //前缀部分
      e = XMLObject.GetElementByName("PrefixQuery");
      QueryStubObject.PrefixQueryClass = XMLObject.GetElementValue(e, "class", null);
      QueryStubObject.PrefixQueryXML = XMLObject.GetElementValue(e, "xml", null);
      QueryStubObject.PrefixMethod = XMLObject.GetElementValue(e, "method", null);
      QueryStubObject.PrefixParam = XMLObject.GetElementValue(e, "param", null);
      //数据组织
      e = XMLObject.GetElementByName("OrganizeQuery");
      QueryStubObject.OrganizeQueryClass = XMLObject.GetElementValue(e, "class", null);
      QueryStubObject.OrganizeQueryXML = XMLObject.GetElementValue(e, "xml", null);
      QueryStubObject.OrganizeMethod = XMLObject.GetElementValue(e, "method", null);
      QueryStubObject.ReclaimerMethod = XMLObject.GetElementValue(e, "reclaimer", null);
      QueryStubObject.QueryBmStruct = XMLObject.GetElementValue(e, "bmstruct", null);
      //实体
      e = XMLObject.GetElementByName("EntityQuery");
      processEQO(QueryStubObject.EntityQuery, XMLObject, e);
      QueryStubObject.EntityQueryClass = XMLObject.GetElementValue(e, "class", null);
      QueryStubObject.EntityQueryXML = XMLObject.GetElementValue(e, "xml", null);
      QueryStubObject.ViewCustomClass = XMLObject.GetElementValue(e,
          "viewclass", null);
      QueryStubObject.ViewCustomObject = null;
      QueryStubObject.EntityQueryXMLURL = JXMLResource.LoadSXML(Path +
          "EntityQuery/", QueryStubObject.EntityQueryXML);
      QueryStubObject.EntityIcon = XMLObject.GetElementValue(e, "entityicon", null);
      QueryStubObject.EntityIconURL = JXMLResource.LoadSImageIcon(Path +
          "EntityQuery/", QueryStubObject.EntityIcon);
      //后缀
      e = XMLObject.GetElementByName("PostfixQuery");
      QueryStubObject.PostfixQueryClass = XMLObject.GetElementValue(e, "class", null);
      QueryStubObject.PostfixQueryXML = XMLObject.GetElementValue(e, "xml", null);
      //定制
      e = XMLObject.GetElementByName("CustomQuery");
      QueryStubObject.CustomQueryClass = XMLObject.GetElementValue(e, "class", null);
      QueryStubObject.CustomQueryXML = XMLObject.GetElementValue(e, "xml", null);
      //外部数据
      e = XMLObject.GetElementByName("ExteriorQuery");
      if (e!=null){
        QueryStubObject.ExteriorQueryClass = XMLObject.GetElementValue(e,
            "class", null);
        QueryStubObject.ExteriorMethod = XMLObject.GetElementValue(e, "method", null);
        if (QueryStubObject.ExteriorList == null)
          QueryStubObject.ExteriorList = new ArrayList();
        List list = e.getChildren();
        ExteriorQueryStru Stru;
        for (int i = 0; i < list.size(); i++) {
          Stru = new ExteriorQueryStru();
          Element em = (Element) list.get(i);
          Stru.addField(em.getAttributeValue("field", ""));
          Stru.addTable(em.getAttributeValue("table", ""));
          Stru.Where = em.getAttributeValue("where", "");
          Stru.Group = em.getAttributeValue("group", "");
          Stru.Having = em.getAttributeValue("having", "");
          Stru.Order = em.getAttributeValue("order", "");
          QueryStubObject.ExteriorList.add(Stru);
        }
      }
      //深层加工
      e = XMLObject.GetElementByName("DeepQuery");
      QueryStubObject.DeepQueryClass = XMLObject.GetElementValue(e,"class",null);
      QueryStubObject.DeepMethod = XMLObject.GetElementValue(e,"method",null);
      //绘制器
      e = XMLObject.GetElementByName("PainterQuery");
      QueryStubObject.PainterQueryClass = XMLObject.GetElementValue(e,"class",null);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return QueryStubObject;
  }

  JXMLBaseObject getQueryObject(String QueryStub) {
    JXMLBaseObject XMLObject = null;
    String Path;
    Path = "/Resource/" + QueryStub;
    URL url = this.getClass().getResource(Path);
    if (url != null) {
      XMLObject = new JXMLBaseObject(url);
    }
    return XMLObject;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object AnalyzeObject(Object Param, Object Data, Object CustomObject,
                              Object AdditiveObject) {
    return null;
  }

  /**
   * 获得PO，默认为当前的PO，由子类继承去现实获得不同服务器的PO
   * qupengfei 2009.06.29
   */
  protected JParamObject getServerPO() {
    JParamObject PO = JParamObject.Create();
    return PO;

  }
  /*
      try {
        //Application.MainWindow.BeginWaitCursor();
        QO.RO = RO;
        PO.SetValueByParamName("ModelClassName","jservice.jbof.classes.GenerQueryObject.JQueryReportModel");
        ReportWindow = (JBOFChildWindow)Class.forName("jreport.jdof.classes.DOFReportObject.JReportWindow").newInstance();
        ReportWindow.LoadWindowIcon();
        ReportWindow.setApplication(JActiveDComDM.MainApplication);
        // 设置ActiveObject
        ReportWindow.setActiveObject(this);
        ReportWindow.InitChildWindow(JActiveDComDM.MainApplication,JActiveDComDM.MainApplication.MainWindow,QO,PO);
        JActiveDComDM.MainApplication.OpenObjectWindow("新建报表",ReportWindow.WindowIcon,"新建报表",ReportWindow);
        JBOFClass.CallObjectMethod(ReportWindow,"InitWindow");
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        //Application.MainWindow.EndWaitCursor();
      }
   */
}

class WaitClass
    implements Runnable {
  JWaitDialog WaitDialog = null;
  public WaitClass(JWaitDialog wd) {
    WaitDialog = wd;
  }

  public void run() {
    WaitDialog.CenterWindow();
    WaitDialog.setModal(true);
    WaitDialog.show();
  }
}
