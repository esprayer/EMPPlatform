package com.swing.tablepane;

import javax.swing.plaf.basic.*;
import java.awt.Color;
import javax.swing.plaf.ComponentUI;
import javax.swing.JComponent;
import java.awt.*;
import com.incors.plaf.alloy.*;
import javax.swing.text.View;

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
public class ColorTabbedPaneUI extends AlloyTabbedPaneUI {
  /**
   *
   * @param c JComponent
   * @return ComponentUI
   */
  public static ComponentUI createUI(JComponent c) {
    return new ColorTabbedPaneUI();
  }
  /**
   *
   */
  protected Color tabColor = null;
  /**
   *
   * @return Color
   */
  public Color getTabColor() {
    return tabColor;
  }
  /**
   *
   * @param tabColor Color
   */
  public void setTabColor(Color tabColor) {
    this.tabColor = tabColor;
  }
  /**
   *
   */
  public ColorTabbedPaneUI() {
  }
  /**
   *
   * @param g Graphics
   * @param tabPlacement int
   * @param tabIndex int
   * @param x int
   * @param y int
   * @param w int
   * @param h int
   * @param isSelected boolean
   */
  protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {

    g.setColor(!isSelected || tabColor == null?
               tabPane.getBackgroundAt(tabIndex) : tabColor);
    switch(tabPlacement) {
      case LEFT:
          g.fillRect(x+1, y+1, w-2, h-3);
          break;
      case RIGHT:
          g.fillRect(x, y+1, w-2, h-3);
          break;
      case BOTTOM:
          g.fillRect(x+1, y, w-3, h-1);
          break;
      case TOP:
      default:
          g.fillRect(x+1, y+1, w-3, h-1);
        }
//    super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, false);
  }
  /**
   *
   * @param g Graphics
   * @param tabPlacement int
   * @param font Font
   * @param metrics FontMetrics
   * @param tabIndex int
   * @param title String
   * @param textRect Rectangle
   * @param isSelected boolean
   */
  protected void paintText(Graphics g, int tabPlacement,
                           Font font, FontMetrics metrics, int tabIndex,
                           String title, Rectangle textRect,
                           boolean isSelected) {

      g.setFont(font);

      View v = getTextViewForTab(tabIndex);
      if (v != null) {
          // html
          v.paint(g, textRect);
      } else {
          // plain text
          int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

          if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
            if ( isSelected ) {
              g.setColor(java.awt.Color.white);
            } else {
              g.setColor(tabPane.getForegroundAt(tabIndex));
            }
              BasicGraphicsUtils.drawStringUnderlineCharAt(g,
                           title, mnemIndex,
                           textRect.x, textRect.y + metrics.getAscent());

          } else { // tab disabled
              g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
              BasicGraphicsUtils.drawStringUnderlineCharAt(g,
                           title, mnemIndex,
                           textRect.x, textRect.y + metrics.getAscent());
              g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
              BasicGraphicsUtils.drawStringUnderlineCharAt(g,
                           title, mnemIndex,
                           textRect.x - 1, textRect.y + metrics.getAscent() - 1);

          }
      }
    }
}
