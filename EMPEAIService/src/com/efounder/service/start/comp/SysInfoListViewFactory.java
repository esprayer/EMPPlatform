package com.efounder.service.start.comp;

import com.efounder.view.*;
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
public class SysInfoListViewFactory extends CompViewFactory {
  /**
   *
   */
  public SysInfoListViewFactory() {
  }

  /**
   *
   * @param contextObject Object
   * @param compObject Object
   * @return boolean
   * @todo Implement this com.efounder.view.CompViewFactory method
   */
  public boolean canDisplay(Object contextObject, Object compObject) {
    String viewClass = (String)stubObject.getObject("viewClass",null);
    return (viewClass != null);
  }

  /**
   *
   * @param contextObject Object
   * @param compObject Object
   * @return Object
   * @todo Implement this com.efounder.view.CompViewFactory method
   */
  public ComponentView createComponent(Object contextObject, Object compObject) {
    String viewClass = (String)stubObject.getObject("viewClass",null);
    ComponentView compView = null;
    try {
      compView = (ComponentView) Class.forName(viewClass).newInstance();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return compView;
  }

  public ComponentView createComponent(Object contextObject, Object compObject,
                                       Context context) {
    return null;
  }
}
