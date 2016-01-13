package com.wait;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;

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
public class WaitTest
    extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JButton jButton1 = new JButton();

  public WaitTest() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jButton1.setText("jButton1");
    borderLayout1.setHgap(10);
    borderLayout1.setVgap(10);
    this.add(jButton1, java.awt.BorderLayout.CENTER);
  }
}
