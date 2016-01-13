package com.efounder.date;

import java.util.*;
import java.text.*;

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

	long time=System.currentTimeMillis();
	Date date=new Date(time);

	int hour = date.getHours();
	int minute = date.getMinutes();
	int second = date.getSeconds();

	return String.valueOf(hour)+String.valueOf(minute)+String.valueOf(second);
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
   * 返回显示给用户的格式的日期
   * 即：将yyyy-mm-dd格式变为yyyymmdd格式
   */
  public static String unformatDate(String date){
	return date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
  }
  /**
   * 将字符串日期转换成Date型
   * @param charDate String
   * @param pattern String
   * @throws Exception
   * @return Date
   */
  public static Date convertDate(String charDate,String pattern) throws Exception {
    java.text.DateFormat dateFormat = new SimpleDateFormat(pattern);
    Date date = dateFormat.parse(charDate);
    return date;
  }
  /**
   *
   * @param date Date
   * @throws Exception
   * @return Calendar
   */
  public static Calendar convertCalendar(Date date) throws Exception {
    Calendar calr = Calendar.getInstance();
    calr.setTime(date);
    return calr;
  }
  /**
   *
   * @param charDate String
   * @param pattern String
   * @throws Exception
   * @return Calendar
   */
  public static Calendar convertCalendar(String charDate,String pattern) throws Exception {
    Date date = convertDate(charDate,pattern);
    return convertCalendar(date);
  }
  /**
   *
   * @param calr Calendar
   * @return int
   */
  public static String getYear(Calendar calr) {
    return String.valueOf(calr.get(Calendar.YEAR));
  }
  /**
   * 1,2,3    = Q1
   * 4,5,6    = Q2
   * 7,8,9    = Q3
   * 10,11,12 = Q4
   * @param calr Calendar
   * @return int
   */
  public static String getQuarter(Calendar calr) {
    int month = calr.get(Calendar.MONTH)+1;
    if ( month >= 1 && month <= 3 ) return String.valueOf(1);
    if ( month >= 4 && month <= 6 ) return String.valueOf(2);
    if ( month >= 7 && month <= 9 ) return String.valueOf(3);
    if ( month >= 10 && month <= 12 ) return String.valueOf(4);
    return null;
  }
  /**
   *  1...12
   * @param calr Calendar
   * @return int
   */
  public static String getMonth(Calendar calr) {
    int month = calr.get(Calendar.MONTH) + 1;
    if ( month < 10 ) return "0"+month; else return String.valueOf(month);
  }
  /**
   * 1-10      = 1
   * 11 - 20   = 2
   * 21 - 30/1 = 3
   * @param calr Calendar
   * @return String
   */
  public static String getTenday(Calendar calr) {
    int day = calr.get(Calendar.DAY_OF_MONTH);
    if ( day >= 1 && day <= 10 ) return String.valueOf(1);
    if ( day >= 11 && day <= 20 ) return String.valueOf(2);
    if ( day >= 21 && day <= 31 ) return String.valueOf(3);
    return null;
  }
  /**
   * 1 ... 52
   * @param calr Calendar
   * @return int
   */
  public static String getWeek(Calendar calr) {
    int week = calr.get(Calendar.WEEK_OF_YEAR);
    if ( week < 10 ) return "0"+week; else return String.valueOf(week);
  }
  /**
   *
   * @param calr Calendar
   * @return String
   */
  public static String getDay(Calendar calr) {
    int day = calr.get(Calendar.DAY_OF_MONTH);
    if ( day < 10 ) return "0"+day; else return String.valueOf(day);
  }

}
