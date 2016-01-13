package com.efounder.node.view;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import org.openide.*;
import com.borland.jbcl.layout.*;
import com.core.xml.*;
import com.efounder.action.*;
import com.efounder.actions.*;
import com.efounder.eai.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.ui.action.fileaction.FileActionInterface;
import com.efounder.model.biz.BIZContext;
import com.efounder.node.*;
import com.efounder.pfc.dialog.*;
import com.efounder.pfc.window.*;
import com.efounder.view.*;
import com.efounder.eai.data.JParamObject;
import com.efounder.comp.*;
import com.efounder.service.security.OperateLoginfo;
import com.efounder.service.security.ServiceSecurityManager;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeWindow extends JChildWindow implements Runnable{//,BIZContext,FileActionInterface,EditActionInterface,CalcActionInterface,MoveActionInterface,SearchActionInterface,ToolsActionInterface {

  protected java.util.Map structViewVisibleList   = new HashMap();
  protected java.util.Map propertyViewVisibleList = new HashMap();

  protected JNodeStub nodeStub = null;
  protected Context   nodeContext = null;
  public Context getNodeContext() {
    return nodeContext;
  }
  /**
   *
   */
  protected NodeViewerFactory[] nodeViewerFactoryArray = null;
  protected NodeViewer[]        nodeViewerArray        = null;
  public void setNodeStub(JNodeStub nodeStub) {
    this.nodeStub = nodeStub;
  }

//  private boolean isStructViewVisible(NodeViewer nodeView) {
//    Boolean b = (Boolean)structViewVisibleList.get(nodeView);
//    if ( b != null ) return b.booleanValue();
//    return true;
//  }
//  private void setStructViewVisible(NodeViewer nodeView,boolean v) {
//    structViewVisibleList.put(nodeView,new Boolean(v));
//  }
//  private boolean isPropertyViewVisible(NodeViewer nodeView) {
//    Boolean b = (Boolean)propertyViewVisibleList.get(nodeView);
//    if ( b != null ) return b.booleanValue();
//    return false;
//  }
//  private void setPropertyViewVisible(NodeViewer nodeView,boolean v) {
//    propertyViewVisibleList.put(nodeView,new Boolean(v));
//  }

  /**
   *
   * @return JNodeStub
   */
  public JNodeStub getNodeStub() {
    return nodeStub;
  }

  public NodeViewerFactory[] getNodeViewerFactoryArray() {
    return nodeViewerFactoryArray;
  }

  public NodeViewer[] getNodeViewerArray() {
    return nodeViewerArray;
  }



  BorderLayout borderLayout1 = new BorderLayout();
  java.util.Map NodeViewList = new Hashtable();
  // 结构视图列表
  java.util.Map nodeStructViewList   = new java.util.HashMap();
  // 属性视图列表
  java.util.Map nodePropertyViewList = new java.util.HashMap();
  // Bottom视图列表
  java.util.Map nodeBottomViewList = new java.util.HashMap();
  /**
   *
   * @return JTabbedPane
   */
  public JComponent getViewPane() {
    return null;
  }
  /**
   *
   */
  public NodeWindow() {
    try {
      jbInit();

    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   */
//  private void initMouseEvent() {
//    String javaVersion = System.getProperty("java.runtime.version");
//    if ( !javaVersion.startsWith("1.4") ) {
//      tpNodeView.addMouseListener(this);
//    } else {
//      Component[] comps = tpNodeView.getComponents();
//      if (comps.length > 0 && comps[0] instanceof JViewport) {
//        JViewport vp = (JViewport) comps[0];
//        vp.getComponent(0).addMouseListener(this);
//      }
//    }
//  }
  protected void activeExplorerView() {
    if ( this.nodeStub != null && nodeStub.getExplorerView() != null ) {
      EnterpriseExplorer.ExplorerView.setVisible(nodeStub.getExplorerView(),true);
//      EnterpriseExplorer.ExplorerView.activeWindow(nodeStub.getExplorerView());
    }
  }
  /**
   *
   * @param view IView
   */
  public void activeWindowEvent(IView view) {
    super.activeWindowEvent(view);
    activeExplorerView();
    if ( LAST_ACTIVE_WINDOW != null ) {
      LAST_ACTIVE_WINDOW.browserActivated();
      openAttribView(LAST_ACTIVE_WINDOW);
    }
//    // 可能需要切换布局
//    switchLayout();
  }
  /**
   *
   * @param view IView
   */
  public void deactiveWindowEvent(IView view) {
    super.deactiveWindowEvent(view);
    if ( LAST_ACTIVE_WINDOW != null ) {
      LAST_ACTIVE_WINDOW.browserDeactivated();
      closeAttribView(LAST_ACTIVE_WINDOW);
    }
  }
  /**
   *
   */
  protected void switchLayout() {

  }
  /**
   *
   * @throws Exception
   */
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
//    tpNodeView.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//    tpNodeView.setTabPlacement(JTabbedPane.BOTTOM);
    pnNorth.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
//    BoxLayout2 bl2 = new BoxLayout2();
    FlowLayout b12 = new FlowLayout(FlowLayout.RIGHT,0,0);
    pnSouth.setLayout(b12);
//    pnSouth.setOpaque(false);
    pnSouth.setBackground(UIManager.getColor("ToolBar.background"));
    flowLayout2.setAlignment(FlowLayout.RIGHT);
    flowLayout2.setHgap(0);
    flowLayout2.setVgap(0);
    pnWest.setLayout(verticalFlowLayout1);
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);
    pnEast.setLayout(verticalFlowLayout2);
    verticalFlowLayout2.setHgap(0);
    verticalFlowLayout2.setVgap(0);
//    this.add(tpNodeView,  BorderLayout.CENTER);
    this.add(pnNorth, java.awt.BorderLayout.NORTH);
    this.add(pnSouth, java.awt.BorderLayout.SOUTH);
    this.add(pnWest, java.awt.BorderLayout.WEST);
    this.add(pnEast, java.awt.BorderLayout.EAST);
//    initMouseEvent();
  }
  /**
   *
   */
  void initEvent() {
//    this.tpNodeView.addChangeListener(this);
  }
  /**
   *
   * @param context Context
   */
  public void reInitNodeChildWindow(Context context) {
    try {
      nodeStub = context.getNode();// 设置当前Node
      for (int i = 0; nodeViewerArray != null && i < nodeViewerArray.length; i++) {
        if ( nodeViewerArray[i] != null )
          nodeViewerArray[i].reInitNodeViewer(context, context.getNode(),
                                              nodeViewerFactoryArray[i], this);
        nodeViewerArray[i].initCompBIZContext();
      }
      activeNodeExplorerWindow();
      autoNodeViewTitle();
    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(0,e,this);
    }
  }
  protected void activeNodeExplorerWindow() {
    openExplorerWindow(this.getActiveNodeViewer());
  }
  /**
   *
   * @return boolean
   */
  public boolean canClose() {
    if ( !this.isModified() ) return true;
    int Res = JOptionPane.showConfirmDialog(EAI.EA.getMainWindow(),this.getTitle()+"已经修改，关闭之前要进行保存吗?", "系统提示",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    switch ( Res ) {
      case JOptionPane.YES_OPTION:
        try {
          this.saveNode();
         String err= (String) EAI.getEnv("ERR",null);//不得已而为之
         if("1".equals(err)){
          EAI.putEnv("ERR",null);
           return false;
         }
        } catch ( Exception ex ) {
          ErrorManager.getDefault().notify(ex);
          return false;
        }
      case JOptionPane.NO_OPTION:
        return true;
      case JOptionPane.CANCEL_OPTION:
        return false;
    }
    return true;
  }
  protected void initJSplitPanel() {

  }
  /**
   *
   * @param context Context
   * @param Key String
   * @param nvfs NodeViewerFactory[]
   * @throws Exception
   */
  public void initNodeChildWindow2(Context context,String Key,NodeViewerFactory[] nvfs) throws Exception {
    NodeViewer nodeViewer = null;
    //
    initJSplitPanel();
    for(int i=0;i<nodeViewerArray.length;i++) {
      nodeViewer = nodeViewerArray[i];
      nodeViewer.initNodeViewer2(context,context.getNode(),nodeViewerFactoryArray[i],this);// 初始化
      nodeViewer.initCompBIZContext();
      // explorer
      NodeExplorerWindow nepw = nodeViewer.getNodeExpWindow();
      if ( nepw != null ) nepw.initView2();

      String viewKey = nodeViewer.getNodeViewID();
      NodeAttribView[] nodeAttribView = null;
      // struct
      nodeAttribView = (NodeAttribView[])this.nodeStructViewList.get(viewKey);
      for(int k=0;nodeAttribView!=null&&k<nodeAttribView.length;k++){
        JBOFClass.CallObjectMethod(nodeAttribView[k],"initView2");
      }
      // property
      nodeAttribView = (NodeAttribView[])this.nodePropertyViewList.get(viewKey);
      for(int k=0;nodeAttribView!=null&&k<nodeAttribView.length;k++){
        JBOFClass.CallObjectMethod(nodeAttribView[k],"initView2");
      }

    }
  }
  /**
   *
   * @param context Context
   * @param Key String
   * @param nvfs NodeViewerFactory[]
   */
  public void initNodeChildWindow(Context context,String Key,NodeViewerFactory[] nvfs)  throws Exception {
    nodeViewerFactoryArray = nvfs;
    nodeContext = context;
    StubObject SO = null;int index = 0;NodeViewer[] nvs = null;
    if ( nodeViewerFactoryArray == null ) return;String iKey = null;
    nodeViewerArray = new NodeViewer[nvfs.length];
    nodeStub = context.getNode();//设置当前Node
    // 初始化
    prepareViewer(context,Key,nvfs);
    for(int i=0;i<nodeViewerFactoryArray.length;i++) {
//      SO = nodeViewerFactoryArray[i].getStubObject();
      iKey = nodeViewerFactoryArray[i].getID();//SO.getString("id",null);
      if ( Key == null || Key.equals(iKey) ) {
        NodeViewer nv = (NodeViewer)NodeViewList.get(iKey);
        if ( nv == null ) {//如果这个NodeView还没有创建,则需要新建,并显示出来
          nvs = nodeViewerFactoryArray[i].createNodeViewers(context,context.getNode(),context.getBrowser());
          for(int k=0;nvs!=null&&k<nvs.length;k++) {
            nv = nvs[k];
            if ( nv == null ) continue;
            nv.setNodeWindow(this);
            if ( nv.getNodeViewID() == null )
              nv.setNodeViewID(iKey); // 设置ID
            nv.setViewFactory(nodeViewerFactoryArray[i]); // 设置工厂
            // 设置初始化
            nv.setNodeViewer(context,context.getNode(),nodeViewerFactoryArray[i],this);//
            // 准备Viewer
            nv.prepareViewer(context,context.getNode(),nodeViewerFactoryArray[i],this);//

//            tpNodeView.insertTab(nv.getViewerTitle(),nv.getViewerIcon(),nv.getViewerComponent(),nv.getViewerDescription(),index++);
            nv.initNodeViewer(context,context.getNode(),nodeViewerFactoryArray[i],this);// ��ʼ��
            // 增加到可视
            insertNodeView(nv,index++);
            NodeViewList.put(iKey,nv);// 设置缓冲
          }
          ofterInsertNodeView();
        } else {
          if ( Key != null ) {
            selectNodeView(nv);
//            tpNodeView.setSelectedComponent(nv.getViewerComponent());
          }
        }
        // 节点视图
        nodeViewerArray[i] = nv;
      }
    }
    LAST_ACTIVE_WINDOW = getSelectedNodeView();//(NodeViewer)tpNodeView.getSelectedComponent();
    initEvent();

    autoNodeViewTitle();

    createOperateLoginfo();
  }
  protected void ofterInsertNodeView() {

  }
  protected void insertNodeView(NodeViewer nv,int index) {
//    tpNodeView.insertTab(nv.getViewerTitle(),nv.getViewerIcon(),nv.getViewerComponent(),nv.getViewerDescription(),index);
  }
  protected void selectNodeView(NodeViewer nv) {

  }
  public NodeViewer getSelectedNodeView() {
    return null;
  }
  /**
   *
   */
  protected java.util.List contextCompList = null;
  /**
   *
   * @return List
   */
  public java.util.List getContextCompList() {
    return contextCompList;
  }
  /**
   *
   * @param bizContext BIZContext
   */
  protected void addContextComp(BIZContext bizContext) {
    if ( contextCompList == null ) contextCompList = new java.util.ArrayList();
    contextCompList.add(bizContext);
  }
  /**
   *
   * @param context Context
   * @param Key String
   * @param nvfs NodeViewerFactory[]
   * @throws Exception
   */
  public void prepareViewer(Context context,String Key,NodeViewerFactory[] nvfs) throws Exception {
    initLeftTopView(context);
    initTopView(context);
    initSouthView(context);
    initWestView();
    initEastView();
  }
//  JToolBar topToolBar = null;
  ExplorerViewToolBarPane actionTopToolBar = null;
  /**
   *
   */
  protected void initLeftTopView(Context context) {
//    topToolBar = new JToolBar();

    pnNorth.setVisible(false);
//    topToolBar.setBorder(null);
    // 初始化Action
//    initNodeViewTopCompViewAction();
    // 初始化View
    initNodeViewLeftTopCompView(context);
  }
  /**
   *
   * @param o Object
   */
  public void refreshData(Object o) {
    try {
      WaitingManager.getDefault().beginWait(EAI.EA.getMainWindow());
      NodeViewer[] nvs = this.getNodeViewerArray();
      for(int i=0;i<nvs.length;i++) {
        JBOFClass.VoidCallObjectMethod(nvs[i], "refreshData", new Object[] {o});
      }
    } finally {
      WaitingManager.getDefault().endWait(EAI.EA.getMainWindow());
    }
  }
  /**
   *
   * @param o Object
   */
  public void dispatchEvent(Object o,String event,Object userObject) {
    try {
      WaitingManager.getDefault().beginWait(EAI.EA.getMainWindow());
      NodeViewer[] nvs = this.getNodeViewerArray();
      for (int i = 0; i < nvs.length; i++) {
        JBOFClass.VoidCallObjectMethod(nvs[i], "dispatchEvent", new Object[] {o,event, userObject});
      }
    } finally {
      WaitingManager.getDefault().endWait(EAI.EA.getMainWindow());
    }
  }
  protected String getWindowPrefix() {
    return "";
  }
  /**
   *
   */
  protected void initNodeViewLeftTopCompView(Context context) {
    java.util.List compViewList = CompViewManager.getComponentView(nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowLeftTopCompView",this,nodeStub,context);
    ComponentView cv = null;
    for(int i=0;i<compViewList.size();i++) {
      cv = (ComponentView)compViewList.get(i);
      if ( cv == null ) continue;
      if ( cv instanceof BIZContext ) {
        this.addContextComp((BIZContext)cv);
      }
      actionTopToolBar.add(cv.getComponent());
//      topToolBar.add(cv.getComponent());
      pnNorth.setVisible(true);
    }
  }
  /**
   *
   */
  protected void initTopView(Context context) {
    actionTopToolBar = new ExplorerViewToolBarPane(this);
//    topToolBar.add(actionTopToolBar);
//    pnNorth.add(topToolBar);
    pnNorth.add(actionTopToolBar);
    // 初始化Action
    initNodeViewTopCompViewAction();
    // 初始化View
    initNodeViewTopCompView(context);
  }
  /**
   *
   */
  protected void initNodeViewTopCompView(Context context) {
    java.util.List compViewList = CompViewManager.getComponentView(nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowTopCompView",this,nodeStub,context);
    ComponentView cv = null;
    for(int i=0;i<compViewList.size();i++) {
      cv = (ComponentView)compViewList.get(i);
      if ( cv == null ) continue;
      if ( cv instanceof BIZContext ) {
        this.addContextComp((BIZContext)cv);
      }
      actionTopToolBar.add(cv.getComponent());
//      topToolBar.add(cv.getComponent());
      pnNorth.setVisible(true);
    }
  }
  /**
   *
   */
  protected void initNodeViewTopCompViewAction() {
    Action[] actionArray = ActionManager.getContextActions(nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowTopCompViewAction",this,nodeStub);
    ActionGroup ag = new ActionGroup();
    for(int i=0;actionArray!=null&&i<actionArray.length;i++) {
      ag.add(actionArray[i]);
    }
    if ( ag.getActionCount() > 0 ) {
      actionTopToolBar.addGroup(ag);
      pnNorth.setVisible(true);
    }
  }
//  JToolBar southToolBar = null;
//  ExplorerViewToolBarPane actionSouthToolBar = null;
  /**
   *
   */
  protected void initSouthView(Context context) {
//    southToolBar = new JToolBar();
//    southToolBar.setBorder(null);
//    actionSouthToolBar = new ExplorerViewToolBarPane(this);
//    actionSouthToolBar.setOrientation(SwingConstants.RIGHT);
//    southToolBar.add(actionSouthToolBar);
//    pnSouth.add(southToolBar);
//    pnSouth.add(actionSouthToolBar);
    pnSouth.setVisible(false);
    // 初始化Action
    initNodeViewSouthCompViewAction();
    // 初始化View
    initNodeViewSouthCompView(context);
  }
  /**
   *
   */
  protected void initNodeViewSouthCompViewAction() {
    Action[] actionArray = ActionManager.getContextActions(nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowSouthCompViewAction",this,nodeStub);
    ActionGroup ag = new ActionGroup();
    for(int i=0;actionArray!=null&&i<actionArray.length;i++) {
      ag.add(actionArray[i]);
    }
    if ( ag.getActionCount() > 0 ) {
//      actionSouthToolBar.addGroup(ag);
//      pnSouth.setVisible(true);
    }
  }
  /**
   *
   */
  protected void initNodeViewSouthCompView(Context context) {
    java.util.List compViewList = CompViewManager.getComponentView(nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowSouthCompView",this,nodeStub,context);
    ComponentView cv = null;
    for(int i=0;i<compViewList.size();i++) {
      cv = (ComponentView)compViewList.get(i);
      if ( cv == null ) continue;
      if ( cv instanceof BIZContext ) {
        this.addContextComp((BIZContext)cv);
      }
//      actionSouthToolBar.RIGHT;
//      actionSouthToolBar.add(cv.getComponent());
      pnSouth.add(cv.getComponent());
      pnSouth.setVisible(true);
    }
  }
//  JToolBar westToolBar = null;
  ExplorerViewToolBarPane actionWestToolBar = null;
  /**
   *
   */
  protected void initWestView() {
//    westToolBar = new JToolBar();
//    westToolBar.setOrientation(JToolBar.VERTICAL);
//    westToolBar.setFloatable(false);
//    westToolBar.setBorder(null);
    actionWestToolBar = new ExplorerViewToolBarPane(this);
    actionWestToolBar.setHorizontal(false);
//    westToolBar.add(actionWestToolBar);
//    pnWest.add(westToolBar);
    pnWest.add(actionWestToolBar);
    pnWest.setVisible(false);
    // 初始化Action
      initNodeViewWestCompViewAction();
    // 初始化View
    initNodeViewWestCompView();
  }
  /**
   *
   */
  protected void initNodeViewWestCompViewAction() {
    Action[] actionArray = ActionManager.getContextActions(this.nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowWestCompViewAction",this,nodeStub);
    ActionGroup ag = new ActionGroup();
    for(int i=0;actionArray!=null&&i<actionArray.length;i++) {
      ag.add(actionArray[i]);
    }
    if ( ag.getActionCount() > 0 ) {
      actionWestToolBar.addGroup(ag);
      pnWest.setVisible(true);
    }
  }
  /**
   *
   */
  protected void initNodeViewWestCompView() {
    java.util.List compViewList = CompViewManager.getComponentView(this.nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowWestCompView",this,nodeStub,null);
    ComponentView cv = null;
    for(int i=0;i<compViewList.size();i++) {
      cv = (ComponentView)compViewList.get(i);
      if ( cv == null ) continue;
      if ( cv instanceof BIZContext ) {
        this.addContextComp((BIZContext)cv);
      }
      actionWestToolBar.add(cv.getComponent());
//      westToolBar.add(cv.getComponent());
      pnWest.setVisible(true);
    }
  }
//  JToolBar eastToolBar = null;
  ExplorerViewToolBarPane actionEastToolBar = null;
  /**
   *
   */
  protected void initEastView() {
//    eastToolBar = new JToolBar();
//    eastToolBar.setOrientation(JToolBar.VERTICAL);
//    eastToolBar.setFloatable(false);
//    eastToolBar.setBorder(null);
    actionEastToolBar = new ExplorerViewToolBarPane(this);
    actionEastToolBar.setHorizontal(false);
//    eastToolBar.add(actionEastToolBar);
//    pnEast.add(eastToolBar);
    pnEast.add(actionEastToolBar);
    pnEast.setVisible(false);
    // 初始化Action
   initNodeViewEastCompViewAction();
   // 初始化View
    initNodeViewEastCompView();
  }
  /**
   *
   */
  protected void initNodeViewEastCompViewAction() {
    Action[] actionArray = ActionManager.getContextActions(this.nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowEastCompViewAction",this,nodeStub);
    ActionGroup ag = new ActionGroup();
    for(int i=0;actionArray!=null&&i<actionArray.length;i++) {
      ag.add(actionArray[i]);
    }
    if ( ag.getActionCount() > 0 ) {
      actionEastToolBar.addGroup(ag);
      pnEast.setVisible(true);
    }
  }
  /**
   *
   */
  protected void initNodeViewEastCompView() {
    java.util.List compViewList = CompViewManager.getComponentView(this.nodeStub.getID()+"_"+getWindowPrefix()+"NodeWindowEastCompView",this,nodeStub,null);
    ComponentView cv = null;
    for(int i=0;i<compViewList.size();i++) {
      cv = (ComponentView)compViewList.get(i);
      if ( cv == null ) continue;
      if ( cv instanceof BIZContext ) {
        this.addContextComp((BIZContext)cv);
      }
      actionEastToolBar.add(cv.getComponent());
//      eastToolBar.add(cv.getComponent());
      pnEast.setVisible(true);
    }
  }
  /**
   * ��ȡ�����ĵ�Action
   * @return Action
   */
  public Action getContextAction() {
    ActionGroup ag = null;
    if ( nodeViewerArray == null ) return ag;
    //
    ag = new ActionGroup();Action ac = null;
    for(int i=0;i<nodeViewerArray.length;i++) {
      if ( nodeViewerArray[i] == null ) continue;
      ac = nodeViewerArray[i].getContextAction();
      if ( ac != null ) ag.add(ac);
    }
    if ( ag.getActionCount() == 0 ) ag = null;
    return ag;
  }
  /**
   * 获取主框架中的Action
   * @return Action
   */
  public Action getTopFrameAction() {
    ActionGroup ag  = new ActionGroup();Action topAction = null;
    if ( nodeViewerArray == null ) return null;
    // 处理FrameAction的两个ActionGroup
    for(int i=0;i<nodeViewerArray.length;i++) {
      if ( nodeViewerArray[i] == null ) continue;
      if ( !getSelectedNodeView().equals(nodeViewerArray[i]) ) continue;
//      if ( !tpNodeView.getSelectedComponent().equals(nodeViewerArray[i]) ) continue;
      topAction = (Action)nodeViewerArray[i].getTopFrameAction();
      if ( topAction != null )
        ag.add(topAction);
    }
    if ( ag.getActionCount() == 0 ) return null;
    return ag;
  }
  /**
   * 获取主框架中的Action
   * @return Action
   */
  public Action getFrameAction() {
    ActionGroup ag  = new ActionGroup(),nodeAG = null;
    ActionGroup ag1 = new ActionGroup();
    ActionGroup ag2 = new ActionGroup();ActionGroup tmpAG = null;
    // 处理FrameAction的两个ActionGroup
    for(int i=0;i<nodeViewerArray.length;i++) {
      if ( nodeViewerArray[i] == null ) continue;
      if ( !getSelectedNodeView().equals(nodeViewerArray[i]) ) continue;
//      if ( !tpNodeView.getSelectedComponent().equals(nodeViewerArray[i]) ) continue;
      nodeAG = (ActionGroup)nodeViewerArray[i].getFrameAction();
      ActionGroup entityActionGroup = (ActionGroup)nodeViewerArray[i].getEntityAction();
      if ( entityActionGroup != null ) {
        ag1.add(entityActionGroup);
      }
      if ( nodeAG != null ) {
        //
        if ( nodeAG.getActionCount() > 0 ) {
          tmpAG = (ActionGroup)nodeAG.getAction(0);
          for(int j=0;j<tmpAG.getActionCount();j++) {
            ag1.add(tmpAG.getAction(j));
          }
        }
        //
        if ( nodeAG.getActionCount() > 1 ) {
          tmpAG = (ActionGroup)nodeAG.getAction(1);
          for(int j=0;j<tmpAG.getActionCount();j++) {
            ag2.add(tmpAG.getAction(j));
          }
        }
      }
    }
    if ( ag1.getActionCount() != 0 ) {
      ag.add(ag1);
    }
    if ( ag2.getActionCount() != 0 ) {
      ag.add(ag2);
    }
    if ( ag.getActionCount() == 0 ) ag = null;
    return ag;
  }
  /**
   *
   * @return ActionStub[]
   */
  public ActionStub[] getFloatAction() {
    ActionStub[] asArray = null;
    // 处理getFloatAction
    for(int i=0;i<nodeViewerArray.length;i++) {
      if ( nodeViewerArray[i] == null ) continue;
      if ( !getSelectedNodeView().equals(nodeViewerArray[i]) ) continue;
      asArray = nodeViewerArray[i].getFloatAction();
    }
    return asArray;
  }
  /**
   * 获取右键弹出的Action
   * @return Action
   */
  public Action getPopupAction() {
    ActionGroup ag = null;
    if ( nodeViewerArray == null ) return ag;
    //
    ag = new ActionGroup();Action ac = null;
    for(int i=0;i<nodeViewerArray.length;i++) {
      if ( nodeViewerArray[i] == null ) continue;
      ac = nodeViewerArray[i].getPopupAction();
      if ( ac != null ) ag.add(ac);
    }
    if ( ag.getActionCount() == 0 ) ag = null;
    return ag;
  }
  /**
   * 获取当前的NodeViewer
   * @return NodeViewer
   */
  public NodeViewer getActiveNodeViewer() {
    NodeViewer nv = getSelectedNodeView();
//    Component comp = this.tpNodeView.getSelectedComponent();
//    if ( comp instanceof NodeViewer ) {
//      nv = (NodeViewer)comp;
//    }
    return nv;
  }

  protected void activeNodeView() {
    activeEvent();
    autoNodeViewTitle();
  }
  protected Object getStateChanged() {
    return null;
  }
  /**
   *
   * @param e ChangeEvent
   */
  NodeViewer LAST_ACTIVE_WINDOW = null;
  protected void activeEvent() {
    if ( LAST_ACTIVE_WINDOW != null ) {

      if ( isMaxWindow(LAST_ACTIVE_WINDOW) ) {
        // 先恢得到不是最大化状态
        byte[] _screenLayout = getMaxLayoutData(LAST_ACTIVE_WINDOW);
        if ( _screenLayout != null )
          EnterpriseExplorer.getExplorer().getDockableHolder().getDockingManager().setLayoutRawData(_screenLayout);
      }

      LAST_ACTIVE_WINDOW.viewerDeactivated();
      closeAttribView(LAST_ACTIVE_WINDOW);
    }
    NodeViewer NOW_ACTIVE_WINDOW = (NodeViewer)this.getSelectedNodeView();
    if ( NOW_ACTIVE_WINDOW != null ) {
      NOW_ACTIVE_WINDOW.viewerActivated(true);
      LAST_ACTIVE_WINDOW = NOW_ACTIVE_WINDOW;
      openAttribView(NOW_ACTIVE_WINDOW);
    }
    this.getView().removeWindowAction();
    this.getView().insertWindowAction(this);
  }
  /**
   *
   * @param nodeViewer NodeViewer
   */
  protected void showFloatBarWindow(NodeViewer nodeViewer) {
    ActionStub[] asArray = nodeViewer.getFloatAction();
    if ( asArray == null ) return;
    ActionStub actionStub = null;
    int x = 0;int y = 0;boolean bv;
    Dimension scnSize = Toolkit.getDefaultToolkit().getScreenSize();
    x = (int)(scnSize.getWidth()/4);y = (int)(scnSize.getHeight()/5);
    for(int i=0;i<asArray.length;i++) {
      actionStub = asArray[i];
      bv = createFloatBarWindow(nodeViewer,actionStub,x,y); //创建FloatBar窗口
      if ( bv ) {x+=16;y+=16;}
    }
  }
  /**
   *
   * @param actionStub ActionStub
   */
  protected boolean createFloatBarWindow(NodeViewer nodeViewer,ActionStub actionStub,int x,int y) {
    Map floatBarMap = nodeViewer.getFloatBarMap();boolean res = false;
    boolean lastVisible = actionStub.getBoolean("lastVisible",true);
    ActionGroup ag = (ActionGroup)actionStub.getObject("action",null);
    String barID = (String)actionStub.getID();
    ToolBarDialog toolBarDialog = (ToolBarDialog)floatBarMap.get(barID);
    // 如果为空，则需要新建一个ToolBarDialog
    if ( toolBarDialog == null ) {
      toolBarDialog = ToolBarDialog.createToolBarDialog(EAI.EA.getMainWindow(),nodeViewer,actionStub.getCaption(),ag);
      toolBarDialog.setVisible(actionStub.isStartShow()); //根据开始的显示状态,设置Visible
      floatBarMap.put(barID,toolBarDialog); //缓冲到FloatBarMap中
      res = true;
      // 新建的ToolBarDialog需要设置位置
      setToolBarDialogPos(toolBarDialog,x,y);
    } else {// 如果不为空，则检查是否需要显示
      toolBarDialog.setVisible(lastVisible); // 根据上次的显示状态决定是否显示些Window
    }
    return res;
  }
  /**
   *
   * @param toolBarDialog ToolBarDialog
   * @param x int
   * @param y int
   */
  protected void setToolBarDialogPos(ToolBarDialog toolBarDialog,int x,int y) {
    toolBarDialog.setLocation(x,y);
  }
  /**
   *
   * @param nodeViewer NodeViewer
   */
  protected void hideFloatBarWindow(NodeViewer nodeViewer) {
    ActionStub[] asArray = nodeViewer.getFloatAction();
    if ( asArray == null ) return;
    Map floatBarMap = nodeViewer.getFloatBarMap();
    String barID = null;boolean lastVisible = false;
    ToolBarDialog toolBarDialog = null;
    for(int i=0;i<asArray.length;i++) {
      barID = (String)asArray[i].getID();
      toolBarDialog = (ToolBarDialog)floatBarMap.get(barID);
      if ( toolBarDialog != null ) {
        lastVisible = toolBarDialog.isVisible();  // 记录当前FloatBar的显示状态
        asArray[i].setBoolean("lastVisible",lastVisible); //将状态设置到asArray中
        toolBarDialog.setVisible(false);
        toolBarDialog.dispose();
      }
    }
  }
  /**
   *
   * @param NOW_ACTIVE_WINDOW NodeViewer
   */
  public void openAttribView(NodeViewer NOW_ACTIVE_WINDOW) {
    RUN_NOW_ACTIVE_WINDOW = NOW_ACTIVE_WINDOW;
    SwingUtilities.invokeLater(this);
  }


  protected NodeViewer RUN_NOW_ACTIVE_WINDOW = null;
  private void runOpenAttribView(NodeViewer NOW_ACTIVE_WINDOW) {
    if ( NOW_ACTIVE_WINDOW == null ) return;
    try {
      openExplorerWindow(NOW_ACTIVE_WINDOW);
      openStructView(NOW_ACTIVE_WINDOW);
      openPropertyView(NOW_ACTIVE_WINDOW);
      openBottomView(NOW_ACTIVE_WINDOW);
      showFloatBarWindow(NOW_ACTIVE_WINDOW);


    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(0,e,this,"创建结构视图或属性视图失败！");
    } finally {
      if ( !isInitJSplitPanel ) {
        initJSplitPanel();
        isInitJSplitPanel = true;
      }
      RUN_NOW_ACTIVE_WINDOW = null;
    }
  }
  private boolean isInitJSplitPanel = false;
  public void run() {

    runOpenAttribView(RUN_NOW_ACTIVE_WINDOW);
    // 如果是最大化
    if ( this.isMaxWindow() ) {
      // 先保存当前状态
      byte[] _screenLayout = EnterpriseExplorer.getExplorer().getDockableHolder().getDockingManager().getLayoutRawData();
      this.setMaxLayoutData(_screenLayout);
      // 再设为最大化
      EnterpriseExplorer.getExplorer().getDockableHolder().getDockingManager().autohideAll();
    }

    // 设置存局
//    byte[] _screenLayout = this.getMaxLayoutData();
//    if ( _screenLayout != null )
//      EnterpriseExplorer.getExplorer().getDockableHolder().getDockingManager().setLayoutRawData(_screenLayout);
//
//    if ( this.isMaxWindow() ) {
//      EnterpriseExplorer.getExplorer().getDockableHolder().getDockingManager().autohideAll();
//      EnterpriseExplorer.getExplorer().getDockableHolder().getDockingManager().
//    }

  }

  public void closeAttribView(NodeViewer LAST_ACTIVE_WINDOW) {
//    this.setStructViewVisible(LAST_ACTIVE_WINDOW,EnterpriseExplorer.StructView.isVisible());
//    this.setPropertyViewVisible(LAST_ACTIVE_WINDOW,EnterpriseExplorer.PropertyView.isVisible());
    closeStructView();
    closePropertyView();
    closeExplorerWindow(LAST_ACTIVE_WINDOW);
    hideFloatBarWindow(LAST_ACTIVE_WINDOW);
    //
    structViewArray   = null;
    propertyViewArray = null;
  }
  /**
   *
   */
  protected java.util.Map nodeExplorerWindowMap = new HashMap();
  /**
   *
   * @param NOW_ACTIVE_WINDOW NodeViewer
   */
  public void openExplorerWindow(NodeViewer NOW_ACTIVE_WINDOW) {
    Object exp = nodeExplorerWindowMap.get(NOW_ACTIVE_WINDOW);
    if ( exp != null && exp instanceof String ) return;
    NodeExplorerWindow explorerWindow = (NodeExplorerWindow)exp;
    if ( explorerWindow == null ) {
      try {
        explorerWindow = NodeExplorerWindowUtil.openExpObject(nodeStub, NOW_ACTIVE_WINDOW);
        if ( explorerWindow != null ) {
          nodeExplorerWindowMap.put(NOW_ACTIVE_WINDOW, explorerWindow);
          explorerWindow.setWinOwner(NOW_ACTIVE_WINDOW);
        } else {
          nodeExplorerWindowMap.put(NOW_ACTIVE_WINDOW, "1");
        }
      } catch ( Exception e ) {e.printStackTrace();}
    }
    if ( explorerWindow != null ) {
      if ( NOW_ACTIVE_WINDOW.isAttribViewVisible(explorerWindow,true) ) {
        EnterpriseExplorer.ExplorerView.openChildWindow(explorerWindow,nodeStub.toString(),"",this.nodeStub.getNodeIcon());
        NOW_ACTIVE_WINDOW.setAttribViewVisible(explorerWindow,true);
      }
//      if ( EnterpriseExplorer.ExplorerView.isOpen(explorerWindow) )
//        EnterpriseExplorer.ExplorerView.setVisible(explorerWindow,true);
//      else
//        EnterpriseExplorer.ExplorerView.openChildWindow(explorerWindow,nodeStub.toString(),"",this.nodeStub.getNodeIcon());
    }
  }
  /**
   *
   * @param LAST_ACTIVE_WINDOW NodeViewer
   */
  public void closeExplorerWindow(NodeViewer LAST_ACTIVE_WINDOW) {
    Object exp = nodeExplorerWindowMap.get(LAST_ACTIVE_WINDOW);
    if ( exp != null && exp instanceof String ) return;
    NodeExplorerWindow explorerWindow = (NodeExplorerWindow)exp;
    if ( explorerWindow != null ) {
      EnterpriseExplorer.ExplorerView.closeChildWindow(explorerWindow);
    }
  }

  /**
   *
   * @return IWindow
   */
  public NodeAttribView[] getStructureComponent(NodeViewer NOW_ACTIVE_WINDOW) throws Exception {
    // 将当前显示的NodeView的ID获取
    String Key = NOW_ACTIVE_WINDOW.getNodeViewID();
    JExplorerView ExplorerView = this.getNodeStub().getExplorerView();
    Context context = new Context(ExplorerView, this.getNodeStub());
    context.setNodeViewer(NOW_ACTIVE_WINDOW);
    NodeAttribView[] nodeAttribView = null;
    if ( Key != null )
      nodeAttribView = (NodeAttribView[])this.nodeStructViewList.get(Key);
    if ( nodeAttribView == null ) {
      NodeAttribViewerFactory[] nvfs = NodeStructViewManager.
          getNodeViewerFactorys(context, LAST_ACTIVE_WINDOW, this, ExplorerView);
      // ֻ只获取第一个视
      if ( nvfs != null ) {
        nodeAttribView = new NodeAttribView[nvfs.length];
        for (int i = 0; i < nvfs.length; i++) {
          nodeAttribView[i] = nvfs[i].createNodeViewer(context,
              LAST_ACTIVE_WINDOW, this, ExplorerView);

          nodeAttribView[i].setNodeAttribViewerFactory(nvfs[i]);
          nodeAttribView[i].setID(nodeAttribView[i].getClass().getName()+nodeAttribView[i].hashCode());
          nodeAttribView[i].setWinOwner(NOW_ACTIVE_WINDOW);
//          nodeAttribView[i].setID(nvfs[i].getStubObject().getString("id",
//              nodeAttribView.getClass().getName()));
          nodeAttribView[i].initNodeViewer(context,LAST_ACTIVE_WINDOW,this,ExplorerView);
        }
        nodeStructViewList.put(Key, nodeAttribView);
      }
    }
//    if ( nodeAttribView != null ) // 每次都进行初始化
//      for(int i=0;i<nodeAttribView.length;i++)
//        nodeAttribView[i].initNodeViewer(context,LAST_ACTIVE_WINDOW,this,ExplorerView);
    return nodeAttribView;
  }
  /**
   *
   * @return IWindow[]
   */
  public NodeAttribView[] getPropertyComponent(NodeViewer NOW_ACTIVE_WINDOW) throws Exception {
    // 将当前显示的NodeView的ID获取
    String Key = NOW_ACTIVE_WINDOW.getNodeViewID();
    JExplorerView ExplorerView = this.getNodeStub().getExplorerView();
    Context context = new Context(ExplorerView, this.getNodeStub());
    context.setNodeViewer(NOW_ACTIVE_WINDOW);
    NodeAttribView[] nodeAttribView = null;NodeAttribView nv = null;
    if ( Key != null )
      nodeAttribView = (NodeAttribView[])this.nodePropertyViewList.get(Key);
    if ( nodeAttribView == null ) {
      NodeAttribViewerFactory[] nvfs = NodePropertyViewManager.
          getNodeViewerFactorys(context, LAST_ACTIVE_WINDOW, this, ExplorerView);
    // ֻ只获取第一个视
      if ( nvfs != null ) {
        nodeAttribView = new NodeAttribView[nvfs.length];
        for (int i = 0; i < nvfs.length; i++) {
          nv = nvfs[i].createNodeViewer(context, LAST_ACTIVE_WINDOW, this,
                                        ExplorerView);
          nv.setNodeAttribViewerFactory(nvfs[i]);
          nodeAttribView[i] = nv;
          // 设置ID
          nv.setID(nvfs[i].getStubObject().getString("id",nv.getClass().getName()));
          nodeAttribView[i].setWinOwner(NOW_ACTIVE_WINDOW);
          nodeAttribView[i].initNodeViewer(context,LAST_ACTIVE_WINDOW,this,ExplorerView);
        }
        nodePropertyViewList.put(Key, nodeAttribView);
      }
    }
//    if ( nodeAttribView != null ) //  每次都进行初始化
//      for(int i=0;i<nodeAttribView.length;i++)
//        nodeAttribView[i].initNodeViewer(context,LAST_ACTIVE_WINDOW,this,ExplorerView);
    return nodeAttribView;
  }

  /**
   *
   */
  protected void closeStructView() {
    EnterpriseExplorer.StructView.closeChildWindow(null);
  }
  /**
   *
   */
  protected void closePropertyView() {
    EnterpriseExplorer.PropertyView.closeChildWindow(null);
  }
  /**
   *
   * @param nodeViewer NodeViewer
   * @throws Exception
   */
  protected void openBottomView(NodeViewer nodeViewer) throws Exception {
    // 如果不为空，说明打开过，不需要再打开了，无需关闭
    if ( bottomViewArray != null ) return;
    // 获取节点自己拱提的类
    IWindow[] winArray = null;//nodeViewer.getTopComponent();
    // 如果为空,则使用注册的方式进行获取
    if ( winArray == null )
      winArray = getBottomComponent(nodeViewer);
    if ( winArray == null ) return;
    bottomViewArray = winArray;
    for (int i = 0; i < winArray.length; i++) {
      openBottomWindow(nodeViewer,
                       winArray[i],
                       winArray[i].getTitle(),
                       winArray[i].getTips(),
                       winArray[i].getIcon());
    }
  }
  public void openBottomWindow(NodeViewer nodeViewer,IWindow cw,String title,String tips,Icon icon) {

  }
  public NodeAttribView[] getBottomComponent(NodeViewer NOW_ACTIVE_WINDOW) throws Exception {
    // 将当前显示的NodeView的ID获取
    String Key = NOW_ACTIVE_WINDOW.getNodeViewID();
    JExplorerView ExplorerView = this.getNodeStub().getExplorerView();
    Context context = new Context(ExplorerView, this.getNodeStub());
    context.setNodeViewer(NOW_ACTIVE_WINDOW);
    NodeAttribView[] nodeAttribView = null;NodeAttribView nv = null;
    if ( Key != null )
      nodeAttribView = (NodeAttribView[])this.nodeBottomViewList.get(Key);
    if ( nodeAttribView == null ) {
      NodeAttribViewerFactory[] nvfs = NodeBottomViewManager.
          getNodeViewerFactorys(context, LAST_ACTIVE_WINDOW, this, ExplorerView);
    // ֻ只获取第一个视
      if ( nvfs != null ) {
        nodeAttribView = new NodeAttribView[nvfs.length];
        for (int i = 0; i < nvfs.length; i++) {
          nv = nvfs[i].createNodeViewer(context, LAST_ACTIVE_WINDOW, this,
                                        ExplorerView);
          nv.setNodeAttribViewerFactory(nvfs[i]);
          nodeAttribView[i] = nv;
          // 设置ID
          nv.setID(nvfs[i].getStubObject().getString("id",nv.getClass().getName()));
          nodeAttribView[i].setWinOwner(NOW_ACTIVE_WINDOW);
          nodeAttribView[i].initNodeViewer(context,LAST_ACTIVE_WINDOW,this,ExplorerView);
        }
        nodeBottomViewList.put(Key, nodeAttribView);
      }
    }
//    if ( nodeAttribView != null ) //  每次都进行初始化
//      for(int i=0;i<nodeAttribView.length;i++)
//        nodeAttribView[i].initNodeViewer(context,LAST_ACTIVE_WINDOW,this,ExplorerView);
    return nodeAttribView;
  }
  protected void openPropertyView(NodeViewer nodeViewer) throws Exception {
    // 获取节点自己拱提的类
    IWindow[] winArray = nodeViewer.getPropertyComponent();
    // 如果为空,则使用注册的方式进行获取
    if ( winArray == null )
      winArray = getPropertyComponent(nodeViewer);
    if ( winArray == null )
      closePropertyView();
    else {
      propertyViewArray = winArray;
      // 检查是否需要显示
//      if ( winArray.length > 0 ) {
//        EnterpriseExplorer.PropertyView.setVisible(this.isPropertyViewVisible(nodeViewer));
//        EnterpriseExplorer.PropertyView.setLocation(0.7);
//      }
      for (int i = 0; i < winArray.length; i++) {
        // 默认为false
        if ( nodeViewer.isAttribViewVisible(winArray[i],false) ) {
          EnterpriseExplorer.PropertyView.openChildWindow(winArray[i],
              winArray[i].getTitle(),
              winArray[i].getTips(),
              winArray[i].getIcon());
        }
      }
    }
  }
  public void openExplorerView(Object actionObject,Object dataObject,Action action,ActionEvent actionevent) {
    NodeExplorerWindow nav = (NodeExplorerWindow)dataObject;
    EnterpriseExplorer.ExplorerView.setVisible(true);
    EnterpriseExplorer.StructView.setLocation(0.6);
    EnterpriseExplorer.ExplorerView.openChildWindow(nav);
    NodeViewer nodeViewer = this.getActiveNodeViewer();
    nodeViewer.setAttribViewVisible(nav,true);
  }
  public void openStructView(Object actionObject,Object dataObject,Action action,ActionEvent actionevent) {
    NodeAttribView nav = (NodeAttribView)dataObject;
    EnterpriseExplorer.StructView.setVisible(true);
    EnterpriseExplorer.StructView.setLocation(0.6);
    EnterpriseExplorer.StructView.openChildWindow(nav);
    NodeViewer nodeViewer = this.getActiveNodeViewer();
    nodeViewer.setAttribViewVisible(nav,true);
  }
  public void openPropertyView(Object actionObject,Object dataObject,Action action,ActionEvent actionevent) {
    NodeAttribView nav = (NodeAttribView)dataObject;
    EnterpriseExplorer.PropertyView.setVisible(true);
    EnterpriseExplorer.PropertyView.setLocation(0.8);
    EnterpriseExplorer.PropertyView.openChildWindow(nav);
    NodeViewer nodeViewer = this.getActiveNodeViewer();
    nodeViewer.setAttribViewVisible(nav,true);
  }

  IWindow[] structViewArray   = null;
  IWindow[] propertyViewArray = null;
  IWindow[] bottomViewArray = null;
  /**
   *
   */
  JPanel pnNorth = new JPanel();
  JPanel pnSouth = new JPanel();
  JPanel pnWest = new JPanel();
  JPanel pnEast = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  /**
   *
   * @return Action
   */
  public Action getNodeAttribViewAction() {
    ActionGroup ag = new ActionGroup();NodeAttribView nav = null;
    //
    NodeExplorerWindow explorerWindow = null;
    if ( nodeExplorerWindowMap.get(getSelectedNodeView()) instanceof NodeExplorerWindow ) {
      explorerWindow = (NodeExplorerWindow) nodeExplorerWindowMap.get(getSelectedNodeView());
    }
    if ( structViewArray == null && propertyViewArray == null && explorerWindow == null ) return null;
    // 如果ExplorerWindow不为空，则需要处理
    if ( explorerWindow != null ) {
      ActionGroup explorerGroup = new ActionGroup(getActiveNodeViewer().getViewerTitle()+"资源视图",'0',"节点资源视图",EnterpriseIcons.ICON_IDL_UNION,true);
      Action action = new com.efounder.action.ActiveObjectAction(this,explorerWindow,"openExplorerView",explorerWindow.getTitle(),'0',explorerWindow.getTips(),explorerWindow.getIcon());
      explorerGroup.add(action);
      ag.add(explorerGroup);
    }

    if ( structViewArray != null ) {
      ActionGroup structGroup = new ActionGroup(getActiveNodeViewer().getViewerTitle()+"结构视图",'0',"节点结构视图",EnterpriseIcons.ICON_BROWSESYMBOL,true);
      for(int i=0;i<structViewArray.length;i++) {
        nav = (NodeAttribView)structViewArray[i];
        Action action = new com.efounder.action.ActiveObjectAction(this,nav,"openStructView",nav.getTitle(),'0',nav.getTips(),nav.getIcon());
        structGroup.add(action);
      }
      ag.add(structGroup);
    }
    if ( propertyViewArray != null ) {
      ActionGroup propertyGroup = new ActionGroup(getActiveNodeViewer().getViewerTitle()+"属性视图",'0',"节点属性视图",EnterpriseIcons.ICON_ARCHIVE_MODULE,true);
      for(int i=0;i<propertyViewArray.length;i++) {
        nav = (NodeAttribView)propertyViewArray[i];
        Action action = new com.efounder.action.ActiveObjectAction(this,nav,"openPropertyView",nav.getTitle(),'0',nav.getTips(),nav.getIcon());
        propertyGroup.add(action);
      }
      ag.add(propertyGroup);
    }

    return ag;
  }

  /**
   *
   * @param nodeViewer NodeViewer
   */
  protected void openStructView(NodeViewer nodeViewer) throws Exception {
    // 首先获取节点自己提供的类
    IWindow[] winArray = nodeViewer.getStructureComponent();
    // 如果节点没有提供相应的类，则使用注册的方法进行获取
    if ( winArray == null )
      winArray = getStructureComponent(nodeViewer);
    if ( winArray == null ) {
      EnterpriseExplorer.StructView.closeChildWindow(null);
    } else {
      structViewArray = winArray;
      if(winArray.length!=0&&!EnterpriseExplorer.StructView.isVisible())
        EnterpriseExplorer.StructView.setVisible(true);
      for(int i=0;i<winArray.length;i++) {

        if ( nodeViewer.isAttribViewVisible(winArray[i],true) ) {
          // 打开
          EnterpriseExplorer.StructView.openChildWindow(winArray[i],
                                                        winArray[i].getTitle(),
                                                        winArray[i].getTips(),
                                                        winArray[i].getIcon());
          // 设置状态
          nodeViewer.setAttribViewVisible(winArray[i],true);
        }
      }
//        if ( winArray.length > 0 ) {
//          EnterpriseExplorer.StructView.setVisible(this.isStructViewVisible(
//              nodeViewer));
//          EnterpriseExplorer.StructView.setLocation(0.6);
//        }
      }
  }
  public boolean canPagesetup() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      return ((FileActionInterface)nv).canPagesetup();
    }
    return false;
  }
  public void    pageSetup() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      ((FileActionInterface)nv).pageSetup();
    }
  }
  // 是否可以打印
  public boolean canPrint() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      return ((FileActionInterface)nv).canPrint();
    }
    return false;
  }
  // 是否可以预览
  public boolean canPreview() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      return ((FileActionInterface)nv).canPreview();
    }
    return false;
  }
  // 是否可以保存
  public boolean canSave() {
    boolean res = nodeStub.canSave();
    if ( !res ) {
      NodeViewer nv = getActiveNodeViewer();
      if ( nv != null && nv instanceof FileActionInterface ) {
        return ((FileActionInterface)nv).canSave();
      }
    }
    return res;
  }
  // 是否已修改
  public boolean isModified() {
    boolean res = nodeStub.isModified();
    if ( !res ) {
      NodeViewer nv = getActiveNodeViewer();
      if ( nv != null && nv instanceof FileActionInterface ) {
        return ((FileActionInterface)nv).isModified();
      }
    }
    return res;
  }
  public void setModified(boolean m) {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      ((FileActionInterface)nv).setModified(m);
    }
    this.nodeStub.setModified(m);
    super.setModified(m);
  }
  
  public void newNode() {
		// TODO Auto-generated method stub
	  NodeViewer nv = getActiveNodeViewer();
	    if ( nv != null && nv instanceof FileActionInterface ) {
	      ((FileActionInterface)nv).newNode();
	    }
  }
  
  // 打印
  public void printNode() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      ((FileActionInterface)nv).printNode();
    }
  }
  // 预览
  public void previewNode() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      ((FileActionInterface)nv).previewNode();
    }
  }
  // 保存
  public void saveNode() throws Exception {
//    try {
      // 用于节点保存
      if ( (nodeStub.getNodeDataManager() != null ||
           nodeStub.getNodeDataStub() != null) && !nodeStub.isSaveNodeView() ) {
        nodeStub.doSave();
      } else {// 用于视图保存
        saveNodeView();
      }
      String err= (String) EAI.getEnv("ERR",null);//不得已而为之
        if(!"1".equals(err)){
         this.setModified(false);
        }
//    } catch ( Exception e ) {
//      ErrorManager.getDefault().notify(0,e);
//    }
  }
  /**
   *
   */
  protected void saveNodeView() throws Exception {
    NodeViewer[] nvs = this.getNodeViewerArray();
    for(int i=0;i<nvs.length;i++) {
      // 如果为null，则继续处理下一个视图
      if ( nvs[i] == null ) continue;
      JBOFClass.CallObjectMethodException(nvs[i],"saveNode");
    }
  }
//  /**
//   *
//   * @return boolean
//   */
//  public boolean canCopy() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      return ((EditActionInterface)nv).canCopy();
//    }
//    return false;
//  }
//  public boolean canCut() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      return ((EditActionInterface)nv).canCut();
//    }
//    return false;
//  }
//  public boolean canDelete() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      return ((EditActionInterface)nv).canDelete();
//    }
//    return false;
//  }
//  public boolean canPaste() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      return ((EditActionInterface)nv).canPaste();
//    }
//    return false;
//
//  }
//  public boolean canUndo() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      return ((EditActionInterface)nv).canUndo();
//    }
//    return false;
//  }
//  public void editCopy() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      ((EditActionInterface)nv).editCopy();
//    }
//  }
//  public void editCut() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      ((EditActionInterface)nv).editCut();
//    }
//  }
//  public void editDelete() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      ((EditActionInterface)nv).editDelete();
//    }
//  }
//  public void editPaste() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      ((EditActionInterface)nv).editPaste();
//    }
//
//  }
//  public void editUndo() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      ((EditActionInterface)nv).editUndo();
//    }
//
//  }
//  public void editRedo() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof EditActionInterface ) {
//      ((EditActionInterface)nv).editRedo();
//    }
//  }
//  public boolean canCalc() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof CalcActionInterface ) {
//      return ((CalcActionInterface)nv).canCalc();
//    }
//    return false;
//
//  }
//  public boolean canCheck() {
//   NodeViewer nv = getActiveNodeViewer();
//   if ( nv != null && nv instanceof CalcActionInterface ) {
//     return ((CalcActionInterface)nv).canCheck();
//   }
//   return false;
//
//  }
//  public boolean canCheckReport() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof CalcActionInterface ) {
//      return ((CalcActionInterface)nv).canCheckReport();
//    }
//    return false;
//
//  }
//  public boolean canChart() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof CalcActionInterface ) {
//      return ((CalcActionInterface)nv).canChart();
//    }
//    return false;
//
//  }
//  public void calcNode() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof CalcActionInterface ) {
//      ((CalcActionInterface)nv).calcNode();
//    }
//  }
//  public void checkNode() {
//    NodeViewer nv = getActiveNodeViewer();
//   if ( nv != null && nv instanceof CalcActionInterface ) {
//     ((CalcActionInterface)nv).checkNode();
//   }
//
//  }
//  public void checkReportNode() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof CalcActionInterface ) {
//      ((CalcActionInterface)nv).checkReportNode();
//    }
//
//  }
//  public void chartNode() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof CalcActionInterface ) {
//      ((CalcActionInterface)nv).chartNode();
//    }
//  }
//  public boolean canMoveFirst() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof MoveActionInterface ) {
//      return ((MoveActionInterface)nv).canMoveFirst();
//    }
//    return false;
//  }
//  public boolean canMoveNext() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof MoveActionInterface ) {
//      return ((MoveActionInterface)nv).canMoveNext();
//    }
//    return false;
//
//  }
//  public boolean canMovePrior() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof MoveActionInterface ) {
//      return ((MoveActionInterface)nv).canMovePrior();
//    }
//    return false;
//
//  }
//  public boolean canMoveLast() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof MoveActionInterface ) {
//      return ((MoveActionInterface)nv).canMoveLast();
//    }
//    return false;
//
//  }
//  public void moveFirst() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof MoveActionInterface ) {
//      ((MoveActionInterface)nv).moveFirst();
//    }
//
//  }
//  public void moveNext() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof MoveActionInterface ) {
//      ((MoveActionInterface)nv).moveNext();
//    }
//
//  }
//  public void movePrior() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof MoveActionInterface ) {
//      ((MoveActionInterface)nv).movePrior();
//    }
//
//  }
//  public void moveLast() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof MoveActionInterface ) {
//      ((MoveActionInterface)nv).moveLast();
//    }
//
//  }
//  public boolean canSearch() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof SearchActionInterface ) {
//      return ((SearchActionInterface)nv).canSearch();
//    }
//    return false;
//
//  }
//  public boolean canReplace() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof SearchActionInterface ) {
//      return ((SearchActionInterface)nv).canReplace();
//    }
//    return false;
//  }
//  public void searchNode() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof SearchActionInterface ) {
//      ((SearchActionInterface)nv).searchNode();
//    }
//
//  }
//  public void searchAgainNode() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof SearchActionInterface ) {
//      ((SearchActionInterface)nv).searchAgainNode();
//    }
//
//  }
//  public void replaceNode() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof SearchActionInterface ) {
//      ((SearchActionInterface)nv).replaceNode();
//    }
//  }
//  public boolean canWizard() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof ToolsActionInterface ) {
//      return ((ToolsActionInterface)nv).canWizard();
//    }
//    return false;
//  }
//  public void doWizard() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof ToolsActionInterface ) {
//      ((ToolsActionInterface)nv).doWizard();
//    }
//  }

  public void closeWindowEvent(IView view) {
    super.closeWindowEvent(view);
//    this.closeAttribView(LAST_ACTIVE_WINDOW);
//    removeWindowAction();
    this.nodeStub.setModified(false);
    //fsz 所有窗口都关闭了，显示出导航窗口
    if(EnterpriseExplorer.ContentView.getActiveWindow()==null){
      EnterpriseExplorer.ExplorerView.setVisible(true);
      EnterpriseExplorer.StructView.setVisible(false);
    }
    //
    this.writeOperateLoginfo();
    closeNodeView();
    removeAutoUpdateComponent();
  }
  protected void removeAutoUpdateComponent() {
      // 清掉自动状态管理的组件
//    ActionToolBar.removeAutoUpdateComponent(this);
      NodeViewer[] nvs = this.getNodeViewerArray();
      for(int i=0;i<nvs.length;i++) {
          ActionToolBar.removeAutoUpdateComponent(nvs[i].getViewerComponent());
          IWindow[] ws = nvs[i].getPropertyComponent();
          for(int j=0;ws!=null&&j<ws.length;j++) {
              ActionToolBar.removeAutoUpdateComponent(ws[j].getWindowComponent());
          }
          ws = nvs[i].getStructureComponent();
          for(int j=0;ws!=null&&j<ws.length;j++) {
              ActionToolBar.removeAutoUpdateComponent(ws[j].getWindowComponent());
          }
      }
  }
  /**
   *
   */
  protected void closeNodeView() {
    NodeViewer[] nvs = this.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return;
    for(int i=0;i<nvs.length;i++) {
      nvs[i].nodeViewClose(this);
    }
  }
  protected void removeWindowAction() {
    // 将用户可定制的组清除掉
    EnterpriseExplorer.removeAllToolBarGroup(1);
    // 将用户可定制的组清除掉
    EnterpriseExplorer.removeAllToolBarGroup(2);
  }

  /**
   * canExport
   *
   * @return boolean
   */
  public boolean canExport() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      return ((FileActionInterface)nv).canExport();
    }
    return false;
  }

  /**
   * canImport
   *
   * @return boolean
   */
  public boolean canImport() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      return ((FileActionInterface)nv).canImport();
    }
    return false;
  }

  /**
   * exportData
   */
  public void exportData() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      ((FileActionInterface)nv).exportData();
    }
  }

  /**
   * importData
   */
  public void importData() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      ((FileActionInterface)nv).importData();
    }
  }
//
//  /**
//   * canSaveAs
//   *
//   * @return boolean
//   */
//  public boolean canSaveAs() {
//    NodeViewer nv = getActiveNodeViewer();
//    if ( nv != null && nv instanceof FileActionInterface ) {
//      return ((FileActionInterface)nv).canSaveAs();
//    }
//    return false;
//  }

  /**
   * saveAsNode
   */
  public void saveAsNode() {
    NodeViewer nv = getActiveNodeViewer();
    if ( nv != null && nv instanceof FileActionInterface ) {
      ((FileActionInterface)nv).saveAsNode();
    }
  }
  public Icon getGroupIcon() {
    if ( this.groupIcon == null )
      return this.nodeStub.getNodeIcon();
    return super.getGroupIcon();
  }
  /**
   * @return String
   */
  public String getGroupID() {
    if ( this.groupID == null )
      return this.nodeStub.getGroupID();
    return super.getGroupID();
  }
  /**
   * 获取组名称
   * @return String
   */
  public String getGroupCaption() {
    if ( this.groupCaption == null )
      return this.nodeStub.getGroupCaption();
    return super.getGroupCaption();
  }






  /**
   *
   */
  protected void autoNodeViewTitle() {

  }
  /**
   *
   * @param comp Component
   * @return JDialog
   */
//  public static JDialog getDialog(Component comp) {
//    if ( comp instanceof JDialog ) {
//      return (JDialog)comp;
//    }
//    return getDialog(comp.getParent());
//  }
  /**
   *
   * @param comp Component
   * @return NodeChildWindow
   */
  public static NodeChildWindow getNodeChildWindow(Component comp) {
    if ( comp instanceof NodeViewer ) {
      return (NodeChildWindow)((NodeViewer)comp).getNodeWindow();
    }
    if ( comp instanceof NodeChildWindow || comp == null )
      return (NodeChildWindow)comp;
    else
      return getNodeChildWindow(comp.getParent());
  }
  /**
   *
   * @param comp Component
   * @return IView
   */
  public static IView  getView(Component comp) {
    if ( comp instanceof IView || comp == null )
      return (IView)comp;
    else
      return getView(comp.getParent());
  }
  /**
   *
   * @param comp Component
   * @return JDialog
   */
  public static JDialog getDialog(Component comp) {
    if ( comp instanceof JDialog || comp == null )
      return (JDialog)comp;
    else
      return getDialog(comp.getParent());
  }
  /**
   *
   * @param comp Component
   * @return JDialog
   */
  public static JPDialog getPDialog(Component comp) {
    if ( comp instanceof JPDialog || comp == null )
      return (JPDialog)comp;
    else
      return getPDialog(comp.getParent());
  }
  /**
   *
   * @return String
   */
  public String getBIZUnit() {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        return ((BIZContext)nodeStub).getBIZUnit();
      }
      return null;
    }
    BIZContext bizContext = null;String bizUnit = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizUnit = bizContext.getBIZUnit();
      if ( bizUnit != null ) return bizUnit;
    }
    if ( this.nodeStub instanceof BIZContext ) {
      return ((BIZContext)nodeStub).getBIZUnit();
      }
    return null;
  }
  /**
   *
   * @return String
   */
  public String getBIZType() {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        return ((BIZContext)nodeStub).getBIZType();
      }
      return null;
    }
    BIZContext bizContext = null;String bizType = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizType = bizContext.getBIZType();
      if ( bizType != null ) return bizType;
    }
    if ( this.nodeStub instanceof BIZContext ) {
      return ((BIZContext)nodeStub).getBIZType();
    }
    return null;
  }
  /**
   *
   * @return String
   */
  public String getDateType() {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        return ((BIZContext)nodeStub).getDateType();
      }
      return null;
    }
    BIZContext bizContext = null;String bizSDate = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizSDate = bizContext.getDateType();
      if ( bizSDate != null ) return bizSDate;
    }
    if ( this.nodeStub instanceof BIZContext ) {
      return ((BIZContext)nodeStub).getDateType();
    }
    return null;
  }
  /**
   *
   * @return String
   */
  public String getBIZSDate() {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        return ((BIZContext)nodeStub).getBIZSDate();
      }
      return null;
    }
    BIZContext bizContext = null;String bizSDate = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizSDate = bizContext.getBIZSDate();
      if ( bizSDate != null ) return bizSDate;
    }
    if ( this.nodeStub instanceof BIZContext ) {
      return ((BIZContext)nodeStub).getBIZSDate();
    }
    return null;
  }
  /**
   *
   * @return String
   */
  public String getBIZEDate() {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        return ((BIZContext)nodeStub).getBIZEDate();
      }
      return null;
    }
    BIZContext bizContext = null;String bizEDate = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizEDate = bizContext.getBIZEDate();
      if ( bizEDate != null ) return bizEDate;
    }
    if ( this.nodeStub instanceof BIZContext ) {
      return ((BIZContext)nodeStub).getBIZEDate();
    }
    return null;
  }
  /**
   *
   * @param object Object
   * @param object1 Object
   * @return Object
   */
  public Object getBIZValue(Object object, Object object1) {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        return ((BIZContext)nodeStub).getBIZValue(object,object1);
      }
      return null;
    }
    BIZContext bizContext = null;Object BIZValue = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      BIZValue = bizContext.getBIZValue(object,object1);
      if ( BIZValue != null ) return BIZValue;
    }
    if ( this.nodeStub instanceof BIZContext ) {
      return ((BIZContext)nodeStub).getBIZValue(object,object1);
    }
    return object1;
  }
  /**
   *
   * @param object Object
   * @param object1 Object
   */
  public void   callBack(Object object,Object object1) {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        ((BIZContext)nodeStub).callBack(object,object1);
        return;
      }
      return;
    }
    BIZContext bizContext = null;Object BIZValue = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizContext.callBack(object,object1);
    }
    if ( this.nodeStub instanceof BIZContext ) {
      ((BIZContext)nodeStub).callBack(object,object1);
      return;
    }
  }
  /**
   *
   * @param object Object
   * @param object1 Object
   * @return Object
   */
  public void setBIZValue(Object object, Object object1) {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        ((BIZContext)nodeStub).setBIZValue(object,object1);
        return;
      }
      return;
    }
    BIZContext bizContext = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizContext.setBIZValue(object,object1);
    }
    if ( this.nodeStub instanceof BIZContext ) {
      ((BIZContext)nodeStub).setBIZValue(object,object1);
      return;
    }
  }
  public String getBIZContextUnit() {return null;}
  public String getBIZContextType() {return null;}
  public String getBIZContextDateType() {return null;}
  public String getBIZContextSDate() {return null;}
  public String getBIZContextEDate() {return null;}
  public Object getBIZContextValue(Object object, Object object1) {return null;}
  public void setBIZContextValue(Object object, Object object1) {return;}
  public void bizContextCallBack(Object object, Object object1) {return;}
  /**
   *
   * @param contextObject Object
   * @param addinObject Object
   */
  public void   initBIZContext(Object sourceObject,Object contextObject,Object addinObject){};
  public void   changeEvent(int eventType,Object sourceObject,Object contextObject,Object addinObject){};
  public void enumBIZKey(java.util.List list) {
    if ( contextCompList == null || contextCompList.size() == 0 ) {
      if ( this.nodeStub instanceof BIZContext ) {
        ((BIZContext)nodeStub).enumBIZKey(list);
        return;
      }
      return;
    }
    BIZContext bizContext = null;
    for(int i=0;i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizContext.enumBIZKey(list);
    }
    if ( this.nodeStub instanceof BIZContext ) {
      ((BIZContext)nodeStub).enumBIZKey(list);
      return;
    }
  }
  public void bizContextEnumKey(java.util.List list) {

  }
  public int indexOfNodeView(NodeViewer nodeVierer) {
    return 0;
  }
  public void setNodeViewerIconAt(int index,Icon icon) {

  }
  public void setNodeViewerTitleAt(int index,String v) {

  }
  public void setNodeViewerToolTipAt(int index,String v) {

  }
  protected Object getContextObject() {return this;};
  protected Object getAddinObject() {return this;};
  /**
   *
   * @param contextObject Object
   * @param addinObject Object
   */
  public void initCompBIZContext(){
//    java.util.List contextList = new java.util.ArrayList();
    BIZContextManager bizContext = BIZContextManager.getInstance((NodeWindow)this);
    bizContext.initBIZContext(this,getContextObject(),getAddinObject());
//    BIZContextManager.processContextComponent(this,contextList);
//    BIZContext bizContext = null;
//    for(int i=0;i<contextList.size();i++) {
//      bizContext = (BIZContext)contextList.get(i);
//      if ( bizContext != null ) bizContext.initBIZContext(this,getContextObject(),getAddinObject());
//    }
  };
  public JParamObject convertParamObject(Object userObject,JParamObject po){return po;}
  public void setCallBackValue(Object key,Object value){}
  public java.util.Map getCallBackMap(){return null;}
  public void addBIZContext(BIZContext bizContext) {}

  public String getPrepareAttString() {
    return null;
  }
  /**
   *
   * @param l ChangeListener
   */
  public void addChangeListener(ChangeListener l) {

  }
  /**
   *
   * @param l ChangeListener
   */
  public void removeChangeListener(ChangeListener l) {

  }
  /**
   *
   * @return OperateLoginfo
   */
  public OperateLoginfo getOperateLoginfo() {
    return operateLoginfo;
  }
  /**
   *
   */
  protected void writeOperateLoginfo() {
    if ( operateLoginfo == null ) return;
    ServiceSecurityManager.getDefault().writeOperateLog(operateLoginfo);
  }
  /**
   *
   */
  protected OperateLoginfo operateLoginfo = null;
  /**
   *
   */
  protected void createOperateLoginfo() {
    if ( this.nodeStub == null ) return;
    if ( nodeStub instanceof JPrepareNodeStub ) return;
    String gnbh =null;String gnmc = nodeStub.toString();
    StubObject stub = nodeStub.getNodeStubObject();
    if ( stub != null )
      gnbh = stub.getString("GNBH",null);
    // 处理节点的自定义功能编号，用于操作日志
    if ( gnbh == null ) {
      gnbh = nodeStub.getOperateNo();
    }
    if ( gnbh == null && nodeStub.getID() != null ) gnbh = nodeStub.getID().toString();
    if ( gnbh == null ) gnbh = nodeStub.getClass().getSimpleName();
    operateLoginfo = ServiceSecurityManager.getDefault().createLoginfo(gnbh,gnmc);
  }
  /**
   *
   */
  public void removeUserComponent() {
    NodeViewer[] nvs = this.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return;
    for(int i=0;i<nvs.length;i++) {
      JBOFClass.CallObjectMethod(nvs[i],"removeUserComponent");
    }
  }







  protected java.util.Map maxMap              = new java.util.HashMap();
  protected java.util.Map maxLayoutDataMap    = new java.util.HashMap();
  protected java.util.Map switchLayoutDataMap = new java.util.HashMap();

  // 增加对布局管理的属性
  /**
   *
   * @return byte[]
   */
  public byte[] getMaxLayoutData() {    // 最大化之前的布局数据
    NodeViewer nodeViewer = this.getSelectedNodeView();
    return getMaxLayoutData(nodeViewer);
  }
  protected byte[] getMaxLayoutData(NodeViewer nodeViewer) {
    return (byte[])maxLayoutDataMap.get(nodeViewer);
  }
  /**
   *
   * @param maxLayoutData byte[]
   */
  public void setMaxLayoutData(byte[] maxLayoutData) {
    NodeViewer nodeViewer = this.getSelectedNodeView();
    setMaxLayoutData(nodeViewer,maxLayoutData);
//    this.maxLayoutData = maxLayoutData;
  }
  protected void setMaxLayoutData(NodeViewer nodeViewer,byte[] maxLayoutData) {
    maxLayoutDataMap.put(nodeViewer,maxLayoutData);
  }
  /**
   *
   * @return byte[]
   */
  public byte[] getSwitchLayoutData() { // 窗口切换的布局数据
    NodeViewer nodeViewer = this.getSelectedNodeView();
    return getSwitchLayoutData(nodeViewer);
  }
  protected byte[] getSwitchLayoutData(NodeViewer nv) {
    return (byte[])switchLayoutDataMap.get(nv);
  }
  /**
   *
   * @param switchLayoutData byte[]
   */
  public void setSwitchLayoutData(byte[] switchLayoutData) {
    NodeViewer nodeViewer = this.getSelectedNodeView();
    setSwitchLayoutData(nodeViewer,switchLayoutData);
  }
  protected void setSwitchLayoutData(NodeViewer nv,byte[] switchLayoutData) {
    switchLayoutDataMap.put(nv,switchLayoutData);
  }
  /**
   *
   */
//  private boolean maxWindow = false;
  // 设置最大化状态
  /**
   *
   * @param isMaxWindow boolean
   */
  public void setMaxWindow(boolean mx) {
    NodeViewer nodeViewer = this.getSelectedNodeView();
    setMaxWindow(nodeViewer,mx);
  }
  protected void setMaxWindow(NodeViewer nv,boolean mx) {
    maxMap.put(nv,new Boolean(mx));
  }
  // 是否最大化状态
  /**
   *
   * @return boolean
   */
  public boolean isMaxWindow() {
    NodeViewer nodeViewer = this.getSelectedNodeView();
    return isMaxWindow(nodeViewer);
  }
  protected boolean isMaxWindow(NodeViewer nodeViewer) {
    Boolean b = (Boolean)maxMap.get(nodeViewer);
    return (b == null)?false:b.booleanValue();
  }

  private boolean reMemberWnd = true;

  public void setReMemberWnd(boolean mem) {
    reMemberWnd = mem;
  }
  public boolean isReMemberWnd() {
    return reMemberWnd;
  }







}
