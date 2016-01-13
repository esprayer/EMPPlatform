package com.efounder.node;

import com.borland.dx.dataset.*;
import com.efounder.view.*;
import com.core.xml.*;
import com.efounder.pfc.window.*;
import com.efounder.node.view.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Context extends StubObject {
  /**
   *
   */
  protected JNodeStub mainNodeStub = null;
  /**
   *
   * @param node JNodeStub
   */
  public void setMainNodeStub(JNodeStub node) {
    this.mainNodeStub = node;
  }
  /**
   *
   * @return JNodeStub
   */
  public JNodeStub getMainNodeStub() {
    return mainNodeStub;
  }

  /**
   * 数据对象，可能是DCTObject,TABObject,或其他对象
   */
  private Object dataObject = null;
  /**
   * 具体的一个数据行
   */
  private DataRow dataRow = null;
  // Fields
  private JNodeStub a;
  private JExplorerView b;
  private JNodeStub res;
  private JChildWindow nodeWindow = null;
  protected JNodeStub[] nodeStubArray = null;
  private Object userObject = null;
  protected JNodeStub parentNodeStub = null;
  private Object nodeKey;
  protected boolean isCreateNodeView = true;
  public void setCreateNodeView(boolean b) {
    isCreateNodeView = b;
  }
  public boolean getCreateNodeView() {
    return isCreateNodeView;
  }
  public void setNodeWindow(JChildWindow cw) {
    this.nodeWindow =cw;
  }
  public JChildWindow getNodeWindow() {
    return nodeWindow;
  }
  /**
   *
   * @param userObject Object
   */
  public void setUserObject(Object userObject) {
    this.userObject = userObject;
  }
  /**
   *
   * @param dataRow DataRow
   */
  public void setDataRow(DataRow dataRow) {
    this.dataRow = dataRow;
  }
  /**
   *
   * @param dataObject Object
   */
  public void setDataObject(Object dataObject) {
    this.dataObject = dataObject;
  }
  /**
   *
   * @param nodeStubArray JNodeStub[]
   */
  public void setNodeStubArray(JNodeStub[] nodeStubArray) {
    this.nodeStubArray = nodeStubArray;
  }

  public void setParentNodeStub(JNodeStub parentNodeStub) {
    this.parentNodeStub = parentNodeStub;
  }

  public void setNodeKey(Object nodeKey) {
    this.nodeKey = nodeKey;
  }

  /**
   *
   * @return Object
   */
  public Object getUserObject() {
    return userObject;
  }
  /**
   *
   * @return DataRow
   */
  public DataRow getDataRow() {
    return dataRow;
  }
  /**
   *
   * @return Object
   */
  public Object getDataObject() {
    return dataObject;
  }
  /**
   *
   * @return JNodeStub[]
   */
  public JNodeStub[] getNodeStubArray() {
    return nodeStubArray;
  }
  public JNodeStub getParentNodeStub() {
    return getParentNodeStub(null);
  }

  public Object getNodeKey() {
    return nodeKey;
  }

  public JNodeStub getParentNodeStub(JNodeStub def) {
    if ( parentNodeStub == null ) return def;
    return parentNodeStub;
  }

  /**
   *
   * @param browser JExplorerView
   * @param nodeArray JNodeStub[]
   */
  public Context(JExplorerView browser, JNodeStub[] nodeArray) {
    b = browser;
    nodeStubArray = nodeArray;
  }
  /**
   *
   * @param browser JExplorerView
   * @param node JNodeStub
   */
  public Context(JExplorerView browser, JNodeStub node) {
    b = browser;
    a = node;
  }
  // Methods
  public String toString() { return a.toString();}
  public int hashCode() { return 0;}
  public boolean equals(Object object) { return false;}
  public JNodeStub getNode() { return a;}
  public void setNode(JNodeStub ns) {a=ns;}
  public void setBrowser(JExplorerView ev) {b = ev;}
  public JExplorerView getBrowser() { return b;}
  public JNodeStub getRetNode() {return res;}
  public void setRetNode(JNodeStub ns) { res = ns;}
  /**
   *
   * @param nodeStub JNodeStub
   * @return Context
   */
  public static Context getContext(JNodeStub nodeStub) {
    Context context = new Context(nodeStub.getExplorerView(),nodeStub);
    return context;
  }
  /**
   *
   * @param obj Object
   */
  public void putResponseObject(Object obj) {
    this.setObject("responseObject",obj);
  }
  /**
   *
   * @return Object
   */
  public Object getResponseObject() {
    return this.getObject("responseObject",null);
  }
  protected boolean prepareSucess = true;
  public boolean isPrepareSucess() {
    return prepareSucess;
  }
  public void setPrepareSucess(boolean v) {
    prepareSucess = v;
  }
  protected NodeViewer nodeViewer = null;
  public NodeViewer getNodeViewer() {
    return nodeViewer;
  }
  public void setNodeViewer(NodeViewer nodeViewer) {
    this.nodeViewer = nodeViewer;
  }
  private boolean reMemberWnd = true;

  public void setReMemberWnd(boolean mem) {
    reMemberWnd = mem;
  }
  public boolean isReMemberWnd() {
    return reMemberWnd;
  }
}
