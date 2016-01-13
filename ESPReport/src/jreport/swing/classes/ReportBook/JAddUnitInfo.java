package jreport.swing.classes.ReportBook;

import jreport.swing.classes.JReportModel;

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
public class JAddUnitInfo extends JUnitInfo{
//  public static JRowInfo     HeadRowInfo = null;
//  public static JColInfo     HeadColInfo = null;

//  public String DYZD_ORDE="";
//  public String DYZD_SJ="";
//  public double DYZD_DATA=0.00;
//  public int ChangeLog=0;     // �޸ı�־(0:û���޸Ĺ���1:�½���2:�޸Ĺ�)
//  public JAddUnitInfo PrioUnit=null;
//  public JAddUnitInfo NextUnit=null;
//  public JAddUnitInfo RowPrioUnit=null;  // ͬһ�е���һ����Ԫ
//  public JAddUnitInfo RowNextUnit=null;  // ͬһ�е���һ����Ԫ
//  public JAddUnitInfo ColPrioUnit=null;  // ͬһ�е���һ����Ԫ
//  public JAddUnitInfo ColNextUnit=null;  // ͬһ�е���һ����Ԫ
//  public JRowInfo RowInfo=null;      // �е�ָ��
//  public JColInfo ColInfo=null;      // �е�ָ��
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JAddUnitInfo() {
  }
/*
    long                  ID;
    AnsiString            cData;     // �ַ��͵ĵ�Ԫ����
    double                nData;     // ��ֵ�͵ĵ�Ԫ����
    unsigned char         ChangeLog; // �޸ı�־(0:û���޸Ĺ���1:�½���2:�޸Ĺ�)
    long                  lResver;   // ����
    struct strAddUnitInfo *PrioUnit;
    struct strAddUnitInfo *NextUnit;
    struct strAddUnitInfo *RowPrioUnit;  // ͬһ�е���һ����Ԫ
    struct strAddUnitInfo *RowNextUnit;  // ͬһ�е���һ����Ԫ
    struct strAddUnitInfo *ColPrioUnit;  // ͬһ�е���һ����Ԫ
    struct strAddUnitInfo *ColNextUnit;  // ͬһ�е���һ����Ԫ
    void                  *RowInfo;   // �е�ָ��
    void                  *ColInfo;   // �е�ָ��
*/
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setModified() {
    if ( ChangeLog != 1 ) ChangeLog = 2;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByHL(int HZB,int LZB,JReportModel RM) {
    return GetUnitInfoByHL(HZB,LZB,0,RM);
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByHL(int HZB,int LZB,int Op,JReportModel RM) {
    JUnitInfo UnitInfo=null;JRowInfo RowInfo;JColInfo ColInfo;
    RowInfo = JRowInfo.GetRowInfoByZB(HZB,Op,RM);
    if ( RowInfo == null ) return null;
    ColInfo = JColInfo.GetColInfoByZB(LZB,Op,RM);
    if ( ColInfo == null ) return null;
    UnitInfo = RowInfo.AddUnitRowHead;
    while ( UnitInfo != null ) {
      if ( UnitInfo.ColInfo.LZD_ZB == LZB ) return UnitInfo;
      UnitInfo = UnitInfo.RowNextUnit;
    }
    if ( Op == 1 ) {
      UnitInfo = CreateUI(RowInfo,ColInfo);
      RM.setModified();
    }
    return UnitInfo;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static JAddUnitInfo CreateUI(JRowInfo RowInfo,JColInfo ColInfo) {
    JAddUnitInfo UnitInfo = new JAddUnitInfo();
    UnitInfo.ChangeLog = 1; // �½�
    UnitInfo.RowInfo = RowInfo;
    UnitInfo.ColInfo = ColInfo;
    // �������б�
    UnitInfo.RowNextUnit = RowInfo.AddUnitRowHead;
    if ( RowInfo.AddUnitRowHead != null ) RowInfo.AddUnitRowHead.PrioUnit = UnitInfo;
    RowInfo.AddUnitRowHead = UnitInfo;
    // �������б�
    UnitInfo.ColNextUnit = ColInfo.AddUnitColHead;
    if ( ColInfo.AddUnitColHead != null ) ColInfo.AddUnitColHead.PrioUnit = UnitInfo;
      ColInfo.AddUnitColHead = UnitInfo;
    return UnitInfo;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByOrde(String HOrde,String LOrde,int Op,JReportModel RM) {
    JUnitInfo UnitInfo=null;JRowInfo RowInfo;JColInfo ColInfo;
    RowInfo = JRowInfo.GetRowInfoByOrde(HOrde,RM);
    if ( RowInfo == null ) return null;
    ColInfo = JColInfo.GetColInfoByOrde(LOrde,RM);
    if ( ColInfo == null ) return null;
    UnitInfo = RowInfo.AddUnitRowHead;
    while ( UnitInfo != null ) {
      if ( UnitInfo.ColInfo.LZD_ORDE.equals(LOrde) ) return UnitInfo;
      UnitInfo = UnitInfo.RowNextUnit;
    }
    if ( Op == 1 ) {
      UnitInfo = CreateUI(RowInfo,ColInfo);
    }
    return UnitInfo;
  }
}
