package com.mrp.biz.server.plugins.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.dbcp.DelegatingResultSet;

import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.bizmodel.SYS_MDL_VAL;

public class FormCodeUtil {

	public static String GetNewBillCode(JStatement stmt, EFRowSet bill,String F_DJLX) throws Exception {
		EFDataSet  bhfsDataSet = getBhfs(stmt, F_DJLX);
		Date          currDate = new Date();
		String          typebm = getDjlx(F_DJLX);
		String         qjvalue = parseKJQJPattern(currDate, bhfsDataSet, F_DJLX);
		String       codevalue = parseDJBHPattern(bill, currDate, bhfsDataSet, F_DJLX);
		String         codelen = parseDJBHLenPattern(bill, bhfsDataSet, F_DJLX);
		String           where = "F_DATE='" + qjvalue + "' and F_DJLX='" + typebm + "'";
		String             sql = "select * from SYS_MDL_BH where " + where;		
		int             number = 1;
		int                len = Integer.parseInt(codelen);
		String            bhfs = "";
		DelegatingResultSet rs;
		
		rs = (DelegatingResultSet) stmt.executeQuery(sql);
		if (!rs.next()) {// 没有需要新插入
			rs.close();
			sql = " insert into SYS_MDL_BH (F_DATE,F_DJLX,F_MAXBM,F_LSHLEN)"
				+ " values('" + qjvalue + "','" + typebm + "',1," + len + ")";
			stmt.executeUpdate(sql);
		} else {
			number = rs.getInt(1);
			bhfs = rs.getString(2);
		}
		if (bhfs == null) bhfs = "";
		bhfs = bhfs.trim();
		rs.close();
		// 锁定
		sql = "update SYS_MDL_BH set F_MAXBM=F_MAXBM where " + where;
		stmt.executeUpdate(sql);
		// 再重新选
		sql = " select F_MAXBM from SYS_MDL_BH where " + where;
		rs = (DelegatingResultSet) stmt.executeQuery(sql);
		if (rs.next()) {
			number = rs.getInt(1);
		}
		java.text.DecimalFormat df;
		String FmtStr = "";
		for (int i = 0; i < len; i++) {
			FmtStr += "0";
		}
		df = new java.text.DecimalFormat(FmtStr);
		String code = df.format(number);
		sql = "update SYS_MDL_BH set F_MAXBM=" + (number + 1) + " WHERE " + where;
		stmt.executeUpdate(sql);
		if (code.trim().length() == 0) {
			throw new Exception("未生成单据编号，信息:" + where);
		}
		return (codevalue+code).trim();
	}
	
	public static String GetNewBillCode(JStatement stmt, EFFormDataModel model, EFRowSet bill,String headtable, String F_DJLX) throws Exception {
		EFDataSet  bhfsDataSet = getBhfs(stmt, F_DJLX);
		String          typebm = getDjlx(F_DJLX);
		String         qjvalue = parseKJQJPattern(bill, bhfsDataSet, F_DJLX);
		String       codevalue = parseDJBHPattern(bill, bhfsDataSet, F_DJLX);
		String         codelen = parseDJBHLenPattern(bill, bhfsDataSet, F_DJLX);
		String           where = "F_DATE='" + qjvalue + "' and F_DJLX='" + typebm + "'";
		String             sql = "select * from SYS_MDL_BH where " + where;
		int             number = 1;
		int                len = Integer.parseInt(codelen);
		String            bhfs = "";
		DelegatingResultSet rs;
		
		rs = (DelegatingResultSet) stmt.executeQuery(sql);
		if (!rs.next()) {// 没有需要新插入
			rs.close();
			sql = " insert into SYS_MDL_BH (F_DATE,F_DJLX,F_MAXBM,F_LSHLEN)"
				+ " values('" + qjvalue + "','" + typebm + "',1," + len + ")";
			stmt.execute(sql);
		} else {
			number = rs.getInt(1);
			bhfs = rs.getString(2);
		}
		if (bhfs == null) bhfs = "";
		bhfs = bhfs.trim();
		rs.close();
		// 锁定
		sql = "update SYS_MDL_BH set F_MAXBM=F_MAXBM where " + where;
		stmt.execute(sql);
		// 再重新选
		sql = " select F_MAXBM from SYS_MDL_BH where " + where;
		rs = (DelegatingResultSet) stmt.executeQuery(sql);
		if (rs.next()) {
			number = rs.getInt(1);
		}
		java.text.DecimalFormat df;
		String FmtStr = "";
		for (int i = 0; i < len; i++) {
			FmtStr += "0";
		}
		df = new java.text.DecimalFormat(FmtStr);
		String code = df.format(number);
		sql = "update SYS_MDL_BH set F_MAXBM=" + (number + 1) + " WHERE " + where;
		stmt.execute(sql);
		if (code.trim().length() == 0) {
			throw new Exception("未生成单据编号，信息:" + where);
		}
		return (codevalue+code).trim();
	}
	
	public static EFDataSet getBhfs(JStatement stmt, String F_DJLX) throws Exception {
		String     strSql = "select * from SYS_MDL_VAL where MDL_KEY like '" + F_DJLX + "%'";
		EFDataSet dataSet = EFDataSet.getInstance("SYS_MDL_VAL");
		String[] primeKey = {"MDL_KEY"};
		
		dataSet.setPrimeKey(primeKey);
		
		DelegatingResultSet rs = (DelegatingResultSet) stmt.executeQuery(strSql);
		dataSet = DataSetUtils.resultSet2DataSet(rs, dataSet);
		return dataSet;
	}
	
	public static String getDjlx(String F_DJLX) {
		if(F_DJLX.equals(SYS_MDL_VAL.CLRK)) return "R";
		if(F_DJLX.equals(SYS_MDL_VAL.CLCK)) return "C";
		if(F_DJLX.equals(SYS_MDL_VAL.CLDB)) return "D";
		if(F_DJLX.equals(SYS_MDL_VAL.CLTH)) return "T";
		if(F_DJLX.equals(SYS_MDL_VAL.XMDR)) return "X";
		return "";
	}
	
	private static String parseKJQJPattern(EFRowSet bill, EFDataSet bhfsDataSet, String F_DJLX) {
		String       qjvalue = null;
		EFRowSet      rowset = (EFRowSet) bhfsDataSet.getRowSet(F_DJLX + SYS_MDL_VAL.BILL_KJQJ_BHFS);
		String     KJQJ_BHFS = null;
		
		if(rowset != null) KJQJ_BHFS = rowset.getString(SYS_MDL_VAL.MDL_KEY, "");
		
		if(KJQJ_BHFS == null) {
			qjvalue = FormBillFieldUtil.getQJValue("F_KJQJ", bill);
		} else if(KJQJ_BHFS != null && KJQJ_BHFS.equals("@KJQJ@")) {
			qjvalue = FormBillFieldUtil.getQJValue("F_KJQJ", bill);
		} else if(KJQJ_BHFS != null && KJQJ_BHFS.equals("@KJND@")) {
			qjvalue = FormBillFieldUtil.getQJValue("F_KJQJ", bill).substring(0, 4);
		} 
		if(qjvalue == null) {
			qjvalue = FormBillFieldUtil.getQJValue("F_KJQJ", bill);
		}
		return qjvalue;
	}
	
	private static String parseKJQJPattern(Date currTime, EFDataSet bhfsDataSet, String F_DJLX) {
		String             qjvalue = null;
		EFRowSet            rowset = (EFRowSet) bhfsDataSet.getRowSet(F_DJLX + SYS_MDL_VAL.BILL_KJQJ_BHFS);
		String           KJQJ_BHFS = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String[]         kjqjArray = null;
		if(rowset != null) KJQJ_BHFS = rowset.getString(SYS_MDL_VAL.MDL_VALUE, "");
		
		if(KJQJ_BHFS == null) {
			qjvalue = formatter.format(currTime);
		} else if(KJQJ_BHFS != null) {
			kjqjArray = KJQJ_BHFS.split(":");
			formatter = new SimpleDateFormat(kjqjArray[1]);
		}
		
		if(qjvalue == null) {
			qjvalue = formatter.format(currTime);
		}
		return qjvalue;
	}
	
	private static String parseDJBHPattern(EFRowSet bill, EFDataSet bhfsDataSet, String F_DJLX) {
		String       bhvalue = null;
		String        F_KJQJ = FormBillFieldUtil.getQJValue("F_KJQJ", bill);
		String        F_KJND = F_KJQJ.substring(0, 4);
		String        F_DATE = FormBillFieldUtil.getDateValue("F_CRDATE", bill);
		EFRowSet      rowset = (EFRowSet) bhfsDataSet.getRowSet(F_DJLX + SYS_MDL_VAL.BILL_DJBH_BHFS);
		String     DJBH_BHFS = "";
		
		if(rowset == null) return "";
		
		DJBH_BHFS = rowset.getString(SYS_MDL_VAL.MDL_KEY, "");
		
		if(DJBH_BHFS.indexOf("@KJQJ@") > -1) {
			bhvalue = DJBH_BHFS.replaceAll("@KJQJ@", F_KJQJ);
		} else if(DJBH_BHFS.equals("@KJND@")) {
			bhvalue = DJBH_BHFS.replaceAll("@KJND@", F_KJND);
		} else if(DJBH_BHFS.equals("@F_CRDATE@")) {
			bhvalue = DJBH_BHFS.replaceAll("@F_DATE@", F_DATE);
		} 
		if(bhvalue == null) {
			bhvalue = "";
		}
		return bhvalue;
	}
	
	private static String parseDJBHPattern(EFRowSet bill, Date currDate, EFDataSet bhfsDataSet, String F_DJLX) {
		String              bhvalue = null;
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyyMMdd");
		String               F_KJQJ = "";
		String               F_KJND = "";
		String               F_DATE = "";
		EFRowSet             rowset = (EFRowSet) bhfsDataSet.getRowSet(F_DJLX + SYS_MDL_VAL.BILL_DJBH_BHFS);
		String            DJBH_BHFS = "";
		
		if(rowset == null) return "";
		
		F_DATE = formatter.format(currDate);
		F_KJQJ = F_DATE.substring(0, 6);
		F_KJND = F_DATE.substring(0, 4);

		DJBH_BHFS = rowset.getString(SYS_MDL_VAL.MDL_VALUE, "");
		
		if(DJBH_BHFS.indexOf("@KJQJ@") > -1) {
			bhvalue = DJBH_BHFS.replaceAll("@KJQJ@", F_KJQJ);
		} else if(DJBH_BHFS.contains("@KJND@")) {
			bhvalue = DJBH_BHFS.replaceAll("@KJND@", F_KJND);
		} else if(DJBH_BHFS.contains("@F_CRDATE@")) {
			bhvalue = DJBH_BHFS.replaceAll("@F_DATE@", F_DATE);
		} 
		if(bhvalue == null) {
			bhvalue = "";
		}
		return bhvalue;
	}
	
	private static String parseDJBHLenPattern(EFRowSet bill, EFDataSet bhfsDataSet, String F_DJLX) {
		EFRowSet      rowset = (EFRowSet) bhfsDataSet.getRowSet(F_DJLX + SYS_MDL_VAL.BILL_DJBH_LEN);
		String      DJBH_LEN = "";
		
		if(rowset == null) return "5";
		
		DJBH_LEN = rowset.getString(SYS_MDL_VAL.MDL_VALUE, "5");
		
		return DJBH_LEN;
	}
}
