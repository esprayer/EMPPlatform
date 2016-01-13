package com.efounder.view.user;

import com.core.xml.StubObject;
import java.io.Serializable;
import java.util.Date;
import javax.swing.JCheckBox;

public class JSessionStubObject
  extends StubObject
  implements Serializable
{
  public JCheckBox CheckBox;
  
  public String getKey()
  {
    String sKey = "";
    sKey = getUser() + "-" + getUserName() + "-" + getHostAddress();
    return sKey;
  }
  
  public String toString()
  {
    return getUser() + "-" + getUserName() + "(" + getHostAddress() + ")";
  }
  
  public String getUser()
  {
    return (String)getObject("User", "");
  }
  
  public String getUserName()
  {
    return (String)getObject("UserName", "");
  }
  
  public String getHostAddress()
  {
    return (String)getObject("HostAddress", "");
  }
  
  public String getHostName()
  {
    return (String)getObject("HostName", "");
  }
  
  public Date getUserTime()
  {
    return (Date)getObject("UserTime", "");
  }
}
