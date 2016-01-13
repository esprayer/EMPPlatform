package com.efounder.node.view;

import java.beans.PropertyChangeListener;
import javax.swing.Action;
import com.efounder.node.NodeDataStub;
import com.efounder.pfc.window.IWindow;
import javax.swing.JComponent;
import javax.swing.Icon;
import com.efounder.node.Context;
import javax.swing.*;
import com.efounder.node.*;
import com.core.xml.*;
import com.efounder.eai.ide.*;
import java.awt.*;
import com.efounder.view.CompViewManager;
import com.efounder.view.*;
import com.efounder.actions.ActionManager;
import com.efounder.action.*;
import com.borland.jbcl.layout.VerticalFlowLayout;
import com.efounder.model.biz.*;
import java.util.List;
import com.efounder.pfc.window.JChildWindow;
import com.efounder.eai.data.JParamObject;
import java.util.Map;
import com.borland.jbcl.layout.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeViewerAdapter extends JPanel implements NodeViewer,BIZContext {
  BorderLayout borderLayout1 = new BorderLayout();

  public NodeViewerAdapter() {
    super();
  }
//  public NodeViewerAdapter() {
//    super();
//  }
  public void setViewerIcon(Icon icon) {
    int index = nodeChildWindow.indexOfNodeView(this);// this.nodeChildWindow.tpNodeView.indexOfComponent(this);
    if ( index == -1 ) return;
    nodeChildWindow.setNodeViewerIconAt(index,icon);
  }
  public void setViewerDescription(String v) {
    int index = nodeChildWindow.indexOfNodeView(this);// this.nodeChildWindow.tpNodeView.indexOfComponent(this);
    if ( index == -1 ) return;
    nodeChildWindow.setNodeViewerTitleAt(index,v);
  }
  public void setViewerTitle(String v) {
    int index = nodeChildWindow.indexOfNodeView(this);// this.nodeChildWindow.tpNodeView.indexOfComponent(this);
    if ( index == -1 ) return;
    nodeChildWindow.setNodeViewerToolTipAt(index,v);
  }

  public void setNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception {

  }
  public void initNodeViewer(Context context, Object p1, Object p2, IWindow nodeWindow) throws Exception {
  }
  public void initNodeViewer2(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception {

  }
  public void reInitNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception {

  }
  public void releaseViewer() {
  }
  public void browserDeactivated() {
  }
  public void browserActivated() {
  }
  public void viewerDeactivated() {
  }
  public void viewerDeactivating() {
  }
  public void viewerActivated(boolean boolean0) {
  }
  public void viewerNodeChanged() {
  }
//  public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
//  }
//  public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
//  }
  public IWindow[] getStructureComponent() {
    return null;
  }
  public IWindow[] getTopComponent() {
    return null;
  }
  public IWindow[] getPropertyComponent() {
    return null;
  }
  public JComponent getViewerComponent() {
    return this;
  }
  public Icon getViewerIcon() {
    if ( nodeViewerFactory == null ) return null;
//    StubObject so = nodeViewerFactory.getStubObject();
    String ii = nodeViewerFactory.getIcon();//so.getString("icon",null);
    Icon icon = null;
    if ( ii != null )
      icon = ExplorerIcons.getExplorerIcon(ii);
    return icon;
  }
  public String getViewerDescription() {
    return null;
  }
  public String getViewerTitle() {
    if ( nodeViewerFactory == null ) {
      JNodeStub nodeStub = getNodeStub();
      if ( nodeStub != null ) return nodeStub.toString();
      return this.toString();
    }
//    StubObject so = nodeViewerFactory.getStubObject();
    return nodeViewerFactory.getName();
//    JNodeStub nodeStub = getNodeStub();
//    if ( nodeStub != null ) return nodeStub.toString();
//    return this.toString();
  }
  /**
   *
   * @return Action
   */
  public Action getTopFrameAction() {
    return null;
  }
  /**
   *
   * @return Action
   */
  public Action getContextAction() {
    return null;
  }
  /**
   *
   * @return Action
   */
  public Action getEntityAction() {
    Action[] actions = ActionManager.getContextActions("nodeViewEntityAction",this,null);
    if ( actions == null ) return null;
    ActionGroup ag = null;
    for(int i=0;i<actions.length;i++) {
      if ( ag == null ) ag = new ActionGroup();
      ag.add(actions[i]);
    }
    return ag;
  }
  /**
   *
   * @return Action
   */
  public Action getFrameAction() {
    return null;
  }
  /**
   *
   * @return Action
   */
  public Action getPopupAction() {
    return null;
  }
  /**
   *
   * @param nodeDataStub NodeDataStub
   * @return Object
   */
  public Object getNodeDataObject(NodeDataStub nodeDataStub) {
    return null;
  }
  /**
   *
   */
  protected String nodeViewID = null;
  /**
   *
   * @return String
   */
  public String getNodeViewID() {
    if ( nodeViewID == null ) {
      return getClass().getName() + "[" + this.hashCode() + "]";
    } else
      return nodeViewID;
  }
  /**
   *
   * @param id String
   */
  public void setNodeViewID(String id) {
    nodeViewID = id;
  }
  /**
   *
   */
  protected NodeViewerFactory nodeViewerFactory = null;
  /**
   *
   * @param nvf NodeViewerFactory
   */
  public void setViewFactory(NodeViewerFactory nvf) {
    nodeViewerFactory = nvf;
  }
  /**
   *
   * @return NodeViewerFactory
   */
  public NodeViewerFactory getViewFactory() {
    return nodeViewerFactory;
  }
  protected NodeWindow nodeChildWindow = null;
  public void setNodeWindow(NodeWindow ncw) {
    nodeChildWindow = ncw;
  }
  public NodeWindow getNodeWindow() {
    return nodeChildWindow;
  }
  public JNodeStub getNodeStub() {
    return nodeChildWindow.getNodeStub();
  }
  JPanel pnNorth = new JPanel();
  JPanel pnWest = new JPanel();
  JPanel pnSouth = new JPanel();
  JPanel pnEast = new JPanel();
  /**
   *
   */
  protected void initSystemLayout() throws Exception {
    jbInit2();
  }
  /**
   *
   * @throws Exception
   */
  private void jbInit2() throws Exception {
    this.setLayout(borderLayout1);

    this.add(pnNorth, java.awt.BorderLayout.NORTH);
    FlowLayout flowLayout1 = new FlowLayout();
//    BorderLayout borderLayout = new BorderLayout();
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    pnNorth.setLayout(flowLayout1);

    this.add(pnSouth, java.awt.BorderLayout.SOUTH);
    flowLayout1 = new FlowLayout();
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    pnSouth.setLayout(flowLayout1);

    this.add(pnWest, java.awt.BorderLayout.WEST);
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    pnWest.setLayout(verticalFlowLayout1);
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);

    this.add(pnEast, java.awt.BorderLayout.EAST);
    verticalFlowLayout1 = new VerticalFlowLayout();
    pnEast.setLayout(verticalFlowLayout1);
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);

  }
  public void prepareViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception {
    initLeftTopView();
    initTopView();
    initSouthView();
    initWestView();
    initEastView();
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
    // ��ʼ��Action
    initNodeViewEastCompViewAction();
    // ��ʼ��View
    initNodeViewEastCompView();
  }
  /**
   *
   */
  protected void initNodeViewEastCompViewAction() {
    Action[] actionArray = ActionManager.getContextActions(this.getNodeViewID()+"_"+"NodeViewEastCompViewAction",this,this.getNodeStub());
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
    java.util.List compViewList = CompViewManager.getComponentView(this.getNodeViewID()+"_"+"NodeViewEastCompView",this,nodeChildWindow,null);
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
    // ��ʼ��Action
    initNodeViewWestCompViewAction();
    // ��ʼ��View
    initNodeViewWestCompView();
  }
  /**
   *
   */
  protected void initNodeViewWestCompViewAction() {
    Action[] actionArray = ActionManager.getContextActions(this.getNodeViewID()+"_"+"NodeViewWestCompViewAction",this,this.getNodeStub());
    ActionGroup ag = new ActionGroup();
    for(int i=0;actionArray!=null&&i<actionArray.length;i++) {
      ag.add(actionArray[i]);
    }
    if ( ag.getActionCount() > 0 ) {
      actionWestToolBar.addGroup(ag);
      actionWestToolBar.setVisible(true);
      actionWestToolBar.setVisible(true);
//      westToolBar.setVisible(true);
      pnWest.setVisible(true);
    }
  }
  /**
   *
   */
  protected void initNodeViewWestCompView() {
    java.util.List compViewList = CompViewManager.getComponentView(this.getNodeViewID()+"_"+"NodeViewWestCompView",this,nodeChildWindow,null);
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
//  JToolBar topToolBar = null;
  ExplorerViewToolBarPane actionTopToolBar = null;
  /**
   *
   */
  protected void initLeftTopView() {
//    topToolBar = new JToolBar();

    pnNorth.setVisible(false);
//    topToolBar.setBorder(null);
    // ��ʼ��Action
//    initNodeViewTopCompViewAction();
    // ��ʼ��View
    initNodeViewLeftTopCompView();
  }
  /**
   *
   */
  protected void initNodeViewLeftTopCompView() {
    java.util.List compViewList = CompViewManager.getComponentView(this.getNodeViewID()+"_"+"NodeViewLeftTopCompView",this,nodeChildWindow);
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
  protected void initTopView() {
    actionTopToolBar = new ExplorerViewToolBarPane(this);
    actionTopToolBar.setOpaque(false);
//    topToolBar.add(actionTopToolBar);
//    pnNorth.add(topToolBar);//,BorderLayout.CENTER);
    pnNorth.add(actionTopToolBar);
    pnNorth.setOpaque(false);
    // ��ʼ��Action
    initNodeViewTopCompViewAction();
    // ��ʼ��View
    initNodeViewTopCompView();
  }
  /**
   *
   */
  protected void initNodeViewTopCompViewAction() {
    Action[] actionArray = ActionManager.getContextActions(this.getNodeViewID()+"_"+"NodeViewTopCompViewAction",this,this.getNodeStub());
    ActionGroup ag = new ActionGroup();
    for(int i=0;actionArray!=null&&i<actionArray.length;i++) {
      ag.add(actionArray[i]);
    }
    if ( ag.getActionCount() > 0 ) {
      actionTopToolBar.addGroup(ag);
      pnNorth.setVisible(true);
    }
  }
  protected java.util.List contextCompList = null;
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
   * @return String
   */
  public String getBIZUnit() {
    BIZContext bizContext = null;String bizUnit = null;
    for(int i=0;contextCompList!=null&&i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizUnit = bizContext.getBIZUnit();
      if ( bizUnit != null ) return bizUnit;
    }
    bizUnit = this.nodeChildWindow.getBIZUnit();
    if ( bizUnit != null ) return bizUnit;
    NodeViewer[] nvs = nodeChildWindow.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return null;
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] instanceof BIZContext ) {
        bizUnit = ((NodeViewerAdapter)nvs[i]).getBIZContextUnit();
        if ( bizUnit != null ) return bizUnit;
      }
    }
    return null;
  }
  public String getBIZContextUnit() {return null;}
  /**
   *
   * @return String
   */
  public String getBIZType() {
    BIZContext bizContext = null;String bizType = null;
    for(int i=0;contextCompList!=null&&i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizType = bizContext.getBIZType();
      if ( bizType != null ) return bizType;
    }
    bizType = this.nodeChildWindow.getBIZType();
    if ( bizType != null ) return bizType;
    NodeViewer[] nvs = nodeChildWindow.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return null;
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] instanceof NodeViewerAdapter ) {
        bizType = ((NodeViewerAdapter)nvs[i]).getBIZContextType();
        if ( bizType != null ) return bizType;
      }
    }
    return null;
  }
  public String getBIZContextType() {return null;}
  /**
   *
   * @return String
   */
  public String getDateType() {
    BIZContext bizContext = null;String bizSDate = null;
    for(int i=0;contextCompList!=null&&i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizSDate = bizContext.getDateType();
      if ( bizSDate != null ) return bizSDate;
    }
    bizSDate = this.nodeChildWindow.getDateType();
    if ( bizSDate != null ) return bizSDate;
    NodeViewer[] nvs = nodeChildWindow.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return null;
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] instanceof NodeViewerAdapter ) {
        bizSDate = ((NodeViewerAdapter)nvs[i]).getBIZContextDateType();
        if ( bizSDate != null ) return bizSDate;
      }
    }
    return null;
  }
  public String getBIZContextDateType() {return null;}
  /**
   *
   * @return String
   */
  public String getBIZSDate() {
    BIZContext bizContext = null;String bizSDate = null;
    for(int i=0;contextCompList!=null&&i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizSDate = bizContext.getBIZSDate();
      if ( bizSDate != null ) return bizSDate;
    }
    bizSDate = this.nodeChildWindow.getBIZSDate();
    if ( bizSDate != null ) return bizSDate;
    NodeViewer[] nvs = nodeChildWindow.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return null;
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] instanceof NodeViewerAdapter ) {
        bizSDate = ((NodeViewerAdapter)nvs[i]).getBIZContextSDate();
        if ( bizSDate != null ) return bizSDate;
      }
    }
    return null;
  }
  public String getBIZContextSDate() {return null;}
  /**
   *
   * @return String
   */
  public String getBIZEDate() {
    BIZContext bizContext = null;String bizEDate = null;
    for(int i=0;contextCompList!=null&&i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizEDate = bizContext.getBIZEDate();
      if ( bizEDate != null ) return bizEDate;
    }
    bizEDate = this.nodeChildWindow.getBIZSDate();
    if ( bizEDate != null ) return bizEDate;
    NodeViewer[] nvs = nodeChildWindow.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return null;
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] instanceof NodeViewerAdapter ) {
        bizEDate = ((NodeViewerAdapter)nvs[i]).getBIZContextEDate();
        if ( bizEDate != null ) return bizEDate;
      }
    }
    return null;
  }
  public String getBIZContextEDate() {return null;}
  /**
   *
   * @param object Object
   * @param object1 Object
   * @return Object
   */
  public Object getBIZValue(Object object, Object object1) {
    BIZContext bizContext = null;Object BIZValue = null;
    for(int i=0;contextCompList!=null&&i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      BIZValue = bizContext.getBIZValue(object,object1);
      if ( BIZValue != null ) return BIZValue;
    }
    BIZValue = null;
    if ( nodeChildWindow != null )
      BIZValue = this.nodeChildWindow.getBIZValue(object,object1);
    if ( BIZValue != null ) return BIZValue;
    if(nodeChildWindow==null)return null;
    NodeViewer[] nvs = nodeChildWindow.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return null;
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] instanceof NodeViewerAdapter ) {
        BIZValue = ((NodeViewerAdapter)nvs[i]).getBIZContextValue(object,object1);
        if ( BIZValue != null ) return BIZValue;
      }
    }
    return null;
  }
  public Object getBIZContextValue(Object object, Object object1) {return null;}
  /**
   *
   * @param object Object
   * @param object1 Object
   * @return Object
   */
  public void setBIZValue(Object object, Object object1) {
    BIZContext bizContext = null;
    for(int i=0;contextCompList!=null&&i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizContext.setBIZValue(object,object1);
    }
    nodeChildWindow.setBIZValue(object,object1);
    NodeViewer[] nvs = nodeChildWindow.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return;
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] instanceof NodeViewerAdapter ) {
        ((NodeViewerAdapter)nvs[i]).setBIZContextValue(object,object1);
      }
    }
  }
  public void setBIZContextValue(Object object, Object object1) {return;}
  /**
   *
   * @param callObject Object
   * @param context Object
   */
  public void   callBack(Object object,Object object1) {
    BIZContext bizContext = null;
    for(int i=0;contextCompList!=null&&i<contextCompList.size();i++) {
      bizContext = (BIZContext)contextCompList.get(i);
      bizContext.callBack(object,object1);
    }
    nodeChildWindow.callBack(object,object1);
    NodeViewer[] nvs = nodeChildWindow.getNodeViewerArray();
    if ( nvs == null || nvs.length == 0 ) return;
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] instanceof NodeViewerAdapter ) {
        ((NodeViewerAdapter)nvs[i]).bizContextCallBack(object,object1);
      }
    }
  }
  public void bizContextCallBack(Object object, Object object1) {return;}
  /**
   *
   */
  protected void initNodeViewTopCompView() {
    java.util.List compViewList = CompViewManager.getComponentView(this.getNodeViewID()+"_"+"NodeViewTopCompView",this,nodeChildWindow);
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
//  JToolBar southToolBar = null;
  ExplorerViewToolBarPane actionSouthToolBar = null;
  /**
   *
   */
  protected void initSouthView() {
//    southToolBar = new JToolBar();
//    southToolBar.setBorder(null);
    actionSouthToolBar = new ExplorerViewToolBarPane(this);
//    southToolBar.add(actionSouthToolBar);
//    pnSouth.add(southToolBar);
    pnSouth.add(actionSouthToolBar);
    pnSouth.setVisible(false);
    // ��ʼ��Action
    initNodeViewSouthCompViewAction();
    // ��ʼ��View
    initNodeViewSouthCompView();
  }
  /**
   *
   */
  protected void initNodeViewSouthCompViewAction() {
    Action[] actionArray = ActionManager.getContextActions(this.getNodeViewID()+"_"+"NodeViewSouthCompViewAction",this,this.getNodeStub());
    ActionGroup ag = new ActionGroup();
    for(int i=0;actionArray!=null&&i<actionArray.length;i++) {
      ag.add(actionArray[i]);
    }
    if ( ag.getActionCount() > 0 ) {
      actionSouthToolBar.addGroup(ag);
      pnSouth.setVisible(true);
    }
  }
  /**
   *
   */
  protected void initNodeViewSouthCompView() {
    java.util.List compViewList = CompViewManager.getComponentView(this.getNodeViewID()+"_"+"NodeViewSouthCompView",this,nodeChildWindow);
    ComponentView cv = null;
    for(int i=0;i<compViewList.size();i++) {
      cv = (ComponentView)compViewList.get(i);
      if ( cv == null ) continue;
      if ( cv instanceof BIZContext ) {
        this.addContextComp((BIZContext)cv);
      }
      actionSouthToolBar.add(cv.getComponent());
//      southToolBar.add(cv.getComponent());
      pnSouth.setVisible(true);
    }
  }
  public boolean isAutoTitle() {
    return false;
  }
  /**
   *
   * @param list List
   */
  public void enumBIZKey(List list) {
  }
  /**
   *
   * @param list List
   */
  public void bizContextEnumKey(List list) {

  }
  protected NodeExplorerWindow nodeExpWindow = null;
  public void setNodeExpWindow(NodeExplorerWindow ncw) {
    nodeExpWindow = ncw;
  }
  public NodeExplorerWindow getNodeExpWindow() {
    return nodeExpWindow;
  }
  protected NodeViewer expNodeViewer = null;
  public NodeViewer getExpNodeViewer() {
    return expNodeViewer;
  }
  public void setExpNodeViewer(NodeViewer nv) {
    this.expNodeViewer = expNodeViewer;
  }
  protected Object getContextObject() {return this;};
  protected Object getAddinObject() {return this;};
  /**
   *
   * @param contextObject Object
   * @param addinObject Object
   */
  public void initCompBIZContext(){
    java.util.List contextList = new java.util.ArrayList();
    BIZContextManager.processContextComponent(this,contextList);
    BIZContext bizContext = null;
    if ( this.nodeExpWindow != null ) contextList.add(nodeExpWindow);
    for(int i=0;i<contextList.size();i++) {
      bizContext = (BIZContext)contextList.get(i);
      if ( bizContext != null ) bizContext.initBIZContext(this,getContextObject(),getAddinObject());
    }
  };
  public void initBIZContext(Object sourceObject,Object object, Object object1) {
  }
  public void   changeEvent(int eventType,Object sourceObject,Object contextObject,Object addinObject){};
  public JParamObject convertParamObject(Object userObject,JParamObject po){return po;}
  protected boolean modified = false;
  public void setModified(boolean v) {
    modified = v;
  }
  public boolean isModified() {
    return modified;
  }
  public void setCallBackValue(Object key,Object value){}
  public java.util.Map getCallBackMap(){return null;}
  public void addBIZContext(BIZContext bizContext) {}

  public String getPrepareAttString() {
    return null;
  }
  public ActionStub[] getFloatAction() {
    return null;
  }
  /**
   *
   */
  java.util.Map floatBarMap = null;
  /**
   *
   * @return Map
   */
  public Map getFloatBarMap() {
    // ���Ϊ�գ����½�һ��componentMap
    if ( floatBarMap == null ) floatBarMap = new java.util.HashMap();
    return floatBarMap;
  }
  public void nodeViewClose(NodeWindow nw) {

  }

  private java.util.Map attribViewVisibleMap = null;
  /**
   *
   * @param attribView Object
   * @param def boolean
   * @return boolean
   */
  public boolean isAttribViewVisible(Object attribView,boolean def) {
    if ( attribViewVisibleMap == null ) return def;
    Boolean rv =  ((Boolean)attribViewVisibleMap.get(attribView));
    if ( rv != null ) return rv.booleanValue();
    return def;
  }
  /**
   *
   * @param attribView Object
   * @param value boolean
   */
  public void setAttribViewVisible(Object attribView,boolean value) {
    if ( attribViewVisibleMap == null ) attribViewVisibleMap = new java.util.HashMap();
    attribViewVisibleMap.put(attribView,new Boolean(value));
  }
}
