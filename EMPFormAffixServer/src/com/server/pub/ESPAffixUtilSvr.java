package com.server.pub;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;

/**
 * 数据导入
 * @author ZhangES
 *
 */
public class ESPAffixUtilSvr {	
	/* 上传附件信息
	 * @param efRowSet
	 * @param conn
	 * @param puser
	 */
	public static void uploadAffixTable(JParamObject PO, EFRowSet efRowSet,JConnection conn) throws Exception{
		Statement  stmt = null;
		ResultSet    rs = null;
		String   DCT_ID = efRowSet.getStringByTrim("DCT_ID", "");
		String   prefix = efRowSet.getStringByTrim("Prefix", "");
		String     guid = efRowSet.getString("F_GUID", "");
		String     fybh = efRowSet.getString("F_FYBH", "");
		String     name = efRowSet.getString("F_NAME", "");
		String     wjlx = efRowSet.getString("F_WJLX", "");
		String     path = efRowSet.getString("FILEPATH", "");
		String     user = efRowSet.getString("F_USER", "");
		String    table = prefix/*+"_AFFIX"*/;
		String    where = " F_GUID = '" + guid + "'";
		Calendar      c = Calendar.getInstance();
        long       time = c.getTimeInMillis();
        byte[]       bt = (byte[])efRowSet.getObject("Bytes", "");
//        FileInputStream fins = (FileInputStream) efRowSet.getObject("inputStream", "");
        
        try {
			String strSql = "select * from " + DCT_ID+ " where " + where;
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(strSql);
			// 创建或更新到AFFIX的sql语句
			if(!rs.next()){
				strSql = " insert into " + DCT_ID + "(F_GUID,F_FYBH,F_NAME,F_WJLX,F_USER,F_PATH,F_CRDATE,F_CHDATE)"
					   + " values('" + guid + "','" + fybh + "','" + name 
					   + "','" + wjlx + "','" + user + "','" + path + "',NOW(),NOW())";
			}else{
				strSql = "update " + DCT_ID + " set F_NAME='"+name+"',F_USER='"+user+"',"
			           + " F_WJLX='"+wjlx+"',F_CHDATE = NOW()"
			           + " where " + where;
			}
			stmt.execute(strSql);
			strSql = "select F_GUID,F_FYBH,F_NAME,F_WJLX,F_USER,F_PATH,F_WJSJ,F_CRDATE,F_CHDATE from " + DCT_ID + " where " + where;
			rs = stmt.executeQuery(strSql);
			if(rs.next()){
				rs.updateBytes(7,bt);
				rs.updateRow();
			}
			rs.close();
//			strSql = " insert into " + DCT_ID + "(F_GUID,F_FYBH,F_NAME,F_WJLX,F_USER,F_PATH,F_WJSJ,F_CRDATE,F_CHDATE)"
//			       + " values(?,?,?,?,?,?,?,?,?)";
//			preparedStatement = conn.prepareStatement(strSql);
//			preparedStatement.setString(1, guid);
//			preparedStatement.setString(2, fybh);
//			preparedStatement.setString(3, name);
//			preparedStatement.setString(4, wjlx);
//			preparedStatement.setString(5, user);
//			preparedStatement.setString(6, path);
//			preparedStatement.setBinaryStream(7, fins, fins.available());
//			preparedStatement.setTimestamp(8, new Timestamp(time));
//			preparedStatement.setTimestamp(9, new Timestamp(time));
//			preparedStatement.execute();
//			strSql = "select F_WJSJ from " + DCT_ID + " where F_GUID = '" + guid + "' FOR UPDATE";
//			rs = stmt.executeQuery(strSql);
//			if( rs != null && rs.next() ) {
//	      		java.sql.Blob srcBlob = rs.getBlob( "F_WJSJ" );
//	      		Class clazz = srcBlob.getClass();
//	      		Method method = clazz.getMethod( "getBinaryOutputStream", new Class[] {} );
//	         	OutputStream out = ( OutputStream )method.invoke( srcBlob, new Object[] {} );
//	        	out.write( bt, 0, bt.length );
//	         	out.close();
//	      	}
//		
//     rs = stmt.executeQuery( strSql );
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		} finally{
			conn.BackStatement(stmt, rs);
		}
	}
	
	/**
	 * 上传附件文件信息
	 * @param efRowSet
	 * @param conn
	 * @param puser
	 */
	public static boolean uploadAffixBlobTable(JParamObject PO, EFRowSet efRowSet,JConnection conn) throws Exception{
		String          prefix = efRowSet.getStringByTrim("Prefix", "");
		String           order = efRowSet.getString("F_ORDE", "");
		byte[]            data = (byte[])efRowSet.getValue("Bytes", null);
		String           table = prefix + "_AFFIXBLOB";
		ResultSet           rs = null;
		String          F_CODE = efRowSet.getString("F_CODE", "");
		String          F_USER = efRowSet.getString("F_USER", "");
		String         FLOW_ID = efRowSet.getString("FLOW_ID", "");
		String         NODE_ID = efRowSet.getString("NODE_ID", "");
		String      fieldNames = "F_CODE,F_USER,FLOW_ID,NODE_ID,F_ORDE";
		String     fieldValues = "'" + F_CODE + "','" + F_USER + "','" + FLOW_ID + "','" + NODE_ID + "','" + order + "'";
		Statement         stmt = conn.createStatement();
		String           where = " F_CODE = '" + F_CODE + "' and F_USER = '" + F_USER + "' and FLOW_ID = '" + FLOW_ID + "' and NODE_ID = '" + NODE_ID + "' and F_ORDE = '" + order + "'";
		String            bSql = "select F_ORDE from " + table+ " where "+ where;
		try {
			rs = stmt.executeQuery(bSql);
			// 创建或更新到AFFIXBLOB的sql语句
			if(!rs.next()){
				conn.getSQLToolkit().insertBlobData(conn, table, fieldNames, "F_BLOB", fieldValues, where, data);
			}else{
				conn.getSQLToolkit().updateBlobData(conn, table, "F_BLOB", where, data);
			}
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		} finally{
			conn.BackStatement(stmt, rs);
		}
	}
	
	/**
	 * 下载附件
	 * */			
	public static void loadAffixBlobFromDB(EFRowSet efRowSet,JConnection conn){
		String       F_GUID = efRowSet.getString("F_GUID", "");
		String       DCT_ID = efRowSet.getString("DCT_ID", "");
		JStatement     stmt = null;
		ResultSet        rs = null;
	    byte []        data = null;
	    String     sqlWhere = "F_GUID = '" + F_GUID + "'";
		StringBuffer   bSql = new StringBuffer();
		bSql.append("select F_WJSJ from ");
		bSql.append(DCT_ID);
		bSql.append(" where ");
		bSql.append(sqlWhere);
		bSql.trimToSize();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(bSql.toString());
			if(rs != null && rs.next()) {
				data = rs.getBytes("F_WJSJ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		efRowSet.setValue("Bytes", data);
	}
}
