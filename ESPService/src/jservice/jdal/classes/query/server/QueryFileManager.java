package jservice.jdal.classes.query.server;

import java.io.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import jservice.jdal.classes.query.dataset.*;
import com.core.xml.StubObject;
import com.core.xml.PackageStub;

/**查询结果文件方法缓存
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class QueryFileManager
    extends QueryCacheManager {

  public QueryFileManager() {
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
                              Object data,int rows) throws Exception {
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
                              Object data,boolean isend) throws Exception {
    FileOutputStream fileOutputStream;
    GZIPOutputStream zipOutputStream;
    byte[] bys = (byte[])data;
    if (dataObject.getOutObject()==null){
      String fileName = getUUIDFileName();

      fileOutputStream = new FileOutputStream(Local_UserHome +   fileName);
      zipOutputStream = new GZIPOutputStream(fileOutputStream, BUFFER_INIT_SIZE);
      dataObject.getFormatedBufferDate().append(URL_PREFIX +QUERY_PATH + "/" + fileName);

    }else{
      zipOutputStream = (GZIPOutputStream) dataObject.getOutObject();
    }
    //写入文件
    zipOutputStream.write(bys, 0, bys.length);
    zipOutputStream.flush();
    //最后关闭文件
    if (isend){
      zipOutputStream.finish();
      zipOutputStream.close();
    }
    dataObject.setOutObject(zipOutputStream);
  }

}
