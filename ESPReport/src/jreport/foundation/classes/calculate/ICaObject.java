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
public interface ICaObject {
  void   BeginCalc(JFormulaStub F1S);
  void   EndCalc();
  String GetZB(int Row,int Col);
  public String CalcFunction(Object FunctionResult,JFormulaStub F1S,JFunctionStub FS,Object CustomObject,
                             String FunctionString,Object ConnObject,JParamObject PO);
  Object CalcFormulaOne(String FormulaOneString,JFormulaStub F1S,Object CustomObject);
  public Object GetObjectResult(int Index);
  public Object GetObjectResultAll(int Index);
  public void IncErrorCount(int Count);
  public int  GetErrorCount();
}
