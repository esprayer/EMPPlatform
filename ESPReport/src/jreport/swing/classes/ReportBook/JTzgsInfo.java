package jreport.swing.classes.ReportBook;

import java.util.ResourceBundle;
import jreport.swing.classes.JReportModel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JTzgsInfo {
  static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public static final String FROM_XTBB = "1"; //ϵͳ����ʽ
  //
  public String TZGS_BBBH = "";
  public String TZGS_GSX  = "";
  public String TZGS_EXGSX  = "";
  public String TZGS_DATE  = ""; //�����ڼ�
  public String TZGS_SHOW = "";// ��������Ϣ
  public int TZGS_HZD = 0;//������
  public int TZGS_LZD = 0;//������
  /**
   * У�鹫ʽ��𣬣�ϵͳ����;
   * �������ֵ�ǰУ����Ϣ��ϵͳ����Ļ��ǵ�λ�����
   * add by hufeng 2005.11.17
   */
  public String TZGS_FROM   = ""; //
  // ���ڱ䶯����У�鹫ʽר��������
//  public int BD_OFFSET = 0;
  //------------------------------------------------------------------------------------------------
  //����:
  //���: mengfei(2009.11.16)
  //ʵ��: mengfei
  //�޸�:
  //------------------------------------------------------------------------------------------------

  public JTzgsInfo() {
  }
  public static JTzgsInfo CreateTzgsInfo(JReportModel RM) {
   JTzgsInfo TzgsInfo = new JTzgsInfo();
   TzgsInfo.TZGS_BBBH = String.valueOf(++RM.TzgsMaxOrde);
   return TzgsInfo;
 }
 public String toString() {
   return TZGS_GSX+TZGS_EXGSX;
 }

}
