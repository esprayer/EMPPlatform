package com.efounder.dctbuilder.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import com.core.xml.StubObject;
import com.efounder.buffer.DictDataBuffer;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.util.ESPClientContext;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.dctmodel.DCTMetaDataManager;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dctbuilder.client.pub.ESPLogDlg;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.loader.DefaultDctProvider;
import com.efounder.dctbuilder.loader.DefaultResolver;
import com.efounder.dctbuilder.variant.Variant;
import com.efounder.dctbuilder.view.DictView;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.help.HelpInfoContext;
import com.pub.util.StringFunction;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DictUtil {

    /**
     *
     */
    public DictUtil() {
    }

    /**
     * 获取星期
     *
     * @return String
     */
    public static String getWeekDay(Calendar c) {
        int count = c.get(c.DAY_OF_WEEK);
        switch (count) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
        }
        return null;
    }

    /**
     * 获取当前年
     *
     * @return String
     */
    public static String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return formatDate(c.getTime(), "yyyy");
    }

    /**
     * 获取当前年
     *
     * @return String
     */
    public static String getCurrentYearMonth() {
        Calendar c = Calendar.getInstance();
        return formatDate(c.getTime(), "yyyyMM");
    }

    /**
     * 获取当前年
     *
     * @return String
     */
    public static String getCurrentYearMonthDay() {
        Calendar c = Calendar.getInstance();
        return formatDate(c.getTime(), "yyyyMMdd");
    }

    /**
     * 取指定日期的月的第一天
     *
     * @param   date String  指定日期
     * @param offset int     指定日期的月偏移，0为指定日期的当前月
     * @param format String  日期格式，默认是YYYYMMDD
     * @return       String  月份的最后一天
     */
    public static String getMonthFirstDay(String date, int offset,
                                          String format) {
        if (date == null || date.trim().length() == 0) {
            date = getCurrentYear() + "01";
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int mont = Integer.parseInt(date.substring(4, 6)) - 1 + offset; //月份是从0开始的数组，所以这里要用实际月份-1。
        if (mont > 11)
            mont = 11;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, mont);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return formatDate(c.getTime(), format);
    }

    public static java.util.Date getUtilDate(String dateVal) {
        if (dateVal == null || dateVal.length() < 4 || dateVal.startsWith("@")) {
            return new java.util.Date();
        }
        dateVal = StringFunction.replaceString(dateVal, "-", "");
        dateVal = StringFunction.replaceString(dateVal, ":", "");
        dateVal = StringFunction.replaceString(dateVal, ".", "");
        dateVal = StringFunction.replaceString(dateVal, "/", "");
        dateVal = StringFunction.replaceString(dateVal, " ", "");
        String i1 = dateVal.substring(0, 4);
        String i2 = "01";
        String i3 = "01";
        String i4 = "00";
        String i5 = "00";
        String i6 = "00";
        if (dateVal.length() >= 8) {
            i2 = dateVal.substring(4, 6);
            i3 = dateVal.substring(6, 8);
            if (dateVal.length() >= 14) {
                i4 = dateVal.substring(8, 10);
                i5 = dateVal.substring(10, 12);
                i6 = dateVal.substring(12, 14);
            }
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(i1));
        c.set(Calendar.MONTH, Integer.parseInt(i2) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(i3));
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(i4));
        c.set(Calendar.MINUTE, Integer.parseInt(i5));
        c.set(Calendar.SECOND, Integer.parseInt(i6));
        java.util.Date d = new java.util.Date(c.getTime().getTime());
        return d;
    }

    /**
     * 根据字符串获取一个日期型
     *
     * @param dateString String
     * @return Date
     */
    public static java.sql.Date getSqlDate(String dateVal) {
        if (dateVal == null || dateVal.length() < 4) {
            return null;
        }
        dateVal = StringFunction.replaceString(dateVal, "-", "");
        dateVal = StringFunction.replaceString(dateVal, ":", "");
        dateVal = StringFunction.replaceString(dateVal, ".", "");
        dateVal = StringFunction.replaceString(dateVal, "/", "");
        dateVal = StringFunction.replaceString(dateVal, " ", "");
        String i1 = dateVal.substring(0, 4);
        String i2 = "01";
        String i3 = "01";
        String i4 = "00";
        String i5 = "00";
        String i6 = "00";
        if (dateVal.length() >= 8) {
            i2 = dateVal.substring(4, 6);
            i3 = dateVal.substring(6, 8);
            if (dateVal.length() >= 14) {
                i4 = dateVal.substring(8, 10);
                i5 = dateVal.substring(10, 12);
                i6 = dateVal.substring(12, 14);
            }
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(i1));
        c.set(Calendar.MONTH, Integer.parseInt(i2));
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(i3));
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(i4));
        c.set(Calendar.MINUTE, Integer.parseInt(i5));
        c.set(Calendar.SECOND, Integer.parseInt(i6));
        java.sql.Date sd = new java.sql.Date(c.getTime().getTime());
        return sd;
    }

    /**
     * 根据字符型的结果返回一个时间戳，如果结果非法返回当前时间
     *
     * @param longVal String
     * @return Timestamp
     */
    public static java.sql.Timestamp getTimeStamp(String longVal) {
        long l = 0;
        try {
            l = Long.parseLong(longVal);
        }
        catch (NumberFormatException ex) {
            l = System.currentTimeMillis();
        }
        java.sql.Timestamp time = new Timestamp(l);
        return time;
    }

    /**
     * 根据字符串获取一个日期型
     *
     * @param dateString String
     * @return Date
     */
    public static java.sql.Timestamp getTimeStamp2(String dateVal) {
        if (dateVal == null || dateVal.length() < 4) {
            return null;
        }
        dateVal = StringFunction.replaceString(dateVal, "-", "");
        dateVal = StringFunction.replaceString(dateVal, ":", "");
        dateVal = StringFunction.replaceString(dateVal, ".", "");
        dateVal = StringFunction.replaceString(dateVal, "/", "");
        dateVal = StringFunction.replaceString(dateVal, " ", "");
        String i1 = dateVal.substring(0, 4);
        String i2 = "01";
        String i3 = "01";
        String i4 = "00";
        String i5 = "00";
        String i6 = "00";
        if (dateVal.length() >= 8) {
            i2 = String.valueOf(Integer.parseInt(dateVal.substring(4, 6)) - 1);
            i3 = dateVal.substring(6, 8);
            if (dateVal.length() >= 14) {
                i4 = dateVal.substring(8, 10);
                i5 = dateVal.substring(10, 12);
                i6 = dateVal.substring(12, 14);
            }
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(i1));
        c.set(Calendar.MONTH, Integer.parseInt(i2));
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(i3));
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(i4));
        c.set(Calendar.MINUTE, Integer.parseInt(i5));
        c.set(Calendar.SECOND, Integer.parseInt(i6));
        java.sql.Timestamp ts = new java.sql.Timestamp(c.getTime().getTime());
        return ts;
    }

    public static String getDateStr(String value) {
        value = StringFunction.replaceString(value, "-", "");
        value = StringFunction.replaceString(value, ":", "");
        value = StringFunction.replaceString(value, ".", "");
        value = StringFunction.replaceString(value, "/", "");
        value = StringFunction.replaceString(value, " ", "");
        return value;
    }

    public static String formatCurDate(String format) {
        if (format == null || format.trim().length() == 0) {
            format = "yyyyMMdd";
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(c.getTime());
    }

    /**
     * 格式化日期
     *
     * @param   date Date
     * @param format String
     * @return       String
     */
    public static String formatDate(java.util.Date date, String format) {
        if (format == null || format.trim().length() == 0) {
            format = "yyyyMMdd";
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }

    /**
     * 取指定日期的月最后一天
     *
     * @param   date String  指定日期
     * @param offset int     指定日期的月偏移，0为指定日期的当前月
     * @param format String  日期格式，默认是YYYYMMDD
     * @return       String  月份的最后一天
     */
    public static String getMonthLastDay(String date, int offset, String format) {
        int year = Integer.parseInt(date.substring(0, 4));
        int mont = Integer.parseInt(date.substring(4, 6)) - 1 + offset; //月份是从0开始的数组，所以这里要用实际月份-1。
        if (mont > 11)
            mont = 11;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, mont);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(c.getTime(), format);
    }

    /**
     *
     * @param    ds DataSet
     * @param   col String
     * @param value Object
     */
    public static void setVariantValue(ClientDataSet ds, String col, Object value) {
    	ColumnMetaData    columnMetaData = ds.hasColumn(col);
        if (ds.hasColumn(col) == null) {
            return;
        }
        char colType = columnMetaData.getColType().charAt(0);
        String val = "0";
        switch (colType) {
            case Variant.DOUBLE:
                if (value != null && value.toString().length() > 0)
                    val = value.toString();
                ds.getRowSet().putNumber(col, Double.parseDouble(val));
            case Variant.BIGDECIMAL:
                if (value != null && value.toString().length() > 0)
                    val = value.toString();
                ds.getRowSet().putNumber(col, new BigDecimal(val));
                break;
            case Variant.STRING:
                if (value == null) {
                    value = "";
                }
                ds.getRowSet().putString(col, value.toString());
                break;
            case Variant.DATE:
                if (value instanceof java.sql.Date)
                	ds.getRowSet().putObject(col, ( (java.sql.Date) value).getTime());
                if (value instanceof java.util.Date)
                	ds.getRowSet().putObject(col, ( (java.util.Date) value).getTime());
                if (value instanceof String)
                	ds.getRowSet().putObject(col, Long.parseLong(value.toString()));
                break;
            case Variant.TIMESTAMP:
                long l = 0;
                if (value instanceof String) {
                  if (value.toString().toUpperCase().equals("SYSDATE"))
                    l = System.currentTimeMillis();
                  else
                    l = Long.parseLong(value.toString());
                }
                if (value instanceof java.sql.Date)
                  l = ( (java.sql.Date) value).getTime();
                if (value instanceof java.util.Date)
                  l = ( (java.util.Date) value).getTime();
                if (value instanceof java.sql.Timestamp)
                  l = ( (java.sql.Timestamp) value).getTime();
                ds.getRowSet().putObject(col, l);
                break;
        }
    }

    public static String getVariantValue(ClientDataSet ds, String col) {
    	Object o = null;
        Column column = ds.getColumn(col);
        if (column == null)
            return "";
        char colType = column.getDataType().charAt(0);
        switch (colType) {
            case Variant.BIGDECIMAL:
                return String.valueOf(ds.getRowSet().getNumber(col, 0).doubleValue());
            case Variant.STRING:
                return ds.getRowSet().getString(col, "");
            case Variant.DATE:
            	o = ds.getRowSet().getDate(col, null);
            	if(o == null) return "";
            	else return String.valueOf(o);
            case Variant.TIMESTAMP:
            	o = ds.getRowSet().getTimestamp(col, null);
            	if(o == null) return "";
            	else return String.valueOf(ds.getRowSet().getTimestamp(col, null).getTime());
            default:
                return "";
        }
    }

    public static String getString(Variant o) {
        return String.valueOf(getInt(o));
    }

    public static int getInt(Variant o) {
//        switch (o.getType()) {
//            case Variant.BIGDECIMAL:
//                return o.getAsInt();
//            case Variant.STRING:
//                return Integer.parseInt(o.getString());
//        }
        return 0;
    }

    public static String getNextCode(String strInput, int iSkip, int iLV) {
        int[] pVals = new int[strInput.length()];
        for (int i = 0; i < pVals.length; i++) {
            pVals[i] = strInput.charAt(i) - '0';
        }

        int iValSkip = iSkip;

        //自右向左扫描。
        for (int i = pVals.length - 1; i >= 0; i--) {
            int iNew = pVals[i] + iValSkip;
            if (iNew >= iLV) {
                iValSkip = iNew / iLV; //商数
                iNew = iNew % iLV; //余数。
                pVals[i] = iNew;
            }
            else {
                pVals[i] = iNew;
                break;
            }
        }

        StringBuffer pBuf = new StringBuffer();
        for (int i = 0; i < pVals.length; i++) {
            char pChar = (char) (pVals[i] + '0');
            pBuf.append(pChar);
        }
        return pBuf.toString();
    }

    public static java.util.List getValueFromDataSet(ClientDataSet ds, String col) {
        java.util.List list = new ArrayList();
        if (ds.getRowCount() > 0) {
        	for(int i = 0; i < ds.getRowCount(); i++) {
        		list.add(ds.getRowSet(i).getString(col, ""));
        	}
        }
        return list;
    }

    /**
     * 根据编码结构和级数，返回该编号的长度
     *
     * @param stru String
     * @param   js int
     * @return     int
     */
    public static int getCodeLength(String stru, int js) {
        char[] chars = stru.toCharArray();
        int length = 0;
        for (int i = 0; i < js; i++) {
            char c = chars[i];
            length += Integer.parseInt(String.valueOf(c));
        }
        return length;
    }
    
    /**
     *
     * @param DCT_ID String
     * @param serverName String
     * @return DCTMetaData
     */
	public static DCTMetaData getDCTMetaData(String DCT_ID, String serverName) throws Exception {
       JParamObject PO = JParamObject.Create();
       PO.setEAIServer(serverName);
       ESPClientContext ctx = ESPClientContext.getInstance(PO);
       return (DCTMetaData) DCTMetaDataManager.getDCTDataManager().getMetaData(
           ctx, DCT_ID);
   }
	
	public static EFDataSet getHelpDataSet(HelpInfoContext hic, JParamObject PO) {
		try {
			// 支持跨数据库取数，如果指定了Server的话
//			DictUtil.setServerPO(PO, hic.getServerName());
			JResponseObject RO = (JResponseObject) com.efounder.eai.EAI.DAL
					.IOM("ESPHelp", "getHelpData", PO, hic);
			if (hic.getBHColumn().trim().length() == 0) {
				HelpInfoContext aa = (HelpInfoContext) RO.getAddinObject();
				hic.setDictInfo(aa.getDictInfo());
			}
			EFDataSet ers = (EFDataSet) RO.getResponseObject();
			return ers;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
    *
    * @param efrs EFRowSet
    * @return StubObject
    */
   public static StubObject getStubFromRowSet(EFRowSet efrs) {
       StubObject so = new StubObject();
       so.setStubTable(new Hashtable());
       so.getStubTable().putAll(efrs.getDataMap());
       return so;
   }
   
   public static void setDefaultDctViewProperty(DictModel model) {
//       DefaultDictPainter tp = new DefaultDictPainter(model);
//       if (model.getView() != null)
//           ( (DictView) model.getView()).setTablePainter(tp);
       DefaultDctProvider provider = new DefaultDctProvider();
       DefaultResolver resolver = new DefaultResolver();
       model.setProvider(provider);
       model.setResolver(resolver);
   }
   
   /**
	 * 取某个DataSet在某列上的最大值
	 * 
	 * @param cds
	 *            ClientDataSet
	 * @param column
	 *            String
	 * @param enablevent
	 *            boolean
	 * @return String
	 */
	public static String getMaxValue(ClientDataSet cds, String column, String prefix, boolean enablevent) {
		if (cds.hasColumn(column) == null)
			return "";
		String tmp = "";
		for(int i = 0; i < cds.getRowCount(); i++) {
			String val = cds.getRowSet(i).getString(column, "");
			if (val.startsWith(prefix) && val.compareTo(tmp) > 0)
				tmp = val;
		}
		return tmp;
	}
	
	/**
	 *
	 * @param fobj String
	 * @param code String
	 * @return EFRowSet
	 */
	public static EFRowSet getRowSetFromBuffer(String fobj, String code, String serverName) {
		return getRowSetFromBuffer(fobj, code, serverName, false);
	}
	/**
	 *
	 * @param fobj String
	 * @param code String
	 * @return EFRowSet
	 */
	public static EFRowSet getRowSetFromBuffer(String fobj, String code, String serverName, boolean bload) {
		if (code == null) code = "";
		code = code.trim();
		JParamObject PO = JParamObject.Create();
		PO.setEAIServer(serverName);
		EFRowSet rowSet = DictDataBuffer.getDefault(PO).getDictDataItem(PO, fobj, code, bload);
		return rowSet;
	}
	
	/**
     * 显示单表日志
     * @param logString String
     */
    public static boolean showLog(String logString) {
        ESPLogDlg dlg = new ESPLogDlg(EAI.EA.getMainWindow(), "消息日志", true);
        dlg.appendMessage(logString);
        dlg.setVisible(true);
        return dlg.Result == dlg.RESULT_OK;
    }
}

