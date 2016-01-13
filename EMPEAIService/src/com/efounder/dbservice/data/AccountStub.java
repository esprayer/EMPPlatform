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

public class AccountStub extends StubObject implements java.io.Serializable {
  static final long serialVersionUID = 1L;
  private static int createCount = 1;
  public AccountStub() {

    setCaption("新建账套"+createCount++);
  }
  public String getDataStorageId(){
    return (String)this.getObject("${DataStorageId}",null);
  }
  public void setDataStorageId(String dsId){
    this.setObject("${DataStorageId}",dsId);
  }
  public String getDataStorageName(){
    return (String)this.getObject("${DataStorageName}",null);
  }
  public void setDataStorageName(String dsn){
    this.setObject("${DataStorageName}",dsn);
  }
  public String getDataStorageType(){
    return (String)this.getObject("${DataStorageType}",null);
  }
  public void setDataStorageType(String dst){
    this.setObject("${DataStorageType}",dst);
  }
  public String getDataStorageHost(){
    return (String)this.getObject("${DataStorageHost}",null);
  }
  public void setDataStorageHost(String dsh){
    this.setObject("${DataStorageHost}",dsh);
  }
  public String getDataStoragePort(){
    return (String)this.getObject("${DataStoragePort}",null);
  }
  public void setDataStoragePort(String dsp){
    this.setObject("${DataStoragePort}",dsp);
  }
  public String getDataStorageCharset(){
    return (String)this.getObject("${DataStorageCharset}",null);
  }
  public void setDataStorageCharset(String dsc){
    this.setObject("${DataStorageCharset}",dsc);
  }
  public String getDSType(){
    return (String)this.getObject("${type}",null);
  }
  public void setDSType(String dst){
    this.setObject("${type}",dst);
  }
  public String getDBDriver(){
    return (String)this.getObject("${jdbcDriverClass}",null);
  }
  public void setDBDriver(String driver){
    this.setObject("${jdbcDriverClass}",driver);
  }
  public String getDBURL(){
    return (String)this.getObject("${jdbcUrl}",null);
  }
  public void setDBURL(String dburl){
    this.setObject("${jdbcUrl}",dburl);
  }
  public String getDataSource(){
    return (String)this.getObject("${dataSource}",null);
  }
  public void setDataSource(String dataSource){
    this.setObject("${dataSource}",null);
  }
  public String getAccountId(){
    return (String)this.getObject("${accountID}",null);
  }
  public void setAccountId(String accId){
    this.setObject("${accountID}",accId);
  }
  public String getAccountName(){
     return (String)this.getObject("${accountName}",null);
  }
  public void setAccountName(String accountName){
    this.setObject("${accountName}",accountName);
  }
  public String getUserId(){
    return (String)this.getObject("${user}",null);
  }
  public void setUserId(String userId){
    this.setObject("${user}",userId);
  }
  public String getUserpass(){
    return (String)this.getObject("${password}",null);
  }
  public void setUserpass(String userpass){
    this.setObject("${password}",userpass);
  }
  public StubObject getDBAttr(){
    return (StubObject) this.getObject("${extendAttr}",null);
  }
  public void setDBAttr(StubObject attrStub){
    this.setObject("${extendAttr}",attrStub);
  }

}
