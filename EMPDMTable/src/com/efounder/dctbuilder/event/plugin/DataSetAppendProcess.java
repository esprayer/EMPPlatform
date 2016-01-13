package com.efounder.dctbuilder.event.plugin;

import javax.swing.tree.*;

import com.borland.dx.dataset.*;
import com.efounder.dbc.*;
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
public class DataSetAppendProcess extends DataLoaderCltAdapter {

    public DataSetAppendProcess() {
    }

//    public void afterDataLoad(DictModel dm, DctContext context) throws Exception {
//       DataSet ds=(DataSet)context.getLosableValue("DataSet",null);
//       if(ds==null)return;
//       context.setLosableValue("DataSet",null);
//       DictTreeTableView view=(DictTreeTableView)dm.getView();
//       TreePath path=view.getTree().getSelectionPath();
//       DatasetTreeModel model=(DatasetTreeModel) view.getTree().getModel();
//       DataSetTreeNode node=(DataSetTreeNode) path.getLastPathComponent();
//       while(ds.inBounds()){
//           DataSetTreeNode curnode=TreeUtils.createTreeNode(ds,dm.getMetaData());
//           model.putTreeNode(curnode.getDctBH(),curnode);
//           node.add(curnode);
//           ds.next();
//       }
//       model.nodeStructureChanged(node);
//   }
//
//   public boolean canProcess(DictModel o1, DctContext context) throws Exception {
//        return context.isGradeQuery()&&!context.isFirstLoad()&&(o1.getView() instanceof DictTreeTableView);
//    }

}
