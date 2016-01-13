package jenterprise.bof.classes.AppExplorerObject;


import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;

import org.jdom.*;
import com.borland.jbcl.layout.*;
import com.eai.resources.*;
import com.eai.toolkit.xml.*;
import com.pansoft.pub.comp.*;
import jbof.application.classes.*;
import jbof.gui.window.classes.style.mdi.*;
import jcomponent.custom.swing.*;
import jenterprise.bof.classes.*;
import jenterprise.bof.classes.AppExplorerObject.noticeService.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;
import jtoolkit.scheduler.*;
import jtoolkit.xml.classes.*;
import jfoundation.bridge.classes.JResponseObject;
import com.eai.resources.TImages;
import com.eai.toolkit.xml.XmlEngine;
import com.eai.toolkit.xml.XmlEngineEnv;
import com.pansoft.pub.util.Debug;
import jtoolkit.string.classes.TString;
import jenterprise.bof.classes.AppExplorerObject.messlist.JIconList;
import jbof.application.classes.operate.JOperateItemStub;
//import com.pansoft.workflow.appov.client.pfawake.JPFAwakeAdapter;
//import com.pansoft.workflow.appov.client.pfawake.JIPFconList;
//import jservice.jbof.classes.GenerQueryObject.JQueryWaitPanel;
import com.pansoft.common.JWaitDialog;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skylin
//修改:
//--------------------------------------------------------------------------------------------------

public class JFunctionListPanel
    extends JBOFMDIChildWindow implements ActionListener, MouseListener, Runnable {
    static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
    static ResourceBundle Panres = null;
  private static boolean SERVER_REMOTE     = false; //true 取标准服务地址的配置文件;false 取本地应用的配置文件；
    private static final String T_LOCAL_TYPE = "CLSID_BusinessActiveObjectCategory";
    private static final String T_RMT_TYPE   = "CLSID_AbstractDataActiveObjectCategory";
    private static final String T_FINANCE    = "Financing.bof.classes";
    private static final String T_ZJServer   = "ZJServer";

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel pnFunction = new JPanel();
    JSplitPane spnMainFrame = new JSplitPane();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel pnFuncTree = new JPanel();
    JPanel pnLeftLeft = new JPanel();
    JPanel pnLeftRight = new JPanel();
    JButton bnOpenAll = new JButton();
    JButton bnClose = new JButton();
//  JButton bnAttrib = new JButton();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JSplitPane spnRight = new JSplitPane();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout7 = new BorderLayout();
    JSplitPane spnRightBottom = new JSplitPane();
    JRequestPanel jPanel2 = null; //new JRequestPanel();
//    JMessagePanel jPanel3 = null; //new JMessagePanel();
    BorderLayout borderLayout8 = new BorderLayout();
    BorderLayout borderLayout9 = new BorderLayout();
    JButton bnCloseAll = new JButton();
    JButton bnOpen = new JButton();
    DefaultMutableTreeNode EnterPriseNode = null;
    JScrollPane jScrollPane1 = new JScrollPane();
    JBOFTree trFuncTree = new JBOFTree();
    private TitledBorder titledBorder1;
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JList lstFavList = new JList();
    private java.util.Vector MenuList = new java.util.Vector();
    private JPanel jPanel4 = new JPanel();
    private JPanel jPanel5 = new JPanel();
    private JButton bnAddFav = new JButton();
    private JButton bnDelFav = new JButton();
    private BorderLayout borderLayout11 = new BorderLayout();
    private JLabel lbFavList = new JLabel();
    BorderLayout borderLayout6 = new BorderLayout();
    JPanel pnLeftTop = new JPanel();
    JLabel lbFuncDes2 = new JLabel();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel lbFuncDes1 = new JLabel();
    javax.swing.JTabbedPane tabPane = new JTabbedPane();
    javax.swing.JPanel iconlistPane = new JPanel();
    javax.swing.JScrollPane scrPane = new JScrollPane(iconlistPane);
    BorderLayout borderLayout10 = new BorderLayout();
    JIconList inactiveCodeList;
    JIconList noticeCodeList;
    VerticalFlowLayout verticalFlowLayout10 = new VerticalFlowLayout();

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JFunctionListPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JFunctionListPanel(JBOFApplication App, JActiveObject AO) {
        super(App, AO);
        try {
            jbInit();
        } catch (Exception e) {
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
        verticalFlowLayout10.setAlignment(verticalFlowLayout10.TOP);
        verticalFlowLayout10.setVerticalFill(true);
        iconlistPane.setLayout(verticalFlowLayout10);
        iconlistPane.setBackground(Color.white);
        scrPane.setBackground(Color.white);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        titledBorder1 = new TitledBorder("");
        this.setLayout(borderLayout1);
        pnFunction.setLayout(borderLayout2);
        spnMainFrame.setDividerSize(5);
        pnFuncTree.setLayout(borderLayout5);
        pnLeftLeft.setLayout(verticalFlowLayout1);
        pnLeftRight.setLayout(borderLayout4);
        verticalFlowLayout1.setHgap(0);
        verticalFlowLayout1.setVgap(0);
        spnRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
        spnRight.setDividerSize(5);
        spnRight.setLastDividerLocation(30);
        jPanel1.setLayout(borderLayout7);
        spnRightBottom.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
//        spnRightBottom.setBottomComponent(jPanel3);
        spnRightBottom.setContinuousLayout(false);
        spnRightBottom.setDividerSize(5);
        spnRightBottom.setLastDividerLocation(300);
//    jPanel2.setLayout(borderLayout8);
//    jPanel3.setLayout(borderLayout9);
        trFuncTree.setRootVisible(true);
        //trFuncTree.setModel(TreeModel);
//    pnLeftTop.setOpaque(true);
        jPanel5.setLayout(borderLayout6);
        jPanel4.setLayout(borderLayout11);
        lbFavList.setForeground(Color.black);
        lbFavList.setBorder(titledBorder1);
        lbFavList.setText(res.getString("String_32"));
        jPanel4.setPreferredSize(new Dimension(144, 24));
        lbFuncDes2.setToolTipText("");
        lbFuncDes2.setText(res.getString("String_34"));
        pnLeftTop.setLayout(borderLayout3);
        lbFuncDes1.setText(res.getString("String_35"));
        verticalFlowLayout10.setVgap(0);
        pnLeftLeft.add(bnOpenAll, null);
        pnLeftLeft.add(bnOpen, null);
        pnLeftLeft.add(bnCloseAll, null);
        pnLeftLeft.add(bnClose, null);
        pnLeftLeft.add(bnAddFav, null);
//    pnLeftLeft.add(bnAttrib, null);
        pnFuncTree.add(pnLeftRight, BorderLayout.CENTER);
        pnLeftRight.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(trFuncTree, null);
        //tpExplorer.add(pnFunction, "功能列表");
        pnFunction.add(spnMainFrame, BorderLayout.CENTER);
        pnFuncTree.add(pnLeftLeft, BorderLayout.WEST);
        spnMainFrame.add(pnFuncTree, JSplitPane.LEFT);
        jPanel1.add(jScrollPane2, BorderLayout.CENTER);
        jPanel1.add(jPanel4, BorderLayout.NORTH);
        jPanel4.add(jPanel5, BorderLayout.EAST);
        jPanel5.add(bnDelFav, BorderLayout.EAST);
        jPanel4.add(lbFavList, BorderLayout.CENTER);
        jScrollPane2.getViewport().add(lstFavList, null);
        spnMainFrame.add(tabPane, JSplitPane.RIGHT);
        //不要快捷窗口和消息窗口了
//        spnRight.add(spnRightBottom, JSplitPane.BOTTOM);
//        spnRight.add(tabPane, JSplitPane.TOP);
        //tpExplorer.add(pnFlow, "流程列表");
        //tpExplorer.add(pnDocument, "文档列表");
        //this.add(tpExplorer,  BorderLayout.CENTER);
        this.add(pnFunction, BorderLayout.CENTER);
        pnFuncTree.add(pnLeftTop, BorderLayout.NORTH);
        CreateEnterPriseNode();
        ListAllApplicationMenu();
        trFuncTree.list();
        ProcessButton();
        LoadFavList();
//    InitRequest();
        spnRightBottom.setDividerLocation(360);
        InitPanel();

        tabPane.add(res.getString("String_36"), scrPane);
        tabPane.add(res.getString("String_37"), jPanel2);
        tabPane.setSelectedComponent(scrPane);


        /**
         * 设置树的绘制器。
         */
        trFuncTree.setCellRenderer(new JFunctionNodeRender());
        trFuncTree.setRowHeight(18);
        GUIPrepare();
        spnMainFrame.setDividerLocation(270);
        spnRight.setDividerLocation(320);
        messagePrepare();
        /**
     * 流程消息提示
     */
    this.workflowMessagePrepare();
    }
    void InitPanel() {
     jPanel2 = new JRequestPanel();
//     jPanel3 = new JMessagePanel();
//     spnRightBottom.add(jPanel3, JSplitPane.RIGHT);
     spnRightBottom.add(jPanel1, JSplitPane.LEFT);
 }

    private void workflowMessagePrepare() {
        //调度任务的对象
        java.util.List taskList = new ArrayList();

//        JIPFconList iconList = null;
        //根据类别个数创建多个消息块
        String[] ids = JNoticeParamObject.WF_TYPE_ID;
        String[] mcs = JNoticeParamObject.WF_TYPE_MC;
        for (int i = 0, n = ids.length; i < n; i++) {
//          iconList = null;//new JIPFconList(mcs[i], ids[i]);
//
//          iconlistPane.add(iconList.getParentPanel());
//          taskList.add(iconList);

        }

        /**
         * 增加消息列表启用开关和时间间隔开关，如果设置的时间间隔小于15分钟，则默认是15分钟。
         */
        JParamObject PO = JParamObject.Create();
        //时间迭代器的对象
        int defaultInterval = PO.GetIntByEnvName("SC_WF_MESS_GET_MINUTE", 60);
        if (defaultInterval < 15) {
          defaultInterval = 15;
        }
        Integer time = new Integer(defaultInterval * 60 * 1000);
        //新建一个适配器
        String isGetMess = PO.GetValueByEnvName("SC_WF_MESS_GET", "1");
        if ("1".equals(isGetMess)) {
//          JPFAwakeAdapter adapter = new JPFAwakeAdapter(PO, taskList, time);
//          adapter.start();
        }
      }

    /**
     * 处理消息面板
     */
    private void messagePrepare() {
        //调度任务的对象
        java.util.List taskList = new ArrayList();
        //根据类别个数创建多个消息块
        String[] ids = JNoticeParamObject.TYPE_ID;
        String[] mcs = JNoticeParamObject.TYPE_MC;
        JIconList iconList;
        for (int i = 0, n = ids.length; i < n; i++) {
            iconList = new JIconList(mcs[i], ids[i]);
            iconlistPane.add(iconList.getParentPanel());
            taskList.add(iconList);
        }

        /**
         * 增加消息列表启用开关和时间间隔开关，如果设置的时间间隔小于15分钟，则
         * 默认是15分钟。
         * modified by gengeng 2008-6-18
         */
        JParamObject PO = JParamObject.Create();
        //时间迭代器的对象
        int defaultInterval = PO.GetIntByEnvName("SC_MESS_GET_MINUTE", 60);
        if (defaultInterval < 15) {
            defaultInterval = 15;
        }
        Integer time = new Integer(defaultInterval * 60 * 1000);
        //新建一个适配器
        String isGetMess = PO.GetValueByEnvName("SC_MESS_GET", "1");
        if ("1".equals(isGetMess)) {
            JNoticeAdapter adapter = new JNoticeAdapter(PO, taskList, time);
            adapter.start();
        }
    }

    public void GUIPrepare() {
        lbFavList.setIcon(TImages.getIcon("SHORT"));
    }
    private void ProcessButton() {
        bnDelFav.setText(res.getString("String_39"));
        bnDelFav.addActionListener(new JFunctionListPanel_bnDelFav_actionAdapter(this));
        ImageIcon II;
        Insets is = new Insets(2, 2, 2, 2);
        try {
            II = JXMLResource.LoadImageIcon(this, "delf.gif");
            bnDelFav.setIcon(II);
            bnDelFav.setMargin(is);
            bnDelFav.setToolTipText(res.getString("String_41"));
            II = JXMLResource.LoadImageIcon(this, "addf.gif");
            bnAddFav.setIcon(II);
            bnAddFav.setMargin(is);
            bnAddFav.setToolTipText(res.getString("String_43"));
            II = JXMLResource.LoadImageIcon(this, "exone.gif");
            bnOpen.setIcon(II);
            bnOpen.setMargin(is);
            bnOpen.setToolTipText(res.getString("String_45"));
            II = JXMLResource.LoadImageIcon(this, "exall.gif");
            bnOpenAll.setIcon(II);
            bnOpenAll.setMargin(is);
            bnOpenAll.setToolTipText(res.getString("String_47"));
            II = JXMLResource.LoadImageIcon(this, "closeone.gif");
            bnClose.setIcon(II);
            bnClose.setMargin(is);
            bnClose.setToolTipText(res.getString("String_49"));
            II = JXMLResource.LoadImageIcon(this, "closeall.gif");
            bnCloseAll.setIcon(II);
            bnCloseAll.setMargin(is);
            bnCloseAll.setToolTipText(res.getString("String_51"));
            II = JXMLResource.LoadImageIcon(this, "function.gif");
            JIConListCellRenderer ICR = new JIConListCellRenderer(II);
            lstFavList.setCellRenderer(ICR);
            //
        } catch (Exception e) {
            e.printStackTrace();
        }
        trFuncTree.addMouseListener(this);
        bnOpenAll.addActionListener(this);
        bnOpen.addActionListener(this);
        bnCloseAll.addActionListener(this);
        bnClose.addActionListener(this);
//      bnAttrib.addActionListener(this);
        bnAddFav.addActionListener(this);
        bnDelFav.addActionListener(this);
        lstFavList.addMouseListener(this);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ListAllApplicationMenu() {
        JBOFApplicationStub BOFApplicationStub;
        DefaultMutableTreeNode treenode;
        JMenuItemObjectStub nodeobject;
        Vector definedMenuItem = this.getDefindMenu();
        for (int i = 0; i < JBOFAppExplorerObject.AppList.size(); i++) {
            BOFApplicationStub = (JBOFApplicationStub) JBOFAppExplorerObject.AppList.get(i);
            nodeobject = new JMenuItemObjectStub();
            nodeobject.Name = BOFApplicationStub.name;
            nodeobject.Caption = BOFApplicationStub.description;

            /**
             * 设置应用结点的图标。
             */
            nodeobject.Icon = BOFApplicationStub.getIconName();

            treenode = new DefaultMutableTreeNode(nodeobject);
            BOFApplicationStub.AddObject1 = treenode;
            EnterPriseNode.add(treenode);
            ListApplicationMenu(BOFApplicationStub, definedMenuItem);
        }
        DefaultListModel ListModel;
        ListModel = new DefaultListModel();
        this.lstFavList.setModel(ListModel);

        ListModel = new DefaultListModel();
        DefaultTreeModel TreeModel;
        TreeModel = new DefaultTreeModel(EnterPriseNode);
        trFuncTree.setModel(TreeModel);
        trFuncTree.list();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ListApplicationMenu(JBOFApplicationStub BOFApplicationStub, Vector definedMenuItem) {
        String XMLGuiDefineFile;
        JXMLResourceReadObject XMLObject;
        Element node;
//      XMLGuiDefineFile  = JActiveDComDM.CodeBase+BOFApplicationStub.name+"/Resource/";
//      XMLGuiDefineFile += JActiveDComDM.International+"/"+BOFApplicationStub.XML_STYLE_MDI;
        java.net.URL url = JXMLResource.LoadSXML(BOFApplicationStub.name + "/Resource/", BOFApplicationStub.XML_STYLE_MDI);
        XMLGuiDefineFile = url.toString();
        XMLObject = new JXMLResourceReadObject(XMLGuiDefineFile);
        node = XMLObject.GetElementByName("mainmenu");
        //Yrh 2006-07-23 Begin
        AddDefinedMenu(BOFApplicationStub.name, definedMenuItem, XMLObject, node);
        //Yrh 2006-07-23 End
        CreateTree(XMLObject, node, (DefaultMutableTreeNode) BOFApplicationStub.AddObject1, BOFApplicationStub.AddObject1.toString());
        if(getHideMenu() ){
          disposeTree( (DefaultMutableTreeNode) BOFApplicationStub.AddObject1);
        }
    }
    /**
     * 不显示非明细菜单项
     * @param addObject1 DefaultMutableTreeNode
     */
    private void disposeTree(DefaultMutableTreeNode addObject1){
      for(int j=0;j<addObject1.getDepth();j++){
        if(addObject1.getChildCount()>0){
          for(int i=0;i<addObject1.getChildCount();i++){
            DefaultMutableTreeNode aa = (DefaultMutableTreeNode) addObject1.
                getChildAt(i);
            if (aa.isLeaf()) {
              JMenuItemObjectStub nodeobject = (JMenuItemObjectStub) aa.
                  getUserObject();
              if (nodeobject.OIS == null) {
                addObject1.remove(i);
              }
            }else{
              disposeTree(aa);
            }
          }
        }
      }
    }
    /**
     * 是否需要隐藏无功能权限的菜单 add xft 2008.06.13
     * @return boolean
     */
    private boolean getHideMenu(){
      String ENV_HIDEMENU = "";
      ENV_HIDEMENU = System.getProperties().getProperty("ENV_HIDEMENU","");
      if(ENV_HIDEMENU!=null && ENV_HIDEMENU.trim().equals("1")){
        return true;
      }else{
        return false;
      }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private DefaultMutableTreeNode CreateTree(JXMLResourceReadObject XMLObject, Element node, DefaultMutableTreeNode treenode, String Captions) {
        int Index = 0;
        Element nd;
        java.util.List nodelist;
        DefaultMutableTreeNode td;
        String Caption;
        JMenuItemObjectStub nodeobject;
        boolean ibHideMenu = false;
        String pServerType = "";
        nodelist = XMLObject.BeginEnumerate(node);
        if (nodelist != null) {
            while (true) {
                ibHideMenu = false;
                nd = XMLObject.Enumerate(nodelist, Index);
                if (nd == null)break;
                Index++;
//               if ( nd.getNodeType() == nd.ELEMENT_NODE ) {
                Caption = XMLObject.GetElementValue( (Element) nd, "caption");

                if (Caption.equals("-") == true)continue;
                nodeobject = new JMenuItemObjectStub();
                MenuList.add(nodeobject);
                nodeobject.Name = XMLObject.GetElementValue( (Element) nd, "name");
                nodeobject.Caption = Caption;
                nodeobject.Captions = Captions + "\\" + Caption;
                nodeobject.OperateItem = XMLObject.GetElementValue( (Element) nd, "operateitem");
                nodeobject.Icon = XMLObject.GetElementValue( (Element) nd, "icon");
                nodeobject.OIS = JBOFAppExplorerObject.FindOperateFromList(nodeobject);
                td = new DefaultMutableTreeNode(nodeobject);
                nodeobject.TreeNodeObject = td;
                td.setUserObject(nodeobject);

                if(getHideMenu() && nodeobject.OIS!=null ){
                  /**
                   * 处理隐藏没有功能权限的菜单项 add xft 2008.06.13
                   * 需要根据nodeobject.OIS中的对象名，检查是否是远程服务，如
                   * 果是远程服务则需要调用远程服务取当前登录用户所具有的功能权限编号；
                   * 注：不用每次都取一遍用户所具有的功能权限列表，每一个远程服务取一次即可
                   */
                  pServerType = getServerName(nodeobject.OIS.OperateObject);
                  ibHideMenu = checkGnQx(nodeobject.OIS,pServerType);
                  if(ibHideMenu){
                    treenode.add(td);
                  }
                }else{
                  treenode.add(td);
                }
                CreateTree(XMLObject, nd, td, nodeobject.Captions);
//               }
            }
        }
        XMLObject.EndEnumerate();
        return treenode;
    }
    /**
     * 获取所有功能权限列表，检查是否有权限
     * @param OIS JOperateItemStub
     * @param pServerType String
     * @return boolean
     */
    private HashMap gnqxServerList = new HashMap();
    private boolean checkGnQx(JOperateItemStub OIS,String pServerType){
      boolean ibGnqx = true;
      String sGnqx = "0";
      HashMap gnqxList = (HashMap)gnqxServerList.get(pServerType);
      if(gnqxList==null){
        JParamObject PO = (JParamObject) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
        if (!JActiveDComDM.SystemOffline) {
            /**
             * 根据这个菜单项，找到它的BOF对象，查得它是否是一个远程对象。
             * 如果定义了Server项，则设置CommBase。让检查功能权限的操作，调往这个指定的服务器。
             */
            String pRmtSvrUrl = getServerUrl(OIS.OperateObject, PO);
            if (pRmtSvrUrl != null) {
                //人为设置调用的执行服务器。
                PO.SetValueByParamName("CommBase", pRmtSvrUrl);
            }

            String pSvrID = getServerId(OIS.OperateObject, PO);
            String pPOText = null;
            JParamObject pPNew = null;
            JResponseObject RO = null;
            if (pSvrID != null) {
                pPOText = JParamObject.getPOByServer(pSvrID);
                pPNew = new JParamObject(pPOText);
            } else {
                pPOText = PO.GetRootXMLString();
                pPNew = new JParamObject(pPOText);
            }
            pPNew.SetValueByParamName("Server", pSvrID);
            pPNew.SetValueByParamName("CommBase", pRmtSvrUrl);

            //获取功能权限列表的调用。
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
                InvokeObjectMethod("SecurityObject", "EmptyMethod", pPNew);

            pPOText = null;
            if (pSvrID != null) {
                pPOText = JParamObject.getPOByServer(pSvrID);
            } else {
                pPOText = PO.GetRootXMLString();
            }
            pPNew = new JParamObject(pPOText);
            pPNew.SetValueByParamName("Server", pSvrID);
            pPNew.SetValueByParamName("CommBase", pRmtSvrUrl);
            Debug.PrintlnMessageToSystem("xxxx pPNew:" + pSvrID);
            Debug.PrintlnMessageToSystem("xxxx pPNew:" + pRmtSvrUrl);
            Debug.PrintlnMessageToSystem("xxxx pPNew:" + pPNew.GetValueByEnvName("SQMS", ""));
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
                InvokeObjectMethod("SecurityObject", "getFunctionLimitList", pPNew);
            if(RO==null || RO.ErrorCode<0){
              gnqxList = null;
            }else{
              gnqxServerList.put(pServerType, RO.ResponseObject);
              gnqxList = (HashMap) RO.ResponseObject;
            }
        }

      }
      if(gnqxList!=null){
        if(OIS.OperateNo==null || OIS.OperateNo.trim().equals("")){
          ibGnqx = true;
        }else{
          sGnqx = (String) gnqxList.get(OIS.OperateNo);
          if (sGnqx == null) {
            sGnqx = "0";
          }
          if (sGnqx.trim().equals("1")) {
            ibGnqx = true;
          }
          else {
            ibGnqx = false;
          }
        }
      }else{
        ibGnqx = true;
      }
      return ibGnqx;
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void CreateEnterPriseNode() {
        JMenuItemObjectStub nodeobject = new JMenuItemObjectStub();

        String pTitle = null;
        try {
          if ( Panres == null){
            Panres = ResourceBundle.getBundle("fmis.publicresource.Language");
          }
          pTitle = Panres.getString("SOFT_DESCRIPTION");
        }
        catch (Exception E) {

        }

        if ( pTitle == null ){
          pTitle = res.getString("String_61");
        }

        nodeobject.Caption = pTitle;
        nodeobject.Icon = "SYS_EXPLORER";
        EnterPriseNode = new DefaultMutableTreeNode(nodeobject);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void mouseClicked(java.awt.event.MouseEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ListDoubleClick(int Row) {
        if (Row > -1) {
            DefaultListModel ListModel;
            JFavMenuItemObjectStub FMOS;
            ListModel = (DefaultListModel) lstFavList.getModel();
            FMOS = (JFavMenuItemObjectStub) ListModel.getElementAt(Row);
            if (FMOS != null) {
                CallOperateItem(FMOS.MOS);
            }
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void mouseDragged(MouseEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // user code begin {1}
        // user code end

        // user code begin {2}
        // user code end
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void mouseExited(java.awt.event.MouseEvent e) {
        // user code begin {1}
        // user code end
        // user code begin {2}
        // user code end
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void mouseMoved(MouseEvent e) {
        System.out.println("mouseMoved");
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (e.getSource() == trFuncTree) {
            int selRow = trFuncTree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = trFuncTree.getPathForLocation(e.getX(), e.getY());
            if (selRow != -1) {
                if (e.getClickCount() == 1) {
                    TreeSingleClick(selRow, selPath);
                } else if (e.getClickCount() == 2) {
                    TreeDoubleClick(selRow, selPath);
                }
            }
        }
        if (e.getSource() == this.lstFavList) {
            if (e.getClickCount() == 2) {
                int index = lstFavList.locationToIndex(e.getPoint());
                ListDoubleClick(index);
            }
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void TreeSingleClick(int Row, TreePath Path) {
        JMenuItemObjectStub MOS;
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode) Path.getLastPathComponent();
        MOS = (JMenuItemObjectStub) node.getUserObject();
        this.lbFuncDes1.setText(Path.toString().replace(',', '/'));
        if (MOS.OIS != null) {
            this.lbFuncDes2.setText(MOS.OIS.Description);
        } else {
            this.lbFuncDes2.setText(MOS.Caption);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void TreeDoubleClick(int Row, TreePath Path) {
        JMenuItemObjectStub MOS;
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode) Path.getLastPathComponent();
        MOS = (JMenuItemObjectStub) node.getUserObject();
        if (MOS.OIS != null) {
            CallOperateItem(MOS);
        }
    }

    //----------------------------------------------------------------------------------------------
    //描述: 调用应用中某一个对象的某一个方法
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public static Object CallOperateItem(JMenuItemObjectStub MOS) {
        JResponseObject RO = null;
        // 没有定义，就不检查
        if (MOS.OIS.OperateNo == null || MOS.OIS.OperateNo.trim().length() == 0) {
            return JActiveDComDM.BusinessActiveFramework.MInvokeObjectMethod(MOS.OIS.OperateObject, MOS.OIS.OperateMethod, MOS.OIS.ParamString,
                MOS.OIS.ParamData, MOS, JBOFAppExplorerObject.BOFApplication);
        }
        // 在执行操作之前首先要检查当前用户是否有执行此项功能的权限
        JParamObject PO = (JParamObject) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
        PO.SetValueByParamName("FunctionNo", MOS.OIS.OperateNo); // 此项属于参数

        if (!JActiveDComDM.SystemOffline) {

            /**
             * 根据这个菜单项，找到它的BOF对象，查得它是否是一个远程对象。
             * 如果定义了Server项，则设置CommBase。让检查功能权限的操作，调往这个指定的服务器。
             */
            String pRmtSvrUrl = getServerUrl(MOS, PO);
            if (pRmtSvrUrl != null) {
                //人为设置调用的执行服务器。
                PO.SetValueByParamName("CommBase", pRmtSvrUrl);
            }

            String pSvrID = getServerId(MOS, PO);
            String pPOText = null;
            JParamObject pPNew = null;
            if (pSvrID != null) {
                pPOText = JParamObject.getPOByServer(pSvrID);
                pPNew = new JParamObject(pPOText);
                /**
                 * 如果是远程服务
                 * 需要检查当前程序版本与总部是否一致
                 * add by hufeng 2007.10.31
                 */
                JParamObject tmpPO = PO.Create();
                String localVer  = tmpPO.GetValueByEnvName(pSvrID + "Ver","");
                String serverVer = pPNew.GetValueByEnvName(pSvrID + "Ver","");
                if(!serverVer.equals(localVer)){
                    String mess = TString.F(res.getString("String_415"),localVer,serverVer,localVer.compareTo(serverVer) > 0 ? res.getString("String_416"):res.getString("String_417"));
//                    String mess = new res.getString("String_415");
                    JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,mess);
                    return null;
                }
            } else {
                pPOText = PO.GetRootXMLString();
                pPNew = new JParamObject(pPOText);
            }
            pPNew.SetValueByParamName("Server", pSvrID);
            pPNew.SetValueByParamName("CommBase", pRmtSvrUrl);

            pPNew.SetValueByParamName("FunctionNo", MOS.OIS.OperateNo); // 此项属于参数
            //检查功能权限的调用。
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
                InvokeObjectMethod("SecurityObject", "EmptyMethod", pPNew);

            pPOText = null;
            if (pSvrID != null) {
                pPOText = JParamObject.getPOByServer(pSvrID);
            } else {
                pPOText = PO.GetRootXMLString();
            }
            pPNew = new JParamObject(pPOText);
            pPNew.SetValueByParamName("Server", pSvrID);
            pPNew.SetValueByParamName("CommBase", pRmtSvrUrl);
            pPNew.SetValueByParamName("FunctionNo", MOS.OIS.OperateNo); // 此项属于参数
            Debug.PrintlnMessageToSystem("xxxx pPNew:" + pSvrID);
            Debug.PrintlnMessageToSystem("xxxx pPNew:" + pRmtSvrUrl);
            Debug.PrintlnMessageToSystem("xxxx pPNew:" + pPNew.GetValueByEnvName("SQMS", ""));
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
                InvokeObjectMethod("SecurityObject", "CheckFunctionLimit", pPNew);

        }
        if ( (RO != null && RO.GetErrorCode() == 0) || JActiveDComDM.SystemOffline) {
            Object pRES = JActiveDComDM.BusinessActiveFramework.MInvokeObjectMethod(MOS.OIS.OperateObject, MOS.OIS.OperateMethod, MOS.OIS.ParamString,
                MOS.OIS.ParamData, MOS, JBOFAppExplorerObject.BOFApplication);
            System.out.println(MOS.OIS.OperateObject+" "+ MOS.OIS.OperateMethod);
            PO.SetValueByParamName("CommBase", "");

            return pRES;
        } else {
          JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.getMainWindow(), RO.ErrorString == null ? res.getString("String_85") : RO.ErrorString, res.getString("String_86"), JOptionPane.ERROR_MESSAGE);
//            JOptionPane.showMessageDialog(this, RO.ErrorString == null ? res.getString("String_85") : RO.ErrorString, res.getString("String_86"), JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    /**
     * 根据对象取得Server值
     * @param sOperateObject String
     * @return String
     */
    private String getServerName(String sOperateObject){
      String pServerType = "";
      JActiveObjectStub pStub = JManagerActiveObject.FindActiveObjectStub(sOperateObject, T_LOCAL_TYPE);
      if (pStub != null) {
          pServerType = pStub.mRmtServer == null ? "" : pStub.mRmtServer;
      }
      if(pServerType.equals("")){
          return "";
      }
      return pServerType;
    }

    private static String getServerUrl(String sOperateObject, JParamObject PO){
      JActiveObjectStub pStub = JManagerActiveObject.FindActiveObjectStub(sOperateObject, T_LOCAL_TYPE);

      String pServerType = "";
      if (pStub != null) {
          pServerType = pStub.mRmtServer == null ? "" : pStub.mRmtServer;
      }
      if(pServerType.equals("")){
          return null;
      }

      /**
       * 如果服务器信息没有被初始化过。则初始化。
       */
      if (JAppSvrManager.getDefault().getServerCount() == 0) {
          LoadAppSvrInfo(PO);
      }

      /**
       * 如果服务器被指定，则取出这个服务器的URL。放入strCommBase
       */
      if (!pServerType.equals("")) {
          JAppSvrStub pSvr = JAppSvrManager.getDefault().getServer(
              pServerType);
          if (pSvr != null) {
              PO.SetValueByParamName("Server", pSvr.getName());
              return pSvr.getUrl();
          }
      }

      return null;

    }

    private static String getServerUrl(JMenuItemObjectStub MOS, JParamObject PO) {
      return getServerUrl(MOS.OIS.OperateObject,PO);
    }

    private static String getServerId(String sOperateObject, JParamObject PO) {
      JActiveObjectStub pStub = JManagerActiveObject.FindActiveObjectStub(sOperateObject, T_LOCAL_TYPE);

      String pServerType = "";
      if (pStub != null) {
          pServerType = pStub.mRmtServer == null ? "" : pStub.mRmtServer;
      }
      if (pServerType.equals("")) {
          return null;
      }

      /**
       * 如果服务器信息没有被初始化过。则初始化。
       */
      if (JAppSvrManager.getDefault().getServerCount() == 0) {
          LoadAppSvrInfo(PO);
      }

      /**
       * 如果服务器被指定，则取出这个服务器的URL。放入strCommBase
       */
      if (!pServerType.equals("")) {
          JAppSvrStub pSvr = JAppSvrManager.getDefault().getServer(
              pServerType);
          if (pSvr != null) {
              PO.SetValueByParamName("Server", pSvr.getName());
              return pSvr.getName();
          }
      }

      return null;

    }

    private static String getServerId(JMenuItemObjectStub MOS, JParamObject PO) {
      return getServerId(MOS.OIS.OperateObject,PO);
    }

    public static void LoadAppSvrInfo(JParamObject pPO) {
        JResponseObject RO;
        String pText = null;

        pText = SERVER_REMOTE ? onLoadAppSvrInfoRmt(pPO) : onLoadAppSvrInfoMine(pPO);

        /**
         * 下面装入。
         */
        if (pText != null) {
            XmlEngine Xml = new XmlEngine(pText);

            Xml.OpenRootSection("EAIServers");
            String[] pSvrNames = Xml.getChildrenName();

            /**
             * 取出所有SVR。
             */
            XmlEngineEnv pEnv = Xml.SaveEnv();
            for (int i = 0; i < pSvrNames.length; i++) {
                Xml.OpenChildrenSection(pSvrNames[i]);

                String pName = pSvrNames[i];
                String pCap = Xml.getKeyValue("Caption", "");
                String pUrl = Xml.getKeyValue("Url", "");
                String pCls = Xml.getKeyValue("ClassMask", "");

                if (pCls == null || pCls.equals("")) {
                    //如果是空的，则不处理。当成没有。
                } else {
                    JAppSvrStub pStub = new JAppSvrStub(pName, pCap, pUrl, pCls);
                    JAppSvrManager.getDefault().addServer(pStub);
                }

                Xml.RestoreEnv(pEnv);
            }
        }
    }

    private static String onLoadAppSvrInfoRmt(JParamObject pPO) {
        String pText = null;

        JResponseObject RO;
        String pOldUrl = pPO.GetValueByParamName("CommBase");
        String pBZUrl = pPO.GetValueByEnvName("STANDARD_SERVICE");
        try {
            pPO.SetValueByParamName("CommBase", pBZUrl);
            pPO.SetValueByParamName("FileName", "EAIServers.xml");
            // 从服务器返回新的财务环境
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
                InvokeObjectMethod("EnvironmentObject", "getOnlineFileText", pPO);
            if (RO.ErrorCode == 0) {
                // 重新设置环境对象
                pText = (String) RO.ResponseObject;
                Debug.PrintlnMessageToSystem(pText);
            }
        } catch (Exception E) {
            System.err.println(res.getString("String_110"));
            return pText;
        } finally {
            pPO.SetValueByParamName("CommBase", pOldUrl);
        }
        return pText;
    }

    private static String getDefaultUrl() {
        String s = JActiveDComDM.CodeBase;
        s = s.substring(0, s.indexOf("Codebase"));
        s = s + "EAIServer";
        return s;
    }

    private static String onLoadAppSvrInfoMine(JParamObject pPO) {
        String pText = null;

        JResponseObject RO;
        try {
            pPO.SetValueByParamName("CommBase", getDefaultUrl());
            pPO.SetValueByParamName("FileName", "EAIServers.xml");
            // 从服务器返回新的财务环境
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
                InvokeObjectMethod("EnvironmentObject", "getOnlineFileText", pPO);
            if (RO.ErrorCode == 0) {
                // 重新设置环境对象
                pText = (String) RO.ResponseObject;
                Debug.PrintlnMessageToSystem(pText);
            }
        } catch (Exception E) {
            System.err.println(res.getString("String_119"));
            return pText;
        } finally {
            pPO.SetValueByParamName("CommBase", "");
        }
        return pText;
    }

    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void run() {
        return;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // user code begin {1}
        // user code end
        // user code begin {2}
        // user code end
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bnOpenAll) {
            ProcessOpenAll();
        }
        if (e.getSource() == bnOpen) {
            ProcessOpen();
        }
        if (e.getSource() == bnCloseAll) {
            ProcessCloseAll();
        }
        if (e.getSource() == bnClose) {
            ProcessClose();
        }
//    if ( e.getSource() == bnAttrib ) {
//      ProcessAttrib();
//    }
        if (e.getSource() == bnAddFav) {
            ProcessAddFav();
        }
        if (e.getSource() == bnDelFav) {
            ProcessDelFav();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessOpenAll() {
        TreePath tp = this.trFuncTree.getSelectionPath();
        trFuncTree.ExpandAll(tp);
    }

//  //------------------------------------------------------------------------------------------------
//  //描述:
//  //设计: Skyline(2001.12.29)
//  //实现: Skyline
//  //修改:
//  //------------------------------------------------------------------------------------------------
//  void ExpandAll(TreePath tp) {
//    DefaultMutableTreeNode node,node1;TreePath tp2;
//    trFuncTree.expandPath(tp);
//    node = (DefaultMutableTreeNode)tp.getLastPathComponent();
//    for(int i=0;i<node.getChildCount();i++) {
//      node1 = (DefaultMutableTreeNode)node.getChildAt(i);
//      if ( node1.isLeaf() ) continue;
//      tp2 = new TreePath(node1.getPath());
//      ExpandAll(tp2);
//    }
//  }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessOpen() {
        trFuncTree.expandPath(this.trFuncTree.getSelectionPath());
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessCloseAll() {
        TreePath tp = this.trFuncTree.getSelectionPath();
        trFuncTree.CollapseAll(tp);
    }

//  //------------------------------------------------------------------------------------------------
//  //描述:
//  //设计: Skyline(2001.12.29)
//  //实现: Skyline
//  //修改:
//  //------------------------------------------------------------------------------------------------
//  void CollapseAll(TreePath tp) {
//    DefaultMutableTreeNode node,node1;TreePath tp2;
//    node = (DefaultMutableTreeNode)tp.getLastPathComponent();
//    for(int i=0;i<node.getChildCount();i++) {
//      node1 = (DefaultMutableTreeNode)node.getChildAt(i);
//      if ( node1.isLeaf() ) continue;
//      tp2 = new TreePath(node1.getPath());
//      CollapseAll(tp2);
//    }
//    trFuncTree.collapsePath(tp);
//  }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessClose() {
        trFuncTree.collapsePath(this.trFuncTree.getSelectionPath());
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessAttrib() {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessAddFav() {
        TreePath tp = this.trFuncTree.getSelectionPath();
        DefaultListModel ListModel;
        ListModel = (DefaultListModel)this.lstFavList.getModel();
        AddFav(tp, ListModel);
        SaveFavList();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void AddFav(TreePath tp, DefaultListModel ListModel) {
        DefaultMutableTreeNode node, node1;
        TreePath tp2;
        JMenuItemObjectStub nodeobject;
        node = (DefaultMutableTreeNode) tp.getLastPathComponent();
        JFavMenuItemObjectStub FMOS;
        if (node.isLeaf()) {
            nodeobject = (JMenuItemObjectStub) node.getUserObject();
            if (nodeobject.OperateItem.trim().length() != 0) {
                FMOS = new JFavMenuItemObjectStub(nodeobject);
                ListModel.addElement(FMOS);
            }
            return;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            node1 = (DefaultMutableTreeNode) node.getChildAt(i);
            tp2 = new TreePath(node1.getPath());
            AddFav(tp2, ListModel);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessDelFav() {
        DefaultListModel ListModel;
        Object[] objects;
        ListModel = (DefaultListModel)this.lstFavList.getModel();
        objects = lstFavList.getSelectedValues();
        for (int i = 0; i < objects.length; i++) {
            ListModel.removeElement(objects[i]);
        }
        SaveFavList();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void SaveFavList() {
        JParamObject PO;
        String UserName;
        JFavMenuItemObjectStub mis;
        PO = (JParamObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
        UserName = PO.GetValueByEnvName("UserName");
        DefaultListModel ListModel;
        ListModel = (DefaultListModel)this.lstFavList.getModel();
        int Count = ListModel.getSize();
        JActiveDComDM.LocalRegistry.Put(UserName + "_FAVLIST_COUNT", String.valueOf(Count));
        for (int i = 0; i < Count; i++) {
            mis = (JFavMenuItemObjectStub) ListModel.getElementAt(i);
            if (mis.MOS.OperateItem.trim().length() != 0) {
                JActiveDComDM.LocalRegistry.Put(UserName + "_FAV_" + String.valueOf(i), mis.MOS.OperateItem);
            }
        }
        JActiveDComDM.LocalRegistry.Save();
    }

    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    void LoadFavList() {
        JParamObject PO;
        String UserName;
        JMenuItemObjectStub mis;
        String tmp;
        int Count = 0;
        DefaultListModel ListModel;
        JFavMenuItemObjectStub FMOS;
        ListModel = (DefaultListModel)this.lstFavList.getModel();
        PO = (JParamObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
        UserName = PO.GetValueByEnvName("UserName");
        tmp = JActiveDComDM.LocalRegistry.Get(UserName + "_FAVLIST_COUNT");
        if (tmp != null && tmp.trim().length() != 0) Count = Integer.valueOf(tmp).intValue();
        for (int i = 0; i < Count; i++) {
            tmp = JActiveDComDM.LocalRegistry.Get(UserName + "_FAV_" + String.valueOf(i));
            mis = FindMenuItemObjectStub(tmp);
            if (mis != null) {
                FMOS = new JFavMenuItemObjectStub(mis);
                ListModel.addElement(FMOS);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    JMenuItemObjectStub FindMenuItemObjectStub(String OperateItem) {
        int Count = MenuList.size();
        JMenuItemObjectStub mis;
        for (int i = 0; i < Count; i++) {
            mis = (JMenuItemObjectStub) MenuList.get(i);
            if (mis.OperateItem != null && mis.OperateItem.equals(OperateItem)) {
                return mis;
            }
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public String getChildWindowName() {
        return res.getString("String_132");
    }

    /**
     * 从自定义菜单
     * @return Vector
     */
    private Vector getDefindMenu() {
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("Type", "MDI");
        Vector definedMenuItem = null;
        JResponseObject RO = (JResponseObject) JActiveDComDM.
            AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject",
            "getDefineMenuItem", PO, null);
        if (RO != null) {
            definedMenuItem = (Vector) RO.ResponseObject;
        }
        return definedMenuItem;
    }

    /**
     * 增加自定义的菜单
     * @param XMLObject JXMLResourceReadObject
     * @param node Element
     */
    void AddDefinedMenu(String name, Vector definedMenuItem, JXMLResourceReadObject XMLObject, Element node) {
        for (int i = 0; i < definedMenuItem.size(); i++) {
            JMenuItemObjectStub MenuItem = (JMenuItemObjectStub) definedMenuItem.get(i);
            Element pnode = XMLObject.GetElementByName(node, MenuItem.Parent);
            if (pnode == null)continue;
            if (!MenuItem.MenuFileName.equals(name))continue;
//      Element menuitem = new Element("menuitem2");
            Element menuitem = new Element(MenuItem.Name);
            menuitem.setAttribute("name", MenuItem.Name);
            menuitem.setAttribute("caption", MenuItem.Caption);
            menuitem.setAttribute("operateitem", MenuItem.OperateItem);
            menuitem.setAttribute("ischild", "false");
            menuitem.setAttribute("icon", MenuItem.Icon);
            menuitem.setAttribute("selchar", "A");
            pnode.addContent(menuitem);
        }
    }

    void bnDelFav_actionPerformed(ActionEvent e) {

    }
}

class JFunctionListPanel_bnDelFav_actionAdapter
    implements java.awt.event.ActionListener {
    JFunctionListPanel adaptee;

    JFunctionListPanel_bnDelFav_actionAdapter(JFunctionListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bnDelFav_actionPerformed(e);
    }
}
