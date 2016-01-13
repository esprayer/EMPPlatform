package com.db;

import com.efounder.dbc.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.db.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAIDBAgent extends DBAgent implements ExtendClientAgent {
  private static AgentDatabase agentDatabase = new AgentDatabase();
//  private static EAIDBAgent dbAgent = new EAIDBAgent();
  /**
   *
   * @return AgentDatabase
   */
  public AgentDatabase getAgentDatabase() {
    agentDatabase.setExtendClientAgent(this);
    agentDatabase.setExtendServerAgent("com.db.EAIDBServerAgent");
    return agentDatabase;
  }
  /**
   *
   */
  public EAIDBAgent() {
  }
  /**
   *
   * @param agentDatabase AgentDatabase
   * @return Object
   */
  public Object getExtendObject(AgentDatabase agentDatabase) {
    if ( !EAI.getSecurity() ) {
      agentDatabase.setAgentServer(EAI.Server);
      agentDatabase.setAgentPort(EAI.Port);
    } else {
      agentDatabase.setAgentServer(EAI.Server);
      agentDatabase.setAgentPort(EAI.Port);
    }
    JParamObject PO = JParamObject.Create();
    return PO;
  }
}
