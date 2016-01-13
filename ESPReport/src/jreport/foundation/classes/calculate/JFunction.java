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
public class JFunction implements IFunction {
  public static final int CALC_NESTING = 0x0001;// ��ҪǶ�׼��
  public static final int CALC_REPORT  = 0x0002;// ������
  public static final int CALC_COUNT   = 0x0004;// ���ܱ�����Ҫ����

  public static final int RESULTSET_NORMAL = 0;
  public static final int RESULTSET_REPORT = 1;
  public static final int RESULTSET_XM     = 2;
  public static final int RESULT_TYPE_UNDEF  = 0;
  public static final int RESULT_TYPE_NUMBER = 1;
  public static final int RESULT_TYPE_STRING = 2;
  protected int ResultType = RESULT_TYPE_UNDEF;
  // ��������ֵ����
  public int getResultType() {
    return ResultType;
  }
  // ���ú�������ֵ����
  public void setResultType(int type) {
    ResultType = type;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JFunction() {
  }
  public int    GetResultSetType() {
    return RESULTSET_NORMAL;
  }
  // ��ȡ����������Ϣ
  public String getErrText() {
    return null;
  }
  // ��ȡ����������Ϣ
  public Object GetAddInfo() {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public int    GetFunctionType() {
    return 0;
  }
  // ��ȡ������ʶ
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String GetFunctionID() {
    return null;
  }
  // ��ȡ��������
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String GetFunctionName() {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
//  public JNestingAreaStub GetNestingArea(JFormulaStub F1S,String FunctionString,JParamObject PO,Object ConnObject) {
//    return null;
//  }
  public Object GetNestingArea(JFormulaStub F1S,JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject) {
	return null;
  }
  public void InitFunction(JFunctionStub FS) {

  }
  // ��麯���Ƿ�Ϸ�
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object IsValid(String FunctionString,JParamObject PO) {
    return null;
  }
  // �û�ģʽ���洢ģʽ��ת��
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String UserModelToStoreModel(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject) {
    return "(Store)";
  }
  // �洢ģʽ���û�ģʽ��ת��
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String StoreModelToUserModel(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject) {
    return "(User)";
  }
  // ����ú���
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CalculateFunction(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S) {
    return CalculateFunction(FS,FunctionString,PO,ConnObject,F1S,ConnObject);
  }
  public Object CalculateFunction(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S,Object RptConnObject) {
	return "(1)";
  }
  // ����ú������ص���XML�ַ���
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CalculateFunctionXML(JFunctionStub FS,String FunctionString,JParamObject PO,Object ConnObject,JFormulaStub F1S) {
    return null;
  }
  public void EndCalculate() {
    return;
  }
}
