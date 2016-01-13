package com.efounder.dbc.swing.tree;

import javax.swing.tree.*;

import com.efounder.builder.base.data.*;
import com.efounder.datamanager.pub.meta.MetaDataConstants;
import com.efounder.dbc.swing.context.*;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.help.util.ESPHelpUtil;

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
public class EFDataSetTreeModel extends BaseTreeModel {
    EFDataSet dataSet;
      protected EFDataSetTreeModel(TreeNode root) {
          super(root);
      }

      public void setDataSet(EFDataSet ds){
          this.dataSet = ds;
      }

      public EFDataSet getDataSet(){
          return this.dataSet;
      }
      /**
       *
       * @param treeModel EFDataSetTreeModel
       * @param dctMetaData DCTMetaData
       * @param dataSet EFDataSet
       */
      public static void buildeDataSetTreeModel(EFDataSetTreeModel treeModel,
                                                DCTMetaData dctMetaData,
                                                EFDataSet dataSet) {
          TreeInfoContext exphic = ESPHelpUtil.getTreeInfoFormDO(dctMetaData);
          exphic.setDataObject(dataSet);
          DataSetTreeNode root = TreeUtils.createRootNode(exphic.getCaption());
          treeModel.setRoot(root);
          treeModel.dataSet=(EFDataSet)exphic.getDataObject();
          treeModel.CreateTree(exphic);
      }

      public static EFDataSetTreeModel  getInstance(TreeInfoContext tc){
          DataSetTreeNode root = TreeUtils.createRootNode(tc.getCaption());

          EFDataSetTreeModel model = new EFDataSetTreeModel(root);
          model.dataSet=(EFDataSet)tc.getDataObject();
          model.CreateTree(tc);
          return model;

      }
      public void setCurrentTreeNode(EFRowSet row) {
          DataSetTreeNode node = TreeUtils.createTreeNode(row, context, null);
          if (node != null) {
              node.setUserObject(row);
              putTreeNode(node.getDctBH(), node);
              addChildNode(node);
          }
      }

      protected void CreateTree() {
          putTreeNode(MetaDataConstants.DCT_ROOT, (DataSetTreeNode) root);
          int count=dataSet.getRowCount();
          for (int i = 0; i <count; i++) {
              setCurrentTreeNode(dataSet.getRowSet(i));
          }
      }

  }
