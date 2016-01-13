package com.efounder.manager.meta.server.bizmodel;

import java.sql.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.eai.data.*;
import com.efounder.manager.meta.server.service.*;

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
public class DALBIZMetaDataManager extends BIZMetaDataManager {
  /**
   *
   */
  public DALBIZMetaDataManager() {
  }
  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return BIZMetaData
   * @throws Exception
   */
  protected BIZMetaData getSYS_MODEL(ESPContext espContext, String doID) throws Exception {
    ESPServerContext espServerCtx = (ESPServerContext)espContext;
    // 新建一个DOMetaData
    BIZMetaData bizMetaData = null;
    String tableName = BIZMetaData._SYS_MODEL_;String fields = "*";
    String where = "MDL_ID='"+doID+"'";
//    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    // 元数据多语言 gengeng 2011-5-23
    ResultSet resultSet = ESPMetaDataUtil.getMetaDataResultSet(espServerCtx, tableName,fields, where);
    try {
      if (resultSet.next()) {
        bizMetaData = BIZMetaData.getInstance(doID);
        DataSetUtils.resultSet2RowSet(resultSet, bizMetaData);
      }
    } catch ( Exception ex ) {
      ex.printStackTrace();throw ex;
    } finally {
      resultSet.close();
    }
    return bizMetaData;
  }
  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   * @return EFDataSet
   * @throws Exception
   */
  protected EFDataSet getSYS_MDL_CTN(ESPContext espContext, BIZMetaData bizMetaData) throws Exception {
    if ( bizMetaData.getDataSet(BIZMetaData._SYS_MDL_CTN_) != null ) return null;
    ESPServerContext espServerCtx = (ESPServerContext)espContext;EFDataSet dataSet = null;
    String tableName = bizMetaData._SYS_MDL_CTN_;String fields = "*";
    String where = "MDL_ID='"+bizMetaData.getObjID()+"'";
    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    dataSet = EFDataSet.getInstance(tableName);
    dataSet.setPrimeKey(new String[]{SYS_MDL_CTN.CTN_ID});
    try {
      DataSetUtils.resultSet2DataSet(resultSet, dataSet);
      bizMetaData.setDataSet(BIZMetaData._SYS_MDL_CTN_, dataSet);
    } catch ( Exception ex ) {ex.printStackTrace();throw ex;} finally {resultSet.close();}
    return dataSet;
  }
  /**
   *
   * @param espContext ESPContext
   * @param bizMetaData BIZMetaData
   * @return EFDataSet
   * @throws Exception
   */
  protected EFDataSet getSYS_MDL_VAL(ESPContext espContext, BIZMetaData bizMetaData) throws Exception {
    if ( bizMetaData.getExtendProperty() != null ) return null;
    ESPServerContext espServerCtx = (ESPServerContext)espContext;EFDataSet dataSet = null;
    String tableName = bizMetaData._SYS_MDL_VAL_;String fields = "*";
    JParamObject po = espServerCtx.getParamObject();
    String where = "MDL_ID='"+bizMetaData.getObjID()+"'";// and (UNIT_ID is null or UNIT_ID = '"+po.GetValueByEnvName("UNIT_ID",null)+"') order by UNIT_ID desc";
    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    dataSet = EFDataSet.getInstance(tableName);
    dataSet.setPrimeKey(new String[]{SYS_MDL_VAL.MDL_KEY,"UNIT_ID"});
    try {
      DataSetUtils.resultSet2DataSet(resultSet, dataSet);
      bizMetaData.processExtendProperty(dataSet,"UNIT_ID","MDL_KEY","MDL_VALUE");
    } catch ( Exception ex ) {ex.printStackTrace();throw ex;} finally {resultSet.close();}
    return dataSet;
  }

  /**
   *
   * @param metaData MetaData
   * @return Object
   * @throws Exception
   */
  public Object updateMetaData(ESPContext ctx, MetaData metaData) throws Exception {
    ESPServerContext espContext = (ESPServerContext) ctx;

    return null;
  }

}
