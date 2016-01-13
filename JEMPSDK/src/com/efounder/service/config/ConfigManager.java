package com.efounder.service.config;

import java.io.*;

import com.efounder.eai.*;
import com.efounder.pub.util.FileUtil;
import com.efounder.service.base.BaseManager;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ConfigManager implements BaseManager {
  protected static ConfigManager defaultConfigManager = null;
  public static final String CONF_FILE_DIR = "CnfgSpace";
  public static final String CODE_FILE_DIR = "CodeSpace";
  public static final String EAIP_FILE_DIR = "EaipSpace";
  public static final String JNLP_FILE_DIR = "JnlpSpace";
  public static final String REGE_FILE_DIR = "RegeSpace";
  public static final String OFFL_FILE_DIR = "OfflSpace";
  public static final String LOG_FILE_DIR  = "LogSpace";
  protected File   ConfigSpaceFile = null;
  protected File   WEB_INF_CfgFile = null;
//  protected File   WEB_INF_ServiceFile = null;
  protected ConfigManager() {
  }
  /**
   *
   * @return ConfigManager
   */
  public static synchronized ConfigManager getDefault() {
    if ( defaultConfigManager == null ) {
      defaultConfigManager = new ConfigManager();//(ConfigManager) Lookup.getDefault().lookup(ConfigManager.class);
      defaultConfigManager.initContext();
    }
    return defaultConfigManager;
  }
  /**
   *
   */
  protected void initContext() {
//	  appendMethodB("c:\\test.txt",  EAI.LocalUserHome+"WEB-INF/CnfgSpace");
    String sp = System.getProperty("file.separator");
    String ConfigSpaceFilePath = EAI.LocalUserHome+"CnfgSpace"+sp;//+EAI.Application+sp;
    ConfigSpaceFile = new File(ConfigSpaceFilePath);
    ConfigSpaceFile.mkdirs();
    ConfigSpaceFilePath = EAI.LocalUserHome+"WEB-INF/CnfgSpace"+sp;
    WEB_INF_CfgFile = new File(ConfigSpaceFilePath);
    WEB_INF_CfgFile.mkdirs();
    ConfigSpaceFilePath = EAI.LocalUserHome+"WEB-INF/ServiceSpace"+sp;
    File WEB_INF_ServiceFile = new File(ConfigSpaceFilePath);
    WEB_INF_ServiceFile.mkdirs();
    ConfigSpaceFilePath = EAI.LocalUserHome+"WEB-INF/FormSpace"+sp;
    File WEB_INF_FormFile = new File(ConfigSpaceFilePath);
    WEB_INF_FormFile.mkdirs();
    ConfigSpaceFilePath = EAI.LocalUserHome+"WEB-INF/FlowSpace"+sp;
    File WEB_INF_FlowFile = new File(ConfigSpaceFilePath);
    WEB_INF_FlowFile.mkdirs();

    ConfigSpaceFilePath = EAI.LocalUserHome+"WEB-INF/FLEXFormSpace"+sp;
    WEB_INF_FlowFile = new File(ConfigSpaceFilePath);
    WEB_INF_FlowFile.mkdirs();

    ConfigSpaceFilePath = EAI.LocalUserHome+"WEB-INF/FLEXFlowSpace"+sp;
    WEB_INF_FlowFile = new File(ConfigSpaceFilePath);
    WEB_INF_FlowFile.mkdirs();
  }
  
  /**
   * B方法追加文件：使用FileWriter
   */
//  public void appendMethodB(String fileName, String content) {
//      try {
//          //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
//          FileWriter writer = new FileWriter(fileName, true);
//          writer.write(content);
//          writer.close();
//      } catch (IOException e) {
//          e.printStackTrace();
//      }
//  }
  
  /**
   *
   * @param fileName String
   * @param datas byte[]
   * @throws Exception
   */
  public void writeServiceFile(String type,String serviceName,byte[] datas) throws Exception {
    String fileUrl = EAI.LocalUserHome+"WEB-INF/"+type+"/"+serviceName+".xml";
    File file = new File(fileUrl);
    file.createNewFile();
    FileUtil.writeFile(file,datas);
  }
  /**
   *
   * @param serviceName String
   * @return byte[]
   * @throws Exception
   */
  public byte[] readServiceFile(String type,String serviceName) throws Exception {
    String fileUrl = EAI.LocalUserHome+"WEB-INF/"+type+"/"+serviceName+".xml";
    File file = new File(fileUrl);
    if ( !file.exists() ) return null;
    return FileUtil.readFile(file);
  }
  /**
   *
   * @param typeName String
   * @param fileName String
   * @return File
   */
  public File getPackageFile(String typeName,String fileName) {
    String fileUrl = EAI.LocalUserHome+"WEB-INF/classes/Package/"+typeName+"/"+fileName;
    File file = new File(fileUrl);
    return file.exists()?file:null;
  }
  /**
   *
   * @param typeName String
   * @return File
   */
  public File getPackageDir(String typeName) {
    String fileUrl = EAI.LocalUserHome+"WEB-INF/classes/Package/"+typeName;
    File file = new File(fileUrl);
    return file.exists()?file:null;
  }
  /**
   *
   * @return Iterator
   */
  public java.util.Iterator Enumerate() {
    return null;
  }
  /**
   *
   * @param ManagerKey String
   * @param stubObjects Object
   * @throws Exception
   */
  public synchronized void saveConfig(String ManagerKey,Object stubObjects) throws Exception {
    String Filename = ManagerKey+".cfg";
    File ConfigFile = new File(ConfigSpaceFile,Filename);
    ConfigFile.createNewFile();
    FileUtil.writeObj2XMLFile(stubObjects,ConfigFile);
  }
  /**
   *
   */
  protected java.util.Map cfgDataMap = null;
  /**
   *
   * @param managerKey String
   * @param cfgData Object
   */
  protected void putCfgData(String managerKey,Object cfgData) {
    if ( cfgDataMap == null ) cfgDataMap = new java.util.HashMap();
    if ( cfgData != null )
      cfgDataMap.put(managerKey,cfgData);
    else
      cfgDataMap.remove(managerKey);
  }
  /**
   *
   * @param managerKey String
   * @param def Object
   * @return Object
   * @throws Exception
   */
  public Object getCfgData(String managerKey,Object def) throws Exception {
    Object cfgData = null;
    if ( cfgDataMap != null ) {
      cfgData = cfgDataMap.get(managerKey);
    }
    if ( cfgData == null ) {
      cfgData = loadWebInfCfg(managerKey);
      if ( cfgData != null ) putCfgData(managerKey,cfgData);else cfgData = def;
    }
    return cfgData;
  }
  /**
   *
   * @param ManagerKey String
   * @param stubObjects Object
   * @throws Exception
   */
  public synchronized void saveWebInfCfg(String ManagerKey,Object stubObjects) throws Exception {
    String Filename = ManagerKey+".cfg";
    File ConfigFile = new File(WEB_INF_CfgFile,Filename);
    ConfigFile.createNewFile();
    FileUtil.writeObj2XMLFile(stubObjects,ConfigFile);
    putCfgData(ManagerKey,stubObjects);
  }
  /**
   *
   * @param ManagerKey String
   * @return StubObject[]
   */
  public synchronized Object loadConfig(String ManagerKey) throws Exception {
    String Filename = ManagerKey+".cfg";
    File ConfigFile = new File(ConfigSpaceFile,Filename);
    if ( !ConfigFile.exists() ) return null;
    return FileUtil.readObjFromXMLFile(ConfigFile);
  }
  /**
   *
   * @param ManagerKey String
   * @return StubObject[]
   */
  public synchronized Object loadWebInfCfg(String ManagerKey) throws Exception {
    String Filename = ManagerKey+".cfg";
    File ConfigFile = new File(WEB_INF_CfgFile,Filename);
    if ( !ConfigFile.exists() ) return null;
    return FileUtil.readObjFromXMLFile(ConfigFile);
  }
  
  /**
   *
   * @param ConfType String
   * @param isProduct boolean
   * @return String
   */
  public static String getLocalSpacePath(String ConfType,boolean isProduct) {
	  String  PathName = null;
	  String sp = System.getProperty("file.separator");
	  String ProductPath = isProduct?EAI.Product+sp:"";
//	  PathName = EAI.LocalUserHome+ConfType+sp+EAI.Application+sp+ProductPath;
	  PathName = EAI.LocalUserHome+ConfType+sp+ProductPath;
	  File DirFile = new File(PathName);
	  DirFile.mkdirs();
	  return PathName;
  }
}
