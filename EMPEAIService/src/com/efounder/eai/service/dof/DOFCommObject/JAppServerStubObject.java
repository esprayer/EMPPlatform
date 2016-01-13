package com.efounder.eai.service.dof.DOFCommObject;

import com.core.xml.*;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JAppServerStubObject extends JStubObject implements Serializable{
  public String Server = null;
  public String Port   = null;
  public String SSLServer = null;
  public String SSLPort = null;
  public String Path    = null;
  public String Servlet = null;
  public boolean isSec = false;
  public boolean isCheck = true;
  public String Product = null;
  public String ServerType = null;
  public String SecurityFile = null;
  public String SecurityPass = null;
  public String CommBase = "";
  private Map extMap = null;
  public JAppServerStubObject() {
  }
  public void putValue(String key,Object value){
    if(extMap == null) extMap = new HashMap();
    extMap.put(key,value);
  }
  public Object getValue(String key){
    if(extMap == null) return null;
    return extMap.get(key);
  }
   public Object getValue(String key,Object defaultValue){
     Object obj = getValue(key);
     if(obj == null) obj = defaultValue;
     return obj;
   }
  public void setExtMap(Map extMap){
    this.extMap = extMap;
  }
  public Map getExtMap(){
    return this.extMap;
  }


}
