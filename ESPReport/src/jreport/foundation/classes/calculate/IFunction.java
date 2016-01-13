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
//����:
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public interface IFunction {
  // ��������ֵ����
  public int getResultType();
  // ���ú�������ֵ����
  public void setResultType(int type);
  // ��ȡ���ص�ResultSet����
  public int    GetResultSetType();

  // ��ȡ��������(�����Ƿ���Ҫ����Ƕ�׼��)
  public int    GetFunctionType();
  // ��ȡ������ʶ
  public String GetFunctionID();
  // ��ȡ��������
  public String GetFunctionName();
  // ��ȡ����������Ϣ
  public String getErrText();
  // ��ȡ����������Ϣ
  public Object GetAddInfo();

  public void InitFunction(JFunctionStub FS);
  // ����Ƕ������
//  public JNestingAreaStub GetNestingArea(JFormulaStub F1S,String FunctionString,JParamObject PO,Object ConnObject);
  // ����Ƕ������
  public Object GetNestingArea(JFormulaStub F1S,JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject);
  // ��麯���Ƿ�Ϸ�
  public Object IsValid(String FunctionString,JParamObject PO);
  // �û�ģʽ���洢ģʽ��ת��
  public String UserModelToStoreModel(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject);
  // �洢ģʽ���û�ģʽ��ת��
  public String StoreModelToUserModel(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject);
  // ����ú���
  public Object CalculateFunction(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S);
  public Object CalculateFunction(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S,Object RptConnObject);
  // ����ú������ص���XML�ַ���
  public Object CalculateFunctionXML(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S);

  public void EndCalculate();
}
