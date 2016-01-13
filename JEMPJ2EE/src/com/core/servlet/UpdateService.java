package com.core.servlet;

import javax.swing.JOptionPane;
import java.util.Hashtable;
import java.awt.Frame;

import com.core.update.UpdateObject;
import com.core.update.UpdateStub;
import com.core.xml.JDOMXMLBaseObject;
import java.net.URL;
import java.io.File;

import com.core.loader.mainConfig;
import com.core.net.BasicDownloadLayer;
import com.core.net.BasicNetworkLayer;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class UpdateService {
  /**
   *
   */
  protected UpdateService() {
  }
  /**
   *
   * @param PropertyList Hashtable
   * @return boolean
   * @throws Exception
   */
  protected static boolean updateService(Hashtable PropertyList) throws
      Exception {
    boolean mustDownlaod = true;
    UpdateStub[] USA = null;
    Object[] OArray;
    boolean autoUpdate = true;
    String Codebase = null, DefaultCodebase = null;
    String updateURL = (String) PropertyList.get("updateurl");
    if (updateURL != null && updateURL.startsWith("http")) {
      Codebase = updateURL; //(String) PropertyList.get("codebase");
      DefaultCodebase = updateURL; //(String) mainConfig.get("DefaultUpdateCenter", Codebase);
    }
    else {
      Codebase = (String) PropertyList.get("codebase");
      DefaultCodebase = (String) mainConfig.get("DefaultUpdateCenter", Codebase);
      PropertyList.put("updateurl", Codebase);
    }
    // 如果为空，则返回
    if ( Codebase == null || Codebase.trim().length() == 0 ) return false;
    try {
      // 获取是否需要自动检查升级
      autoUpdate = true; //UpdateObject.isAutoUpdate();
      // 获取必须下载的列表
      OArray = UpdateObject.checkUpdateLocalMustDownload(Codebase, PropertyList);
      // 检查是否需要必须下载
      mustDownlaod = UpdateObject.checkMustDownload(OArray);
      if (autoUpdate && !mustDownlaod) {
        // 检查是否需要升级
        String Application = (String) PropertyList.get("application");
        String Product = (String) PropertyList.get("product");
        USA = UpdateObject.checkUpdateStatus(DefaultCodebase, PropertyList,
                                             Application, Product);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (USA != null || mustDownlaod) {
      // 如果需要升及或是必须升级，则执行升级过程
      execUpdateService(PropertyList, Codebase, "JEnterprise", "");
    }
    return true;
  }
  /**
   *
   * @param hashList Hashtable
   * @param codebase String
   * @param Application String
   * @param Product String
   * @throws Exception
   */
  protected static void execUpdateService(Hashtable hashList, String codebase,
                                       String Application, String Product) throws
      Exception {
    JDOMXMLBaseObject XML = UpdateObject.buildService(codebase, hashList,
        Application, Product);
    // 创建StubObject
    UpdateStub rootUpdateStub = (UpdateStub) UpdateObject.buildStubObject(
        Application, XML);
    UpdateStub[] updateStubArray = null;
    if (rootUpdateStub != null) {
      updateStubArray = UpdateObject.getUpdateFiles(rootUpdateStub);
      saveApplicatinService(XML, hashList);
      for (int i = 0; i < updateStubArray.length; i++) {
        Object res = downloadFile(codebase, hashList, updateStubArray[i]);
        if (res != null) {
          // 设置错误信息
          updateStubArray[i].setString("ERROR_MESSAGE",
                                       ( (Throwable) res).getMessage());
//        JOptionPane.showMessageDialog(this,"下载错误："+((Throwable)res).getMessage(),"系统提示",JOptionPane.ERROR_MESSAGE);
//        break;
        }
      }
    }
  }
  /**
   *
   * @param XML JDOMXMLBaseObject
   * @param hashList Hashtable
   */
  private static void saveApplicatinService(JDOMXMLBaseObject XML,
                                            Hashtable hashList) {
    String PathName = (String) hashList.get("user.home");
    String Separator = System.getProperty("file.separator");
    PathName = PathName + UpdateObject.getCodeSpace() + Separator;
    File dirFile = new File(PathName);
    if (!dirFile.exists()) {
      dirFile.mkdirs();
    }

    String FileName = (String) hashList.get("application") + ".xml";
    XML.setEncoding("UTF-8");
    XML.SaveToFile(PathName + FileName);
  }
  /**
   *
   * @param Codebase String
   * @param hashList Hashtable
   * @param US UpdateStub
   * @return Object
   */
  private static Object downloadFile(String Codebase, Hashtable hashList,
                                     UpdateStub US) {
    int size = US.getInt("remotesize", 0);
    String LocalFileName = getLocalFileURI(hashList, US);
    String RemoteFileName = getRemoteFileURI(Codebase, US);
    File file = getLocalFile(LocalFileName);
    URL url = getRemoteFileURL(RemoteFileName);
    BasicNetworkLayer basicLayer = new BasicNetworkLayer();
    BasicDownloadLayer downloadFile = new BasicDownloadLayer(basicLayer);
    try {
      System.out.print("downloading:" + url.toString());
      downloadFile.download(url, file, null);
      file.setLastModified(Long.parseLong(US.getRemoteTime()));
      System.out.println(" is finish!");
    }
    catch (Exception e) {
      System.out.println("downloading:" + url.toString() + " is error!");
      file.delete();
      e.printStackTrace();
      if (e.getMessage() == null) {
        return e.getCause();
      }
      return e;
//      e.printStackTrace();
    }
    return null;
//    for(int i=0;i<size;i++) {
//      pnSecond.setValue(i+1);
//      this.lbTitle3.setText(US.toString()+" "+ String.valueOf(i+1)+"/"+String.valueOf(size));
//    }
  }
  /**
   *
   * @param hashList Map
   * @param US UpdateStub
   * @return String
   */
  private static String getLocalFileURI(java.util.Map hashList, UpdateStub US) {
    String FileName = null;
    String PathName = (String) hashList.get("user.home");
    String Separator = System.getProperty("file.separator");
    PathName = PathName + UpdateObject.getCodeSpace() + Separator +
        US.getStubPathName(false) + Separator;
    File dirFile = new File(PathName);
    if (!dirFile.exists()) {
      dirFile.mkdirs();
    }
    FileName = PathName + US.getStubFileName();
    return FileName;
  }
  /**
   *
   * @param FileName String
   * @return File
   */
  private static File getLocalFile(String FileName) {
    File file = new File(FileName);
    try {
      file.createNewFile();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return file;
  }
  /**
   *
   * @param codebase String
   * @return URL
   */
  private static URL getRemoteFileURL(String codebase) {
    URL url = null;
    try {
      url = new URL(codebase);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return url;
  }
  /**
   *
   * @param Codebase String
   * @param US UpdateStub
   * @return String
   */
  private static String getRemoteFileURI(String Codebase, UpdateStub US) {
    String PathName = null;
    if (Codebase.toLowerCase().startsWith("file")) {
      PathName = Codebase + US.getStubPathName(true) + US.getStubFileName();
    }
    else {
      if (US.getString("svr", null) == null) { //add by fsz
        PathName = Codebase + UpdateObject.CODE_SPACE + "/" +
            US.getStubPathName(true) + US.getStubFileName();
      }
      else {
//        PathName =US.getString("svr","")+US.getString("urlpath","")+US.getStubFileName();
        PathName = US.getString("svr", "") + UpdateObject.CODE_SPACE + "/" +
            US.getStubPathName(true) + US.getStubFileName();
      }
    }
    return PathName;
  }
}
