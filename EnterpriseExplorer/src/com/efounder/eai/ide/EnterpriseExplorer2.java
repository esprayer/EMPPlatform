package com.efounder.eai.ide;

import java.awt.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import com.core.xml.*;


/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class EnterpriseExplorer2 extends EnterpriseExplorer {

  public EnterpriseExplorer2() {
    super();
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void initComp() {

  }
  void jbInit() throws Exception {

  }
  void initMenubar() {
  }
  protected Class callBackWindowClazz = null;
  //----------------------------------------------------------------------------------------------
  //描述: 打开一个对象窗口
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Component OpenObjectWindow(String title,Icon icon, String tip,Component comp) {
    if ( callBackWindowClazz == null ) callBackWindowClazz = (Class)System.getProperties().get("fwkViewCallback");
    if ( callBackWindowClazz == null ) return null;
    Object[] OArray = {title,icon,tip,comp};
    return (Component)JBOFClass.CallClassMethod(callBackWindowClazz,"callBackOpenObjectWindow",OArray);
  }
  //----------------------------------------------------------------------------------------------
  //描述: 关闭一个对象窗口
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean CloseObjectWindow(Component comp) {
    if ( callBackWindowClazz == null ) return false;
    Object[] OArray = {comp};
    JBOFClass.CallClassMethod(callBackWindowClazz,"callBackCloseObjectWindow",OArray);
    return true;
  }
}
