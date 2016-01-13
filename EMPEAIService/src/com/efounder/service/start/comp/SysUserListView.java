package com.efounder.service.start.comp;

import com.efounder.view.*;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import com.efounder.eai.ide.*;

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
public class SysUserListView
    extends ComponentViewAdapter {
  public SysUserListView() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
  //  com.pansoft.user.dof.userListView userView = new com.pansoft.user.dof.userListView();
//    this.add(userView, java.awt.BorderLayout.CENTER);
  }

  BorderLayout borderLayout1 = new BorderLayout();
}
