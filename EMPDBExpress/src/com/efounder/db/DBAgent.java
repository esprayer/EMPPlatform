package com.efounder.db;

import com.efounder.dbc.AgentDatabase;
import com.efounder.dbc.ExtendClientAgent;

import org.openide.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DBAgent implements ExtendClientAgent {
  public static DBAgent defaultDBAgent = null;
  public DBAgent() {
  }
  public abstract AgentDatabase getAgentDatabase();
  public abstract Object getExtendObject(AgentDatabase agentDatabase);
  public static DBAgent getDefault() {
    if ( defaultDBAgent == null ) {
      defaultDBAgent = (DBAgent) Lookup.getDefault().lookup(com.efounder.db.DBAgent.class);
    }
    return defaultDBAgent;
  }

}
