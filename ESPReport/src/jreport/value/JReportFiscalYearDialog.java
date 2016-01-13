package jreport.value;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import com.efounder.eai.data.JParamObject;

import jfoundation.gui.window.classes.*;
import java.util.ResourceBundle;

/**
 * <p>Title: 启用新会计年度窗口</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Pansoft</p>
 * @author gengeng
 * @version 1.0
 */
public class JReportFiscalYearDialog
    extends JFrameDialog implements ActionListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.value.Language");

    private static final int OPTION_OK     = 0;
    private static final int OPTION_CANCEL = 1;
    private static       int OPTION;

    public JReportFiscalYearDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            initYear();
            setSize(280,140);
            CenterWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 会计年度候选人初始化。
     * 如果存在财务日期，则用财务年，否则用当前自然年。
     */
    private void initYear() {
        String yearCandidate = "";
        JParamObject PO = JParamObject.Create();
        String financeDate = PO.GetValueByEnvName("ZW_LSCWRQ", "");
        String logoninDate = PO.GetValueByEnvName("LoginDate", "");
        if ("".equals(financeDate)) {
            yearCandidate = logoninDate.substring(0, 4);
        } else {
            yearCandidate = financeDate.substring(0, 4);
        }
        int startYear = 1900;
        while (startYear < 10000) {
            model.addElement(String.valueOf(startYear));
            startYear++;
        }
        model.setSelectedItem(yearCandidate);
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
            if (e.getSource() == this.btnOK) {
                processOK();
            } else if (e.getSource() == this.btnCancel) {
                processCancel();
            }

        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void processOK() {
        setOption(OPTION_OK);
        super.OnOk();
    }

    private void processCancel() {
        setOption(OPTION_CANCEL);
        super.OnCancel();
    }

    private void setOption(int option) {
        OPTION = option;
    }

    public int getOption() {
        return OPTION;
    }

    public String getFiscalYear() {
        return model.getSelectedItem().toString();
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        btnOK.setPreferredSize(new Dimension(80, 25));
        btnOK.setMnemonic('O');
        btnOK.setText(res.getString("String_60"));
        btnCancel.setPreferredSize(new Dimension(80, 25));
        btnCancel.setMnemonic('C');
        btnCancel.setText(res.getString("String_62"));
        jLabel1.setText(res.getString("String_63"));
        cbYear.setPreferredSize(new Dimension(100, 25));
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        verticalFlowLayout1.setAlignment(VerticalFlowLayout.MIDDLE);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel3);
        jPanel3.add(jLabel1);
        jPanel3.add(cbYear);
        jPanel2.setLayout(flowLayout2);
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setLayout(verticalFlowLayout1);
        this.getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(btnOK);
        jPanel2.add(btnCancel);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        cbYear.setModel(model);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JButton btnOK = new JButton();
    JButton btnCancel = new JButton();
    JLabel jLabel1 = new JLabel();
    JComboBox cbYear = new JComboBox();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel jPanel3 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    DefaultComboBoxModel model = new DefaultComboBoxModel();
}
