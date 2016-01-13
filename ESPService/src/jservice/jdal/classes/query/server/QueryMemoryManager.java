package jservice.jdal.classes.query.server;

import java.io.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import jservice.jdal.classes.query.dataset.*;
import com.efounder.eai.service.*;

/**查询结果内存方法缓存
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class QueryMemoryManager
    extends QueryCacheManager {
  public static String Mem_PREFIX = "MemCached";

  public QueryMemoryManager() {
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
  public void saveQueryResult(String key,FormatDataManager dataObject,Object data,int rows) throws Exception{
    boolean isend = false;
    if (rows < QueryDataOutObject.ROW_VALVE)
      isend = true;
    this.saveQueryResult(key,dataObject,data,isend);
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
    byte[] bys = (byte[]) data;
    if (dataObject.getOutObject() == null) {
      String fileName = super.getUUIDFileName();
      fileName = Mem_PREFIX + "-" + fileName;
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
      //写入文件
      key = dataObject.getFormatedBufferDate().toString();
      java.io.InputStream ins = new FileInputStream(new File(Local_UserHome + key));
      byte[] insb=new byte[ins.available()];
      ins.read(insb);

      java.util.Date date = new java.util.Date();
      long time = date.getTime() + DeleteFileTime * 60 * 1000;
      date.setTime(time);
      boolean result = MemCachedManager.getDefault().getMemCached().set(key, insb,date);
      if (!result){
        System.out.println("--QueryDataSetToMemCachedFalse:"+Local_UserHome + key);
        MemCachedManager.getDefault().getMemCached().get(key);
      }
    }
  }
}
