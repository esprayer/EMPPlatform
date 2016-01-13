package com.efounder.action;

import java.awt.event.*;
import javax.swing.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class StateAction extends UpdateAction {
  public static final String GROUPED;// = "Grouped";

  public abstract boolean getState(Object obj);

  public abstract void setState(Object obj, boolean flag);

  public final void doPerformed(ActionEvent actionevent)
  {
      setState(actionevent.getSource(), !getState(actionevent.getSource()));
  }

  public boolean isGrouped()
  {
      Object obj = super.getValue("Grouped");
      return (obj instanceof Boolean) && ((Boolean)obj).booleanValue();
  }

  public void setGrouped(boolean flag)
  {
      super.putValue("Grouped", new Boolean(flag));
  }

  public StateAction(String s, char c, String s1, Icon icon, boolean flag)
  {
      super(s, c, s1, icon);
      super.putValue("Grouped", new Boolean(flag));
  }

  public StateAction(String s, char c, String s1, Icon icon, Icon icon1)
  {
      super(s, c, s1, icon, icon1);
  }

  public StateAction(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }

  public StateAction(String s, char c, String s1)
  {
      super(s, c, s1);
  }

  public StateAction(String s, char c)
  {
      super(s, c);
  }

  public StateAction(String s)
  {
      super(s);
  }

  public StateAction()
  {
  }

  static
  {
      GROUPED = "Grouped";
  }


}
