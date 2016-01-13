package com.efounder.view;

import com.efounder.view.*;
import com.efounder.node.Context;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class PrepareWindowSouthViewFactory extends CompViewFactory {
  /**
   *
   */
  public PrepareWindowSouthViewFactory() {
  }

  /**
   *
   * @param contextObject Object
   * @param compObject Object
   * @return boolean
   * @todo Implement this com.efounder.view.CompViewFactory method
   */
  public boolean canDisplay(Object contextObject, Object compObject) {
    return true;
  }

  /**
   *
   * @param contextObject Object
   * @param compObject Object
   * @return Object
   * @todo Implement this com.efounder.view.CompViewFactory method
   */
  public ComponentView createComponent(Object contextObject, Object compObject,Context context) {
    return new PrepareWindowSouthView();
  }
}
