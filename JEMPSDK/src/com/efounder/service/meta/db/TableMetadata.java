package com.efounder.service.meta.db;

import com.efounder.service.meta.base.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TableMetadata extends MetaObject {
  /**
   *
   */
  protected TableMetadata() {
  }
  /**
   *
   * @return TableMetadata
   */
  public static TableMetadata getInstance() {
    return new TableMetadata();
  }
  /**
   *
   */
  public java.util.List colList   = null;
  /**
   *
   */
  public java.util.List indexList = null;
  /**
   *
   */
  public java.util.List keyList   = null;
  /**
   *
   * @return List
   */
  public java.util.List getColList() {
    return colList;
  }
  /**
   *
   * @return List
   */
  public java.util.List getIndexList() {
    return indexList;
  }
  /**
   *
   * @return List
   */
  public java.util.List getKeyList() {
    return keyList;
  }
}
