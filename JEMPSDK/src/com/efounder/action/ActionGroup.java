package com.efounder.action;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.Icon;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ActionGroup extends UpdateAction {
  protected ArrayList aList;
  public static final String DEFAULT_ACTION;// = "DefaultAction";
  public static final String LIST;// = "List";
  public static final String POPUP;// = "Popup";
  protected Vector MenuBarList = new Vector();
  public final void doPerformed(ActionEvent actionevent)
  {
  }
//  public void addMenuBar(ActionMenuBar amb) {
//    MenuBarList.add(amb);
//  }
//  public void removeMenuBar(ActionMenuBar amb) {
//    MenuBarList.remove(amb);
//  }
  public void doUpdate(Object obj)
  {
      boolean flag = isPopup();
      if(flag)
          setEnabled(aList != null && aList.size() > 0);
      else
      if(aList != null)
      {
          for(int i = 0; i < aList.size(); i++)
          {
              Action action = (Action)aList.get(i);
              if(action instanceof UpdateableAction)
                  ((UpdateableAction)action).update(obj);
          }

      }
  }

  public void removeAll()
  {
      if(aList != null)
          aList.clear();
      aList = null;
      firePropertyChange("List", null, null);
      this.setEnabled(false);
  }

  public void remove(Action action)
  {
      if(aList != null)
      {
          aList.remove(action);
          if(aList.size() == 0)
              aList = null;
          firePropertyChange("List", null, null);
      }
  }

  public void add(int i, Action action)
  {
      if(aList == null)
          aList = new ArrayList();
      if(!aList.contains(action))
      {
          aList.add(i, action);
          firePropertyChange("List", null, null);
      }
      this.setEnabled(true);
  }

  public void add(Action action)
  {
    if ( action == null ) return;
      if(aList == null)
          aList = new ArrayList();
      if(!aList.contains(action))
      {
          aList.add(action);
          firePropertyChange("List", null, null);
      }
      this.setEnabled(true);
  }

  public Action[] getActions()
  {
      if(aList == null || aList.size() == 0)
      {
          return UpdateAction.EMPTY_ARRAY;
      } else
      {
          Action aaction[] = new Action[aList.size()];
          aaction = (Action[])aList.toArray(aaction);
          return aaction;
      }
  }

  public Action getAction(int i)
  {
      if(aList != null && i > -1 && i < aList.size())
          return (Action)aList.get(i);
      else
          throw new ArrayIndexOutOfBoundsException(i);
  }

  public int getActionCount()
  {
      return aList == null ? 0 : aList.size();
  }

  public boolean isPopup()
  {
      Object obj = super.getValue("Popup");
      return (obj instanceof Boolean) && ((Boolean)obj).booleanValue();
  }

  public void setPopup(boolean flag)
  {
      super.putValue("Popup", new Boolean(flag));
  }

  public int getDefaultAction()
  {
      Object obj = super.getValue("DefaultAction");
      if(obj instanceof Integer)
          return ((Integer)obj).intValue();
      else
          return -1;
  }

  public void setDefaultAction(int i)
  {
      super.putValue("DefaultAction", new Integer(i));
  }

  public ActionGroup(String s, char c, String s1, Icon icon, boolean flag, String s2)
  {
      super(s, c, s1, icon);
      super.putValue("Popup", new Boolean(flag));
      setAltShortText(s2);
  }

  public ActionGroup(String s, char c, String s1, Icon icon, boolean flag)
  {
      super(s, c, s1, icon);
      super.putValue("Popup", new Boolean(flag));
  }

  public ActionGroup(String s, char c, String s1, Icon icon, String s2)
  {
      super(s, c, s1, icon);
      setAltShortText(s2);
  }

  public ActionGroup(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }

  public ActionGroup(String s, char c, String s1)
  {
      super(s, c, s1);
  }

  public ActionGroup(String s, char c, boolean flag)
  {
      super(s, c);
      super.putValue("Popup", new Boolean(flag));
  }

  public ActionGroup(String s, char c)
  {
      super(s, c);
  }

  public ActionGroup(String s)
  {
      super(s);
  }

  public ActionGroup()
  {
  }

  static
  {
      POPUP = "Popup";
      LIST = "List";
      DEFAULT_ACTION = "DefaultAction";
  }


}
