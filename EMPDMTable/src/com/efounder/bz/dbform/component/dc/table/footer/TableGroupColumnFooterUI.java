package com.efounder.bz.dbform.component.dc.table.footer;

import java.awt.*;
import javax.swing.*;

import com.efounder.bz.dbform.datamodel.footer.*;
import com.swing.table.MultiSpanCellTableUI;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TableGroupColumnFooterUI extends MultiSpanCellTableUI {

    /**
     *
     */
    private TableGroupColumnFooter footer;
    private GroupTableModel    tableModel;


    /**
     * Paints the specified component appropriate for the look and feel.
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @param c the component being painted; this argument is often ignored, but might be used if the UI object is
     *   stateless and shared by multiple components
     * @todo Implement this javax.swing.plaf.ComponentUI method
     */
    public void paint(Graphics g, JComponent c) {
        //根据分组情况合并单元格
        mergeCellByGroup(g, c);
        //
        super.paint(g, c);
    }

    /**
     * mergeCellByGroup
     *
     * @param g Graphics
     * @param c JComponent
     */
    public void mergeCellByGroup(Graphics g, JComponent c) {
        if (footer == null && table instanceof TableGroupColumnFooter) {
            footer = (TableGroupColumnFooter) table;
        }
        if (footer == null)     return;
        tableModel = footer.getTableModel();
        if (tableModel == null) return;

        //所有的分组
        java.util.List grouplist = footer.getColumnGroups();
        if (grouplist == null) return;

        CellSpan cellAtt = (CellSpan) tableModel.getCellAttribute();
        ColumnFooterGroup group = null;
        for (int i = 0, n = grouplist.size(); i < n; i++) {
            group  = (ColumnFooterGroup) grouplist.get(i);
            int r1 = group.getStartRow();
            int r2 = group.getEndRow();
            int c1 = group.getStartCol();
            int c2 = group.getEndCol();
            if(r2>=r1 &&c2>=c1){
              cellAtt.combine(getArray(r1, r2), getArray(c1, c2));
              if (r1 < tableModel.getRowCount() && c1 < tableModel.getColCount()) {
                Object o=tableModel.getValueAt(r1,c1);
                if(o==null||!o.equals(group.getName()))
                tableModel.setValueAt(group.getName(), r1, c1);
              }
            }
        }
    }

    /**
     *
     * @param i1 int
     * @param i2 int
     * @return int[]
     */
    private int[] getArray(int i1, int i2) {
        int[] arr = new int[ (i2 - i1) + 1];
        int index = 0;
        for (int i = i1; i <= i2; i++) {
            arr[index] = i;
            index ++;
        }
        return arr;
    }


}
