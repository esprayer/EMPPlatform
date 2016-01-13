package jreport.swing.classes.wizard.imp;

import jfoundation.gui.window.classes.JFrameDialog;
import java.awt.*;
import javax.swing.*;
import jreportwh.jdof.classes.common.swing.*;
import jreport.swing.classes.*;
import java.awt.event.*;
import java.io.*;
import com.f1j.swing.*;
import com.f1j.swing.SelectionChangedEvent;
import jreport.foundation.classes.*;
import jfoundation.dataobject.classes.*;
import jreportwh.jdof.classes.common.util.*;
import java.util.ResourceBundle;

/**
 * <p>Title: JHelpReportRefDlg</p>
 * <p>Description: ±¨±í·¶Î§</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JHelpReportRefDlg
    extends JFrameDialog
    implements ActionListener, SelectionChangedListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.Language");
    private JReportBook mReportBook = new JReportBook();
    private String BBZD_DATE = null;
    private String BBZD_BH = null;
    private JTextField mRefTF = new JTextField();
    private JButton mOkBtn = new JButton();
    public JHelpReportRefDlg(Frame frame,
                             String title,
                             boolean isModal,
                             JReportBook reportBook) {
        super(frame, title, isModal);
        mReportBook = reportBook;
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        mOkBtn.addActionListener(this);
        mReportBook.ReportView.addSelectionChangedListener(this);
        this.getContentPane().setLayout(borderLayout1);
        mRefTF.setPreferredSize(new Dimension(100, 22));
        mRefTF.setText("");
        mOkBtn.setMnemonic('O');
        mOkBtn.setText(res.getString("String_57"));
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        centerPanel.setBorder(BorderFactory.createEtchedBorder());
        centerPanel.setLayout(borderLayout2);
        jLabel1.setText(res.getString("String_58"));
        centerPanel.add(mReportBook.ReportView, BorderLayout.CENTER);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        topPanel.add(jLabel1, null);
        topPanel.add(mRefTF, null);
        topPanel.add(mOkBtn, null);
        //final
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(UIUtil.getStdDialogSize());
        this.CenterWindow();

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == mOkBtn) {
            dispose();
        }
    }

    public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
        try {
            String T1, T2, Text;
            T1 = mReportBook.ReportView.formatRCNr(mReportBook.ReportView.getSelStartRow(), mReportBook.ReportView.getSelStartCol(), false);
            if (mReportBook.ReportView.getSelStartRow() == mReportBook.ReportView.getSelEndRow() &&
                mReportBook.ReportView.getSelStartCol() == mReportBook.ReportView.getSelEndCol()) {
                Text = T1;
            }
            else {
                T2 = mReportBook.ReportView.formatRCNr(mReportBook.ReportView.getSelEndRow(), mReportBook.ReportView.getSelEndCol(), false);
                Text = T1 + ":" + T2;
            }
            this.mRefTF.setText(Text);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getReportRef() {
        return this.mRefTF.getText();
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel centerPanel = new JPanel();
    JPanel topPanel = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel jLabel1 = new JLabel();

}
