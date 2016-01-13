package com.efounder.bz.dbform.datamodel;
/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface DMColComponent extends DataModelComponent {
  // ��ݼ����
//  public DataSetComponent getDataSetComponent();
  // ��ݼ�ID
  public String getDataSetID();
  // ��ݼ�ColID
  public String getDataSetColID();
  // ������ݼ�ColID
  public void setDataSetColID(String dataSetColID);
  // �ڲ���ݼ�ID
  public String getInternalDataSetID();
  // �����ڲ���ݼ�ID
  public void setInternalDataSetID(String dataSetID);
  // �Ƿ�Ԫ���
  public boolean getMetaData();
  // �����Ƿ�Ԫ���
  public void setMetaData(boolean v);
  // ��ʾ��ݼ�
  public void   setViewDataSetID(String viewDataSetID);
  public String getViewDataSetID();
  //����ʾ��ݼ���
  public void   setViewDataSetColID(String viewDataSetColID);
  //
  public String getViewDataSetColID();
  // 取值字段
  public void setValueDataSetColID(String valueDataSetColID);
  //
  public String getValueDataSetColID();
  //
  public boolean isUserInternalDataSetID();
  //
  public void setUserInternalDataSetID(boolean v);
  //
  public String getFkeyID();
  //
  public void setFkeyID(String fkey);
  //
  public String getRlglID();
  //
  public void setRlglID(String rlglID);
  /**
   *
   * @return int
   */
  public int getHorizontalAlignment();
  /**
   *
   * @param horizontalAlignment int
   */
  public void setHorizontalAlignment(int horizontalAlignment);
  /**
   *
   * @return String
   */
  public String getNumberFormat();
  /**
   *
   * @param numberFormat String
   */
  public void setNumberFormat(String numberFormat);
  /**
   *
   * @return String
   */
  public String getDateFormat();
  /**
   *
   * @param dateFormat String
   */
  public void setDateFormat(String dateFormat);
  /**
   *
   * @return String
   */
  public String getFormulaOne();
  /**
   *
   * @param formulaOne String
   */
  public void setFormulaOne(String formulaOne);
  
  /**
  *
  * @return String
  */
 public String getEditMask();
 /**
  *
  * @param formulaOne String
  */
 public void setEditMask(String editMask);
  
}
