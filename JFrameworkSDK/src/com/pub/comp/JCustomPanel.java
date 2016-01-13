package com.pub.comp;

import java.awt.*;
import javax.swing.JPanel;

/**
 * <p>Title: 自定义JPanel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JCustomPanel extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  ICustomDraw CustomDraw = null;
  public void setCustomDraw(ICustomDraw cd) {
    CustomDraw = cd;
  }
  public JCustomPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
  }
  public void paint(Graphics g) {
    super.paint(g);
    if ( CustomDraw != null ) {
      CustomDraw.PaintDraw(g);
    }
  }
}