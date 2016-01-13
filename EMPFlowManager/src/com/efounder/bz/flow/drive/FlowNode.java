package com.efounder.bz.flow.drive;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FlowNode implements java.io.Serializable {
  private String nodeID = null;
  private String nodeName = null;
  private Map nodeScriptMap;
  private Map attrMap;
//  private boolean canEnd = false;
//  private boolean canReset   = false;
//  private boolean canCancel=false;
  public FlowNode() {
  }
  public void putNodeScript(String scriptKey,String script) {
    if ( script == null || script.trim().length() == 0 ) return;
    if ( nodeScriptMap == null ) nodeScriptMap = new java.util.HashMap();
    nodeScriptMap.put(scriptKey,script);
  }
  /**
   *
   * @param scriptKey String
   * @return String
   */
  public String getNodeScript(String scriptKey) {
    if ( nodeScriptMap == null ) return null;
    return (String)nodeScriptMap.get(scriptKey);
  }
  public void setNodeScriptMap(Map edgeScriptMap) {
    this.nodeScriptMap = edgeScriptMap;
  }
  public String getNodeAttr(String key) {
	    if ( attrMap == null ) return null;
	    return (String)attrMap.get(key);
	  }
	  public void setNodeAttrMap(Map attrMap) {
	    this.attrMap = attrMap;
	  }
  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }
  public String getNodeID() {
    return nodeID;
  }

  public String getNodeName() {
    return nodeName;
  }
//  public void setCanCancel(boolean canCancel) {
//    this.canCancel = canCancel;
//  }
//
//  public boolean isCanEnd() {
//    return canEnd;
//  }
//  public boolean isCanCancel() {
//    return canCancel;
//  }
//  public boolean isCanReset() {
//    return canReset;
//  }
//  public void setCanEnd(boolean canEnd) {
//    this.canEnd = canEnd;
//  }
//
//  public void setCanReset(boolean canReset) {
//    this.canReset = canReset;
//  }

}
