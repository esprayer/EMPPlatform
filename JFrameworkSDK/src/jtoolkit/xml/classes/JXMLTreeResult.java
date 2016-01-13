package jtoolkit.xml.classes;

import java.sql.*;

import org.jdom.*;

import com.efounder.pub.util.StringFunction;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JXMLTreeResult extends JXMLResultSet {
  String BH_FIELD = null;
  String JS_FIELD = null;
  String BHStruct = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JXMLTreeResult(ResultSet RS,String BHF,String JSF,String STF) {
    super();
    BH_FIELD = BHF;JS_FIELD = JSF;BHStruct = STF;
    OpenResultSet(RS);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JXMLTreeResult(String DataString) {
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
    int ColCount;Element R;
    int OldJS = 0,JS = 0,Length;String BH=null,OldParent = null,Parent_CODE=null;Element node=null,pnode=null;
    try {
      ColCount = GetResultSetMetaData(RS.getMetaData());
      pnode = null;node = null;
      while ( RS.next() ) {
        BH  = RS.getString(BH_FIELD).trim();
        JS  = RS.getInt(JS_FIELD);
        if ( OldJS == 0 ) OldJS = JS;
        if ( JS == OldJS ) {
          node    = AddChildElement(ResultElement,"D_"+BH);
        } else if ( JS > OldJS ) {
          Length  = StringFunction.GetStructLength(BHStruct,JS-1);
          Parent_CODE = BH.substring(0,Length);
          if ( !Parent_CODE.equals(OldParent) || pnode == null ) {
            OldParent = Parent_CODE;
            pnode   = GetElementByName("D_"+Parent_CODE);
          }
          node = AddChildElement(pnode,"D_"+BH);
        }
        for(int i=0;i<ColCount;i++) {
          SetElementValue(node,ColAliasArray[i],com.efounder.pub.util.StringFunction.Uni2GB(RS.getString(ColNameArray[i])));
        }
        ResultRowCount++;
      }
      // 设置行数
      ResultElement.setAttribute("RowCount",String.valueOf(ResultRowCount));
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}