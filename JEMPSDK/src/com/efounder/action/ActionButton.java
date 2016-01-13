package com.efounder.action;

import java.beans.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.efounder.ui.*;
import com.efounder.pfc.window.*;
import com.jidesoft.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ActionButton extends JideButton implements ActionWidget {
  public class ActionPropertyChangeListener
      implements PropertyChangeListener
  {
    ActionButton a;
      public void propertyChange(PropertyChangeEvent propertychangeevent)
      {
          String s = propertychangeevent.getPropertyName();
          if("ShortDescription".equals(s))
          {
              String s1 = (String)propertychangeevent.getNewValue();
              a.setShortText(s1 == null || "".equals(s1) ? a.action.toString() : s1);
          } else
          if("AltShortDescription".equals(s))
          {
              String s2 = (String)propertychangeevent.getNewValue();
              a.setAltShortText(s2 == null || "".equals(s2) ? a.action.toString() : s2);
          } else
          if("LongDescription".equals(s))
          {
              String s3 = (String)propertychangeevent.getNewValue();
              a.setLongText(s3 == null || "".equals(s3) ? a.action.toString() : s3);
          } else
          if("enabled".equals(s))
              a.setEnabled(a.action.isEnabled());
          else
          if("SmallIcon".equals(s))
          {
              Icon icon1 = (Icon)propertychangeevent.getNewValue();
              a.setIcon(icon1);
          } else
          if("List".equals(s))
              a.repaint();
          else
          if("DefaultAction".equals(s))
              a.repaint();
          Object oldValue = propertychangeevent.getOldValue();
          Object newValue = propertychangeevent.getNewValue();
          if ( newValue != null && !newValue.equals(oldValue) ) {
            a.repaint();return;
          }
          if ( oldValue != null && !oldValue.equals(newValue) ) {
            a.repaint();return;
          }
      }

      public ActionPropertyChangeListener(ActionButton actionbutton)
      {
        a = actionbutton;
      }

  }


  static Class b; /* synthetic field */
  protected PropertyChangeListener pcl;
  protected Image darkerDither;
  protected Image dither;
  protected boolean glowStyle;
  protected boolean center;
  protected boolean showRips;
  protected boolean showText;
  protected boolean showIcon;
  protected boolean popping;
  protected boolean mouseDown;
  protected boolean rollover;
  protected String longText;
  protected String altShortText;
  protected String shortText;
  protected int gapSize;
  protected Icon icon_roll;
  protected Icon icon_dis;
  protected Icon icon;
  protected boolean useSmallIcon;
  protected Action action;
  protected Object source;
  protected ActionEvent ae;
  protected int popWidth;
  protected int fixedHeight;
  protected int fixedWidth;
  protected static Icon icon_marker;
  protected static boolean globalPopping = false;
  protected static boolean globalMouseDown = false;
  protected static final int DOWN = -1;
  protected static final int FLAT = 0;
  protected static final int UP = 1;
  JToolTip a;
  public Component getActionComponent() {
    return this;
  }
//  private static final Profiler c = Profiler.instance("ActionButton");

//  static Class class$(String s)
//  {
//      try
//      {
//          return Class.forName(s);
//      }
//      catch(ClassNotFoundException classnotfoundexception)
//      {
//          throw new NoClassDefFoundError(classnotfoundexception.getMessage());
//      }
//  }

  public void paintChildren(Graphics g)
  {
  }

  private void a(Graphics g, Rectangle rectangle, boolean flag, boolean flag1)
  {
      boolean flag2 = "Borland".equals(UIManager.getLookAndFeel().getID());
      Color color = g.getColor();
      Color color1 = UIManager.getColor("Panel.background");
      Color color2 = color1;
      Color color3 = color1;
      if(flag)
      {
          if(!glowStyle)
          {
              color2 = color1.brighter();
              color3 = color1.darker();
          }
      } else
      {
          color2 = color1.darker();
          color3 = color1.brighter();
      }
      if(flag2 && flag)
      {
          if(flag)
              g.setColor(Color.BLACK);
          else
              g.setColor(color2);
      } else
      {
          g.setColor(color2);
          g.drawLine(rectangle.x, rectangle.y, rectangle.x, (rectangle.y + rectangle.height) - 1);
      }
      if(flag1)
          g.drawLine(rectangle.x, rectangle.y, rectangle.x, (rectangle.y + rectangle.height) - 1);
      g.drawLine(rectangle.x, rectangle.y, (rectangle.x + rectangle.width) - 1, rectangle.y);
      if(flag2 && flag)
          g.setColor(Color.BLACK);
      else
          g.setColor(color3);
      g.drawLine((rectangle.x + rectangle.width) - 1, rectangle.y, (rectangle.x + rectangle.width) - 1, (rectangle.y + rectangle.height) - 1);
      g.drawLine(rectangle.x, (rectangle.y + rectangle.height) - 1, (rectangle.x + rectangle.width) - 1, (rectangle.y + rectangle.height) - 1);
  }

  public void drawBorder(Graphics g, Rectangle rectangle, boolean flag)
  {
      a(g, rectangle, flag, true);
  }

  public void drawPopArrow(Graphics g, Rectangle rectangle)
  {
      Dimension dimension = getSize();
      rectangle.x += (int)Math.ceil((popWidth - 5) / 2);
      rectangle.y += (int)Math.ceil((dimension.height - 3) / 2);
      Color color = g.getColor();
      g.setColor(isEnabled() ? Color.black : UIManager.getColor("Separator.shadow"));
      g.drawLine(rectangle.x, rectangle.y, rectangle.x + 4, rectangle.y);
      g.drawLine(rectangle.x + 1, rectangle.y + 1, rectangle.x + 3, rectangle.y + 1);
      g.drawLine(rectangle.x + 2, rectangle.y + 2, rectangle.x + 2, rectangle.y + 2);
      g.setColor(color);
  }

  public void paintComponent(Graphics g)
  {
      super.paintComponent(g);
      Dimension dimension = getSize();
      int i = buttonPopState();
      if(i == -1)
      {
          if("Borland".equals(UIManager.getLookAndFeel().getID()))
              Util.paintGradientBackground2(g, this);
          else
          if(action instanceof StateAction)
          {
              if(dither == null)
                  dither = Images.getImage(this.getClass(), "image/brightdither.gif");
              ImageTexture.texture(dither, g, 0, 0, dimension.width, dimension.height);
          }
      } else
      if(i == 1 && "Borland".equals(UIManager.getLookAndFeel().getID()))
      {
          g.setColor(new Color(192, 192, 192));
          g.fillRect(0, 0, dimension.width, dimension.height);
      }
      int j = 0;
      int k = 0;
      Icon icon1 = theIcon();
      if(showIcon && icon1 != null )
      {

          int l = icon1.getIconWidth();
          int i1 = icon1.getIconHeight();
          if(showText && shortText != null)
          {
              if(center)
              {
                  java.awt.Font font1 = UIManager.getFont("Label.font");
                  FontMetrics fontmetrics1 = g.getFontMetrics(font1);
                  k = fontmetrics1.stringWidth(altShortText == null ? shortText : altShortText);
                  j = (int)Math.ceil((dimension.width - l - gapSize - k - ((action instanceof ActionGroup) ? popWidth : 0)) / 2);
              } else
              {
                  j = gapSize;
              }
          } else
          {
              j = (int)Math.ceil((dimension.width - l - ((action instanceof ActionGroup) ? popWidth : 0)) / 2);
          }
          int k1 = (int)Math.ceil((dimension.height - i1) / 2);
          if(i == -1)
          {
              j++;
              k1++;
          }
          icon1.paintIcon(this, g, j, k1);
          j += l;
      }
      if(showText && shortText != null)
      {
          String s = altShortText == null ? shortText : altShortText;
          java.awt.Font font = UIManager.getFont("Label.font");
          FontMetrics fontmetrics = g.getFontMetrics(font);
          if(k == 0)
              k = fontmetrics.stringWidth(s);
          int l1 = fontmetrics.getHeight();
          int i2 = (int)Math.ceil((dimension.height - l1) / 2) + fontmetrics.getAscent();
          if(showIcon || !center)
            j += gapSize;
          else
            j = (int)Math.ceil((dimension.width - k - ((action instanceof ActionGroup) ? popWidth : 0)) / 2) + (i != -1 ? 0 : 1);
          int j2 = dimension.width - j - gapSize - ((action instanceof ActionGroup) ? popWidth : 0);
          if(k > j2)
          {
              for(j2 -= fontmetrics.stringWidth("..."); s.length() > 0 && k > j2; k = fontmetrics.stringWidth(s))
                  if(s.length() > 1)
                      s = s.substring(0, s.length() - 1);
                  else
                      s = "";

              s = String.valueOf(String.valueOf(s)).concat("...");
          }
          if(i == -1)
              i2++;
          g.setFont(font);
          if(isEnabled())
          {
              if(glowStyle && rollover)
              {
                  g.setColor(Color.green);
                  g.drawString(s, j - 1, i2 - 1);
                  g.drawString(s, j - 1, i2 + 1);
                  g.drawString(s, j + 1, i2 - 1);
                  g.drawString(s, j + 1, i2 + 1);
              }
              g.setColor(UIManager.getColor("Label.foreground"));
              g.drawString(s, j, i2);
          } else
          {
              g.setColor(UIManager.getColor("Separator.highlight"));
              g.drawString(s, j + 1, i2 + 1);
              g.setColor(UIManager.getColor("Separator.shadow"));
              g.drawString(s, j, i2);
          }
      }
      boolean flag = hasPopBar();
      Rectangle rectangle = flag ? new Rectangle(0, 0, dimension.width - popWidth, dimension.height) : new Rectangle(0, 0, dimension.width, dimension.height);
      if(i != 0)
          drawBorder(g, rectangle, i == 1);
      if(action instanceof ActionGroup)
      {
          int j1 = popPopState();
          Rectangle rectangle1 = new Rectangle(dimension.width - popWidth, 0, popWidth, dimension.height);
          if(flag && j1 != 0)
              a(g, rectangle1, j1 == 1, false);
          if(j1 == -1)
          {
              rectangle1.x++;
              rectangle1.y++;
          }
          drawPopArrow(g, rectangle1);
      }
      if(showRips)
      {
          Container container = getParent();
          if(container != null)
          {
              Dimension dimension1 = container.getSize();
              Rectangle rectangle2 = getBounds();
              if(rectangle2.x < dimension1.width && rectangle2.x + rectangle2.width > dimension1.width)
              {
                  int k2 = dimension1.width - rectangle2.x - 5;
                  Color color = UIManager.getColor("Label.background");
                  Color color1 = color.darker();
                  for(int l2 = 0; l2 < dimension.height; l2 += 8)
                  {
                      g.setColor(color);
                      g.drawLine(k2 + 0, l2 + 0, k2 + 0, l2 + 1);
                      g.fillRect(k2 + 2, l2 + 0, 3, 2);
                      g.drawLine(k2 + 1, l2 + 2, k2 + 1, l2 + 3);
                      g.fillRect(k2 + 3, l2 + 2, 2, 2);
                      g.drawLine(k2 + 2, l2 + 4, k2 + 2, l2 + 5);
                      g.drawLine(k2 + 4, l2 + 4, k2 + 4, l2 + 5);
                      g.drawLine(k2 + 1, l2 + 6, k2 + 1, l2 + 7);
                      g.fillRect(k2 + 3, l2 + 6, 2, 2);
                      g.setColor(color1);
                      g.drawLine(k2 + 1, l2 + 0, k2 + 1, l2 + 1);
                      g.drawLine(k2 + 2, l2 + 2, k2 + 2, l2 + 3);
                      g.drawLine(k2 + 3, l2 + 4, k2 + 3, l2 + 5);
                      g.drawLine(k2 + 2, l2 + 6, k2 + 2, l2 + 7);
                  }

              }
          }
      }
  }

  protected Icon theIcon()
  {
      Icon icon1 = isEnabled() ? !rollover || !glowStyle ? icon : icon_roll : icon_dis;
      if(icon1 == null)
      {
          icon1 = icon;
          if(icon1 == null)
              icon1 = icon_marker;
      }
      return icon1;
  }

  protected int popPopState()
  {
      if(action instanceof ActionGroup)
      {
          if(popping)
              return -1;
          boolean flag = isEnabled();
          if(rollover && mouseDown && flag)
              return -1;
          if(rollover && flag)
              return 1;
      }
      return 0;
  }

  protected int buttonPopState()
  {
      boolean flag = isEnabled();
      if(action instanceof StateAction)
      {
          boolean flag1 = ((StateAction)action).getState(source);
          if(flag1 || rollover && mouseDown && flag)
              return -1;
          return !rollover || !flag || flag1 ? 0 : 1;
      }
      if((action instanceof ActionGroup) && popping)
          return hasPopBar() ? 1 : -1;
      if(rollover && mouseDown && flag)
          return -1;
      return !rollover || !flag ? 0 : 1;
  }

  protected boolean hasPopBar()
  {
      if(action instanceof ActionGroup)
      {
          ActionGroup actiongroup = (ActionGroup)action;
          int i = actiongroup.getActionCount();
          int j = actiongroup.getDefaultAction();
          return i > 0 && j > -1 && j < i;
      } else
      {
          return false;
      }
  }

  protected void processMouseEvent(MouseEvent mouseevent)
  {
      boolean flag = globalPopping && popping;
      super.processMouseEvent(mouseevent);
      flag = flag && !globalPopping;
      if(flag)
          return;
      if(mouseevent.isConsumed())
          return;
      if(mouseevent.getClickCount() == 2)
          return;
      boolean flag1 = SwingUtilities.isRightMouseButton(mouseevent);
      IWindowStatus Status;
      switch(mouseevent.getID())
      {
      case 503:
      default:
          break;

      case 501:
          rollover = true;
          if(action instanceof UpdateableAction)
          {
//              c.start();
              ((UpdateableAction)action).update(source);
//              if(c.stopWithStatus(200L))
//                  c.println(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf((String)action.getValue("ShortDescription"))))).append(" ").append((String)action.getValue("LongDescription")))));
          }
          if(flag1 || !isEnabled())
              return;
          mouseDown = true;
          globalMouseDown = true;
          repaint();
          if(action instanceof ActionGroup)
          {
              ActionGroup actiongroup = (ActionGroup)action;
              Dimension dimension = getSize();
              boolean flag2 = mouseevent.getX() > dimension.width - popWidth || !hasPopBar();
              int i = actiongroup.getActionCount();
              int j = actiongroup.getDefaultAction();
              if(i > 0 && (flag2 || j < 0 || j >= i))
              {
                  ActionPopupMenu actionpopupmenu = new ActionPopupMenu(source, actiongroup);
                  actionpopupmenu.setBoldDefaultAction(true);
                  popping = true;
                  globalPopping = true;
                  rollover = false;
                  actionpopupmenu.addPopupMenuListener(new y((ActionButton)this, actionpopupmenu));
                  actionpopupmenu.show(this, 0, dimension.height);
                  Dimension dimension1 = actionpopupmenu.getSize();
                  if(dimension1.width < dimension.width)
                  {
                      Object obj = actionpopupmenu;
                      for(Point point = ((Component) (obj)).getLocation(); point.x == 0 && point.y == 0 && ((Component) (obj)).getParent() != null; point = ((Component) (obj)).getLocation())
                          obj = ((Component) (obj)).getParent();

                      ((Component) (obj)).setSize(dimension.width, dimension1.height);
                      ((Container) (obj)).validate();
                  }
              }
          }
          break;

      case 502:
          if(flag1 || !isEnabled())
              return;
          mouseDown = false;
          globalMouseDown = false;
          if(rollover && !popping)
              fire();
          repaint();
          break;

      case 504:
          if(!mouseDown && (globalMouseDown || globalPopping))
           break;
          rollover = true;
          repaint();
          Status = getStatus();
          if( Status != null ) {
            Status.setText(longText);
            Status.setIcon(this.icon);
          }
          break;

      case 505:
          rollover = false;
          if(!mouseDown && (globalMouseDown || globalPopping))
              break;
          repaint();
          Status = getStatus();
          if( Status != null ) {
            Status.setText(null);
            Status.setIcon(null);
          }
          break;
      }
  }
  private IWindowStatus getStatus() {
    return (IWindowStatus)System.getProperties().get("WindowStatus");
  }

  private void a(Action action1)
  {
      if(action1 instanceof UpdateableAction)
      {
//          c.start();
          ((UpdateableAction)action1).update(source);
//          if(c.stopWithStatus(200L))
//              c.println(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf((String)action1.getValue("ShortDescription"))))).append(" ").append((String)action1.getValue("LongDescription")))));
      }
      if(action1.isEnabled())
      {
          if(action1 instanceof ActionGroup)
          {
              ActionGroup actiongroup = (ActionGroup)action1;
              int i = actiongroup.getDefaultAction();
              int j = actiongroup.getActionCount();
              if(i >= 0 && i < j)
              {
                  action1 = actiongroup.getAction(i);
                  if((action1 instanceof ActionGroup) && !((ActionGroup)action1).isPopup())
                  {
                      a(action1);
                      return;
                  }
              } else
              {
                  action1 = null;
              }
          }
          if(action1 != null)
          {
              if(ae == null)
                  ae = new ActionEvent(this, 1001, "");
              action1.actionPerformed(ae);
//              if(source instanceof Browser)
//                  ((Browser)source).getStatusView().setHintText(null);
          }
      }
  }

  protected void fire()
  {
      a(action);
  }

  protected void initButton()
  {
      setRequestFocusEnabled(false);
      Object obj = action.getValue("ShortDescription");
      if(obj instanceof String)
          setShortText((String)obj);
      Object obj1 = action.getValue("AltShortDescription");
      if(obj1 instanceof String)
          setAltShortText((String)obj1);
      Object obj2 = action.getValue("LongDescription");
      if(obj2 instanceof String)
          setLongText((String)obj2);
      Object obj3 = action.getValue(useSmallIcon ? "SmallIcon" : "LargeIcon");
      if(!(obj3 instanceof Icon))
          obj3 = icon_marker;
      setIcon((Icon)obj3);
      setEnabled(action.isEnabled());
      enableEvents(16L);
  }

  public JToolTip createToolTip()
  {
      if(a == null)
          a = super.createToolTip();
      else
          a.updateUI();
      return a;
  }

  public void removeNotify()
  {
      if(action instanceof UpdateableAction)
      {
//          Browser browser = Browser.findBrowser(getParent());
//          if(browser != null)
//              browser.removeButton(this);
      }
      if(pcl != null && action != null)
          action.removePropertyChangeListener(pcl);
      super.removeNotify();
      try
      {
          if(a != null)
          {
              a.setComponent(null);
              a = null;
          }
      }
      catch(Exception exception) { }
  }

  public void addNotify()
  {
      super.addNotify();
      if(action instanceof UpdateableAction)
      {
//          Browser browser = Browser.findBrowser(getParent());
//          if(browser != null)
//              browser.addButton(this);
          if(pcl == null)
              pcl = new ActionPropertyChangeListener(this);
          action.addPropertyChangeListener(pcl);
      }
  }

  protected void setAltShortText(String s)
  {
      if(shortText == null || s == null || !shortText.equals(s))
      {
          altShortText = s;
          if(!showText && s != null)
              setToolTipText(s);
          else
              repaint();
      }
  }

  protected void setShortText(String s)
  {
      shortText = s;
      if(!showText)
      {
          setToolTipText(s);
      } else
      {
          invalidate();
          validate();
          repaint();
      }
  }

  protected void setLongText(String s)
  {
      longText = s;
      if(showText)
          setToolTipText(s);
  }

  public void setToolTipText(String s)
  {
      for(; s != null && s.endsWith("."); s = s.substring(0, s.length() - 1));
      super.setToolTipText(s);
  }

  public boolean isFocusable()
  {
      return false;
  }

  public boolean isFocusTraversable()
  {
      return false;
  }

  public Dimension getPreferredSize()
  {
      Dimension dimension = new Dimension(fixedWidth, fixedHeight);
      if(fixedWidth > 0 && fixedHeight > 0)
      {
          if(action instanceof ActionGroup)
              dimension.width += popWidth;
          return dimension;
      }
      int i = 0;
      int j = 0;
      if(showText && shortText != null)
      {
          Graphics g = getGraphics();
          if(g != null)
          {
              java.awt.Font font = UIManager.getFont("Label.font");
              FontMetrics fontmetrics = g.getFontMetrics(font);
              i = fontmetrics.stringWidth(altShortText == null ? shortText : altShortText);
              j = fontmetrics.getHeight();
          }
      }
      int k = 0;
      int l = 0;
      if(showIcon)
      {
          k = icon.getIconWidth();
          if(showText)
              k += gapSize;
          l = icon.getIconHeight();
      }
      if(fixedWidth <= 0)
          dimension.width = gapSize + k + i + gapSize;
      if(fixedHeight <= 0)
          dimension.height = 2 * gapSize + j <= l ? l : j;
      if(action instanceof ActionGroup)
          dimension.width += popWidth;
      return dimension;
  }

  public Action getAction()
  {
      return action;
  }

  public Object getSource()
  {
      return source;
  }

  public void setIcon(Icon icon1)
  {
      icon = icon1;
      icon_dis = null;
      icon_roll = null;
      if(icon instanceof ImageIcon)
      {
          ImageIcon imageicon = (ImageIcon)icon;
          Image image = imageicon.getImage();
          Image image1 = Images.getDisabledImage(image);
          icon_dis = new ImageIcon(image1);
          if(glowStyle)
          {
              Image image2 = Images.getAuraImage(image);
              icon_roll = new ImageIcon(image2);
          }
          return;
      } else
      {
          icon_dis = Icons.getDisabledIcon(icon);
          if(!glowStyle);
          return;
      }
  }

  public boolean isUseSmallIcon()
  {
      return useSmallIcon;
  }

  public void setUseSmallIcon(boolean flag)
  {
      if(flag != useSmallIcon)
      {
          useSmallIcon = flag;
          repaint();
      }
  }

  public boolean isGlowStyle()
  {
      return glowStyle;
  }

  public void setGlowStyle(boolean flag)
  {
      glowStyle = flag;
  }

  public boolean isCentered()
  {
      return center;
  }

  public void setCentered(boolean flag)
  {
      if(center != flag)
      {
          center = flag;
          repaint();
      }
  }

  public boolean isShowRips()
  {
      return showRips;
  }

  public void setShowRips(boolean flag)
  {
      if(showRips != flag)
      {
          showRips = flag;
          repaint();
      }
  }

  public boolean isShowText()
  {
      return showText;
  }

  public void setShowText(boolean flag)
  {
      if(showText != flag)
      {
          showText = flag;
          setShortText(shortText);
          setAltShortText(altShortText);
          setLongText(longText);
          repaint();
      }
  }

  public boolean isShowIcon()
  {
      return showIcon;
  }

  public void setShowIcon(boolean flag)
  {
      if(showIcon != flag)
      {
          showIcon = flag;
          repaint();
      }
  }

  public int getGapSize()
  {
      return gapSize;
  }

  public void setGapSize(int i)
  {
      gapSize = i;
  }

  public int getFixedHeight()
  {
      return fixedHeight;
  }

  public void setFixedHeight(int i)
  {
      if(i != fixedHeight)
      {
          if(i > 0)
              fixedHeight = i;
          else
              fixedHeight = 0;
          invalidate();
          repaint();
      }
  }

  public int getFixedWidth()
  {
      return fixedWidth;
  }

  public void setFixedWidth(int i)
  {
      if(i != fixedWidth)
      {
          if(i > 0)
              fixedWidth = i;
          else
              fixedWidth = 0;
          invalidate();
          repaint();
      }
  }

  public Dimension getFixedSize()
  {
      return new Dimension(fixedWidth, fixedHeight);
  }

  public void setFixedSize(Dimension dimension)
  {
      if(dimension != null)
      {
          setFixedWidth(dimension.width);
          setFixedHeight(dimension.height);
      }
  }

  public void clear()
  {
      source = null;
      action = null;
  }
  public ActionButton(Object obj, Action action1, Dimension dimension, boolean flag) {
    this(obj,action1,dimension,flag,true);
  }
  public ActionButton(Object obj, Action action1, Dimension dimension, boolean flag,boolean timer)
  {
      fixedWidth = 25;
      fixedHeight = 25;
      popWidth = 12;
      ae = null;
      useSmallIcon = true;
      gapSize = 6;
      rollover = false;
      mouseDown = false;
      popping = false;
      showIcon = true;
      showText = false;
      showRips = false;
      center = true;
      glowStyle = false;
      source = obj;
      action = action1;
      useSmallIcon = flag;
      setFixedSize(dimension);
      initButton();
      // ��ӵ���ʱ����
      if ( timer )
        ActionToolBar.addAutoUpdateComponent(this);
      this.setOpaque(false);
  }

  public ActionButton(Object obj, Action action1, Dimension dimension)
  {
      this(obj, action1, dimension, true);
  }

  public ActionButton(Object obj, Action action1)
  {
      this(obj, action1, null, true);
  }
  public static ActionButton getActionButton(Object obj,Action a) {
    ActionButton ab = new ActionButton(obj,a,null,true,false);
    return ab;
  }
  static
  {
//      UP = 1;
//      FLAT = 0;
//      DOWN = -1;
//      icon_marker = BrowserIcons.ICON_OBJECTS;
  }

class y
  implements PopupMenuListener
{

  private final ActionPopupMenu a; /* synthetic field */
  private final ActionButton b; /* synthetic field */

  public void popupMenuCanceled(PopupMenuEvent popupmenuevent)
  {
  }

  public void popupMenuWillBecomeInvisible(PopupMenuEvent popupmenuevent)
  {
      b.popping = false;
      ActionButton.globalPopping = false;
      a.setInvoker(null);
      b.repaint();
  }

  public void popupMenuWillBecomeVisible(PopupMenuEvent popupmenuevent)
  {
  }

  y(ActionButton actionbutton, ActionPopupMenu actionpopupmenu)
  {
      b = actionbutton;
      a = actionpopupmenu;
  }
}

}
