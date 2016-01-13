package com.efounder.pub.comp;
/*
=====================================================================

  CompoundIcon.java

  Created by Claude Duguay
  Copyright (c) 2001

=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public class CompoundIcon
  implements Icon, SwingConstants
{
  protected static final int[]
    VALID_ORIENTATION = {VERTICAL, HORIZONTAL};
  protected int orientation, gap;
  protected Icon iconOne, iconTwo;
  public CompoundIcon(Icon iconOne, Icon iconTwo) {
    this(iconOne,iconTwo,HORIZONTAL,0);
  }
  public CompoundIcon(Icon iconOne, Icon iconTwo, int orientation, int gap)
  {
    if (!isLegalValue(orientation, VALID_ORIENTATION))
    {
      throw new IllegalArgumentException(
        "Orientation must be either VERTICAL or HORIZONTAL");
    }
    this.orientation = orientation;
    this.iconOne = iconOne;
    this.iconTwo = iconTwo;
    this.gap = gap;
  }

  public boolean isLegalValue(int value, int[] legal)
  {
    for (int i = 0; i < legal.length; i++)
    {
      if (value == legal[i]) return true;
    }
    return false;
  }

  public int getIconWidth()
  {
    int widthOne = 0;
    int widthTwo = 0;
    if ( iconOne != null )
      widthOne = iconOne.getIconWidth();
    if ( iconTwo != null )
      widthTwo = iconTwo.getIconWidth();
    if (orientation == VERTICAL)
    {
      return Math.max(widthOne, widthTwo);
    }
    else
    {
      return widthOne + widthTwo + gap;
    }
  }

  public int getIconHeight()
  {
    int heightOne = 0;
    int heightTwo = 0;
    if ( iconOne != null )
      heightOne = iconOne.getIconHeight();
    if ( iconTwo != null )
      heightTwo = iconTwo.getIconHeight();
    if (orientation == HORIZONTAL)
    {
      return Math.max(heightOne, heightOne);
    }
    else
    {
      return heightOne + heightTwo + gap;
    }
  }

  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    if ( iconOne != null ) {
      iconOne.paintIcon(c, g, x, y);
      if (orientation == VERTICAL) {
        y += iconOne.getIconHeight() + gap;
      }
      else {
        x += iconOne.getIconWidth() + gap;
      }
    }
    if ( iconTwo != null )
      iconTwo.paintIcon(c, g, x, y);
  }
  public Icon getMainIcon() {
    return iconTwo;
  }
}
