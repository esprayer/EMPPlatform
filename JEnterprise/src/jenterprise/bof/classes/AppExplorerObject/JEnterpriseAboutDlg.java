package jenterprise.bof.classes.AppExplorerObject;

import java.awt.*;
import javax.swing.*;
import jfoundation.gui.window.classes.*;
import jframework.resource.classes.JXMLResource;
import com.borland.jbcl.layout.*;
import jbof.application.classes.JBOFApplication;
import jframework.foundation.classes.JActiveDComDM;
import java.awt.event.*;
import jfoundation.dataobject.classes.JParamObject;
import com.eai.resources.TImages;
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
public class JEnterpriseAboutDlg extends JFrameDialog {
  static ResourceBundle mPublicStrings = null;
  static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JButton bnOK = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel lbTitle = new JLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JLabel lbSoftMC = new JLabel();
  JLabel lbSoftBB = new JLabel();
  JLabel lbSoftDataBB = new JLabel();
  JBOFApplication App;
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JLabel jLabel2 = new JLabel();
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JEnterpriseAboutDlg(Frame frame, String title, boolean modal) {
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
  public JEnterpriseAboutDlg() {
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
    bnOK.setPreferredSize(new Dimension(82, 23));
    bnOK.setMnemonic('O');
    bnOK.setText(res.getString("String_4"));
    bnOK.addActionListener(new JEnterpriseAboutDlg_bnOK_actionAdapter(this));
    jPanel2.setMaximumSize(new Dimension(32767, 32767));
    jPanel2.setPreferredSize(new Dimension(84, 37));
    jPanel2.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(8);
    flowLayout1.setVgap(7);
    jPanel1.setLayout(borderLayout2);
    jPanel3.setLayout(verticalFlowLayout1);
    verticalFlowLayout1.setHgap(5);
    verticalFlowLayout1.setHorizontalFill(true);
    verticalFlowLayout1.setVerticalFill(true);
    jLabel4.setText(res.getString("String_5"));
    jLabel5.setText(res.getString("String_6"));
    jLabel6.setRequestFocusEnabled(true);
    jLabel6.setToolTipText("");
    jLabel6.setText(res.getString("String_8"));
    jLabel1.setToolTipText("");
    jLabel1.setText(res.getString("String_10"));
    jLabel3.setText(res.getString("String_11"));
    borderLayout1.setVgap(0);
    jPanel3.setVerifyInputWhenFocusTarget(true);
    jPanel4.setMaximumSize(new Dimension(32767, 32767));
    jPanel4.setPreferredSize(new Dimension(260, 10));
    jLabel2.setText("icon");
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.NORTH);
    panel1.add(jPanel2,  BorderLayout.SOUTH);
    jPanel2.add(bnOK, null);
    jPanel1.add(lbTitle, BorderLayout.CENTER);
    panel1.add(jPanel3,  BorderLayout.EAST);
    jPanel3.add(lbSoftMC, null);
    jPanel3.add(lbSoftBB, null);
    jPanel3.add(lbSoftDataBB, null);
    jPanel3.add(jLabel1, null);
    jPanel3.add(jLabel3, null);
    jPanel3.add(jLabel4, null);
    jPanel3.add(jLabel5, null);
    jPanel3.add(jLabel6, null);
    panel1.add(jPanel4, BorderLayout.WEST);
    jPanel4.add(jLabel2, null);
    InitDlg();
    GUIPrepare();
//    this.setResizable(false);
    this.setMinimumSize(new Dimension(351, 208));
    jLabel2.setText("");
    bnOK.setMargin(new Insets(2, 2, 2, 2));
  }

  public void GUIPrepare(){
    jLabel2.setIcon(TImages.getIcon("About_DL"));
    bnOK.setIcon(TImages.getIcon("SYS_CMD_OK"));
    }

  public void setCustomObject(Object CO) {
    App = (JBOFApplication)CO;
  }
//  public void Show() {
//    if ( App != null ) {
//      this.set
//    }
//    show();
//  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void InitDlg() {
    ImageIcon II = JXMLResource.LoadImageIcon(this,"FMISAboutTitle.jpg");
    if ( II == null ){
      II = JXMLResource.LoadImageIcon(this,"AboutTitle.jpg");
    }

    lbTitle.setIcon(II);

    //标题
    this.setTitle(getResouce("SOFT_ABOUT_TITLE", "String_17"));

    InitLabel();
  }

  private String getResouce(String pKey1,String pKey2){
    String pRES = null;
    try{
      if ( mPublicStrings == null){
        mPublicStrings = ResourceBundle.getBundle(
            "fmis.publicresource.Language");
      }
      pRES = mPublicStrings.getString(pKey1);
    }
    catch(Exception E){

    }

    if ( pRES == null){
      pRES = res.getString(pKey2);
    }

    return pRES;
  }

  void InitLabel() {
    String Text;JParamObject PO;
    PO = (JParamObject)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject","CreateParamObjectDOM");
    Text = PO.GetValueByEnvName("CW_SOFTMC");
    this.lbSoftMC.setText(getResouce("SOFT_ABOUT_PRODUCT","String_21"));
    lbSoftBB.setRequestFocusEnabled(true);
    this.lbSoftBB.setText(getResouce("SOFT_ABOUT_VERSION","String_23"));
    // 加入更新日期 add by hufeng 2007.10.31
    Text = res.getString("String_24") + PO.GetValueByEnvName("Version");
    this.lbSoftDataBB.setText(Text);

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void bnOK_actionPerformed(ActionEvent e) {
    this.OnOk();
  }
}
//------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//------------------------------------------------------------------------------------------------
class JEnterpriseAboutDlg_bnOK_actionAdapter implements java.awt.event.ActionListener {
  JEnterpriseAboutDlg adaptee;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  JEnterpriseAboutDlg_bnOK_actionAdapter(JEnterpriseAboutDlg adaptee) {
    this.adaptee = adaptee;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void actionPerformed(ActionEvent e) {
    adaptee.bnOK_actionPerformed(e);
  }
}
