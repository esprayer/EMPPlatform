package jreport.foundation.classes.calculate;

import com.efounder.eai.data.JParamObject;

import jreport.model.classes.calculate.*;
import jreport.swing.classes.wizard.JFunctionStub;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public interface IFunction {
  // 函数返回值类型
  public int getResultType();
  // 设置函数返回值类型
  public void setResultType(int type);
  // 获取返回的ResultSet类型
  public int    GetResultSetType();

  // 获取函数类型(包括是否需要进行嵌套检查)
  public int    GetFunctionType();
  // 获取函数标识
  public String GetFunctionID();
  // 获取函数名称
  public String GetFunctionName();
  // 获取函数错误信息
  public String getErrText();
  // 获取函数附加信息
  public Object GetAddInfo();

  public void InitFunction(JFunctionStub FS);
  // 返回嵌套区域
//  public JNestingAreaStub GetNestingArea(JFormulaStub F1S,String FunctionString,JParamObject PO,Object ConnObject);
  // 返回嵌套区域
  public Object GetNestingArea(JFormulaStub F1S,JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject);
  // 检查函数是否合法
  public Object IsValid(String FunctionString,JParamObject PO);
  // 用户模式到存储模式的转换
  public String UserModelToStoreModel(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject);
  // 存储模式到用户模式的转换
  public String StoreModelToUserModel(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject);
  // 计算该函数
  public Object CalculateFunction(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S);
  public Object CalculateFunction(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S,Object RptConnObject);
  // 计算该函数返回的是XML字符串
  public Object CalculateFunctionXML(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S);

  public void EndCalculate();
}
