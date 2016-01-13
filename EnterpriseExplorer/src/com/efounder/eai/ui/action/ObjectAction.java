package com.efounder.eai.ui.action;

import com.efounder.eai.ide.*;
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

public class ObjectAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.Res");
  private String Command = null;
  private Object actionObject = null;
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer)
  {
      Object[] OArray = {explorer,actionObject,this};
      JBOFClass.VoidCallObjectMethod(actionObject,res.getString("Update")+Command,OArray);
  }
  /**
   *
   * @param browser EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer browser) {
    Object[] OArray = {browser,actionObject,this};
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
  public ObjectAction(Object object,String Command,String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
      this.Command = Command;
      actionObject = object;
  }

}
