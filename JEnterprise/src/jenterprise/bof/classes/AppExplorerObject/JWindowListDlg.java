package jenterprise.bof.classes.AppExplorerObject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import com.efounder.eai.application.classes.JBOFApplication;

import jbof.application.classes.*;
import jbof.gui.window.classes.*;
import jcomponent.custom.swing.*;
import jfoundation.gui.window.classes.*;
import jframework.resource.classes.*;
import jframework.foundation.classes.JActiveDComDM;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skylin
//修改:
//--------------------------------------------------------------------------------------------------
public class JWindowListDlg extends JFrameDialog {
  static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList lstWindowList = new JList();
  JPanel jPanel3 = new JPanel();
  JButton bnOK = new JButton();
  JButton bnClose = new JButton();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JButton bnActive = new JButton();
  JLabel jLabel1 = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JBOFMainWindow MainWindow;
  static ImageIcon II = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------

  public JWindowListDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------

  public JWindowListDlg() {
    this(JActiveDComDM.MainApplication.MainWindow, "", false);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    bnOK.setMnemonic('O');
    bnOK.setText(res.getString("String_409"));
    bnOK.addActionListener(new JWindowListDlg_bnOK_actionAdapter(this));
    bnClose.setActionCommand(res.getString("String_410"));
    bnClose.setMnemonic('C');
    bnClose.setText(res.getString("String_411"));
    bnClose.addActionListener(new JWindowListDlg_bnClose_actionAdapter(this));
    jPanel3.setLayout(verticalFlowLayout1);
    bnActive.setMnemonic('A');
    bnActive.setText(res.getString("String_412"));
    bnActive.addActionListener(new JWindowListDlg_bnActive_actionAdapter(this));
    jLabel1.setText(res.getString("String_413"));
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    jPanel2.setLayout(borderLayout2);
    jScrollPane1.setPreferredSize(new Dimension(259, 180));
    jPanel2.setPreferredSize(new Dimension(259, 180));
    jPanel3.setPreferredSize(new Dimension(104, 180));
    getContentPane().add(panel1);
    panel1.add(jPanel2,  BorderLayout.CENTER);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    panel1.add(jPanel3, BorderLayout.EAST);
    jPanel3.add(bnOK, null);
    jPanel3.add(bnClose, null);
    jPanel3.add(bnActive, null);
    panel1.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel1, null);
    jScrollPane1.getViewport().add(lstWindowList, null);
    this.setTitle(res.getString("String_414"));
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void InitDialog() {
    JBOFApplication App;
    App = (JBOFApplication)CustomObject;
//    MainWindow = App.MainWindow;
//    InitWindowList(MainWindow);
    if ( II == null )
      II = JXMLResource.LoadImageIcon(this,"window.gif");
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void InitWindowList(JBOFMainWindow Window) {
    DefaultListModel ListModel;int Count;JBOFChildWindow cwnd;
    JIConListCellRenderer ICR = new JIConListCellRenderer(II);
    lstWindowList.setCellRenderer(ICR);
    ListModel = new DefaultListModel();
    Count = Window.GetChildWindowCount();
    for(int i=0;i<Count;i++) {
      cwnd = Window.GetChildWindow(i);
      ListModel.addElement(cwnd);
    }
    this.lstWindowList.setModel(ListModel);
  }

  void bnOK_actionPerformed(ActionEvent e) {
    this.OnOk();
  }

  void bnClose_actionPerformed(ActionEvent e) {
    DefaultListModel ListModel;int Count;
    ListModel = (DefaultListModel)lstWindowList.getModel();
    Object[] OA = lstWindowList.getSelectedValues();
    for(int i=0;i<OA.length;i++) {
      this.MainWindow.CloseObjectWindow((Component)OA[i]);
    }
    InitWindowList(MainWindow);
  }

  void bnActive_actionPerformed(ActionEvent e) {
    DefaultListModel ListModel;
    ListModel = (DefaultListModel)lstWindowList.getModel();
    Object OA = lstWindowList.getSelectedValue();
    if ( OA == null ) return;
    this.MainWindow.setActiveWindow((JBOFChildWindow)OA);

  }
}

class JWindowListDlg_bnOK_actionAdapter implements java.awt.event.ActionListener {
  JWindowListDlg adaptee;

  JWindowListDlg_bnOK_actionAdapter(JWindowListDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnOK_actionPerformed(e);
  }
}

class JWindowListDlg_bnClose_actionAdapter implements java.awt.event.ActionListener {
  JWindowListDlg adaptee;

  JWindowListDlg_bnClose_actionAdapter(JWindowListDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnClose_actionPerformed(e);
  }
}

class JWindowListDlg_bnActive_actionAdapter implements java.awt.event.ActionListener {
  JWindowListDlg adaptee;

  JWindowListDlg_bnActive_actionAdapter(JWindowListDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnActive_actionPerformed(e);
  }
}
