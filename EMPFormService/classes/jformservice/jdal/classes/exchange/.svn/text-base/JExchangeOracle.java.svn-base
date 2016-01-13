package jformservice.jdal.classes.exchange;


import com.eai.form.dal.plugins.TOracleExchangePlugin;
import java.sql.*;
import com.pansoft.pub.util.*;
import oracle.jdbc.*;
import oracle.sql.*;
import java.io.*;
import java.util.*;
import org.apache.soap.encoding.soapenc.*;
/**
 * <p>JExchangeOracle</p>
 * <p>Sybase的交换对象。</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JExchangeOracle extends TOracleExchangePlugin{
  public JExchangeOracle() {
  }

  /**
   * SQL转换。
   * @param pConn Connection
   * @param strSql String
   * @return String
   */
  public      String onTranslateQuerySQL (Connection pConn,String strSql){
    return strSql;
  }

  /**
   * 更新的SQL。
   * @param pConn Connection
   * @param strSql String
   * @return String
   */
  public      String onTranslateUpdateSQL(Connection pConn,String strSql){
    Statement pST = null;
    try{
      pST = pConn.createStatement();
      String strNewSql = JConvertSql.convertSql(pST, strSql);
      return strNewSql;
    }catch(Exception E){
      return strSql;
    }
    finally{
      try{
        pST.close();
      }catch(Exception E){ }
    }

  }

  private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 5;

  public String ReadFieldValue( ResultSet RS, String strFieldName, String strFieldType ){
    return super.ReadFieldValue(RS, strFieldName, strFieldType);

//    try{
//      if( strFieldType.equals( "Image" ) ){
//        OracleResultSet pRS = (OracleResultSet)RS;
//        BLOB          pBlob = pRS.getBLOB(strFieldName);
//        InputStream STR     = pBlob.getBinaryStream();
//
//        /**
//         * 从输入流中读出
//         */
//        String   EncodeStr = "";
//        int    iBufferSize = 1024;   //一次读1K.
//        int          iRead = 0;
//        int          iCount= 0;
//        byte[]        DATA = new byte[MAX_BUFFER_SIZE];  //5M
//        int           iPos = 0;
//        while( true ){
//          iRead = STR.read( DATA, iPos, iBufferSize );
//          if( iRead < 0 ){
//            break;
//          }
//          iCount += iRead;
//          iPos += iRead;
//          if( iRead < iBufferSize ){
//            break;
//          }
//        }
//
//        //iPos就是总的个数.
//        byte[] pRES = new byte[ iCount ];
//        //复制出来.
//        System.arraycopy( DATA, 0, pRES, 0, iCount );
//        EncodeStr = Base64.encode( pRES );
//        return EncodeStr;
//      }
//    }
//    catch(Exception E){
//      return "";
//    }
//
//    return "";
  }
  public byte[] ReadFieldValueBytes( ResultSet RS, String strFieldName, String strFieldType ){
    return null;
  }
  /**
   * 返回Oracle特定的表达式.
   * @param strType String
   * @return String
   */
  public String getSqlImageLabelForType( String strType ){
    return super.getSqlImageLabelForType(strType);
    //return "empty_blob()";
  }

  //更新.
  public boolean UpdateRecordImages(Connection pConn, String strSql,
                                    String strTName, Vector pKeyFieldNames,
                                    Vector pKeyFieldTypes,
                                    Vector pKeyFieldValues,
                                    Vector pImageFieldNames,
                                    Vector pImageFieldTypes,
                                    Vector pImageFieldValues)throws Exception {
    return super.UpdateRecordImages(pConn, strSql, strTName, pKeyFieldNames,
                                    pKeyFieldTypes, pKeyFieldValues,
                                    pImageFieldNames, pImageFieldTypes,
                                    pImageFieldValues);
    /*
    try{
     pConn.setAutoCommit(false);

     //先执行.
     Statement pST = pConn.createStatement();
     pST.execute(strSql);

     //生成IMAGE列表.
     StringBuffer pBF = new StringBuffer();
     pBF.append("SELECT 1");
     for ( int i = 0; i < pImageFieldNames.size(); i ++){
       pBF.append(",");
       pBF.append(pImageFieldNames.get(i).toString());
     }

     pBF.append(" FROM ");
     pBF.append(strTName);
     pBF.append(" WHERE 1=1 ");

     //主键的条件.
     for ( int i = 0; i < pKeyFieldNames.size(); i ++){
       pBF.append(" AND ");
       pBF.append(pKeyFieldNames.get(i));
       pBF.append("=");
       if ( pKeyFieldTypes.get(i).equals("String")){
         pBF.append(" '"+pKeyFieldValues.get(i)+"'");
       }
       else{
         pBF.append(pKeyFieldValues.get(i));
       }
     }

     pBF.append("  FOR UPDATE");

     String pSql  = pBF.toString();

     //转换一下SQL。
     pSql         = onTranslateUpdateSQL(pConn,pSql);

     PreparedStatement  pPreST = pConn.prepareStatement(pSql);
     ResultSet              RS = pPreST.executeQuery(pSql);
     if ( RS.next()){
       for ( int i =0; i < pImageFieldNames.size(); i ++){
         String  strFName = pImageFieldNames.get(i).toString();
         BLOB       pBlob = (BLOB) RS.getBlob( strFName );  //获得BLOB.
         OutputStream out = pBlob.getBinaryOutputStream();
         byte[]    pBytes = (byte[])pImageFieldValues.get(i);
         out.write(pBytes);
         out.close();
       }
     }
     pPreST.executeUpdate();
     //递交。
     pConn.commit();
     //INSERT完成以后，并不向RES里面加。
     return true;
   }
   catch( Exception E ){
     E.printStackTrace();
     try{
       pConn.rollback();
     }
     catch( Exception EE ){
     }
   }
   return false;
   */


  }
}
