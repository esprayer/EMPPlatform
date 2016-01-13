package com.efounder.builder.meta;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.util.ESPClientContext;
import com.efounder.builder.base.util.ESPContext;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;

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
public abstract class ClientMetaDataManager extends MetaDataManager {
  /**
   *
   */
  public ClientMetaDataManager() {
  }
  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return MetaData
   * @throws Exception
   */
  public MetaData getMetaData(ESPContext ctx,String objid)  throws Exception  {
    if ( objid == null || objid.trim().length() == 0 ) return null;
    // 初始化元数据缓冲区
    initMetaDataBuffer();
    // 创建缓冲区中的数据Key
    String dataKey = objid+"_"+ctx.getDBUniqueID()+"_"+ctx.getParamObject().GetValueByEnvName("Language");
    // 首先从缓冲区里取数
    MetaData metaData = (MetaData)metaDataBufferManger.getDataBuffer(metaTypeKey).getData(dataKey);
    if ( metaData == null ) {
      // 为空的，从数据库中装入
      metaData = getMetaDataModel(ctx, objid,metaData);
      // 如果在缓冲中不存在，则放入，放入缓冲区中
      metaDataBufferManger.getDataBuffer(metaTypeKey).addData(dataKey,metaData);
      JParamObject paramObject = ctx.getParamObject();
      // 设置元数据所在的数据库
      if (metaData != null) {
        metaData.setDataBaseName(paramObject.GetValueByEnvName("DataBaseName", null));
        metaData.setDBNO(paramObject.GetValueByEnvName("DBNO", null));
      } else
        System.out.println("- |||| " + objid + "'s metaData is null.");
    }
    return metaData;
  }

  /**
   * This method is used to manage(insert/update) the object's metadata.
   *
   * @param metaData MetaData
   * @return Object
   * @throws Exception
   */
  public abstract Object updateMetaData(MetaData metaData) throws Exception;

  /**
   *
   * @param ctx ESPContext
   * @param metaData MetaData
   * @return Object
   * @throws Exception
   */
  public Object updateMetaData(String metaType, ESPContext ctx, MetaData metaData) throws Exception {
    JParamObject paramObject = ((ESPClientContext)ctx).getParamObject();
    paramObject.setValue("OBJ_ID",metaData.getObjID());
    paramObject.setValue("META_TYPE", metaType);
    JResponseObject RO = (JResponseObject)EAI.DOF.IOM("BZMetaDataService","updateMetaData",paramObject, metaData);
    if (RO == null) return null;
    // 更新成功
    if (RO.getErrorCode() == RO.RES_OK)
      metaData.setDataStatus(EFRowSet._DATA_STATUS_NORMAL_);
    return RO.getResponseObject();
  }

}
