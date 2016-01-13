package com.efounder.mdm.client;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.eai.data.*;
import com.efounder.mdm.*;
import com.efounder.builder.base.util.ESPContext;
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
public class ClientMasterDataManager extends MasterDataManager {
  /**
   *
   */
  public ClientMasterDataManager() {
    super();
  }
  /**
   *
   */
  protected void initMasterDataManager() {
    masterDataBuffer = masterDataBufferManger.createDataBuffer("masterData");
  }
  /**
   *
   */
  protected static String DEFAULT_SERVICE_KEY = "MDMProvider";
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
    return getMDMDataModel(paramObject,MDL_ID,DCT_ID,null,null,null,false);
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
                                        String MDL_ID,String DCT_ID,String FKEY_ID) throws Exception {
    return getMDMDataModel(paramObject,MDL_ID,DCT_ID,FKEY_ID,null,null,false);
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
                                        String RLGL_ID) throws Exception {
    return getMDMDataModel(paramObject,MDL_ID,DCT_ID,FKEY_ID,RLGL_ID,null,false);
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
                                        String RLGL_ID,String CONT) throws Exception {
    return getMDMDataModel(paramObject,MDL_ID,DCT_ID,FKEY_ID,RLGL_ID,CONT,false);
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
                                        String RLGL_ID,String CONT,boolean refresh) throws Exception {
    return getMDMDataModel(DEFAULT_SERVICE_KEY,paramObject,MDL_ID,DCT_ID,FKEY_ID,RLGL_ID,CONT,refresh);
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
    if ( refresh ) {
      return loadMDMDataModel(serviceKey,paramObject,MDL_ID,DCT_ID,FKEY_ID,RLGL_ID,CONT);
    } else {
      return loadMDMDataModelFromBF(serviceKey, paramObject, MDL_ID, DCT_ID, FKEY_ID, RLGL_ID, CONT);
    }
  }
  /**
   *
   * @param serviceKey String
   * @param paramObject JParamObject
   * @param MDL_ID String
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @param CONT String
   * @return EFMDMDataModel
   * @throws Exception
   */
  private EFMDMDataModel loadMDMDataModelFromBF(String serviceKey,
                                      JParamObject paramObject, String MDL_ID,
                                      String DCT_ID, String FKEY_ID,
                                      String RLGL_ID, String CONT) throws Exception {
    paramObject.SetValueByParamName("MDL_ID", MDL_ID);
    paramObject.SetValueByParamName("DCT_ID", DCT_ID);
    String key=getDataBufferKey(MDL_ID,DCT_ID,paramObject);
    EFDataSet DCT_ID_DataSet = (EFDataSet)this.masterDataBuffer.getData(key);
    EFDataSet FKEY_ID_DataSet = null;
    EFDataSet RLGL_ID_DataSet = null;
    boolean needDCTLoad = false,needFKEYLoad = false,needRLGLLoad = false;
    if ( DCT_ID_DataSet != null ) {
      paramObject.SetValueByParamName(DCT_ID, "0");
    } else {
      paramObject.SetValueByParamName(DCT_ID, "1");needDCTLoad = true;
      paramObject.SetValueByParamName("CONT", CONT);
    }
    String fkey_key= key+="-"+FKEY_ID;
    boolean needProcfkey=false;
    if ( FKEY_ID != null ) {
    needProcfkey=true;
      FKEY_ID_DataSet = (EFDataSet)this.masterDataBuffer.getData(getDataBufferKey(MDL_ID,FKEY_ID,paramObject));
      if (FKEY_ID_DataSet == null) {
        paramObject.SetValueByParamName("FKEY_ID", FKEY_ID);needFKEYLoad = true;
      }else{
         String load= (String) masterDataBuffer.getData(fkey_key);
         if(load!=null)
             needProcfkey=false;
      }
    }
    if ( RLGL_ID != null ) {
      RLGL_ID_DataSet = (EFDataSet)this.masterDataBuffer.getData(getDataBufferKey(MDL_ID,"RLGL_"+RLGL_ID,paramObject));
      if (RLGL_ID_DataSet == null) {
        paramObject.SetValueByParamName("RLGL_ID", RLGL_ID);needRLGLLoad = true;
      }
    }
    EFMDMDataModel mdmDataModel = null;
    if ( needDCTLoad || needFKEYLoad || needRLGLLoad ) {
      mdmDataModel = loadMDMDataModelFromDB(serviceKey,paramObject,MDL_ID);
    } else {
      mdmDataModel = EFMDMDataModel.getInstance();
      mdmDataModel.setMDL_ID(MDL_ID);
      mdmDataModel.setDCT_ID(DCT_ID);
      mdmDataModel.setFKEY_ID(FKEY_ID);
      mdmDataModel.setRLGL_ID(RLGL_ID);
    }
    if ( DCT_ID_DataSet != null ) mdmDataModel.setDataSet(DCT_ID,DCT_ID_DataSet);
    if ( FKEY_ID_DataSet != null ) mdmDataModel.setDataSet(FKEY_ID_DataSet.getTableName(),FKEY_ID_DataSet);
    if ( RLGL_ID_DataSet != null ) {
    	mdmDataModel.setDataSet(RLGL_ID_DataSet.getTableName(),RLGL_ID_DataSet);
    	mdmDataModel.setDataSet("RLGL_"+RLGL_ID,RLGL_ID_DataSet);
    }
    if ( needProcfkey ) {
        this.processFKEY(paramObject, mdmDataModel, DCT_ID, FKEY_ID, RLGL_ID);
        masterDataBuffer.addData(fkey_key, "1");
    }
    if ( needRLGLLoad )
      this.processRLGL(paramObject,mdmDataModel,DCT_ID,FKEY_ID,RLGL_ID);
    return mdmDataModel;
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
  protected EFMDMDataModel loadMDMDataModel(String serviceKey,JParamObject po,String MDL_ID,
                                        String DCT_ID,String FKEY_ID,String RLGL_ID,String CONT) throws Exception {
    po.SetValueByParamName("MDL_ID", MDL_ID);
    po.SetValueByParamName("DCT_ID", DCT_ID);
    po.SetValueByParamName(DCT_ID, "1");
    po.SetValueByParamName("FKEY_ID", FKEY_ID);
    po.SetValueByParamName("RLGL_ID", RLGL_ID);
    po.SetValueByParamName("CONT", CONT);
    // 设用
    EFMDMDataModel mdmDataModel = loadMDMDataModelFromDB(serviceKey, po,MDL_ID);
    if ( mdmDataModel != null ) {
      // 在这里需要对其中的数据集进行处理
      processFKEY(po, mdmDataModel, DCT_ID, FKEY_ID, RLGL_ID);
      processRLGL(po, mdmDataModel, DCT_ID, FKEY_ID, RLGL_ID);
    }
    return mdmDataModel;
  }
  /**
   *
   * @param serviceKey String
   * @param po JParamObject
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @param CONT String
   * @return EFMDMDataModel
   * @throws Exception
   */
  private EFMDMDataModel loadMDMDataModelFromDB(String serviceKey, JParamObject po,String MDL_ID) throws Exception {
    JResponseObject ro = ServiceComponent.syncRunEnterpriseService(serviceKey,po,null,null,null);
    //
    if ( ro != null && ro.getErrorCode() == JResponseObject.RES_OK ) {
      EFMDMDataModel mdmDataModel = (EFMDMDataModel)ro.getResponseObject();
      // 处理缓冲
      processDataBuffer(po, mdmDataModel,MDL_ID);
      return mdmDataModel;
    }
    return null;
  }
  /**
   *
   * @param mdmDataModel EFMDMDataModel
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @param CONT String
   * @throws Exception
   */
  protected void processDataBuffer(JParamObject paramObject,EFMDMDataModel mdmDataModel,String MDL_ID) throws Exception {
//    if ( mdmDataModel == null ) return;
//    String[] dsTypes = mdmDataModel.getDSTypes();
//    if ( dsTypes == null || dsTypes.length == 0 ) return;
//    for(int i=0;i<dsTypes.length;i++) {
//      this.masterDataBuffer.addData(getDataBufferKey(MDL_ID,dsTypes[i],paramObject),mdmDataModel.getDataSet(dsTypes[i]));
//    }
  }
  /**
   *
   * @param MDL_ID String
   * @param DCT_ID String
   * @param paramObject JParamObject
   * @return String
   */
  protected String getDataBufferKey(String MDL_ID,String DCT_ID,JParamObject paramObject) {
    // 如果没有指定模型ID，则直接返回DCT_ID
	  String DCT_SUFFIX = (String)paramObject.GetValueByParamName("DCT_SUFFIX","");
	  if("".equals(DCT_SUFFIX))
		  DCT_SUFFIX = (String)paramObject.getEnvValue("DCT_SUFFIX","");
	DCT_ID = DCT_ID + "@"+DCT_SUFFIX;//大庆需求，区分上市 未上市 股份主数据字典  缺陷：一部分缓存数据无法清空add by wujf at20120712
    if ( MDL_ID == null || MDL_ID.trim().length() == 0 ) return DCT_ID;
    try {
      //
      BIZMetaData bizMetaData = (BIZMetaData)MetaDataManager.getInstance(MetaDataManager._BIZModel_).getMetaData(MDL_ID);
      if ( bizMetaData == null ) return DCT_ID;
      String UNIT_DCT = "";
      String ZZJG_ID = "";
//      String UNIT_DCT = bizMetaData.getUNIT_DCT();
//      String ZZJG_ID = paramObject.GetValueByParamName(UNIT_DCT,null);
//      if ( ZZJG_ID == null || ZZJG_ID.trim().length() == 0 ) ZZJG_ID = paramObject.GetValueByEnvName(UNIT_DCT,null);
//      if ( ZZJG_ID == null || ZZJG_ID.trim().length() == 0 ) return DCT_ID;
      //

      DCTMetaData dctMetaData = bizMetaData.getDCTMetaData(DCT_ID);
      if ( dctMetaData == null ) return DCT_ID;
      String UNIT_ID = paramObject.GetValueByEnvName("UNIT_ID",null);
      EFRowSet rowSet = dctMetaData.getACJGSZRowSet(UNIT_DCT,UNIT_ID);
      // 如果设置了机构选用，则设置Key+ZZJG_ID
      if ( rowSet != null ) return DCT_ID+"_"+ZZJG_ID;
      // 如果当前字典中有外键列引用了当前模型的组织机构，则需要设置Key+ZZJG_ID
      DCTMetaData fkeyDctMetaData = dctMetaData.getFKeyDCTMetaData(UNIT_DCT);
      if ( fkeyDctMetaData != null ) {
        return DCT_ID+"_"+ZZJG_ID;
      }
//      EFDataSet dataSet = dctMetaData.getACJGSZ();
//      if ( dataSet != null && dataSet.getRowCount() != 0 ) {
//        ESPRowSet rowSet = dataSet.getRowSet(new String[]{UNIT_ID, UNIT_DCT,DCT_ID});
//        if ( rowSet != null ) return DCT_ID+"_"+ZZJG_ID;
//      }
      return DCT_ID;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return DCT_ID;
  }

  /**
   *
   * @param mdmDataModel EFMDMDataModel
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @throws Exception
   */
  protected void processFKEY(JParamObject paramObject,EFMDMDataModel mdmDataModel,String DCT_ID,String FKEY_ID,String RLGL_ID) throws Exception {
    String FKEY_COL_ID = mdmDataModel.getString("FKEY_COL_ID",null);
    if ( FKEY_COL_ID == null ) {
     if(FKEY_ID!=null){
         DCTMetaData dctMetaData = (DCTMetaData) MetaDataManager.getInstance(
                 MetaDataManager._DCTObject_).getMetaData(DCT_ID);
         FKEY_COL_ID = dctMetaData.getDoMetaData().getColIDByFKEYObj(FKEY_ID);
     }
      if ( FKEY_COL_ID == null )
        return;
    }
    EFDataSet fkeyDataSet= mdmDataModel.getDataSet(FKEY_ID);
    if ( fkeyDataSet == null || fkeyDataSet.getRowCount() == 0 ) return; // 如果不处理
    EFDataSet dctDataSet = mdmDataModel.getDataSet(DCT_ID);
    if ( dctDataSet == null ) return;
    java.util.List rowSetList = dctDataSet.getRowSetList();
    if ( rowSetList == null || rowSetList.size() == 0 ) return;
    ESPRowSet rowSet = null;String tabName = FKEY_ID+"."+DCT_ID;
    for(int i=0;i<fkeyDataSet.getRowCount();i++)
        fkeyDataSet.getRowSet(i).removeDataSet(tabName);
    for(int i=0;i<rowSetList.size();i++) {
      rowSet = (ESPRowSet)rowSetList.get(i);
      String FKEY_VALUE = rowSet.getString(FKEY_COL_ID,null);
      if ( FKEY_VALUE == null || FKEY_VALUE.trim().length() == 0 ) continue;
      ESPRowSet fkeyRowSet = fkeyDataSet.getRowSet(new String[]{FKEY_VALUE});
      if ( fkeyRowSet == null ) continue;
      EFDataSet linkDataSet = fkeyRowSet.getDataSet(tabName);
      if ( linkDataSet == null ) {
        linkDataSet = EFDataSet.getInstance(tabName);
        linkDataSet.setPrimeKey(new String[]{mdmDataModel.getString("DCT_BMCOLID","F_BH")});
        fkeyRowSet.setDataSet(tabName,linkDataSet);
      }
      if ( linkDataSet != null ) linkDataSet.addRowSet(rowSet);
    }
  }
  /**
   *
   * @param mdmDataModel EFMDMDataModel
   * @param DCT_ID String
   * @param FKEY_ID String
   * @param RLGL_ID String
   * @throws Exception
   */
  protected void processRLGL(JParamObject paramObject,EFMDMDataModel mdmDataModel,String DCT_ID,String FKEY_ID,String RLGL_ID) throws Exception {
    String RLGL_OBJ_ID = mdmDataModel.getString("RLGL_OBJ_ID",null);
    String DCT_BMCOL1 = mdmDataModel.getString("DCT_BMCOL1",null);
    String DCT_BMCOL2 = mdmDataModel.getString("DCT_BMCOL2",null);
    if ( RLGL_OBJ_ID == null ) return;
    EFDataSet dctDataSet = mdmDataModel.getDataSet(DCT_ID);
    if ( dctDataSet == null ) return;
    EFDataSet rlglDCTDataSet = mdmDataModel.getDataSet("RLGL_"+RLGL_ID);
    if ( rlglDCTDataSet == null || rlglDCTDataSet.getRowCount() == 0 ) return;
    java.util.List rowSetList = rlglDCTDataSet.getRowSetList();
    ESPRowSet rlglrowSet = null;
    for(int i=0;i<rowSetList.size();i++) {
      rlglrowSet = (ESPRowSet)rowSetList.get(i);
      EFDataSet rlglObjDataSet = rlglrowSet.getDataSet(RLGL_OBJ_ID);
      if ( rlglObjDataSet == null || rlglObjDataSet.getRowCount() == 0 ) continue;
      processRLGLOBJ(mdmDataModel,rlglrowSet,rlglObjDataSet,dctDataSet,DCT_BMCOL1,DCT_BMCOL2,RLGL_ID,DCT_ID);
    }
  }
  /**
   *
   * @param rlglRowSet ESPRowSet
   * @param rlglObjDataSet EFDataSet
   * @param dctDataSet EFDataSet
   * @param DCT_BMCOL1 String
   * @param DCT_BMCOL2 String
   * @param RLGL_ID String
   * @param DCT_ID String
   */
  protected void processRLGLOBJ(EFMDMDataModel mdmDataModel,ESPRowSet rlglRowSet,EFDataSet rlglObjDataSet,EFDataSet dctDataSet,String DCT_BMCOL1,String DCT_BMCOL2,String RLGL_ID,String DCT_ID) {
    java.util.List rowSetList = rlglObjDataSet.getRowSetList();ESPRowSet objRowSet = null;
    for(int i=0;i<rowSetList.size();i++) {
      objRowSet = (ESPRowSet)rowSetList.get(i);
      String DCT_BM2 = objRowSet.getString(DCT_BMCOL2,null);
      if ( DCT_BM2 == null ) continue;
      ESPRowSet dctRowSet = dctDataSet.getRowSet(new String[]{DCT_BM2});
      if ( dctRowSet == null ) continue;
      EFDataSet rlglDataSet = rlglRowSet.getDataSet(RLGL_ID+"."+DCT_ID);
      if ( rlglDataSet == null ) {
        rlglDataSet = EFDataSet.getInstance(RLGL_ID+"."+DCT_ID);
        rlglDataSet.setPrimeKey(new String[]{mdmDataModel.getString("DCT_BMCOLID","F_BH")});
        rlglRowSet.setDataSet(RLGL_ID+"."+DCT_ID,rlglDataSet);
      }
      rlglDataSet.addRowSet(dctRowSet);
    }
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
    return null;
  }
}
