package com.efounder.util;

import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ResultSetUtil {
  public ResultSetUtil() {
  }
  /**
   *
   * @param rs ResultSet
   * @param col int
   * @param defaultString String
   * @throws Exception
   * @return String
   */
  public static String getString(ResultSet rs,int col,String defaultString) throws Exception {
    String res = rs.getString(col);
    return res==null?defaultString:res;
  }
  /**
   *
   * @param rs ResultSet
   * @param col String
   * @param defaultString String
   * @throws Exception
   * @return String
   */
  public static String getString(ResultSet rs,String col,String defaultString) throws Exception {
    String res = rs.getString(col);
    return res==null?defaultString:res;
  }

  /**
   *
   * @param rs ResultSet
   * @return int
   * @throws Exception
   */
  public static int getRowCount(ResultSet rs) throws Exception {
    if (rs == null) return 0;
    int count = 0;
    if (rs.next() && rs.last()) {
      count = rs.getRow();
      rs.beforeFirst();
    }
    return count;
  }

}
