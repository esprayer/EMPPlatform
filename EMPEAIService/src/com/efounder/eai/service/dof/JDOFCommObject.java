package com.efounder.eai.service.dof;

import java.net.*;
import java.util.*;

import javax.swing.*;

import com.core.xml.*;
import com.efounder.eai.*;
import com.efounder.eai.framework.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.service.*;
import com.efounder.eai.service.dof.DOFCommObject.JAppServerStubObject;
import com.efounder.pfc.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class JDOFCommObject extends JActiveObject {
	public static  Hashtable AppServiceList = new Hashtable();
	private String LocalFileName = null;
	protected static final String ESPService = "ESPService";
	private static final String GUID = "00000000-0002-0001-0000000000000002";
	private String CommBase  = null;
//  private String SSLCommBase  = null;
//  private static JActiveFramework AbstractDataActiveFramework = null;
//  private static String JEAISERVER_JEAISERVICE = "/EnterpriseServer/EAIService";
	/**
	 *
	 */
	public JDOFCommObject() {
		setObjectGUID(GUID);
	}
	/**
	 *
	 * @param Param Object
	 * @param Data Object
	 * @param CustomObject Object
	 * @param AdditiveObject Object
	 * @return Object
	 */
	public Object InitObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
		JStubObject stub;
		stub           = new JStubObject();
		stub.Name      = "ApplicationPanel";
//		stub.Caption   = JEnterpriseResource.GetString("JLoginDialog",stub.Name,"Ӧ�÷�����");
		stub.ClassName = "com.efounder.eai.service.dof.DOFCommObject.JCommPanel";
		try {
			EAI.DOF.InvokeObjectMethod("SecurityObject", "RegistryObject", stub);
		} catch ( Exception e ) {
			org.openide.ErrorManager.getDefault().notify (0,e,this);
		}
		GetOnlineCommBase();
		CommBase    = EAI.Protocol+"://" +EAI.Server+":"+EAI.Port+"/"+EAI.Path+"/"+ESPService;

//		SSLCommBase = "https://"+EAI.SSLServer+":"+EAI.SSLPort+"/"+EAI.Path+"/"+ESPService;
		return null;
	}

	/**
	 *
	 * @return URL
	 * @throws Exception
	 */
	private URL getGenerURL() throws Exception  {
		URL url = null;
		url = new URL(CommBase);
		return url;
	}
	/**
	 *
	 * @param ObjectName Object
	 * @param MethodName Object
	 * @param Param Object
	 * @param Data Object
	 * @return Object
	 * @throws Exception
	 */
	public Object frameworkMethodAgent(Object ObjectName,Object MethodName,Object Param,Object Data) throws Exception {
		Object[] params = (Object[])Param;
		return CommUtil.frameworkCall(this,(String)ObjectName,(String)MethodName,params[0],params[1],params[2],params[3],this.getGenerURL());
	}
	/**
	 *
	 * @param ObjectName Object
	 * @param MethodName Object
	 * @param Param Object
	 * @param Data Object
	 * @return Object
	 * @throws Exception
	 */
	public Object RemoteObjectMethodAgent(Object ObjectName,Object MethodName,Object Param,Object Data) throws Exception {
		Object[] params = (Object[])Param;
		return CommUtil.OnlineCall(this,(String)ObjectName,(String)MethodName,params[0],params[1],params[2],params[3],this.getGenerURL());
	}
	/**
	 *
	 * @param ObjectName Object
	 * @param MethodName Object
	 * @param Param Object
	 * @param Data Object
	 * @return Object
	 * @throws Exception
	 */
	public Object webObjectMethodAgent(Object ObjectName,Object MethodName,Object Param,Object Data) throws Exception {
		Object[] params = (Object[])Param;
		return CommUtil.webCall(this,(String)ObjectName,(String)MethodName,params[0],null,null,null,(Map)params[1],this.getGenerURL());
	}

	/**
	 *
	 * @param XMLObject JXMLBaseObject
	 */
	private void GetCommBase(JXMLBaseObject XMLObject) {
		JAppServerStubObject RASO = null;
		RASO = new JAppServerStubObject();
		RASO.ID      = "LocalnetWorkService";
		RASO.Caption = "当前登录应用服务";
		RASO.Server  = EAI.Server;
		RASO.Port    = EAI.Port;
//    RASO.SSLServer  = EAI.SSLServer;
//    RASO.SSLPort    = EAI.SSLPort;
		RASO.Path       = EAI.Path;
		AppServiceList.put(RASO.ID,RASO);
		//modify by luody 2008-3-19

		java.util.List nodelist = null;
		String Name="DataApplication";
		JAppServerStubObject ASO = null;String ID;
		StubObject node = null;
		//AppServicesElement = XMLObject.GetElementByName(Name);
		//nodelist = XMLObject.BeginEnumerate(AppServicesElement);
//		try {
//			nodelist = ApplicationServiceManager.getDefault().Enumerate("DataApplication");
//		} catch (Exception ex) {
//		}
		if ( nodelist == null ) return;
		for(int i=0;i<nodelist.size();i++) {
			node = (StubObject) nodelist.get(i);
			ASO = new JAppServerStubObject();
			ASO.ID      = node.getString("${appId}",null);
			if(ASO.ID == null || ASO.ID.equals("")) continue;
			if(ASO.ID.equals("#local_99")) continue;
			ASO.Caption = node.getString("${appName}",null);
			ASO.Server  = node.getString("${appIp}","");
			ASO.Port    = node.getString("${appPort}","");
			ASO.Path = node.getString("${proPath}","");//add by luody
	        ASO.Servlet = node.getString("${proServlet}","");//add by luody
	        ASO.ServerType = node.getString("${appType}","");//����������
	        String extParam = node.getString("${appParam}","");//add by luody
	        //add by luody
	        if(!"".equals(extParam)){
	        	Map map = getExtParam(extParam);
	        	Iterator it = map.keySet().iterator();
	        	while(it.hasNext()){
	        		String key = (String)it.next();
	        		if("SSLServer".equals(key))
	        			ASO.SSLServer = (String)map.get(key);
	        		else if("SSLPort".equals(key))
	        			ASO.SSLPort = (String)map.get(key);
	        		else if("ISSEC".equals(key)){
	        			String value = (String)map.get(key);
	        			ASO.isSec = "1".equals(value)?true:false;
	        		}
	        		else if("ISCHECK".equals(key)){
	        			String value = (String)map.get(key);
	        			ASO.isCheck = "1".equals(value)?true:false;
	        		}

	        		else if("SSLFile".equals(key)){
	        			String value = (String)map.get(key);
	        			ASO.SecurityFile = value;
	        		}
	        		else if("SSLPass".equals(key)){
	        			String value = (String)map.get(key);
	        			ASO.SecurityPass = value;
	        		}
	        		else if("CommBase".equals(key)){
	        			String value = (String)map.get(key);
	        			if(value != null)
	        				ASO.CommBase = value;
	        		}
	        		else if("Product".equals(key)) ASO.Product = (String)map.get(key);
	        	}
	        	if("".equals(ASO.SSLServer)) ASO.SSLServer = ASO.Server;
	        }
	        //end add by luody
	        AppServiceList.put(ASO.ID,ASO);

		}
	}
	//add by luody
	Map getExtParam(String extparam){
		Map map = new HashMap();
		String[] params = extparam.split(";");
		for(int i=0;i<params.length;i++){
			String param = params[i];
			int start = param.indexOf("=");
			if(start != -1){
				String key = param.substring(0, start);
				String value = param.substring(start+1);
				map.put(key,value);
			}
		}
		return map;
	}
	/**
	 *
	 */
	void GetOnlineCommBase() {
		GetCommBase(null);
	}
	/**
	 *
	 * @param Param1 Object
	 * @param Param2 Object
	 * @param Param3 Object
	 * @param Param4 Object
	 * @return Object
	 * @throws Exception
	 */
	public Object LoginInit(Object Param1,Object Param2,Object Param3,Object Param4) throws Exception {
//		JComboBox ComboBox = (JComboBox)Param1;JAppServerStubObject ASO = null;
//		String ID = EAI.LocalRegistry.Get("APP_SERVER",null);
		boolean Res = false;
//		ComboBox.removeAllItems();
////    ImageIcon ii = JResource.getImageIcon(this,"icon.gif",null);
//		Icon ii = ExplorerIcons.getExplorerIcon("oicons/hostserviceconn.png");
//    	JIConListCellRenderer ICR = new JIConListCellRenderer(ii);
//    	ComboBox.setRenderer(ICR);
//    	for(int i=0;i<this.AppServiceList.size();i++) {
//    		ASO = (JAppServerStubObject)AppServiceList.values().toArray()[i];
//    		ComboBox.addItem(ASO);
////      ComboBox.insertItemAt(ASO,i);
//    		if ( ASO.ID.equals(ID) ) {
//    			ComboBox.setSelectedIndex(i);
//    		}
//    		Res = true;
//    	}
//    	if ( ID == null && ComboBox.getItemCount() != 0 ) {
//    		ComboBox.setSelectedIndex(0);
//    	}
    	return new Boolean(Res);
	}
	/**
	 *
	 * @param Param1 Object
	 * @param Param2 Object
	 * @param Param3 Object
	 * @param Param4 Object
	 * @return Object
	 * @throws Exception
	 */
	public Object LoginSetup(Object Param1,Object Param2,Object Param3,Object Param4) throws Exception {
		JComboBox ComboBox = (JComboBox)Param1;boolean Res = false;
		JAppServerStubObject ASO = (JAppServerStubObject)ComboBox.getSelectedItem();
		if ( ASO != null ) {
			EAI.Server = ASO.Server;
			EAI.Port   = ASO.Port;
//      EAI.SSLServer = ASO.SSLServer;
//      EAI.SSLPort   = ASO.SSLPort;
			EAI.Path      = ASO.Path;
			CommBase    = EAI.Protocol+"://" +EAI.Server+":"+EAI.Port+"/"+EAI.Path+"/"+ESPService;
//      SSLCommBase = "https://"+EAI.SSLServer+":"+EAI.SSLPort+"/"+EAI.Path+"/"+ESPService;
			EAI.LocalRegistry.Put("APP_SERVER",ASO.ID);
			Res = true;
		}
		return new Boolean(Res);
	}

}
