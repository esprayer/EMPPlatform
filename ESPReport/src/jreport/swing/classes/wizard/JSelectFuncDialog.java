package jreport.swing.classes.wizard;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import jfoundation.gui.window.classes.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Set;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JSelectFuncDialog extends JFrameDialog implements ListSelectionListener,ActionListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JButton bnOK = new JButton();
  private JButton bnCancel = new JButton();
  private FlowLayout flowLayout1 = new FlowLayout();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JList lstFunctionTypeList = new JList();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JPanel jPanel4 = new JPanel();
  private JPanel jPanel5 = new JPanel();
  private BorderLayout borderLayout3 = new BorderLayout();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JList lstFunctionList = new JList();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private FlowLayout flowLayout2 = new FlowLayout();
  private FlowLayout flowLayout3 = new FlowLayout();

  public JFunctionWizardObject WizardObject = null;
  public JFunctionStub FunctionStub = null;

  /**
   * 为了支持自定义公式向导中显示什么函数而加，函数类别
   */
  public boolean isCustom;
  public HashMap mFuncMap;

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JSelectFuncDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      setMenuShow(false);
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
  public JSelectFuncDialog() {
    this(null, "", false);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    bnOK.setFont(new java.awt.Font("Dialog", 0, 12));
        bnOK.setPreferredSize(new Dimension(80, 25));
        bnOK.setMnemonic('O');
    bnOK.setText(res.getString("String_290"));
    bnCancel.setFont(new java.awt.Font("Dialog", 0, 12));
        bnCancel.setPreferredSize(new Dimension(80, 25));
        bnCancel.setMnemonic('C');
    bnCancel.setText(res.getString("String_293"));
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    jPanel2.setLayout(borderLayout2);
    jScrollPane1.setPreferredSize(new Dimension(200, 160));
    jPanel4.setLayout(borderLayout3);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setForeground(Color.black);
    jLabel1.setText(res.getString("String_295"));
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setForeground(Color.black);
    jLabel2.setText(res.getString("String_297"));
    jPanel3.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    jPanel5.setLayout(flowLayout3);
    flowLayout3.setAlignment(FlowLayout.LEFT);
    jScrollPane2.setPreferredSize(new Dimension(200, 160));
    getContentPane().add(panel1);
    panel1.add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(bnOK, null);
    jPanel1.add(bnCancel, null);
    panel1.add(jPanel2, BorderLayout.WEST);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(lstFunctionTypeList, null);
    jPanel2.add(jPanel3,  BorderLayout.NORTH);
    jPanel3.add(jLabel1, null);
    panel1.add(jPanel4, BorderLayout.CENTER);
    jPanel4.add(jPanel5, BorderLayout.NORTH);
    jPanel5.add(jLabel2, null);
    jPanel4.add(jScrollPane2,  BorderLayout.CENTER);
    jScrollPane2.getViewport().add(lstFunctionList, null);

    this.lstFunctionList.addListSelectionListener(this);
    this.lstFunctionTypeList.addListSelectionListener(this);
    bnOK.addActionListener(this);
    bnCancel.addActionListener(this);
  }

  /**
   * 是否是自定义函数向导
   *
   * @param isCustom boolean
   */
  public void setCustomFunction(boolean isCustom){
      this.isCustom = isCustom;
  }

  /**
   * 报告生成用
   *
   * @param map HashMap
   */
  public void setCustomFunctionMap(HashMap map){
      this.mFuncMap = null;
      this.mFuncMap = map;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void InitDialog() {
    InitFunctionTypeList();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void InitFunctionTypeList() {
    DefaultListModel dm;JFunctionTypeStub FTS;
    dm = new DefaultListModel();
    for(int i=0;i<WizardObject.FunctionTypeList.size();i++) {
      FTS = (JFunctionTypeStub)WizardObject.FunctionTypeList.get(i);
      //如果是自定义函数向导
      //add by gengeng 2008-8-4
      if (isCustom) {
          if (mFuncMap != null) {
              Set keySet = mFuncMap.keySet();
              if (keySet.contains(FTS.getId())) {
                  dm.addElement(FTS);
              }
          }
      } else {
          dm.insertElementAt(FTS, i);
      }
    }
    lstFunctionTypeList.setModel(dm);
    if ( WizardObject.FunctionTypeList.size() >0 )
      lstFunctionTypeList.setSelectedIndex(0);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void InitFunctionList(JFunctionTypeStub FTS) {
    DefaultListModel dm;JFunctionStub FS;
    dm = new DefaultListModel();
    for(int i=0;i<FTS.FunctionList.size();i++) {
      FS = (JFunctionStub)FTS.FunctionList.get(i);
      if (isCustom) {
          if (mFuncMap != null) {
              java.util.List fList = (java.util.List) mFuncMap.get(FTS.getId());
              if (fList.contains(FS.getId())) {
                  dm.addElement(FS);
              }
          }
      } else {
          dm.insertElementAt(FS, i);
      }
    }
    lstFunctionList.setModel(dm);
    if ( FTS.FunctionList.size() >0 )
      lstFunctionList.setSelectedIndex(0);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void valueChanged(ListSelectionEvent e) {
    if ( e.getSource() == this.lstFunctionTypeList ) {
      ProcessSelectTypeList();
    }
    if ( e.getSource() == this.lstFunctionList ) {

    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessSelectTypeList() {
    JFunctionTypeStub FTS = (JFunctionTypeStub)this.lstFunctionTypeList.getSelectedValue();
    if ( FTS == null ) return;
    InitFunctionList(FTS);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void actionPerformed(ActionEvent e) {
    if ( e.getSource() == bnOK ) {
      ProcessOK();
      this.OnOk();
    }
    if ( e.getSource() == bnCancel ) {
      this.OnCancel();
    }
  }
  void ProcessOK() {
    FunctionStub = (JFunctionStub)lstFunctionList.getSelectedValue();
  }
}
