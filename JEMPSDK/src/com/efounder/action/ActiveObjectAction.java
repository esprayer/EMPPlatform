package com.efounder.action;

import java.awt.event.*;
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

public class ActiveObjectAction extends UpdateAction {
  private String Command = null;
  private Object actionObject = null;
  private Object dataObject   = null;
  public Object getDataObject() {
    return dataObject;
  }

  public String getCommand() {
    return Command;
  }

  public Object getActionObject() {
    return actionObject;
  }

  public final void doUpdate(Object obj)
  {
    if(actionObject != null)
      doUpdate();
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate()
  {
      Object[] OArray = {actionObject,dataObject,this};
      Boolean b = (Boolean)JBOFClass.CallObjectMethod(actionObject,"update"+Command,OArray);
      if ( b != null )
        this.setEnabled(b.booleanValue());
  }
  /**
   *
   * @param actionevent ActionEvent
   */
  public final void doPerformed(ActionEvent actionevent)
  {
    Object[] OArray = {actionObject,dataObject,this,actionevent};
    JBOFClass.VoidCallObjectMethod(actionObject,Command,OArray);
  }
  /**
   *
   * @param browser EnterpriseExplorer
   */
  public void actionPerformed() {
    Object[] OArray = {actionObject,dataObject,this};
    JBOFClass.VoidCallObjectMethod(actionObject,Command,OArray);
  }

  /**
   *
   * @param object Object
   * @param Command String
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public ActiveObjectAction(Object actionObject,Object dataObject,String Command,String s, char c, String s1, Icon icon) {
      super(s, c, s1, icon);
      this.Command = Command;
      this.actionObject = actionObject;
      this.dataObject = dataObject;
  }

}
