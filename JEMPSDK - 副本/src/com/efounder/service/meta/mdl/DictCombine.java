package com.efounder.service.meta.mdl;

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
public class DictCombine extends MetaObject {
  /**
   *
   */
  protected DictCombine() {
  }
  /**
   *
   * @return DictCombine
   */
  public static DictCombine getInstance() {
    return new DictCombine();
  }
  /**
   *
   */
  protected java.util.List dictList = null;
  /**
   *
   * @return List
   */
  public java.util.List getDictList() {
    return dictList;
  }
}
