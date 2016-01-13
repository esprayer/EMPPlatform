package com.efounder.manager.meta.client.service;

import com.efounder.eai.data.*;
import com.efounder.eai.framework.*;
import com.efounder.builder.meta.*;
import com.efounder.eai.*;

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
public class DOFMetaDataService extends JActiveObject {
  /**
   *
   */
  public DOFMetaDataService() {
  }
  /**
   *
   * @param paramObject JParamObject
   * @param o2 Object
   * @param o3 Object
   * @param o4 Object
   * @return MetaData
   * @throws Exception
   */
  public JResponseObject getMetaData(JParamObject paramObject,Object o2,Object o3,Object o4) throws Exception {
    MetaData metaData = null;
    JResponseObject ro = (JResponseObject)EAI.DAL.IOM("BZMetaDataService","getMetaData",paramObject);
    if ( ro != null ) {
      String UNIT_ID = paramObject.GetValueByEnvName("UNIT_ID",null);
      metaData = (MetaData) ro.getResponseObject();
      java.util.Map pubMetaMap = (java.util.Map)ro.getResponseObject("pubMetaMap");
      MetaData refMetaData = null;java.util.Map map = null;
      if ( pubMetaMap != null && pubMetaMap.size() > 0 ) {
        Object[] keys = pubMetaMap.keySet().toArray();
        for(int i=0;i<keys.length;i++) {
          refMetaData = (MetaData)keys[i];
          map = (java.util.Map)pubMetaMap.get(keys[i]);
          refMetaData.setExtendProperty(map);
        }
      }
      java.util.Map priMetaMap = (java.util.Map)ro.getResponseObject("priMetaMap");
      if ( pubMetaMap != null && pubMetaMap.size() > 0 && UNIT_ID != null && UNIT_ID.trim().length() > 0 ) {
        Object[] keys = priMetaMap.keySet().toArray();
        for(int i=0;i<keys.length;i++) {
          refMetaData = (MetaData)keys[i];
          map = (java.util.Map)priMetaMap.get(keys[i]);
          refMetaData.setExtendProperty(UNIT_ID,map);
        }
      }
    }
    return ro;
  }
}
