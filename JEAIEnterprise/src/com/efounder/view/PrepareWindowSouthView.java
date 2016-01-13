package com.efounder.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.core.xml.*;
import com.efounder.node.view.*;


public class PrepareWindowSouthView extends JPanel implements ComponentView {
  FlowLayout flowLayout1 = new FlowLayout();
  JButton bnOK = new JButton();
  JButton bnCancel = new JButton();
  FlowLayout flowLayout2 = new FlowLayout();

  public PrepareWindowSouthView() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(flowLayout1);
    bnOK.setMnemonic('O');
    bnOK.setText("确定(O)");
    bnOK.addActionListener(new TaskResrPrepareNodeSouthView_bnOK_actionAdapter(this));
    bnCancel.setMnemonic('C');
    bnCancel.setText("取消(C)");
    bnCancel.addActionListener(new
        TaskResrPrepareNodeSouthView_bnCancel_actionAdapter(this));
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setVgap(0);
    flowLayout1.setHgap(0);
    this.add(bnOK);
    this.add(bnCancel);
  }
  NodeChildWindow nodeWindow = null;
  public void setParentView(Component comp) {
    if ( comp instanceof NodeChildWindow ){
      nodeWindow = (NodeChildWindow)comp;
//      floatAxisNode = (FloatAxisNodeAdapter)nodeWindow.getNodeStub();
    }
  }

  public void initComponentView(CompViewFactory cvf, StubObject stubObject) {
  }

  public Component getComponent() {
    return this;
  }

  public Object getValue(Object key, Object def) {
    return null;
  }

  public void setValue(Object key, Object value) {
  }
  protected void processNodeView() {
    NodeViewer[] nvs = nodeWindow.getNodeViewerArray();
    for(int i=0;nvs!=null&&i<nvs.length;i++) {
      if ( nvs[i] != null )
        JBOFClass.CallObjectMethod(nvs[i],"applyView");
    }
  }
  public void bnOK_actionPerformed(ActionEvent e) {
    processNodeView();
    NodeChildWindow.getPDialog(bnOK).OnOk();
  }

  public void bnCancel_actionPerformed(ActionEvent e) {
    NodeChildWindow.getPDialog(bnCancel).OnCancel();
  }
}

class TaskResrPrepareNodeSouthView_bnCancel_actionAdapter
    implements ActionListener {
  private PrepareWindowSouthView adaptee;
  TaskResrPrepareNodeSouthView_bnCancel_actionAdapter(
      PrepareWindowSouthView adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bnCancel_actionPerformed(e);
  }
}

class TaskResrPrepareNodeSouthView_bnOK_actionAdapter
    implements ActionListener {
  private PrepareWindowSouthView adaptee;
  TaskResrPrepareNodeSouthView_bnOK_actionAdapter(PrepareWindowSouthView
                                                  adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bnOK_actionPerformed(e);
  }
}
