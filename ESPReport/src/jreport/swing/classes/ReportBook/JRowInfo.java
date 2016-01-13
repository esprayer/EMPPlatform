package jreport.swing.classes.ReportBook;

import jreport.swing.classes.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
public class JRowInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public String HZD_ORDE="";
  public int    HZD_ZB=0;
  public int    UP_BDHS=0;
  public String BZBM_BM="";
  public int    HZD_PRO = 0;// �Ƿ��Ǳ䶯��������
  public int    ChangeLog=0;// �޸ı�־(0:û���޸Ĺ���1:�½���2:�޸Ĺ�)
  public JRowInfo      PrioRow=null;
  public JRowInfo      NextRow=null;
  public JUnitInfo     UnitRowHead=null;
  public JAddUnitInfo  AddUnitRowHead=null;
  public JTzUnitInfo  TzUnitRowHead=null;
  // �����鱣��䶯������
  private java.util.ArrayList BdhDataList = null;// ������ж����˱䶯�У��򱣴�䶯������
  private ArrayList BackBdhDataList = null;
  // ��������䶯�ж���
  public JBdhInfo      BdhInfo = null; // �䶯����Ϣ�Ķ���

  public ArrayList getBdhDataList() {
    return BdhDataList;
  }
  public void setNewBDH() {
    HZD_PRO = 1;
    BdhDataList = new ArrayList();
    setModified();
  }
  public void backBDH() {
    BackBdhDataList = BdhDataList;
    BdhDataList = null;
  }
  public void restoreBDH() {
    BdhDataList = BackBdhDataList;
    BackBdhDataList = null;
  }
  public void delNewBDH() {
    HZD_PRO = 0;
    BackBdhDataList = null;
    BdhDataList = null;
    setModified();
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JRowInfo() {
  }
  public void setModified() {
    if ( ChangeLog != 1 ) ChangeLog = 2;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JRowInfo GetRowInfoByZB(int ZB,JReportModel RM) {
    return GetRowInfoByZB(ZB,0,RM);
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JRowInfo GetRowInfoByZB(int ZB,int Op,JReportModel RM) {
    JRowInfo RowInfo=null;
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ZB == ZB ) return RowInfo;
      RowInfo = RowInfo.NextRow;
    }
    RowInfo = null;
    if ( Op == 1 ) { // �½�
      RowInfo = new JRowInfo();
      RowInfo.ChangeLog = 1; // �½�
      RowInfo.NextRow = RM.HeadRowInfo.NextRow;
      if ( RM.HeadRowInfo.NextRow != null ) RM.HeadRowInfo.NextRow.PrioRow = RowInfo;
      RM.HeadRowInfo.NextRow = RowInfo;
      RowInfo.PrioRow = RM.HeadRowInfo;
      RowInfo.HZD_ZB = ZB;
      RowInfo.HZD_ORDE = String.valueOf(++RM.RowMaxOrde);
      RM.setModified();
    }
    return RowInfo;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JRowInfo GetRowInfoByOrde(String Orde,JReportModel RM) {
    JRowInfo RowInfo=null;
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ORDE.equals(Orde) == true ) return RowInfo;
      RowInfo = RowInfo.NextRow;
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static void InsertRow(int SRow,int ERow,JReportModel RM) {
    JRowInfo RowInfo = null;int R=ERow-SRow+1;
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ZB >= SRow ) {
        ChangeRowUnit(RowInfo);
        RowInfo.HZD_ZB += R;
        RowInfo.setModified();
      }
      RowInfo = RowInfo.NextRow;
    }
    for(int i=SRow;i<=ERow;i++)
      GetRowInfoByZB(i,1,RM);
    return;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static void ChangeRowUnit(JRowInfo RowInfo) {
    JUnitInfo UI;
    UI = RowInfo.UnitRowHead;
    while ( UI != null ) {
      UI.DYZD_EXGSX = null;
      UI.setModified();
      UI = UI.RowNextUnit;
    }
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static void DeleteRow(int SRow,int ERow,JReportModel RM) {
    JRowInfo RowInfo = null,DelRowInfo = null;int R=ERow-SRow+1;
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ZB >= SRow && RowInfo.HZD_ZB <= ERow ) {
        DelRowInfo = RowInfo;
        RowInfo = RowInfo.NextRow;
        if ( DelRowInfo.PrioRow != null ) {
          DelRowInfo.PrioRow.NextRow = DelRowInfo.NextRow;
        }
        if ( DelRowInfo.NextRow != null ) {
          DelRowInfo.NextRow.PrioRow = DelRowInfo.PrioRow;
        }
        DelRowInfo.NextRow = RM.DelHeadRowInfo.NextRow;
        DelRowInfo.PrioRow = RM.DelHeadRowInfo;
        RM.DelHeadRowInfo.NextRow = DelRowInfo;
      } else {
        RowInfo = RowInfo.NextRow;
      }
    }
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ZB > ERow ) {
        ChangeRowUnit(RowInfo);
        RowInfo.HZD_ZB -= R;
        RowInfo.setModified();
      }
      RowInfo = RowInfo.NextRow;
    }
    return;
  }
}
