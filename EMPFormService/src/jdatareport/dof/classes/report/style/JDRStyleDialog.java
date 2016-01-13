package jdatareport.dof.classes.report.style;

import java.net.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import jdatareport.dof.classes.report.util.*;
import jframework.resource.classes.*;
import jfoundation.gui.window.classes.*;
import java.awt.event.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRStyleDialog
    extends JFrameDialog implements ActionListener {
    /**
     *
     */
    private JDRStyleImpl styleImpl = new JDRStyleImpl();
    /**
     *
     */
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JButton btnOk = new JButton();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JButton btnCancel = new JButton();
    private JPanel jPanel2 = new JPanel();
    private Border border1;
    Border border2;
    TitledBorder titledBorder1;
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JDRStyleViewPanel pnlStyleView = new JDRStyleViewPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTree treStyleItems = new JTree();
    /**
     *
     */
    public JDRStyleDialog(Frame owner, String title, boolean isModal) {
        super(owner, title, isModal);
        try {
            jbInit();
            initGUI();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder1 = new TitledBorder(border2, "预览");
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout2);
        btnOk.setMnemonic('O');
        btnOk.setText("确定(O)");
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        btnCancel.setMnemonic('C');
        btnCancel.setText("取消(C)");
        jPanel2.setLayout(flowLayout1);
        jPanel1.setBorder(border1);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.add(jTabbedPane1, JSplitPane.RIGHT);
        jSplitPane1.add(jScrollPane1, JSplitPane.LEFT);
        jScrollPane1.getViewport().add(treStyleItems, null);
        jTabbedPane1.add(pnlStyleView, "样式预览");
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(btnOk, null);
        jPanel2.add(btnCancel, null);
        jSplitPane1.setDividerLocation(200);
        this.setSize(550,400);
    }

    /**
     *
     * @throws java.lang.Exception
     */
    private void initGUI() throws Exception {
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);


        try {
            URL url = JXMLResource.LoadXML(this, "JDRStyleList.xml");
            this.loadStyleItems(url);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载样式配置文件失败", "系统提示", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.treStyleItems.addTreeSelectionListener(new TreeSelectionListener(){
            public void valueChanged(TreeSelectionEvent tse) {
                onTreeSelectionChanged(tse);
            }
        });
        //
        this.treStyleItems.setSelectionRow(1);
    }


    /**
     *
     * @throws java.lang.Exception
     */
    private void loadStyleItems(URL url) throws Exception {
        TreeNode rootNode = styleImpl.createTree(url);
        if (rootNode == null) {
            return;
        }
        this.treStyleItems.setModel(new DefaultTreeModel(rootNode));
    }
    /**
     *
     * @param tse
     */
    private void onTreeSelectionChanged(TreeSelectionEvent tse){
        try{
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
            if (node.getUserObject() != null && node.getUserObject()instanceof JDRStyleItemEntry) {
                JDRStyleItemEntry itemEntry = (JDRStyleItemEntry) node.getUserObject();
                JDRQueryFormatInfo fmtInfo = (JDRQueryFormatInfo) itemEntry.userObject;
                paintStylePreview(fmtInfo);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     *
     * @throws java.lang.Exception
     */
    private void paintStylePreview(JDRQueryFormatInfo fmtInfo) throws Exception {
        if(fmtInfo != null){
            pnlStyleView.initGUI(fmtInfo);
        }
    }

    /**
     *
     */
    public void onOk() {
        super.OnOk();
    }

    /**
     *
     */
    public void onCancel() {
        super.OnCancel();
    }

    /**
     *
     * @return
     */
    public JDRQueryFormatInfo getSelectedFormatInfo() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treStyleItems.getLastSelectedPathComponent();
        if (node.getUserObject() != null && node.getUserObject()instanceof JDRStyleItemEntry) {
            JDRStyleItemEntry itemEntry = (JDRStyleItemEntry) node.getUserObject();
            return (JDRQueryFormatInfo) itemEntry.userObject;
        }
        return null;
    }
    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btnOk){
            onOk();
        }else if(e.getSource()== this.btnCancel){
            onCancel();
        }
    }
}