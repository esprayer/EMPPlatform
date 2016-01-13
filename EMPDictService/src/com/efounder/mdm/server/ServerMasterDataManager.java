package com.efounder.mdm.server;

import com.efounder.buffer.*;
import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.eai.data.*;
import org.openide.util.*;
import com.efounder.builder.base.util.*;
import com.efounder.mdm.*;
import com.efounder.db.*;
import java.sql.*;
import java.util.List;

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
public class ServerMasterDataManager extends MasterDataManager {
  /**
   *
   */
  public ServerMasterDataManager() {
    super();
  }
  /**
   *
   */
  protected void initMasterDataManager() {

  }
  /**
   *
   * @param paramObject JParamObject
   * @param MDL_ID String
   * @param DCT_ID String
   * @return EFMDMDataModel
   * @throws Exception
   */
  public EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID) throws Exception {
    return null;
  }
  /**
   *
   * @param paramObject JParamObject
   * @param MDL_ID String
   * @param DCT_ID String
   * @param FKEY_ID String
   * @return EFMDMDataModel
   * @throws Exception
   */
  public EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID) throws Exception  {
    return null;
  }

  /**
   *
   * @param paramObject JParamObject
   * @param MDL_ID String
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @return EFMDMDataModel
   * @throws Exception
   */
  public EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID,
                                        String RLGL_ID) throws Exception  {
    return null;
  }

  /**
   *
   * @param paramObject JParamObject
   * @param MDL_ID String
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @param CONT String
   * @return EFMDMDataModel
   * @throws Exception
   */
  public EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID,
                                        String RLGL_ID,String CONT) throws Exception  {
    return null;
  }

  /**
   *
   * @param paramObject JParamObject
   * @param MDL_ID String
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @param CONT String
   * @param refresh boolean
   * @return EFMDMDataModel
   * @throws Exception
   */
  public EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID,
                                        String RLGL_ID,String CONT,boolean refresh) throws Exception  {
    return null;
  }

  /**
   *
   * @param serviceKey String
   * @param paramObject JParamObject
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @param CONT String
   * @return EFMDMDataModel
   * @throws Exception
   */
  public EFMDMDataModel getMDMDataModel(String serviceKey,JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID,
                                        String RLGL_ID,String CONT,boolean refresh) throws Exception {
    return null;
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BMs String[]
   * @param espContext ESPContext
   * @return ESPRowSet[]
   * @throws Exception
   */
  public List<ESPRowSet> getDCTRowSet(String MDL_ID,String DCT_ID,String[] DCT_BMs,ESPContext espContext) throws Exception {
    // 如果不是ESPServerContext，则直接返回
    if ( !(espContext instanceof ESPServerContext) ) return null;
    ESPServerContext sc = (ESPServerContext)espContext;
    BIZMetaData bizMetaData = BIZMetaDataManager.getInstance().getBIZMetaData(espContext,MDL_ID);
    if ( bizMetaData == null ) return null;
    DCTMetaData dctMetaData = bizMetaData.getDCTMetaData(DCT_ID);
    if ( dctMetaData == null ) return null;
    String where = "";
    for(int i=0;i<DCT_BMs.length;i++) {
      where += dctMetaData.getDCT_BMCOLID()+"='"+DCT_BMs[i]+"'";
      if ( i < (DCT_BMs.length-1) ) {
        where += " and ";
      }
    }
    ResultSet resultSet = DataSetUtils.getResultSet(sc.getParamObject(),sc.getStatement(),DCT_ID,"*",where);
    EFDataSet dataSet = EFDataSet.getInstance(DCT_ID);
    DataSetUtils.resultSet2DataSet(resultSet,dataSet);
    resultSet.close();
    return dataSet.getRowSetList();
  }
}
