package com.efounder.dbc.swing.tree;

import javax.swing.tree.*;

import com.core.xml.*;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.datamanager.pub.meta.MetaDataConstants;
import com.efounder.dbc.swing.context.*;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import java.util.*;

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
public class ListDataTreeModel extends BaseTreeModel {
    java.util.List dataList=null;
    protected ListDataTreeModel(TreeNode root) {
        super(root);
    }
    public static ListDataTreeModel  getInstance(TreeInfoContext tc){
        DataSetTreeNode root = TreeUtils.createRootNode(tc.getCaption());
        ListDataTreeModel model = new ListDataTreeModel(root);
        model.dataList=(List)tc.getDataObject();
        model.CreateTree(tc);
        return model;
    }

    protected void CreateTree() {
        putTreeNode(MetaDataConstants.DCT_ROOT, (DataSetTreeNode) root);
        for (int i = 0; i < dataList.size(); i++) {
            setCurrentTreeNode( dataList.get(i));
        }
    }

    public void setCurrentTreeNode(Object so) {
        DataSetTreeNode node = null;
        if(so instanceof StubObject)
        	node=TreeUtils.createTreeNode((StubObject)so, context,null);
        if(so instanceof EFRowSet)
        	node=TreeUtils.createTreeNode((EFRowSet)so, context,null);	
        if (node != null) {
            node.setUserObject(so);
            putTreeNode(node.getDctBH(),node);
            addChildNode(node);
        }
    }
}
