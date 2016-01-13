package jframework.CntTier.GUI.Table;

//import jframework.BofJfc.JXML.*;
import jtoolkit.xml.classes.*;
import org.jdom.*;
import java.util.List;
import java.util.Hashtable;
import jtoolkit.xml.classes.JXML4JObject;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Pansoft Ltd.</p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JIDtoName {
    static  Hashtable TypeList   = new Hashtable();
    private Hashtable AttribList = new Hashtable();
    public String ID_Type = null;
    public String ID;
    public String DisplayName1; // 全称
    public String DisplayName2; // 简称
    public String AliasName;    // 别名
    private String AllName; //名称的全称(仅限分级的字典信息)
    public String JS;
    public String MX;
    public String SYZT;			// 使用权限
    public String LIMIT;		// 使用权限

  public Hashtable getAttribList() {
    return AttribList;
  } //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public JIDtoName(String type) {
      if ( type != null ) {
        ID_Type = type;
//        getIDList(ID_Type,true);
      }
    }
    static Hashtable getIDList(String type,boolean create) {
      Hashtable IDList = (Hashtable)TypeList.get(type);
      if ( IDList == null && create ) {
        IDList = new Hashtable();
        TypeList.put(type, IDList);
      }
      return IDList;
    }
    static public void putIDtoName(String type,String id,JIDtoName IDtoName) {
      if ( id == null || "".equals(id.trim()) ) return;
      if ( IDtoName == null ) return;
      Hashtable IDList = getIDList(type,true);
      IDList.put(id,IDtoName);
    }
    static public JIDtoName getIDtoName(String type,String id) {
      Hashtable IDList = getIDList(type,false);
      if ( IDList == null) return null;
      return (JIDtoName)IDList.get(id);
    }
    public JIDtoName() {

    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public String GetKey() {
        return ID;
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void InitToObject(JIDtoName idtoname) {
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void InitXMLToObject(String XMLString) {
      JXML4JObject XMLObject;
      java.util.List nodelist;
      Element element;
      int Index = 0;
      if ( XMLString.startsWith("<?xml") ) {
        XMLObject = new JXML4JObject(XMLString);
        nodelist = XMLObject.BeginEnumerate(null);
        element = (Element) XMLObject.Enumerate(nodelist, 0);
        if (element != null) {
          ID = XMLObject.GetElementValue(element, "ID");
          DisplayName1 = XMLObject.GetElementValue(element, "NAME");
          DisplayName2 = XMLObject.GetElementValue(element, "NAME2");
          if(DisplayName2 == null){
            DisplayName2 = ID;
          }
          JS = XMLObject.GetElementValue(element, "JS");
          MX = XMLObject.GetElementValue(element, "MX");
          SYZT = XMLObject.GetElementValue(element, "SYZT");
          LIMIT = XMLObject.GetElementValue(element, "LIMIT");
          AllName = XMLObject.GetElementValue(element, "ALLNAME","");
          if(AllName == null)
            AllName = "";
        }
        XMLObject.EndEnumerate();
        // 获取某个Element中的所有属性
        JXML4JObject.getAttributes(element,AttribList);
      } else {
        ID = XMLString;
        DisplayName1 = ID;
        JS = "1";
        MX = "1";
        SYZT = "1";
        LIMIT = "1";
        AllName = "";
      }
    }
    public Object getValue(String Key) {
      return this.AttribList.get(Key);
    }
    public String getHSKey() {
      Object res = getValue("HSKEY");
      return (String)res;
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void SetKey(String key) {
        ID = key;
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public String toAliasName() {
        return ID;
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public String toDisplayName1() {
        return DisplayName1;
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public String toDisplayName2() {
        return DisplayName2;
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public String toString() {
        return ID;
    }
    /**
     * 显示各级的名称
     */
    public String toDisplayAllName(){
      return AllName;
    }
  }
