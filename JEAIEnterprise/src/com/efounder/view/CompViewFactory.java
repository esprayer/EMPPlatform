package com.efounder.view;

import com.core.xml.*;
import com.efounder.node.Context;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class CompViewFactory {
  /**
   *
   */
  protected StubObject stubObject = null;
  /**
   *
   * @return StubObject
   */
  public StubObject getStubObject() {
    return stubObject;
  }
  /**
   *
   * @param so StubObject
   */
  public void setStubObject(StubObject so) {
    stubObject = so;
  }
  /**
   *
   */
  public CompViewFactory() {
  }
  /**
   *
   * @param contextObject Object
   * @param compObject Object
   * @return boolean
   */
  public abstract boolean canDisplay(Object contextObject,Object compObject);
  /**
   *
   * @param contextObject Object
   * @param compObject Object
   * @return Object
   */
  public abstract ComponentView createComponent(Object contextObject,Object compObject,Context context);
}
