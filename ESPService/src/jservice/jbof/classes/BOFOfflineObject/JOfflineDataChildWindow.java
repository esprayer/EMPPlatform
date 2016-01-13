package jservice.jbof.classes.BOFOfflineObject;

import java.net.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import jbof.application.classes.*;
import jbof.gui.window.classes.*;
import jbof.gui.window.classes.style.mdi.*;
import jfoundation.dataobject.classes.*;
import jframework.resource.classes.*;
import jservice.jbof.classes.*;
import jframework.foundation.classes.JActiveDComDM;
import jfoundation.bridge.classes.JResponseObject;
import java.awt.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JOfflineDataChildWindow extends JBOFMDIChildWindow {
  JPanel MainPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable tabDataInfo = new JTable();
 //add by fsz
  JScrollPane jScrollTreePane = new JScrollPane();
  JSplitPane jSplitPane1 = new JSplitPane();
//
  Jtree_actionAdapter selAdapter=new Jtree_actionAdapter(this);
  final String[] ColNames = {
    "字典名称",
    "字典表名",
    "编号列名",
    "责任中心过滤",
    "数据权限过滤",
    "清除原有数据",
    "错误信息"
  };
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JLabel lbText = new JLabel();
  JCheckBox cbSJQX = new JCheckBox();
  JCheckBox cbZRZX = new JCheckBox();
  BorderLayout borderLayout4 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();
//  Thread thread;
  public static int ThreadType = 0;
  public JProgressBar pbProcess = new JProgressBar();
  TableModel dataModel;
  JCheckBox bnDeleteData = new JCheckBox();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  FlowLayout flowLayout3 = new FlowLayout();
  JButton bnStop = new JButton();
  boolean IsStop = false;
  JCheckBox cbUserDB = new JCheckBox();
  String OldString = null;
  JTree jTree1 = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JOfflineDataChildWindow() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    MainPanel.setLayout(borderLayout2);
    jPanel2.setLayout(borderLayout3);
    lbText.setForeground(new Color(160, 0, 0));
    lbText.setText("下载离线数据须在在线状态下才能正确执行！");
    cbSJQX.setText("数据权限");
    cbSJQX.addActionListener(new JOfflineDataChildWindow_cbSJQX_actionAdapter(this));
    cbZRZX.setText("责任中心");
    cbZRZX.addActionListener(new JOfflineDataChildWindow_cbZRZX_actionAdapter(this));
    jPanel1.setLayout(borderLayout4);
    jPanel4.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    jPanel3.setMaximumSize(new Dimension(32767, 32767));
    jPanel3.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    pbProcess.setString("");
    pbProcess.setValue(0);
    pbProcess.setStringPainted(true);
    bnDeleteData.setActionCommand("jCheckBox4");
    bnDeleteData.setText("清除数据");
    bnDeleteData.addActionListener(new JOfflineDataChildWindow_bnDeleteData_actionAdapter(this));
    jPanel5.setLayout(borderLayout5);
    jPanel6.setLayout(flowLayout3);
    flowLayout3.setAlignment(FlowLayout.RIGHT);
    flowLayout3.setHgap(2);
    flowLayout3.setVgap(2);
    bnStop.setEnabled(false);
    bnStop.setMnemonic('S');
    bnStop.setText("停止(S)");
    bnStop.addActionListener(new JOfflineDataChildWindow_bnStop_actionAdapter(this));
    cbUserDB.setText("帐套方式");
    cbUserDB.addActionListener(new JOfflineDataChildWindow_cbUserDB_actionAdapter(this));
//    jTextField1.setText("不下载数据");
    jSplitPane1.setDebugGraphicsOptions(0);
    jSplitPane1.setContinuousLayout(false);
    jSplitPane1.setDividerSize(5);
    jSplitPane1.setLastDividerLocation(100);
    jPanel4.add(cbUserDB, null);
    jPanel4.add(bnDeleteData, null);
    this.add(MainPanel,  BorderLayout.CENTER);
    MainPanel.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jPanel4, BorderLayout.CENTER);
    jPanel4.add(cbZRZX, null);
    jPanel4.add(cbSJQX, null);
    MainPanel.add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(pbProcess, BorderLayout.CENTER);
    jPanel5.add(bnStop, BorderLayout.EAST);
    jPanel2.add(jPanel6,  BorderLayout.EAST);
    MainPanel.add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.add(jScrollTreePane,jSplitPane1.LEFT);
    jSplitPane1.add(jScrollPane1,jSplitPane1.RIGHT);
    //add by fsz
//    new JTree()
    SelfTreeNode root=new SelfTreeNode("全部","ALL");
    String[] asData;
    for(int i=0;i<JBOFOfflineObject.OfflineDataLbList.size();i++){
      asData=(String[])JBOFOfflineObject.OfflineDataLbList.get(i);
      SelfTreeNode elem = new SelfTreeNode(asData[0],asData[1]);
      //elem.
      root.add(elem);
    }
    jTree1=new JTree(root);
    jTree1.addTreeSelectionListener(this.selAdapter);
    jTree1.setSelectionInterval(0,0);
    //
    jScrollTreePane.getViewport().add(jTree1, null);
    jScrollPane1.getViewport().add(tabDataInfo, null);
    jPanel1.add(jPanel3, BorderLayout.WEST);
    jPanel3.add(lbText, null);
    jSplitPane1.setDividerLocation(250);

  }
  public void TreeSelectionChange(javax.swing.event.TreeSelectionEvent e){
    if(e.getOldLeadSelectionPath()==null)return;
    javax.swing.tree.TreePath[] path=jTree1.getSelectionPaths();
    SelfTreeNode node;
    JBOFOfflineObject.OfflineDataInfoList.clear();
    tabDataInfo.repaint();
    int k=0;
    for(int i=0;i<JBOFOfflineObject.OfflineDataInfoListbak.size();i++){
      JOfflineDataInfoStub o=(JOfflineDataInfoStub)JBOFOfflineObject.OfflineDataInfoListbak.get(i);
      for (int j = 0; j < path.length; j++) {
        node = (SelfTreeNode) path[j].getLastPathComponent();
        if(node.ifContiantable(o.name)){
          JBOFOfflineObject.OfflineDataInfoList.add(o);
          break;
        }
      }
    }
    //必须改一下,否则整个jSplitPane不repaint
    //造成JTable只repaint一部分。
    if(jSplitPane1.getDividerLocation()==250)
      jSplitPane1.setDividerLocation(249);
    else
      jSplitPane1.setDividerLocation(250);

    tabDataInfo.selectAll();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void LoadGUIResource() {
    Object Key;String ClassName;
    try {
      String URI=null,RB=null;Object AO;
      RB = this.getClass().getName().replace('.','/');;
      RB = "Codebase/"+RB;
      URL url = JXMLResource.LoadXML(this,"REMenu.xml");
      URI = url.toString();
      MainMenu = new JMenuBar();
      AO = LoadChildGUIMenu(URI,MainMenu,RB,null,"toolbars","mainmenu");
      RegUserMenuBar(MainMenu);
      RegXMLToolbarList(XMLToolbarList);
      TablePopupMenu = new JPopupMenu();
      LoadPopupGUIMenu(URI,TablePopupMenu,RB,AO,"notoolbar","popupmenu1");
      RegUserPopopMenu(TablePopupMenu);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object InitChildWindow(JBOFApplication App,JBOFMainWindow MainWindow,Object AddObject,Object AddObject1) {
    InitTable();
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void InitTable() {
    dataModel = new OfflineTableModel(tabDataInfo,this);
    tabDataInfo.setModel(dataModel);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void StartRun() {
    // 如果线程类型等于0，则是本地数据初始化
    IsStop = false;setMenu(false);
    if ( ThreadType == 0 ) {
      InitLocalDB();
    }
    // 下载数据
    if ( ThreadType == 1 ) {
      DownDataInfo();
    }
    IsStop = true;setMenu(true);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void InitLocalDB() {
    int i,Row;
    javax.swing.event.TreeSelectionListener[] a = jTree1.getTreeSelectionListeners();
    for(int j=0;j<a.length;j++)
      jTree1.removeTreeSelectionListener(a[j]);
   try{
     int[] SelRows = this.tabDataInfo.getSelectedRows();
     int Count = SelRows.length;
     JOfflineDataInfoStub ODIS, ODISBAK;
     String Text;
     JParamObject PO;
     JResponseObject RO;
     pbProcess.setMinimum(0);
     pbProcess.setMaximum(Count);
     PO = JParamObject.Create();//(JParamObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod(
//         "EnvironmentObject", "CreateParamObjectDOM");
     // 设置用户方式还是帐套方式
     PO.SetIntByParamName("OFFLINE_USER_DB", cbUserDB.isSelected() ? 1 : 0);
     // 设置OFFLINE标志和离线初始化状态
     PO.SetValueByEnvName("OFFLINEINIT", "1");
     PO.SetValueByEnvName("OFFLINE", "1");
     for (i = 0; i < Count; i++) {
       if (IsStop)break;
       Row = SelRows[i];
       ODIS = (JOfflineDataInfoStub) dataModel.getValueAt(Row, 0);
       this.pbProcess.setValue(i + 1);
       Text = "正在创建" + ODIS.text + "！当前进行：" + String.valueOf(i + 1) + "/" +
           String.valueOf(Count);
       pbProcess.setString(Text);
       PO.SetValueByParamName("TABNAME", ODIS.name);
       RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
           InvokeObjectMethod("OfflineObject", "CreateTableForLocalDB", PO,
                              ODIS.SQL);
       ODIS.IsErr = RO.ErrorCode != 0;
       ODIS.ErrorString = RO.ErrorString;
       //
       ODISBAK = null;
       for (int j = 0; j < JBOFOfflineObject.OfflineDataInfoListbak.size(); j++) {
         ODISBAK = (JOfflineDataInfoStub) (JBOFOfflineObject.
                                           OfflineDataInfoList.get(j));
         if (ODISBAK.name.equals(ODIS.name))
           break;
       }
       if (ODISBAK != null) {
         ODISBAK.IsErr = RO.ErrorCode != 0;
         ODISBAK.ErrorString = RO.ErrorString;
       }
       //
       this.tabDataInfo.repaint();
     }
     if (IsStop)
       pbProcess.setString(pbProcess.getString() + "----用户终止！");
     else
       pbProcess.setString("初始化完成本地数据存储！");
   }finally{
     jTree1.addTreeSelectionListener(this.selAdapter);
   }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void DownDataInfo() {
    int[] SelRows = tabDataInfo.getSelectedRows();int i,Row;
    JOfflineDataInfoStub ODIS,ODISBAK;TableModel dataModel;String Text;
    JParamObject PO;Object XMLTable;JResponseObject RO;
    dataModel = tabDataInfo.getModel();
    java.util.Hashtable hs = new java.util.Hashtable();
    pbProcess.setMinimum(0);
    pbProcess.setMaximum(SelRows.length);
    PO = JParamObject.Create();//(JParamObject)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject","CreateParamObjectDOM");
    PO.SetIntByParamName("OFFLINE_USER_DB",cbUserDB.isSelected()?1:0);
    javax.swing.event.TreeSelectionListener[] a = jTree1.getTreeSelectionListeners();
     for(int j=0;j<a.length;j++)
       jTree1.removeTreeSelectionListener(a[j]);
    try{
      for (i = 0; i < SelRows.length; i++) {
        if (IsStop)break;
        Row = SelRows[i];
        ODIS = (JOfflineDataInfoStub) dataModel.getValueAt(Row, 0);
        // 如果不需要下载，继续
        if (ODIS.DNDATA.equals("0"))continue;
        //
        PO.SetValueByEnvName("OFFLINE", "0");
        PO.SetValueByEnvName("OFFLINEINIT", "1");
        //
        this.pbProcess.setValue(i + 1);
        // 从在线数据库中读取数据
        Text = "正在从在线" + ODIS.text + "中读取数据！当前进度：" + String.valueOf(i + 1) +
            "/" + String.valueOf(SelRows.length);
        pbProcess.setString(Text);
        // 设置获取字典的信息
        PO.SetValueByParamName("TABNAME", ODIS.name);
        // 如果设置了数据权限
        if (ODIS.isQX.booleanValue())
          PO.SetValueByParamName("F_SJQX", ODIS.F_SJQX);
        else
          PO.SetValueByParamName("F_SJQX", "");
          // 如果设置责任中心
        if (ODIS.isZX.booleanValue())
          PO.SetValueByParamName("F_CODE", ODIS.F_CODE);
        else
          PO.SetValueByParamName("F_CODE", "");
        PO.SetValueByParamName("F_BHZD", ODIS.F_BHZD);
        PO.SetValueByParamName("CODE_TYPE", ODIS.CODE_TYPE);
        PO.SetValueByParamName("QXBZW", ODIS.QXBZW);
        PO.SetValueByParamName("WHERE", ODIS.WHERE);

//      PO.SetValueByParamName("");
        RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
            InvokeObjectMethod("OfflineObject", "QueryOfflineDataInfo", PO);

        if (RO.ErrorCode == 0) {
          XMLTable =  RO.ResponseObject;
          PO.SetValueByEnvName("OFFLINE", "1");
          if (ODIS.isDel.booleanValue()) {
            PO.SetValueByParamName("DELETE", "1");
          }
          else {
            PO.SetValueByParamName("DELETE", "0");
          }
          Text = "正在向离线数据库中写入" + ODIS.text + "数据！当前进度：" + String.valueOf(i + 1) +
              "/" + String.valueOf(SelRows.length);
          OldString = Text;
          pbProcess.setString(Text);
          if (IsStop)break;

          hs.put("DATA", XMLTable);
          hs.put("OBJ", this);
          RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
              InvokeObjectMethod("OfflineObject", "SaveOfflineDataInfo", PO, hs); //XMLTable);
          ODIS.IsErr = RO.ErrorCode != 0;
          ODIS.ErrorString = RO.ErrorString;
          this.tabDataInfo.repaint();
        }
        else {
          ODIS.IsErr = RO.ErrorCode != 0;
          ODIS.ErrorString = RO.ErrorString;
          this.tabDataInfo.repaint();
        }
        //
        ODISBAK = null;
        for (int j = 0; j < JBOFOfflineObject.OfflineDataInfoListbak.size(); j++) {
          ODISBAK = (JOfflineDataInfoStub) (JBOFOfflineObject.
                                            OfflineDataInfoList.get(j));
          if (ODISBAK.name.equals(ODIS.name))
            break;
        }
        if (ODISBAK != null) {
          ODISBAK.IsErr = RO.ErrorCode != 0;
          ODISBAK.ErrorString = RO.ErrorString;
        }
        //end
      }
      if (IsStop)
        pbProcess.setString(pbProcess.getString() + "----用户终止！");
      else
        pbProcess.setString("离线数据已经全部下载到本地数据存储，您可以进行正确的离线操作！");
      OldString = null;
    }finally{
        jTree1.addTreeSelectionListener(this.selAdapter);
    }
  }
  public void SetSaveProcessIndex(String text) {
    pbProcess.setString(OldString+text);
  }
  public Boolean IsStopProcess() {
    return new Boolean(this.IsStop);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void setMenu(boolean value) {
    this.setMenuEnabled("mnCreateStruct",value);
    this.setMenuEnabled("mnDownData",value);
    this.setMenuEnabled("mnUpData",value);
    this.setMenuEnabled("mnSynData",value);
    this.bnStop.setEnabled(!value);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object ActionmnCreateStruct(Object p1,Object p2,Object p3,Object p4) {
    ThreadType = 0;
    Thread thread = new Thread( new JOfflineDataInfoObject());
    thread.start();
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object ActionmnDownData(Object p1,Object p2,Object p3,Object p4) {
    ThreadType = 1;
    Thread thread = new Thread(new JOfflineDataInfoObject());
    thread.start();
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object ActionmnUpData(Object p1,Object p2,Object p3,Object p4) {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object ActionmnSynData(Object p1,Object p2,Object p3,Object p4) {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void cbSelectAll_actionPerformed(ActionEvent e) {
    this.tabDataInfo.selectAll();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void cbZRZX_actionPerformed(ActionEvent e) {
    Boolean Value = new Boolean(this.cbZRZX.isSelected());
    setZRZX(Value);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void setZRZX(Boolean Value) {
    int[] SelRows = this.tabDataInfo.getSelectedRows();
    int i,Row;JOfflineDataInfoStub ODIS;
    for(i=0;i<SelRows.length;i++) {
      Row = SelRows[i];
      if ( this.dataModel.isCellEditable(Row,3) ) {
        this.dataModel.setValueAt(Value,Row,3);
      }
    }
    tabDataInfo.repaint();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void cbSJQX_actionPerformed(ActionEvent e) {
    Boolean Value = new Boolean(this.cbSJQX.isSelected());
    setSJQX(Value);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void setSJQX(Boolean Value) {
    int[] SelRows = this.tabDataInfo.getSelectedRows();
    int i,Row;JOfflineDataInfoStub ODIS;
    for(i=0;i<SelRows.length;i++) {
      Row = SelRows[i];
      if ( this.dataModel.isCellEditable(Row,4) ) {
        this.dataModel.setValueAt(Value,Row,4);
      }
    }
    tabDataInfo.repaint();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void bnDeleteData_actionPerformed(ActionEvent e) {
    Boolean Value = new Boolean(this.bnDeleteData.isSelected());
    setDEL(Value);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void setDEL(Boolean Value) {
    int[] SelRows = this.tabDataInfo.getSelectedRows();
    int i,Row;JOfflineDataInfoStub ODIS;
    for(i=0;i<SelRows.length;i++) {
      Row = SelRows[i];
      if ( this.dataModel.isCellEditable(Row,5) ) {
        this.dataModel.setValueAt(Value,Row,5);
      }
    }
    tabDataInfo.repaint();
  }

  void bnStop_actionPerformed(ActionEvent e) {
    IsStop = true;bnStop.setEnabled(false);
  }

  void cbUserDB_actionPerformed(ActionEvent e) {
    if ( cbUserDB.isSelected() ) {
      cbUserDB.setText("用户方式");
    } else {
      cbUserDB.setText("帐套方式");
    }
  }
}
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
class OfflineTableModel extends AbstractTableModel {
  JTable Table;JOfflineDataChildWindow Window;
  public OfflineTableModel(JTable tab,JOfflineDataChildWindow wnd) {
    Table = tab;Window = wnd;
  }
  public int getColumnCount() {
    return Window.ColNames.length;
  }

  public int getRowCount() {
    return JBOFOfflineObject.OfflineDataInfoList.size();
  }

  public Object getValueAt(int row, int col) {
    JOfflineDataInfoStub ODIS = (JOfflineDataInfoStub) JBOFOfflineObject.OfflineDataInfoList.get(row);

    Object cp;javax.swing.table.TableCellRenderer TCR;
    java.awt.Color BackColor=java.awt.Color.white,FrontColor=java.awt.Color.black;
    // 只处理错误信息列
    if ( col == 6 ) {
      boolean IsErr;
      IsErr = ODIS.IsErr;
      if (!IsErr) {
        if (ODIS.DNDATA.equals("0")) { // 如查不需要下载，用淡黄色显示
          BackColor = java.awt.Color.gray; //java.awt.Color.red;//java.awt.Color.white;
          FrontColor = java.awt.Color.black;
        }
        else {
          BackColor = java.awt.Color.white; //java.awt.Color.red;//java.awt.Color.white;
          FrontColor = java.awt.Color.black;
        }
      }
      if (IsErr) {
        BackColor = java.awt.Color.red;
        FrontColor = java.awt.Color.white;
      }
    }
      TCR = Table.getCellRenderer(row, col);
      if (TCR != null) {
        try {
          cp = TCR.getTableCellRendererComponent(Table, "", true, true, row,
                                                 col);
          if (cp != null && cp instanceof java.awt.Component) {
            ( (java.awt.Component) (cp)).setBackground(BackColor);
            ( (java.awt.Component) (cp)).setForeground(FrontColor);
          }
        }
        catch (Exception e) {}
      }
    switch (col) {
      case 0:
        return ODIS;
      case 1:
        return ODIS.name;
      case 2:
        return ODIS.F_BHZD;
      case 3:
        return ODIS.isZX;
      case 4:
        return ODIS.isQX;
      case 5:
        return ODIS.isDel;
      case 6:
        return ODIS.ErrorString;
    }
    return null;
  }

  public String getColumnName(int column) {
    return Window.ColNames[column];
  }

  public Class getColumnClass(int c) {
    switch (c) {
      case 0:
        return new String().getClass();
      case 1:
        return new String().getClass();
      case 2:
        return new String().getClass();
      case 3:
        return new Boolean(true).getClass();
      case 4:
        return new Boolean(true).getClass();
      case 5:
        return new Boolean(true).getClass();
      case 6:
        return new String().getClass();
    }
    return null;
  }

  public boolean isCellEditable(int row, int col) {
    JOfflineDataInfoStub ODIS = (JOfflineDataInfoStub) JBOFOfflineObject.
        OfflineDataInfoList.get(row);
    if ( col == 3 ) {
      return ( ODIS.F_CODE != null && ODIS.F_CODE.trim().equals("1") );
    }
    if ( col == 4 ) {
      return ( ODIS.F_SJQX != null && ODIS.F_SJQX.trim().length() != 0 );
    }
    if ( col == 5 ) return true;
    return false;
  }

  public void setValueAt(Object aValue, int row, int column) {
    JOfflineDataInfoStub ODISBAK=null,ODIS = (JOfflineDataInfoStub) JBOFOfflineObject.
        OfflineDataInfoList.get(row);
     //同时将OfflineDataInfoListbak里面的数据更新
     for(int i=0; i<JBOFOfflineObject.OfflineDataInfoListbak.size();i++){
       ODISBAK=(JOfflineDataInfoStub)(JBOFOfflineObject.OfflineDataInfoList.get(i));
       if(ODISBAK.name.equals(ODIS.name))
         break;
     }
     if(ODISBAK==null)return;
    if (column == 3) {
      ODIS.isZX = (Boolean) aValue;
      ODISBAK.isZX= (Boolean) aValue;
      return;
    }
    if (column == 4) {
      ODIS.isQX = (Boolean) aValue;
      ODISBAK.isQX = (Boolean) aValue;
      return;
    }
    if (column == 5) {
      ODIS.isDel = (Boolean) aValue;
      ODISBAK.isDel = (Boolean) aValue;
      return;
    }
  }
}

class JOfflineDataChildWindow_cbZRZX_actionAdapter implements java.awt.event.ActionListener {
  JOfflineDataChildWindow adaptee;

  JOfflineDataChildWindow_cbZRZX_actionAdapter(JOfflineDataChildWindow adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cbZRZX_actionPerformed(e);
  }
}

class JOfflineDataChildWindow_cbSJQX_actionAdapter implements java.awt.event.ActionListener {
  JOfflineDataChildWindow adaptee;

  JOfflineDataChildWindow_cbSJQX_actionAdapter(JOfflineDataChildWindow adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cbSJQX_actionPerformed(e);
  }
}

class JOfflineDataChildWindow_bnDeleteData_actionAdapter implements java.awt.event.ActionListener {
  JOfflineDataChildWindow adaptee;

  JOfflineDataChildWindow_bnDeleteData_actionAdapter(JOfflineDataChildWindow adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnDeleteData_actionPerformed(e);
  }
}

class JOfflineDataChildWindow_bnStop_actionAdapter implements java.awt.event.ActionListener {
  JOfflineDataChildWindow adaptee;

  JOfflineDataChildWindow_bnStop_actionAdapter(JOfflineDataChildWindow adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnStop_actionPerformed(e);
  }
}

class JOfflineDataChildWindow_cbUserDB_actionAdapter implements java.awt.event.ActionListener {
  JOfflineDataChildWindow adaptee;

  JOfflineDataChildWindow_cbUserDB_actionAdapter(JOfflineDataChildWindow adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cbUserDB_actionPerformed(e);
  }
}
class Jtree_actionAdapter implements javax.swing.event.TreeSelectionListener{
    JOfflineDataChildWindow adaptee;
    Jtree_actionAdapter(JOfflineDataChildWindow adaptee) {
     this.adaptee = adaptee;
   }
  public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
    adaptee.TreeSelectionChange(e);
   }
  }
class  SelfTreeNode  extends DefaultMutableTreeNode{
  String Tables="";
  public SelfTreeNode(Object ob,String tab){
    super(ob);
    Tables=tab;
  }
  public boolean ifContiantable(String tabname){
    String asTemp;
    if(Tables.equals("ALL"))return true;
    if(Tables.indexOf(tabname)==-1)return false;
    asTemp=Tables.substring(Tables.indexOf(tabname));
    if(asTemp.indexOf(",")!=-1)
      asTemp = asTemp.substring(0,asTemp.indexOf(","));
    return tabname.equals(asTemp.trim());
  }
}

