package com.efounder.dctbuilder.event.plugin;

import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.efounder.dbc.swing.tree.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.event.*;
import com.efounder.dctbuilder.mdl.*;
import com.efounder.dctbuilder.view.*;

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
public class EventUpdateTreeNode implements IClientEvent{
    public EventUpdateTreeNode() {
    }
    public void changed(DictModel dm,StubObject param) {
        Column col = (Column) param.getObject("column", null);
        String bmcol, mccol;
        mccol = dm.getMetaData().getDctMcCol();
        bmcol = dm.getMetaData().getDctBmCol();
        DictTreeTableView dv = (DictTreeTableView) dm.getView();
        String columnname = col.getColumnName();
        if (columnname.equals(bmcol))
            updateBM(dm, dv);
        if (columnname.equals(mccol))
            updateMC(dm, dv);
    }

    protected void updateBM(DictModel dm,  DictTreeTableView dv) {
        String bmcol;
        bmcol = dm.getMetaData().getDctBmCol();
        String bh = dm.getDataSet().getString(bmcol);
        String oldbh = dm.getDataSet().getString("ZD_BH_OLD");
        DatasetTreeModel dtm = (DatasetTreeModel) dv.getTree().getModel();
        DataSetTreeNode dst = dtm.getTreeNode(oldbh);
        if (dst == null)
            return;
        dm.getDataSet().setString("ZD_BH_OLD",bh);
        dtm.putTreeNode(oldbh, null);
        dst.setDctBH(bh);
        dst.setCurBH(bh);
        String pt=dst.getParentBH();
        if(dst.getIntJs() > 1 && bh.length()>pt.length())
          dst.setCurBH(bh.substring(pt.length()));
        dtm.putTreeNode(bh, dst);
        dtm.nodeStructureChanged(dst);
    }
    protected void updateMC(DictModel dm,  DictTreeTableView dv) {
        String bmcol, mccol;
        mccol = dm.getMetaData().getDctMcCol();
        bmcol = dm.getMetaData().getDctBmCol();
        String bh = dm.getDataSet().getString(bmcol);
        String mc = dm.getDataSet().getString(mccol);
        DatasetTreeModel dtm = (DatasetTreeModel) dv.getTree().getModel();
        DataSetTreeNode dst = dtm.getTreeNode(bh);
        if (dst == null)
            return;
        dst.setDctMc(mc);
        dtm.nodeStructureChanged(dst);
    }

    public boolean canProcess(DictModel dm, DctContext context) throws
            Exception {
        if (dm.getView() != null && dm.getView() instanceof DictTreeTableView)
            return true;
        return false;
    }

}
