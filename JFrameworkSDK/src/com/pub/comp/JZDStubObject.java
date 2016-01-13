package com.pub.comp;

import java.util.*;

/**
 * <p>Title: 非公用类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JZDStubObject implements IStubObject {
  static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.pub.comp.Language");
  List ChildList = null;
  IStubObject    Parent = null;
  String Caption = null;
  public String getBH() {
    return null;
  }
  public String getMC(){
    return null;
  }

  public String getID(){
    return null;
  }

  public boolean isMX() {
    return true;
  }
  public int    getJS() {
    return 1;
  }
  public int    getStatus() {
    return 0;
  }
  public boolean isUse() {
    return true;
  }
  public IStubObject getParent() {
    return Parent;
  }
  public void setParent(IStubObject iso) {
    Parent = iso;
  }
  public void setCaption(String cap) {
    Caption = cap;
  }
  public String toString() {
    if ( Caption != null ) return Caption;
    return getBH()+"-"+getMC();
  }
  public List getChilds() {
    return ChildList;
  }
  void CreateList() {
    if ( ChildList == null ) ChildList = new Vector();
  }
  public void addChild(IStubObject iso) {
    CreateList();
    ChildList.add(iso);
  }
  public void delChild(IStubObject iso) {
    if ( ChildList != null )
      ChildList.remove(iso);
  }

  public IStubObject findChild(String id) {
    IStubObject iso = null;List list = null;
    list = getChilds();String BH;
    if ( list == null ) return null;
    for(int i=0;i<list.size();i++) {
      iso = (IStubObject)list.get(i);
      BH  = iso.getBH();
      if ( BH.equals(id) ) return iso;
    }
    return null;
  }

  public String getString(String FieldName) {
    return null;
  }
  public String getString(String FieldName,String def){
    return null;
  }

  public void   setString(String FieldName,String v){  }

  public Integer getInt(String FieldName){
    return null;
  }

  public Integer getInt(String FieldName,int def){
    return null;
  }

  public void    setInt(String FieldName,int v) { }

  public Double getDouble(String FieldName){
    return null;
  }

  public Double getDouble(String FieldName,double def){
    return null;
  }

  public void   setDouble(String FieldName,double v){  }

  public Boolean    getBoolean(String FieldName){
    return null;
  }

  public Boolean    getBoolean(String FieldName,boolean def){
    return null;
  }

  public void       setBoolean(String FieldName,boolean v) { }

  public Date    getDate(String FieldName){
    return null;
  }

  public Date    getDate(String FieldName,Date def)
  {
    return null;
  }

  public void    setDate(String FieldName,Date v){  }

  public Object getObject(Object FieldName){
    return null;
  }

  public Object getObject(Object FieldName,Object def){
    return null;
  }

  public void   setObject(Object FieldName,Object v) {  }

}
