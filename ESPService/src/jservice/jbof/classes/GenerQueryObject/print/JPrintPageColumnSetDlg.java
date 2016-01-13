package jservice.jbof.classes.GenerQueryObject.print;

import java.awt.*;
import jfoundation.gui.window.classes.JFrameDialog;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import jframework.foundation.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPrintPageColumnSetDlg extends JFrameDialog
    implements ActionListener {
  public String Fixcol = "",Changecol="";
  public boolean Context = true;
  JPanel main = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel topPanel = new JPanel();
  JPanel leftPanel = new JPanel();
  JPanel rightPanel = new JPanel();
  JPanel bottomPanel = new JPanel();
  JPanel centerPanel = new JPanel();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  BoxLayout2 boxLayout22 = new BoxLayout2();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  Border border1;
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();
  FlowLayout flowLayout3 = new FlowLayout();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel2 = new JLabel();
  JSpinner jfixcolSpinner = new JSpinner();
  JLabel jLabel3 = new JLabel();
  JSpinner jchangecolSpinner = new JSpinner();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JTextPane jTextPane1 = new JTextPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JPanel jPanel6 = new JPanel();
  FlowLayout flowLayout4 = new FlowLayout();
  JCheckBox jCheckBox1 = new JCheckBox();
  JCheckBox jCheckBox2 = new JCheckBox();
  private String path=null;
  public JPrintPageColumnSetDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public JPrintPageColumnSetDlg(String path,int Colcount) {
    super(JActiveDComDM.MainApplication.MainWindow, "参数设置", true);
    this.path=path;
    try {
      jbInit();
      InitDialog(Colcount);
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    main.setLayout(borderLayout1);
    boxLayout21.setAxis(BoxLayout.Y_AXIS);
    centerPanel.setLayout(boxLayout22);
    jPanel1.setLayout(gridLayout1);
    jPanel1.setBorder(border1);
    jPanel1.setPreferredSize(new Dimension(250, 300));
    gridLayout1.setRows(5);
    jPanel3.setLayout(flowLayout1);
    jPanel4.setLayout(flowLayout2);
    jPanel5.setLayout(flowLayout3);
    jTextField1.setPreferredSize(new Dimension(180, 22));
    jTextField1.setEditable(false);
    jTextField1.setText("jTextField1");
    jLabel2.setText("             固定列(前?列):");
    jfixcolSpinner.setPreferredSize(new Dimension(60, 22));
    jLabel3.setText("变动列(不包括固定列):");
    SpinnerNumberModel fixColModel = new SpinnerNumberModel(0,0,99,1);
    jfixcolSpinner.setModel(fixColModel);
    SpinnerNumberModel changeColModel = new SpinnerNumberModel(99,0,99,1);
    jchangecolSpinner.setModel(changeColModel);
    jchangecolSpinner.setPreferredSize(new Dimension(60, 22));
    jPanel2.setLayout(borderLayout2);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    flowLayout3.setAlignment(FlowLayout.LEFT);
    jTextPane1.setEditable(false);
    jTextPane1.setText("说明:\n固定列与每页数皆设置为 0 时， 系统将自动折页打印；当显示或打印页序号为 0 时， 将全部显示或逐页打印，否则只显示或打印指定的页！");
    okButton.setMnemonic('O');
    okButton.setText("确定(O)");
    okButton.addActionListener(this);
    cancelButton.setMnemonic('C');
    cancelButton.setText("取消(C)");
    cancelButton.addActionListener(this);
    main.setPreferredSize(new Dimension(400, 200));
    jPanel2.setPreferredSize(new Dimension(250, 106));
    this.setResizable(false);
    jPanel6.setLayout(flowLayout4);
    flowLayout4.setAlignment(FlowLayout.LEFT);
    jCheckBox1.setText("是否需要“承前页”、“过次页”");
    jCheckBox2.setText("对于分级数据，仅打印展开的行");
    getContentPane().add(main);
    main.add(topPanel, BorderLayout.NORTH);
    main.add(leftPanel, BorderLayout.WEST);
    main.add(rightPanel, BorderLayout.EAST);
    main.add(bottomPanel, BorderLayout.SOUTH);
    bottomPanel.add(okButton, null);
    bottomPanel.add(cancelButton, null);
    main.add(centerPanel, BorderLayout.CENTER);
    centerPanel.add(jPanel1, null);
    jPanel1.add(jPanel3, null);
    jPanel3.add(jTextField1, null);
    jPanel1.add(jPanel4, null);
    jPanel4.add(jLabel2, null);
    jPanel4.add(jfixcolSpinner, null);
    jPanel1.add(jPanel5, null);
    jPanel5.add(jLabel3, null);
    jPanel5.add(jchangecolSpinner, null);
    JPanel jPane26 = new JPanel();
    jPane26.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
    jPane26.add(jCheckBox2,null) ;
    jPanel1.add(jPanel6, null);
    jPanel1.add(jPane26, null);
    jPanel6.add(jCheckBox1, null);
    centerPanel.add(jPanel2, null);
    jPanel2.add(jPanel7, BorderLayout.WEST);
    jPanel2.add(jPanel8, BorderLayout.EAST);
    jPanel2.add(jTextPane1, BorderLayout.CENTER);

  }

  public void InitDialog(int Colcount){
//    String Fixcol   =JActiveDComDM.LocalRegistry.Get("PFixcol");
//    String Changecol=JActiveDComDM.LocalRegistry.Get("PChangecol");
//    if (Fixcol==null || "0".equals(Fixcol)) {
//      Fixcol = "1";
//    }
//    if (Changecol==null || "0".equals(Changecol)) {
//      Changecol = "100";
//    }
    String Fixcol   ="0";
    String Changecol=Integer.toString(Colcount-Integer.parseInt(Fixcol));
    int viFixcol = Integer.parseInt(Fixcol);
    int viChangecol=Integer.parseInt(Changecol);

    int page = (Colcount - viFixcol)/viChangecol;
    if ((Colcount - viFixcol)%viChangecol!=0){
      page++;
    }
    this.jTextField1.setText("本表共 "+Colcount+" 列，横向可分"+page+"页!");

    this.jfixcolSpinner.getModel().setValue(new Integer(viFixcol));
    this.jchangecolSpinner.getModel().setValue(new Integer(viChangecol));
    //取出上次参数
    String Header = JActiveDComDM.LocalRegistry.Get("Header");
    getParam();
  }
  /**
   * Invoked when an action occurs.
   *
   * @param e ActionEvent
   * @todo Implement this java.awt.event.ActionListener method
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource()==this.okButton){
      Perokbutton();
    }else if(e.getSource()==this.cancelButton){
      Percancelbutton();
    }
  }

  public void Perokbutton(){
    Fixcol = this.jfixcolSpinner.getModel().getValue().toString();
    Changecol=this.jchangecolSpinner.getModel().getValue().toString();
    Context = this.jCheckBox1.isSelected();

    JActiveDComDM.LocalRegistry.Put(path +"PFixcol",Fixcol);
    JActiveDComDM.LocalRegistry.Put(path +"PChangecol",Changecol);
    JActiveDComDM.LocalRegistry.Put(path +"IsPrePage",jCheckBox1.isSelected()?"1":"0");
    JActiveDComDM.LocalRegistry.Put(path +"IsUnwreped",jCheckBox2.isSelected()?"1":"0");
    this.OnOk();
  }

  private void getParam(){
    //固定列
    String fixcol = JActiveDComDM.LocalRegistry.Get(path + "PFixcol");
    //变动列
    String changecol = JActiveDComDM.LocalRegistry.Get(path + "PChangecol");
    //是否承前页
    String isPrepage = JActiveDComDM.LocalRegistry.Get(path + "IsPrePage");
    //是否仅打印展开的数据行
    String isUnwreped = JActiveDComDM.LocalRegistry.Get(path + "IsUnwreped");
    if(fixcol!=null){
      this.jfixcolSpinner.getModel() .setValue(new Integer(fixcol)) ;
    }
    if(changecol!=null){
      this.jchangecolSpinner.getModel() .setValue(new Integer(changecol));
    }
    jCheckBox1.setSelected("1".equals(isPrepage)) ;
    jCheckBox2.setSelected("1".equals(isUnwreped)) ;
  }

  public void Percancelbutton(){
    this.OnCancel();
  }

  public boolean isUnwraped(){
    return jCheckBox2.isSelected();
  }
}
