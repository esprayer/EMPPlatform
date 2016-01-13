package com.efounder.sql;

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
public interface SQLPlugin {
  /**
   *
   * @param sqlSource String
   * @param context Object
   * @return String
   */
  public String convertSQL(String sqlSource,Object context);
  /**
   *
   * @param sqlSource String
   * @param context Object
   * @return String
   */
  public String dialectSQL(String sqlSource,Object context);
}
