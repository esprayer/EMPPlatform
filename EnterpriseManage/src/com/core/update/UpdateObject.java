package com.core.update;

import com.core.core.FileUtils;
import com.core.loader.mainConfig;

import esyt.framework.com.core.xml.JDOMXMLBaseObject;
import esyt.framework.com.core.xml.JXMLBaseObject;
import esyt.framework.com.core.xml.StubObject;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.jdom.Element;

public class UpdateObject
{
  public static UpdateStub localRootUpdateStub = null;
  public static UpdateStub remoteRootUpdateStub = null;
  public static final int BOOT_UPDATE_TYPE = 0;
  public static final int EXEC_UPDATE_TYPE = 1;
  public static final String BOOT_CODE_SPACE = "CodeSpace";
  public static final String DOWNLOAD_CODE_SPACE = "ExtractSpace";
  public static final String EXEC_CODE_SPACE = "TempCodeSpace";
  public static String CODE_SPACE = "CodeSpace";
  public static int BOOT_OR_EXEC_UPDATE_TYPE = 0;

  protected static Hashtable UpdateCenterList = new Hashtable();

  public static boolean REDOWNLOAD_ALL_FILE_AND_DELETEALL = false;

  protected static int MAX_FILE_LENGTH = 65536;

  public static Hashtable getUpdateCenterList()
  {
    return UpdateCenterList; }

  public static Object copyTempCodeSpace2CodeSpace(String Codebase, Hashtable hashList) throws Exception {
    Object res = null;
    String Separator = System.getProperty("file.separator");
    String LocalCodeSpace = ((String)hashList.get("user.home")) + CODE_SPACE + Separator;
    String TempLocalCodeSpace = ((String)hashList.get("user.home")) + "TempCodeSpace" + Separator;
    FileUtils.copyDir2Dir(TempLocalCodeSpace, LocalCodeSpace);
    return res; }

  public static void deleteTempCodeSpace(String Codebase, Hashtable hashList) throws Exception {
    String Separator = System.getProperty("file.separator");
    String TempLocalCodeSpace = ((String)hashList.get("user.home")) + "TempCodeSpace" + Separator;
    File dirFile = new File(TempLocalCodeSpace);
    FileUtils.deleteDirTree(dirFile); }

  public static boolean isAutoUpdate() {
    return "1".equals((String)mainConfig.get("autocheckupdate", "1")); }

  public static boolean checkMustDownload(Object[] OArray) {
    boolean mustDownlaod = false;
    if (OArray != null) {
      Hashtable mustDownloadList = (Hashtable)OArray[0];
      int NoExists = ((Integer)OArray[1]).intValue();
      if (NoExists > 0)
        mustDownlaod = true;
    } else {
      mustDownlaod = true; }
    return mustDownlaod;
  }

  public static Object[] checkUpdateLocalMustDownload(String Codebase, Hashtable hashList)
    throws Exception
  {
    String LocalCodeSpace = null; String Application = null; String Product = null;
    String Separator = System.getProperty("file.separator"); JDOMXMLBaseObject XML = null;
    Application = (String)hashList.get("application");
    Product = (String)hashList.get("product");
    LocalCodeSpace = ((String)hashList.get("user.home")) + CODE_SPACE + Separator;
    String AppXmlFileName = LocalCodeSpace + Application + ".xml";
    File XmlFile = new File(AppXmlFileName); Hashtable mustList = null;

    if (XmlFile.exists()) {
      XML = new JDOMXMLBaseObject(XmlFile);

      localRootUpdateStub = (UpdateStub)buildStubObject(Application, XML);

      removeOProduct(localRootUpdateStub, Product);
      mustList = new Hashtable();
      checkLocalUpdateService(XML, XML.Root, LocalCodeSpace, mustList, "core", Application, Product);
      int NotExistsCount = _$5723(mustList);
      Object[] OArray = { mustList, new Integer(NotExistsCount) };
      return OArray;
    }
    return null; }

  private static int _$5723(Hashtable mustList) {
    int Count = 0; File jarFile = null;
    Object[] fileArray = mustList.keySet().toArray();
    for (int i = 0; i < fileArray.length; ++i) {
      jarFile = (File)fileArray[i];
      if (!(jarFile.exists())) {
        ++Count;
      }
    }
    return Count;
  }

  public static void checkLocalUpdateService(JDOMXMLBaseObject XML, Element e, String Codebase, Hashtable mustList, String key, String Application, String Product)
  {
    List nodeList = XML.BeginEnumerate(e); int Index = 0;
    String PathName = null; String Separator = System.getProperty("file.separator");
    while (nodeList != null) {
      Element node = XML.Enumerate(nodeList, Index++);
      if (node == null) return;
      String nodeName = node.getName();
      if (("public".equals(nodeName)) || ("client".equals(nodeName)) || ("server".equals(nodeName)) || (Product.equals(nodeName)) || (Application.equals(nodeName)))
      {
        List childList = node.getChildren();

        if ((childList == null) || (childList.size() == 0)) {
          _$5733(XML, node, Codebase, mustList, key);
        }
        else {
          PathName = Codebase + node.getName() + Separator;
          checkLocalUpdateService(XML, node, PathName, mustList, key, Application, Product);
        }
      }
    }
  }

  private static void _$5733(JDOMXMLBaseObject XML, Element node, String Codebase, Hashtable mustList, String key)
  {
    String FileName = XML.GetElementValue(node, "href", null);
    if (FileName != null) {
      File jarFile = new File(Codebase, FileName);

      if ("1".equals(XML.GetElementValue(node, key, "0")))
        mustList.put(jarFile, node);
    }
  }

  public static void removeOProduct(UpdateStub rootStub, String Product)
  {
    StubObject[] pArray = rootStub.getChilds();
    for (int i = 0; i < pArray.length; ++i) {
      String stubID = pArray[i].getStubID();
      if (("public".equals(stubID)) || ("client".equals(stubID)) || ("server".equals(stubID)) || (Product.endsWith(stubID)) || ("".equals(Product))) continue; if (Product == null)
      {
        continue;
      }

      rootStub.removeChild(pArray[i]);
    }
  }

  public static UpdateStub[] checkUpdateStatus(String Codebase, Hashtable hashList, String Application, String Product)
    throws Exception
  {
    JDOMXMLBaseObject XML = buildService(Codebase, hashList, Application, Product);

    UpdateStub rootUpdateStub = (UpdateStub)buildStubObject(Application, XML);

    remoteRootUpdateStub = rootUpdateStub;

    removeOProduct(rootUpdateStub, Product);

    REDOWNLOAD_ALL_FILE_AND_DELETEALL = checkRedownload();

    UpdateStub[] USA = null;
    USA = getUpdateFiles(rootUpdateStub);
    if ((USA != null) && (USA.length == 0)) USA = null;
    return USA;
  }

  public static boolean checkRedownload()
  {
    boolean res = false; String lVer = null; String rVer = null;

    if ((localRootUpdateStub != null) && (remoteRootUpdateStub != null)) {
      lVer = localRootUpdateStub.getString("ver", "1");
      rVer = remoteRootUpdateStub.getString("ver", "1");

      res = !(lVer.equals(rVer));
    }
    return res;
  }

  public static void execUpdateService(int UpdateType, Hashtable hashList, Frame owner)
  {
    BOOT_OR_EXEC_UPDATE_TYPE = UpdateType;

    loadUpdateCenter(hashList);
    UpdateForm updateForm = new UpdateForm(owner, hashList);
    updateForm.setCustomObject(hashList);
    updateForm.setModal(true);
    updateForm.CenterWindow();
    updateForm.show();
  }

  public static String getCodeSpace()
  {
    if (BOOT_OR_EXEC_UPDATE_TYPE == 0) {
      return "CodeSpace";
    }
    return "TempCodeSpace";
  }

  public static Hashtable loadUpdateCenter(Hashtable hashList)
  {
    String Codebase = (String)hashList.get("updateurl");
    if (Codebase == null) return UpdateCenterList;

    StubObject SO = new StubObject();
    SO.setID("LocalNetwork"); SO.setObject("codebase", Codebase);
    SO.setCaption("当前登录网络服务");
    UpdateCenterList.put(SO.getID(), SO);

    String uri = Codebase + CODE_SPACE + "/UpdateCenter.xml";
    try {
      URL url = new URL(uri);
      JXMLBaseObject XML = new JXMLBaseObject(url);
      return loadUpdateCenter(XML, Codebase);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return UpdateCenterList;
  }

  public static Hashtable loadUpdateCenter(JXMLBaseObject XML, String Codebase)
  {
    List nodelist = null; int Index = 0; Element e = null; StubObject SO = null;
    nodelist = XML.BeginEnumerate(XML.Root);

    while (nodelist != null) {
      e = XML.Enumerate(nodelist, Index++);
      if (e == null) break;
      SO = new StubObject();
      SO.setObjectFromXMLElemnt(e);
      UpdateCenterList.put(SO.getID(), SO);
    }
    XML.EndEnumerate();
    return UpdateCenterList;
  }

  public static JXMLBaseObject getUpdateServiceXML(Hashtable hashList, String Codebase, String Application, String Product)
    throws Exception
  {
    JXMLBaseObject XML = null;
    String sXML = getUpdateServiceXMLString(hashList, Codebase, Application, Product);
    if (sXML != null) {
      XML = new JXMLBaseObject(sXML);
    }
    return XML;
  }

  public static String getUpdateServiceXMLString(Hashtable hashList, String Codebase, String Application, String Product)
    throws Exception
  {
    String XML = null;
    if (!(Codebase.toLowerCase().startsWith("http"))) {
      XML = _$5761(hashList, Codebase, Application, Product);
    } else {
      URL url = null;
      url = _$5762(hashList, Codebase);
      if (url != null) {
        XML = downloadFile(url);
      }
    }
    return XML; }

  public static String downloadFile(URL url) throws IOException {
    String XML = "";
    HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
    try {
      urlc.setRequestProperty("WebLogin", "no");
      urlc.setDoOutput(true);  
      urlc.setDoInput(true);  
      urlc.setConnectTimeout(10000);  //设置连接超时为10s  
      urlc.setReadTimeout(10000);     //读取数据超时也是10s  
      urlc.setRequestMethod("POST"); 
      // 连接指定的资源
      urlc.connect();
      
      InputStream is = urlc.getInputStream();
      byte[] copy = new byte[MAX_FILE_LENGTH];

      int i = 0;
      for (i = 0; i < MAX_FILE_LENGTH; ++i) {
        int read = is.read();
        if (read == -1)
          break;
        copy[i] = (byte)read;
      }
      byte[] content = new byte[i];
      System.arraycopy(copy, 0, content, 0, i);
      XML = new String(content, "utf8");
      is.close();
    }
    finally {
      urlc.disconnect();
    }

    return XML; }

  private static String _$5761(Hashtable hashList, String Codebase, String Application, String Product) throws Exception {
    String XML = null;
    String uri = "file:///" + Codebase + Application + ".xml"; URL url = new URL(uri);
    JDOMXMLBaseObject XMLObject = new JDOMXMLBaseObject(url);
    buildUpdateService(XMLObject, XMLObject.Root, true, Codebase, Application, Product);
    XML = XMLObject.GetRootXMLString();
    return XML;
  }

  private static URL _$5762(Hashtable hashList, String Codebase)
  {
    String uri = _$5775(hashList, Codebase); URL url = null;
    try {
      url = new URL(uri);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return url;
  }

  private static String _$5775(Hashtable hashList, String Codebase)
  {
    String uri = null;
    String isApplet = System.getProperty("isapplet", "0");
    uri = Codebase + "UpdateService?application=" + ((String)hashList.get("application"));
    uri = uri + "&product=" + ((String)hashList.get("product")) + "&applet=" + isApplet;
    return uri; }

  public static JDOMXMLBaseObject buildUpdateService(String Localhome, String Application, String Product, boolean isRemote) {
    return buildUpdateService(Localhome, Application, Product, isRemote, false);
  }

  public static JDOMXMLBaseObject buildUpdateService(String Localhome, String Application, String Product, boolean isRemote, boolean isApplet)
  {
    String Separator = System.getProperty("file.separator");
    String PathName = Localhome + CODE_SPACE + Separator;
    String FileName = null;
    if (isApplet)
      FileName = Application + "_Applet.xml";
    else
      FileName = Application + ".xml";
    File xmlFile = new File(PathName + FileName);
    JDOMXMLBaseObject XML = new JDOMXMLBaseObject(xmlFile);
    buildUpdateService(XML, XML.Root, isRemote, PathName, Application, Product);
    return XML;
  }

  public static void buildUpdateService(JDOMXMLBaseObject XML, Element e, boolean isRemote, String Codebase, String Application, String Product)
  {
    List nodeList = XML.BeginEnumerate(e); int Index = 0;
    String PathName = null; String Separator = System.getProperty("file.separator");
    while (nodeList != null) {
      Element node = XML.Enumerate(nodeList, Index++);
      if (node == null) return;
      List childList = node.getChildren();

      if ((childList == null) || (childList.size() == 0))
      {
        _$5779(XML, node, isRemote, Codebase);
      } else {
        PathName = Codebase + node.getName() + Separator;
        if (!(isRemote)) {
          _$5780(PathName);
        }
        buildUpdateService(XML, node, isRemote, PathName, Application, Product); }
    }
  }

  private static void _$5781(JDOMXMLBaseObject XML, Element node, boolean isRemote) {
    String svr = XML.GetElementValue(node, "svr", null);
    String uri = svr + "UpdateService?urlpath=" + XML.GetElementValue(node, "urlpath", null);
    uri = uri + "&file=" + XML.GetElementValue(node, "href", null);
    try {
      URL url = new URL(uri);
      URLConnection urlc = url.openConnection();
      urlc.connect();
      int length = urlc.getContentLength();
      InputStream is = url.openStream();
      byte[] copy = new byte[MAX_FILE_LENGTH];

      int i = 0;
      for (i = 0; i < MAX_FILE_LENGTH; ++i) {
        int read = is.read();
        if (read == -1)
          break;
        copy[i] = (byte)read;
      }
      byte[] content = new byte[i];
      System.arraycopy(copy, 0, content, 0, i);
      String sXML = new String(content, "GBK");
      JXMLBaseObject xdom = new JXMLBaseObject(sXML);
      XML.SetElementValue(node, "remotetime", xdom.GetElementValue(xdom.Root, "remotetime"));

      XML.SetElementValue(node, "remotesize", xdom.GetElementValue(xdom.Root, "remotesize"));

      XML.SetElementValue(node, "localtime", xdom.GetElementValue(xdom.Root, "localtime"));

      XML.SetElementValue(node, "localsize", xdom.GetElementValue(xdom.Root, "localsize"));
    }
    catch (Exception e) {
      XML.SetElementValue(node, "remotetime", "10000");
      XML.SetElementValue(node, "remotesize", "0");
      XML.SetElementValue(node, "localtime", "0");
      XML.SetElementValue(node, "localsize", "0");
    }
  }

  private static void _$5780(String PathName) {
    File jarDir = new File(PathName);
    if (!(jarDir.exists()))
      jarDir.mkdirs();
  }

  private static void _$5779(JDOMXMLBaseObject XML, Element node, boolean isRemote, String Codebase)
  {
    String FileName = XML.GetElementValue(node, "href", null);
    if (FileName == null)
    {
      return;
    }

    File jarFile = new File(Codebase, FileName);
    if (jarFile.exists()) {
      long time = jarFile.lastModified();
      long size = jarFile.length();
      if (isRemote) {
        XML.SetElementValue(node, "remotetime", String.valueOf(time));
        XML.SetElementValue(node, "remotesize", String.valueOf(size));
        XML.SetElementValue(node, "localtime", "");
        XML.SetElementValue(node, "localsize", "");
      }
      else {
        XML.SetElementValue(node, "localtime", String.valueOf(time));
        XML.SetElementValue(node, "localsize", String.valueOf(size));
      }
    }
    else if (isRemote) {
      XML.SetElementValue(node, "remotetime", "10000");
      XML.SetElementValue(node, "remotesize", "0");
      XML.SetElementValue(node, "localtime", "0");
      XML.SetElementValue(node, "localsize", "0");
    }
    else {
      XML.SetElementValue(node, "localtime", "0");
      XML.SetElementValue(node, "localsize", "0");
    }
  }

  public static String FindFileProp(String FileName, String Codebase)
  {
    File jarFile = new File(Codebase, FileName);
    JDOMXMLBaseObject XML = new JDOMXMLBaseObject();
    XML.setEncoding("UTF-8");
    if (jarFile.exists()) {
      long time = jarFile.lastModified();
      long size = jarFile.length();
      XML.SetElementValue(XML.Root, "remotetime", String.valueOf(time));
      XML.SetElementValue(XML.Root, "remotesize", String.valueOf(size));
      XML.SetElementValue(XML.Root, "localtime", "");
      XML.SetElementValue(XML.Root, "localsize", "");
    }
    else {
      XML.SetElementValue(XML.Root, "remotetime", "10000");
      XML.SetElementValue(XML.Root, "remotesize", "0");
      XML.SetElementValue(XML.Root, "localtime", "0");
      XML.SetElementValue(XML.Root, "localsize", "0");
    }
    return XML.GetRootXMLString();
  }

  public static JDOMXMLBaseObject buildService(String Codebase, Hashtable hashList, String Application, String Product)
    throws Exception
  {
    String xml = getUpdateServiceXMLString(hashList, Codebase, Application, Product);
    JDOMXMLBaseObject XML = new JDOMXMLBaseObject(xml);
    String PathName = (String)hashList.get("user.home");
    String Separator = System.getProperty("file.separator");
    PathName = PathName + CODE_SPACE + Separator;
    buildUpdateService(XML, PathName, Application, Product);
    return XML;
  }

  public static void buildUpdateService(JDOMXMLBaseObject XML, String Codebase, String Application, String Product)
  {
    buildUpdateService(XML, XML.Root, false, Codebase, Application, Product);
  }

  public static StubObject buildStubObject(String Application, JDOMXMLBaseObject XML)
  {
    Element e = XML.GetElementByName(Application);
    StubObject SO = StubObject.convertXML2Stub(XML, e, UpdateStub.class);
    return SO;
  }

  public static UpdateStub[] getUpdateFiles(UpdateStub US)
  {
    UpdateStub[] USA = null; Vector stubList = new Vector();
    _$5795(US, stubList);
    USA = new UpdateStub[stubList.size()];
    USA = (UpdateStub[])(UpdateStub[])stubList.toArray(USA);
    return USA;
  }

  private static void _$5795(UpdateStub US, Vector stubList)
  {
    if (US.isFile()) {
      if (US.isSelected()) {
        stubList.add(US);
      }
    }
    else if (US.hasChild()) {
      StubObject[] USA = (StubObject[])US.getChilds();
      for (int i = 0; i < USA.length; ++i)
        _$5795((UpdateStub)USA[i], stubList);
    }
  }
}