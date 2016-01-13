package jreport.foundation.classes.calculate;

import com.efounder.eai.data.JParamObject;

import jreport.model.classes.calculate.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����: �����Լ��,��Ҫ��������У����Ϣ
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public interface IPerformance {
  // ��ʼ��¼��Ϣ
  public void StartRecordJyInfo(JFormulaStub F1S,JCheckInfo CheckInfo);
  public void RecordJyInfo(JFormulaStub F1S,JCheckInfo CheckInfo);
  // ������Ϣ�ļ�¼
  public int  EndRecordJyInfo(Object stmt,JParamObject PO,JFormulaStub F1S,JCheckInfo CheckInfo,int Index);
  // ����У����Ϣ
  public long SaveJyInfo(Object stmt,JParamObject PO);
  // ����ϴ�У����Ϣ
  public void ClearJyInfo(Object stmt,JParamObject PO);
  public void SetPerformance(int Type);
  public int  GetPerformance();
}
