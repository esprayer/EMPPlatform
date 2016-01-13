package com.efounder.view;

import java.awt.*;
import javax.swing.*;
import com.core.xml.StubObject;

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
public class ComponentViewAdapter extends JPanel implements ComponentView {
  /**
   *
   */
  protected StubObject stubObject = null;
  /**
   *
   */
  public ComponentViewAdapter() {

  }
  /**
   *
   * @param comp Component
   */
  public void setParentView(Component comp) {

  }
  /**
   *
   * @param cvf CompViewFactory
   * @param stubObject StubObject
   */
  public void initComponentView(CompViewFactory cvf, StubObject stubObject) {
    this.stubObject = stubObject;
  }
  /**
   *
   * @return Component
   */
  public Component getComponent() {
    return this;
  }
  /**
   *
   * @param key Object
   * @param def Object
   * @return Object
   */
  public Object getValue(Object key, Object def) {
    if ( stubObject == null ) return null;
    return stubObject.getObject(key,def);
  }
  /**
   *
   * @param key Object
   * @param value Object
   */
  public void setValue(Object key, Object value) {
    if ( stubObject == null ) return;
    stubObject.setObject(key,value);
  }
}
