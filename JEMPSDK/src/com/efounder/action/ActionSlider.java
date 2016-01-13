package com.efounder.action;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ActionSlider extends JSlider implements ActionWidget,javax.swing.event.ChangeListener {
  protected SliderDelegateAction action;
  protected Object source;
  protected int fixedHeight;
  protected int fixedWidth;
  /**
   *
   * @return Component
   */
  public Component getActionComponent() {
    return this;
  }
  /**
   *
   * @param obj Object
   * @param sliderDelegateAction SliderDelegateAction
   * @param dimension Dimension
   */
  public ActionSlider(Object obj, SliderDelegateAction sliderDelegateAction, Dimension dimension) {
    source = obj;
    action = sliderDelegateAction;
    fixedWidth = sliderDelegateAction.getWidth();
    fixedHeight = 25;
    fixedHeight = (int)super.getPreferredSize().getHeight();
    setFixedSize(dimension);
    initSlider();
    ActionToolBar.addAutoUpdateComponent(this);
    this.addChangeListener(this);
  }
  public ActionSlider(Object obj, SliderDelegateAction sliderDelegateAction) {
    this(obj,sliderDelegateAction,null);
  }
  /**
   *
   */
  protected void initSlider() {
    this.setMinimum(action.getMin());
    this.setMaximum(action.getMax());
    this.setValue(action.getValue());
  }
  /**
   *
   * @param dimension Dimension
   */
  public void setFixedSize(Dimension dimension) {
    if(dimension != null) {
      setFixedWidth(dimension.width);
      setFixedHeight(dimension.height);
    }
  }
  /**
   *
   * @param i int
   */
  public void setFixedWidth(int i) {
    if(i != fixedWidth) {
      if(i > 0)
        fixedWidth = i;
      else
        fixedWidth = 0;
      invalidate();
      repaint();
    }
  }
  /**
   *
   * @param i int
   */
  public void setFixedHeight(int i) {
    if(i != fixedHeight) {
      if(i > 0)
        fixedHeight = i;
      else
        fixedHeight = 0;
      invalidate();
      repaint();
    }
  }
  /**
   *
   * @return Action
   */
  public Action getAction() {
    return action;
  }
  /**
   *
   * @return Object
   */
  public Object getSource() {
    return source;
  }
  /**
   *
   * @param e ChangeEvent
   */
  public void stateChanged(ChangeEvent e) {
    ActionEvent ae = new ActionEvent(this,0,"ChangeEvent");
    action.actionPerformed(ae);
  }
}
