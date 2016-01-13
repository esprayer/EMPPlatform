package com.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.ResultSet;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;

public class EMPSQLUtil {
	
	public static String getWholeSql(JParamObject PO) {
		String       strSql = "";
		String    tableName = PO.GetValueByParamName("tableName", "");
		String    sqlColumn = "";
		String     sqlWhere = PO.GetValueByParamName("sqlWhere", null);
		String   sqlOrderBy = PO.GetValueByParamName("sqlOrderBy", null);
		String   sqlGroupBy = PO.GetValueByParamName("sqlGroupBy", null);
		EFDataSet   dataSet = (EFDataSet) PO.getValue("columns", EFDataSet.getInstance(tableName));
		EFRowSet     rowSet = null;
		
		for(int i = 0; i < dataSet.getRowCount(); i++) {
			rowSet = dataSet.getRowSet(i);
			sqlColumn += " " + rowSet.getString("COL_ID", "") + ",";
		}
		sqlColumn = sqlColumn.substring(0, sqlColumn.lastIndexOf(","));
		strSql = " select " + sqlColumn + " from " + tableName;
		if(sqlWhere != null) {
			strSql += " where " + sqlWhere;
		}
		if(sqlOrderBy != null) {
			strSql += " order by " + sqlOrderBy;
		}
		if(sqlGroupBy != null) {
			strSql += " group by " + sqlGroupBy;
		}
		return strSql;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 获取Bolb列数据
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	public static Object getBlogData( ResultSet rs, String BlobField ) throws Exception {
		ByteArrayOutputStream bout = null;
	    Blob                  blob = null;
	    blob = rs.getBlob( BlobField );
	    
	    if( blob != null ) {
	    	InputStream is = blob.getBinaryStream();
	    	int Length = 0;
	    	Class clazz = blob.getClass();
	    	Method method = clazz.getMethod( "getBufferSize", new Class[] {} );
	    	Object object = method.invoke( blob, new Object[] {} );
	    	if( object != null && object instanceof Integer ) {
	    		Length = ( ( Integer )object ).intValue();
	    	} else {
	    		Length = ( int )blob.length();
	    	}
	    	byte[] data = new byte[ Length ];
	    	int readLength = 0;
	    	while( ( readLength = is.read( data ) ) > 0 ) {
	    		if( bout == null ) bout = new ByteArrayOutputStream();
	    		bout.write( data, 0, readLength );
	    	}
	    	if( bout != null ) {
	    		data = bout.toByteArray();
	    		bout.close();
	    	}
	    	return data;
	    }
	    return null;
	}
	
	public static void closeAllResources(ResultSet rs, JStatement stmt, JConnection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(stmt != null) stmt.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(conn != null) conn.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
}
