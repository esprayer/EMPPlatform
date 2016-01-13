package com.efounder.bz.dbform.datamodel;

import java.util.EventObject;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DataSetComponentEvent extends EventObject {
  // 可以编缉
  public static final int DSC_EVENT_START_EDIT = 0x0010;
  // 不允许编缉
  public static final int DSC_EVENT_STOP_EDIT  = 0x0011;
  // 停止编缉
  public static final int DSC_EVENT_END_EDIT  = 0x0012;
  /**
   *
   * @param dataSetComponent DataSetComponent
   * @param eventType int
   */
  public DataSetComponentEvent(DataSetComponent dataSetComponent,int eventType) {
    super(dataSetComponent);
    this.eventType = eventType;
  }
  /**
   *
   * @return DataSetComponent
   */
  public DataSetComponent getDataSetComponent() {
    return (DataSetComponent)this.getSource();
  }
  /**
   *
   */
  protected int eventType = 0x0000;
  /**
   *
   * @return int
   */
  public int getEventType() {
    return eventType;
  }
}
