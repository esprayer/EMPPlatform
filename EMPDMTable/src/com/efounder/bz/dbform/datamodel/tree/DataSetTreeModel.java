package com.efounder.bz.dbform.datamodel.tree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import com.efounder.builder.base.data.DataSetListener;
import com.efounder.bz.dbform.bizmodel.MDModel;
import com.efounder.bz.dbform.datamodel.DataComponent;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.builder.base.data.DataSetEvent;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.dctmodel.DCTMetaDataManager;
import com.efounder.bz.dbform.datamodel.DataSetComponentEvent;
import com.efounder.builder.base.data.ESPRowSet;

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
public class DataSetTreeModel extends DefaultTreeModel implements DataSetListener,DMComponent{
  /**
   *
   */
  public DataSetTreeModel() {
    super(new DefaultMutableTreeNode("DMTree"));
  }


  /**
   *
   * @param e DataSetEvent
   */
  public void dataSetChanged(DataSetEvent e) {
    if (e.getEventType() == DataSetEvent.CURSOR) {
      return;
    }
    if (e.getEventType() == DataSetEvent.INSERT) {
      return;
    }
  }

  /**
   *
   */
  protected DataSetComponent dataSetComponent = null;
  /**
   *
   * @return DataSetComponent
   */
  public DataSetComponent getDataSetComponent() {
    return dataSetComponent;
  }

  /**
   *
   * @param dsc DataSetComponent
   */
  public void setDataSetComponent(DataSetComponent dsc) {
    if (dataSetComponent != dsc) {
      if (dataSetComponent != null) {
        dataSetComponent.removeDMComponent(this);
      }
      dataSetComponent = dsc;
      if (dataSetComponent != null) {
        dataSetComponent.insertDMComponent(this);
      }
    }
    if ( dataSetID != null ) {
      DCTMetaData dctMetaData = getDCTMetaData();
      if ( dctMetaData != null ) {
    	  this.dataSet = ((MDModel)dsc).getMDMDataModel().getDataSet(dataSetID);
          TreeUtils.createTree(this,dctMetaData,dataSet);
      }
    }
  }

  /**
   *
   */
  protected String dataSetID = null;
  /**
   *
   * @param dataSetID String
   */
  public void setDataSetID(String dataSetID) {
    this.dataSetID = dataSetID;
    if (dataSetComponent != null) {
    	setDataSet(dataSetComponent.getDataSet(dataSetID));
      DCTMetaData dctMetaData = getDCTMetaData();
      if ( dctMetaData != null )
        TreeUtils.createTree(this,dctMetaData,dataSet);
//      setDataSet(dataSetComponent.getDataSet(dataSetID));
    }
  }
  protected EFDataSet dataSet = null;
  /**
   *
   * @return String
   */
  public String getDataSetID() {
    return dataSetID;
  }
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet() {
    return dataSet;
  }

  /**
   *
   * @param dataSet EFDataSet
   */
  public void setDataSet(EFDataSet ds) {
    if (dataSet != ds) {
      // 清除掉事件监听器
      if (dataSet != null) {
        dataSet.removeDataSetListener(this);
      }
      dataSet = ds;
      // 增加事件监听器
      if (dataSet != null) {
        dataSet.addDataSetListener(this);
        DCTMetaData dctMetaData = getDCTMetaData();
        if ( dctMetaData != null )
          TreeUtils.createTree(this,dctMetaData,dataSet);
//          this.buildeDataSetTreeModel(this,dctMetaData,dataSet);
      }
    }
  }
  /**
   *
   * @return DCTMetaData
   */
  protected DCTMetaData getDCTMetaData() {
    DataSetComponent dsc = this.getDataSetComponent();
    if ( dsc == null ) return null;
    try {
		DCTMetaData dctMeta = (DCTMetaData) DCTMetaDataManager.getDCTMetaDataManager().getMetaData(dataSetID);
		return dctMeta;
	} catch (Exception e) {
		e.printStackTrace();
	}
    DOMetaData[] doMetaDatas  = dsc.getDOMetaData(dataSetID);
    if ( doMetaDatas != null && doMetaDatas.length > 0 ) {
      return doMetaDatas[0].getDCTMetaData();
    }
    return null;
  }
  /**
   *
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
  }

  public ESPRowSet getMainRowSet() {
    return null;
  }
}
