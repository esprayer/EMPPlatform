package com.efounder.JEnterprise.bof;

import java.lang.reflect.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import org.jdom.*;
import com.core.xml.*;
import com.efounder.action.*;
import com.efounder.actions.*;
import com.efounder.eai.*;
import com.efounder.eai.framework.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.resource.*;
import com.efounder.eai.ui.*;
import com.efounder.eai.ui.action.*;
import com.efounder.node.view.*;
import com.efounder.pfc.window.*;
import com.efounder.util.*;
import com.efounder.resource.JResource;
import com.efounder.service.security.*;
import com.efounder.eai.data.*;
import com.efounder.view.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBOFEnterprise extends JActiveObject {
  private static final String GUID = "00000000-0001-0001-0000000000000001";
  public static final String ENTERPRISE_EXPLORER = "EnterpriseExplorer";
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JBOFEnterprise() {
    setObjectGUID(GUID);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object InitObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    // 初始化NodeContextActionProvider
    initNodeContextActionProvider();
    // 初始化NodeViewerFactory
    initNodeViewerFactory();
    // 初始化NodeExplorerFactory
    initNodeExplorerViewFactory();
    // 结点结构视图工厂
    initNodeStructViewerFactory();
    // 结点属性视图工厂
    initNodePropertyViewerFactory();
    // 结点top视图工厂
    initNodeBottomViewerFactory();
    return null;
  }
  /**
   *
   */
  protected void initNodeBottomViewerFactory() {
    Vector ProviderList = PackageStub.getContentVector("nodeBottomViewFactory");
    if ( ProviderList == null ) return;
    StubObject SO = null;String Clazz,Order;
    for(int i=0;ProviderList!=null && i<ProviderList.size();i++) {
      SO = (StubObject)ProviderList.get(i);
      Clazz = (String)SO.getObject("clazz",null);
      Order = (String)SO.getObject("order","-1");
      int order = Integer.parseInt(Order);
      if ( Clazz != null && !"".equals(Clazz.trim()) ) {
        try {
          NodeAttribViewerFactory nvf = (NodeAttribViewerFactory)Class.forName(Clazz).newInstance();
          nvf.setStubObject(SO);
          if ( ServiceSecurityManager.getDefault().checkPermission(nvf.getSecurityKey()) )
            NodeBottomViewManager.registerNodeViewerFactory(order,nvf);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
      }
    }
  }
  /**
   *
   */
  protected void initNodePropertyViewerFactory() {
    Vector ProviderList = PackageStub.getContentVector("nodePropertyViewFactory");
    if ( ProviderList == null ) return;
    StubObject SO = null;String Clazz,Order;
    for(int i=0;ProviderList!=null && i<ProviderList.size();i++) {
      SO = (StubObject)ProviderList.get(i);
      Clazz = (String)SO.getObject("clazz",null);
      Order = (String)SO.getObject("order","-1");
      int order = Integer.parseInt(Order);
      if ( Clazz != null && !"".equals(Clazz.trim()) ) {
        try {
          NodeAttribViewerFactory nvf = (NodeAttribViewerFactory)Class.forName(Clazz).newInstance();
          nvf.setStubObject(SO);
          if ( ServiceSecurityManager.getDefault().checkPermission(nvf.getSecurityKey()) )
            NodePropertyViewManager.registerNodeViewerFactory(order,nvf);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
      }
    }
  }
  /**
   *
   */
  protected void initNodeStructViewerFactory() {
    Vector ProviderList = PackageStub.getContentVector("nodeStructViewFactory");
    if ( ProviderList == null ) return;
    StubObject SO = null;String Clazz,Order;
    for(int i=0;ProviderList!=null && i<ProviderList.size();i++) {
      SO = (StubObject)ProviderList.get(i);
      Clazz = (String)SO.getObject("clazz",null);
      Order = (String)SO.getObject("order","-1");
      int order = Integer.parseInt(Order);
      if ( Clazz != null && !"".equals(Clazz.trim()) ) {
        try {
          NodeAttribViewerFactory nvf = (NodeAttribViewerFactory)Class.forName(Clazz).newInstance();
          nvf.setStubObject(SO);
          if ( ServiceSecurityManager.getDefault().checkPermission(nvf.getSecurityKey()) )
            NodeStructViewManager.registerNodeViewerFactory(order,nvf);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
      }
    }
  }
  /**
   *
   */
  protected void initNodeContextActionProvider() {
    Vector ProviderList = PackageStub.getContentVector("nodecontextactionprovider");
    if ( ProviderList == null ) return;
    StubObject SO = null;String Clazz,Key,Order;
    for(int i=0;i<ProviderList.size();i++) {
      SO = (StubObject)ProviderList.get(i);
      Clazz = (String)SO.getObject("clazz",null);
      Key   = (String)SO.getObject("key",null);
      Order = (String)SO.getObject("order","-1");
      int order = Integer.parseInt(Order);
      if ( Clazz != null && Key != null && !"".equals(Clazz.trim()) && !"".equals(Key.trim()) ) {
        try {
          ContextActionProvider cap = (ContextActionProvider)Class.forName(Clazz).newInstance();
          ActionManager.registerContextActionProvider(Key,cap,order);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
      }
    }
  }
  /**
   *
   */
  protected void initNodeExplorerViewFactory() {
    Vector ProviderList = PackageStub.getContentVector("nodeexplorerviewfactory");
    if ( ProviderList == null ) return;
    StubObject SO = null;String Clazz,Order;
    for(int i=0;i<ProviderList.size();i++) {
      SO = (StubObject)ProviderList.get(i);
      Clazz = (String)SO.getObject("clazz",null);
      Order = (String)SO.getObject("order","-1");
      int order = Integer.parseInt(Order);
      if ( Clazz != null && !"".equals(Clazz.trim()) ) {
        try {
          NodeViewerFactory nvf = (NodeViewerFactory)Class.forName(Clazz).newInstance();
          nvf.setStubObject(SO);
//          if ( nvf.getSecurityKey() != null )
//            System.out.println(nvf.getSecurityKey());
          if ( ServiceSecurityManager.getDefault().checkPermission(nvf.getSecurityKey()) )
            NodeExplorerViewManager.registerNodeViewerFactory(order,nvf);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
      }
    }
  }
  /**
   *
   */
  protected void initNodeViewerFactory() {
    Vector ProviderList = PackageStub.getContentVector("nodeviewerfactory");
    if ( ProviderList == null ) return;
    StubObject SO = null;String Clazz,Order;
    for(int i=0;i<ProviderList.size();i++) {
      SO = (StubObject)ProviderList.get(i);
      Clazz = (String)SO.getObject("clazz",null);
      Order = (String)SO.getObject("order","-1");
      int order = Integer.parseInt(Order);
      if ( Clazz != null && !"".equals(Clazz.trim()) ) {
        try {
          NodeViewerFactory nvf = (NodeViewerFactory)Class.forName(Clazz).newInstance();
          nvf.setStubObject(SO);
//          if ( nvf.getSecurityKey() != null )
//            System.out.println(nvf.getSecurityKey());
          if ( ServiceSecurityManager.getDefault().checkPermission(nvf.getSecurityKey()) )
            NodeViewerManager.registerNodeViewerFactory(order,nvf);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
      }
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object JEnterprise(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    try {
      // 装入当前产品的资源
      loadProductResource();
      // 初始化Enterprise Explorer
      initEnterpriseExplorer();

//      openExplorerView();
//      openContentView();

    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }
  /**
   *
   */
  protected void loadProductResource() {
    if ( EAI.EA.getMainWindow() == null ) return;
    URL url = JResource.getResource(this,"/"+EAI.Product+"/Resource",EAI.Product+".xml",EAI.getLanguage());
    String value = null;
    if ( url != null ) {
      JEnterpriseResource.LoadResource(EAI.Product, url, EAI.getLanguage());
      String DWMC = (String)JParamObject.getObject("STAT_DWMC",null);
      value = JEnterpriseResource.GetString(EAI.Product,EAI.Product+"Caption",null);
      if ( value != null ) {
        if ( DWMC != null ) value = DWMC + "-" + value;
        EAI.EA.getMainWindow().setTitle(value);
      }
      value = JEnterpriseResource.GetString(EAI.Product,EAI.Product+"Icon",null);
      if ( value != null ) {
        ImageIcon ii = JResource.getImageIcon(this,"/"+EAI.Product+"/Resource",value,EAI.getLanguage());
        EAI.EA.getMainWindow().setIconImage(ii.getImage());
      }
    }
  }
  /**
   *
   */
  protected void initEnterpriseExplorer() {
    // 1、读取不同产品下的不同的EnterpriseExplorer.xml配置文件
    JXMLBaseObject EnterpriseExplorer = initEEXML();
    // 2、根据配置文件中的配置信息，生成不同的显示界面
    if ( EnterpriseExplorer != null ) {
      initEE(EnterpriseExplorer);
      initStartContextView();
    }
  }
  /**
   *
   * @param XMLEnterpriseExplorer JXMLBaseObject
   */
  protected void initEE(JXMLBaseObject XMLEnterpriseExplorer) {
    ActionGroup GROUP_View = new ExplorerActionGroup("视图面板管理", '@', "视图管理", null,true);
    EnterpriseExplorer.ViewDevice.setAction(GROUP_View);

    initEEView(XMLEnterpriseExplorer,"TopView",EnterpriseExplorer.TopView);
    initEEView(XMLEnterpriseExplorer,"ExplorerView",EnterpriseExplorer.ExplorerView);
    initEEView(XMLEnterpriseExplorer,"StructView",EnterpriseExplorer.StructView);
    initEEView(XMLEnterpriseExplorer,"ContentView",EnterpriseExplorer.ContentView);
    initEEView(XMLEnterpriseExplorer,"PropertyView",EnterpriseExplorer.PropertyView);
//    initEEView(XMLEnterpriseExplorer,"MessageView",EnterpriseExplorer.MessageView);

    ViewMenu.GROUP_ViewContext.add(GROUP_View);


  }
  protected void initStartContextView() {
    try {
      EAI.DOF.IOM(EAI.Product, "openStartWindow", EnterpriseExplorer.getExplorer());
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @param EnterpriseExplorer JXMLBaseObject
   * @param ViewName String
   * @param CView IView
   */
  protected void initEEView(JXMLBaseObject XMLEnterpriseExplorer,String ViewName,IView CView) {
    Element View = XMLEnterpriseExplorer.GetElementByName(ViewName);
    if ( View != null ) {
      initEEView(XMLEnterpriseExplorer,View,CView);
      String v = XMLEnterpriseExplorer.GetElementValue(View,"visible","1");
      if ( "0".equals(v) ) CView.setVisible(false);
//      String type = XMLEnterpriseExplorer.GetElementValue(View,"type","view");
//      if ( "view".equals(type) ) {
        Action ViewPanel  = new JViewAction(CView,null,CView.getTitle(),'@',CView.getTitle(),CView.getIcon());
        ((ActionGroup)EnterpriseExplorer.ViewDevice.getAction()).add(ViewPanel);
//      }
    }
  }
  /**
   *
   * @param XMLEnterpriseExplorer JXMLBaseObject
   * @param View Element
   * @param CView IView
   */
  protected void initEEView(JXMLBaseObject XMLEnterpriseExplorer,Element View,IView CView) {
    try {
      String CaptionID = XMLEnterpriseExplorer.GetElementValue(View,"id");
      String Caption   = XMLEnterpriseExplorer.GetElementValue(View,"caption");
      Caption = JEnterpriseResource.GetString(EAI.Product,CaptionID,Caption);
      String type = XMLEnterpriseExplorer.GetElementValue(View,"type","view");
      // 设置标题
      CView.setTitle(Caption);
      String icon      = XMLEnterpriseExplorer.GetElementValue(View,"icon");
      Icon ii = ExplorerIcons.getImageIcon(this,"/"+EAI.Product+"/Resource",icon,EAI.getLanguage());
      // 设置Icon
      CView.setIcon(ii);
      // 设置ViewAction
      if ( !"".equals(Caption) && "view".equals(type) ) {
        Action GROUP_View = new ExplorerActionGroup(Caption, '@', Caption, ii,true);
        ViewMenu.GROUP_ViewComponents.add(GROUP_View);
        CView.setAction(GROUP_View);
      }
      // 设置到窗口菜单中
      if ( !"".equals(Caption) && "window".equals(type) ) {
        Action GROUP_Window = new ExplorerActionGroup(Caption, '@', Caption, ii);
        WindowMenu.GROUP_WindowList.add(GROUP_Window);
        CView.setAction(GROUP_Window);
      }
      // 设置
      // 初始化预设的子窗口
      initChildWindow(XMLEnterpriseExplorer,View,CView);
      // 打开窗口岸后，再进行某些设置
      // 设置 Scale
      String Scale = XMLEnterpriseExplorer.GetElementValue(View,"scale",null);
      if ( Scale != null && !"".equals(Scale) ) {
        String[]str=Scale.split(",");
        double dScale = Double.parseDouble(str[0]);
        CView.setLocation(dScale);
        if(str.length>1){
          JSplitPane sp = (JSplitPane)CView.getViewComponent().getParent();
          for(int i=1;i<str.length;i++){
            sp = (JSplitPane) sp.getParent();
            sp.setDividerLocation(Double.parseDouble(str[i]));
          }
        }
      }
      // 设置 Placement
      String Placement = XMLEnterpriseExplorer.GetElementValue(View,"placement");
      if ( Placement != null && !"".equals(Placement) ) {
        Field field = CView.getClass().getField(Placement);
        int Place = field.getInt(field);
        CView.setViewPlacement(Place);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
  }
  /**
   * 初始化预设的子窗口
   * @param XMLEnterpriseExplorer JXMLBaseObject
   * @param View Element
   * @param CView IView
   */
  protected void initChildWindow(JXMLBaseObject XMLEnterpriseExplorer,Element View,IView CView) {
    java.util.List nodelist = XMLEnterpriseExplorer.BeginEnumerate(View);
    Element childview = null;int Index = 0;
    while ( nodelist != null ) {
      childview = XMLEnterpriseExplorer.Enumerate(nodelist,Index++);
      if ( childview == null ) break;
      // 打开子窗口
      initChildWindow(XMLEnterpriseExplorer,View,childview,CView);
    }
    XMLEnterpriseExplorer.EndEnumerate();
  }
  protected void initChildWindow(JXMLBaseObject XMLEnterpriseExplorer,Element View,Element echildWindow,IView CView) {
    // 获取Clazz的值
    String Clazz   = XMLEnterpriseExplorer.GetElementValue(echildWindow,"class",null);
    String ID      = XMLEnterpriseExplorer.GetElementValue(echildWindow,"id",null);
    String ICON    = XMLEnterpriseExplorer.GetElementValue(echildWindow,"icon",null);
    String Caption = XMLEnterpriseExplorer.GetElementValue(echildWindow,"caption",null);
    String XML     = XMLEnterpriseExplorer.GetElementValue(echildWindow,"xml",null);
    String Root     = XMLEnterpriseExplorer.GetElementValue(echildWindow,"root",null);
    Caption        = JEnterpriseResource.GetString(EAI.Product,ID,Caption);
    java.util.List AttrList = echildWindow.getAttributes();
    Attribute attr = null;
    try {
      // 如果Clazz不为空则装入此类
      if (Clazz != null && !"".equals(Clazz.trim())) {
        Class ViewClass = Class.forName(Clazz);
        // 新建一个视
        IWindow childWindow = (IWindow)ViewClass.newInstance();

        // 临时增加
        if ( childWindow instanceof JRootExplorerView ) {
          ((JRootExplorerView)childWindow).setCanClose(false);
        }


        // 将属性设置到childWindow中
        for(int i=0;i<AttrList.size();i++) {
          attr = (Attribute)AttrList.get(i);
          childWindow.putObject(attr.getName(),attr.getValue());
        }
        // 设置ID
        childWindow.setID(ID);
        JXMLBaseObject XMLBaseObject = null;
        // 形成 XMLObject
        if ( XML != null && !"".equals(XML) ) {
          URL url = JResource.getResource(this, "/" + EAI.Product + "/Resource",XML, null);
          if (url != null) {
            XMLBaseObject = new JXMLBaseObject(url);
          }
        }
        // 以XMLBaseObject初始化一个子窗口
        if ( XMLBaseObject != null )
          childWindow.initObject(XMLBaseObject);
        else
          childWindow.initObject(Root);
        // 获取icon
        Icon icon = ExplorerIcons.getImageIcon(this,"/"+EAI.Product+"/Resource",ICON,EAI.getLanguage());
        // 打开新建的窗口
        if ( childWindow.canOpen() ) {
          CView.openChildWindow(childWindow, Caption, Caption, icon);
        }
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @return JXMLObject
   */
  protected JXMLBaseObject initEEXML() {
    JXMLBaseObject XMLObject = null;
    URL url = JResource.getResource(this,"/"+EAI.Product+"/Resource",ENTERPRISE_EXPLORER+".xml",null);
    if ( url != null ) {
      XMLObject = new JXMLBaseObject(url);
    }
    return XMLObject;
  }
}
