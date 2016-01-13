package jbof.gui.window.classes;

import java.awt.*;

import javax.swing.*;

import com.efounder.eai.application.classes.JBOFApplication;

import jbof.application.classes.*;
import jfoundation.gui.window.classes.*;

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
public class JBOFMainWindow extends JMainWindow {
  public JBOFApplication          Application=null;
  public Object                   ParentObject=null;
  public int WaitCount     = 0;
  public int DefaultCursor = 0;

  public ImageIcon                WindowIcon = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JBOFMainWindow() {
  }


  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JBOFChildWindow GetCurrentWindow() {
    return null;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------

  public void setActiveWindow(JChildWindow cw) {

  }

  public JBOFChildWindow GetChildWindow(int Index) {
    return null;
  }


}
