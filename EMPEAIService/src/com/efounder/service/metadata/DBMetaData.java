package com.efounder.service.metadata;

import com.core.xml.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract interface DBMetaData {
  /**
   * 获取对象类型
   * @return int
   */
  public abstract int getObjectType();
  /**
   * 获取对象的标识
   * @return String
   */
  public abstract String getObjectID();
  /**
   * 获取对象信息,在对象信息中，存在
   * @return StubObject
   */
  public abstract StubObject getObjectInfo();
  /**
   * 获取对象列信息
   * @return StubObject[]
   */
  public abstract StubObject[] getObjectColumns();
  /**
   * 获取对象的触发器
   * @return StubObject[]
   */
  public abstract StubObject[] getObjectTriggers();
  /**
   * 获取对象的索引信息
   * @return StubObject[]
   */
  public abstract StubObject[] getObjectIndexs();
  /**
   * 获取对象的主外键的信息
   * @return StubObject[]
   */
  public abstract StubObject[] getObjectKeys();
  /**
   * 获取数据字典的元数据信息
   * @return StubObject
   */
  public abstract StubObject   getDCTInfo();
  /**
   * 获取事实表的元数据信息
   * @return StubObject
   */
  public abstract StubObject   getFCTInfo();

}
