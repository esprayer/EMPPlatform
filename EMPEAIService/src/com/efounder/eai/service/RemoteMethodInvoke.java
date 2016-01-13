package com.efounder.eai.service;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.text.SimpleDateFormat;
import com.efounder.eai.*;
import com.efounder.eai.data.JRequestObject;
import com.efounder.service.comm.CommManager;
import com.core.xml.*;

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
public class RemoteMethodInvoke {
	/**
	 *
	 */
	protected RemoteMethodInvoke() {
	}
	/**
	 *
	 * @param sender Object
	 * @param url URL
	 * @param ObjectName String
	 * @param MethodName String
	 * @param Param Object
	 * @param Data Object
	 * @param c Object
	 * @param a Object
	 * @return Object
	 * @throws Exception
	 */
	public static Object remoteFWKInvoke(Object sender,URL url,String ObjectName,
                                         String MethodName,Object Param,Object Data,
                                         Object c,Object a,String serverName) throws Exception {
		Object RequestObject = null;
		// change by Skyline , add serverName param 2010.07.29
		RequestObject = getFWKRequestObject(ObjectName, MethodName, Param, Data,c,a,serverName);
		return invokeRemoteService(sender,RequestObject,url,null);
	}
	/**
	 *
	 * @param ObjectName String
	 * @param MethodName String
	 * @param Param Object
	 * @param Data Object
	 * @param c Object
	 * @param a Object
	 * @return JRequestObject
	 */
	private static /*jfoundation.bridge.classes.JRequestObject*/ Object getFWKRequestObject(String ObjectName,String MethodName,
			Object Param,Object Data,Object c,Object a,String serverName) {
		// 获取服务器的stub
		StubObject serverStub = EAIServer.getEAIServer(serverName);
		if ( serverStub == null || "esp".equals(serverStub.getString("serverType","esp")) ) { // 如果为空，则用默认方式进行
//			jfoundation.bridge.classes.JRequestObject RequestObject = null;
//			RequestObject = new jfoundation.bridge.classes.JRequestObject();
//			RequestObject.InitRequestObject(ObjectName, MethodName, Param, Data, c, a);
//			return RequestObject;
		} else {
			if ( "fwk".equals(serverStub.getString("serverType","esp")) ) {
//        Object requestObject = jfoundation.bridge.classes.JRequestObject.initRequestMap(ObjectName, MethodName, Param, Data, c, a);
//        return requestObject;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param sender Object
	 * @param url URL
	 * @param ObjectName String
	 * @param MethodName String
	 * @param Param Object
	 * @param Data Object
	 * @param c Object
	 * @param a Object
	 * @return Object
	 * @throws Exception
	 */
	public static Object remoteESPInvoke(Object sender,URL url,String ObjectName,
                                         String MethodName,Object Param,Object Data,
                                         Object c,Object a,Map requestHeader,String serverName) throws Exception {
		Object RequestObject = null;
		RequestObject = getESPRequestObject(ObjectName, MethodName, Param, Data,c,a);
		return invokeRemoteService(sender,RequestObject,url,requestHeader);
 	}
	/**
	 *
	 * @param ObjectName String
	 * @param MethodName String
	 * @param Param Object
	 * @param Data Object
	 * @param c Object
	 * @param a Object
	 * @return JRequestObject
	 */
	private static JRequestObject getESPRequestObject(String ObjectName,String MethodName, Object Param,Object Data,Object c,Object a) {
		JRequestObject RequestObject = null;
		RequestObject = new com.efounder.eai.data.JRequestObject();
		RequestObject.InitRequestObject(ObjectName,MethodName,Param,Data,c,a);
		return RequestObject;
	}
	/**
	 *
	 * @param RequestObject Object
	 * @param ResponseObject JResponseObject
	 * @param urlc URLConnection
	 * @param url URL
	 * @return JResponseObject
	 * @throws Exception
	 */
	private static void logInfo(URL url,Object re, int size){
		if(!"1".equals(ParameterManager.getDefault().getSystemParam("CALL_MONITOR")))return;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
		String pTime = sdf.format(new Date());
		String obj="",med="", server="";
		if(re instanceof com.efounder.eai.data.JRequestObject){
			com.efounder.eai.data.JRequestObject espro=(com.efounder.eai.data.JRequestObject)re;
			obj=espro.ActiveObjectName;
			med=espro.ActiveObjectMethodName;
			if("BZServiceComponentManager".equals(obj)&&"syncRunService".equals(med))
				med+=" "+((com.efounder.eai.data.JParamObject)espro.ParamObject).GetValueByEnvName("ServiceKey", "");
			server= CommUtil.getObjectOnServer(espro.ParamObject, obj);
		}
//		if(re   instanceof jfoundation.bridge.classes.JRequestObject){
//			jfoundation.bridge.classes.JRequestObject fmwro=(jfoundation.bridge.classes.JRequestObject)re;
//			obj=fmwro.ActiveObjectName;
//			med=fmwro.ActiveObjectMethodName;
//			server= CommUtil.getObjectOnServer(fmwro.ParamObject, obj);
//		}
		if(server==null)server="";
		System.out.println(pTime+" "+server+" "+obj+" "+med+" "+size+" BIT "+Double.valueOf(size/1024)+" KB"+" "+url.toString());
	}
	/**
	 *
	 * @param sender Object
	 * @param RequestObject Object
	 * @param url URL
	 * @param requestHeader Map
	 * @return Object
	 * @throws Exception
	 */
	private static Object invokeRemoteService(Object sender,Object RequestObject, URL url,Map requestHeader) throws Exception {
		URLConnection urlc = null;Object ResponseObject = null;
		try {
			//
			CommManager.getDefault().startComm(sender,url,RequestObject);
			//
			urlc = CommManager.getDefault().writeObject(url, RequestObject,requestHeader);
			try{
				logInfo(url, RequestObject, urlc.getInputStream().available());
			} catch(Exception e){}
			ResponseObject = CommManager.getDefault().readObject(urlc);
		} finally {
//			//
//			CommManager.getDefault().stopComm(sender,urlc,ResponseObject);
//			if(ResponseObject instanceof com.efounder.eai.data.JResponseObject){
//				com.efounder.eai.data.JResponseObject ro=(com.efounder.eai.data.JResponseObject)ResponseObject;
//				List list=(List)ro.getResponseObject("$$SQL_VIEWLIST");
//				if(list!=null){
//					for(int i=0;i<list.size();i++)
//						System.out.println(list.get(i));
//				}
//			}
//			// add by Skyline 与 FMIS7兼容性
//			if ( ResponseObject instanceof Map ) {
//				Map roMap = (Map)ResponseObject;
//				String __isResponseObject__ = (String)roMap.get("__isResponseObject__");
//				if ( "1".equals(__isResponseObject__) ) {
////					public Object ResponseObject;
////					public int ErrorCode;
////					public String ErrorString;
//					jfoundation.bridge.classes.JResponseObject ro = new jfoundation.bridge.classes.JResponseObject();
////					ro.setResponseObject(roMap.get("ResponseObject"));
//					ro.ErrorString = (String)roMap.get("ErrorString");
//					ro.ErrorCode   = 0;
//					if ( roMap.get("ErrorCode") instanceof Integer ) {
//						ro.ErrorCode = ((Integer)roMap.get("ErrorCode")).intValue();
//					}
//					ResponseObject = ro;
//				}
//			}
//			//
//			if ( ResponseObject != null && ResponseObject instanceof jfoundation.bridge.classes.JResponseObject ) {
//          // 在这里需要检查是否有getExceptoin方法，如果有，则用反射的方法进行调用
////        Exception Error = ((jfoundation.bridge.classes.JResponseObject)ResponseObject).getExceptoin();
//				Exception Error=(Exception) JBOFClass.CallObjectMethod(ResponseObject, "getExceptoin");
//				if (Error != null)throw Error;
//			}
		}
		return ResponseObject;
	}
}
