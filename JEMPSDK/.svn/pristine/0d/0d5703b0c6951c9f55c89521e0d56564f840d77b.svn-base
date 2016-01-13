package com.efounder.ui;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Util {
      public static final int MARGIN_SPACE = 6;

      public static void expandAllChildren(JTree jtree, DefaultMutableTreeNode defaultmutabletreenode)
      {
          Enumeration enumeration = defaultmutabletreenode.depthFirstEnumeration();
          do
          {
              if(!enumeration.hasMoreElements())
                  break;
              DefaultMutableTreeNode defaultmutabletreenode1 = (DefaultMutableTreeNode)enumeration.nextElement();
              if(defaultmutabletreenode1.isLeaf())
                  jtree.expandPath(new TreePath(((DefaultMutableTreeNode)defaultmutabletreenode1.getParent()).getPath()));
          } while(true);
      }

      public static void collapseAllChildren(JTree jtree, DefaultMutableTreeNode defaultmutabletreenode)
      {
          Enumeration enumeration = defaultmutabletreenode.depthFirstEnumeration();
          do
          {
              if(!enumeration.hasMoreElements())
                  break;
              DefaultMutableTreeNode defaultmutabletreenode1 = (DefaultMutableTreeNode)enumeration.nextElement();
              if(!defaultmutabletreenode1.isLeaf())
                  jtree.collapsePath(new TreePath(defaultmutabletreenode1.getPath()));
          } while(true);
      }

      public static String getActionKeyStrokeString(Action action)
      {
          Object obj = action.getValue("Accelerator");
          String s = "";
          if(obj instanceof KeyStroke)
          {
              KeyStroke keystroke = (KeyStroke)obj;
              StringBuffer stringbuffer = new StringBuffer("");
              if(keystroke != null)
              {
                  String s1 = KeyEvent.getKeyModifiersText(keystroke.getModifiers());
                  if(s1 != null && s1.length() > 0)
                      stringbuffer.append(s1).append("+");
                  stringbuffer.append(KeyEvent.getKeyText(keystroke.getKeyCode()));
                  s = stringbuffer.toString();
              }
          }
          return s;
      }

      public static void paintGradientBackground2(Graphics g, Component component)
      {
          Graphics2D graphics2d = (Graphics2D)g;
          Color color = component.getBackground();
          Color color1 = new Color(Math.min(color.getRed() + 30, 255), Math.min(color.getGreen() + 30, 255), Math.min(color.getBlue() + 30, 255));
          GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, color, 0.0F, component.getHeight(), color1);
          graphics2d.setPaint(gradientpaint);
          try
          {
              graphics2d.fillRect(0, 0, component.getWidth(), component.getHeight());
          }
          catch(InternalError internalerror) { }
      }

      public static void paintGradientBackground(Graphics g, Component component)
      {
          Graphics2D graphics2d = (Graphics2D)g;
          Color color = component.getBackground();
          Color color1 = new Color(Math.min(color.getRed() + 20, 255), Math.min(color.getGreen() + 20, 255), Math.min(color.getBlue() + 20, 255));
          Color color2 = new Color(Math.max(color.getRed() - 20, 0), Math.max(color.getGreen() - 20, 0), Math.max(color.getBlue() - 20, 0));
          GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, color1, 0.0F, component.getHeight(), color2);
          graphics2d.setPaint(gradientpaint);
          try
          {
              graphics2d.fillRect(0, 0, component.getWidth(), component.getHeight());
          }
          catch(InternalError internalerror) { }
      }

      public static Frame findFrame(Component component)
      {
          Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
          if(window instanceof Frame)
              return (Frame)window;
          for(; component != null; component = component.getParent())
              if(component instanceof Window)
              {
                  for(Window window1 = (Window)component; window1 != null; window1 = window1.getOwner())
                      if(window1 instanceof Frame)
                          return (Frame)window1;

                  return null;
              }

          return null;
      }

      public static Window findWindow(Component component)
      {
          Object obj;
          for(obj = component; !(obj instanceof Frame) && !(obj instanceof Window) && obj != null; obj = ((Component) (obj)).getParent());
          return (Window)obj;
      }

      public static String[] updateHistoryArray(String as[], String s, int i)
      {
          java.util.List list = Arrays.asList(as);
          ArrayList arraylist = new ArrayList(list);
          arraylist.remove(s);
          arraylist.add(0, s);
          if(arraylist.size() > i)
              arraylist.remove(i);
          String as1[] = new String[arraylist.size()];
          return (String[])arraylist.toArray(as1);
      }

      public static Image makeIconImage(Icon icon)
      {
          if(icon == null)
              icon = Icons.getBlankIcon(16, 16);
          BufferedImage bufferedimage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
          icon.paintIcon(null, bufferedimage.getGraphics(), 0, 0);
          return bufferedimage;
      }

      public static Rectangle getWindowRectangle(Window window)
      {
          GraphicsConfiguration graphicsconfiguration = window.getGraphicsConfiguration();
          Rectangle rectangle = graphicsconfiguration.getBounds();
          Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsconfiguration);
          rectangle.x += insets.left;
          rectangle.y += insets.top;
          rectangle.width -= insets.left + insets.right;
          rectangle.height -= insets.top + insets.bottom;
          return rectangle;
      }

      public static Window[] findWindowsOnDevice(Window window, Window awindow[])
      {
          ArrayList arraylist = new ArrayList();
          String s = window.getGraphicsConfiguration().getDevice().getIDstring();
          for(int i = 0; i < awindow.length; i++)
              if(awindow[i].getGraphicsConfiguration().getDevice().getIDstring().equals(s))
                  arraylist.add(awindow[i]);

          return (Window[])arraylist.toArray(new Window[arraylist.size()]);
      }

      public static void invokeAndWaitOnSwingThread(Runnable runnable)
          throws InvocationTargetException, InterruptedException
      {
          if(SwingUtilities.isEventDispatchThread())
              runnable.run();
          else
              SwingUtilities.invokeAndWait(runnable);
      }

      public static void invokeOnSwingThread(Runnable runnable)
      {
          if(SwingUtilities.isEventDispatchThread())
              runnable.run();
          else
              SwingUtilities.invokeLater(runnable);
      }

      private static Point a(Rectangle rectangle, Rectangle rectangle1)
      {
          Point point = new Point(rectangle.getLocation());
          if(point.x + rectangle.width > rectangle1.x + rectangle1.width)
              point.x = (rectangle1.x + rectangle1.width) - rectangle.width;
          if(point.y + rectangle.height > rectangle1.y + rectangle1.height)
              point.y = (rectangle1.y + rectangle1.height) - rectangle.height;
          if(point.x < rectangle1.x)
              point.x = rectangle1.x;
          if(point.y < rectangle1.y)
              point.y = rectangle1.y;
          return point;
      }

      public static Point getBestLocation(Rectangle rectangle)
      {
          GraphicsDevice agraphicsdevice[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
          Point point = rectangle.getLocation();
          double d = 1.7976931348623157E+308D;
          for(int i = 0; i < agraphicsdevice.length; i++)
          {
              GraphicsConfiguration graphicsconfiguration = agraphicsdevice[i].getDefaultConfiguration();
              Rectangle rectangle1 = (Rectangle)graphicsconfiguration.getBounds().clone();
              Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsconfiguration);
              rectangle1.setBounds(rectangle1.x + insets.left, rectangle1.y + insets.top, rectangle1.width - (insets.left + insets.right), rectangle1.height - (insets.top + insets.bottom));
              Point point1 = a(rectangle, rectangle1);
              double d1 = point1.distance(rectangle.getLocation());
              if(d1 < d)
              {
                  point = point1;
                  d = d1;
              }
          }

          return point;
      }

      private static Rectangle a(Rectangle rectangle, Dimension dimension, int i, int j)
      {
          Rectangle rectangle1 = new Rectangle(rectangle.x + i, rectangle.y + j, dimension.width, dimension.height);
          Point point = getBestLocation(rectangle1);
          rectangle1.setLocation(point.x, point.y);
          return rectangle1;
      }

      public static Point getBestLocation(Rectangle rectangle, Dimension dimension, boolean flag)
      {
          Rectangle rectangle1 = a(rectangle, dimension, 0, rectangle.height);
          if(!rectangle1.intersects(rectangle))
              return rectangle1.getLocation();
          Rectangle rectangle2 = null;
          if(flag)
          {
              rectangle2 = a(rectangle, dimension, 0, -dimension.height);
              if(!rectangle2.intersects(rectangle))
                  return rectangle2.getLocation();
          }
          rectangle2 = a(rectangle, dimension, rectangle.width, 0);
          if(!rectangle2.intersects(rectangle))
              return rectangle2.getLocation();
          rectangle2 = a(rectangle, dimension, -dimension.width, 0);
          if(!rectangle2.intersects(rectangle))
              return rectangle2.getLocation();
          if(!flag)
          {
              Rectangle rectangle3 = a(rectangle, dimension, 0, -dimension.height);
              if(!rectangle3.intersects(rectangle))
                  return rectangle3.getLocation();
          }
          return rectangle1.getLocation();
      }

      public static boolean isKeyPending()
      {
          EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
          return eventqueue.peekEvent(400) != null || eventqueue.peekEvent(401) != null;
      }



      public static boolean checkHasFocus(Container container)
      {
          Component acomponent[] = container.getComponents();
          for(int i = 0; i < acomponent.length; i++)
          {
              if((acomponent[i] instanceof Container) && checkHasFocus((Container)acomponent[i]))
                  return true;
              if(acomponent[i].hasFocus())
                  return true;
          }

          return false;
      }

      public static Component getFocusedComponent()
      {
          Frame frame = getFocusedFrame();
          if(frame == null)
              return null;
          else
              return frame.getFocusOwner();
      }

      public static Frame getFocusedFrame()
      {
          Frame aframe[] = Frame.getFrames();
          for(int i = 0; i < aframe.length; i++)
          {
              Frame frame = aframe[i];
              if(frame.isVisible() && frame.getFocusOwner() != null)
                  return frame;
          }

          return null;
      }

      private static boolean a(Component component, Cursor cursor, Cursor cursor1)
      {
          if(component.getCursor() == cursor)
          {
              component.setCursor(cursor1);
              return true;
          } else
          {
              return false;
          }
      }

      public static void setGlobalCursor(Cursor cursor, Cursor cursor1)
      {
          Frame aframe[] = Frame.getFrames();
          for(int i = 0; i < aframe.length; i++)
          {
              Frame frame = aframe[i];
              a(frame, cursor, cursor1);
              Window awindow[] = frame.getOwnedWindows();
              for(int j = 0; j < awindow.length; j++)
              {
                  Window window = awindow[j];
                  a(window, cursor, cursor1);
              }

          }

      }

      public static Border createDefaultTitledBorder(String s)
      {
          return BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(s), createDefaultEmptyBorder());
      }

      public static Border createDefaultEmptyBorder()
      {
          return BorderFactory.createEmptyBorder(6, 6, 6, 6);
      }



      public static JDialog getJDialogAncestor(Component component)
      {
          for(Container container = component.getParent(); container != null; container = container.getParent())
              if(container instanceof JDialog)
                  return (JDialog)container;

          return null;
      }

      public static JButton findButtonNamed(Container container, String s)
      {
          Component acomponent[] = container.getComponents();
          for(int i = 0; i < acomponent.length; i++)
          {
              if(acomponent[i] instanceof JButton)
              {
                  JButton jbutton = (JButton)acomponent[i];
                  if(jbutton.getText().equals(s))
                      return jbutton;
                  continue;
              }
              if(!(acomponent[i] instanceof Container))
                  continue;
              JButton jbutton1 = findButtonNamed((Container)acomponent[i], s);
              if(jbutton1 != null)
                  return jbutton1;
          }

          return null;
      }

      public static void dumpComponentHierarchy(Component component)
      {
          for(; component != null; component = component.getParent())
              System.out.print(String.valueOf(String.valueOf(component.getClass().getName())).concat(" <-\n  "));

          System.out.println("<null>");
      }

      private static Component a(Window window)
      {
          Component component = null;
          if(window.isVisible())
              component = SwingUtilities.findFocusOwner(window);
          if(component == null)
          {
              Window awindow[] = window.getOwnedWindows();
              for(int i = 0; component == null && i < awindow.length; i++)
                  component = a(awindow[i]);

          }
          return component;
      }

      public static Component getApplicationFocus()
      {
          Component component = null;
          Frame aframe[] = Frame.getFrames();
          for(int i = 0; component == null && i < aframe.length; i++)
              component = a(aframe[i]);

          return component;
      }

      public Util()
      {
      }

  }
