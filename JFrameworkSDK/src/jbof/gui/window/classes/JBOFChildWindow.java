package jbof.gui.window.classes;

import javax.swing.*;

import com.efounder.eai.application.classes.JBOFApplication;

import jfoundation.gui.window.classes.JChildWindow;
import jfoundation.gui.window.classes.JMainWindow;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;


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
public class JBOFChildWindow extends JChildWindow {
  public JBOFApplication          Application=null;
  public JActiveObject            ActiveObject=null;
  public ImageIcon                WindowIcon = null;
  public JMainWindow           MainWindow = null;
  public JBOFChildWindow          ParentWindow = null;
  /**
   * 窗口打开日期和时间，用于日志记录
   */
  protected String mStartDate = "";
  protected String mStartTime = "";
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JBOFChildWindow() {
  }
  //----------------------------------------------------------------------------------------------
  //描述: 构造函数
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JBOFChildWindow(JBOFApplication App,JActiveObject AO) {
      super();
      Application = App;
      ActiveObject= AO;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void WindowOpened() {
    /**
     * 取得打开时间
     * add by hufeng 2006.7.5
     */
//    getLogDate();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void WindowClosed() {
    /**
     * 记录日志
     * add by hufeng 2006.7.5
     */
//    this.writeLog();
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void WindowActive(Object Sender) {

  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void WindowDeActive(Object Sender) {

  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void LoadWindowIcon() {
    String IconName = "childicon.gif";
    LoadWindowIcon(IconName);
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void LoadWindowIcon(String IconName) {
    WindowIcon = JXMLResource.LoadImageIcon(this,IconName);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setApplication(jfoundation.application.classes.JApplication App) {
    Application = (JBOFApplication)App;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void LoadGUIResource() {

  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Object GetWindowObject() {
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setActiveObject(JActiveObject AO) {
    ActiveObject = AO;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
//  public Object InitChildWindow(JApplication App,JMainWindow MainWindow,Object AddObject,Object AddObject1) {
//    return null;
//  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String getChildWindowName() {
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean Close() {
      return true;
  }
  public void setWindowText(String Text) {
    MainWindow.setTitleAt(this,Text);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setToolTipTextAt(String Text) {
    MainWindow.setToolTipTextAt(this,Text);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String getTitleAt() {
    return null;
  }
  public Icon getIcon() {
    return null;
  }

  protected String title = null;
  public void setTitle(String t) {
    title = t;
  }


  /**
   * 取当前窗口的标题
   * @return String
   */
  public String getTitle(){
    return MainWindow.getTitleAt(this);
  }

  /**
   * 取得日志时间
   */
//  private void getLogDate(){
//    if(JLogManager.isWriteLog()){
//      String date[] = JLogManager.getCurrentDateTime();
//      if(date != null){
//        mStartDate = date[0];
//        mStartTime = date[1];
//      }
//    }
//  }

  /**
   * 记录日志
   * 在窗口关闭时，要记录操作日志
   */
//  private void writeLog(){
//    if(JLogManager.isWriteLog()){
//      String dateTime[] = JLogManager.getCurrentDateTime();
//      String date,time,title;
//      //时间为空时，可能是系统登录窗口
//      if(dateTime == null){
//        return;
//      }
//      date = dateTime[0];
//      time = dateTime[1];
//      title = this.getTitle();
//      JLogManager.writeLog(title,mStartDate,mStartTime,date,time);
//    }
//  }

}
