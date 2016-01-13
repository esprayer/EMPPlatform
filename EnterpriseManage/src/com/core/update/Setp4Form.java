package com.core.update;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import esyt.framework.com.core.xml.JDOMXMLBaseObject;
import esyt.framework.com.core.xml.StubObject;

public class Setp4Form extends JPanel
{
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList lstEAM = new JList();
  JLabel jLabel1 = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  UpdateStub[] updateStubArray = null;
  StubObject updateCenterStubObject = null;
  JDOMXMLBaseObject XMLObject = null;

  public Setp4Form() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    setLayout(this.borderLayout1);
    this.jLabel1.setText("已下载安装的企业应用模块：");
    this.jPanel1.setLayout(this.flowLayout1);
    this.flowLayout1.setAlignment(0);
    add(this.jPanel1, "North");
    this.jPanel1.add(this.jLabel1, null);
    add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add(this.lstEAM, null);
  }

  public Object willNext(Object p1, Object p2, Object p3, Object p4)
  {
    return null;
  }

  public Object willBack(Object p1, Object p2, Object p3, Object p4)
  {
    return null;
  }

  public Object willInto(Object p1, Object p2, Object p3, Object p4)
  {
    Object[] Stubs = (Object[])(Object[])p1;
    this.updateStubArray = ((UpdateStub[])(UpdateStub[])Stubs[0]);
    this.updateCenterStubObject = ((StubObject)Stubs[1]);
    this.XMLObject = ((JDOMXMLBaseObject)Stubs[2]);
    _$5850();
    return Stubs; }

  private void _$5850() {
    this.lstEAM.removeAll();
    if ((this.updateStubArray == null) || (this.updateStubArray.length == 0)) return;
    DefaultComboBoxModel listModel = new DefaultComboBoxModel();
    for (int i = 0; i < this.updateStubArray.length; ++i) {
      listModel.insertElementAt(this.updateStubArray[i], i);
    }
    this.lstEAM.setModel(listModel);
  }
}