package jreport.jdal.classes.DALReportObject;

import java.sql.*;
import java.util.*;

import org.jdom.*;
import com.core.xml.*;
import com.efounder.eai.*;
import com.efounder.eai.data.JParamObject;
import com.efounder.sql.JConnection;

import jframework.foundation.classes.*;
import jreport.model.classes.calculate.*;
import jreport.model.classes.calculate.prepare.*;
import jreport.swing.classes.ReportBook.*;
import jreport.value.*;
import jreportfunction.pub.*;
//import jservice.jdal.classes.DALSecurityObject.DBOSecurityObject;
import jtoolkit.xml.classes.JXMLObject;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.builder.meta.dctmodel.DCTMetaData;

/**
 * <p>Title: 报表服务类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author hufeng
 * @version 1.0
 */
public class JReportService {
    static ResourceBundle res = ResourceBundle.getBundle("jreport.jdal.classes.DALReportObject.Language");
	static HashMap zdMessMap  = new HashMap();
	static HashMap DwJsMap  = new HashMap();


	/**
	 * 取得Key值
	 * @param PO JParamObject
	 * @return String key
	 */
	public static String getConnectKey(JParamObject PO) {
		String sKey = "";
		String svrName = PO.GetValueByParamName("SVRNAME","");
		if (!svrName.equals("") && !svrName.equals("SAP") && !svrName.equals("BO") ) { //SAP BO 这里设为默认关键字
			EAIServer.initEAIServer();
			StubObject stansvr = EAIServer.getEAIServer(svrName);
			if (stansvr != null) {
				String DataBaseName = stansvr.getString(EAIServer.SERVER_DBID, "");
				String DBNO = stansvr.getString(EAIServer.SERVER_ZTID,"");
				if (!DataBaseName.equals("") && !DBNO.equals("") ) {
					sKey = DataBaseName+"_"+DBNO;
				}
			}
		}
		return sKey;
    }
    /**
     * 取得新连接
     * @param PO JParamObject
     * @return JConnection
     */
    public static JConnection getConnection(JParamObject PO) {
        //取数据库连接
        String svrName = PO.GetValueByParamName("SVRNAME", ""); //lk 根据SVRNAME来确定取哪个连接
		String oldDbName = PO.GetValueByEnvName("DataBaseName", "");
		String oldDbNo = PO.GetValueByEnvName("DBNO", "");


        if (!svrName.equals("") && !svrName.equals("SAP") && !svrName.equals("BO") ) { //SAP BO 这里设为默认关键字
            EAIServer.initEAIServer();
            StubObject stansvr = EAIServer.getEAIServer(svrName);
            if (stansvr != null) {
                String dbset = stansvr.getString(EAIServer.SERVER_DBID, null);
                if (dbset != null && dbset.trim().length() > 0)
                    PO.SetValueByEnvName("DataBaseName", dbset);
                dbset = stansvr.getString(EAIServer.SERVER_ZTID, null);
                if (dbset != null && dbset.trim().length() > 0)
                    PO.SetValueByEnvName("DBNO", dbset);
            }
        }
		System.out.print(" svrName:"+svrName);
		System.out.print(" DataBaseName:"+PO.GetValueByEnvName("DataBaseName", "null"));
		System.out.print(" DBNO:"+PO.GetValueByEnvName("DBNO", "null"));
		System.out.print(" dbOwner:"+PO.GetValueByEnvName("dbOwner", "null"));
		JConnection conn = null;
		try {
			conn = (JConnection) JActiveDComDM.
				AbstractDataActiveFramework.
				InvokeObjectMethod("DBManagerObject", "GetDBConnection", PO);
		} catch(Exception ex){
			PO.SetValueByEnvName("DataBaseName", oldDbName);
			PO.SetValueByEnvName("DBNO", oldDbNo);
			System.out.print("Create Conn Exception:"+ex.getMessage());
		} finally {
			//恢复原样
			PO.SetValueByEnvName("DataBaseName", oldDbName);
			PO.SetValueByEnvName("DBNO", oldDbNo);
		}
        return conn;
    }
    public static String getZtSvrName(JConnection conn,JParamObject PO) {
    	String sSvr = "";
    	String sZtbh = PO.GetValueByParamName("ZTBH","");
    	if ( sZtbh.equals("")) {
    		return "";
    	}
		Statement st = null;
		ResultSet rs = null;
		String asSql = "";
		try {
			st = conn.createStatement();
			asSql = " select ZT_SOUCE from RPTZTZD where ZT_ID = '"+sZtbh+"' ";
			rs = st.executeQuery(asSql);
			if ( rs.next()) {
				sSvr = rs.getString(1);
			}
		} catch (Exception e) {
				e.printStackTrace();
		}finally{
			conn.BackStatement(st,rs);
		}
    	return sSvr;
    }
    
	public static Object GetAllCalType(JConnection conn, JParamObject PO,String[] dwList,String[] bbList) {
				HashMap	DealMap	=	new HashMap();
				Statement st = null;
				ResultSet rs = null;
				Vector ErrMessage = new Vector();
				String strDate		= PO.GetValueByParamName("BBZD_DATE"); 	// 查询报表的所属日期
				String strAddBh 	= PO.GetValueByParamName("ADD_BH"); 		// 查询报表的所属编号
				String strAddType = PO.GetValueByParamName("ADD_TYPE"); // 查询报表的所属类型
				String ReportSuffixyear	= PO.GetValueByEnvName("ReportSuffixyear", "");
				String thisYear		=	strDate.substring(0,4);
				PreparedStatement pStmt = null;
				String asSql = "";
				String CalType = "";
				String CalSouce= "";
				try {
					int add_type = PO.GetIntByParamName("ADD_TYPE", 0);
					String tableName="", columnName="";

					switch (add_type) {
						case 4:
							tableName = JREPORT.getRTNbyPO("LBBB",PO);
							columnName = "LBZD_BH";
							break;
						case 5:
							tableName = JREPORT.getRTNbyPO("DWSB",PO);
							columnName = "DWZD_BH";
							break;
						case 6:
							tableName = JREPORT.getRTNbyPO("BMBB",PO);
							columnName = "BMZD_BH";
							break;
						case 7:
							tableName = JREPORT.getRTNbyPO("CBBB",PO);
							columnName = "CBZD_BH";
							break;
					}

					asSql = " select SB.DWZD_BH,CAL_TYPE,CAL_SOUCE from "+tableName+" SB,"+JREPORT.getRTNbyPO("JSLXZD",PO)+" JSLX"
						+   " where SB.CAL_BH = JSLX.CAL_BH(+) "
						+   " and ? like SB."+columnName+"||'%' and SB.BBZD_BH=?  ";
					if ( add_type==6 || add_type==7 ) {
						asSql += " and SB.BBZD_DATE = '"+strDate+"' ";
					}
					asSql +=" order by DWZD_BH DESC ";
					pStmt = conn.prepareStatement(asSql);

					for ( int i=0;i<dwList.length;i++ ) {
						for ( int j=0;j<bbList.length;j++) {
							Object obj = DealMap.get(((String) dwList[i])+"_"+((String) bbList[j]));
							if ( obj != null ) //处理过了
								continue;
							pStmt.setString(1, (String) dwList[i]);
							pStmt.setString(2, (String) bbList[j]);
							rs = pStmt.executeQuery();
							while ( rs.next() ) {
						        CalType = rs.getString("CAL_TYPE");
								CalSouce =rs.getString("CAL_SOUCE");
								if ( CalType==null ) {
									continue;
								} else { //找到了，可能是自己设的，可能是上级设的
									DealMap.put(((String) dwList[i])+"_"+((String) bbList[j]),CalType+"_"+CalSouce);
									break;
								}
							}
						}
                    }
				}
				catch (Exception e) {
					e.printStackTrace();
				}finally{
					conn.BackPreparedStatement(pStmt,rs);
				}
				return DealMap;
	}
//
//    /**
//     * 取系统报表列表
//     */
//    public static void getSystemReportList(JConnection conn, JXMLObject XMLObject, Element ParentElement, JParamObject PO) throws Exception {
//        String strSql;
//        Statement stmt = null;
//        ResultSet rs = null;
//        String RTS, ADD_BH, ReportYear, ReportMonth, ReportDate;
//
//        ADD_BH = PO.GetValueByParamName("ADD_BH", "");
//        ReportYear = PO.GetValueByParamName("BBZD_YEAR");
//        ReportMonth = PO.GetValueByParamName("BBZD_MONTH");
//        ReportDate = ReportYear + ReportMonth;
//        RTS = PO.GetValueByEnvName("ReportSuffixyear");
//
//        try {
//			PO.SetValueByParamName("pszgbh", PO.GetValueByEnvName("UserName"));
//			PO.SetValueByParamName("psqxbh", JREPORT.getQxName("BBQX"));
//			PO.SetValueByParamName("pscol", "BBZD.BBZD_BH");
//			PO.SetValueByParamName("pibzw", "1");
//
//            stmt = conn.createStatement();
//            strSql = "select ZD.BBZD_BH,ZD.BBZD_DATE,ZD.BBZD_MC,ZD.BBZD_LX," +
//                " ZD.BBZD_SBBH,ZD.BBZD_FCBZ,ZD.BBZD_NB,ZD.BBZD_XF,ZD.TZZD_ORDE," +
//                " ZD.BBZD_USE,isnull(XZ.SBXZ_NAME,'') as SBXZ_NAME,isnull(FC.BBZD_FCBZ,'') as BBZD_FCBZ," +
//                " isnull(WC.BBZD_BBWC,'') as BBZD_BBWC,isnull(WC.BBZD_ZGXM,'') as BBZD_ZGXM,isnull(WC.BBZD_WCRQ,'') as BBZD_WCRQ " +
//                " from " + JREPORT.getRTNbyPO("BBZD",PO) + RTS + " ZD, " +
//                JREPORT.getRTNbyPO("SBXZ",PO) + RTS + " XZ, " +
//                JREPORT.getRTNbyPO("DWFC",PO) + RTS + " FC, " +
//                JREPORT.getRTNbyPO("DWWC",PO) + RTS + " WC, " +
//                " where ZD.BBZD_SBXZ = XZ.SBXZ_ID " +
//                " and ZD." +
//                " and BBZD_DATE = '" + ReportDate + "' " +
//				" and " + DBOSecurityObject.CheckDataLimit3New(conn.getESPConnection(), PO.getEAIParamObject(),true) +
////                " and " + DBOSecurityObject.SCheckDataLimit3(conn, PO, JREPORT.getQxName("BBQX"), "BBZD.BBZD_BH", "1") +
//                " ORDER BY BBZD_BH";
//            rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//
//            // 设置当前所到月份的报表
//            Element EReport;
//            while (rs.next()) {
//                EReport = XMLObject.AddChildElement(ParentElement, "Report");
//                EReport.setAttribute("BBZD_BH", rs.getString("BBZD_BH"));
//                EReport.setAttribute("BBZD_DATE", rs.getString("BBZD_DATE"));
//                EReport.setAttribute("BBZD_MC", rs.getString("BBZD_MC"));
//                EReport.setAttribute("BBZD_SBXZMC", rs.getString("SBXZ_NAME"));
//                EReport.setAttribute("BBZD_LX", rs.getString("BBZD_LX"));
//                EReport.setAttribute("BBZD_SBBH", rs.getString("BBZD_SBBH"));
//                EReport.setAttribute("BBZD_FCBZ", rs.getString("BBZD_FCBZ"));
//                EReport.setAttribute("BBZD_NB", rs.getString("BBZD_NB"));
//                EReport.setAttribute("BBZD_XF", rs.getString("BBZD_XF"));
//                EReport.setAttribute("TZZD_ORDE", com.pansoft.pub.util.RSFunction.getString(rs, "TZZD_ORDE"));
//                String BBZD_LOCK = "0";
//                if (rs.getString("BBZD_USE") != null) {
//                    int BBZD_USE = rs.getInt("BBZD_USE") % 2;
//                    if (BBZD_USE == 1) {
//                        BBZD_LOCK = "1";
//                    }
//                }
//                EReport.setAttribute("BBZD_LOCK", BBZD_LOCK);
//            }
//        } finally {
//            conn.BackStatement(stmt, rs);
//        }
//    }
//
//    /**
//     * 取单位报表列表
//     */
//    public static void getUnitReportList(JConnection conn, JXMLObject XMLObject, Element ParentElement, JParamObject PO) throws Exception {
//        String strSql;
//        Statement stmt = null;
//        ResultSet rs = null;
//        String RTS, ReportYear, ReportMonth, ReportDate;
//
//        ReportYear = PO.GetValueByParamName("BBZD_YEAR");
//        ReportMonth = PO.GetValueByParamName("BBZD_MONTH");
//        ReportDate = ReportYear + ReportMonth;
//        RTS = PO.GetValueByEnvName("ReportSuffixyear");
//
//        try {
//			PO.SetValueByParamName("pszgbh", PO.GetValueByEnvName("UserName"));
//			PO.SetValueByParamName("psqxbh", JREPORT.getQxName("BBQX"));
//			PO.SetValueByParamName("pscol", "BBZD.BBZD_BH");
//			PO.SetValueByParamName("pibzw", "1");
//
//            stmt = conn.createStatement();
//            strSql = "select BBZD_BH,BBZD_DATE,BBZD_MC,BBZD_SBXZ,BBZD_LX," +
//                " BBZD_SBBH,BBZD_FCBZ,BBZD_NB,BBZD_XF,TZZD_ORDE,BBZD_USE,isnull(SBXZ_NAME,'') as SBXZ_NAME " +
//                " from " + JREPORT.getRTNbyPO("BBZD",PO) + RTS + " ZD, " + JREPORT.getRTNbyPO("SBXZ",PO) + RTS + " XZ, " +
//                " where ZD.BBZD_SBXZ = XZ.SBXZ_ID " +
//                " and BBZD_DATE = '" + ReportDate + "' " +
//				" and " + DBOSecurityObject.CheckDataLimit3New(conn.getESPConnection(), PO.getEAIParamObject(),true) +
////                " and " + DBOSecurityObject.SCheckDataLimit3(conn, PO, JREPORT.getQxName("BBQX"), "BBZD.BBZD_BH", "1") +
//                " ORDER BY BBZD_BH";
//            rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//
//            // 设置当前所到月份的报表
//            Element EReport;
//            while (rs.next()) {
//                EReport = XMLObject.AddChildElement(ParentElement, "Report");
//                EReport.setAttribute("BBZD_BH", rs.getString("BBZD_BH"));
//                EReport.setAttribute("BBZD_DATE", rs.getString("BBZD_DATE"));
//                EReport.setAttribute("BBZD_MC", rs.getString("BBZD_MC"));
//                EReport.setAttribute("BBZD_SBXZMC", rs.getString("SBXZ_NAME"));
//                EReport.setAttribute("BBZD_LX", rs.getString("BBZD_LX"));
//                EReport.setAttribute("BBZD_SBBH", rs.getString("BBZD_SBBH"));
//                EReport.setAttribute("BBZD_FCBZ", rs.getString("BBZD_FCBZ"));
//                EReport.setAttribute("BBZD_NB", rs.getString("BBZD_NB"));
//                EReport.setAttribute("BBZD_XF", rs.getString("BBZD_XF"));
//                EReport.setAttribute("TZZD_ORDE", com.pansoft.pub.util.RSFunction.getString(rs, "TZZD_ORDE"));
//                String BBZD_LOCK = "0";
//                if (rs.getString("BBZD_USE") != null) {
//                    int BBZD_USE = rs.getInt("BBZD_USE") % 2;
//                    if (BBZD_USE == 1) {
//                        BBZD_LOCK = "1";
//                    }
//                }
//                EReport.setAttribute("BBZD_LOCK", BBZD_LOCK);
//            }
//        } finally {
//            conn.BackStatement(stmt, rs);
//        }
//    }
//
//    /**
//     * 取部门报表列表
//     */
//    public static void getDeptReportList(JConnection conn, JXMLObject XMLObject, Element ParentElement, JParamObject PO) {
//
//    }
//
//    /**
//     * 取成本报表列表
//     */
//    public static void getCostReportList(JConnection conn, JXMLObject XMLObject, Element ParentElement, JParamObject PO) {
//
//    }
//
//    /**
//     * 根据汇总类别获得包含的单位列表
//     */
//    public static String getUnitListByLbbh(JConnection conn, JParamObject PO) throws Exception {
//        Statement stmt = null;
//        ResultSet rs = null;
//        String bh, mc, lbbh, type, strSql, suffixYear;
//
//        try {
//            stmt = conn.createStatement();
//            lbbh = PO.GetValueByParamName("Lbbh");
//            suffixYear = PO.GetValueByEnvName("ReportSuffixyear", "");
//            //取包含类别
//            strSql = "select LBZD_CON_TYPE from " + JREPORT.getRTNbyPO("LBZD",PO) + suffixYear + " where LBZD_BH='" + lbbh + "'";
//            rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//            type = "";
//            if (rs.next()) {
//                type = rs.getString("LBZD_CON_TYPE");
//            }
//            //取包含单位列表
//            //1/2/3/4：类别/单位/部门/成本中心
//            if (type.equals("1")) {
//                strSql = "select ZD.LBZD_BH,ZD.LBZD_MC " +
//                    " from " + JREPORT.getRTNbyPO("LBZD",PO) + suffixYear + " ZD ," + JREPORT.getRTNbyPO("LBCONDW",PO) + suffixYear + " LBDW " +
//                    " where  ZD.LBZD_BH    = LBDW.CON_DWBH " +
//                    " and    LBDW.LBZD_BH  = '" + lbbh + "' " +
//                    " and    LBDW.CON_TYPE = '" + type + "' " +
//                    " order  by ZD.LBZD_JS,ZD.LBZD_BH ";
//            } else if (type.equals("2")) {
//                strSql = "select ZD.DWZD_BH,ZD.DWZD_MC " +
//                    " from " + JREPORT.getRTNbyPO("DWZD",PO) + suffixYear + " ZD ," + JREPORT.getRTNbyPO("LBCONDW",PO) + suffixYear + " LBDW " +
//                    " where  ZD.DWZD_BH    = LBDW.CON_DWBH " +
//                    " and    LBDW.LBZD_BH  = '" + lbbh + "' " +
//                    " and    LBDW.CON_TYPE = '" + type + "' " +
//                    " order  by ZD.DWZD_JS,ZD.DWZD_BH ";
//            } else if (type.equals("3")) {
//                strSql = "select ZD.BMZD_BH,ZD.BMZD_MC " +
//                    " from " + JREPORT.getRTNbyPO("BMZD",PO) + suffixYear + " ZD ," + JREPORT.getRTNbyPO("LBCONDW",PO) + suffixYear + " LBDW " +
//                    " where  ZD.BMZD_BH    = LBDW.CON_DWBH " +
//                    " and    LBDW.LBZD_BH  = '" + lbbh + "' " +
//                    " and    LBDW.CON_TYPE = '" + type + "' " +
//                    " order  by ZD.BMZD_JS,ZD.BMZD_BH ";
//            } else if (type.equals("4")) {
//                strSql = "select ZD.CBZD_BH,ZD.CBZD_MC " +
//                    " from " + JREPORT.getRTNbyPO("CBZD",PO) + suffixYear + " ZD ," + JREPORT.getRTNbyPO("LBCONDW",PO) + suffixYear + " LBDW " +
//                    " where  ZD.CBZD_BH    = LBDW.CON_DWBH " +
//                    " and    LBDW.LBZD_BH  = '" + lbbh + "' " +
//                    " and    LBDW.CON_TYPE = '" + type + "' " +
//                    " order  by ZD.CBZD_JS,ZD.CBZD_BH ";
//            } else {
//                throw new Exception("无效类别！");
//            }
//            JXMLObject XMLObject = new JXMLObject();
//            Element unitElmt = XMLObject.AddChildElement(null, "UnitList");
//            rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//            int i = 0;
//            while (rs.next()) {
//                bh = rs.getString(1);
//                mc = rs.getString(2);
//                unitElmt.setAttribute("Bh" + i, bh);
//                unitElmt.setAttribute("Mc" + i, mc);
//                i++;
//            }
//            unitElmt.setAttribute("UnitCount", i + "");
//            //
//            return XMLObject.GetRootXMLString();
//        } finally {
//            conn.BackStatement(stmt, rs);
//        }
//    }
//
//    /**
//     * 初始化报表会计年度
//     */
//    public static String InitKjnd(JConnection conn, JParamObject PO) throws Exception {
//        String strSql, kjnd;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            kjnd = PO.GetValueByParamName("FiscalYear", "");
//            stmt = conn.GetStatement(conn);
//            String vkey = JREPORT.getKjndName();
//            // 先删除
//            strSql = "delete from BSCONF where F_VKEY = '" + vkey + "'";
//            stmt.executeUpdate(JConvertSql.convertSql(stmt, strSql));
//            // 再插入
//            strSql = "insert into BSCONF(F_VKEY,F_VAL,F_NOTE) " +
//                " values('" + vkey + "','" + kjnd + "','Report System')";
//            stmt.executeUpdate(JConvertSql.convertSql(stmt, strSql));
//
//            return kjnd;
//        } catch (Exception e) {
//            conn.rollback();
//            throw e;
//        } finally {
//            conn.BackStatement(stmt, rs);
//        }
//    }
//
//
//    /**
//     * 取报表会计年度
//     */
//    public static String GetKjnd(JConnection conn, JParamObject PO) throws Exception {
//        String strSql,type,mod;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            type = PO.GetValueByParamName("MDL_KEY", "");
//            mod =  PO.GetValueByParamName("MOD", "");
//            stmt = conn.GetStatement(conn);
//            String kjnd = "";
//            strSql = "select MDL_VALUE from SYS_MDL_VAL where MDL_ID = '"+mod+"' and MDL_KEY = '"+type+"'";
//            rs = stmt.executeQuery(strSql);
//            while(rs.next()){
//              kjnd = rs.getString("MDL_VALUE").toString();
//            }
//            return kjnd;
//
//        } catch (Exception e) {
//            conn.rollback();
//            throw e;
//        } finally {
//            conn.BackStatement(stmt, rs);
//        }
//    }
//
//    /**
//     * 取责任中心对应的数据库用户
//     * 1.如果当前单位对应了责任中心编号，则使用对应的责任中心
//     * 2.如果当前责任中心是虚拟责任中心，用使用cwbaseX_v用户
//     * 3.如果当前责任中心是明细实责任中心，则使用dbo用户
//     * 4.如果当前责任中心是非明细实责任中心
//     *   如果是取汇总口径的数据，则用ACZRZX.F_USER中的用户，否则用dbo用户
//     * 5.如果当前责任中心是合并组织机构
//     *   如果是取汇总口径的数据，用ACZRZX.F_USER中的用户，如果取合并口径的数，则用cwbaseX_v用户
//     * 6.其它的情况用dbo用户
//     *
//     * 口径：1/2：合并口径/汇总口径
//     */
//    public static String getDbUser(Statement stmt, String dwbh, String strCwrq, String bbDate, String dbName, boolean isZrzx) {
//        String strSql, zrzx, kj;
//        String xnZrzxUser;
//
//        try {
//            // 只有历史年份加后缀
//            String suffixYear = "";
///*
//            if (bbDate.substring(0, 4).compareTo(strCwrq.substring(0, 4)) < 0) {
//                suffixYear = bbDate.substring(0, 4);
//            }
//*/
//            // 1.空责任中心返回
//            if (dwbh.equals("")) {
//                return "";
//            }
//
//            // 2.形成虚拟责任中心用户
//            dbName = dbName.toLowerCase();
//            String dbNum = StringFunction.replaceString(dbName, "cwbase", "");
//            if (dbNum.length() == 1) {
//                dbNum = "0" + dbNum;
//            }
//            xnZrzxUser = "cwbase" + dbNum + "_v";
//
//            // 3.取责任中心用户
//            if (isZrzx) {
//                zrzx = dwbh;
//            } else {
//                JConnection conn = (JConnection) stmt.getConnection();
//                zrzx = getZrzxByDwbh(conn, dwbh, strCwrq, bbDate);
//            }
//            if (zrzx.equals("")) {
//                return "";
//            }
//
//            // 取对应口径信息
//            kj = "";
//            strSql = "select isnull(DWZD_KJ,'') from "+JREPORT.getRTNbyPO("DWZD",null) + suffixYear + " where DWZD_BH = '" + dwbh + "'";
//            ResultSet rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//            if (rs.next()) {
//                kj = rs.getString(1).trim();
//            } else {
//                return "";
//            }
//            if (kj.equals("")) {
//                kj = "1";
//            }
//
//            // 4.取责任中心属性
//            String isTrue = "";
//            String isMx = "1";
//            String dbUser = "dbo";
//            strSql = "select isnull(F_TRUE,''),F_MX,isnull(F_USER,'') from ACZRZX" + suffixYear + " where F_CODE = '" + zrzx + "'";
//            rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//            if (rs.next()) {
//                isTrue = rs.getString(1).trim();
//                isMx = rs.getString(2).trim();
//                dbUser = rs.getString(3).trim();
//            }
//            rs.close();
//            // 5.虚拟责任中心用cwbaseX_v登录
//            if (isTrue.equals("0")) {
//                return xnZrzxUser;
//            }
//            // 6.非明细实责任中心取汇总数据用ACZRZX.F_USER用户
//            if (isMx.equals("0") && isTrue.equals("1") && kj.equals("2")) {
//                return dbUser;
//            }
//            // 7.合并组织机构汇总口径用ACZRZX.F_USER用户，合并口径用cwbaseX_v用户
//            if (isTrue.equals("2")) {
//                if (kj.equals("2")) {
//                    return dbUser;
//                } else {
//                    return xnZrzxUser;
//                }
//            }
//            return "dbo";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//	public static String CalFzbbResult(ResultSet RS, JConnection conn, JParamObject PO) {
//		String asSql = "";
//		String zbTmpTb = PO.GetValueByParamName("TMP_NAME"+PO.GetValueByParamName("TMP_NUM","0"),"");
//		PreparedStatement pStmt = null;
//		asSql = " insert into "+zbTmpTb+"(F_CODE,F_XM01,F_XM02) values(?,?,?) ";
//		try {
//			pStmt = conn.prepareStatement(asSql);
//			while (RS.next()) {
//				pStmt.setString(1, RS.getString(1));
//				pStmt.setString(2, RS.getString(2));
//				pStmt.setString(3, RS.getString(3));
//				pStmt.addBatch();
//			}
//			pStmt.executeBatch();
//			pStmt.clearBatch();
//		} catch (Exception ex){
//
//		}
//		return "";
//	}
//	/**
//	 *
//	 * @param conn JConnection
//	 * @param sDwbh String
//	 * @return int
//	 */
//	public static String getDwSx(JConnection conn,String dwbh,String sxbh) {
//		String sSx="";
//		ResultSet rs = null;
//		PreparedStatement pStmt = null;
//		String strSql = "";
//		try {
//			if (dwbh.trim().equals("")) {
//				return "1";
//			}
//			strSql = " select NVL(trim("+sxbh+"),'') from RPTDWZD where DWZD_BH = ? ";
//			pStmt = conn.prepareStatement(strSql);
//			pStmt.setString(1, dwbh);
//			rs = pStmt.executeQuery();
//			if (rs.next()) {
//				sSx = rs.getString(1).trim();
//			}
//			rs.close();
//			return sSx;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		} finally {
//			conn.BackPreparedStatement(pStmt, rs);
//       }
//	}
//	/**
//	 *
//	 * @param conn JConnection
//	 * @param sDwbh String
//	 * @return int
//	 */
//	public static String getDwCalKj(JConnection conn,String dwbh) {
//		String sKj="1";
//		ResultSet rs = null;
//		PreparedStatement pStmt = null;
//		String strSql = "";
//		try {
//			if (dwbh.trim().equals("")) {
//				return "1";
//			}
//			strSql = " select NVL(trim(DWZD_KJ),'1') from RPTDWZD where DWZD_BH = ? ";
//			pStmt = conn.prepareStatement(strSql);
//			pStmt.setString(1, dwbh);
//			rs = pStmt.executeQuery();
//			if (rs.next()) {
//				sKj = rs.getString(1).trim();
//			}
//			rs.close();
//			return sKj;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "1";
//		} finally {
//			conn.BackPreparedStatement(pStmt, rs);
//        }
//	}
//
//
//    /**
//     * 根据指定的单位编号取对应的责任中心pr
//     * 1.如果当前日期小于财务年份，则取历史字典表
//     * 2.如果对应责任中心为空，则用当前单位编号
//     */
//    public static String getZrzxByDwbh(JConnection conn, String dwbh, String strCwrq, String bbDate) {
//        String strSql, suffixYear, zrzx;
//// 为什么要判断当前财务日期呢，意义何在？ lk
////        if (strCwrq.length() < 4 || bbDate.length() < 4) {
////            return dwbh;
////        }
//
//        ResultSet rs = null;
//        PreparedStatement pStmt = null;
//        try {
//            suffixYear = "";
///*
//            if (bbDate.substring(0, 4).compareTo(strCwrq.substring(0, 4)) < 0) {
//                suffixYear = bbDate.substring(0, 4);
//            }
//*/
//            if (dwbh.trim().equals("")) {
//                return dwbh;
//            }
//            strSql = "select nvl(DWZD_CODE,' ') from "+JREPORT.getRTNbyPO("DWZD",null) + suffixYear + " where DWZD_BH = ? ";
//            pStmt = conn.prepareStatement(strSql);
//            pStmt.setString(1, dwbh);
//            rs = pStmt.executeQuery();
//            zrzx = "";
//            if (rs.next()) {
//                zrzx = rs.getString(1).trim();
//            }
//            rs.close();
//            if (zrzx.equals("")|| zrzx==null) {
//                zrzx = dwbh;
//            }
//            return zrzx;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return dwbh;
//        } finally {
//            conn.BackPreparedStatement(pStmt, rs);
//        }
//    }
//    /**
//     * 根据指定的单位编号取对应的责任中心pr
//     * 1.如果当前日期小于财务年份，则取历史字典表
//     * 2.如果对应责任中心为空，则用当前单位编号
//     */
//    public static String getUnitIdByZrzx(JConnection conn, String zrzx) {
//        String strSql, suffixYear, unitid;
//
//        ResultSet rs = null;
//        PreparedStatement pStmt = null;
//        try {
//            suffixYear = "";
//            if (zrzx.trim().equals("")) {
//                return "";
//            }
//            strSql = "select nvl(UNIT_ID,' ') from ACZRZX where F_CODE = ? ";
//            pStmt = conn.prepareStatement(strSql);
//            pStmt.setString(1, zrzx);
//            rs = pStmt.executeQuery();
//            unitid = "";
//            if (rs.next()) {
//            	unitid = rs.getString(1).trim();
//            }
//            rs.close();
//            if (unitid.trim().equals("")|| zrzx==null) {
//            	unitid = "";
//            }
//            return unitid;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        } finally {
//            conn.BackPreparedStatement(pStmt, rs);
//        }
//    }
//    public static boolean getIsQywl(JConnection conn, String dwbh) {
//        String strSql, suffixYear,dwWl;
//        boolean bQywl = true;
//        ResultSet rs = null;
//        PreparedStatement pStmt = null;
//        try {
//            suffixYear = "";
//            if (dwbh.trim().equals("")) {
//                return bQywl;
//            }
//            strSql = "select nvl(DWZD_WLDW,'0') from RPTDWZD where DWZD_BH = ? ";
//            pStmt = conn.prepareStatement(strSql);
//            pStmt.setString(1, dwbh);
//            rs = pStmt.executeQuery();
//            dwWl = "";
//            if (rs.next()) {
//            	dwWl = rs.getString(1).trim();
//            }
//            rs.close();
//            if (dwWl.trim().equals("1") || dwWl==null) {
//            	bQywl = false;
//            }
//            return bQywl;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return true;
//        } finally {
//            conn.BackPreparedStatement(pStmt, rs);
//        }
//    }
//
//    /**
//     * 取得附加信息数据。
//     */
//    public static Object getAddInfoList(JConnection conn, JParamObject PO, Object Data, Object CustomObject, Object AdditiveObject) {
//        Vector addVector = new Vector();
//        JAddInfoStub infoStub;
//        Statement st = null;
//        ResultSet rs = null;
//        try {
//            st = conn.createStatement();
//            String suffix = PO.GetValueByEnvName("ReportSuffixyear", "");
////            String strSQL =
////                " select A.ADD_ID,A.ADD_MC,B.DWZD_BH,B.ADDINFO_DATE,A.ADD_TYPE " +
////                " from " +JREPORT.getRTNbyPO("ADDLX",PO)+ suffix + " A," + JREPORT.getRTNbyPO("DWADDIN",PO) + suffix + " B " +
////                " where A.ADD_ID = B.ADDINFO_LX " +
////                " order by A.ADD_ID";
//			String date = PO.GetValueByParamName("date");
//			String dwzd_bh = PO.GetValueByParamName("dwzd_bh");
//			String strSQL =
//				" select A.ADD_ID,A.ADD_MC,B.DWZD_BH,B.ADDINFO_DATE,B.ADDINFO_XH " +
//				" from " +JREPORT.getRTNbyPO("ADDLX",PO) + " A," +
//				"(SELECT DWZD_BH,ADDINFO_LX,ADDINFO_XH,ADDINFO_DATE,UNIT_ID FROM " + JREPORT.getRTNbyPO("DWADDIN",PO) +
//				" where DWZD_BH = '" + dwzd_bh + "' and ADDINFO_DATE = '" + date + "' ) B" +
//				" where A.ADD_ID = B.ADDINFO_LX(+) " +
//				" order by A.ADD_ID";
//
////			select A.ADD_ID,A.ADD_MC,B.DWZD_BH,B.ADDINFO_DATE,A.ADD_TYPE
////				from rptaddlx A, (SELECT DWZD_BH,ADDINFO_LX,ADDINFO_XH,ADDINFO_DATE,UNIT_ID FROM RPTDWADDIN WHERE  DWZD_BH = '1000' AND ADDINFO_DATE = '201007') B
////				where  A.ADD_ID = B.ADDINFO_LX(+)  order by A.ADD_ID
//
//            rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//            while (rs.next()) {
//                infoStub = new JAddInfoStub();
//                infoStub.ADD_ID = rs.getString(1);
//                infoStub.ADD_MC = rs.getString(2);
//				if(rs.getString(3) == null || rs.getString(3).length() == 0)
//					infoStub.DWZD_BH = "" ;
//				else
//					infoStub.DWZD_BH = rs.getString(3);
//				if(rs.getString(4) == null || rs.getString(4).length() == 0)
//					infoStub.ADD_DATE = "" ;
//				else
//					infoStub.ADD_DATE = rs.getString(4);
////				infoStub.ADD_TYPE = rs.getString(5);  // yinl
//				infoStub.ADD_TYPE = rs.getInt(5)+"";
//                addVector.add(infoStub);
//                infoStub = null;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//
//        return addVector;
//    }
//
//    /**
//     * 从总部获取上报的报表信息
//     *
//     * @param   conn  JConnection
//     * @param   PO    JParamObject
//     * @return        Object
//     */
//    public static HashMap getSubmitReportInfo(JConnection conn, JParamObject PO, Object data) throws Exception {
//        Statement st = null;
//        ResultSet rs = null;
//        HashMap map = new HashMap();
//        try {
//            st = conn.createStatement();
//            String  suffixYear = PO.GetValueByEnvName("ReportSuffixyear", "");
//            String   BBZD_DATE = PO.GetValueByParamName("BBZD_DATE", "");
//
//            /**
//             * 增加上报周期的条件过滤
//             * add by gengeng 2008-5-15
//             */
//            String pIncreWhere = getReportAttrWhere("A.SBKZ_SBZQ",BBZD_DATE);
//            String ADD_BH;
//            if (data == null)
//                return null;
//            List addList = (List) data;
//            for (Iterator it = addList.iterator(); it.hasNext(); ) {
//                Vector dataV = new Vector();
//                ADD_BH = (String) it.next();
//                String strSQL =
//                    " select distinct A.BBZD_BH,C.BBZD_MC " +
//                    " from "+JREPORT.getRTNbyPO("SBKZ",PO) + suffixYear + " A,"+JREPORT.getRTNbyPO("BBZD",PO) + suffixYear + " C" +
//                    " where C.BBZD_BH = A.BBZD_BH and A.SBKZ_BZ = '1' " +
//                    " and A.DWZD_BH = '" + ADD_BH + "' and C.BBZD_DATE = '" + BBZD_DATE + "'" + pIncreWhere +
//                    " and exists(select 1 from "+JREPORT.getRTNbyPO("DWSB",PO)+" where A.DWZD_BH = DWZD_BH and A.BBZD_BH = BBZD_BH)" +
//                    " order by A.BBZD_BH";
//                rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//                String BBZD_BH, BBZD_MC;
//                while (rs.next()) {
//                    BBZD_BH = rs.getString(1);
//                    BBZD_MC = rs.getString(2);
//                    Vector v = new Vector(2);
//                    v.add(BBZD_BH);
//                    v.add(BBZD_MC);
//                    dataV.add(v);
//                }
//                map.put(ADD_BH, dataV);
//            }
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//        return map;
//    }
//
//    /**
//     * 获取报表列表
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @return Object
//     */
//    public static Vector getReportList(JConnection conn, JParamObject PO) {
//        ResultSet rs = null;
//        PreparedStatement pStmt = null;
//        Vector report = new Vector();
//        try {
//            String suffixYear = PO.GetValueByEnvName("ReportSuffixyear", "");
//            String BBZD_DATE = PO.GetValueByParamName("BBZD_DATE", "");
//            String DWZD_BH = PO.GetValueByParamName("DWZD_BH", "");
//            int add_type = PO.GetIntByParamName("ADD_TYPE", 0);
//            String tableName="", columnName="";
//
//            switch (add_type) {
//                case 5:
//                    tableName = JREPORT.getRTNbyPO("DWSB",PO);
//                    columnName = "DWZD_BH";
//                    break;
//                case 6:
//                    tableName = JREPORT.getRTNbyPO("BMBB",PO);
//                    columnName = "BMZD_BH";
//                    break;
//                case 7:
//                    tableName = JREPORT.getRTNbyPO("CBBB",PO);
//                    columnName = "CBZD_BH";
//                    break;
//            }
//            String strSQL =
//                " select A.BBZD_BH,BBZD_MC,BBZD_DATE,BBZD_LX,TZZD_ORDE " +
//                " from "+JREPORT.getRTNbyPO("BBZD",PO) + suffixYear + " A," + tableName + suffixYear + " B" +
//                " where A.BBZD_BH = B.BBZD_BH and A.BBZD_LX = '2' " +
//                //" and A.BBZD_DATE = '" + BBZD_DATE +  //LIUKUN 去掉RPTBMBB,RPTCBBB的日期属性
//				"' and B." + columnName + " = ?" +
//                " order by A.BBZD_BH";
//            pStmt = conn.prepareStatement(strSQL);
//            pStmt.setString(1, DWZD_BH);
//            rs = pStmt.executeQuery();
//            JReportObject rptObj;
//            while (rs.next()) {
//                rptObj = new JReportObject();
//                rptObj.BBZD_BH = rs.getString(1);
//                rptObj.BBZD_MC = rs.getString(2);
//                rptObj.BBZD_DATE = rs.getString(3);
//                rptObj.BBZD_LX = rs.getString(4);
//                rptObj.TZZD_ORDE = rs.getString(5);
//                report.add(rptObj);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            conn.BackPreparedStatement(pStmt, rs);
//        }
//
//        return report;
//    }
//
//    /**
//     * 获取单位映射信息
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @return Object
//     */
//    public static Object getUnitMapData(JConnection conn, JParamObject PO) throws Exception {
//        Statement st = null;
//        ResultSet rs = null;
//        Vector dataV = new Vector();
//        try {
//            st = conn.createStatement();
//            String suffixYear = PO.GetValueByEnvName("ReportSuffixyear", "");
//            String strSQL =
//                " select SBDW_ZBDW,SBDW_TYPE,SBDW_DWBH " +
//                " from "+JREPORT.getRTNbyPO("SBDW",PO) + suffixYear +
//                " order by SBDW_ZBDW";
//            rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//            String SBDW_DWBH, SBDW_ZBDW, SBDW_TYPE;
//            while (rs.next()) {
//                SBDW_ZBDW = rs.getString(1);
//                SBDW_TYPE = rs.getString(2);
//                SBDW_DWBH = rs.getString(3);
//                Vector v = new Vector(3);
//                v.add(SBDW_ZBDW);
//                v.add(SBDW_TYPE);
//                v.add(SBDW_DWBH);
//                dataV.add(v);
//            }
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//        return dataV;
//    }
//
//    /**
//     * 根据单位编号获取单位名称
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @return Object
//     */
//    public static Object getUnitNameByCode(JConnection conn, JParamObject PO, Object unit) throws Exception {
//        PreparedStatement pst1 = null;
//        PreparedStatement pst2 = null;
//        ResultSet rs = null;
//        Vector dataV = new Vector();
//        try {
//            String suffixYear = PO.GetValueByEnvName("ReportSuffixyear", "");
//            String strSQL1 = " select DWZD_MC from "+JREPORT.getRTNbyPO("DWZD",PO) + suffixYear + " where ? like DWZD_BH ||'%' order by DWZD_BH";
//            String strSQL2 = " select LBZD_MC from "+JREPORT.getRTNbyPO("LBZD",PO) + suffixYear + " where ? like LBZD_BH ||'%' order by LBZD_BH";
//            pst1 = conn.prepareStatement(strSQL1);
//            pst2 = conn.prepareStatement(strSQL2);
//
//            Object[] datas = (Object[])unit;
//            ArrayList unitList = (ArrayList)datas[1];
//            ArrayList unitType = (ArrayList)datas[0];
//            for (int i = 0, n = unitList.size(); i < n; i++) {
//                String unitBH = (String) unitList.get(i);
//                String unitLB = (String) unitType.get(i);
//                if ("2".equals(unitLB)) {
//                    pst1.setString(1, unitBH);
//                    rs = pst1.executeQuery();
//                } else {
//                    pst2.setString(1, unitBH);
//                    rs = pst2.executeQuery();
//                }
//                StringBuffer sb = new StringBuffer();
//                while (rs.next()) {
//                    sb.append("/" + rs.getString(1));
//                }
//                String value = sb.toString();
//                if(value.length() > 1){
//                    value = value.substring(1);
//                }
//                dataV.add(value);
//            }
//        } finally {
//            conn.BackStatement(pst1, rs);
//        }
//        return dataV;
//    }
//
//    /**
//     * 更新系统参数：类别字典编码结构、批量计算个数、调度监测时间间隔
//     *
//     * @param conn JConnection
//     * @param   PO JParamObject
//     * @return     Object
//     */
//    public static Object updateSystemParam(JConnection conn, JParamObject PO) {
//        Statement stmt = null;
//        ArrayList paramKeyList = new ArrayList();
//        ArrayList paramValList = new ArrayList();
//        String sysParamKeyValue = null;
//        String sysParamValValue = null;
//        try {
//            stmt = conn.createStatement();
//            String strSQL = "";
//			DCTMetaData meta;
//			String stru = "";
//			try {
//				meta = (DCTMetaData) MetaDataManager.getDCTDataManager().
//					getMetaData(JREPORT.getRTNbyPO("LBZD", PO));
//				stru = meta.getDCT_BMSTRU();
//			}
//			catch (Exception ex) {
//			}
//			if ( stru.equals("") ) {
//				stru = PO.GetValueByEnvName("RT_LBSTRU","22222");
//			}
//
//            sysParamValValue = stru;
//            paramKeyList.add("RT_LBSTRU");
//            paramValList.add(sysParamValValue);
//            sysParamValValue = PO.GetValueByParamName("RPT_THREAD", "");
//            paramKeyList.add("RPT_THREAD");
//            paramValList.add(sysParamValValue);
//            sysParamValValue = PO.GetValueByParamName("RPT_REFRESH", "");
//            paramKeyList.add("RPT_REFRESH");
//            paramValList.add(sysParamValValue);
//            sysParamValValue = PO.GetValueByParamName("RPT_AUTO_CALC", "0");
//            paramKeyList.add("RPT_AUTO_CALC");
//            paramValList.add(sysParamValValue);
//            sysParamValValue = PO.GetValueByParamName("RPT_CALC_CHECK", "0");
//            paramKeyList.add("RPT_CALC_CHECK");
//            paramValList.add(sysParamValValue);
//            sysParamValValue = PO.GetValueByParamName("RPT_GZSBKZ", "0");
//            paramKeyList.add("RPT_GZSBKZ");
//            paramValList.add(sysParamValValue);
//            sysParamValValue = PO.GetValueByParamName("RPT_GZSBBB", "");
//            paramKeyList.add("RPT_GZSBBB");
//            paramValList.add(sysParamValValue);
//            Iterator itKey = paramKeyList.iterator();
//            Iterator itVal = paramValList.iterator();
//            while (itKey.hasNext()) {
//                sysParamKeyValue = (String) itKey.next();
//                sysParamValValue = (String) itVal.next();
//                strSQL = "delete from BSCONF where F_VKEY = '" + sysParamKeyValue + "'";
//                stmt.addBatch(strSQL);
//                strSQL = "insert into BSCONF(F_VKEY,F_VAL)values('" + sysParamKeyValue + "','" + sysParamValValue + "')";
//                stmt.addBatch(strSQL);
//            }
//            stmt.executeBatch();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            conn.BackStatement(stmt, null);
//        }
//
//        return "OK";
//    }
//
//    /**
//     * 获取报表分组信息
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @return Object
//     */
//    public static Object getReportGroupData(JConnection conn, JParamObject PO) throws Exception {
//        Statement st = null;
//        ResultSet rs = null;
//        ArrayList groupList = new ArrayList();
//        ArrayList dataList = new ArrayList();
//        HashMap groupMap = new HashMap();
//        String strSql, bh, mc, suffixYear,UNIT_ID;
//
//        try {
//            st = conn.createStatement();
//            suffixYear = PO.GetValueByEnvName("ReportSuffixyear", "");
//            UNIT_ID = PO.GetValueByEnvName("UNIT_ID","");
//
//            // 取分组信息
//            strSql =
//                " select GPZD_BH,GPZD_MC " +
//                " from "+JREPORT.getRTNbyPO("GPZD",PO) + suffixYear +" WHERE (UNIT_ID IS NULL OR UNIT_ID ='"+UNIT_ID+"')"+
//                " order by GPZD_BH";
//            rs = st.executeQuery(strSql);
//            while (rs.next()) {
//                bh = rs.getString(1);
//                mc = rs.getString(2);
//                ArrayList list = new ArrayList(2);
//                list.add(bh);
//                list.add(mc);
//                groupList.add(list);
//            }
//            rs.close();
//            dataList.add(groupList);
//
//            // 取分组成员信息
//            String oldBh = "";
//            String bbbh, gpbh = "";
//            strSql = "select GPZD_BH,BBZD_BH " +
//                " from "+JREPORT.getRTNbyPO("GPBB",PO) + suffixYear +
//                " order by GPZD_BH";
//            rs = st.executeQuery(strSql);
//            ArrayList list = new ArrayList();
//            while (rs.next()) {
//                gpbh = rs.getString(1);
//                bbbh = rs.getString(2);
//                if (oldBh.equals("")) {
//                    oldBh = gpbh;
//                }
//                if (!gpbh.equals(oldBh)) {
//                    groupMap.put(oldBh, list);
//                    list = new ArrayList();
//                    list.add(bbbh);
//                    oldBh = gpbh;
//                } else {
//                    list.add(bbbh);
//                }
//            }
//            if (list.size() > 0) {
//                groupMap.put(gpbh, list);
//            }
//            dataList.add(groupMap);
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//        return dataList;
//    }
//
//    /**
//     * 获取某张表的预处理公式结果
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @return HashMap
//     */
//    public static HashMap getPreFunctionResult(JConnection conn, JParamObject PO) {
//        JPrepareFunctionService pfsService = new JPrepareFunctionService();
//        return pfsService.getPreFunctionResultMap(conn, PO);
//    }
//
//    /**
//     * 取某个单位编号的直接下级数据
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @return Object
//     */
//    public static Object getUnitDirectChildren(JConnection conn, JParamObject PO) throws Exception{
//        Statement st = null;
//        ResultSet rs = null;
//        Vector dataV = new Vector();
//        try {
//            st = conn.createStatement();
//            String add_bh = PO.GetValueByParamName("ADD_BH", "");
//            int  add_type = PO.GetIntByParamName("ADD_TYPE", 5);
//            String tableName = "", bhCol = "", mcCol = "", jsCol = "";
//            switch (add_type) {
//                case 4:
//                    bhCol = "LBZD_BH";
//                    mcCol = "LBZD_MC";
//                    jsCol = "LBZD_JS";
//                    tableName = JREPORT.getRTNbyPO("LBZD",PO);
//                    break;
//                case 5:
//                    bhCol = "DWZD_BH";
//                    mcCol = "DWZD_MC";
//                    jsCol = "DWZD_JS";
//                    tableName = JREPORT.getRTNbyPO("DWZD",PO);
//                    break;
//                case 6:
//                    bhCol = "BMZD_BH";
//                    mcCol = "BMZD_MC";
//                    jsCol = "BMZD_JS";
//                    tableName = JREPORT.getRTNbyPO("BMZD",PO);
//                    break;
//                case 7:
//                    bhCol = "CBZD_BH";
//                    mcCol = "CBZD_MC";
//                    jsCol = "BBZD_JS";
//                    tableName = JREPORT.getRTNbyPO("CBZD",PO);
//                    break;
//            }
//            String strSQL =
//                " select " + bhCol + "," + mcCol + "," + jsCol +
//                " from " + tableName +
//                " where " + bhCol + " like '" + add_bh + "%'" +
//                " order by " + bhCol;
//            rs = st.executeQuery(strSQL);
//            int referJs = 0;
//            String bh, mc, js;
//            while (rs.next()) {
//                bh = rs.getString(1);
//                mc = rs.getString(2);
//                js = rs.getString(3);
//                if (bh.equals(add_bh)) {
//                    referJs = Integer.parseInt(js);
//                }
//                if (referJs + 1 == Integer.parseInt(js)) {
//                    String[] data = new String[2];
//                    data[0] = bh;
//                    data[1] = mc;
//                    dataV.add(data);
//                }
//            }
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//        return dataV;
//    }
//
//    /**
//     * 取报表周期性质条件
//     * 1,2,4,5,7,8,10,11 计算月报
//     * 3,9 月报 + 季报
//     * 6   月报 + 季报 + 半年报
//     * 其余计算所有
//     * @param preFix String
//     * @param date String
//     * @return String
//     */
//    public static String getReportAttrWhere(String colName,String date){
//        String mon  = date.substring(4,6);
//        int    nMon = Integer.parseInt(mon);
//        String where = "";
//
//        switch(nMon){
//            case 1:
//            case 2:
//            case 4:
//            case 5:
//            case 7:
//            case 8:
//            case 10:
//            case 11:
//                // 月报条件
//                where = " and " + colName + " = '1'";
//                break;
//
//            case 3:
//            case 9:
//                // 月报 + 季报
//                where = " and (" + colName + " = '1' or " + colName + " = '4')";
//
//            case 6:
//                // 月报 + 季报 + 半年报
//                where = " and (" + colName + " = '1' or " + colName + " = '4' or " + colName + " = '5')";
//        }
//
//        return where;
//    }
//
//
//    /**
//     * 取当前数据库安装的子系统
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @return Object
//     */
//    public static Object getSystemList(JConnection conn) throws Exception{
//        String value;
//        Statement stmt = null;
//        ResultSet rs   = null;
//        ArrayList sysList = new ArrayList();
//        try{
//            stmt = conn.GetStatement(conn);
//            String strSql = "select F_XTBH from LSXTMC";
//            rs = stmt.executeQuery(strSql);
//            while(rs.next()){
//                value = rs.getString(1).trim();
//                sysList.add(value);
//            }
//            return sysList;
//        }finally{
//            conn.BackStatement(stmt,rs);
//        }
//    }
//	/**
//	 *
//	 *
//	 * @param conn JConnection
//	 */
//	public static String getWJZD(JConnection conn, String sTabName, String sColName) throws Exception {
//		Statement st = null;
//		ResultSet rs = null;
//		String sRes = "";
//
//	    String skey = sTabName+"_"+sColName;
//		Object obj = zdMessMap.get(skey);
//		if ( obj != null ) {
//			sRes = (String) obj;
//			return sRes;
//		}
//		try {
//			st = conn.GetStatement(conn);
//			String    strSQL =
//				"  select COL_FOBJ from sys_objcols where OBJ_ID = '"+sTabName+"' AND COL_ID = '"+sColName+"' ";
//			rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//			if (rs.next()) {
//				sRes = rs.getString("COL_FOBJ");
//			}
//			zdMessMap.put(skey,sRes);
//		} finally {
//			conn.BackStatement(st, rs);
//		}
//		return sRes;
//	}
//
//	/**
//	 *
//	 *
//	 * @param conn JConnection
//	 */
//	public static String getDWJS(JConnection conn, String sTabName, String sColName) throws Exception {
//		Statement st = null;
//		ResultSet rs = null;
//		String sRes = "";
//
//		String skey = sTabName+"_"+sColName;
//		Object obj = zdMessMap.get(skey);
//		if ( obj != null ) {
//			sRes = (String) obj;
//			return sRes;
//		}
//		try {
//			st = conn.GetStatement(conn);
//			String    strSQL =
//				"  select COL_FOBJ from sys_objcols where OBJ_ID = '"+sTabName+"' AND COL_ID = '"+sColName+"' ";
//			rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//			if (rs.next()) {
//				sRes = rs.getString("COL_FOBJ");
//			}
//			zdMessMap.put(skey,sRes);
//		} finally {
//			conn.BackStatement(st, rs);
//		}
//		return sRes;
//	}
//
//	public static String getZdbhCol(JConnection conn, String sTabName) throws Exception {
//		Statement st = null;
//		ResultSet rs = null;
//		String sRes = "";
//		String bhCol="",mcCol="";
//
//		String bhkey = sTabName+"_BHCOL";
//		String mckey = sTabName+"_MCCOL";
//		Object obj = zdMessMap.get(bhkey);
//		if ( obj != null ) {
//			sRes = (String) obj;
//			return sRes;
//		}
//		try {
//			st = conn.GetStatement(conn);
//			String    strSQL =
//				"  select DCT_BMCOLID,DCT_MCCOLID from sys_dicts where OBJ_ID = '"+sTabName+"'";
//			rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//			if (rs.next()) {
//				bhCol = rs.getString("DCT_BMCOLID");
//				mcCol = rs.getString("DCT_MCCOLID");
//				sRes = bhCol;
//			}
//			zdMessMap.put(bhkey,bhCol);
//			zdMessMap.put(mckey,mcCol);
//		} finally {
//			conn.BackStatement(st, rs);
//		}
//		return sRes;
//	}
//	public static String getZdMcCol(JConnection conn, String sTabName) throws Exception {
//		Statement st = null;
//		ResultSet rs = null;
//		String sRes = "";
//		String bhCol="",mcCol="";
//
//		String bhkey = sTabName+"_BHCOL";
//		String mckey = sTabName+"_MCCOL";
//		Object obj = zdMessMap.get(mckey);
//		if ( obj != null ) {
//			sRes = (String) obj;
//			return sRes;
//		}
//		try {
//			st = conn.GetStatement(conn);
//			String    strSQL =
//				"  select DCT_BMCOLID,DCT_MCCOLID from sys_dicts where OBJ_ID = '"+sTabName+"'";
//			rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//			if (rs.next()) {
//				bhCol = rs.getString("DCT_BMCOLID");
//				mcCol = rs.getString("DCT_MCCOLID");
//				sRes = mcCol;
//			}
//			zdMessMap.put(bhkey,bhCol);
//			zdMessMap.put(mckey,mcCol);
//		} finally {
//			conn.BackStatement(st, rs);
//		}
//		return sRes;
//	}
//    /**
//     * 报告处临时用
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @param Data Object
//     * @return Object
//     */
//    public static Object getFunction(JConnection conn, JParamObject PO, Object Data) throws Exception {
//        Statement st = null;
//        ResultSet rs = null;
//        try {
//            st = conn.GetStatement(conn);
//            JFormulaStub  FS = new JFormulaStub();
//            String   BBZD_BH = PO.GetValueByParamName("BBZD_BH", "");
//            String BBZD_DATE = PO.GetValueByParamName("BBZD_DATE", "");
//            String    strSQL =
//                " select DYZD.*,HZD.HZD_ZB,LZD.LZD_ZB " +
//                " from "+JREPORT.getRTNbyPO("DYZD",PO)+" DYZD,"+JREPORT.getRTNbyPO("HZD",PO)+" HZD,"+JREPORT.getRTNbyPO("LZD",PO)+" LZD " +
//                " where DYZD.BBZD_BH = HZD.BBZD_BH   and DYZD.BBZD_BH   = LZD.BBZD_BH " +
//                " and DYZD.BBZD_DATE = HZD.BBZD_DATE and DYZD.BBZD_DATE = LZD.BBZD_DATE " +
//                " and  DYZD.HZD_ORDE = HZD.HZD_ORDE  and DYZD.LZD_ORDE  = LZD.LZD_ORDE " +
//                " and isnull(DYZD.DYZD_GSX,'') <> '' and   DYZD.BBZD_BH = '" + BBZD_BH + "' and DYZD.BBZD_DATE = '" + BBZD_DATE + "'" +
//                " order by convert(int,DYZD.HZD_ORDE),convert(int,DYZD.LZD_ORDE)";
//            rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//            String GSX = "";
//            while (rs.next()) {
//                GSX  = rs.getString("DYZD_GSX")  == null ? "" : rs.getString("DYZD_GSX");
//                GSX += rs.getString("DYZD_GSX1") == null ? "" : rs.getString("DYZD_GSX1");
//                GSX += rs.getString("DYZD_GSX2") == null ? "" : rs.getString("DYZD_GSX2");
//                GSX += rs.getString("DYZD_GSX3") == null ? "" : rs.getString("DYZD_GSX3");
//                GSX += rs.getString("DYZD_GSX4") == null ? "" : rs.getString("DYZD_GSX4");
//                GSX += rs.getString("DYZD_GSX5") == null ? "" : rs.getString("DYZD_GSX5");
//
//                if (isRequireModified(GSX)) {
//                    FS.BBZD_BH = rs.getString("BBZD_BH");
//                    FS.BBZD_DATE = rs.getString("BBZD_DATE");
//                    FS.DYZD_GSX = GSX;
//                    FS.HZD_ORDE = rs.getString("HZD_ORDE");
//                    FS.LZD_ORDE = rs.getString("LZD_ORDE");
//                    FS.HZD_ZB   = rs.getInt("HZD_ZB");
//                    FS.LZD_ZB   = rs.getInt("LZD_ZB");
//                    return FS;
//                }
//            }
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//        return null;
//    }
//
//    /**
//     * 报告处临时用
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @param Data Object
//     * @return Object
//     */
//    public static Object saveFunction(JConnection conn, JParamObject PO, Object Data) throws Exception {
//        Statement st = null;
//        ResultSet rs = null;
//        try {
//            st = conn.GetStatement(conn);
//            JFormulaStub  FS = (JFormulaStub) Data;
//            String   BBZD_BH = FS.BBZD_BH;
//            String BBZD_DATE = FS.BBZD_DATE;
//            String  HZD_ORDE = FS.HZD_ORDE;
//            String  LZD_ORDE = FS.LZD_ORDE;
//            String       GSX = FS.DYZD_GSX;
//
//            String[]  pArray = JReportPubFunc.SplitStringDB(GSX, Integer.parseInt(PO.GetValueByEnvName("RPT_GSLEN", "500")), 6);
//            pArray[0] = StringFunction.replaceString(pArray[0], "\'", JReportMask.CHAR_QUOTA);
//            pArray[1] = StringFunction.replaceString(pArray[1], "\'", JReportMask.CHAR_QUOTA);
//            pArray[2] = StringFunction.replaceString(pArray[2], "\'", JReportMask.CHAR_QUOTA);
//            pArray[3] = StringFunction.replaceString(pArray[3], "\'", JReportMask.CHAR_QUOTA);
//            pArray[4] = StringFunction.replaceString(pArray[4], "\'", JReportMask.CHAR_QUOTA);
//            pArray[5] = StringFunction.replaceString(pArray[5], "\'", JReportMask.CHAR_QUOTA);
//
//            String strSQL =
//                " update "+JREPORT.getRTNbyPO("DYZD",PO)+" set DYZD_GSX = '" + pArray[0] + "',DYZD_GSX1 = '" + pArray[1] + "'," +
//                " DYZD_GSX2 = '" + pArray[2] + "',DYZD_GSX3 = '" + pArray[3] + "',DYZD_GSX4 = '" + pArray[4] + "',DYZD_GSX5 = '" + pArray[5] + "'" +
//                " where BBZD_BH = '" + BBZD_BH + "' and BBZD_DATE = '" + BBZD_DATE + "'" +
//                " and  HZD_ORDE = '" + HZD_ORDE + "' and LZD_ORDE  = '" + LZD_ORDE + "'";
//            st.executeUpdate(JConvertSql.setSQLHeadTagOfNoConvert(strSQL));
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//        return null;
//    }
//
//    private static boolean isRequireModified(String gsx) {
//        try {
//            List pList = getReportBH(gsx);
//            for (Iterator it = pList.iterator(); it.hasNext(); ) {
//                String pBH = (String) it.next();
//                if (pBH.trim().length() > 4) {
//                    return true;
//                }
//            }
//        } catch (Exception ex) {
//        }
//        return false;
//    }
//
//
//    public static List getReportBH(String gsx) throws Exception {
//        String  FunctionTag ="+-*/()><=,;?:~^&$%@#!`.'";
//        List       reportBH = new ArrayList();
//        Stack   SourceStack = new Stack();
//        String[] pValueList;
//        int i = 0, b = 0, f = 0;
//        String sTmp1, sTmp2, FTag, FString, F1String;
//        F1String = gsx;
//        while (i < F1String.length()) {
//            sTmp1 = F1String.substring(i, i + 1);
//            // 如果等"("压栈
//            if (sTmp1.equals("(")) {
//                SourceStack.push(String.valueOf(i));
//            }
//            // 括号封闭
//            if (sTmp1.equals(")")) {
//                sTmp2 = (String) SourceStack.pop();
//                b = Integer.valueOf(sTmp2).intValue();
//                f = b - 1;
//                // 找出函数的左边的开始位置
//                while (f >= 0) {
//                    sTmp2 = F1String.substring(f, f + 1);
//                    if (FunctionTag.indexOf(sTmp2) != -1)
//                        break;
//                    else
//                        f--;
//                }
//                f++;
//                FTag = F1String.substring(f, b);
//                FString = F1String.substring(f, i + 1);
//                pValueList = FString.split(",");
//                if (pValueList != null && pValueList.length > 2) {
//                    String pVal = pValueList[2].trim().toUpperCase();
//                    if (!pVal.equals("DEFAULT") &&
//                        ! (pVal.length() == 6 && pVal.endsWith("01")) &&
//                        ! ("C22202".equals(pVal) || "C23302".equals(pVal) || "G00302".equals(pVal))) {
//                        reportBH.add(pValueList[2]);
//                    }
//                }
//                if (FTag != null) {
//                    F1String = F1String.substring(0, f) + FTag + F1String.substring(i + 1, F1String.length());
//                    // i从替换后听位置处开始处理
//                    i = f + FTag.length();
//                    continue;
//                } else {
//                    i = f + FString.length();
//                    continue;
//                }
//            }
//            i++;
//        }
//        if (SourceStack.size() != 0) {
//            throw new Exception("公式定义错误,左右括号不匹配!");
//        }
//        return reportBH;
//    }
//
//
//    /**
//     * 获取附加信息列表
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @param Data Object
//     * @return ArrayList
//     * @throws Exception
//     */
//    public static ArrayList getAddInfoTypeList(JConnection conn, JParamObject PO) throws Exception {
//        ResultSet rs   = null;
//        Statement stmt = null;
//        ArrayList list = new ArrayList();
//        String strSql,bh,mc,where,date,sbzq,sbbz;
//
//        try{
//            date   = PO.GetValueByParamName("BBZD_DATE","");
//            sbbz   = PO.GetValueByParamName("SBBZ","0");
//            where  = getReportAttrWhere("ADD_SBZQ",date);
//
//            strSql = "select ADD_ID,ADD_MC,ADD_SBZQ from "+JREPORT.getRTNbyPO("ADDLX",PO)+" where 2 > 1 ";
//            // 需要上报
//            if(sbbz.equals("1")){
//                where  = getReportAttrWhere("ADD_SBZQ",date);
//                strSql += where;
//            }
//            stmt   = conn.createStatement();
//            rs = stmt.executeQuery(strSql);
//            while(rs.next()){
//                bh = rs.getString(1);
//                mc = rs.getString(2);
//                sbzq = rs.getString(3);
//                JAddInfoTypeStub stub = new JAddInfoTypeStub(bh,mc,sbzq);
//                list.add(stub);
//            }
//        }finally{
//            conn.BackStatement(stmt,rs);
//        }
//        return list;
//    }
//
//    /**
//     * 保存附加类型信息列表
//     * 主要用于从总部报表同步附加信息目录时使用
//     * add by hufeng 2008.11.19
//     * @param conn JConnection
//     * @param typeList ArrayList
//     * @throws Exception
//     */
//    public static void saveAddInfoTypeList(JConnection conn, ArrayList typeList) throws Exception {
//        int count = 0;
//        ResultSet rs   = null;
//        Statement stmt = null;
//        JAddInfoTypeStub typeStub = null;
//        String strSql,bh,mc,sbzq;
//
//        try{
//            conn.setAutoCommit(false);
//            stmt = conn.GetStatement(conn);
//            for(Iterator it = typeList.iterator(); it.hasNext(); ){
//                count ++;
//                typeStub = (JAddInfoTypeStub)it.next();
//                bh = typeStub.getADD_ID();
//                mc = typeStub.getADD_MC();
//                sbzq = typeStub.getADD_SBZQ();
//                // 先删除
//                strSql = "delete from "+JREPORT.getRTNbyPO("ADDLX",null)+" where ADD_ID = '" + bh + "'";
//                stmt.addBatch(strSql);
//                // 再插入
//                strSql = "insert into "+JREPORT.getRTNbyPO("ADDLX",null)+"(ADD_ID,ADD_MC,ADD_SBZQ) " +
//                    " values('" + bh + "','" + mc + "','" + sbzq + "')";
//                stmt.addBatch(strSql);
//            }
//            if(count > 0){
//                stmt.executeBatch();
//                conn.commit();
//            }
//        }catch(Exception e){
//            conn.rollback();
//            throw e;
//        }finally{
//            conn.setAutoCommit(true);
//            conn.BackStatement(stmt,rs);
//        }
//    }
//
//    /**
//     * 判断BSCONF中是否含有某个变量，没有这条记录或者值为空，都认为不存在
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @param Data Object
//     * @return Object
//     */
//    public static Object isExistsInBSCONF(JConnection conn, JParamObject PO, Object Data) throws Exception {
//        ResultSet rs = null;
//        Statement st = null;
//        try {
//            st = conn.createStatement();
//            String key = PO.GetValueByParamName("PARAM_NAME", "");
//            String strSql = "select * from BSCONF where F_VKEY = '" + key + "'";
//            rs = st.executeQuery(strSql);
//            if (rs.next()) {
//                String value = rs.getString("F_VAL");
//                value = value == null ? "" : value.trim();
//                if (value.trim().length() == 0) {
//                    return new Boolean(false);
//                }
//                return new Boolean(true);
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//        return new Boolean(false);
//    }
//    /**
//     * 根据责任中心编号判断是否为虚拟责任中心，并返回取数表名或视图名
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @param Data Object
//     * @return Object
//     */
//    public static String getRealNameByZrzx(JConnection conn,String sTabName,String sZrzx ,String sDwbh) {
//        String strSql, sTrue="1", where = "";
//		int  iFmx = 0;
//        String sRealName = sTabName;
//        ResultSet rs = null;
//        Statement stmt = null;
//		PreparedStatement pStmt= null;
//		// 虚拟责任中心和合并口径的数据从 sTabName_V 里取
//		// 汇总口径数据从sTabName_H 里取
//        try {
//			Object obj = DwJsMap.get(sZrzx);
//			if ( obj != null ) {
//				return sRealName+((String)obj).trim(); 
//			}
//			stmt = conn.createStatement();
///* liukun 20110518 非明细汇总这条路大概走不通了
//			strSql = " select 1 FROM  ACZRZX where F_CODE  = ? " +
//				" AND F_MX = 0 AND EXISTS ( select 1 from SYS_MDL_VAL where SYS_MDL_VAL.MDL_KEY = 'JZHZJS' "+
//				" and SYS_MDL_VAL.MDL_ID = 'ACPZModel' and ACZRZX.UNIT_ID=SYS_MDL_VAL.UNIT_ID and  " +
//				" ACZRZX.F_JS >= to_number(NVL(trim(SYS_MDL_VAL.MDL_VALUE),99))) " ;
//			pStmt = conn.prepareStatement(strSql);
//			pStmt.setString(1, sZrzx);
//			rs = pStmt.executeQuery();
//			if ( rs.next() ) {
//				iFmx = rs.getInt(1);
//			}
//*/
//			strSql = "select F_TRUE,F_MX from ACZRZX  where F_CODE = '" + sZrzx + "'";
//			rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//			sTrue = "1";
//			if (rs.next()) {
//				sTrue = rs.getString(1);
//			}
//			if (sTrue.equals("0") || sTrue.equals("2")) {
//			//	bisViurs = true;
//				DwJsMap.put(sZrzx,"_V");
//				return sRealName+"_V"; //虚拟责任中心直接返回_V;
//			} else {
//				DwJsMap.put(sZrzx," ");
//				return sRealName;
//			}
//
////			if ( iFmx == 1 ) {
////				DwJsMap.put(sZrzx,"_FMX");
////				return sRealName+"_FMX";
////			}
//       }
//       catch (Exception e) {
//           e.printStackTrace();
//       } finally {
//		   conn.BackPreparedStatement(pStmt, rs);
//	   }
//      return sRealName;
//    }
//	/**
//	 * 根据责任中心编号判断是否为虚拟责任中心，并返回取数表名或视图名
//	 *
//	 * @param conn JConnection
//	 * @param PO JParamObject
//	 * @param Data Object
//	 * @return Object
//	 */
//	public static String getKeyDict(JConnection conn,String sTabName,String sColName) {
//		String strSql, value="", where = "";
//		ResultSet rs = null;
//		Statement stmt = null;
//		try {
//			stmt = conn.createStatement();
//			strSql = "select COL_FOBJ from sys_objcols where obj_id = '"+sTabName+"' and col_id = '"+sColName+"' ";
//			rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//			if (rs.next()) {
//				value = rs.getString(1);
//			}
//	   }
//	   catch (Exception e) {
//		   e.printStackTrace();
//	   } finally {
//		   conn.BackStatement(stmt, rs);
//	   }
//	  return value;
//    }
//	/**
//	 * 判断一个指标类别的某一列，是否存在公式
//	 *
//	 * @param conn JConnection
//	 * @param PO JParamObject
//	 * @param Data Object
//	 * @return Object
//	 */
//	public static boolean IsExistExp(JConnection conn,String sYear,String sZblb,String sColName) {
//		String strSql, value="", where = "";
//		boolean bHaveExp = false;
//		ResultSet rs = null;
//		Statement stmt = null;
//		try {
//			stmt = conn.createStatement();
//			strSql =
//				" select DIM.F_EXPRESS  "
//				+ " from SYS_MDL_DIMSET_BBZBMODEL DIM,SYS_MDL_KEYSET_BBZBMODEL K, ZBLBZD ZD,SYS_OBJCOLS COL where "
//				+ " DIM.CST_ID = K.CST_ID "
//				+ " 	 AND DIM.F_YEAR = K.F_YEAR "
//				+ "      AND K.KEY_BM = ZD.F_LBBH "		
////				+ " AND DIM_ID IN ( "
////				+ "     select COL_ID from sys_objcols where OBJ_ID = 'ZBMDSJ' ) "
//				+ " AND DIM.F_YEAR = '" + sYear + "' "
//				+ " AND ZD.F_LBBH = '"+sZblb+"' "
//				+ " AND COL.COL_ID = DIM.DIM_ID "
//				+ " AND COL.OBJ_ID = DIM.FCT_ID "
//				+ " AND COL.COL_ID = '"+sColName+"' ";
//			rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//			if (rs.next()) {
//				value = rs.getString(1);
//			}
//			if ( !value.trim().equals("")) {
//				bHaveExp = true;
//			}
//	   }
//	   catch (Exception e) {
//		   e.printStackTrace();
//	   } finally {
//		   conn.BackStatement(stmt, rs);
//	   }
//	  return bHaveExp;
//    }
//
//	
//	
//	public static String getMJNM(JConnection conn,String sZblb,String sColName,String sYear) {
//		String strSql, value="", where = "";
//		ResultSet rs = null;
//		Statement stmt = null;
//		try {
//			stmt = conn.createStatement();
//			strSql =
//				" select DIM.F_SRCCONT " 
//				+" from SYS_MDL_DIMSET_BBZBMODEL DIM,SYS_MDL_KEYSET_BBZBMODEL K "
//				+" where DIM.CST_ID = K.CST_ID "
//				+"	 AND DIM.F_YEAR = K.F_YEAR "
//				+"   AND K.KEY_BM = '"+sZblb+"' "
//				+"   AND DIM.F_YEAR = '"+sYear+"' "
//				+"   AND DIM.DIM_ID = '"+sColName+"' ";
//			rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//			if (rs.next()) {
//				value = rs.getString(1);
//			}
//			if ( value.indexOf("MJZD_NAME")>-1) {
//				value = value.substring(value.indexOf("MJZD_NAME")+10);
//				value = value.replaceAll("=", "").trim();
//			}
//	   }
//	   catch (Exception e) {
//		   e.printStackTrace();
//	   } finally {
//		   conn.BackStatement(stmt, rs);
//	   }
//	  return value;
//    }	
//    /**
//     * getJygsMaxOrder
//     *
//     * @param conn JConnection
//     * @param PO JParamObject
//     * @return Object
//     */
//    public static Object getJygsMaxOrder(JConnection conn, JParamObject PO) throws Exception {
//        ResultSet rs = null;
//        Statement st = null;
//        try {
//            st = conn.createStatement();
//            String code = PO.GetValueByParamName("BBZD_BH", "");
//            String date = PO.GetValueByParamName("BBZD_DATE", "");
//            String strSql =
//                " select max(convert(int,JYGS_ORDE)) as JYGS_ORDE from  "+JREPORT.getRTNbyPO("JYGS",PO) +
//                " where BBZD_BH = '" + code + "' and BBZD_DATE = '" + date + "'";
//            rs = st.executeQuery(strSql);
//            if (rs.next()) {
//                String value = rs.getString("JYGS_ORDE");
//                if (value == null || value.trim().length() == 0)
//                    value = "0";
//                Integer i = new Integer(value);
//                return i;
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            conn.BackStatement(st, rs);
//        }
//        return null;
//    }
//
//	/**
//	 * getFunctionLimit
//	 *
//	 * @param conn JConnection
//	 * @param PO JParamObject
//	 * @return Object
//	 */
//	public static Object getFunctionLimit(JConnection conn, JParamObject PO) throws Exception {
//		ResultSet rs = null;
//		Statement st = null;
//		try {
//			st = conn.createStatement();
//			String gnbh = PO.GetValueByParamName("FunctionNo", "");
//			String zgbh = PO.GetValueByEnvName("UserName");
//
//			String strSql =
//				" SELECT F_G FROM BSUSGN WHERE  "+
//				" ( (F_ZGBH = '" + zgbh + "' and F_GNBH = '" + gnbh + "')" +
//				" or exists (SELECT 1 FROM BSUSERROLE WHERE F_ZGBH = '"+zgbh+"' and F_GNBH = '" + gnbh + "'"+
//			    " and BSUSERROLE.F_ROLECODE = BSUSGN.F_ZGBH )) ";
//
//			rs = st.executeQuery(strSql);
//			if (rs.next()) {
//				String value = rs.getString(1);
//				if (value == null || value.trim().length() == 0)
//					value = "0";
//				Integer i = new Integer(value);
//				return i;
//			}
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			conn.BackStatement(st, rs);
//		}
//		return 0;
//	}
//	/**
//	 * getCalSvrName
//	 * 根据
//	 * @param conn JConnection
//	 * @param PO JParamObject
//	 * @return Object
//	 */
//	public static String getCalSvrName(JConnection conn, JParamObject PO) {
//		ResultSet rs = null;
//		Statement st = null;
//		String sRes   = "";
//		String sDwRes = "";
//		try {
//			st = conn.createStatement();
//			int  addType   =  PO.GetIntByParamName("ADD_TYPE", 0);
//			String addBh   = PO.GetValueByParamName("ADD_BH","");
//			String sBbbh   = PO.GetValueByParamName("BBZD_BH","");
//			String tableName="",columnName="",strSql;
//			if (!(addType==5 || addType==6 || addType==7)) //不是单位、部门、成本中心
//				return "";
//			switch (addType) {
//				case 5:
//					tableName = JREPORT.getRTNbyPO("DWSB",PO);
//					columnName = "DWZD_BH";
//					break;
//				case 6:
//					tableName = JREPORT.getRTNbyPO("BMBB",PO);
//					columnName = "BMZD_BH";
//					break;
//				case 7:
//					tableName = JREPORT.getRTNbyPO("CBBB",PO);
//					columnName = "CBZD_BH";
//					break;
//			}
//			strSql = " select CAL_SOUCE from " + tableName + " SB," +
//				JREPORT.getRTNbyPO("JSLXZD", PO) + " JSLX"
//				+ " where SB.CAL_BH = JSLX.CAL_BH(+) "
//				+ " and '" + addBh + "' like SB." + columnName +
//				"||'%' and SB.BBZD_BH='" + sBbbh + "'  order by "+columnName+" DESC ";
//			rs = st.executeQuery(strSql);
//			while ( rs.next() ) {
//				sDwRes =rs.getString("CAL_SOUCE");
//				if ( sDwRes==null ) {
//					continue;
//				} else {
//					sRes = sDwRes;
//					break;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			conn.BackStatement(st, rs);
//		}
//		// 目前暂定以报表上设置的取数规则级别优先
//		return sRes;
//	}
//
//    /**
// * 取得打印设置列表。
// */
//public static ArrayList getPrintSetList(JConnection conn, JParamObject PO) {
//
//    ArrayList list = new ArrayList();
//    JPrintSetStub setStub;
//    Statement st = null;
//    ResultSet rs = null;
//
//    try {
//        st = conn.createStatement();
//        String suffix = PO.GetValueByEnvName("ReportSuffixyear", "");
//        String strSQL =
//            " select A.DYSZ_BH,A.DYSZ_MC,A.DYSZ_YH,A.DYSZ_DY,A.DYSZ_QSZZ,A.DYSZ_ZZFX,A.DYSZ_ZZZX,A.DYSZ_QSSF,A.DYSZ_SF,A.DYSZ_YK,A.DYSZ_YG,A.DYSZ_FS,A.DYSZ_BZ,A.DYSZ_YLZD1,A.DYSZ_YLZD2  " +
//            " from "+JREPORT.getRTNbyPO("DYSZZD",PO) + suffix + " A " +
//            " where (1=1) " +
//            " order by A.DYSZ_BH";
//        rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//        while (rs.next()) {
//            setStub = new JPrintSetStub();
//            setStub.DYSZ_BH = rs.getString(1);
//            setStub.DYSZ_MC = rs.getString(2);
//            setStub.DYSZ_YH = rs.getString(3);
//            setStub.DYSZ_DY = rs.getString(4);
//            setStub.DYSZ_QSZZ = rs.getString(5);
//            setStub.DYSZ_ZZFX = rs.getString(6);
//            setStub.DYSZ_ZZZX = rs.getString(7);
//            setStub.DYSZ_QSSF = rs.getString(8);
//            setStub.DYSZ_SF = rs.getString(9);
//            setStub.DYSZ_YK = rs.getString(10);
//            setStub.DYSZ_YG = rs.getString(11);
//            setStub.DYSZ_FS = rs.getString(12);
//            setStub.DYSZ_BZ = rs.getString(13);
//            setStub.DYSZ_YLZD1 = rs.getString(14);
//            setStub.DYSZ_YLZD2 = rs.getString(15);
//
//            list.add(setStub);
//            setStub = null;
//        }
//    } catch (Exception ex) {
//        ex.printStackTrace();
//    } finally {
//        conn.BackStatement(st, rs);
//    }
//
//    return list;
//}
//  /**
//* 取得调整金额列表。
//*/
//public static ArrayList getAdjustMoneyList(JConnection conn, JParamObject PO) {
//
//   ArrayList list = new ArrayList();
//   JReportAdjustStub setStub;
//   Statement st = null;
//   ResultSet rs = null;
//
//
//   try {
//       st = conn.createStatement();
//       String suffix = PO.GetValueByEnvName("ReportSuffixyear", "");
//       String strSQL =
//           " select A.TZZD_BH,A.TZZD_MC,A.TZZD_FH,A.TZZD_XS,A.TZZD_JD,A.TZZD_DW  " +
//           " from "+JREPORT.getRTNbyPO("TZZD",PO) + suffix + " A " +
//           " where (1=1) "+
//           " order by A.TZZD_BH";
//       rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//       while (rs.next()) {
//           setStub = new JReportAdjustStub();
//           setStub.TZZD_BH = rs.getString(1);
//           setStub.TZZD_MC = rs.getString(2);
//           setStub.TZZD_FH = rs.getString(3);
//           setStub.TZZD_XS = rs.getString(4);
//           setStub.TZZD_JD = rs.getString(5);
//           setStub.TZZD_DW = rs.getString(6);
//
//
//
//           list.add(setStub);
//           setStub = null;
//       }
//   } catch (Exception ex) {
//       ex.printStackTrace();
//   } finally {
//       conn.BackStatement(st, rs);
//   }
//
//   return list;
//}
//public static ArrayList MonthlyYsYfYMQR(JConnection conn, JParamObject PO) {
//
//	   ArrayList list = new ArrayList();
//	   Statement st = null;
//	   ResultSet rs = null;
//       String DWZD_CODE = PO.GetValueByParamName("DWZD_CODE");
//       String BBZD_DATE = PO.GetValueByParamName("BBZD_DATE");
//
//
//	   try {
//	       st = conn.createStatement();
//	    //   String suffix = PO.GetValueByEnvName("ReportSuffixyear", "");
//	       String strSQL =
//	           " SELECT F_CODE||'_'||F_STMC FROM ACZRZX "+
//	           " WHERE exists ( select 1 from JYQRSJ where F_YFDW = ACZRZX.F_CODE "+
//	           " AND F_YFDW like  '"+DWZD_CODE+"%' AND F_KJQJ = '"+BBZD_DATE+"' AND F_YFQR = '0') "+
//	           " or exists ( select 1 from JYQRSJ where F_YSDW = ACZRZX.F_CODE "+
//	           " AND F_YSDW like  '"+DWZD_CODE+"%' AND F_KJQJ = '"+BBZD_DATE+"' AND F_YSQR = '0') ";
//	       rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//	       while (rs.next()) {
//	           list.add(rs.getString(1));
//	       }
//	   } catch (Exception ex) {
//	       ex.printStackTrace();
//	   } finally {
//	       conn.BackStatement(st, rs);
//	   }
//
//	   return list;
//	}
//
//    /**
//   * 取得报表
//   */
//            public static ArrayList getBHGZList(JConnection conn, JParamObject PO) {
//
//             ArrayList list = new ArrayList();
//             String gz = "";
//             Statement st = null;
//             ResultSet rs = null;
//             String UNIT_ID = PO.GetValueByEnvName("UNIT_ID","");
//             try {
//                     st = conn.createStatement();
//                     String suffix = PO.GetValueByEnvName("ReportSuffixyear", "");
//                     String strSQL =
//                             " select A.BHGZ_GZ  " +
//                             " from "+JREPORT.getRTNbyPO("BHGZ",PO) + suffix + " A " +
//                             " where (1=1) AND A.UNIT_ID = '" +UNIT_ID+"'"+
//                             " ";
//                     rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//                     while (rs.next()) {
//                              gz = rs.getString(1);
//                             list.add(gz);
//                             gz = "";
//                     }
//             } catch (Exception ex) {
//                     ex.printStackTrace();
//             } finally {
//                     conn.BackStatement(st, rs);
//             }
//
//             return list;
//            }
//
//              /**
//            * 取得报表
//            */
//            public static ArrayList getBHFFZFList(JConnection conn, JParamObject PO) {
//
//            ArrayList list = new ArrayList();
//            String gz = "";
//            Statement st = null;
//            ResultSet rs = null;
//            String UNIT_ID = PO.GetValueByEnvName("UNIT_ID","");
//            try {
//                    st = conn.createStatement();
//                    String suffix = PO.GetValueByEnvName("ReportSuffixyear", "");
//                    String strSQL =
//                            " select A.BHGZ_FFZF  " +
//                            " from "+JREPORT.getRTNbyPO("BHGZ",PO) + suffix + " A " +
//                            " where (1=1) AND A.UNIT_ID = '" +UNIT_ID+"'"+
//                            " ";
//                    rs = st.executeQuery(JConvertSql.convertSql(st, strSQL));
//                    while (rs.next()) {
//                            gz = rs.getString(1);
//                            list.add(gz);
//                             gz = "";
//                    }
//            } catch (Exception ex) {
//                    ex.printStackTrace();
//            } finally {
//                    conn.BackStatement(st, rs);
//            }
//
//            return list;
//            }
//
//			public static void assignDataLimit(JConnection conn, JParamObject PO) {
//					assignDataLimit(conn.getESPConnection(),PO);
//			}
//
//			public static void assignDataLimit(com.efounder.sql.JConnection conn, JParamObject PO) {
//				if (JREPORT.isOldDbVer())
//						assignDataLimitGf(conn, PO);
//				else
//						assignDataLimitJt(conn, PO);
//
//			}
//
//            //集团用的分配权限
//            public static void assignDataLimitJt(com.efounder.sql.JConnection conn, JParamObject PO) {
//                    String zgbh = PO.GetValueByParamName("ZGBH","");
//                    if ( zgbh.equals("") ) {
//                            zgbh = PO.GetValueByEnvName("UserName");
//                    }
//                    String qxbh = PO.GetValueByParamName("QXBH");
//                    String sjbh = PO.GetValueByParamName("SJBH");
//                    String gran = PO.GetValueByParamName("GRAN");
//                    String isDelete = PO.GetValueByParamName("DELETE", "1"); //1:删除权限,0:分配权限
//                    String UNIT_ID = PO.GetValueByEnvName("UNIT_ID","");
//                    String TYPE = PO.GetValueByParamName("TYPE","");
//                    Statement stmt = null;
//                    try {
//                            conn.setAutoCommit(false);
//                            stmt = conn.createStatement();
//                            //先删除
//                            String delSql = "delete from BSUSSJ where F_ZGBH='" + zgbh + "' and F_QXBH='" + qxbh + "' and F_SJBH='" + sjbh + "'";
//                            stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql));
//                            //先删除二级管理员报表的数据权限 add by mf 20100531
//                             delSql = "DELETE  BSUSSJ where F_ZGBH in (select F_ZGBH from BSUSER WHERE UNIT_ID = '"+UNIT_ID+"' and F_MANA = 1)"
//                             + " and F_QXBH='" + qxbh + "' and F_SJBH='" + sjbh + "'";
//                            stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql));
//
//                            
//                            //删除BSUSSJ_TRANSIT
//                            delSql = "delete from BSUSSJ_TRANSIT where F_ZGBH='" + zgbh + "' and F_QXBH='" + qxbh + "' and F_SJBH='" + sjbh + "'";
//                            stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql));
//                            //先删除二级管理员报表的数据权限 add by mf 20100531
//                             delSql = "DELETE  BSUSSJ_TRANSIT where F_ZGBH in (select F_ZGBH from BSUSER WHERE UNIT_ID = '"+UNIT_ID+"' and F_MANA = 1)"
//                             + " and F_QXBH='" + qxbh + "' and F_SJBH='" + sjbh + "'";
//                            stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql));
//                            
//                            if (! (isDelete.equals("1"))) {
//                                    //后添加
//                                    String addSql = "insert into BSUSSJ(F_ZGBH,F_QXBH,F_SJBH,F_GRAN,F_G1,F_G2,F_G3,F_G4,F_G5,F_G6,F_G7,F_G8,F_SH) ";
//                                                   addSql += "values('" + zgbh + "','" + qxbh + "','" + sjbh + "','" + gran + "'";
//                                    String signs = "";
//                                    for (int i = 0; i < gran.length(); i++) {
//                                            signs += ",'" + gran.charAt(i) + "'";
//                                    }
//                                    //不足8位
//                                    if (gran.length() < 8) {
//                                            for (int i = 0; i < 8 - gran.length(); i++) {
//                                                    signs += ",''";
//                                            }
//                                    }
//                                    signs += ",'1'"; //审核权限
//                                    addSql += signs;
//                                    addSql += ")";
//                                    stmt.executeUpdate(JConvertSql.convertSql(stmt, addSql));
//
//                                    //后添加二级管理员报表的数据权限 add by mf 20100531
//                                    String SQL = "select F_ZGBH from BSUSER WHERE UNIT_ID = '" + UNIT_ID + "' and F_MANA = 1";
//                                    Statement st = conn.createStatement();
//                                    ResultSet rs = null;
//                                    try {
//                                            rs = st.executeQuery(SQL);
//
//                                            while ( rs.next() ) {
//                                                    zgbh = rs.getString(1);
//                                                    SQL = "insert into BSUSSJ(F_ZGBH,F_QXBH,F_SJBH,F_GRAN,";
//                                                    SQL += "F_G1,F_G2,F_G3,F_G4,F_G5,F_G6,F_G7,F_G8,F_SH) values";
//                                                    SQL += "('" + zgbh + "','" + qxbh + "','" + sjbh + "','" + gran +"'";
//                                                    SQL += signs;
//                                                    SQL += ")";
//                                                     stmt.executeUpdate(JConvertSql.convertSql(stmt, SQL));
//                                            }
//                                    } catch ( Exception e ) {
//                                            e.printStackTrace();
//                                    } finally {
//                                            conn.BackStatement(st,rs);
//                                    }
//
//                                    
//                                    //后添加BSUSSJ_TRANSIT
//                                    addSql = "insert into BSUSSJ_TRANSIT(F_ZGBH,F_QXBH,F_SJBH,F_GRAN,F_G1,F_G2,F_G3,F_G4,F_G5,F_G6,F_G7,F_G8,F_SH) ";
//                                                   addSql += "values('" + zgbh + "','" + qxbh + "','" + sjbh + "','" + gran + "'";
//                                    signs = "";
//                                    for (int i = 0; i < gran.length(); i++) {
//                                            signs += ",'" + gran.charAt(i) + "'";
//                                    }
//                                    //不足8位
//                                    if (gran.length() < 8) {
//                                            for (int i = 0; i < 8 - gran.length(); i++) {
//                                                    signs += ",''";
//                                            }
//                                    }
//                                    signs += ",'1'"; //审核权限
//                                    addSql += signs;
//                                    addSql += ")";
//                                    stmt.executeUpdate(JConvertSql.convertSql(stmt, addSql));
//
//                                    //后添加二级管理员报表的数据权限 add by mf 20100531
//                                    SQL = "select F_ZGBH from BSUSER WHERE UNIT_ID = '" + UNIT_ID + "' and F_MANA = 1";
//                                    st = conn.createStatement();
//                                    rs = null;
//                                    try {
//                                            rs = st.executeQuery(SQL);
//
//                                            while ( rs.next() ) {
//                                                    zgbh = rs.getString(1);
//                                                    SQL = "insert into BSUSSJ_TRANSIT(F_ZGBH,F_QXBH,F_SJBH,F_GRAN,";
//                                                    SQL += "F_G1,F_G2,F_G3,F_G4,F_G5,F_G6,F_G7,F_G8,F_SH) values";
//                                                    SQL += "('" + zgbh + "','" + qxbh + "','" + sjbh + "','" + gran +"'";
//                                                    SQL += signs;
//                                                    SQL += ")";
//                                                     stmt.executeUpdate(JConvertSql.convertSql(stmt, SQL));
//                                            }
//                                    } catch ( Exception e ) {
//                                            e.printStackTrace();
//                                    } finally {
//                                            conn.BackStatement(st,rs);
//                                    }
//                                    
//                            }else{
//                              ArrayList tableList = (ArrayList)getTableNameByBh(conn,PO);
//                              if(tableList.size()>0){
//                                String delSql1 = "";
//                                if(TYPE.equals("5")){
//                                  for (int i = 0; i < tableList.size(); i++) {
//                                    String tablename = (String)tableList.get(i);
//                                    if(tablename.equals("RPTLBCONDW")){
//                                      delSql1 = "delete from "+tablename+" WHERE CON_TYPE =2 AND CON_DWBH = '"+sjbh+"'";
//                                    }else{
//                                      delSql1 = "delete from "+tablename+" WHERE DWZD_BH = '"+sjbh+"'";
//                                     }
//                                     stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql1));
//                                  }
//                                }else if(TYPE.equals("6")){
//                                  for (int i = 0; i < tableList.size(); i++) {
//                                    String tablename = (String)tableList.get(i);
//                                    if(tablename.equals("RPTLBCONDW")){
//                                      delSql1 = "delete from "+tablename+" WHERE CON_TYPE =3 AND CON_DWBH = '"+sjbh+"'";
//                                    }else{
//                                      delSql1 = "delete from "+tablename+" WHERE BMZD_BH = '"+sjbh+"'";
//                                    }
//                                    stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql1));
//                                  }
//                                }else if(TYPE.equals("7")){
//                                  for (int i = 0; i < tableList.size(); i++) {
//                                    String tablename = (String)tableList.get(i);
//                                    if(tablename.equals("RPTLBCONDW")){
//                                      delSql1 = "delete from "+tablename+" WHERE CON_TYPE =4 AND CON_DWBH = '"+sjbh+"'";
//                                    }else{
//                                      delSql1 = "delete from "+tablename+" WHERE CBZD_BH = '"+sjbh+"'";
//                                    }
//                                    stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql1));
//                                  }
//                                }else if(TYPE.equals("4")){
//                                  for (int i = 0; i < tableList.size(); i++) {
//                                    String tablename = (String)tableList.get(i);
//                                    if(tablename.equals("RPTLBCONDW")){
//                                      delSql1 = "delete from "+tablename+" WHERE CON_TYPE =1 AND CON_DWBH = '"+sjbh+"'";
//                                    }else{
//                                      delSql1 = "delete from "+tablename+" WHERE LBZD_BH = '"+sjbh+"'";
//                                    }
//                                    stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql1));
//                                  }
//                                }
//
//                              }
//                            }
//                            conn.commit();
//                    } catch (Exception e) {
//                            e.printStackTrace();
//                            conn.rollback();
//                    } finally {
//                            conn.BackStatement(stmt, null);
//                    }
//            }
//
//            //股份用的分配权限，BSUSSJ表增加了一个F_SH列，以及权限编号是以HZ开头，而不是RT
//            public static void assignDataLimitGf(com.efounder.sql.JConnection conn, JParamObject PO) {
//                    String zgbh = PO.GetValueByParamName("ZGBH");
//                    String qxbh = PO.GetValueByParamName("QXBH");
//                    String sjbh = PO.GetValueByParamName("SJBH");
//                    String gran = PO.GetValueByParamName("GRAN");
//                    String isDelete = PO.GetValueByParamName("DELETE", "1"); //1:删除权限,0:分配权限
//                    qxbh = "HZ" + qxbh.substring(2);
//                    Statement stmt = null;
//                    try {
//                            conn.setAutoCommit(false);
//                            stmt = conn.createStatement();
//                            //先删除
//                            String delSql = "delete from BSUSSJ where F_ZGBH='" + zgbh + "' and F_QXBH='" + qxbh + "' and F_SJBH='" + sjbh + "'";
//                            stmt.executeUpdate(JConvertSql.convertSql(stmt, delSql));
//                            if (! (isDelete.equals("1"))) {
//                                    //后添加
//                                    String addSql = "insert into BSUSSJ(F_ZGBH,F_QXBH,F_SJBH,F_GRAN,F_G1,F_G2,F_G3,F_G4";
//                                    addSql += ",F_G5,F_G6,F_G7,F_G8,F_SH) values('" + zgbh + "','" + qxbh + "','" + sjbh + "','" + gran + "'";
//                                    String signs = "";
//                                    for (int i = 0; i < gran.length(); i++) {
//                                            signs += ",'" + gran.charAt(i) + "'";
//                                    }
//                                    //不足8位
//                                    if (gran.length() < 8) {
//                                            for (int i = 0; i < 8 - gran.length(); i++) {
//                                                    signs += ",''";
//                                            }
//                                    }
//                                    signs += ",'1'"; //F_SH
//                                    addSql += signs;
//                                    addSql += ")";
//                                    stmt.executeUpdate(JConvertSql.convertSql(stmt, addSql));
//                            }
//                            conn.commit();
//                    } catch (Exception e) {
//                            e.printStackTrace();
//                            conn.rollback();
//                    } finally {
//                            conn.BackStatement(stmt, null);
//                    }
//            }
//
//            /**
//             * 根据编号取报表系统相关的表名列表
//             * @param conn JConnection
//             * @param PO JParamObject
//             * @return Object
//             */
//            public static Object getTableNameByBh(com.efounder.sql.JConnection conn, JParamObject PO) throws Exception{
//                    String value;
//                    String sjbh = "";
//                    String TYPE = PO.GetValueByParamName("TYPE","");
//                    if(TYPE.equals("5")){
//                      sjbh = "DWZD_BH";
//                    }else if(TYPE.equals("6")){
//                      sjbh = "BMZD_BH";
//                    }else if(TYPE.equals("7")){
//                      sjbh = "CBZD_BH";
//                    }else if(TYPE.equals("4")){
//                      sjbh = "LBZD_BH";
//                    }
//
//
//                    Statement stmt = null;
//                    ResultSet rs   = null;
//                    ArrayList tableList = new ArrayList();
//                    try{
//                            stmt = conn.GetStatement(conn);
//                            String strSql = "select OBJ_ID from sys_objcols where COL_ID = '"+sjbh+"'";
//                            rs = stmt.executeQuery(strSql);
//                            while(rs.next()){
//                                    value = rs.getString(1).trim();
//                                    tableList.add(value);
//                            }
//                            return tableList;
//                    }finally{
//                            conn.BackStatement(stmt,rs);
//                    }
//	}
//        /**
//         * 获得 单位字典 编码结构
//         * @param conn JConnection
//         * @yinl
//         * @return Object
//         * @throws Exception
//         */
//        public static String getDictBmStru(JConnection conn,String zdmc) throws SQLException {
//			String asRes = "";
//			Statement st = conn.createStatement();
//			try {
//				asRes = getDictBmStru(st, zdmc);
//			} catch (Exception ex){
//				conn.BackStatement(st,null);
//			} finally {
//				conn.BackStatement(st,null);
//			}
//			return asRes;
//		}
//      public static String getDictBmStru(Statement stmt, String zdmc) throws SQLException {
//              ResultSet rs   = null;
//          try{
//                  String bmStru = "";
//                  String strSql = "select DCT_BMSTRU from SYS_DICTS where DCT_ID = '"+JREPORT.getRTN(zdmc)+"'";
//                  rs = stmt.executeQuery(strSql);
//                  while(rs.next()){
//                    bmStru = rs.getString("DCT_BMSTRU").toString();
//                  }
//                  return bmStru;
//          }finally{
//          }
//	  }
//
//	  public static int DropTable(JConnection conn, String tabName) throws
//		  SQLException {
//		  int iRes = 1;
//		  Statement st;
//		  String SQL;
//		  st = conn.GetStatement(conn);
//		  try {
//			  SQL = "drop table " + tabName;
//			  st.execute(SQL);
//		  }
//		  catch (Exception e) {
//			  iRes = 0;
//			//  e.printStackTrace();
//		  }
//		  finally {
//			  conn.BackStatement(st, null);
//		  }
//		  return iRes;
//	  }
//
//	  public static String CreateTempTable(JConnection conn, String TableName,
//										   String TempTableName) {
//		  Statement st;
//		  String SQL;
//		  st = conn.GetStatement(conn);
//		  try {
//			  SQL = "create table " + TempTableName +
//				  " as select * from " + TableName + " where 2 < 1 ";
//			  st.execute(SQL);
//		  }
//		  catch (Exception e) {
//			//  e.printStackTrace();
//		  }
//		  finally {
//			  conn.BackStatement(st, null);
//		  }
//		  return TempTableName;
//	  }
//	  public static String CreateTempCaTable(JConnection conn,JParamObject PO,String TableName) {
//		  String SQL;
//		  String TempTableName="";
//		  try {
//			  TempTableName = TableName;
//			  String tempTableBody =
//				  " ( F_KMBH    VARCHAR(30) null,"
//				  + " F_BMBH    VARCHAR(30) null,"
//				  + " F_DWBH    VARCHAR(30) null,"
//				  + " F_ZGBH    VARCHAR(30) null,"
//				  + " F_XMBH    VARCHAR(30) null,"
//				  + " F_WBBH    VARCHAR(30) null,"
//				  + " F_CODE    VARCHAR(30) null,"
//				  + " F_NCYE    NUMBER(26,6) null,"
//				  + " F_JFLJ    NUMBER(26,6) null,"
//				  + " F_DFLJ    NUMBER(26,6) null,"
//				  + " F_JFFS    NUMBER(26,6) null,"
//				  + " F_DFFS    NUMBER(26,6) null,"
//				  + " F_DQYE    NUMBER(26,6) null,"
//				  + " F_SZ01    NUMBER(26,6) null,"
//				  + " F_SZ02    NUMBER(26,6) null,"
//				  + " F_SZ03    NUMBER(26,6) null,"
//				  + " F_SZ04    NUMBER(26,6) null,"
//				  + " F_SZ05    NUMBER(26,6) null,"
//				  + " F_XM01    VARCHAR(120) null,"
//				  + " F_XM02    VARCHAR(120) null,"
//				  + " F_XM03    VARCHAR(120) null,"
//				  + " F_XM04    VARCHAR(120) null,"
//				  + " F_XM05    VARCHAR(120) null)";
//			  TempTableName = TempTableManager.createTmpTable(conn, PO,
//				  TempTableName,
//				  tempTableBody, "", "");
//
//		  }
//		  catch (Exception e) {
//			//  e.printStackTrace();
//		  }
//		  return TempTableName;
//	  }
//	  public static String CreateZbTempCaTable(JConnection conn,JParamObject PO,String TableName) {
//		  String SQL;
//		  String TempTableName="";
//		  try {
//			  TempTableName = TableName;
//			  String tempTableBody =
//				   " (DJLX_BH VARCHAR(30) null,"
//				  +"  F_BDBH VARCHAR(30) null,"
//				  +"  F_BDMC VARCHAR(254) null,"
//				  +"  F_BGZT VARCHAR(30) null,"
//				  +"  F_BRFS VARCHAR(30) null,"
//				  +"  F_CHAR01 VARCHAR(30) null,"
//				  +"  F_CHAR02 VARCHAR(30) null,"
//				  +"  F_CHAR03 VARCHAR(30) null,"
//				  +"  F_CHAR04 VARCHAR(30) null,"
//				  +"  F_CHAR05 VARCHAR(30) null,"
//				  +"  F_CHAR06 VARCHAR(30) null,"
//				  +"  F_CHAR07 VARCHAR(30) null,"
//				  +"  F_CHAR1 VARCHAR(30) null,"
//				  +"  F_CHAR2 VARCHAR(30) null,"
//				  +"  F_CODE VARCHAR(30) null,"
//				  +"  F_CPLB VARCHAR(30) null,"
//				  +"  F_CPZD VARCHAR(30) null,"
//				  +"  F_DATE1 VARCHAR(8) null,"
//				  +"  F_DATE2 VARCHAR(8) null,"
//				  +"  F_DBDW VARCHAR(30) null,"
//				  +"  F_DBFS VARCHAR(30) null,"
//				  +"  F_DBXZ VARCHAR(30) null,"
//				  +"  F_DBZL VARCHAR(30) null,"
//				  +"  F_DJBH VARCHAR(36) null,"
//				  +"  F_DWXZ VARCHAR(30) null,"
//				  +"  F_DWXZI VARCHAR(30) null,"
//				  +"  F_FDBH VARCHAR(254) null,"
//				  +"  F_FDBZL VARCHAR(30) null,"
//				  +"  F_FLBH VARCHAR(4) null,"
//				  +"  F_GQDB VARCHAR(30) null,"
//				  +"  F_GUID VARCHAR(50) null,"
//				  +"  F_ID VARCHAR(30) null,"
//				  +"  F_JNJW VARCHAR(30) null,"
//				  +"  F_KJND VARCHAR(4) null,"
//				  +"  F_KJQJ VARCHAR(8) null,"
//				  +"  F_LBBH VARCHAR(30) null,"
//				  +"  F_LSGX VARCHAR(30) null,"
//				  +"  F_QSRQ VARCHAR(8) null,"
//				  +"  F_QYXZ VARCHAR(30) null,"
//				  +"  F_QYZCLX VARCHAR(30) null,"
//				  +"  F_SFCC VARCHAR(30) null,"
//				  +"  F_SFYQ VARCHAR(30) null,"
//				  +"  F_SSYH VARCHAR(30) null,"
//				  +"  F_SYZT VARCHAR(1) null,"
//				  +"  F_WGFF VARCHAR(30) null,"
//				  +"  F_WLBH VARCHAR(30) null,"
//				  +"  F_XMLB VARCHAR(30) null,"
//				  +"  F_XMXZ VARCHAR(30) null,"
//				  +"  F_ZBBH VARCHAR(30) null,"
//				  +"  F_ZBMC VARCHAR(90) null,"
//				  +"  F_ZCFS VARCHAR(30) null,"
//				  +"  F_ZF02 VARCHAR(30) null,"
//				  +"  F_ZRZX VARCHAR(30) null,"
//				  +"  F_ZYBH VARCHAR(30) null,"
//				  +"  F_ZZRQ VARCHAR(8) null,"
//				  +"  F_BQJE  NUMBER(26,6) null,"
//				  +"  F_BQSL  NUMBER(26,6) null,"
//				  +"  F_LJJE  NUMBER(26,6) null,"
//				  +"  F_LJSL  NUMBER(26,6) null,"
//				  +"  F_NCJE  NUMBER(26,6) null,"
//				  +"  F_NCSL  NUMBER(26,6) null,"
//				  +"  F_NUM01  NUMBER(26,6) null,"
//				  +"  F_NUM02  NUMBER(26,6) null,"
//				  +"  F_NUM03  NUMBER(26,6) null,"
//				  +"  F_NUM04  NUMBER(26,6) null,"
//				  +"  F_NUM05  NUMBER(26,6) null,"
//				  +"  F_NUM06  NUMBER(26,6) null,"
//				  +"  F_NUM07  NUMBER(26,6) null,"
//				  +"  F_NUM08  NUMBER(26,6) null,"
//				  +"  F_NUM09  NUMBER(26,6) null,"
//				  +"  F_NUM1  NUMBER(26,6) null,"
//				  +"  F_NUM10  NUMBER(26,6) null,"
//				  +"  F_NUM11  NUMBER(26,6) null,"
//				  +"  F_NUM12  NUMBER(26,6) null,"
//				  +"  F_NUM13  NUMBER(26,6) null,"
//				  +"  F_NUM14  NUMBER(26,6) null,"
//				  +"  F_NUM15  NUMBER(26,6) null,"
//				  +"  F_NUM16  NUMBER(26,6) null,"
//				  +"  F_NUM17  NUMBER(26,6) null,"
//				  +"  F_NUM18  NUMBER(26,6) null,"
//				  +"  F_NUM19  NUMBER(26,6) null,"
//				  +"  F_NUM2  NUMBER(26,6) null,"
//				  +"  F_NUM20  NUMBER(26,6) null,"
//				  +"  F_NUM3  NUMBER(26,6) null,"
//				  +"  F_NUM4  NUMBER(26,6) null,"
//				  +"  F_NUM5  NUMBER(26,6) null,"
//				  +"  F_NUM6  NUMBER(26,6) null,"
//				  +"  F_QCJE  NUMBER(26,6) null,"
//				  +"  F_QCSL  NUMBER(26,6) null,"
//				  +"  F_QMJE  NUMBER(26,6) null,"
//				  +"  F_QMSL  NUMBER(26,6) null,"
//				  +"  F_CRDATE TIMESTAMP(6) null,"
//				  +"  F_CHDATE TIMESTAMP(6) null)";
//
//			  TempTableName = TempTableManager.createTmpTable(conn, PO,
//				  TempTableName,
//				  tempTableBody, "", "");
//		  }
//		  catch (Exception e) {
//			//  e.printStackTrace();
//		  }
//		  return TempTableName;
//	  }
//
//
//
//	  public static int DropCaTable(JConnection conn, JParamObject PO ,String tabName) throws
//		  SQLException {
//		  int iRes = 1;
//		  //Statement st;
//		  String SQL;
//		  //st = conn.GetStatement(conn);
//		  try {
//			  //SQL = "drop table " + tabName;
//			  //st.execute(SQL);
//			  TempTableManager.dropTable(conn,PO,tabName);
//		  }
//		  catch (Exception e) {
//			  iRes = 0;
//			  e.printStackTrace();
//		  }
//		  finally {
//		//	  conn.BackStatement(st, null);
//		  }
//		  return iRes;
//	  }
//	  public static int TruncateCaTable(JConnection conn, JParamObject PO ,String tabName) throws
//		  SQLException {
//		  int iRes = 1;
//		  Statement st;
//		  String SQL;
//		  st = conn.GetStatement(conn);
//		  try {
//			  SQL = "truncate table " + tabName;
//			  st.execute(SQL);
//		  }
//		  catch (Exception e) {
//			  iRes = 0;
//			  e.printStackTrace();
//		  }
//		  finally {
//			  conn.BackStatement(st, null);
//		  }
//		  return iRes;
//	  }


}
//adsfafdfdsfasfa
