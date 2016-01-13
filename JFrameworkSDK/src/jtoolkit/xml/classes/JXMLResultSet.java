package jtoolkit.xml.classes;

import java.sql.*;

import org.jdom.*;

import com.core.xml.JBOFClass;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JXMLResultSet extends JOpenXMLResultSet {
  Object CallBackObject = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JXMLResultSet(ResultSet RS) {
    this(RS,null);
  }
  public JXMLResultSet(ResultSet RS,Object CO) {
    super();
    CallBackObject = CO;
    OpenResultSet(RS);
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JXMLResultSet() {
    super();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JXMLResultSet(String DataString) {
    super(DataString);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void OpenResultSet(ResultSet RS) {
    if ( RS == null ) return;
    int Count;Element R;
    try {
      Count = GetResultSetMetaData(RS.getMetaData());
      while ( RS.next() ) {
        R = this.AddChildElement(ResultElement,"R");
        for(int i=0;i<Count;i++) {
          if ( "B".equals(ColTypeArray[i]) ) {
            // 如果是Image列,则调用CallbackObject对象的方法进行处理
            // ResultSet 列别名，列名,行索引
            if ( CallBackObject != null ) {
              try {
                Object[] OArray = {RS, ColNameArray[i], ColAliasArray[i],new Integer(ResultRowCount),this};
                JBOFClass.CallObjectMethod(CallBackObject, "getInputStream",OArray);
              }
              catch (Exception e) {}
            }
          } else {
            SetElementValue(R, ColAliasArray[i], com.efounder.pub.util.StringFunction.Uni2GB(RS.getString(ColNameArray[i])));
            // 给用户修改数据的机会
            if ( CallBackObject != null ) {
              try {
                Object[] OArray = {RS, ColNameArray[i], ColAliasArray[i],new Integer(ResultRowCount),this,R};
                JBOFClass.CallObjectMethod(CallBackObject, "callBackResultSet",OArray);
              }
              catch (Exception e) {}
            }

          }
        }
        ResultRowCount++;
      }
      // 设置行数
      ResultElement.setAttribute("RowCount",String.valueOf(ResultRowCount));
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int GetResultSetMetaData(ResultSetMetaData RSMD) {
    int Index = 0,ColCount = 0;String Name,Alias;int Type;String AttName;
    try {
      ResultElement = this.AddChildElement(null,"ResultSet");
      ColCount      = RSMD.getColumnCount();
      ColNameArray  = new String[ColCount];
      ColAliasArray = new String[ColCount];
      ColTypeArray  = new String[ColCount];
      // 设置列数
      ResultElement.setAttribute("ColCount",String.valueOf(ColCount));
      // 循行设置列属性
      for(Index=0;Index<ColCount;Index++) {
        Type = RSMD.getColumnType(Index+1);
        // 确定列类型
        if ( Type == java.sql.Types.CHAR || Type == java.sql.Types.VARCHAR ||
             Type == java.sql.Types.LONGVARCHAR ) {
          ColTypeArray[Index] = "C";
        } else {
          if ( Type == java.sql.Types.INTEGER  || Type == java.sql.Types.NUMERIC ||
               Type == java.sql.Types.SMALLINT || Type == java.sql.Types.TINYINT ||
               Type == java.sql.Types.BIGINT ) {
            ColTypeArray[Index] = "N";
          } if ( Type == java.sql.Types.BINARY || Type == java.sql.Types.VARBINARY ||
                 Type == java.sql.Types.LONGVARBINARY || Type == java.sql.Types.JAVA_OBJECT ||
                 Type == java.sql.Types.BLOB || Type == java.sql.Types.CLOB ) {
            ColTypeArray[Index] = "B";

          } else {
            ColTypeArray[Index] = "F";
          }
        }
        ColNameArray[Index]  = RSMD.getColumnName(Index+1);
        // 设置列名
        AttName  = "c_"+String.valueOf(Index+1);
        ResultElement.setAttribute(AttName,ColNameArray[Index]);
        // 设置别名
        AttName  = "a_"+String.valueOf(Index+1);
        ColAliasArray[Index] = "C"+String.valueOf(Index+1);
        ResultElement.setAttribute(AttName,ColAliasArray[Index]);
        // 设置列类型
        AttName  = "t_"+String.valueOf(Index+1);
        ResultElement.setAttribute(AttName,ColTypeArray[Index]);
        // ResultSet 列别名，列名,行索引
        if ( CallBackObject != null && "B".equals(ColTypeArray[Index])) {
          try {
            Object[] OArray = {RSMD, ColNameArray[Index], ColAliasArray[Index],ColTypeArray[Index],this};
            JBOFClass.CallObjectMethod(CallBackObject, "callBackMetaData",OArray);
          }
          catch (Exception e) {}
        }
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return ColCount;
  }
}
