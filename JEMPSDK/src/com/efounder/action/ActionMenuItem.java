package com.efounder.action;

import javax.swing.*;
import com.efounder.ui.Icons;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.efounder.pub.comp.*;
import java.awt.event.*;
import com.efounder.pfc.window.*;
import java.awt.Component;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ActionMenuItem extends JMenuItem implements DelegateHandler, ActionWidget {
  public static Icon ICON_BLANK = Icons.getBlankIcon(16,16);
  public static Icon ICON_CHECKED = null;
  public static Icon ICON_CHECKED_DISABLED = null;
  protected Action action;
  protected Object source;
  protected static boolean showIcons = true;
  static {
    ICON_CHECKED = (Icon)System.getProperties().get("ICON_CHECKED");
    ICON_CHECKED_DISABLED = (Icon)System.getProperties().get("ICON_CHECKED_DISABLED");
  }
  public Component getActionComponent() {
    return this;
    }
  public static JMenuItem createMenuItem(Object obj, Action action1)
  {
      if(action1 instanceof UpdateableAction)
      {
          ((UpdateableAction)action1).update(obj);
      }
      Object obj1 = null;
      if(action1 instanceof ActionGroup) {
          obj1 = new ActionMenu(obj, (ActionGroup)action1);
      } else
          obj1 = new ActionMenuItem(obj, action1);
      Object obj2 = obj1;
      Object obj3 = action1.getValue("Mnemonic");
      if(obj3 instanceof Character)
          ((AbstractButton) (obj2)).setMnemonic(((Character)obj3).charValue());
      Object obj4 = action1.getValue("ShortDescription");
      if((obj4 instanceof String) && !"".equals(obj4))
          ((AbstractButton) (obj2)).setText("".concat(String.valueOf(String.valueOf(obj4))));
      else
          ((AbstractButton) (obj2)).setText(action1.toString());
      if(action1 instanceof StateAction)
      {
          StateAction stateaction = (StateAction)action1;
          Object obj5 = action1.getValue("SmallIcon");
          Icon actionicon = (obj5 instanceof Icon) ? (Icon)obj5 : ICON_BLANK;
          if(stateaction.getState(obj))
          {
            Icon icon = new CompoundIcon(ICON_CHECKED,actionicon,CompoundIcon.HORIZONTAL,0);
              ((AbstractButton) (obj2)).setIcon(icon);
              ((AbstractButton) (obj2)).setDisabledIcon(Icons.getDisabledIcon(icon));
          } else
          {
              Icon icon = new CompoundIcon(ICON_BLANK,actionicon,CompoundIcon.HORIZONTAL,0);
              ((AbstractButton) (obj2)).setIcon(icon);
              ((AbstractButton) (obj2)).setDisabledIcon(Icons.getDisabledIcon(icon));
          }
      } else
      if(showIcons)
      {
          Object obj5 = action1.getValue("SmallIcon");
          Icon icon = (obj5 instanceof Icon) ? (Icon)obj5 : ICON_BLANK;
          ((AbstractButton) (obj2)).setIcon(icon);
          ((AbstractButton) (obj2)).setDisabledIcon(Icons.getDisabledIcon(icon));
      }
      Object obj6 = action1.getValue("Accelerator");
      if(obj6 instanceof KeyStroke)
          ((JMenuItem) (obj2)).setAccelerator((KeyStroke)obj6);
      ((JMenuItem) (obj2)).setEnabled(action1.isEnabled());
      ActionListener al = new DefaultListener(obj, ((JMenuItem) (obj2)), action1);
      ((AbstractButton) (obj2)).addActionListener(al);
      MenuItemMouseEvent mv = new MenuItemMouseEvent(action1);
      ((JMenuItem) (obj2)).addMouseListener(mv);
      return ((JMenuItem) (obj2));
  }
  public static boolean isShowIcons()
  {
      return showIcons;
  }

  public static void setShowIcons(boolean flag)
  {
      showIcons = flag;
  }

  public Action getAction()
  {
      return action;
  }

  public Object getSource()
  {
      return source;
  }

  public Action getAction(DelegateAction delegateaction)
  {
    return action;
  }
  public ActionMenuItem(Object obj, Action action1)
  {
      source = null;
      action = null;
      source = obj;
      action = action1;
  }
  static class MenuItemMouseEvent extends MouseAdapter {
    protected Action action;
    protected IWindowStatus Status = null;
    public MenuItemMouseEvent(Action action) {
      this.action = action;
      Status = (IWindowStatus)System.getProperties().get("WindowStatus");
    }
    public void mouseExited(MouseEvent mouseevent)
    {
      if ( Status != null ) {
        Status.setText(null);
        Status.setIcon(null);
      }
      //          ((Browser)a).getStatusView().setHintText(null);
    }

    public void mousePressed(MouseEvent mouseevent)
    {
      if ( Status != null ) {
        Status.setText(null);
        Status.setIcon(null);
      }
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
//      public static final String SHORT_DESCRIPTION = "ShortDescription";
//      public static final String LONG_DESCRIPTION = "LongDescription";

      Object obj = action.getValue(Action.LONG_DESCRIPTION);
      Icon icon = (Icon)action.getValue(Action.SMALL_ICON);
      if ( obj == null )
        obj = action.getValue(Action.SHORT_DESCRIPTION);
      if((obj instanceof String) && !"".equals(obj) && Status != null ) {
        Status.setText(obj.toString());
        Status.setIcon(icon);
      }
    }
  }

}
