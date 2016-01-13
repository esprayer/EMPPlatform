package com.error.ui;

import java.net.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.borland.jbcl.layout.*;
import com.efounder.eai.EAI;
import com.efounder.eai.ide.*;
import com.efounder.pfc.dialog.*;
import com.efounder.pfc.swing.*;
import com.error.HelpErrorManager;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class HelpErrorDialog extends JPDialog implements ListSelectionListener {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  JButton bnOK = new JButton();
  JButton bnCancel = new JButton();
  JButton bnHelp = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  JTabbedPane tpnError = new JTabbedPane();
  JPanel pnErrorStack = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextPane epMessageList = new JTextPane();
  Throwable exceptionObject = null;
  int Severity = 0;Object fireObject;
  JPanel pnErrorMessage = new JPanel();
  JPanel pnErrorHelp = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  JList lstStackList = new JList();
  JScrollPane jScrollPane3 = new JScrollPane();
  BorderLayout borderLayout4 = new BorderLayout();
  JEditorPane epHelpHtml = new JEditorPane();
  JPanel jPanel1 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JScrollPane jScrollPane4 = new JScrollPane();
  JEditorPane epExceptionHelp = new JEditorPane();
  java.util.List exceptionHelpList = null;
  java.util.List helpList = null;int helpIndex = 0;
  String errorMessage = null;
  JTextArea lbMessage1 = new JTextArea();
  JPanel jPanel3 = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JTextArea lbMessage2 = new JTextArea();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  protected HelpErrorDialog(Frame frame, String title, boolean modal,int severity,Throwable e,Object fireObject,String message) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
      this.lstStackList.addListSelectionListener(this);
      exceptionObject = e; Severity = severity;this.fireObject = fireObject;errorMessage = message;
      initError();
      viewExceptionObjectHelp();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  private void viewExceptionObjectHelp() {
    helpList = new java.util.ArrayList();StackTraceElement stack = null;
    Throwable e = getThrowable(exceptionObject);java.util.List tempList = null;
    java.util.List stackList = findErrorClass(e,fireObject);
    for(int i=0;i<stackList.size();i++) {
      stack = (StackTraceElement)stackList.get(i);
      tempList = HelpErrorManager.getHelpErrorManager().getExceptionObjectHelp(e,stack.getClassName(),stack.getMethodName());
      if ( tempList != null )
        helpList.addAll(tempList);
    }
    if ( stackList.size() == 0 ) helpList.add(HelpErrorManager.getHelpErrorManager().getNotFound());
    viewExObjHelp(helpList,0);
  }
  private void viewExObjHelp(java.util.List helpList,int index) {
    URL helpUrl = (URL)helpList.get(index);
    try {
      this.epHelpHtml.setPage(helpUrl);
    } catch ( Exception e ) {}
  }
  /**
   * Called whenever the value of the selection changes.
   * @param e the event that characterizes the change.
   */
  public void valueChanged(ListSelectionEvent e) {
    Throwable ex = (Throwable)this.lstStackList.getSelectedValue();
    viewExceptionHelp(ex);
  }
  private void viewExceptionHelp(Throwable ex) {
    exceptionHelpList = HelpErrorManager.getHelpErrorManager().getExceptionHelp(ex);
    if ( exceptionHelpList != null ) {
      try {
        this.epExceptionHelp.setPage( (URL) exceptionHelpList.get(0));
      } catch ( Exception e ) {e.printStackTrace();}
    }
  }
  /**
   * 根据当前对象，查找出出错的位置，并根据此内容，确定帮助的内容
   *
   * @param e Throwable
   * @param fObject Object
   * @return StackTraceElement
   */
  private java.util.List  findErrorClass(Throwable e,Object fObject) {
    StackTraceElement[] stes = e.getStackTrace();String stackName,objName=fObject.getClass().getName();
    java.util.List stackList = new java.util.ArrayList();
    for(int i=0;i<stes.length;i++) {
      stackName = stes[i].getClassName();
      if ( stackName.equals(objName) ) {
        stackList.add(stes[i]);
      }
    }
    return stackList;
  }
  private void initError() {
    epMessageList.setAutoscrolls(true);
    Throwable e = getThrowable(exceptionObject);
    String title = HelpErrorManager.getHelpErrorManager().getExceptionName(e);
    if ( errorMessage == null ) errorMessage = title;
    this.setTitle(errorMessage);
    if ( errorMessage != null ) {
      if ( e instanceof com.efounder.eai.data.EAServerException ) {
        title = "应用服务端异常:" + title;
      }
      lbMessage1.setText(title);
      lbMessage2.setText(getMessage(e));
    }
    processStack(e);
    //处理错误列表
    listError(exceptionObject,e);
  }
  private void listError(Throwable e,Throwable tE) {
    DefaultListModel listModel = new DefaultListModel();
    insertException(listModel,e);
    lstStackList.setModel(listModel);
    lstStackList.setCellRenderer(new JIConListCellRenderer(ExplorerIcons.ICON_ERROR));
    for(int i=0;i<lstStackList.getModel().getSize();i++) {
      if ( lstStackList.getModel().getElementAt(i).equals(tE) ) {
        lstStackList.setSelectedIndex(i);break;
      }
    }
  }
  private void insertException(DefaultListModel listModel,Throwable e) {
    listModel.insertElementAt(e,listModel.size());
    if ( e.getCause() != null ) {
      insertException(listModel,e.getCause());
    }
  }
  String getMessage(Throwable e) {
    if ( e == null ) return null;
    String m = e.getMessage();
    if ( m != null ) return m;else return getMessage(e.getCause());
  }
  Throwable getThrowable(Throwable e) {
    String m = e.getMessage();
    if ( m != null )
      return e;
    else {
      if ( e.getCause() == null )
        return e;
      else
        return getThrowable(e.getCause());
    }
  }

  private void processStack(Throwable e) {
    StackTraceElement[] stes = e.getStackTrace();
    for(int i=0;i<stes.length;i++) {
      insertString(stes[i].toString(),1);
      insertString("\n",1);
    }
    java.util.Map errorList = null;
    Object[] errorArray = null;
    if ( e instanceof com.efounder.eai.data.EAServerException ) {
      com.efounder.eai.data.EAServerException ee = (com.efounder.eai.data.EAServerException)e;
      errorList = ee.getErrorMessageList();
      errorArray = ee.getErrorMessageList().keySet().toArray();
    } else {
      errorList = new java.util.Hashtable();
      getErrorMessageList(e,errorList);
      errorArray = errorList.keySet().toArray();
    }
    for(int i=0;i<errorArray.length;i++) {
      insertString(errorArray[i].toString()+":"+errorList.get(errorArray[i]),1);
      insertString("\n",1);
    }
  }
  void getErrorMessageList(Throwable e,java.util.Map ErrorMessageList) {
    if ( e == null ) return;
    String m = e.getMessage();
    if ( m != null ) {
      ErrorMessageList.put(e.getClass().getName(),m);
    }
    getErrorMessageList(e.getCause(),ErrorMessageList);
  }

  private void insertString(String text,int Style) {
    Document doc = this.epMessageList.getDocument();
    SimpleAttributeSet attrSet = new SimpleAttributeSet();
    StyleConstants.setForeground(attrSet, Color.RED);
    try {
      doc.insertString(doc.getLength(), text, attrSet);
    } catch ( Exception e ) {
    }
  }
  public HelpErrorDialog(int severity,Throwable e,Object fireObject,String message) {
    this(EAI.EA.getMainWindow(), null,true,severity,e,fireObject,message);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    bnOK.setMnemonic('O');
    bnOK.setText("确定(O)");
    bnOK.addActionListener(new HelpErrorDialog_bnOK_actionAdapter(this));
    bnCancel.setMnemonic('C');
    bnCancel.setText("取消(C)");
    bnCancel.addActionListener(new HelpErrorDialog_bnCancel_actionAdapter(this));
    bnHelp.setMnemonic('H');
    bnHelp.setText("帮助(H)");
    jPanel2.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    pnErrorStack.setLayout(borderLayout2);
    epMessageList.setForeground(Color.red);
    epMessageList.setPreferredSize(new Dimension(520, 260));
    epMessageList.setEditable(false);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setPreferredSize(new Dimension(520, 260));
    jPanel2.setBorder(BorderFactory.createRaisedBevelBorder());
    pnErrorMessage.setLayout(borderLayout3);
    jScrollPane2.setPreferredSize(new Dimension(140, 120));
    pnErrorHelp.setLayout(borderLayout4);
    epHelpHtml.setEditable(false);
    epHelpHtml.setText("");
    pnErrorStack.setAlignmentY((float) 0.5);
    jButton1.setPreferredSize(new Dimension(22, 22));
    jButton1.setText("下一页");
    jButton2.setPreferredSize(new Dimension(22, 22));
    jButton2.setText("上一页");
    jPanel1.setLayout(verticalFlowLayout2);
    epExceptionHelp.setEditable(false);
//    lbMessage1.setText("错误1");
    jPanel3.setLayout(verticalFlowLayout1);
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);
    jPanel3.setBackground(Color.red);
    jPanel3.setBorder(BorderFactory.createRaisedBevelBorder());
//    lbMessage2.setText("错误2");
    verticalFlowLayout2.setHgap(0);
    verticalFlowLayout2.setVgap(0);
    jButton3.setText("上一页");
    jButton3.setPreferredSize(new Dimension(22, 22));
    jButton4.setText("上一页");
    jButton4.setPreferredSize(new Dimension(22, 22));
    lbMessage1.setBackground(Color.red);
    lbMessage1.setEditable(false);
    lbMessage1.setLineWrap(true);
    lbMessage2.setBackground(Color.red);
    lbMessage2.setEditable(false);
    lbMessage2.setLineWrap(true);
    lbMessage2.setWrapStyleWord(true);
    getContentPane().add(panel1);
    panel1.add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(bnOK, null);
    jPanel2.add(bnCancel, null);
    jPanel2.add(bnHelp, null);
    panel1.add(tpnError,  BorderLayout.CENTER);
    tpnError.insertTab("异常列表",ExplorerIcons.ICON_ERROR,pnErrorMessage,"",0);
//    tpnError.add(pnErrorMessage,         "异常列表");
    pnErrorMessage.add(jScrollPane2,  BorderLayout.WEST);
    pnErrorMessage.add(jScrollPane4, BorderLayout.CENTER);
    jScrollPane4.getViewport().add(epExceptionHelp, null);
    jScrollPane2.getViewport().add(lstStackList, null);
    tpnError.insertTab("解决方法",ExplorerIcons.ICON_FILEHTML,pnErrorHelp,"",1);
//    tpnError.add(pnErrorHelp,      "解决方法");
    tpnError.insertTab("堆栈信息",ExplorerIcons.ICON_THREADBREAK,pnErrorStack,"",2);
//    tpnError.add(pnErrorStack,    "堆栈信息");
    pnErrorStack.add(jScrollPane1, BorderLayout.CENTER);
    panel1.add(jPanel3, BorderLayout.NORTH);
    jPanel3.add(lbMessage1, null);
    jPanel3.add(lbMessage2, null);

    jScrollPane1.getViewport().add(epMessageList, null);
    pnErrorHelp.add(jScrollPane3,  BorderLayout.CENTER);
    pnErrorHelp.add(jPanel1,  BorderLayout.EAST);
    jPanel1.add(jButton1, null);
    jPanel1.add(jButton4, null);
    jPanel1.add(jButton3, null);
    jPanel1.add(jButton2, null);
    jScrollPane3.getViewport().add(epHelpHtml, null);
  }

  void bnOK_actionPerformed(ActionEvent e) {
    this.OnOk();
  }

  void bnCancel_actionPerformed(ActionEvent e) {
    this.OnCancel();
  }
}

class HelpErrorDialog_bnOK_actionAdapter implements java.awt.event.ActionListener {
  HelpErrorDialog adaptee;

  HelpErrorDialog_bnOK_actionAdapter(HelpErrorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnOK_actionPerformed(e);
  }
}

class HelpErrorDialog_bnCancel_actionAdapter implements java.awt.event.ActionListener {
  HelpErrorDialog adaptee;

  HelpErrorDialog_bnCancel_actionAdapter(HelpErrorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnCancel_actionPerformed(e);
  }
}
