package com.db;

import java.sql.*;

import com.efounder.db.*;
import com.efounder.dbc.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAIDBServerAgent extends DBServerAgent implements ExtendServerAgent {
  /**
   *
   */
  public EAIDBServerAgent() {
  }
  /**
   * getExtendServerAgent
   *
   * @param rdsd RequestDataSetData
   * @return Connection
   */
  public Connection getExtendServerAgent(RequestDataSetData rdsd) throws Exception {
    // 取出PO
    JParamObject PO = (JParamObject)rdsd.getExtentObject();
    // 获取数据库连接
    JConnection jconn = (JConnection)EAI.DAL.IOM("DBManagerObject","GetDBConnection", PO);
    // 将JDBC的连接获取后，传递过去
    return jconn.getConnection();
  }

}
