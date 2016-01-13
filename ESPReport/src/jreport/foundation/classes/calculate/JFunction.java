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
public class JFunction implements IFunction {
  public static final int CALC_NESTING = 0x0001;// 需要嵌套检查
  public static final int CALC_REPORT  = 0x0002;// 报表函数
  public static final int CALC_COUNT   = 0x0004;// 汇总报表需要计算

  public static final int RESULTSET_NORMAL = 0;
  public static final int RESULTSET_REPORT = 1;
  public static final int RESULTSET_XM     = 2;
  public static final int RESULT_TYPE_UNDEF  = 0;
  public static final int RESULT_TYPE_NUMBER = 1;
  public static final int RESULT_TYPE_STRING = 2;
  protected int ResultType = RESULT_TYPE_UNDEF;
  // 函数返回值类型
  public int getResultType() {
    return ResultType;
  }
  // 设置函数返回值类型
  public void setResultType(int type) {
    ResultType = type;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JFunction() {
  }
  public int    GetResultSetType() {
    return RESULTSET_NORMAL;
  }
  // 获取函数错误信息
  public String getErrText() {
    return null;
  }
  // 获取函数附加信息
  public Object GetAddInfo() {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int    GetFunctionType() {
    return 0;
  }
  // 获取函数标识
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String GetFunctionID() {
    return null;
  }
  // 获取函数名称
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String GetFunctionName() {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
//  public JNestingAreaStub GetNestingArea(JFormulaStub F1S,String FunctionString,JParamObject PO,Object ConnObject) {
//    return null;
//  }
  public Object GetNestingArea(JFormulaStub F1S,JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject) {
	return null;
  }
  public void InitFunction(JFunctionStub FS) {

  }
  // 检查函数是否合法
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object IsValid(String FunctionString,JParamObject PO) {
    return null;
  }
  // 用户模式到存储模式的转换
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String UserModelToStoreModel(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject) {
    return "(Store)";
  }
  // 存储模式到用户模式的转换
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String StoreModelToUserModel(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject) {
    return "(User)";
  }
  // 计算该函数
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object CalculateFunction(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S) {
    return CalculateFunction(FS,FunctionString,PO,ConnObject,F1S,ConnObject);
  }
  public Object CalculateFunction(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S,Object RptConnObject) {
	return "(1)";
  }
  // 计算该函数返回的是XML字符串
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object CalculateFunctionXML(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S) {
    return null;
  }
  public void EndCalculate() {
    return;
  }
}
