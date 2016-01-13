package com.core.xml;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import org.jdom.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StubObject implements java.io.Serializable {

  protected transient java.util.Map  lostStubTable = null;

  public Object getLostValue(Object key,Object def) {
    if ( lostStubTable == null ) return def;
    Object v = lostStubTable.get(key);
    if ( v == null ) v = def;
    return v;
  }
  public void setLostValue(Object key,Object value) {
    if ( lostStubTable == null ) lostStubTable = new java.util.HashMap();
    lostStubTable.put(key,value);
  }

  static final long serialVersionUID = 1L;
  protected transient int level = 1;
  protected transient Icon compIcon = null;
  public Icon getCompIcon() {
    return compIcon;
  }
  public void setCompIcon(Icon compIcon) {
    this.compIcon = compIcon;
  }
  protected transient Icon icon = null;
  protected transient Color frontColor = null;
  protected transient Color backColor  = null;

//  protected boolean selected = true;
//  protected String Name    = null;
//  protected Object ID      = null;
//  protected String stubID  = null;
//  protected String Caption = null;
  protected java.util.List childList = null;
  public java.util.List getChildList() {
	  return childList;
  }

protected StubObject parentStub = null;
  protected Hashtable  StubTable = null;
//  private String nodeText = null;
//  private String nodeCData = null;

  /**
   *
   * @param list List
   */
  public void setChildList(java.util.List list) {
    childList = list;
  }
  /**
   *
   * @return List
   */
  public java.util.List removeChildList() {
    java.util.List list = childList;
    childList = null;
    return list;
  }
  public void addChild(StubObject SO) {
    if ( childList == null ) childList = new ArrayList();
    childList.add(SO);
  }
  public void removeChild(StubObject SO) {
    if ( childList != null ) {
      childList.remove(SO);
    }
  }
  public void setName(String name) {
    this.setObject("name",name);
  }
  public String getName() {
    return this.getString("name",null);
  }
  public boolean hasChild() {
    if ( childList == null || childList.size() == 0 ) return false;
    return true;
  }
  public StubObject[] getChilds() {
    if ( childList == null ) return null;
    StubObject SOS[] = new StubObject[childList.size()];
    return (StubObject[])childList.toArray(SOS);
  }
  public void setParent(StubObject SO) {
    parentStub = SO;
  }
  public StubObject getParent() {
    return parentStub;
  }
  public void setID(Object ID) {
    setObject("id",ID);
  }

  public Object getID() {
    return getObject("id",null);
  }

  public void setCaption(String Caption) {
    setObject("caption",Caption);
  }

  public String getCaption() {
    return this.getString("caption",null);
  }

  public void setStubTable(Hashtable StubTable) {
    this.StubTable = StubTable;
  }

  public Hashtable getStubTable() {
    return StubTable;
  }

  public StubObject() {
    this(null);
  }

  public StubObject(String id) {
    this.setID(id);
  }
  public String toString() {
    return getCaption();
  }
  public void removeAll() {
    if ( StubTable != null ) {
      StubTable.clear();
    }
  }
  public Object getObject(Object Key,Object Default) {
    Object res = Default;
    if ( StubTable != null ) {
      Default = StubTable.get(Key);
      if ( Default != null ) res = Default;
    }
    return res;
  }
  public void setObject(Object Key,Object Value) {
    if ( Value != null ) {
      if ( StubTable == null ) StubTable = new Hashtable();
      StubTable.put(Key,Value);
    } else {
      if ( StubTable != null ) {
        StubTable.remove(Key);
      }
    }
  }
  public String getString(Object Key,String def) {
    String res;
    res = (String)getObject(Key,def);
    if ( res == null ) res = def;
    return res;
  }
  public void setString(Object key,String v) {
    this.setObject(key,v);
  }
  public int getInt(Object Key,int def) {
    int res = def;Object re = null;
    try {
      re = getObject(Key,String.valueOf(def));
      res = Integer.parseInt(re.toString());
    } catch ( Exception e ) {
      res = def;
    }
    return res;
  }
  public void setInt(Object key,int v) {
    this.setObject(key,new Integer(v));
  }
  public boolean getBoolean(Object Key,boolean def) {
    boolean res = def;Object b;
    b = getObject(Key,new Boolean(def));
    if ( b instanceof Boolean )
      res = ((Boolean)b).booleanValue();
    return res;
  }
  public void setBoolean(Object key,boolean v) {
    this.setObject(key,new Boolean(v));
  }
  public void setObjectFromXMLElemnt(Element e) {
    if ( e == null ) return;
    this.setStubID(e.getName());
    this.setNodeText(e.getText());
    java.util.List list = e.getAttributes();
    if ( list == null ) return;
    String Name,Value;Attribute attr;
    for(int i=0;i<list.size();i++) {
      attr = (Attribute)list.get(i);
      if ( attr != null ) {
        Name = attr.getName();Value = attr.getValue();
        if ( Name != null && Value != null ) {
          setObject(Name,Value);
        }
      }
    }
  }
  public static StubObject convertXML2Stub(JDOMXMLBaseObject XML) {
    return convertXML2Stub(XML,StubObject.class);
  }
  private static StubObject getStubObject(Class StubClass) {
    StubObject SO = null;
    try {
      SO = (StubObject)StubClass.newInstance();
    } catch ( Exception e ) {

    }
    return SO;
  }
  public static StubObject convertXML2Stub(JDOMXMLBaseObject XML,Class StubClass) {
    StubObject SO = convertXML2Stub(XML,XML.Root,StubClass);
    return SO;
  }
  public static StubObject convertXML2Stub(JDOMXMLBaseObject XML,Element e,Class StubClass) {
    StubObject SO = getStubObject(StubClass);
    createXML2Stub(XML,e,SO);
    return SO;
  }

  public static void createXML2Stub(JDOMXMLBaseObject XML,Element e,StubObject SO) {
    java.util.List nodelist = XML.BeginEnumerate(e);int Index = 0;Element node;
    StubObject childSO = null;
    // ����SO������
    SO.setObjectFromXMLElemnt(e);
    while ( nodelist != null ) {
      node = XML.Enumerate(nodelist,Index++);
      if ( node == null ) break;
      childSO = getStubObject(SO.getClass());
      childSO.setParent(SO); SO.addChild(childSO);
      createXML2Stub(XML,node,childSO);
    }
    XML.EndEnumerate();
  }
  public boolean isSelected() {
    return this.getBoolean("selected",false);
  }
  public boolean isSelected(boolean def) {
    return this.getBoolean("selected",def);
  }
  public String getStubID() {
    return this.getString("stubID",null);
  }

  public Icon getIcon() {
    return icon;
  }

  public int getLevel() {
    return level;
  }

  public Color getBackColor() {
    return backColor;
  }

  public Color getFrontColor() {
    return frontColor;
  }

  public String getNodeText() {
    return this.getString("_nodeText_",null);
  }

  public String getNodeCData() {
    return this.getString("_nodeCData_",null);
  }

  public void setSelected(boolean b) {
    this.setBoolean("selected",b);
  }

  public void setStubID(String stubID) {
    this.setString("stubID",stubID);
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setBackColor(Color backColor) {
    this.backColor = backColor;
  }

  public void setFrontColor(Color frontColor) {
    this.frontColor = frontColor;
  }

  public void setNodeText(String nodeText) {
    this.setString("_nodeText_",nodeText);
  }

  public void setNodeCData(String nodeCData) {
    this.setString("_nodeCData_",nodeCData);
  }

  /**
   *
   * @param key Object
   * @param def long
   * @return long
   */
  public long getLong(Object key,long def) {
    long res = def;Object re = null;
    try {
      java.lang.Number  num = (java.lang.Number)getObject(key,new Long(def));
      res = num.longValue();
    } catch ( Exception e ) {
      res = def;
    }
    return res;
  }
  public void setLong(Object key,long v) {
    this.setObject(key,new Long(v));
  }
  /**
   *
   * @param key Object
   * @param def float
   * @return float
   */
  public float getFloat(Object key,float def) {
    float res = def;Object re = null;
    try {
      java.lang.Number  num = (java.lang.Number)getObject(key,new Float(def));
      res = num.floatValue();
    } catch ( Exception e ) {
      res = def;
    }
    return res;
  }
  public void setFloat(Object key,float v) {
    setObject(key,new Float(v));
  }
  /**
   *
   * @param key Object
   * @param def double
   * @return double
   */
  public double getDouble(Object key,double def) {
    double res = def;Object re = null;
    try {
      java.lang.Number  num = (java.lang.Number)getObject(key,new Double(def));
      res = num.doubleValue();
    } catch ( Exception e ) {
      res = def;
    }
    return res;
  }
  public void setDouble(Object key,double v) {
    setObject(key,new Double(v));
  }
  /**
   *
   * @param key Object
   * @param def byte
   * @return byte
   */
  public byte getByte(Object key,byte def) {
    byte res = def;Object re = null;
    try {
      java.lang.Number  num = (java.lang.Number)getObject(key,new Byte(def));
      res = num.byteValue();
    } catch ( Exception e ) {
      res = def;
    }
    return res;
  }
  public void setByte(Object key,byte v) {
    setObject(key,new Byte(v));
  }
  /**
   *
   * @param key Object
   * @param def short
   * @return short
   */
  public short getShort(Object key,short def) {
    short res = def;Object re = null;
    try {
      java.lang.Number  num = (java.lang.Number)getObject(key,new Short(def));
      res = num.shortValue();
    } catch ( Exception e ) {
      res = def;
    }
    return res;
  }
  /**
   *
   * @param key Object
   * @param v short
   */
  public void setShort(Object key,short v) {
    this.setObject(key,new Short(v));
  }
  /**
   *
   * @return Class
   */
  public Class getPluginClass() {
    return (Class)getObject("pluginClass",null);
  }
  /**
   *
   * @param pluginClass Class
   */
  public void setPluginClass(Class pluginClass) {
    setObject("pluginClass",pluginClass);
  }
  /**
   *
   * @return Object
   */
  public Object getPluginObject() {
    return getObject("pluginObject",null);
  }
  /**
   *
   * @param pluginObject Object
   */
  public void setPluginObject(Object pluginObject) {
    setObject("pluginObject",pluginObject);
  }
  /**
   *
   * @param stub StubObject
   */
  public void assignStubObject(StubObject stub) {
    if ( stub.getStubTable() == null ) return;
    if ( this.StubTable == null ) StubTable = new Hashtable();
    StubTable.putAll(stub.getStubTable());
  }
  /**
   *
   */
  protected long signature = 0x0000;
  /**
   *
   * @return long
   */
  public long getSignature() {
    return signature;
  }
  /**
   *
   * @param s long
   */
  public void setSignature(long s) {
    signature =s;
  }
}
