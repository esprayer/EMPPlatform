package com.efounder.eai.ui.action;

import com.efounder.action.*;
import javax.swing.*;
import com.core.xml.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class ObjectStateAction extends StateAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.Res");
  private String Command = null;
  private Object actionObject = null;
  public ObjectStateAction(Object object,String Command,String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
      this.Command = Command;
      actionObject = object;
  }
  /**
   *
   * @param obj Object
   * @return boolean
   */
  public boolean getState(Object obj) {
    Object[] OArray = {actionObject,obj,this};
    Boolean b = (Boolean)JBOFClass.CallObjectMethod(actionObject,res.getString("getState")+Command,OArray);
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
    Object[] OArray = {actionObject,obj,this,new Boolean(flag)};
    JBOFClass.VoidCallObjectMethod(actionObject,res.getString("setState")+Command,OArray);
  }

}
