package com.efounder.action;

import java.awt.*;
import java.awt.geom.RectangularShape;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import com.efounder.ui.Util;
import com.jidesoft.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ActionPopupMenu extends JidePopupMenu {

  protected boolean boldDefault;
  protected ActionGroup aGroup;
  protected Object source;
  private boolean b;
  private boolean a;

  static void b(ActionPopupMenu actionpopupmenu)
  {
      actionpopupmenu.a();
  }

  static boolean a(ActionPopupMenu actionpopupmenu)
  {
      return actionpopupmenu.a;
  }

  private void a()
  {
      if(!a)
      {
          removeAll();
          ActionMenu.expandMenuGroup(source, this, aGroup, boldDefault);
          if(!ActionMenu.scanForIcons(this))
              ActionMenu.stripIcons(this);
          if(isStripMnemonics())
              ActionMenu.stripMnemonics(this);
          ActionMenu.useAltText(this);
      }
  }

  private void a(Component component, Rectangle rectangle, boolean flag)
  {
      a();
      a = true;
      try
      {
          Dimension dimension = getPreferredSize();
          Point point = component.getLocationOnScreen();
          Rectangle rectangle1 = (Rectangle)rectangle.clone();
          rectangle1.translate(point.x, point.y);
          int i = rectangle1.x;
          int k = rectangle1.y;
          Point point1 = Util.getBestLocation(rectangle1, dimension, flag);
          rectangle.translate(point1.x - i, point1.y - k);
          super.show(component, rectangle.x, rectangle.y);
      }
      finally
      {
          a = false;
      }
  }

  public void show(Component component, Rectangle rectangle)
  {
      a(component, rectangle, false);
  }

  public void show(Component component, int i, int k)
  {
      Rectangle rectangle = null;
      boolean flag = false;
      if(component instanceof ActionButton)
      {
          rectangle = new Rectangle(0, 0, component.getWidth(), component.getHeight());
          flag = true;
      } else
      {
          rectangle = new Rectangle(i, k, 1, 1);
      }
      a(component, rectangle, flag);
  }

  public boolean isBoldDefaultAction()
  {
      return boldDefault;
  }

  public void setBoldDefaultAction(boolean flag)
  {
      boldDefault = flag;
  }

  public boolean isStripMnemonics()
  {
      return b;
  }

  public ActionGroup getGroup()
  {
      return aGroup;
  }

  public Action getAction()
  {
      return getGroup();
  }

  public Object getSource()
  {
      return source;
  }

  public Dimension getPreferredSize()
  {
      a();
      Dimension dimension = super.getPreferredSize();
      return dimension;
  }

  public ActionPopupMenu(Object obj, ActionGroup actiongroup, boolean flag)
  {
      a = false;
      source = null;
      aGroup = null;
      boldDefault = false;
      source = obj;
      aGroup = actiongroup;
      b = flag;
      addPopupMenuListener(new j((ActionPopupMenu)this));
  }

  public ActionPopupMenu(Object obj, ActionGroup actiongroup)
  {
      this(obj, actiongroup, true);
  }
  class j
      implements PopupMenuListener
  {

      private final ActionPopupMenu a; /* synthetic field */

      public void popupMenuCanceled(PopupMenuEvent popupmenuevent)
      {
      }

      public void popupMenuWillBecomeInvisible(PopupMenuEvent popupmenuevent)
      {
          a(a);
          a.removeAll();
      }

      public void popupMenuWillBecomeVisible(PopupMenuEvent popupmenuevent)
      {
          ActionPopupMenu.b(a);
      }

      private void a(Container container)
      {
          if(container instanceof ActionMenu)
          {
              ActionMenu actionmenu = (ActionMenu)container;
              if(actionmenu.aGroup != null)
                  actionmenu.aGroup.removePropertyChangeListener(actionmenu);
          }
          java.awt.Component acomponent[] = container.getComponents();
          for(int i = 0; i < acomponent.length; i++)
              if(acomponent[i] instanceof ActionMenu)
                  a(((Container) ((ActionMenu)acomponent[i])));

      }

      j(ActionPopupMenu actionpopupmenu)
      {
          a = actionpopupmenu;
      }
  }

}
