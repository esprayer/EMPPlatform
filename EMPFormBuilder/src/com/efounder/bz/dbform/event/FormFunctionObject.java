package com.efounder.bz.dbform.event;

import com.efounder.service.script.ScriptObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormFunctionObject
  implements ScriptObject
{
  public Map getFunctionCodeMap()
  {
    return this.functionCodeMap;
  }
  
  public void setFunctionCodeMap(Map functionCodeMap)
  {
    this.functionCodeMap = functionCodeMap;
  }
  
  protected Map functionCodeMap = new HashMap();
  
  public String getFunctionCode(String funcID)
  {
    return (String)this.functionCodeMap.get(funcID);
  }
  
  public void setFunctionCode(String funcID, String funcCode)
  {
    this.functionCodeMap.put(funcID, funcCode);
  }
  
  public Object getObject(Object key, Object def)
  {
    return null;
  }
  
  public void setObject(Object key, Object value) {}
  
  public Object getValue(Object key, Object def)
  {
    return null;
  }
  
  public void setValue(Object key, Object value) {}
  
  public String getScriptKey()
  {
    return null;
  }
  
  public void setScriptKey(String value) {}
  
  public String getScriptInstance()
  {
    return null;
  }
  
  public void setScriptInstance(String value) {}
  
  public List getFunctionList()
  {
    return null;
  }
  
  public void setFunctionList(List list) {}
  
  public List getEventList()
  {
    return null;
  }
  
  public void setEventList(List list) {}
  
  public List getScriptNameList(String scriptType)
  {
    return null;
  }
  
  public void setScriptNameList(String scriptType, List scriptNameList) {}
  
  public String getScript(String scriptType, String scriptName)
  {
    if ("METHOD".equals(scriptType)) {
      return getFunction(scriptName);
    }
    return null;
  }
  
  public String getFunction(String scriptName)
  {
    return getFunctionCode(scriptName);
  }
  
  public String getEvent(String scriptName)
  {
    return null;
  }
  
  public void setScript(String scriptType, String scriptName, String script)
  {
    if ("METHOD".equals(scriptType)) {
      setFunctionCode(scriptName, script);
    }
  }
}
