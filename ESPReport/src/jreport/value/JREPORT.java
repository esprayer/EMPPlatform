package jreport.value;

import java.util.*;

import jfoundation.bridge.classes.*;
import jframework.foundation.classes.*;
import jreport.swing.classes.*;
import javax.swing.JOptionPane;
import java.util.ResourceBundle;

import jreport.jdal.classes.DALReportObject.JReportService;
import jreport.model.classes.calculate.JFormulaStub;

import com.client.fwkview.FMISContentWindow;
import com.core.xml.StubObject;
import com.efounder.buffer.DictDataBuffer;
import com.efounder.buffer.DataBuffer;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.sql.JConnection;

import java.sql.*;
import com.core.xml.PackageStub;
import jbof.gui.window.classes.JBOFChildWindow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JREPORT
        implements java.io.Serializable {
        static ResourceBundle res = ResourceBundle.getBundle("jreport.value.Language");

    private static     int        MAX_KJQJ    = 0;     //������ڼ�\uFFFD
    private static     int        MAX_RPTLEN  = 8;     //��󱨱��ų���\uFFFD
    public  static     int        MAX_DECN    = 6;     //��������󾫶�\uFFFD
    public  static boolean        IS_INIT     = false; //�Ƿ��Ѿ���ʼ��
    private static boolean        isOldDb     = false; //�Ƿ����ϰ汾��ݿ�\uFFFD
    public static String          RPT_SPLIT   = "#@#@";

    private static final String   HZ          = "HZ";
    private static final String   RPT         = "RPT";
    private static final String   LC          = "LC";
    private static final String   DEFAULT_RPT = RPT;
    private static final String[] RPTS        = {HZ, RPT, LC};

    public static Hashtable       TableList   = new Hashtable();
	public static Hashtable       DwUnit   = new Hashtable();

    public JREPORT() {
    }

    //�ж��Ƿ��Ǿɵ���ݽ�\uFFFD add by fsz 7.25 ��initReportTN����\uFFFD
    public static boolean isOldDbVer() {
        return isOldDb;
    }

    public static String getKjndName() {
        if (isOldDb)
            return "HZ_KJND";
        else
            return "RPT_KJND";
    }

    public static void setOldDbVer(boolean b) {
        isOldDb = b;
    }

    public static void putReportTable(String TableName) {
        String sTN = null;
        for (int i = 0; i < RPTS.length; i++) {
            if (TableName.startsWith(RPTS[i])) {
                sTN = TableName.substring(RPTS[i].length());
                TableList.put(sTN, TableName);
                break;
            }
        }
    }

    /**
     * ȡ�������\uFFFD
     * @param TableName String
     * @return String
     */
    public static String getRTN(String TableName) {
        String o = (String) TableList.get(TableName);
        if (o == null)
            o = DEFAULT_RPT + TableName;
        return o;
    }

    /**
     * ȡ�������\uFFFD
     * @param TableName String
     * @param BH String
     * @return String
     */
/*
	public static String getRTNbyBH(String TableName,String BH,JParamObject PO) {
            JConnection conn = JReportService.getConnection(PO);
			String sRes = "";
            try{
                sRes = getRTNbyBH(TableName, BH, conn);
            }
            catch(Exception e ){e.printStackTrace();}
            finally{
				conn.close();
				return sRes ;
            }

         //   return getRTNbyBH(TableName,BH,JReportService.getConnection(PO));
	}
*/
	public static String getRTNbyBH(String TableName,String BH,JConnection conn) {
		Statement st = conn.createStatement();
		String asRes = "";
		try {
			asRes = getRTNbyBH(TableName, BH, st);
		} catch (Exception ex ) {
			conn.BackStatement(st,null);
		} finally {
			conn.BackStatement(st,null);
		}
		return asRes;
	}
	public static String getRTNbyBH(String TableName,String BH,Statement st) {
		String sUnit="";
		String sRes = TableName;
		String sTemp;
		DataBuffer dbf ;
		String table ;
		JParamObject PO = JParamObject.Create() ;
		if (TableName.toUpperCase().equals("DWSJ") ||
			TableName.toUpperCase().equals("DWBDSJ"))
			table = JREPORT.getRTNbyPO("DWZD", PO);
		else if (TableName.toUpperCase().equals("BMSJ") ||
				 TableName.toUpperCase().equals("BMBDSJ"))
			table = JREPORT.getRTNbyPO("BMZD", PO);
		else if (TableName.toUpperCase().equals("CBSJ") ||
				 TableName.toUpperCase().equals("CBBDSJ"))
			table = JREPORT.getRTNbyPO("CBZD", PO);
		else if (TableName.toUpperCase().equals("LBSJ") ||
				 TableName.toUpperCase().equals("LBBDSJ"))
			table = JREPORT.getRTNbyPO("LBZD", PO);
		else
			return null;
		try {
			Object obj = DwUnit.get(TableName + "_" + BH);
			if (obj != null) {
				sUnit = (String) obj;
			}
			else {
				ResultSet rs = null;
				String sqlStr = "select ltrim(UNIT_ID) from " + table +
					" where " +
					TableName.substring(0, 2) + "ZD_BH = '" + BH + "'"+
					" and exists ( select 1 from RPTUNIT where RPTUNIT.UNIT_ID = "+table+".UNIT_ID )";
				rs = st.executeQuery(sqlStr);
				while (rs.next()) {
					sTemp = rs.getString(1);
					if (sTemp == null)
						sUnit = "NULL";
					else
					    sUnit = sTemp;
					DwUnit.put(TableName + "_" + BH, sUnit);
				}
			}
			if (sUnit.equals("NULL") || sUnit.equals("") || sUnit.equals(" "))
				sRes = DEFAULT_RPT + TableName;
			else
				sRes = DEFAULT_RPT + TableName + "_T_" + sUnit;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			return sRes;
		}
	}

	public static String getRTNbyZrzx(String TableName,String zrzx,Statement st) {
		String sUnit="";
		String sRes = TableName;
		String sTemp;
		DataBuffer dbf ;
		String table ;
		JParamObject PO = JParamObject.Create() ;
		if (TableName.toUpperCase().equals("DWSJ") ||
			TableName.toUpperCase().equals("DWBDSJ"))
			table = JREPORT.getRTNbyPO("DWZD", PO);
		else if (TableName.toUpperCase().equals("BMSJ") ||
				 TableName.toUpperCase().equals("BMBDSJ"))
			table = JREPORT.getRTNbyPO("BMZD", PO);
		else if (TableName.toUpperCase().equals("CBSJ") ||
				 TableName.toUpperCase().equals("CBBDSJ"))
			table = JREPORT.getRTNbyPO("CBZD", PO);
		else if (TableName.toUpperCase().equals("LBSJ") ||
				 TableName.toUpperCase().equals("LBBDSJ"))
			table = JREPORT.getRTNbyPO("LBZD", PO);
		else
			return null;
		try {
				ResultSet rs = null;
				String sqlStr = "select ltrim(UNIT_ID) from " + table +
					" where DWZD_CODE = '" + zrzx + "'"+
					" and DWZD_BH like '1%'"+
					" and exists ( select 1 from RPTUNIT where RPTUNIT.UNIT_ID = "+table+".UNIT_ID )";
				rs = st.executeQuery(sqlStr);
				while (rs.next()) {
					sTemp = rs.getString(1);
					if (sTemp == null)
						sUnit = "NULL";
					else
					    sUnit = sTemp;
//					DwUnit.put(TableName + "_" + BH, sUnit);
				}
			if (sUnit.equals("NULL") || sUnit.equals("") || sUnit.equals(" "))
				sRes = DEFAULT_RPT + TableName;
			else
				sRes = DEFAULT_RPT + TableName + "_T_" + sUnit;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			return sRes;
		}
	}


//    public static String getRTNbyBH(String TableName,String BH) {
//		JParamObject PO = JParamObject.Create();
//
//		PO.SetValueByParamName("TableName",TableName);
//		PO.SetValueByParamName(TableName,"TableName");
//		PO.SetValueByParamName("BH",BH);
//		PO.SetValueByParamName(BH,"BH");
//		JResponseObject RO = (JResponseObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "getUNIT", PO, null);
//		return (String) RO.ResponseObject ;
///*
////        String o = (String) TableList.get(TableName);
//        JParamObject PO = JParamObject.Create();
// //       String ISFBKG = PO.GetValueByParamName("ISFBKG","");
//		String sUnit="";
//		String sRes = TableName;
//		DataBuffer dbf ;
//		String table ;
//		if ( TableName.toUpperCase().equals("DWSJ")||TableName.toUpperCase().equals("DWBDSJ"))
//                table = JREPORT.getRTNbyPO("DWZD",PO);
//                else if ( TableName.toUpperCase().equals("BMSJ")||TableName.toUpperCase().equals("BMBDSJ"))
//                table = JREPORT.getRTNbyPO("BMZD",PO);
//                else if ( TableName.toUpperCase().equals("CBSJ")||TableName.toUpperCase().equals("CBBDSJ"))
//                table = JREPORT.getRTNbyPO("CBZD",PO);
//                else if ( TableName.toUpperCase().equals("LBSJ")||TableName.toUpperCase().equals("LBBDSJ"))
//                table = JREPORT.getRTNbyPO("LBZD",PO);
//                else return null ;
//	try{
//        JConnection jconn = null;
//        ResultSet rs = null;
//        Statement st = null;
//        jconn = getDBConnection(PO);
//        String sqlStr = "select UNIT_ID from " + table + " where DWZD_BH = '" + BH + "'";
//		rs = st.executeQuery(sqlStr);
//		while(rs.next()){
//			sUnit = rs.getString(1);
//		}
//		if(sUnit == null || sUnit.equals(""))
//			sRes = DEFAULT_RPT + TableName ;
//		else
//			sRes = DEFAULT_RPT+TableName+"_T_"+sUnit;
//    }
//	catch(Exception e){e.printStackTrace();}
//        finally{
//			return sRes ;
//		}
//	*/
//        //rs =
//
////                dbf = DictDataBuffer.getDefault().getDataBuffer(table);
////
////		if (dbf == null) {
////			java.util.Map data = ESPStandardClientUtil.getDictDataMap(table);
////			DictDataBuffer.getDefault().addDictDataMap(table, data);
////			dbf = DictDataBuffer.getDefault().getDataBuffer(table);
////		}
////		if (dbf != null) {
////			StubObject so = (StubObject) dbf.getData(BH);  //ͨ�����������ֵ�����ҵ�ʹ�õ�λ��ţ�Ȼ����Ϣ���뵽ʹ�õ�λ��ر���
////			if (so != null)
////				sUnit = so.getString("UNIT_ID", "");
////			    if(sUnit == null || sUnit.equals(""))
////					sRes = DEFAULT_RPT + TableName ;
////				else
////					sRes = DEFAULT_RPT+TableName+"_T_"+sUnit;
////		}
//
//
////        if("1".equals(ISFBKG)) {
////          if (o == null){
////            o = DEFAULT_RPT + TableName+"_"+BH;
////          }else{
////            o= o+"_"+BH;
////          }
////        }else{
////           if (o == null)
////             o = DEFAULT_RPT + TableName;
////        }
////        return sRes;
//    }
	private static JConnection getDBConnection(JParamObject PO) throws Exception {
	//ȡ���ݿ�����
	JConnection conn = JReportService.getConnection(PO);
	if (conn == null) {
		throw new Exception("��ȡ���ݿ�����ʧ�ܣ�");
	}
	return conn;
}

    /**
     * ȡ�������\uFFFD
     * @param TableName String
     * @param BH String
     * @return String
     */
    public static String getRTNbyPO(String TableName,JParamObject PO) {
/*
        String o = (String) TableList.get(TableName);
        if(PO == null){
          PO = JParamObject.Create();
        }
          String UNITID = PO.GetValueByParamName("UNIT_ID","");
          if(UNITID.trim().length()>0) {
            if (o == null){
              o = DEFAULT_RPT + TableName+"_T_"+UNITID;
            }else{
              o= o+"_T_"+UNITID;
            }
          }else{
            if (o == null)
              o = DEFAULT_RPT + TableName;
          }
        return o;
 */
         return DEFAULT_RPT+TableName;
      }
    /**
     * ��ʼ�������б�
     * @param tl Hashtable
     */
    public static void initReportTableName(Hashtable tl) {
        TableList = tl;
    }

    /**
     * ȡĬ�ϵ�ǰ׺
     * @return String
     */
    public static String getDefaultRpt() {
        return DEFAULT_RPT;
    }

    /**
     * ����Ȩ��Ҳ��Ϊ��̬����
     * @param String
     * @return String
     * add by hufeng 2005.10.27
     */
    public static String getQxName(String qxbh) {
		String sQxbh= "";
		if ( qxbh.equals("BBQX") ) {
			sQxbh = "BBZD";
		} else if ( qxbh.equals("DWQX") ) {
			sQxbh = "DWZD";
		} else if ( qxbh.equals("BMQX") ) {
			sQxbh = "BMZD";
		} else if ( qxbh.equals("CBQX") ) {
			sQxbh = "CBZD";
		} else if ( qxbh.equals("LBQX") ) {
			sQxbh = "LBZD";
		}
        if (isOldDb)
            return "HZ" + sQxbh;
        else
            return "RPT" + sQxbh;
    }

    /**
     * ��ݱ������ȡ��Ӧ�ı���
     * @param name String   ������,��:GS,JY
     * @param stubType int  �������\uFFFD
     * @return String
     */
    public static String getTableNameByStub(String name, int stubType) {
        String table = "";
        switch (stubType) {
            case JReportModel.STUB_TYPE_LBZD: //���ܱ���
                table = "LB";
                break;
            case JReportModel.STUB_TYPE_DWZD: //��λ����
                table = "DW";
                break;
            case JReportModel.STUB_TYPE_BMZD: //���ű���
                table = "BM";
                break;
            case JReportModel.STUB_TYPE_CBZD: //�ɱ�����
                table = "CB";
                break;
        }
        table += name;
        return getRTNbyPO(table,null);
    }
    /**
     * ��ݱ������ȡ��Ӧ�ı���
     * @param name String   ������,��:GS,JY
     * @param stubType int  �������\uFFFD
     * @return String
     */
//    public static String getTableNameByStub(String name, int stubType,String bh) {
//        String table = "";
//        switch (stubType) {
//            case JReportModel.STUB_TYPE_LBZD: //���ܱ���
//                table = "LB";
//                break;
//            case JReportModel.STUB_TYPE_DWZD: //��λ����
//                table = "DW";
//                break;
//            case JReportModel.STUB_TYPE_BMZD: //���ű���
//                table = "BM";
//                break;
//            case JReportModel.STUB_TYPE_CBZD: //�ɱ�����
//                table = "CB";
//                break;
//        }
//        table += name;
//		return null ;
//        //return getRTNbyBH(table,bh);
//    }
    public static String getTableNameByStub(String name, int stubType,
                                            String bh,Statement st) {
        String table = "";
        switch (stubType) {
            case JReportModel.STUB_TYPE_LBZD: //���ܱ���
                table = "LB";
                break;
            case JReportModel.STUB_TYPE_DWZD: //��λ����
                table = "DW";
                break;
            case JReportModel.STUB_TYPE_BMZD: //���ű���
                table = "BM";
                break;
            case JReportModel.STUB_TYPE_CBZD: //�ɱ�����
                table = "CB";
                break;
        }
        table += name;
		if ( bh == null ) return table;
        return getRTNbyBH(table, bh, st);
    }

    /**
     * ��ݱ������ȡ�ֵ�����
     * @param stubType int
     * @return String
     */
    public static String getColNameByStub(int stubType) {
        switch (stubType) {
            case JReportModel.STUB_TYPE_LBZD: //���ܱ���
                return "LBZD_BH";
            case JReportModel.STUB_TYPE_DWZD: //��λ����
                return "DWZD_BH";
            case JReportModel.STUB_TYPE_BMZD: //���ű���
                return "BMZD_BH";
            case JReportModel.STUB_TYPE_CBZD: //�ɱ�����
                return "CBZD_BH";
            default:
                return "";
        }
    }

    /**
     * ��ݱ������ȡ�ֵ�����
     * @param stubType int
     * @return String
     */
    public static String getColNameByStub(String suffix,int stubType) {
        switch (stubType) {
            case JReportModel.STUB_TYPE_LBZD: //���ܱ���
                return "LBZD_" + suffix;
            case JReportModel.STUB_TYPE_DWZD: //��λ����
                return "DWZD_" + suffix;
            case JReportModel.STUB_TYPE_BMZD: //���ű���
                return "BMZD_" + suffix;
            case JReportModel.STUB_TYPE_CBZD: //�ɱ�����
                return "CBZD_" + suffix;
            default:
                return "";
        }
    }

    /**
     * ��ݱ������ȡ���Ȩ��\uFFFD
     * @param stubType int
     * @return String
     */
    public static String getSjqxNameByStub(int stubType) {
        switch (stubType) {
            case JReportModel.STUB_TYPE_LBZD: //���ܱ���
                return getQxName("LBQX");
            case JReportModel.STUB_TYPE_DWZD: //��λ����
                return getQxName("DWQX");
            case JReportModel.STUB_TYPE_BMZD: //���ű���
                return getQxName("BMQX");
            case JReportModel.STUB_TYPE_CBZD: //�ɱ�����
                return getQxName("CBQX");
            default:
                return "";
        }
    }

    /**
     * ȡ������ڼ�\uFFFD
     * @return int
     */
    public static int getMaxKjqj(){
      if(MAX_KJQJ == 0){
        JParamObject PO = JParamObject.Create();
        MAX_KJQJ = 16;
//        MAX_KJQJ = Integer.parseInt(PO.GetValueByEnvName("CW_MAXQJ", "16"));
      }
      return MAX_KJQJ;
    }

    /**
     *
     */
    public static int getMaxRPTLength() {
        return MAX_RPTLEN;
    }

    /**
     * ���û���ڼ�\uFFFD
     */
    public static void reset(){
        MAX_KJQJ = 0;
    }


    /**
     * ���ϵͳ��û��RPT_KJND��
     * ���Զ���ʼ��Ϊ��ǰ�������\uFFFD
     * @return boolean
     */
    public static boolean initKjnd(String sSvrName){
        JParamObject PO = JParamObject.Create();
        /**
         * ����ͨ��PO�жϣ�����ֱ��ȥ��ݿ����ж�\uFFFD
         * modified by gengeng 2009-6-29
         */
        if (isExistsInBSCONF(sSvrName,getKjndName())) {
            return true;
        }
//        String kjnd = PO.GetValueByEnvName(getKjndName(), "");
//        //����Ѿ������򷵻�true
//        if (!"".equals(kjnd.trim())) {
//            return true;
//        }
        //�������������»�����
        String fiscalYear;
        JReportFiscalYearDialog yearDlg;
        yearDlg = new JReportFiscalYearDialog(JActiveDComDM.MainApplication.MainWindow, res.getString("String_40"), true);
        yearDlg.setVisible(true);
        if (yearDlg.getOption() == 0) {
            fiscalYear = yearDlg.getFiscalYear();
            if (fiscalYear == null)
                return false;
        } else {
            return false;
        }
        PO.SetValueByParamName("FiscalYear",fiscalYear);
		String rptSvr = sSvrName;
		if ( !rptSvr.equals("") ) {
			rptSvr += ".";
		}
        JResponseObject RO = null;
		try {
			RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(rptSvr+"ReportObject", "InitKjnd", PO);
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(RO.ErrorCode == -1){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,res.getString("String_44") + RO.ErrorString);
            return false;
        }
        String value = RO.ResponseObject.toString();
        try {
			JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject", "SetEnvValue", getKjndName(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return true;
    }
	public static String GetRealRptSvr() {
		String sSvrName = "";
		Object childwindow=com.efounder.eai.ide.EnterpriseExplorer.ContentView.getActiveWindow();
		if(childwindow instanceof FMISContentWindow) {
			childwindow = ( (FMISContentWindow) childwindow).getFMISComp();
			if ( childwindow instanceof JBOFChildWindow ) {
				sSvrName = ((JBOFChildWindow)childwindow).getPageBaseUrl();
			}
		}
		if ( !sSvrName.equals("") && sSvrName != null ) {
			return sSvrName + ".";
		} else {
			sSvrName = "";
		}
		return sSvrName;
	}

    public static boolean isExistsInBSCONF(String sSvrName,String keyName){
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("PARAM_NAME", keyName);
		String rptSvr = sSvrName;
		if ( !rptSvr.equals("") ) {
			rptSvr += ".";
		}
		JResponseObject RO = null;
		try {
			RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(rptSvr+"ReportObject", "isExistsInBSCONF", PO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (RO != null && RO.ErrorCode == 0) {
            Boolean b = (Boolean) RO.ResponseObject;
            return b.booleanValue();
        }
        return false;
    }

    /**
     * ���ز������ڣ����û�в������ڣ��򷵻ػ�����\uFFFD+��ǰ��¼����
     * @return String
     */
    public static String getCwrq() {
        JParamObject PO = JParamObject.Create();
        String kjqj = PO.GetValueByEnvName(getKjndName(), "");
        String date = PO.GetValueByEnvName("LoginDate", "");
        String cwrq = PO.GetValueByEnvName("ZW_LSCWRQ", "");
        if ("".equals(cwrq) && date.length() > 4)
            return kjqj + date.substring(4);
        else
            return cwrq;
    }

    /**
     * �ж��Ƿ���ĳ����Ȩ��
     * @param gnbh String
     * @return boolean
     */
    public static boolean checkGnqx(String gnbh) {
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("FunctionNo", gnbh);
//        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("SecurityObject", "CheckFunctionLimit", PO, "");
		JResponseObject RO = null;
		try {
			RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "CheckFunctionLimit", PO, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (RO != null && RO.GetErrorCode() == 0) {
			Integer in = (Integer) RO.ResponseObject;
			return in.intValue()>0;
		}
        return false;
    }

    /**
     * ȡ�ܲ�����PO
     * @return JParamObject
     */
    public static JParamObject getServerPO(){
		return null;
//        return JServiceManager.getPOByServer(JServiceManager.SV_REPORT);
    }

    /**
     * ����У��ѡ��
     * @param PO JParamObject
     */
    public static boolean setCheckType(JParamObject PO){
//        // �����ظ�����
//        String JyType = PO.GetValueByParamName("JyType","");
//        if(!JyType.trim().equals("")){
//            return true;
//        }
//        //
//        JReportCheckOptionDlg pDlg = new JReportCheckOptionDlg(JActiveDComDM.MainApplication.MainWindow, res.getString("String_84"), true);
//        pDlg.CenterWindow();
//        pDlg.setVisible(true);
//        if (pDlg.Result != pDlg.RESULT_OK) {
//            return false;
//        }
//        JyType   = pDlg.getJyType();
//        String JyFilter = pDlg.getFilter();
//        PO.SetValueByParamName("JyType", JyType);
//        PO.SetValueByParamName("JyFilter", JyFilter);
        return true;
    }


    public static boolean setAdjustMoney(JParamObject PO){
//            // �����ظ�����
//            String TZZD_BH = PO.GetValueByParamName("TZZD_BH","");
//            if(!TZZD_BH.trim().equals("")){
//                return true;
//            }
//            //
//            JReportTzjeSelectDlg pDlg = new JReportTzjeSelectDlg(JActiveDComDM.MainApplication.MainWindow, res.getString("String_84"), true);
//            pDlg.CenterWindow();
//            pDlg.setVisible(true);
//            if (pDlg.Result != pDlg.RESULT_OK) {
//                return false;
//            }
//            TZZD_BH   = pDlg.getTZZD_BH();
//            String TZZD_MC = pDlg.getTZZD_MC();
//            String TZZD_FH = pDlg.getTZZD_FH();
//            String TZZD_XS = pDlg.getTZZD_XS();
//            String TZZD_JD = pDlg.getTZZD_JD();
//            String TZZD_DW = pDlg.getTZZD_DW();
//            PO.SetValueByParamName("TZZD_BH", TZZD_BH);
//            PO.SetValueByParamName("TZZD_MC", TZZD_MC);
//            PO.SetValueByParamName("TZZD_FH", TZZD_FH);
//            PO.SetValueByParamName("TZZD_XS", TZZD_XS);
//            PO.SetValueByParamName("TZZD_JD", TZZD_JD);
//            PO.SetValueByParamName("TZZD_DW", TZZD_DW);
            return true;
    }
    /**
     * ���ػ�����
     * @return String
     */
    public static String getReportFiscalYear() {
        JParamObject PO = JParamObject.Create();
        String yearStrs = PO.GetValueByEnvName("RPT_KJND", "");
        if (yearStrs.trim().length() != 0)
            return yearStrs;
        return "0000";
    }

    /**
     * �Ƿ�������ʱ��
     * @param PO JParamObject
     * @return boolean
     */
    public static boolean isOutCalcTime(JParamObject PO){
        String isOut = PO.GetValueByEnvName(JReportMask.RPT_CALC_TIME,"0");
        if(isOut.equals("1")){
            return true;
        }
        return false;
    }

    /**
     * �Ƿ���Լ���SAP����
     * @param PO JParamObject
     * @return boolean
     */
    public static boolean isCanCalcSap(JParamObject PO) {
        String isCan = PO.GetValueByEnvName(JReportMask.RPT_CALC_SAP, "0");
        if (isCan.equals("1")) {
            return true;
        }
        return true;
//lk        return false;
    }
	/**
	 * ��Package���������BIS���������
	 * @param id String
	 * @return StubObject
	 */
	public static StubObject getBISClientStub(String id){
	  List list = PackageStub.getContentVector("BISClientStub");
	  StubObject so=null;
	  for (int i=0;i<list.size();i++){
		so = (StubObject) list.get(i);
		if (id.equals(so.getID().toString()))
		  break;
	  }
	  return so;
  }
  /**
   * ���ò�ѯ����
   * @param property List
   * @param iTable ITable
   * @throws Exception
   */
//  public static void setTableRowValue(String ID,List property,ITable iTable) throws Exception{
//	List list = PackageStub.getContentVector(ID+ "_"+ iTable.getName());
//	for(int i = 0;i<property.size();i++){
//	  String[] value = (String[]) property.get(i);
//	  IStructure iStru = iTable.CreateEmptyRow();
//	  for (int l=0;l<list.size();l++){
//		StubObject so = (StubObject)list.get(l);
//		String key = so.getString("InputStru", "");
//		int index = Integer.parseInt( so.getString("Index", "0"));
//		iStru.setString(key, value[index]);
//	  }
//	}
//  }
  /**
   * ���ò�ѯ����
   * @param property List
   * @param iTable ITable
   * @throws Exception
   */
//  public static void setStructRowValue(String ID,List property,ITable iTable) throws Exception{
//	List list = PackageStub.getContentVector(ID+ "_"+ iTable.getName());
//	for(int i = 0;i<property.size();i++){
//	  String[] value = (String[]) property.get(i);
//	  IStructure iStru = iTable.CreateEmptyRow();
//	  for (int l=0;l<list.size();l++){
//		StubObject so = (StubObject)list.get(l);
//		String key = so.getString("InputStru", "");
//		int index = Integer.parseInt( so.getString("Index", "0"));
//		iStru.setString(key, value[index]);
//	  }
//	}
//  }
  /**
   * ȡ����ֵ
   * @param ID String
   * @param iTable ITable
   * @return EFDataSet
   * @throws Exception
   */
//  public static Object getTableRowValue(String ID,ITable iTable) throws Exception{
//	List list = PackageStub.getContentVector(ID+ "_"+ iTable.getName());
//	Vector data = new Vector();
//	for(int i = 0;i<iTable.getRowCount();i++){
//	  IStructure iStru = iTable.getRow(i);
//	  for (int l=0;l<list.size();l++){
//		StubObject so = (StubObject)list.get(l);
//		String field = so.getString("ResultStru", "");
//		data.add(iStru.AsString(field));
//	  }
//	}
//	return data;
//  }
//  public static Object getTableRowValue(String ID,ITable iTable,HashMap mEccResultMap,HashMap mEccID_FuncMap) throws Exception{
//	  String sID="", LastID="", sRes;
//	  Object obj=null;
//	  List list = PackageStub.getContentVector(ID + "_" + iTable.getName());
//	  Vector data = new Vector();
//	  for (int i = 0; i < iTable.getRowCount(); i++) {
//		  IStructure iStru = iTable.getRow(i);
//		  LastID = sID;
//		  sID = iStru.AsString("ID");
//		  if ( !sID.equals(LastID) && data.size()>0 ) {
//			  obj = mEccID_FuncMap.get(LastID);
//			  if ( obj != null) {
//				  mEccResultMap.put((String)obj, data);
//			  }
//			  data = new Vector();
//		  }
//		  sRes = iStru.AsString("LINE");
//		  data.add(sRes);
//	  }
//	  if ( data.size()>0  ) {
//		  obj = mEccID_FuncMap.get(sID);
//		  if ( obj != null) {
//			  mEccResultMap.put((String)obj, data);
//		  }
//	  }
//	  return mEccResultMap;
//  }


  public static void formatSql(List list, String string, String separator, int len) {
	//�Ƚ����ַ�ת��
	String[] value;
	while(string.length()>0){
	  value = new String[1];
	  if (string.length()>=len){
		value[0] = string.substring(0,len);
		int index = value[0].toUpperCase().lastIndexOf(separator);
		if (index<=0) index = value[0].length();
		value[0] = string.substring(0,index);
		string = string.substring(index);
	  }else{
		value[0] = string;
		string = "";
	  }
	  System.out.println(value[0]);
	  list.add(value);
	}
  }
  public static void formatSql(List list, String string, String[] separator, int len,int iMaxID) {
	//�Ƚ����ַ�ת��
	string = string.trim();
	String[] value;
	while (string.length() > 0) {
		value = new String[2];
		if (string.length() >= len) {
			value[0] = String.valueOf(iMaxID);
			value[1] = string.substring(0, len);
			int index = len;
			for ( int i=0;i<separator.length;i++) {
                index = value[1].toUpperCase().lastIndexOf(separator[i]);
                if (index == 0)
                    index = separator[i].length();
				if  ( index >0 )
					break;
            }
			value[1] = string.substring(0, index);
			string = string.substring(index);
		} else {
			value[0] = String.valueOf(iMaxID);
			value[1] = string;
			string = "";
		}
//		System.out.println(value[1]);
		list.add(value);
	}
  }

}
