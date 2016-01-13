package com.efounder.bz.dbform.component;

import javax.swing.*;

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
public class FormButton extends JButton implements FormComponent {
  /**
   *
   */
  public FormButton() {
    this.setText("OK");
  }
  /**
   *
   * @return FormContainer
   */
  public FormContainer getFormContainer() {
    return null;
  }
  /**
   *
   * @return JComponent
   */
  public JComponent getJComponent() {
    return this;
  }
}
