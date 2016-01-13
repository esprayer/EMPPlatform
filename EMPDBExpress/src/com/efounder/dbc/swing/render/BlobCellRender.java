package com.efounder.dbc.swing.render;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;
import javax.swing.Icon;
import com.efounder.eai.ide.ExplorerIcons;

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
public class BlobCellRender extends JLabel implements TableCellRenderer {
    public BlobCellRender() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
          Icon icon1 = ExplorerIcons.getExplorerIcon("IxImage_Color16.gif");
          this.setIcon(icon1);
        return this;
    }
}
