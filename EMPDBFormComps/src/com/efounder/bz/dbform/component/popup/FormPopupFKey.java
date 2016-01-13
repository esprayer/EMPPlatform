package com.efounder.bz.dbform.component.popup;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

import com.efounder.comp.*;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.component.FormFKeyField;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.help.HelpInfoContext;
import com.help.TreeTableHelpPanel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FormPopupFKey extends TreeTableHelpPanel implements PopupComponent,
    ActionListener {

    /**
     *
     */
    public FormPopupFKey() {
        initCommand();
    }

    /**
     * initCommand
     */
    private void initCommand() {
        JPanel cmdPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton   btnOK = new JButton("确定");
        btnOK.setActionCommand("OK");
        btnOK.addActionListener(this);
        btnOK.setPreferredSize(new Dimension(80, 23));
        JButton   btnCL = new JButton("取消");
        btnCL.setActionCommand("CANCEL");
        btnCL.addActionListener(this);
        btnCL.setPreferredSize(new Dimension(80, 23));
        cmdPanel.add(btnOK);
        cmdPanel.add(btnCL);
        this.add(cmdPanel, java.awt.BorderLayout.SOUTH);
    }

    /**
     *
     * @return JComponent
     */
    public JComponent getJComponent() {
        return this;
    }

    /**
     *
     * @return ListCellRenderer
     */
    public ListCellRenderer getListCellRenderer() {
        return null;
    }

    /**
     *
     */
    public void prepareData(HelpInfoContext hic) {
        initObj(this, hic);
        if (comboBox instanceof JComponentComboBox) {
            ( (JComponentComboBox) comboBox).setPopHeight(getHeight());
            ( (JComponentComboBox) comboBox).setPopWidth(getWidth());
        }
    }

    /**
     *
     */
    protected JComboBox comboBox = null;

    /**
     *
     * @param box JComboBox
     */
    public void setJComboBox(JComboBox box) {
        comboBox = box;
    }

    /**
     *
     * @param item Object
     */
    public void setSelectedItem(Object item) {
        Object sss;
    }

    /**
     *
     * @return Object
     */
    public Object getSelectedItem() {
//        Object result = getResult();
        return null;
    }

    protected ComboPopup comboPopup = null;

    /**
     *
     * @param comboPopup ComboPopup
     */
    public void setComboPopup(ComboPopup comboPopup) {
        this.comboPopup = comboPopup;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("CANCEL")) {
            onCancel();
        } else if (e.getActionCommand().equals("OK")) {
            onOK();
        }
    }

    /**
     *
     * @return String
     */
    private String getSelectValue(){
        DCTMetaData meta = null;
        if (comboBox instanceof FormFKeyField) {
            meta = ( (FormFKeyField) comboBox).getFKeyMetaData();
        }
        Object result = getResult();
        if (result != null && result instanceof EFRowSet) {
            return ( (EFRowSet) result).getString(meta.getDCT_BMCOLID(), "");
        }
        return "";
    }

    /**
     * onOK
     */
    private void onOK() {
        if (comboBox != null) {
            comboBox.setSelectedItem(getSelectValue());
        }
        if (comboPopup != null) {
            comboPopup.hide();
        }
    }

    /**
     * onCancel
     */
    private void onCancel() {
    }

    public boolean isNotUseScrollPane() {
        return true;
    }

}
