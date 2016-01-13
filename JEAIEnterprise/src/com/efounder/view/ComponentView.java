package com.efounder.view;

import java.awt.*;
import com.core.xml.*;

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
public interface ComponentView {
  /**
   *
   * @param comp Component
   */
  public void setParentView(Component comp);
  /**
   *
   */
  public void initComponentView(CompViewFactory cvf,StubObject stubObject);
  /**
   *
   * @return Component
   */
  public Component getComponent();
  /**
   *
   * @param key Object
   * @param def Object
   * @return Object
   */
  public Object getValue(Object key,Object def);
  /**
   *
   * @param key Object
   * @param value Object
   */
  public void   setValue(Object key,Object value);
}
