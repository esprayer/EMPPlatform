package com.efounder.dbc.swing.treetable;

import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.comp.treetable.*;
import com.efounder.dbc.swing.editor.*;
import com.efounder.dbc.swing.render.*;
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
public  class BaseTreeTable extends JTreeTable {
    public BaseTreeTable() {
    }

    public void setModel(TreeTableModel treeTableModel) {
      super.setModel(treeTableModel);
      setSwingInfo();
    }
    public void setSwingInfo() {
        JTree jt = getTree();
        this.setShowGrid(true);
        jt.setShowsRootHandles(true);
        jt.setRootVisible(false);
        DictTreeCellRender dtcr = new DictTreeCellRender();
        jt.setCellRenderer(dtcr);
        ((DefaultTreeCellRenderer) jt.getCellRenderer()).setOpenIcon((
                ExplorerIcons.ICON_REMOVE_ALL_CUSTOM_VIEW));
        ((DefaultTreeCellRenderer) jt.getCellRenderer()).setClosedIcon((
                ExplorerIcons.ICON_ADD_CUSTOM_VIEW));
        ((DefaultTreeCellRenderer) jt.getCellRenderer()).setLeafIcon((
                ExplorerIcons.ICON_CUSTOM_VIEW));
        setDefaultRenderer(Boolean.class, new BoolCellRender());
        setDefaultEditor(Boolean.class, new BoolCellEditor());
    }

}
