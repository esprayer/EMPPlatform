package com.efounder.dataobject.view;

import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ViewDockbar {
  public Component getTopDockComp(Object DCT_TYPE);
  public Component getDockComp(Object DCT_TYPE);
  public Object getValue(Object DCT_TYPE,Object key);
}
