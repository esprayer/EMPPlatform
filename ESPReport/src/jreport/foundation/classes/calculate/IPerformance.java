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
//描述: 用于性监测,主要用来生成校验信息
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public interface IPerformance {
  // 开始记录信息
  public void StartRecordJyInfo(JFormulaStub F1S,JCheckInfo CheckInfo);
  public void RecordJyInfo(JFormulaStub F1S,JCheckInfo CheckInfo);
  // 结束信息的记录
  public int  EndRecordJyInfo(Object stmt,JParamObject PO,JFormulaStub F1S,JCheckInfo CheckInfo,int Index);
  // 保存校验信息
  public long SaveJyInfo(Object stmt,JParamObject PO);
  // 清除上次校验信息
  public void ClearJyInfo(Object stmt,JParamObject PO);
  public void SetPerformance(int Type);
  public int  GetPerformance();
}
