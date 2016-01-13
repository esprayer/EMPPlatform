package com.efounder.dbc;

import java.io.*;

import com.borland.dx.sql.dataset.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RequestDataSetData implements Serializable {
  protected ConnectionDescriptor connectSet = null;
  protected Object extentObject = null;// JParamObject
  protected String extendServerAgent = null;
  protected String agentCommand = null;
  protected Object agentObject  = null;
  public void setAgentObject(Object agentObject) {
    this.agentObject = agentObject;
  }

  public void setConnectSet(ConnectionDescriptor connectSet) {
    this.connectSet = connectSet;
  }

  public void setExtentObject(Object extentObject) {
    this.extentObject = extentObject;
  }

  public void setAgentCommand(String agentCommand) {
    this.agentCommand = agentCommand;
  }

  public void setExtendServerAgent(String extendServerAgent) {
    this.extendServerAgent = extendServerAgent;
  }

  public String getAgentCommand() {
    return agentCommand;
  }

  public Object getAgentObject() {
    return agentObject;
  }

  public Object getExtentObject() {
    return extentObject;
  }


  public String getExtendServerAgent() {
    return extendServerAgent;
  }

  public ConnectionDescriptor getConnectSet() {
    return connectSet;
  }

  public RequestDataSetData(AgentDatabase agentDatabase,String command,Object ao) {
    agentCommand = command;
    agentObject  = ao;
    extentObject = agentDatabase.getExtendAgentObject();
    connectSet  = agentDatabase.getConnectSet();
    extendServerAgent = agentDatabase.getExtendServerAgent();
  }

}
