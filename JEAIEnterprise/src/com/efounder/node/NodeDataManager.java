package com.efounder.node;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeDataManager {
  protected static java.util.Map nodeDataManagerList = new Hashtable();
  protected java.util.Map nodeDataActionListenerList = null;
  protected java.util.Map nodeDataList = null;//new Hashtable();
  protected JNodeStub nodeStub = null;
  protected boolean   modified = false;
  public void setModified(boolean modified) {
    this.modified = modified;
  }
  /**
   *
   * @return Object[]
   */
  public Object[] enumKeys() {
    return nodeDataList.keySet().toArray();
  }
  /**
   * 枚举节点数据管理中的所有管理的节点数据
   * @return Map
   */
  public java.util.List Enumerate(String key) throws Exception {
    // 如果为空，说明还没有装入
    if ( nodeDataList == null )
      this.doLoad(key);
    return (java.util.List)nodeDataList.get(key);
  }
  /**
   *
   * @param key String
   */
  public void clear(String key) {
    if ( nodeDataList == null ) return;
    java.util.List dataList = (java.util.List)nodeDataList.get(key);
    Object[] Datas = dataList.toArray();
    for(int i=0;i<Datas.length;i++) {
      this.removeNodeData(key,(NodeDataStub)Datas[i]);
    }
    nodeDataList = null;
  }
  /**
   *
   * @param nodeStub JNodeStub
   */
  public void setNodeStub(JNodeStub nodeStub) {
    this.nodeStub = nodeStub;
  }
  /**
   *
   * @return boolean
   */
  public boolean isModified() {
    return modified;
  }

  public JNodeStub getNodeStub() {
    return nodeStub;
  }

  public NodeDataManager() {
  }
  /**
   *
   * @param key String
   * @return NodeDataManager
   */
  public static NodeDataManager getNodeDataManager(String key) {
    NodeDataManager ndm = (NodeDataManager)nodeDataManagerList.get(key);
    return ndm;
  }
  /**
   *
   * @param key String
   * @param ndm NodeDataManager
   */
  public static void regNodeDataManager(String key,NodeDataManager ndm) {
    nodeDataManagerList.put(key,ndm);
  }
  /**
   *
   * @param Key String
   * @param nds NodeDataStub
   * @param actionObject Object
   */
  public Context insertNodeData(String Key,NodeDataStub nds) {
    JNodeStub nodeStub = null;
    Context context = new Context(null,nodeStub);
    insertNodeData(Key,nds,context);
    return context;
  }
  /**
   *
   * @param Key String
   * @param nds NodeDataStub
   * @param actionObject Context
   */
  public Context insertNodeData(String Key,NodeDataStub nds,Context actionObject) {
    if ( nodeDataList == null ) nodeDataList = new Hashtable();
    java.util.List dataList = (java.util.List)nodeDataList.get(Key);
    if ( dataList == null ) {
      dataList = new ArrayList();
      nodeDataList.put(Key,dataList);
    }
    if ( nds != null ) {
      nds.setNodeDataManager(this); // 设置当前节点数据的管理对象
      dataList.add(nds);
    }
    this.setModified(true);
    this.fireInsertNodeDataActionListener(Key,nds,actionObject);
    if ( actionObject.getRetNode() != null ) {
      actionObject.getRetNode().setParentNode(actionObject.getParentNodeStub());
      actionObject.getRetNode().setNodeDataManager(this);
    }
    return actionObject;
  }
  /**
   *
   * @param Key String
   * @param nds NodeDataStub
   * @param actionObject Object
   */
  public void removeNodeData(String Key,NodeDataStub nds) {
    JNodeStub nodeStub = null;
    Context context = new Context(null,nodeStub);
    removeNodeData(Key,nds,context);
  }

  public void removeNodeData(String Key,NodeDataStub nds,Context actionObject) {
    if ( nodeDataList == null ) return;
    java.util.List dataList = (java.util.List)nodeDataList.get(Key);
    if ( dataList == null ) return;
    dataList.remove(nds);    this.setModified(true);
    this.fireRemoveNodeDataActionListener(Key,nds,actionObject);
  }
  /**
   *
   * @param Key String
   * @param nds NodeDataStub
   * @param actionObject Object
   */
  public void changeNodeData(String Key,NodeDataStub nds) {
    JNodeStub nodeStub = null;
    Context context = new Context(null,nodeStub);
    changeNodeData(Key,nds,context);
  }
  /**
   *
   * @param Key String
   * @param nds NodeDataStub
   * @param actionObject Context
   */
  public void changeNodeData(String Key,NodeDataStub nds,Context actionObject) {
    if ( nodeDataList == null ) return;
    java.util.List dataList = (java.util.List)nodeDataList.get(Key);
    if ( dataList == null ) return;
    this.setModified(true);
    this.fireChangeNodeDataActionListener(Key,nds,actionObject);
  }

  /**
   *
   * @param ServiceKey String
   * @param sal ServiceActionListener
   */
  public synchronized void addNodeDataActionListener(String ServiceKey,NodeDataActionListener sal) {
    if ( ServiceKey == null ) return;
    if ( nodeDataActionListenerList == null ) nodeDataActionListenerList = new Hashtable();
    List ListenerList = (List)nodeDataActionListenerList.get(ServiceKey);
    if ( ListenerList == null ) {
      ListenerList = new ArrayList();
      nodeDataActionListenerList.put(ServiceKey,ListenerList);
    }
    ListenerList.add(sal);
  }
  /**
   *
   * @param ServiceKey String
   * @param sal ServiceActionListener
   */
  public synchronized void removeNodeDataActionListener(String ServiceKey,NodeDataActionListener sal) {
    if ( ServiceKey == null ) return;
    if ( nodeDataActionListenerList == null ) return;
    List ListenerList = (List)nodeDataActionListenerList.get(ServiceKey);
    if ( ListenerList == null ) return;
    ListenerList.remove(sal);
  }
  /**
   *
   * @param ServiceKey String
   * @param stubObject StubObject
   */
  public void fireInsertNodeDataActionListener(String ServiceKey,NodeDataStub nodeDataStub,Context actionObject) {
    if ( ServiceKey == null ) return;
    if ( nodeDataActionListenerList == null ) return;
    List ListenerList = (List)nodeDataActionListenerList.get(ServiceKey);
    if ( ListenerList == null ) return;NodeDataActionListener sal = null;
    for(int i=0;i<ListenerList.size();i++) {
      sal = (NodeDataActionListener)ListenerList.get(i);
      if ( sal != null ) {
        sal.insertNodeDataStubEvent(ServiceKey,nodeDataStub,actionObject);
      }
    }
  }
  /**
   *
   * @param ServiceKey String
   * @param stubObject StubObject
   */
  public void fireRemoveNodeDataActionListener(String ServiceKey,NodeDataStub nodeDataStub,Context actionObject) {
    if ( ServiceKey == null ) return;
    if ( nodeDataActionListenerList == null ) return;
    List ListenerList = (List)nodeDataActionListenerList.get(ServiceKey);
    if ( ListenerList == null ) return;NodeDataActionListener sal = null;
    for(int i=0;i<ListenerList.size();i++) {
      sal = (NodeDataActionListener)ListenerList.get(i);
      if ( sal != null ) {
        sal.removeNodeDataStubEvent(ServiceKey,nodeDataStub,actionObject);
      }
    }
  }
  /**
   *
   * @param ServiceKey String
   * @param stubObject StubObject
   */
  public void fireChangeNodeDataActionListener(String ServiceKey,NodeDataStub nodeDataStub,Context actionObject) {
    if ( ServiceKey == null ) return;
    if ( nodeDataActionListenerList == null ) return;
    List ListenerList = (List)nodeDataActionListenerList.get(ServiceKey);
    if ( ListenerList == null ) return;NodeDataActionListener sal = null;
    for(int i=0;i<ListenerList.size();i++) {
      sal = (NodeDataActionListener)ListenerList.get(i);
      if ( sal != null ) {
        sal.changeNodeDataStubEvent(ServiceKey,nodeDataStub,actionObject);
      }
    }
  }
  /**
   *
   * @param ServiceKey String
   * @param stubObject StubObject
   */
  public void fireRefreshNodeDataActionListener(String ServiceKey,NodeDataStub nodeDataStub,Context actionObject) {
    if ( ServiceKey == null ) return;
    if ( nodeDataActionListenerList == null ) return;
    List ListenerList = (List)nodeDataActionListenerList.get(ServiceKey);
    if ( ListenerList == null ) return;NodeDataActionListener sal = null;
    for(int i=0;i<ListenerList.size();i++) {
      sal = (NodeDataActionListener)ListenerList.get(i);
      if ( sal != null ) {
        sal.refreshNodeDataStubEvent(ServiceKey,nodeDataStub,actionObject);
      }
    }
  }
  public void doSave() throws Exception {

  }
  public void doLoad() throws Exception {

  }
  public void doSave(Object Key) throws Exception {

  }
  public void doLoad(Object Key) throws Exception {

  }

}
