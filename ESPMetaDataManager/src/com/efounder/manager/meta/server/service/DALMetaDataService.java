package com.efounder.manager.meta.server.service;

import java.sql.*;

import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.eai.data.*;
import com.efounder.eai.framework.*;
import com.efounder.sql.*;


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
public class DALMetaDataService  extends JActiveObject {
  /**
   *
   */
  public DALMetaDataService() {
  }
  /**
   *
   * @param paramObject JParamObject
   * @param o3 Object
   * @param o4 Object
   * @return MetaData
   * @throws Exception
   */
  public JResponseObject getMetaData(JParamObject paramObject,Object o2,Object o3,Object o4) throws Exception {
    Object[] returnValues = getMetaDatas(paramObject);;
    MetaData metaData     = (MetaData)returnValues[0];
    java.util.Map refMap  = (java.util.Map)returnValues[1];
    JResponseObject responseObject = null;String UNIT_ID = paramObject.GetValueByEnvName("UNIT_ID",null);
    if ( metaData != null ) {
      responseObject = new JResponseObject(metaData);
      if ( refMap != null && refMap.size() > 0 ) {
        java.util.Map pubMetaMap = new java.util.HashMap();
        java.util.Map priMetaMap = new java.util.HashMap();
        Object[] mes = refMap.values().toArray();
        for(int i=0;i<mes.length;i++) {
          MetaData refMetaData = (MetaData)mes[i];
          java.util.Map map = refMetaData.getExtendProperty();
          if ( map != null ) pubMetaMap.put(refMetaData,map);
          if ( UNIT_ID != null && UNIT_ID.trim().length() > 0 ) {
            map = refMetaData.getExtendProperty(UNIT_ID);
            if ( map != null ) priMetaMap.put(refMetaData,map);
          }
        }
        responseObject.setResponseObject("pubMetaMap",pubMetaMap);
        responseObject.setResponseObject("priMetaMap",priMetaMap);
      }
    }
    return responseObject;
  }
  /**
   *
   * @param paramObject JParamObject
   * @param metaType String
   * @return MetaData
   * @throws Exception
   */
  private Object[] getMetaDatas(JParamObject paramObject) throws Exception {
    // 获取OBJ_ID
    String objid    = (String)paramObject.getValue("OBJ_ID",null);
    // 获取元数据类型
    String metaType = (String)paramObject.getValue("META_TYPE",null);
    if ( objid == null || metaType == null ) return null;
    // 获取数据库连接
    JConnection conn = JConnection.getInstance(paramObject);
    Statement stmt = null;MetaData metaData = null;java.util.Map refMap = null;
    try {
      // 获取Statement
      stmt = conn.createStatement();
      // 获取Context
      ESPServerContext espContext = ESPServerContext.getInstance(paramObject,conn);
      // 设置客户端调用方式
      espContext.putString("isClient","1");
      espContext.setStatement(stmt);
      MetaDataManager metaDataManager = MetaDataManager.getInstance(metaType + "_DAL");
      // 获取元数据
      metaData = metaDataManager.getMetaData(espContext,objid);
      // 获取参考的对象
      refMap = null;//(java.util.Map)espContext.getObject(MetaDataManager._META_DATA_REF_MAP_,null);

    } catch ( Exception ex ) {
      ex.printStackTrace();
      throw ex;
    } finally {
      conn.BackStatement(stmt,null);
      conn.close();
    }
    return new Object[]{metaData,refMap};
  }


  /**
   *
   * @param paramObject JParamObject
   * @param o2 Object
   * @param o3 Object
   * @param o4 Object
   * @return JResponseObject
   * @throws Exception
   */
  public JResponseObject updateMetaData(JParamObject paramObject,Object o2,Object o3,Object o4) throws Exception {
    JConnection conn = JConnection.getInstance(paramObject);
    Statement stmt = null;
    MetaData metaData = (MetaData) o2;
    try {
      stmt = conn.createStatement();

      ESPServerContext espContext = ESPServerContext.getInstance(paramObject, conn);
      espContext.setStatement(stmt);

      String metaType = (String) paramObject.getValue("META_TYPE", null);
      MetaDataManager metaDataManager = MetaDataManager.getInstance(metaType);
      Object resultObj= metaDataManager.updateMetaData(espContext, metaData);

      return new JResponseObject(resultObj);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new JResponseObject(null, JResponseObject.RES_ERROR, ex);
    } finally {
      conn.BackStatement(stmt, null);
      conn.close();
    }
  }
}
