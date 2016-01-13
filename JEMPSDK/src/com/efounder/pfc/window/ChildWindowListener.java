package com.efounder.pfc.window;

import java.util.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ChildWindowListener extends EventListener {
  /**
   * Invoked the first time a window is made visible.
   */
  public void windowOpened(ChildWindowEvent e);

  /**
   * Invoked when the user attempts to close the window
   * from the window's system menu.  If the program does not
   * explicitly hide or dispose the window while processing
   * this event, the window close operation will be cancelled.
   */
  public boolean windowClosing(ChildWindowEvent e);

  /**
   * Invoked when a window has been closed as the result
   * of calling dispose on the window.
   */
  public void windowClosed(ChildWindowEvent e);

  /**
   * Invoked when a window is changed from a normal to a
   * minimized state. For many platforms, a minimized window
   * is displayed as the icon specified in the window's
   * iconImage property.
   * @see java.awt.Frame#setIconImage
   */
  public void windowIconified(ChildWindowEvent e);

  /**
   * Invoked when a window is changed from a minimized
   * to a normal state.
   */
  public void windowDeiconified(ChildWindowEvent e);

  /**
   * Invoked when the Window is set to be the active Window. Only a Frame or
   * a Dialog can be the active Window. The native windowing system may
   * denote the active Window or its children with special decorations, such
   * as a highlighted title bar. The active Window is always either the
   * focused Window, or the first Frame or Dialog that is an owner of the
   * focused Window.
   */
  public void windowActivated(ChildWindowEvent e);

  /**
   * Invoked when a Window is no longer the active Window. Only a Frame or a
   * Dialog can be the active Window. The native windowing system may denote
   * the active Window or its children with special decorations, such as a
   * highlighted title bar. The active Window is always either the focused
   * Window, or the first Frame or Dialog that is an owner of the focused
   * Window.
   */
  public void windowDeactivated(ChildWindowEvent e);

}
