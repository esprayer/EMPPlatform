package jservice.jbof.classes.GenerQueryObject;

import java.net.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.openide.WaitingManager;

import com.core.xml.JBOFClass;
import com.core.xml.StubObject;
import com.efounder.dbc.swing.tree.DataSetTreeNode;
import com.efounder.dbc.swing.tree.DictTree;
import com.efounder.dctbuilder.loader.DefaultDctProvider;
import com.efounder.dctbuilder.loader.DefaultResolver;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.f1j.ss.*;
import com.f1j.swing.*;
import jbof.application.classes.*;
import jbof.gui.window.classes.*;
import jbof.gui.window.classes.style.mdi.*;
import jbof.gui.window.classes.style.mdi.imp.*;
import jcomponent.custom.swing.*;
import jfoundation.bridge.classes.*;
import jframework.resource.classes.*;
import jservice.jbof.classes.GenerQueryObject.action.*;
import jservice.jbof.classes.GenerQueryObject.print.*;
import jdatareport.dof.classes.report.QueryDataTransformer;
import jdatareport.dof.classes.report.QueryDataSet;
import com.report.table.jxml.TableManager;
import com.print.JF1PageSets;
import com.print.service.AccountPrintFormatParam;
import com.print.service.CustomPrint;
import com.print.service.CustomPrintObject;
import com.print.service.AccountPrint;
import java.util.*;
import com.report.table.jxml.*;
import com.pub.util.Debug;
import jservice.jbof.classes.GenerQueryObject.print.JPrintPageColumnSetDlg;
import jframework.foundation.classes.JActiveDComDM;
import jframework.foundation.classes.JFwkActiveObjectStub;

import java.io.FileOutputStream;
import java.io.File;
import jdatareport.dof.classes.report.util.JDRConstants;
import jdatareport.dof.classes.report.paint.JDRQueryPainter;
import jdatareport.dof.classes.report.paint.JDRQueryPainterUtils;
import jdatareport.dof.classes.report.util.JDRUtils;
import com.f1j.util.*;
import jfoundation.application.classes.*;
import jfoundation.gui.window.classes.JMainWindow;
import jdatareport.dof.classes.report.paint.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JGenerQueryWindow
    extends JBOFMDIChildWindow implements ActionListener,MouseListener, SelectionChangedListener {


  public MultiLevelHeaderTreeTable vwQueryView = new MultiLevelHeaderTreeTable();
//  private DictTreeView dictTreeView = null;
  public JParamObject PO;
  public JQueryStubObject QO;
  private java.util.Hashtable QueryValue = new java.util.Hashtable();
  public String[] QueryObject = null;
  public QueryDataSet monthQueryDataSet;
  public QueryDataSet yearQueryDataSet;
  public TableManager monthTableManager;
  public TableManager yearTableManager;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JBOFToolBar tbMainToolbar = new JBOFToolBar(1);
  private JBOFToolBar tbFormatToolbar = new JBOFToolBar(2);

  private JFontComboBox cbFont = new JFontComboBox();
  private JComboBox cbFontSize = new JComboBox();
  private JComboBox cbViewScale = new JComboBox();

  private JBOFToolBar queryFormatToolbar = new JBOFToolBar(2);
  private JButton chartButtom = new JButton("联查图表");
  /**
   * 图表格式对象
   */
  private Object chartFormatObject=null;

  /**
   * 多个查询结果格式数组
   */
  public TableManager[] tableManagers = null;
  /**
   * 多个查询结果数据数组
   */
  public QueryDataSet[] queryDataSets = null;

  /**
   * 构造器
   */
  public JGenerQueryWindow() {
    try {
      jbInit();
      vwQueryView.getDataReport();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public JGenerQueryWindow(JQueryStubObject QO, JParamObject PO) {
	  this.QO = QO;
	  this.PO = PO;
	  try {
	      jbInit();
	      vwQueryView.getDataReport();
	  }
	  catch (Exception ex) {
	      ex.printStackTrace();
	  }
  }
  
  public Object getValue(Object name, Object def) {
    Object res = def;
    res = QueryValue.get(name);
    if (res == null) {
      res = def;
    }
    return res;
  }

  public void putValue(Object name, Object v) {
    QueryValue.put(name, v);
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jPanel1.setLayout(borderLayout2);
    jPanel1.add(vwQueryView, BorderLayout.CENTER);
    boolean showTree = false;
    if(PO != null) {
    	showTree = this.PO.GetValueByParamName("showTree", "").equals("1")? true:false;
    }
    if(!showTree) {
    	this.add(jPanel1, BorderLayout.CENTER);
    } else {
    	JSplitPane split = new JSplitPane();
    	initDictTreeView();
//    	split.setLeftComponent(dictTreeView);
    	split.setRightComponent(jPanel1);
    	split.setDividerLocation(300);
    	this.add(split, BorderLayout.CENTER);
    }

    tbMainToolbar.setToolBarID("ScaleView");
    tbMainToolbar.setName("可视比例工具条");
    tbFormatToolbar.setToolBarID("FontToolbar");
    tbFormatToolbar.setName("字体控制工具条");
    cbViewScale.setPreferredSize(new Dimension(65, 25));
    cbViewScale.setEditable(true);
    cbFontSize.setPreferredSize(new Dimension(60, 27));
    cbFontSize.setEditable(true);
    cbFont.setPreferredSize(new Dimension(120, 27));
    tbFormatToolbar.add(cbFont, null);
    tbFormatToolbar.add(cbFontSize, null);

    queryFormatToolbar.setToolBarID("FormatSetToolbar");
    queryFormatToolbar.setName("查询格式设置工具条");
//    queryFormatToolbar.add(chartButtom, null);
    chartButtom.addActionListener(this);
    tbMainToolbar.add(cbViewScale, null);
//    tbMainToolbar.add(queryFormatToolbar,null);
  }
  
  private void initDictTreeView() {
	  boolean showFind = this.PO.GetValueByParamName("showFind", "").equals("1")? true:false;
//	  if(showFind) {
//		  dictTreeView = new DictTreeView();
//	  } else {
//		  dictTreeView = new DictTreeView(false);
//	  }
//	  DictTree dictTree = dictTreeView.getTree();
//	  dictTree.addMouseListener(new MouseListener() {
//		public void mouseReleased(MouseEvent e) {
//			
//		}
//		
//		public void mousePressed(MouseEvent e) {
//			
//		}
//		
//		public void mouseExited(MouseEvent e) {
//			
//		}
//		
//		public void mouseEntered(MouseEvent e) {
//			
//		}
//		
//		public void mouseClicked(MouseEvent e) {
//			if(e.getClickCount() == 2) {
//				DataSetTreeNode dataSetTreeNode = dictTreeView.getSelectTreeNode();
//				if(dataSetTreeNode == null) {
//					return;
//				}
//				String needCol = PO.GetValueByParamName("needCol", "");
//				String[] cols = needCol.split(",");
//				Object obj = dataSetTreeNode.getDctBH();
//				PO.SetValueByParamName("treeBh", obj.toString());
//				Object obj1 = dataSetTreeNode.getDctMc();
//				PO.SetValueByParamName("treeMc", obj1.toString());
//				JActiveDComDM.MainApplication.BeginWaitCursor();
//				try {
//					Runnable run = new Runnable() {
//						public void run() {
//							queryByNewCon();
//						}
//					};
//					WaitingManager.getDefault().waitInvoke(QO.CaptionText,"正在刷新数据...",QO.EntityIconURL,run);
//				} catch (Exception e1) {
//					
//				} finally {
//					JActiveDComDM.MainApplication.EndWaitCursor();
//				}
//			}
//		}
//	});
//	  String dctId = this.PO.GetValueByParamName("DCT_ID", "");
//	  String where = this.PO.GetValueByParamName("treeWhere", "");
//	  dictTreeView.getDictModel().setDCT_ID(dctId);
//	  dictTreeView.getDictModel().getCdsParam().setServerName(this.QO.ServerName);
//	  dictTreeView.getDictModel().getCdsParam().setWhere(where);
//	  DefaultDctProvider provider = new DefaultDctProvider();
//      DefaultResolver resolver = new DefaultResolver();
//      dictTreeView.getDictModel().setProvider(provider);
//      dictTreeView.getDictModel().setResolver(resolver);
//	  try {
//		  dictTreeView.getDictModel().getCdsParam().setString("ID2NAME", "0");
//		dictTreeView.getDictModel().loadData();
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
  }

  private void queryByNewCon() {
	  try {
		  JFwkActiveObjectStub ativeobjStub = JActiveDComDM.BusinessActiveFramework.findActiveObjectStubByName("GenerQueryObject");
		  if (ativeobjStub == null) {
			  return;
		  }
		  Object obj = JBOFClass.CallObjectMethod(ativeobjStub.iActiveObject, "processOrganizeQuery", this.QO, PO);
		  if (obj == null) {
			  return;
		  }
		  JResponseObject RO = (JResponseObject) obj;
		  if (RO.ErrorCode == 0) {
			  // 处理实体阶段
			  this.QO.RO = RO;
			  this.ViewQueryData(PO, this.QO);
			  this.InitEventListener();
		  }
		  else {
			  JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, RO.ErrorString, "系统提示", JOptionPane.ERROR_MESSAGE);
		  }
	  } catch (Exception e) {
		  e.printStackTrace();
          JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, e.getMessage(), "系统提示", JOptionPane.ERROR_MESSAGE);
	}
  }
  
  /**
   * 初始化子窗口,处理查询结果
   * @param App
   * @param MainWindow
   * @param AddObject
   * @param AddObject1
   * @return
   */
  public Object InitChildWindow(JApplication App, JMainWindow MainWindow,
                                Object AddObject, Object AddObject1) {
    PO = (JParamObject) AddObject1;
    QO = (JQueryStubObject) AddObject;
    MyInit(PO, QO);

    try {
      if (!"".equals(QO.ViewCustomClass) && QO.ViewCustomClass != null) {
        if (viewCustomData(PO, QO) == 0) {
          ViewQueryData(PO, QO);
        }
      }
      else {
    	  long start = System.currentTimeMillis();
        ViewQueryData(PO, QO);
        long end = System.currentTimeMillis();
        System.out.println("show data :" + (end - start) + "ms%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    //add by fsz
   setBookProp();
    //
    InitEventListener();
    setDefaultPrintPageAttributes();
    return null;
  }

  private void insertPrintSheet(int SheetIndex) {
    JBook Book = this.vwQueryView.getDataReport().getBook();

    try {
      int SheetCount = Book.getBook().getSheetCount();
      if (SheetIndex >= SheetCount) { // 如果不存在这个Sheet
        Book.insertSheets(SheetCount, 1);
      }
      else { //删除原有的，再插入一个
        Book.deleteSheets(SheetCount - 1, 1);
        Book.insertSheets(SheetCount - 1, 1);
      }
      Book.setSheet(SheetIndex);
      Book.setShowGridLines(false);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  private int viewCustomData(JParamObject PO, JQueryStubObject QO) {
    int res = 0;
    try {
      Class ViewClass = Class.forName(QO.ViewCustomClass);
      QO.ViewCustomObject = ViewClass.newInstance();
      if (JBOFClass.FindObjectMethod(QO.ViewCustomObject, "viewCustomQueryData") != null) {
        Object[] array = {
            this, PO, QO, vwQueryView};
        JBOFClass.CallObjectMethod(QO.ViewCustomObject, "viewCustomQueryData",
                                   array);
        res = 1;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * 展示查询结果数据
   * @param PO
   * @param QO
   */
  public void ViewQueryData(JParamObject PO, JQueryStubObject QO) throws Exception {
    this.PO = PO;
    this.QO = QO;
    JResponseObject RO = QO.RO;

    //2008-4-25 fengbo 此次修改为了兼容将多个查询结果展示在一起；
    try {
      //新的查询结果模式，后台返回一个二维字符串数组
      String[][] queryResult = (String[][]) RO.ResponseObject;
      makeMultiQueryResult(queryResult);
      //展示查询结果
      vwQueryView.openTreeBookData(tableManagers[0], queryDataSets[0],
                                   (String) QO.Param);
      //追加后继的查询结果
      JDRQueryPainter queryPanter = vwQueryView.getDataReport().getRDPainter().
          queryPanter;
      //避免二次绘制表头格式
      queryPanter.setIsMultiQueryArea(true);
      JBook book = queryPanter.getJBook();
      for (int i = 1; i < tableManagers.length; i++) {
        queryPanter.setTableManager(tableManagers[i]);
        //表头
        queryPanter.drawQueryHeader();
        //表体
        int bodyDataBeginRowIndex = queryPanter.getJBook().getLastRow() + 1;
        queryPanter.drawQueryBodyData(queryPanter.getJBook().getLastRow() +
                                      1, queryDataSets[i].getDataVector());
        int bodyDataEndRowIndex = queryPanter.getJBook().getLastRow();
        queryPanter.setQueryBodyFormat(bodyDataBeginRowIndex,
                                       bodyDataEndRowIndex);
        queryPanter.drawQueryFooter();
      }
      //重新设置打印区域
      String printAreaFormula = JDRUtils.coor2Formula(book, 0, 0,
          book.getLastRow(),
          book.getMaxCol());
      if (printAreaFormula != null && printAreaFormula.length() > 0) {
        book.setPrintArea(printAreaFormula);
      }

    }
    catch (Exception ex0) {
      //原来的查询结果模式，后台返回一个字符串数组
      QueryObject = (String[]) RO.ResponseObject;
      if (QueryObject.length == 6) {
        //试图恢复PO
        try {
          if (QueryObject[0].startsWith("<?xml")) {
            this.PO = new JParamObject(QueryObject[0]);
            Debug.PrintlnMessageToSystem("将第一个返回值恢复为PO，成功！");
          }
        }
        catch (Exception ex) {
        }
        //图表格式对象
        this.chartFormatObject=QueryObject[5];

        showBNBQData(QO, QueryObject);
      }
      if (QueryObject.length == 5) {
        //试图恢复PO
        try {
          if (QueryObject[0].startsWith("<?xml")) {
            this.PO = new JParamObject(QueryObject[0]);
            Debug.PrintlnMessageToSystem("将第一个返回值恢复为PO，成功！");
          }
        }
        catch (Exception ex) {
        }
        showBNBQData(QO, QueryObject);
      }
      else if (QueryObject.length == 4) {
        //试图恢复PO
        try {
          if (QueryObject[0].startsWith("<?xml")) {
            this.PO = new JParamObject(QueryObject[0]);
            Debug.PrintlnMessageToSystem("将第一个返回值恢复为PO，成功！");
          }
        }
        catch (Exception ex) {
        }
        //图表格式对象
        this.chartFormatObject=QueryObject[3];

        Object[] obj = showData(QueryObject[1], QueryObject[2]);
        vwQueryView.openTreeBookData( (TableManager) obj[0],
                                     (QueryDataSet) obj[1],
                                     (String) QO.Param);
      }
      else if (QueryObject.length == 3) {
        //试图恢复PO
        try {
          if (QueryObject[0].startsWith("<?xml")) {
            this.PO = new JParamObject(QueryObject[0]);
            Debug.PrintlnMessageToSystem("将第一个返回值恢复为PO，成功！");
          }
        }
        catch (Exception ex) {
        }
        Object[] obj = showData(QueryObject[1], QueryObject[2]);
        vwQueryView.openTreeBookData( (TableManager) obj[0],
                                     (QueryDataSet) obj[1],
                                     (String) QO.Param);
      }
      else if (QueryObject.length == 2) {
        Object[] obj = showData(QueryObject[0], QueryObject[1]);
        vwQueryView.openTreeBookData( (TableManager) obj[0],
                                     (QueryDataSet) obj[1],
                                     (String) QO.Param);
      }
      else {
        throw new Exception("返回结果的数组元素个数不正确(应该为2，3，4，5，6)\r\n；" +
                            "参数个数为3，5时，将第一个数组元素默认为PO字符串\r\n；"+
                            "参数个数为4，6时，将第一个数组元素默认为PO字符串，最后一个默认为图表格式字符串!");
      }
    }
  }

  /**
   * 兼容服务器端返回的三种数据格式：
   * 1 文本字符串
   * 2 XML 字符串 TableDataManager
   * 3 URL文件流
   * @throws Exception
   */
  public Object[] showData(String formatXML, String data) throws Exception {
    //是否账页打印
    String isPrint = "0";
    if (PO != null) {
      isPrint = PO.GetValueByParamName("IS_LEDGER_PRINT", "0");
    }
    String[] urlStr;
    URL url;
    TableManager tempTableManager = new TableManager(formatXML);
    //tableManager=tempTableManager;
    QueryDataSet queryDataSet;
//    System.out.println("QueryURL:" + data);
    //URL
    if (data.startsWith("http")) {
      urlStr = data.split("\t");
      url = new URL(urlStr[0]);
      queryDataSet = QueryDataTransformer.getQueryNodeFromZIPURL(
          MultiLevelHeaderTreeTable.getViewIDs(tempTableManager), url,
          isPrint.equals("1"));
    }
    //XML字符串
    else if (data.startsWith("<?xml")) {
      queryDataSet = QueryDataTransformer.getQueryNodeFromXMLString(
          MultiLevelHeaderTreeTable.getViewIDs(tempTableManager), data);
      //MemCached方式
    }else if(data.startsWith("MemCached")){
      queryDataSet= QueryDataTransformer.getQueryNodeFromZIPDAL(
          MultiLevelHeaderTreeTable.getViewIDs(tempTableManager), data);
    }
    //文本字符串
    else {
      queryDataSet = QueryDataTransformer.getQueryNodeFromTXTString(
          MultiLevelHeaderTreeTable.getViewIDs(tempTableManager), data);
    }
    Object[] resultObject = new Object[2];
    resultObject[0] = tempTableManager;
    resultObject[1] = queryDataSet;
    return resultObject;
  }

  /**
   * 处理本年,本期 多数据集:
   * 构造本年/本期两个数据集,需要时切换数据集
   * @param QO JQueryStubObject
   * @param QueryObject String[]
   * @throws Exception
   */
  public void showBNBQData(JQueryStubObject QO, String[] QueryObject) throws
      Exception {
    JGenerQueryWindow QueryWindow = (JGenerQueryWindow) QO.EntityQuery.get(
        "PARENTWINDOW");
    String BNBQ = null;
    if (QueryWindow == null) {
      BNBQ = "BQ";
    }
    else {
      BNBQ = (String) QueryWindow.getValue("BNBQ", "BQ");
    }
    //构造数据集
    //本期
    Object[] obj = showData(QueryObject[1], QueryObject[2]);
    monthTableManager = (TableManager) obj[0];
    monthQueryDataSet = (QueryDataSet) obj[1];
    //tableManager=monthTableManager;
    //本年
    obj = showData(QueryObject[3], QueryObject[4]);
    yearTableManager = (TableManager) obj[0];
    yearQueryDataSet = (QueryDataSet) obj[1];

    if (QueryWindow == null || "BQ".equals(BNBQ)) {
      // 显示本期数
      vwQueryView.openTreeBookData(monthTableManager, monthQueryDataSet,
                                   (String) QO.Param);
      putValue("BNBQ", "BQ");
      // 设置本年菜单
      JMenuItemStub mis = this.FindMenuItem("mnBNLJ");
      if (mis != null) {
        mis.setText("本年累计");
      }
    }
    if ("BN".equals(BNBQ)) {
      // 显示本年数
      vwQueryView.openTreeBookData(yearTableManager, yearQueryDataSet,
                                   (String) QO.Param);
      putValue("BNBQ", "BN");
      // 设置本年菜单
      // 设置本年菜单
      JMenuItemStub mis = this.FindMenuItem("mnBNLJ");
      if (mis != null) {
        mis.setText("本期发生");
      }
    }
  }

  void MyInit(JParamObject PO, JQueryStubObject QO) {
    LoadGUIResource(QO);
    InitFontSizeList();
    InitViewScale();
  }

  /**
   * 字体
   */
  void InitFontSizeList() {
    cbFontSize.insertItemAt("6", 0);
    cbFontSize.insertItemAt("8", 1);
    cbFontSize.insertItemAt("9", 2);
    cbFontSize.insertItemAt("10", 3);
    cbFontSize.insertItemAt("12", 4);
    cbFontSize.insertItemAt("14", 5);
    cbFontSize.insertItemAt("16", 6);
    cbFontSize.insertItemAt("18", 7);
    cbFontSize.insertItemAt("20", 8);
    cbFontSize.insertItemAt("22", 9);
    cbFontSize.insertItemAt("24", 10);
    cbFontSize.insertItemAt("26", 11);
    cbFontSize.insertItemAt("28", 12);
    cbFontSize.insertItemAt("36", 13);
    cbFontSize.insertItemAt("48", 14);
    cbFontSize.insertItemAt("72", 15);
  }

  /**
   *缩放比率
   */
  void InitViewScale() {
    cbViewScale.insertItemAt("200%", 0);
    cbViewScale.insertItemAt("100%", 1);
    cbViewScale.insertItemAt("75%", 0);
    cbViewScale.insertItemAt("50%", 0);
    cbViewScale.insertItemAt("25%", 0);
  }

  /**
   *事件监听
   */
  void InitEventListener() {
    JBook book = null;
    if (this.vwQueryView.getDataReport() != null) {
      book = this.vwQueryView.getDataReport().getBook();
    }
    if (book == null) {
      return;
    }
    book.addMouseListener(this);
    book.addSelectionChangedListener(this);
  }

  /**
   *
   * @param QO
   */
  public void LoadGUIResource(JQueryStubObject QO) {

    String URI = null, RB = null;
    Object AO;
    MainMenu = new JMenuBar();
    RB = this.getClass().getName().replace('.', '/'); ;
    RB = "Codebase/" + RB;
    java.net.URL url = JXMLResource.LoadXML(this, "QueryWindow.xml");
    URI = url.toString();
    AO = LoadChildGUIMenu(URI, MainMenu, RB, null, "toolbars", "mainmenu");
    this.RegUserMenuBar(MainMenu);
    this.RegXMLToolbarList(XMLToolbarList);
    TablePopupMenu = new JPopupMenu();
    LoadPopupGUIMenu(URI, TablePopupMenu, RB, AO, "notoolbar", "popupmenu1");
    //
    this.RegUserToolbar(tbFormatToolbar);
    //
    if (QO.EntityQueryXMLURL != null) {
      MainMenu = new JMenuBar();
      RB = QO.Path + "EntityQuery/";
      URI = QO.EntityQueryXMLURL.toString();
      if (URI.toLowerCase().endsWith(".xml")){
        AO = LoadChildGUIMenu(URI, MainMenu, RB, null, "toolbars", "mainmenu", null);
        this.RegUserMenuBar(MainMenu);
        this.RegXMLToolbarList(XMLToolbarList);
      }
    }
    //
    this.RegUserToolbar(tbMainToolbar);
    //查询格式工具条
//    this.RegUserToolbar(this.queryFormatToolbar);
  }

  /**
   *
   * @param comp
   * @param p
   */
  public void PopTabMenu(java.awt.Component comp, Point p) {
    if (TablePopupMenu != null) {
      TablePopupMenu.show(comp, p.x, p.y);
    }
  }

  /**
   *
   * @param e
   */
  //------------------------------------------------------------------------------------------------
  public void mouseClicked(java.awt.event.MouseEvent e) {
//    if ( e.getSource() == ReportBook && e.getButton() == e.BUTTON3 ) {
//      PopTabMenu((java.awt.Component)e.getSource(),e.getPoint());
//    }
  }

  /**
   *
   * @param e
   */
  public void mouseDragged(MouseEvent e) {

  }

  /**
   *
   * @param e
   */
  public void mouseEntered(java.awt.event.MouseEvent e) {
    // user code begin {1}
    // user code end

    // user code begin {2}
    // user code end
  }

  /**
   *
   * @param e
   */
  public void mouseExited(java.awt.event.MouseEvent e) {
    // user code begin {1}
    // user code end
    // user code begin {2}
    // user code end
  }

  /**
   *
   * @param e
   */
  public void mouseMoved(MouseEvent e) {
    //System.out.println("mouseMoved");
  }

  /**
   *
   * @param e
   */
  public void mouseReleased(java.awt.event.MouseEvent e) {
    // user code begin {1}
    // user code end
    // user code begin {2}
    // user code end
  }

  /**
   *
   * @param e
   */
  public void mousePressed(java.awt.event.MouseEvent e) {
    if (e.getSource() == this.vwQueryView.getDataReport().getBook() &&
        e.getModifiers() == e.BUTTON3_MASK) {
      PopTabMenu( (java.awt.Component) e.getSource(), e.getPoint());
    }
    // 处理双JI事件
    if (e.getSource() == this.vwQueryView.getDataReport().getBook() &&
        e.getModifiers() == e.BUTTON1_MASK && e.getClickCount() == 2) {
      process2Click();
    }
  }

  class InnerEventThread
      implements Runnable {
    JGenerQueryWindow QueryWindow = null;
    ActionEvent Event = null;
    InnerEventThread(JGenerQueryWindow qw, ActionEvent e) {
      QueryWindow = qw;
      Event = e;
    }

    public void run() {
      actionPerformed(Event);
    }
  }

  private void process2Click() {
    String ActionName = (String) QO.EntityQuery.get("DefaultAction");
    if (ActionName != null && !"".equals(ActionName)) {
      ActionEvent e = new ActionEvent(this, 0, ActionName);
      // 线程调用
      SwingUtilities.invokeLater(new InnerEventThread(this, e));
    }
  }

  /**
   *
   * @param CO
   */
  public void CallBackContextObject(JContextObject CO) {
    CO.ParamObject = PO;
    CO.DataObject = QO;
    CO.CustomObject = this;
    CO.AdditiveObject = vwQueryView;
    return;
  }

  /**
   *
   * @param e
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == cbViewScale) {
      setViewScale();
      return;
    }
    if (e.getSource() == cbFont) {
      SetFont();
    }
    if (e.getSource() == cbFontSize) {
      SetFontSize();
    }
    if (e.getActionCommand().equals("mnPrintPreview")){
      ProcessmnPrintPreview();
      return;
    }else if(e.getActionCommand().equals("mnPrint")){
      printBook();
      return;
    }else if(e.getActionCommand().equals("mnPageSetup")){
      try{
        this.ProcessmnPageSetup(null, null, null, null);
        return;
      }catch(Exception ex){

      }
    }
    super.actionPerformed(e);
  }

  /**
   *
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */
  public Object ProcessmnPageSetup(Object Param, Object Data,
                                   Object CustomObject, Object AdditiveObject) {


    JBook Book = this.vwQueryView.getDataReport().getBook();
    try {
//      com.efounder.eai.service.dof.JDOFResourceObject.setOldLookAndFeel();
      com.f1j.swing.ss.PageSetupDlg PageDlg = new com.f1j.swing.ss.PageSetupDlg(
          Book);
      PageDlg.show();
    } catch ( Exception ex ) {
      ex.printStackTrace();
//    } finally {
//      com.efounder.eai.service.dof.JDOFResourceObject.setNewLookAndFeel();
    }

//    SwingUtilities.invokeLater(new InnerThread(this) {
//      public void run() {
//        try {
//          com.efounder.eai.service.dof.JDOFResourceObject.setOldLookAndFeel();
//          JBook Book = QueryWindow.vwQueryView.getDataReport().getBook();
//          com.f1j.swing.ss.PageSetupDlg PageDlg = new com.f1j.swing.ss.
//              PageSetupDlg(Book);
//          PageDlg.show();
//        } catch (Exception e) {
//          e.printStackTrace();
//        } finally {
//          com.efounder.eai.service.dof.JDOFResourceObject.setNewLookAndFeel();
//        }
//      }
//    });
    return null;
  }

  /**
   * 打印预览参数设置。
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */
  public Object ProcessmnPrintPreview() {
//        actionPerformed( (ActionEvent) Param);
    try {
      //1 首先保存本地查询格式，打印时就可以直接使用
      saveChangedTableManager();
      JBook Book = this.vwQueryView.getDataReport().getBook();
      Book.setSheet(0);
      int Colcount = Book.getLastCol() + 1;
      JPrintPageColumnSetDlg Dlg = new JPrintPageColumnSetDlg( (String) QO.
          Param, Colcount);
      Dlg.CenterWindow();
      Dlg.show();

      if (Dlg.Result == Dlg.RESULT_CANCEL) {
        return null;
      }
      //2 获取当前查询结果展开的数据集合
      QueryFormat.getUnWrapedNode(this, Dlg.isUnwraped());
      int Fixcol = Integer.parseInt(Dlg.Fixcol);
      int Changecol = Integer.parseInt(Dlg.Changecol);
      openPrintViewWindow(Fixcol, Changecol, Dlg.Context);
    }
    catch (Exception e) {}

    return null;
  }

  /**
   * 打印预览
   * @param Fixcol int
   * @param Changecol int
   * @param Context boolean
   */
  private void openPrintViewWindow(int Fixcol, int Changecol, boolean Context) {
    JBook Book = vwQueryView.getDataReport().getBook();
    TableManager tableManager = vwQueryView.getTableManager();
    try {
      JF1PageSets F1PageSets = new JF1PageSets(Book);
      insertPrintSheet(1);
      //取有多少列
      int Colcount = getShowColumnCount(tableManager, Fixcol, Changecol);
      Book.setMaxCol(Colcount);
      F1PageSets.saveToBook(Book);
      AccountPrintFormatParam AccountFormat = new AccountPrintFormatParam();
      AccountFormat.AccountBook = false;
      AccountFormat.TopMargin = Book.getPrintTopMargin() * 100;
      AccountFormat.BottomMargin = Book.getPrintTopMargin() * 100;
      AccountFormat.LeftMargin = Book.getPrintLeftMargin() * 100;
      AccountFormat.RightMargin = Book.getPrintRightMargin() * 100;
      AccountFormat.Default_Orientation = Book.isPrintLandscape();
      AccountFormat.PaperSize = Book.getPrintPaperSize();
      StubObject[] datas = new StubObject[1];
      datas[0] = new StubObject();
      //复位行指针，可以重复预览
      vwQueryView.getQueryDataSet().goTop();
      datas[0].setObject("dataManager",
                         vwQueryView.getQueryDataSet());
      //2008-4-25 fengbo
      //传递查询标示符
      CustomPrint printObject = new CustomPrintObject(PO, QO, Book,
          AccountFormat);
      Object[] printArray = (Object[]) AccountPrint.printAccountBook3(Book,
          tableManager, datas, printObject, 0, Fixcol, Changecol, 1, 0, 3, 0,
          Context, true);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //取显示的列数
  private int getShowColumnCount(TableManager tableManager, int Fixcol,
                                 int Changecol) {
    int Count = 0;
    Vector TableView = tableManager.getTableView();
    for (int i = 0; i < TableView.size(); i++) {
      XmlViewCol viewItem = (XmlViewCol) TableView.get(i);
      if (viewItem.getType().equals("group")) {
        Count += getGroupColumns(tableManager, viewItem.getId());
      }
      else {
        Count++;
      }
    }
    int page = (Count - Fixcol) / Changecol;
    if ( (Count - Fixcol) % Changecol != 0) {
      page++;
    }
    Count = page * (Fixcol + Changecol);

    return Count;
  }

  //取某个组内的列数
  private int getGroupColumns(TableManager tableManager, String id) {
    int Count = 0;
    XmlGroup xmlGroup = tableManager.getGroupById(id);
    Vector groupItem = xmlGroup.getItems();
    for (int k = 0; k < groupItem.size(); k++) {
      XmlItem xmlItem = (XmlItem) groupItem.get(k);
      if (xmlItem.getType().equals("group")) {
        Count += getGroupColumns(tableManager, xmlItem.getId());
      }
      else {
        Count++;
      }
    }
    return Count;
  }

  /**
   * 菜单中的普通打印功能
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */
  public Object ProcessmnPrint(Object Param, Object Data, Object CustomObject,
                               Object AdditiveObject) {
    printBook();
    return null;
  }

  /**
   * （普通）打印查询结果.
   */
  private void printBook() {
    try {
      //1 获取JBook
      JBook Book = vwQueryView.getDataReport().getBook();
      //多格式复杂查询，直接调用JBook的print打印。
      JDRQueryPainter queryPanter =
          vwQueryView.getDataReport().getRDPainter().queryPanter;
//      if (queryPanter != null && !queryPanter.isIsMultiQueryArea()) {
      //多查询区域 或者 不打印表头 2008-8-4 fengbo
      if (queryPanter != null && !queryPanter.isIsMultiQueryArea() && queryPanter.isPrintHeader()) {

        TableManager tableManager = vwQueryView.getTableManager();
        //2 打印
        //2.1 如果当前sheet为查询结果sheet,则重新生成打印预览sheet.
        if (Book.getSheet() == 0) {
          if (Book.getBook().getSheetCount() >= 2) {
            Book.deleteSheets(1, 1);
          }
          /**
           * 2007-9-6 fengbo.
           * 原来的'打印' 和'打印预览'功能有些混乱,不好理解;
           * 为了使得 '打印' 和 '打印预览'有相同的打印效果,屏蔽了原来的'直接打印'代码;
           * 改为和'打印预览'相同的效果.
           */
          //AccountPrint.printAccountBook(Book, tableManager,vwQueryView.getQueryDataSet().getDataVector(), QO, 1, PO);
          //调用打印预览代码.
          ProcessmnPrintPreview();
        }
      }
      JPrintDlg Dlg = new JPrintDlg(Book, QO);
      Dlg.CenterWindow();
      Dlg.Show();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 导出数据
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */
  public Object ProcessmnExport(Object Param, Object Data, Object CustomObject,
                                Object AdditiveObject) {
    actionPerformed( (ActionEvent) Param);
    return null;
  }

  /**
   *
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */
  public Object ProcessmnSave(Object Param, Object Data, Object CustomObject,
                              Object AdditiveObject) {
//    actionPerformed( (ActionEvent) Param);
    JBookActionImpl actionImpl = new JGenerQueryActionImpl();
    JBookActionManager actionMgr = new JBookActionManager(actionImpl);
    try {
      if (vwQueryView != null) {
        JBook book = vwQueryView.getDataReport().getBook();
        if (book != null) {
          String actionName = JBookActionConstants.ACTION_EXPORT;
          String defaultName = this.getTitleAt();
          JBookAction action = new JBookAction(book, actionName, defaultName);
          actionMgr.execute(action);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   *
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */
  public Object ProcessmnFind(Object Param, Object Data, Object CustomObject,
                              Object AdditiveObject) {
    actionPerformed( (ActionEvent) Param);
    return null;
  }

  /**
   *
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */
  public Object ProcessmnCut(Object Param, Object Data, Object CustomObject,
                             Object AdditiveObject) {
    actionPerformed( (ActionEvent) Param);
    return null;
  }

  /**
   *
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */

  public Object ProcessmnCopy(Object Param, Object Data, Object CustomObject,
                              Object AdditiveObject) {
    actionPerformed( (ActionEvent) Param);
    return null;
  }

  /**
   *
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */

  public Object ProcessmnPaste(Object Param, Object Data, Object CustomObject,
                               Object AdditiveObject) {
    actionPerformed( (ActionEvent) Param);
    return null;
  }

  /**
   *
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */

  public Object ProcessmnReplace(Object Param, Object Data, Object CustomObject,
                                 Object AdditiveObject) {
    actionPerformed( (ActionEvent) Param);
    return null;
  }

  /**
   *
   * @param Param
   * @param Data
   * @param CustomObject
   * @param AdditiveObject
   * @return
   */

  public Object ProcessmnDelete(Object Param, Object Data, Object CustomObject,
                                Object AdditiveObject) {
    actionPerformed( (ActionEvent) Param);
    return null;
  }

  /**
   *
   * @param selectionChangedEvent
   */
  public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
    GetFont();
    GetFormat();
    GetViewScale();
  }

  /**
   *
   */
  private void GetFont() {
    JBook book = this.vwQueryView.getDataReport().getBook();
    if (book == null) {
      return;
    }
    try {
      cbFont.removeActionListener(this);
      cbFontSize.removeActionListener(this);
      this.cbFont.FindFont(book.getCellFormat().getFontName());
      this.cbFontSize.setSelectedItem(String.valueOf(book.getCellFormat().
          getFontSize() / 20));
    }
    catch (Exception e) {
//      e.printStackTrace();
    }
    finally {
      cbFont.addActionListener(this);
      cbFontSize.addActionListener(this);
    }
  }

  /**
   *
   */
  private void GetFormat() {
    JBook book = this.vwQueryView.getDataReport().getBook();
    if (book == null) {
      return;
    }
    CellFormat CF;
    try {
      CF = book.getCellFormat();
      setMenuSelected("mnBold", CF.isFontBold());
      setMenuSelected("mnItalic", CF.isFontItalic());
      setMenuSelected("mnUnderline", CF.isFontUnderline());
      setMenuSelected("mnUnite", CF.isMergeCells());
      short HAlign = CF.getHorizontalAlignment();
      setMenuSelected("mnLeft", false);
      setMenuSelected("mnCenter", false);
      setMenuSelected("mnRight", false);
      if (HAlign == CF.eHorizontalAlignmentLeft) {
        setMenuSelected("mnLeft", true);
      }
      if (HAlign == CF.eHorizontalAlignmentCenter) {
        setMenuSelected("mnCenter", true);
      }
      if (HAlign == CF.eHorizontalAlignmentRight) {
        setMenuSelected("mnRight", true);
      }

      short VAlign = CF.getVerticalAlignment();
      setMenuSelected("mnTop", false);
      setMenuSelected("mnVCenter", false);
      setMenuSelected("mnDown", false);
      if (VAlign == CF.eVerticalAlignmentTop) {
        setMenuSelected("mnTop", true);
      }
      if (VAlign == CF.eVerticalAlignmentCenter) {
        setMenuSelected("mnVCenter", true);
      }
      if (VAlign == CF.eVerticalAlignmentBottom) {
        setMenuSelected("mnDown", true);
      }
      short orientation = CF.getOrientation();
      setMenuSelected("mnLeftToRight", false);
      setMenuSelected("mnUpToDown", false);
      if (orientation == CF.eOrientationNone) {
        setMenuSelected("mnLeftToRight", true);
      }
      if (orientation == CF.eOrientationTopToBottom) {
        setMenuSelected("mnUpToDown", true);
      }
    }
    catch (Exception e) {
//      e.printStackTrace();
    }
  }

  /**
   *
   */
  private void GetViewScale() {
    JBook book = this.vwQueryView.getDataReport().getBook();
    if (book == null) {
      return;
    }

    cbViewScale.removeActionListener(this);
    int ViewScale = book.getViewScale();
    String Text = String.valueOf(ViewScale) + "%";
    cbViewScale.setSelectedItem(Text);
    cbViewScale.addActionListener(this);
  }

  /**
   *
   */
  void SetFontSize() {
    JBook book = this.vwQueryView.getDataReport().getBook();
    if (book == null) {
      return;
    }

    CellFormat CF;
    try {
      CF = book.getCellFormat();
      CF.setFontSize(Integer.valueOf( (String) cbFontSize.getSelectedItem()).
                     intValue() * 20);
      book.setCellFormat(CF);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   */
  void setViewScale() {
    JBook book = this.vwQueryView.getDataReport().getBook();
    if (book == null) {
      return;
    }

    cbViewScale.removeActionListener(this);
    try {
      String Text = (String) cbViewScale.getSelectedItem();
      if (Text.endsWith("%")) {
        Text = Text.substring(0, Text.length() - 1);
      }
      int ViewScale;
      ViewScale = Integer.valueOf(Text).intValue();
      book.setViewScale(ViewScale);
      Text = String.valueOf(ViewScale) + "%";
      cbViewScale.setSelectedItem(Text);
    }
    catch (Exception e) {
//      e.printStackTrace();
    }
    finally {
      cbViewScale.addActionListener(this);
      book.grabFocus();
    }
  }

  /**
   *
   */
  void SetFont() {
    JBook book = this.vwQueryView.getDataReport().getBook();
    if (book == null) {
      return;
    }

    CellFormat CF;
    try {
      CF = book.getCellFormat();
      CF.setFontName( (String) cbFont.getSelectedItem());
      //CF.setFontSize(Integer.valueOf((String)cbFontSize.getSelectedItem()).intValue()*20);
      book.setCellFormat(CF);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置打印的页面属性
   */
  private void setDefaultPrintPageAttributes() {
    JBook book = null;
    if (this.vwQueryView.getDataReport() != null) {
      book = this.vwQueryView.getDataReport().getBook();
    }
    if (book == null) {
      return;
    }
    try {
      if (!getPageSet(book)) {
        //页宽
        book.setPrintScaleFitToPage(false);
//            book.setPrintScaleFitHPages(1);
        // 默认 横向
        book.setPrintLandscape(true);
        //页高
//            book.setPrintScaleFitVPages(9999);
        //颜色
        book.setPrintNoColor(true);
        //网格线
        book.setPrintGridLines(false);
        //页眉为空
        book.setPrintHeader("");
        //
        book.setPrintFooter("");
        //
        book.setPrintTopMargin(0.5);
        book.setPrintBottomMargin(0.5);
        book.setPrintFooterMargin(0.0);
        book.setPrintHeaderMargin(0.0);
        //页脚
//            String printFooter = getDefaultPrintFooter();
//            if (printFooter != null) {
//                book.setPrintFooter(printFooter);
//            }
//            else {
//                book.setPrintFooter("&C&P");
//            }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @return
   */
  private String getDefaultPrintFooter() {
    String printFooter = "";
    try {
      String title = "";
      if (QO != null) {
        title = QO.CaptionText;
      }
      if (title == null) {
        title = "";
      }
      JParamObject PO = JParamObject.Create();
      printFooter += "&C"; //居中
      printFooter += PO.GetValueByEnvName("CW_SOFTMC", "");
      printFooter += PO.GetValueByEnvName("CW_VERSIO", "");
      printFooter += "-";
      printFooter += PO.GetValueByEnvName("CW_SYDWMC", "");
      printFooter += "-";
      printFooter += title;
      printFooter += " ";
      printFooter += "第&P页";

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return printFooter;
  }

  /**
   *
   * <p>Title: </p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: </p>
   * @author not attributable
   * @version 1.0
   */
  class InnerThread
      extends Thread {
    JGenerQueryWindow QueryWindow = null;
    InnerThread(JGenerQueryWindow qw) {
      QueryWindow = qw;
    }
  }

  /**
   * 取出已取的页面设置
   * @return boolean
   */
  public boolean getPageSet(JBook book) {
    try {
      //打印方向
      String Lanscape = JActiveDComDM.LocalRegistry.Get(QO.Param + "Lanscape");
      //显示比例
      String Scale = JActiveDComDM.LocalRegistry.Get(QO.Param + "Scale");
      //纸张大小
      String Papger = JActiveDComDM.LocalRegistry.Get(QO.Param + "Paper");
      //自动页号
      String AutoPage = JActiveDComDM.LocalRegistry.Get(QO.Param + "AutoPage");
      String StartPage = JActiveDComDM.LocalRegistry.Get(QO.Param + "StartPage");
      //页边距
      String Top = JActiveDComDM.LocalRegistry.Get(QO.Param + "Top");
      String Bottom = JActiveDComDM.LocalRegistry.Get(QO.Param + "Bottom");
      String Left = JActiveDComDM.LocalRegistry.Get(QO.Param + "Left");
      String Right = JActiveDComDM.LocalRegistry.Get(QO.Param + "Right");
      String HeaderM = JActiveDComDM.LocalRegistry.Get(QO.Param + "HeaderM");
      String FooterM = JActiveDComDM.LocalRegistry.Get(QO.Param + "FooterM");
      //页眉页脚
      String Header = JActiveDComDM.LocalRegistry.Get(QO.Param + "Header");
      String Footer = JActiveDComDM.LocalRegistry.Get(QO.Param + "Footer");

      String GridLines = JActiveDComDM.LocalRegistry.Get(QO.Param + "GridLines");
      String NoColor = JActiveDComDM.LocalRegistry.Get(QO.Param + "NoColor");
      String RowHeading = JActiveDComDM.LocalRegistry.Get(QO.Param +
          "RowHeading");
      String ColHeading = JActiveDComDM.LocalRegistry.Get(QO.Param +
          "ColHeading");

      boolean isPrintScaleFitToPage = "1".equals(JActiveDComDM.LocalRegistry.Get(QO.Param +
          "IsPrintScaleFitToPage"));
      String fitHPages = JActiveDComDM.LocalRegistry.Get(QO.Param + "FitHPages");
      String fitVPages = JActiveDComDM.LocalRegistry.Get(QO.Param + "FitVPages");
      book.setPrintScaleFitToPage(isPrintScaleFitToPage);
      if (fitHPages!=null && !"".equals(fitHPages))
        book.setPrintScaleFitHPages(parseInteger(fitHPages));
      if (fitVPages!=null && !"".equals(fitVPages))
        book.setPrintScaleFitVPages(parseInteger(fitVPages));

      book.setPrintLandscape(parseBoolean(Lanscape).booleanValue());
      book.setPrintPaperSize(parseShort(Papger));
      book.setPrintScale(parseInteger(Scale));

      if (!parseBoolean(AutoPage).booleanValue()) {
        book.setPrintAutoPageNumbering(false);
        book.setPrintStartPageNumber(parseInteger(StartPage));
      }

      book.setPrintTopMargin(parseDouble(Top));
      book.setPrintBottomMargin(parseDouble(Bottom));
      book.setPrintLeftMargin(parseDouble(Left));
      book.setPrintRightMargin(parseDouble(Right));
      book.setPrintFooterMargin(parseDouble(FooterM));
      book.setPrintHeaderMargin(parseDouble(HeaderM));

      book.setPrintHeader(Header);
      book.setPrintFooter(Footer);

      book.setPrintGridLines(parseBoolean(GridLines).booleanValue());
      book.setPrintNoColor(parseBoolean(NoColor).booleanValue());
      book.setPrintRowHeading(parseBoolean(RowHeading).booleanValue());
      book.setPrintColHeading(parseBoolean(ColHeading).booleanValue());
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private Boolean parseBoolean(String o) {
	try {
		return Boolean.valueOf(o);
	} catch(Exception ce) {
		System.out.println(o);
		ce.printStackTrace();
		return Boolean.valueOf("false");
	}
  }
  
  private int parseInteger(String o) {
	try {
		return Integer.parseInt(o);
	} catch (Exception ce) {
		System.out.println(o);
		ce.printStackTrace();
		return 0;
	}
  }
  
  private Short parseShort(String o) {
	try {
		return Short.parseShort(o);
	} catch(Exception ce) {
		System.out.println(o);
		ce.printStackTrace();
		return 0;
	}
  }
  
  private double parseDouble(String o) {
	try {
		return Double.parseDouble(o);
	} catch(Exception ce) {
		System.out.println(o);
		ce.printStackTrace();
		return 0;
	}
  }
  
  /**
   * 记录页面设置
   */
  public boolean Close() {
    if (this.vwQueryView == null || this.vwQueryView.getDataReport()==null)
      return true;
    JBook Book = this.vwQueryView.getDataReport().getBook();
    try {
      //打印方向
      boolean Lanscape = Book.isPrintLandscape();
      //显示比例
      int Scale = Book.getPrintScale();
      //适应宽度、高度
      boolean isPrintScaleFitToPage = Book.isPrintScaleFitToPage();
      int fitHPages = Book.getPrintScaleFitHPages();
      int fitVPages = Book.getPrintScaleFitVPages();
      //默认纸张
      int Paper = Book.getPrintPaperSize();
      //自动页号
      boolean AutoPage = Book.isPrintAutoPageNumbering();
      int StartPage = Book.getPrintStartPageNumber();
      //页边距
      double Top = Book.getPrintTopMargin();
      double Bottom = Book.getPrintBottomMargin();
      double Left = Book.getPrintLeftMargin();
      double Right = Book.getPrintRightMargin();
      //页眉页脚高度
      double HeaderM = Book.getPrintHeaderMargin();
      double FooterM = Book.getPrintFooterMargin();
      //页眉页脚
      String Header = Book.getPrintHeader();
      String Footer = Book.getPrintFooter();
      //是否打印网线
      boolean GridLines = Book.isPrintGridLines();
      //黑白打印
      boolean NoColor = Book.isPrintNoColor();
      //行头
      boolean RowHeading = Book.isPrintRowHeading();
      //列头
      boolean ColHeading = Book.isPrintColHeading();

      JActiveDComDM.LocalRegistry.Put(QO.Param + "Lanscape",
                                      String.valueOf(Lanscape));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "Scale", String.valueOf(Scale));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "Paper", String.valueOf(Paper));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "AutoPage",
                                      String.valueOf(AutoPage));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "StartPage",
                                      String.valueOf(StartPage));

      JActiveDComDM.LocalRegistry.Put(QO.Param + "Top", String.valueOf(Top));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "Bottom",
                                      String.valueOf(Bottom));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "Left", String.valueOf(Left));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "Right", String.valueOf(Right));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "HeaderM",
                                      String.valueOf(HeaderM));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "FooterM",
                                      String.valueOf(FooterM));

      JActiveDComDM.LocalRegistry.Put(QO.Param + "Header", Header);
      JActiveDComDM.LocalRegistry.Put(QO.Param + "Footer", Footer);

      JActiveDComDM.LocalRegistry.Put(QO.Param + "GridLines",
                                      String.valueOf(GridLines));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "NoColor",
                                      String.valueOf(NoColor));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "RowHeading",
                                      String.valueOf(RowHeading));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "ColHeading",
                                      String.valueOf(ColHeading));
      //
      JActiveDComDM.LocalRegistry.Put(QO.Param + "IsPrintScaleFitToPage",
                                      isPrintScaleFitToPage ? "1" : "0");
      JActiveDComDM.LocalRegistry.Put(QO.Param + "FitHPages",
                                      String.valueOf(fitHPages));
      JActiveDComDM.LocalRegistry.Put(QO.Param + "FitVPages",
                                      String.valueOf(fitVPages));
      //保存本地查询格式
      boolean notSaveFlag = "1".equals(PO.GetValueByParamName("notSaveFlag", ""));
      if(!notSaveFlag) {
    	  saveChangedTableManager();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    finally{
        try{
            vwQueryView.removeAll();
            Book.removeMouseListener(this);
            Book.removeSelectionChangedListener(this);
            vwQueryView.getDataReport().removeAll();
            jPanel1.remove(vwQueryView);
            vwQueryView.setJDRPainter(null);
            Book.removeNotify();
            Book.removeAll();
            Book.destroy();
            vwQueryView = null;
        }catch(Exception e){

        }
    }
    return true;
  }

  /**
   *
   * 保存本地查询格式。
   * @param comp Component
   * @param p Point
   */
  private void saveChangedTableManager() throws Exception {
    if (QO != null && QO.Param != null) {
      TableManager tableManager = vwQueryView.getTableManager();
      int cidSize = 0;
      String[] cidNameGroup = null;
      String cidName = "";
      XmlColumn xmlColumn = null;
      cidNameGroup = MultiLevelHeaderTreeTable.getViewIDs(tableManager);
      JBook book = vwQueryView.getDataReport().getBook();
      //设置当前Sheet为sheet1，为了取得sheet1的查询格式
      int nowSheet = book.getSheet();
      book.setSheet(0);
      cidSize = cidNameGroup.length;
      Map tempMap = null;
      //取标题行的属性
      int titleRow = vwQueryView.getTableManager().getTitles().size();
      int mTitleLevels = JDRQueryPainterUtils.getHeaderLevels(vwQueryView.
          getTableManager());
      int rowIndex = titleRow + mTitleLevels - 1;
      //2008-6-2 fengbo
      //仅当查询结果包含数据时，才保存查询格式
      if (book.getLastRow() > rowIndex) {
        for (int i = 0; i < cidSize; i++) {
          cidName = cidNameGroup[i].toString();
          xmlColumn = tableManager.getColumnById(cidName);
          tempMap = getBookParamter(book, rowIndex, i);
          xmlColumn.setFontname( (String) tempMap.get("fontName"));
          xmlColumn.setFontsize( (String) tempMap.get("fontSize"));
          xmlColumn.setAlign( (String) tempMap.get("horizontalAliment"));
          xmlColumn.setWidth( (String) tempMap.get("columnWidth"));
          tableManager.UpdateColumn(xmlColumn);
        }
        //恢复sheet为初始sheet
        book.setSheet(nowSheet);
        //新建文件路径
        String fileName = QueryFormat.xmlPath + (String) QO.Param;
        String path = fileName.substring(0, fileName.lastIndexOf("/"));
        File file = new File(path);
        if (!file.exists()) {
          file.mkdirs();
        }
        //存储xml文件
        FileOutputStream out = new FileOutputStream(fileName);
        byte[] content = tableManager.GetRootXMLString().getBytes("UTF-8");
        out.write(content);
        out.flush();
        out.close();
      }
    }
  }

  private Map getBookParamter(JBook book, int rowIndex, int columnIndex) {
    Map parameterMap = new HashMap();
    try {

      //返回指定列的cellformat
      CellFormat cellFormat = book.getCellFormat(rowIndex, columnIndex,
                                                 rowIndex, columnIndex);
      //返回指定行的列宽
      int columnWidth = book.getColWidth(columnIndex) /
          JDRConstants.DEFAULT_SCALE;
      //返回指定行的字体名称
      String fontName = cellFormat.getFontName();
      //返回指定行的字体大小
      int fontSize = cellFormat.getFontSize() / JDRConstants.DEFAULT_FONT_SCALE;
      //返回指定行的水平位置
      cellFormat = book.getCellFormat(rowIndex + 1, columnIndex, rowIndex + 1,
                                      columnIndex);
      short horizontalAliment = cellFormat.getHorizontalAlignment();
      String align = "";
      switch (horizontalAliment) {
        case 1:
          align = "left";
          break;
        case 2:
          align = "center";
          break;
        case 3:
          align = "right";
          break;
      }
      //将四个参数放到Map中
      parameterMap.put("columnWidth", String.valueOf(columnWidth));
      parameterMap.put("fontName", fontName);
      parameterMap.put("fontSize", String.valueOf(fontSize));
      parameterMap.put("horizontalAliment", align);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
    }
    return parameterMap;
  }

  /**
   * 2008-4-25 fengbo
   * 把查询结果加工为多个查询格式和数据
   * @param queryResult String[][]
   */
  private void makeMultiQueryResult(String[][] queryResult) throws Exception {
    if (queryResult.length < 2) {
      throw new Exception("不是多个查询结果，后台返回的查询结果格式不正确！");
    }
    tableManagers = new TableManager[queryResult[0].length];
    queryDataSets = new QueryDataSet[queryResult[0].length];
    //查询结果包含：格式和数据两部分
    Object[] obj = null;
    for (int j = 0; j < queryResult[0].length; j++) {
      //格式,数据
      obj = showData(queryResult[0][j], queryResult[1][j]);
      tableManagers[j] = (TableManager) obj[0];
      queryDataSets[j] = (QueryDataSet) obj[1];
    }
  }

  public void setChartFormatObject(Object chartFormatObject) {
    this.chartFormatObject = chartFormatObject;
  }

  public Object getChartFormatObject() {
    return chartFormatObject;
  }

  public void doLayout() {
//    setBookProp();
    super.doLayout();
  }

  public void setBookProp() {
    String sz = (String) QO.EntityQuery.get("showzero");
    if (sz != null && "1".equals(sz))
      vwQueryView.getDataReport().getBook().setShowZeroValues(true);
    String fixcols = (String) QO.EntityQuery.get("fixcols");
    if (fixcols == null)
      fixcols = "2";
    try {
      vwQueryView.getDataReport().getBook().setFixedCols(0,
          Integer.parseInt(fixcols));
    }
    catch (Exception ex1) {
    }

  }
  public String getTitle(){
    return QO.CaptionText;
  }

  /**
   * 2010-6-9 zhanges
   * 调用主窗口的“打印”按钮
   */
  public boolean canPrint() {
      return true;
  }
  public void printNode() {
      printBook();
  }

  /**
     * 2010-6-9 zhanges
     * 调用主窗口的“打印设置”按钮
   */
  public boolean canPagesetup() {
      return true;
  }

  public void pageSetup(){
    this.ProcessmnPageSetup(null, null, null, null);
  }

  /**
     * 2010-6-9 zhanges
     * 调用主窗口的“打印预览”按钮
   */
  public boolean canPreview() {
      return true;
  }
  public void previewNode() {
    ProcessmnPrintPreview();
  }

  /**
   * 2010-6-9 zhanges
   * 调用主窗口的“导出”按钮
   */
  public boolean canExport() {
      return true;
  }
  public void exportData() {
    JBookActionImpl mActionImpl = new JGenerQueryActionImpl();
    JBookActionManager mActionMgr = new JBookActionManager(mActionImpl);
    try {
      String actionName = JBookActionConstants.ACTION_EXPORT;
      String defaultName = this.getTitle();
      JBook book = this.vwQueryView.getDataReport().getBook();
      if (book != null) {
        JBookAction action = new JBookAction(book, actionName, defaultName);
        mActionMgr.execute(action);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  public JBOFToolBar getMainToolbar(){
    return this.tbMainToolbar;
  }

  public JBOFToolBar getFormatToolbar(){
    return this.tbFormatToolbar;
  }

  public JBOFToolBar getQueryFormatToolbar(){
    return this.queryFormatToolbar;
  }

  public void setMainToolbar(JBOFToolBar mainToolbar){
    this.tbMainToolbar = mainToolbar;
  }

  public void setFormatToolbar(JBOFToolBar formatToolbar){
    this.tbFormatToolbar = formatToolbar;
  }

  public void setQueryFormatToolbar(JBOFToolBar queryFormatToolbar){
    this.queryFormatToolbar = queryFormatToolbar;
  }
}
