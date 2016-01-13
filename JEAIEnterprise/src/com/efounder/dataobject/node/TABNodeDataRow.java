package com.efounder.dataobject.node;

import com.borland.dx.dataset.*;
import com.efounder.dataobject.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: NodeStub 和 DataRow 管理接口</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TABNodeDataRow {
  /**
   * 从指定节点获取特定的DataSet
   */
  public TABObject getTABObject();
  /**
   *
   * @return DataSet
   */
  public DataSet   getDataSet();
  /**
   *
   * @param dataRow DataRow
   * @return Icon
   */
  public Icon      getIcon(DataRow dataRow);
  /**
   *
   * @return String
   */
  public String    getText();

}
