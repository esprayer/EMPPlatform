package com.efounder.bz.dbform.component.dc.table.render;

import javax.swing.JComponent;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface TitleIcon {
  /**
   *
   * @param dataObject Object
   * @param js int
   * @return Icon
   */
  public javax.swing.Icon getLeafIcon(Object dataObject,int js);
  /**
   *
   * @param dataObject Object
   * @return Icon
   */
  public javax.swing.Icon getOpenIcon(Object dataObject,int js);
  /**
   *
   * @param dataObject Object
   * @return Icon
   */
  public javax.swing.Icon getCloseIcon(Object dataObject,int js);
  /**
   *
   * @param dataObject Object
   * @return String
   */
  public String getTitle(Object dataObject,int js);
  /**
   *
   * @param comp JComponent
   * @param dataObject Object
   */
  public void confing(JComponent comp,Object dataObject,int js);
}
