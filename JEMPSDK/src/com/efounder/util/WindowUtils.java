package com.efounder.util;

import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class WindowUtils {
  public WindowUtils() {
  }
  /**
   *
   * @param window Window
   */
  public static void centerWindow(java.awt.Window window) {
    Dimension dlgSize = window.getSize();
    Dimension ScnSize = Toolkit.getDefaultToolkit().getScreenSize();
    window.setLocation((ScnSize.width - dlgSize.width)/2, (ScnSize.height - dlgSize.height)/2);
  }
}
