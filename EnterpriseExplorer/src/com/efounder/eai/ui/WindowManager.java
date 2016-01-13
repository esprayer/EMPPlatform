package com.efounder.eai.ui;

import java.util.*;
import com.efounder.pfc.window.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class WindowManager {
  protected static Hashtable windowHashtable = new Hashtable();
  protected static Hashtable viewHashtable   = new Hashtable();
  /**
   *
   * @param Key Object
   * @param view IView
   */
  public static void registryView(Object Key,IView view) {
    if ( Key != null )
      viewHashtable.put(Key,view);
  }
  public static IView unregistryView(Object Key) {
    if ( Key != null ) {
      return (IView)viewHashtable.remove(Key);
    }
    return null;
  }
  /**
   *
   * @param Key Object
   * @return IView
   */
  public static IView findView(Object Key) {
    IView view = null;
    if ( Key != null ) return null;
    view = (IView)viewHashtable.get(Key);
    return view;
  }
  /**
   *
   * @param Key Object
   * @param window IWindow
   */
  public static void registryWindow(Object Key,IWindow window) {
//    if ( Key != null )
//      windowHashtable.put(Key,window);
  }
  public static IWindow unregistryWindow(Object Key) {
//    if ( Key != null ) {
//      return (IWindow)windowHashtable.remove(Key);
//    }
    return null;
  }
  /**
   *
   * @param Key Object
   * @return IWindow
   */
  public static IWindow findWindow(Object Key) {
    IWindow window = null;
    if ( Key != null ) return null;
    window = (IWindow)windowHashtable.get(Key);
    return window;
  }
  public WindowManager() {
  }

}
