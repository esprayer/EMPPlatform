package com.efounder.eai.framework;

//import jbof.application.classes.JBOFApplicationStub;
//import jbof.application.classes.JBOFApplication;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//----------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.12.29)
//ʵ��: Skyline
//�޸�:
//----------------------------------------------------------------------------------------------
public class CallThread implements Runnable{
  String ActiveObjectName;
  String ActiveObjectMethodName;
  Object Param;
  Object Data;
  Object CustomObject;
  Object AdditiveObject;
  JActiveFramework AFW;
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public CallThread() {
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void CallOperateItem(JActiveFramework pAF,String pActiveObjectName,String pActiveObjectMethodName,
                                   Object pParam,
                                   Object pData,
                                   Object pCustomObject,
                                   Object pAdditiveObject) {
    ActiveObjectName        = pActiveObjectName;
    ActiveObjectMethodName  = pActiveObjectMethodName;
    Param                   = pParam;
    Data                    = pData;
    CustomObject            = pCustomObject;
    AdditiveObject          = pAdditiveObject;
    AFW                     = pAF;
    Thread CT = new Thread(this);
    CT.start();
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void run() {
    try {
      AFW.TInvokeObjectMethod(ActiveObjectName, ActiveObjectMethodName, Param,
                              Data, CustomObject, AdditiveObject);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return;
  }
}
