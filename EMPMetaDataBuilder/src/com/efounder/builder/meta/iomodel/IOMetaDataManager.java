package com.efounder.builder.meta.iomodel;

import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.eai.data.JParamObject;
import com.efounder.builder.base.data.EFDataSet;

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
public abstract class IOMetaDataManager extends MetaDataManager {

  /**
   *
   */
  public IOMetaDataManager() {
  }

  /**
   *
   * @return DOMetaManager
   */
  public static final IOMetaDataManager getInstance() {
    return (IOMetaDataManager)getInstance(MetaDataManager._IObject_);
  }

  /**
   *
   * @param objid String
   * @return MetaData
   * @throws Exception
   * @todo Implement this com.efounder.builder.meta.MetaDataManager method
   */
  public MetaData getMetaData(String objid) throws Exception {
    java.util.List modelList = PackageStub.getContentVector("BIZIOList");
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
   * @param ctx MetaContext
   * @param objid String
   * @param metaData MetaData
   * @return MetaData
   * @throws Exception
   * @todo Implement this com.efounder.builder.meta.MetaDataManager method
   */
  protected MetaData getMetaDataModel(ESPContext ctx, String objid, MetaData metaData) throws Exception {
    IOMetaData ioMetaData = null;
    if ( metaData == null ) ioMetaData = IOMetaData.getInstance(objid);
    else ioMetaData = (IOMetaData)metaData;
    if (ioMetaData == null) return null;
    EFDataSet iODataSet = loadSYS_IOCOLSET(ctx, objid);
    ioMetaData.setIODataSet(iODataSet);
    return ioMetaData;
  }

  /**
   *
   *
   * @param ctx ESPContext
   * @param objid String
   * @return EFDataSet
   */
  protected abstract EFDataSet loadSYS_IOCOLSET(ESPContext ctx, String objid) throws Exception;
}
