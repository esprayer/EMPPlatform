package com.efounder.view;

import java.awt.*;
import javax.swing.*;
import com.efounder.pfc.window.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JCommandPanel extends JPanel implements ComponentFactory {
  JComboBox jComboBox1 = new JComboBox();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();

  public JCommandPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    jComboBox1.setPreferredSize(new Dimension(120, 22));
    jComboBox1.setEditable(true);
    jButton1.setPreferredSize(new Dimension(22, 22));
    jButton1.setText("B");
    jButton2.setPreferredSize(new Dimension(22, 22));
    jButton2.setText("B");
    this.add(jComboBox1, null);
    this.add(jButton1, null);
    this.add(jButton2, null);
  }
  public JComponent[] createComponent(JComponent parent) {
    JComponent[] rs = {this};
    return rs;
  }

}
