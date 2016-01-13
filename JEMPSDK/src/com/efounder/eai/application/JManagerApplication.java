package com.efounder.eai.application;

import java.awt.Component;
import java.net.*;
import java.util.*;

import javax.swing.Icon;

import org.jdom.*;
import com.efounder.eai.*;
import com.efounder.eai.registry.*;
import com.efounder.pfc.application.*;
import com.efounder.resource.JResource;
import com.efounder.util.*;
import com.efounder.eai.resource.JEnterpriseResource;
import com.core.xml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JManagerApplication implements IManagerApplication {
  public Hashtable ApplicationList = new Hashtable();
  public JManagerApplication() {
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void InitManager() throws Exception {
    String KeyName;Element ClassesRoot,ClassElement;Element node;List nodelist;int Index=0;
    JApplicationStub AStub;
    KeyName = "SOFTWARE"+"\\"+"JActiveFrameworkApplications"+"\\Application\\"+EAI.Company;
    ClassesRoot = EAI.Registry.RegOpenKey(IRegistry.HKEY_LOCAL_MACHINE,KeyName);
    nodelist = EAI.Registry.BeginEnumerate(ClassesRoot);
    while ( nodelist != null ) {
      node = EAI.Registry.Enumerate(nodelist,Index++);
      // ���Ϊ��,����
      if ( node == null ) break;
      // ���Ϊ��,���Ƿ���Ԫ��,��Ԫ�زŽ��д���
      ClassElement          = (Element)node;
      AStub = new JApplicationStub();
      AStub.name = EAI.Registry.GetElementValue(ClassElement,"name",null);
      if ( AStub == null ) continue;
      ApplicationList.put(AStub.name,AStub);
      AStub.des = EAI.Registry.GetElementValue(ClassElement,"des",null);
      AStub.ver = EAI.Registry.GetElementValue(ClassElement,"ver",null);
      AStub.icon = EAI.Registry.GetElementValue(ClassElement,"icon",null);
      AStub.init = EAI.Registry.GetElementValue(ClassElement,"init","0");
      AStub.application = EAI.Registry.GetElementValue(ClassElement,"application","com.efounder.eai.application.JEnterpriseApplication");
      AStub.mainwindow = EAI.Registry.GetElementValue(ClassElement,"mainwindow","com.efounder.pfc.window.tab.JTabMainWindow");
      if ( AStub.init.equals("1") ) {
        InitApp(AStub);
      }
      break;
    }
    EAI.Registry.EndEnumerate();
    //
    initPackageObject();
  }
  /**
   *
   */
  public void initPackageObject() {
    // ��ȡPackageStub�е�applications�б�
    Vector ApplicationSOList = PackageStub.getContentVector("applications");
    if ( ApplicationSOList == null ) return;
    StubObject SO = null;JApplicationStub AStub = null;
    for(int i=0;i<ApplicationSOList.size();i++) {
      // ��ȡÿһ��Application Stub
      SO = (StubObject)ApplicationSOList.get(i);
      AStub = convertSO2AO(SO);
      ApplicationList.put(AStub.name,AStub);
      if ( "1".equals(AStub.init) ) {
        InitApp(AStub);
      }
    }
  }
  /**
   *
   * @param SO StubObject
   * @return JApplicationStub
   */
  private static JApplicationStub convertSO2AO(StubObject SO) {
    JApplicationStub AStub = null;
    AStub = new JApplicationStub();
    AStub.name = (String)SO.getObject("name",null);
    AStub.des = (String)SO.getObject("des",null);
    AStub.ver = (String)SO.getObject("ver",null);
    AStub.icon = (String)SO.getObject("icon",null);
    AStub.init = (String)SO.getObject("init",null);
    AStub.application = (String)SO.getObject("application","com.efounder.eai.application.JEnterpriseApplication");
    AStub.mainwindow = (String)SO.getObject("mainwindow","com.efounder.pfc.window.tab.JTabMainWindow");
    return AStub;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public int ExecuteApplication(String AppName,Object[] Array) {
    JApplicationStub AStub = (JApplicationStub)ApplicationList.get(AppName);
    if ( AStub != null ) {
      InitApp(AStub);
      int Res = AStub.EnterpriseApplication.Execute(AStub,Array);
      try {
        // ִ��ÿ��Ӧ�ù̶��ķ�������
        EAI.BOF.IOM(AStub.name, AStub.name, AStub.EnterpriseApplication, this, AStub, null);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
      return Res;
    }
    return -1;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  private void InitApp(JApplicationStub AStub) {
    if ( AStub.EnterpriseApplication != null ) return;
    try {
      AStub.EnterpriseApplication = (JApplication)Class.forName(AStub.application).newInstance();
      AStub.EnterpriseApplication.setName(AStub.name);
      AStub.EnterpriseApplication.setLanguage(EAI.getLanguage());
      EAI.EA = AStub.EnterpriseApplication;
      // ��ʼ����Դ
      URL url = JResource.getResource(this,"/"+AStub.name+"/Resource",AStub.name+".xml",EAI.getLanguage());
      JEnterpriseResource.LoadResource(AStub.name,url,EAI.getLanguage());
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public int ExecuteApplication(String AppName) {
    return ExecuteApplication(AppName,null);
  }
}
