package jreport.swing.classes.wizard.imp;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jfoundation.gui.window.classes.*;
import jreport.swing.classes.wizard.*;
import jreportwh.jdof.classes.common.util.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JHelpDOBJInfoDlg
    extends JFrameDialog implements ActionListener, MouseListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.Language");

    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JButton bnOK = new JButton();
    private JButton bnCancel = new JButton();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private FlowLayout flowLayout2 = new FlowLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private BorderLayout borderLayout2 = new BorderLayout();
    public JList lstDataList = new JList();

    public JFunctionStub FS = null;
    public JDataObjectStub DOS = null;
    public JFunctionWizardObject WizardObject = null;

    public JHelpDOBJInfoDlg(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JHelpDOBJInfoDlg() {
        this(null, "", false);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        bnOK.setFont(new java.awt.Font("Dialog", 0, 12));
        bnOK.setMnemonic('O');
        bnOK.setText(res.getString("String_22"));
        bnOK.setPreferredSize(new Dimension(80, 25));
        bnCancel.setFont(new java.awt.Font("Dialog", 0, 12));
        bnCancel.setMnemonic('C');
        bnCancel.setText(res.getString("String_25"));
        bnCancel.setPreferredSize(new Dimension(80, 25));
        jPanel1.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel1.setText(res.getString("String_27"));
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jPanel3.setLayout(borderLayout2);
        getContentPane().add(panel1);
        panel1.add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(bnOK, null);
        jPanel1.add(bnCancel, null);
        panel1.add(jPanel2, BorderLayout.NORTH);
        jPanel2.add(jLabel1, null);
        panel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(lstDataList, null);

        bnOK.addActionListener(this);
        bnCancel.addActionListener(this);
        lstDataList.addMouseListener(this);
        this.setSize(UIUtil.getAttrDialogSize());
    }

    public void InitDialog() {
        if (FS == null) {
            return;
        } else
            InitData(FS);
    }

    public Vector getSelfDojbList(JFunctionStub fs) {
        if ( fs.getId().equals("ZYCB") ) {


        } else if ( fs.getId().equals("ZYZB") ) {

        } else if ( fs.getId().equals("BCJE") ) {

        }
        return null;

    }

    void InitData(JFunctionStub fs) {
        JDataObjectStub dos;
        String DName;
        DefaultListModel dm;
        dm = new DefaultListModel();
        Vector DList = fs.getDObj();
        for (int i = 0; i < DList.size(); i++) {
            DName = (String) DList.get(i);
            dos = (JDataObjectStub) WizardObject.DataObjectList.get(DName);
            dm.insertElementAt(dos, i);
        }
        /*
        在这里加入根据已输入参数，相应增加不好初始化的取数对象
        */
//        Vector SelfDList = getSelfDObjList(FS);
//        for (int i = 0; i < SelfDList.size(); i++) {
//            dos = (JDataObjectStub) SelfDList.get(i);
//            dm.insertElementAt(dos, i+dm.getSize());
//        }

        lstDataList.setModel(dm);
    }

    /**
     * 根据数组初始化对话框
     * @param DList Vector
     */
    public void InitData(Vector DList) {
        DefaultListModel dm;
        dm = new DefaultListModel();
        JDataObjectStub dos;
        for (int i = 0; i < DList.size(); i++) {
            dos = (JDataObjectStub) DList.get(i);
            dm.insertElementAt(dos, i);
        }
        lstDataList.setModel(dm);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bnOK) {
            ProcessOK();
        }
        if (e.getSource() == bnCancel) {
            ProcessCancel();
        }
    }

    void ProcessOK() {
        DOS = getSelectDOS();
        this.OnOk();
    }

    void ProcessCancel() {
        this.OnCancel();
    }

    JDataObjectStub getSelectDOS() {
        return (JDataObjectStub) lstDataList.getSelectedValue();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            this.ProcessOK();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
