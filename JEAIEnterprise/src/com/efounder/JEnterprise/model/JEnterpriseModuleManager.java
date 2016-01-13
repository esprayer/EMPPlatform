package com.efounder.JEnterprise.model;

import java.util.*;

import org.jdom.*;
import com.core.xml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JEnterpriseModuleManager {
  // 企业系统模块列表
  public Vector EnterpriseModuleList = new Vector();
  // 62094187
  public JEnterpriseModuleManager() {
  }
  public void LoadEnterpriseModuleList() {
    LoadWebEnterpriseModuleList();
    LoadDBEnterpriseModuleList();
  }
  // 装入旧系统中的模块定义
  public void LoadWebEnterpriseModuleList() {

  }
  // 装入新系统中，数据库中的模块定义文件
  public void LoadDBEnterpriseModuleList() {

  }
  // 装入操作定义
  public void LoadOperateDefine(Vector OperateList,JDOMXMLBaseObject DOM) {
    java.util.List nodelist;
    JOperateItemStub  ois;int Index=0;
    Element OE = DOM.GetElementByName("operates");
    nodelist = DOM.BeginEnumerate(OE);
    while ( nodelist != null ) {
      OE = DOM.Enumerate(nodelist,Index++);
      if ( OE == null ) break;
      ois = new JOperateItemStub();
      ois.OperateName     =   DOM.GetElementValue(OE,"name");
      ois.OperateNo       =   DOM.GetElementValue(OE,"operateno");
      ois.OperateObject   =   DOM.GetElementValue(OE,"object");
      ois.Description     =   DOM.GetElementValue(OE,"des");
      ois.OperateMethod   =   DOM.GetElementValue(OE,"method");
      ois.ParamString     =   DOM.GetElementValue(OE,"param");
      ois.ParamData       =   DOM.GetElementValue(OE,"data");
      ois.Standard        =   DOM.GetElementValue(OE,"standard");
      OperateList.add(ois);
    }
    DOM.EndEnumerate();

  }
  // 装入菜单定义
  public void LoadMenuDefine(JDOMXMLBaseObject DOM) {

  }
/*
    //----------------------------------------------------------------------------------------------
    //描述: 开始一个元素的解析
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void startApplication() {
      Element E = this.GetElementByName("Application");
        if ( E != null ) {
          BOFApplicationStub.XML_OPERATE        = GetElementValue(E,"operate");        // 操作定义的XML描述
          BOFApplicationStub.XML_STYLE          = GetElementValue(E,"style");          // MDI
          BOFApplicationStub.description       = GetElementValue(E,"description");
          BOFApplicationStub.img1              = GetElementValue(E,"img1");
          BOFApplicationStub.img2              = GetElementValue(E,"img2");
          BOFApplicationStub.img3              = GetElementValue(E,"img3");
          BOFApplicationStub.img4              = GetElementValue(E,"img4");
          BOFApplicationStub.img5              = GetElementValue(E,"img5");
        }
    }
    //----------------------------------------------------------------------------------------------
    //描述: 开始一个元素的解析
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void startStyle() {
        // 获取应用的模式
        Element E = this.GetElementByName("STYLE");
        if ( E != null ) {
          BOFApplicationStub.XML_STYLE_MDI      = GetElementValue(E,"STYLE_MDI");
          BOFApplicationStub.XML_STYLE_WIZARD   = GetElementValue(E,"STYLE_WIZARD");
          BOFApplicationStub.XML_STYLE_EXPLORER = GetElementValue(E,"STYLE_EXPLORER");
        }
    }

   */
}
