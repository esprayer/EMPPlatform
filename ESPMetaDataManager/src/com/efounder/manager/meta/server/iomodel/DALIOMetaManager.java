package com.efounder.manager.meta.server.iomodel;

import java.sql.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.MetaData;
import com.efounder.builder.meta.iomodel.*;
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
public class DALIOMetaManager extends IOMetaDataManager {

  /**
   *
   */
  public DALIOMetaManager() {
  }

  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return EFDataSet
   */
  protected EFDataSet loadSYS_IOCOLSET(ESPContext ctx, String objid) throws Exception {
    ESPServerContext espServerCtx = (ESPServerContext)ctx;
    String table = "SYS_IOCOLSET";
    String field = "*";
    String where = "OBJ_ID='" + objid + "'";
    ResultSet rs = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), table, field, where);
    EFDataSet dataSet = EFDataSet.getInstance(objid);
    dataSet.setPrimeKey(new String[]{SYS_IOCOLSET.OBJ_ID, SYS_IOCOLSET.IO_ID, SYS_IOCOLSET.COL_ID});
    DataSetUtils.resultSet2DataSet(rs, dataSet);
    rs.close();
    return dataSet;
  }
  /**
   *
   */
//  @Override
//  public Object updateMetaData(ESPContext arg0, MetaData arg1) throws Exception {
//	  // TODO Auto-generated method stub
//	  return null;
//  }

  public Object updateMetaData(ESPContext ctx, MetaData metaData) throws
      Exception {
    return null;
  }

}
