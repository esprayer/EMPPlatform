package com.efounder.service.command;

import com.efounder.service.security.*;
import javax.swing.*;
import java.beans.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface Command {
  public static final String COMMAND_ID = "CommandID";
  // 执行此项命令
  public Object executeCommand(Object o1,Object[] args);
  // 返回此命令的功能权限
  public FunctionStubObject getFunction();
  public String getID();
  public String getCaption();
  public Icon   getIcon();
  /**
   * Gets one of this object's properties
   * using the associated key.
   * @see #putValue
   */
  public Object getValue(String key);
  /**
   * Sets one of this object's properties
   * using the associated key. If the value has
   * changed, a <code>PropertyChangeEvent</code> is sent
   * to listeners.
   *
   * @param key    a <code>String</code> containing the key
   * @param value  an <code>Object</code> value
   */
  public void putValue(String key, Object value);

  /**
   * Sets the enabled state of the <code>Action</code>.  When enabled,
   * any component associated with this object is active and
   * able to fire this object's <code>actionPerformed</code> method.
   * If the value has changed, a <code>PropertyChangeEvent</code> is sent
   * to listeners.
   *
   * @param  b true to enable this <code>Action</code>, false to disable it
   */
  public void setEnabled(boolean b);
  /**
   * Returns the enabled state of the <code>Action</code>. When enabled,
   * any component associated with this object is active and
   * able to fire this object's <code>actionPerformed</code> method.
   *
   * @return true if this <code>Action</code> is enabled
   */
  public boolean isEnabled();

  /**
   * Adds a <code>PropertyChange</code> listener. Containers and attached
   * components use these methods to register interest in this
   * <code>Action</code> object. When its enabled state or other property
   * changes, the registered listeners are informed of the change.
   *
   * @param listener  a <code>PropertyChangeListener</code> object
   */
  public void addPropertyChangeListener(PropertyChangeListener listener);
  /**
   * Removes a <code>PropertyChange</code> listener.
   *
   * @param listener  a <code>PropertyChangeListener</code> object
   * @see #addPropertyChangeListener
   */
  public void removePropertyChangeListener(PropertyChangeListener listener);

}
