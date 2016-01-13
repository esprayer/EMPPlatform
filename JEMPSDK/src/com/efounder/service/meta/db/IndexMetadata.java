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
public class IndexMetadata extends MetaObject {
  /**
   *
   */
  protected IndexMetadata() {
  }
  /**
   *
   * @return KeyMetadata
   */
  public static IndexMetadata getInstance() {
    return new IndexMetadata();
  }
}
