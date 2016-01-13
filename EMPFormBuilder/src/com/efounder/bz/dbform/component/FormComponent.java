package com.efounder.bz.dbform.component;

import java.awt.Component;
import javax.swing.JComponent;

public abstract interface FormComponent {
  public abstract FormContainer getFormContainer();
  
  public abstract JComponent getJComponent();
  
  public abstract Component[] getComponents();
  
  public abstract String getName();
}