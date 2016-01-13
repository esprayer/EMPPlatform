package jtoolkit.date.classes;

import java.util.*;
import java.lang.*;

public class DateFunction {

  public DateFunction() {
  }
  /**
   * 返回显示给用户的中文格式的日期
   * 即：将yyyymmdd格式变为yyyy年mm月dd格式
   */
  public static String formatChDate(String date){
	try{
	  return date.substring(0,4)+"年"+date.substring(4,6)+"月"+date.substring(6,8)+"日";
	}catch(Exception e){
	  return null;
	}
  }
  /**
   * 返回显示给用户的中文格式的时间
   * 即：将hhmm格式变为hh时mm分格式
   */
  public static String formatChTime(String time){
	return time.substring(0,2)+"时"+time.substring(2,4)+"分";
  }
  /**
   * 返回显示给用户的格式的日期
   * 即：将yyyymmdd格式变为yyyy-mm-dd格式
   */
  public static String formatDate(String date){
	return date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
  }
  /**
   * 返回显示给用户的格式的时间
   * 即：将hhmm格式变为hh:mm格式
   */
  public static String formatTime(String time){
	return time.substring(0,2)+":"+time.substring(2,4);
  }
  /**
   * 返回当前日期 ，格式为yyyymmdd格式
   */
  public static String getCurrentDate(){
	long time=System.currentTimeMillis();
	Date date=new Date(time);
	int year=1900+date.getYear();
	int month=date.getMonth()+1;
	int day = date.getDate();
	//
	String ymd=String.valueOf(year);
	if(month<10)
	  ymd+="0";
	ymd+=String.valueOf(month);
	if(day<10)
	  ymd+="0";
	ymd+=String.valueOf(day);
	return ymd;
  }
  /**
   * 返回当前时间 ，格式为hhmmss格式
   */
  public static String getCurrentTime(){
    Calendar cal = new GregorianCalendar();
    int hour = cal.get(Calendar.HOUR_OF_DAY); // 0..23
    int minute = cal.get(Calendar.MINUTE); // 0..59
    int second = cal.get(Calendar.SECOND); // 0..59
    String value = "";
    if(hour < 10){
      value += "0" + hour;
    }else{
      value += hour;
    }
    if(minute < 10){
      value += "0" + minute;
    }else{
      value += minute;
    }
    if(second < 10){
      value += "0" + second;
    }else{
      value += second;
    }
    return value;

//	long time=System.currentTimeMillis();
//	Date date=new Date(time);
//
//	int hour = date.getHours();
//	int minute = date.getMinutes();
//	int second = date.getSeconds();
//
//	return String.valueOf(hour)+String.valueOf(minute)+String.valueOf(second);
  }
  /**
   * 返回要存到数据库里的日期字符串
   * 即：将yyyymmdd格式变为yyyymmdd格式
   */
  public static String getDate(String str){
	if(!isLegalDate(str)) return null;
	return str.substring(0,4)+str.substring(4,6)+str.substring(6,8);
  }
  /**
   * 判断给定的字符串是否是一个合法的日期表示
   * 本系统中的日期表示均为：YYYYMMDD。 ?表示任一字符
   * 注：这个格式是用户输入和显示给用户的，实际存到数据库里时要去掉?
   * @param     str      要进行处理的参数字符串
   * @return    boolean  true or false
   */
  public static boolean isLegalDate(String str) {
    String tmp = str.trim();
    //if(tmp.length()!=8) return false;
    try {
      int year = Integer.parseInt(tmp.substring(0, 4));
      if (year < 1900 || year > 3000)
        return false;
      int month = Integer.parseInt(tmp.substring(4, 6));
      if (month < 1 || month > 12)
        return false;
      int day = Integer.parseInt(tmp.substring(6, 8));
      if (day < 1)
        return false;
//      if (month == 2) {
//        if ( (year % 400 == 0) || ( (year % 4 == 0) && (year % 100 != 0))) {
//          if (day > 29)
//            return false;
//        } else
//        if (day > 28)
//          return false;
//      } else if (month == 8) {
//        if (day > 31) {
//          return false;
//        }
//      } else {
//        if (day > (30 + (month % 2))) {
//          return false;
//        }
//      }
      //
      switch (month) {
        case 2:
          if ( (year % 400 == 0) || ( (year % 4 == 0) && (year % 100 != 0))) {
            if (day > 29)
              return false;
          } else if (day > 28) {
            return false;
          }
          break;
        //31天
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
          if (day > 31) {
            return false;
          }
          break;
        //default 30天
        default:
          if (day > 30) {
            return false;
          }
          break;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 传入一个日期,加上或减去一个数后,得到新的日期
   * @param psDate String
   * @param piValue int
   * @return String
   */
  public static String getBeforeDate(String psDate, int piValue) {
      int year, month, day;
      String asDate;
      Calendar cl = Calendar.getInstance();
      year = Integer.valueOf(psDate.substring(0, 4)).intValue();
      cl.set(cl.YEAR, year);
      //Calendar的月份是从0开始的
      month = Integer.valueOf(psDate.substring(4, 6)).intValue() - 1;
      cl.set(cl.MONTH, month);
      day = Integer.valueOf(psDate.substring(6)).intValue();
      cl.set(cl.DAY_OF_MONTH, day);
      cl.add(cl.DATE, piValue);
      year = cl.get(cl.YEAR);
      month = cl.get(cl.MONDAY) + 1;
      day = cl.get(cl.DAY_OF_MONTH);
      asDate = String.valueOf(year);
      if (month < 10)
          asDate += "0" + String.valueOf(month);
      else
          asDate += String.valueOf(month);
      if (day < 10)
          asDate += "0" + String.valueOf(day);
      else
          asDate += String.valueOf(day);
      return asDate;
  }

  /**
   * 取某个月的最后一天
   * @param psYear String
   * @param psMon String
   * @return String
   */
  public static String getLastDay(String psYear, String psMon) {
      int year, month;
      year = Integer.valueOf(psYear).intValue();
      month = Integer.valueOf(psMon).intValue();
      if (month == 12) {
          year += 1;
          month = 0;
      }
      Calendar cl = Calendar.getInstance();
      cl.set(cl.YEAR, year);
      cl.set(cl.MONTH, month);
      cl.set(cl.DAY_OF_MONTH, 1);
      cl.add(cl.DATE, -1);
      return String.valueOf(cl.get(cl.DAY_OF_MONTH));
    }

}
