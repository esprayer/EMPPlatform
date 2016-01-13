package jreport.value;

import java.util.*;

public class JReportMask {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.value.Language");

    int mask;

    public static final int           OUT_BBGS = 0x01;   //��ʽ
    public static final int           OUT_JSGS = 0x02;   //���㹫ʽ
    public static final int           OUT_BZBM = 0x04;   //��׼����
    public static final int           OUT_BBSJ = 0x08;   //��������
    public static final int           OUT_JYGS = 0x10;   //У�鹫ʽ
    public static final int           OUT_XFBB = 0x20;   //�·�����
    public static final int           OUT_SRKZ = 0x40;   //�������
    public static final int           OUT_SHGS = 0x80;   //��˹�ʽ
    public static final int         OUT_FMSHGS = 0x100;  //������˹�ʽ
    public static final int           OUT_MJZD = 0x200;  //ö���ֵ�
    public static final int         OUT_JSGSTZ = 0x400; //���㹫ʽ����
    public static final int         OUT_JYGSTZ = 0x800; //У�鹫ʽ����

    public static final int            IN_BBGS = 0x01;   //�����ʽ
    public static final int            IN_BBYS = 0x02;   //������ʽ
    public static final int            IN_JSGS = 0x04;   //���㹫ʽ
    public static final int            IN_JYGS = 0x08;   //У�鹫ʽ
    public static final int            IN_SHGS = 0x10;   //��˹�ʽ
    public static final int          IN_FMSHGS = 0x20;   //������˹�ʽ
    public static final int            IN_TZGS = 0x30;   //������ʽ
    public static final int            IN_BZBM = 0x1000; //��׼����
    public static final int            IN_MJZD = 0x2000; //ö���ֵ�

    public static final int           DEL_BBGS = 0x01;   //ɾ����ʽ
    public static final int           DEL_BBYS = 0x02;   //ɾ����ʽ
    public static final int           DEL_JSGS = 0x04;   //ɾ�����㹫ʽ
    public static final int           DEL_JYGS = 0x08;   //ɾ��У�鹫ʽ
    public static final int           DEL_DWSJ = 0x10;   //ɾ����λ����
    public static final int           DEL_HZSJ = 0x20;   //ɾ����������
    public static final int           DEL_BMSJ = 0x30;   //ɾ����������
    public static final int           DEL_CBSJ = 0x40;   //ɾ���ɱ�����
    public static final int           DEL_SHGS = 0x50;   //ɾ����˹�ʽ
    public static final int         DEL_FMSHGS = 0x80;   //ɾ��������˹�ʽ
    public static final int           DEL_DWJS = 0x100;  //ɾ����λ��ʽ
    public static final int           DEL_DWJY = 0x200;  //ɾ����λУ�鹫ʽ
    public static final int           DEL_LBJS = 0x400;  //ɾ�����ʽ
    public static final int           DEL_LBJY = 0x800;  //ɾ�����У�鹫ʽ
    public static final int           DEL_BMJS = 0x300;  //ɾ�����Ź�ʽ
    public static final int           DEL_BMJY = 0x600;  //ɾ������У�鹫ʽ
    public static final int           DEL_CBJS = 0x2000; //ɾ���ɱ���ʽ
    public static final int           DEL_CBJY = 0x4000; //ɾ���ɱ�У�鹫ʽ
    public static final int           DEL_MJZD = 0x10000; //ɾ��ö���ֵ�(�����ɾ������ʱ��ɾ��ö���ֵ������)

    public static final int       DATAOUT_DATA = 0x01; //����
    public static final int        DATAOUT_DOC = 0x02; //�����ĵ�
    public static final int       DATAOUT_FMDM = 0x04; //�������

    public static final int         DATAIN_DOC = 0x02; //�������
    public static final int        DATAIN_FMDM = 0x04; //�������
    public static final int        ENCRPT_FILE = 0x10; //�����ļ�
    public static final int    NOT_ENCRPT_FILE = 0x20; //�Ǽ����ļ�

    public static final int         GSOUT_JSGS = 0x01; //���㹫ʽ
    public static final int         GSOUT_JYGS = 0x02; //У�鹫ʽ   

    public static final int          GSIN_JSGS = 0x01; //���㹫ʽ
    public static final int          GSIN_JYGS = 0x02; //У�鹫ʽ

    public static final int           DATA_OUT = 1; //����ת��
    public static final int             GS_OUT = 2; //��ʽת��

    public static final int          CTCX_LBJS = 0x01; //��͸�����㹫ʽ
    public static final int          CTCX_DWJS = 0x02; //��͸��λ���㹫ʽ
    public static final int          CTCX_BMJS = 0x04; //��͸���ż��㹫ʽ
    public static final int          CTCX_CBJS = 0x08; //��͸�ɱ����㹫ʽ
    public static final int          CTCX_LBJY = 0x10; //��͸���У�鹫ʽ
    public static final int          CTCX_DWJY = 0x20; //��͸��λУ�鹫ʽ
    public static final int          CTCX_BMJY = 0x40; //��͸����У�鹫ʽ
    public static final int          CTCX_CBJY = 0x80; //��͸�ɱ�У�鹫ʽ

    public static final String      CHAR_QUOTA = "\'\'";   //�ֵ䴮�ĵ����ţ�Ҫ��Ϊchar(39),�Ա�֧�ֶ����ݿ�

    public static final String    GNQX_RPT_NEW = "RT1020"; // �½������ʽ
    public static final String GNQX_RPT_IMPROT = "RT1110"; // �����ʽת��

    public static final String    JY_TYPE_INNER  = "1";    // ����У��
    public static final String    JY_TYPE_OUTER  = "2";    // ���У��
    public static final String    JY_TYPE_ALL    = "3";    // ȫ��У��

    public static final String REPORT_CALC_ALL     = "1";    // �������к���
    public static final String REPORT_CALC_COUNT   = "2";    // ������ܺ���(�꺯��+������)
    public static final String REPORT_CALC_REPORT  = "3";    // ���㱨����
    public static final String REPORT_CALC_MACRO   = "4";    // ����꺯��
    public static final String REPORT_CALC_INNER   = "5";    // ���㱨�������ȡ����ʽ
	public static final String REPORT_CALC_ACCOUNT = "6";    // ����������

    public static final String RPT_CALC_SAP         = "RPT_CALC_SAP";     // �Ƿ����SAP����
    public static final String RPT_CALC_TIME        = "RPT_CALC_TIME";    // �Ƿ��̨�������ʱ����־

    public static final String CALC_UNIT_LOG        = "CALC_UNIT_LOG";    // �Ƿ���Ҫ��¼���˼�����־
    public static final String RPT_CHECK_LOG        = "RPT_CHECK_LOG";    // �ϱ�����У����־

    public static final String RPT_REMOTE_CALL       = "RPT_REMOTE_CALL";  // Զ�̵���

    public JReportMask() {
        mask = 0;
    }

    public JReportMask(int m) {
        mask = m;
    }

    public int getMask() {
        return mask;
    }

    public void restMask() {
    	mask = 0;
    }
    
    public boolean haveMask(int m) {
        return ( (mask & m) > 0 ? true : false);
    }

    public void setMask(int m) {
        mask |= m;
    }
}
