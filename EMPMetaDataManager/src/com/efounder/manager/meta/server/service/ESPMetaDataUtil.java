package com.efounder.manager.meta.server.service;

import java.sql.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.db.*;

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
public class ESPMetaDataUtil {

  /**
   *
   */
  public static final String[] _MLANG_METATABLE_ = {"SYS_OBJECTS","SYS_OBJCOLS","SYS_DICTS","SYS_FACTS","SYS_MODEL","SYS_MDL_CTN"};

  /**
   * 元数据加载多语言
   *
   * @param espContext ESPServerContext
   * @param metaTable String
   * @param field String
   * @param where String
   * @return ResultSet
   * @throws Exception
   */
  public static ResultSet getMetaDataResultSet (ESPServerContext espContext, String metaTable, String field, String where) throws Exception {
    ResultSet resultSet = null;
    DCTMetaData dctMeta = null;
    try {
//      dctMeta = (DCTMetaData) DCTMetaDataManager.getDCTDataManager().getMetaData(espContext, metaTable);
      // 多语言方式
      String f_lang = espContext.getParamObject().GetValueByEnvName("Language","");
      if (dctMeta != null && dctMeta.getDoMetaData().getString(SYS_OBJECTS._OBJ_LANG_, "").equals("1")) {
        String where1 = where + " and (F_LANG = '" + f_lang + "' or F_LANG = ' ' or F_LANG is null)";
        String metaTable1 = DBTools.getDBAObject(espContext.getParamObject(), dctMeta);
        String SQL = DBTools.selectSql(metaTable1, field, where1);
        resultSet = espContext.getStatement().executeQuery(SQL);
      } else {
        // 旧的方式
        resultSet = DataSetUtils.getResultSet(espContext.getParamObject(),espContext.getStatement(), metaTable, field,where);
      }
    } catch (Exception ex1) {
      ex1.printStackTrace();
      resultSet = DataSetUtils.getResultSet(espContext.getParamObject(),espContext.getStatement(), metaTable, field,where);
    }
    return resultSet;
  }

  /**
   *
   * @param colsDS EFDataSet
   * @param enumDS EFDataSet
   */
  public static void setSYS_SELFENUM(EFDataSet colsDS, EFDataSet enumDS) {
//    EFRowSet colSet = null;String COL_ID = null;
//    EFDataSet colEnumDS = null;
//    for (int i = 0; colsDS != null && i < colsDS.getRowCount(); i++) {
//      colSet = colsDS.getRowSet(i);
//      COL_ID = colSet.getString(SYS_OBJCOLS._COL_ID_, "");
//      colEnumDS = enumDS.getDataSetByKey("COL_ID", COL_ID);
//      // 自列表
//      colSet.putString("SYS_SELFENUM", appendSYS_SELFENUM(colEnumDS));
//    }
  }

  /**
   *
   * @param colEnumDS EFDataSet
   * @return String
   */
  public static String appendSYS_SELFENUM(EFDataSet colEnumDS) {
    if (colEnumDS == null) return null;
    // 升序
    DataSetUtils.sortEFDataSet(colEnumDS, new String[]{"COL_DISP"}, null);
    StringBuffer sb = new StringBuffer();
    EFRowSet rs = null;
    for (int i = 0; i < colEnumDS.getRowCount(); i++) {
      rs = colEnumDS.getRowSet(i);
      sb.append(rs.getString("COL_VAL", "")).append(":").append(rs.getString("F_NOTE", "")).append(";");
    }
    return sb.toString();
  }

}
