package com.efounder.node;

import com.efounder.model.biz.*;
import com.efounder.node.view.*;
import java.awt.*;
import javax.swing.*;
import com.efounder.eai.data.*;
import com.efounder.eai.ide.*;
import com.efounder.pfc.window.*;
import com.efounder.action.ActionToolBarPane;
import com.efounder.action.*;

public class BIZContextManager implements BIZContext {
  /**
   *
   */
  protected BIZContextManager() {
  }
  protected JNodeStub       nodeStub   = null;
  protected NodeWindow nodeWindow = null;
  protected JDialog dialog = null;
  protected NodeViewer      nodeViewer = null;
  protected BIZContext[]    userBizContexts = null;
  protected Object          userObject = null;
  protected java.util.List  windowContextList = null;
  protected java.util.List  viewerContextList = null;
  protected static BIZContext EnvContext = EnvBIZContext.getBIZContext();

  /**
   *
   * @param userBizContexts BIZContext[]
   * @param userObject Object
   * @return BIZContextManager
   */
  public static BIZContextManager getInstance(BIZContext[] userBizContexts,Object userObject) {
    BIZContextManager bizContextManager = new BIZContextManager();
    java.util.List toolbarBIZContextList = getBIZContextToolbar();
    bizContextManager.userBizContexts = getUserBizContexts(userBizContexts,toolbarBIZContextList);
    bizContextManager.userObject      = userObject;
    bizContextManager.prepareBIZContext(userObject);
    return bizContextManager;
  }
  protected static BIZContext[] getUserBizContexts(BIZContext[] userBizContexts,
      java.util.List toolbarBIZContextList) {
    for(int i=0;userBizContexts!=null&&i<userBizContexts.length;i++) {
      if ( userBizContexts[i] == null ) continue;
      toolbarBIZContextList.add(i,userBizContexts[i]);
    }
    BIZContext[] ucs = new BIZContext[toolbarBIZContextList.size()];
    ucs = (BIZContext[])toolbarBIZContextList.toArray(ucs);
    return ucs;
  }
  public static BIZContextManager getInstance(BIZContext[] userBizContexts) {
    return getInstance(userBizContexts,null);
  }
  public static BIZContextManager getInstance(Object userObject) {
    return getInstance((BIZContext[])null,userObject);
  }
  public static BIZContextManager getInstance(NodeWindow nodeWindow) {
    return getInstance((BIZContext[])null,(Object)nodeWindow);
  }
  public static BIZContextManager getInstance(BIZContext userBizContext) {
    BIZContext[] userBizContexts = {userBizContext};
    return getInstance(userBizContexts);
  }
  public static BIZContextManager getInstance(BIZContext userBizContext,Object userObject) {
    BIZContext[] userBizContexts = {userBizContext};
    return getInstance(userBizContexts,userObject);
  }
  /**
   *
   * @param userObject Object
   */
  protected void prepareBIZContext(Object userObject) {
    if ( userObject instanceof Component ) {
      findNodeWindow((Component)userObject);
    }
    if ( userObject instanceof JNodeStub ) {
      findNodeStub((JNodeStub)userObject);
    }
    if ( userObject == null ) {
      findNodeWindow((Component)userObject);
    }
    // 处理NodeWindow之上的Context Comp
    windowContextList = new java.util.ArrayList();
    if ( nodeWindow != null )
      findContextList(nodeWindow,nodeWindow.getViewPane(),windowContextList);
    if ( dialog != null ) {
      findContextList(dialog, null, windowContextList);
      if ( dialog instanceof BIZContext )
        windowContextList.add((BIZContext)dialog);
    }
    // 处理NodeViewer之上的Context Comp
    viewerContextList = new java.util.ArrayList();
    if ( nodeViewer != null ) {
      findContextList(nodeViewer.getViewerComponent(), null, viewerContextList);
      // 如果NodeExpWindow不为空，则增加到列表中
      if ( nodeViewer.getNodeExpWindow() !=null ) {
        viewerContextList.add(nodeViewer.getNodeExpWindow());
      }
    }
  }
  /**
   *
   * @param userObject JNodeStub
   */
  protected void findNodeStub(JNodeStub userObject) {
    nodeStub = userObject;
    if ( userObject.getNodeWindow() != null )
      nodeWindow = userObject.getNodeWindow();
    if ( nodeWindow != null ) nodeViewer = nodeWindow.getActiveNodeViewer();
  }
  /**
   *
   * @param userObject Component
   */
  protected void findNodeWindow(Component userObject) {
    NodeViewer nv = NodeViewerManager.findNodeViewer(userObject);
    if ( nv != null ) {
      nodeWindow = nv.getNodeWindow();
      nodeViewer = nodeWindow.getActiveNodeViewer();
      nodeStub = nodeWindow.getNodeStub();
      return;
    }
    nodeWindow = NodeChildWindow.getNodeChildWindow((Component)userObject);
    dialog = null;
    if ( nodeWindow == null ) { // 如果为空，则检查一下是否是对话框
      dialog = NodeChildWindow.getDialog(userObject);
    }
    if ( nodeWindow == null && dialog == null ) {
      IWindow window = EnterpriseExplorer.findExplorer(userObject).ContentView.getActiveWindow();
      if ( window != null )
        nodeWindow = NodeChildWindow.getNodeChildWindow(window.getWindowComponent());
    }
    if ( nodeWindow != null ) {
      nodeViewer = nodeWindow.getActiveNodeViewer();
      nodeStub = nodeWindow.getNodeStub();
    }
  }
  /**
   *
   * @param container Container
   * @param exclComp Component
   * @param contextList List
   */
  protected void findContextList(Container container,Component exclComp,java.util.List contextList) {
    Component[] childComps = container.getComponents();
    for(int i=0;i<childComps.length;i++) {
      // 如果是排除的列表，则继续
      if ( exclComp != null && childComps[i].equals(exclComp) ) continue;
      if ( childComps[i] instanceof BIZContext ) { // 如果是BIZContext，则不处理其子组件了
        contextList.add(childComps[i]);
      } else if ( childComps[i] instanceof Container ) {// 如果不是BIZContxt,并且还是容器
        findContextList((Container)childComps[i],exclComp,contextList);
      }
    }
  }
  /**
   * callBack
   *
   * @param object Object
   * @param object1 Object
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public void callBack(Object object, Object object1) {
    BIZContext bizContext = null;
    EnvContext.callBack(object,object1);
    // 节点的BIZContext
    if( nodeStub != null ) try {nodeStub.callBack(object,object1);}catch(Exception e){e.printStackTrace();};
    if ( nodeWindow != null )
      try {nodeWindow.bizContextCallBack(object,object1);}catch(Exception e){e.printStackTrace();};
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {bizContext.callBack(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeViewer != null )
      try {nodeViewer.bizContextCallBack(object,object1);}catch(Exception e){e.printStackTrace();};
    // Viewer的BIZContext;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {bizContext.callBack(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    // 用户提供的BIZContext
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {userBizContexts[i].callBack(object,object1);}catch(Exception e){e.printStackTrace();};
      }
    }
    // 处理固定维度
    BIZEntityAdapter.processFixValue(object);
    // 处理宏
    processParamValue(object);
  }
  /**
   *
   * @param callObject Object
   */
  protected void processParamValue(Object callObject) {
    JParamObject paramObject = (JParamObject)callObject;
    java.util.Map attribMap = paramObject.getAttriMap();
    if ( attribMap == null ) return;
    Object[] keys = attribMap.keySet().toArray();Object value = null;String svalue = null;
    for(int i=0;i<keys.length;i++) {
      value = attribMap.get(keys[i]);
      if ( value instanceof String ) {
        BIZEntityAdapter.processParamValue(paramObject,keys[i],(String)value);
      }
    }
  }
//  /**
//   *
//   * @param PO JParamObject
//   * @param key Object
//   * @param value String
//   */
//  protected void processParamValue(JParamObject PO,Object key,String value) {
//    // 如果不是宏，则不需要处理
//    if ( !value.startsWith("@") || !value.endsWith("@") ) return;
//    value = value.substring(1,value.length()-1);
//    Object v = PO.getBIZValue(value,value);
//    if ( v instanceof String ) {
//      PO.setBIZValue(key,v);
//    }
//  }
  /**
   * getBIZEDate
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getBIZEDate() {
    // 用户提供的BIZContext
    String value = null;
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {value=null;value = userBizContexts[i].getBIZEDate();}catch(Exception e){e.printStackTrace();};
        if ( value != null && value.trim().length() > 0 ) return value;
      }
    }
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {value=null;value = bizContext.getBIZEDate();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeViewer != null ) {
      try {value=null;value = nodeViewer.getBIZContextEDate();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {value=null;value = bizContext.getBIZEDate();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeWindow != null ) {
      try {value=null;value = nodeWindow.getBIZContextEDate();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {value=null;value = nodeStub.getBIZEDate();}catch(Exception e){e.printStackTrace();};
      if (value != null&& value.trim().length() > 0 ) return value;
    }
    return EnvContext.getBIZEDate();
  }

  /**
   * getBIZSDate
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getBIZSDate() {
    // 用户提供的BIZContext
    String value = null;
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {value=null;value = userBizContexts[i].getBIZSDate();}catch(Exception e){e.printStackTrace();};
        if ( value != null && value.trim().length() > 0 ) return value;
      }
    }
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {value=null;value = bizContext.getBIZSDate();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeViewer != null ) {
      try {value=null;value = nodeViewer.getBIZContextSDate();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {value=null;value = bizContext.getBIZSDate();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeWindow != null ) {
      try {value=null;value = nodeWindow.getBIZContextSDate();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {value=null;value = nodeStub.getBIZSDate();}catch(Exception e){e.printStackTrace();};
      if (value != null&& value.trim().length() > 0 ) return value;
    }
    return EnvContext.getBIZSDate();
  }

  /**
   * getBIZType
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getBIZType() {
    // 用户提供的BIZContext
    String value = null;
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {value=null;value = userBizContexts[i].getBIZType();}catch(Exception e){e.printStackTrace();};
        if ( value != null && value.trim().length() > 0 ) return value;
      }
    }
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {value=null;value = bizContext.getBIZType();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeViewer != null ) {
      try {value=null;value = nodeViewer.getBIZContextType();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {value=null;value = bizContext.getBIZType();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeWindow != null ) {
      try {value=null;value = nodeWindow.getBIZContextType();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {value=null;value = nodeStub.getBIZType();}catch(Exception e){e.printStackTrace();};
      if (value != null&& value.trim().length() > 0 ) return value;
    }
    return EnvContext.getBIZType();
  }

  /**
   * getBIZUnit
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getBIZUnit() {
    // 用户提供的BIZContext
    String value = null;
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {value=null;value = userBizContexts[i].getBIZUnit();}catch(Exception e){e.printStackTrace();};
        if ( value != null && value.trim().length() > 0 ) return value;
      }
    }
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {value=null;value = bizContext.getBIZUnit();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeViewer != null ) {
      try {value=null;value = nodeViewer.getBIZContextUnit();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {value=null;value = bizContext.getBIZUnit();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeWindow != null ) {
      try {value=null;value = nodeWindow.getBIZContextUnit();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {value=null;value = nodeStub.getBIZUnit();}catch(Exception e){e.printStackTrace();};
      if (value != null&& value.trim().length() > 0 ) return value;
    }
    return EnvContext.getBIZUnit();
  }

  /**
   * getBIZValue
   *
   * @param object Object
   * @param object1 Object
   * @return Object
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public Object getBIZValue(Object object, Object object1) {
    // 用户提供的BIZContext
    Object value = null;
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {value=null;value = userBizContexts[i].getBIZValue(object,object1);}catch(Exception e){e.printStackTrace();};
        if ( value != null ) return value;
      }
    }
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {value=null;value = bizContext.getBIZValue(object,object1);}catch(Exception e){e.printStackTrace();};
      if ( value != null ) return value;
    }
    if ( nodeViewer != null ) {
      try {value=null;value = nodeViewer.getBIZContextValue(object,object1);}catch(Exception e){e.printStackTrace();};
      if ( value != null ) return value;
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {value=null;value = bizContext.getBIZValue(object,object1);}catch(Exception e){e.printStackTrace();};
      if ( value != null ) return value;
    }
    if ( nodeWindow != null ) {
      try {value=null;value = nodeWindow.getBIZContextValue(object,object1);}catch(Exception e){e.printStackTrace();};
      if ( value != null ) return value;
    }
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {value=null;value = nodeStub.getBIZValue(object,object1);}catch(Exception e){e.printStackTrace();};
      if (value != null) return value;
    }
    return EnvContext.getBIZValue(object,object1);
  }

  /**
   * getDateType
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getDateType() {
    // 用户提供的BIZContext
    String value = null;
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {value=null;value = userBizContexts[i].getDateType();}catch(Exception e){e.printStackTrace();};
        if ( value != null && value.trim().length() > 0 ) return value;
      }
    }
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {value=null;value = bizContext.getDateType();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeViewer != null ) {
      try {value=null;value = nodeViewer.getBIZContextDateType();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {value=null;value = bizContext.getDateType();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    if ( nodeWindow != null ) {
      try {value=null;value = nodeWindow.getBIZContextDateType();}catch(Exception e){e.printStackTrace();};
      if ( value != null && value.trim().length() > 0 ) return value;
    }
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {value=null;value = nodeStub.getDateType();}catch(Exception e){e.printStackTrace();};
      if (value != null&& value.trim().length() > 0 ) return value;
    }
    return EnvContext.getDateType();
  }
  public void   changeEvent(int eventType,Object sourceObject,Object object,Object object1){
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    EnvContext.changeEvent(eventType,sourceObject,object,object1);
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {nodeStub.changeEvent(eventType,sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {bizContext.changeEvent(eventType,sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
    }
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {bizContext.changeEvent(eventType,sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeViewer != null ) {
      try {nodeViewer.changeEvent(eventType,sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeWindow != null ) {
      try {nodeWindow.changeEvent(eventType,sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
    }
    // 用户提供的BIZContext
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {userBizContexts[i].changeEvent(eventType,sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
      }
    }
  };
  /**
   *
   * @param contextObject Object
   * @param addinObject Object
   */
  public void   initBIZContext(Object sourceObject,Object object,Object object1){
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {nodeStub.initBIZContext(sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
    }
//    if ( nodeWindow != null ) {
//      try {nodeWindow.initBIZContext(object,object1);}catch(Exception e){e.printStackTrace();};
//    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {bizContext.initBIZContext(sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
    }
//    if ( nodeViewer != null ) {
//      try {nodeViewer.setBIZContextValue(object,object1);}catch(Exception e){e.printStackTrace();};
//    }
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {bizContext.initBIZContext(sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
    }
    // 用户提供的BIZContext
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {userBizContexts[i].initBIZContext(sourceObject,object,object1);}catch(Exception e){e.printStackTrace();};
      }
    }
    EnvContext.initBIZContext(sourceObject,object,object1);
  };
  /**
   * setBIZValue
   *
   * @param object Object
   * @param object1 Object
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public void setBIZValue(Object object, Object object1) {
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {nodeStub.setBIZValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeWindow != null ) {
      try {nodeWindow.setBIZContextValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {bizContext.setBIZValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeViewer != null ) {
      try {nodeViewer.setBIZContextValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {bizContext.setBIZValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    // 用户提供的BIZContext
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {userBizContexts[i].setBIZValue(object,object1);}catch(Exception e){e.printStackTrace();};
      }
    }
    EnvContext.setBIZValue(object,object1);
  }
  /**
   *
   * @return List
   */
  protected static java.util.List getBIZContextToolbar() {
    java.util.List bizContextList = new java.util.ArrayList();
    ActionToolBarPane[] toolbars = EnterpriseExplorer.getExplorer().getToolBars();
    ActionToolBarPane toolbar = null;
    for(int i=0;toolbars!=null&&i<toolbars.length;i++) {
      toolbar = toolbars[i];if ( toolbar== null || !toolbar.isShowing() ) continue;
      processContextComponent(toolbar,bizContextList);
    }
    return bizContextList;
  }
  /**
   *
   * @param comp JComponent
   * @param bizContextList List
   */
  public static void processContextComponent(JComponent comp,java.util.List bizContextList) {
    if ( comp instanceof BIZContext ) {
      bizContextList.add((BIZContext)comp);
    }
    javax.swing.Action action = null;
    if ( comp instanceof ActionButton ) {
      action =  ((ActionButton)comp).getAction();
    }
    if ( comp instanceof ActionCombo ) {
      action =  ((ActionCombo)comp).getAction();
    }
    if ( comp instanceof ActionSlider ) {
      action =  ((ActionSlider)comp).getAction();
    }
    if ( comp instanceof ActionWidget ) {
      action =  ((ActionWidget)comp).getAction();
    }
    if ( action != null && action instanceof BIZContext ) {
      if ( bizContextList.indexOf(action) == -1 ) bizContextList.add((BIZContext)action);
    }
    Component[] comps = comp.getComponents();
    if ( comps == null || comps.length ==0 ) return;
    for(int i=0;i<comps.length;i++) {
      if ( comps[i] instanceof JComponent )
        processContextComponent((JComponent)comps[i],bizContextList);
    }
  }
  /**
   *
   * @param keyList List
   */
  public void   enumBIZKey(java.util.List keyList){
    BIZContext bizContext = null;
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {nodeStub.enumBIZKey(keyList);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeWindow != null ) {
      try {nodeWindow.bizContextEnumKey(keyList);}catch(Exception e){e.printStackTrace();};
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {bizContext.enumBIZKey(keyList);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeViewer != null ) {
      try {nodeViewer.bizContextEnumKey(keyList);}catch(Exception e){e.printStackTrace();};
    }
    // Viewer的BIZContext;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {bizContext.enumBIZKey(keyList);}catch(Exception e){e.printStackTrace();};
    }
    // 用户提供的BIZContext
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {userBizContexts[i].enumBIZKey(keyList);}catch(Exception e){e.printStackTrace();};
      }
    }
    EnvContext.enumBIZKey(keyList);
  }
  /**
   *
   * @param userObject Object
   * @return JParamObject
   */
  public JParamObject convertParamObject(Object userObject) {
    return convertParamObject(userObject,null);
  }
  /**
   *
   * @param userObject Object
   * @return JParamObject
   */
  public JParamObject convertParamObject(Object userObject,JParamObject po) {
    JParamObject paramObject = po;
    if ( paramObject == null )
      paramObject = (BIZParamObject)JParamObject.Create("BIZContext");
    Object value = null;

    BIZContext bizContext = null;
    EnvContext.convertParamObject(userObject,paramObject);
    // 节点的BIZContext
    if( nodeStub != null ) try {nodeStub.convertParamObject(userObject,paramObject);}catch(Exception e){e.printStackTrace();};
    if ( nodeWindow != null )
      try {nodeWindow.convertParamObject(userObject,paramObject);}catch(Exception e){e.printStackTrace();};
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {bizContext.convertParamObject(userObject,paramObject);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeViewer != null )
      try {nodeViewer.convertParamObject(userObject,paramObject);}catch(Exception e){e.printStackTrace();};
    // Viewer的BIZContext;
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {bizContext.convertParamObject(userObject,paramObject);}catch(Exception e){e.printStackTrace();};
    }
    // 用户提供的BIZContext
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {userBizContexts[i].convertParamObject(userObject,paramObject);}catch(Exception e){e.printStackTrace();};
      }
    }
    if ( paramObject != null ) {
      value = (String)getBIZUnit();
      if ( value != null ) paramObject.setBIZValue("BIZUnit",value);
      value = (String) getBIZType();
      if ( value != null ) paramObject.setBIZValue("BIZType",value);
      value = (String) getDateType();
      if ( value != null ) paramObject.setBIZValue("DateType",value);
      value = (String) getBIZSDate();
      if ( value != null ) paramObject.setBIZValue("BIZSDate",value);
      value = (String) getBIZEDate();
      if ( value != null ) paramObject.setBIZValue("BIZEDate",value);
      java.util.List keyList = new java.util.ArrayList();
      this.enumBIZKey(keyList);
      for(int i=0;i<keyList.size();i++) {
        value = this.getBIZValue(keyList.get(i),null);
        if ( value != null ) paramObject.setBIZValue(keyList.get(i),value);
      }
      callBack(paramObject, userObject);
    }
    // 处理固定维度
    BIZEntityAdapter.processFixValue(paramObject);
    // 处理宏
    processParamValue(paramObject);
    return paramObject;
  }
  public void setCallBackValue(Object object,Object object1){
    // Viewer的BIZContext;
    BIZContext bizContext = null;
    // 节点的BIZContext
    if( nodeStub != null ) {
      try {nodeStub.setCallBackValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeWindow != null ) {
      try {nodeWindow.setCallBackValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    // NodeWindow的BIZContext
    for(int i=0;windowContextList!=null&&i<windowContextList.size();i++) {
      bizContext = (BIZContext)windowContextList.get(i);
      try {bizContext.setCallBackValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    if ( nodeViewer != null ) {
      try {nodeViewer.setCallBackValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    for(int i=0;viewerContextList!=null&&i<viewerContextList.size();i++) {
      bizContext = (BIZContext)viewerContextList.get(i);
      try {bizContext.setCallBackValue(object,object1);}catch(Exception e){e.printStackTrace();};
    }
    // 用户提供的BIZContext
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        if ( userBizContexts[i] == null ) continue;
        try {userBizContexts[i].setCallBackValue(object,object1);}catch(Exception e){e.printStackTrace();};
      }
    }
    EnvContext.setCallBackValue(object,object1);
  }
  public java.util.Map getCallBackMap(){
    return null;
  }
  public void addBIZContext(BIZContext bizContext) {
    if ( bizContext == null ) return;
    java.util.List contextList = new java.util.ArrayList();
    if ( userBizContexts != null ) {
      for(int i=0;i<userBizContexts.length;i++) {
        contextList.add(userBizContexts[i]);
      }
    }
    contextList.add(bizContext);
    BIZContext[] ucs = new BIZContext[contextList.size()];
    ucs = (BIZContext[])contextList.toArray(ucs);
    userBizContexts = ucs;
  }

  public String getPrepareAttString() {
    return null;
  }
}
