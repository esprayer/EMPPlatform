package com.efounder.manager.meta.server.fctmodel;

import com.efounder.builder.meta.*;
import com.efounder.builder.base.util.ESPContext;
import com.efounder.builder.meta.fctmodel.*;
import com.efounder.builder.base.util.*;
import java.sql.*;
import com.efounder.builder.base.data.*;
import com.efounder.manager.meta.server.service.ESPMetaDataUtil;
import java.util.Arrays;

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
public class DALFCTMetaDataManager extends FCTMetaDataManager {
  /**
   *
   */
  public DALFCTMetaDataManager() {
  }
  /**
   *ESPContext
   * @param eSPContext ESPContext
   * @param string String
   * @return FCTMetaData
   */
  protected FCTMetaData loadSYS_FACTS(ESPContext espContext, String doID) throws Exception {
    ESPServerContext espServerCtx = (ESPServerContext)espContext;
    // 新建一个DOMetaData
    FCTMetaData fctMetaData = null;
    String tableName = "SYS_FACTS";String fields = "*";
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
        fctMetaData = FCTMetaData.getInstance(doID);
        DataSetUtils.resultSet2RowSet(resultSet, fctMetaData);
      }
    } catch ( Exception ex ) {
      ex.printStackTrace();throw ex;
    } finally {
      resultSet.close();
    }
    return fctMetaData;
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

  /**
   *
   * @param ctx ESPContext
   * @param fctMetaData FCTMetaData
   * @throws Exception
   */
  protected void loadSYS_DCT_GRP(ESPContext ctx, FCTMetaData fctMetaData) throws Exception {
  }

}
