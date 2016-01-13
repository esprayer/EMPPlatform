package com.efounder.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.dbcp.DelegatingConnection;

/**
 * <p>Title: ��װ PreparedStatement �࣬ʵ�ֶ�SQL���ת��������</p>
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
public class JPreparedStatement
    extends JStatement implements PreparedStatement {
  /**
   * Statement
   */
  public PreparedStatement preparedStatement;

  public JPreparedStatement(JConnection conn,PreparedStatement preparedStatement) {
    super(null,preparedStatement);
    this.preparedStatement = preparedStatement;
  }

  public void addBatch() throws SQLException {
    preparedStatement.addBatch();
  }

  public void clearParameters() throws SQLException {
    preparedStatement.clearParameters();
  }

  public boolean execute() throws SQLException {
    return preparedStatement.execute();
  }

  public ResultSet executeQuery() throws SQLException {
    return preparedStatement.executeQuery();
  }

  public int executeUpdate() throws SQLException {
    return preparedStatement.executeUpdate();
  }

  public ResultSetMetaData getMetaData() throws SQLException {
    return preparedStatement.getMetaData();
  }

  public ParameterMetaData getParameterMetaData() throws SQLException {
    return preparedStatement.getParameterMetaData();
  }

  public void setArray(int i, Array x) throws SQLException {
    preparedStatement.setArray(i, x);
  }

  public void setAsciiStream(int parameterIndex, InputStream x, int length) throws
      SQLException {
    preparedStatement.setAsciiStream(parameterIndex, x, length);
  }

  public void setBigDecimal(int parameterIndex, BigDecimal x) throws
      SQLException {
    preparedStatement.setBigDecimal(parameterIndex, x);
  }

  public void setBinaryStream(int parameterIndex, InputStream x, int length) throws
      SQLException {
    preparedStatement.setBinaryStream(parameterIndex, x, length);
  }

  public void setBlob(int i, Blob x) throws SQLException {
    preparedStatement.setBlob(i, x);
  }

  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    preparedStatement.setBoolean(parameterIndex, x);
  }

  public void setByte(int parameterIndex, byte x) throws SQLException {
    preparedStatement.setByte(parameterIndex, x);
  }

  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    preparedStatement.setBytes(parameterIndex, x);
  }

  public void setCharacterStream(int parameterIndex, Reader reader, int length) throws
      SQLException {
    preparedStatement.setCharacterStream(parameterIndex, reader, length);
  }

  public void setClob(int i, Clob x) throws SQLException {
    preparedStatement.setClob(i, x);
  }

  public void setDate(int parameterIndex, Date x) throws SQLException {
    preparedStatement.setDate(parameterIndex, x);
  }

  public void setDate(int parameterIndex, Date x, Calendar cal) throws
      SQLException {
    preparedStatement.setDate(parameterIndex, x, cal);
  }

  public void setDouble(int parameterIndex, double x) throws SQLException {
    preparedStatement.setDouble(parameterIndex, x);
  }

  public void setFloat(int parameterIndex, float x) throws SQLException {
    preparedStatement.setFloat(parameterIndex, x);
  }

  public void setInt(int parameterIndex, int x) throws SQLException {
    preparedStatement.setInt(parameterIndex, x);
  }

  public void setLong(int parameterIndex, long x) throws SQLException {
    preparedStatement.setLong(parameterIndex, x);
  }

  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    preparedStatement.setNull(parameterIndex, sqlType);
  }

  public void setNull(int paramIndex, int sqlType, String typeName) throws
      SQLException {
    preparedStatement.setNull(paramIndex, sqlType, typeName);
  }

  public void setObject(int parameterIndex, Object x) throws SQLException {
    preparedStatement.setObject(parameterIndex, x);
  }

  public void setObject(int parameterIndex, Object x, int targetSqlType) throws
      SQLException {
    preparedStatement.setObject(parameterIndex, x, targetSqlType);
  }

  public void setObject(int parameterIndex, Object x, int targetSqlType,
                        int scale) throws SQLException {
    preparedStatement.setObject(parameterIndex, x, targetSqlType, scale);
  }

  public void setRef(int i, Ref x) throws SQLException {
    preparedStatement.setRef(i, x);
  }

  public void setShort(int parameterIndex, short x) throws SQLException {
    preparedStatement.setShort(parameterIndex, x);
  }

  public void setString(int parameterIndex, String x) throws SQLException {
    preparedStatement.setString(parameterIndex, x);
  }

  public void setTime(int parameterIndex, Time x) throws SQLException {
    preparedStatement.setTime(parameterIndex, x);
  }

  public void setTime(int parameterIndex, Time x, Calendar cal) throws
      SQLException {
    preparedStatement.setTime(parameterIndex, x, cal);
  }

  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    preparedStatement.setTimestamp(parameterIndex, x);
  }

  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws
      SQLException {
    preparedStatement.setTimestamp(parameterIndex, x, cal);
  }

  public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws
      SQLException {
    preparedStatement.setUnicodeStream(parameterIndex, x, length);
  }

  public void setURL(int parameterIndex, URL x) throws SQLException {
    preparedStatement.setURL(parameterIndex, x);
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
}
