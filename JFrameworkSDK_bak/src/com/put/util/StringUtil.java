package com.put.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class StringUtil {
	public static String formatDate(String F_DATE) {
		return F_DATE.substring(0, 4) + "-" + F_DATE.substring(4, 6) + "-" + F_DATE.substring(6, 8);
	}
	
	public static Date formatDate(String format, String F_DATE) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			date = formatter.parse(F_DATE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getDateString(String format, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getDate(String F_DATE) {
		return F_DATE.replaceAll("-", "");
	}
	
	public static String nextId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
     * 取得指定月份的第一天
     *
     * @param strdate String
     * @return String
     */
	public static String getMonthBegin(String strdate) {
        return strdate + "01";
	}
    /**
     * 取得指定月份的最后一天
     *
     * @param strdate String
     * @return String
     */
    public static String getMonthEnd(String strdate) {
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    	Calendar cal = Calendar.getInstance();
    	// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
    	cal.set(Calendar.YEAR,getYear(strdate));
    	cal.set(Calendar.MONTH, getMonth(strdate));
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	String day_last = df.format(cal.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();
    	return day_last;
    }
    
    private static int getYear(String strDate) {
    	strDate = strDate.replaceAll("-", "");
    	String strYear = "";
    	int year = 0;
    	try {
    		strYear = strDate.substring(0, 4);
    		year = Integer.parseInt(strYear);
    	} catch(Exception ce) {
    		ce.printStackTrace();
    	}
    	return year;
    }
    
    private static int getMonth(String strDate) {
    	strDate = strDate.replaceAll("-", "");
    	String strMonth = "";
    	int month = 0;
    	try {
    		strMonth = strDate.substring(4, 6);
    		month = Integer.parseInt(strMonth);
    	} catch(Exception ce) {
    		ce.printStackTrace();
    	}
    	return month;
    }
    
    /**
     * 常用的格式化日期
     *
     * @param date Date
     * @return String
     */
    public String formatDate(java.util.Date date)
    {
        return formatDateByFormat(date,"yyyy-MM-dd");
    }
    /**
     * 以指定的格式来格式化日期
     *
     * @param date Date
     * @param format String
     * @return String
     */
    public String formatDateByFormat(java.util.Date date,String format)
    {
        String result = "";
        if(date != null)
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
