package com.efounder.eai.service.dof.DOFCommObject;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JCommPanel extends JPanel {
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField1 = new JTextField();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JLabel jLabel4 = new JLabel();
  JTextField jTextField3 = new JTextField();
  JPanel jPanel4 = new JPanel();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField4 = new JTextField();
  JLabel jLabel6 = new JLabel();
  JTextField jTextField5 = new JTextField();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  BoxLayout2 boxLayout22 = new BoxLayout2();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  TitledBorder titledBorder2;
  BoxLayout2 boxLayout23 = new BoxLayout2();
  JPanel jPanel5 = new JPanel();
  JButton bnAdd = new JButton();
  JButton bnFix = new JButton();
  JButton bnDel = new JButton();
  JCheckBox jCheckBox1 = new JCheckBox();
  FlowLayout flowLayout1 = new FlowLayout();

  public JCommPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder1 = new TitledBorder(border1,"安全连接设置");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(border2,"普通连接设置");
    this.setLayout(verticalFlowLayout1);
    jLabel1.setText("服务器列表：");
    jPanel1.setLayout(boxLayout23);
    jComboBox1.setPreferredSize(new Dimension(150, 25));
    jLabel2.setText("服务器名称：");
    jPanel2.setLayout(borderLayout1);
    jLabel3.setRequestFocusEnabled(true);
    jLabel3.setText("址址：");
    jPanel3.setLayout(boxLayout21);
    borderLayout1.setHgap(0);
    borderLayout1.setVgap(0);
    jLabel4.setText("端口：");
    jLabel5.setText("址址：");
    jLabel6.setText("端口：");
    jPanel4.setLayout(boxLayout22);
    jTextField2.setText("");
    jTextField4.setText("");
    jTextField5.setText("");
    jTextField3.setText("");
    jTextField1.setText("");
    jPanel4.setBorder(titledBorder1);
    jPanel3.setBorder(titledBorder2);
    bnAdd.setMnemonic('A');
    bnAdd.setText("增加(A)");
    bnFix.setMnemonic('F');
    bnFix.setText("修改(F)");
    bnDel.setMnemonic('D');
    bnDel.setText("删除(D)");
    verticalFlowLayout1.setVerticalFill(false);
    jCheckBox1.setText("默认");
    jPanel5.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    this.add(jPanel1, null);
    jPanel1.add(jLabel1, null);
    jPanel1.add(jComboBox1, null);
    this.add(jPanel2, null);
    jPanel2.add(jLabel2, BorderLayout.WEST);
    jPanel2.add(jTextField1, BorderLayout.CENTER);
    this.add(jPanel3, null);
    jPanel3.add(jLabel3, null);
    jPanel3.add(jTextField2, null);
    jPanel3.add(jLabel4, null);
    jPanel3.add(jTextField3, null);
    this.add(jPanel4, null);
    jPanel4.add(jLabel5, null);
    jPanel4.add(jTextField4, null);
    jPanel4.add(jLabel6, null);
    jPanel4.add(jTextField5, null);
    this.add(jPanel5, null);
    jPanel5.add(jCheckBox1, null);
    jPanel5.add(bnAdd, null);
    jPanel5.add(bnFix, null);
    jPanel5.add(bnDel, null);
  }
}
