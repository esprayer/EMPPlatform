package com.efounder.action;

import javax.swing.*;
import com.core.xml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StateActiveObjectAction extends StateAction {
  private String Command = null;
  private Object actionObject = null;
  private Object dataObject   = null;
  public StateActiveObjectAction(Object actionObject,Object dataObject,String Command,String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
      this.Command = Command;
      this.actionObject = actionObject;
      this.dataObject = dataObject;
  }
  /**
   *
   * @param obj Object
   * @return boolean
   */
  public boolean getState(Object obj) {
    Object[] OArray = {actionObject,dataObject,obj,this};
    Boolean b = (Boolean)JBOFClass.CallObjectMethod(actionObject,"getState"+Command,OArray);
    if ( b != null )
      return b.booleanValue();
    return false;
  }
  /**
   *
   * @param obj Object
   * @param flag boolean
   */
  public void setState(Object obj, boolean flag) {
    Object[] OArray = {actionObject,dataObject,obj,this,new Boolean(flag)};
    JBOFClass.VoidCallObjectMethod(actionObject,"setState"+Command,OArray);
  }

}
