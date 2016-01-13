package com.core.update;

import java.util.ArrayList;

import esyt.framework.com.core.xml.StubObject;

public class UpdateStub extends StubObject
{
  public boolean isFile()
  {
    return (getObject("href", null) != null); }

  public boolean isRequirUnzip() {
    return "unzip".equals(getObject("type", null)); }

  public String getUnzipPath() {
    return getString("zippath", null); }

  public boolean isDelete() {
    String RemoteTime = getRemoteTime();
    long lRemoteTime = Long.parseLong(RemoteTime);
    return (lRemoteTime == 0L);
  }

  public boolean isChanged()
  {
    String RemoteTime = getRemoteTime();
    String RemoteSize = getRemoteSize();
    String LocalTime = getLocalTime();
    if ((LocalTime == null) || ("".equals(LocalTime.trim()))) LocalTime = "0";
    String LocalSize = getLocalSize();
    long lRemoteTime = Long.parseLong(RemoteTime);
    long lLocalTime = Long.parseLong(LocalTime);

    return (Math.abs(lRemoteTime - lLocalTime) > 5000L);
  }

  public boolean isSelected()
  {
    boolean res = false;
    if (!(hasChild())) {
      res = ((isChanged()) && (super.isSelected(true))) || (UpdateObject.REDOWNLOAD_ALL_FILE_AND_DELETEALL);
    } else {
      StubObject[] SOA = getChilds();
      for (int i = 0; i < SOA.length; ++i) {
        res = SOA[i].isSelected();
        if (res) {
          break;
        }
      }
    }

    return res; }

  public void setSelected(boolean v) {
    if (!(isChanged()))
      super.setSelected(false);
    else
      super.setSelected(v);
  }

  public String getVersion(String def) {
    return ((String)getObject("version", def)); }

  public String getNewVersion(String def) {
    return ((String)getObject("newversion", def)); }

  public String getDes(String def) {
    return ((String)getObject("description", def)); }

  public String getRemoteTime() {
    return ((String)getObject("remotetime", "1")); }

  public String getLocalTime() {
    return ((String)getObject("localtime", "0")); }

  public String getRemoteSize() {
    return ((String)getObject("remotesize", "0")); }

  public String getLocalSize() {
    return ((String)getObject("localsize", "0")); }

  public String toString() {
    String cap = super.toString();
    String EM = getString("ERROR_MESSAGE", null);
    if (EM != null)
      EM = "-错误信息:" + EM;
    else EM = "";
    if ((!(isChanged())) && (!(UpdateObject.REDOWNLOAD_ALL_FILE_AND_DELETEALL))) {
      if (isFile())
        cap = cap + "（没有新版本无需下载）";
    } else {
      String sl = getRemoteSize();
      int L = Integer.parseInt(sl);
      if (L != 0) {
        L /= 1024;
        cap = cap + "（" + L + "kb）";
      }
    }
    return cap + EM; }

  public String getStubFileName() {
    return getString("href", null); }

  public String getStubPathName(boolean isRemote) {
    String PathName = ""; ArrayList NodeArray = new ArrayList();
    String Separator = (isRemote) ? "/" : System.getProperty("file.separator");
    _$5488(NodeArray, this);
    for (int i = NodeArray.size() - 1; i >= 0; --i) {
      PathName = PathName + NodeArray.get(i).toString() + Separator;
    }

    return PathName;
  }

  private void _$5488(ArrayList pathList, StubObject SO)
  {
    StubObject parentSO = null;
    if (SO == null) return;
    if (SO.hasChild()) {
      String Name = SO.getStubID();
      pathList.add(Name);
    }
    parentSO = SO.getParent();
    _$5488(pathList, parentSO);
  }
}