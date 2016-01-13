package com.efounder.form.server.resolver.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.meta.bizmodel.SYS_MDL_CTN;
import com.metadata.bizmodel.SYS_MODEL;

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
public class FormBillFieldUtil {
	public FormBillFieldUtil() {
	}

	public static String getQJValue(String key, EFRowSet rowset) {
		SimpleDateFormat   formatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date      currTime = new java.util.Date();
		
		String               qjvalue = rowset.getString(key, null);
		if (qjvalue == null || "".equals(qjvalue)) qjvalue = formatter.format(currTime);

		return qjvalue;
	}
	
	public static String getDateValue(String key, EFRowSet rowset) {
		SimpleDateFormat   formatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date      currTime = new java.util.Date();
		
		Date F_CRDATE = rowset.getDate(key, null);
		if (F_CRDATE == null) F_CRDATE = currTime;

		return formatter.format(F_CRDATE);
	}
	
	public static Object getValue(String key, EFRowSet rowset, String prix) {
		Object o = null;
		o = rowset.getObject(key, null);
		if (o != null && !"".equals(o)) return o;
		
		o = rowset.getObject(prix + "." + key, null);
		
		if (o != null && !"".equals(o)) return o;
		
		return null;
	}

	public static java.util.List<String> getALLPrimKey(String dataType){
		String                   pks[] = getPrimeKey(dataType);
		java.util.List<String>    list = new ArrayList<String>();
		list.add("F_CODE");
		
	    for (int i = 0; i < pks.length; i++)
	      list.add(pks[i]);
	    list.add("F_KJQJ");
		return list;
	}
	
	protected static String[] getPrimeKey(String dataType) {
		// 表头数据 - 单据编号
	    String[] primeKey = null;
	    if ( SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_.equals(dataType) ) {
	      primeKey = new String[1];
	      primeKey[0] = "F_DJBH";
	    }
	    // 分录数据 - 单据编号+分录编号
	    if ( SYS_MDL_CTN._BIZ_CTN_TYPE_JIDS_.equals(dataType) ) {
	      primeKey = new String[2];
	      primeKey[0] = "F_DJBH";
	      primeKey[1] = "F_FLBH";
	    }
	    return primeKey;
	}
	
	public static DecimalFormat getBILL_FL_Format(){
		return  new java.text.DecimalFormat("0000");
	}
	
	public static String getBILLBHCol(EFDataSet mdlValDS) {
		String BILL_BH = "";
		if(mdlValDS.getRowSet(SYS_MODEL._BILL_BH_COL_) != null) {
			BILL_BH = mdlValDS.getRowSet(SYS_MODEL._BILL_BH_COL_).getString("MDL_VAL", SYS_MDL_VAL.BILL_BH);
		} else {
			BILL_BH = SYS_MDL_VAL.BILL_BH;
		}
		return BILL_BH;
	}
	
	public static String getFLBHBHCol(EFDataSet mdlValDS) {
		String BIFL_BH = "";
		if(mdlValDS.getRowSet(SYS_MODEL._BLFL_BH_COL_) != null) {
			BIFL_BH = mdlValDS.getRowSet(SYS_MODEL._BLFL_BH_COL_).getString("MDL_VAL", SYS_MDL_VAL.BLFL_BH);
		} else {
			BIFL_BH = SYS_MDL_VAL.BLFL_BH;
		}
		return BIFL_BH;
	}
}
