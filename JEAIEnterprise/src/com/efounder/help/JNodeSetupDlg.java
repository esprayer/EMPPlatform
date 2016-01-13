package com.efounder.help;

import java.awt.*;
import com.efounder.pfc.dialog.JPDialog;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.efounder.node.*;
import com.efounder.eai.*;
import com.efounder.eai.ide.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JNodeSetupDlg extends JPDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnTop = new JPanel();
  JPanel pnContent = new JPanel();
  JPanel pnBottom = new JPanel();
  JButton bnUpload = new JButton();
  JButton bnOK = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel lbTitle = new JLabel();
  FlowLayout flowLayout2 = new FlowLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JLabel lbNodeDoc = new JLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  JTextField jTextField1 = new JTextField();
  JButton bnListDoc = new JButton();
  JLabel lbNodeHelp = new JLabel();
  JTextField jTextField2 = new JTextField();
  JButton bnListHelp = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  /**
   *
   * @param frame Frame
   * @param title String
   * @param modal boolean
   */
  public JNodeSetupDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @param nodeStub JNodeStub
   */
  public JNodeSetupDlg(JNodeStub nodeStub) {
    this(EAI.EA.getMainWindow(), nodeStub.toString(), true);
    this.nodeStub = nodeStub;
    initValue();
  }
  protected JNodeStub nodeStub = null;
  /**
   *
   */
  protected void initValue() {
    this.lbTitle.setIcon(nodeStub.getNodeIcon());
    this.lbTitle.setText(nodeStub.toString()+"节点帮助文档设置！");
    this.lbNodeDoc.setIcon(EnterpriseIcons.ICON_JBPROJECT);
    lbNodeDoc.setText("文档设置：");
    this.lbNodeHelp.setIcon(ExplorerIcons.ICON_WARNING);
    lbNodeHelp.setText("帮助设置：");
  }
  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    bnUpload.setMnemonic('U');
    bnUpload.setText("上传(U)");
    bnOK.setMnemonic('O');
    bnOK.setText("确定(O)");
    pnBottom.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(5);
    lbTitle.setText("jLabel1");
    pnTop.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    flowLayout2.setHgap(5);
    pnContent.setLayout(verticalFlowLayout1);
    lbNodeDoc.setText("jLabel2");
    jPanel1.setLayout(borderLayout2);
    jTextField1.setOpaque(true);
    jTextField1.setPreferredSize(new Dimension(200, 22));
    jTextField1.setRequestFocusEnabled(true);
//    jTextField1.setText("jTextField1");
    bnListDoc.setMargin(new Insets(0, 0, 0, 0));
    bnListDoc.setText("...");
    lbNodeHelp.setText("jLabel3");
    jTextField2.setPreferredSize(new Dimension(200, 22));
//    jTextField2.setText("jTextField1");
    bnListHelp.setMargin(new Insets(0, 0, 0, 0));
    bnListHelp.setText("...");
    jPanel2.setLayout(borderLayout3);
    getContentPane().add(panel1);
    panel1.add(pnTop, BorderLayout.NORTH);
    panel1.add(pnContent, BorderLayout.CENTER);
    panel1.add(pnBottom,  BorderLayout.SOUTH);
    pnBottom.add(bnUpload, null);
    pnBottom.add(bnOK, null);
    pnTop.add(lbTitle, null);
    pnContent.add(jPanel1, null);
    jPanel1.add(jTextField1, BorderLayout.CENTER);
    jPanel1.add(lbNodeDoc, BorderLayout.WEST);
    jPanel1.add(bnListDoc, BorderLayout.EAST);
    pnContent.add(jPanel2, null);
    jPanel2.add(lbNodeHelp, BorderLayout.WEST);
    jPanel2.add(jTextField2, BorderLayout.CENTER);
    jPanel2.add(bnListHelp, BorderLayout.EAST);
  }
}
