package com.efounder.eai.main;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author prayer
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.core.xml.JBOFClass;
import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.eai.EAI;
import com.efounder.eai.ide.ExplorerIcons;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.office2003.BasicOffice2003Theme;
import com.jidesoft.plaf.office2003.Office2003Painter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author prayer
 * @version 1.0
 */
public class JEAIEntry {
	public        static String    SAP_STD_LOOKANDFEEL = "com.sap.plaf.frog.FrogLookAndFeel";
	protected     static String          _EXT_SERVICE_ = "EnterpriseExtService";
	static public final String  ENTERPRISE_APPLICATION = "JEnterprise";
	
	public static void main(String[] args) {
		JEAIEntry Entery = new JEAIEntry();
		try {
			/**
			 * 初始化参数
			 */
			Hashtable p = new Hashtable();
	    	initArgs(args,p);
	    	PackageStub.initMETA_INF(Entery.getClass().getClassLoader());
	    	Entery.entry(p);
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
	
	public static void entry(Hashtable Param) throws Exception {
		System.setProperty("EAT","client");
	    // 设置系统变量
	    InitParam(Param);
	    // 初始化EAI
	    EAIActiveComp DComDM = new EAIActiveComp();
	    // 设置属性列表
	    DComDM.PropertyList = new Hashtable(Param);
	    // 初始化EAI
	    DComDM.InitEAI();
//	    // 初始化语言
//	    InitLanguage(Param);
	    // 初始化Look and Feel
	    InitLookFeel(Param);
	    // 初始化系统字体
	    InitFont(new Font("微软雅黑", 0, 12));
	    // 初始化Splash
	    Splash();
	    // 登录
	    if ( SystemLogin() ) {
	    	// 运行
	    	RUN(Param);
	    } else {
	    	System.exit(0);
	    }
	}
	
	// 设置系统变量
	static void InitParam(Hashtable Param) {
	    Param.put("company","efounder");
	    Param.put("tier","Client");
//	    EAIActiveComp.LocalUserHome     = (String)Param.get("user.home");
	    EAIActiveComp.LocalUserHome     = System.getProperty("user.dir") + System.getProperty("file.separator");
	    initEAIHOME();
	    EAIActiveComp.Codebase          = (String)Param.get("codebase");
	    EAIActiveComp.Company           = (String)Param.get("company");
	    EAIActiveComp.Tier              = (String)Param.get("tier");
	    EAIActiveComp.Product           = (String)Param.get("product");
	    EAIActiveComp.Protocol          = (String)Param.get("protocol");
	    if ( EAIActiveComp.Protocol == null ) EAIActiveComp.Protocol = "http";
	    EAIActiveComp.Server            = (String)Param.get("server");
	    EAIActiveComp.Port              = (String)Param.get("port");
	    EAIActiveComp.Path              = (String)Param.get("serverpath");
	    if( EAIActiveComp.Path == null ) EAIActiveComp.Path = "EnterpriseServer";
//	    EAIActiveComp.SSLServer         = (String)Param.get("sslserver");
//	    EAIActiveComp.SSLPort           = (String)Param.get("sslport");
	    EAIActiveComp.ProxyServer       = (String)Param.get("proxyserver");
	    EAIActiveComp.ProxyPort         = (String)Param.get("proxyport");
	    EAIActiveComp.Title             = (String)Param.get("frametitle");
	    EAIActiveComp.ServerPath        = (String)Param.get("serverpath");
	    processCodebase();

	}
	
	private static void initEAIHOME() {
		String LocalUserHome = EAIActiveComp.LocalUserHome;
		System.setProperty ("eai.home",LocalUserHome);
		System.setProperty ("Language","zh_CN");
	}
	
	protected static void processCodebase() {
//		try {
//			URL url = new URL(EAIActiveComp.Codebase);
//			String Protocol = url.getProtocol().toLowerCase();
//			if ( "http".equals(Protocol) ) {
//				EAIActiveComp.setSecurity(false);
//				EAIActiveComp.Server = url.getHost();
//				EAIActiveComp.Port = String.valueOf(url.getPort());
//			}
//			if ( "https".equals(Protocol) ) {
//				EAIActiveComp.setSecurity(true);
//			}
//		} catch ( Exception e ) {
//			e.printStackTrace();
//	    }
	}

	/**
	 * 初始化参数
	 * @param args
	 * @param PropertyList
	 */
	private static void initArgs(String args[],Hashtable PropertyList) {
	    String name,value,tmp;
	    for(int i=0;i<args.length;i++) {
	    	tmp = args[i];
	    	int index = tmp.indexOf("=");
	    	if ( index > 0 ) {
	    		name = tmp.substring(0, index);
	    		value = tmp.substring(index+1);
	    		PropertyList.put(name.toLowerCase(),value);// Key
	    	}
	    }
	}
	
	/**  
     * 初始化Look and Feel
     */
	static void InitLookFeel(Hashtable Param) {
		try {
//			UIManager.setLookAndFeel(SAP_STD_LOOKANDFEEL);
			EAI.DOF.InvokeObjectMethod("ResourceObject", "initLookFeel");
	    } catch ( Exception e ) {

	    }
	}
	
	/**  
     * 设置全局字体样式，否则中文将产生乱码
     */
	static void InitFont(Font myFont) {
//		FontUIResource fontRes = new FontUIResource(myFont);      
//        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
//            Object key = keys.nextElement();      
//            Object value = UIManager.get(key);
//            if (value instanceof FontUIResource) {
//                UIManager.put(key, fontRes);
//            }
//        }
		
		try {
			EAI.DOF.InvokeObjectMethod("ResourceObject", "initFont");
		} catch ( Exception e ) {

		}
	}
	
	static void Splash() {
		try {
	      EAI.DOF.InvokeObjectMethod("ResourceObject", "initSplash");
	    } catch ( Exception e ) {

	    }

	}
	
	// 进行系统登录
	static boolean SystemLogin() {
		boolean res = false;
	    try {
	    	Object o = EAI.DOF.IOM("SecurityObject", "DoLogin");
	    	if ( o == null ) res = true;
	    } catch ( Exception e ) {
	    	e.printStackTrace();
	    	res = false;
	    }
	    return res;
	}
	  
	// 执行应用
	static void RUN(Hashtable Param) {
		// 运行扩展服力
	    runExtService();

	    EAI.MA.ExecuteApplication(ENTERPRISE_APPLICATION,null);	     
	}

	/**
	 *
	 */
	protected static void runExtService() {
		java.util.List serviceList = PackageStub.getContentVector(_EXT_SERVICE_);
	    if ( serviceList == null || serviceList.size() == 0 ) return;
	    StubObject stub = null;
	    for(int i=0;i<serviceList.size();i++) {
	      stub = (StubObject)serviceList.get(i);
	      String clazz = stub.getString("class",null);
	      if ( clazz == null || clazz.trim().length() == 0 ) continue;
	      try {
	        Class cls = JEAIEntry.class.getClass().forName(clazz);
	        JBOFClass.VoidCallClassMethod(cls,"startService");
	      } catch ( Throwable t) {
	        t.printStackTrace();
	      }
	    }
	  }
}
