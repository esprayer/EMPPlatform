package com.efounder.builder.meta.fctmodel;

import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.builder.meta.dctmodel.*;
import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.eai.data.JParamObject;

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
public abstract class FCTMetaDataManager extends MetaDataManager {
  /**
   *
   */
  public FCTMetaDataManager() {
  }
  /**
   *
   * @return FCTMetaDataManager
   */
  public static final FCTMetaDataManager getInstance() {
    return (FCTMetaDataManager)getInstance(MetaDataManager._FCTObject_);
  }
  /**
   *
   * @param objid String
   * @return MetaData
   */
  public MetaData getMetaData(String objid)  throws Exception {
    java.util.List modelList = PackageStub.getContentVector("BIZFCTList");
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
   * @return FCTMetaData
   */
  public final FCTMetaData getFCTMetaData(String objid) throws Exception{
    return getFCTMetaData(null,objid);
  }
  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return FCTMetaData
   */
  public final FCTMetaData getFCTMetaData(ESPContext ctx,String objid) throws Exception{
    return (FCTMetaData)getMetaData(ctx,objid);
  }
  /**
   *
   * @param ctx MetaContext
   * @param objid String
   * @return MetaData
   * @todo Implement this com.efounder.builder.meta.MetaDataManager method
   */
  protected MetaData getMetaDataModel(ESPContext ctx, String fctID,MetaData metaData) throws Exception{
    // 装入FCTMetaData
    FCTMetaData fctMetaData = null;
    if ( metaData == null )
      fctMetaData = loadSYS_FACTS(ctx,fctID);
    else
      fctMetaData = (FCTMetaData)metaData;
    if ( fctMetaData == null ) return null;
    // 装入数据字典的对象定义
    loadDOMetaData(ctx,fctMetaData);
    // 装入数据字典的外键引用数据字典
    loadFKEY_DCT(ctx,fctMetaData);
    // 装入数据字典的参考数据字典
    loadREF_FCT(ctx,fctMetaData);
    // 装入数据字典组合使用定义
    loadSYS_DCT_GRP(ctx,fctMetaData);
    // 放到FCT_MAP中
  //  addContextFCTMap(ctx,fctID,fctMetaData);
    return fctMetaData;
  }
  protected final void addContextFCTMap(ESPContext ctx,String fctID,
                                        FCTMetaData fctMetaData) {
    java.util.Map fctMap = (java.util.Map)ctx.getObject("FCT_MAP",null);
    if ( fctMap == null ) {
      fctMap = new java.util.HashMap();
      ctx.putObject("FCT_MAP", fctMap);
    }
    fctMap.put(fctID,fctMetaData);
  }
  public void addContextREF(ESPContext ctx,String id, MetaData md){
     addContextFCTMap(ctx,id,(FCTMetaData)md);
 }

  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected final void loadDOMetaData(ESPContext ctx,FCTMetaData fctMetaData) throws Exception{
    // 装入此数据字典的数据对象元数据
    DOMetaData doMetaData = DOMetaDataManager.getInstance().getDOMetaData(ctx,fctMetaData.getObjID());
    // 设置到DCTMetaData元数据中
    if ( doMetaData != null ) {
      fctMetaData.setDoMetaData(doMetaData);
      doMetaData.setFCTMetaData(fctMetaData);
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected void loadFKEY_DCT(ESPContext ctx,FCTMetaData fctMetaData) throws Exception {
    DOMetaData doMetaData = fctMetaData.getDoMetaData();
    if ( doMetaData == null ) return;
    DOMetaData[] dos = doMetaData.getFKeyMetaDatas();
    if ( dos == null || dos.length == 0 ) return;
    for(int i=0;i<dos.length;i++) {
      // 装入外键对象的数据字典信息
      DCTMetaData fkeyDCTMetaData = DCTMetaDataManager.getInstance().getDCTMetaData(ctx,dos[i].getObjID());
      // 增加强到事实表的外键字典表管理中
      fctMetaData.addFKEYDCTMetaData(dos[i].getObjID(),fkeyDCTMetaData);
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected void loadREF_FCT(ESPContext ctx,FCTMetaData fctMetaData) throws Exception{
    DOMetaData doMetaData = fctMetaData.getDoMetaData();
    if ( doMetaData == null ) return;
    doMetaData = doMetaData.getRefObject();
    if ( doMetaData == null ) return;
    FCTMetaData refFCTMetaData = (FCTMetaData)getMetaData(ctx,doMetaData.getObjID());
    fctMetaData.setRefFCTObject(refFCTMetaData);
  }
  /**
   *
   * @param ctx ESPContext
   * @param fctID String
   * @return FCTMetaData
   */
  protected abstract FCTMetaData loadSYS_FACTS(ESPContext ctx,String fctID) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param fctMetaData FCTMetaData
   */
  protected abstract void loadSYS_DCT_GRP(ESPContext ctx,FCTMetaData fctMetaData) throws Exception;
}
