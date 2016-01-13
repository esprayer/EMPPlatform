package jservice.jdal.classes.query.server;

import java.util.*;
import com.core.xml.*;
import com.pub.util.UUIDCreator;

import org.openide.util.*;
import java.io.File;

import jservice.jdal.classes.query.dataset.DelTempFileSchedule;
import jservice.jdal.classes.query.dataset.FormatDataManager;

/**查询结果缓存类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not YRH
 * @version 1.0
 */
public abstract class QueryCacheManager {
  public static String Cache_Key = "";//缓存方式
  public static final String Cache_File = "FileCached";//文件方式
  public static final String Cache_Mem  = "MemCached";//内存方式
  public static int BUFFER_INIT_SIZE = 2048;
  public static int DeleteFileTime = 1;//间隔多长时间删除历史文件

  /**
   * 项目部署的URL
   */
  public static String URL_PREFIX = "http://10.230.118.27:8088/EnterpriseServer/";
  /**
   * 输出文件的相对路径名
   */
  public static String QUERY_PATH = "Query";
  /**
   * 输出文件决对路径的上级路径
   */
  public static String Local_UserHome = "/";

  static {
    try {
      //取查询缓存地址
      List list = PackageStub.getContentVector("querycacheServer");
      if (list != null && list.size()>0){
        StubObject so = (StubObject) list.get(0);
        URL_PREFIX = so.getString("url", "");
        QUERY_PATH = so.getString("patch", "Query");
        System.out.println(URL_PREFIX + ":" + QUERY_PATH);
      }
      //取出操作系统的分隔符
      String strSeparator = System.getProperty("file.separator");
      //取程序发布目录
      Local_UserHome = (String)System.getProperty ("eai.home") ;
      //加工出自己的路径
      Local_UserHome = Local_UserHome +
          (Local_UserHome.endsWith(strSeparator) ? "" : strSeparator) +
          QUERY_PATH +  strSeparator;

      File dirFile = new File(Local_UserHome);
      //如果此路径不存在，则创建
      if (!dirFile.exists()) {
        dirFile.mkdir();
      }
      //启动临时文件删除线程，每一小时监测删除一次
      DelTempFileSchedule schedule = new DelTempFileSchedule(Local_UserHome,
          0, DeleteFileTime, 0);
      schedule.start();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public QueryCacheManager() {
  }

  public static QueryCacheManager getDefault(){
    if (Cache_Key==null || "".equals(Cache_Key)){
      List list = PackageStub.getContentVector("QueryMacheMode");
      StubObject so = (StubObject)list.get(0);
      Cache_Key = (String)so.getID();
    }
    return getDefault(Cache_Key);
  }
  public static QueryCacheManager getDefault(String key){
    return (QueryCacheManager)Lookup.getDefault().lookup(QueryCacheManager.class,key);
  }
  public abstract void saveQueryResult(String key,FormatDataManager dataObject,Object data,boolean isend) throws Exception;
  public abstract void saveQueryResult(String key,FormatDataManager dataObject,Object data,int rows) throws Exception;
  public abstract Object loadQueryResult() throws Exception;

  /**
   * 获取文件名
   * @return String
   */
  public synchronized static String getUUIDFileName() {
    java.util.Date date = new java.util.Date();
    return UUIDCreator.generateUUID() + "_" + date.getTime();
  }
}
