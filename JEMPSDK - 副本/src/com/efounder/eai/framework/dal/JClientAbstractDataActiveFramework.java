package com.efounder.eai.framework.dal;

import com.efounder.eai.*;
import java.util.Map;

import jframework.foundation.classes.JActiveDComDM;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JClientAbstractDataActiveFramework extends JAbstractDataActiveFramework{
  /**
   *
   */
  public JClientAbstractDataActiveFramework() {
    AcitveFrameworkName = "AbstractDataActiveFramework";
    setCLSID_Type("CLSID_AbstractDataActiveObjectCategory");
    setTier("Client");
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
//  public Object CallObjectMethod(String ActiveObjectName,String ActiveObjectMethodName,
//                                   Object Param,
//                                   Object Data,
//                                   Object CustomObject,
//                                   Object AdditiveObject) throws Exception {
//    /**
//     *
//     */
//    Object[] param ={Param,Data,CustomObject,AdditiveObject};
//   /**
//    *
//    */
//   return EAI.DOF.InvokeObjectMethod("CommObject","RemoteObjectMethodAgent",
//                                     ActiveObjectName,ActiveObjectMethodName,param,null);
//  }
  /**
   *
   * @param ActiveObjectName String
   * @param ActiveMethodName String
   * @param Param Object
   * @param Data Object
   * @return Object
   * @throws Exception
   */
  public Object webInvoke(String ActiveObjectName,String ActiveMethodName,Object Param,Map requestHeader) throws Exception{
    Object[] param ={Param,requestHeader,null,null};
    return EAI.DOF.InvokeObjectMethod("CommObject","webObjectMethodAgent",
                                     ActiveObjectName,ActiveMethodName,param,null);
  }
}
