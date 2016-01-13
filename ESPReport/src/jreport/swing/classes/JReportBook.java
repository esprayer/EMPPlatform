package jreport.swing.classes;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jbof.gui.window.classes.*;
import jframework.resource.classes.*;
import jreport.model.classes.calculate.*;
import jreport.swing.classes.ReportBook.*;
import jreport.swing.classes.wizard.*;
import java.util.ResourceBundle;
import jfoundation.gui.window.classes.JMainWindow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
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
public class JReportBook extends JPanel implements ActionListener,IWizardInterface {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.Language");
//  public JBOFMainWindow MainWindow = null;
  public JMainWindow MainWindow = null;
  private static ImageIcon iiDH = null;
  private static ImageIcon iiOK = null;
  private static ImageIcon iiCancel = null;
  private static ImageIcon iiSum = null;
  private static ImageIcon iiFunc = null;

  private static ImageIcon iiAddJygs = null;
  private static ImageIcon iiEditJygs = null;

  private int EditStatus = 0;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private BorderLayout borderLayout2 = new BorderLayout();
  private BorderLayout borderLayout3 = new BorderLayout();
  private FlowLayout flowLayout1 = new FlowLayout();
  //
  private JBookTextField BookTextField = new JBookTextField();
  private JBookComboBox BookComboBox = new JBookComboBox();
  //
  private JButton bnCancelSum = new JButton();
  private JButton bnDHOK = new JButton();
  private JButton bnFuncWzd = new JButton();

  private JPanel jPanel3 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private BorderLayout borderLayout4 = new BorderLayout();
  private JPanel jPanel4 = new JPanel();
  public  JReportView ReportView = new JReportView();
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  // 计算管理器
  public static JCalculateManager CalculateManager = null;
  // 函数管理器
  public static JFunctionManager  FunctionManager  = null;
  private JComboBox cbFH = new JComboBox();
  JCheckBox cbJYGS = new JCheckBox();
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JReportBook() {
    try {
      jbInit();
//      setPublicAttrib();
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
  void jbInit() throws Exception {
    bnFuncWzd.setActionCommand("bnFuncWzd");
    jPanel1.setAlignmentX((float) 0.0);
    jPanel1.setAlignmentY((float) 0.0);
    jPanel1.setLayout(borderLayout2);
    this.setLayout(borderLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    BookComboBox.setEditable(true);
    jPanel3.setLayout(borderLayout3);
    jPanel2.setLayout(flowLayout1);
    jPanel4.setLayout(borderLayout4);
    ReportView.setFileName("");
    cbFH.setPreferredSize(new Dimension(50, 27));
    jPanel2.add(BookComboBox, null);
    jPanel2.add(bnFuncWzd, null);
    jPanel2.add(bnCancelSum, null);
    jPanel2.add(bnDHOK, null);
    jPanel2.add(cbFH, null);
    jPanel1.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(BookTextField, BorderLayout.CENTER);
    jPanel3.add(cbJYGS,  BorderLayout.EAST);
    this.add(jPanel4, BorderLayout.CENTER);
    jPanel4.add(ReportView, BorderLayout.CENTER);
    jPanel1.add(jPanel2, BorderLayout.WEST);
    this.add(jPanel1,  BorderLayout.NORTH);
    this.BookComboBox.setReportView(ReportView);
    this.BookTextField.setReportView(ReportView);

    ReportView.bnCancelSum = bnCancelSum;
    ReportView.bnDHOK      = bnDHOK;
    ReportView.bnFuncWzd   = bnFuncWzd;
    ReportView.cbFH   = cbFH;
    ReportView.cbJYGS = cbJYGS;

    LoadImageIcon();
    setImageIcon1();
    bnDHOK.addActionListener(this);
    bnCancelSum.addActionListener(this);
    bnFuncWzd.addActionListener(this);
//    BookComboBox.addActionListener(this);

    InitcbFH();
  }
  void InitcbFH() {
    BookTextField.cbFH = cbFH;
    BookTextField.cbJYGS = cbJYGS;
    cbFH.setVisible(false);
    cbJYGS.setVisible(false);

    cbFH.insertItemAt("=",0);
    cbFH.insertItemAt("<>",1);
    cbFH.insertItemAt(">",2);
    cbFH.insertItemAt(">=",3);
    cbFH.insertItemAt("<",4);
    cbFH.insertItemAt("<=",5);
    cbFH.setSelectedIndex(0);
    cbFH.addActionListener(BookTextField);
    cbJYGS.addActionListener(BookTextField);
    cbFH.addItemListener(BookTextField);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void LoadImageIcon() {
    if ( iiDH == null ) {
      iiDH = JXMLResource.LoadImageIcon(this,"dhwzd.gif");
      iiSum = JXMLResource.LoadImageIcon(this,"sumwzd.gif");
      iiOK = JXMLResource.LoadImageIcon(this,"ok.gif");
      iiFunc = JXMLResource.LoadImageIcon(this,"funcwzd.gif");
      iiCancel = JXMLResource.LoadImageIcon(this,"cancel.gif");
      iiAddJygs = JXMLResource.LoadImageIcon(this,"addjygs.gif");
      iiEditJygs = JXMLResource.LoadImageIcon(this,"editjygs.gif");
    }
    Insets is = new Insets(2,2,2,2);
    bnDHOK.setMargin(is);
    bnCancelSum.setMargin(is);
    bnFuncWzd.setMargin(is);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void setImageIcon1() {
    EditStatus = 0;
    bnDHOK.setIcon(iiDH);
    bnCancelSum.setIcon(iiSum);
    bnFuncWzd.setIcon(iiFunc);
    cbJYGS.setIcon(iiEditJygs);
    cbJYGS.setSelectedIcon(iiEditJygs);
//    cbJYGS.setSelectedIcon(iiAddJygs);
    cbJYGS.setToolTipText(res.getString("String_32"));
    cbJYGS.setBackground(Color.orange);

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void setImageIcon2() {
    EditStatus = 1;
    bnDHOK.setIcon(iiOK);
    bnCancelSum.setIcon(iiCancel);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void actionPerformed(ActionEvent e) {
    if ( e.getSource() == bnDHOK ) {
      ProcecssDHOK();
    }
    if ( e.getSource() == bnCancelSum ) {
      ProcessCancelSum();
    }
    if ( e.getSource() == bnFuncWzd ) {
      ProcessFuncWzd();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcecssDHOK() {
    if ( EditStatus == 0 ) {
      setImageIcon2();
      ProcessDH();
      return;
    }
    if ( EditStatus == 1 ) {
      setImageIcon1();
      ProcessOK();
      return;
    }

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessDH() {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessOK() {
      //保存公式
      this.BookTextField.SaveF1();
//      ((JReportModel)ReportView.getModel()).WizardObject.BookTextField.SaveF1();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessCancelSum() {
    if ( EditStatus == 0 ) {
      setImageIcon2();
      ProcessSum();
      return;
    }
    if ( EditStatus == 1 ) {
      setImageIcon1();
      ProcessCancel();
      return;
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessSum() {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessCancel() {
      //删除公式
      BookTextField.TextField.setText("");
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void ProcessFuncWzd() {
    setImageIcon2();
    int x = BookComboBox.getLocationOnScreen().x+BookComboBox.getWidth();
    int y = BookComboBox.getLocationOnScreen().y+BookComboBox.getHeight();
    ReportView.getModel().ShowWizard(x,y,MainWindow,ReportView.getModel(),this);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void WizardDialogClose() {
    setImageIcon1();
  }
}
