package com.efounder.bz.dbform.component.dc;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JTextComponentMouseEvent implements MouseListener {
  /**
   *
   * @param tcCol JTextComponentColumn
   * @return JTextComponentMouseEvent
   */
  public static JTextComponentMouseEvent getJTextComponentMouseEvent(JTextComponentColumn tcCol) {
    JTextComponentMouseEvent tcme = new JTextComponentMouseEvent(tcCol);
    return tcme;
  }
  /**
   *
   */
  protected JTextComponentColumn textComponentColumn = null;
  /**
   *
   */
  protected JTextComponentMouseEvent(JTextComponentColumn tcCol) {
    textComponentColumn = tcCol;
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseClicked(MouseEvent e) {

  }
  /**
   *
   * @param e MouseEvent
   */
  public void mousePressed(MouseEvent e) {
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseEntered(MouseEvent e) {
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseExited(MouseEvent e) {
  }
}
