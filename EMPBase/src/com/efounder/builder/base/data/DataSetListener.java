package com.efounder.builder.base.data;

import java.util.*;
/**
 *
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
public interface DataSetListener extends EventListener {
  /**
   * DataSet事件定义
   */
  public static final int DATA_SET_CURSOR_CHANGE = 0x0001; // 游标改变事件
  public static final int DATA_SET_OPEN_EVENT    = 0x0002; // 打开事件
  public static final int DATA_SET_APPEND_EVENT  = 0x0004; // 数据追加事件
  public static final int DATA_SET_CHILD_APPEND  = 0x0008; // 下级节点追回事件
  /**
   * DataSet事件定义
   */
  public static final int DATA_SET_INSERT        = 0x0010; // 数据插件事件
  public static final int DATA_SET_DELETE        = 0x0020; // 数据删除事件
  public static final int DATA_SET_UPDATE        = 0x0040; // 数据更新事件

  public void dataSetChanged(DataSetEvent e);
}
