package com.efounder.db;

import com.efounder.dbc.ExtendServerAgent;
import java.sql.Connection;
import com.efounder.dbc.RequestDataSetData;
import org.openide.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DBServerAgent implements ExtendServerAgent {
  public static DBServerAgent defaultDBServerAgent = null;
  /**
   *
   */
  public DBServerAgent() {
  }
  /**
   *
   * @param rdsd RequestDataSetData
   * @throws Exception
   * @return Connection
   */
  public abstract Connection getExtendServerAgent(RequestDataSetData rdsd) throws Exception;
  /**
   *
   * @return DBServerAgent
   */
  public static DBServerAgent getDefault() {
    if ( defaultDBServerAgent == null ) {
      defaultDBServerAgent = (DBServerAgent) Lookup.getDefault().lookup(com.efounder.db.DBServerAgent.class);
    }
    return defaultDBServerAgent;
  }

}
