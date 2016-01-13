package com.wait;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.VerticalFlowLayout;
import java.net.URL;
import com.borland.jbcl.layout.BoxLayout2;
import java.awt.BorderLayout;
import com.borland.jbcl.layout.PaneLayout;
import com.borland.jbcl.layout.*;

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
public class BusyBar2 extends JPanel {
  JLabel lbTitle = new JLabel();
  JLabel lbImage = new JLabel();
  /**
   * 忙处理Bar
   * @param title String
   */
  public BusyBar2(String title) {
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
  protected static String IMAGE_NAME = "loading.gif";
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel(); /**
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
    lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
    lbTitle.setText("Test");
    jPanel2.setLayout(borderLayout3);
    jPanel6.setLayout(borderLayout2);
    borderLayout2.setHgap(10);
    borderLayout2.setVgap(10);
    jLabel1.setToolTipText("");
    jLabel1.setText("       ");
    jLabel2.setText("       ");
    this.add(jPanel2, java.awt.BorderLayout.CENTER);
    this.add(jPanel1, java.awt.BorderLayout.NORTH);
    this.add(jPanel3, java.awt.BorderLayout.SOUTH);
    this.add(jPanel4, java.awt.BorderLayout.WEST);
    this.add(jPanel5, java.awt.BorderLayout.EAST);
    jPanel2.add(lbImage, java.awt.BorderLayout.WEST);
    jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);
    jPanel6.add(lbTitle, java.awt.BorderLayout.CENTER);
    jPanel6.add(jLabel1, java.awt.BorderLayout.WEST);
    jPanel6.add(jLabel2, java.awt.BorderLayout.EAST);
  }
  public void setWaitText(String title) {
    this.lbTitle.setText(title.trim());
  }
  public void setWaitIcon(Icon icon) {
    this.lbTitle.setIcon(icon);
  }
}
