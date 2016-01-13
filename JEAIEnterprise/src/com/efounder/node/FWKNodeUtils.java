package com.efounder.node;

import com.core.xml.StubObject;
import com.core.xml.JXMLBaseObject;
import java.net.URL;
import java.util.*;
import org.jdom.Element;

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
public class FWKNodeUtils {
  protected FWKNodeUtils() {
  }

  public static void buildFWKMenus(JNodeStub nodeStub) {
    StubObject stub = nodeStub.getNodeStubObject();
    if ( stub == null ) return;
    String fwkops   = stub.getString("fwkops",null);
    String fwkmenus = stub.getString("fwkmenus",null);
    if ( fwkops == null || fwkmenus == null ) return;
    java.util.Map  opMap = getOperates(fwkops);
    java.util.List menuList = getMainMenu(fwkmenus);
    processMenus(opMap,menuList);
    // 如果以前没有，或是设置了bottom，则增加到最下面
    if ( "top".equals(stub.getString("fkworder","top")) ) {
      if ( nodeStub.getChildNodeStubList().size() > 0 )
        menuList.addAll(nodeStub.getChildNodeStubList());
      nodeStub.getChildNodeStubList().clear();
    }
    nodeStub.getChildNodeStubList().addAll(menuList);
  }
  /**
   * 节点：
   <node
     id="pzclPZQR1"
     caption="本责任中心确认信息"
     nodeClass="jenterprise.FWKNodeStub"
     object="VoucherObject"
     method="ProcessQR1Voucher"
     GNBH="0120"/>
   * 菜单：
   * <menuitem3
   *   name="mmm1"
   *   caption="本责任中心确认信息"
   *   operateitem="pzcl.pzqr1"
   *   selchar="B"/>
   * OP:
   * <operateitem
   *   name="pzcl.pzsl"
   *   des="凭证输入"
   *   object="VoucherObject"
       method="GetRandomVoucher"
       operateno="0110" />
   */

  protected static void processMenus(java.util.Map  opMap,java.util.List menuList) {
    if ( menuList == null || menuList.size() == 0 ) return;
    StubObject stub = null;
    for(int i=0;i<menuList.size();i++) {
      stub = (StubObject)menuList.get(i);
      // 处理自己
      processMenu(opMap,stub);
      // 处理下级
      processMenus(opMap,stub.getChildList());
    }
  }
  /**
   *
   * @param opMap Map
   * @param stub StubObject
   */
  protected static void processMenu(java.util.Map opMap,StubObject stub) {
    String operateitem = stub.getString("operateitem",null);
    StubObject opStub = (StubObject)opMap.get(operateitem);
    // 明细菜单
    if ( opStub != null ) {
      stub.setID(operateitem);
      // 设置节点类
      stub.setString("nodeClass","jenterprise.FWKNodeStub");
      stub.setString("object",opStub.getString("object",null));
      stub.setString("method",opStub.getString("method",null));
      stub.setString("param",opStub.getString("param",null));
      stub.setString("data",opStub.getString("data",null));
      stub.setString("GNBH",opStub.getString("operateno",null));
    } else {// 非明细菜单
      stub.setID(stub.getName());
      stub.setString("icon","fmisres/SYS_RUN.gif");
    }
    JNodeStub.initStubObject(stub);
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @param opMap Map
   * @param menuList List
   */
  public static void buildMenus(JNodeStub nodeStub,java.util.Map  opMap,java.util.List menuList) {

  }
  /**
   *
   * @param fwkmenus String
   * @return List
   */
  public static java.util.List getMainMenu(String fwkmenus) {
    java.util.List menuList = null;
    String[] fwkmenusArray = fwkmenus.split(";");
    if ( fwkmenusArray == null || fwkmenusArray.length == 0 ) return null;
    for(int i=0;i<fwkmenusArray.length;i++) {
      URL url = FWKNodeUtils.class.getClassLoader().getResource(fwkmenusArray[i]);
      if ( url == null ) continue;
      JXMLBaseObject xml = new JXMLBaseObject(url);
      java.util.List list = getMainMenu(xml);
      if ( list != null ) {
        if ( menuList == null ) menuList = new ArrayList();
        menuList.addAll(list);
      }
    }
    return menuList;
  }
  /**
   *
   * @param fwkops String
   * @return Map
   */
  public static java.util.Map getOperates(String fwkops) {
    java.util.Map opMap = null;
    String[] fwkopArray = fwkops.split(";");
    if ( fwkopArray == null || fwkopArray.length == 0 ) return null;
    for(int i=0;i<fwkopArray.length;i++) {
      URL url = FWKNodeUtils.class.getClassLoader().getResource(fwkopArray[i]);
      if ( url == null ) continue;
      JXMLBaseObject xml = new JXMLBaseObject(url);
      java.util.Map map = getOperates(xml);
      if ( map != null ) {
        if ( opMap == null ) opMap = new HashMap();
        opMap.putAll(map);
      }
    }
    return opMap;
  }












  /**
   <ModuleConfig>
           <operates>
                <!-- 凭证处理 -->
        <operateitem name="pzcl.pzsl" des="凭证输入" object="VoucherObject"/>
           <operates>
   <ModuleConfig>
   */
  public static final String operates = "operates";
  /**
   * 获取FMIS中定义的所有Operates
   * @param XML JXMLBaseObject
   * @return Map
   */
  public static java.util.Map getOperates(JXMLBaseObject XML) {
    Element child;int Index = 0;StubObject SO = null;String ID,Caption;
    java.util.List nodelist = XML.BeginEnumerate(XML.Root);
    java.util.Map opMap = null;
    // 枚举operates节点
    while ( nodelist != null ) {
      child = XML.Enumerate(nodelist,Index++);
      if ( child == null ) break;
      if ( !"operates".equals(child.getName()) ) continue;
      java.util.List nodelist1 = XML.BeginEnumerate(child);
      int i1=0;Element child1=null;
      while ( nodelist1 != null ) {
        child1 = XML.Enumerate(nodelist1,i1++);
        if ( child1 == null ) break;
        // 新建HashMap
        if ( opMap == null ) opMap = new java.util.HashMap();
        SO = new StubObject();
        // 转换成StubObject
        StubObject.createXML2Stub(XML,child1,SO);
        opMap.put(SO.getString("name",null),SO);
      }
    }
    return opMap;
  }
  /**
  <ModuleConfig>
          <gui>
                  <resourcebase url="http://127.0.0.1/JFinance/Resource/"/>
                  <menus>
                          <mainmenu name="menu">
                                  <menupzcl name="File" caption="凭证处理" operateitem="" ischild="false" icon="" selchar="A">
                                          <menuitem2 name="new" caption="凭证输入" operateitem="pzcl.pzsl" icon="pzsr.jpg" toolbar="1" selchar="A" keychar="V" keymask="CTRL"/>
                                          <menuitem2 name="open" caption="凭证确认" operateitem="" selchar="B">
  */

 public static java.util.List getMainMenu(JXMLBaseObject XML) {
   int i1 = 0;Element child1 = null;
   java.util.List nodelist1 = XML.BeginEnumerate(XML.Root);
   java.util.List mainMenuList = null;StubObject SO = null;
   // 第一层是循环处理gui
   while ( nodelist1 != null ) {
     child1 = XML.Enumerate(nodelist1,i1++);
     if ( child1 == null ) break;;
     // 如果不是gui，处理下一个
     if ( !"gui".equals(child1.getName()) ) continue;

     int i2 = 0;Element child2 = null;
     java.util.List nodelist2 = XML.BeginEnumerate(child1);
     while ( nodelist2 != null ) {
       child2 = XML.Enumerate(nodelist2,i2++);
       if ( child2 == null ) break;
       if ( !"menus".equals(child2.getName()) ) continue;

       int i3 = 0;Element child3 = null;
       java.util.List nodelist3 = XML.BeginEnumerate(child2);
       while ( nodelist3 != null ) {
         child3 = XML.Enumerate(nodelist3,i3++);
         if ( child3 == null ) break;
         if ( !"mainmenu".equals(child3.getName()) ) continue;
         // 在这里正式处理
         SO = new StubObject();
         if ( mainMenuList == null ) mainMenuList = new java.util.ArrayList();
         // 形成StubObject
         StubObject.createXML2Stub(XML,child3,SO);
         if ( SO.getChildList() != null )
           mainMenuList.addAll(SO.getChildList());
       }
     }
     //
   }
   return mainMenuList;
 }
}
