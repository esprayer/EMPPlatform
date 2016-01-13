package com.swing.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.table.*;
import javax.swing.border.*;
import com.jidesoft.swing.JideScrollPane;

public class Table extends JTable{

    public Table() {
        super();
    }
    public Table(TableModel dm) {
        super(dm, null, null);
   }

    protected void configureEnclosingScrollPane() {
        /**
         * setColumnHeaderView
         */
        super.configureEnclosingScrollPane();

        Container p = getParent();
        if (p instanceof JViewport) {
            Container gp = p.getParent();
            if (gp instanceof JideScrollPane) {
                JideScrollPane scrollPane = (JideScrollPane)gp;
                // Make certain we are the viewPort's view and not, for
                // example, the rowHeaderView of the scrollPane -
                // an implementor of fixed columns might do this.
                JViewport viewport = scrollPane.getViewport();
                if (viewport == null || viewport.getView() != this) {
                    return;
                }

                /**
                 * ColumnHeader
                 */
                TableColumnModel cm = this.getColumnModel();
                ColumnGroup g_name = new ColumnGroup("09年上半年");//ColumnHeader分组
                g_name.add(cm.getColumn(0));
                g_name.add(cm.getColumn(1));
                ColumnGroup g_lang = new ColumnGroup("09年下半年");
                g_lang.add(cm.getColumn(2));
                ColumnGroup g_other = new ColumnGroup("第四季度");
                g_other.add(cm.getColumn(3));
                g_other.add(cm.getColumn(4));
                g_lang.add(g_other);
                GroupableTableHeader header = (GroupableTableHeader)this.getTableHeader();
                header.addColumnGroup(g_name);
                header.addColumnGroup(g_lang);
                TableCellRenderer renderer =  new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int column) {
                        JTableHeader header =new JTableHeader();
                        if (header != null) {
                            setForeground(header.getForeground());
                            setBackground(header.getBackground());
                            setFont(header.getFont());
                        }
                        setHorizontalAlignment(JLabel.CENTER);
                        setText((value == null) ? "" : value.toString());
                        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                        return this;
                    }
                };
                TableColumnModel model = this.getColumnModel();
                for (int i=0;i<model.getColumnCount();i++) {
                    model.getColumn(i).setHeaderRenderer(renderer);
                }
                this.getTableHeader().setUI(new GroupableTableHeaderUI());
                scrollPane.setColumnHeaderView(this.getTableHeader());
                /**
                 * end ColumnHeader
                 */


                /**
                 * ColumnFooter
                 */
                DefaultTableModel dm1 = new DefaultTableModel();
                dm1.setDataVector(new Object[50][],
                                  new Object[]{"第一季度","第二季度","第三季度","10月份","12月份"});
                TableColumnModel cm1 = this.getColumnModel();
                GroupableTableHeader footer=new GroupableTableHeader(cm1);
                ColumnGroup g_name1 = new ColumnGroup("09年上半年");   //ColumnFooter分组
                g_name1.add(cm1.getColumn(0));
                g_name1.add(cm1.getColumn(1));
                ColumnGroup g_lang1 = new ColumnGroup("09年下半年");
                g_lang1.add(cm1.getColumn(2));
                ColumnGroup g_other1 = new ColumnGroup("第四季度");

                g_other1.add(cm1.getColumn(3));
                g_other1.add(cm1.getColumn(4));
                g_lang1.add(g_other1);
                footer.addColumnGroup(g_name1);
                footer.addColumnGroup(g_lang1);
                scrollPane.setColumnFooterView(footer);
                /**
                 * end ColumnFooter
                 */

                Border border = scrollPane.getBorder();
                if (border == null || border instanceof UIResource) {
                    scrollPane.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
                }
            }
        }
    }

}
