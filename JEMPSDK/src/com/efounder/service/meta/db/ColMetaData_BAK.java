package com.efounder.service.meta.db;

import com.efounder.service.meta.base.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ColMetaData_BAK extends MetaObject {
  /**
   *
   */
  protected ColMetaData_BAK() {
  }
  /**
   *
   * @return ColMetadata
   */
  public static ColMetaData_BAK getInstance() {
    return new ColMetaData_BAK();
  }
  /**
   *
   */
  public DictMetadata refDictmetadata = null;
  /**
   *
   * @return DictMetadata
   */
  public DictMetadata getRefDictmetadata() {
    return refDictmetadata;
  }
}
