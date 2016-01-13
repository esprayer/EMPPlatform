package com.efounder.eai.service;

import java.sql.Statement;
import com.efounder.builder.base.util.ESPServerContext;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.sql.JConnection;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.buffer.DictDataBuffer;
import com.efounder.eai.data.*;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MDMDataUtil {
  public MDMDataUtil() {
  }
  public  static EFDataSet getMainDctData(String objid,boolean bload){
    return getMainDctData(null,objid,bload);
  }
  public  static EFDataSet getTableData(String objid,boolean bload){
   return getTableData(null,objid,null,bload);
 }
 public  static EFDataSet getTableData(JParamObject PO,String objid,String where,boolean bload){
   String key=objid;
   if(where!=null)key+="-"+where;
    EFDataSet eds = (EFDataSet) DictDataBuffer.getDefault(PO).getDataItem(DictDataBuffer.
        DCTDATASET, key);
    if(eds==null&&bload){
       eds=DictDataBuffer.getDefault(PO).QueryData(PO,objid,null,where);
       if(eds!=null&&eds.getRowCount()>1)//只有一条数就别缓存了  没办法，这个方法用烂了
    	   DictDataBuffer.getDefault(PO).addDataItem(DictDataBuffer.DCTDATASET, key, eds);
    }
    return eds;
  }

  public  static EFDataSet getTableData(JParamObject PO,String objid,String where,String[] keys, boolean bload){
  String key=objid;
  if(where!=null)key+="-"+where;
   EFDataSet eds = (EFDataSet) DictDataBuffer.getDefault(PO).getDataItem(DictDataBuffer.DCTDATASET, key);
   if(eds==null&&bload){
      eds=DictDataBuffer.getDefault(PO).QueryData(PO,objid,null,where);
      if (eds != null && keys != null) {
        eds.setPrimeKey(keys);
        eds.buildPrimeKeyIndex();
      }
      if(eds!=null&&eds.getRowCount()>1)//只有一条数就别缓存了  没办法，这个方法用烂了
    	  DictDataBuffer.getDefault(PO).addDataItem(DictDataBuffer.DCTDATASET, key, eds);
   }
   return eds;
 }


  public  static EFDataSet getMainDctData(JParamObject PO,String objid,boolean bload){
    EFDataSet eds = (EFDataSet) DictDataBuffer.getDefault(PO).getDataItem(
      DictDataBuffer.DCTDATASET, objid);
    //如果从缓存里取到且不需要刷新，则直接返回
    if (eds != null && !bload) return eds;
    if(PO==null)PO=JParamObject.Create();
//    String defmodel = ParameterManager.getDefault().getDefaultModelName();
//    String modlid = PO.GetValueByParamName("MDL_ID",defmodel);
    try {
//      EFMDMDataModel efm = MasterDataManager.getDefault().getMDMDataModel(PO, modlid, objid);
//      if (efm != null)
//        eds = efm.getDataSet(objid);
      if (eds == null ) eds = EFDataSet.getInstance(objid);
      // masterDataManager里已经有主数据缓存了
      if (eds != null) {
          DictDataBuffer.getDefault(PO).addDataItem(DictDataBuffer.DCTDATASET, objid, eds);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return eds;
  }

}
