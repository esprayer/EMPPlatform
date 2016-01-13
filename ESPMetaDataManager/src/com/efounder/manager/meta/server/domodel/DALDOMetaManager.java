package com.efounder.manager.meta.server.domodel;

import java.sql.*;
import java.util.*;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.manager.meta.server.service.*;
import com.efounder.db.DBTools;
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
public class DALDOMetaManager extends DOMetaDataManager{
  /**
   *
   * @param eSPContext ESPContext
   * @param dOMetaData DOMetaData
   */
  protected void loadKEYS(ESPContext eSPContext, DOMetaData dOMetaData) {
  }
  
  /**
   *
   * @param objid String
   * @return MetaData
   */
  public MetaData getMetaData(String objid)  throws Exception {
	  java.util.List modelList = PackageStub.getContentVector("BIZDOList");
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
   * @param eSPContext ESPContext
   * @param dOMetaData DOMetaData
   * @return EFDataSet
   */
  protected EFDataSet loadSYS_OBJCOLS(ESPContext espContext, DOMetaData doMetaData) throws Exception {
    EFDataSet dataSet = null;
    dataSet = doMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_);
    if ( dataSet != null ) return dataSet;
    ESPServerContext espServerCtx = (ESPServerContext)espContext;
    String tableName = "SYS_OBJCOLS";String fields = "*";
//    String where = "OBJ_ID='"+doMetaData.getObjID()+"'";
    // add ordery by COL_DISP by skyline 2011.12.15
    String where = "OBJ_ID='"+doMetaData.getObjID()+"' order by COL_DISP ";
//    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    // 元数据多语言 gengeng 2011-5-23
    ResultSet resultSet = null;
    if (Arrays.asList(ESPMetaDataUtil._MLANG_METATABLE_).contains(doMetaData.getObjID()))
      resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    else
      resultSet = ESPMetaDataUtil.getMetaDataResultSet(espServerCtx, tableName,fields, where);
    dataSet = EFDataSet.getInstance(tableName);
		try {
			dataSet.setPrimeKey(new String[] { SYS_OBJCOLS._COL_ID_ });
			DataSetUtils.resultSet2DataSet(resultSet, dataSet);
			// 装入编辑方式定义为自列表的列的自列表数据 gengeng 2011-11-21
			loadSYS_SELFENUM(espContext, doMetaData, dataSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			resultSet.close();
		}
		return dataSet;
  }

  /**
   *
   * @param espContext ESPContext
   * @param doMetaData DOMetaData
   * @param dataSet EFDataSet
   */
  protected void loadSYS_SELFENUM(ESPContext espContext, DOMetaData doMetaData, EFDataSet dataSet) {
//    ESPServerContext espServerCtx = (ESPServerContext)espContext;
//    ResultSet rs = null;
//    try {
//      String table = "SYS_SELFENUM";
//      String field = "*";
//      String where = "OBJ_ID = '" + doMetaData.getObjID() + "' and F_SYZT = '1'";
//      rs = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), table, field, where);
//      EFDataSet ds = EFDataSet.getInstance("");
//      ds.setPrimeKey(new String[]{"COL_ID", "COL_DISP"});
//      DataSetUtils.resultSet2DataSet(rs, dataSet);
//      // 组织成特定格式放到每个列上
//      ESPMetaDataUtil.setSYS_SELFENUM(dataSet, ds);
//    } catch(Exception e) {
//      e.printStackTrace();
//    } finally {
//      espServerCtx.getConnection().BackStatement(null, rs);
//    }
  }

  /**
   *
   * @param eSPContext ESPContext
   * @param string String
   * @return DOMetaData
   */
  protected DOMetaData loadSYS_OBJECTS(ESPContext espContext, String doID) throws Exception  {
    ESPServerContext espServerCtx = (ESPServerContext)espContext;
    // 新建一个DOMetaData
    DOMetaData doMetaData = null;
    String tableName = "SYS_OBJECTS";String fields = "*";
    String where = "OBJ_ID='"+doID+"'";
//    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    // 元数据多语言 gengeng 2011-5-23
    ResultSet resultSet = null;
    if (Arrays.asList(ESPMetaDataUtil._MLANG_METATABLE_).contains(doID))
      resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    else
      resultSet = ESPMetaDataUtil.getMetaDataResultSet(espServerCtx, tableName,fields, where);
    try {
      if (resultSet.next()) {
        doMetaData = DOMetaData.getInstance(doID);
        DataSetUtils.resultSet2RowSet(resultSet, doMetaData);
      }
    } catch ( Exception ex ) {
      ex.printStackTrace();throw ex;
    } finally {
      resultSet.close();
    }
    return doMetaData;
  }
  /**
   *
   * @param ctx ESPContext
   * @param doMetaData DOMetaData
   * @throws Exception
   */
  protected void loadExtPropery(ESPContext espContext, DOMetaData doMetaData) throws Exception {
      if(doMetaData.getExtendProperty()!=null)return ;
    ESPServerContext espServerCtx = (ESPServerContext)espContext;
    String tableName = "SYS_OBJ_VAL";String fields = "*";
    String where = "OBJ_ID='"+doMetaData.getObjID()+"'";
    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    try {
      java.util.Map pMap = null;
      while ( resultSet.next() ) {
        if ( pMap == null ) pMap = new HashMap();
        String key   = resultSet.getString("OBJ_KEY");
        String value = resultSet.getString("OBJ_VAL");
        pMap.put(key,value);
      }
      if ( pMap != null ) doMetaData.setExtendProperty(pMap);
    } catch ( Exception ex ) {
      ex.printStackTrace();throw ex;
    } finally {
      resultSet.close();
    }
  }
  /**
   *
   * @param espContext ESPContext
   * @param doMetaData DOMetaData
   * @return EFDataSet
   * @throws Exception
   */
  protected EFDataSet getSYS_OBJ_VAL(ESPContext espContext, DOMetaData doMetaData) throws Exception {
    ESPServerContext espServerCtx = (ESPServerContext)espContext;EFDataSet dataSet = null;
    String tableName = "SYS_OBJ_VAL";String fields = "*";
     dataSet = doMetaData.getSYS_OBJ_VAL_DS();
    if ( dataSet != null ) return dataSet;
    String where = "OBJ_ID='"+doMetaData.getObjID()+"'";
    ResultSet resultSet = DataSetUtils.getResultSet(espServerCtx.getParamObject(),espServerCtx.getStatement(), tableName, fields,where);
    dataSet = EFDataSet.getInstance(tableName);
    dataSet.setPrimeKey(new String[]{"OBJ_KEY"});
    try {
      DataSetUtils.resultSet2DataSet(resultSet, dataSet);
      doMetaData.setSYS_OBJ_VAL_DS(dataSet);
    } catch ( Exception ex ) {ex.printStackTrace();throw ex;} finally {resultSet.close();}
    return dataSet;
  }

  /**	
   *
   * @param metaData MetaData
   * @return Object
   * @throws Exception
   */
  public Object updateMetaData(ESPContext ctx, MetaData metaData) throws Exception {
    ESPServerContext espContext = (ESPServerContext) ctx;

    return null;
  }

}
