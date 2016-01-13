package com.efounder.bz.dbform.component.dc.tree;

import javax.swing.*;
import com.efounder.bz.dbform.component.FormContainer;
import com.efounder.bz.dbform.component.FormComponent;
import com.efounder.bz.dbform.component.dc.DCTDataSetContainer;
import com.efounder.bz.dbform.datamodel.DCTreeModel;
import com.efounder.bz.dbform.datamodel.tree.DataSetTreeNode;

import javax.swing.tree.TreeModel;
import org.jdesktop.swingx.JXTree;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.meta.dctmodel.DCTMetaData;

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
public class DMTree extends JXTree implements FormComponent,DCTDataSetContainer {
  /**
   *
   */
  public DMTree() {
  }

  /**
   *
   * @return FormContainer
   */
  public FormContainer getFormContainer() {
    return null;
  }

  /**
   *
   * @return JComponent
   */
  public JComponent getJComponent() {
    return this;
  }
  /**
   *
   * @param treeModel TreeModel
   */
  public void setModel(TreeModel treeModel) {
    if ( treeModel == null ) treeModel = JTree.getDefaultTreeModel();
    super.setModel(treeModel);
  }


  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet() {
    DCTreeModel treeModel = (DCTreeModel)this.getModel();
    return treeModel.getDataSet();
  }
  /**
   *
   * @return ESPRowSet
   */
  public ESPRowSet getSelectRowSet() {
    DataSetTreeNode selectObject = (DataSetTreeNode)this.getSelectionPath().getLastPathComponent();
    if ( selectObject != null )
      return selectObject.getRowSet();
    return null;
  }
  /**
   *
   */
  protected DCTMetaData dctMetaData = null;
  /**
   *
   * @return DCTMetaData
   */
  public DCTMetaData getDCTMetaData() {
    return dctMetaData;
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   */
  public void setDCTMetaData(DCTMetaData dctMetaData) {
    this.dctMetaData = dctMetaData;
  }
}
