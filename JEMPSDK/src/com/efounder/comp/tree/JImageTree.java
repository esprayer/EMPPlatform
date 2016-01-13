package com.efounder.comp.tree;

import javax.swing.*;
import java.awt.*;

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
public class JImageTree extends JTree {
  private Color nodeSelectFrontColor = Color.white;
  private Color nodeFrontColor = Color.black;
  private Color nodeSelectBackColor = Color.BLUE.darker();
  public Color getNodeSelectFrontColor() {
    return nodeSelectFrontColor;
  }
  public void setNodeSelectFrontColor(Color c) {
    nodeSelectFrontColor = c;
  }
  public void setNodeFrontColor(Color c) {
    nodeFrontColor = c;
  }
  public Color getNodeFrontColor() {
    return nodeFrontColor;
  }
  public void setNodeSelectBackColor(Color c) {
    nodeSelectBackColor = c;
  }
  public Color getNodeSelectBackColor() {
    return nodeSelectBackColor;
  }
  private ImageIcon iconImage = null;
  /**
   *
   * @param icon Icon
   */
  public void setIconImage(ImageIcon icon) {
    if ( icon != null ) {
      this.setOpaque(false);
      iconImage = icon;
      this.repaint();
    }
  }
  /**
   *
   */
  public JImageTree() {
    super();
  }
  public void paint( Graphics g ) {
    // First draw the background image - tiled
    if ( iconImage != null ) {
      Dimension d = getSize();
      Rectangle rect = this.getVisibleRect();
      g.drawImage(iconImage.getImage(), rect.x, rect.y, null, null);
      for (int x = rect.x + iconImage.getIconWidth(); x < rect.width;x += iconImage.getIconWidth()) {
        g.drawImage(iconImage.getImage(), x, rect.y, null, null);
      }
      for (int y = rect.y + iconImage.getIconHeight(); y < rect.height; y += iconImage.getIconHeight()) {
        g.drawImage(iconImage.getImage(), rect.x, y, null, null);
      }
      for (int x = rect.x + iconImage.getIconWidth(); x < rect.width;x += iconImage.getIconWidth()) {
        for (int y = rect.y + iconImage.getIconHeight(); y < rect.height; y += iconImage.getIconHeight()) {
          g.drawImage(iconImage.getImage(), x, y, null, null);
        }
      }
    }
    super.paint(g);
  }

  /**
   *
   * @param g Graphics
   */
//  protected void paintComponent(Graphics g) {
//    setOpaque(true);
//    super.paintComponent(g);
//    iconImage.paintIcon(this,g,0,0);
//    //              Dimension d = getSize();
//    //              for( int x = 0; x < d.width; x += image.getIconWidth() )
//    //             for( int y = 0; y < d.height; y += image.getIconHeight() )
//    //      g.drawImage( image.getImage(), x, y, null, null );
//  }
}
