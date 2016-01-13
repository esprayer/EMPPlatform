package esyt.framework.com.core.foundation.sql;

import java.sql.*;
import java.math.BigDecimal;
import java.net.URL;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Calendar;

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
public class DelegateCallableStatement  extends DelegatePreparedStatement implements CallableStatement {
  /**
   *
   * @param stmt Statement
   * @return Statement
   */
  public static CallableStatement getInstance(JConnection conn,CallableStatement stmt) {
    return new DelegateCallableStatement(conn,stmt);
  }
  protected CallableStatement cstmt = null;
  /**
   *
   * @param conn JConnection
   * @param stmt PreparedStatement
   */
  protected DelegateCallableStatement(JConnection conn,CallableStatement stmt) {
    super(conn,stmt);
    cstmt = stmt;
  }

  public boolean wasNull() throws SQLException {
    return cstmt.wasNull();
  }

  public byte getByte(int p) throws SQLException {
    return cstmt.getByte(p);
  }

  public double getDouble(int p) throws SQLException {
    return cstmt.getDouble(p);
  }

  public float getFloat(int p) throws SQLException {
    return cstmt.getFloat(p);
  }

  public int getInt(int p) throws SQLException {
    return cstmt.getInt(p);
  }

  public long getLong(int p) throws SQLException {
    return cstmt.getLong(p);
  }

  public short getShort(int p) throws SQLException {
    return cstmt.getShort(p);
  }

  public boolean getBoolean(int p) throws SQLException {
    return cstmt.getBoolean(p);
  }

  public byte[] getBytes(int p) throws SQLException {
    return cstmt.getBytes(p);
  }

  public void registerOutParameter(int p, int sqlType) throws
      SQLException {
    cstmt.registerOutParameter(p,sqlType);
  }

  public void registerOutParameter(int p, int sqlType, int scale) throws
      SQLException {
    cstmt.registerOutParameter(p,sqlType,scale);
  }

  public Object getObject(int p) throws SQLException {
    return cstmt.getObject(p);
  }

  public String getString(int p) throws SQLException {
    return cstmt.getString(p);
  }

  public void registerOutParameter(int p, int sqlType, String typeName) throws
      SQLException {
    cstmt.registerOutParameter(p,sqlType,typeName);
  }

  public byte getByte(String p) throws SQLException {
    return cstmt.getByte(p);
  }

  public double getDouble(String p) throws SQLException {
    return cstmt.getDouble(p);
  }

  public float getFloat(String p) throws SQLException {
    return cstmt.getFloat(p);
  }

  public int getInt(String p) throws SQLException {
    return cstmt.getInt(p);
  }

  public long getLong(String p) throws SQLException {
    return cstmt.getLong(p);
  }

  public short getShort(String p) throws SQLException {
    return cstmt.getShort(p);
  }

  public boolean getBoolean(String p) throws SQLException {
    return cstmt.getBoolean(p);
  }

  public byte[] getBytes(String p) throws SQLException {
    return cstmt.getBytes(p);
  }

  public void setByte(String p, byte x) throws SQLException {
    cstmt.setByte(p,x);
  }

  public void setDouble(String p, double x) throws SQLException {
    cstmt.setDouble(p,x);
  }

  public void setFloat(String p, float x) throws SQLException {
    cstmt.setFloat(p,x);
  }

  public void registerOutParameter(String p, int sqlType) throws
      SQLException {
    cstmt.registerOutParameter(p,sqlType);
  }

  public void setInt(String p, int x) throws SQLException {
    cstmt.setInt(p,x);
  }

  public void setNull(String p, int sqlType) throws SQLException {
    cstmt.setNull(p,sqlType);
  }

  public void registerOutParameter(String p, int sqlType, int scale) throws
      SQLException {
    cstmt.registerOutParameter(p,sqlType,scale);
  }

  public void setLong(String p, long x) throws SQLException {
    cstmt.setLong(p,x);
  }

  public void setShort(String p, short x) throws SQLException {
    cstmt.setShort(p,x);
  }

  public void setBoolean(String p, boolean x) throws SQLException {
    cstmt.setBoolean(p,x);
  }

  public void setBytes(String p, byte[] x) throws SQLException {
    cstmt.setBytes(p,x);
  }

  public BigDecimal getBigDecimal(int p) throws SQLException {
    return cstmt.getBigDecimal(p);
  }

  public BigDecimal getBigDecimal(int p, int scale) throws
      SQLException {
    return cstmt.getBigDecimal(p,scale);
  }

  public URL getURL(int p) throws SQLException {
    return cstmt.getURL(p);
  }

  public Array getArray(int i) throws SQLException {
    return cstmt.getArray(i);
  }

  public Blob getBlob(int i) throws SQLException {
    return cstmt.getBlob(i);
  }

  public Clob getClob(int i) throws SQLException {
    return cstmt.getClob(i);
  }

  public Date getDate(int p) throws SQLException {
    return cstmt.getDate(p);
  }

  public Ref getRef(int i) throws SQLException {
    return cstmt.getRef(i);
  }

  public Time getTime(int p) throws SQLException {
    return cstmt.getTime(p);
  }

  public Timestamp getTimestamp(int p) throws SQLException {
    return cstmt.getTimestamp(p);
  }

  public void setAsciiStream(String p, InputStream x, int length) throws
      SQLException {
    cstmt.setAsciiStream(p,x,length);
  }

  public void setBinaryStream(String p, InputStream x, int length) throws
      SQLException {
    cstmt.setBinaryStream(p,x,length);
  }

  public void setCharacterStream(String p, Reader reader,
                                 int length) throws SQLException {
    cstmt.setCharacterStream(p,reader,length);
  }

  public Object getObject(String p) throws SQLException {
    return cstmt.getObject(p);
  }

  public void setObject(String p, Object x) throws SQLException {
    cstmt.setObject(p,x);
  }

  public void setObject(String p, Object x, int targetSqlType) throws
      SQLException {
    cstmt.setObject(p,x,targetSqlType);
  }

  public void setObject(String p, Object x, int targetSqlType,
                        int scale) throws SQLException {
    cstmt.setObject(p,x,targetSqlType,scale);
  }

  public Object getObject(int i, Map map) throws SQLException {
    return cstmt.getObject(i,map);
  }

  public String getString(String p) throws SQLException {
    return cstmt.getString(p);
  }

  public void registerOutParameter(String p, int sqlType,
                                   String typeName) throws SQLException {
    cstmt.registerOutParameter(p,sqlType,typeName);
  }

  public void setNull(String p, int sqlType, String typeName) throws
      SQLException {
    cstmt.setNull(p,sqlType,typeName);
  }

  public void setString(String p, String x) throws SQLException {
    cstmt.setString(p,x);
  }

  public BigDecimal getBigDecimal(String p) throws SQLException {
    return cstmt.getBigDecimal(p);
  }

  public void setBigDecimal(String p, BigDecimal x) throws
      SQLException {
    cstmt.setBigDecimal(p,x);
  }

  public URL getURL(String p) throws SQLException {
    return cstmt.getURL(p);
  }

  public void setURL(String p, URL val) throws SQLException {
    cstmt.setURL(p,val);
  }

  public Array getArray(String p) throws SQLException {
    return cstmt.getArray(p);
  }

  public Blob getBlob(String p) throws SQLException {
    return cstmt.getBlob(p);
  }

  public Clob getClob(String p) throws SQLException {
    return cstmt.getClob(p);
  }

  public Date getDate(String p) throws SQLException {
    return cstmt.getDate(p);
  }

  public void setDate(String p, Date x) throws SQLException {
    cstmt.setDate(p,x);
  }

  public Date getDate(int p, Calendar cal) throws SQLException {
    return cstmt.getDate(p,cal);
  }

  public Ref getRef(String p) throws SQLException {
    return cstmt.getRef(p);
  }

  public Time getTime(String p) throws SQLException {
    return cstmt.getTime(p);
  }

  public void setTime(String p, Time x) throws SQLException {
    cstmt.setTime(p,x);
  }

  public Time getTime(int p, Calendar cal) throws SQLException {
    return cstmt.getTime(p,cal);
  }

  public Timestamp getTimestamp(String p) throws SQLException {
    return cstmt.getTimestamp(p);
  }

  public void setTimestamp(String p, Timestamp x) throws
      SQLException {
    cstmt.setTimestamp(p,x);
  }

  public Timestamp getTimestamp(int p, Calendar cal) throws
      SQLException {
    return cstmt.getTimestamp(p,cal);
  }

  public Object getObject(String p, Map map) throws SQLException {
    return cstmt.getObject(p,map);
  }

  public Date getDate(String p, Calendar cal) throws SQLException {
    return cstmt.getDate(p,cal);
  }

  public Time getTime(String p, Calendar cal) throws SQLException {
    return cstmt.getTime(p,cal);
  }

  public Timestamp getTimestamp(String p, Calendar cal) throws
      SQLException {
    return cstmt.getTimestamp(p,cal);
  }

  public void setDate(String p, Date x, Calendar cal) throws
      SQLException {
    cstmt.setDate(p,x,cal);
  }

  public void setTime(String p, Time x, Calendar cal) throws
      SQLException {
    cstmt.setTime(p,x,cal);
  }

  public void setTimestamp(String p, Timestamp x, Calendar cal) throws
      SQLException {
    cstmt.setTimestamp(p,x,cal);
  }

@Override
public Reader getCharacterStream(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getCharacterStream(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getNCharacterStream(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getNCharacterStream(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public NClob getNClob(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public NClob getNClob(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getNString(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getNString(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public RowId getRowId(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public RowId getRowId(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public SQLXML getSQLXML(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public SQLXML getSQLXML(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setAsciiStream(String parameterName, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setAsciiStream(String parameterName, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBinaryStream(String parameterName, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBinaryStream(String parameterName, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBlob(String parameterName, Blob x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBlob(String parameterName, InputStream inputStream)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBlob(String parameterName, InputStream inputStream, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setCharacterStream(String parameterName, Reader reader)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setCharacterStream(String parameterName, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClob(String parameterName, Clob x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClob(String parameterName, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClob(String parameterName, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNCharacterStream(String parameterName, Reader value)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNCharacterStream(String parameterName, Reader value, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(String parameterName, NClob value) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(String parameterName, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(String parameterName, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNString(String parameterName, String value) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setRowId(String parameterName, RowId x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setSQLXML(String parameterName, SQLXML xmlObject)
		throws SQLException {
	// TODO Auto-generated method stub
	
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
