package com.efounder.dbc.swing.render;

import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;
import com.borland.dbswing.JdbTable;
import javax.swing.table.TableCellEditor;
import com.efounder.plugin.PluginManager;
import com.core.xml.StubObject;
import java.awt.Color;
import javax.swing.JComponent;

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
public class DictTableCellRendererWrapper implements TableCellRenderer {

    private TableCellRenderer tableCellRenderer;
    private int row;
    private int column;
    private JTable table;
    private String pluginKey;
    private boolean readOnly;

    /**
     *
     */
    public DictTableCellRendererWrapper(TableCellRenderer tableCellRenderer,JTable table, String pluginKey, boolean readOnly) {
        this.tableCellRenderer = tableCellRenderer;
        this.table = table;
        this.pluginKey = pluginKey;
        this.readOnly = readOnly;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (tableCellRenderer != null) {
            this.row = row;
            this.column = column;
            if (pluginKey != null && pluginKey.trim().length() > 0) {
                java.util.List list = PluginManager.loadPlugins("CELLRENDERER_" + pluginKey, true);
                for (int i = 0; list != null && i < list.size(); i++) {
                    StubObject so = (StubObject) list.get(i);
                    ITableCellRenderer re = (ITableCellRenderer) so.getPluginObject();
                    if (re.canProcess(table, tableCellRenderer, value, row, column))
                        re.setCellRenderer(table, tableCellRenderer, value, row, column);
                }
            }
            Component comp = tableCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            try {
//                if ( (table instanceof CardTable && column % 2 == 0) || readOnly)
//                    ;
//                else if (comp != null && comp.isVisible()) {
                    boolean b = table.isCellEditable(row, column);
                    if (!b)
                        comp.setBackground(Color.lightGray);
                    else {
                    	//屏蔽掉，否则选择行的背景色就没有了
//                        comp.setBackground(table.getBackground());
                        comp.setForeground(table.getForeground());
                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return comp;
        }
        return null;
    }

    public TableCellRenderer getTableCellRenderer(){
    	return this.tableCellRenderer;
    }
}
