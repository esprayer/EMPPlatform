package com.efounder.dbc.swing.editor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dbc.swing.pub.TimeDialog;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.mdl.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TimeTextField extends JPanel implements ActionListener {
    JTextField textField = null;
    JButton bthelp = new JButton();
    Icon icon = null;
    ColumnMetaData cmd;
    DictModel model;

    /**
     *
     * @param jtf JTextField
     * @param cmd ColumnMetaData
     */
    public TimeTextField(JTextField textField,ColumnMetaData cmd) {
        this.cmd = cmd;
        this.textField = textField;
        icon = ExplorerIcons.getExplorerIcon("help.gif");
        bthelp.setIcon(icon);
        bthelp.setPreferredSize(new Dimension(icon.getIconWidth(),
                                              icon.getIconHeight()));
        setLayout(new BorderLayout());
        add(textField, BorderLayout.CENTER);
        add(bthelp, BorderLayout.EAST);
        bthelp.addActionListener(this);
    }

    /**
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String format = cmd.getString("COL_VIEW","");
        TimeDialog dlg = new TimeDialog(EAI.EA.getMainWindow(), "日期选择", true, format);
        dlg.setValue(textField.getText());
        dlg.setVisible(true);
        dlg.pack();
        dlg.CenterWindow();
        textField.setText((String)dlg.getValue());
    }

    /**
     *
     * @return JButton
     */
    public JButton getHelpButton() {
        return bthelp;
    }
}
