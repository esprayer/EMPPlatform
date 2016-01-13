package com.efounder.pfc.window;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 3</p>
 * @author not attributable
 * @version 1.0
 */

public interface IWindowCustom {
  public void WindowOpenEvent(Object window,Object o);
  public void WindowCloseEvent(Object window,Object o);
  public void WindowCustomEvent(Object window,Object o);
}
