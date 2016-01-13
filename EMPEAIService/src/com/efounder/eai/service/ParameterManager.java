package com.efounder.eai.service;

import java.sql.*;
import java.util.*;

import com.core.xml.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.sql.*;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.buffer.DictDataBuffer;
import com.efounder.buffer.util.ConifgDataUtil;
import com.efounder.builder.base.data.*;

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
public class ParameterManager {
  protected ParameterManager() {
  }
  protected static ParameterManager pm = null;
  public static ParameterManager getDefault() {
    if ( pm == null ) {
      pm = new ParameterManager();
      if ( !pm.initParameterManager() ) return null;
    }
    return pm;
  }
  protected static  Map sysMap=null;//
  public Map getSysMap() {
    Map map = new HashMap();
    map.putAll(sysMap);
    return map;
  }
  public  boolean initParameterManager() {
    if(sysMap==null)
      sysMap = new HashMap();
    Vector v = PackageStub.getContentVector("System_Variable");
    for (int i = 0; v!=null&&i < v.size(); i++) {
      StubObject so=(StubObject)v.get(i);
      setSystemParam((String)so.getID(),so.getString("value",""));
    }
    return true;
  }
  public void setSystemParam(String key, String val) {
    if(val==null||val.trim().length()==0)
      sysMap.remove(key);
    else
      sysMap.put(key, val);
  }
  public String getSystemParam(String key) {
    if(sysMap==null)return "";
    String val = (String) sysMap.get(key);
    return val == null ? "" : val;
  }
  public String getDefaultModelName(){
    String model="ACPZModel";
    StubObject so=ConifgDataUtil.getConfigStub("System_Variable",EAI.Product+"_MODEL");
    if(so!=null)model=so.getString("value",model);
    if(model.indexOf(",")!=-1)model=model.substring(0,model.indexOf(","));
    return model;
  }
  public  String getUNIT_ID(JParamObject PO) {
    return PO.GetValueByEnvName("UNIT_ID", "");
  }
  public  String getGSDM(JParamObject PO) {
    return PO.GetValueByEnvName("F_GSDM", "");
  }
  public String getPZBHFS(JParamObject PO) {
    return PO.GetValueByEnvName("ZW_PZBHFS", "");
  }
  public String getLoginZRZX(JParamObject PO) {
    return PO.GetValueByEnvName("CW_ZWZRZX", "");
  }
  public String getZRZX(JParamObject PO) {
    return PO.GetValueByEnvName("CW_ZWZRZX", "");
  }
  public String getMAXQJ(JParamObject PO) {
    return PO.GetValueByEnvName("CW_MAXQJ", "16");
  }

  public String getCWRQ(JParamObject PO) {
    return PO.GetValueByEnvName("CurFaDate", "");
  }

  public String getUserName(JParamObject PO) {
    return PO.GetValueByEnvName("UserName", "");
  }

  public String getLoginDate(JParamObject PO) {
    return PO.GetValueByEnvName("LoginDate", "");
  }

  public int getJEDECN(JParamObject PO) {
    String ii=PO.GetValueByEnvName("ZW_JEDECN", "2");
    return Integer.parseInt(ii);
  }

  public int getSLDECN(JParamObject PO) {
    String ii=PO.GetValueByEnvName("ZW_SLDECN", "2");
    return Integer.parseInt(ii);
  }

  public int getDJDECN(JParamObject PO) {
    String ii=PO.GetValueByEnvName("ZW_DJDECN", "2");
    return Integer.parseInt(ii);
  }

  public int getWBDECN(JParamObject PO,String wbbh) {
    String ii=PO.GetValueByEnvName("ZW_WBDECN", "2");
    EFDataSet eds = (EFDataSet) DictDataBuffer.getDefault(PO).getDataItem(
               DictDataBuffer.DCTDATASET, "LSWBZD");
    if(eds==null)return Integer.parseInt(ii);
    EFRowSet ers=(EFRowSet) eds.getRowSet(wbbh);
    if(ers==null)return Integer.parseInt(ii);
    ii=ers.getString("F_SCALE","2");
    try{
      return Integer.parseInt(ii);
    }catch(Exception e){
      return 2;
    }
  }

  public int getHLDECN(JParamObject PO) {
    String ii=PO.GetValueByEnvName("ZW_HLDECN", "2");
    return Integer.parseInt(ii);
  }

  public boolean isQYDBWB(JParamObject PO) {
    String qybwb = PO.GetValueByEnvName("ZW_BWB", "0");
    return "1".equals(qybwb);
  }
  public void initServerContext(ESPServerContext context){
    String mdl=getDefaultModelName();
    JParamObject PO= context.getParamObject();
    mdl=PO.GetValueByParamName("MDL_ID",mdl);

    try {
      BIZMetaData bizMetaData = (BIZMetaData) MetaDataManager.
          getBIZMetaDataManager().getMetaData(context, mdl);
      PO.setLosableValue(PO.DEF_BIZMODEL,bizMetaData);
      context.putObject(PO.DEF_BIZMODEL,bizMetaData);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }
  //获取某一个具体模型的配制
  //客户端用
  public String getModelConfigValue(JParamObject PO,String MDL_ID,String key,String def){
  try {
	  if(MDL_ID==null||MDL_ID.trim().length()==0)return def;
      BIZMetaData bmd = (BIZMetaData) MetaDataManager.
          getBIZMetaDataManager().getMetaData( MDL_ID);
      if(bmd!=null){
    	  String uid=PO.GetValueByEnvName("UNIT_ID","");
           return bmd.getSYS_MDL_VAL(uid,key, def);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return def;
  }
  //服务端用
  public String getModelConfigValue(ESPServerContext esc,String MDL_ID,String key,String def){
	  JParamObject PO=esc.getParamObject();
	  try {
		  if(MDL_ID==null||MDL_ID.trim().length()==0)return def;
	      BIZMetaData bmd = (BIZMetaData) MetaDataManager.
	          getBIZMetaDataManager().getMetaData(esc,MDL_ID);
	      if(bmd!=null){
	    	  String uid=PO.GetValueByEnvName("UNIT_ID","");
	           return bmd.getSYS_MDL_VAL(uid, uid, def);
	      }
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return def;
	  }
  /**
   * 权限是否审核
   * @return boolean
   */
  public boolean isUseLimitAudit() {
      String isUseLimitAudit = getSystemParam("UseLimitAudit");
      if (isUseLimitAudit == null) isUseLimitAudit = "";
      return "1".equals(isUseLimitAudit) || "YES".equals(isUseLimitAudit.toUpperCase());
  }

  /**
   * 角色是否使用数据权限
   * @return boolean
   */
  public boolean isRoleUseLimit() {
      String isRoleUseLimit = getSystemParam("RoleUseLimit");
      if (isRoleUseLimit == null) isRoleUseLimit = "";
      return "1".equals(isRoleUseLimit) || "YES".equals(isRoleUseLimit.toUpperCase());
  }

  /**
   *
   * @return String
   */
  public String getTimerManagerServer() {
    return getSystemParam("TimerManagerServer");
  }

  /**
   * 返回标准平台服务器id，默认是StandardServer
   * @return String
   */
  public String getStandardServer() {
      return getSystemParam("StandardServer");
  }

  /**
   * TaskManager的代理类型,默认是Memcache(Memcache or database or svrfile).
   * 多线程任务管理功能使用.
   * @return String
   */
  public String getTaskManagerAgentType() {
      return getSystemParam("TaskManagerAgentType");
  }

  /**
   * TaskManager的代理服务器,即客户端统一去该服务器上异步管理多线程任务.
   * 多线程任务管理功能使用.
   * @return String
   */
  public String getTaskManagerAgentServer() {
    return getSystemParam("TaskManagerAgentServer");
  }

  /**
   * TaskManager的代理监视线程的执行间隔(单位s).
   * 多线程任务管理功能使用.
   * @return String
   */
  public String getDispatchTaskUpdaterDelay() {
      return getSystemParam("DispatchTaskUpdaterDelay");
  }

}
