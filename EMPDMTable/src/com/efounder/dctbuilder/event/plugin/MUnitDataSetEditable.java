package com.efounder.dctbuilder.event.plugin;

import com.efounder.dbc.swing.table.*;
import com.efounder.dctbuilder.mdl.*;
import com.efounder.dctbuilder.view.*;
import com.efounder.dctbuilder.data.*;
import com.borland.dx.dataset.*;
import com.efounder.node.JNodeStub;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class MUnitDataSetEditable implements ICellEditable {

    public MUnitDataSetEditable() {
    }

    public boolean isCellEditable(Object o, int row, int col) {
        DictModel dm = (DictModel) o;
        BaseDBTableModel model = (BaseDBTableModel) ( (DictView) dm.getView()).getTable().getModel();
        String column = model.getColumn(col).getColumnName();
        String mccol = dm.getMetaData().getDctMcCol();
        if (((DictView) dm.getView()).getViewer() == null) {
            return true;
        }
        JNodeStub node = ( (DictView) dm.getView()).getViewer().getNodeStub();
        if (node == null || node.getNodeStubObject() == null) {
          return true;
        }

        String munit = node.getNodeStubObject().getString("munit", "");
        if (!"1".equals(munit)) {
            return true;
        }
        if (mccol.equals(column)){
            return false;
        }
        return true;
    }

}
