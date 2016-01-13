package com.efounder.bz.flow.drive;

import com.efounder.builder.base.data.*;
import com.efounder.bz.flow.*;

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
public class NodeTaskDataSet extends EFDataSet {
  /**
   *
   */
  protected java.util.Map inEdges  = null;
  /**
   *
   * @return String[]
   */
  public String[] getInEdges() {
    if ( inEdges == null || inEdges.size() == 0 ) return null;
    String[] edges = new String[inEdges.size()];
    edges = (String[])inEdges.keySet().toArray(edges);
    return edges;
  }
  /**
   *
   * @param edgeID String
   * @return FlowEdge
   */
  public FlowEdge getInEdge(String edgeID) {
    if ( inEdges == null ) return null;
    return (FlowEdge)inEdges.get(edgeID);
  }
  /**
   *
   * @param edgeID String
   * @param edgeName String
   */
  public void putInEdge(String edgeID,FlowEdge flowEdge) {
    if ( inEdges == null ) inEdges = new java.util.HashMap();
    inEdges.put(edgeID,flowEdge);
  }

  protected java.util.Map outEdges = null;

  /**
   *
   * @return String[]
   */
  public String[] getOutEdges() {
    if ( outEdges == null || outEdges.size() == 0 ) return null;
    String[] edges = new String[outEdges.size()];
    edges = (String[])outEdges.keySet().toArray(edges);
    return edges;
  }
  public FlowEdge getOutEdge(String edgeID) {
    if ( outEdges == null ) return null;
    return (FlowEdge)outEdges.get(edgeID);
  }
  /**
   *
   * @param edgeID String
   * @param edgeName String
   */
  public void putOutEdge(String edgeID,FlowEdge flowEdge) {
    if ( outEdges == null ) outEdges = new java.util.HashMap();
    outEdges.put(edgeID,flowEdge);
  }

  protected java.util.Map outManEdges = null;

  /**
   *
   * @return String[]
   */
  public String[] getOutManEdges() {
    if ( outManEdges == null || outManEdges.size() == 0 ) return null;
    String[] edges = new String[outManEdges.size()];
    edges = (String[])outManEdges.keySet().toArray(edges);
    java.util.List list=new java.util.ArrayList();
    for(int i=0;i<edges.length;i++)
    	list.add(edges[i]);
    java.util.Collections.sort(list);
    for(int i=0;i<edges.length;i++)
    	edges[i]=(String)list.get(i);

    return edges;
  }
  public FlowEdge getOutManEdge(String edgeID) {
    if ( outManEdges == null ) return null;
    return (FlowEdge)outManEdges.get(edgeID);
  }
  /**
   *
   * @param edgeID String
   * @param edgeName String
   */
  public void putOutManEdge(String edgeID,FlowEdge flowEdge) {
    if ( outManEdges == null ) outManEdges = new java.util.HashMap();
    outManEdges.put(edgeID,flowEdge);
  }


  private String nodeCaption = null;
  private boolean startNode = false;
  private boolean finishNode = false;
  private boolean loopNode = false;
  /**
   *
   */
  public NodeTaskDataSet() {
  }
  /**
   *
   * @return NodeTaskDataSet
   */
  public static NodeTaskDataSet getNodeTaskDataSet(String FLOW_ID,String NODE_ID,String OBJ_GUID) {
    NodeTaskDataSet dataSet = new NodeTaskDataSet();
    dataSet.FLOW_ID = FLOW_ID;
    dataSet.NODE_ID = NODE_ID;
    dataSet.OBJ_GUID = OBJ_GUID;
    return dataSet;
  }
  /**
   *
   * @return String
   */
  public String getFLOW_ID() {
    return FLOW_ID;
  }
  /**
   *
   */
  private String OBJ_GUID;
  /**
   *
   * @return String
   */
  public String getNODE_ID() {
    return NODE_ID;
  }
  /**
   *
   * @return String
   */
  public String getOBJ_GUID() {
    return OBJ_GUID;
  }
  /**
   *
   * @param nodeCaption String
   */
  public void setNodeCaption(String nodeCaption) {
    this.nodeCaption = nodeCaption;
  }

  public void setStartNode(boolean startNode) {
    this.startNode = startNode;
  }

  public void setFinishNode(boolean finishNode) {
    this.finishNode = finishNode;
  }
  public void setLoopNode(boolean b) {
    this.loopNode = b;
  }
  public void setBIZ_DJBH(String BIZ_DJBH) {
    this.BIZ_DJBH = BIZ_DJBH;
  }

  public void setBIZ_DATE(String BIZ_DATE) {
    this.BIZ_DATE = BIZ_DATE;
  }

  public void setBIZ_UNIT(String BIZ_UNIT) {
    this.BIZ_UNIT = BIZ_UNIT;
  }

  /**
   *
   * @return String
   */
  public String getNodeCaption() {
    return nodeCaption;
  }

  public boolean isStartNode() {
    return startNode;
  }

  public boolean isFinishNode() {
    return finishNode;
  }
  public boolean isLoopNode() {
      return loopNode;
  }
  public String getBIZ_DJBH() {
    return BIZ_DJBH;
  }

  public String getBIZ_DATE() {
    return BIZ_DATE;
  }

  public String getBIZ_UNIT() {
    return BIZ_UNIT;
  }

  /**
   *
   */
  private String FLOW_ID;
  /**
   *
   */
  private String NODE_ID;
  private String BIZ_UNIT = null;
  private String BIZ_DATE = null;
  private String BIZ_DJBH = null;

  /**
   *
   */
  private NodeTaskAddDataSet taskAddDataSet = null;
  private FlowNode flowNode;

  public NodeTaskAddDataSet getTaskAddDataSet() {
    return this.taskAddDataSet;
  }

  public FlowNode getFlowNode() {
    return flowNode;
  }

  /**
   *
   * @param taskAddDataSet NodeTaskAddDataSet
   */
  public void setTaskAddDataSet(NodeTaskAddDataSet taskAddDataSet) {
    this.taskAddDataSet = taskAddDataSet;
  }

  public void setFlowNode(FlowNode flowNode) {
    this.flowNode = flowNode;
  }

  public ESPRowSet lastByToUnit(String tounit){
      int count=getRowCount();
      for(int i=count-1;i>=0;i--){
          ESPRowSet ers =getRowSet(i);
          if(ers.getString(FlowConstants._TASK_TO_UNIT_COL_,"").equals(tounit))return ers;
      }
      return null;
  }
    public ESPRowSet lastStatusTaskToUnit(String tounit,String status){
      int count=getRowCount();
      for (int i = count - 1; i >= 0; i--) {
        ESPRowSet ers = getRowSet(i);
        if (ers.getString(FlowConstants._TASK_TO_UNIT_COL_,"").equals(tounit)&&
            ers.getString(FlowConstants._RESR_IN_CAUSE_COL_, "").equals(status))
          return ers;
      }
      return null;
    }
  public ESPRowSet lastByUnit(String unit){
      int count=getRowCount();
      for(int i=count-1;i>=0;i--){
          ESPRowSet ers =getRowSet(i);
          if(ers.getString(FlowConstants._TASK_UNIT_COL_,"").equals(unit))return ers;
      }
      return null;
  }
}
