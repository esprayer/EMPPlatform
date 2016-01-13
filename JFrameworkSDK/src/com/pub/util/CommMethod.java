
/**
 * 常用方法，格式处理，转换处理
 */
package com.pub.util;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.*;
import org.apache.log4j.Logger;



/**
 * 
 * @author tyler
 *
 */

public class CommMethod {
	private static Logger log4 = Logger.getLogger(CommMethod.class);

	
	/**
	 * 前(day)一天
	 * @param date
	 * @param day
	 * @return
	 */
    public static Date getDateBefore(Date date, int day){    	    	   
		Date rdate = new Date(date.getTime());
		rdate.setDate(date.getDate()-day);
	
		return rdate;
	}
    
    /**
     * 上day天
     * @param date
     * @param day
     * @return
     */
    public static String getDateBefore(String dates, int day){
    	Date date = CommMethod.convertStrToDate(dates, null);
		Date rdate = new Date(date.getTime());
		rdate.setDate(date.getDate()-day);
		
		return CommMethod.formatDate(rdate, null);
	}
    
    /**
     * 前(mon)一月
     * @param date
     * @param month
     * @return
     */
    public static Date getMonthBefore(Date date, int month){
		Date rdate = new Date(date.getTime());
		rdate.setMonth(date.getMonth()-month);
		return rdate;
	}
    
    
    /**
     * 前(year)一年
     * @param date
     * @param year
     * @return
     */
    public static Date getYearBefore(Date date, int year){
		Date rdate = new Date(date.getTime());
		rdate.setYear(date.getYear()-year);
		return rdate;
	}
    
    
    /**
     * 前(min)一分钟
     * @param time
     * @param min
     * @return
     */
    public static Time getTimeBefore(Time time, int min){
    	Time rtime = new Time(time.getTime());
    	rtime.setMinutes(time.getMinutes()-min);
    	return rtime;
    }
    
    
    /**
     * calendar格式化，转成String
     * @param d
     * @param expstr,日历正则表达式,如:MM-dd HH:mm
     * 如果expstr为空，默认为MM-dd HH:mm
     * @return
     */
    public static String formatCalendar(Calendar d, String expstr) {    	
	    if (d == null) {
	      return "";
	    }
	    if(ErrorCode.isEmpty(expstr)){
	    	expstr = "yyyy-MM-dd";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat(expstr);
	    java.util.Date date = new java.util.Date();
	    date.setTime(d.getTimeInMillis());
	    return sdf.format(date);
	}
    
//    /**
//     * calendar格式化，转成String
//     * @param d
//     * @param expstr,日历正则表达式,如:MM-dd HH:mm
//     * 如果expstr为空，默认为MM-dd HH:mm
//     * @return
//     */
//    public static String formatD(Date d, String expstr) {    	
//	    if (d == null) {
//	      return "";
//	    }
//	    if(ErrorCode.isEmpty(expstr)){
//	    	expstr = "yyyy-MM-dd HH:mm";
//	    }
//	    SimpleDateFormat sdf = new SimpleDateFormat(expstr);	   
//	    return sdf.format(d);
//	}
    
    
    /**
     * date格式化，转成String
     * @param d
     * @param expstr,日期正则表达式,如:MM-dd HH:mm\yyyy-MM-dd,
     * 如果expstr为空，默认为yyyy-MM-dd
     * @return
     */
    public static String formatDate(Date date, String expstr) {
	    if (date == null) {
	      return "";
	    }
	    if(ErrorCode.isEmpty(expstr)){
	    	expstr = "yyyy-MM-dd";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat(expstr);
	    return sdf.format(date);
	}
    

    /**
     * time格式化，转成String
     * @param time
     * @return格式00:00:00
     */
	public static String formatTime(Time time){
		if(time!=null){
			return time.toString();
		}
		return null;
	}
	
	
    /**
     * String转Date
     * 依据expstr的格式将字符串datestr转成Date
     * @param datestr
     * @param expstr日期正则表达式如:MM-dd HH:mm\yyyy-MM-dd,
     * 如果expstr为空，默认为yyyy-MM-dd
     * @return
     */ 
	public static Date convertStrToDate(String datestr, String expstr){
		Date date= null;
		
		if(ErrorCode.isEmpty(expstr)){
			expstr="yyyy-MM-dd";
		}
		
		if(datestr == null || datestr.equals(""))
			return null;
				
		SimpleDateFormat sdf = new SimpleDateFormat(expstr);
		
		try{
			date = sdf.parse(datestr); 
		}catch(Exception e){
			e.printStackTrace();
			log4.info(e.toString());			
		}
		return date;
	}
	
	
	/**
     * String转Time
     * @param datestr
     * @param expstr
     * @return
     */ 
	public static Time convertStrToTime(String timestr){
		Time time = null;
		
		if(ErrorCode.isEmpty(timestr))
			return null;
		
		try{
			time = Time.valueOf(timestr);
		}catch(Exception e){
			log4.info(e.toString());			
		}
		return time;
	}
	
		
	/**
	 * String转Calendar
	 * @param calstr 日历字符串:2006-09-01 12:12:12
	 * @param expstr,日历正则表达式:yyyy-MM-dd HH:mm:ss
	 * 如果expstr为空，则默认为yyyy-MM-dd HH:mm:ss
	 * @return Calendar
	 */
	public static Calendar convertStrToCalendar(String calstr, String expstr){
		if(ErrorCode.isEmpty(calstr))
			return null;
		if(ErrorCode.isEmpty(expstr)){
			expstr = "yyyy-MM-dd HH:mm:ss";
		}
		
		Calendar cal = null;
		String format = expstr;
		Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(calstr);
            cal = Calendar.getInstance();
			cal.setTimeInMillis(date.getTime());
        } catch (ParseException e) {
        	e.printStackTrace();
        }
					
		return cal;
	}
	
	
	/**
	 * 当前日历
	 * @return
	 */  
	public static Calendar getCurrentCalendar(){		
		return  Calendar.getInstance();
	}
	
	
	/**
	 * 当前日期
	 * @return
	 */
	public static Date getCurrentDate(){		
		return new Date();
	}
	
	
	/**
	 * 取当前时间
	 * @return
	 */
	public static Time getCurrentTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return Time.valueOf(sdf.format(CommMethod.getCurrentDate()));		  		  
	}
		
	
	/**
	 * 取当前日期(字符)
	 * @return
	 */
	public static String getCurrentDateStr(){		
		return CommMethod.formatDate(CommMethod.getCurrentDate(), null);
	}
	
	
	/**
	 * 取当前时间(String)
	 * @return
	 */
	public static String getCurrentTimestr(){
		return ""+CommMethod.getCurrentTime();		  		  
	}
	
	
	/**
	 * 用于数值自动对齐，在后补空格
	 * @param value
	 * @return
	 */
	public static String convertCodeBlank1(int value){
		if( value >=10 ){
			return ""+value+"&nbsp;";
		}
		return value+"&nbsp;"+"&nbsp;";
		
	}
	
	
	/**
	 * 显示10位整数，不足前面自动补0
	 * @param value
	 * @return
	 */
	public static String convertNumber10String(int value){
		return CommMethod.convertNumberString(value, "0000000000");	
	}
	
	
	/**
	 * 匹配时间输入
	 * @param timestr如16:00:20
	 * @param expstr正则表达式，如 ^\\d{1,2}:\\d{1,2}:\\d{1,2}$
	 * 如果expstr为空则，默认为^\\d{1,2}:\\d{1,2}:\\d{1,2}$
	 * @return
	 */
	public static boolean matchTimestr(String timestr, String expstr){		
		if(ErrorCode.isEmpty(expstr)){
			expstr = "^\\d{1,2}:\\d{1,2}:\\d{1,2}$";
		}

        boolean result = Pattern.compile(expstr).matcher(timestr).matches();        
        return result;
	}
	
		
	
	
	/**
	 * 匹配数字输入
	 * @param numberstr是否为数字型字符串
	 * @return
	 */
	public static boolean matchNumberstr(String numberstr){
		String regEx = "^\\d+$";
        boolean result = Pattern.compile(regEx).matcher(numberstr).matches();       
        return result;
	}
		
	
	/**
	 * 匹配日期:如2004-02-02
	 * @param yearstr是否为日期型字符串
	 * @param expstr正则表达式^\\d{4}-\\d{1,2}-\\d{1,2}$,即:0000-00-00格式
	 * 如果expstr为空则默认为
	 * @return
	 */
	public static boolean matchDatestr(String datestr, String expstr){
		if(ErrorCode.isEmpty(datestr))
			return true;
		if(ErrorCode.isEmpty(expstr)){
			expstr = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
		}
        boolean result = Pattern.compile(expstr).matcher(datestr).matches();
        return result;
	}
	
	/**
	 * 匹配日期:如邮箱地址
	 * @param emailstr地址
	 * @return
	 */
	public static boolean matchEmail(String emailstr){
		if(ErrorCode.isEmpty(emailstr))
			return false;
//		Pattern pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",Pattern.CASE_INSENSITIVE);

		String expstr = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
        boolean result = Pattern.compile(expstr).matcher(emailstr).matches();
        return result;
	}
	
	/**
	 * 一周7天的中文转换,将0转成一,1转成二
	 * @param day
	 * @return
	 */
	public static String getWeekday(int day){
		String weekday= "星期";
		if(day==0){
			weekday = weekday+"日";
		}
		else {
			weekday = weekday+getNumberName(day);
		}
		
		return weekday;
	}
	/**
	 * 一周7天的中文转换,将0转成一,1转成二
	 * @param day
	 * @return
	 */
	public static String getNumberName(int day){
		String weekday= "";
		switch(day){
		case 1:
			weekday = "一";
			break;
		case 2:
			weekday = "二";
			break;
		case 3:
			weekday = "三";
			break;
		case 4:
			weekday = "四";
			break;
		case 5:
			weekday = "五";
			break;
		case 6:
			weekday = "六";
			break;
		case 7:
			weekday = "七";
			break;
		default:
			weekday = "一";		
		}
		
		return weekday;
	}
	
	
	/**
	 * 字符转String
	 * @param value
	 * @return
	 */
	public static String getCharToString(char value){
		String temp = ""+value;
		if(ErrorCode.isEmpty(temp))
			return null;
		else
			return temp;
	}
	
	
	/**
	 * 查询条件处理
	 * @param querySql 查询条件
	 * @param tempSql　新的查询条件
	 * @return　合并后的查询条件
	 */
	public static String getQuerySql(String querySql, String tempSql){
		if(querySql==null){
			return tempSql;
		}
		else if(querySql.length()>2)
			querySql = querySql+" and "+tempSql;
		else
			querySql = querySql+tempSql;
		return querySql;
	}
	
	/**
	 * 查询条件处理
	 * @param querySql 查询条件
	 * @param tempSql　新的查询条件
	 * @return　合并后的查询条件
	 */
	public static StringBuffer getQuerySql(StringBuffer querySql, StringBuffer tempSql){
		if(querySql.length()>2){
			querySql.append(" and ");
			querySql.append(tempSql);
//			querySql = querySql+" and "+tempSql;
		}
		else
			querySql = tempSql;
		return querySql;
	}

	/**
	 * value为空时，不显示null,而显示""
	 * @param value
	 * @return
	 */
	public static String getNotNull(String value){
		if(value == null){
			return "&nbsp";
		}
		else if(value.equals("null"))
			return "&nbsp";
		else{
			return value;
		}
	}
	

	/**
	 * 对像为空时不显示null而显示成0
	 * @param obj
	 * @return
	 */
	public static String getNotNullCount(Object obj){
		String value = null;
		if(obj==null)
			value = "0";
		else 
			value = ""+obj;
		return value;
		
	}
	
	
	/**
	 * 对像为空时不显示null而显示成0.0
	 * @param obj
	 * @return
	 */
	public static String getNotNullFloat(Object obj){
		String value = null;
		if(obj==null)
			value = "0";
		else 
			value = ""+obj;
		return value;
		
	}	
	
	/**
	 * 标题过长时,截取前size位
	 * @param title
	 * @param size
	 * @return
	 */
	public static String getShortTitle(String title, int size){
		if(title!=null){
			if(title.length()>size){
				title = title.substring(0, size)+"...";
			}		
		}
		return title;
	}
	
	
	/**
	 * 排序
	 * @param arrays
	 * @return
	 */
	public static int[] sortArrays(int[] arrays){
		for(int i=0; i<arrays.length; i++){
			for(int j=0; j< i; j++){
				int temp = 0;
				if(arrays[i]<arrays[j]){
					temp = arrays[i];
					arrays[i] = arrays[j];
					arrays[j] = temp;
				}
			}
		}
		return arrays;
	}
	
	/**
	 * 
	 * @param  char[] to arrays[]
	 * @return
	 */
	public static String[] toStringArray(char[] arrays){
		String[] values = new String[arrays.length];
		for(int i=0; i<arrays.length; i++){
			values[i]=""+arrays[i];
		}
		return values;
	}
	
	/**
	 * 
	 * @param  char[] to arrays[]
	 * @return
	 */
	public static Character[] toCharacterArray(char[] arrays){
		Character[] values = new Character[arrays.length];
		for(int i=0; i<arrays.length; i++){
			values[i]=arrays[i];
		}
		return values;
	}
	
	/**
	 * 
	 * @param arrays to String
	 * @return
	 */
	public static String toString(String[] arrays){
		String values ="";
		for(int i=0; i<arrays.length; i++){
			values= values+arrays[i];
		}
		return values;
	}
	
	/**
	 * 
	 * @param arrays to String
	 * @return
	 */
	public static String toString(Character[] arrays){
		String values ="";
		for(int i=0; i<arrays.length; i++){
			values= values+arrays[i];
		}
		return values;
	}
	
	/**
	 * 去除重复
	 * @param list (id, List) ,为已排序的有重复id
	 * @return
	 */
	public static List distinctList(List list){
		List tList = null;
		if(list==null && list.size()==0)
			return null;
		List vList = new LinkedList();	
		int cur = 0;
		int var = 0;
		for(int i=0; i<list.size(); i++){			
			tList = (List)list.get(i);
			var = Integer.parseInt(""+tList.get(0));
			if(cur!=var){
				cur = var;
				vList.add(tList.get(1));
			}
		}	
		return vList;
	}
	
	
	/**
	 *使从数库读取的数据换行显示 
	 * @param value
	 * @return
	 */
	public static String convertText(String value){
		return value.replaceAll("\n", "<BR>");
	}
	
//	/**
//	 * 
//	 * @param value
//	 * @return
//	 */
//	public static String convertFloat2String(String value){
//		String temp = null;
//		if(ErrorCode.isEmpty(value)){
//			return "0.0";
//		}
//		else if(value.length()>value.indexOf(".")+3)
//			return value.substring(0, value.indexOf(".")+3);
//		else
//			return value;
//		
//	}
	
	
	/**
	 *金额格式化
	 * @param value
	 * @param expstr表达式###,###,###,###,##0.0
	 * 如果expstr为空，则默认为###,###,###,###,##0.0
	 * @return
	 */
	public static String convertMoneyString(double value, String expstr){
		if("NaN".equals(""+value)){
			return "0";
		}
		if(ErrorCode.isEmpty(expstr)){
			expstr = "###,###,###,###,##0.0";
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(expstr);
		return df.format(value);
	}
	
	
	/**
	 * 英式金额格式化,带2位小数
	 * @param value
	 * @return
	 */
	public static String convertMoney2String(double value){
		return CommMethod.convertMoneyString(value, "###,###,###,###,##0.00");				
	}
	
	
	/**
	 * 中式金额格式化，带2位小数
	 * @param value
	 * @return
	 */
	public static String convertMoney2StringCN(double value){
		return CommMethod.convertMoneyString(value, "####,####,####,###0.00");
	}
	
	
	/**
	 * 数值处理，前面补0
	 * @param value
	 * @param expstr表达式:00000000
	 * @return
	 */
	public static String convertNumberString(double value, String expstr){		
		if("NaN".equals(""+value)){
			return "0";
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(expstr);
		return df.format(value);
	}
	
	
	/**
	 * 数值处理，至少两位
	 * @param value
	 * @return
	 */
	public static String convertNumber2String2(double value){
		return CommMethod.convertNumberString(value, "00");
	}
	
	
	/**
	 * 数值处理，至少两位,精确到2位小数点
	 * @param value
	 * @return
	 */
	public static String convertNumber2StringDecimal2(double value){
		return CommMethod.convertNumberString(value, "########0.00");
	}
	
	/**
	 * Date转成int,以年、月、日共同组成一组数字
	 * @param date
	 * @return
	 */
	public static int convertDateToInt(Date date){
		if(date==null)
			return 0;
		String value_str = ""+(1900+date.getYear())+CommMethod.convertNumber2String2(date.getMonth()+1)+CommMethod.convertNumber2String2(date.getDate());
		return Integer.parseInt(value_str);
	}
	
	
	/**
	 * Time转成int,以时、分、秒共同组成一组数字
	 * @param date
	 * @return
	 */
	public static int convertTimeToInt(Time time){
		if(time==null) 
			return 0;
		String time_str = time.toString().replace(":", "");
		return Integer.parseInt(time_str);
	}
	
	
	/**
	 * 时间比较
	 * @param time1
	 * @param time2
	 * @return:time1>time2则value=1
	 * 			time1<time2则value=-1
	 * 			time1==time2则value=0
	 */
	public static int compareTime(Time time1, Time time2){
		
		int value = 0;
		int t1 = Integer.parseInt(time1.toString().replaceAll(":", ""));
		int t2 = Integer.parseInt(time2.toString().replaceAll(":", ""));
		if(t1>t2){
			value = 1;
		}
		else if(t1<t2){
			value = -1;
		}
		else if(t1==t2){
			value = 0;
		}
		return value;
	}
	
	
	/**
	 * 子集处理
	 * @param idstrs:id1, id2, id3,
	 * @return 返回sql要查询的结果子集 EX:in (id1, id2, id3)
	 */
	public static String getRangeIdStr(String idstrs){
		 if(!ErrorCode.isEmpty(idstrs)){
			 return ("("+idstrs+")").replaceAll(",\\)",")");
		 }
		 return null;
		
	}
	
	public static String getModuleManysStr(String[] moduleManyIds){
		String module_manys_str="";
		if(moduleManyIds!=null && moduleManyIds.length>0){						
			for(int i=0; i<moduleManyIds.length; i++){
				module_manys_str = module_manys_str + moduleManyIds[i]+",";
			}
			if(module_manys_str.lastIndexOf(",")>0){
				module_manys_str = module_manys_str.substring(0, module_manys_str.length()-1);
			}
		}
		if(ErrorCode.isEmpty(module_manys_str)){
			module_manys_str="0";
		}
		
		return module_manys_str;
	}
	
	/**
	 * 成绩分析%处理
	 * @param value
	 * @return
	 */
	public static String format2Rate(double value){
		if(value==-1){
			//log4.info("value="+value);
			return "∞";
		}
		else if(value>0){
			return CommMethod.convertNumberString(value*100, "###0.0");
		}
		else {
			//log4.info("value="+value);
			return "0";
		}
	}
	
	public static float getMaxValue(float value1, float value2){
		if(value1>=value2)
			return value1;
		else
			return value2;
	}
	
	public static float getMinValue(float value1, float value2){
		if(value1>=value2)
			return value2;
		else
			return value1;
	}
	
	
}
