package jenterprise.bof.classes.AppExplorerObject.taskManager.subdlg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.*;
import jfoundation.gui.window.classes.*;

/**
 * 动作操作窗口.
 * 操作对一个动作的添加和删除.
 *
 * @version 1.0
 */
public class JManageActionDialog
    extends JFrameDialog implements ActionListener {

    public final int OPTION_OK     = 0;
    public final int OPTION_CANCEL = 1;
    public       int OPTION;

    private JSelectActionPanel   actionPanel = new JSelectActionPanel();

    public JManageActionDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        centerPanel.setLayout(verticalFlowLayout1);
        jPanel1.setLayout(flowLayout1);
        jLabel1.setPreferredSize(new Dimension(60, 16));
        jLabel1.setText("动作名称:");
        nameTextField.setPreferredSize(new Dimension(245, 22));
        cancelButton.setPreferredSize(new Dimension(80, 22));
        cancelButton.setMnemonic('C');
        cancelButton.setText("取消(C)");
        cancelButton.addActionListener(this);
        okButton.setPreferredSize(new Dimension(80, 22));
        okButton.setMnemonic('O');
        okButton.setText("确定(O)");
        okButton.addActionListener(this);
        jPanel2.setLayout(borderLayout3);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        this.getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
        centerPanel.add(jPanel2);
        centerPanel.add(actionPanel);
        this.getContentPane().add(cmdPanel, java.awt.BorderLayout.SOUTH);
        cmdPanel.add(okButton);
        cmdPanel.add(cancelButton);
        this.getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);
        jPanel2.add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jLabel1, null);
        jPanel1.add(nameTextField, null);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel centerPanel = new JPanel();
    JPanel northPanel = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel jPanel1 = new JPanel();
    JTextField nameTextField = new JTextField();
    JLabel jLabel1 = new JLabel();
    JPanel cmdPanel = new JPanel();
    JButton cancelButton = new JButton();
    JButton okButton = new JButton();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            processOk();
        } else if (e.getSource() == cancelButton) {
            processCancel();
        }
    }

    /**
     * 确定
     */
    private void processOk() {
        OPTION = OPTION_OK;
        this.dispose();
    }

    /**
     * 取消
     */
    private void processCancel() {
        this.OPTION =  this.OPTION_CANCEL;
        this.dispose();
    }

    public Object getActionObject() {
        return actionPanel.getSelectedInfo();
    }

    public void setActionObject(JActionPanel.ActionObject obj) {
        nameTextField.setText(obj.actionMC);
        actionPanel.setActionObject(obj);
    }
}
