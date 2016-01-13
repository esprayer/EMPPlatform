package com.efounder.dataobject;

import com.borland.dx.dataset.*;
import com.efounder.dbc.*;
import com.efounder.eai.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTObject {
  /**
   *
   */
  protected static java.util.Map DCTObjectList = new java.util.Hashtable();
  /**
   *
   */
  protected Object        userObject1 = null;
  /**
   *
   * @return Object
   */
  public Object getUserObject1() {
    return userObject1;
  }
  /**
   *
   * @param userObject Object
   */
  public void setUserObject1(Object userObject1) {
    this.userObject1 = userObject1;
  }
  /**
   *
   */
  protected Object        userObject2 = null;
  /**
   *
   * @return Object
   */
  public Object getUserObject2() {
    return userObject2;
  }
  /**
   *
   * @param userObject Object
   */
  public void setUserObject2(Object userObject2) {
    this.userObject2 = userObject2;
  }
  /**
   *
   */
  public TABObject     tabObject = null;
  /**
   *
   */
  public ClientDataSet SYS_DICTS = null;
  /**
   *
   */
  public String        DCT_ID    = null;
  /**
   *
   * @return ClientDataSet
   */
  public ClientDataSet getSYS_DICTS() {
    return SYS_DICTS;
  }
  /**
   *
   * @return TABObject
   */
  public TABObject getTabObject() {
    return tabObject;
  }
  /**
   *
   * @return String
   */
  public String getDCT_ID() {
    return DCT_ID;
  }
  /**
   * 获取数据字典的Field标识
   * @param DCT_FIELD String
   * @return String
   */
  public String getDCTField(String DCT_FIELD) {
    String FIELD = null;
    if ( SYS_DICTS != null )
      FIELD = SYS_DICTS.getString(DCT_FIELD).trim();
    return FIELD;
  }
  /**
   *
   */
  public DCTObject() {
  }
  /**
   *
   * @param DCT_ID String
   * @param dataSetDatas DataSetData[]
   * @return DCTObject
   */
  protected static DCTObject getDCTObject(String DCT_ID,DCTObject dctObject,DataSetData[] dataSetDatas) {
	return getDCTObject(DCT_ID,dctObject,dataSetDatas,"");  
  }
  protected static DCTObject getDCTObject(String DCT_ID,DCTObject dctObject,DataSetData[] dataSetDatas,String server) {
    // 新建一个DCTObject对象
    if ( dctObject == null )
      dctObject = new DCTObject();
    TABObject tabObject = dctObject.getTabObject();
    // 字典标识
    dctObject.DCT_ID = DCT_ID;
    INFObject infObject = null;
    if ( tabObject != null )
      infObject = tabObject.getInfObject();
    // 对象信息
    ClientDataSet SYS_OBJECTS = null;
    if ( infObject != null ) SYS_OBJECTS = infObject.getSYS_OBJECTS();
    // 对象列信息
    ClientDataSet SYS_OBJCOLS = null;
    if ( infObject != null ) SYS_OBJCOLS = infObject.getSYS_OBJCOLS();
    // 数据字典信息
    ClientDataSet SYS_DICTS   = dctObject.SYS_DICTS;
    // 对象数据信息
    ClientDataSet TAB_DATA    = null;
    if ( tabObject != null )
      TAB_DATA = tabObject.getTAB_DATA();
    // 对象信息
    SYS_OBJECTS = INFObject.createClientDataSet(SYS_OBJECTS,dataSetDatas[0]);
    // 对象列信息
    SYS_OBJCOLS = INFObject.createClientDataSet(SYS_OBJCOLS,dataSetDatas[1]);
    // 字典信息
    SYS_DICTS   = INFObject.createClientDataSet(SYS_DICTS,dataSetDatas[2]);
    // 字典数据
    TAB_DATA    = INFObject.createClientDataSet(TAB_DATA,dataSetDatas[3]);
    // 创建表对象
    tabObject = TABObject.getTabObject(DCT_ID,tabObject,TAB_DATA,SYS_OBJECTS,SYS_OBJCOLS);
    // 引用表对角
    dctObject.tabObject = tabObject;
    // 引用字典信息
    dctObject.SYS_DICTS = SYS_DICTS;
    return dctObject;
  }
  /**
   *
   * @param DCT_IDS String[]
   * @throws Exception
   * @return DCTObject[]
   */
  public static DCTObject[] getDCTObject(DCTObject[] dctObjects,String[] DCT_IDS,String server) throws Exception {
	  return getDCTObject(dctObjects,DCT_IDS,true,true,server);
  }
  public static DCTObject[] getDCTObject(DCTObject[] dctObjects,String[] DCT_IDS) throws Exception {
    return getDCTObject(dctObjects,DCT_IDS,true,true,"");
  }
  /**
   * 根据传递过来的数据字典标识数组形成DCTObject数据
   * @param DCT_IDS String[]
   * @return DCTObject[]
   */
  public static DCTObject[] getDCTObject(DCTObject[] dctObjects,String[] DCT_IDS,boolean isML,boolean isQX,String server) throws Exception {
//    DCTObject[] dctObjects = null;
    if ( dctObjects == null )
      dctObjects = new DCTObject[DCT_IDS.length];
    DataSetData[] dataSetDatas = null;
    // 获取DataSetData数组
    Object[] datas = (Object[])EAI.DOF.IOM("DBService","getDCTObject",DCT_IDS,new Boolean(isML),new Boolean(isQX),server);
    // 循环处理每一个
    for(int i=0;i<DCT_IDS.length;i++) {
      dataSetDatas = (DataSetData[])datas[i];
      // 形成DCTObject
      dctObjects[i]= getDCTObject(DCT_IDS[i],dctObjects[i],dataSetDatas);
      // 将获取的DCTObject放入缓冲区中,如果存在以前的数据，将会被覆盖
      DCTObjectList.put(DCT_IDS[i],dctObjects[i]);
    }
    return dctObjects;
  }
  /**
   *
   * @param DCT_ID String
   * @throws Exception
   * @return DCTObject
   */
  public static DCTObject getDCTObject(String DCT_ID,String server) throws Exception {
    return getDCTObject(DCT_ID,false,server);
  }
  public static DCTObject getDCTObject(String DCT_ID) throws Exception {
	    return getDCTObject(DCT_ID,false,"");
}
  /**
   *
   * @param DCT_ID String
   * @return DCTObject
   * @throws Exception
   */
  public static DCTObject getNDCTObject(String DCT_ID) throws Exception {
    // 先清除掉
    DCTObjectList.remove(DCT_ID);
    DCTObject dctObject = getDCTObject(DCT_ID,false,"");
    // 再清除掉
    DCTObjectList.remove(DCT_ID);
    return dctObject;
  }
  /**
   *
   * @param DCT_ID String
   * @throws Exception
   * @return DCTObject
   */
  public static DCTObject getDCTObject(String DCT_ID,boolean isrefresh) throws Exception {
	  return getDCTObject(DCT_ID,isrefresh,"");
  }
  public static DCTObject getDCTObject(String DCT_ID,boolean isrefresh,String server) throws Exception {
    DCTObject dctObject = (DCTObject) DCTObjectList.get(DCT_ID);
    // 如果不是刷新，则从缓冲区里的取出来，直接返回
    if ( !isrefresh ) {
      // 从DCTObjectList中返回
      // 如果不为空，则直接返回缓冲区里的DCTObject
      if ( dctObject != null ) return dctObject;
    }
    String[] DCT_IDS = {DCT_ID};
    DCTObject[] dctObjects = {dctObject};
    dctObjects = getDCTObject(dctObjects,DCT_IDS,server);
    return dctObjects[0];
  }
  /**
   *
   * @param sourceDCT DCTObject
   * @param targetDCT DCTObject
   * @param filedName String
   * @return DCTObject
   */
  public static DCTObject jionDCT(DCTObject sourceDCT,DCTObject targetDCT,String filedName) {
    // 1.首先联接两个表
    TABObject.jionTable(sourceDCT.getTabObject(),targetDCT.getTabObject(),filedName);
    return targetDCT;
  }
}
