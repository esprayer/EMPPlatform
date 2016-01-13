package com.efounder.dataobject.view;

import com.borland.dbswing.TableScrollPane;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTTableScrollPane extends TableScrollPane {
  /**
   *
   */
  public DCTTableScrollPane() {
    super();
  }
  public void updateUI() {
    setUI(new BasicScrollPaneUI());
  }
  /**
   * <p>Creates a vertical scroll bar for the table scroll pane.</p>
   */
  public JScrollBar createVerticalScrollBar() {
    return new DCTTableScrollBar(JScrollBar.VERTICAL);
  }
  public class DCTTableScrollBar extends TableScrollBar {
    public DCTTableScrollBar(int orientation) {
      super(orientation);
    }
    public void updateUI() {
      setUI((ScrollBarUI)UIManager.getUI(this));
    }
  }

}
