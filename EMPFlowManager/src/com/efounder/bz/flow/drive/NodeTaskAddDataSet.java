package com.efounder.bz.flow.drive;

import com.efounder.builder.base.data.*;

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
public class NodeTaskAddDataSet extends EFDataSet {
  /**
   *
   */
  public NodeTaskAddDataSet() {
  }
  /**
   *
   * @return NodeTaskDataSet
   */
  public static NodeTaskAddDataSet getNodeTaskDataSet(String FLOW_ID,/**String OP_GUID,**/String OBJ_GUID) {
    NodeTaskAddDataSet dataSet = new NodeTaskAddDataSet();
    dataSet.FLOW_ID = FLOW_ID;
//    dataSet.OP_GUID = OP_GUID;
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
  public String getOP_GUID() {
    return OP_GUID;
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
   */
  private String FLOW_ID;
  /**
   *
   */
  private String OP_GUID;
  /**
   *
   * @param TASK_ADD_KEY String
   * @return EFRowSet
   */
  public EFRowSet getTaskAddRowSet(String TASK_ADD_KEY) {
    return (EFRowSet)this.getRowSet(TASK_ADD_KEY);
  }
  /**
   *
   * @param TASK_ADD_KEY String
   * @param def Object
   * @return Object
   */
  public String getTaskAddData(String TASK_ADD_KEY,String def) {
    EFRowSet rowSet = getTaskAddRowSet(TASK_ADD_KEY);
    if ( rowSet == null ) return def;
    return rowSet.getString("TASK_ADD_CDATA",def);
  }
  /**
   *
   * @param TASK_ADD_KEY String
   * @param value String
   */
  public void setTaskAddData(String TASK_ADD_KEY,String value) {
    EFRowSet rowSet = getTaskAddRowSet(TASK_ADD_KEY);
    if ( rowSet != null ) {
      rowSet.putString("TASK_ADD_CDATA",value);
      rowSet.setDataStatus(EFRowSet._DATA_STATUS_UPDATE_);
    } else if(value!=null){
      rowSet = EFRowSet.getInstance();
      // 设置状态为新建
      rowSet.setDataStatus(EFRowSet._DATA_STATUS_INSERT_);
      rowSet.putString("FLOW_ID",this.FLOW_ID);
      rowSet.putString("OP_GUID",this.OP_GUID);
      rowSet.putString("OBJ_GUID",this.OBJ_GUID);
      rowSet.putString("TASK_ADD_KEY",TASK_ADD_KEY);
      rowSet.putString("TASK_ADD_CDATA",value);
      // 将新创建的行增加到DataSet中
      this.insertRowSet(rowSet);
    }
  }

}
