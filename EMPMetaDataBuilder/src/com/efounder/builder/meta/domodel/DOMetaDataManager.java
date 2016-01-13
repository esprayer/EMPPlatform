package com.efounder.builder.meta.domodel;

import java.util.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.eai.data.JParamObject;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.builder.meta.iomodel.IOMetaDataManager;
import com.efounder.builder.meta.iomodel.IOMetaData;

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
public abstract class DOMetaDataManager
    extends MetaDataManager {
  /**
   *
   * @return DOMetaManager
   */
  public static final DOMetaDataManager getInstance() {
    return (DOMetaDataManager)getInstance(MetaDataManager._DataObject_);
  }
  /**
   *
   * @param objid String
   * @return MetaData
   */
  public MetaData getMetaData(String objid)  throws Exception {
    java.util.List modelList = PackageStub.getContentVector("BIZDOList");
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
   * @param ctx ESPContext
   * @param objid String
   * @return DOMetaData
   */
  public final DOMetaData getDOMetaData(String objid)throws Exception {
    return getDOMetaData(null,objid);
  }
  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return DOMetaData
   */
  public final DOMetaData getDOMetaData(ESPContext ctx,String objid)throws Exception {
    return (DOMetaData)getMetaData(ctx,objid);
  }
  /**
   *
   * @param ctx MetaContext
   * @param objid String
   * @return MetaData
   */
  protected MetaData getMetaDataModel(ESPContext ctx, String objid,MetaData metaData) throws Exception {
    // 创建数据对象的元数据对象
    DOMetaData doMetaData = null;
    if ( metaData == null ) {
      doMetaData = loadSYS_OBJECTS(ctx, objid);
    } else
      doMetaData = (DOMetaData)metaData;
    if (doMetaData == null) {
      return null;
    }
    processReferenceMetaData(ctx,objid,doMetaData);
    // 装入主键定义
    loadKEYS(ctx, doMetaData);
    // 装入对象的所有列定义
    EFDataSet colDataSet = loadSYS_OBJCOLS(ctx, doMetaData);
    if (colDataSet != null) {
      // 设置到doMetaData
      doMetaData.setDataSet(SYS_OBJCOLS._SYS_OBJCOLS_, colDataSet);
      // 装入对象所有引用的外键对象
      loadFKEYS(ctx, doMetaData);
      // 装入参考数据对象的元数据
      loadRefObject(ctx, doMetaData);
    }
    //
    EFDataSet dataSet = getSYS_OBJ_VAL(ctx,doMetaData);
    if( dataSet != null ) {
      initSYS_OBJ_VAL(dataSet,doMetaData);
    }
    // 装入接口元数据

//    loadIOMetaData(ctx, doMetaData);
    return doMetaData;
  }
  /**
   *
   * @param dataSet EFDataSet
   * @param doMetaData DOMetaData
   */
  protected void initSYS_OBJ_VAL(EFDataSet dataSet,DOMetaData doMetaData) {
    if ( dataSet == null ) return;
    List<ESPRowSet> rowList = dataSet.getRowSetList();
    if ( rowList == null || rowList.size() == 0 ) return;
    ESPRowSet rowSet = null;
    for(int i=0;i<rowList.size();i++) {
      rowSet = rowList.get(i);
      doMetaData.addSYS_OBJ_VAL(rowSet.getString("OBJ_KEY",null),rowSet.getString("OBJ_VALUE",null));
    }
  }
  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   * @return EFDataSet
   * @throws Exception
   */
  protected abstract EFDataSet getSYS_OBJ_VAL(ESPContext ctx, DOMetaData doMetaData) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   */
  protected final void loadRefObject(ESPContext ctx, DOMetaData doMetaData) throws Exception{
    String REF_ID = getRefID(doMetaData);
    if (REF_ID == null) {
      return;
    }
    DOMetaData refMetaData = (DOMetaData) getMetaData(ctx, REF_ID);
    doMetaData.setRefObject(refMetaData);
  }

  /**
   *
   * @param rowSet ESPRowSet
   * @return String
   */
  protected final String getRefID(ESPRowSet rowSet) {
    return rowSet.getString(SYS_OBJECTS._REF_ID_, null);
  }
  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   */
  protected abstract EFDataSet loadSYS_OBJCOLS(ESPContext ctx,
                                               DOMetaData doMetaData) throws Exception;

  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   */
  protected final void loadFKEYS(ESPContext ctx, DOMetaData doMetaData)throws Exception {
    // 获取对象的列DataSet
    EFDataSet colDataSet = doMetaData.getDOColumns();
    // RowSet
    List<ESPRowSet> rowSetList = colDataSet.getRowSetList();
    ESPRowSet rowSet = null;
    String fkeyColID = null, fkeyObjID = null;
    // 处理每一列
    for (int i = 0; rowSetList != null && i < rowSetList.size(); i++) {
      rowSet = rowSetList.get(i);
      // 获取列ID
      fkeyColID = getFKEYColID(rowSet);
      // 获取外键对象
      fkeyObjID = getFKEYObjID(rowSet);
      // 如果外键列及外键对象都不为空，则获取对象的元数据
      if (fkeyObjID != null && fkeyObjID.trim().length()>0 && !fkeyObjID.equals(doMetaData.getObjID()) ) {
        // 调用元数据管理对象，获取数据
        DOMetaData fkeyMetaData = (DOMetaData) this.getMetaData(ctx, fkeyObjID);
        // 增加到宿主对象中
        if(fkeyMetaData!=null)
            doMetaData.addFKEYMetaData(fkeyColID, fkeyMetaData);
      }
    }
  }

  /**
   *
   * @param rowSet ESPRowSet
   * @return String
   */
  protected final String getFKEYColID(ESPRowSet rowSet) {
    return rowSet.getString(SYS_OBJCOLS._COL_ID_, null);
  }

  /**
   *
   * @param rowSet ESPRowSet
   * @return String
   */
  protected final String getFKEYObjID(ESPRowSet rowSet) {
    return rowSet.getString(SYS_OBJCOLS._COL_FOBJ_, null);
  }
  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   */
  protected abstract void loadExtPropery(ESPContext ctx, DOMetaData doMetaData) throws Exception;
  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   */
  protected abstract void loadKEYS(ESPContext ctx, DOMetaData doMetaData) throws Exception ;

  /**
   *
   * @param ctx ESPContext
   * @param objid String
   * @return DOMetaData
   */
  protected abstract DOMetaData loadSYS_OBJECTS(ESPContext ctx, String objid)throws Exception ;

  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   * @throws Exception
   */
  protected void loadIOMetaData(ESPContext ctx, DOMetaData doMetaData) throws Exception {
    IOMetaData ioMetaData=doMetaData.getIOMetaData();
    if(ioMetaData==null)
      ioMetaData = (IOMetaData) IOMetaDataManager.getInstance().getMetaData(ctx,doMetaData.getObjID());
    if (ioMetaData != null ) {
      doMetaData.setIOMetaData(ioMetaData);
      ioMetaData.setDOMetaData(doMetaData);
    }
  }

}
