package com.efounder.eai.framework.dal;

import com.efounder.eai.*;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.framework.IActiveObject;
import com.efounder.eai.framework.JActiveObject;
import com.efounder.eai.framework.JFwkActiveObjectStub;

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
	public Object CallObjectMethod(String ActiveObjectName,String ActiveObjectMethodName,
                                   Object Param,
                                   Object Data,
                                   Object CustomObject,
                                   Object AdditiveObject) throws Exception {
		JParamObject     PO = JParamObject.Create();
		String    webInvoke = PO.GetValueByEnvName("webInvoke", "0");
		
		//现有两种方式调用：
		//1:单机版调用
		//2:单机版调用web后台
		if(webInvoke.equals("1")) {
			/**
			 *
			 */
			Object[] param ={Param,Data,CustomObject,AdditiveObject};
			/**
			 *
			 */
			return EAI.DOF.InvokeObjectMethod("CommObject","RemoteObjectMethodAgent", ActiveObjectName,ActiveObjectMethodName,param,null);
		} else {
			JFwkActiveObjectStub AOStub=null;Object Res=null;
		    // ���Ȱ������
		    AOStub = (JFwkActiveObjectStub)findActiveObjectStubByName(ActiveObjectName);
		    // ���û�ҵ�,��GUID��
		    if ( AOStub == null ) AOStub = (JFwkActiveObjectStub)findActiveObjectStubByGUID(ActiveObjectName);
		    if ( AOStub != null ) {
		      Res = invokeMethod(ActiveObjectMethodName, Param, Data, CustomObject, AdditiveObject, AOStub);
		    } else {
		      System.out.println("对象-"+ActiveObjectName+"方法-"+ActiveObjectMethodName);
		    }
		    return Res;
		}
	}
	/**
	 *
	 * @param ActiveObjectName String
	 * @param ActiveMethodName String
	 * @param Param Object
	 * @param Data Object
	 * @return Object
	 * @throws Exception
	 */
	public Object webInvoke(String ActiveObjectName,String ActiveMethodName,Object Param,Map requestHeader) throws Exception {
		Object[] param ={Param,requestHeader,null,null};
		return EAI.DOF.InvokeObjectMethod("CommObject","webObjectMethodAgent", ActiveObjectName,ActiveMethodName,param,null);
	}
	
	/**
	 *
	 * @param ActiveObjectMethodName String
	 * @param Param Object
	 * @param Data Object
	 * @param CustomObject Object
	 * @param AdditiveObject Object
	 * @param AOStub JFwkActiveObjectStub
	 * @return Object
	 * @throws Exception
	 */
	private Object invokeMethod(String ActiveObjectMethodName, Object Param,
	                            Object Data, Object CustomObject,
	                            Object AdditiveObject,
	                            JFwkActiveObjectStub AOStub) throws Exception {
		Object Res = null;
		if ( AOStub.iActiveObject == null ) {
			AOStub.ActiveObjectStub.ActiveObjectClass = Class.forName(AOStub.ActiveObjectStub.objectclass);
			//AOStub.ActiveObjectStub.ActiveObjectClass = FrameworkClassLoader.loadClass(AOStub.ActiveObjectStub.objectclass,true);
			AOStub.oActiveObject = (JActiveObject)AOStub.ActiveObjectStub.ActiveObjectClass.newInstance();
			AOStub.oActiveObject.ActiveFramework     = this;
			AOStub.oActiveObject.ActiveObjectStub    = AOStub.ActiveObjectStub;
			AOStub.oActiveObject.FwkActiveObjectStub = AOStub;
			AOStub.iActiveObject = (IActiveObject) AOStub.oActiveObject;
			// ִ�ж���ĳ�ʼ������
			AOStub.oActiveObject.InitObject(this,AOStub,AOStub.ActiveObjectStub,null);
		}
		// ���ö���ķ���
		if ( AOStub.iActiveObject != null && ActiveObjectMethodName != null ) {
			Res = AOStub.iActiveObject.InvokeObjectMethod(ActiveObjectMethodName,Param,Data,CustomObject,AdditiveObject);
		}
		return Res;
	}
}
