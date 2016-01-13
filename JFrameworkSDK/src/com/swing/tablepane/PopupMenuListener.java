package com.swing.tablepane;

import java.util.EventListener;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface PopupMenuListener extends EventListener {
  public void popupOutsideOperation(MouseEvent e);
}
