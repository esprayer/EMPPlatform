package com.efounder.eai.main;

import java.io.File;
import java.net.URL;

import org.apache.catalina.Manager;

import com.core.servlet.InitBootObject;
import com.efounder.eai.EAI;
import com.efounder.eai.application.JManagerApplication;
import com.efounder.eai.framework.JManagerActiveObject;
import com.efounder.eai.framework.bof.JBusinessActiveFramework;
import com.efounder.eai.framework.dal.JClientAbstractDataActiveFramework;
import com.efounder.eai.framework.dof.JDataActiveFramework;
import com.efounder.eai.registry.JLocalRegistry;
import com.efounder.eai.registry.JRegistry;
import com.efounder.resource.JResource;
import com.efounder.service.config.ConfigManager;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAIActiveComp extends EAI {
	public static final String PanEAI = "PanEAI";

	public EAIActiveComp() {
	}

	/**
	 * 
	 * @throws Exception
	 */
	public static void InitEAIComp() throws Exception {
		if (EAI.Registry == null) {
			EAIActiveComp activeComp = new EAIActiveComp();
			activeComp.initFramework(activeComp);
		}
	}
	
	/**
	 * 
	 * @param DComDM  EAIActiveComp
	 * @throws Exception
	 */
	private void initFramework(EAIActiveComp DComDM) throws Exception {
//		DComDM.Tier = "Middle";
//		DComDM.Company = "efounder";
//		DComDM.Tier = "Middle";
//		DComDM.Product = "JPublic";
//		DComDM.setLanguage("zh", "CN");
		DComDM.InitEAI();
	}
	  
	/**
	 * 初始化EAI
	 */
	public void InitEAI() throws Exception {
		InitLocal();// 初始化本地信息存储
	    InitRegistry();// 初始化注册表
	    InitObject();// 初始对象
	    InitBOF();// 初始化BOF
	    InitDOF();// 初始化DOF
	    InitDAL();// 初始化DAL
	    InitMA();// 初始应用管理器
	}

	// 初始化注册表
	private void InitRegistry() throws Exception {
		JRegistry reg = null;
	    URL url = null;
	    reg = InitRemoteRegistry();// 首先获取远程的注册表
	    if ( reg == null ) {
	    	reg = InitLocalRegistry();// 本地注册表
	    	if ( reg == null ) {
	    		reg = new JRegistry();// JAR包里的注册表"/"+Application+
	    		url = JResource.getResource(this,"/"+Application+"/Registry/","registry.xml",null);
	    		if(url != null) {
		    		reg.InitXMLURL(url);
		    		reg.InitRegistry();
	    		}
	    	}
	    }
	    Registry = reg;
	}
	  
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	JRegistry InitRemoteRegistry() {
		return null;
//	    JRegistry reg = new JRegistry();
//	    String FileName = "registry.xml";
//	    URL url = ConfigManager.getRemoteSpaceFile(ConfigManager.CONF_FILE_DIR,FileName,true);
//	    String PathName = ConfigManager.getLocalSpacePath(ConfigManager.CONF_FILE_DIR,true);
//	    boolean Res = reg.InitXMLURL(url);
//	    if ( Res ) {
//	      reg.InitRegistry();
//	      ConfigManager.saveSpaceFileToLocal(reg,PathName,FileName);
//	    } else {
//	      reg = null;
//	    }
//	    return reg;
	}
	  
	//------------------------------------------------------------------------------------------------
	  //描述:
	  //设计: Skyline(2001.12.29)
	  //实现: Skyline
	  //修改:
	  //------------------------------------------------------------------------------------------------
	  JRegistry InitLocalRegistry() {
	    return null;
//	    String PathName = ConfigManager.getLocalSpacePath(ConfigManager.CONF_FILE_DIR,true);
//	    String FileName = "registry.xml";
//	    JRegistry reg = new JRegistry();
//	    File file = new File(PathName+FileName);
//	    boolean Res = reg.InitXMLFile(file);
//	    reg.InitRegistry();
//	    if ( !Res ) reg = null;
//	    return reg;
	  }
	  
	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	static void GetLocalDir() {
		try {
			if(LocalUserHome != null) {
				File DirFile = new File(LocalUserHome);
				DirFile.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 初始化本地信息存储
	private static void InitLocal() throws Exception {
		//tomcat启动的时候，会初始化，单机版的时候不会
		if(InitBootObject.EASystem.get("LocalUserHome") != null) {
			LocalUserHome = (String) InitBootObject.EASystem.get("LocalUserHome");
		}
		// 初始化本地目录
		GetLocalDir();
		LocalRegistry = new JLocalRegistry();
		if(Product == null) {
			Product = EAI.Product;
		}
		String FileName = Product + ".inf";
		String PathName = ConfigManager.getLocalSpacePath(ConfigManager.CONF_FILE_DIR, true);
		LocalRegistry.Open(PathName + FileName);// LocalRegistryFileName
	}

	// 初始对象
	private static void InitObject() throws Exception {
		JManagerActiveObject.LoadAcitveObjects();
	}

	// 初始化BOF
	private static void InitBOF() throws Exception {
		// 创建业务对象管理框架
		BOF = new JBusinessActiveFramework();
		BOF.setCompany(Company);
		BOF.setProduct(Product);
		BOF.setTier(Tier);
		BOF.Initialize(null, null, null, null);
	}

	// 初始化DOF
	private static void InitDOF() throws Exception {
		DOF = new JDataActiveFramework();
		DOF.setCompany(Company);
		DOF.setProduct(Product);
		DOF.setTier(Tier);
		DOF.Initialize(null, null, null, null);
	}

	// 初始化DAL
	private static void InitDAL() throws Exception {
		DAL = new JClientAbstractDataActiveFramework();
		DAL.setCompany(Company);
		DAL.setProduct(Product);
		DAL.setTier(Tier);
		DAL.Initialize(null, null, null, null);
	}

	// 初始化应用管理器
	private static JManagerApplication InitMA() throws Exception {
		JManagerApplication Manager = new JManagerApplication();
		Manager.InitManager();
		MA = Manager;
		return Manager;
	}
}
