package com.pub.comp;

import java.util.*;

/**
 * <p>Title: 非公用类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


public interface IStubObject {
  public String getBH();
  public String getMC();
  public String getID();
  public void setCaption(String cap);
  public boolean isMX();
  public int    getJS();
  public int    getStatus();
  public boolean isUse();
  public IStubObject getParent();
  public void setParent(IStubObject iso);
  public List getChilds();
  public void addChild(IStubObject iso);
  public void delChild(IStubObject iso);
  public IStubObject findChild(String id);
  public String toString();

  public Object getObject(Object FieldName);
  public Object getObject(Object FieldName,Object def);
  public void   setObject(Object FieldName,Object v);


  public String getString(String FieldName);
  public String getString(String FieldName,String def);
  public void   setString(String FieldName,String v);

  public Integer    getInt(String FieldName);
  public Integer    getInt(String FieldName,int def);
  public void   setInt(String FieldName,int v);

  public Double getDouble(String FieldName);
  public Double getDouble(String FieldName,double def);
  public void   setDouble(String FieldName,double v);

  public Boolean    getBoolean(String FieldName);
  public Boolean    getBoolean(String FieldName,boolean def);
  public void       setBoolean(String FieldName,boolean v);

  public Date    getDate(String FieldName);
  public Date    getDate(String FieldName,Date def);
  public void    setDate(String FieldName,Date v);


}