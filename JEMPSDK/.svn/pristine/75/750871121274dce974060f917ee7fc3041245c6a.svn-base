package com.efounder.sql;

import java.io.*;
import java.math.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

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
public class DelegatePreparedStatement extends DelegateStatement implements PreparedStatement {
  /**
   *
   */
  PreparedStatement pstmt = null;
  /**
   *
   * @param stmt Statement
   * @return Statement
   */
  public static PreparedStatement getInstance(JConnection conn,PreparedStatement stmt) {
    return new DelegatePreparedStatement(conn,stmt);
  }
  /**
   *
   * @param conn JConnection
   * @param stmt PreparedStatement
   */
  protected DelegatePreparedStatement(JConnection conn,PreparedStatement stmt) {
    super(conn,stmt);
    pstmt = stmt;
  }
  /**
   *
   * @return int
   * @throws SQLException
   */
  public int executeUpdate() throws SQLException {
    return pstmt.executeUpdate();
  }
  /**
   *
   * @throws SQLException
   */
  public void addBatch() throws SQLException {
    pstmt.addBatch();
  }

  public void clearParameters() throws SQLException {
    pstmt.clearParameters();
  }

  public boolean execute() throws SQLException {
    return pstmt.execute();
  }

  public void setByte(int p, byte x) throws SQLException {
    pstmt.setByte(p,x);
  }

  public void setDouble(int p, double x) throws SQLException {
    conn.logInfo(" index"+p+"="+x+" ",4,p%5==0?true:false);
    pstmt.setDouble(p,x);
  }

  public void setFloat(int p, float x) throws SQLException {
    pstmt.setFloat(p,x);
  }

  public void setInt(int p, int x) throws SQLException {
    conn.logInfo(" index"+p+"="+x+" ",4,p%5==0?true:false);
    pstmt.setInt(p,x);
  }

  public void setNull(int p, int sqlType) throws SQLException {
    pstmt.setNull(p,sqlType);
  }

  public void setLong(int p, long x) throws SQLException {
    conn.logInfo(" index"+p+"="+x+" ",4,p%5==0?true:false);
    pstmt.setLong(p,x);
  }

  public void setShort(int p, short x) throws SQLException {
    pstmt.setShort(p,x);
  }

  public void setBoolean(int p, boolean x) throws SQLException {
    pstmt.setBoolean(p,x);
  }

  public void setBytes(int p, byte[] x) throws SQLException {
    pstmt.setBytes(p,x);
  }

  public void setAsciiStream(int p, InputStream x, int length) throws
      SQLException {
    pstmt.setAsciiStream(p,x,length);
  }

  public void setBinaryStream(int p, InputStream x, int length) throws
      SQLException {
    pstmt.setAsciiStream(p,x,length);
  }

  public void setUnicodeStream(int p, InputStream x, int length) throws
      SQLException {
    pstmt.setUnicodeStream(p,x,length);
  }

  public void setCharacterStream(int p, Reader reader, int length) throws
      SQLException {
    pstmt.setCharacterStream(p,reader,length);
  }

  public void setObject(int p, Object x) throws SQLException {
    pstmt.setObject(p,x);
  }

  public void setObject(int p, Object x, int targetSqlType) throws
      SQLException {
    pstmt.setObject(p,x,targetSqlType);
  }

  public void setObject(int p, Object x, int targetSqlType,
                        int scale) throws SQLException {
    pstmt.setObject(p,x,targetSqlType,scale);
  }

  public void setNull(int p, int sqlType, String typeName) throws
      SQLException {
    pstmt.setNull(p,sqlType,typeName);
  }

  public void setString(int p, String x) throws SQLException {
     conn.logInfo(" index"+p+"="+x+" ",4,p%5==0?true:false);
    pstmt.setString(p,x);
  }

  public void setBigDecimal(int p, BigDecimal x) throws
      SQLException {
    pstmt.setBigDecimal(p,x);
  }

  public void setURL(int p, URL x) throws SQLException {
    pstmt.setURL(p,x);
  }

  public void setArray(int i, Array x) throws SQLException {
    pstmt.setArray(i,x);
  }

  public void setBlob(int i, Blob x) throws SQLException {
    pstmt.setBlob(i,x);
  }

  public void setClob(int i, Clob x) throws SQLException {
    pstmt.setClob(i,x);
  }

  public void setDate(int p, Date x) throws SQLException {
    pstmt.setDate(p,x);
  }

  public ParameterMetaData getParameterMetaData() throws SQLException {
    return pstmt.getParameterMetaData();
  }

  public void setRef(int i, Ref x) throws SQLException {
    pstmt.setRef(i,x);
  }

  public ResultSet executeQuery() throws SQLException {
    return pstmt.executeQuery();
  }

  public ResultSetMetaData getMetaData() throws SQLException {
    return pstmt.getMetaData();
  }

  public void setTime(int p, Time x) throws SQLException {
    pstmt.setTime(p,x);
  }

  public void setTimestamp(int p, Timestamp x) throws SQLException {
    pstmt.setTimestamp(p,x);
  }

  public void setDate(int p, Date x, Calendar cal) throws
      SQLException {
    pstmt.setDate(p,x,cal);
  }

  public void setTime(int p, Time x, Calendar cal) throws
      SQLException {
    pstmt.setTime(p,x,cal);
  }

  public void setTimestamp(int p, Timestamp x, Calendar cal) throws
      SQLException {
    pstmt.setTimestamp(p,x,cal);
  }
@Override
public void setAsciiStream(int parameterIndex, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setAsciiStream(int parameterIndex, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setBinaryStream(int parameterIndex, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setBinaryStream(int parameterIndex, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setBlob(int parameterIndex, InputStream inputStream)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setBlob(int parameterIndex, InputStream inputStream, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setCharacterStream(int parameterIndex, Reader reader)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setCharacterStream(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setClob(int parameterIndex, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setClob(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setNCharacterStream(int parameterIndex, Reader value)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setNCharacterStream(int parameterIndex, Reader value, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setNClob(int parameterIndex, NClob value) throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setNClob(int parameterIndex, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setNClob(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setNString(int parameterIndex, String value) throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setRowId(int parameterIndex, RowId x) throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public boolean isClosed() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean isPoolable() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}
@Override
public void setPoolable(boolean poolable) throws SQLException {
	// TODO Auto-generated method stub
	
}
@Override
public boolean isWrapperFor(Class<?> iface) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}
@Override
public <T> T unwrap(Class<T> iface) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

}
