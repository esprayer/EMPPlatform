package com.efounder.eai.ide;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

/**
 * <p>Title: Enterprise Explorer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: EFounder</p>
 *
 * @author Skyline
 * @version 1.0
 */
public class FixedGlassPane extends JPanel implements MouseListener,MouseMotionListener, FocusListener {
  // helpers for redispatch logic
  /**
   *
   */
  protected Toolkit toolkit;
  /**
   *
   */
  protected Container contentPane;
  /**
   *
   */
  protected boolean needToRedispatch = false;
  /**
   *
   * @param mb JMenuBar
   * @param cp Container
   */
  public FixedGlassPane(Container cp) {
//    toolkit = Toolkit.getDefaultToolkit();
    contentPane = cp;
    addMouseListener(this);
    addMouseMotionListener(this);
    addFocusListener(this);
  }
  /**
   *
   * @param v boolean
   */
  public void setVisible(boolean v) {
    // Make sure we grab the focus so that key events don't go astray.
    if (v)
      requestFocus();
    super.setVisible(v);
  }
  /**
   *
   * @param fe FocusEvent
   */
  public void focusLost(FocusEvent fe) {
    if (isVisible())
      requestFocus();
  }
  /**
   *
   * @param fe FocusEvent
   */
  public void focusGained(FocusEvent fe) {
  }
  /**
   *
   * @param need boolean
   */
  public void setNeedToRedispatch(boolean need) {
    needToRedispatch = need;
  }
  /*
   * (Based on code from the Java Tutorial) We must forward at least the mouse
   * drags that started with mouse presses over the check box. Otherwise, when
   * the user presses the check box then drags off, the check box isn't
   * disarmed -- it keeps its dark gray background or whatever its L&F uses to
   * indicate that the button is currently being pressed.
   */
  public void mouseDragged(MouseEvent e) {
//    if (needToRedispatch)
      redispatchMouseEvent(e);
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseMoved(MouseEvent e) {
    if (needToRedispatch)
      redispatchMouseEvent(e);
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseClicked(MouseEvent e) {
    if (needToRedispatch)
      redispatchMouseEvent(e);
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseEntered(MouseEvent e) {
    if (needToRedispatch)
      redispatchMouseEvent(e);
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseExited(MouseEvent e) {
    if (needToRedispatch)
      redispatchMouseEvent(e);
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mousePressed(MouseEvent e) {
    if (needToRedispatch)
      redispatchMouseEvent(e);
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
    if (needToRedispatch) {
      redispatchMouseEvent(e);
    }
  }
  /**
   *
   * @param e MouseEvent
   */
  private void redispatchMouseEvent(MouseEvent e) {
//    Point glassPanePoint = e.getPoint();
//    Component component  = null;
//    Container container  = contentPane;
//    Point containerPoint = SwingUtilities.convertPoint(this,glassPanePoint, contentPane);
//    int eventID = e.getID();
//    /**
//     *
//     */
//    if ( containerPoint.y < 0 ) {
//      return;
//    }
//    //XXX: If the event is from a component in a popped-up menu,
//    //XXX: then the container should probably be the menu's
//    //XXX: JPopupMenu, and containerPoint should be adjusted
//    //XXX: accordingly.
//    if ( container == null || containerPoint == null ) return;
//    component = SwingUtilities.getDeepestComponentAt(container,containerPoint.x, containerPoint.y);
//    if (component == null) return;
//      Point componentPoint = SwingUtilities.convertPoint(this,glassPanePoint, component);
//      component.dispatchEvent(new MouseEvent(component,
//                                             eventID,
//                                             e.getWhen(),
//                                             e.getModifiers(),
//                                             componentPoint.x,
//                                             componentPoint.y,
//                                             e.getClickCount(),
//                                             e.isPopupTrigger(),e.getButton())
//                              );
  }
}
