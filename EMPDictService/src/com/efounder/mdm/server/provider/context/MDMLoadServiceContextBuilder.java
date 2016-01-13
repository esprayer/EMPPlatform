package com.efounder.mdm.server.provider.context;

import com.efounder.builder.base.util.*;
import com.efounder.bz.service.*;
import com.efounder.eai.data.*;
import com.efounder.sql.*;
import com.efounder.mdm.server.MDMContext;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.mdm.EFMDMDataModel;
import java.sql.*;
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
public class MDMLoadServiceContextBuilder extends ServiceContextBuilder {
  /**
   * 数据字典的装入方式：
   * 1.简单装入符合条件的用户字典数据
   * 2.简单装入符合条件并有具有权限的用户字典数据
   * 3.以某项关系方式装入数据字典数据
   * 4.以某项关系方式装入数据字典数据
   */
  public MDMLoadServiceContextBuilder() {
  }

  /**
   *
   * @param espContext ESPServerContext
   * @return Object
   * @throws Exception
   * @todo Implement this com.efounder.bz.service.ServiceContextBuilder method
   */
  public Object createResponse(ESPServerContext espContext) throws Exception {
    MDMContext mdmContext = (MDMContext)espContext;
    // 获取业务模型ID
    String bizID = espContext.getParamObject().GetValueByParamName("MDL_ID");
    String DCT_ID = espContext.getParamObject().GetValueByParamName("DCT_ID");
    // 获取元数据
    BIZMetaData bizMetaData = (BIZMetaData) MetaDataManager.getInstance(MetaDataManager._BIZModel_ + "_DAL").getMetaData(mdmContext, bizID);
    // 如果元数据为空，则直接返回
    DCTMetaData dctMetaData = null;
    if ( bizMetaData != null ) {
      dctMetaData = bizMetaData.getDCTMetaData(DCT_ID);
    }
    if(dctMetaData==null)
      dctMetaData = (DCTMetaData) MetaDataManager.getInstance(MetaDataManager._DCTObject_ + "_DAL").getMetaData(mdmContext, DCT_ID);
    mdmContext.setDCTMetaData(dctMetaData);
    // 将元数据设置到上下文环境
    mdmContext.setBIZMetaData(bizMetaData);
    // 创建formModel
    EFMDMDataModel mdmModel = (EFMDMDataModel) espContext.getDataObject();
    if (mdmModel == null)
      mdmModel = EFMDMDataModel.getInstance();
    mdmModel.setMDL_ID(bizID);
    // 在这里需要判断是否返回字典的元数据，如果是1，则需要将元数据也返回
    if ( "1".equals(espContext.getParamObject().GetValueByParamName("isGetMetaData","0")) ) {
      // 将DCT_ID的元数据返回
      mdmModel.setValue("metaData",dctMetaData);
    }
    return mdmModel;
  }

  /**
   *
   * @param espContext ESPServerContext
   * @param response Object
   * @todo Implement this com.efounder.bz.service.ServiceContextBuilder method
   */
  protected void finishContext(ESPServerContext espContext, Object response) {
    if ( espContext.getStatement() != null )
      try {
        espContext.getStatement().close();
      }
      catch (SQLException ex) {
      }
  }

  /**
   *
   * @param paramObject JParamObject
   * @param connection JConnection
   * @param dataObject Object
   * @param customObject Object
   * @param addinObject Object
   * @param runType int
   * @return ESPServerContext
   * @throws Exception
   * @todo Implement this com.efounder.bz.service.ServiceContextBuilder method
   */
  protected ESPServerContext newContext(JParamObject paramObject, JConnection connection, Object dataObject, Object customObject, Object addinObject, int runType) throws Exception {
    MDMContext mdmContext = MDMContext.getInstance(paramObject,connection);
    mdmContext.setDataObject(dataObject);
    mdmContext.setCustomObject(customObject);
    mdmContext.setAddinObject(addinObject);
    // 设置Statement
    Statement statement = connection.createStatement();
    mdmContext.setStatement(statement);
    return mdmContext;
  }
}
