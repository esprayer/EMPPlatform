package com.efounder.action;

import javax.swing.Icon;

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
public class SliderDelegateAction extends DelegateAction {
  int orientation;int width;int min;int max;int value;
  /**
   *
   * @return int
   */
  public int getOrientation() {
    return orientation;
  }
  /**
   *
   * @return int
   */
  public int getWidth() {
    return width;
  }
  /**
   *
   * @return int
   */
  public int getMin() {
    return min;
  }
  /**
   *
   * @return int
   */
  public int getMax() {
    return max;
  }
  /**
   *
   * @return int
   */
  public int getValue() {
    return value;
  }
  /**
   *
   * @param orientation int
   * @param width int
   * @param min int
   * @param max int
   * @param value int
   */
  public SliderDelegateAction(int orientation,int width,int min,int max,int value) {
    super("SliderDelegateAction");
    this.orientation = orientation;
    this.width = width;
    this.min = min;
    this.max = max;
    this.value = value;
  }
}
