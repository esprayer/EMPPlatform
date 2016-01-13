package com.efounder.view.user;

import com.core.xml.StubObject;
import java.io.Serializable;
import java.util.Vector;

public class JMessageStubObject
  extends StubObject
  implements Serializable
{
  Vector MessageList;
  
  public void setMessageList(Vector mVector)
  {
    this.MessageList = mVector;
  }
  
  public Vector getMessageList()
  {
    return this.MessageList;
  }
}
