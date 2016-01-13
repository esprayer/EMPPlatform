package com.efounder.action;

import com.core.xml.*;
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
public class ActionStub extends StubObject {
  protected boolean startShow = false;
  /**
   *
   * @param ID String
   * @param caption String
   * @param action Action
   * @return ActionStub
   */
  public static ActionStub getActionStub(String ID,String caption,Action action) {
    return getActionStub(ID,caption,action,false);
  }
  /**
   *
   * @return boolean
   */
  public boolean isStartShow() {
    return startShow;
  }
  /**
   *
   * @param ID String
   * @param caption String
   * @param action Action
   * @return ActionStub
   */
  public static ActionStub getActionStub(String ID,String caption,Action action,boolean startShow) {
    ActionStub actionStub = new ActionStub();
    actionStub.setID(ID);actionStub.setCaption(caption);
    actionStub.setObject("action",action);
    actionStub.startShow = startShow;
    return actionStub;
  }
  /**
   *
   */
  protected ActionStub() {
  }
}
