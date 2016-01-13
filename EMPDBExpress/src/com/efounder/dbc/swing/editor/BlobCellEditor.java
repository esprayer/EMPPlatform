package com.efounder.dbc.swing.editor;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComponent;
import com.efounder.eai.ide.ExplorerIcons;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.efounder.dbc.swing.help.IHelpCallBack;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dbc.blob.*;
import javax.swing.*;

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
public class BlobCellEditor  extends DefaultCellEditor {
    protected JComponent helpcomp = null;
    public BlobCellEditor(IHelpCallBack cmd,BlobDataSet bds,Object param) {
        super(new JTextField());
        helpcomp = new blobHelptField(bds, cmd,param);
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

   public class blobHelptField extends JPanel implements ActionListener{
    BorderLayout bl = new BorderLayout();
    BlobDataSet bds=null;
    JButton bthelp = new JButton();
    Icon icon = null;
    IHelpCallBack cmd;
    Object param;
    JLabel bel=new JLabel();
    public blobHelptField(BlobDataSet bds,IHelpCallBack cmd,Object param) {
        setLayout(bl);
        this.bds=bds;
        icon = ExplorerIcons.getExplorerIcon("IxImage_Color16");
        this.cmd=cmd;
        this.param=param;
        bthelp.setIcon(icon);
        bthelp.setPreferredSize(new Dimension(icon.getIconWidth(),
                                              icon.getIconHeight()));
        add(bel, BorderLayout.CENTER);
        add(bthelp, BorderLayout.EAST);
        bthelp.addActionListener(this);
        ColumnMetaData col=(ColumnMetaData)param;
    }
    public JButton getHelpButton() {
        return bthelp;
    }

    public void actionPerformed(ActionEvent e) {
        if (cmd == null) return;
        ColumnMetaData col=(ColumnMetaData)param;
        Object o=cmd.help(new Object[]{param,bds});
        if (o != null)
            try {
                bds.setBlob(col.getColid(), o);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
}

}
