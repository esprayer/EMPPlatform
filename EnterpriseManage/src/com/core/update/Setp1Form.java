package com.core.update;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.core.loader.mainConfig;

import esyt.framework.com.core.xml.StubObject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Hashtable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Setp1Form extends JPanel
  implements ActionListener, ListSelectionListener
{
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JLabel lbStep1 = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  JPanel jPanel2 = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JCheckBox rbRemote = new JCheckBox();
  JPanel jPanel3 = new JPanel();
  JCheckBox rbLocal = new JCheckBox();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  JLabel jLabel1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JPanel jPanel4 = new JPanel();
  JButton bnProxy = new JButton();
  FlowLayout flowLayout2 = new FlowLayout();
  JPanel jPanel5 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JList lstUpdateCenter = new JList();
  JCheckBox cbDefaultCenter = new JCheckBox();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  Hashtable hashList = null;

  public Setp1Form() {
    try { jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    setLayout(this.borderLayout1);
    this.lbStep1.setText("选择下载升级中心");
    this.jPanel1.setLayout(this.flowLayout1);
    this.flowLayout1.setAlignment(0);
    this.jPanel2.setLayout(this.verticalFlowLayout1);
    this.rbRemote.setText("从指定网站下载可用升级模块");
    this.rbLocal.setText("从本地安装已下载升级模块");
    this.jPanel3.setLayout(this.verticalFlowLayout2);
    this.jLabel1.setText("选择一个下载中心：");
    this.verticalFlowLayout2.setHgap(25);
    this.bnProxy.setText("代理服务器");
    this.jPanel4.setLayout(this.flowLayout2);
    this.flowLayout2.setAlignment(0);
    this.flowLayout2.setHgap(5);
    this.jPanel5.setLayout(this.borderLayout2);
    this.cbDefaultCenter.setText("默认下载中心");
    add(this.jPanel1, "North");
    this.jPanel1.add(this.lbStep1, null);
    add(this.jPanel2, "Center");
    this.jPanel2.add(this.rbRemote, null);
    this.jPanel2.add(this.jPanel3, null);
    this.jPanel3.add(this.jLabel1, null);
    this.jPanel3.add(this.jPanel5, null);
    this.jPanel5.add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add(this.lstUpdateCenter, null);
    this.jPanel3.add(this.jPanel4, null);
    this.jPanel4.add(this.cbDefaultCenter, null);
    this.jPanel4.add(this.bnProxy, null);
    this.jPanel2.add(this.rbLocal, null);
    initCtrl();
    initSize();
    initValue();
    processOnrbRemote();
    initEvent();
  }

  void initEvent()
  {
    this.rbRemote.addActionListener(this);
    this.rbLocal.addActionListener(this);
    this.cbDefaultCenter.addActionListener(this);
    this.lstUpdateCenter.addListSelectionListener(this);
  }

  void initCtrl()
  {
    this.bnProxy.setVisible(false);
    this.lstUpdateCenter.setSelectionMode(0);
  }

  void initSize()
  {
    this.jPanel5.setPreferredSize(new Dimension(0, 100));
    setPreferredSize(new Dimension(400, 300));
  }

  void initValue()
  {
    initUpdateCenterList();
  }

  void initUpdateCenterList()
  {
    Hashtable UpdateCenterList = UpdateObject.getUpdateCenterList();
    Object[] UCL = UpdateCenterList.values().toArray();
    this.lstUpdateCenter.setListData(UCL);
    if (UCL.length > 0)
      this.lstUpdateCenter.setSelectedIndex(0);
  }

  public Object willNext(Object p1, Object p2, Object p3, Object p4)
  {
    StubObject SO = _$5544();
    if (SO == null) {
      JOptionPane.showMessageDialog(this, "请选择一个下载中心进行下载升级！", "系统提示", 0);
    }

    return SO; }

  private StubObject _$5544() {
    StubObject SO = null;
    if (this.rbRemote.isSelected()) {
      SO = (StubObject)this.lstUpdateCenter.getSelectedValue();
      if (SO != null)
        SO.setObject("REMOTE", "1");
    } else if (this.rbLocal.isSelected()) {
      SO = new StubObject();
      SO.setObject("REMOTE", "0");
    }
    return SO;
  }

  public Object willBack(Object p1, Object p2, Object p3, Object p4)
  {
    return null;
  }

  public Object willInto(Object p1, Object p2, Object p3, Object p4)
  {
    this.hashList = ((Hashtable)p3);
    _$5550();
    _$5551();
    return null;
  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == this.rbRemote) {
      processOnrbRemote();
    }
    if (e.getSource() == this.rbLocal) {
      processOnrbLocal();
    }
    if (e.getSource() == this.cbDefaultCenter)
      _$5555();
  }

  private void _$5550()
  {
    String CB = (String)this.hashList.get("codebase");
    CB = (String)mainConfig.get("DefaultUpdateCenter", CB); StubObject SO = null;
    for (int i = 0; i < this.lstUpdateCenter.getModel().getSize(); ++i) {
      SO = (StubObject)this.lstUpdateCenter.getModel().getElementAt(i);
      if (CB.equals(SO.getObject("codebase", ""))) {
        this.lstUpdateCenter.setSelectedIndex(i);
        return;
      }
    }
  }

  private void _$5555() {
    _$5559(true);
    StubObject SO = _$5560();
    if (SO != null)
      mainConfig.put("DefaultUpdateCenter", SO.getObject("codebase", ""));
  }

  private StubObject _$5560()
  {
    StubObject SO = (StubObject)this.lstUpdateCenter.getSelectedValue();
    return SO;
  }

  private void _$5559(boolean v)
  {
    this.cbDefaultCenter.setSelected(v);
  }

  private void _$5551()
  {
    String CB = (String)this.hashList.get("codebase");
    CB = (String)mainConfig.get("DefaultUpdateCenter", CB);
    StubObject SO = _$5560();
    if (CB.equals(SO.getObject("codebase", "")))
      _$5559(true);
    else
      _$5559(false);
  }

  public void valueChanged(ListSelectionEvent e)
  {
    _$5551(); }

  void processOnrbRemote() {
    this.rbRemote.setSelected(true);
    this.rbLocal.setSelected(false);
    this.lstUpdateCenter.setEnabled(true);
    this.bnProxy.setEnabled(true);
    this.cbDefaultCenter.setEnabled(true);
    this.buttonGroup1.add(this.rbRemote);
    this.buttonGroup1.add(this.rbLocal); }

  void processOnrbLocal() {
    this.rbRemote.setSelected(false);
    this.rbLocal.setSelected(true);
    this.lstUpdateCenter.setEnabled(false);
    this.bnProxy.setEnabled(false);
    this.cbDefaultCenter.setEnabled(false);
  }
}