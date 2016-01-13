package com.efounder.dbc.swing.editor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.efounder.dctbuilder.data.*;
import com.efounder.eai.ide.*;
import java.lang.reflect.Constructor;

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
public class PopupEditComponent extends JPanel implements ActionListener {

    BorderLayout bl = new BorderLayout();
    JTextField text = null;
    JButton bthelp = new JButton();
    protected ColumnMetaData cmd;
    protected IWindowCellEditor wineditor;

    /**
     *
     * @param jtf JTextField
     * @param cmd ColumnMetaData
     */
    public PopupEditComponent(JTextField jtf, ColumnMetaData cmd) {
        setLayout(bl);
        text = jtf;
        Icon icon = ExplorerIcons.getExplorerIcon("help.gif");
        this.cmd = cmd;
        this.initPopupWindow();
        bthelp.setIcon(icon);
        bthelp.setPreferredSize(new Dimension(icon.getIconWidth(),
                                              icon.getIconHeight()));
        add(text, BorderLayout.CENTER);
        if (wineditor != null) {
            add(bthelp, BorderLayout.EAST);
            bthelp.addActionListener(this);
        }
    }

    /**
     * 初始化弹出窗口类
     */
    private void initPopupWindow() {
        String cls = cmd.getPopupClass();
        if (cls.trim().length() == 0) return;
        try {
            Constructor cons = null;
            wineditor = (IWindowCellEditor) Class.forName(cls).newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * getValue
     *
     * @return Object
     */
    private Object getValue() {
        if (text != null) return text.getText();
        return null;
    }


    /**
     *
     * @return JButton
     */
    public JButton getHelpButton() {
        return bthelp;
    }

    /**
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if (wineditor == null) return;
        try {
            wineditor.setValue(getValue());
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (!wineditor.isVisible()) {
                wineditor.setVisible(true);
            }
            if (wineditor.isResultOK()) {
                Object val = wineditor.getValue();
                if (val != null)
                    text.setText(val.toString());
            }
        } finally {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }


}
