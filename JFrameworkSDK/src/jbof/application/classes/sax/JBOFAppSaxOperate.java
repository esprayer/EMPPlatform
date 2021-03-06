package jbof.application.classes.sax;

import java.util.*;
import jbof.application.classes.*;
import jbof.application.classes.operate.*;
import jtoolkit.xml.classes.JDOMXMLBaseObject;
import org.jdom.*;

import com.efounder.eai.application.classes.JBOFApplication;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JBOFAppSaxOperate extends JDOMXMLBaseObject {
  private JBOFApplication    ba;
  private Vector OperateList;
  //----------------------------------------------------------------------------------------------
  //描述: 模块定义文件的解析器的构造函数
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JBOFAppSaxOperate(JBOFApplication BA,Vector ol) {
      ba = BA;
      OperateList = ol;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 模块定义文件的解析器解析过程
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean parseXmlApplicationDefine(String uri) {
    this.InitXMLURI(uri);
    startElement();
    return true;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 开始一个元素的解析
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void startElement() {
    java.util.List nodelist;
    JOperateItemStub  ois;int Index=0;
    Element OE = this.GetElementByName("operates");
    nodelist = this.BeginEnumerate(OE);
    while ( nodelist != null ) {
      OE = this.Enumerate(nodelist,Index++);
      if ( OE == null ) break;
      ois = new JOperateItemStub();
      ois.OperateName     =   GetElementValue(OE,"name");
      ois.OperateNo       =   GetElementValue(OE,"operateno");
      ois.OperateObject   =   GetElementValue(OE,"object");
      ois.Description     =   GetElementValue(OE,"des");
      ois.OperateMethod   =   GetElementValue(OE,"method");
      ois.ParamString     =   GetElementValue(OE,"param");
      ois.ParamData       =   GetElementValue(OE,"data");
      ois.Standard        =   GetElementValue(OE,"standard");
      OperateList.add(ois);
    }
    this.EndEnumerate();
  }
  /**
  //描述: 动态添加菜单内容
  //设计: Skyline(2001.12.29)
  //实现: Yrh (2006.07.23)
  //修改:
   */
  public void addMenuElement(String name,Vector OperateItems) {
    if (OperateItems==null) return;

    JOperateItemStub  ois;
    for (int i = 0 ; i < OperateItems.size(); i++){
      ois = (JOperateItemStub)OperateItems.get(i);
      if (ois==null) continue;
      if (!ois.MenuFileName.equals(name)) continue;
      OperateList.add(ois);
    }
    this.EndEnumerate();
  }
}
