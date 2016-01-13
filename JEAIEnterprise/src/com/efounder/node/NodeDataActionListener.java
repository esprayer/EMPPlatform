package com.efounder.node;

import java.util.EventListener;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface NodeDataActionListener extends EventListener {
  public void insertNodeDataStubEvent(String ServiceKey,NodeDataStub nodeDataStub,Context actionObject);// 增加了一个SutbObject
  public void removeNodeDataStubEvent(String ServiceKey,NodeDataStub nodeDataStub,Context actionObject);// 删除了一个SutbObject
  public void changeNodeDataStubEvent(String ServiceKey,NodeDataStub nodeDataStub,Context actionObject);// 修改了一个SutbObject
  public void refreshNodeDataStubEvent(String ServiceKey,NodeDataStub nodeDataStub,Context actionObject);// 修改了一个SutbObject
}
