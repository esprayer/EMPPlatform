package com.efounder.comp;

import javax.swing.*;
import java.awt.Graphics;

public class JImagePanel extends JPanel {
  private Icon leftTopImage;
  private Icon rightTopImage;
  private Icon leftBottomImage;
  private Icon rightBottomImage;
  private Icon leftImage;
  private Icon topImage;
  private Icon rightImage;
  private Icon bottomImage;
  /**
   *
   */
  public JImagePanel() {

  }
  /**
   *
   * @param g Graphics
   */
  public void paintChildren(Graphics g) {
    paintImage(g);
    super.paintChildren(g);
  }
  /**
   *
   * @param g Graphics
   */
  protected void paintImage(Graphics g) {
    paintLeftImage(g);
    paintTopImage(g);
    paintRightImage(g);
    paintBottomImage(g);
    paintLeftTopImage(g);
    paintRightTopImage(g);
    paintRightBottomImage(g);
    paintLeftBottomImage(g);
  }
  /**
   *
   * @param g Graphics
   */
  protected void paintLeftTopImage(Graphics g) {
    if ( this.leftTopImage == null ) return;
    leftTopImage.paintIcon(this,g,0,0);
  }
  /**
   *
   * @param g Graphics
   */
  protected void paintRightTopImage(Graphics g) {
    if ( this.rightTopImage == null ) return;
    rightTopImage.paintIcon(this,g,
                            this.getWidth()-rightTopImage.getIconWidth(),0);
  }
  /**
   *
   * @param g Graphics
   */
  protected void paintRightBottomImage(Graphics g) {
    if ( rightBottomImage == null ) return;
    rightBottomImage.paintIcon(this,g,
                               this.getWidth()-rightBottomImage.getIconWidth(),
                               this.getHeight()-rightBottomImage.getIconHeight());
  }
  /**
   *
   * @param g Graphics
   */
  protected void paintLeftBottomImage(Graphics g) {
    if ( leftBottomImage == null ) return;
    leftBottomImage.paintIcon(this,g,0,
                              this.getHeight()-leftBottomImage.getIconHeight());
  }
  protected void paintLeftImage(Graphics g) {

  }
  protected void paintTopImage(Graphics g) {

  }
  protected void paintRightImage(Graphics g) {

  }
  protected void paintBottomImage(Graphics g) {

  }

  public void setLeftTopImage(Icon leftTopImage) {
    this.leftTopImage = leftTopImage;
  }

  public void setRightTopImage(Icon rightTopImage) {
    this.rightTopImage = rightTopImage;
  }

  public void setLeftBottomImage(Icon leftBottomImage) {
    this.leftBottomImage = leftBottomImage;
  }

  public void setRightBottomImage(Icon rightBottomImage) {
    this.rightBottomImage = rightBottomImage;
  }

  public void setLeftImage(Icon leftImage) {
    this.leftImage = leftImage;
  }

  public void setTopImage(Icon topImage) {
    this.topImage = topImage;
  }

  public void setRightImage(Icon rightImage) {
    this.rightImage = rightImage;
  }

  public void setBottomImage(Icon bottomImage) {
    this.bottomImage = bottomImage;
  }

  public Icon getLeftTopImage() {
    return leftTopImage;
  }

  public Icon getRightTopImage() {
    return rightTopImage;
  }

  public Icon getLeftBottomImage() {
    return leftBottomImage;
  }

  public Icon getRightBottomImage() {
    return rightBottomImage;
  }

  public Icon getLeftImage() {
    return leftImage;
  }

  public Icon getTopImage() {
    return topImage;
  }

  public Icon getRightImage() {
    return rightImage;
  }

  public Icon getBottomImage() {
    return bottomImage;
  }

}
