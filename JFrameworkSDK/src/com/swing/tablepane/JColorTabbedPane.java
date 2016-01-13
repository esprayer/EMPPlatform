package com.swing.tablepane;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.TabbedPaneUI;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JColorTabbedPane extends JTabbedPane {
  /**
   *
   */
  public JColorTabbedPane() {
    this(new java.awt.Color(0,51,102));
//    this(java.awt.Color.GREEN.darker());
  }
  /**
   *
   * @param tabColor Color
   */
  public JColorTabbedPane(Color tabColor) {
    super();
    paneUI = new ColorTabbedPaneUI();
    paneUI.setTabColor(tabColor);
    super.setUI(paneUI);
  }
  private ColorTabbedPaneUI paneUI;
  /**
   * Override JTabbedPane method. Does nothing.
   */
  public void setUI(TabbedPaneUI ui) {
    super.setUI(ui);
  }
}
