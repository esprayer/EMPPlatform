package com.efounder.pfc.window;

import javax.swing.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class WindowGroup {
  private String groupCaption = null;
  private String groupID = null;
  private Icon groupIcon = null;
  public WindowGroup() {
  }
  /**
   *
   * @return List
   */
  public java.util.List getWindowList() {
    return null;
  }
  /**
   *
   * @param groupCaption String
   */
  public void setGroupCaption(String groupCaption) {
    this.groupCaption = groupCaption;
  }
  /**
   *
   * @param groupID String
   */
  public void setGroupID(String groupID) {
    this.groupID = groupID;
  }
  /**
   *
   * @param groupIcon Icon
   */
  public void setGroupIcon(Icon groupIcon) {
    this.groupIcon = groupIcon;
  }
  /**
   *
   * @return String
   */
  public String getGroupCaption() {
    return groupCaption;
  }
  /**
   *
   * @return String
   */
  public String getGroupID() {
    return groupID;
  }
  /**
   *
   * @return Icon
   */
  public Icon getGroupIcon() {
    return groupIcon;
  }
}
