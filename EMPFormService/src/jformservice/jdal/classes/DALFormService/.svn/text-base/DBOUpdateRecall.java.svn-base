package jformservice.jdal.classes.DALFormService;

import java.sql.Connection;
import java.util.Vector;
import jfoundation.sql.classes.JConnection;
import java.sql.Statement;
import com.pansoft.pub.util.Debug;
import com.eai.form.dal.update.*;
import com.pansoft.pub.util.JConvertSql;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DBOUpdateRecall implements IUpdateRecall {
  private  JConnection mConn = null;

  public DBOUpdateRecall(JConnection conn) {
    mConn = conn;
  }

  public Connection getRecallConnection() {
    return mConn;
  }

  public boolean RecallSqls(Connection conn, Vector strSqls) {
    //------------------------------------------------------------------------------------
    //下面执行
    //------------------------------------------------------------------------------------
    try {
    Statement Std = conn.createStatement();

    if (Std != null) {
        Std.clearBatch();
        //加入删除SQL
        for (int i = 0; i < strSqls.size(); i++) {
          String Sql = (String) (strSqls.get(i));
          Std.addBatch(JConvertSql.convertSql(Std, Sql ));
          Debug.PrintlnMessageToSystem( Sql );
        }

        /**
         * 批处理执行
         */
        Std.executeBatch();

        return true;

      }
    }
    catch (Exception E) {
      E.printStackTrace();
      return false;
    }
    finally{
    }

    return true;
  }
  public int getMaxSqls() {
    return 10000;
  }
}
