package jenterprise.bof.classes.AppExplorerObject.MessageService;

import java.io.*;
import java.net.*;

import javax.swing.JCheckBox;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JMessageSessionStub {
  public JMessageSessionStub() {
  }
  public String HostAddress;
  public Socket socket;
  public ObjectOutputStream objOut;
  public ObjectInputStream  objIn;
  public JCheckBox    CheckBox;
}
