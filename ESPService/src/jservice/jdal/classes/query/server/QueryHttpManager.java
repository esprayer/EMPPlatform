package jservice.jdal.classes.query.server;

import java.io.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import jservice.jdal.classes.query.dataset.*;
import com.efounder.eai.service.*;
import com.core.xml.StubObject;
import com.core.xml.PackageStub;

/**查询结果内存方法缓存
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class QueryHttpManager
    extends QueryCacheManager {
  public static String Http_PREFIX = "http";

  public QueryHttpManager() {
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

  public void saveQueryResult(String key, FormatDataManager dataObject,
                              Object data, int rows) throws Exception {
    boolean isend = false;
    if (rows < QueryDataOutObject.ROW_VALVE)
      isend = true;
    this.saveQueryResult(key, dataObject, data, isend);
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
                              Object data, boolean isend) throws Exception {
    OutputStream fileOutputStream;
    GZIPOutputStream zipOutputStream;
    String url = "", fileName = "", url_prefix = "", query_path = "";

    byte[] bys = (byte[]) data;
    if (dataObject.getOutObject() == null) {
      //先从dataObject里取
      fileName = dataObject.getFormatedBufferDate().toString();
      if (fileName == null || "".equals(fileName))
        fileName = super.getUUIDFileName();
      fileName = Http_PREFIX + "-" + fileName;
      fileOutputStream = new FileOutputStream(Local_UserHome + fileName);
//      fileOutputStream = new ByteArrayOutputStream();
      zipOutputStream = new GZIPOutputStream(fileOutputStream, BUFFER_INIT_SIZE);
      dataObject.getFormatedBufferDate().append(fileName);
    }
    else {
      zipOutputStream = (GZIPOutputStream) dataObject.getOutObject();
    }

    zipOutputStream.write(bys, 0, bys.length);
    zipOutputStream.flush();

    dataObject.setOutObject(zipOutputStream);
    //最后关闭文件
    if (isend) {
      zipOutputStream.finish();
      zipOutputStream.close();
    }
    //取本次查询缓存地址
    List list = PackageStub.getContentVector("querycacheServer");
    if (list.size() > 0) {
      StubObject so = (StubObject) list.get(0);
      url_prefix = so.getString("url", "");
      query_path = so.getString("patch", "Query");
      System.out.println(URL_PREFIX + ":" + QUERY_PATH);
    }
    //写入文件
    key = dataObject.getFormatedBufferDate().toString();
    if (key.indexOf("Query/") > 0)
      key = key.substring(key.indexOf("Query/") + 6);
    url = url_prefix + query_path + "/" + key;
    dataObject.getFormatedBufferDate().setLength(0);
    dataObject.getFormatedBufferDate().append(url);
    java.io.InputStream ins = new FileInputStream(new File(Local_UserHome +
        key));
    byte[] insb = new byte[ins.available()];
    ins.read(insb);

    java.util.Date date = new java.util.Date();
    long time = date.getTime() + DeleteFileTime * 60 * 1000;
    date.setTime(time);
    boolean result = MemCachedManager.getDefault().getMemCached().set(key,
        url, date);
    if (!result) {
      System.out.println("--QueryDataSetToHttpFalse:" + Local_UserHome + key);
      MemCachedManager.getDefault().getMemCached().get(key);
    }
  }
}
