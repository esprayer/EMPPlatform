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
public class FlowEdge implements java.io.Serializable {
  private String edgeID = null;
  private String edgeIcon = null;
  private String outNodeIcon = null;
  private String edgeName = null;
  private String inNodeIcon = null;
  private String inNodeID = null;
  private String outNodeID = null;
  private String outNodeName = null;
  private String inNodeName = null;
  private Map edgeScriptMap;
  private Map attrMap;
  private String edgeGnbh;
  private boolean isSourceisStart = false;
  private boolean isTargetisEnd   = false;
  public FlowEdge() {
  }

  public void setEdgeID(String edgeID) {
    this.edgeID = edgeID;
  }

  public String getEdgeAttr(String key) {
	    if ( attrMap == null ) return null;
	    return (String)attrMap.get(key);
	  }
	  public void setEdgeAttrMap(Map attrMap) {
	    this.attrMap = attrMap;
	  }

  public void setEdgeName(String edgeName) {
    this.edgeName = edgeName;
  }

  public void setInNodeID(String inNodeID) {
    this.inNodeID = inNodeID;
  }

  public void setInNodeName(String inNodeName) {
    this.inNodeName = inNodeName;
  }

  public void setOutNodeID(String outNodeID) {
    this.outNodeID = outNodeID;
  }

  public void setOutNodeName(String outNodeName) {
    this.outNodeName = outNodeName;
  }

  public void setEdgeIcon(String edgeIcon) {
    this.edgeIcon = edgeIcon;
  }

  public void setInNodeIcon(String inNodeIcon) {
    this.inNodeIcon = inNodeIcon;
  }

  public void setOutNodeIcon(String outNodeIcon) {
    this.outNodeIcon = outNodeIcon;
  }
  /**
   *
   * @param scriptKey String
   * @param script String
   */
  public void putEdgeScript(String scriptKey,String script) {
    if ( script == null || script.trim().length() == 0 ) return;
    if ( edgeScriptMap == null ) edgeScriptMap = new java.util.HashMap();
    edgeScriptMap.put(scriptKey,script);
  }
  /**
   *
   * @param scriptKey String
   * @return String
   */
  public String getEdgeScript(String scriptKey) {
    if ( edgeScriptMap == null ) return null;
    return (String)edgeScriptMap.get(scriptKey);
  }
  public void setEdgeScriptMap(Map edgeScriptMap) {
    this.edgeScriptMap = edgeScriptMap;
  }

  public void setEdgeGnbh(String edgeGnbh) {
    this.edgeGnbh = edgeGnbh;
  }

  public void setIsSourceisStart(boolean isSourceisStart) {
    this.isSourceisStart = isSourceisStart;
  }

  public void setIsTargetisEnd(boolean isTargetisEnd) {
    this.isTargetisEnd = isTargetisEnd;
  }

  public String getEdgeID() {
    return edgeID;
  }

  public String getEdgeName() {
    return edgeName;
  }

  public String getInNodeID() {
    return inNodeID;
  }

  public String getInNodeName() {
    return inNodeName;
  }

  public String getOutNodeID() {
    return outNodeID;
  }

  public String getOutNodeName() {
    return outNodeName;
  }

  public String getEdgeIcon() {
    return edgeIcon;
  }

  public String getInNodeIcon() {
    return inNodeIcon;
  }

  public String getOutNodeIcon() {
    return outNodeIcon;
  }

  public Map getEdgeScriptMap() {
    return edgeScriptMap;
  }

  public String getEdgeGnbh() {
    return edgeGnbh;
  }

  public boolean isIsSourceisStart() {
    return isSourceisStart;
  }

  public boolean isIsTargetisEnd() {
    return isTargetisEnd;
  }

}
