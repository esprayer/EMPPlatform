package com.object;

import jtoolkit.xml.classes.*;
import org.jdom.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StubObject implements java.io.Serializable{
  protected boolean selected = true;
  protected String Name    = null;
  protected Object ID      = null;
  protected String Caption = null;
  protected Vector childList = null;
  protected StubObject parentStub = null;
  protected Hashtable StubTable = null;
  public void addChild(StubObject SO) {
    if ( childList == null ) childList = new Vector();
    childList.add(SO);
  }
  public void removeChild(StubObject SO) {
    if ( childList != null ) {
      childList.remove(SO);
    }
  }
  public void setName(String name) {
    Name = name;
  }
  public String getName() {
    return Name;
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
    this.ID = ID;
  }

  public Object getID() {
    return getObject("id",ID);
  }

  public void setCaption(String Caption) {
    setObject("caption",Caption);
    this.Caption = Caption;
  }

  public String getCaption() {
    return getObject("caption",Caption).toString();
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
    ID = id;
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
    }
  }
  public String getString(Object Key,String def) {
    String res;
    res = (String)getObject(Key,def);
    if ( res == null ) res = def;
    return res;
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
  public boolean getBoolean(Object Key,boolean def) {
    boolean res = def;Object b;
    b = getObject(Key,new Boolean(def));
    if ( b instanceof Boolean )
      res = ((Boolean)b).booleanValue();
    return res;
  }
  public void setObjectFromXMLElemnt(Element e) {
    if ( e == null ) return;
    this.setName(e.getName());
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

  private static void createXML2Stub(JDOMXMLBaseObject XML,Element e,StubObject SO) {
    java.util.List nodelist = XML.BeginEnumerate(e);int Index = 0;Element node;
    StubObject childSO = null;
    // 设置SO的属性
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
    return selected;
  }
  public void setSelected(boolean b) {
    selected = b;
  }

}
