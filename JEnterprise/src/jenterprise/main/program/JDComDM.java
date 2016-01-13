package jenterprise.main.program;
//// 应用程管理器
//import jbof.application.classes.*;
//// 业务对象管理框架
//import jbof.foundation.classes.*;
//import jdal.foundation.classes.*;
//// 数据对象管理框架
//import jdof.foundation.classes.*;
//import jframework.foundation.classes.*;
//import jtoolkit.registry.classes.*;
//import jframework.resource.classes.*;
import java.io.File;

import com.efounder.eai.application.classes.JBOFApplicationManager;
import com.efounder.eai.framework.JManagerActiveObject;
import com.efounder.eai.framework.bof.JBusinessActiveFramework;
import com.efounder.eai.framework.dal.JClientAbstractDataActiveFramework;
import com.efounder.eai.framework.dof.JDataActiveFramework;
import com.efounder.eai.registry.JLocalRegistry;

import jframework.foundation.classes.JActiveDComDM;
import jframework.foundation.classes.JManagerRequestObject;
import jframework.resource.classes.JXMLResource;
import jtoolkit.registry.classes.JXMLRegistry;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述: 组件对象容器
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JDComDM extends JActiveDComDM {
//  public JBOFApplicationManager    BOFApplicationManager    = null;       // 应用程序管理器
//  public JBusinessActiveFramework  BusinessActiveFramework  = null;       // 业务对象管理框架
//  public JDataActiveFramework      DataActiveFramework      = null;       // 数据对象管理框架

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JDComDM() {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void GetLocalDir() {
    String Fileseparator = System.getProperty("file.separator");
    String LocalRegistryFileName = System.getProperty("user.home")+Fileseparator;//+JActiveDComDM.Product+Fileseparator;
    try {
      File DirFile = new File(LocalRegistryFileName + JActiveDComDM.Product);
      if ( DirFile.mkdir() || DirFile.exists() ) {
        LocalRegistryFileName += JActiveDComDM.Product + Fileseparator;
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    JActiveDComDM.LocalUserHome = LocalRegistryFileName;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object Initialize(String Param,String Data,Object CustomObject,Object AdditiveObject) {
    // ClassLoader
    String Lang;
    FrameworkClassLoader = this.getClass().getClassLoader();//new JFrameworkClassLoader(this.getClass().getClassLoader(),CodeBase);
    // 初始本地资源
    GetLocalDir();
    JActiveDComDM.LocalRegistry = new JLocalRegistry();
    JActiveDComDM.LocalRegistry.Open(JActiveDComDM.LocalUserHome+"LocalRegistry.inf");//LocalRegistryFileName

    Lang = JActiveDComDM.LocalRegistry.Get("Language", "zh");
    if ( Lang != null ) International = Lang;
    JActiveDComDM.FrameworkClassLoader = getClass().getClassLoader();
    // 初始化资源装入器
    JXMLResource.InitResource(CodeBase,International);
    // 系统注册表初始化
    JActiveDComDM.XMLRegistry = new JXMLRegistry();
    JActiveDComDM.XMLRegistry.InitRegistry(BofXmlFile);
//    System.out.print(JXMLRegistry.document.toString());
    // 活动对象管理器初始化,装入系统中所有的活动对象(只是生成ActiveObjectStub)
    JManagerActiveObject.LoadAcitveObjects();
    // 创建应用程序管理器
    BOFApplicationManager   = new JBOFApplicationManager();
    // 创建业务对象管理框架
    BusinessActiveFramework = new JBusinessActiveFramework();
    BusinessActiveFramework.FrameworkClassLoader = FrameworkClassLoader;
    // 创建数据对象管理器
    DataActiveFramework     = new JDataActiveFramework();
    DataActiveFramework.FrameworkClassLoader = FrameworkClassLoader;
    // 创建抽像数据对象
    AbstractDataActiveFramework = new JClientAbstractDataActiveFramework();
    AbstractDataActiveFramework.FrameworkClassLoader = FrameworkClassLoader;
    // 应用程序管理器初始化
    BOFApplicationManager.setCompany(Company);
    BOFApplicationManager.setProduct(Product);
    BOFApplicationManager.setTier(Tier);
    BOFApplicationManager.Initialize(Param,Data,CustomObject,AdditiveObject);
    // 业务对象管理框架初始化
    BusinessActiveFramework.setCompany(Company);
    BusinessActiveFramework.setProduct(Product);
    BusinessActiveFramework.setTier(Tier);
    BusinessActiveFramework.Initialize(Param,Data,CustomObject,AdditiveObject);
    // 数据对象管理器初始化
    DataActiveFramework.setCompany(Company);
    DataActiveFramework.setProduct(Product);
    DataActiveFramework.setTier(Tier);
    DataActiveFramework.Initialize(Param,Data,CustomObject,AdditiveObject);
    // 抽像数据对象初始化(此对象是代理对象,用于调用后台的抽像数据对象)
    AbstractDataActiveFramework.setCompany(Company);
    AbstractDataActiveFramework.setProduct(Product);
    AbstractDataActiveFramework.setTier(Tier);
    //AbstractDataActiveFramework.Initialize(Param,Data,CustomObject,AdditiveObject);
    // 创建请求对象管理器
    ManagerRequestObject = new JManagerRequestObject();
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object Destroy() {
    // 应用程序管理器执行清除程
    if ( BOFApplicationManager != null ) {
      BOFApplicationManager.Destroy();
      BOFApplicationManager = null;
    }
    if ( BusinessActiveFramework != null ) {
      BusinessActiveFramework.Destroy();
      BusinessActiveFramework = null;
    }
    if ( DataActiveFramework != null ) {
      DataActiveFramework.Destroy();
      DataActiveFramework = null;
    }
    if ( AbstractDataActiveFramework != null ) {
      //AbstractDataActiveFramework.Destroy();
      AbstractDataActiveFramework = null;
    }
    return null;
  }
}
/*
1.数据描象层 				        jdals
2.数据对象与数据对象框架层		jbaos
3.业务对象与业务对象框架层		jdaos
*/