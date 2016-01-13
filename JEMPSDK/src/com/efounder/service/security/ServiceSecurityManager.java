package com.efounder.service.security;

//import com.core.core.*;
import java.util.*;
import org.openide.util.*;

import com.efounder.eai.EAI;
import com.efounder.eai.data.*;
import com.efounder.pub.util.*;
import com.efounder.service.command.Command;
import com.efounder.builder.base.data.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class ServiceSecurityManager {
  /**
   *
   */
  protected java.util.List functionList = null;
  /**
   *
   */
  protected static ServiceSecurityManager defaultSecurityManager = null;
  /**
   *
   */
  protected String codeObject = "ACZRZX";
  /**
   *
   * @param co String
   */
  public void setCodeObject(String co) {
    codeObject = co;
  }
  /**
   *
   * @return String
   */
  public String getCodeObject() {
    return codeObject;
  }
  /**
   *
   */
  protected java.util.Map codeGuidMap = null;
  /**
   *
   * @param cgs CodeGuidStub
   */
  public void registryCodeGuid(CodeGuidStub cgs) {
    if ( codeGuidMap == null ) codeGuidMap = new java.util.HashMap();
    codeGuidMap.put(cgs.getCode(),cgs);
  }
  /**
   *
   * @param code String
   * @return boolean
   */
  public boolean checkCodeGuidSerialNo(String code) {
    if ( !standalone ) return true;
    if ( codeGuidMap == null ) return false;
    if(code.length()==0)return false;
    int i=0;
    String strTemp;
    while (i <code.length()) {
        i++;
        strTemp = code.substring(0, i);
        CodeGuidStub cgs = (CodeGuidStub) codeGuidMap.get(strTemp);
        if (cgs == null)
            continue;
        boolean b = checkCodeGuidSerialNo(cgs);
        if (b)
            return true;
    }
    return false;
  }
  /**
   *
   * @param cgs CodeGuidStub
   * @return boolean
   */
  public boolean checkCodeGuidSerialNo(CodeGuidStub cgs) {
    return checkGUID(cgs.getCode(),cgs.getGuid(),cgs.getSerialNo());
  }
  /**
   *
   * @param code String
   * @param guid1 String
   * @param guid2 String
   * @return boolean
   */
  private static boolean checkGUID(String code,String guid1,String guid2) {
    String guid = MD5Tool.getDefault().getMD5ofStr(code.trim() + guid1.trim());
    return guid2.trim().equals(guid);
  }
  /**
   *
   * @param code String
   */
  public void unregistryCodeGuid(String code) {
    if ( codeGuidMap == null ) return;
    codeGuidMap.remove(code);
  }

  /**
   *
   * @param FSO FunctionStubObject
   */
  public void registryFunction(FunctionStubObject FSO) {
    if ( FSO == null ) return;
    if ( functionList == null ) functionList = new ArrayList();
    functionList.add(FSO);
  }
  /**
   *
   */
  protected boolean standalone = false;
  /**
   *
   * @return boolean
   */
  public boolean isStandalone() {
    return standalone;
  }
  /**
   *
   * @param sd boolean
   */
  public void setStandalone(boolean sd) {
    standalone = sd;
  }

  /**
   *
   */
  protected ServiceSecurityManager() {

  }
  /**
   * @return SecurityManager
   */
  public static ServiceSecurityManager getDefault() {
    if ( defaultSecurityManager == null ) {
      defaultSecurityManager = (ServiceSecurityManager) Lookup.getDefault().lookup(com.efounder.service.security.ServiceSecurityManager.class, EAI.Product);
      if ( defaultSecurityManager != null )
        defaultSecurityManager.initContext();
    }
    return defaultSecurityManager;
  }
  
  /**
   * @return SecurityManager
   */
  public static ServiceSecurityManager getDefault(String key) {
    if ( defaultSecurityManager == null ) {
      defaultSecurityManager = (ServiceSecurityManager) Lookup.getDefault().lookup(com.efounder.service.security.ServiceSecurityManager.class, key);
      if ( defaultSecurityManager != null )
        defaultSecurityManager.initContext();
    }
    return defaultSecurityManager;
  }
  public abstract Object initContext();
  public abstract Object getContext();
  protected abstract Object DoLogin(Object p1,Object p2,Object p3,Object p4) throws Exception ;
  public abstract Object Login(Object p1,Object p2,Object p3,Object p4) throws Exception ;
  protected abstract Object DoLogoff(Object p1,Object p2,Object p3,Object p4) throws Exception ;
  public abstract Object Logoff(Object p1,Object p2,Object p3,Object p4) throws Exception ;
  public abstract Map getAccessControllerMap(JParamObject PO,String qxbh);
  public final JResponseObject loginProduct(JParamObject paramObject) throws Exception {
    return loginProduct(paramObject,null);
  }
  public abstract JResponseObject loginProduct(JParamObject paramObject,Object callObject) throws Exception;
  /**
   *
   * @param Key String
   * @return boolean
   */
  public boolean checkPermission(String Key) {
    return checkPermission(Key,null);
  }
  /**
   *
   * @param Key String
   * @param Value String
   * @return boolean
   */
  public boolean checkPermission(String Key,String Value) {
    if ( Key == null || "".equals(Key.trim()) ) return true;
    JParamObject PO = JParamObject.Create();
    String UserName = PO.GetValueByEnvName("UserName");
    return checkPermission(UserName,Key,Value);
  }
  /**
   * @param UserName String
   * @param Key String
   * @param Value String
   * @return boolean
   */
  public abstract boolean checkPermission(String UserName,String Key,String Value) ;
  /**
   *
   * @param USO UserStubObject
   * @param command Command
   * @return Object
   */
  public Object checkPermission(UserStubObject USO,Command command) throws Exception {
    java.util.List functionList = USO.getFunctionList();
    FunctionStubObject FSO = null;
    for(int i=0;i<functionList.size();i++) {
      FSO = (FunctionStubObject)functionList.get(i);
      if ( command.getFunction().equals(FSO) ) {
        return FSO;
      }
    }
    throw new SecurityException("无权使用"+command.getCaption()+"功能�\uFFFD");
  }
  /**
   *
   * @param OBJ_ID String
   * @param rowSet EFRowSet
   * @param bit String
   * @return boolean
   */
  public boolean checkDataAccessController(String OBJ_ID,String OBJ_BM,EFRowSet rowSet,String bit) {

    if ( OBJ_ID.equals(codeObject) ) {
      //���������
      boolean check = this.checkCodeGuidSerialNo(OBJ_BM);
      if ( !check ) return false;
    }
    return "1".equals(rowSet.getString("F_G" + bit, "0"));
  }
  public void initCodeLicence(String code){
    if(!standalone)return;
    int i=0;
    String strTemp;
    while (i < code.length()) {
      i++;
      strTemp = code.substring(0, i);
      CodeGuidStub cgs = (CodeGuidStub) codeGuidMap.get(strTemp);
      if (cgs == null)
        continue;
      boolean b = checkCodeGuidSerialNo(cgs);
      if (b){
        System.getProperties().setProperty("offlineCode",cgs.getCode());
        System.getProperties().setProperty("offlineGuid",cgs.getGuid());
        System.getProperties().setProperty("offlineSerial",cgs.getSerialNo());

        return;
      }
    }

  }
  /**
   *
   * @param Key String
   * @param Value String
   * @param index int
   * @return boolean
   * @throws Exception
   */
  public abstract boolean getAccessController1(String Key,String Value,int index) throws Exception ;
  /**
   *
   * @param Key String
   * @param index int
   * @return String
   * @throws Exception
   */
  public abstract String getAccessController2(String Key,int index) throws Exception ;
  /**
   *
   * @param Key String
   * @param index int
   * @return String
   * @throws Exception
   */
  public abstract String getAccessController3(String Key,int index) throws Exception ;

  protected long timeBeginServer = 0;
  protected long timeBeingClient  = 0;

  protected java.util.Vector currentLogList = new Vector();
  /**
   *
   * @return Vector
   */
  public java.util.Vector getCurrentLogList() {
    return currentLogList;
  }
  /**
   *
   * @param gnbh String
   * @param gnmc String
   * @return OperateLoginfo
   */
  public OperateLoginfo createLoginfo(String gnbh,String gnmc) {
    OperateLoginfo ql = OperateLoginfo.getInstance(gnbh,gnmc);
    ql.setStartTime(timeBeginServer+(System.currentTimeMillis()-this.timeBeingClient));
    currentLogList.add(ql);
    return ql;
  }
  /**
   *
   */
  protected java.util.Vector writeLogList = new Vector();
  /**
   *
   * @return List
   */
  public java.util.List getWriteLogList() {
    java.util.List logList = new ArrayList();
    logList.addAll(writeLogList);writeLogList.clear();
    return logList;
  }
  /**
   *
   * @return List
   */
  public java.util.List getAllLogList() {
    java.util.List logList = new ArrayList();
    logList.addAll(writeLogList);writeLogList.clear();
    logList.addAll(currentLogList);currentLogList.clear();
    return logList;
  }
  /**
   *
   * @param ql OperateLoginfo
   */
  public void writeOperateLog(OperateLoginfo ql) {
    // 设置结束时间
    long time = this.timeBeginServer+(System.currentTimeMillis()-this.timeBeingClient);
    ql.setEndTime(time);
    currentLogList.remove(ql);
    writeLogList.add(ql);
  }
}
