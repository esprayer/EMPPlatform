package jreport.swing.classes.ReportBook;

import jreport.swing.classes.JReportModel;
import jreport.swing.classes.util.ReportUtil;
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
public class JColInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public String LZD_ORDE="";
  public int LZD_ZB=0;
  public String BZBM_BM="";
  public String LZD_MC="";
  public boolean LZD_SZ=true;// ��ֵ��
  public boolean LZD_HZ=true;// ������
  public boolean LZD_TZ=true;// ������
  public boolean LZD_XM=false;// ��Ŀ��
  public boolean LZD_PX=false;// ������
  public int ChangeLog=0; // �޸ı�־(0:û���޸Ĺ���1:�½���2:�޸Ĺ�)
  public JColInfo PrioCol=null;         // ��һ��
  public JColInfo NextCol=null;         // ��һ��
  public JUnitInfo UnitColHead=null;     // ����ĵ�Ԫ
  public JAddUnitInfo AddUnitColHead=null;  // �����ǵ�λ���ݻ��ǻ�������
  public JTzUnitInfo TzUnitColHead=null;  // �����ǵ�λ���ݻ��ǻ������ݵĵ���
  public JReportModel RM = null;
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JColInfo() {
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
  public String GetColName(JReportModel RM) {
    String ColText=null;
    ColText = LZD_MC;
    if ( ColText != null && ColText.trim().length() == 0 ) ColText = null;
    if ( ColText == null ) {
      try {
        ColText = RM.ReportView.getText(RM.BBZD_TITLE,LZD_ZB-1);
        LZD_MC = ColText;
        if ( LZD_MC == null || LZD_MC.trim().length() == 0 ) {
          ColText = ReportUtil.strID(LZD_ZB);
        }
        if ( LZD_MC != null && LZD_MC.trim().length() != 0 ) {
          setModified();
          RM.setModified();
        }
      }catch ( Exception e ) {

      }
    }
    return ColText;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String toString() {
    return GetColName(this.RM);
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JColInfo GetColInfoByZB(int ZB,JReportModel RM) {
    return GetColInfoByZB(ZB,0,RM);
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JColInfo GetColInfoByZB(int ZB,int Op,JReportModel RM) {
    JColInfo ColInfo=null;
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ZB == ZB ) return ColInfo;
      ColInfo = ColInfo.NextCol;
    }
    ColInfo = null;
    if ( Op == 1 ) { // �½�
      ColInfo = new JColInfo();
      /**
       * ������½��������ǵ�һ�У��Զ�����Ϊ��Ŀ��
       * add by hufeng 2006.8.21
       */
      if(ZB == 1){
        ColInfo.LZD_HZ = false;
        ColInfo.LZD_SZ = false;
        ColInfo.LZD_TZ = false;
        ColInfo.LZD_XM = true;
      }
      ColInfo.RM = RM;
      ColInfo.ChangeLog = 1; // �½�
      ColInfo.NextCol = RM.HeadColInfo.NextCol;
      if ( RM.HeadColInfo.NextCol != null ) RM.HeadColInfo.NextCol.PrioCol = ColInfo;
      RM.HeadColInfo.NextCol = ColInfo;
      ColInfo.PrioCol = RM.HeadColInfo;
      ColInfo.LZD_ZB = ZB;
      ColInfo.LZD_ORDE = String.valueOf(++RM.ColMaxOrde);
      RM.setModified();
    }
    return ColInfo;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JColInfo GetColInfoByOrde(String Orde,JReportModel RM) {
    JColInfo ColInfo=null;
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ORDE.equals(Orde) == true ) return ColInfo;
      ColInfo = ColInfo.NextCol;
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static void InsertCol(int SCol,int ECol,JReportModel RM) {
    JColInfo ColInfo = null;int C=Math.abs(SCol-ECol)+1;
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ZB >= SCol ) {
        ChangeColUnit(ColInfo);
        ColInfo.LZD_ZB += C;
        ColInfo.setModified();
      }
      ColInfo = ColInfo.NextCol;
    }
    for(int i=SCol;i<=ECol;i++)
      GetColInfoByZB(i,1,RM);
    return;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static void ChangeColUnit(JColInfo ColInfo) {
    JUnitInfo UI;
    UI = ColInfo.UnitColHead;
    while ( UI != null ) {
      UI.DYZD_EXGSX = null;
      UI.setModified();
      UI = UI.ColNextUnit;
    }
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static void DeleteCol(int SCol,int ECol,JReportModel RM) {
    JColInfo ColInfo = null,DelColInfo = null;int C=ECol-SCol+1;
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ZB >= SCol && ColInfo.LZD_ZB <= ECol ) {
        DelColInfo = ColInfo;
        ColInfo = ColInfo.NextCol;
        if ( DelColInfo.PrioCol != null ) {
          DelColInfo.PrioCol.NextCol = DelColInfo.NextCol;
        }
        if ( DelColInfo.NextCol != null ) {
          DelColInfo.NextCol.PrioCol = DelColInfo.PrioCol;
        }
        DelColInfo.NextCol = RM.DelHeadColInfo.NextCol;
        DelColInfo.PrioCol = RM.DelHeadColInfo;
        RM.DelHeadColInfo.NextCol = DelColInfo;
      } else {
        ColInfo = ColInfo.NextCol;
      }
    }
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ZB > ECol ) {
        ChangeColUnit(ColInfo);
        ColInfo.LZD_ZB -= C;
        ColInfo.setModified();
      }
      ColInfo = ColInfo.NextCol;
    }
    return;
  }
}
