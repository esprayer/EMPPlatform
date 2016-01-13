package com.efounder.action;

import java.awt.*;
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

public class DelegateAction extends UpdateAction {

  protected Action action;
  public Component lastFocusOwner;
  int width = 120;
//  private static final Profiler a = Profiler.instance("DelegateAction");

  protected Component findFocusOwner(Object obj)
  {
      if(lastFocusOwner != null && !lastFocusOwner.isVisible())
          lastFocusOwner = null;
      Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
      if(component == null || component.getParent() != null && (component.getParent() instanceof JComboBox )) //ActionCombo))
          return lastFocusOwner;
      if((component instanceof JRootPane) && (obj instanceof Component))
          return (Component)obj;
      if(component != null)
          if(!component.isVisible())
              lastFocusOwner = null;
          else
              lastFocusOwner = component;
      return component;
  }

  protected void findAction(Object obj)
  {
      action = null;
      Object obj1 = findFocusOwner(obj);
      if(obj1 instanceof JFrame || obj1 instanceof JDialog )
          obj1 = lastFocusOwner;
      for(; obj1 != null; obj1 = ((Component) (obj1)).getParent())
      {
          if(!(obj1 instanceof DelegateHandler))
              continue;
          Action action1 = ((DelegateHandler)obj1).getAction(this);
          if(action1 != null)
          {
              action = action1;
              return;
          }
      }

      lastFocusOwner = null;
  }

  public void doPerformed(ActionEvent actionevent)
  {
      findAction(actionevent.getSource());
      if(action instanceof UpdateableAction)
      {
//          a.start();
          ((UpdateableAction)action).update(actionevent.getSource());
//          if(a.stopWithStatus(200L))
//              a.println(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf((String)action.getValue("ShortDescription"))))).append(" ").append((String)action.getValue("LongDescription")))));
      }
      if((action instanceof Action) && action.isEnabled())
          action.actionPerformed(actionevent);
  }

  public void doUpdate(Object obj)
  {
      findAction(obj);
      if(action instanceof UpdateableAction)
      {
//          a.start();
          ((UpdateableAction)action).update(obj);
//          if(a.stopWithStatus(200L))
//              a.println(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf((String)action.getValue("ShortDescription"))))).append(" ").append((String)action.getValue("LongDescription")))));
      }
      if(action instanceof Action)
          setEnabled(action.isEnabled());
      else
          setEnabled(false);
  }

  public boolean isEnabled()
  {
    return super.isEnabled();
//      return action == null ? false : action.isEnabled();
  }
  protected boolean editable = true;
  /**
   *
   * @return boolean
   */
  public boolean isEditable() {
    return editable;
  }
  /**
   *
   * @param editable boolean
   */
  public void setEditable(boolean editable) {
    this.editable = editable;
  }
  public Object getValue(String s)
  {
      if(action != null)
      {
          Object obj = action.getValue(s);
          if(obj != null)
              return obj;
      }
      return super.getValue(s);
  }

  public DelegateAction(String s, char c, String s1, Icon icon, String s2)
  {
      super(s, c, s1, icon, s2);
  }

  public DelegateAction(String s, char c, String s1, Icon icon,int width)
  {
      super(s, c, s1, icon);
      this.width = width;
  }
  public int getWidth() {
    return width;
  }
  public DelegateAction(String s, char c, String s1)
  {
      super(s, c, s1);
  }

  public DelegateAction(String s, char c)
  {
      super(s, c);
  }

  public DelegateAction(String s)
  {
      super(s);
  }

  public DelegateAction()
  {
  }
  /**
   *
   * @return ActionWidget
   */
  public ActionWidget getActionComponent() {
    return null;
  }
}
