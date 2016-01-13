package com.wait;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BusyBar extends JPanel {
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JLabel lbTitle = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel lbImage = new JLabel();
  /**
   * 忙处理Bar
   * @param title String
   */
  public BusyBar(String title) {
    try {
      jbInit();
      initImage();
      this.lbTitle.setText(title);
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public void addUserWaitComp(Component waitComp) {
    if ( waitComp != null ) {
      this.add(waitComp,java.awt.BorderLayout.EAST);
    }
  }
  protected static String IMAGE_NAME = "waiting.gif";
  FlowLayout flowLayout2 = new FlowLayout();
  JPanel jPanel3 = new JPanel();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  /**
   * Load　Image
   */
  protected void initImage() {
    URL url = this.getClass().getResource(IMAGE_NAME);
    ImageIcon ii = new ImageIcon(url);
    this.lbImage.setIcon(ii);lbImage.setText(null);
  }
  /**
   *
   * @throws Exception
   */
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    jPanel1.setLayout(flowLayout1);
    jPanel2.setLayout(flowLayout2);
    jPanel3.setLayout(verticalFlowLayout2);
    jPanel3.add(jPanel1);
    jPanel1.add(lbTitle, null);
    jPanel3.add(jPanel2);
    jPanel2.add(lbImage, null);
    this.add(jPanel3, java.awt.BorderLayout.CENTER);
  }
  public void setWaitText(String title) {
    this.lbTitle.setText(title.trim());
  }
  public void setWaitIcon(Icon icon) {
    this.lbTitle.setIcon(icon);
  }
}
