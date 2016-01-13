package com.efounder.bz.dbform.component.dc.combobox;

import com.efounder.bz.dbform.datamodel.DCTreeModel;
import com.efounder.bz.dbform.bizmodel.MDModel;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.bz.dbform.component.dc.tree.DMTree;

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
public class DynTreeUtils {
  /**
   *
   */
  protected DynTreeUtils() {
  }
  /**
   *
   * @param mdModel MDModel
   * @param dmColComp DMColComponent
   * @return DCTreeModel
   */
  public static DCTreeModel createDCTreeModel(MDModel mdModel,DMColComponent dmColComp) {
    DCTreeModel treeModel = new DCTreeModel();
    treeModel.setDataSetID(dmColComp.getViewDataSetID());
    treeModel.setDataSetComponent(mdModel);
//    treeModel.setDataSetID(dmColComp.getViewDataSetID());
    return treeModel;
  }
  public static DMTree createDMTree(DCTreeModel treeModel) {
    DMTree tree = new DMTree();
    tree.setModel(treeModel);
    return tree;
  }
}
