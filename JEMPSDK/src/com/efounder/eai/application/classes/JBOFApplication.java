package com.efounder.eai.application.classes;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.core.xml.StubObject;
import com.efounder.JEnterprise.model.JOperateItemStub;
import com.efounder.eai.EAI;
import com.efounder.eai.ide.ExplorerIcons;

import jbof.application.classes.sax.JBOFAppSaxModule;
import jbof.application.classes.sax.JBOFAppSaxOperate;
import jbof.gui.window.classes.JBOFChildWindow;
import jbof.gui.window.classes.JBOFMainWindow;
import jbof.gui.window.classes.style.mdi.JBOFMDIMainWindow;

import jfoundation.application.classes.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.JXMLResource;

//--------------------------------------------------------------------------------------------------
//锟斤拷锟斤拷:
//锟斤拷锟?: Skyline(2001.04.22)
//实锟斤拷: Skyline
//锟睫革拷:
//--------------------------------------------------------------------------------------------------
public class JBOFApplication extends JApplication {
  public Vector OperateList                                  = new Vector();
  public JBOFApplicationStub     BOFApplicationStub          = new JBOFApplicationStub();
  // 定义应用的风格
  // 正常方式
  public static final int APPLICATION_STYLE_MDI       = 0;
  // 向导方式
  public static final int APPLICATION_STYLE_WIZARD    = 1;
  // 浏览方式
  public static final int APPLICATION_STYLE_EXPLORER  = 2;

  public static final String sAPPLICATION_STYLE_MDI       = "STYLE_MDI";
  // 向导方式
  public static final String sAPPLICATION_STYLE_WIZARD    = "STYLE_WIZARD";
  // 浏览方式
  public static final String sAPPLICATION_STYLE_EXPLORER  = "STYLE_EXPLORER";
  // 该应用当前的使用的风格,默认为0,正常风格
  protected int ApplicationStyle                         = APPLICATION_STYLE_MDI;
  protected boolean AppInited                            = false;
  // 存放三种不同风格的窗口
  protected JBOFMainWindow[] BOFMainWindows              = new JBOFMainWindow[3];
  // 应用程序管理器
  protected JBOFApplicationManager BOFApplicationManager = null;
//  public JBOFMainWindow MainWindow                    = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void setBOFApplicationManager(JBOFApplicationManager Value) {
    BOFApplicationManager = Value;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
 //设计: Skyline(2001.12.29)
 //实现: Skyline
 //修改:

  //------------------------------------------------------------------------------------------------
  public JBOFApplicationManager getBOFApplicationManager() {
    return BOFApplicationManager;
  }

  public void setAppInited(boolean value) {
    AppInited = value;
  }
  public boolean getAppInited() {
    return AppInited;
  }
  //------------------------------------------------------------------------------------------------
 //描述: 设置应用的风格
 //设计: Skyline(2001.12.29)
 //实现: Skyline
 //修改:
  public void setApplicationStyle(int Style) {
    ApplicationStyle = Style;
  }
  //------------------------------------------------------------------------------------------------
  //描述: 获取应用的风格

  //------------------------------------------------------------------------------------------------
  public int  getApplicationSytle() {
    return ApplicationStyle;
  }

  public JBOFApplication() {
    BOFMainWindows[APPLICATION_STYLE_MDI]      = null;
    BOFMainWindows[APPLICATION_STYLE_WIZARD]   = null;
    BOFMainWindows[APPLICATION_STYLE_EXPLORER] = null;
  }
  private void LoadModelDefine() {
    String uri;
//      uri = JActiveDComDM.CodeBase + BOFApplicationStub.name +  "/Resource/"+JActiveDComDM.International+"/" +BOFApplicationStub.modulexml;
      java.net.URL url = JXMLResource.LoadSXML(BOFApplicationStub.name +  "/Resource/",BOFApplicationStub.modulexml);
      try {
        uri = url.toString();
        JBOFAppSaxModule SaxModule = new JBOFAppSaxModule(this, BOFApplicationStub);
        SaxModule.parseXmlApplicationDefine(uri);
      } catch ( Exception e ) {
        //e.printStackTrace();
      }
  }
  //------------------------------------------------------------------------------------------------
  // 此函数是在创建一个类实例进执行的,在执行InitApplication之前执行此函数
  //
  //
  //------------------------------------------------------------------------------------------------
  public boolean InitInstance() {
      setAppInited(false);
      LoadModelDefine();
    return true;
  }

  private void LoadOperateDefine() {
    String uri;
//      uri = JActiveDComDM.CodeBase + +JActiveDComDM.International+"/" +BOFApplicationStub.XML_OPERATE;
      java.net.URL url = JXMLResource.LoadSXML(BOFApplicationStub.name + "/Resource/",BOFApplicationStub.XML_OPERATE);
      try {
        uri = url.toString();
        JBOFAppSaxOperate SaxOperate = new JBOFAppSaxOperate(this, OperateList);
        SaxOperate.parseXmlApplicationDefine(uri);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
  }
  //------------------------------------------------------------------------------------------------
  // 此函数是在当创建一个应用是执行的
  //
  //
  //------------------------------------------------------------------------------------------------
  public boolean InitApplication() {
      // 如果还没有初始化过,则进行应用的初始化
    if ( AppInited == false ) {
      LoadOperateDefine();
    }
    setAppInited(true);
    return true;
  }
  //------------------------------------------------------------------------------------------------
 //描述:Run函数是应用的入口,一般是在此函数中创建应用的主窗口,然后打开主窗口,用户也可以自己定制此函数
 //    完成特定功能
  //
  //------------------------------------------------------------------------------------------------
  public int Run(String Param,int WindowStyle) {
    int Res = 0;
    switch ( WindowStyle ) {
    case APPLICATION_STYLE_MDI:
      Res = RunMDI(Param);
      break;
    case APPLICATION_STYLE_WIZARD:
      Res = RunWIZARD(Param);
      break;
    case APPLICATION_STYLE_EXPLORER:
      Res = RunWIZARD(Param);
      break;
    }
    // 调用一个以应用名称为名称的操作,做为此应用的初始化操作
    CallOperateItem(BOFApplicationStub.name,null,null);
    return Res;
  }

  private int RunMDI(String Param) {
    JBOFMDIMainWindow MainWnd;
    if ( BOFMainWindows[0] == null ) {
      MainWnd = new JBOFMDIMainWindow(this);
      BOFMainWindows[0] = MainWnd;
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      MainWnd.setSize(screenSize.width,screenSize.height-25);//(screenSize);
      MainWnd.setExtendedState(MainWnd.MAXIMIZED_BOTH);
      if ( MainWnd.WindowIcon != null )
        MainWnd.setIconImage(MainWnd.WindowIcon.getImage());
    } else {
      MainWnd = (JBOFMDIMainWindow)BOFMainWindows[0];
    }
    if ( "1".equals(System.getProperty("mainWindowShow","1")) )
      MainWnd.show();
    this.MainWindow = MainWnd;
    MainWindow = MainWnd;
    return 0;
  }
  private int RunWIZARD(String Param) {
    return 0;
  }
  private int RunEXPLORER(String Param) {
    return 0;
  }
  //------------------------------------------------------------------------------------------------
  // 在执行ExitInstance之前执行此函数
  //
  //
  //------------------------------------------------------------------------------------------------
  public int ExitApplication() {
      // 如果还没有反初始化过,则进行应用的反初始化
    if ( AppInited == true ) {

    }
    setAppInited(false);
    if ( MainWindow == null ) System.exit(0);
//    if ( MainWindow != null && MainWindow.CloseAllWindow() ){
//      System.exit(0);
//    }
    return 0;
  }
  //------------------------------------------------------------------------------------------------
  //
  //
  //
  //------------------------------------------------------------------------------------------------
  public int ExitInstance() { // return app exit code
    setAppInited(false);
    return 0;
  }
  //------------------------------------------------------------------------------------------------
  //
  //
  //
  //------------------------------------------------------------------------------------------------
  public void About() {
  }
  //----------------------------------------------------------------------------------------------
  //描述: 调用应用中某一个对象的某一个方法

  //----------------------------------------------------------------------------------------------
  public Object CallOperateItem(String OperateName,Object Sender,ActionEvent AE) {
    int i,Count;JOperateItemStub ois=null;Object Res=null;//JMenuItemStub MIS=null;
      //MIS = (JMenuItemStub)Sender;
      Count = OperateList.size();
      for(i=0;i<Count;i++) {
          ois = (JOperateItemStub)OperateList.elementAt(i);
          if ( ois != null && ois.OperateName.compareTo(OperateName) == 0 ) {
              // 在执行操作之前首先要检查当前用户是否有执行此项功能的权限
            //Res = JActiveDComDM.DataActiveFramework.InvokeObjectMethod("SecurityObject","CheckFunctionLimit",ois.OperateNo,null,null,this);
              //if ( Res != null ) {
                  try {
					Res =EAI.DAL.IOM(ois.OperateObject,ois.OperateMethod,AE/*ois.ParamString*/,null/*ois.ParamData*/,Sender,this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//                  Res = JActiveDComDM.BusinessActiveFramework.MInvokeObjectMethod(ois.OperateObject,ois.OperateMethod,AE/*ois.ParamString*/,null/*ois.ParamData*/,Sender,this);
              /*} else {
                   JOptionPane.showMessageDialog(null,
                                 "无权使用"+ois.Description+"功能",
                                 "系统提示",
                  JOptionPane.WARNING_MESSAGE
                  );
              }*/
          }
      }
      return Res;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 打开一个对象窗口
  //----------------------------------------------------------------------------------------------
  public Component OpenObjectWindow(String title,Icon icon, String tip,Component comp) {
	  JBOFChildWindow fp;
      if ( MainWindow != null && comp != null ) {
          MainWindow.OpenObjectWindow(title,icon,tip,comp);
          fp = (JBOFChildWindow)comp;
          fp.Application = this;
      }
      return comp;
  }
  
  //----------------------------------------------------------------------------------------------
  //描述: 打开一个对象窗口
  //----------------------------------------------------------------------------------------------
  public Component OpenObjectWindow(String title,StubObject nodeStub, String tip,Component comp) {
	  JBOFChildWindow fp;
	  Icon icon = null;
	  
      if ( MainWindow != null && comp != null ) {
    	  try {
    		  if(nodeStub != null) {
    			  icon = (Icon) nodeStub.getObject("icon", null);
    		  }
    	  } catch(Exception ce) {
    		  
    	  }
          MainWindow.OpenObjectWindow(title,icon,tip,comp);
          fp = (JBOFChildWindow)comp;
          fp.Application = this;
      }
      return comp;
  }
  
  //----------------------------------------------------------------------------------------------
  //描述: 关闭一个对象窗口

  //----------------------------------------------------------------------------------------------
  public void CloseObjectWindow(Component comp) {
      if ( MainWindow != null && comp != null )
          MainWindow.CloseObjectWindow(comp);
  }
}
