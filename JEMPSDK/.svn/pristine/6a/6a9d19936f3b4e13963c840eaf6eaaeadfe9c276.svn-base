package com.efounder.comp;

import javax.swing.*;
import java.awt.Graphics;

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
public class JPicPanel extends JPanel {
  /**
   *
   */
  public JPicPanel() {
    super();
  }
  /**
   *
   * @param g Graphics
   */
  public void paint(Graphics g) {
    if ( iconPic != null ) {
      iconPic.paintIcon(this,g,0,0);
    }
    super.paint(g);
  }
  protected Icon iconPic = null;
  public void setIconPic(Icon icon) {
    iconPic = icon;
  }
  public Icon getIconPic() {
    return iconPic;
  }
}
