package com.core.update;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.core.core.FileUtils;
import com.core.core.TopThreadGroup;
import com.core.net.BasicDownloadLayer;
import com.core.net.BasicNetworkLayer;
import com.core.net.HttpDownloadListener;
import com.core.net.HttpRequest;
import com.core.net.HttpResponse;

import esyt.framework.com.core.xml.JDOMXMLBaseObject;
import esyt.framework.com.core.xml.StubObject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.MutableComboBoxModel;

public class Setp3Form extends JPanel
  implements Runnable, HttpDownloadListener, ActionListener
{
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JLabel lbTitle2 = new JLabel();
  JProgressBar pbMain = new JProgressBar();
  JLabel lbTitle3 = new JLabel();
  JProgressBar pnSecond = new JProgressBar();
  JPanel jPanel3 = new JPanel();
  JButton bnStop = new JButton();
  FlowLayout flowLayout2 = new FlowLayout();
  JLabel lbTitle1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList epDes = new JList();

  UpdateStub[] updateStubArray = null;
  StubObject updateCenterStubObject = null;
  JDOMXMLBaseObject XMLObject = null;
  String Codebase = null;
  Hashtable hashList = null;
  Thread thread = null;
  boolean IS_STOP = false;
  UpdateForm updateForm = null;
  MutableComboBoxModel listModel = null;
  String Application = null;
  String Product = null;

  protected Map pathMap = null;

  public Setp3Form(String Application, String Product)
  {
    try
    {
      this.Application = Application;
      this.Product = Product;
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    setLayout(this.borderLayout1);
    this.jPanel2.setLayout(this.verticalFlowLayout1);
    this.bnStop.setText("停止");
    this.jPanel3.setLayout(this.flowLayout2);
    this.flowLayout2.setAlignment(2);

    add(this.jPanel2, "North");
    this.jPanel2.add(this.lbTitle1, null);
    this.jPanel2.add(this.lbTitle2, null);
    this.jPanel2.add(this.pbMain, null);
    this.jPanel2.add(this.lbTitle3, null);
    this.jPanel2.add(this.pnSecond, null);
    this.jPanel2.add(this.jPanel3, null);
    this.jPanel3.add(this.bnStop, null);
    add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add(this.epDes, null);
    initEvent(); }

  void initEvent() {
    this.bnStop.addActionListener(this);
    this.listModel = new DefaultComboBoxModel();
    this.epDes.setModel(this.listModel);
  }

  public Object willNext(Object p1, Object p2, Object p3, Object p4)
  {
    Object[] Stubs = { this.updateStubArray, this.updateCenterStubObject, this.XMLObject };
    return Stubs;
  }

  public Object willBack(Object p1, Object p2, Object p3, Object p4)
  {
    return null;
  }

  public Object willInto(Object p1, Object p2, Object p3, Object p4)
  {
    Object[] stubs = (Object[])(Object[])p1;
    this.hashList = ((Hashtable)p3);
    this.updateForm = ((UpdateForm)p4);
    this.updateStubArray = ((UpdateStub[])(UpdateStub[])stubs[0]);
    this.updateCenterStubObject = ((StubObject)(StubObject)stubs[1]);
    this.XMLObject = ((JDOMXMLBaseObject)(JDOMXMLBaseObject)stubs[2]);
    processCodebase();
    processInto();
    return this.updateStubArray; }

  void processCodebase() {
    this.Codebase = this.updateCenterStubObject.getString("codebase", null);
  }

  void processInto()
  {
    this.thread = TopThreadGroup.startThread(this, "downloadUpdate"); }

  void setEnable() {
    this.updateForm.setEnable(0, true);
    this.updateForm.setEnable(1, true);
    this.updateForm.setEnable(2, false);
    this.updateForm.setEnable(3, false); }

  void setDisable() {
    this.updateForm.setEnable(0, false);
    this.updateForm.setEnable(1, false);
    this.updateForm.setEnable(2, false);
    this.updateForm.setEnable(3, false);
  }

  protected void deleteOldFile()
  {
    if (UpdateObject.REDOWNLOAD_ALL_FILE_AND_DELETEALL) {
      String PathName = getLocalUserHome();
      String Separator = System.getProperty("file.separator");
      String Application = (String)this.hashList.get("application");
      PathName = PathName + UpdateObject.getCodeSpace() + Separator + Application + Separator;
      File dirFile = null; String SP = null;
      try {
        StubObject[] SOS = UpdateObject.localRootUpdateStub.getChilds();
        for (int i = 0; i < SOS.length; ++i) {
          SP = SOS[i].getStubID();
          dirFile = new File(PathName + SP);
          FileUtils.deleteDirTree(dirFile);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void run()
  {
    this.bnStop.setEnabled(true);
    this.IS_STOP = false;
    setDisable();

    deleteOldFile();

    saveApplicatinService();
    this.lbTitle1.setText("共有" + this.updateStubArray.length + "项更新，正在下载！");
    this.pbMain.setMinimum(0);
    this.pbMain.setMaximum(this.updateStubArray.length);
    int i = 0;
    List list = new ArrayList();
    for (i = 0; i < this.updateStubArray.length; ++i) {
      if (this.IS_STOP) break;
      this.lbTitle2.setText("正在下载 " + this.updateStubArray[i].toString());
      this.pbMain.setValue(i + 1);
      Object res = downloadFile(this.updateStubArray[i]);
      if (res != null)
      {
        this.updateStubArray[i].setString("ERROR_MESSAGE", ((Throwable)res).getMessage());
      }

      if (this.updateStubArray[i].isRequirUnzip())
        list.add(this.updateStubArray[i]);
    }
    unZipFile(list);
    if (i == this.updateStubArray.length) {
      this.lbTitle1.setText("共有" + this.updateStubArray.length + "项更新，下载完毕！");
      this.lbTitle2.setText(" ");
    } else {
      this.lbTitle1.setText("共有" + this.updateStubArray.length + "项更新，下载终止！");
      this.lbTitle2.setText(this.updateStubArray[i].toString() + "没有成功下载！");
    }
    this.bnStop.setEnabled(false);
    setEnable();
    JOptionPane.showMessageDialog(this, "下载升级完毕！", "系统提示", 1);
    this.updateForm.processNext();
    this.updateForm.setEnable(0, false);
    this.updateForm.setEnable(1, false);
    this.updateForm.setEnable(2, true); }

  void unZipFile(List list) {
    if (list.size() == 0) return;
    this.pbMain.setMinimum(0);
    this.pbMain.setMaximum(list.size());
    int i = 0;
    for (i = 0; i < list.size(); ++i) {
      UpdateStub us = (UpdateStub)list.get(i);
      this.lbTitle2.setText("正在自解压 " + us.toString());
      this.pbMain.setValue(i + 1);
      String sf = getLocalFileURI(us);
      String df = getZipPath(us);

      ZipSelfExtractor.extract(this.pnSecond, new File(sf), new File(df));

      String dllFiles = us.getString("dllFiles", null);
      if (dllFiles != null) {
        String[] dllFileses = dllFiles.split(",");
        if (dllFileses == null) continue; if (dllFileses.length != 0)
          for (int k = 0; k < dllFileses.length; ++k) {
            String command = "regsvr32 /s " + df + "/" + dllFileses[k];
            try {
              this.lbTitle2.setText("正在注册 " + dllFileses[k]);
              Process process = Runtime.getRuntime().exec(command);
              process.waitFor();
            }
            catch (Exception ex) {
              ex.printStackTrace();
            }
            this.lbTitle2.setText("注册完成： " + dllFileses[k]); } 
      }
    }
  }

  void saveApplicatinService() {
    String PathName = getLocalUserHome();
    String Separator = System.getProperty("file.separator");
    PathName = PathName + UpdateObject.getCodeSpace() + Separator;
    File dirFile = new File(PathName);
    if (!(dirFile.exists())) {
      dirFile.mkdirs();
    }
    String FileName = ((String)this.hashList.get("application")) + ".xml";
    this.XMLObject.setEncoding("UTF-8");
    this.XMLObject.SaveToFile(PathName + FileName); }

  Object downloadFile(UpdateStub US) {
    int size = US.getInt("remotesize", 0);
    this.pnSecond.setMinimum(0);
    this.pnSecond.setMaximum(size);
    String LocalFileName = getLocalFileURI(US);
    String RemoteFileName = getRemoteFileURI(US);
    File file = getLocalFile(LocalFileName);
    URL url = getRemoteFileURL(RemoteFileName);
    BasicNetworkLayer basicLayer = new BasicNetworkLayer();
    BasicDownloadLayer downloadFile = new BasicDownloadLayer(basicLayer);
    try {
      downloadFile.download(url, file, this);
      file.setLastModified(Long.parseLong(US.getRemoteTime()));
    } catch (Exception e) {
      file.delete();
      e.printStackTrace();
      if (e.getMessage() == null) {
        return e.getCause();
      }
      return e;
    }

    return null;
  }

  public boolean downloadProgress(int i, int j, Object callback1, Object callback2)
  {
    this.pnSecond.setMinimum(0);
    this.pnSecond.setMaximum(j);
    this.pnSecond.setValue(i);

    if (callback1 instanceof HttpResponse) {
      processBegin((HttpResponse)callback1, (HttpRequest)callback2);
    }

    if (callback1 instanceof File) {
      processEnd((File)callback1);
    }
    return true; }

  void processBegin(HttpResponse hr, HttpRequest httprequest) {
    Map map = hr.getHeader();
    setMap(map);
    URL url = httprequest.getURL();
    if (url != null)
      this.listModel.insertElementAt(url, 0);
  }

  void setMap(Map map) {
    Object[] OArray = map.keySet().toArray();
    for (int i = 0; i < OArray.length; ++i)
      this.listModel.insertElementAt(OArray[i].toString() + ":" + map.get(OArray[i]), 0);
  }

  void processEnd(File file)
  {
  }

  String getLocalUserHome() {
    String PathName = (String)this.hashList.get("user.home");
    return PathName; }

  String getLocalFileURI(UpdateStub US) {
    String FileName = null;
    String PathName = (String)this.hashList.get("user.home");
    String Separator = System.getProperty("file.separator");
    PathName = PathName + UpdateObject.getCodeSpace() + Separator + US.getStubPathName(false);
    File dirFile = new File(PathName);
    if (!(dirFile.exists()))
      dirFile.mkdirs();
    FileName = PathName + US.getStubFileName();
    return FileName;
  }

  protected void initPathMap() {
    if (this.pathMap != null) return;
    this.pathMap = new HashMap();
    this.pathMap.put("%SystemRoot%", "SystemRoot");
    this.pathMap.put("%windir%", "windir");
    this.pathMap.put("%localhome%", this.hashList.get("user.home")); }

  String getZipPath(UpdateStub US) {
    initPathMap();
    String pathName = US.getUnzipPath();
    if ((pathName == null) || (pathName.trim().length() == 0)) return null;
    Object[] keys = this.pathMap.keySet().toArray();
    for (int i = 0; i < keys.length; ++i)
    {
      if (pathName.indexOf(keys[i].toString()) != -1) {
        String key = (String)this.pathMap.get(keys[i]);
        String value = System.getenv(key);
        if ((value != null) && (value.trim().length() > 0)) {
          pathName = replaceString(pathName, keys[i].toString(), value);
        } else {
          value = (String)this.pathMap.get(keys[i]);
          pathName = replaceString(pathName, keys[i].toString(), value);
        }
      }
    }

    pathName = replaceString(pathName, "%Product%", this.Product);

    return replaceString(pathName, "%Application%", this.Application);
  }

  public static String replaceString(String psStr, String psS, String psD)
  {
    int viPos = psStr.indexOf(psS);
    if (viPos < 0)
      return psStr;
    int viLength = psS.length();
    StringBuffer vsValue = new StringBuffer();
    while (viPos >= 0) {
      vsValue.append(psStr.substring(0, viPos));
      vsValue.append(psD);
      psStr = psStr.substring(viPos + viLength);
      viPos = psStr.indexOf(psS);
    }
    vsValue.append(psStr);
    return vsValue.toString();
  }

  File getLocalFile(String FileName)
  {
    File file = new File(FileName);
    try {
      file.createNewFile();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return file; }

  URL getRemoteFileURL(String codebase) {
    URL url = null;
    try {
      url = new URL(codebase);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return url; }

  String getRemoteFileURI(UpdateStub US) {
    String PathName = null;
    if (this.Codebase.toLowerCase().startsWith("file")) {
      PathName = this.Codebase + US.getStubPathName(true) + US.getStubFileName();
    }
    else if (US.getString("svr", null) == null) {
      PathName = this.Codebase + UpdateObject.CODE_SPACE + "/" + US.getStubPathName(true) + US.getStubFileName();
    }
    else {
      PathName = US.getString("svr", "") + UpdateObject.CODE_SPACE + "/" + US.getStubPathName(true) + US.getStubFileName();
    }
    return PathName;
  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == this.bnStop)
      processStopThread();
  }

  void processStopThread() {
    this.IS_STOP = true;
  }
}