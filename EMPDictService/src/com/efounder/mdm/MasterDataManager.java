package com.efounder.mdm;

import com.efounder.buffer.*;
import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.eai.data.*;
import org.openide.util.*;
import com.efounder.builder.base.util.*;
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
public abstract class MasterDataManager {
  /**
   * 创建元数据管理用的数据缓冲管理器
   */
  protected static DataBufferManager masterDataBufferManger = null;
  /**
   *
   */
  static {
    masterDataBufferManger = DataBufferManager.createDataBufferManager(MasterDataManager.class);
  }
  /**
   *
   */
  public MasterDataManager() {
    initMasterDataManager();
  }
  /**
   *
   */
  protected static MasterDataManager masterDataManager = null;
  /**
   *
   * @param paramObject JParamObject
   * @return EFMDMDataModel
   * @throws Exception
   */
  public static MasterDataManager getDefault() throws Exception {
    MasterDataManager masterDataManager = (MasterDataManager)Lookup.getDefault().lookup(MasterDataManager.class);
    return masterDataManager;
  }
  /**
   *
   */
  protected DataBuffer masterDataBuffer = null;
  /**
   *
   */
  protected abstract void initMasterDataManager();
  /**
   *
   * @param paramObject JParamObject
   * @param MDL_ID String
   * @param DCT_ID String
   * @return EFMDMDataModel
   * @throws Exception
   */
  public abstract EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID) throws Exception;
  /**
   *
   * @param paramObject JParamObject
   * @param MDL_ID String
   * @param DCT_ID String
   * @param FKEY_ID String
   * @return EFMDMDataModel
   * @throws Exception
   */
  public abstract EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID) throws Exception ;
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
  public abstract EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID,
                                        String RLGL_ID) throws Exception ;
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
  public abstract EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID,
                                        String RLGL_ID,String CONT) throws Exception ;
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
  public abstract EFMDMDataModel getMDMDataModel(JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID,
                                        String RLGL_ID,String CONT,boolean refresh) throws Exception ;
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
  public abstract EFMDMDataModel getMDMDataModel(String serviceKey,JParamObject paramObject,
                                        String MDL_ID,String DCT_ID,String FKEY_ID,
                                        String RLGL_ID,String CONT,boolean refresh) throws Exception;
  /**
   *
   * @param DCT_ID String
   * @param DCT_BM String
   * @param espContext ESPContext
   * @return ESPRowSet
   * @throws Exception
   */
  public final ESPRowSet getDCTRowSet(String MDL_ID,String DCT_ID,String DCT_BM,ESPContext espContext) throws Exception {
    List<ESPRowSet> rowSets = getDCTRowSet(MDL_ID,DCT_ID,new String[]{DCT_BM},espContext);
    if ( rowSets == null || rowSets.size() == 0 ) return null;
    return rowSets.get(0);
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BMs String[]
   * @param espContext ESPContext
   * @return ESPRowSet[]
   * @throws Exception
   */
  public abstract List<ESPRowSet> getDCTRowSet(String MDL_ID,String DCT_ID,String[] DCT_BMs,ESPContext espContext) throws Exception;
}
