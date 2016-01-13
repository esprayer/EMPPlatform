package com.efounder.service.meta;

import com.core.xml.*;
import com.efounder.eai.data.*;
import com.efounder.sql.*;
import java.sql.*;
import com.efounder.eai.*;
import org.openide.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class MetaContextObject extends StubObject {
  /**
   *
   */
  protected MetaContextObject() {
  }
  /**
   *
   * @return MetaContextObject
   */
  public static MetaContextObject getInstance(JParamObject PO,JConnection conn,Object userObject,String metaKey) {
    MetaContextObject mco = (MetaContextObject) Lookup.getDefault().lookup(MetaContextObject.class,EAI.Tier.toLowerCase()+"_"+metaKey);
    mco.PO = PO;mco.conn = conn;mco.userObject = userObject;
    mco.initContext();
    return mco;
  }
  protected abstract void initContext();
  /**
   *
   */
  protected JParamObject PO   = null;
  /**
   *
   */
  protected JConnection  conn = null;
  protected Object userObject = null;
  /**
   *
   */
  protected Statement    stmt = null;
  /**
   *
   */
  protected boolean StmtSeries = false;
  /**
   *
   * @return boolean
   */
  public boolean isStmtSeries() {
    return StmtSeries;
  }
  /**
   *
   * @param v boolean
   */
  public void setStmtSeries(boolean v) {
    StmtSeries = v;
  }
  /**
   *
   * @return JParamObject
   */
  public JParamObject getParamObject() {
    return PO;
  }
  /**
   *
   * @return JConnection
   */
  public JConnection getConnection() {
    return conn;
  }
  /**
   *
   * @return Statement
   */
  public Statement getStatement() {
    if ( stmt == null ) {
      stmt = conn.GetStatement(conn);
    }
    return stmt;
  }
  protected int styleCount = 0;
  /**
   *
   * @param rs ResultSet
   */
  public void backStatement(ResultSet rs) {
//    if ( stmt != null && !isStmtSeries() ) {
    conn.BackStatement(null,rs);
//      stmt = null;
//    }
  }
  public void startStyle() {
    styleCount++;
  }
  public void stopStyle() {
    styleCount--;
    if ( stmt != null && styleCount == 0 ) {
      conn.BackStatement(stmt,null);
      stmt = null;
    }
  }
  /**
   *
   */
  public void backStatement() {
    backStatement(null);
  }
}
