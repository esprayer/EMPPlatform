package com.efounder.bz.dbform.component.dc;

import javax.swing.event.*;

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
public class JTextDocumentEvent implements DocumentListener {
  /**
   *
   */
  protected JTextComponentColumn textComponentCloumn = null;
  /**
   *
   */
  protected JTextDocumentEvent(JTextComponentColumn textComponentCloumn) {
    this.textComponentCloumn = textComponentCloumn;
  }
  /**
   *
   * @return JTextComponentColumn
   */
  public static JTextDocumentEvent getJTextDocumentEvent(JTextComponentColumn textComponentCloumn) {
    JTextDocumentEvent tde = new JTextDocumentEvent(textComponentCloumn);
    return tde;
  }
  /**
   * Gives notification that an attribute or set of attributes changed.
   *
   * @param e the document event
   * @todo Implement this javax.swing.event.DocumentListener method
   */
  public void changedUpdate(DocumentEvent e) {
  }

  /**
   * Gives notification that there was an insert into the document.
   *
   * @param e the document event
   * @todo Implement this javax.swing.event.DocumentListener method
   */
  public void insertUpdate(DocumentEvent e) {
  }

  /**
   * Gives notification that a portion of the document has been removed.
   *
   * @param e the document event
   * @todo Implement this javax.swing.event.DocumentListener method
   */
  public void removeUpdate(DocumentEvent e) {
  }
}
