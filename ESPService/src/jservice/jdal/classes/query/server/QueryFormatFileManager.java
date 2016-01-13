package jservice.jdal.classes.query.server;

import java.io.*;
import java.io.*;

import com.efounder.pub.util.Debug;
import com.report.table.jxml.TableManager;

import jservice.jdal.classes.query.dataset.*;

/**查询结果处理，定时删除物理文件
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class QueryFormatFileManager
    extends QueryCacheManager implements java.io.Serializable {
  /**
  /**
   * 输出文件的相对路径名
   */
  public static String QUERY_PATH = "QueryFormate";
  /**
   * 输出文件决对路径的上级路径
   */
  public static String Local_UserHome = "/";

  public static String fileName = "";

  static {
    try {
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
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public QueryFormatFileManager() {
  }

  /**
   * 文件是否存在
   * @param fn String
   * @return boolean
   */
  public boolean isExist(){
    File file = new File(Local_UserHome+fileName);
    if(file.exists()){
      return true;
    }
    return false;
  }

  /**
   * loadQueryResult
   *
   * @return Object
   * @throws Exception
   * @todo Implement this com.pansoft.esp.query.server.QueryCacheManager method
   */
  public Object loadQueryResult() throws Exception {
    return null;
  }

  public Object loadQueryResult(String fn) throws Exception{
    Object obj = null;

    FileInputStream fileOutputSteam = new FileInputStream(Local_UserHome+fn);
    ObjectInputStream objInputStream = new ObjectInputStream(fileOutputSteam);
    obj = objInputStream.readObject();

    objInputStream.close();
    fileOutputSteam.close();

    return obj;
  }
  public void saveQueryResult(String key, FormatDataManager dataObject,
                            Object data,int rows) throws Exception {
  }
  /**
   * saveQueryResult
   *
   * @param key String
   * @param dataObject FormatDataManager
   * @param data Object
   * @throws Exception
   * @todo Implement this com.pansoft.esp.query.server.QueryCacheManager method
   */
  public void saveQueryResult(String key, FormatDataManager dataObject,
                              Object data,boolean isend) throws Exception {
    Object[] object= (Object[])data;
    //xml序列化异常 ：TableManager 以字符串序列化
    TableManager tm = (TableManager)object[0];
    object[0] = tm.printDOMTree();

    FileOutputStream fileOutputStream;
    ObjectOutputStream objOutputStream;
    fileOutputStream = new FileOutputStream(Local_UserHome +   fileName);
    objOutputStream = new ObjectOutputStream(fileOutputStream);
    objOutputStream.writeObject(object);

    objOutputStream.close();
    fileOutputStream.close();
  }

  public void saveQueryResult(String s) throws Exception{
    File file = new File("C:/FmisQueryFormat/"+fileName);

    if (!file.exists()) {
      file.createNewFile();
    }

    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    out.write(s);
    out.close();

  }

  /**
   * 加载文件
   * @param fn String
   * @return String
   * @throws Exception
   */
  public String loadStringFile(String fn) throws Exception{
//    File file = new File("C:/FmisQueryFormat/"+fileName);
    //1 打开本地格式文件
        FileInputStream in = new FileInputStream(Local_UserHome+fn);

        InputStreamReader inputStreamReader = new InputStreamReader(
            in, "UTF-8");
        BufferedReader bufReader = new BufferedReader(inputStreamReader);
        char [] content=new char[1024*256];
        int count=bufReader.read(content);
        String xml=new String(content,0,count);

    return xml;
  }

  /**
   * 删除指定路径下的文件
   * @param path String
   * @return int
   * @throws Exception
   */
  public int deleteFiles() throws Exception{
    int fileCount = 0;

    File file = new File(Local_UserHome);
    File[] fileList = file.listFiles();
    for(int i=0; i<fileList.length; i++){
      if(fileList[i].getName().endsWith("obj")){
        fileList[i].delete();
        fileCount++;
        Debug.PrintlnMessageToSystem("删除临时文件 " + fileList[i].getName());
      }
    }

    return fileCount;
  }


  public void setFileName(String fn){
    fileName = fn;
  }
}
