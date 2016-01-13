package com.efounder.service.start.comp;

import com.efounder.view.ComponentViewAdapter;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.service.management.MemoryMonitor;

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
public class SysMemListView extends ComponentViewAdapter {
  public SysMemListView() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  protected MemoryMonitor demo = null;
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jLabel1.setText("内存使用情况");
    jLabel1.setIcon(ExplorerIcons.getExplorerIcon("sunone9/monitor.gif"));
    jPanel2.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    this.add(jPanel2, java.awt.BorderLayout.NORTH);
    jPanel2.add(jLabel1);
//    demo = new MemoryMonitor();
//    this.add(demo,java.awt.BorderLayout.CENTER);
  }

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  public void stopCompView() {
    if ( demo != null ) {
      demo.surf.stop();
      this.remove(demo);
    }
    demo = null;
  }
  public void startCompView() {
    demo = new MemoryMonitor();
    this.add(demo,java.awt.BorderLayout.CENTER);
    demo.surf.start();
  }
}
