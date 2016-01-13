package com.efounder.eai.ui;

import javax.swing.JTabbedPane;
import javax.swing.Icon;
import com.efounder.pfc.window.IWindow;
import com.efounder.pfc.window.WindowGroup;

/**
 * <p>Title: Enterprise Explorer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: EFounder</p>
 *
 * @author Skyline
 * @version 1.0
 */
public class JNormalViewPanel extends JViewPanel {
  public JNormalViewPanel() {
    super();
  }
  public JTabbedPane getTabbedPane() {
    tbContent = new JTabbedPane(JTabbedPane.BOTTOM);
    return tbContent;
  }
  protected void initEvent() {
    this.tbContent.addChangeListener(this);
  }
  protected Icon getCompIcon(IWindow cw) {
    return null;
  }
  public WindowGroup[] getWindowGroups() {
    return null;
  }
}
