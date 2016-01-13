package com.efounder.eai.service.dof;

import java.util.*;

import jfoundation.gui.window.classes.Dlg.EMPAboutDlg;
import jfoundation.gui.window.classes.Dlg.EMPChangePaawordDlg;

import org.jdom.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.eai.framework.*;
import com.efounder.eai.service.*;
import com.core.xml.*;
import com.efounder.service.security.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDOFSecurityObject extends JActiveObject {
	private static final String GUID = "00000000-0002-0001-0000000000000001";
	public  static Vector InitSetupList = new Vector();
	protected static Vector ProductList = new Vector();
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	public JDOFSecurityObject() {
		setObjectGUID(GUID);
	}
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	public Object InitObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
		JStubObject stub;
		// 获取JEnterprise的基本配置信息
		GetOnlineApplicationBase();
		// 获取产品信息
		getProductinfo();
		GetOnlineCommBase();
		stub           = new JStubObject();
		stub.Name      = "CodebasePanel";
//    stub.Caption   = JEnterpriseResource.GetString("JLoginDialog",stub.Name,"程序服务器");
		stub.ClassName = "com.efounder.eai.service.dof.DOFSecurityObject.JCodebasePanel";
		InitSetupList.add(stub);
    //

		return null;
	}
	/**
	 * 获取产品列表
	 * @param Param Object
	 * @param Data Object
	 * @param CustomObject Object
	 * @param AdditiveObject Object
	 * @return Object
	 */
	public Object GetProductList(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
		return this.ProductList;
	}
	/**
	 * 获取产品的信息
	 */
	protected void getProductinfo() {
    // 产品配置信息
//    JDOMXMLBaseObject XML = new JDOMXMLBaseObject();
//    ConfigManager.getSpaceFile(XML,ConfigManager.CONF_FILE_DIR,"Products.xml",false);
//    if ( XML != null )
//      getProductinfo(XML);
	}
	
	protected void getProductinfo(JDOMXMLBaseObject XML) {
//    java.util.List nodelist = XML.BeginEnumerate(XML.Root);
//    ProductStub PS = null;int Index = 0;Element e;
//    while ( nodelist != null ) {
//      e = XML.Enumerate(nodelist,Index++);
//      if ( e == null ) break;
//      PS = new ProductStub();
//      PS.setObjectFromXMLElemnt(e);
//      this.ProductList.add(PS);
//    }
	}
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	void GetOnlineCommBase() {
//    String FileName = EAI.Product+".xml";
//    JXMLResourceReadObject rro = new JXMLResourceReadObject();
//    ConfigManager.getSpaceFile(rro,ConfigManager.CONF_FILE_DIR,FileName,true);
//    JEnterpriseResource.LoadResource(EAI.Product,rro,null);
	}
	void GetOnlineApplicationBase() {
//    String FileName = EAI.Application+".xml";
//    JXMLResourceReadObject rro = new JXMLResourceReadObject();
//    ConfigManager.getSpaceFile(rro,ConfigManager.CONF_FILE_DIR,FileName,false);
//    JEnterpriseResource.LoadResource(EAI.Application,rro,null);
	}
	public Object RegistryObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
		JStubObject stub = (JStubObject)Param;
		if ( stub != null )
			InitSetupList.add(stub);
		return null;
	}
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	public Object DoLogin(Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception {
		return ServiceSecurityManager.getDefault(EAI.Product).Login(Param,Data,CustomObject,AdditiveObject);
    // 如果不需要Login
//    String Value = JEnterpriseResource.GetString(EAI.Product,"NeedLogin",null,null);
//    if ( "0".equals(Value) ) return null;
//    try {
//      Dialog1 dlg = new Dialog1(null,"CASL",true);
//      dlg.show();
//      JLoginDialog LoginDialog = new JLoginDialog(null,"CASL",true);
//      LoginDialog.CenterWindow();
//      LoginDialog.show();
//      if (LoginDialog.Result == LoginDialog.RESULT_CANCEL)
//        return new Boolean(false);
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return null;
	}
  
	/**
	 *
	 * @param PO JParamObject
	 * @param o2 Object
	 * @param o3 Object
	 * @param o4 Object
	 * @return Object
	 * @throws Exception
	 */
	public Object changePassword(Object PO,Object o2,Object o3,Object o4) throws Exception {
		EMPChangePaawordDlg dlg;
		dlg= new EMPChangePaawordDlg(EAI.EA.getMainWindow(), "修改密码", true);
		dlg.CenterWindow();
		dlg.setVisible(true);
		return null;
	}
	
	/**
	 *
	 * @param PO JParamObject
	 * @param o2 Object
	 * @param o3 Object
	 * @param o4 Object
	 * @return Object
	 * @throws Exception
	 */
	public Object aboutus(Object PO,Object o2,Object o3,Object o4) throws Exception {
		EMPAboutDlg aboutDlg;
		aboutDlg = new EMPAboutDlg(EAI.EA.getMainWindow(), "关于软件...", true);
		aboutDlg.CenterWindow();
		aboutDlg.setVisible(true);
		return null;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	public Object LoginSetup(Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception {
//    JEnterpriseSetupDlg LoginSetupDialog = new JEnterpriseSetupDlg();
//    LoginSetupDialog.CenterWindow();
//    LoginSetupDialog.show();
//    if ( LoginSetupDialog.Result == LoginSetupDialog.RESULT_CANCEL )
//      return new Boolean(false);
		return null;
	}
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	public Object Login(Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception {
		return ServiceSecurityManager.getDefault().Login(Param,Data,CustomObject,AdditiveObject);
//    JResponseObject RO = (JResponseObject)EAI.DAL.InvokeObjectMethod("SecurityObject","Login",Param);
//    if ( RO.ErrorCode == 0 ) {
//      // 重置环境变量
//      EAI.DOF.InvokeObjectMethod("EnvironmentObject","ResetEnvObject",RO.ResponseObject);
//    }
//    return RO;
	}
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	public Object Logout(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
		return null;
	}
  // String Key,String Value,int index
	public Object getAccessController1(JParamObject Param,String Key,String Value,Integer Index) throws Exception {

		return null;
	}
  // String Key,int index
	public String getAccessController2(JParamObject Param,String Key,Object CustomObject,Integer Index) throws Exception  {
		return null;
	}
  // String Key,int index
	public String getAccessController3(JParamObject Param,String Key,Object CustomObject,Integer Index) throws Exception  {
		return null;
	}
}
