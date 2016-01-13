package com.efounder.builder.meta.bizmodel;

import java.util.*;

import com.core.xml.*;
import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.fctmodel.*;
import com.efounder.eai.data.*;
import com.efounder.builder.meta.dctmodel.DCTMetaDataManager;
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
public abstract class BIZMetaDataManager extends MetaDataManager {
  /**
   *
   */
  public BIZMetaDataManager() {
  }
  /**
   *
   * @return BIZMetaDataManager
   */
  public static final BIZMetaDataManager getInstance() {
    return (BIZMetaDataManager)getInstance(MetaDataManager._BIZModel_);
  }
  /**
   *
   * @param objid String
   * @return MetaData
   */
  public MetaData getMetaData(String objid)  throws Exception {
    java.util.List modelList = PackageStub.getContentVector("BIZModelList");
    JParamObject po = JParamObject.Create();
    for(int i=0; modelList != null&&i<modelList.size();i++) {
      StubObject stub = (StubObject)modelList.get(i);
      if ( !objid.equals(stub.getID()) ) continue;
//      po.setEAIServer(stub.getString("eaiServer",null));
    }
    ESPContext espContext = ESPClientContext.getInstance(po);
    return getMetaData(espContext,objid);
  }
  /**
   *
   * @param objid String
   * @return BIZMetaData
   */
  public final BIZMetaData getBIZMetaData(String objid)  throws Exception {
    return (BIZMetaData)getMetaData(objid);
  }
  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return BIZMetaData
   */
  public final BIZMetaData getBIZMetaData(ESPContext ctx,String objid) throws Exception  {
    return (BIZMetaData)getMetaData(ctx,objid);
  }

  /**
   *
   * @param dataSet EFDataSet
   */
  protected void initSYS_MDL_VAL(EFDataSet dataSet,BIZMetaData bizMetaData) {
    if ( dataSet == null ) return;
    List<ESPRowSet> rowList = dataSet.getRowSetList();
    if ( rowList == null || rowList.size() == 0 ) return;
    ESPRowSet rowSet = null;
    for(int i=0;i<rowList.size();i++) {
      rowSet = rowList.get(i);
      String unitid=rowSet.getString("UNIT_ID",null);
      if(unitid==null||unitid.trim().length()==0)unitid="";
      bizMetaData.addSYS_MDL_VAL(rowSet.getString("MDL_KEY",null)+"-"+unitid,rowSet.getString("MDL_VALUE",null));
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return MetaData
   */
  protected MetaData getMetaDataModel(ESPContext ctx, String objid,MetaData metaData)  throws Exception {
    // 装入模型定义
    BIZMetaData bizMetaData = null;
    if ( metaData == null )
      bizMetaData = getSYS_MODEL(ctx,objid);
    else
      bizMetaData = (BIZMetaData)metaData;
    if ( bizMetaData == null ) return null;
    // 装入模型值定义
    EFDataSet dataSet = null;//getSYS_MDL_VAL(ctx,bizMetaData);
    getSYS_MDL_VAL(ctx,bizMetaData);
    // 处理模型值定义
//    if( dataSet != null ) {
//      // 设置到元数据中
//      initSYS_MDL_VAL(dataSet,bizMetaData);
//      bizMetaData.setDataSet(BIZMetaData._SYS_MDL_VAL_,dataSet);
//    }
    // 装入模型内容定义
    getSYS_MDL_CTN(ctx,bizMetaData);

    // 装入模型内容中的事实表定义
    getMDL_CTN_FCTS(ctx,bizMetaData);
    //
    getDCT_and_FCTMap(ctx,bizMetaData);
    // 装入SYS_MODEL中其他的引用字典的元数据（非事实表引用的字典）
    initDCTMetaData(ctx,bizMetaData);

    return bizMetaData;
  }

  /**
   * initDCTMetaData
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   */
  protected void initDCTMetaData(ESPContext ctx, BIZMetaData bizMetaData) throws Exception {
    //
    if (bizMetaData.refDCTMap == null) {
      bizMetaData.refDCTMap = new HashMap();
      ctx.putObject("DCT_MAP", bizMetaData.refDCTMap);
    }
    //年度字典
    initDCTMetaData(ctx, bizMetaData, bizMetaData.getYEAR_DCT());
    //月度字典
    initDCTMetaData(ctx, bizMetaData, bizMetaData.getMONTH_DCT());
    //日度字典
    initDCTMetaData(ctx, bizMetaData, bizMetaData.getDAY_DCT());
    //类型字典
    initDCTMetaData(ctx, bizMetaData, bizMetaData.getLX_DCT());
    //统一类型字典
    initDCTMetaData(ctx, bizMetaData, bizMetaData.getTY_DCT());
    //其他附属字典，定义在扩展属性中
    initDCTMetaData(ctx, bizMetaData, bizMetaData.getSYS_MDL_VAL(SYS_MDL_VAL.AFFIX_OBJID, null));
  }

  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   * @param dctid String
   * @throws Exception
   */
  protected void initDCTMetaData(ESPContext ctx, BIZMetaData bizMetaData, String dctid) throws Exception {
    if(dctid == null || dctid.trim().length() == 0) return;
    String[] dctids = dctid.split(",");
    DCTMetaData dctMetaData = null;
    for (int i = 0; dctids != null && i < dctids.length; i++) {
      dctMetaData = bizMetaData.getDCTMetaData(dctids[i]);
      //如果没有从数据库中装一次
      if (dctMetaData == null) {
        dctMetaData = ((DCTMetaDataManager)DCTMetaDataManager.getInstance(MetaDataManager._DCTObject_ + "_DAL")).getDCTMetaData(ctx, dctids[i]);
        if (dctMetaData != null)
          bizMetaData.addDCT_METAData(dctMetaData);
      }
    }
  }

  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   */
  protected void getDCT_and_FCTMap(ESPContext ctx,BIZMetaData bizMetaData) {
    java.util.Map dctMap = (java.util.Map)ctx.getObject("DCT_MAP",null);
    bizMetaData.refDCTMap = dctMap;
    java.util.Map fctMap = (java.util.Map)ctx.getObject("FCT_MAP",null);
    bizMetaData.refFCTMap = fctMap;
  }
  /**
   *
   * @param dataSet EFDataSet
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   * @throws Exception
   */
//  protected final void getMDL_CTN_VALS(EFDataSet dataSet,ESPContext ctx,BIZMetaData bizMetaData) throws Exception {
//    if ( dataSet == null || dataSet.first() == -1 ) return;
//    EFRowSet rowSet = null;
//    do {
//      // 获取一行内容
//      rowSet = dataSet.getRowSet();
//      String MDL_KEY = rowSet.getString(SYS_MDL_VAL.MDL_KEY,null);
//      String MDL_VAL = rowSet.getString(SYS_MDL_VAL.MDL_VALUE,null);
//      // 如果都不为空，则放入bizMetaData元数据中
//      if ( MDL_KEY != null && MDL_VAL != null ) {
//        bizMetaData.putString(MDL_KEY.trim(),MDL_VAL.trim());
//      }
//    } while ( dataSet.next() != -1 );
//  }
  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return BIZMetaData
   * @throws Exception
   */
  protected abstract BIZMetaData getSYS_MODEL(ESPContext ctx, String objid) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   * @return EFDataSet
   * @throws Exception
   */
  protected abstract EFDataSet getSYS_MDL_VAL(ESPContext ctx,BIZMetaData bizMetaData) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   * @throws Exception
   */
  protected abstract EFDataSet getSYS_MDL_CTN(ESPContext ctx,BIZMetaData bizMetaData) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   * @throws Exception
   */
  protected final void getMDL_CTN_FCTS(ESPContext ctx,BIZMetaData bizMetaData) throws Exception {
    EFDataSet dataSet = (EFDataSet)bizMetaData.getDataSet(BIZMetaData._SYS_MDL_CTN_);
    if ( dataSet == null || dataSet.first() == -1 ) return;
    EFRowSet rowSet = null;
    do {
      // 获取一行内容
      rowSet = dataSet.getRowSet();
      // 处理此行内容的事实表
      processFacts(ctx,bizMetaData,rowSet);
      // 处理内容信息
      processContents(ctx,bizMetaData,rowSet);
    } while ( dataSet.next() != -1 );
  }
  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   * @param rowSet EFRowSet
   * @throws Exception
   */
  private final void processContents(ESPContext ctx,BIZMetaData bizMetaData,EFRowSet rowSet) throws Exception {
    // 获取CTN_ID
    String CTN_ID = rowSet.getString(SYS_MDL_CTN.CTN_ID,null);
    String CTN_TYPE = rowSet.getString(SYS_MDL_CTN.CTN_TYPE,null);
    if ( CTN_ID == null || CTN_TYPE == null ) return;
     if(CTN_TYPE.equals("01")){
       CTN_TYPE=SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_;
     }else if(CTN_TYPE.equals("02")){
       CTN_TYPE=SYS_MDL_CTN._BIZ_CTN_TYPE_JIDS_;
     }
//     else if(CTN_TYPE.equals("03")){
//       CTN_TYPE=SYS_MDL_CTN._BIZ_CTN_TYPE_JPDS_;
//     }else if(CTN_TYPE.equals("10")){
//       CTN_TYPE=SYS_MDL_CTN._BIZ_CTN_TYPE_BLDS_;
//     }else if(CTN_TYPE.equals("05")){
//       CTN_TYPE=SYS_MDL_CTN._BIZ_CTN_TYPE_BCDS_;
//     }
    bizMetaData.addCTN_ID2CTN_TypeMap(CTN_TYPE,CTN_ID);
  }
  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   * @param rowSet EFRowSet
   * @throws Exception
   */
  private final void processFacts(ESPContext ctx,BIZMetaData bizMetaData,EFRowSet rowSet) throws Exception {
    String FCT_ID;
    java.util.List fctList = new java.util.ArrayList();
    // 获取CTN_ID
    String CTN_ID = rowSet.getString(SYS_MDL_CTN.CTN_ID,null);
    if ( CTN_ID == null ) return;
    // 循环处理每一行内容定义
    for( int i=0;i<BIZMetaData._FCT_COUNT_;i++) {
      // 获取一个事实表ID
      FCT_ID = rowSet.getString(SYS_MDL_CTN.CTN_FCT+(i+1),null);
      if ( FCT_ID == null ) continue;
      // 获取事实表元数据
      FCTMetaData fctMetaData = ((FCTMetaDataManager)FCTMetaDataManager.getInstance(MetaDataManager._FCTObject_ + "_DAL")).getFCTMetaData(ctx,FCT_ID);
      if ( fctMetaData == null ) continue;
      // 增加到Map中
      bizMetaData.addFCT_METAData(fctMetaData);
      // 增加到临时列表中
      fctList.add(fctMetaData);
    }
    // 增加到内容管理Map中
    bizMetaData.addFCT_MetaData2CTN(CTN_ID,fctList);
  }
}
