package com.efounder.dbc.swing.editor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.efounder.dbc.swing.help.*;
import com.efounder.eai.ide.*;

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
public class SelfHelpCellEditor  extends DefaultCellEditor {
    protected JComponent helpcomp = null;
    public SelfHelpCellEditor(IHelpCallBack cmd,Object param) {
        super(new JTextField());
        helpcomp = new selfhelpTextField((JTextField) editorComponent, cmd,param);
    }

    public Component getComponent() {
        return helpcomp;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        return helpcomp;
    }

   public class selfhelpTextField extends JPanel implements ActionListener{
    BorderLayout bl = new BorderLayout();
    JTextField text = null;
    JButton bthelp = new JButton();
    Icon icon = null;
    IHelpCallBack cmd;
    Object param;
    public selfhelpTextField(JTextField jtf,IHelpCallBack cmd,Object param) {
        setLayout(bl);
        text = jtf;
        icon = ExplorerIcons.getExplorerIcon("help.gif");
        this.cmd=cmd;
        this.param=param;
        bthelp.setIcon(icon);
        bthelp.setPreferredSize(new Dimension(icon.getIconWidth(),
                                              icon.getIconHeight()));
        add(text, BorderLayout.CENTER);
        add(bthelp, BorderLayout.EAST);
        bthelp.addActionListener(this);
    }
    public JButton getHelpButton() {
        return bthelp;
    }

    public void actionPerformed(ActionEvent e) {
        if (cmd == null) return;
        String[] bh = (String[]) cmd.help(param);
        if (bh != null && bh.length > 0 && bh[0] != null && bh[0].length() > 0) {
            text.setText(bh[0]);
        }
    }
}
}
