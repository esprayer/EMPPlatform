package com.efounder.buffer.util;

import java.util.*;

import com.core.xml.*;
import com.efounder.buffer.*;
import com.efounder.builder.base.data.*;
import com.efounder.eai.data.*;
import com.efounder.eai.service.*;
import com.efounder.eai.service.security.*;

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
//当前用户用的配置信息(比如选用)和小数量级的关键信息以及权限信息一次全取
public class ConifgDataUtil {
  public ConifgDataUtil() {
  }

  public static  StubObject getConfigStub(String typename, String id) {
    Vector v=PackageStub.getContentVector(typename);
     for (int i = 0; v != null && i < v.size(); i++) {
         StubObject so = (StubObject) v.get(i);
         if(so.getString("id","").equals(id)){
           return so;
         }
     }
    return null;
  }
  //不需要按KEY取的东西，一次性取LIST
  public static EFDataSet getWholeData(String dctid, String where,String key) {
      return getWholeData(null, dctid, where,key);
  }

  public static EFDataSet getWholeData(String dctid, String where) {
      return getWholeData(null, dctid, where,null);
  }
    public static EFDataSet getWholeData(JParamObject PO,String dctid,String where){
        return getWholeData(PO,dctid,where,null);
    }
  public static EFDataSet getWholeData(JParamObject PO,String dctid,String where,String key){
      String typekey=dctid;
      if(where==null)where="1=1";
      if(!DictDataBuffer.getDefault(PO).hasData(dctid,where)){
          EFDataSet eds = DictDataBuffer.getDefault(PO).QueryData(PO, dctid, null,
                  where);
          if (eds != null)
              if (key != null) {
                  String[]keys=key.split(",");
                  EFDataSet newds=EFDataSet.getInstance(dctid);
                  newds.setPrimeKey(keys);
                  for(int i=0;i<eds.getRowCount();i++)
                      newds.addRowSet(eds.getRowSet(i));
                  eds=newds;
              }
          DictDataBuffer.getDefault(PO).addDataItem(typekey,
                  where, eds);
      }
      return (EFDataSet) DictDataBuffer.getDefault(PO).getDataItem(typekey,where);
  }
  //在完整的数据中找，没有就到后台一次性取
  public static EFRowSet  getDataInWholeData(String dctid,String keycol,String key){
    return getDataInWholeData(null,dctid,keycol,key);
  }
  public static EFRowSet  getDataInWholeData(JParamObject PO,String dctid,String keycol,String key){
    if(!DictDataBuffer.getDefault(PO).hasData(dctid,keycol)){
      EFDataSet eds=DictDataBuffer.getDefault(PO).QueryData(PO,dctid,keycol.split(","),null);
      if(eds!=null)
        DictDataBuffer.getDefault(PO).addDataItem(dctid,keycol,eds);
    }
    EFDataSet eds=(EFDataSet)DictDataBuffer.getDefault(PO).getDataItem(dctid,keycol);
    if(eds==null)return EFRowSet.getInstance();
    String[]keys=key.split(",");
    return (EFRowSet) eds.getRowSet(keys);
  }



public static boolean hasGrantWithDict(String table,String sjbh,String bzw){
  return hasGrantWithDict(null,table,sjbh,bzw);
}
  public static boolean hasGrantWithDict(JParamObject PO,String table,String sjbh,String bzw){
    EFRowSet gran=getDataInWholeData(PO,"BSGRAN","F_TABN",table);
    if(gran==null)return true;
    EFRowSet so = DictDataBuffer.getDefault(PO).getDictDataItem(table, sjbh);
    if (gran.getString("F_SFSY", "").equals("0"))
        return true; //不使用权限
    else if (gran.getString("F_SFSY", "").equals("1")) { //使用用户权限
        so = DictDataBuffer.getDefault(PO).getDictDataItem(table, sjbh);
//        MDMDataUtil.getMainDctData(PO, table, true);
        if (so == null)
            return false;
    } else { //使用规则权限，没法缓冲，只能跑后面判断了
        try {
            boolean b = EAISecurityService.getDefault().getAccessController1(
                    gran.
                    getString("F_QXBH", ""), sjbh,
                    Integer.parseInt(bzw));
            return b;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    boolean b = "1".equals(so.getString("F_G" + bzw, "0"));
    return b;
  }
}
