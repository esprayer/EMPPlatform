package com.efounder.bz.dbform.container;

import java.awt.Component;

import com.jidesoft.swing.*;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import com.efounder.bz.dbform.component.FormContainer;
import com.efounder.bz.dbform.component.FormComponent;

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
public class EFScrollPane extends JScrollPane {
  public EFScrollPane() {
  }

  /**
   *
   * @param comp FormComponent
   * @param constraints Object
   * @param index int
   */
  public void addChildFormComp(FormComponent comp, Object constraints,int index) {
//    setViewportView(comp.getJComponent());
    this.getViewport().add(comp.getJComponent());
//    this.addImpl(comp.getJComponent(),constraints,index);
  }
  /**
   *
   * @param comp FormComponent
   */
  public void removeChildFormComp(FormComponent comp) {
    this.getViewport().remove(comp.getJComponent());
  }
  /**
   *
   * @return FormContainer
   */
  public FormContainer getFormContainer() {
    return null;
  }
}
