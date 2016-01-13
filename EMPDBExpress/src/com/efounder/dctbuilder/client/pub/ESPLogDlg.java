package com.efounder.dctbuilder.client.pub;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.efounder.pfc.dialog.*;

/**
 * <p>Title: 消息日志显示对话框</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Pansoft</p>
 * @author gengeng
 * @version 1.0
 */
public class ESPLogDlg
    extends JPDialog implements ActionListener {

    BorderLayout borderLayout1 = new BorderLayout();
    JTabbedPane tpanelCenter = new JTabbedPane();
    JScrollPane panelOne = new JScrollPane();
    JPanel panelNorth = new JPanel();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel panelSouth = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton btnExport = new JButton();
    JButton btnQuit = new JButton();
    JButton btnOK = new JButton();
    JEditorPane editPane = new JEditorPane();
    BorderLayout borderLayout2 = new BorderLayout();

    public ESPLogDlg(Frame frame, String title, boolean modal) {
    	this(frame, title, modal, 600, 400);
    }
    
    public ESPLogDlg(Frame frame, String title, boolean modal, int width, int height) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
            setSize(width, height);
            CenterWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        panelSouth.setLayout(flowLayout1);
        btnExport.setPreferredSize(new Dimension(80, 23));
        btnExport.setMargin(new Insets(2, 2, 2, 2));
        btnExport.setMnemonic('E');
        btnExport.setText("转出(E)");
        btnExport.addActionListener(this);
        btnQuit.setPreferredSize(new Dimension(80, 23));
        btnQuit.setMargin(new Insets(2, 2, 2, 2));
        btnQuit.setMnemonic('C');
        btnQuit.setText("取消(C)");
        btnQuit.addActionListener(this);
        btnOK.setPreferredSize(new Dimension(80, 23));
        btnOK.setMargin(new Insets(2, 2, 2, 2));
        btnOK.setMnemonic('O');
        btnOK.setText("确定(O)");
        btnOK.addActionListener(this);
        editPane.setAlignmentX( (float) 0.0);
        editPane.setAlignmentY( (float) 0.0);
        this.getContentPane().add(tpanelCenter, java.awt.BorderLayout.CENTER);
        tpanelCenter.add(panelOne, "消息日志");
        this.getContentPane().add(panelNorth, java.awt.BorderLayout.NORTH);
        this.getContentPane().add(panelSouth, java.awt.BorderLayout.SOUTH);
        panelSouth.add(btnOK);
        panelSouth.add(btnQuit);
        panelSouth.add(btnExport);
        this.getContentPane().add(jPanel2, java.awt.BorderLayout.EAST);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);
        panelOne.getViewport().add(editPane, java.awt.BorderLayout.CENTER);
        editPane.setAutoscrolls(true);
    }

    /**
     * 追加消息
     *
     * @param mess String
     */
    public void appendMessage(String mess) {
        String text = editPane.getText();
        StringBuffer sb = new StringBuffer(text);
        if (text != null) {
            sb.append(mess == null ? "" : mess);
            editPane.setText("");
            editPane.setText(sb.toString());
        }
    }

    /**
     *
     * @return JEditorPane
     */
    public JEditorPane getEditPane() {
        return editPane;
    }

    /**
     * 一次性设置消息
     *
     * @param mess String
     */
    public void setMessage(String mess) {
        editPane.setText("");
        editPane.setText(mess);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (e.getSource() == btnExport) {
                onExportMessage();
            } else if (e.getSource() == btnQuit) {
                super.OnCancel();
            } else if (e.getSource() == btnOK) {
                super.OnOk();
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * 导入日志信息
     */
    private void onExportMessage() {
        JFileChooser fDialog = new JFileChooser(); // 文件选择器
        TxtFileFilter txtFilter1 = new TxtFileFilter(); // 文件过滤器
        fDialog.addChoosableFileFilter(txtFilter1);
        int result = fDialog.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String fname = fDialog.getName(fDialog.getSelectedFile());
            String fpath = fDialog.getSelectedFile().getPath();
            if (isFull(fpath)) {
                String suffix = ".txt";
                if (!suffix.equals(fpath.substring(fpath.length() -
                    suffix.length(), fpath.length()))) {
                    fpath = fpath + suffix;
                }
                try {
                    writeTxtFile(fpath, editPane.getText());
                    JOptionPane.showMessageDialog(this, "文件导出成功");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        }
    }

    private boolean isFull(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * writeTxtFile
     *
     * @param fpath String
     * @param string String
     * @param object Object
     */
    private void writeTxtFile(String fpath, String string) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fpath, false));
        bw.write(string);
        bw.flush();
        bw.close();
    }

    /**
     * 退出
     */
    private void onQuit() {
        super.OnCancel();
    }

    class TxtFileFilter
        extends FileFilter {
        public String getDescription() {
            return "*.txt(" + "文本文件" + ")";
        }

        public boolean accept(File file) {
            String name = file.getName();
            return name.toLowerCase().endsWith(".txt");
        }
    }

}
