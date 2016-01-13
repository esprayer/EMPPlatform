package com.efounder.node;

import com.core.xml.*;
import com.efounder.node.view.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeDataStub extends StubObject implements java.io.Serializable {
  static final long serialVersionUID = 1L;
  protected transient Object nodeStub = null;
  protected transient NodeDataManager nodeDataManager = null;
  protected transient boolean          modified = false;
  /**
   *
   * @return NodeDataStub
   */
  public NodeDataStub initNodeData(StubObject SO,Context context) {
    return this;
  }

  public boolean removeNodeData(JNodeStub childNode) {
    return true;
  }

  public void destroyNodeData(JNodeStub childNode) {

  }
  public void changeNodeData(JNodeStub childNode) {

  }
  public void setNodeStub(Object nodeStub) {
    this.nodeStub = nodeStub;
  }

  public void setNodeDataManager(NodeDataManager nodeDataManager) {
    this.nodeDataManager = nodeDataManager;
  }

  public void setModified(boolean modified) {
    this.modified = modified;
  }

  public void setNodeStub(JNodeStub nodeStub) {
    this.nodeStub = nodeStub;
  }

  public Object getNodeStub() {
    return nodeStub;
  }

  public NodeDataManager getNodeDataManager() {
    return nodeDataManager;
  }

  public boolean isModified() {
    return modified;
  }
  /**
   *
   */
  protected transient boolean newCreate = false;
  /**
   *
   * @return boolean
   */
  public boolean isNewCreate() {
    return newCreate;
  }
  /**
   *
   * @param newcreate boolean
   */
  public void setNewCreate(boolean newcreate) {
    newCreate = newcreate;
  }
  /**
   *
   */
  public NodeDataStub() {
  }
  /**
   *
   * @throws Exception
   */
  public void doSave() throws Exception {

  }
  /**
   *
   * @throws Exception
   */
  public void doLoad() throws Exception {

  }
  /**
   *
   */
  public void getNodeDataObject() {
    JNodeStub nodeStub = (JNodeStub)this.getNodeStub();
    NodeChildWindow nodeWindow = nodeStub.getNodeWindow();
    if ( nodeWindow == null ) return;
    NodeViewer[] nvs = nodeWindow.getNodeViewerArray();
    for(int i=0;i<nvs.length;i++) {
      if ( nvs[i] != null ) {
        nvs[i].getNodeDataObject(this);
      }
    }
  }
}
