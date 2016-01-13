package com.efounder.dbservice.data;

import com.core.xml.StubObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DataStorageStub extends StubObject implements java.io.Serializable {
  static final long serialVersionUID =1L;
  private static int createCount = 1;
  public DataStorageStub() {
    setID("DataStorage"+String.valueOf(createCount));
    setCaption("新建数据服务"+createCount++);
  }
  public String getDataStorageId(){
    return (String)this.getObject("${DataStorageId}",null);
  }
  public void setDataStorageId(String dsi){
    this.setObject("${DataStorageId}",dsi);
  }
  public String getDataStorageName(){
    return (String)this.getObject("${DataStorageName}",null);
  }
  public void setDataStorageName(String dsn){
    this.setObject("${DataStorageName}",dsn);
  }
  public String getDBType(){
    return (String)this.getObject("${type}",null);
  }
  public void setDBType(String type){
    this.setObject("${type}",type);
  }
  public String getJDBCDriverClass(){
    return (String)this.getObject("${jdbcDriverClass}",null);
  }
  public void setJDBCDriverClass(String driverClass){
    this.setObject("${jdbcDriverClass}",driverClass);
  }
  public String getJDBCUrl(){
    return (String)this.getObject("${jdbcUrl}",null);
  }
  public void setJDBCUrl(String url){
    this.setObject("${jdbcUrl}",url);
  }
  public String getDBHost(){
    return (String)this.getObject("${host}",null);
  }
  public void setDBHost(String host){
    this.setObject("${host}",host);
  }
  public String getDBPort() {
    return (String)this.getObject("${port}",null);
  }
  public void setDBPort(String port){
    this.setObject("${port}",port);
  }
  public String getDataBaseName(){
    return (String)this.getObject("${database}",null);
  }
  public void setDataBaseName(String dbn){
    this.setObject("${database}",dbn);
  }
  public String getDBUser(){
    return (String)this.getObject("${user}",null);
  }
  public void setDBUser(String user){
    this.setObject("${user}",user);
  }
  public String getDataSource(){
    return (String)this.getObject("${dataSource}",null);
  }
  public void setDataSource(String dataSource){
    this.setObject("${dataSource}",dataSource);
  }
  public StubObject getDBAttr(){
    return (StubObject) this.getObject("${extendAttr}",null);
  }
  public void setDBAttr(StubObject attrStub){
    this.setObject("${extendAttr}",attrStub);
  }

}
