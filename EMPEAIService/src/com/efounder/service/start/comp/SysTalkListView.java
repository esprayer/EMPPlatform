package com.efounder.service.start.comp;

import com.efounder.view.*;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import com.borland.jbcl.layout.VerticalFlowLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import com.efounder.eai.ide.ExplorerIcons;

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
public class SysTalkListView extends ComponentViewAdapter {
  public SysTalkListView() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jPanel2.setLayout(verticalFlowLayout1);
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);
    jPanel4.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    jButton1.setMargin(new Insets(0, 0, 0, 0));
    jButton2.setMargin(new Insets(0, 0, 0, 0));
    jButton3.setMargin(new Insets(0, 0, 0, 0));
    jPanel3.setLayout(borderLayout2);
    jPanel5.setLayout(borderLayout3);
    jButton4.setMargin(new Insets(4, 4, 4, 4));
    jButton4.setText("发送");
    jButton1.setIcon(ExplorerIcons.getExplorerIcon("office/(11,18).gif"));
    jButton2.setIcon(ExplorerIcons.getExplorerIcon("office/(22,28).gif"));
    jButton3.setIcon(ExplorerIcons.getExplorerIcon("office/(22,48).gif"));
    jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.
                                              HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.
                                            VERTICAL_SCROLLBAR_ALWAYS);
    jLabel1.setText("用户对话");
    jLabel1.setIcon(ExplorerIcons.getExplorerIcon("sunone9/registration.png"));
    jPanel1.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    flowLayout2.setHgap(0);
    flowLayout2.setVgap(0);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                              HORIZONTAL_SCROLLBAR_NEVER);
    this.add(jPanel1, java.awt.BorderLayout.NORTH);
    jPanel1.add(jLabel1);
    this.add(jPanel2, java.awt.BorderLayout.SOUTH);
    jPanel2.add(jPanel4);
    jPanel4.add(jButton3);
    jPanel4.add(jButton1);
    jPanel4.add(jButton2);
    jPanel2.add(jPanel3);
    this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jEditorPane1);
    jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);
    jPanel3.add(jPanel6, java.awt.BorderLayout.EAST);
    jPanel6.add(jButton4);
    jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);
    jScrollPane2.getViewport().add(jEditorPane2);
  }

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JEditorPane jEditorPane1 = new JEditorPane();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JButton jButton1 = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JScrollPane jScrollPane2 = new JScrollPane();
  BorderLayout borderLayout3 = new BorderLayout();
  JEditorPane jEditorPane2 = new JEditorPane();
  JButton jButton4 = new JButton();
  JLabel jLabel1 = new JLabel();
  FlowLayout flowLayout2 = new FlowLayout();
}
