package com.efounder.action;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.JMenuItem;
import com.jidesoft.swing.JideMenu;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ActionMenu extends JideMenu implements DelegateHandler, PropertyChangeListener, ActionWidget {
  private static final String g = " .\"'()";
  private static boolean d = System.getProperties().getProperty("borland.menu.focus.refocus.fix", "1").equals("1");
  private static boolean f = System.getProperties().getProperty("borland.menu.focus.delegate.fix", "1").equals("1");
  private static boolean e = System.getProperties().getProperty("borland.menu.focus.always.fix", "0").equals("1");

//  private static Node b;
  static Component a;

  protected ActionGroup aGroup;
  protected Object source;

  static boolean c()
  {
      return d;
  }
  public Component getActionComponent() {
    return this;
  }
//  static Node a(Node node)
//  {
//      return b = node;
//  }

  static boolean b()
  {
      return e;
  }

  static boolean a()
  {
      return f;
  }


  public ActionMenu() {
      super();
  }
  public ActionMenu(Object obj, ActionGroup actiongroup)
  {
      source = null;
      aGroup = null;
      source = obj;
      aGroup = actiongroup;
      String s = aGroup.getShortText();
      setText(s == null || "".equals(s) ? aGroup.toString() : s);
      setMnemonic(aGroup.getMnemonic());
      setEnabled(aGroup.isEnabled());
      if(ActionMenuItem.isShowIcons())
      {
          javax.swing.Icon icon = aGroup.getSmallIcon();
          setIcon(icon);
      }
      addMenuListener(new ActionMenuEvent((ActionMenu)this, obj));
      aGroup.addPropertyChangeListener(this);
  }
  public void propertyChange(PropertyChangeEvent propertychangeevent)
  {
      String s = propertychangeevent.getPropertyName();
      ActionMenu _tmp = this;
      if("ShortDescription".equals(s))
      {
          String s1 = aGroup.getShortText();
          setText(s1 == null || "".equals(s1) ? aGroup.toString() : s1);
      } else
      if("enabled".equals(s))
      {
          setEnabled(aGroup.isEnabled());
      } else
      {
          ActionMenu _tmp1 = this;
          if("Mnemonic".equals(s))
          {
              setMnemonic(aGroup.getMnemonic());
          } else
          {
              ActionMenu _tmp2 = this;
              if("SmallIcon".equals(s))
              {
                  setIcon(aGroup.getSmallIcon());
              } else
              {
                  ActionMenu _tmp3 = this;
                  if("List".equals(s))
                      setEnabled(aGroup.getActionCount() > 0);
              }
          }
      }
  }

  protected Point getPopupMenuOrigin()
   {
       Point point = super.getPopupMenuOrigin();
       Container container = getParent();
       if(!(container instanceof JPopupMenu))
       {
           Window window = null;
           do
           {
               if(container == null)
                   break;
               if(container instanceof Window)
               {
                   window = (Window)container;
                   break;
               }
               container = container.getParent();
           } while(true);
           Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
           Insets insets = new Insets(50, 50, 50, 50);
           if(window != null)
           {
               GraphicsConfiguration graphicsconfiguration = window.getGraphicsConfiguration();
               dimension = graphicsconfiguration.getBounds().getSize();
               insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsconfiguration);
           }
           double d1 = (double)dimension.width / (double)dimension.height;
           if(d1 > 2D)
               dimension.width = dimension.width / 2;
           Dimension dimension1 = getPopupMenu().getSize();
           Point point1 = getLocationOnScreen();
           Dimension dimension2 = getSize();
           if(point.y > 0)
           {
               if(point1.y + dimension1.height + point.y + insets.bottom > dimension.height)
               {
                   point.y = -dimension1.height;
                   if(insets.top > point1.y - dimension1.height)
                   {
                       point.y = insets.top - point1.y;
                       point.x += point1.x + dimension1.width + insets.left <= dimension.width ? dimension2.width : point.x != 0 ? -dimension2.width : -dimension1.width;
                   }
               }
           } else
           if(insets.top > point1.y - dimension1.height)
           {
               point.y = insets.top - point1.y;
               point.x += point1.x + dimension1.width + insets.left <= dimension.width ? dimension2.width : point.x != 0 ? -dimension2.width : -dimension1.width;
           }
       }
       return point;
   }
   public Action getAction(DelegateAction delegateaction)
   {
     return null;
   }
   public ActionGroup getGroup()
   {
       return aGroup;
   }
   public Object getSource()
   {
     return source;
   }
   public Action getAction()
   {
       return getGroup();
   }
   private static void update(ActionGroup actiongroup, Object obj)
    {
      actiongroup.update(obj);
    }

    private static void createActionMenu(Object obj, Container container, ActionGroup actiongroup, boolean flag)
    {
      if ( actiongroup == null || actiongroup.aList == null ) return;
        Object aaction[] = actiongroup.aList.toArray();// PersonalityManager.filterActionGroupByPersonality(actiongroup);
        int i = actiongroup.getDefaultAction();
        boolean flag1 = false;
        for(int j = 0; j < aaction.length; j++)
        {
            if((aaction[j] instanceof ActionGroup) && !((ActionGroup)aaction[j]).isPopup() )
            {
                update((ActionGroup)aaction[j], obj);
                // ���û��Action
                if ( ((ActionGroup)aaction[j]).getActionCount() == 0 ) {
                  continue;
                }
                if(j > 0 && !flag1)
                    if(container instanceof JMenu)
                        ((JMenu)container).addSeparator();
                    else
                    if(container instanceof JPopupMenu)
                        ((JPopupMenu)container).addSeparator();
                createActionMenu(obj, container, (ActionGroup)aaction[j], flag);
                continue;
            }

            JMenuItem jmenuitem = ActionMenuItem.createMenuItem(obj, (Action)aaction[j]);
            if(flag && j == i)
            {
                Font font = jmenuitem.getFont();
                if(!font.isBold())
                    jmenuitem.setFont(new Font(font.getName(), 1, font.getSize()));
                else
                if(!font.isItalic())
                    jmenuitem.setFont(new Font(font.getName(), 3, font.getSize()));
            }
            container.add(jmenuitem);
            flag1 = false;
        }

    }

    public static void expandMenuGroup(Object obj, Container container, ActionGroup actiongroup, boolean flag)
    {
        update(actiongroup, obj);
        createActionMenu(obj, container, actiongroup, flag);
    }

    public static void useAltText(Container container)
    {
        Component acomponent[] = (container instanceof JMenu) ? ((JMenu)container).getMenuComponents() : container.getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            if(!(acomponent[i] instanceof ActionMenuItem))
                continue;
            Action action = ((ActionMenuItem)acomponent[i]).getAction();
            if(!(action instanceof UpdateAction))
                continue;
            String s = ((UpdateAction)action).getAltShortText();
            if(s != null)
                ((JMenuItem)acomponent[i]).setText(s);
        }

    }

    public static void stripMnemonics(Container container)
    {
        Component acomponent[] = (container instanceof JMenu) ? ((JMenu)container).getMenuComponents() : container.getComponents();
        for(int i = 0; i < acomponent.length; i++)
            if(acomponent[i] instanceof JMenuItem)
              ((JMenuItem)acomponent[i]).setMnemonic(0);
    }

    public static void stripIcons(Container container)
    {
        Component acomponent[] = (container instanceof JMenu) ? ((JMenu)container).getMenuComponents() : container.getComponents();
        for(int i = 0; i < acomponent.length; i++)
            if(acomponent[i] instanceof JMenuItem)
                ((JMenuItem)acomponent[i]).setIcon(null);

    }

    public static boolean scanForIcons(Container container)
    {
        Component acomponent[] = (container instanceof JMenu) ? ((JMenu)container).getMenuComponents() : container.getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            if(!(acomponent[i] instanceof JMenuItem))
                continue;
            javax.swing.Icon icon = ((JMenuItem)acomponent[i]).getIcon();
            if(icon != null && icon != ActionMenuItem.ICON_BLANK)
                return true;
        }

        return false;
    }
    class ActionMenuEvent implements MenuListener
    {
      ActionMenu b;
      Object a;
      public ActionMenuEvent(ActionMenu bb,Object aa) {
        b = bb;
        a = aa;
      }
        void a()
        {
            if(ActionMenu.c() && (ActionMenu.b() || false )) //|| Platform.IBM_LINUX))
                SwingUtilities.invokeLater(new v());
        }

        void b()
        {
            if(ActionMenu.a() && (ActionMenu.b() || false )) //|| Platform.IBM_LINUX))
            {
                Component component = a(a);
                if(component != null && !(component instanceof ActionMenu))
                {
                    ActionMenu.a = component;
//                    ActionMenu.a(Browser.getActiveBrowser().getActiveNode());
                }
            }
        }

        Component a(Object obj)
        {
            if(obj instanceof Component)
            {
                for(Object obj1 = (Component)obj; obj1 != null; obj1 = ((Component) (obj1)).getParent())
                    if(obj1 instanceof Window)
                        return ((Window)obj1).getFocusOwner();

            }
            return null;
        }

        void c()
        {
            Component acomponent[] = b.getMenuComponents();
            for(int i = 0; i < acomponent.length; i++)
                if(acomponent[i] instanceof ActionMenu)
                {
                    ActionMenu actionmenu = (ActionMenu)acomponent[i];
                    ActionGroup actiongroup = actionmenu.getGroup();
                    actiongroup.removePropertyChangeListener(actionmenu);
                    actionmenu.setUI(null);
                }

        }

        public void menuCanceled(MenuEvent menuevent)
        {
            c();
            b.removeAll();
        }

        public void menuDeselected(MenuEvent menuevent)
        {
            c();
            b.removeAll();
            a();
        }

        public void menuSelected(MenuEvent menuevent)
        {
            b();
            ActionMenu.expandMenuGroup(a, b, b.aGroup, false);
            ActionPopupMenu actionpopupmenu = a(b.getParent());
            if(actionpopupmenu != null && actionpopupmenu.isStripMnemonics())
                ActionMenu.stripMnemonics(b);
            if(!ActionMenu.scanForIcons(b))
                ActionMenu.stripIcons(b);
        }

        private ActionPopupMenu a(Container container)
        {
            for(; container != null; container = container.getParent())
                if(container instanceof ActionPopupMenu)
                    return (ActionPopupMenu)container;

            return null;
        }


// Unreferenced inner classes:

/* anonymous class */
        class v
            implements Runnable
        {

            public void run()
            {
//                Node node = Browser.getActiveBrowser().getActiveNode();
//                if(node != null && node.equals(ActionMenu.d()) && ActionMenu.a != null && (ActionMenu.a instanceof JComponent))
//                    ((JComponent)ActionMenu.a).requestFocus();
            }


// Unreferenced inner classes:
            /* anonymous class not found */
            class _anm1 {}

        }

    }

}
