package jservice.jbof.classes.GenerQueryObject.action.ui;

import jfoundation.gui.window.classes.JFrameDialog;
import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPrintTitleSettingDialog extends JFrameDialog {
    static ResourceBundle res = ResourceBundle.getBundle("jservice.jbof.classes.GenerQueryObject.action.ui.Language");
  private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    private JButton btnOk = new JButton();
    private JButton btnCancel = new JButton();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    private JPanel jPanel4 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JTextField tfPrintTitle = new JTextField();
    private JPanel jPanel5 = new JPanel();
    private JLabel jLabel2 = new JLabel();
    private FlowLayout flowLayout2 = new FlowLayout();
    public JPrintTitleSettingDialog(Frame owner,String title,boolean isModal) {
        super(owner,title,isModal);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout2);
        btnOk.setText(res.getString("String_34"));
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOk_actionPerformed(e);
            }
        });
        btnCancel.setText(res.getString("String_35"));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCancel_actionPerformed(e);
            }
        });
        jPanel3.setLayout(verticalFlowLayout1);
        jPanel2.setLayout(verticalFlowLayout2);
        jLabel1.setText(res.getString("String_36"));
        jPanel4.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        tfPrintTitle.setPreferredSize(new Dimension(150, 22));
        tfPrintTitle.setText("");
        verticalFlowLayout2.setAlignment(VerticalFlowLayout.TOP);
        verticalFlowLayout1.setAlignment(VerticalFlowLayout.TOP);
        jLabel2.setText(res.getString("String_38"));
        jPanel5.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel4, null);
        jPanel4.add(jLabel1, null);
        jPanel4.add(tfPrintTitle, null);
        jPanel2.add(jPanel5, null);
        jPanel5.add(jLabel2, null);
        jPanel1.add(jPanel3,  BorderLayout.EAST);
        jPanel3.add(btnOk, null);
        jPanel3.add(btnCancel, null);
        this.setSize(350,120);
    }
    public void setPrintTitleArea(String printTitleArea){
        this.tfPrintTitle.setText(printTitleArea);
    }
    public String getPrintTilteArea(){
        return this.tfPrintTitle.getText();
    }
    void btnOk_actionPerformed(ActionEvent e) {
        super.OnOk();
    }
    void btnCancel_actionPerformed(ActionEvent e) {
        super.OnCancel();
    }


}
