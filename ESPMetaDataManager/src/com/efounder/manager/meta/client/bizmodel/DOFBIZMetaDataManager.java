package com.efounder.manager.meta.client.bizmodel;

import com.efounder.builder.meta.*;
import com.efounder.builder.base.util.*;
import com.efounder.eai.data.*;
import com.efounder.eai.*;
import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.builder.meta.bizmodel.BIZMetaData;

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
public class DOFBIZMetaDataManager extends ClientMetaDataManager {
  /**
   *
   */
  public DOFBIZMetaDataManager() {
  }
  /**
   *
   * @param objid String
   * @return MetaData
   */
  public MetaData getMetaData(String objid)  throws Exception {
    java.util.List modelList = PackageStub.getContentVector("BIZModelList");
    JParamObject po = JParamObject.Create();
    for(int i=0;(modelList!=null && i<modelList.size());i++) {
      StubObject stub = (StubObject)modelList.get(i);
      if ( !objid.equals(stub.getID()) ) continue;
      po.setEAIServer(stub.getString("eaiServer",null));
    }
    ESPContext espContext = ESPClientContext.getInstance(po);
    return getMetaData(espContext,objid);
  }

  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return MetaData
   */
  protected MetaData getMetaDataModel(ESPContext ctx, String objid,MetaData metaData) throws Exception {
    JParamObject paramObject = ((ESPClientContext)ctx).getParamObject();
    paramObject.setValue("OBJ_ID",objid);
    paramObject.setValue("META_TYPE",MetaDataManager._BIZModel_);
    JResponseObject ro = (JResponseObject)EAI.DOF.IOM("BZMetaDataService","getMetaData",paramObject);
    if(ro==null)return null;
//    MetaData metaData = null;
    metaData = (MetaData)ro.getResponseObject();
    BIZMetaData bizMetaData = (BIZMetaData)metaData;
    processDCTMetaData(ctx,bizMetaData);
    processFCTMetaData(ctx,bizMetaData);
    return metaData;
  }

  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   */
  protected void processDCTMetaData(ESPContext ctx,BIZMetaData bizMetaData) {
    String[] DCTIDs = bizMetaData.getRefDCTIDs();
    if ( DCTIDs == null || DCTIDs.length == 0 ) return;
    for(int i=0;i<DCTIDs.length;i++) {
//      String key = DCTIDs[i]+"_"+ctx.getDBUniqueID();
      String key = DCTIDs[i]+"_"+ctx.getDBUniqueID()+"_"+ctx.getParamObject().GetValueByEnvName("Language");
      MetaDataManager.getDCTMetaDataManager().addMetaData(key,bizMetaData.getRefDCTMetaData(DCTIDs[i]));
      // DOMetadata
      if (bizMetaData.getRefDCTMetaData(DCTIDs[i]) != null)
        MetaDataManager.getDODataManager().addMetaData(key,bizMetaData.getRefDCTMetaData(DCTIDs[i]).getDoMetaData());
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param bizMetaData BIZMetaData
   */
  protected void processFCTMetaData(ESPContext ctx,BIZMetaData bizMetaData) {
    String[] FCTIDs = bizMetaData.getRefFCTIDs();
    if ( FCTIDs == null || FCTIDs.length == 0 ) return;
    for(int i=0;i<FCTIDs.length;i++) {
//      String key = FCTIDs[i]+"_"+ctx.getDBUniqueID();
      String key = FCTIDs[i]+"_"+ctx.getDBUniqueID()+"_"+ctx.getParamObject().GetValueByEnvName("Language");
      MetaDataManager.getFCTMetaDataManager().addMetaData(key,bizMetaData.getRefFCTMetaData(FCTIDs[i]));
      // DOMetadata
      if (bizMetaData.getRefFCTMetaData(FCTIDs[i]) != null)
        MetaDataManager.getDODataManager().addMetaData(key,bizMetaData.getRefFCTMetaData(FCTIDs[i]).getDoMetaData());
    }
  }

  /**
   *
   * @param metaData MetaData
   * @return Object
   * @throws Exception
   */
  public Object updateMetaData(MetaData metaData) throws Exception {
    java.util.List modelList = PackageStub.getContentVector("BIZModelList");
    JParamObject po = JParamObject.Create();
    for(int i=0;(modelList!=null && i<modelList.size());i++) {
      StubObject stub = (StubObject)modelList.get(i);
      if (!metaData.getObjID().equals(stub.getID()) ) continue;
      po.setEAIServer(stub.getString("eaiServer",null));
    }
    ESPContext espContext = ESPClientContext.getInstance(po);
    return updateMetaData(espContext, metaData);
  }

  /**
   *
   * @param metaData MetaData
   * @return Object
   * @throws Exception
   */
  public Object updateMetaData(ESPContext ctx, MetaData metaData) throws Exception {
    return updateMetaData(MetaDataManager._BIZModel_, ctx, metaData);
  }

}
