package com.efounder.buffer;

import java.util.*;

import com.core.xml.*;
import com.efounder.builder.base.data.*;
import com.efounder.eai.data.*;
import com.efounder.eai.service.*;

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

public class DictDataBuffer {
  public static String DCTDATASET = "DCTDATASET";
   static Map dbf=new HashMap();
   DataBufferManager dbm;
    //这个KEY即做为DataBufferManager的KEY，也做为DictDataBuffer在dbf里的key
    public static String getKey(JParamObject po){
//      String key=po.getAppUniqueID()+"-"+"BUFFER";
    	String key="-"+"BUFFER";
      return key;
    }
    public static DictDataBuffer getDefault(){
      return getDefault(JParamObject.Create());
    }

    public static DictDataBuffer getDefault(JParamObject PO) {
        if(PO==null)PO=JParamObject.Create();
        String key=DictDataBuffer.getKey(PO);
        DictDataBuffer ddb=(DictDataBuffer) dbf.get(key);
        if (ddb == null){
          ddb = new DictDataBuffer(PO);
          dbf.put(key,ddb);
        }
        if(DataBufferManager.getDataBufferManager(key)==null&&ddb.dbm!=null){
          DataBufferManager.pubMemMap(key,ddb.dbm,true);
        }
        return ddb;
    }
    protected DictDataBuffer(JParamObject po) {
      String key=DictDataBuffer.getKey(po);
      dbm=DataBufferManager.createDataBufferManager(key);
    }
    public DataBuffer getDataBuffer(String typekey){
        return dbm.getDataBuffer(typekey);
    }

    public boolean hasData(String bkey,String key){
      Object o=getDataItem(bkey,key);
      return o!=null;
    }

    //以map的KEY做为buf的key
    public void addDictDataMap(String typekey,Map map){
      String key;
      Iterator it=map.keySet().iterator();
      while(it.hasNext()){
        key=(String)it.next();
        StubObject so=(StubObject)map.get(key);
        addDataItem(typekey,key,so);
      }
    }

    //公用的缓冲
    public void setData(String key,Object o){
      addDataItem("PUBLICBUF",key,o);
    }
    public Object getData(String key){
      return getDataItem("PUBLICBUF",key);
    }
    public void addDataItem(String buftypekey, String key, Object dataso) {
      DataBuffer db = dbm.getDataBuffer(buftypekey);
      if (db == null){
        db = dbm.createDataBuffer(buftypekey);
      }
      db.addData(key, dataso);
    }
    public Object getDataItem(String buftypekey, String key) {
      DataBuffer db = dbm.getDataBuffer(buftypekey);
      if (db == null)
        return null;
      return db.getData(key);
    }
    public EFRowSet getDictDataItem(String typekey, String key,boolean bload) {
        return getDictDataItemByServer(JParamObject.Create(),typekey, key, bload);
    }
    public EFRowSet getDictDataItem(JParamObject PO,String typekey, String key,boolean bload) {
           return getDictDataItemByServer(PO,typekey, key, bload);
    }
    /**
     *
     * @param typekey String
     * @param key String
     * @param bload boolean
     * @param serverName String
     * @return EFRowSet
     */
    public EFRowSet getDictDataItemByServer(String typekey, String key, boolean bload, String serverName) {
      JParamObject PO=JParamObject.Create();
      PO.setEAIServer(serverName);
      return getDictDataItemByServer(PO,typekey,key,bload);
    }
    public EFRowSet getDictDataItemByServer(JParamObject PO,String typekey, String key, boolean bload) {
        EFDataSet eds = (EFDataSet)MDMDataUtil.getMainDctData(PO,typekey,bload);
        if (eds != null) {
            EFRowSet row = (EFRowSet) eds.getRowSet(key.split(","));
            if (row == null) return getDictDataItem2(typekey, key);
            return row;
        }
        return EFRowSet.getInstance();
    }

    /**
     * 首先按照不刷新的方式取，如果没取到，按刷新的方式取
     */
    public EFRowSet getDictDataItem2(String typekey, String key){
       JParamObject PO=JParamObject.Create();
       return getDictDataItem2(PO,typekey,key);
    }
    public EFRowSet getDictDataItem2(JParamObject PO,String typekey, String key){

        EFDataSet eds = (EFDataSet)MDMDataUtil.getMainDctData(PO,typekey,true);
        if (eds != null) {
            EFRowSet row = (EFRowSet) eds.getRowSet(key.split(","));
            return row;
        }
        return EFRowSet.getInstance(); //不能为NULL
    }

    /**
     * 首先按照不刷新的方式取，如果没取到，按刷新的方式取
     */
    public EFRowSet getDictDataItem(String typekey, String key) {
        return getDictDataItem(typekey, key, false);
    }

    public EFRowSet getDictDataItemByServer(String typekey, String key, String serverName) {
        return getDictDataItemByServer(typekey, key, false, serverName);
    }

    public  EFDataSet QueryData(JParamObject PO,String tab,String[]key,String where){
        if(PO==null)PO=JParamObject.Create();
      PO.SetValueByParamName("TABLE",tab);
      if(key!=null)
          PO.getParamRoot().put("KEYS",key);
      PO.SetValueByParamName("WHERE",where);
      try {
        JResponseObject RO=(JResponseObject) com.efounder.eai.EAI.DAL.IOM("DBService", "QueryEFDataSet", PO);
        return (EFDataSet) RO.getResponseObject();
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return null;
      }finally{
          PO.SetValueByParamName("TABLE",null);
          PO.getParamRoot().remove("KEYS");
         PO.SetValueByParamName("WHERE",null);

      }
    }

}
