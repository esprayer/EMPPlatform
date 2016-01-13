package jframework.log;

import java.util.ArrayList;
import jframework.foundation.classes.JActiveDComDM;
import java.text.SimpleDateFormat;

import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;


/**
 * <p>Title: 日志管理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005　</p>
 * <p>Company: pansoft</p>
 * @author hufeng
 * @version 1.0
 */
public class JLogManager {
  private static boolean   mWriteLog = true;   //是否记录日志
  private static int       mLogCount = 30;     //多少条日志提交一次
  private static String    mLoginDate = "";    //系统登录日期
  private static String    mLoginTime = "";    //系统登录时间
  private static ArrayList mLogList  = new ArrayList();   //存放日志的数组
  private static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMdd.HH:mm:ss");
  private static String    mRelative = null;
  /**
   * 不记录日志的标题列表
   */
  private static String    mTitleList[] = {
      "帮助"
  };

  public JLogManager() {
  }

  /**
   * 增加日志信息
   * @param title String
   * @param startDate String
   * @param startTime String
   * @param endDate String
   * @param endTime String
   */
  public static void writeLog(String title,String startDate,String startTime,String endDate,String endTime){
    if(!mWriteLog){
      return;
    }
    //当前标题是否需要记录
    if(!isWriteTitle(title)){
      return;
    }
    /**
     * 改为只存数据，以提高执行效率
     * modified by hufeng 2008.1.15
     */
    ArrayList dataList = new ArrayList();
    dataList.add(startDate);
    dataList.add(startTime);
    dataList.add(endDate);
    dataList.add(endTime);
    dataList.add(title);
    dataList.add("");
    dataList.add(mLoginDate);
    //
    mLogList.add(dataList);
    //达到一定量，保存到数据库
    if (mLogList.size() >= mLogCount) {
      saveLogToDB();
    }
  }

  /**
   * 当前标题是否记录
   * @param title String
   * @return boolean
   */
  private static boolean isWriteTitle(String title){
    for(int i=0; i<mTitleList.length; i++){
      if(title.indexOf(mTitleList[i]) >= 0){
        return false;
      }
    }
    return true;
  }

  /**
   * 将当前日志写入数据库中
   */
  public static void writeLog(){
    if(!mWriteLog){
      return;
    }
    if(mLogList.size() > 0){
      saveLogToDB();
    }
  }


  /**
   * 写数据改变日志
   * @param gnbh String
   * @param sjbh String
   * @param datetime String[]
   */
  public static void writeDataLog(String gnbh,String sjbh,String datetime[]){
    if(!mWriteLog){
      return;
    }
    String date,time,strSql;
    date = datetime[0];
    time = datetime[1];
//    String userID = PO.GetValueByEnvName("UserID","");
//    String user   = PO.GetValueByEnvName("UserName","");
//    String name   = PO.GetValueByEnvName("UserCaption","");
//    //
//    strSql = "delete from LSRECO where F_USER = '" + userID + "' and F_LOGB = '" + mLoginDate + "'" +
//        " and F_DATB = '" + date + "' and F_TIMB = '" + time + "'";
//    mLogList.add(strSql);
//    strSql = "insert into LSRECO(F_USERID,F_USER,F_NAME,F_DATB,F_TIMB,F_DATE,F_TIME,F_USES,F_SJBH,F_LOGB)" +
//        " values('" + userID + "','" + user + "','" + name + "','" + date + "','" + time + "','" +
//        date + "','" + time + "','" + gnbh + "','" + sjbh + "','" + mLoginDate + "')";
//    mLogList.add(strSql);

    /**
     * 改为只存数据，以提高执行效率
     * modified by hufeng 2008.1.15
     */
    ArrayList dataList = new ArrayList();
    dataList.add(date);
    dataList.add(time);
    dataList.add(date);
    dataList.add(time);
    dataList.add(gnbh);
    dataList.add(sjbh);
    dataList.add(mLoginDate);
    //
    mLogList.add(dataList);

    /**
     * 更新日志
     * 如果更新成功，则清除掉当前日志
     */
    if(mLogList.size() >= mLogCount){
      saveLogToDB();
    }
  }

  /**
   * 写数据改变日志
   * @param gnbh String
   * @param sjbh String
   */
  public static void writeDataLog(String gnbh,String sjbh){
    // 取出调用时间
    String datetime[] = getCurrentDateTime();
    writeDataLog(gnbh,sjbh,datetime);
  }

  /**
   * 退出登录时，更新退出时间
   * 同时将日志信息写入数据库
   */
  public static void writeLoginOut(){
    if(!mWriteLog){
      return;
    }
    JParamObject PO = JParamObject.Create();
    String dateTime[] = getCurrentDateTime();
    String date = dateTime[0];
    String time = dateTime[1];
    String userID = PO.GetValueByEnvName("UserID","");
    String strSql = "update LSLOGREC set F_DATE = '" + date + "',F_TIME = '" + time + "'" +
        " where F_USERID = '" + userID + "' and F_DATB = '" + mLoginDate + "' and F_TIMB = '" + mLoginTime + "'";
    ArrayList sqlList = new ArrayList();
    sqlList.add(strSql);
    try {
		JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityObject", "writeLogoutLog", PO,sqlList);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    /**
     * 更新日志
     * 如果更新成功，则清除掉当前日志
     */
    if(mLogList != null && mLogList.size()>0){
      saveLogToDB();
    }
  }

  /**
   * 取当前日期
   * CurrentDate 当前日期 yyyymmdd
   * CurrentTime 当前时间 hh:mm:ss
   * @return String
   */
  public static String[] getCurrentDateTime(){
    long current ;
    String value;
    //如果应用服务器的时间与本地时间相差多少还没有计算过，则
    if(mRelative == null){
      long begin;
      JParamObject PO = JParamObject.Create();
      begin = System.currentTimeMillis();
      JResponseObject RO = null;
	try {
		RO = (JResponseObject) EAI.DAL.IOM("SecurityObject", "getCurrentTimeMillis", PO);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      if (RO != null && RO.ErrorCode > 0) {
        mRelative = (String)RO.ResponseObject;
        //使用当前获得的应用服务器时间
        current = Long.parseLong(mRelative);
        //用返回的应用服务器时间减掉本地的时间
        mRelative = String.valueOf(Long.parseLong(mRelative) - begin);
      }
      //如果没取到，则直接使用本地时间
      else{
        current = System.currentTimeMillis();
      }
    }
    //如果计算过，则取本地时间，再加上差就可以了
    else{
      current = System.currentTimeMillis()+Long.parseLong(mRelative);
    }
    value = sdfDateTime.format(new java.util.Date(current));
    String[] date = new String[2];
    date[0] = value.substring(0,value.indexOf("."));
    date[1] = value.substring(value.indexOf(".")+1);
    return date;
  }


  /**
   * 取当前日期
   * CurrentDate 当前日期 yyyymmdd
   * @return String
   */
  public static String getCurrentDate(){
    return getCurrentDateTime()[0];
  }

  /**
   * 取当前时间
   * CurrentDate 当前日期 yyyymmdd
   * @return String
   */
  public static String getCurrentTime(){
    return getCurrentDateTime()[1];
  }

  /**
   * 是否记录日志
   * @return boolean
   */
  public static boolean isWriteLog(){
    return mWriteLog;
  }

  /**
   * 设置应用登录的日期与时间
   * LoginDate
   * LoginTime
   * @param PO JParamObject
   */
  public static void setLoginDateTime(JParamObject PO){
    mLoginDate = PO.GetValueByParamName("LoginDate","");
    mLoginTime = PO.GetValueByParamName("LoginTime","");
  }

  /**
   * 设置日志参数
   * @param PO JParamObject
   */
  public static void setLogCount(JParamObject PO){
    String sValue = PO.GetValueByEnvName("CW_LOGCOUNT","50");
    int count = 0;
    try{
      count = Integer.parseInt(sValue);
    }catch(Exception e){}
    if(count != 0){
      mLogCount = count;
    }
  }

  /**
   * 设置应用登录的日期与时间
   * LoginDate
   * LoginTime
   * @param PO JParamObject
   */
  public static void setLoginDateTime(String date,String time){
    mLoginDate = date;
    mLoginTime = time;
  }
  /**
   * 更新日志
   * 如果更新成功，则清除掉当前日志
   */
  private static void saveLogToDB() {
    JParamObject PO = JParamObject.Create();
    String userID = PO.GetValueByEnvName("UserID","");
    PO.SetValueByParamName("Log_UserID",userID);
    JResponseObject RO = null;
	try {
		RO = (JResponseObject) EAI.DAL.IOM("SecurityObject",
		    "writeSystemLog", PO, mLogList);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    if (RO.ErrorCode == 0) {
      mLogList.clear();
    }
  }
}
