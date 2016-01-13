package jreport.swing.classes.wizard;

import java.util.*;

import javax.swing.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JFunctionStub {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
  public static Hashtable ParamList = null;
  public static Vector    ParamList1 = null;
  // <F id="BB" ps="4" p1="YEAR" p2="MONTH" p3="BBBH" p4="BBREF" text="报表函数" des1="" des2=""/>
  public String id;
  public int    ps;
  public String param;
  public String text;
  public String des1;
  public String des2;
  public String dobj;
  public String cobj;
    private String functionString;
    private Vector DObj;
    private Vector CObj;
    public JFunctionStub() {
  }
  public String toString() {
    return id+"("+text+")";
  }
  // 初始化
  public void InitFunctionStub() {
    ParamList = new Hashtable();
    ParamList1= new Vector();
  }
  // 设置值控制
  public void PutParamValueCtrl(String ParamName,JTextField ValueCtrl) {
    if ( ParamList == null ) return;
    ParamList.put(ParamName,ValueCtrl);
    if ( ParamList1 == null ) return;
    ParamList1.add(ValueCtrl);
  }
  // 获取值控制
  public JTextField GetParamValueCtrl(String ParamName) {
      if (ParamList == null)
          return null;
      return (JTextField) ParamList.get(ParamName);
  }

  // 获取参数列表
  public Vector getParam() {
    return com.pub.util.StringFunction.convertFromStringToStringVectorByString(param,",");
  }
  // 获取数据对象列表
  public Vector getDObj() {
    return com.pub.util.StringFunction.convertFromStringToStringVectorByString(dobj,",");
  }
  // 获取条件对象列表
  public Vector getCObj() {
    return com.pub.util.StringFunction.convertFromStringToStringVectorByString(cobj,",");
  }
  // 返回完整的函数
  public String getFunctionString() {
    String FString = null,Param;JTextField VC;
    Vector PList = getParam();String Value;
    FString = id+"(";
    for(int i=0;i<PList.size();i++) {
      Param = (String)PList.get(i);
      VC = (JTextField)ParamList.get(Param);
      Value = VC.getText();
      if ( Value != null && Value.trim().length() == 0 ) Value = null;
      if ( Value == null ) Value = "0";
      FString += Value+",";
    }
    FString = FString.substring(0,FString.length()-1);
    FString += ")";
    return FString;
  }

    public String getCobj() {
        return cobj;
    }

    public String getDes1() {
        return des1;
    }

    public String getDes2() {
        return des2;
    }

    public String getDobj() {
        return dobj;
    }

    public String getId() {
        return id;
    }

    public int getPs() {
        return ps;
    }

    public String getText() {
        return text;
    }

    public Vector getParamList1() {
        return ParamList1;
    }

    public Hashtable getParamList() {
        return ParamList;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFunctionString(String functionString) {
        this.functionString = functionString;
    }

    public void setDes2(String des2) {
        this.des2 = des2;
    }

    public void setDes1(String des1) {
        this.des1 = des1;
    }

    public void setCobj(String cobj) {
        this.cobj = cobj;
    }

    public void setDObj(Vector DObj) {
        this.DObj = DObj;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setDobj(String dobj) {
        this.dobj = dobj;
    }

    public void setParamList(Hashtable ParamList) {
        this.ParamList = ParamList;
    }

    public void setParamList1(Vector ParamList1) {
        this.ParamList1 = ParamList1;
    }
}
