package jreport.swing.classes.ReportBook;

import jreport.swing.classes.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
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
public class JJygsInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public static final String FROM_XTBB = "1"; //ϵͳ����ʽ
  public static final String FROM_DWBB  = "2"; //��λ����ʽ
  //
  public String JYGS_ORDE = "";
  public String JYGS_GSX  = "";
  public String JYGS_EXGSX  = "";
  public String JYGS_BJ   = "=";
  public String JYGS_SHOW = "";// ��������Ϣ
  public String JYGS_SYFW   = "";
  public int    JYGS_TYPE = 1;// 1�߼���ˡ�2������ˡ�3�߼�����4�������
  public int    JYGS_STYLE= 0;//  ��ʶ�˹�ʽ�Ƿ�Ϊ��˹�ʽ��2Ϊ��˹�ʽ
  public int JYGS_HOFFSET = 0;
  public int JYGS_LOFFSET = 0;
  /**
   * У�鹫ʽ��𣬣�ϵͳ����;
   * �������ֵ�ǰУ����Ϣ��ϵͳ����Ļ��ǵ�λ�����
   * add by hufeng 2005.11.17
   */
  public String JYGS_FROM   = ""; //
  // ���ڱ䶯����У�鹫ʽר��������
//  public int BD_OFFSET = 0;
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JJygsInfo() {
  }
  public static JJygsInfo CreateJygsInfo(JReportModel RM) {
    JJygsInfo JygsInfo = new JJygsInfo();
    JygsInfo.JYGS_ORDE = String.valueOf(++RM.JygsMaxOrde);
    return JygsInfo;
  }
  public String toString() {
    return JYGS_BJ+JYGS_EXGSX;
  }
}
