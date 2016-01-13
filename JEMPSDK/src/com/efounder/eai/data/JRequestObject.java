package com.efounder.eai.data;

import com.efounder.service.security.ServiceSecurityManager;
import com.efounder.eai.service.ParameterManager;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���\uFFFD: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JRequestObject implements java.io.Serializable {
  public String ActiveObjectName;
  public String ActiveObjectMethodName;
  public Object ParamObject;
  public Object DataObject;
  public Object CustomObject;
  public Object AdditiveObject;
  public boolean transform = false;
  /**
   *
   */
  public JRequestObject() {
  }
  /**
   *
   * @param pActiveObjectName String
   * @param pActiveObjectMethodName String
   * @param pParamObject Object
   * @param pDataObject Object
   * @param pCustomObject Object
   * @param pAdditiveObject Object
   */
  public void transformRequestObject(String pActiveObjectName,String pActiveObjectMethodName,
                                Object pParamObject,Object pDataObject,Object pCustomObject,Object pAdditiveObject) {
    InitRequestObject(pActiveObjectName,pActiveObjectMethodName,pParamObject,pDataObject,pCustomObject,pAdditiveObject);
    transform = true;
  }
  /**
   *
   * @param pActiveObjectName String
   * @param pActiveObjectMethodName String
   * @param pParamObject Object
   * @param pDataObject Object
   * @param pCustomObject Object
   * @param pAdditiveObject Object
   */
  public void InitRequestObject(String pActiveObjectName,String pActiveObjectMethodName,
                                Object pParamObject,Object pDataObject,Object pCustomObject,Object pAdditiveObject) {
    ActiveObjectName       = pActiveObjectName;
    ActiveObjectMethodName = pActiveObjectMethodName;
    ParamObject = pParamObject;
    if ( ParamObject instanceof com.efounder.eai.data.JParamObject ) {
      processLoginfo((com.efounder.eai.data.JParamObject)ParamObject);
    }
    CustomObject   = pCustomObject;
    AdditiveObject = pAdditiveObject;
    DataObject = pDataObject;
  }
  /**
   *
   * @param pActiveObjectName String
   * @param pActiveObjectMethodName String
   * @param pParamObject Object
   * @param pDataObject Object
   * @param pCustomObject Object
   * @param pAdditiveObject Object
   * @return Map
   */
  public static java.util.Map initRequestMap(String pActiveObjectName,String pActiveObjectMethodName,
                                             Object pParamObject,Object pDataObject,
                                             Object pCustomObject,Object pAdditiveObject) {
    java.util.Map requestMap = new java.util.HashMap();
    requestMap.put("ActiveObjectName",pActiveObjectName);
    requestMap.put("ActiveObjectMethodName",pActiveObjectMethodName);

    requestMap.put("ParamObject",pParamObject);
    requestMap.put("CustomObject",pCustomObject);
    requestMap.put("AdditiveObject",pAdditiveObject);
    requestMap.put("DataObject",pDataObject);
    return requestMap;
  }
  /**
   *
   * @param po JParamObject
   */
  protected void processLoginfo(com.efounder.eai.data.JParamObject po) {
    if ( po == null ) return;
    if ("1".equals(ParameterManager.getDefault().getSystemParam("SQL_VIEW"))) {
      po.SetValueByParamName("$$SQL_VIEW", "1");
    }
    if ( po.getLoginfoList() != null && po.getLoginfoList().size() != 0 ) return;
    ServiceSecurityManager ssm = ServiceSecurityManager.getDefault();
    if ( ssm != null ) {
      java.util.List list = ServiceSecurityManager.getDefault().getWriteLogList();
      po.setLoginfoList(list);
    }
  }
}
