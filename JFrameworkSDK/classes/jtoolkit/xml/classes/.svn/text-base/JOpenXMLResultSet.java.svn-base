package jtoolkit.xml.classes;

import org.jdom.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JOpenXMLResultSet extends JXMLBaseObject {
  public String[] ColNameArray = null;
  public String[] ColAliasArray = null;
  public String[] ColTypeArray = null;
  Element  ResultElement = null;
  Element  RowElement    = null;
  int      RowIndex      = 0;
  public int      ResultRowCount= 0;
  List nodelist      = null;
  boolean bof = true;
  boolean eof = false;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JOpenXMLResultSet() {
    super();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JOpenXMLResultSet(String DataString) {
    super(DataString);
    // 获取ResultElement
    OpenDataString();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void OpenDataString() {
     ResultElement = GetElementByName("ResultSet");
     int Count;String AttName;
     Count = Integer.valueOf(GetElementValue(ResultElement,"ColCount")).intValue();
     ColNameArray  = new String[Count];
     ColAliasArray = new String[Count];
     ColTypeArray  = new String[Count];
     for(int Index=0;Index<Count;Index++) {
       // 列名
       AttName  = "c_"+String.valueOf(Index+1);
       ColNameArray[Index]  = GetElementValue(ResultElement,AttName);
       // 别名
       AttName  = "a_"+String.valueOf(Index+1);
       ColAliasArray[Index] = GetElementValue(ResultElement,AttName);
       // 类型
       AttName  = "t_"+String.valueOf(Index+1);
       ColTypeArray[Index]  = GetElementValue(ResultElement,AttName);
     }
     ResultRowCount = Integer.valueOf(ResultElement.getAttribute("RowCount").getValue()).intValue();
     bof = true;
     if ( ResultRowCount == 0 ) eof = true;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int GetColNameIndex(String ColName) {
    for(int i=0;i<ColNameArray.length;i++) {
      if ( ColNameArray[i].equals(ColName) ) return i;
    }
    return -1;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String GetColAlias(String ColName) {
    for(int i=0;i<ColNameArray.length;i++) {
      if ( ColNameArray[i].equals(ColName) ) return ColAliasArray[i];
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String GetColType(String ColName) {
    for(int i=0;i<ColNameArray.length;i++) {
      if ( ColNameArray[i].equals(ColName) ) return ColTypeArray[i];
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean next() {
    boolean Res = ResultElement.hasChildren();
    if ( Res == false ) return Res;
    if ( RowIndex == 0 ) {
      InitNext();
      bof = true;
    } else {
      bof = false;
    }
    RowElement = (Element)Enumerate(nodelist,RowIndex++);
    if ( RowElement == null ) {EndEnumerate();Res = false;RowElement=null;RowIndex=0;eof=true;}
    return Res;
  }
  public boolean isBof() {
    return bof;
  }
  public boolean isEof() {
    return eof;
  }
  public Element getRowElement() {
    return RowElement;
  }
  public void gotoBegin() {
    RowIndex = 0;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String getString(String ColName) {
    if ( RowElement == null ) return null;
    return RowElement.getAttribute(GetColAlias(ColName)).getValue();
  }
  public void setString(String ColName,String Value) {
    if ( RowElement == null ) return;
    RowElement.setAttribute(GetColAlias(ColName),Value);
  }
  public void setVaule(String ColName,String Value) {
    if ( RowElement == null ) return;
    RowElement.setAttribute(ColName,Value);
  }
  public String getValue(String ColName,String def) {
    if ( RowElement == null ) return def;
    String Value = RowElement.getAttribute(ColName).getValue();
    if ( Value == null ) Value = def;
    return Value;
  }
  public int getInt(String ColName) {
    String Value = getString(ColName);
    return Integer.valueOf(Value).intValue();
  }
  public double getDouble(String ColName) {
    String Value = getString(ColName);
    return Double.valueOf(Value).doubleValue();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String getString(int ColIndex) {
    if ( RowElement == null ) return null;
    return RowElement.getAttribute(ColAliasArray[ColIndex]).getValue();
  }
  public void setString(int ColIndex,String Value) {
    if ( RowElement == null ) return;
    RowElement.setAttribute(ColAliasArray[ColIndex],Value);
  }
  public int getInt(int ColIndex) {
    String Value = getString(ColIndex);
    return Integer.valueOf(Value).intValue();
  }
  public double getDouble(int ColIndex) {
    String Value = getString(ColIndex);
    return Double.valueOf(Value).doubleValue();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void InitNext() {
    nodelist = BeginEnumerate(ResultElement);
  }
}
