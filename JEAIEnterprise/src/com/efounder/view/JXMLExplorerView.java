package com.efounder.view;

import com.core.xml.*;
import org.jdom.*;
import com.efounder.node.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JXMLExplorerView extends JExplorerView {
  public JXMLExplorerView() {
    super(null);
  }
  /**
   *
   * @param o Object
   */
  public void initObject(Object o) {
    JXMLBaseObject XMLObject = (JXMLBaseObject)o;
    XMLObject.GetElementValue(XMLObject.Root,"");
    // 生成 root 节点
    JNodeStub NodeStub = getNodeFromXML(XMLObject,XMLObject.Root);
    // 生成其他节点
    createNodeStub(XMLObject,NodeStub,XMLObject.Root);
    this.registryRoot(NodeStub);
  }
  /**
   *
   * @param XMLObject JXMLBaseObject
   * @param parentNodeStub JNodeStub
   * @param element Element
   */
  protected void createNodeStub(JXMLBaseObject XMLObject,JNodeStub parentNodeStub,Element element) {
    java.util.List nodelist = XMLObject.BeginEnumerate(element);
    int Index=0;Element e;JNodeStub nodeStub;
    while ( nodelist != null ) {
      e = XMLObject.Enumerate(nodelist,Index++);
      if ( e == null ) break;
      nodeStub = getNodeFromXML(XMLObject,e);// 生成结点
      parentNodeStub.add(nodeStub);// 设置成父节点
      createNodeStub(XMLObject,nodeStub,e); // 递归调用
    }
    XMLObject.EndEnumerate();
  }
  /**
   *
   * @param XMLObject JXMLBaseObject
   * @param element Element
   * @return JNodeStub
   */
  protected JNodeStub getNodeFromXML(JXMLBaseObject XMLObject,Element element) {
    JNodeStub NodeStub = null;
//    NodeStub = XMLNodeStub.getNodeStubAttrib(XMLObject,element);
    NodeStub.setExplorerView(this);
    return NodeStub;
  }
}
