package com.efounder.service.tree;

import com.core.xml.*;
import com.tree.util.TreeTools;

import java.util.List;

public class TreeNodeStubObject extends StubObject {
  static final long serialVersionUID = 1L;
  /**
   *
   */

  protected TreeNodeStubObject() {
  }
  private   StubObject dsStub = null;
  private  StubObject attrStub = null;
  private  StubObject privateattrStub = null;

  private List dsList = null;
  /**
   *
   * @return TreeNodeStubObject
   */
  public static TreeNodeStubObject getInstance() {
    return new TreeNodeStubObject();
  }
  public String getNodeGUID() {
    return getString("NODE_GUID","");
  }

  public StubObject getDsStub(){
    if(dsStub == null)
      dsStub = TreeTools.keyValueToStub(getString("NODE_DS", ""), ";", "=");
    return dsStub;
  }
  public StubObject getAttrStub(){
    if(attrStub == null)
     attrStub = TreeTools.keyValueToStub(getString("NODE_ATTR", ""), ";", "=");
   return attrStub;

  }

  public StubObject getPrivateAttrStub(){
    if(privateattrStub == null)
     privateattrStub = TreeTools.keyValueToStub(getString("VTREE_ATTR", ""), ";", "=");
   return privateattrStub;

  }
  public void copyAttrib() {
    StubObject attrib = null;
    attrib = getAttrStub();
    if ( attrib != null ) this.assignStubObject(attrib);
    attrib = getPrivateAttrStub();
    if ( attrib != null ) this.assignStubObject(attrib);
    if ( this.getObject("caption",null) == null ) {
      this.setObject("caption",this.getString("VTREE_MC",null));
    }
    if ( this.getObject("icon",null) == null ) {
      this.setObject("icon",this.getString("VTREE_ICON",null));
    }
  }
  public void setDsList(List dsList){
    this.dsList = dsList;
  }
  public List getDsList(){
    return this.dsList;
  }

}
