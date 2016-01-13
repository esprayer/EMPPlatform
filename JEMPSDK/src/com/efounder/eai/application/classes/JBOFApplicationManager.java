package com.efounder.eai.application.classes;

import java.util.*;

import org.jdom.*;
import jfoundation.application.classes.*;
import jframework.foundation.classes.*;
import jtoolkit.registry.classes.*;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//锟斤拷锟斤拷: 应锟矫筹拷锟斤拷锟斤拷锟斤拷锟\uFFFD(锟斤拷锟斤拷锟斤拷锟接︼拷贸锟斤拷锟\uFFFD)
//     锟斤拷锟皆讹拷应锟矫筹拷锟斤拷锟斤拷械锟斤拷锟\uFFFD,锟斤拷锟斤拷,删锟斤拷
//锟斤拷锟\uFFFD: Skyline(2001.04.22)
//实锟斤拷: Skyline
//锟睫革拷:
//--------------------------------------------------------------------------------------------------
public class JBOFApplicationManager extends JApplicationManager{
  static final String SOFTWARE                     = "SOFTWARE";
  static final String JActiveFrameworkApplications = "JActiveFrameworkApplications";
  static final String Applications                 = "Applications";
  protected String Company;
  protected String Product;
  protected String Tier;
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public void setCompany(String Value) {
    Company = Value;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public String getCompany() {
    return Company;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public void setProduct(String Value) {
    Product = Value;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public String getProduct() {
    return Product;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public void setTier(String Value) {
    Tier = Value;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public String getTier() {
    return Tier;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public JBOFApplicationManager() {
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
//  public int Execute(String ApplicationName,String Param,int WindowStyle) {
//    JBOFApplication BOFApplication;int Res = 0;
//    BOFApplication = (JBOFApplication)FindApplication(ApplicationName);
//    if ( BOFApplication != null ) {
//      if ( BOFApplication.InitApplication() == true ) {
//        JActiveDComDM.MainApplication = BOFApplication;
//        Res = BOFApplication.Run(Param,WindowStyle);
//      }
//    }
//    return Res;
//  }
  public int Execute(String ApplicationName,String Param,int WindowStyle) {
    JBOFApplication BOFApplication;int Res = 0;
    BOFApplication = (JBOFApplication)FindApplication(ApplicationName);
    JActiveDComDM.MainApplication = BOFApplication;
    if ( BOFApplication != null && WindowStyle != -1 ) {
      if ( BOFApplication.InitApplication() == true ) {
        Res = BOFApplication.Run(Param,WindowStyle);
      }
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public JApplication FindApplication(String AppName) {
    JBOFApplication App = null;int i,Count;
      Count = ApplicationList.size();
      for(i=0;i<Count;i++) {
        App = (JBOFApplication)ApplicationList.get(i);
        if ( App.BOFApplicationStub.name.equals(AppName) ) return App;
      }
      return null;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public Object Initialize(String Param,String Data,Object CustomObject,Object AdditiveObject) {
    LoadApplications();
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public void LoadApplications() {
    Element ClassesRoot,ClassElement;Element node;List nodelist;int Index=0;
    String KeyName;JBOFApplication Application;
      ApplicationList.removeAllElements();
      KeyName = SOFTWARE+"\\"+JActiveFrameworkApplications+"\\"+Company+"\\"+Product+"\\"+Tier+"\\"+Applications;
      ClassesRoot = JActiveDComDM.XMLRegistry.RegOpenKey(JXMLRegistry.HKEY_LOCAL_MACHINE,KeyName);
      nodelist = JActiveDComDM.XMLRegistry.BeginEnumerate(ClassesRoot);
      while ( nodelist != null ) {
        node = JActiveDComDM.XMLRegistry.Enumerate(nodelist,Index++);
        // 锟斤拷锟轿拷锟\uFFFD,锟斤拷锟斤拷
        if ( node == null ) break;
        // 锟斤拷锟轿拷锟\uFFFD,锟斤拷锟角凤拷锟斤拷元锟斤拷,锟斤拷元锟截才斤拷锟叫达拷锟斤拷
//        if ( node.getNodeType() == node.ELEMENT_NODE ) {
          ClassElement          = (Element)node;
          Application                   = new JBOFApplication();
          // 锟斤拷锟斤拷应锟矫筹拷锟斤拷锟斤拷锟斤拷锟\uFFFD
          Application.setBOFApplicationManager(this);
          Application.BOFApplicationStub.ApplicationAttrib = ClassElement;             // 锟斤拷锟斤拷锟斤拷注锟斤拷锟斤拷械墓锟斤拷锟斤拷锟斤拷锟皆拷锟\uFFFD
          Application.BOFApplicationStub.name              = ClassElement.getAttribute("name").getValue();
          Application.BOFApplicationStub.modulexml         = ClassElement.getAttribute("modulexml").getValue();
//          Application.BOFApplicationStub.description       = ClassElement.getAttribute("description");
//          Application.BOFApplicationStub.img1              = ClassElement.getAttribute("img1");
//          Application.BOFApplicationStub.img2              = ClassElement.getAttribute("img2");
//          Application.BOFApplicationStub.img3              = ClassElement.getAttribute("img3");
//          Application.BOFApplicationStub.img4              = ClassElement.getAttribute("img4");
//          Application.BOFApplicationStub.img5              = ClassElement.getAttribute("img5");
          // 执锟斤拷应锟斤拷实锟斤拷锟绞硷拷锟\uFFFD
          Application.InitInstance();
          ApplicationList.add(Application);
//        }
      }
  }
  //------------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟\uFFFD: Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //------------------------------------------------------------------------------------------------
  public Object Destroy() {
    return null;
  }
}
