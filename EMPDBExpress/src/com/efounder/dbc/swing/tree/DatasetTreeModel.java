package com.efounder.dbc.swing.tree;

import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.efounder.datamanager.pub.meta.MetaDataConstants;
import com.efounder.db.*;
import com.efounder.dbc.*;
import com.efounder.dbc.swing.context.*;
import com.efounder.dbc.swing.*;
import com.efounder.dctbuilder.data.DictMetaDataEx;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DatasetTreeModel
    extends BaseTreeModel {

protected ClientDataSet dataSet;
protected DatasetTreeModel(TreeNode root) {
    super(root);
}
public ClientDataSet getDataSet(){
    return dataSet;
}
public static DatasetTreeModel getInstance(TreeInfoContext tc) {
    DataSetTreeNode root = TreeUtils.createRootNode(tc.getCaption());
    DatasetTreeModel model = new DatasetTreeModel(root);
    if(tc.getDataSet()!=null)model.dataSet=tc.getDataSet();
    model.CreateTree(tc);
    return model;
}

public static DatasetTreeModel  getInstance(DictMetaDataEx dmde,ClientDataSet cds){
    TreeInfoContext context = new TreeInfoContext();
    DataSetTreeNode    root = TreeUtils.createRootNode(dmde.getDctMc(), dmde.isControlOne());
    context.setBHColumn(dmde.getDctBmCol());
    context.setMCColumn(dmde.getDctMcCol());
    context.setJSColumn(dmde.getDctJsCol());
    context.setMXColumn(dmde.getDctMxCol());
    context.setBMStru(dmde.getDctBMStru());
    //设置父列，add by gengeng 2009-9-9
    context.setParentColumn(dmde.getDctParentCol());
    context.setKZColumn(dmde.getDctControlCol());
    DatasetTreeModel model = new DatasetTreeModel(root);
    model.dataSet = cds;
    model.CreateTree(context);
    return model;
}

public static DatasetTreeModel  getInstance(DictDataSet  dds){
    TreeInfoContext context = new TreeInfoContext();
    DataSetTreeNode root = TreeUtils.createRootNode(dds.getDictMc());
    root.setDctMc(dds.getDictMc());
    context.setBHColumn(dds.getBhCol());
    context.setMCColumn(dds.getMcCol());
    context.setJSColumn(dds.getJsCol());
    context.setMXColumn(dds.getMxCol());
    context.setBMStru(dds.getBmStru());
    DatasetTreeModel model = new DatasetTreeModel(root);
    context.setDataSet(dds);
    model.dataSet=dds;
    model.CreateTree(context);
    return model;
}
protected void CreateTree() {
//    if (!dataSet.isOpen())
//        dataSet.setActiveAgent(true);
//    putTreeNode(MetaDataConstants.DCT_ROOT, (DataSetTreeNode) root);
//    dataSet.first();
//    while (dataSet.inBounds()) {
//        DataSetTreeNode node = TreeUtils.createTreeNode(dataSet, context,null);
//        if (node != null) {
//            putTreeNode(node.getDctBH(), node);
//            addChildNode(node);
//        }
//        dataSet.next();
//    }
    dataSet.first();
}


}
