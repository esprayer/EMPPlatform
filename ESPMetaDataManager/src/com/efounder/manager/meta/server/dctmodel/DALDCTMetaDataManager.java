package com.efounder.manager.meta.server.dctmodel;

import java.sql.*;
import java.util.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.eai.data.*;
import com.efounder.manager.meta.server.service.*;
import com.efounder.builder.meta.MetaData;

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
public class DALDCTMetaDataManager extends DCTMetaDataManager {
  /**
   *
   */
  public DALDCTMetaDataManager() {
  }
  /**
   *
   * @param eSPContext ESPContext
   * @param dCTMetaData DCTMetaData
   */
  protected void loadDCT_RLGL(ESPContext espContext, DCTMetaData dCTMetaData) throws Exception {
    // 如果已经有了，不装
    if ( dCTMetaData.getDataSet("SYS_RLGL") != null ) return;
    ESPServerContext espServerCtx = (ESPServerContext)espContext;
    // 新建一个DOMetaData

    String tableName = "SYS_RLGL";String fields = "*";
    String where = "DCT_ID2='"+dCTMetaData.getObjID()+"'";
    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    EFDataSet dataSet = EFDataSet.getInstance(tableName);
    try {
      dataSet.setPrimeKey(new String[] {"RLGL_ID"});
      DataSetUtils.resultSet2DataSet(resultSet, dataSet);
      // 设置到DCTMetaData中
      dCTMetaData.setDataSet("SYS_RLGL",dataSet);
    } catch ( Exception ex ) {ex.printStackTrace();throw ex;} finally {resultSet.close();}
    return;
  }

  /**
   *
   * @param eSPContext ESPContext
   * @param string String
   * @return DCTMetaData
   */
  protected DCTMetaData loadSYS_DICTS(ESPContext espContext, String doID) throws Exception {
    ESPServerContext espServerCtx = (ESPServerContext)espContext;
    // 新建一个DOMetaData
    DCTMetaData dctMetaData = null;
    String tableName = "SYS_DICTS";String fields = "*";
    String where = "OBJ_ID='"+doID+"'";
//    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    // 元数据多语言 gengeng 2011-5-23
    ResultSet resultSet = null;
    if (Arrays.asList(ESPMetaDataUtil._MLANG_METATABLE_).contains(doID))
      resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    else
      resultSet = ESPMetaDataUtil.getMetaDataResultSet(espServerCtx, tableName,fields, where);
    try {
      if (resultSet.next()) {
        dctMetaData = DCTMetaData.getInstance(doID);
        DataSetUtils.resultSet2RowSet(resultSet, dctMetaData);
      }
    } catch ( Exception ex ) {
      ex.printStackTrace();throw ex;
    } finally {
      resultSet.close();
    }
    return dctMetaData;
  }
  /**
   *
   * @param eSPContext ESPContext
   * @param dCTMetaData DCTMetaData
   */
  protected void loadSYS_DCT_CST(ESPContext eSPContext, DCTMetaData dCTMetaData) {
  }
  /**
   *
   * @param eSPContext ESPContext
   * @param dCTMetaData DCTMetaData
   */
  protected void loadSYS_DCT_CTN_CST(ESPContext eSPContext,
                                     DCTMetaData dCTMetaData) {
  }
  /**
   *
   * @param espContext ESPContext
   * @param dctMetaData DCTMetaData
   * @return EFDataSet
   * @throws Exception
   */
  protected EFDataSet getSYS_DCT_CST(ESPContext espContext, DCTMetaData dctMetaData) throws Exception {
    if ( dctMetaData.getExtendProperty() != null ) return null;
    String tableName = "SYS_DCT_CST";
    EFDataSet dataSet = dctMetaData.getSYS_DCT_CST_DS();
    if ( dataSet != null ) return dataSet;
    ESPServerContext espServerCtx = (ESPServerContext)espContext;
    String fields = "*";
    JParamObject po = espServerCtx.getParamObject();
    String where = "DCT_ID='"+dctMetaData.getObjID()+"'";// and (UNIT_ID is null or UNIT_ID = '"+po.GetValueByEnvName("UNIT_ID",null)+"') order by UNIT_ID desc";
    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    dataSet = EFDataSet.getInstance(tableName);
    dataSet.setPrimeKey(new String[]{"DCT_KEY","UNIT_ID"});
    try {
      DataSetUtils.resultSet2DataSet(resultSet, dataSet);
      dctMetaData.setSYS_DCT_CST_DS(dataSet);
      dctMetaData.processExtendProperty(dataSet,"UNIT_ID","DCT_KEY","DCT_VAL");
    } catch ( Exception ex ) {ex.printStackTrace();throw ex;} finally {resultSet.close();}
    return dataSet;
  }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   * @throws Exception
   */
  protected void loadACJGSZ(ESPContext espContext,DCTMetaData dctMetaData) throws Exception {
    if ( dctMetaData.getACJGSZ() != null ) return;
    ESPServerContext espServerCtx = (ESPServerContext)espContext;EFDataSet dataSet = null;
    String tableName = "ACJGSZ";String fields = "*";
    JParamObject po = espServerCtx.getParamObject();
    String where = "ZD_DCT='"+dctMetaData.getObjID()+"'";// and (UNIT_ID = '"+po.GetValueByEnvName("UNIT_ID",null)+"')";
    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    dataSet = EFDataSet.getInstance(tableName);
    dataSet.setPrimeKey(new String[]{"UNIT_ID","JG_DCT","ZD_DCT"});
    try {
      DataSetUtils.resultSet2DataSet(resultSet, dataSet);
      dctMetaData.setACJGSZ(dataSet);
    } catch ( Exception ex ) {ex.printStackTrace();throw ex;} finally {resultSet.close();}
    return;
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
