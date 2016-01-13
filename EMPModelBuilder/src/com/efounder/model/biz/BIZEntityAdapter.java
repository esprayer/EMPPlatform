package com.efounder.model.biz;

import com.core.xml.StubObject;
import com.efounder.eai.data.JParamObject;
import com.efounder.pub.util.StringFunction;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BIZEntityAdapter
  extends StubObject
  implements BIZEntity, EntityConstant
{
  static final long serialVersionUID = 100L;
  protected String entityKey = null;
  
  public BIZEntityAdapter() {}
  
  public BIZEntityAdapter(String entityKey, String entityID, String entityBIZID, String entityName, String entityIcon, String entityLargeIcon, String entityFuncID, String entityLimit, BIZContext bizContext)
  {
    this.bizContext = bizContext;
    this.entityKey = entityKey;
    setString("entityKey", entityKey);
    setString(entityKey + "_" + "EntityID", entityID);
    setString(entityKey + "_" + "EntityBIZID", entityBIZID);
    setString(entityKey + "_" + "EntityName", entityName);
    setString(entityKey + "_" + "EntityIcon", entityIcon);
    setString(entityKey + "_" + "EntityLargeIcon", entityLargeIcon);
    setString(entityKey + "_" + "EntityFuncID", entityFuncID);
    setString(entityKey + "_" + "EntityLimit", entityLimit);
  }
  
  protected transient BIZContext bizContext = null;
  
  protected void processContext(Object context) {}
  
  protected void prepareAttrib(Object attrib) {}
  
  public void setBIZContext(BIZContext bizContext)
  {
    this.bizContext = bizContext;
  }
  
  public BIZContext getBIZContext()
  {
    return this.bizContext;
  }
  
  public void setEntityKey(String key)
  {
    this.entityKey = key;
    setString("entityKey", this.entityKey);
  }
  
  public String getEntityKey()
  {
    return getString("entityKey", null);
  }
  
  public String getEntityID()
  {
    return getString(this.entityKey + "_" + "EntityID", null);
  }
  
  public String getEntityBIZID()
  {
    return getString(this.entityKey + "_" + "EntityBIZID", null);
  }
  
  public String getEntityName()
  {
    return getString(this.entityKey + "_" + "EntityName", null);
  }
  
  public String getEntityIcon()
  {
    return getString(this.entityKey + "_" + "EntityIcon", null);
  }
  
  public String getEntityLargeIcon()
  {
    return getString(this.entityKey + "_" + "EntityLargeIcon", null);
  }
  
  public String getEntityFuncID()
  {
    return getString(this.entityKey + "_" + "EntityFuncID", null);
  }
  
  public String getEntityLimit()
  {
    return getString(this.entityKey + "_" + "EntityLimit", null);
  }
  
  public String getBIZContextString()
  {
    return getString(this.entityKey + "_" + "bizContext", null);
  }
  
  public String getPrepareAttString()
  {
    return getString(this.entityKey + "_" + "prepareAtt", null);
  }
  
  public Object getEntityObject(String key, Object def)
  {
    return getObject(this.entityKey + "_" + key, def);
  }
  
  public void setEntityObject(String key, Object value)
  {
    setObject(this.entityKey + "_" + key, value);
  }
  
  public String getEntityUnit()
  {
    String value = (String)getValue("BIZUnit", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getBIZUnit();
  }
  
  public String getEntityType()
  {
    String value = (String)getValue("BIZType", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getBIZType();
  }
  
  public String getDateType()
  {
    String value = (String)getValue("DataType", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getDateType();
  }
  
  public String getEntitySDate()
  {
    String value = (String)getValue("BIZSDate", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getBIZSDate();
  }
  
  public String getEntityEDate()
  {
    String value = (String)getValue("BIZEDate", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getBIZEDate();
  }
  
  public Object getEntityValue(Object object, Object object1)
  {
    return this.bizContext.getBIZValue(object, object1);
  }
  
  public void setEntityValue(Object key, Object value)
  {
    this.bizContext.setBIZValue(key, value);
    setObject(key, value);
  }
  
  protected transient BIZEntityAppView bizEntityAppView = null;
  
  public BIZEntityAppView getBIZEntityAppView()
  {
    return this.bizEntityAppView;
  }
  
  public void setBIZEntityAppView(BIZEntityAppView bizAppView)
  {
    this.bizEntityAppView = bizAppView;
  }
  
  public int getFuncType()
  {
    return 0;
  }
  
  public String getBIZUnit()
  {
    String value = (String)getValue("BIZUnit", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getBIZUnit();
  }
  
  public String getBIZType()
  {
    String value = (String)getValue("BIZType", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getBIZType();
  }
  
  public String getBIZSDate()
  {
    String value = (String)getValue("BIZSDate", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getBIZSDate();
  }
  
  public String getBIZEDate()
  {
    String value = (String)getValue("BIZEDate", null);
    if ((value != null) && (!"".equals(value.trim()))) {
      return value;
    }
    return this.bizContext.getBIZEDate();
  }
  
  public Object getBIZValue(Object key, Object def)
  {
    Map callMap = getCallBackMap();
    Object reso = null;
    if (callMap != null)
    {
      reso = callMap.get(key);
      if (reso != null) {
        return reso;
      }
    }
    return this.bizContext.getBIZValue(key, def);
  }
  
  public void setBIZValue(Object key, Object value)
  {
    this.bizContext.setBIZValue(key, value);
  }
  
  public void callBack(Object callObject, Object context)
  {
    this.bizContext.callBack(callObject, context);
    processCBCP((JParamObject)callObject);
  }
  
  protected JParamObject processCBCP(JParamObject paramObject)
  {
    processFixValue(paramObject);
    
    setCallBackMap(paramObject);
    
    processParamValue(paramObject);
    

    return paramObject;
  }
  
  protected void po2vm(JParamObject paramObject)
  {
    this.valueMap = new HashMap();
    Map pm = paramObject.getParamRoot();
    if (pm != null) {
      this.valueMap.putAll(pm);
    }
  }
  
  protected Object getValue(Object key, Object def)
  {
    if (this.callbackMap == null) {
      return def;
    }
    Object res = def;
    res = this.callbackMap.get(key);
    if (res == null) {
      res = def;
    }
    return res;
  }
  
  protected transient Map valueMap = null;
  
  public JParamObject convertParamObject(Object userObject, JParamObject po)
  {
    po = this.bizContext.convertParamObject(userObject, po);
    processCBCP(po);
    return po;
  }
  
  protected void processParamValue(Object callObject)
  {
    JParamObject paramObject = (JParamObject)callObject;
    Map attribMap = paramObject.getAttriMap();
    if (attribMap == null) {
      return;
    }
    Object[] keys = attribMap.keySet().toArray();Object value = null;String svalue = null;
    for (int i = 0; i < keys.length; i++)
    {
      value = attribMap.get(keys[i]);
      if (((keys[i] instanceof String)) && ((value instanceof String))) {
        processParamValue(paramObject, keys[i], (String)value);
      }
    }
  }
  
  public static void processParamValue(JParamObject PO, Object key, String value)
  {
    if ((!value.startsWith("@")) || (!value.endsWith("@"))) {
      return;
    }
    value = value.substring(1, value.length() - 1);
    Object v = PO.getBIZValue(value, value);
    if ((v instanceof String)) {
      PO.setBIZValue(key, v);
    }
  }
  
  public static void processFixValue(Object callObject)
  {
    JParamObject paramObject = (JParamObject)callObject;
  }
  
  protected void processFixValue(JParamObject paramObject)
  {
    paramObject.setBIZValue("STAT_DWZD", getBIZUnit());
  }
  
  protected void callEntityBack()
  {
    String bizContextString = getBIZContextString();
    if ((bizContextString == null) || (bizContextString.length() == 0)) {
      return;
    }
    callBackBIZContext(bizContextString);
  }
  
  protected void callBackBIZContext(String bizString)
  {
    if ((bizString == null) || (bizString.trim().length() == 0)) {
      return;
    }
    String[] bizStrings = StringFunction.convertFromStringToStringArrayBySymbolNO(bizString, ";");
    if ((bizStrings == null) || (bizStrings.length == 0)) {
      return;
    }
    for (int i = 0; i < bizStrings.length; i++)
    {
      String tmp = bizStrings[i];
      int index = tmp.indexOf("=");
      if (index > 0)
      {
        String name = tmp.substring(0, index);
        String value = tmp.substring(index + 1);
        this.callbackMap.put(name, value);
      }
    }
  }
  
  protected void setCallBackMap(Object callObject)
  {
    if (callObject == null) {
      return;
    }
    Map callMap = getCallBackMap();
    if (callMap == null) {
      return;
    }
    JParamObject paramObject = (JParamObject)callObject;
    Object[] keys = callMap.keySet().toArray();
    for (int i = 0; i < keys.length; i++) {
      if (((keys[i] instanceof String)) && ((callMap.get(keys[i]) instanceof String))) {
        paramObject.setBIZValue(keys[i], callMap.get(keys[i]));
      }
    }
  }
  
  protected Map callbackMap = null;
  
  public void setCallBackValue(Object key, Object value)
  {
    getCallBackMap();
    if (this.callbackMap == null) {
      this.callbackMap = new HashMap();
    }
    this.callbackMap.put(key, value);
  }
  
  public Map getCallBackMap()
  {
    if (this.callbackMap == null)
    {
      this.callbackMap = new HashMap();
      callEntityBack();
    }
    return this.callbackMap;
  }
  
  public void enumBIZKey(List keyList)
  {
    this.bizContext.enumBIZKey(keyList);
  }
  
  public void initBIZContext(Object sourceObject, Object contextObject, Object addinObject)
  {
    this.bizContext.initBIZContext(sourceObject, contextObject, addinObject);
  }
  
  public void changeEvent(int eventType, Object sourceObject, Object contextObject, Object addinObject)
  {
    this.bizContext.changeEvent(eventType, sourceObject, contextObject, addinObject);
  }
  
  protected transient JParamObject paramObject = null;
  
  public void addBIZContext(BIZContext bizContext) {}
  
  public JParamObject getParamobject()
  {
    return this.paramObject;
  }
  
  public void setParamObject(JParamObject paramObject)
  {
    this.paramObject = paramObject;
  }
  
  protected transient Map lostValueList = null;
  protected transient BIZEntityContext bizEntityContext;
  
  public Object getLostValue(Object key, Object def)
  {
    if (this.lostValueList == null) {
      return def;
    }
    Object res = this.lostValueList.get(key);
    if (res == null) {
      res = def;
    }
    return res;
  }
  
  public void setLostValue(Object key, Object value)
  {
    if (this.lostValueList == null) {
      this.lostValueList = new HashMap();
    }
    if (value != null) {
      this.lostValueList.put(key, value);
    } else {
      this.lostValueList.remove(key);
    }
  }
  
  public void setBIZEntityContext(BIZEntityContext bizEntityContext)
  {
    this.bizEntityContext = bizEntityContext;
  }
  
  public BIZEntityContext getBIZEntityContext()
  {
    return this.bizEntityContext;
  }
  
  public Object cloneObject(BIZEntityAdapter bizEntity)
    throws Exception
  {
    BIZEntityAdapter bizEntityClone = (BIZEntityAdapter)bizEntity.getClass().newInstance();
    bizEntityClone.bizContext = bizEntity.bizContext;
    bizEntityClone.bizEntityContext = bizEntity.bizEntityContext;
    bizEntityClone.paramObject = bizEntity.paramObject;
    bizEntityClone.entityKey = bizEntity.entityKey;
    if (bizEntity.lostValueList != null)
    {
      bizEntityClone.lostValueList = new HashMap();
      bizEntityClone.lostValueList.putAll(bizEntity.lostValueList);
    }
    if (bizEntity.StubTable != null)
    {
      bizEntityClone.StubTable = new Hashtable();
      bizEntityClone.StubTable.putAll(bizEntity.StubTable);
    }
    if (bizEntity.callbackMap != null)
    {
      bizEntityClone.callbackMap = new HashMap();
      bizEntityClone.callbackMap.putAll(bizEntity.callbackMap);
    }
    return bizEntityClone;
  }
  
  public Object getPrepareObject()
  {
    return null;
  }
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZEntityAdapter
 * JD-Core Version:    0.7.0.1
 */