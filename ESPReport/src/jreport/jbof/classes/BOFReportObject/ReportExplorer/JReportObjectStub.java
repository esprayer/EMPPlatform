package jreport.jbof.classes.BOFReportObject.ReportExplorer;

import java.util.*;
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
public class JReportObjectStub {
static ResourceBundle res = ResourceBundle.getBundle("jreport.jbof.classes.BOFReportObject.ReportExplorer.Language");
    public static final int STUB_TYPE_YEAR   = 0;
    public static final int STUB_TYPE_MONTH  = 1;
    public static final int STUB_TYPE_REPORT = 2;
    public static final int STUB_TYPE_ROOT   = 3;
    public static final int STUB_TYPE_LBZD   = 4;
    public static final int STUB_TYPE_DWZD   = 5;
    public static final int STUB_TYPE_BMZD   = 6;
    public static final int STUB_TYPE_CBZD   = 7;
    public static final int STUB_TYPE_GFBB   = 8;
    public String  IS_GFBB  = "0";
    public String  TZZD_ORDE  = null;
    public String  TZZD_MC  = null;
    public String  BBZD_BH    = null;
    public String  BBZD_MC    = null;
    public String  ADD_BH     = null;
    public String  ADD_MC     = null;
    public String  ADD_JC     = null;
    public String  BBZD_DATE  = null;
    public String  BBZD_YEAR  = null;
    public String  BBZD_MONTH = null;
    public String  BBZD_SBBH  = null;
    public String  BBZD_FCBZ  = null;  //����־
    public String  BBZD_XF    = null;  //�·���־
    public String  F_JS       = null;
    public String  F_MX       = null;
    public boolean F_BBFC     = false; // ������
    public boolean F_GSFC     = false; // ��ʽ���
    public boolean F_LOCK     = false; // ����
    public boolean F_BBWC     = false; // ���
    public boolean F_BBXF     = false; // �·�
    public String  F_ZGXM     = "";    // �����
    public String  F_WCRQ     = "";    // �������
    public String  BBZD_SBXZ  = "";
    public String  F_BBXZ     = "";    // �ϱ�����
    public String  BBZD_LX    = "";
//  public boolean F_BBYJ     = false; // �½�
    public boolean F_BBSB     = false; //�ϱ�
    public boolean F_BBJY     = false; //У��
    public boolean F_NULL     = false; //������
    public Object  AddObject  = null;
    public int     STUB_TYPE  = STUB_TYPE_REPORT;
    public Vector  NextROS    = null;
    public JReportObjectStub  Parent;

    public void addNextStub(JReportObjectStub ros) {
        if (NextROS == null)
            NextROS = new Vector();
        NextROS.add(ros);
        ros.Parent = this;
    }

    public void setNextList(Vector v) {
        NextROS = v;
    }

    public Vector getNextList() {
        return NextROS;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public JReportObjectStub(int Type) {
        STUB_TYPE = Type;
    }

    public JReportObjectStub() {
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�: �����У���12���¸�Ϊ16���ڼ�2007.1.16
    //------------------------------------------------------------------------------------------------
    public String toString() {
        switch (STUB_TYPE) {
            case STUB_TYPE_YEAR:
                return BBZD_YEAR + res.getString("String_6");
            case STUB_TYPE_MONTH:
                return BBZD_MONTH + res.getString("String_11");
            case STUB_TYPE_ROOT:
                return BBZD_MC;
            case STUB_TYPE_LBZD:
            case STUB_TYPE_DWZD:
            case STUB_TYPE_BMZD:
            case STUB_TYPE_CBZD:
                return ADD_BH + " " + ADD_MC;
            default:
                return BBZD_BH + "-" + BBZD_MC;
        }
    }
}
