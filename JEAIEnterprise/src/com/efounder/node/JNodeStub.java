package com.efounder.node;

import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import org.openide.*;
import com.core.xml.*;
import com.efounder.actions.*;
import com.efounder.eai.*;
import com.efounder.eai.ide.*;
import com.efounder.node.view.*;
import com.efounder.pfc.window.*;
import com.efounder.view.*;
import com.efounder.pfc.dialog.*;
import com.efounder.model.biz.*;
import org.openide.util.RequestProcessor;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.*;
import java.awt.Frame;
import com.efounder.eai.ui.*;
import com.efounder.pub.comp.CompoundIcon;
import com.efounder.action.*;
import java.awt.Dialog;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
*对于一个节点来说有以下内容需要定义:
* 1.子节点类型集合
* 2.以子节点类型为Key的子节点Class列表
* 3.以子节点类型为Key的子节点数据对象的Class列表
* 4.以子节点类型为Key的子节点数据的列表
* 可以对子节点执行的操作如下:
* 1.增加 2.删除 3 修改 4 刷新
*/
public class JNodeStub extends DefaultMutableTreeNode implements BIZContext,NodeDataActionListener,ChildWindowListener {
  // 功能权限Key
  protected String SecurityKey   = null;
  // 功能权限Value
  protected String SecurityValue = null;
  // 数据权限Key
  protected String AccessKey     = null;
  // 数据权限Value
  protected String AccessValue   = null;

  // 列出该节点所能包含的全部子节点的类型,以字符串型式表式如:storageService,convertService等
  protected java.util.List    childNodeStubList = new ArrayList();// 子节点信息描述表,成员为StubObject
  protected StubObject       nodeStubObject    = null;// 本节点信息描述StubObject
  protected NodeDataManager  nodeDataManager   = null;// 节点的数据管理
  protected NodeDataStub     nodeDataStub      = null;// 节点数据对象
  protected Object           actionObject      = null;//
  protected JExplorerView    ExplorerView      = null;
//  protected NodeChildWindow  nodeWindow        = null;
  protected boolean          loaded = false;
  protected String ID         = null;
  protected String commandID  = null;
  protected String groupID    = null;
  protected Object nodeID     = null;
  protected String groupCaption = null;
  protected Icon   groupIcon    = null;
  protected String caption      = null;
  protected int    nodeType     = -1;
  /**
   *
   * @return List
   */
  public List getEntityList() {
    return null;
  }
  public List getEntityList(String key) {
    return null;
  }
  public void createEntityList() {

  }
  public void createEntityList(String key) {

  }
  /**
   *
   * @param nodeType int
   */
  public void setNodeType(int nodeType) {
    this.nodeType = nodeType;
  }
  /**
   *
   * @return int
   */
  public int getNodeType() {
    return nodeType;
  }
  protected int contextType = -1;
  /**
   *
   * @param contextType int
   */
  public void setContextType(int contextType) {
    this.contextType = contextType;
  }
  /**
   *
   * @return int
   */
  public int getContextType() {
    return this.contextType;
  }
  /**
   *
   * @param caption String
   */
  public void setCaption(String caption) {
    this.caption = caption;
  }
  protected static java.util.Map  nodeWindowList = null;
//  protected static java.util.Map  nodeExpWindowList = null;
  /**
   *
   * @param Key Object
   * @return NodeChildWindow
   */
  protected static NodeChildWindow getOpenNodeWindow(Object Key) {
    if ( nodeWindowList == null ) return null;
    return (NodeChildWindow)nodeWindowList.get(Key);
  }
//  protected static NodeExplorerWindow getOpenNodeExpWindow(Object Key) {
//    if ( nodeExpWindowList == null ) return null;
//    return (NodeExplorerWindow)nodeExpWindowList.get(Key);
//  }
  /**
   *
   * @param Key Object
   */
  protected static void removeOpenNodeWindow(Object Key) {
    if ( nodeWindowList == null ) return;
    nodeWindowList.remove(Key);
  }
//  protected static void removeOpenNodeExpWindow(Object Key) {
//    if ( nodeExpWindowList == null ) return;
//    nodeExpWindowList.remove(Key);
//  }
  /**
   *
   * @param Key Object
   * @param nodeWindow NodeChildWindow
   */
  protected static void registryOpenNodeWindow(Object Key,NodeChildWindow nodeWindow) {
    if ( !nodeWindow.isReMemberWnd() ) return;
    if ( nodeWindowList == null ) {
      nodeWindowList = new java.util.HashMap();
    }
    nodeWindow.setNodeKey(Key);
    nodeWindowList.put(Key,nodeWindow);
  }
//  protected static void registryOpenNodeExpWindow(Object Key,NodeExplorerWindow nodeWindow) {
//    if ( nodeExpWindowList == null ) {
//      nodeExpWindowList = new java.util.HashMap();
//    }
//    nodeWindow.setNodeKey(Key);
//    nodeExpWindowList.put(Key,nodeWindow);
//  }
  /**
   *
   * @return Object
   */
  public Object getNodeID() {
    if ( nodeID != null ) return nodeID;
    return this.getClass().getName()+this.hashCode();
  }
  public Icon getGroupIcon() {
    if ( groupIcon == null )
      return this.getNodeIcon();
    return groupIcon;
  }
  public void setGroupIcon(Icon icon) {
    groupIcon = icon;
  }
  /**
   *
   * @param id String
   */
  public void setGroupID(String id) {
    groupID = id;
  }
  /**
   *
   * @param caption String
   */
  public void setGroupCaption(String caption) {
    groupCaption = caption;
  }
  /**
   * 获取组ID
   * @return String
   */
  public String getGroupID() {
    return groupID;
  }
  /**
   * 获取组名称
   * @return String
   */
  public String getGroupCaption() {
    if ( groupCaption == null )
      return this.toString();
    return groupCaption;
  }
  /**
   * 获取此节点的命令ID
   * @return String
   */
  public String getCommandID() {
    return commandID;
  }
  /**
   * 设置此节点的命令ID
   * @param commandID String
   */
  public void setCommandID(String commandID) {
    this.commandID = commandID;
  }
  public String getLongText() {
    return toString();
  }
  public JNodeStub initCreateNode(String key,Context context) {
    return this;
  }
  /**
   *
   * @param key String
   * @param context Context
   * @return Object
   */
  public JNodeStub createObject(String key,Context context) {
    // 根据Key获取子节点的StubObject
    JNodeStub retNode = null;
    try {
      this.doLoad();
      context.setParentNodeStub(this);
      StubObject childSO = this.getChildNodeStubObject(this, key);
      if (nodeDataManager == null) {
        retNode = createOneChildObject(childSO, context);
      }
      else {
        //
        retNode = createChildObject(key, childSO, context);
      }
      if (retNode != null) {
        context.setNode(retNode);
        context.setBrowser(retNode.getExplorerView());
        // 创建初始化过程
        retNode = retNode.initCreateNode(key,context);
        if ( retNode != null && context.getCreateNodeView() )
          retNode.openObject(null, context);
      }
    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(0,e,this,"创建节点出错！");
    }
    return retNode;
  }
  /**
   *
   * @param childSO StubObject
   * @param context Context
   * @return Object
   */
  private JNodeStub createChildObject(String key,StubObject childSO,Context context) {
    // 根据子节点的StubObject创建其数据StubObject对象
    NodeDataStub nds   = this.createNodeDataStub(childSO,context,nodeDataManager);
    // 如果NodeDataSub创建成功，则再创建节点
    if ( nds != null ) {
      // 插入数据管理器中
      nodeDataManager.insertNodeData(key, nds, context);
    }
    return context.getRetNode();
  }
  /**
   * 如果一个节点没有数据管理对象,则每个类型的子节点只允许创建一个
   * @param childSO StubObject
   * @param context Context
   * @return Object
   */
  private JNodeStub createOneChildObject(StubObject childSO,Context context) {
    JNodeStub ns = this.createChildNode(this,childSO);
    if ( ns != null ) {
      // 如果不为空,则注册子节点
      ExplorerView.registryChildNode(this,ns);
      context.setRetNode(ns);
    }
    return ns;
  }
  /**
   *
   * @param key String
   * @param context Context
   * @return Object
   */
  public Object openObject(final String key,final Context context) {
    return openObject(this.getOpenNodeKey(),key,context);
  }
  /**
   *
   * @param nodeKey Object
   * @param key String
   * @param context Context
   * @return JPrepareNodeStub
   */
  JNodeStub getPrepareNodeStub(final Object nodeKey,final String key,
                                      final Context context) {
    // 获取nodeBuilderManager
    NodeBuilderManager nodeBuilderManager = NodeBuilderManager.getInstance(getPrepareObjectKey());
    if ( nodeBuilderManager == null ) return null;
    JNodeStub ns = nodeBuilderManager.constructOneNode(this,this,context,getPrepareObject());
    return ns;
  }
  protected String getPrepareObjectKey() {
    return this.getClass().getName();
  }
  protected Object getPrepareObject(){return null;};
  /**
   *
   * @return boolean
   */
  public boolean canOpenObject(final Object nodeKey,final String key,final Context context) {
    if (context.getNode() == null || context.getNode().getNodeStubObject() == null) return true;
    String mana = context.getNode().getNodeStubObject().getString("MANA", null);
    // 没指定MANA属性不判断
    if (mana == null || mana.trim().length() == 0) return true;
    JParamObject PO = JParamObject.Create();
    String userMana = PO.GetValueByEnvName("UserMANA", null);
    if (userMana == null) userMana = PO.GetValueByEnvName("UserF_MANA", null);
    // 产品登录时没加载F_MANA不判断
    if (userMana == null) return true;
    if (mana.indexOf(userMana) == -1) {
      context.setString("ErrorMessage", "您无权使用此功能！");
      return false;
    }
    return true;
  }
  /**
   *
   * @return String
   */
  public String getErrorMessage(final Object nodeKey,final String key,final Context context) {
    return context.getString("ErrorMessage", null);
  }
  /**
   *
   * @return NodeChildWindow
   */
//  public NodeChildWindow getNodeWindow() {
//    return this.getOpenNodeWindow(getOpenNodeKey());
//  }
  /**
   *
   * @return BIZContext
   */
  public BIZContext getBIZContext() {
    Object nodeKey = getOpenNodeKey();
    if ( nodeKey == null ) nodeKey = this.getNodeID();
    return (BIZContext)getOpenNodeWindow(nodeKey);
  }
  /**
   *
   * @return Object
   */
  public Object getOpenNodeKey() {
    if ( openNodeKey == null ) {
      return this.getClass().getName()+this.hashCode();
    }
    return openNodeKey;
  }
  /**
   *
   * @param onk Object
   */
  public void setOpenNodeKey(Object onk) {
    openNodeKey = onk;
  }
  /**
   *
   */
  protected Object openNodeKey = null;
//  public boolean prepareOpenObject(final Object nodeKey,final String key,final Context context) {
//    return true;
//  }
  protected void initOpen() {};
  protected void endOpen(){};
  /**
   *
   * @param key String
   * @param context Context
   * @return Object
   */
  public Object openObject(final Object nodeKey,final String key,final Context context) {
//    if ( this instanceof ExecuteNode ) {
//      return ((ExecuteNode)this).executeNode(nodeKey,key,context);
//    }
    initOpen();
    try {
      NodeChildWindow nodeWindow = this.getOpenNodeWindow(nodeKey);
      if (nodeWindow == null) {
//      // 准备
//      boolean prepareBoolean = prepareOpenObject(nodeKey,key,context);
//      // 如果没有准备，则返回
//      if ( !prepareBoolean ) return null;
        openNodeKey = nodeKey;
        context.setNodeKey(nodeKey);
        return invokeLaterOpenObject(nodeKey, key, context);
      }
      else {
        context.setNodeKey(nodeKey);
        nodeWindow.reInitNodeChildWindow(context);
        invokeLaterOpenObject(nodeKey, key, context);
        IView openView = getOpenView();
        openView.openChildWindow(nodeWindow, this.toString(), this.toString(),
                                 this.getNodeIcon());
        return nodeWindow;
      }
    } finally {
      endOpen();
    }
  }

  protected Object invokeLaterOpenObject(Object nodeKey,String key,Context context) {
    try {
      // 获取该节点所有的View工厂
      NodeViewerFactory[] nvfs = NodeViewerManager.getNodeViewerFactorys(
          context, this, ExplorerView);
      // 如果View工厂为空,则直接返回
      if (nvfs == null || nvfs.length == 0)return null;
      // 获取节点窗口
      NodeChildWindow nodeWindow = this.getOpenNodeWindow(nodeKey);
      if (nodeWindow == null) {
        nodeWindow = NodeChildWindow.getNodeChildWindow();
        // 设置是否记录窗口
        nodeWindow.setReMemberWnd(context.isReMemberWnd());
        nodeWindow.addWindowListener(this);
        this.registryOpenNodeWindow(nodeKey,nodeWindow);
      }
      //
      nodeWindow.setView(EnterpriseExplorer.ContentView);
      // 设置节点
      nodeWindow.setNodeStub(context.getNode());
      // 初始化节点窗口
      nodeWindow.initNodeChildWindow(context, (String) key, nvfs);
      String title = nodeWindow.getTitle();
      if ( title == null || "".equals(title.trim()) ) title = this.toString();
      String tips  = nodeWindow.getTips();
      if ( tips == null || "".equals(tips.trim()) ) tips = this.toString();
      //
      if (nodeWindow != null) {
        IView openView = getOpenView();
        openView.openChildWindow(nodeWindow,title, tips, this.getNodeIcon());
        invokeLater(nodeWindow,context, (String) key, nvfs);
        openView.showView(context);

        if ( openView instanceof Dialog ) {
          // 清除掉对话框里的自动状态组件
          ActionToolBar.removeAutoUpdateComponent((Dialog)openView);
        }

        int result = openView.getResult();
        // 如果是取消，则直接返回
        if ( result == JPDialog.RESULT_CANCEL ) {
          return null;
        }
        if ( result == JPDialog.RESULT_OK ) {
          context.setPrepareSucess(true);
        }
        //add by fsz 最大化
        if("1".equals(System.getProperty("maxwnd"))){
          try {
            if (! (openView instanceof JDialogView)) {
              if (nodeWindow.getSelectedNodeView().getNodeExpWindow() == null) {
                EnterpriseExplorer.ContentView.getViewDevice().setExtendedState(
                    EnterpriseExplorer.ContentView, Frame.MAXIMIZED_BOTH);
              }
            }
          }
          catch (Exception e) {}
        }

      }
      return nodeWindow;
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   *
   * @param nodeWindow NodeChildWindow
   * @param context Context
   * @param Key String
   * @param nvfs NodeViewerFactory[]
   * @throws Exception
   */
  protected void invokeLater(NodeWindow nodeWindow,Context context,String Key,NodeViewerFactory[] nvfs) throws Exception {
//    nodeWindow.initNodeChildWindow2(context, Key, nvfs);
    Runnable run = new NodeLakerInvoke(nodeWindow,context, Key, nvfs);
    SwingUtilities.invokeLater(run);
//    RequestProcessor.Task openNodeObjectTask = RequestProcessor.getDefault().create(run);
//    openNodeObjectTask.schedule(100);
  }
  public void setOpenPlace(int op) {
    openPlace = op;
  }
  protected int openPlace = ViewBuilder.CONTENT_VIEW;
  /**
   *
   * @return int
   */
  public int getOpenPlace() {
    if ( nodeStubObject != null ) {
      String sopenPlace = nodeStubObject.getString("openPlace","CONTENT_VIEW");
      if ( "CONTENT_VIEW".equals(sopenPlace) ) return ViewBuilder.CONTENT_VIEW;
      if ( "TOP_VIEW".equals(sopenPlace) ) return ViewBuilder.TOP_VIEW;
      if ( "EXPLORER_VIEW".equals(sopenPlace) ) return ViewBuilder.EXPLORER_VIEW;
      if ( "PROPERTY_VIEW".equals(sopenPlace) ) return ViewBuilder.PROPERTY_VIEW;
      if ( "MESSAGE_VIEW".equals(sopenPlace) ) return ViewBuilder.MESSAGE_VIEW;
      if ( "STRUCT_VIEW".equals(sopenPlace) ) return ViewBuilder.STRUCT_VIEW;
      if ( "DIALOG_VIEW".equals(sopenPlace) ) return ViewBuilder.DIALOG_VIEW;
      if ( "FLOAT_VIEW".equals(sopenPlace) ) return ViewBuilder.FLOAT_VIEW;
    }
    return openPlace;
  }
  /**
   *
   * @return IView
   */
  protected IView getOpenView() {
    IView openView = EnterpriseExplorer.ContentView;
    int op = getOpenPlace();
    if ( op == ViewBuilder.CONTENT_VIEW ) return EnterpriseExplorer.ContentView;
    if ( op == ViewBuilder.TOP_VIEW ) return EnterpriseExplorer.TopView;
    if ( op == ViewBuilder.EXPLORER_VIEW ) return EnterpriseExplorer.ExplorerView;
    if ( op == ViewBuilder.PROPERTY_VIEW ) return EnterpriseExplorer.PropertyView;
    if ( op == ViewBuilder.MESSAGE_VIEW ) return EnterpriseExplorer.MessageView;
    if ( op == ViewBuilder.STRUCT_VIEW ) return EnterpriseExplorer.StructView;
    if ( op == ViewBuilder.DIALOG_VIEW ) {
      openView = EnterpriseExplorer.getDialogView();
      openView.setTitle(this.toString());
      openView.setIcon(this.getNodeIcon());
      return openView;
    }
    if ( op == ViewBuilder.FLOAT_VIEW ) return EnterpriseExplorer.FloatView;
    return openView;
  }
  /**
   *
   * @return String
   */
  public String toString() {
    if ( caption != null ) {
      return caption;
    }
    if ( nodeDataStub != null ) {
      return nodeDataStub.toString();
    } else {
      if ( nodeStubObject != null )
        return nodeStubObject.toString();
      else
        return this.getClass().getName();
    }
  }

  public void setActionObject(Object actionObject) {
    this.actionObject = actionObject;
  }
  public Object getActionObject() {
    return actionObject;
  }
  public NodeDataStub getNodeDataStub() {
    return nodeDataStub;
  }
  public void setNodeDataStub(NodeDataStub dataStub) {
    nodeDataStub = dataStub;
    if ( nodeDataStub != null )
      nodeDataStub.setNodeStub(this);
  }
  public StubObject getNodeStubObject() {
    return nodeStubObject;
  }
  protected void setNodeStubObject(StubObject stubObject) {
    initNode(stubObject);
  }
  /**
   *
   * @param SO StubObject
   * @throws Exception
   */
  protected void initNode(StubObject SO) {
    // 该节点自己的StubObject
    nodeStubObject = SO;
    // 初始化
//    initStubObject(SO);
    // 初始化该节点的直接下级节点
    String ID = SO.getString("id",null);
//    if ( ID != null ) {
      initChildNode(ID);
//    }
    processSecurityKey(SO);
    // 获取该节点的节点数据管理器
    nodeDataManager = createNodeDataManager(SO);
    if ( nodeDataManager != null )
      nodeDataManager.setNodeStub(this);
    // 获取该节点的数据对象
//    nodeDataStub = createNodeDataStub(SO);
    //
    initNodeDataActionListener();
  }
  /**
   *
   * @param SO StubObject
   */
  protected void processSecurityKey(StubObject SO) {
    // 从StubObject中获取功能编号，并且设置功能编号
    String gnbh = SO.getString("GNBH",null);
    // 如果功能编号为空,则使用流程编号
    if ( gnbh == null ) {
      gnbh = SO.getString("flowid", null);
      if ( gnbh != null ) gnbh = "_FLOW_"+gnbh.trim();
    }
    this.setSecurityKey(gnbh);
  }
  /**
   *
   */
  protected void initNodeDataActionListener() {
    if ( nodeDataManager == null ) return;
    StubObject SO = null;
    if ( childNodeStubList.size() == 0 ) {
      nodeDataManager.addNodeDataActionListener(nodeDataManager.getClass().getName(),this);
    }
    for(int i=0;i<childNodeStubList.size();i++) {
      SO = (StubObject)childNodeStubList.get(i);
      nodeDataManager.addNodeDataActionListener((String)SO.getObject("id",null),this);
    }
  }
  /**
   *
   * @param SO StubObject
   * @throws Exception
   * @return NodeDataManager
   */
  public static NodeDataManager createNodeDataManager(StubObject SO) {
    Class nodeDataManagerClass = (Class)SO.getObject("nodeDataManagerClass",null);
    NodeDataManager o = null;String id = null;
    if ( nodeDataManagerClass != null ) {
      try {
        o = (NodeDataManager) nodeDataManagerClass.newInstance();
        id = SO.getString("id",null);
        if ( o != null && id != null ) {
          NodeDataManager.regNodeDataManager(id,o);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return o;
  }
  /**
   *
   * @param SO StubObject
   * @return NodeDataStub
   */
  public static NodeDataStub createNodeDataStub(StubObject SO,Context context,NodeDataManager nodeDataManager) {
    Class nodeDataClass = (Class)SO.getObject("nodeDataClass",null);NodeDataStub o = null;
    if ( nodeDataClass != null ) {
      try {
        o = (NodeDataStub) nodeDataClass.newInstance();
        o.setNodeDataManager(nodeDataManager);
        o.setNewCreate(true);
        o = o.initNodeData(SO,context);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return o;
  }
  /**
   *
   * @param SO StubObject
   * @return JNodeStub
   */
  public static JNodeStub createNodeStub(StubObject SO) {
    return createNodeStub(SO,null);
  }
  public static JNodeStub createNodeStub(StubObject SO,Object actionObject) {
//    initStubObject(SO);
    Class nodeClass = (Class)SO.getObject("nodeClass",null);JNodeStub o = null;
    if ( nodeClass == null ) return null;
    try {
      o = (JNodeStub)nodeClass.newInstance();
      o.setNodeStubObject(SO);o.setActionObject(actionObject);
    } catch ( Exception e ) {e.printStackTrace();}
    return o;
  }
  /**
   *
   * @param parentNode JNodeStub
   * @param Key String
   * @return StubObject
   */
  protected static StubObject getChildNodeStubObject(JNodeStub parentNode,String Key) {
    for(int i=0;i<parentNode.childNodeStubList.size();i++) {
      StubObject SO = (StubObject)parentNode.childNodeStubList.get(i);
      if ( Key.equals(SO.getObject("id",null)) )
        return SO;
    }
    return null;
  }
  /**
   *
   * @param parentNode JNodeStub
   * @param Key String
   * @return JNodeStub
   */
  public JNodeStub createChildNode(JNodeStub parentNode,String Key) {
    StubObject childSO = getChildNodeStubObject(parentNode,Key);
    return createChildNode(parentNode,childSO);
  }
  public JNodeStub createChildNode(JNodeStub parentNode,StubObject childSO) {
    if ( childSO == null ) return null;
    JNodeStub ns = createNodeStub(childSO);
    if ( ns == null ) return null;
    ns.setExplorerView(parentNode.getExplorerView());
    ns.setParentNode(parentNode);
    return ns;
  }
  /**
   *
   * @param SO StubObject
   */
  public static void initStubObject(StubObject SO) {
    Object Tmp=null;
    try {
      Tmp = SO.getObject("nodeClass",JNodeStub.class.getName());
      if ( Tmp != null && Tmp instanceof String ) {Class nodeClass = Class.forName(Tmp.toString());SO.setObject("nodeClass",nodeClass);}
    } catch ( Exception e ) {
      SO.setObject("nodeClass",JNodeStub.class);}
    try {
      Tmp = SO.getObject("nodeDataClass",null);
      if ( Tmp != null && Tmp instanceof String ) {Class nodeDataClass = Class.forName(Tmp.toString());SO.setObject("nodeDataClass",nodeDataClass);}
    } catch ( Exception e ) {SO.setObject("nodeDataClass",null);}
    try {
      Tmp = SO.getObject("nodeDataManagerClass",null);
      if ( Tmp != null && Tmp instanceof String ) {Class nodeDataManagerClass = Class.forName(Tmp.toString());SO.setObject("nodeDataManagerClass",nodeDataManagerClass);}
    } catch ( Exception e ) {SO.setObject("nodeDataManagerClass",null);}
    try {
      Tmp = SO.getObject("icon",null);
      if ( Tmp != null && Tmp instanceof String ) {
        Icon icon = ExplorerIcons.getImageIcon(SO, "/" + EAI.Product + "/Resource",Tmp.toString(), null);//ExplorerIcons.getExplorerIcon(Tmp);
        SO.setObject("icon",icon);
      }
      Tmp = SO.getObject("icon1",null);
      if ( Tmp != null && Tmp instanceof String ) {
        Icon icon = ExplorerIcons.getImageIcon(SO, "/" + EAI.Product + "/Resource",Tmp.toString(), null);//ExplorerIcons.getExplorerIcon(Tmp);
        SO.setObject("icon1",icon);
      }
      Tmp = SO.getObject("icon2",null);
      if ( Tmp != null && Tmp instanceof String ) {
        Icon icon = ExplorerIcons.getImageIcon(SO, "/" + EAI.Product + "/Resource",Tmp.toString(), null);//ExplorerIcons.getExplorerIcon(Tmp);
        SO.setObject("icon2",icon);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
//      SO.setObject("icon",null);
    }
  }
  /**
   *
   * @param nodeKey String
   */
  protected void initChildNode(String nodeKey) {
    childNodeStubList.clear();// 首先清空
    java.util.List childNodeList = null;
    if ( nodeKey != null )
      childNodeList = PackageStub.getContentVector(nodeKey);
    if ( this.getNodeStubObject() != null ) {
      StubObject[] sos = this.getNodeStubObject().getChilds();
      if ( sos != null ) {
        if ( childNodeList == null ) childNodeList = new java.util.ArrayList();
        for (int i = 0; i < sos.length; i++) {
          childNodeList.add(sos[i]);
        }
      }
    }
    if ( childNodeList == null ) return;
    StubObject SO = null;String ID = null,Tmp;
    for(int i=0;i<childNodeList.size();i++) {
      SO = (StubObject)childNodeList.get(i);
      initStubObject(SO);
      childNodeStubList.add(SO);
    }
  }
  protected void insertChildNode(String ServiceKey,NodeDataStub nodeDataStub,Context context) {
    StubObject childSO = this.getChildNodeStubObject(this,ServiceKey);
    insertChildNode(childSO,nodeDataStub,context);
  }
  protected void insertChildNode(StubObject childSO,NodeDataStub nodeDataStub,Context context) {
    // 创建该节点,
    JNodeStub ns = this.createChildNode(context.getParentNodeStub(this),childSO);
    if ( ns == null ) return;
    ns.setExplorerView(context.getParentNodeStub(this).ExplorerView);
    context.setBrowser(context.getParentNodeStub(this).ExplorerView);
    context.setNode(this);
    context.setRetNode(ns);
    // 设置该节点的数据对象
    ns.setNodeDataStub(nodeDataStub);
    // 注册该节点
    this.ExplorerView.registryChildNode(context.getParentNodeStub(this),ns);
  }
  public void insertNodeDataStubEvent(String ServiceKey,NodeDataStub nodeDataStub,Context context) {// 增加了一个SutbObject
    insertChildNode(ServiceKey,nodeDataStub,context);
  }
  public void removeNodeDataStubEvent(String ServiceKey,NodeDataStub nodeDataStub,Context context) {// 删除了一个SutbObject
    JNodeStub childNode = findChiladNode(nodeDataStub);
    if ( childNode == null ) return;
    // 如果不能删除，则直接返回
    if ( !nodeDataStub.removeNodeData(childNode) ) return;
    ExplorerView.removeChildNode(context.getParentNodeStub(this),childNode);
    IWindow win = childNode.getNodeWindow();
    if ( win != null ) {
      EnterpriseExplorer.ContentView.closeChildWindow(win,true);
    }
    nodeDataStub.destroyNodeData(childNode);
    context.setRetNode(childNode);
  }
  protected void changeChildNode(String key,NodeDataStub nodeDataStub,Context context) {
    JNodeStub childNode = findChiladNode(nodeDataStub);
    ExplorerView.changeChildNode(context.getParentNodeStub(this),childNode);
    nodeDataStub.changeNodeData(childNode);
    context.setRetNode(childNode);
  }
  public void changeNodeDataStubEvent(String ServiceKey,NodeDataStub nodeDataStub,Context context) {// 修改了一个SutbObject
    changeChildNode(ServiceKey,nodeDataStub,context);
  }
  public void refreshNodeDataStubEvent(String ServiceKey,NodeDataStub nodeDataStub,Context context) {// 修改了一个SutbObject

  }
  protected JNodeStub findChiladNode(NodeDataStub nodeDataStub) {
    JNodeStub dn = null;
    for(int i=0;i<this.getChildCount();i++) {
      dn = (JNodeStub)getChildAt(i);
      if ( nodeDataStub.equals(dn.getNodeDataStub()) ) {
        return dn;
      }
    }
    return null;
  }


  // 以子节点类型为Key的子节点Class列表
//  protected java.util.Map  childNodeClassList      = new Hashtable();
  // 以子节点类型为Key的子节点数据对象的Class列表
//  protected java.util.Map  childNodeDataClassList  = new Hashtable();
  // 以子节点类型为Key的子节点数据的列表
//  protected java.util.Map  childNodeDataObjectList = new Hashtable();
  protected Icon nodeIcon = null;
  public void setNodeIcon(Icon nodeIcon) {
    this.nodeIcon = nodeIcon;
  }
  public Icon          getNodeIcon() {
    if ( nodeIcon != null ) return nodeIcon;
    if ( nodeStubObject != null ) {
      Icon i = getCombNodeIcon(); //(Icon)this.nodeStubObject.getObject("icon",null);
      if ( i != null ) return i;
//      return ExplorerIcons.getExplorerIcon("fmisres/SYS_EXPLORER.gif");
    }
    return null;
  }
  protected Icon getCombNodeIcon() {
    Icon icon  = (Icon)nodeStubObject.getObject("icon",null);
    Icon icon1 = (Icon)nodeStubObject.getObject("icon1",null);
    Icon icon2 = (Icon)nodeStubObject.getObject("icon2",null);
    CompoundIcon compIcon = null;
    if ( icon != null && icon1 != null ) {
      compIcon = new CompoundIcon(icon,icon1);
    }
    if ( icon2 != null && compIcon != null ) {
      compIcon = new CompoundIcon(compIcon,icon2);
    }
    if ( compIcon == null ) return icon;
    return compIcon;
  }
  public JNodeStub() {

  }
  public Icon getOpenIcon() {
    return getNodeIcon();
  }
  public Icon getClosedIcon() {
    return getNodeIcon();
  }

  public void setNodeDataManager(NodeDataManager nodeDataManager) {
    this.nodeDataManager = nodeDataManager;
  }

  public void setAccessValue(String AccessValue) {
    this.AccessValue = AccessValue;
  }

  public void setAccessKey(String AccessKey) {
    this.AccessKey = AccessKey;
  }

  public void setSecurityValue(String SecurityValue) {
    this.SecurityValue = SecurityValue;
  }

  public void setSecurityKey(String SecurityKey) {
    this.SecurityKey = SecurityKey;
  }

  public void setModified(boolean modified) {
    if ( this.nodeDataStub != null ) {
      nodeDataStub.setModified(modified);
    }
    if ( this.nodeDataManager != null ) {
      nodeDataManager.setModified(modified);
    }
    ism = modified;
  }

  public void setExplorerView(JExplorerView ExplorerView) {
    this.ExplorerView = ExplorerView;
  }

  public NodeDataManager getNodeDataManager() {
    return nodeDataManager;
  }

  public List getChildNodeStubList() {
    return childNodeStubList;
  }

  public NodeChildWindow getNodeWindow() {
    return this.getOpenNodeWindow(this.getOpenNodeKey());
  }

  public String getAccessValue() {
    return AccessValue;
  }

  public String getAccessKey() {
    return AccessKey;
  }

  public String getSecurityValue() {
    return SecurityValue;
  }
  /**
   * 获取功能编号
   * @return String
   */
  public String getSecurityKey() {
    return SecurityKey;
  }

  public boolean canSave() {
    return true;
  }
  private boolean ism = false;
  public boolean isModified() {
    if ( this.nodeDataManager != null ) {
      if ( this.nodeDataManager.isModified() ) return true;
    }
    if ( this.nodeDataStub != null ) {
      if ( this.nodeDataStub.isModified() ) return true;
    }
    return ism;
  }

  public JExplorerView getExplorerView() {
    return ExplorerView;
  }

  public void setID(String id) {
    ID = id;
  }
  public String getID() {
    if ( ID == null ) {
      if ( this.nodeStubObject != null ) {
        ID = this.nodeStubObject.getString("id",null);
      }
    }
    return ID;
  }
  public boolean isOpen() {
    return true;
  }
  public ContextActionProvider getContextActionProvider() {
    return null;
  }
  /**
   * Invoked the first time a window is made visible.
   */
  public void windowOpened(ChildWindowEvent e) {

  }

  /**
   * Invoked when the user attempts to close the window
   * from the window's system menu.  If the program does not
   * explicitly hide or dispose the window while processing
   * this event, the window close operation will be cancelled.
   */
  public boolean windowClosing(ChildWindowEvent e) {
    return true;
  }

  /**
   * Invoked when a window has been closed as the result
   * of calling dispose on the window.
   */
  public void windowClosed(ChildWindowEvent e) {
    IWindow nodeWindow = e.getWindow();
    if ( nodeWindow != null ) {
      this.removeOpenNodeWindow(nodeWindow.getNodeKey());
//      this.removeOpenNodeExpWindow(nodeWindow.getNodeKey());
      nodeWindow.removeWindowListener(this);
      nodeWindow = null;
    }
  }

  /**
   * Invoked when a window is changed from a normal to a
   * minimized state. For many platforms, a minimized window
   * is displayed as the icon specified in the window's
   * iconImage property.
   * @see java.awt.Frame#setIconImage
   */
  public void windowIconified(ChildWindowEvent e) {

  }

  /**
   * Invoked when a window is changed from a minimized
   * to a normal state.
   */
  public void windowDeiconified(ChildWindowEvent e) {

  }

  /**
   * Invoked when the Window is set to be the active Window. Only a Frame or
   * a Dialog can be the active Window. The native windowing system may
   * denote the active Window or its children with special decorations, such
   * as a highlighted title bar. The active Window is always either the
   * focused Window, or the first Frame or Dialog that is an owner of the
   * focused Window.
   */
  public void windowActivated(ChildWindowEvent e) {

  }

  /**
   * Invoked when a Window is no longer the active Window. Only a Frame or a
   * Dialog can be the active Window. The native windowing system may denote
   * the active Window or its children with special decorations, such as a
   * highlighted title bar. The active Window is always either the focused
   * Window, or the first Frame or Dialog that is an owner of the focused
   * Window.
   */
  public void windowDeactivated(ChildWindowEvent e) {

  }
  public void doSave() throws Exception {
    if ( this.nodeDataStub != null ) {
      nodeDataStub.doSave();
      nodeDataStub.setModified(false);
    }
    if ( this.nodeDataManager != null ) {
      nodeDataManager.doSave();
      nodeDataManager.setModified(false);
    }
  }
  /**
   * 创建旧框架的菜单
   * @param nodeStub JNodeStub
   */
  protected void buildFWKMenus(JNodeStub nodeStub) {
    FWKNodeUtils.buildFWKMenus(nodeStub);
  }
  public void doLoad() throws Exception {
    if ( loaded ) return;
    loaded = true;
    // 如果此节点上定义了旧框架的菜单，则需要装入在最后装入
    if ( "1".equals(this.getNodeStubObject().getString("fwknode","0")) ) {
      buildFWKMenus(this);
    }
    java.util.List childList = this.getChildNodeStubList();

    StubObject SO = null;JNodeStub childNode = null;
    Object childNodeDataClass = null;
    for(int i=0;i<childList.size()&&childList!=null;i++) {
      SO = (StubObject)childList.get(i);
      childNodeDataClass = SO.getObject("nodeDataClass",null);
      if ( childNodeDataClass == null ) {
        childNode = this.createChildNode(this, SO);
        this.ExplorerView.registryChildNode(this, childNode);
      }
    }
    if ( this.nodeDataManager != null ) {
      nodeDataManager.doLoad();
    }
  }
  /**
   *
   * @return boolean
   */
  public boolean getAllowsChildren() {
    java.util.List childList = this.getChildNodeStubList();
    if ( childList != null && childList.size() > 0 ) {
      return true;
    }
    if ( getNodeDataManager() != null ) {
      return true;
    }
    return getUserAllowsChildren();
  }
  protected boolean getUserAllowsChildren() {
    return ( this.getChildCount() != 0 );
  }
  public void doRefresh() throws Exception {
    doLoad();
  }
  /**
   * 形成没有节点数据管理器的节点的下级节点
   */
  public void listHasnotDataManagerNode() {
    if ( this.nodeDataManager != null ) return;
    java.util.List childList = this.getChildNodeStubList();
    if ( childList == null || childList.size() == 0 ) {
      try {
        doLoad();
      }catch ( Exception e ) {}
      return;
    }
    if( !loaded ) {
      StubObject SO = null;JNodeStub childNode = null;
      for(int i=0;i<childList.size();i++) {
        SO = (StubObject)childList.get(i);
        childNode = this.createChildNode(this,SO);
        this.ExplorerView.registryChildNode(this,childNode);
      }
      loaded = true;
    }
  }
  public static final String DOCUMENT_CATALOG = "nodeDocument/";
  public static final String HELP_CATALOG     = "nodeHelp/";
  public static String HelpNotFound = "HelpNotFound.html";
  /**
   *
   * @return String
   */
  public String getDocName() {
    String name = this.getID();
    if ( name != null ) name += "_doc";
    return name;
  }
  /**
   *
   * @return String
   */
  public String getHelpName() {
    String name = this.getID();
    if ( name != null ) name += "_help";
    return name;
  }
  protected JNodeStub parentNode = null;
  /**
   *
   * @param parentNode JNodeStub
   */
  public void setParentNode(JNodeStub parentNode) {
    this.parentNode = parentNode;
    if ( parentNode != null )
      this.setExplorerView(parentNode.getExplorerView());
  }
  public JNodeStub getParentNode() {
    return parentNode;
  }
  public boolean canDelete() {
    return true;
  }
  protected java.util.Map userObjectMap = null;
  /**
   *
   * @param key Object
   * @param value Object
   */
  public void putUserObject(Object key,Object value) {
    if ( userObjectMap == null ) userObjectMap = new java.util.HashMap();
    userObjectMap.put(key,value);
  }
  public Object getUserObject(Object key,Object def) {
    if ( userObjectMap == null ) return def;
    if ( userObjectMap.get(key) == null ) return def;
    return userObjectMap.get(key);
  }
  /**
   *
   * @return String
   */
  public String getBIZUnit() {
    if ( this.nodeStubObject != null ) {
      String value = (String) nodeStubObject.getObject("BIZUnit", null);
      if ( value != null && value.trim().length() != 0 ) {
        return value;
      }
    }
    if ( this.getParent() != null )
      return ((JNodeStub)this.getParent()).getBIZUnit();
    return null;
  }
  /**
   *
   * @return String
   */
  public String getBIZType() {
    if ( this.nodeStubObject != null ) {
      String value = (String) nodeStubObject.getObject("BIZType", null);
      if ( value != null && value.trim().length() != 0 ) {
        return value;
      }
    }
    if ( this.getParent() != null )
      return ((JNodeStub)this.getParent()).getBIZType();
    return null;
  }
  /**
   *
   * @return String
   */
  public String getDateType() {
    if ( this.nodeStubObject != null ) {
      String value = (String) nodeStubObject.getObject("DateType", null);
      if ( value != null && value.trim().length() != 0 ) {
        return value;
      }
    }
    if ( this.getParent() != null )
      return ((JNodeStub)this.getParent()).getDateType();
    return null;
  }
  /**
   *
   * @return String
   */
  public String getBIZSDate() {
    if ( this.nodeStubObject != null ) {
      String value = (String) nodeStubObject.getObject("BIZSDate", null);
      if ( value != null && value.trim().length() != 0 ) {
        return value;
      }
    }
    if ( this.getParent() != null )
      return ((JNodeStub)this.getParent()).getBIZSDate();
    return null;
  }
  /**
   *
   * @return String
   */
  public String getBIZEDate() {
    if ( this.nodeStubObject != null ) {
      String value = (String) nodeStubObject.getObject("BIZEDate", null);
      if ( value != null && value.trim().length() != 0 ) {
        return value;
      }
    }
    if ( this.getParent() != null )
      return ((JNodeStub)this.getParent()).getBIZEDate();
    return null;
  }
  /**
   *
   * @param object Object
   * @param object1 Object
   * @return Object
   */
  public Object getBIZValue(Object object, Object object1) {
    if ( this.nodeStubObject != null ) {
      Object value = (Object) nodeStubObject.getObject(object, object1);
      if ( value != null ) {
        return value;
      }
    }
    if ( this.getParent() != null )
      return ((JNodeStub)this.getParent()).getBIZValue(object,object1);
    return null;
  }
  /**
   *
   * @param object Object
   * @param object1 Object
   */
  public void setBIZValue(Object object, Object object1) {
//    if ( this.nodeStubObject != null )
//      nodeStubObject.setObject(object,object1);
  }
  /**
   *
   * @param object Object
   * @param object1 Object
   */
  public void callBack(Object object, Object object1) {
    if ( object instanceof KeyValue ) {
      KeyValue kv = (KeyValue)object;
//      StubObject stub = this.getNodeDataStub();
//      if ( stub != null ) {
//        kv.getAttriMap().putAll(stub.getStubTable());
//      }
      StubObject stub = this.getNodeStubObject();
      if ( stub != null ) {
        putAllStringAttrib(stub.getStubTable(),kv.getAttriMap());
//        kv.getAttriMap().putAll(stub.getStubTable());
      }
    }
  }

  protected void putAllStringAttrib(java.util.Map src,java.util.Map dst) {
    Object[] srco = src.keySet().toArray();Object value;
    for(int i=0;i<srco.length;i++) {
      value = src.get(srco[i]);
      if ( value instanceof String && srco[i] instanceof String) {
        dst.put(srco[i],value);
      }
    }
  }


  public void enumBIZKey(List list) {
  }
  /**
   *
   * @return boolean
   */
  public boolean isAutoExpend() {
    if ( this.nodeStubObject != null ) {
      return "1".equals(nodeStubObject.getString("autoExpend","0"));
    }
    return false;
//    return false;
  }
  public boolean allowOpen(){ return true;}
  public boolean allowOpen(Context context){ return true;}
  public boolean allowExec() { return false;}
  public boolean allowExec(Context context) { return false;}
  public Object execObject(final Object nodeKey,final String key,final Context context) {
    return null;
  }
  /**
   *
   * @param contextObject Object
   * @param addinObject Object
   */
  public void   initBIZContext(Object sourceObject,Object contextObject,Object addinObject){};
  public void   changeEvent(int eventType,Object sourceObject,Object contextObject,Object addinObject){};
  public JParamObject convertParamObject(Object userObject,JParamObject po){return po;}
  public boolean isSaveNodeView() {return false;}
  protected java.util.Map callbackMap = null;
  public void setCallBackValue(Object key,Object value){
    if ( callbackMap == null ) callbackMap = new java.util.HashMap();
    callbackMap.put(key,value);
  }
  public java.util.Map getCallBackMap(){return callbackMap;}
  public void addBIZContext(BIZContext bizContext) {}

  public String getPrepareAttString() {
    return null;
  }
  public boolean allowOpenDocument() {return true;}
  public boolean allowOpenHelp() {return true;}

  public void initData(Context context) throws Exception {}

  public String getOperateNo() {
    return null;
  }
  public String getOperateName() {
    return this.toString();
  }

}
