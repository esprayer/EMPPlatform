package jformservice.jdal.classes.exchange;

import com.eai.form.dal.plugins.*;
import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JExchangeSybase extends TSybaseExchangePlugin{
  public JExchangeSybase() {
  }


  //SQL转换。
  public      String onTranslateQuerySQL (Connection pConn,String strSql){
    return strSql;
//    return GBK2ISO(strSql);
  }

  public      String onTranslateUpdateSQL(Connection pConn,String strSql){
    return strSql;
//    return GBK2ISO( strSql );

  }

  public      String onTranslateDB2Jvm   (Connection pConn,String strText){
    return strText;
//    return ISO2GBK( strText );
  }

  public      String onTranslateJvm2DB   (Connection pConn,String strText){
    return strText;
//    return GBK2ISO( strText );
  }

  protected static String GBK2ISO( String mData ){
    String SqlTrans;
    try{
      byte[] BS = mData.getBytes( "GBK" );
      SqlTrans = new String( BS, "ISO8859_1" );
    }
    catch( Exception E ){
      SqlTrans = mData;
    }
    return SqlTrans;
  }

  protected static String ISO2GBK( String mData ){
    String SqlTrans;
    try{
      byte[] BS = mData.getBytes( "ISO8859_1" );
      SqlTrans = new String( BS, "GBK" );
    }
    catch( Exception E ){
      SqlTrans = mData;
    }
    return SqlTrans;
  }

}
