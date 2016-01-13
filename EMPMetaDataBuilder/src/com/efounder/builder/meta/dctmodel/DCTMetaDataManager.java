package com.efounder.builder.meta.dctmodel;

import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.*;
import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.eai.data.JParamObject;
import com.efounder.builder.base.data.EFDataSet;
import java.util.List;
import com.efounder.builder.base.data.ESPRowSet;

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
public abstract class DCTMetaDataManager extends MetaDataManager {
  /**
   *
   */
  public DCTMetaDataManager() {

  }
  /**
   *
   * @return DCTMetaDataManager
   */
  public static final DCTMetaDataManager getInstance() {
    return (DCTMetaDataManager)getInstance(MetaDataManager._DCTObject_);
  }
  /**
   *
   * @param objid String
   * @return MetaData
   */
  public MetaData getMetaData(String objid)  throws Exception {
    java.util.List modelList = PackageStub.getContentVector("BIZDCTList");
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
   * @return DCTMetaData
   */
  public final DCTMetaData getDCTMetaData(String objid) throws Exception{
    return getDCTMetaData(null,objid);
  }
  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return DCTMetaData
   */
  public final DCTMetaData getDCTMetaData(ESPContext ctx,String objid) throws Exception{
    return (DCTMetaData)getMetaData(ctx,objid);
  }
  /**
   *
   * @param ctx MetaContext
   * @param objid String
   * @return MetaData
   * @todo Implement this com.efounder.builder.meta.MetaDataManager method
   */
  protected MetaData getMetaDataModel(ESPContext ctx, String dctID,MetaData metaData) throws Exception{
    // 装入数据字典定义
    DCTMetaData dctMetaData = null;
    if ( metaData == null ) {
      dctMetaData = loadSYS_DICTS(ctx,dctID);
    } else
      dctMetaData = (DCTMetaData)metaData;
    // 如果为空，则直接返回
    if ( dctMetaData == null ) return null;
    processReferenceMetaData(ctx,dctID,dctMetaData);
    // 装入数据字典的对象定义
    loadDOMetaData(ctx,dctMetaData);
    // 装入数据字典的外键引用数据字典
    loadFKEY_DCT(ctx,dctMetaData);
    // 装入数据字典的参考数据字典
    loadREF_DCT(ctx,dctMetaData);
    // 装入数据字典的关系定义（可能有多种关系定义）
//    loadDCT_RLGL(ctx,dctMetaData);
    //
//    loadRLGL_DCT(ctx,dctMetaData);
    //
//    loadRLGL_OBJ(ctx,dctMetaData);
    //
//    loadACJGSZ(ctx,dctMetaData);
    // 装入数据字典的自定义信息
    loadCustomDCT(ctx,dctMetaData);
    // 放到上下文环境中
//    addContextDCTMap(ctx,dctID,dctMetaData);
    //
    //EFDataSet dataSet =
    getSYS_DCT_CST(ctx,dctMetaData);
//    if ( dataSet != null ) {
//      initSYS_DCT_CST(dataSet,dctMetaData);
//    }
    return dctMetaData;
  }
  /**
   *
   * @param dataSet EFDataSet
   * @param dctMetaData DCTMetaData
   */
  protected void initSYS_DCT_CST(EFDataSet dataSet,DCTMetaData dctMetaData) {
    if ( dataSet == null ) return;
    List<ESPRowSet> rowList = dataSet.getRowSetList();
    if ( rowList == null || rowList.size() == 0 ) return;
    ESPRowSet rowSet = null;
    for(int i=0;i<rowList.size();i++) {
      rowSet = rowList.get(i);
      String unitid=rowSet.getString("UNIT_ID",null);
      if(unitid==null||unitid.trim().length()==0)unitid="";
      dctMetaData.addSYS_DCT_CST(rowSet.getString("DCT_KEY",null)+"-"+unitid,rowSet.getString("DCT_VALUE",null));
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   * @return EFDataSet
   * @throws Exception
   */
  protected abstract EFDataSet getSYS_DCT_CST(ESPContext ctx, DCTMetaData dctMetaData) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param dctID String
   * @param dctMetaData DCTMetaData
   */
  protected final void addContextDCTMap(ESPContext ctx, String dctID,
                                        DCTMetaData dctMetaData) {
    java.util.Map dctMap = (java.util.Map) ctx.getObject("DCT_MAP", null);
    if (dctMap == null) {
      dctMap = new java.util.HashMap();
      ctx.putObject("DCT_MAP", dctMap);
    }
    dctMap.put(dctID, dctMetaData);
  }
    public void addContextREF(ESPContext ctx,String id, MetaData md){
        addContextDCTMap(ctx,id,(DCTMetaData)md);
    }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected void loadCustomDCT(ESPContext ctx,DCTMetaData dctMetaData) {
    // 装入数据字典的自定义属性信息
    loadSYS_DCT_CST(ctx,dctMetaData);
    // 装入数据字典的自定义内容信息
    loadSYS_DCT_CTN_CST(ctx,dctMetaData);
  }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected final void loadDOMetaData(ESPContext ctx,DCTMetaData dctMetaData) throws Exception{
    // 装入此数据字典的数据对象元数据
    DOMetaData doMetaData = ((DOMetaDataManager)DOMetaDataManager.getInstance(MetaDataManager._DataObject_ + "_DAL")).getDOMetaData(ctx,dctMetaData.getObjID());
    // 设置到DCTMetaData元数据中
    if ( doMetaData != null ) {
      dctMetaData.setDoMetaData(doMetaData);
      doMetaData.setDCTMetaData(dctMetaData);
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected void loadFKEY_DCT(ESPContext ctx,DCTMetaData dctMetaData) throws Exception{
    DOMetaData doMetaData = dctMetaData.getDoMetaData();
    if ( doMetaData == null ) return;
    DOMetaData[] dos = doMetaData.getFKeyMetaDatas();
    if ( dos == null || dos.length == 0 ) return;
    for(int i=0;i<dos.length;i++) {
      // 装入外键对象的数据字典信息
      if(dos[i]==null)continue;
      DCTMetaData fkeyDCTMetaData = (DCTMetaData)getMetaData(ctx,dos[i].getObjID());
      dctMetaData.addFKEYDCTMetaData(dos[i].getObjID(),fkeyDCTMetaData);
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected void loadREF_DCT(ESPContext ctx,DCTMetaData dctMetaData) throws Exception{
    DOMetaData doMetaData = dctMetaData.getDoMetaData();
    if ( doMetaData == null ) return;
    doMetaData = doMetaData.getRefObject();
    if ( doMetaData == null ) return;
    DCTMetaData refDCTMetaData = (DCTMetaData)getMetaData(ctx,doMetaData.getObjID());
    dctMetaData.setRefDCTObject(refDCTMetaData);
  }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected abstract void loadDCT_RLGL(ESPContext ctx,DCTMetaData dctMetaData) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   * @throws Exception
   */
  protected void loadRLGL_DCT(ESPContext ctx,DCTMetaData dctMetaData) throws Exception {
    EFDataSet dataSet = dctMetaData.getDataSet("SYS_RLGL");
    if ( dataSet == null ) return;
    List<ESPRowSet> rowSetList = dataSet.getRowSetList();
    if ( rowSetList == null || rowSetList.size() == 0 ) return;
    ESPRowSet rowSet = null;
    for(int i=0;i<rowSetList.size();i++) {
      rowSet = rowSetList.get(i);
      String RLGL_ID = rowSet.getString("RLGL_ID",null);
      String DCT_ID1 = rowSet.getString("DCT_ID1",null);
      if ( DCT_ID1 != null ) {
        DCTMetaData dctMetaData1 = (DCTMetaData)getMetaData(ctx,DCT_ID1.trim());
        if ( dctMetaData1 != null )
          dctMetaData.addRLGLDCT(RLGL_ID,dctMetaData1);
      }
      String RLGL_OBJID = rowSet.getString("RLGL_OBJID",null);
      if ( RLGL_OBJID != null ) {
        DOMetaData doMetaData = DOMetaDataManager.getInstance().getDOMetaData(ctx,RLGL_OBJID);
        if ( doMetaData != null )
          dctMetaData.addRLGLOBJ(RLGL_ID,doMetaData);
      }
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   * @throws Exception
   */
  protected void loadRLGL_OBJ(ESPContext ctx,DCTMetaData dctMetaData) throws Exception {

  }
  /**
   *
   * @param ctx ESPContext
   * @param dctID String
   * @return DCTMetaData
   */
  protected abstract DCTMetaData loadSYS_DICTS(ESPContext ctx,String dctID) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected abstract void loadSYS_DCT_CST(ESPContext ctx,DCTMetaData dctMetaData);
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   */
  protected abstract void loadSYS_DCT_CTN_CST(ESPContext ctx,DCTMetaData dctMetaData);
  /**
   *
   * @param ctx ESPContext
   * @param dctMetaData DCTMetaData
   * @throws Exception
   */
  protected abstract void loadACJGSZ(ESPContext ctx,DCTMetaData dctMetaData) throws Exception;
}
