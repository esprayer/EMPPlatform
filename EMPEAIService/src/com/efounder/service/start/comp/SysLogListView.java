package com.efounder.service.start.comp;

import com.efounder.view.ComponentViewAdapter;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.FlowLayout;
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
public class SysLogListView extends ComponentViewAdapter {
  public SysLogListView() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jLabel1.setText("系统日志列表");
    jLabel1.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/method_stop_16.png"));
    jPanel2.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    this.add(jPanel2, java.awt.BorderLayout.NORTH);
    jPanel2.add(jLabel1);
    com.efounder.eai.ui.LogViewWindow logView = new com.efounder.eai.ui.LogViewWindow();
    this.add(logView, java.awt.BorderLayout.CENTER);
  }

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
}
