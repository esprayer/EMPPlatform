package com.efounder.bz.dbform.datamodel;

import com.efounder.builder.base.data.*;

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
public interface DMComponent extends DataModelComponent {
  // 与组件相关的操作
  public void setDataSetID(String dataSetID);
  public String getDataSetID();
  public void setDataSet(EFDataSet dataSet);
  public EFDataSet getDataSet();
  public ESPRowSet getMainRowSet();
  // 事件监听器
}
