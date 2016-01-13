package com.efounder.sql;

import com.efounder.plugin.*;
import com.core.xml.*;

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
public class SQLManager {
  /**
   *
   */
  public static SQLManager sqlManager = null;
  /**
   *
   */
  protected SQLManager() {
  }
  /**
   *
   * @return SQLManager
   */
  public static SQLManager getDefault() {
    if ( sqlManager == null ) {
      sqlManager = new SQLManager();
      sqlManager.initConvertSQLPlugins();
      sqlManager.initDialectSQLPlugins();
    }
    return sqlManager;
  }
  /**
   *
   */
  protected static java.util.List ConvertSQLPlugins = null;
  /**
   *
   */
  protected static java.util.List DialectSQLPlugins = null;
  /**
   *
   */
  protected void initConvertSQLPlugins() {
    ConvertSQLPlugins = PluginManager.loadPlugins("ConvertSQLPlugins",false);
  }
  /**
   *
   */
  protected void initDialectSQLPlugins() {
    DialectSQLPlugins = PluginManager.loadPlugins("DialectSQLPlugins",false);
  }
  /**
   *
   * @param sql String
   * @return String
   */
  public String convertSQL(String sql) {
    return convertSQL(sql,null);
  }
  /**
   *
   * @param sql String
   * @param context Object
   * @return String
   */
  public String convertSQL(String sql,Object context) {
    if ( ConvertSQLPlugins == null || ConvertSQLPlugins.size() == 0 ) return sql;
    SQLPlugin sqlPlugin = null;StubObject stub = null;
    for(int i=0;i<ConvertSQLPlugins.size();i++) {
      stub = (StubObject)ConvertSQLPlugins.get(i);
      sqlPlugin = (SQLPlugin)PluginManager.createPluginObject(stub);
      if ( sqlPlugin != null ) {
        sql = sqlPlugin.convertSQL(sql,context);
      }
    }
    return sql;
  }
  /**
   *
   * @param sql String
   * @return String
   */
  public String dialectSQL(String sql) {
    return dialectSQL(sql,null);
  }
  /**
   *
   * @param sql String
   * @param context Object
   * @return String
   */
  public String dialectSQL(String sql,Object context) {
    if ( DialectSQLPlugins == null || DialectSQLPlugins.size() == 0 ) return sql;
    SQLPlugin sqlPlugin = null;StubObject stub = null;
    for(int i=0;i<DialectSQLPlugins.size();i++) {
      stub = (StubObject)DialectSQLPlugins.get(i);
      sqlPlugin = (SQLPlugin)PluginManager.createPluginObject(stub);
      if ( sqlPlugin != null ) {
        sql = sqlPlugin.dialectSQL(sql,context);
      }
    }
    return sql;
  }
}
